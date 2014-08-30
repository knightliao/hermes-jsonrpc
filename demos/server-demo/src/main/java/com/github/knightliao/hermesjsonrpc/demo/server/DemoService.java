package com.github.knightliao.hermesjsonrpc.demo.server;

import org.springframework.stereotype.Service;

import com.github.knightliao.apollo.utils.common.RandomUtil;
import com.github.knightliao.hermesjsonrpc.demo.DemoServiceDriver;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-21
 */
@Service(value = "demoService")
public class DemoService implements DemoServiceDriver {

    public Response getRandom(Request seed) {

        return new Response(RandomUtil.random(seed.getMinValue(),
                seed.getMaxValue()));
    }

}
