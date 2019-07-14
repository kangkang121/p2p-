package cn.wolfcode.p2p.base.service.impl;
import java.util.Date;
import java.util.List;

import cn.wolfcode.p2p.base.domain.Iplog;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.mapper.LoginInfoMapper;
import cn.wolfcode.p2p.base.mapper.RealAuthMapper;
import cn.wolfcode.p2p.base.mapper.UserInfoMapper;
import cn.wolfcode.p2p.base.query.RealAuthQuery;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.util.AssertUtil;
import cn.wolfcode.p2p.util.BitStatesUtils;
import cn.wolfcode.p2p.util.PageResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RealAuthServiceImpl implements IRealAuthService {

    @Autowired
    private RealAuthMapper realAuthMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public void save(RealAuth realAuth) {
        //2.申请人
        LoginInfo loginInfo = UserContext.getLoginInfo();
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(loginInfo.getId());

        //3.如果有一个在做实名认证就不能在实名认证了
        //已经通过的
        AssertUtil.instance().isFalse(userInfo.hasRealAuth(),"您已经通过实名认证了不需要在认证!");
        //已经申请了
        AssertUtil.instance().isNull(userInfo.getRealAuthId(),"您已经提交了申请,请耐心的等待!");


        //4.保存一个申请记录
        RealAuth realAuthtemp = new RealAuth();
        realAuthtemp.setRealName(realAuth.getRealName());
        realAuthtemp.setSex(realAuth.getSex());
        realAuthtemp.setIdNumber(realAuth.getIdNumber());
        realAuthtemp.setBornDate(realAuth.getBornDate());
        realAuthtemp.setAddress(realAuth.getAddress());
        realAuthtemp.setImage1(realAuth.getImage1());
        realAuthtemp.setImage2(realAuth.getImage2());
        realAuthtemp.setApplier(loginInfo);
        realAuthtemp.setApplyTime(new Date());
        realAuthtemp.setState(RealAuth.STATE_NORMAL);
        realAuthMapper.insert(realAuthtemp);

        //5.修改当前用户的realAuthorId设置保存的实名认证的对象
        userInfo.setRealAuthId(realAuthtemp.getId());
        userInfoMapper.updateByPrimaryKey(userInfo);
    }

    @Override
    public RealAuth getById(Long realAuthId) {
        return realAuthMapper.selectByPrimaryKey(realAuthId);
    }

    @Override
    public PageResult queryForList(RealAuthQuery qo) {
        Integer count = realAuthMapper.selectForCount(qo);
        if(count == null){
            return PageResult.empty(qo.getPageSize());
        }
        List<Iplog> list =  realAuthMapper.selectForList(qo);
        return new PageResult(list,count, qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public void updateState(Long id, Integer state, String remark) {
        //1. 判断基本参数  (后台的不用进行判断)
        //2. 判断当前实名认证的状态是否为待审核的状态
        RealAuth realAuth = realAuthMapper.selectByPrimaryKey(id);
        AssertUtil.instance().isFalse(realAuth.getState() != RealAuth.STATE_NORMAL,"状态不是审核状态,不能进行审核!");
        //3. 设置一下公共的审核信息
        realAuth.setAuditor(UserContext.getLoginInfo());
        realAuth.setAuditTime(new Date());
        realAuth.setState(state);
        realAuth.setRemark(remark);
        //进行修改
        update(realAuth);

        //4. 如果审核成功了
        UserInfo currentUserInfo = userInfoMapper.selectByPrimaryKey(realAuth.getApplier().getId());
        if (state == RealAuth.STATE_SUCCESS) {
            //4.1 给审核人加上对应的位状态
            currentUserInfo.updateBitState(BitStatesUtils.OP_REAL_AUTH);
            //4.2 设置userInfo中的冗余字段
            currentUserInfo.setRealName(realAuth.getRealName());
            currentUserInfo.setIdNumber(realAuth.getIdNumber());
        }else{
            //5. 如果审核失败
            //5.1 将userInfo中的realAuthId设置为对应的null值
            currentUserInfo.setRealAuthId(null);
        }
        //修改
        userInfoMapper.updateByPrimaryKey(currentUserInfo);
    }

    public void update(RealAuth realAuth){
        realAuthMapper.updateByPrimaryKey(realAuth);
    }
}
