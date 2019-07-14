package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.util.CalculatetUtil;
import cn.wolfcode.p2p.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.net.CacheRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static cn.wolfcode.p2p.util.Constants.*;

/**
 * 借款模型
 */
public class BidRequest extends BaseDomain {

    private Integer    version = 0;
    /**
     * 还款方式  默认为按月分期还
     */
    private Integer    returnType = Constants.RETURN_TYPE_MONTH_INTEREST_PRINCIPAL;
    /**
     * 借款类型  默认为 信用贷
     */
    private Integer    bidRequestType = Constants.BIDREQUEST_TYPE_NORMAL;
    /**
     * 借款的的状态 默认为发标待审状态
     */
    private Integer    bidRequestState = BIDREQUEST_STATE_PENDING_RELEASE;
    /**
     * 借款的金额
     */
    private BigDecimal bidRequestAmount;
    /**
     * 借款的利率
     */
    private BigDecimal currentRate;
    /**
     * 最小的投标金额
     */
    private BigDecimal minBidAmount;
    /**
     * 借款期限
     */
    private Integer    monthes2Return;
    /**
     * 投标次数
     */
    private Integer    bidCount;
    /**
     * 总回报金额
     */
    private BigDecimal    totalRewardAmount;
    /**
     * 投款总额
     */
    private BigDecimal currentSum = new BigDecimal("0.000");
    /**
     * 借款标题
     */
    private String     title;
    /**
     * 借款描述
     */
    private String     description;
    /**
     * 风控评审意见
     */
    private String     note;
    /**
     * 招标到期时间
     */
    private Date       disableDate;
    /**
     * 招标天数
     */
    private Integer    disableDays;
    /**
     * 借款人
     */
    private LoginInfo  createUser;
    /**
     * 借款的的投标记录
     */
    private List<Bid>  bids;
    /**
     * 申请时间
     */
    private Date       applyTime;
    /**
     * 发布时间
     */
    private Date       publishTime;

    /**
     * 复写equals和hashCode的方法 在进行比较的时候是哦用
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BidRequest that = (BidRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * 显示对应的状态值
     */
    public String getBidRequestStateDisplay(){
        switch (bidRequestState){
            case BIDREQUEST_STATE_PENDING_RELEASE : return "待发布";
            case BIDREQUEST_STATE_BIDDING : return "招标中";
            case BIDREQUEST_STATE_UNDO : return "招标中";
            case BIDREQUEST_STATE_BIDDING_OVERDUE : return "流标";
            case BIDREQUEST_STATE_APPROVE_PENDING_1 : return "满标1审";
            case BIDREQUEST_STATE_APPROVE_PENDING_2 : return "满标2审";
            case BIDREQUEST_STATE_REJECTED : return "满标审核被拒绝";
            case BIDREQUEST_STATE_PAYING_BACK : return "还款中";
            case BIDREQUEST_STATE_COMPLETE_PAY_BACK : return "已还清";
            case BIDREQUEST_STATE_PAY_BACK_OVERDUE : return "逾期";
            case BIDREQUEST_STATE_PUBLISH_REFUSE : return "逾期";
            case BIDREQUEST_STATE_PUBLISH_PENDING : return "发标待审";
        }
        return "状态异常";
    }

    /**
     * 获取到Json字符串
     */
    public String getJsonString(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("username",getCreateUser().getUsername());
        map.put("title",title);
        map.put("bidRequestAmount",bidRequestAmount);
        map.put("currentRate",currentRate);
        map.put("monthes2Return",monthes2Return);
        map.put("returnType",getReturntypeDisplay());
        map.put("totalRewardAmount",totalRewardAmount);
        //转换成jsron字符串
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取到returType的名字
     */
    public String getReturntypeDisplay(){
        return returnType == Constants.RETURN_TYPE_MONTH_INTEREST_PRINCIPAL ? "按月分期还款" : "按月到期还款";
    }

    /**
     * 获取到一个投资得百分比
     */
    public Integer getPersent(){
        return  currentSum.divide(bidRequestAmount).multiply(CalculatetUtil.ONE_HUNDRED).intValue();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getReturnType() {
        return returnType;
    }

    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
    }

    public Integer getBidRequestType() {
        return bidRequestType;
    }

    public void setBidRequestType(Integer bidRequestType) {
        this.bidRequestType = bidRequestType;
    }

    public Integer getBidRequestState() {
        return bidRequestState;
    }

    public void setBidRequestState(Integer bidRequestState) {
        this.bidRequestState = bidRequestState;
    }

    public BigDecimal getBidRequestAmount() {
        return bidRequestAmount;
    }

    public void setBidRequestAmount(BigDecimal bidRequestAmount) {
        this.bidRequestAmount = bidRequestAmount;
    }

    public BigDecimal getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(BigDecimal currentRate) {
        this.currentRate = currentRate;
    }

    public BigDecimal getMinBidAmount() {
        return minBidAmount;
    }

    public void setMinBidAmount(BigDecimal minBidAmount) {
        this.minBidAmount = minBidAmount;
    }

    public Integer getMonthes2Return() {
        return monthes2Return;
    }

    public void setMonthes2Return(Integer monthes2Return) {
        this.monthes2Return = monthes2Return;
    }

    public BigDecimal getTotalRewardAmount() {
        return totalRewardAmount;
    }

    public Integer getBidCount() {
        return bidCount;
    }

    public void setBidCount(Integer bidCount) {
        this.bidCount = bidCount;
    }


    public void setTotalRewardAmount(BigDecimal totalRewardAmount) {
        this.totalRewardAmount = totalRewardAmount;
    }

    public BigDecimal getCurrentSum() {
        return currentSum;
    }

    public void setCurrentSum(BigDecimal currentSum) {
        this.currentSum = currentSum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDisableDate() {
        return disableDate;
    }

    public void setDisableDate(Date disableDate) {
        this.disableDate = disableDate;
    }

    public Integer getDisableDays() {
        return disableDays;
    }

    public void setDisableDays(Integer disableDays) {
        this.disableDays = disableDays;
    }

    public LoginInfo getCreateUser() {
        return createUser;
    }

    public void setCreateUser(LoginInfo createUser) {
        this.createUser = createUser;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
