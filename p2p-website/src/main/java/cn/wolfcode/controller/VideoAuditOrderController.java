package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.annotation.NeedLogin;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.domain.VideoAudit;
import cn.wolfcode.p2p.base.exception.CustomerException;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.service.IOrderTimeService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.service.IVideoAuditService;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.UserContext;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 前台视频认证的接口
 */
@Controller
public class VideoAuditOrderController {

    @Autowired
    private ILoginInfoService  loginInfoService;
    @Autowired
    private IOrderTimeService  orderTimeService;
    @Autowired
    private IVideoAuditService videoAuditService;
    @Autowired
    private IUserInfoService userInfoService;

    @RequestMapping("videoAuditOrder")
    @NeedLogin
    public String videoAuditOrder(Model model){
        UserInfo userInfo = userInfoService.getById(UserContext.getLoginInfo().getId());
        if (userInfo.hasVedioAuth()) {
            model.addAttribute("videoSuccess",true);
        }else if(userInfo.getVideoAuditId() != null){
           VideoAudit videoAuditOrder = videoAuditService.getById(userInfo.getVideoAuditId());
            model.addAttribute("videoAuditOrder",videoAuditOrder);;
        }else{
            //客服人员
            List<LoginInfo> auditors = loginInfoService.queryForAuditors();
            model.addAttribute("auditors",auditors);

            //对应的时间的预约
            Date now = new Date();
            ArrayList<Date> orderDates = new ArrayList<>();
            orderDates.add(DateUtils.addDays(now,1));
            orderDates.add(DateUtils.addDays(now,3));
            orderDates.add(DateUtils.addDays(now,2));
            model.addAttribute("orderDates",orderDates);

            //共享所有的时间
            model.addAttribute("orderTimes",orderTimeService.listAll());
        }

        return "videoOrder";
    }

    @RequestMapping("saveVideoAuditOrder")
    @ResponseBody
    public JSONResult saveVideoAuditOrder(VideoAudit videoAudit){
        JSONResult jsonResult = new JSONResult();
        try {
            videoAuditService.audit(videoAudit);
        } catch (CustomerException e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }


    /**
     * 撤销视频认证申请
     */
    @RequestMapping("cancleVideoAudit")
    @ResponseBody
    @NeedLogin
    public JSONResult cancleVideoAudit(){
        JSONResult jsonResult = new JSONResult();
        try {
            videoAuditService.cancleVideoAudit();
        } catch (CustomerException e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
}
