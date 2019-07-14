package cn.wolfcode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 个人充值的页面
 */
@Controller
public class ReChargeController {


    @RequestMapping("recharge")
    public String recharge(){
        return "recharge";
    }


}
