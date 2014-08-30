package com.github.knightliao.hermesjsonrpc.server.handler;

import com.github.knightliao.hermesjsonrpc.core.exception.JsonRpcException;
import com.github.knightliao.hermesjsonrpc.server.dto.RpcRequestDto;
import com.github.knightliao.hermesjsonrpc.server.dto.RpcResponseDto;

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
    public RpcResponseDto service(RpcRequestDto parameterObject)
            throws JsonRpcException;
}