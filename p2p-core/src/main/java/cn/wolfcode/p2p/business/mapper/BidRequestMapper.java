package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.query.BidRequestQuery;
import cn.wolfcode.p2p.business.domain.BidRequest;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BidRequestMapper {
    int insert(BidRequest record);

    BidRequest selectByPrimaryKey(Long id);

    int updateByPrimaryKey(BidRequest record);

    BidRequest selectByCreateUser(LoginInfo loginInfo);

    Integer selectForCount(BidRequestQuery qo);

    List<BidRequest> selectForList(BidRequestQuery qo);

    List<BidRequest> selectPendingRelease(int state);

}