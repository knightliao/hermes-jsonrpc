package com.github.knightliao.hermesjsonrpc.core.dto;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class ResponseDto {

    public static final String JSON_RESULT = "result";
    public static final String JSON_RESULT_ERROR = "error";

    private Object result;
    private ErrorDto error;
    private String version;
    private Long id;

    /**
     * 
     * @author liaoqiqi
     * @version 2014-8-30
     */
    static public class ResponseDtoBuilder {

        public static ResponseDto getResponseDto(Object result, ErrorDto error,
                String version, Long id) {

            return new ResponseDto(result, error, version, id);
        }
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ErrorDto getError() {
        return error;
    }

    public void setError(ErrorDto error) {
        this.error = error;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResponseDto(Object result, ErrorDto error, String version, Long id) {
        super();
        this.result = result;
        this.error = error;
        this.version = version;
        this.id = id;
    }

    @Override
    public String toString() {
        return "ResponseDto [result=" + result + ", error=" + error
                + ", version=" + version + ", id=" + id + "]";
    }
}
