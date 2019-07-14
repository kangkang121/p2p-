package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseAuthDomain;

/**
 * 审核借款的模型
 */
public class BidRequestAuditHistory extends BaseAuthDomain {

    /**
     * 发标中状态
     */
    public static final int AUDITTYPE_PUBLISH = 0;
    /**
     * 一审
     */
    public static final int AUDITTYPE_AUDIT1 = 1;
    /**
     * 二审
     */
    public static final int AUDITTYPE_AUDIT2 = 2;

    /**
     * 借款对象Id
     */
    private Long bidRequestId;
    /**
     * 审核类型
     */
    private Integer auditType;

    public Long getBidRequestId() {
        return bidRequestId;
    }

    public void setBidRequestId(Long bidRequestId) {
        this.bidRequestId = bidRequestId;
    }

    public Integer getAuditType() {
        return auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }
}
