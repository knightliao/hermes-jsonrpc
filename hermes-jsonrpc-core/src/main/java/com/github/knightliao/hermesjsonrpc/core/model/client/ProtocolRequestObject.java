package com.github.knightliao.hermesjsonrpc.core.model.client;

import java.util.HashMap;
import java.util.Map;

import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
import com.github.knightliao.hermesjsonrpc.core.model.common.ProtocolObject;

/**
 * 发送给server的请求
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class ProtocolRequestObject extends ProtocolObject {

    // 参数
    private Object[] params;
    // id
    protected String id;

    /**
     * 
     * @param method
     * @param params
     * @param id
     * @param version
     */
    public ProtocolRequestObject(String method, Object[] params, String id,
            String version) {
        super(method, version);
        this.params = params;
        this.id = id;
    }

    /**
     * 获取Map
     * 
     * @return
     */
    public Map<String, Object> getObjectMap() {

        Map<String, Object> map = new HashMap<String, Object>();

        // protocol version
        map.put(Constants.JSONRPC_PROTOCOL_VERSION,
                Constants.JSONRPC_PROTOCOL_VERSION_VALUE);

        // method name
        map.put(JSONRPC_METHOD, method);

        // args
        if (params != null) {
            map.put(JSONRPC_PARAM, params);
        } else {
            map.put(JSONRPC_PARAM, new Object[0]);
        }

        // id
        map.put(Constants.JSONRPC_ID, id);

        return map;
    }

    /**
     * 
     * @author liaoqiqi
     * @version 2014-8-29
     */
    public static class ProtocolRequestObjectBuilder {

        public static Map<String, Object> getObjectMap(String id,
                String method, Object[] params, String version) {

            return new ProtocolRequestObject(method, params, id, version)
                    .getObjectMap();
        }

    }
}
