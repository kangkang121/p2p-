package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.query.BidRequestQuery;
import cn.wolfcode.p2p.business.service.IBidRequestService;
import cn.wolfcode.p2p.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private IBidRequestService bidRequestService;

    @RequestMapping("main")
    public String index(Model model){
        model.addAttribute("publishPendngBidRequests",bidRequestService.listIndex());

        //查询对应的 照投标中>还款中>已完成的
        BidRequestQuery qo = new BidRequestQuery();
        qo.setStates(new Integer[]{
                Constants.BIDREQUEST_STATE_BIDDING,
                Constants.BIDREQUEST_STATE_PAYING_BACK,
                Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK
        });
        qo.setOrderBy("bid_request_state");
        qo.setOrderType("ASC");
        qo.setPageSize(5);
        model.addAttribute("bidRequests",bidRequestService.queryResult(qo).getlist());
        return "main";
    }


    /**
     * 跳转对应的页面
     */
    @RequestMapping("invest")
    public String invest(){
        return "invest";
    }

    /**
     *Ajax提交对应的表单
     */
    @RequestMapping("invest_list")
    public String invest(Model model,BidRequestQuery qo){
        qo.setOrderBy("publish_time");
        qo.setOrderType("DESC");
        model.addAttribute("result",bidRequestService.queryResult(qo));
        return "invest_list";
    }

}
