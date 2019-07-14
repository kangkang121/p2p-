package cn.wolfcode.p2p.base.domain;

import java.util.Date;

/**
 * 用户审核基本类型
 */
public class BaseAuthDomain extends BaseDomain {

    /**
     * 待审核
     */
    public static final int STATE_NORMAL = 0;
    /**
     * 审核通过
     */
    public static final int STATE_SUCCESS = 1;
    /**
     * 审核失败
     */
    public static final int STATE_FAILUERE = 2;

    /**
     * 认证人
     */
    private LoginInfo auditor;
    /**
     * 审核人
     */
    private LoginInfo applier;
    /**
     * 审核时间
     */
    private Date      auditTime;
    /**
     * 申请时间
     */
    private Date      applyTime;
    /**
     * 状态  用来标记用户是否审核通过
     */
    private Integer   state;
    /**
     * 审核记录
     */
    private String    remark;


    /**
     *  状态的显示
     */
    public String getStateDisplay(){
        switch (state){
            case STATE_NORMAL : return "申请中";
            case STATE_SUCCESS : return "审核通过";
            case STATE_FAILUERE : return "审核拒绝";
        }
        return "状态异常";
    }

    public LoginInfo getAuditor() {
        return auditor;
    }

    public void setAuditor(LoginInfo auditor) {
        this.auditor = auditor;
    }

    public LoginInfo getApplier() {
        return applier;
    }

    public void setApplier(LoginInfo applier) {
        this.applier = applier;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
