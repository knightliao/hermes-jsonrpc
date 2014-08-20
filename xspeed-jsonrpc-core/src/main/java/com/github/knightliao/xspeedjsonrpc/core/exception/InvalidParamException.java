package com.github.knightliao.xspeedjsonrpc.core.exception;

/**
 * 当Rpc调用参数不满足要求时（类型错误，值错误）时抛出
 * 
 */
public class InvalidParamException extends JsonRpcException {

    private static final long serialVersionUID = -2871706631475075323L;

    public static final int INVALID_PARAM_ERROR_CODE = -32602;

    public InvalidParamException() {
        super();
    }

    public InvalidParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParamException(String message) {
        super(message);
    }

    public InvalidParamException(Throwable cause) {
        super(cause);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.rpc.exception.JsonRpcException#errorCode()
     */
    @Override
    public int errorCode() {
        return INVALID_PARAM_ERROR_CODE;
    }

}
