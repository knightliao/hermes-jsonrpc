package com.github.knightliao.hermesjsonrpc.client.test;

import org.junit.Test;

import com.github.knightliao.hermesjsonrpc.client.RpcProxyFactory;
import com.github.knightliao.hermesjsonrpc.client.protocol.gson.GsonRpcProxy;
import com.github.knightliao.hermesjsonrpc.client.protocol.protostuff.ProtostuffPrcProxy;

/**
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class RpcProxyFactoryTestCase {

    /**
     *
     */
    @Test
    public void getGsonRpcProxyTest() {

        GsonRpcProxy rpcProxyWithHeaderProperty =
            RpcProxyFactory.getGsonRpcProxy("http://django-china.cn", "UTF-8", null, null);

    }

    /**
     *
     */
    @Test
    public void getProtoRpcProxyTest() {

        ProtostuffPrcProxy rpcProxyWithHeaderProperty =
            RpcProxyFactory.getProtostuffRpc("http://django-china.cn", "UTF-8", null, null);

    }

}
