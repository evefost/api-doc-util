package test.com.xie.bean;

/**
 * Created by xieyang on 17/3/5.
 */
public enum ResponseEnum {

    ERROR(-1, "失败"),
    SUCCESS(0, "成功"),
    LOGIN_ERR(100, "帐号或密码错误"),
    TOKEN_ERR(101, "token无效"),
    SYS_ERR(-100, "系统错误"),
    UNKNOW_ERR(-101, "未知错误"),;

    private Integer code;
    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
