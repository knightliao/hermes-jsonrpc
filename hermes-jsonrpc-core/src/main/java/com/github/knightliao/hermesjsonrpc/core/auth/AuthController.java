package com.github.knightliao.hermesjsonrpc.core.auth;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.util.EncodingUtil;

/**
 * 非反转的密码控制
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class AuthController {

    /**
     * 生成加密头
     * 
     * @param username
     * @param password
     * @return
     */
    public static String getAuth(String username, String password,
            String encoding) {

        String data = DigestUtils.shaHex(username + ":" + password);
        String _basicAuth = EncodingUtil.getAsciiString(Base64
                .encodeBase64(EncodingUtil.getBytes(data, encoding)));
        return _basicAuth;
    }

}
