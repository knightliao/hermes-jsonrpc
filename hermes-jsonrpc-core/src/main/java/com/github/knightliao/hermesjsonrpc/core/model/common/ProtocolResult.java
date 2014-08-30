package com.github.knightliao.hermesjsonrpc.core.model.common;

import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * server发送给client的请求 / client接受server的请求
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class ProtocolResult {

    //
    // 输出
    //
    private static final String JSON_RESULT = "result";
    private static final String JSON_RESULT_ERROR = "error";

    private JsonElement result;
    private JsonElement error;
    private String version;
    private JsonElement id;

    public ProtocolResult(JsonElement result, JsonElement error,
            String version, JsonElement id) {
        super();
        this.result = result;
        this.error = error;
        this.version = version;
        this.id = id;
    }

    /**
     * 将数据组装成JSON对象
     * 
     * @return
     */
    public JsonObject getJsonObject() {

        JsonObject res = new JsonObject();
        res.addProperty(Constants.JSONRPC_PROTOCOL_VERSION, version);
        res.add(JSON_RESULT, result);
        res.add(Constants.JSONRPC_ID, id);
        if (error != null) {
            res.add(JSON_RESULT_ERROR, error);
        }
        return res;
    }

    /**
     * 
     * server发给client的请求
     * 
     * @author liaoqiqi
     * @version 2014-8-30
     */
    static public class ProtocolResponseResultBuilder {

        /**
         * 正常的结果
         * 
         * @param result
         * @param id
         * @return
         */
        public static JsonObject getJsonObject(JsonElement result,
                JsonElement id) {

            return new ProtocolResult(result, null,
                    Constants.JSONRPC_PROTOCOL_VERSION_VALUE, id)
                    .getJsonObject();
        }

        /**
         * 含ERROR的结果
         * 
         * @param result
         * @param error
         * @param id
         * @return
         */
        public static JsonObject getJsonObject(JsonElement result,
                JsonElement error, JsonElement id) {

            return new ProtocolResult(result, error,
                    Constants.JSONRPC_PROTOCOL_VERSION_VALUE, id)
                    .getJsonObject();
        }
    }

    /**
     * client接受server的请求
     * 
     * @author liaoqiqi
     * @version 2014-8-30
     */
    static public class ProtocolRequestResultBuilder {

        public static ProtocolResult parse(JsonObject jsonObject) {

            // version
            String version = jsonObject.get(Constants.JSONRPC_PROTOCOL_VERSION)
                    .getAsString();

            //
            JsonElement result = jsonObject.get(JSON_RESULT);

            //
            JsonElement id = jsonObject.get(Constants.JSONRPC_ID);

            JsonElement error = jsonObject.get(JSON_RESULT_ERROR);

            return new ProtocolResult(result, error, version, id);
        }
    }

    public JsonElement getResult() {
        return result;
    }

    public void setResult(JsonElement result) {
        this.result = result;
    }

    public JsonElement getError() {
        return error;
    }

    public void setError(JsonElement error) {
        this.error = error;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public JsonElement getId() {
        return id;
    }

    public void setId(JsonElement id) {
        this.id = id;
    }

}
