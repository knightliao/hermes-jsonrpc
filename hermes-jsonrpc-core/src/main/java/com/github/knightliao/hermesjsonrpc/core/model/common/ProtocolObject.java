package com.github.knightliao.hermesjsonrpc.core.model.common;

/**
 * client请求对象 / server接受对象   两者的基类 
 * 
 * @author liaoqiqi
 * @version 2014-8-29
 */
public abstract class ProtocolObject {

    protected static final String JSONRPC_METHOD = "method";
    protected static final String JSONRPC_PARAM = "params";

    // 函数名
    protected String method;
    // 版本
    protected String version;

    public ProtocolObject(String method, String version) {
        super();
        this.method = method;
        this.version = version;
    }

}
