package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.business.domain.BidRequest;

public interface IBidHistoryService {


    /**
     * 保存一个审核记录
     * @param bidRequest
     */
    void saveBidHistory(BidRequest bidRequest,int state);

}
