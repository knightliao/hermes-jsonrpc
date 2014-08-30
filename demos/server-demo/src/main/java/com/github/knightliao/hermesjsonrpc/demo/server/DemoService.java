package com.github.knightliao.hermesjsonrpc.demo.server;

import org.springframework.stereotype.Service;

import com.github.knightliao.apollo.utils.common.RandomUtil;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-21
 */
@Service(value = "demoService")
public class DemoService implements DemoServiceDriver {

    public int getRandom(int maxValue) {

        return RandomUtil.random(0, maxValue);
    }

}
