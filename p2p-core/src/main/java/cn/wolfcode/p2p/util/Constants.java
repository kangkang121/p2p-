package cn.wolfcode.p2p.util;

import java.math.BigDecimal;

/**
 * 常量类
 */
public abstract class Constants {

    /**
     * 手机号长度
     */
    public static final int  PHONE_NUMBER = 11;

    /**
     * 最小密码长度
     */
    public static final int  MIN_PASSWORD_LENGTH = 6;

    /**
     * 最大密码长度
     */
    public static final int  MAX_PASSWORD_LENGTH = 16;

    /**
     * 验证码的长度
     */
    public static final int  VERIFYCODE_LENGTH = 4;

    /**
     * 验证码有效时间
     */
    public static final int  VERIFYCODE_VALID_SECOND = 300;

    /**
     * 验证码间隔时间
     */
    public static final int  VERIFYCODE_INTERVAL_SECOND = 60;

    /**
     * zero
     */
    public static final BigDecimal ZERO = new BigDecimal("0.0000");

    //*  ============================================================================  *//

    /**
     * 系统默认的信用额度
     */
    public static final BigDecimal DEFAULT_BORROW_LIMIT = new BigDecimal("5000.0000");

    /**
     * 显示精度
     */
    public static final int DISPLAY_SCALE = 2;

    /**
     * 存储精度
     */
    public static final int STORE_SCALE = 4;

    /**
     * 计算精度
     */
    public static final int CAL_SCALE = 8;

    /**
     * 最低借款金额
     */
    public static final BigDecimal MINBIDREQUESTAMOUNT = new BigDecimal("1000.000");


    /**
     * 最低年利率
     */
    public static final BigDecimal MINRATE = new BigDecimal("5.000");

    /**
     * 最低年利率
     */
    public static final BigDecimal MAXRATE = new BigDecimal("20.000");

    /**
     * 最小投标金额
     */
    public static final BigDecimal MINBIDAMOUNT = new BigDecimal("50.000");


    // --------------------还款类型---------------------------

    // 按月分期还款(等额本息)
    public final static int RETURN_TYPE_MONTH_INTEREST_PRINCIPAL = 0;

    // 按月到期还款(每月还利息,到期还本息)
    public final static int RETURN_TYPE_MONTH_INTEREST = 1;
    // ---------------------标的类型--------------------------

    // 普通信用标
    public final static int BIDREQUEST_TYPE_NORMAL = 0;

    // ---------------------借款状态---------------------------
    public final static int BIDREQUEST_STATE_PUBLISH_PENDING = 11;  // 发标待审
    public final static int BIDREQUEST_STATE_PENDING_RELEASE = 0;   // 待发布
    public final static int BIDREQUEST_STATE_BIDDING = 1;           // 招标中
    public final static int BIDREQUEST_STATE_UNDO = 2;              // 已撤销
    public final static int BIDREQUEST_STATE_BIDDING_OVERDUE = 3;   // 流标
    public final static int BIDREQUEST_STATE_APPROVE_PENDING_1 = 4; // 满标1审
    public final static int BIDREQUEST_STATE_APPROVE_PENDING_2 = 5; // 满标2审
    public final static int BIDREQUEST_STATE_REJECTED = 6;          // 满标审核被拒绝
    public final static int BIDREQUEST_STATE_PAYING_BACK = 7;       // 还款中
    public final static int BIDREQUEST_STATE_COMPLETE_PAY_BACK = 8; // 已还清
    public final static int BIDREQUEST_STATE_PAY_BACK_OVERDUE = 9;  // 逾期
    public final static int BIDREQUEST_STATE_PUBLISH_REFUSE = 10;   // 发标审核拒绝状态

    public static final BigDecimal SMALLEST_BID_AMOUNT = new BigDecimal("50.0000");// 系统规定的最小投标金额
    public static final BigDecimal SMALLEST_BIDREQUEST_AMOUNT = new BigDecimal("500.0000");// 系统规定的最小借款金额
    public static final BigDecimal SMALLEST_CURRENT_RATE = new BigDecimal("5.0000");// 系统最小借款利息
    public static final BigDecimal MAX_CURRENT_RATE = new BigDecimal("20.0000");// 系统最大借款利息
}
