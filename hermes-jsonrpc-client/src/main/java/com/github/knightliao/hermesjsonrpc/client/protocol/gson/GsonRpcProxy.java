package com.github.knightliao.hermesjsonrpc.client.protocol.gson;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.hermesjsonrpc.client.core.RpcProxyBase;
import com.github.knightliao.hermesjsonrpc.core.auth.AuthController;
import com.github.knightliao.hermesjsonrpc.core.codec.gson.GsonProcessor;
import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
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

    /** 用于放置header中需要添加的属性信息 */
    protected Map<String, String> headerProperties = new HashMap<String, String>();

    /**
     * 处理器
     */
    private static final com.github.knightliao.hermesjsonrpc.core.codec.gson.GsonProcessor processor = new GsonProcessor();

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

        JsonElement jsonElement = processor.serialize(res, RequestDto.class);

        return processor.serialize(encoding, jsonElement);
    }

    /**
     * 
     */
    @Override
    protected ResponseDto deserialize(byte[] req, Type genericReturnType)
            throws ParseErrorException {

        JsonElement jsonElement = processor.deserialize(encoding, req);

        try {

            ResponseDto responseDto = processor.deserialize(jsonElement,
                    ResponseDto.class);

            //
            // get result
            //
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement resultJson = jsonObject.get(ResponseDto.JSON_RESULT);
            Object retsult = processor.deserialize(resultJson,
                    genericReturnType);

            responseDto.setResult(retsult);

            return responseDto;

        } catch (Exception e) {

            LOG.error("Deserialize byte failed", e);
            throw new ParseErrorException("Deserialize byte error");
        }
    }

    /**
     * 
     */
    @Override
    public Object clone() {
        GsonRpcProxy result = new GsonRpcProxy(url, encoding, exceptionHandler);
        result.setHeaderProperties(headerProperties);
        return result;
    }

    public Map<String, String> getHeaderProperties() {
        return headerProperties;
    }

    public void setHeaderProperties(Map<String, String> headerProperties) {
        this.headerProperties = headerProperties;
    }

    /**
     * 
     * @param headerProperties
     */
    public void addHeaderProperties(Map<String, String> headerProperties) {
        if (headerProperties != null) {
            if (this.headerProperties == null) {
                this.headerProperties = new HashMap<String, String>();
            }
            this.headerProperties.putAll(headerProperties);
        }
    }

    /**
     * 带验证头的数据发送
     */
    @Override
    protected void sendRequest(byte[] reqBytes, URLConnection connection) {

        if (null != headerProperties) {
            for (Entry<String, String> entry : headerProperties.entrySet()) {
                if (null != entry.getValue()) {
                    connection.addRequestProperty(entry.getKey(),
                            entry.getValue());
                }
            }
        }
        super.sendRequest(reqBytes, connection);
    }

    /**
     * 
     */
    @Override
    protected RequestDto makeRequest(long id, Method method, Object[] args)
            throws ParseErrorException {
        RequestDto result = super.makeRequest(id, method, args);
        if (LOG.isDebugEnabled()) {
            LOG.debug("JsonRpc head=" + headerProperties + " request=" + result);
        }
        return result;
    }

    /**
     * 
     */
    @Override
    protected void checkResponse(long id, ResponseDto responseDto, Method method)
            throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("JsonRpc head=" + headerProperties + " response="
                    + responseDto);
        }
        super.checkResponse(id, responseDto, method);
    }

    /**
     * 
     * @param key
     * @param value
     */
    public void addHeaderProperties(String key, String value) {
        if (this.headerProperties == null) {
            this.headerProperties = new HashMap<String, String>();
        }
        this.headerProperties.put(key, value);
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
        super(url, encoding, exp);
        // 授权信息
        addHeaderProperties(Constants.WWW_AUTH_RPC,
                AuthController.getAuth(username, password, encoding));
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
