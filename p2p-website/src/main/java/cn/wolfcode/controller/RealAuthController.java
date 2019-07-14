package cn.wolfcode.controller;

import cn.wolfcode.p2p.base.annotation.NeedLogin;
import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.CustomerException;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.UploadUtil;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class RealAuthController {

    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserInfoService userInfoService;

    @Value("${uploadPath}")
    private String uploadPath;

    @RequestMapping("realAuth")
    @NeedLogin
    public String realAuth(Model model){
        UserInfo userInfo = userInfoService.getById(UserContext.getLoginInfo().getId());
        if (userInfo.hasRealAuth()) {
        //已经通过实名认证跳转到通过页面
            model.addAttribute("realAuth",realAuthService.getById(userInfo.getRealAuthId()));
            return "realAuth_result";
        }
        //正在审核中跳转到对应的页面
        if (userInfo.getRealAuthId() != null) {
            model.addAttribute("auditing",true);
            return "realAuth_result";
        }
        return "realAuth";
    }

    @RequestMapping("realAuth_save")
    @ResponseBody
    public JSONResult  realAuthSave(RealAuth realAuth){
        JSONResult jsonResult = new JSONResult();
        try {
            realAuthService.save(realAuth);
        } catch (CustomerException e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     *上传图片
     */
    @RequestMapping("uploadImg")
    @ResponseBody
    public String uploadImg(MultipartFile photo){
        return UploadUtil.upload(photo,uploadPath);
    }
}
