package com.github.knightliao.hermesjsonrpc.core.dto;

import java.util.Arrays;

/**
 * 请求对象
 *
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class RequestDto {

    public static final String JSONRPC_METHOD = "method";
    public static final String JSONRPC_PARAM = "params";

    // 函数名
    private String method;

    // 版本
    private String version;

    // 参数
    private Object[] params;

    // id
    private Long id;

    /**
     * @author liaoqiqi
     * @version 2014-8-30
     */
    static public class RequestDtoBuilder {

        public static RequestDto getRequestDto(String method, String version, Object[] params, Long id) {

            return new RequestDto(method, version, params, id);
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestDto(String method, String version, Object[] params, Long id) {
        super();
        this.method = method;
        this.version = version;
        this.params = params;
        this.id = id;
    }

    @Override
    public String toString() {
        return "RequestDto [method=" + method + ", version=" + version + ", params=" + Arrays.toString(params) +
                   ", id=" + id + "]";
    }

    public RequestDto() {

    }
}
