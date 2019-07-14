package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;

import java.util.List;

public interface ILoginInfoService {

    /**
     * 通过id获取到登录对象
     */
    LoginInfo get(Long id);

    /**
     * 用户的注册验证
     */
    void userRegister(String username, String verifycode, String password, String confirmPwd, Integer userType);

    /**
     * 验证手机是否存在
     */
    boolean existUserName(String username);

    /**
     * 验证登录
     */
    void login(String username, String password, int userType);

    /**
     * 查询所有搭配  客服人员
     */
    List<LoginInfo> queryForAuditors();

}
