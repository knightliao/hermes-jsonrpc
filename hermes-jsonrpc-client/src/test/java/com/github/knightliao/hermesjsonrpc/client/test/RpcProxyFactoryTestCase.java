package com.github.knightliao.hermesjsonrpc.client.test;

import org.junit.Test;

import com.github.knightliao.hermesjsonrpc.client.RpcProxyFactory;
import com.github.knightliao.hermesjsonrpc.client.core.jsonrpc.RpcProxyWithHeaderProperty;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class RpcProxyFactoryTestCase {

    @Test
    public void getJsonRpcWithHeaderProxyTest() {

        RpcProxyWithHeaderProperty rpcProxyWithHeaderProperty = RpcProxyFactory
                .getJsonRpcWithHeaderProxy("http://django-china.cn", "UTF-8");

    }

    @Test
    public void getJsonRpcProxyWithAuthenticatorTest() {

        RpcProxyWithHeaderProperty rpcProxyWithHeaderProperty = RpcProxyFactory
                .getJsonRpcProxyWithAuthenticator("http://django-china.cn",
                        "UTF-8", "hello", "hermesjsonrpc");

    }
}
