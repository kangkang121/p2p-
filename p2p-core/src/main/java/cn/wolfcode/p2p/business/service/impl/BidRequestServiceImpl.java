package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.BaseAuthDomain;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.query.BidRequestQuery;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.business.domain.BidRequestAuditHistory;
import cn.wolfcode.p2p.business.service.IBidHistoryService;
import cn.wolfcode.p2p.business.service.IBidRequestService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.mapper.BidRequestMapper;
import cn.wolfcode.p2p.util.*;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@Transactional
public class BidRequestServiceImpl implements IBidRequestService,ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private BidRequestMapper   bidRequestMapper;
    @Autowired
    private IUserInfoService   userInfoService;
    @Autowired
    private IAccountService    accountService;
    @Autowired
    private IBidHistoryService bidHistoryService;

    /**
     * 建立一个对应的缓存容器存储到内存中
     */
    private ConcurrentLinkedQueue<BidRequest> pendingReleaseBidRequestCache = new ConcurrentLinkedQueue<>();


    @Override
    public void apply(BidRequest br) {
        //1. 基本参数判断
        //2. 判断位状态是否为申请状态
        LoginInfo loginInfo = UserContext.getLoginInfo();
        UserInfo userInfo = userInfoService.getById(loginInfo.getId());
        Account account = accountService.getById(loginInfo.getId());
        AssertUtil.instance()
                .isFalse(!userInfo.hasAllCertification(),"请先完成信息认证!")
                //验证node信息不为null
                .isNotNull(br.getDescription(),"请填写您的借款描述信息!")
                //判断借款金额是否小于系统规定的最小金额
                .isFalse(br.getBidRequestAmount()
                        .compareTo(Constants.MINBIDREQUESTAMOUNT) < 0,"借款金额太少了,不符合要求")
                //判断借款金额是否大于当前用户的剩余授信额度
                .isFalse(br.getBidRequestAmount()
                        .compareTo(account.getRemainBorrowLimitAmount()) > 0,"借款金额不能大于自己的剩余信用额度哦!")
                //判断利息是否大于系统规定的最小利率
                .isFalse(br.getCurrentRate().compareTo(Constants.MINRATE)<0,"设置的利率不符合本平台的要求,请重新设置")
                //判断利息是否小于系统规定的最大利率
                .isFalse(br.getCurrentRate().compareTo(Constants.MAXRATE)>0,"设置的利率不符合本平台的要求,请重新设置")
                //判断当前用户是否 已经提交了已给待审核的标
                .isFalse(userInfo.hasBidRequest(),"您已经申请了一个借款,请先完成那个")
                //判断标题是否为null
                .isNotNull(br.getTitle(),"标题不能为null")
                //判断最小投标是否大于系统规定的最小的投标
                .isFalse(br.getMinBidAmount().compareTo(Constants.MINBIDAMOUNT) < 0,"设置的最小投放金额不符合平台的规定,请从新设置");

        //3. 设置基本参数值
        BidRequest bidRequestTemp = new BidRequest();
        bidRequestTemp.setBidRequestState(Constants.BIDREQUEST_STATE_PUBLISH_PENDING);
        bidRequestTemp.setBidRequestAmount(br.getBidRequestAmount());
        bidRequestTemp.setCurrentRate(br.getCurrentRate());
        bidRequestTemp.setMinBidAmount(br.getMinBidAmount());
        bidRequestTemp.setMonthes2Return(br.getMonthes2Return());
        bidRequestTemp.setTitle(br.getTitle());
        bidRequestTemp.setDescription(br.getDescription());
        bidRequestTemp.setDisableDays(br.getDisableDays());
        bidRequestTemp.setCreateUser(loginInfo);
        bidRequestTemp.setApplyTime(new Date());
        bidRequestTemp.setPublishTime(new Date());

        BidRequest bidRequest =  bidRequestMapper.selectByCreateUser(loginInfo);
        if (bidRequest == null) {
            //借标次数
            bidRequestTemp.setBidCount(1);
        }else{
            Integer bidCount = bidRequest.getBidCount();
            ++bidCount;
            bidRequestTemp.setBidCount(bidCount);
        }
        //招标截止时间
        bidRequestTemp.setDisableDate(DateUtils.addDays(bidRequestTemp.getApplyTime(),br.getDisableDays()));
        //总回报金额
        bidRequestTemp.setTotalRewardAmount(CalculatetUtil.calTotalInterest
                (bidRequestTemp.getReturnType(),bidRequestTemp.getBidRequestAmount(),bidRequestTemp.getCurrentRate()
                        , bidRequestTemp.getMonthes2Return()));

        //4. 进行保存
        bidRequestMapper.insert(bidRequestTemp);

        //5. 修改对应的位状态  为已经提交申请
        userInfo.updateBitState(BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
        userInfoService.update(userInfo);
    }

    @Override
    public PageResult queryResult(BidRequestQuery qo) {
        Integer count = bidRequestMapper.selectForCount(qo);
        if(count == null){
            return PageResult.empty(qo.getPageSize());
        }
        List<BidRequest> list =  bidRequestMapper.selectForList(qo);
        return new PageResult(list,count, qo.getCurrentPage(),qo.getPageSize());
    }

    @Override
    public void bidrequestPublish(Long id, Integer state, Date publishTime, String remark) {
        //准备
        LoginInfo loginInfo = UserContext.getLoginInfo();
        UserInfo userInfo = userInfoService.getById(loginInfo.getId());
        //1. 基本参数认证
        //2. 判断当前用户的状态是否为发标待发布状态
        BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(id);

        AssertUtil.instance().isFalse(bidRequest.getBidRequestState()!=Constants.BIDREQUEST_STATE_PUBLISH_PENDING ,
                "当前状态不能发标");
        //3. 设置风控状态
        bidRequest.setNote(remark);

        //3.1 创建一个审核记录表保存对应的记录
        if (state == BaseAuthDomain.STATE_SUCCESS) {
            //4. 审核通过
            //判断 publishTime是否有值
            if (publishTime == null) {
                //没有值的话立即发布
                //设置借标的状态为招标中
                bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_BIDDING);
                //设置一下发标的时间  就是现在系统的时间
                bidRequest.setPublishTime(new Date());

            }else{
                //有值的话按照制定的时间进行发布
                //设置借标的状态为 待发标
                bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_PENDING_RELEASE);
                //设置一下发标时间 publishTime
                bidRequest.setPublishTime(publishTime);
            }

            //设置一下发标截止时间 publishTime
            bidRequest.setDisableDate(DateUtils.addDays(bidRequest.getPublishTime(),bidRequest.getDisableDays()));

        }else {
            //5. 审核拒绝
            //移除掉对应的userInfo中的位状态
            UserInfo userInfoTemp = userInfoService.getById(bidRequest.getCreateUser().getId());
            userInfoTemp.removeBitState(BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
            userInfoService.update(userInfo);
            //修改对应的状态为发标审核失败
            bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_PUBLISH_REFUSE);
        }


        /**
         * 设置审核类型
         */
        bidHistoryService.saveBidHistory(bidRequest,state);

        //7. 执行对应的更新
        update(bidRequest);

        //将当前的标放到内存缓存 中但是 必须是待审状态
        if (bidRequest.getBidRequestState() == Constants.BIDREQUEST_STATE_PENDING_RELEASE) {
            pendingReleaseBidRequestCache.offer(bidRequest);
        }
    }

    public void update(BidRequest bidRequest) {
        System.out.println(pendingReleaseBidRequestCache);
        AssertUtil.instance().isFalse(bidRequestMapper.updateByPrimaryKey(bidRequest)==0
                ,"借款修改失败,乐观锁异常");
    }

    @Override
    public List<BidRequest> listIndex() {

        BidRequestQuery qo = new BidRequestQuery();
        qo.setState(Constants.BIDREQUEST_STATE_PENDING_RELEASE);
        //按照时间排序
        qo.setOrderBy("br.publish_time");
        qo.setOrderType("ASC");
        //设置查询的大小
        qo.setPageSize(5);
        return bidRequestMapper.selectForList(qo);
    }


    /**
     * 系统重启的时候触发的事件
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        addPendingReleaseBidRequest2Cache();
    }


    @Override
    public void addPendingReleaseBidRequest2Cache() {
        List<BidRequest> brs = bidRequestMapper.selectPendingRelease
                (Constants.RETURN_TYPE_MONTH_INTEREST_PRINCIPAL);

        //进行迭代如果不包含的话需要加到对应的缓存队列中
        if (pendingReleaseBidRequestCache.size() != 0) {
            for (BidRequest bidRequest : pendingReleaseBidRequestCache) {
                if (!brs.contains(bidRequest)) {
                    pendingReleaseBidRequestCache.offer(bidRequest);
                }
            }
        }else{
            for (BidRequest br : brs) {
                pendingReleaseBidRequestCache.offer(br);
            }
        }
    }

    @Override
    public void releaseBidRequestByCache() {
        Date now = new Date();
        for (BidRequest bidRequest : pendingReleaseBidRequestCache) {
            if (bidRequest.getPublishTime().before(now)) {
                bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_BIDDING);
                bidRequest.setVersion(bidRequest.getVersion()+1);
                System.out.println("修改状态");
                //从队列中删除
                pendingReleaseBidRequestCache.remove(bidRequest);
                update(bidRequest);
            }
        }
    }

    @Override
    public BidRequest getById(Long id) {
        return bidRequestMapper.selectByPrimaryKey(id);
    }
}
