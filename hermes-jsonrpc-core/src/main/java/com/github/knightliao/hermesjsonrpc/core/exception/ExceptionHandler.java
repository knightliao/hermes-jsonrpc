package com.github.knightliao.hermesjsonrpc.core.exception;

import java.util.HashMap;
import java.util.List;

import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * 默认的异常处理器，将JsonRpcException异常转换为JsonElement结构
 * 
 * @author liaoqiqi
 * @version 2014-8-20
 */
public class ExceptionHandler {

    /**
     * 用户自定义异常体系
     */
    private HashMap<Integer, Class<? extends JsonRpcException>> errorCodeMap = new HashMap<Integer, Class<? extends JsonRpcException>>();

    private HashMap<Class<? extends JsonRpcException>, Integer> exceptionMap = new HashMap<Class<? extends JsonRpcException>, Integer>();

    /**
     * 添加用户定义的异常。 用户定义的异常都应是JsonRpcException的子类， <br/>
     * 并设置errorCode
     * 
     * @param userException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void setUserException(List<? extends JsonRpcException> userException)
            throws InstantiationException, IllegalAccessException {

        for (JsonRpcException entry : userException) {
            errorCodeMap.put(entry.errorCode(), entry.getClass());
            exceptionMap.put(entry.getClass(), entry.errorCode());
        }
    }

    /**
     * @param error
     * @param e
     * @return 生成的JsonElement树
     */
    private JsonElement makeExcetpion(int error, JsonRpcException e) {

        JsonObject obj = new JsonObject();

        obj.add(Constants.CODE_FIELD, new JsonPrimitive(error));

        if (e instanceof ServerErrorException && !e.getClass().isMemberClass()) {

            Throwable cause = e.getCause();

            if (cause != null) {

                // message
                obj.add(Constants.MESSAGE_FIELD, new JsonPrimitive(cause
                        .getClass().getSimpleName()));

                // data
                String msg = cause.getMessage();
                if (msg == null) {
                    obj.add(Constants.DATA_FIELD, JsonNull.INSTANCE);
                } else {
                    obj.add(Constants.DATA_FIELD, new JsonPrimitive(msg));
                }
                return obj;
            }
        }

        // message
        obj.add(Constants.MESSAGE_FIELD, new JsonPrimitive(e.getClass()
                .getSimpleName()));

        // data
        String msg = e.getMessage();
        if (msg == null) {
            obj.add(Constants.DATA_FIELD, JsonNull.INSTANCE);
        } else {
            obj.add(Constants.DATA_FIELD, new JsonPrimitive(e.getMessage()));
        }

        return obj;
    }

    /**
     * 根据异常创建json-rpc的error数据
     * 
     * @param e
     *            异常
     * @return error数据
     */
    public JsonElement serialize(JsonRpcException e) {

        Integer error = exceptionMap.get(e.getClass());

        if (error != null) {

            return makeExcetpion(error, e);

        } else {

            return makeExcetpion(e.errorCode(), e);
        }

    }

    /**
     * 根据json-rpc的error数据创建异常
     * 
     * @param e
     *            error数据
     * @return 创建的异常
     */
    public JsonRpcException deserialize(JsonElement e) {

        try {

            //
            // get object
            //
            JsonObject obj = e.getAsJsonObject();

            //
            // get data
            //
            String cause = null;
            if (obj.get(Constants.DATA_FIELD).isJsonPrimitive()) {
                cause = obj.get(Constants.DATA_FIELD).getAsString();
            }

            //
            // get code
            //
            int code = obj.get(Constants.CODE_FIELD).getAsInt();
            Class<? extends JsonRpcException> p = errorCodeMap.get(code);

            //
            // 用户自定义的
            //
            if (p != null) {

                return p.getConstructor(String.class).newInstance(cause);

            } else {

                //
                // 系统的
                //

                //
                // get message
                //
                String message = null;

                if (obj.get(Constants.MESSAGE_FIELD).isJsonPrimitive()) {
                    message = obj.get(Constants.MESSAGE_FIELD).getAsString();
                }

                if (code == ParseErrorException.PARSE_ERROR_CODE) {

                    return new ParseErrorException(message,
                            new Exception(cause));

                } else if (code == InvalidRequestException.INVALID_REQUEST_ERROR_CODE) {

                    return new InvalidRequestException(cause);

                } else if (code == MethodNotFoundException.METHOD_NOT_FOUND_ERROR_CODE) {

                    return new MethodNotFoundException(cause);

                } else if (code == InvalidParamException.INVALID_PARAM_ERROR_CODE) {

                    return new InvalidParamException(cause);

                } else if (code == InternalErrorException.INTERNAL_ERROR_CODE) {

                    return new InternalErrorException(cause);

                } else if (code == ServerErrorException.SERVER_ERROR_CODE) {

                    return new ServerErrorException(message, new Exception(
                            cause));
                } else {

                    return new InternalErrorException(message, new Exception(
                            cause));
                }
            }

        } catch (Exception ee) {

            return new InternalErrorException(ee);
        }
    }
}
