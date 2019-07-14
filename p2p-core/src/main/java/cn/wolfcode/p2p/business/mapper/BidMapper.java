package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.Bid;
import java.util.List;

public interface BidMapper {

    int insert(Bid record);

    Bid selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Bid record);
}