package com.github.knightliao.hermesjsonrpc.client.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.hermesjsonrpc.core.auth.AuthController;
import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
import com.github.knightliao.hermesjsonrpc.core.dto.ErrorDto;
import com.github.knightliao.hermesjsonrpc.core.dto.RequestDto;
import com.github.knightliao.hermesjsonrpc.core.dto.RequestDto.RequestDtoBuilder;
import com.github.knightliao.hermesjsonrpc.core.dto.ResponseDto;
import com.github.knightliao.hermesjsonrpc.core.exception.ExceptionHandler;
import com.github.knightliao.hermesjsonrpc.core.exception.InternalErrorException;
import com.github.knightliao.hermesjsonrpc.core.exception.JsonRpcException;
import com.github.knightliao.hermesjsonrpc.core.exception.ParseErrorException;
import com.github.knightliao.hermesjsonrpc.core.exception.ServerErrorException;

/**
 * JsonRpc调用端的公共基类，包含绝大部分rpc调用的实现
 * 
 * @author liaoqiqi
 * @version 2014-8-20
 */
public abstract class RpcProxyBase implements InvocationHandler, Cloneable {

    protected static final Logger LOG = LoggerFactory
            .getLogger(RpcProxyBase.class);

    private ExceptionHandler exceptionHandler = new ExceptionHandler();

    private AtomicLong counter = new AtomicLong(0L);
    protected String encoding;
    private String url;
    private int _connectTimeout = 30; // 默认连接超时30秒
    private int _readTimeout = 60; // 默认连接超时60秒

    /** 用于放置header中需要添加的属性信息 */
    private Map<String, String> headerProperties = new HashMap<String, String>();

    /**
     * 
     * @param req
     * @return
     * @throws ParseErrorException
     */
    protected abstract ResponseDto deserialize(byte[] req, Type type)
            throws ParseErrorException;

    /**
     * 
     * @param res
     * @return
     * @throws ParseErrorException
     */
    protected abstract byte[] serialize(RequestDto res)
            throws ParseErrorException;

    /**
     * 
     * @return
     */
    protected abstract String contentType();

    /**
     * 
     * @param url
     * @param encoding
     * @param exceptionHandler
     */
    public RpcProxyBase(String url, String encoding,
            ExceptionHandler exceptionHandler, String username, String password) {

        this(url, encoding, exceptionHandler);

        // 授权信息
        addHeaderProperties(Constants.WWW_AUTH_RPC,
                AuthController.getAuth(username, password, encoding));
    }

    /**
     * 
     * @param url
     * @param encoding
     * @param exceptionHandler
     */
    public RpcProxyBase(String url, String encoding,
            ExceptionHandler exceptionHandler) {
        this.encoding = encoding;
        this.url = url;
        LOG.debug("url: " + url);
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * 调用 函数
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

        try {

            //
            // 请求
            //
            long id = counter.getAndIncrement();
            RequestDto request = makeRequest(id, method, args);
            // LOG.debug("request=" + request);

            //
            // 序列化
            //
            byte[] reqBytes = serialize(request);
            LOG.debug("request bytes size is " + reqBytes.length);

            //
            // 连接
            //
            HttpURLConnection connection = (HttpURLConnection) new URL(url)
                    .openConnection();
            if (_connectTimeout > 0) {
                connection.setConnectTimeout(_connectTimeout);
            }
            if (_readTimeout > 0) {
                connection.setReadTimeout(_readTimeout);
            }

            //
            // 发送请求
            //
            sendRequest(reqBytes, connection);
            byte[] resBytes = null;
            resBytes = readResponse(connection);

            //
            // 反序列化
            //
            ResponseDto responseDto = deserialize(resBytes,
                    method.getGenericReturnType());
            // LOG.debug("result=" + responseDto);

            //
            // 校验结果
            //
            checkResponse(id, responseDto, method);

            return responseDto.getResult();

        } catch (IOException e) {

            throw new InternalErrorException(e);
        }
    }

    /**
     * 读取服务器返回的信息
     * 
     * @param connection
     * @return 读取到的数据
     * @throws IOException
     * @throws JsonRpcException
     */
    protected byte[] readResponse(URLConnection connection)
            throws JsonRpcException {

        byte[] resBytes;
        InputStream in = null;

        HttpURLConnection httpconnection = (HttpURLConnection) connection;

        try {

            // 正常读取
            if (httpconnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                in = httpconnection.getInputStream();

            } else {

                // 读取错误日志
                if (httpconnection.getContentType().equals(contentType())
                        && httpconnection.getErrorStream() != null) {
                    in = httpconnection.getErrorStream();
                } else {
                    in = httpconnection.getInputStream();
                }
            }

            // 长度
            int len = httpconnection.getContentLength();
            if (len <= 0) {
                throw new InternalErrorException("no response to get.");
            }

            // 读取字节
            resBytes = new byte[len];
            int offset = 0;
            while (offset < resBytes.length) {
                int bytesRead = in.read(resBytes, offset, resBytes.length
                        - offset);
                if (bytesRead == -1) {
                    break; // end of stream
                }
                offset += bytesRead;
            }

            if (offset <= 0) {
                throw new InternalErrorException("there is no service to "
                        + url);
            }

        } catch (IOException e) {

            throw new InternalErrorException(e);

        } finally {

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new InternalErrorException(e);
                }
            }
        }
        return resBytes;
    }

    /**
     * 向服务器发送信息
     * 
     * @param reqBytes
     * @param connection
     * @throws IOException
     */
    protected void sendRequest(byte[] reqBytes, URLConnection connection) {

        //
        // 加头
        //
        if (null != headerProperties) {
            for (Entry<String, String> entry : headerProperties.entrySet()) {
                if (null != entry.getValue()) {
                    connection.addRequestProperty(entry.getKey(),
                            entry.getValue());
                }
            }
        }

        //
        // 发送消息
        //
        HttpURLConnection httpconnection = (HttpURLConnection) connection;
        OutputStream out = null;

        try {
            httpconnection.setRequestMethod("POST");
            httpconnection.setUseCaches(false);
            httpconnection.setDoInput(true);
            httpconnection.setDoOutput(true);
            httpconnection.setRequestProperty("Content-Type", contentType()
                    + ";charset=" + encoding);
            httpconnection.setRequestProperty("Content-Length", ""
                    + reqBytes.length);
            httpconnection.connect();
            out = httpconnection.getOutputStream();
            out.write(reqBytes);

        } catch (Exception e) {

            throw new InternalErrorException(e);

        } finally {

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new InternalErrorException(e);
                }
            }
        }
    }

    /**
     * 
     * @param id
     * @param method
     * @param args
     * @return
     */
    protected RequestDto makeRequest(long id, Method method, Object[] args) {

        RequestDto requestDto = RequestDtoBuilder.getRequestDto(
                method.getName(), Constants.JSONRPC_PROTOCOL_VERSION_VALUE,
                args, id);

        LOG.debug("Request: JsonRpc head=" + headerProperties + " request="
                + requestDto);

        return requestDto;
    }

    /**
     * 
     * @param id
     * @param responseDto
     * @param method
     * @return
     * @throws Exception
     */
    protected void checkResponse(long id, ResponseDto responseDto, Method method)
            throws Exception {

        // LOG.debug("Response: JsonRpc head=" + headerProperties + " response="
        // + responseDto);

        //
        // 版本
        //
        if (responseDto.getVersion() == null
                || !responseDto.getVersion().equals(
                        Constants.JSONRPC_PROTOCOL_VERSION_VALUE)) {
            throw new InternalErrorException();
        }

        //
        // result
        //
        if (responseDto.getResult() != null) {

            //
            // id
            //
            if (responseDto.getId() != id) {
                throw new InternalErrorException("no id in response");
            }

        } else {

            //
            // 出错
            //
            ErrorDto errorDto = responseDto.getError();

            if (errorDto != null) {

                //
                // 是否是服务器异常
                //
                JsonRpcException jre = exceptionHandler.deserialize(errorDto);

                if (jre instanceof ServerErrorException) {
                    String msg = jre.getMessage();
                    Class<?>[] exp_types = method.getExceptionTypes();
                    for (Class<?> exp_type : exp_types) {
                        if (msg.equals(exp_type.getSimpleName())) {
                            Exception custom_exp = (Exception) exp_type
                                    .newInstance();
                            custom_exp.initCause(jre.getCause());
                            throw custom_exp;
                        }
                    }
                }

                //
                // 非正常
                //
                throw jre;

            } else {

                //
                // 非正常
                //
                throw new InternalErrorException("no error or result returned");
            }
        }
    }

    /**
     * 
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * 设置连接超时(ms)
     * 
     */
    public void setConnectTimeout(int v) {
        _connectTimeout = v;
    }

    /**
     * 获取连接超时设置(ms)
     * 
     */
    public int getConnectTimeout() {
        return _connectTimeout;
    }

    /**
     * 获取读超时设置(ms)
     * 
     */
    public void setReadTimeout(int v) {
        _readTimeout = v;
    }

    /**
     * 设置读超时(ms)/c
     * 
     */
    public int getReadTimeout() {
        return _readTimeout;
    }

    /**
     * 
     * @param key
     * @param value
     */
    private void addHeaderProperties(String key, String value) {

        if (this.headerProperties == null) {
            this.headerProperties = new HashMap<String, String>();
        }
        this.headerProperties.put(key, value);
    }

    /**
     * 
     * @param headerProperties
     */
    public void addHeaderProperties(Map<String, String> headerProperties) {

        if (headerProperties != null) {
            if (this.headerProperties == null) {
                this.headerProperties = new HashMap<String, String>();
            }
            this.headerProperties.putAll(headerProperties);
        }
    }

    public Map<String, String> getHeaderProperties() {
        return headerProperties;
    }

    public void setHeaderProperties(Map<String, String> headerProperties) {
        this.headerProperties = headerProperties;
    }
}
