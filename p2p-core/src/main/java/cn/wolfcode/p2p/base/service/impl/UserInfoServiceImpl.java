package cn.wolfcode.p2p.base.service.impl;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.CustomerException;
import cn.wolfcode.p2p.base.mapper.UserInfoMapper;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.BitStatesUtils;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void init(LoginInfo loginInfo) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(loginInfo.getId());
        userInfoMapper.insert(userInfo);
    }

    @Override
    public UserInfo getById(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(UserInfo userInfo) {
        //1.. 修改用户资料  找到当前用户的id  进行设置值 不能直接进行保存页面传来的信息
        Long userInfoId = UserContext.getLoginInfo().getId();
        UserInfo userInfoTemp = userInfoMapper.selectByPrimaryKey(userInfoId);
        userInfoTemp.setIncomeGrade(userInfo.getIncomeGrade());
        userInfoTemp.setMarriage(userInfo.getMarriage());
        userInfoTemp.setKidCount(userInfo.getKidCount());
        userInfoTemp.setEducationBackground(userInfo.getEducationBackground());
        userInfoTemp.setHouseCondition(userInfo.getHouseCondition());
        userInfoTemp.setHouseCondition(userInfo.getHouseCondition());

        //2. 添加对应的位状态
        if (!userInfo.hasBasicInfo()) {
            long state = BitStatesUtils.addState(userInfoTemp.getBitState(), BitStatesUtils.OP_BASIC_INFO);
            userInfoTemp.setBitState(state);
        }

        //3.执行修改
        update(userInfoTemp);
    }

    public void update(UserInfo userInfo){
        try {
            userInfoMapper.updateByPrimaryKey(userInfo);
        } catch (Exception e) {
            //肯定是乐观锁异常
            throw new CustomerException("系统繁忙请稍后重试!");
        }
    }
}
