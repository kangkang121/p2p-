package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.domain.Iplog;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.exception.CustomerException;
import cn.wolfcode.p2p.base.service.IIplogService;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private ILoginInfoService loginInfoService;
    @Autowired
    private IIplogService iIplogService;

    @RequestMapping("login")
    @ResponseBody
    public JSONResult login(String username, String password){
        JSONResult jsonResult = new JSONResult();
        int state = Iplog.STATE_FAILURE;
        try {
            loginInfoService.login(username,password, LoginInfo.USERTYPE_WEBSITE);
            state = Iplog.STATE_SUCCESS;
        } catch (CustomerException e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMessage("系统正在维护请稍后重试!");
        }

        //保存登录记录
        iIplogService.save(username,state,LoginInfo.USERTYPE_WEBSITE);

        return jsonResult;
    }

    @RequestMapping("logout")
    public String logOut(HttpSession session){
        session.invalidate();
        return "redirect:/login.html";
    }
}
