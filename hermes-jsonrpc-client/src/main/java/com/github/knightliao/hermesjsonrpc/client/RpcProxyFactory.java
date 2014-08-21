package com.github.knightliao.hermesjsonrpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.github.knightliao.hermesjsonrpc.client.core.jsonrpc.JsonRpcProxy;
import com.github.knightliao.hermesjsonrpc.client.core.jsonrpc.RpcProxyWithAuthenticator;
import com.github.knightliao.hermesjsonrpc.core.exception.ExceptionHandler;

/**
 * Json rpc client factory
 * 
 * @author liaoqiqi
 * @version 2014-8-20
 */
public class RpcProxyFactory {

    /**
     * 获取普通的Rpc Proxy
     * 
     * @param url
     * @param encoding
     * @return
     */
    public static JsonRpcProxy getJsonRpcProxy(String url, String encoding) {

        return new JsonRpcProxy(url, encoding, new ExceptionHandler());
    }

    /**
     * 获取带权限验证的Rpc Proxy
     * 
     * @param url
     * @param encoding
     * @return
     */
    public static JsonRpcProxy getJsonRpcProxyWithAuthenticator(String url,
            String encoding, String userName, String password) {

        return new RpcProxyWithAuthenticator(url, encoding,
                new ExceptionHandler(), userName, password);
    }

    /**
     * 创建客户端rpc调用代理
     * 
     * @param type
     *            接口类型
     * @param handler
     *            使用的调用代理, 例如JsonRpcProxy
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<T> type, InvocationHandler handler) {

        Class<?>[] clazz = new Class[] { type };
        return (T) Proxy.newProxyInstance(
                RpcProxyFactory.class.getClassLoader(), clazz, handler);
    }

}
