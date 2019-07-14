package cn.wolfcode.p2p.base.vo;

import java.util.Date;

public class VerifyCodeVO {

    /**
     * 验证码
     */
    private String code;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 发送时间
     */
    private Date sendTime;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
