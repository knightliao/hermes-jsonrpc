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
    public void getGsonRpcProxyTest() {

        GsonRpcProxy rpcProxyWithHeaderProperty = RpcProxyFactory
                .getGsonRpcProxy("http://django-china.cn", "UTF-8", null, null);

    }
}
