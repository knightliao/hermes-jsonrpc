package com.github.knightliao.hermesjsonrpc.core.model.server;

import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
import com.github.knightliao.hermesjsonrpc.core.model.common.ProtocolObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 处理来自client的请求
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class ProtocolResponseObject extends ProtocolObject {

    // 参数
    private JsonArray parArray;
    // id
    private JsonElement id;

    public JsonElement getId() {
        return id;
    }

    public JsonArray getParArray() {
        return parArray;
    }

    public String getVersion() {
        return version;
    }

    public String getMethod() {
        return method;
    }

    public ProtocolResponseObject(String method, JsonArray paArray,
            JsonElement id, String version) {
        super(method, version);
        this.parArray = paArray;
        this.id = id;
    }

    /**
     * 
     * @author liaoqiqi
     * @version 2014-8-29
     */
    public static class ProtocolResponseObjectBuilder {

        public static ProtocolResponseObject buildProtocolObject(
                JsonElement id, String method, JsonArray params, String version) {

            return new ProtocolResponseObject(method, params, id, version);
        }

        /**
         * 
         * @param jsonObject
         * @return
         */
        public static ProtocolResponseObject parse(JsonObject jsonObject) {

            String version = jsonObject.get(Constants.JSONRPC_PROTOCOL_VERSION)
                    .getAsString();

            //
            // method, param, id
            //
            String method = jsonObject.get(JSONRPC_METHOD).getAsString();
            JsonArray params = jsonObject.get(JSONRPC_PARAM).getAsJsonArray();
            JsonElement id = jsonObject.get(Constants.JSONRPC_ID);

            return buildProtocolObject(id, method, params, version);
        }
    }
}
