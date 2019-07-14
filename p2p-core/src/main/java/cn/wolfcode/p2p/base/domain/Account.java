package cn.wolfcode.p2p.base.domain;

import cn.wolfcode.p2p.util.Constants;

import java.math.BigDecimal;

/**
 * 用金额账号
 */
public class Account extends BaseDomain {
    /**
     *   交易密码
     */
    private String tradePassword;

    /**
     * 账户可用余额
     */
    private BigDecimal usableAmount = Constants.ZERO;

    /**
     * 账户冻结金额
     */
    private BigDecimal freezedAmount = Constants.ZERO;

    /**
     * 版本号
     */
    private Long version = 0L;

    /**
     * 账户待收利息
     */
    private BigDecimal unReceiveInterest = Constants.ZERO;

    /***
     * 账户待收本金
     */
    private BigDecimal unReceivePrincipal = Constants.ZERO;

    /**
     * 账户待还金额
     */
    private BigDecimal unReturnAmount = Constants.ZERO;

    /**
     * 账户剩余授信额度
     */
    private BigDecimal remainBorrowLimitAmount = Constants.DEFAULT_BORROW_LIMIT;

    /**
     * 账户授信额度
     */
    private BigDecimal borrowLimit = Constants.DEFAULT_BORROW_LIMIT;

    /**
     * 表示对应的总金额
     */
    public BigDecimal getTotalCountAmount() {
        return usableAmount.add(freezedAmount).add(unReceivePrincipal);
    }


    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    public BigDecimal getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(BigDecimal usableAmount) {
        this.usableAmount = usableAmount;
    }

    public BigDecimal getFreezedAmount() {
        return freezedAmount;
    }

    public void setFreezedAmount(BigDecimal freezedAmount) {
        this.freezedAmount = freezedAmount;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public BigDecimal getUnReceiveInterest() {
        return unReceiveInterest;
    }

    public void setUnReceiveInterest(BigDecimal unReceiveInterest) {
        this.unReceiveInterest = unReceiveInterest;
    }

    public BigDecimal getUnReceivePrincipal() {
        return unReceivePrincipal;
    }

    public void setUnReceivePrincipal(BigDecimal unReceivePrincipal) {
        this.unReceivePrincipal = unReceivePrincipal;
    }

    public BigDecimal getUnReturnAmount() {
        return unReturnAmount;
    }

    public void setUnReturnAmount(BigDecimal unReturnAmount) {
        this.unReturnAmount = unReturnAmount;
    }

    public BigDecimal getRemainBorrowLimitAmount() {
        return remainBorrowLimitAmount;
    }

    public void setRemainBorrowLimitAmount(BigDecimal remainBorrowLimitAmount) {
        this.remainBorrowLimitAmount = remainBorrowLimitAmount;
    }

    public BigDecimal getBorrowLimit() {
        return borrowLimit;
    }

    public void setBorrowLimit(BigDecimal borrowLimit) {
        this.borrowLimit = borrowLimit;
    }
}