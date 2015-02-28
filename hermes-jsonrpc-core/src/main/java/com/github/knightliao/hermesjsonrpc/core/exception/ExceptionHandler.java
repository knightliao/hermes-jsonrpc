package com.github.knightliao.hermesjsonrpc.core.exception;

import java.util.HashMap;
import java.util.List;

import com.github.knightliao.hermesjsonrpc.core.dto.ErrorDto;
import com.github.knightliao.hermesjsonrpc.core.dto.ErrorDto.ErrorDtoBuilder;

/**
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class ExceptionHandler {

    /**
     * 用户自定义异常体系
     */
    private HashMap<Integer, Class<? extends JsonRpcException>> errorCodeMap =
        new HashMap<Integer, Class<? extends JsonRpcException>>();

    private HashMap<Class<? extends JsonRpcException>, Integer> exceptionMap =
        new HashMap<Class<? extends JsonRpcException>, Integer>();

    /**
     * 添加用户定义的异常。 用户定义的异常都应是JsonRpcException的子类， <br/>
     * 并设置errorCode
     *
     * @param userException
     *
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
     *
     * @return
     */
    private ErrorDto makeExcetpion(int error, JsonRpcException e) {

        String message = null;
        String data = null;

        if (e instanceof ServerErrorException && !e.getClass().isMemberClass()) {

            Throwable cause = e.getCause();

            if (cause != null) {

                // message
                message = cause.getClass().getSimpleName();

                // data
                data = cause.getMessage();

                return ErrorDtoBuilder.getResponseDto(data, error, message);
            }
        }

        // message
        message = e.getClass().getSimpleName();
        // data
        data = e.getMessage();

        return ErrorDtoBuilder.getResponseDto(data, error, message);
    }

    /**
     * @param e
     *
     * @return
     */
    public ErrorDto serialize(JsonRpcException e) {

        Integer error = exceptionMap.get(e.getClass());

        if (error != null) {

            return makeExcetpion(error, e);

        } else {

            return makeExcetpion(e.errorCode(), e);
        }
    }

    /**
     * @param e
     *
     * @return
     */
    public JsonRpcException deserialize(ErrorDto e) {

        try {

            // get data
            String cause = e.getData();

            //
            // get code
            //
            int code = e.getCode();

            // exception
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

                // get message
                String message = e.getMessage();

                if (code == ParseErrorException.PARSE_ERROR_CODE) {

                    return new ParseErrorException(message, new Exception(cause));

                } else if (code == InvalidRequestException.INVALID_REQUEST_ERROR_CODE) {

                    return new InvalidRequestException(cause);

                } else if (code == MethodNotFoundException.METHOD_NOT_FOUND_ERROR_CODE) {

                    return new MethodNotFoundException(cause);

                } else if (code == InvalidParamException.INVALID_PARAM_ERROR_CODE) {

                    return new InvalidParamException(cause);

                } else if (code == InternalErrorException.INTERNAL_ERROR_CODE) {

                    return new InternalErrorException(cause);

                } else if (code == ServerErrorException.SERVER_ERROR_CODE) {

                    return new ServerErrorException(message, new Exception(cause));
                } else {

                    return new InternalErrorException(message, new Exception(cause));
                }
            }

        } catch (Exception ee) {

            return new InternalErrorException(ee);
        }
    }
}
