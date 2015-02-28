package com.github.knightliao.hermesjsonrpc.core.utils;

import java.io.IOException;

import com.dyuproject.protostuff.JsonIOUtil;
import com.dyuproject.protostuff.Schema;

/**
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class ProtostuffJsonUtils {

    /**
     * @param message
     * @param schema
     *
     * @return
     */
    public static <T> byte[] toByteArray(T message, Schema<T> schema) {
        return JsonIOUtil.toByteArray(message, schema, false);
    }

    /**
     * @param in
     * @param message
     * @param schema
     *
     * @throws IOException
     */
    public static <T> void mergeFrom(byte[] in, T message, Schema<T> schema) throws IOException {
        JsonIOUtil.mergeFrom(in, message, schema, false);
    }
}
