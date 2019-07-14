package cn.wolfcode.p2p.util;

/**
 * 返回对应的jsonResult的方式
 */
public class JSONResult {
    private boolean success = true;

    private String message;

    public boolean isSuccess() {
        return success;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.success = false;
        this.message = message;
    }
}
