package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.annotation.NeedLogin;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.query.IplogQuery;
import cn.wolfcode.p2p.base.service.IIplogService;
import cn.wolfcode.p2p.util.PageResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IplogController {

    @Autowired
    private IIplogService iIplogService;

    @NeedLogin
    @RequestMapping("iplog")
    public String querForList(@ModelAttribute("qo") IplogQuery qo, Model model){
        LoginInfo loginInfo = UserContext.getLoginInfo();
        if (loginInfo != null) {
            qo.setUsername(loginInfo.getUsername());
        }
        PageResult pageResult = iIplogService.querForList(qo);
        model .addAttribute("result",pageResult);
        return "iplog_list";
    }
}
