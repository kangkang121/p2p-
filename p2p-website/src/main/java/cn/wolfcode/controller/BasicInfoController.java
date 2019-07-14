package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.annotation.NeedLogin;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.CustomerException;
import cn.wolfcode.p2p.base.service.ISystemDictionaryItem;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户基本信息认证
 */
@Controller
public class BasicInfoController {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private ISystemDictionaryItem systemDictionaryItem;

    @RequestMapping("basicInfo")
    @NeedLogin
    public String basicInfo(Model model){
        LoginInfo loginInfo = UserContext.getLoginInfo();
        //共享用户的基本消息
        model.addAttribute("userinfo",userInfoService.getById(loginInfo.getId()));

        //个人学历
        model.addAttribute("educationBackgrounds",systemDictionaryItem.listItemByDirSn("educationBackgrounds"));
        //月收入
        model.addAttribute("incomeGrades",systemDictionaryItem.listItemByDirSn("incomeGrades"));
        //婚姻情况
        model.addAttribute("marriages",systemDictionaryItem.listItemByDirSn("marriages"));
        //子女情况
        model.addAttribute("kidCounts",systemDictionaryItem.listItemByDirSn("kidCounts"));
        //住房条件
        model.addAttribute("houseConditions",systemDictionaryItem.listItemByDirSn("houseConditions"));

        return "userInfo";
    }

    @RequestMapping("basicInfo_save")
    @ResponseBody
    public JSONResult save(UserInfo userInfo){
        JSONResult jsonResult = new JSONResult();
        try {
            userInfoService.save(userInfo);
        } catch (CustomerException e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
}
