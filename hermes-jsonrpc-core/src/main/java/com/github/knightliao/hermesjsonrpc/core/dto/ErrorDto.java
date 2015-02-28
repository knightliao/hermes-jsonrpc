package com.github.knightliao.hermesjsonrpc.core.dto;

/**
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class ErrorDto {

    // 异常详情
    public static final String DATA_FIELD = "data";
    // 返回code
    public static final String CODE_FIELD = "code";
    // 异常名
    public static final String MESSAGE_FIELD = "message";

    private String data;
    private int code;
    private String message;

    /**
     * @author liaoqiqi
     * @version 2014-8-30
     */
    static public class ErrorDtoBuilder {

        public static ErrorDto getResponseDto(String data, int code, String message) {

            return new ErrorDto(data, code, message);
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDto(String data, int code, String message) {
        super();
        this.data = data;
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorDto [data=" + data + ", code=" + code + ", message=" + message + "]";
    }
}
