package com.github.knightliao.hermesjsonrpc.core.protocol;

import com.github.knightliao.hermesjsonrpc.core.exception.ParseErrorException;
import com.google.gson.JsonElement;

/**
 * 所有协议处理器的公共接口
 * 
 */
public interface Processor {

    /**
     * 将协议数据格式转换为JsonElement
     * 
     * @param encoding
     * @param req
     * @return 生成的JsonElement树
     * @throws ParseErrorException
     */
    public abstract JsonElement deserialize(String encoding, byte[] req)
            throws ParseErrorException;

    /**
     * 将JsonElement转换为协议格式
     * 
     * @param encoding
     * @param res
     * @return 生成的协议格式的数据
     * @throws ParseErrorException
     */
    public abstract byte[] serialize(String encoding, JsonElement res)
            throws ParseErrorException;
}
