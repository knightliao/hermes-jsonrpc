package com.github.knightliao.hermesjsonrpc.client.selector;

import com.github.knightliao.hermesjsonrpc.client.exception.RpcServiceException;

/**
 * @author liaoqiqi
 * @version 2014-8-30
 */
public interface ServiceInvoker {

    /**
     * 获取服务调用接口
     *
     * @return
     */
    Object getInvoker() throws RpcServiceException;

}
