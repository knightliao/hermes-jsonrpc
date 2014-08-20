package com.github.knightliao.xspeedjsonrpc.server.handler.impl;

import com.github.knightliao.xspeedjsonrpc.core.exception.ParseErrorException;
import com.github.knightliao.xspeedjsonrpc.core.protocol.Processor;
import com.github.knightliao.xspeedjsonrpc.core.protocol.impl.JsonProcessor;
import com.google.gson.JsonElement;

/**
 * 处理Json文本格式的Rpc调用
 * 
 */
public class JsonRpcHandler extends JsonRpcHandlerBase {

    static final Processor processor = new JsonProcessor();

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.rpc.server.JsonRpcHandlerBase#deserialize(java.lang.String,
     * byte[])
     */
    @Override
    protected JsonElement deserialize(String encoding, byte[] req)
            throws ParseErrorException {
        return processor.deserialize(encoding, req);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.rpc.server.JsonRpcHandlerBase#serialize(java.lang.String,
     * com.google.gson.JsonElement)
     */
    @Override
    protected byte[] serialize(String encoding, JsonElement res)
            throws ParseErrorException {
        return processor.serialize(encoding, res);
    }
}
