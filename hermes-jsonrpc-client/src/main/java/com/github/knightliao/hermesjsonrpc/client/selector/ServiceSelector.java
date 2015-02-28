package com.github.knightliao.hermesjsonrpc.client.selector;

/**
 * @author liaoqiqi
 * @version 2014-8-21
 */
public interface ServiceSelector {

    Object invoke(boolean errorExit);
}
