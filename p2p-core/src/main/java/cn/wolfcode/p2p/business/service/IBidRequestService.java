package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.query.BidRequestQuery;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.util.PageResult;

import java.util.Date;
import java.util.List;

public interface IBidRequestService {

    /**
     * 申请对应的提交记录
     */
    void apply(BidRequest br);

    /**
     * 后台审核查询
     */
    PageResult queryResult(BidRequestQuery qo);

    /**
     * 后台审核认证
     * @param id 审核认证的id
     * @param state 审核认证的状态 (决绝或者成功)
     * @param publishTime 审核认证的发布时间 无代表立即发布
     * @param remark  审核认证的备注
     */
    void bidrequestPublish(Long id, Integer state, Date publishTime, String remark);

    /**
     * 查询五个发标的的事件
     */
    List<BidRequest> listIndex();

    /**
     * 利用定时器查询数据库中待发布的标
     */
    void addPendingReleaseBidRequest2Cache();

    /**
     * 根据缓存中的状态去发布对应的标
     */
    void releaseBidRequestByCache();

    BidRequest getById(Long id);
}
