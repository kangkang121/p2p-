package cn.wolfcode.p2p.util;

import cn.wolfcode.p2p.base.exception.CustomerException;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * 断言工具类
 */
public class AssertUtil {

    private static final AssertUtil instance = new AssertUtil();

    /**
     * 手机字符串正则表达式
     */
    public static final String PHONE_NUMBER = "/^1[34578]\\d{9}$/";


    private AssertUtil() {
    }

    public static AssertUtil instance(){
        return instance;
    }

    /**
     * 断言字符串不为空
     */
    public AssertUtil isNotNull(String str,String errorMsg){
        if (!StringUtils.hasLength(str)) {
            throw new CustomerException(errorMsg);
        }
        return instance;
    }

    public AssertUtil isNotNull(Object obj,String errorMsg){
        if (obj == null) {
            throw new CustomerException(errorMsg);
        }
        return instance;
    }

    /**
     * 断言字符串必须规定的满足长度
     */
    public AssertUtil isLength(String str, int length, String errorMsg) {
        if (str.length() != length) {
            throw new CustomerException(errorMsg);
        }
        return instance;
    }

    /**
     * 断言false
     */
    public AssertUtil isFalse(boolean ret, String errorMsg) {
        if (ret) {
            throw new CustomerException(errorMsg);
        }
        return instance;
    }

    /**
     * 断言true
     */
    public AssertUtil isTrue(boolean ret, String errorMsg) {
        if (!ret) {
            throw new CustomerException(errorMsg);
        }
        return instance;
    }

    /**
     * 断言 字符串的长度范围
     */
    public AssertUtil isLengthRange(String str, int minLength, int maxLength, String errorMsg) {
        isNotNull(str,errorMsg);
        if (str.length() < 6 || str.length() >16) {
            throw new CustomerException(errorMsg);
        }
        return instance;
    }

    /**
     *  断言 两个字符串是否一致
     */
    public AssertUtil isEquals(String str1, String str2, String errorMsg) {
        isNotNull(str1,errorMsg);
        isNotNull(str2,errorMsg);
        if (!str1.equals(str2)) {
            throw new CustomerException(errorMsg);
        }
        return instance;
    }

    /**
     * 断言是否为手机号
     */
    public AssertUtil isPhoneNumber(String str,String errorMsg) {
        isNotNull(str,errorMsg);
        if (Pattern.matches(PHONE_NUMBER,str)) {
            throw new CustomerException(errorMsg);
        }
        return instance;
    }

    /**
     * 断言null
     */
    public AssertUtil isNull(Long realAuthId, String errorMsg) {
        if (realAuthId != null) {
            throw new CustomerException(errorMsg);
        }
        return this;
    }
}
