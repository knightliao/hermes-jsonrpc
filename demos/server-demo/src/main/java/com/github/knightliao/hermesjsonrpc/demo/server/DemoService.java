package com.github.knightliao.hermesjsonrpc.demo.server;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

import com.github.knightliao.hermesjsonrpc.demo.DemoServiceDriver;

/**
 * @author liaoqiqi
 * @version 2014-8-21
 */
@Service(value = "demoService")
public class DemoService implements DemoServiceDriver {

    public Response getRandom(Request seed) {

        return new Response(RandomUtils.nextInt(Math.abs(seed.getMaxValue()) - seed.getMinValue() + 100));
    }

}
