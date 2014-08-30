package com.github.knightliao.hermesjsonrpc.server.handler;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.hermesjsonrpc.core.constant.ProtocolEnum;
import com.github.knightliao.hermesjsonrpc.server.handler.gson.GsonRpcHandler;
import com.github.knightliao.hermesjsonrpc.server.handler.protostuff.ProtostuffRpcHandler;

/**
 * 用于将http Content-type映射到不同的协议处理器
 */
public class HandlerFactory {

    protected static final Logger LOG = LoggerFactory
            .getLogger(HandlerFactory.class);

    final static RpcHandler gson = new GsonRpcHandler();
    final static RpcHandler protostuff = new ProtostuffRpcHandler();

    final static Map<String, RpcHandler> handlerMap = new TreeMap<String, RpcHandler>();

    static {
        handlerMap.put(ProtocolEnum.GSON.getModelName(), gson);
        handlerMap.put(ProtocolEnum.PROTOSTUFF.getModelName(), protostuff);
    }

    /**
     * 根据Content-type获取协议处理器
     * 
     * @param type
     * @return 协议处理器
     */
    public RpcHandler getProtocolHandler(String type) {

        LOG.debug("use: " + type);

        RpcHandler handler = handlerMap.get(type);

        if (handler != null) {
            return handler;
        } else {
            return handler;
        }
    }
}
