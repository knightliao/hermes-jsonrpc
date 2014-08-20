package com.github.knightliao.xspeedjsonrpc.server.model;

/**
 * 代表一个rpc请求
 * 
 */
public class RpcRequest {
    /**
     * 所调用的服务接口
     */
    public Class<?> service;
    /**
     * 完成请求的bean
     */
    public Object actor;
    /**
     * 传入的协议数据
     */
    public byte[] request;
    /**
     * 储存答复数据
     */
    public byte[] response;
    /**
     * 请求所使用的字符编码
     */
    public String encoding;
    /**
     * 储存http返回值
     */
    public int statusCode;

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
    public RpcRequest(Class<?> service, Object actor, byte[] reqs,
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
}