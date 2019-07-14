package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.annotation.NeedLogin;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.exception.CustomerException;
import cn.wolfcode.p2p.base.query.VideoAuditQuery;
import cn.wolfcode.p2p.base.service.IVideoAuditService;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VideoAuditController {

    @Autowired
    private IVideoAuditService videoAuditService;

    @RequestMapping("vedioAuth")
    @NeedLogin
    public String videoAudit(Model model, @ModelAttribute("qo") VideoAuditQuery qo ){
        LoginInfo loginInfo = UserContext.getLoginInfo();
        qo.setOrderBy(" va.id ");
        qo.setOrderType("DESC");
        if (!"admin".equals(loginInfo.getUsername())) {
            qo.setAuditorId(loginInfo.getId());
        }
        model.addAttribute("result",videoAuditService.queryForList(qo));
        return "vedioAuth/list";
    }

    /**
     * 后台审核
     */
    @RequestMapping("vedioAuth_audit")
    @ResponseBody
    public JSONResult vedioAuthAudit(Long id,Integer state,String remark){
        JSONResult jsonResult = new JSONResult();
        try {
            videoAuditService.attest(id,state,remark);
        } catch (CustomerException e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
}
