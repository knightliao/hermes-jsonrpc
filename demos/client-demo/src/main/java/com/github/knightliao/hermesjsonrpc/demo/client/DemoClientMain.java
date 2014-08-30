package com.github.knightliao.hermesjsonrpc.demo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.knightliao.hermesjsonrpc.demo.DemoServiceDriver;
import com.github.knightliao.hermesjsonrpc.demo.DemoServiceDriver.Request;
import com.github.knightliao.hermesjsonrpc.demo.DemoServiceDriver.Response;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-21
 */
public class DemoClientMain {

    protected static final Logger LOG = LoggerFactory
            .getLogger(DemoClientMain.class);
    private static String[] fn = null;

    // 初始化spring文档
    private static void contextInitialized() {
        fn = new String[] { "applicationContext.xml" };
    }

    public static void main(String[] args) {

        contextInitialized();
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                fn);

        //
        // process
        //
        DemoServiceDriver demoServiceDriver = (DemoServiceDriver) ctx
                .getBean("demoServiceDriver");

        LOG.info("start to process");

        for (int i = 0; i < 1000; ++i) {

            try {

                Response ret = demoServiceDriver.getRandom(new Request(0,
                        i * 10));

                LOG.info(ret + "");
            } catch (Exception e) {
                LOG.warn(e.toString());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        LOG.info("end. ");
        System.exit(0);
    }
}
