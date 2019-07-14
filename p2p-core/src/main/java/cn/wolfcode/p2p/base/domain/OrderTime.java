package cn.wolfcode.p2p.base.domain;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 预约的事件端木星
 */
public class OrderTime extends BaseDomain {
    /**
     * 开始事件
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String begin;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String end;

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin == null ? null : begin.trim();
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end == null ? null : end.trim();
    }
}