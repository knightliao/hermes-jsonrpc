package com.github.knightliao.hermesjsonrpc.server.handler.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
import com.github.knightliao.hermesjsonrpc.core.exception.ExceptionHandler;
import com.github.knightliao.hermesjsonrpc.core.exception.InternalErrorException;
import com.github.knightliao.hermesjsonrpc.core.exception.InvalidParamException;
import com.github.knightliao.hermesjsonrpc.core.exception.InvalidRequestException;
import com.github.knightliao.hermesjsonrpc.core.exception.JsonRpcException;
import com.github.knightliao.hermesjsonrpc.core.exception.MethodNotFoundException;
import com.github.knightliao.hermesjsonrpc.core.exception.ParseErrorException;
import com.github.knightliao.hermesjsonrpc.core.exception.ServerErrorException;
import com.github.knightliao.hermesjsonrpc.core.gson.GsonFactory;
import com.github.knightliao.hermesjsonrpc.server.handler.RpcHandler;
import com.github.knightliao.hermesjsonrpc.server.model.RpcRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

/**
 * 所有JsonRpc处理器的基类
 * 
 * @author liaoqiqi
 * @version 2014-8-20
 */
public abstract class JsonRpcHandlerBase implements RpcHandler {

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(JsonRpcHandlerBase.class);

    final static Gson gson = GsonFactory.getGson();
    final static ExceptionHandler exp = new ExceptionHandler();

    /**
     * 调用实际的处理函数
     * 
     * @param objClass
     *            被调用的接口类型
     * @param obj
     *            被调用的bean
     * @param methodname
     *            被调用的函数名
     * @param args
     *            被调用函数的参数
     * @return 函数的返回值
     * @throws JsonRpcException
     *             将所有抛出的异常转换为JsonRpcException
     */
    protected JsonElement invoke(Class<?> objClass, Object obj,
            String methodname, JsonArray args) throws JsonRpcException {

        Object arg_obj[];

        for (Method ms : objClass.getMethods()) {

            //
            // 找到处理函数
            //
            if (ms.getName().equals(methodname)) {

                //
                // 参数数目不对
                //
                Type[] argtype = ms.getGenericParameterTypes();
                if (argtype.length != args.size()) {
                    throw new InvalidParamException();
                }

                //
                // 组装函数参数
                //
                arg_obj = new Object[args.size()];
                Iterator<JsonElement> e = args.iterator();
                for (int i = 0; i < args.size(); i++) {
                    arg_obj[i] = gson.fromJson(e.next(), argtype[i]);
                }

                //
                // 调用
                //
                Object res = null;

                try {
                    res = ms.invoke(obj, arg_obj);

                } catch (IllegalArgumentException e1) {

                    // 参数错误
                    throw new InvalidParamException(e1);

                } catch (IllegalAccessException e1) {

                    // 当Rpc请求不符合rpc规范是抛出
                    throw new InvalidRequestException(e1);

                } catch (InvocationTargetException e1) {

                    //
                    // 调用错误
                    //
                    Throwable e2 = e1.getTargetException();
                    if (e2 instanceof JsonRpcException) {
                        throw (JsonRpcException) e2;
                    } else {
                        throw new ServerErrorException(e2);
                    }
                }

                if (res == void.class) {
                    return null;
                } else {
                    return gson.toJsonTree(res, ms.getGenericReturnType());
                }
            }
        }

        // 函数找不到
        throw new MethodNotFoundException();
    }

    /**
     * 将二进制协议数据转换为JsonElement
     * 
     * @param encoding
     * @param req
     * @return 生成的JsonElement树
     * @throws ParseErrorException
     */
    protected abstract JsonElement deserialize(String encoding, byte[] req)
            throws ParseErrorException;

    /**
     * 将JsonElement转换为二进制协议数据
     * 
     * @param encoding
     * @param res
     * @return 生成的二进制协议数据
     * @throws ParseErrorException
     */
    protected abstract byte[] serialize(String encoding, JsonElement res)
            throws ParseErrorException;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.rpc.server.RpcHandler#service(com.baidu.rpc.server.RpcRequest)
     */
    public void service(RpcRequest parameterObject) throws JsonRpcException {

        JsonElement jreq;

        try {

            // 反序列化
            jreq = deserialize(parameterObject.encoding,
                    parameterObject.request);

        } catch (ParseErrorException e) {

            // 反序列化失败
            parameterObject.response = serialize(parameterObject.encoding,
                    make_res(parameterObject, null, e, null));
            return;
        }

        //
        // 解析
        //
        JsonElement res = parse(parameterObject, jreq);

        // 序列化
        parameterObject.response = serialize(parameterObject.encoding, res);
    }

    /**
     * 处理json-rpc请求，分析各字段
     * 
     * @param parameterObject
     * @param json
     * @return 请求处理结果
     */
    protected JsonElement parse(RpcRequest parameterObject, JsonElement json) {

        Class<?> objClass = parameterObject.service;

        Object obj = parameterObject.actor;

        try {

            JsonObject request = json.getAsJsonObject();

            //
            // 版本
            //
            if (!request.get(Constants.JSONRPC_PROTOCOL).getAsString()
                    .equals(Constants.JSONRPC_PROTOCOL_VERSION)) {
                throw new InvalidRequestException();
            }

            //
            // method, param, id
            //
            String method = request.get(Constants.JSONRPC_METHOD).getAsString();
            JsonArray params = request.get(Constants.JSONRPC_PARAM)
                    .getAsJsonArray();
            JsonElement id = request.get(Constants.JSONRPC_ID);

            //
            // 调用实际的函数
            //
            try {

                JsonElement result = invoke(objClass, obj, method, params);
                return make_res(parameterObject, result, null, id);

            } catch (JsonRpcException e) {

                LOGGER.warn(e.toString());
                return make_res(parameterObject, null, e, null);
            }

        } catch (Exception e) {

            LOGGER.warn(e.toString());
            return make_res(parameterObject, null,
                    new InvalidRequestException(), null);
        }
    }

    /**
     * 生成应答数据，result、error、和id间的关系必须满足json-rpc规范
     * 
     * @param parameterObject
     *            请求参数
     * @param result
     *            函数返回值
     * @param error
     *            抛出的异常
     * @param id
     *            请求id
     * @return 应答数据
     */
    protected JsonElement make_res(RpcRequest parameterObject,
            JsonElement result, JsonRpcException error, JsonElement id) {

        //
        // 正确的
        //
        if (result != null && error == null && id != null) {

            JsonObject res = new JsonObject();
            res.addProperty(Constants.JSONRPC_PROTOCOL,
                    Constants.JSONRPC_PROTOCOL_VERSION);
            res.add(Constants.JSON_RESULT, result);
            res.add(Constants.JSONRPC_ID, id);
            return res;

        } else if (result == null && error != null && id == null) {

            //
            // 错误的
            //
            JsonObject res = new JsonObject();
            res.addProperty(Constants.JSONRPC_PROTOCOL,
                    Constants.JSONRPC_PROTOCOL_VERSION);
            res.add(Constants.JSON_RESULT_ERROR, exp.serialize(error));
            res.add("id", JsonNull.INSTANCE);
            int code = error.errorCode();

            if (code == InvalidRequestException.INVALID_REQUEST_ERROR_CODE) {

                parameterObject.statusCode = 400;

            } else if (code == MethodNotFoundException.METHOD_NOT_FOUND_ERROR_CODE) {

                parameterObject.statusCode = 404;

            } else {

                parameterObject.statusCode = 500;
            }

            return res;

        } else {

            return make_res(parameterObject, null,
                    new InternalErrorException(), null);
        }
    }
}
