package com.github.knightliao.hermesjsonrpc.client.selector;

import com.github.knightliao.hermesjsonrpc.client.exception.RpcServiceException;

public interface ServiceInvoker {

    /**
     * 获取服务调用接口
     * 
     * @return
     */
    Object getInvoker() throws RpcServiceException;

}
