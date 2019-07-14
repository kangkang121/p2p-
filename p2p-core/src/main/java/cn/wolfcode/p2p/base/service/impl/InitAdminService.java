package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.mapper.LoginInfoMapper;
import cn.wolfcode.p2p.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * 初始化Admin用户
 */
@Service
public class InitAdminService implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //需要进行判断一下是否有当前的用户存在
        int count = loginInfoMapper.selectCountByUsername("admin");
        if (count != 0) {
            return;
        }

        LoginInfo loginIngfo = new LoginInfo();
        loginIngfo.setUserType(LoginInfo.USERTYPE_MGRSITE);
        loginIngfo.setUsername("admin");
        loginIngfo.setPassword(MD5.encode("000000admin"));

        loginInfoMapper.insert(loginIngfo);
    }
}
