package com.github.knightliao.hermesjsonrpc.server.test.handler;

import org.junit.Assert;
import org.junit.Test;

import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
import com.github.knightliao.hermesjsonrpc.server.handler.HandlerFactory;
import com.github.knightliao.hermesjsonrpc.server.handler.RpcHandler;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class HandlerFactoryTestCase {

    /**
     * 
     */
    @Test
    public void getProtocolHandlerTest() {

        HandlerFactory handlerFactory = new HandlerFactory();
        RpcHandler rpcHandler = handlerFactory
                .getProtocolHandler(Constants.JSON_CONTENT_GSON_TYPE);
        Assert.assertTrue(rpcHandler instanceof RpcHandler);
    }
}
