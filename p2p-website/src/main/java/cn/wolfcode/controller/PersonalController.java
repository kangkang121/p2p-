package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.annotation.NeedLogin;
import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前台个人中心
 */
@Controller
public class PersonalController {

    @Autowired
    IAccountService accountService;

    @RequestMapping("personal")
    @NeedLogin
    public String listPersonal(Model model){
        model.addAttribute("account",accountService.getById(UserContext.getLoginInfo().getId()));
        return "personal";
    }
}


