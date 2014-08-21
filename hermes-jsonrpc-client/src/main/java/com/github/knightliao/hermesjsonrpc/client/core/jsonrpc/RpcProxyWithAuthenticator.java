package com.github.knightliao.hermesjsonrpc.client.core.jsonrpc;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.util.EncodingUtil;

import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
import com.github.knightliao.hermesjsonrpc.core.exception.ExceptionHandler;

/**
 * 远程调用代理类，相对于基类添加了向Header中加入属性和增加base密码验证的功能
 * 
 * @author liaoqiqi
 * @version 2014-8-21
 */
public class RpcProxyWithAuthenticator extends RpcProxyWithHeaderProperty {

    @Override
    public Object clone() {
        RpcProxyWithAuthenticator result = new RpcProxyWithAuthenticator(url,
                encoding, exceptionHandler);
        result.setHeaderProperties(headerProperties);
        return result;
    }

    /**
     * @param url
     *            服务的url
     * @param encoding
     *            编码
     * @param exp
     *            异常处理器
     */
    public RpcProxyWithAuthenticator(String url, String encoding,
            ExceptionHandler exp) {
        super(url, encoding, exp);
    }

    /**
     * 
     * @param url
     * @param encoding
     * @param exp
     * @param username
     * @param password
     */
    public RpcProxyWithAuthenticator(String url, String encoding,
            ExceptionHandler exp, String username, String password) {
        super(url, encoding, exp);
        // 授权信息
        addHeaderProperties(Constants.WWW_AUTH_RPC, getAuth(username, password));
    }

    /**
     * 带auth的构造函数
     * 
     * @param url
     *            服务url
     * @param encoding
     *            编码
     * @param username
     *            auth的username
     * @param password
     *            auth的password
     * @param connectTimeout
     *            连接超时时间
     * @param readTimeout
     *            read超时
     * @param exp
     */
    public RpcProxyWithAuthenticator(String url, String encoding,
            String username, String password, int connectTimeout,
            int readTimeout, ExceptionHandler exp) {

        super(url, encoding, exp);
        setConnectTimeout(connectTimeout);
        setReadTimeout(readTimeout);

        // 授权信息
        addHeaderProperties(Constants.WWW_AUTH_RPC, getAuth(username, password));
    }

    /**
     * 
     * @param username
     * @param password
     * @return
     */
    private String getAuth(String username, String password) {

        String _basicAuth = "Basic "
                + EncodingUtil.getAsciiString(Base64.encodeBase64(EncodingUtil
                        .getBytes(username + ":" + password, encoding)));
        return _basicAuth;
    }
}
