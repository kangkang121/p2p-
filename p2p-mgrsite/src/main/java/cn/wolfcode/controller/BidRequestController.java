package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.annotation.NeedLogin;
import cn.wolfcode.p2p.base.exception.CustomerException;
import cn.wolfcode.p2p.base.query.BidRequestQuery;
import cn.wolfcode.p2p.business.service.IBidRequestService;
import cn.wolfcode.p2p.util.Constants;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 后台审核表
 */
@Controller
public class BidRequestController {

    @Autowired
    private IBidRequestService bidRequestService;

    @RequestMapping("bidrequest_publishaudit_list")
    @NeedLogin
    public String bidrequest(Model model,BidRequestQuery qo){
        //设置对应出查询的条件
        qo.setOrderBy(" apply_time ");
        qo.setOrderType(" DESC ");
        qo.setState(Constants.BIDREQUEST_STATE_PUBLISH_PENDING);

        PageResult result = bidRequestService.queryResult(qo);
        model.addAttribute("result",result);
        return "bidrequest/publish_audit";
    }


    @RequestMapping("bidrequest_publishaudit")
    @ResponseBody
    @NeedLogin
    public JSONResult bidrequestPublish(Long id, Integer state, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            Date publishTime, String remark){

        JSONResult jsonResult = new JSONResult();
        try {
            bidRequestService.bidrequestPublish(id,state,publishTime,remark);
        } catch (CustomerException e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

}
