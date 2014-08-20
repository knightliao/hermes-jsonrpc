package com.github.knightliao.xspeedjsonrpc.client.core;

import com.github.knightliao.xspeedjsonrpc.client.core.base.RpcProxyBase;
import com.github.knightliao.xspeedjsonrpc.core.constant.Constants;
import com.github.knightliao.xspeedjsonrpc.core.exception.ExceptionHandler;
import com.github.knightliao.xspeedjsonrpc.core.exception.ParseErrorException;
import com.github.knightliao.xspeedjsonrpc.core.protocol.Processor;
import com.github.knightliao.xspeedjsonrpc.core.protocol.impl.JsonProcessor;
import com.google.gson.JsonElement;

/**
 * 代理Json文本格式的rpc调用
 * 
 * 
 */
public class JsonRpcProxy extends RpcProxyBase implements Cloneable {

    /**
     * 处理器
     */
    private static final Processor processor = new JsonProcessor();

    /**
     * @param url
     *            服务的url
     * @param encoding
     *            编码
     * @param exp
     *            异常处理器
     */
    public JsonRpcProxy(String url, String encoding, ExceptionHandler exp) {
        super(url, encoding, exp);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.rpc.client.RpcProxyBase#contentType()
     */
    @Override
    protected String contentType() {
        return Constants.JSON_PROTOCOL_TYPE;
    }

    /*
     * 字节 -> 对象
     * 
     * @see com.baidu.rpc.client.RpcProxyBase#deserialize(byte[])
     */
    @Override
    protected JsonElement deserialize(byte[] req) throws ParseErrorException {
        return processor.deserialize(encoding, req);
    }

    /*
     * 字节 -> 对象
     * 
     * @see
     * com.baidu.rpc.client.RpcProxyBase#serialize(com.google.gson.JsonElement)
     */
    @Override
    protected byte[] serialize(JsonElement res) throws ParseErrorException {
        return processor.serialize(encoding, res);
    }

}
