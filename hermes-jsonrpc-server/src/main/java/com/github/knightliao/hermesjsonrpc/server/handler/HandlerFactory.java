package com.github.knightliao.hermesjsonrpc.server.handler;

import java.util.Map;
import java.util.TreeMap;

import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
import com.github.knightliao.hermesjsonrpc.server.handler.impl.JsonRpcHandler;

/**
 * 用于将http Content-type映射到不同的协议处理器
 */
public class HandlerFactory {

    final static RpcHandler gson = new JsonRpcHandler();
    final static Map<String, RpcHandler> handlerMap = new TreeMap<String, RpcHandler>();

    static {
        handlerMap.put(Constants.JSON_CONTENT_GSON_TYPE, gson);
    }

    /**
     * 根据Content-type获取协议处理器
     * 
     * @param type
     * @return 协议处理器
     */
    public RpcHandler getProtocolHandler(String type) {
        RpcHandler handler = handlerMap.get(type);
        if (handler != null) {
            return handler;
        } else {
            return handler;
        }
    }
}
