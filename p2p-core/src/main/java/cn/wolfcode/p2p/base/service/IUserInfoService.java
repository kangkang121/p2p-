package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;

public interface IUserInfoService {

    /**
     * 注册的时候创建一个loginfoe的信息
     */
    void init(LoginInfo loginInfo);

    /**
     * 根据id获取到UserInfo的信息
     */
    UserInfo getById(Long id);

    /**
     * 保存用户的基本信息
     */
    void save(UserInfo userInfo);

    void update(UserInfo userInfo);
}
