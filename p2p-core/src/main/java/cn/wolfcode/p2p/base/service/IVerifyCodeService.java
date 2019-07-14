package cn.wolfcode.p2p.base.service;

/**
 * 验证码实现接口
 */
public interface IVerifyCodeService {

    /**
     * 发送验证码
     * @param phoneNumber
     */
    void sendVerifyCode(String phoneNumber);

}
