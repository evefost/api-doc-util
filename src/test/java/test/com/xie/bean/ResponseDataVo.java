package test.com.xie.bean;


import test.com.xie.annotation.Descript;

/**
 * 接口统一返回格式
 *
 * @param <T>
 */
@Descript("接口统一返回格式顶层类")
public class ResponseDataVo<T> {

    @Descript("返回的数据内容")
    private T data;

    private Integer code;

    private String message;

    private ResponseDataVo(T data, Integer code) {
        this(data, code, null);
    }

    private ResponseDataVo(T data, Integer code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static <T> ResponseDataVo success() {
        return success(null);
    }

    public static <T> ResponseDataVo success(T data) {
        return success(data, ResponseEnum.SUCCESS.getMessage());
    }

    public static <T> ResponseDataVo success(T data, String message) {
        ResponseDataVo responeDataVo = new ResponseDataVo(data, ResponseEnum.SUCCESS.getCode(), message);
        return responeDataVo;
    }


    public static <T> ResponseDataVo error(String message) {
        return error(null,message);
    }


    public static <T> ResponseDataVo error(ResponseEnum code) {
        return error(code.getCode(),code.getMessage());
    }

    public static <T> ResponseDataVo error(T data, ResponseEnum code) {
        return error(data,code.getCode(),code.getMessage());
    }

    private static <T> ResponseDataVo error(Integer code, String message) {
        return error(null,code,message);
    }

    private static <T> ResponseDataVo error(T data, Integer code, String message) {
        ResponseDataVo responeDataVo = new ResponseDataVo(data, code, message);
        return responeDataVo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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
}
