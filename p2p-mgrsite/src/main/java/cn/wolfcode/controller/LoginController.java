package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.domain.Iplog;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.exception.CustomerException;
import cn.wolfcode.p2p.base.service.IIplogService;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 后台登录控制器
 */
@Controller
public class LoginController {

    @Autowired
    private ILoginInfoService loginInfoService;
    @Autowired
    private IIplogService iIplogService;

    @RequestMapping("login")
    @ResponseBody
    public JSONResult login(LoginInfo loginInfo){
        JSONResult jsonResult = new JSONResult();
        Integer state = Iplog.STATE_FAILURE;
        try {
            loginInfoService.login(loginInfo.getUsername(),loginInfo.getPassword(),LoginInfo.USERTYPE_MGRSITE);
        state = Iplog.STATE_SUCCESS;
        } catch (CustomerException e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            jsonResult.setMessage("系统繁忙,请稍后尝试!");
        }

        iIplogService.save(loginInfo.getUsername(), state, LoginInfo.USERTYPE_MGRSITE);

        return  jsonResult;
    }

    /**
     * 注销
     */
    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login.html";
    }
}
