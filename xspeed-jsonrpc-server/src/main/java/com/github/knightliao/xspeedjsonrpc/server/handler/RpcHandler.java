package com.github.knightliao.xspeedjsonrpc.server.handler;

import com.github.knightliao.xspeedjsonrpc.core.exception.JsonRpcException;
import com.github.knightliao.xspeedjsonrpc.server.model.RpcRequest;

/**
 * Rpc处理器的接口
 * 
 */
public interface RpcHandler {

    /**
     * 处理rpc请求
     * 
     * @param parameterObject
     *            Rpc请求的相关数据
     * @throws JsonRpcException
     */
    public void service(RpcRequest parameterObject) throws JsonRpcException;
}