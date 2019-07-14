package cn.wolfcode.p2p.base.domain;

/**
 * 用户登录模型
 */
public class LoginInfo extends BaseDomain {

    /**
     * 登录状态 : 正常
     */
    public static final int STATE_NORMAL = 0;
    /**
     * 登录状态  :  锁定
     */
    public static final int STATE_LOCK = 1;
    /**
     * 用户类型 :  前台用户
     */
    public static final int USERTYPE_WEBSITE = 0;
    /**
     * 用户类型 :  后台用户
     */
    public static final int USERTYPE_MGRSITE = 1;

    /*
        用户名
     */
    private String username;
    /*
        密码
     */
    private String password;
    /*
        登录状态  :  登录  ;  锁定
     */
    private Integer state = STATE_NORMAL ;

    /**
     * 用户类型
     */
    private Integer userType ;

    /**
     * 是否是审核视频得客服
     */
    private boolean auditor = false;

    public boolean getAuditor() {
        return auditor;
    }

    public void setAuditor(boolean auditor) {
        this.auditor = auditor;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
