package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.Iplog;
import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.query.RealAuthQuery;
import java.util.List;

public interface RealAuthMapper {

    int insert(RealAuth record);

    RealAuth selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RealAuth record);

    Integer selectForCount(RealAuthQuery qo);

    List<Iplog> selectForList(RealAuthQuery qo);
}