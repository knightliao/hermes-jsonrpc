package com.github.knightliao.hermesjsonrpc.client.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.knightliao.hermesjsonrpc.client.test.protocol.base.BaseTestCase;
import com.github.knightliao.hermesjsonrpc.client.test.protocol.common.DemoServiceDriver;
import com.github.knightliao.hermesjsonrpc.client.test.protocol.common.DemoServiceDriver.Request;
import com.github.knightliao.hermesjsonrpc.client.test.protocol.common.DemoServiceDriver.Response;

/**
 * @author liaoqiqi
 * @version 2014-9-2
 */
public class RpcProxyFactorySpringTestCase extends BaseTestCase {

    private static String[] fn = null;

    // 初始化spring文档
    private static void contextInitialized() {
        fn = new String[] {"applicationContext.xml"};
    }

    /**
     *
     */
    @Test
    public void testGson() {

        //
        //
        //
        setupGsonRpc();

        contextInitialized();
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(fn);

        //
        //
        //
        DemoServiceDriver demoServiceDriver = (DemoServiceDriver) ctx.getBean("demoServiceDriver");

        Request request = new Request(1000, 10);
        Response response = demoServiceDriver.getRandom(request);

        if (response == null) {
            Assert.assertTrue(false);
        }

        Assert.assertEquals(BaseTestCase.DEFAULT_VALUE.intValue(), response.getValue());

    }

    /**
     *
     */
    @Test
    public void testProtostuff() {

        //
        //
        //
        setupProtostuffRpc();

        contextInitialized();
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(fn);

        //
        //
        //
        DemoServiceDriver demoServiceDriver = (DemoServiceDriver) ctx.getBean("demoServiceDriver2");

        Request request = new Request(1000, 10);
        Response response = demoServiceDriver.getRandom(request);

        if (response == null) {
            Assert.assertTrue(false);
        }

        Assert.assertEquals(BaseTestCase.DEFAULT_VALUE.intValue(), response.getValue());

    }
}
