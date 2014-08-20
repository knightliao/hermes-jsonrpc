package com.github.knightliao.hermesjsonrpc.client;

import com.github.knightliao.hermesjsonrpc.client.core.JsonRpcProxy;
import com.github.knightliao.hermesjsonrpc.client.core.base.RpcProxyBase;
import com.github.knightliao.hermesjsonrpc.core.exception.ExceptionHandler;

/**
 * Json rpc client factory
 * 
 * @author liaoqiqi
 * @version 2014-8-20
 */
public class JsonRpcProxyFactory {

    /**
     * 
     * @param url
     * @param encoding
     * @return
     */
    public static RpcProxyBase getJsonRpcProxy(String url, String encoding) {

        return new JsonRpcProxy(url, encoding, new ExceptionHandler());
    }
}
