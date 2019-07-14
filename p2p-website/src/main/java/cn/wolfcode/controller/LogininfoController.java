package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.CustomerException;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.service.IVerifyCodeService;
import cn.wolfcode.p2p.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogininfoController {

    @Autowired
    private ILoginInfoService  loginInfoService;
    @Autowired
    private IVerifyCodeService verifyCodeService;

    @RequestMapping("userRegister")
    @ResponseBody
    public JSONResult userRegister(String username, String verifycode, String password, String confirmPwd){
        JSONResult jsonResult = new JSONResult();
        try {
            loginInfoService.userRegister(username, verifycode, password, confirmPwd, LoginInfo.USERTYPE_WEBSITE);
        } catch (CustomerException e) {
            jsonResult.setMessage(e.getMessage());
        }catch (Exception e) {
            jsonResult.setMessage("系统出现异常,工作人员正在抢修中!");
        }

        return jsonResult;
    }

    /**
     * 验证是否存在手机号
     */
    @RequestMapping("existUserName")
    @ResponseBody
    public boolean existUserName(String username){
        return (!loginInfoService.existUserName(username));
    }

    /**
     * 发送验证码
     */
    @RequestMapping("sendVerifyCode")
    @ResponseBody
    public JSONResult sendVerifyCode(String phoneNumber){
        JSONResult jsonResult = new JSONResult();
        try {
            verifyCodeService.sendVerifyCode(phoneNumber);
        } catch (CustomerException e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

}
