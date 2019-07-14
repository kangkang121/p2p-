package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.LoginInfo;

public interface IAccountService {
    /**
     *  注册的时候创建已给account]的数据
     */
    void init(LoginInfo loginInfo);

    /**
     * 根据id查询用户的账户
     */
    Account getById(Long id);
}
