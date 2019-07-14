package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.annotation.NeedLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台登录的主页面
 */
@Controller
public class MainController {

    @RequestMapping("main")
    @NeedLogin
    public String main(){
        return "main";


    }
}
