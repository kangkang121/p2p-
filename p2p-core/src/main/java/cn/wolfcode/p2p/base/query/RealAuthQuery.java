package cn.wolfcode.p2p.base.query;

import cn.wolfcode.p2p.util.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RealAuthQuery extends QueryObject {

    /**
     * 状态
     */
    private Integer state = -1;
    /**
     * 开始查询时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    /**
     * 结束查询时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getBeginDate() {
        return DateUtil.getBeginDate(beginDate);
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return DateUtil.getEndDate(endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
