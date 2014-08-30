package com.github.knightliao.hermesjsonrpc.server.dto;

/**
 * 代表一个rpc请求
 * 
 */
public class RpcRequestDto {

    /**
     * 所调用的服务接口
     */
    private Class<?> service;

    /**
     * 完成请求的bean
     */
    private Object actor;

    /**
     * 传入的协议数据
     */
    private byte[] request;

    /**
     * 请求所使用的字符编码
     */
    private String encoding;

    /**
     * @param service
     *            所调用的服务接口
     * @param actor
     *            完成请求的bean
     * @param reqs
     *            传入的协议数据
     * @param encoding
     *            请求所使用的字符编码
     */
    public RpcRequestDto(Class<?> service, Object actor, byte[] reqs,
            String encoding) {

        this.service = service;
        this.actor = actor;
        this.request = reqs;

        if (encoding == null) {

            this.encoding = "utf-8";

        } else {
            this.encoding = encoding.toLowerCase();
        }
    }

    public Class<?> getService() {
        return service;
    }

    public void setService(Class<?> service) {
        this.service = service;
    }

    public Object getActor() {
        return actor;
    }

    public void setActor(Object actor) {
        this.actor = actor;
    }

    public byte[] getRequest() {
        return request;
    }

    public void setRequest(byte[] request) {
        this.request = request;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}