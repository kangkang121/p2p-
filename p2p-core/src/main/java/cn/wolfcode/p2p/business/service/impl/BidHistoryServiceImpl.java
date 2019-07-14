package cn.wolfcode.p2p.business.service.impl;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import java.util.Date;

import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.domain.BidRequestAuditHistory;
import cn.wolfcode.p2p.business.mapper.BidRequestAuditHistoryMapper;
import cn.wolfcode.p2p.business.service.IBidHistoryService;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class BidHistoryServiceImpl implements IBidHistoryService {

    @Autowired
    private BidRequestAuditHistoryMapper bidRequestAuditHistoryMapper;
    @Autowired
    private IUserInfoService userInfoService;


    @Override
    public void saveBidHistory(BidRequest bidRequest,int state) {
        BidRequestAuditHistory bidRequestAuditHistory = new BidRequestAuditHistory();
        bidRequestAuditHistory.setBidRequestId(bidRequest.getId());
        bidRequestAuditHistory.setAuditType(bidRequest.getBidRequestType());
        userInfoService.getById(UserContext.getLoginInfo().getId());
        bidRequestAuditHistory.setAuditor(UserContext.getLoginInfo());
        bidRequestAuditHistory.setApplier(bidRequest.getCreateUser());
        bidRequestAuditHistory.setAuditTime(new Date());
        bidRequestAuditHistory.setApplyTime(bidRequest.getApplyTime());
        bidRequestAuditHistory.setState(state);
        bidRequestAuditHistory.setRemark(bidRequest.getNote());
        bidRequestAuditHistoryMapper.insert(bidRequestAuditHistory);

    }
}
