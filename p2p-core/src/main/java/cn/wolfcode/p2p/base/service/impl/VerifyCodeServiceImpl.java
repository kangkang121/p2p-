package cn.wolfcode.p2p.base.service.impl;
import java.util.Date;

import cn.wolfcode.p2p.base.exception.CustomerException;
import cn.wolfcode.p2p.base.service.IVerifyCodeService;
import cn.wolfcode.p2p.base.vo.VerifyCodeVO;
import cn.wolfcode.p2p.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.UUID;

@Service
@Transactional
public class VerifyCodeServiceImpl implements IVerifyCodeService {

    @Override
    public void sendVerifyCode(String phoneNumber) {
        //1.验证手机号是否正确
        AssertUtil.instance().isPhoneNumber(phoneNumber,"手机号格式不正确");

        //2.判断是否为频繁发送  上一次发送验证码的时间和当前的系统事件作对比
        VerifyCodeVO vo = UserContext.getVerifyCodeVO();
        if (vo != null) {
            Date lastSendTime = vo.getSendTime();
            Date now = new Date();
            AssertUtil.instance().isFalse((DateUtil.getSecondsBetween(now,lastSendTime)
                    < Constants.VERIFYCODE_INTERVAL_SECOND),"发送过于频繁,请稍后在发送");
        }
        //3.创建验证码
        String code = UUID.randomUUID().toString().substring(0, 4);

        //4.执行发送
        //send(phoneNumber,code);
        System.out.println("当前的验证码是" + code);

        Date lastSendTime = new Date();
        //5.保存发送记录到session
        VerifyCodeVO verifyCodeVO = new VerifyCodeVO();
        verifyCodeVO.setCode(code);
        verifyCodeVO.setPhoneNumber(phoneNumber);
        verifyCodeVO.setSendTime(lastSendTime);

        //6. 存放到对应的session中
        UserContext.setVerifyCodeVO(verifyCodeVO);
    }

    /**
     * 发送对应的验证码
     */
    private void send(String phoneNumber,String code){
        HashMap<String,String> map = new HashMap<>();
        map.put("Uid","kangkang");
        map.put("Key","123131231321321321");
        map.put("smsMob",phoneNumber);
        map.put("smsText","您的验证码是:"+code+"请在5分钟之内使用!");

        try {
            String ret = HttpUtil.sendHttpRequest("http://utf8.api.smschinese.cn/", map);
            if (ret.equals("-3")) {
                throw new CustomerException("短信发送失败,提示-3请联系客服");
            }
        } catch (CustomerException e) {
            e.printStackTrace();
            throw new CustomerException(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException("系统繁忙请稍后重试!");
        }
    }
}
