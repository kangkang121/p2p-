package cn.wolfcode.p2p.base.service.impl;
import cn.wolfcode.p2p.base.domain.OrderTime;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.*;

import java.util.Date;
import java.util.List;

import cn.wolfcode.p2p.base.exception.CustomerException;
import cn.wolfcode.p2p.base.mapper.UserInfoMapper;
import cn.wolfcode.p2p.base.mapper.VideoAuditMapper;
import cn.wolfcode.p2p.base.query.VideoAuditQuery;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.service.IVideoAuditService;
import cn.wolfcode.p2p.util.AssertUtil;
import cn.wolfcode.p2p.util.BitStatesUtils;
import cn.wolfcode.p2p.util.PageResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;

@Service
@Transactional
public class VideoAuditServiceImpl implements IVideoAuditService {

    @Autowired
    private VideoAuditMapper videoAuditMapper;
    @Autowired
    private IUserInfoService userInfoService;


    @Override
    public void audit(VideoAudit videoAudit) {
        //1. 简单参数判断
        //2. 判断是否已经通过认证 根据位状态进行判断
        LoginInfo loginInfo = UserContext.getLoginInfo();
        UserInfo userInfo = userInfoService.getById(loginInfo.getId());
        AssertUtil.instance().isFalse(userInfo.hasVedioAuth(),"您已经通过视频认证了!");
        //3. 判断是否已经提交了认证
        AssertUtil.instance().isFalse(userInfo.getVideoAuditId() != null,"正在审核中!请耐心等待!");
        //4. 获取参数进行保存
        VideoAudit videoAuditTemp = new VideoAudit();

        videoAuditTemp.setOrderTime(videoAudit.getOrderTime());
        videoAuditTemp.setOrderDate(videoAudit.getOrderDate());
        videoAuditTemp.setApplier(loginInfo);
        videoAuditTemp.setApplyTime(new Date());
        videoAuditTemp.setState(BaseAuthDomain.STATE_NORMAL);
        videoAuditTemp.setAuditor(videoAudit.getAuditor());
        videoAuditMapper.insert(videoAuditTemp);

        //5. 修改当前的用户的videoAduitId
        userInfo.setVideoAuditId(videoAuditTemp.getId());
        userInfoService.update(userInfo);

    }

    @Override
    public VideoAudit getById(Long videoAuditId) {
        return videoAuditMapper.selectByPrimaryKey(videoAuditId);
    }

    @Override
    public PageResult queryForList(VideoAuditQuery qo) {
        Integer count = videoAuditMapper.selectForCount(qo);
        if(count == null){
            return PageResult.empty(qo.getPageSize());
        }
        List<VideoAudit> list =  videoAuditMapper.selectForList(qo);
        return new PageResult(list,count, qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public void attest(Long id, Integer state, String remark) {
        //1. 简单参数判断
        //2. 认证对象的状态必须处于待审核状态
        VideoAudit videoAudit = videoAuditMapper.selectByPrimaryKey(id);
        AssertUtil.instance().isFalse(videoAudit.getState() != BaseAuthDomain.STATE_NORMAL,"当前状态不能进行审核!");

        //3. 修改对应的信息
        videoAudit.setAuditTime(new Date());
        videoAudit.setState(state);
        videoAudit.setRemark(remark);
        update(videoAudit);

        UserInfo userInfo = userInfoService.getById(videoAudit.getApplier().getId());
        if (state == BaseAuthDomain.STATE_SUCCESS) {
            //4. 认证通过
            //4.1修改对应的位状态
            userInfo.updateBitState(BitStatesUtils.OP_VEDIO_AUTH);
        }else{
            //5.认证失败
            //5.1修改userInfo中的videAuthorId的值为null
            userInfo.setVideoAuditId(null);
        }
        userInfoService.update(userInfo);
    }


    public void update(VideoAudit videoAudit){
        try {
            videoAuditMapper.updateByPrimaryKey(videoAudit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException("请稍后重试!");
        }
    }

    @Override
    public void cancleVideoAudit() {
        //1. 获取到当前用户的userInfo
        LoginInfo loginInfo = UserContext.getLoginInfo();
        UserInfo userInfo = userInfoService.getById(loginInfo.getId());
        //2. 删除对应的申请信息
        videoAuditMapper.delectById(userInfo.getVideoAuditId());
        //3. 设置当前的userInfo中的videIntroiId为null
        userInfo.setVideoAuditId(null);
        userInfoService.update(userInfo);
    }
}
