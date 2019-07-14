package cn.wolfcode.p2p.base.exception;

/**
 * 用来抛出自己定义的异常
 * 比如说用户登录不成功给用户显示密码不正确诸如此类等等
 */
public class CustomerException extends RuntimeException{

    public CustomerException(String errorMsg) {
        super(errorMsg);
    }

}
