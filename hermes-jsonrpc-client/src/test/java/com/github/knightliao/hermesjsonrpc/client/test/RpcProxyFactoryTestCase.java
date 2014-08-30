package com.github.knightliao.hermesjsonrpc.client.test;

import org.junit.Test;

import com.github.knightliao.hermesjsonrpc.client.RpcProxyFactory;
import com.github.knightliao.hermesjsonrpc.client.protocol.gson.GsonRpcProxy;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class RpcProxyFactoryTestCase {

    @Test
    public void getJsonRpcWithHeaderProxyTest() {

        GsonRpcProxy rpcProxyWithHeaderProperty = RpcProxyFactory
                .getGsonRpcWithHeaderProxy("http://django-china.cn", "UTF-8");

    }

    @Test
    public void getJsonRpcProxyWithAuthenticatorTest() {

        GsonRpcProxy rpcProxyWithHeaderProperty = RpcProxyFactory
                .getGsonRpcProxyWithAuthenticator("http://django-china.cn",
                        "UTF-8", "hello", "hermesjsonrpc");

    }
}
