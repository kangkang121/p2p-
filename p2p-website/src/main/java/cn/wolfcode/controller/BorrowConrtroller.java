package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.annotation.NeedLogin;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.CustomerException;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.business.service.IBidRequestService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.util.Constants;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前端我要借款的控制器
 */

@Controller
public class BorrowConrtroller {

    @Autowired
    private IAccountService    accountService;
    @Autowired
    private IUserInfoService   userInfoService;
    @Autowired
    private IBidRequestService bidRequestService;

    @RequestMapping("borrow")
    public String borrow(Model model){
        LoginInfo loginInfo = UserContext.getLoginInfo();
        if (loginInfo == null) {
            //没有登录跳转到`静态页面
            return  "redirect:/borrow.html";
        }

        //如果有登录动态借款页面
        model.addAttribute("account",accountService.getById(loginInfo.getId()));
        model.addAttribute("userinfo",userInfoService.getById(loginInfo.getId()));
        return  "borrow";
    }

    @RequestMapping("borrowInfo")
    @NeedLogin
    public String borrowInfo(Model model){
        LoginInfo loginInfo = UserContext.getLoginInfo();

        UserInfo userInfo = userInfoService.getById(loginInfo.getId());
        if (userInfo.hasBidRequest()) {
            return "borrow_apply_result";
        }

        //最低借款金额
        model.addAttribute("minBidRequestAmount", Constants.MINBIDREQUESTAMOUNT);
        //当前用户的account信息
        model.addAttribute("account", accountService.getById(loginInfo.getId()));
        //最低年利率
        model.addAttribute("minRate", Constants.MINRATE);
        //最大年利率
        model.addAttribute("maxRate", Constants.MAXRATE);
        //最小投标金额
        model.addAttribute("minBidAmount", Constants.MINBIDAMOUNT);

        return "borrow_apply";
    }

    @RequestMapping("borrow_apply")
    @ResponseBody
    @NeedLogin
    public JSONResult borrowApply(BidRequest br){
        JSONResult jsonResult = new JSONResult();
        try {
            bidRequestService.apply(br);
        } catch (CustomerException e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }


}
