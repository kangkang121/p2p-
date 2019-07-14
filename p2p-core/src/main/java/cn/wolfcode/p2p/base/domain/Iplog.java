package cn.wolfcode.p2p.base.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户登录记录模型
 */
public class Iplog extends BaseDomain {

    /**
     * 用户登陆成功
     */
    public static final int STATE_SUCCESS = 0;
    /**
     * 用户登陆失败
     */
    public static final int STATE_FAILURE = 1;

    /**
     *用户名字
     */
    private String username;
    /**
     * 用户登录ip
     */
    private String ip;
    /**
     * 用户登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date loginTime;

    /**
     * 用户是否登录后成功
     */
    private Integer state;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 页面上显示状态的名字
     */
    public String getStateName(){
        return state == STATE_SUCCESS ? "登陆成功" : "登陆失败";
    }
    /**
     * 页面上显示用户类型的名字
     */
    public String getUserTypeName(){
        return userType == LoginInfo.USERTYPE_MGRSITE ? "后台用户" : "前台用户";
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
