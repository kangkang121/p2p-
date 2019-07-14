package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.mapper.LoginInfoMapper;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.vo.VerifyCodeVO;
import cn.wolfcode.p2p.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LoginInfoServiceImpl implements ILoginInfoService {

    @Autowired
    private LoginInfoMapper  loginInfoMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAccountService  accountService;

    @Override
    public LoginInfo get(Long id) {
        return loginInfoMapper.selectByPrimaryKey(id);

    }

    @Override
    public void userRegister(String username, String verifycode, String password, String confirmPwd, Integer userType) {
        //对应用户名验证
        AssertUtil.instance()
                //判断手机号不能为null
                .isNotNull(username,"手机号不能为空")
                //手机号长度不一致
                .isLength(username, Constants.PHONE_NUMBER,"手机号格式不正确")
                //判断是否为false
                .isFalse(existUserName(username),"手机号已经被注册!")
                //密码不能为空
                .isNotNull(password,"密码不能为空")
                //密码长度6 - 16 位
                .isLengthRange(password,Constants.MIN_PASSWORD_LENGTH,Constants.MAX_PASSWORD_LENGTH,"输入密码格式不正确")
                //两次密码不一致
                .isEquals(password,confirmPwd,"两次密码输入不一致")
                //验证码必须为4位
                .isLength(verifycode,Constants.VERIFYCODE_LENGTH,"验证码格式不正确");

        //1.判断现在的手机号和表单提交的手机号是否一致
        VerifyCodeVO vo = UserContext.getVerifyCodeVO();
        AssertUtil.instance().isNotNull(vo,"请重新发送验证码");
        String lastPhoneNumber = vo.getPhoneNumber();
        AssertUtil.instance().isEquals(lastPhoneNumber,username,"注册手机号和发送验证的手机号不一致,请重新注册!");
        //2.验证码过期校验
        Date sendTime = new Date();
        Date lastSendTime = vo.getSendTime();
        AssertUtil.instance().isFalse(DateUtil.getSecondsBetween(sendTime, lastSendTime)
                > Constants.VERIFYCODE_VALID_SECOND,"验证码已经失效,请重新获取!");
        //3.验证码是否正确校验
        AssertUtil.instance().isEquals(vo.getCode(),verifycode,"验证码输入有误,请重新输入");

        LoginInfo loginInfo = new LoginInfo();
        //信息验证
        loginInfo.setUsername(username);
        loginInfo.setPassword(MD5.encode(password+username));
        loginInfo.setState(0);
        loginInfo.setUserType(userType);
        loginInfoMapper.insert(loginInfo);

        //保存新用户的account和userInfo
        userInfoService.init(loginInfo);
        accountService.init(loginInfo);


    }

    @Override
    public boolean existUserName(String username) {
        return  (loginInfoMapper.selectCountByUsername(username) > 0);
    }

    @Override
    public void login(String username, String password, int userType) {

        if (userType == LoginInfo.USERTYPE_MGRSITE) {
            AssertUtil.instance()
                    .isLengthRange(password,Constants.MIN_PASSWORD_LENGTH,Constants.MAX_PASSWORD_LENGTH,"密码输入有误");
        }else{
            //1. 对参数进行后台验证
            AssertUtil.instance()
                    .isLength(username,Constants.PHONE_NUMBER,"手机号格式不正确")
                    .isPhoneNumber(username,"手机号格式不正确")
                    .isLengthRange(password,Constants.MIN_PASSWORD_LENGTH,Constants.MAX_PASSWORD_LENGTH,"密码输入有误");

        }

        //2. 进行查询看用户是否存在
        LoginInfo loginInfo = loginInfoMapper.selectByUsernameAndPassword(username, MD5.encode(password+username));
        AssertUtil.instance().isNotNull(loginInfo,"账户或者密码错误!");

        //3. 判断用户类型是否一致
        AssertUtil.instance().isTrue((loginInfo.getUserType() == userType),"用户类型不一致,请重新输入!");

        //4. 如果查询到了判断用户状态是否正确
        AssertUtil.instance().isFalse((loginInfo.getState() == LoginInfo.STATE_LOCK),"该账户被锁定,请联系客服!");

        //5. 如果都对的话  将信息存储到session中
        UserContext.setLoginInfo(loginInfo);
    }

    @Override
    public List<LoginInfo> queryForAuditors() {
        return loginInfoMapper.queryForAuditors();
    }
}
