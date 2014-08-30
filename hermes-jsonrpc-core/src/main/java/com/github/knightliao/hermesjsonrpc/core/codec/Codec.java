package com.github.knightliao.hermesjsonrpc.core.codec;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public interface Codec {

    public <T> T decode(String encoding, Class<T> clazz, byte[] bytes)
            throws Exception;

    public <T> byte[] encode(String encoding, Class<T> clazz, T object)
            throws Exception;

}
