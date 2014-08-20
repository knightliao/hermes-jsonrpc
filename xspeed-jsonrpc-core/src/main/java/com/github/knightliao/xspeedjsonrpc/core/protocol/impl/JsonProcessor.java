package com.github.knightliao.xspeedjsonrpc.core.protocol.impl;

import java.io.UnsupportedEncodingException;

import com.github.knightliao.xspeedjsonrpc.core.exception.ParseErrorException;
import com.github.knightliao.xspeedjsonrpc.core.protocol.Processor;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 用于Json和JsonElement间的转换
 */
public class JsonProcessor implements Processor {

    static final JsonParser parser = new JsonParser();
    static final Gson gson = new Gson();

    /**
     * 将 字节码 转成 对象
     */
    public JsonElement deserialize(String encoding, byte[] req)
            throws ParseErrorException {
        String json;
        try {
            json = new String(req, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new ParseErrorException(e.toString());
        }
        return parser.parse(json);
    }

    /**
     * 将对象转成字节码
     */
    public byte[] serialize(String encoding, JsonElement res)
            throws ParseErrorException {
        try {

            String data = gson.toJson(res);

            return data.getBytes(encoding);

        } catch (UnsupportedEncodingException e) {

            throw new ParseErrorException(e.toString());

        }
    }
}
