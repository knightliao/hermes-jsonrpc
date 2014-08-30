package com.github.knightliao.hermesjsonrpc.server.dto;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class RpcResponseDto {

    /**
     * 请求所使用的字符编码
     */
    private String encoding;

    /**
     * 储存http返回值
     */
    private int statusCode;

    /**
     * 储存答复数据
     */
    private byte[] response;

    /**
     * 
     * @author liaoqiqi
     * @version 2014-8-30
     */
    static public class RpcResponseDtoBuilder {

        public static RpcResponseDto getRpcResponseDto(String encoding,
                int statusCode, byte[] response) {

            return new RpcResponseDto(encoding, statusCode, response);
        }
    }

    public RpcResponseDto(String encoding, int statusCode, byte[] response) {
        super();
        this.encoding = encoding;
        this.statusCode = statusCode;
        this.response = response;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public byte[] getResponse() {
        return response;
    }

    public void setResponse(byte[] response) {
        this.response = response;
    }
}
