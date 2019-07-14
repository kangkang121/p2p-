package cn.wolfcode.p2p.util;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.vo.VerifyCodeVO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户上下文
 */
public abstract class UserContext {

    /**
     * 验证码的常量
     */
    public static final String VERIFYCODE_IN_SESSION = "verifycode_in_session";
    /**
     * 存放 用户信息
     */
    public static final String LOGINFOR_IN_SESSION = "loginfor";

    /**
     * 获取到当前的请求
     */
    private static HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    /**
     * 获取到当前的seesion对象
     */
    private static HttpSession getSession(){
        return getRequest().getSession();
    }


    /**
     * 将vo中的对象存储到seeison中
     */
    public static void setVerifyCodeVO(VerifyCodeVO vo){
        getSession().setAttribute(VERIFYCODE_IN_SESSION,vo);
    }

    /**
     *  获取到seesion中的对象
     */
    public static VerifyCodeVO getVerifyCodeVO(){
        return (VerifyCodeVO) getSession().getAttribute(VERIFYCODE_IN_SESSION);
    }

    /**
     * 将  logInfo设置到session中
     */
    public static void setLoginInfo(LoginInfo loginInfo) {
        getSession().setAttribute(LOGINFOR_IN_SESSION,loginInfo);
    }

    /**
     * 获取到loginfo
     */
    public static LoginInfo getLoginInfo() {
        return (LoginInfo) getSession().getAttribute(LOGINFOR_IN_SESSION);
    }

    /**
     * 获取到当前的用户登录的ip
     */
    public static String getIp(){
        return getRequest().getRemoteAddr();
    }

}
