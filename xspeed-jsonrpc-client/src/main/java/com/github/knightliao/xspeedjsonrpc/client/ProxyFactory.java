package com.github.knightliao.xspeedjsonrpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 创建客户端rpc调用代理
 * 
 * @author liaoqiqi
 * @version 2014-8-20
 */
public class ProxyFactory {

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
        return (T) Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(),
                clazz, handler);
    }

}
