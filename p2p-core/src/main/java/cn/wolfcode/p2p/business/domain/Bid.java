package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.base.domain.LoginInfo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投款模型
 */
public class Bid extends BaseDomain {

    /**
     * 实际年利率
     */
    private BigDecimal actualRate;
    /**
     * 投标有效金额
     */
    private BigDecimal availableAmount;
    /**
     * 来自于哪个借款标
     */
    private Long       bidRequestId;
    /**
     * 标的名称
     */
    private String     bidRequestTitle;
    /**
     * 投标人
     */
    private LoginInfo  bidUser;
    /**
     * 投标时间
     */
    private Date       bidTime;
    /**
     * 标的状态
     */
    private Integer    bidRequestState;


    public BigDecimal getActualRate() {
        return actualRate;
    }

    public void setActualRate(BigDecimal actualRate) {
        this.actualRate = actualRate;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Long getBidRequestId() {
        return bidRequestId;
    }

    public void setBidRequestId(Long bidRequestId) {
        this.bidRequestId = bidRequestId;
    }

    public String getBidRequestTitle() {
        return bidRequestTitle;
    }

    public void setBidRequestTitle(String bidRequestTitle) {
        this.bidRequestTitle = bidRequestTitle;
    }

    public LoginInfo getBidUser() {
        return bidUser;
    }

    public void setBidUser(LoginInfo bidUser) {
        this.bidUser = bidUser;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    public Integer getBidRequestState() {
        return bidRequestState;
    }

    public void setBidRequestState(Integer bidRequestState) {
        this.bidRequestState = bidRequestState;
    }
}
