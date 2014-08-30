package com.github.knightliao.hermesjsonrpc.core.codec.gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import com.github.knightliao.hermesjsonrpc.core.codec.Codec;
import com.github.knightliao.hermesjsonrpc.core.exception.ParseErrorException;
import com.github.knightliao.hermesjsonrpc.core.utils.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * 用于Json和JsonElement间的转换
 */
public class GsonCodec implements Codec {

    private static final JsonParser parser = new JsonParser();
    private static final Gson gson = GsonFactory.getGson();

    /**
     * 
     * @param jsonElement
     * @param type
     * @return
     * @throws JsonSyntaxException
     */
    @SuppressWarnings("unchecked")
    public <T> T decode(JsonElement jsonElement, Type type)
            throws JsonSyntaxException {

        return (T) gson.fromJson(jsonElement, type);
    }

    /**
     * 将 字节码 转成 对象
     */
    public JsonElement decode(String encoding, byte[] req)
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
    public byte[] encode(String encoding, JsonElement res)
            throws ParseErrorException {

        try {

            String data = gson.toJson(res);

            return data.getBytes(encoding);

        } catch (UnsupportedEncodingException e) {

            throw new ParseErrorException(e.toString());

        }
    }

    /**
     * 
     * @param object
     * @param type
     * @return
     * @throws ParseErrorException
     */
    public JsonElement encode(Object object, Type type)
            throws ParseErrorException {

        return gson.toJsonTree(object, type);
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T decode(String encoding, Class<T> clazz, byte[] bytes)
            throws Exception {

        JsonElement jsonElement = decode(encoding, bytes);

        return (T) decode(jsonElement, clazz);
    }

    /**
     * 
     */
    @Override
    public <T> byte[] encode(String encoding, Class<T> clazz, T object)
            throws Exception {

        JsonElement jsonElement = encode(object, clazz);
        return encode(encoding, jsonElement);
    }

}
