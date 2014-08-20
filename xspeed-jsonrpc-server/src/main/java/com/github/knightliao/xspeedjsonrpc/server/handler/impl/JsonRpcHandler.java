package com.github.knightliao.xspeedjsonrpc.server.handler.impl;

import com.github.knightliao.xspeedjsonrpc.core.exception.ParseErrorException;
import com.github.knightliao.xspeedjsonrpc.core.protocol.Processor;
import com.github.knightliao.xspeedjsonrpc.core.protocol.impl.JsonProcessor;
import com.google.gson.JsonElement;

/**
 * 处理Json文本格式的Rpc调用
 * 
 * @author liaoqiqi
 * @version 2014-8-20
 */
public class JsonRpcHandler extends JsonRpcHandlerBase {

    private static final Processor processor = new JsonProcessor();

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
