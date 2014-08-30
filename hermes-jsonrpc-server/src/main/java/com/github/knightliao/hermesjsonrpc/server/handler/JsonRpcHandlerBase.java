package com.github.knightliao.hermesjsonrpc.server.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
import com.github.knightliao.hermesjsonrpc.core.dto.ErrorDto;
import com.github.knightliao.hermesjsonrpc.core.dto.RequestDto;
import com.github.knightliao.hermesjsonrpc.core.dto.ResponseDto;
import com.github.knightliao.hermesjsonrpc.core.dto.ResponseDto.ResponseDtoBuilder;
import com.github.knightliao.hermesjsonrpc.core.exception.ExceptionHandler;
import com.github.knightliao.hermesjsonrpc.core.exception.InternalErrorException;
import com.github.knightliao.hermesjsonrpc.core.exception.InvalidParamException;
import com.github.knightliao.hermesjsonrpc.core.exception.InvalidRequestException;
import com.github.knightliao.hermesjsonrpc.core.exception.JsonRpcException;
import com.github.knightliao.hermesjsonrpc.core.exception.MethodNotFoundException;
import com.github.knightliao.hermesjsonrpc.core.exception.ParseErrorException;
import com.github.knightliao.hermesjsonrpc.core.exception.ServerErrorException;
import com.github.knightliao.hermesjsonrpc.server.dto.RpcRequestDto;
import com.github.knightliao.hermesjsonrpc.server.dto.RpcResponseDto;
import com.github.knightliao.hermesjsonrpc.server.dto.RpcResponseDto.RpcResponseDtoBuilder;

/**
 * 所有JsonRpc处理器的基类
 * 
 * @author liaoqiqi
 * @version 2014-8-20
 */
public abstract class JsonRpcHandlerBase implements RpcHandler {

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(JsonRpcHandlerBase.class);

    private final static ExceptionHandler exp = new ExceptionHandler();

    /**
     * 
     * @param objClass
     * @param obj
     * @param methodname
     * @param args
     * @return
     * @throws JsonRpcException
     */
    protected Object invoke(Class<?> objClass, Object obj, String methodname,
            Object[] args) throws JsonRpcException {

        for (Method ms : objClass.getMethods()) {

            //
            // 找到处理函数
            //
            if (ms.getName().equals(methodname)) {

                //
                // 参数数目不对
                //
                Type[] argtype = ms.getGenericParameterTypes();
                if (argtype.length != args.length) {
                    throw new InvalidParamException();
                }

                //
                // 调用
                //
                Object res = null;

                try {
                    res = ms.invoke(obj, args);

                    return res;

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
            }
        }

        // 函数找不到
        throw new MethodNotFoundException();
    }

    /**
     * 
     * @param encoding
     * @param req
     * @return
     * @throws ParseErrorException
     */
    protected abstract <T> RequestDto deserialize(String encoding, byte[] req,
            Class<T> clazz) throws ParseErrorException;

    /**
     * 
     * @param encoding
     * @param responseDto
     * @return
     * @throws ParseErrorException
     */
    protected abstract byte[] serialize(String encoding, ResponseDto responseDto)
            throws ParseErrorException;

    /**
     * 
     */
    private void checkRequest(RequestDto requestDto)
            throws InvalidRequestException {

        //
        // 版本
        //
        if (!requestDto.getVersion().equals(
                Constants.JSONRPC_PROTOCOL_VERSION_VALUE)) {
            throw new InvalidRequestException();
        }
    }

    /**
     * 
     */
    public RpcResponseDto service(RpcRequestDto rpcRequestDto)
            throws JsonRpcException {

        try {

            //
            // 反序列化
            //
            RequestDto requestDto = deserialize(rpcRequestDto.getEncoding(),
                    rpcRequestDto.getRequest(), rpcRequestDto.getService());
            LOGGER.debug("request=" + requestDto);

            //
            // 校验
            //
            checkRequest(requestDto);

            //
            // 执行
            //
            RpcResponseDtoInner rpcResponseDtoInner = null;

            Class<?> objClass = rpcRequestDto.getService();
            Object obj = rpcRequestDto.getActor();

            Object ret = invoke(objClass, obj, requestDto.getMethod(),
                    requestDto.getParams());

            rpcResponseDtoInner = make_res(rpcRequestDto, ret, null,
                    requestDto.getId());

            //
            // 序列化
            //
            byte[] data = serialize(rpcRequestDto.getEncoding(),
                    rpcResponseDtoInner.getResponseDto());

            //
            // 返回
            //
            return RpcResponseDtoBuilder.getRpcResponseDto(
                    rpcRequestDto.getEncoding(),
                    rpcResponseDtoInner.getStatus(), data);

        } catch (JsonRpcException e) {

            LOGGER.warn(e.toString());

            // 序列化失败

            // 结果
            RpcResponseDtoInner rpcResponseDtoInner = make_res(rpcRequestDto,
                    null, e, null);

            // 序列化
            byte[] data = serialize(rpcRequestDto.getEncoding(),
                    rpcResponseDtoInner.getResponseDto());

            return RpcResponseDtoBuilder.getRpcResponseDto(
                    rpcRequestDto.getEncoding(),
                    rpcResponseDtoInner.getStatus(), data);
        }
    }

    /**
     * 
     * @param parameterObject
     * @param result
     * @param error
     * @param id
     * @return
     */
    protected RpcResponseDtoInner make_res(RpcRequestDto parameterObject,
            Object result, JsonRpcException error, Long id) {

        //
        // 正确的
        //
        if (result != null && error == null && id != null) {

            ResponseDto responseDto = ResponseDtoBuilder.getResponseDto(result,
                    null, Constants.JSONRPC_PROTOCOL_VERSION_VALUE, id);

            return new RpcResponseDtoInner(responseDto, Constants.HTTP_OK);

        } else if (result == null && error != null && id == null) {

            // error data
            ErrorDto errorDto = exp.serialize(error);

            //
            // 错误的
            //
            ResponseDto responseDto = ResponseDtoBuilder.getResponseDto(result,
                    errorDto, Constants.JSONRPC_PROTOCOL_VERSION_VALUE, id);

            int code = error.errorCode();

            if (code == InvalidRequestException.INVALID_REQUEST_ERROR_CODE) {

                code = Constants.HTTP_400;

            } else if (code == MethodNotFoundException.METHOD_NOT_FOUND_ERROR_CODE) {

                code = Constants.HTTP_404;

            } else {

                code = Constants.HTTP_500;
            }

            return new RpcResponseDtoInner(responseDto, code);

        } else {

            return make_res(parameterObject, null,
                    new InternalErrorException(), null);
        }
    }

    /**
     * 
     * @author liaoqiqi
     * @version 2014-8-30
     */
    static class RpcResponseDtoInner {

        private ResponseDto responseDto;
        private int status;

        public RpcResponseDtoInner(ResponseDto rpcResponseDto, int status) {
            super();
            this.responseDto = rpcResponseDto;
            this.status = status;
        }

        public ResponseDto getResponseDto() {
            return responseDto;
        }

        public void setResponseDto(ResponseDto responseDto) {
            this.responseDto = responseDto;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
