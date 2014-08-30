package com.github.knightliao.hermesjsonrpc.server.handler.impl;

import com.github.knightliao.hermesjsonrpc.core.exception.ParseErrorException;
import com.github.knightliao.hermesjsonrpc.core.protocol.Processor;
import com.github.knightliao.hermesjsonrpc.core.protocol.impl.GsonProcessor;
import com.google.gson.JsonElement;

/**
 * 处理Json文本格式的Rpc调用
 * 
 * @author liaoqiqi
 * @version 2014-8-20
 */
public class JsonRpcHandler extends JsonRpcHandlerBase {

    private static final Processor processor = new GsonProcessor();

    /**
     * 
     */
    @Override
    protected JsonElement deserialize(String encoding, byte[] req)
            throws ParseErrorException {
        return processor.deserialize(encoding, req);
    }

    /**
     * 
     */
    @Override
    protected byte[] serialize(String encoding, JsonElement res)
            throws ParseErrorException {
        return processor.serialize(encoding, res);
    }
}
