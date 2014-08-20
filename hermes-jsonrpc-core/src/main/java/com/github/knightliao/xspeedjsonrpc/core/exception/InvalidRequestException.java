package com.github.knightliao.xspeedjsonrpc.core.exception;

/**
 * 当Rpc请求不符合rpc规范是抛出
 * 
 */
public class InvalidRequestException extends JsonRpcException {

    public static final int INVALID_REQUEST_ERROR_CODE = -32600;

    /**
	 * 
	 */
    private static final long serialVersionUID = -6138595796119511714L;

    public InvalidRequestException() {
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(Throwable cause) {
        super(cause);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.rpc.exception.JsonRpcException#errorCode()
     */
    @Override
    public int errorCode() {
        return INVALID_REQUEST_ERROR_CODE;
    }

}
