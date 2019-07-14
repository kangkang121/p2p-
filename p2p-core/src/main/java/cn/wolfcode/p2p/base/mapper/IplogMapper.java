package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.Iplog;
import cn.wolfcode.p2p.base.query.QueryObject;
import java.util.List;

public interface IplogMapper {

    int insert(Iplog record);

    Integer selectForCount(QueryObject qo);

    List<Iplog> selectForList(QueryObject qo);
}