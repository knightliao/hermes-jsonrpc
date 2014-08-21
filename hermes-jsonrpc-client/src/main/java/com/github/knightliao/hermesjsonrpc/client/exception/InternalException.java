package com.github.knightliao.hermesjsonrpc.client.exception;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-21
 */
public class InternalException extends Exception {

    private static final long serialVersionUID = -760220040076219737L;

    public InternalException() {
        super();
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalException(String message) {
        super(message);
    }

    public InternalException(Throwable cause) {
        super(cause);
    }

}
