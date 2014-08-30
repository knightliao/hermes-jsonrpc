package com.github.knightliao.hermesjsonrpc.client.core.jsonrpc;

import com.github.knightliao.hermesjsonrpc.core.auth.AuthController;
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
        addHeaderProperties(Constants.WWW_AUTH_RPC,
                AuthController.getAuth(username, password, encoding));
    }

    /**
     * 
     * @param url
     * @param encoding
     * @param username
     * @param password
     * @param connectTimeout
     * @param readTimeout
     * @param exp
     */
    public RpcProxyWithAuthenticator(String url, String encoding,
            String username, String password, int connectTimeout,
            int readTimeout, ExceptionHandler exp) {

        this(url, encoding, exp, username, password);
        setConnectTimeout(connectTimeout);
        setReadTimeout(readTimeout);
    }

}
