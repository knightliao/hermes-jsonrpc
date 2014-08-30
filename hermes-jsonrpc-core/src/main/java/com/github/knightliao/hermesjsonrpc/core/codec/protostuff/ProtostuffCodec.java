package com.github.knightliao.hermesjsonrpc.core.codec.protostuff;

import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.github.knightliao.hermesjsonrpc.core.codec.Codec;
import com.github.knightliao.hermesjsonrpc.core.utils.ProtostuffJsonUtils;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class ProtostuffCodec implements Codec {

    /**
     * 
     * @param clazz
     * @param bytes
     * @return
     * @throws Exception
     */
    public <T> T decode(String encoding, Class<T> clazz, byte[] bytes)
            throws Exception {
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        T content = clazz.newInstance();
        ProtostuffJsonUtils.mergeFrom(bytes, content, schema);
        return content;
    }

    /**
     * 
     * @param clazz
     * @param object
     * @return
     * @throws Exception
     */
    public <T> byte[] encode(String encoding, Class<T> clazz, T object)
            throws Exception {

        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        byte[] protostuff = ProtostuffJsonUtils.toByteArray(object, schema);
        return protostuff;
    }
}
