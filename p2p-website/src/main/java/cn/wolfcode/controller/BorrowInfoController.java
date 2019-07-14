package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.service.IBidRequestService;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  前端借款详情接口
 */
@Controller
public class BorrowInfoController {

    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IAccountService    accountService;
    @Autowired
    private IUserInfoService   userInfoService;
    @Autowired
    private IRealAuthService realAuthService;

    @RequestMapping("borrow_info")
    public String borrowInfo(Model model,Long id){
        //共享bidRequest的信息
        BidRequest bidRequest = bidRequestService.getById(id);
        model.addAttribute("bidRequest",bidRequest);

        //判断当前用户是否是自己
        LoginInfo loginInfo = UserContext.getLoginInfo();
        if (loginInfo != null) {
            if (loginInfo.getId().equals(bidRequest.getCreateUser().getId())) {
                model.addAttribute("self",true);
            }
        }

        //对应的account信息
        model.addAttribute("account",
                accountService.getById(bidRequest.getCreateUser().getId()));

        //共享对应的userInfo的信息
        model.addAttribute("userInfo",
                userInfoService.getById(bidRequest.getCreateUser().getId()));

        //共享对应的真实的对象
        model.addAttribute("realAuth",
                realAuthService.getById(bidRequest.getId()));



        return "borrow_info";
    }
}
