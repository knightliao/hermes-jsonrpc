package com.github.knightliao.hermesjsonrpc.client.core.jsonrpc;

import java.lang.reflect.Method;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.hermesjsonrpc.core.exception.ExceptionHandler;
import com.github.knightliao.hermesjsonrpc.core.exception.ParseErrorException;
import com.google.gson.JsonElement;

/**
 * 远程调用代理类，相对于基类添加了向Header中加入属性的功能
 * 
 * @author liaoqiqi
 * @version 2014-8-21
 */
public class RpcProxyWithHeaderProperty extends GsonRpcProxy {

    protected static final Logger LOG = LoggerFactory
            .getLogger(RpcProxyWithHeaderProperty.class);

    /** 用于放置header中需要添加的属性信息 */
    protected Map<String, String> headerProperties = new HashMap<String, String>();

    /**
     * 
     */
    @Override
    public Object clone() {
        RpcProxyWithHeaderProperty result = new RpcProxyWithHeaderProperty(url,
                encoding, exceptionHandler);
        result.setHeaderProperties(headerProperties);
        return result;
    }

    /**
     * @param url
     *            服务的url
     * @param encoding
     *            编码
     * @param exp
     *            异常处理器
     */
    public RpcProxyWithHeaderProperty(String url, String encoding,
            ExceptionHandler exp) {
        super(url, encoding, exp);
    }

    /**
     * 
     */
    @Override
    protected Object parseResult(long id, JsonElement ele, Method method)
            throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("JsonRpc head=" + headerProperties + " response=" + ele);
        }
        return super.parseResult(id, ele, method);
    }

    /**
     * 
     */
    @Override
    protected JsonElement makeRequest(long id, Method method, Object[] args)
            throws ParseErrorException {
        JsonElement result = super.makeRequest(id, method, args);
        if (LOG.isDebugEnabled()) {
            LOG.debug("JsonRpc head=" + headerProperties + " request=" + result);
        }
        return result;
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
}
