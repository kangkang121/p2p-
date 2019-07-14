package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface LoginInfoMapper {

    int insert(LoginInfo record);

    LoginInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKey(LoginInfo record);

    int selectCountByUsername(String username);

    LoginInfo selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    List<LoginInfo> queryForAuditors();

}