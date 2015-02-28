package com.github.knightliao.hermesjsonrpc.core.exception;

/**
 * 标识被调用函数内部发生了异常
 *
 * @author yufan
 */
public class ServerErrorException extends JsonRpcException {

    public static final int SERVER_ERROR_CODE = -32000;

    /**
     *
     */
    private static final long serialVersionUID = 9029113876648829300L;

    public ServerErrorException() {
    }

    public ServerErrorException(String message) {
        super(message);
    }

    public ServerErrorException(Throwable cause) {
        super(cause);
    }

    public ServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int errorCode() {
        return SERVER_ERROR_CODE;
    }

}
