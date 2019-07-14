package cn.wolfcode.interceptor;

import cn.wolfcode.p2p.base.annotation.NeedLogin;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            //1. 获取到当前将要拦截的方法
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            //2. 判断这个方法上是否  有NeedLogin注解
            if (handlerMethod.hasMethodAnnotation(NeedLogin.class)) {
                //3. 如果有需要进行登录  查看是否有对应的user
                LoginInfo loginInfo = UserContext.getLoginInfo();
                //4. 没有跳转到登录页面
                if (loginInfo == null) {
                    response.sendRedirect("/login.html");
                    return false;
                }
           }
        }
        //5. 其他情况直接放行
        return true;
    }
}
