package com.github.knightliao.xspeedjsonrpc.core.exception;

/**
 * 所有Rpc异常的基类。不应直接抛出这个类，而是根据错误原因，抛出它的子类
 * 
 */
public abstract class JsonRpcException extends RuntimeException {

    public JsonRpcException() {
        super();
    }

    public JsonRpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonRpcException(String message) {
        super(message);
    }

    public JsonRpcException(Throwable cause) {
        super(cause);
    }

    /**
     * 用于唯一标识异常的类型
     * 
     * @return 异常错误号
     */
    public abstract int errorCode();

    /**
	 * 
	 */
    private static final long serialVersionUID = 3599653969669270363L;

}
