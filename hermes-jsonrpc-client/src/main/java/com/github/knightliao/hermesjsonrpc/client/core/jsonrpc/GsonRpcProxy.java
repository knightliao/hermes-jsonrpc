package com.github.knightliao.hermesjsonrpc.client.core.jsonrpc;

import com.github.knightliao.hermesjsonrpc.client.core.base.RpcProxyBase;
import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
import com.github.knightliao.hermesjsonrpc.core.exception.ExceptionHandler;
import com.github.knightliao.hermesjsonrpc.core.exception.ParseErrorException;
import com.github.knightliao.hermesjsonrpc.core.protocol.Processor;
import com.github.knightliao.hermesjsonrpc.core.protocol.impl.GsonProcessor;
import com.google.gson.JsonElement;

/**
 * GSON 序列化方式
 * 
 * @author liaoqiqi
 * @version 2014-8-20
 */
public class GsonRpcProxy extends RpcProxyBase implements Cloneable {

    /**
     * 处理器
     */
    private static final Processor processor = new GsonProcessor();

    /**
     * @param url
     *            服务的url
     * @param encoding
     *            编码
     * @param exp
     *            异常处理器
     */
    public GsonRpcProxy(String url, String encoding, ExceptionHandler exp) {
        super(url, encoding, exp);
    }

    /**
     * 协议
     */
    @Override
    protected String contentType() {
        return Constants.JSON_CONTENT_GSON_TYPE;
    }

    /**
     * 对象->字节
     */
    @Override
    protected JsonElement deserialize(byte[] req) throws ParseErrorException {
        return processor.deserialize(encoding, req);
    }

    /**
     * 字节->对象
     */
    @Override
    protected byte[] serialize(JsonElement res) throws ParseErrorException {
        return processor.serialize(encoding, res);
    }

}
