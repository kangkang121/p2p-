package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.annotation.NeedLogin;
import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.CustomerException;
import cn.wolfcode.p2p.base.query.RealAuthQuery;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RealAuthController {

    @Autowired
    private IRealAuthService realAuthService;

    @RequestMapping("realAuth")
    @NeedLogin
    public String queryForList(@ModelAttribute("qo") RealAuthQuery qo, Model model){
        qo.setOrderBy("apply_time");
        qo.setOrderType("DESC");
        model.addAttribute("result",realAuthService.queryForList(qo));
        return "realAuth/list";
    }

    /**
     * 修改按钮
     */
    @RequestMapping("realAuth_audit")
    @ResponseBody
    public JSONResult  updateState(Long id, Integer state, String remark){
        JSONResult jsonResult = new JSONResult();
        try {
            realAuthService.updateState(id, state, remark);
        } catch (CustomerException e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }


}
