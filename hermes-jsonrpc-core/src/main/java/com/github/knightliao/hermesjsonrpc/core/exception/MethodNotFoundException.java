package com.github.knightliao.hermesjsonrpc.core.exception;

/**
 * 找不到被调用的函数时抛出
 * 
 */
public class MethodNotFoundException extends JsonRpcException {

    public static final int METHOD_NOT_FOUND_ERROR_CODE = -32601;

    private static final long serialVersionUID = -1760095868220273812L;

    public MethodNotFoundException() {
        super();
    }

    public MethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodNotFoundException(String message) {
        super(message);
    }

    public MethodNotFoundException(Throwable cause) {
        super(cause);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.rpc.exception.JsonRpcException#errorCode()
     */
    @Override
    public int errorCode() {
        return METHOD_NOT_FOUND_ERROR_CODE;
    }

}
