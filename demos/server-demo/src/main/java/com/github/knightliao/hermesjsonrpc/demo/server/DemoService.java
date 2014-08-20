package com.github.knightliao.hermesjsonrpc.demo.server;

import org.springframework.stereotype.Service;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-21
 */
@Service(value = "demoService")
public class DemoService implements DemoServiceDriver {

    public int getRandom() {
        return 0;
    }

}
