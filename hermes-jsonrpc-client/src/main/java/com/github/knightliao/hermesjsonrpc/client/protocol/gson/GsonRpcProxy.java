package com.github.knightliao.hermesjsonrpc.client.protocol.gson;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.hermesjsonrpc.client.core.RpcProxyBase;
import com.github.knightliao.hermesjsonrpc.core.codec.gson.GsonCodec;
import com.github.knightliao.hermesjsonrpc.core.constant.ProtocolEnum;
import com.github.knightliao.hermesjsonrpc.core.dto.RequestDto;
import com.github.knightliao.hermesjsonrpc.core.dto.ResponseDto;
import com.github.knightliao.hermesjsonrpc.core.exception.ExceptionHandler;
import com.github.knightliao.hermesjsonrpc.core.exception.ParseErrorException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * GSON 序列化方式
 * 
 * @author liaoqiqi
 * @version 2014-8-20
 */
public class GsonRpcProxy extends RpcProxyBase implements Cloneable {

    protected static final Logger LOG = LoggerFactory
            .getLogger(GsonRpcProxy.class);

    /**
     * 处理器
     */
    private static final GsonCodec processor = new GsonCodec();

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
        return ProtocolEnum.GSON.getModelName();
    }

    /**
     * 字节->对象
     */
    @Override
    protected byte[] serialize(RequestDto res) throws ParseErrorException {

        JsonElement jsonElement = processor.encode(res, RequestDto.class);
        LOG.debug("request: " + jsonElement.toString());

        return processor.encode(encoding, jsonElement);
    }

    /**
     * 
     */
    @Override
    protected ResponseDto deserialize(byte[] req, Type type)
            throws ParseErrorException {

        JsonElement jsonElement = processor.decode(encoding, req);
        LOG.debug("response: " + jsonElement.toString());

        try {

            ResponseDto responseDto = processor.decode(jsonElement,
                    ResponseDto.class);

            //
            // get result
            //
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement resultJson = jsonObject.get(ResponseDto.JSON_RESULT);
            Object retsult = processor.decode(resultJson, type);

            responseDto.setResult(retsult);

            return responseDto;

        } catch (Exception e) {

            LOG.error("deserialize byte failed", e);
            throw new ParseErrorException("deserialize byte error");
        }
    }

    /**
     * 
     * @param url
     * @param encoding
     * @param exp
     * @param username
     * @param password
     */
    public GsonRpcProxy(String url, String encoding, ExceptionHandler exp,
            String username, String password) {
        super(url, encoding, exp, username, password);
    }

    /**
     * 
     * @param url
     * @param encoding
     * @param username
     * @param password
     * @param connectTimeout
     * @param readTimeout
     * @param exp
     */
    public GsonRpcProxy(String url, String encoding, String username,
            String password, int connectTimeout, int readTimeout,
            ExceptionHandler exp) {

        this(url, encoding, exp, username, password);
        setConnectTimeout(connectTimeout);
        setReadTimeout(readTimeout);
    }

}
