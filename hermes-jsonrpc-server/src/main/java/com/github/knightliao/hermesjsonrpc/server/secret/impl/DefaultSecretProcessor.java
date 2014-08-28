package com.github.knightliao.hermesjsonrpc.server.secret.impl;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.hermesjsonrpc.server.secret.SecretProcessor;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-28
 */
public class DefaultSecretProcessor implements SecretProcessor {

    protected final Logger LOG = LoggerFactory
            .getLogger(DefaultSecretProcessor.class);

    /**
     * 
     * @param username
     * @param password
     * @return
     */
    private String getAuth(String data, String encoding) {

        try {
            String _basicAuth = new String(Base64.decodeBase64(data), encoding);
            return _basicAuth;
        } catch (UnsupportedEncodingException e) {
            LOG.info(e.toString());
            return "";
        }
    }

    @Override
    public boolean isAuthOk(String data, String encoding, String userName,
            String password) {

        // 均为空则认为过
        if (data == null && userName == null && password == null) {
            return true;
        }

        if (data == null) {
            return false;
        }

        String secretData = getAuth(data, encoding);

        String[] secretPair = StringUtils.split(secretData, ":");
        if (secretPair == null || secretPair.length != 2) {
            return false;
        }

        if (secretPair[0].equals(userName) && secretPair[1].equals(password)) {
            return true;
        }

        LOG.warn("userName:passowrd " + userName + ":" + password);

        return false;
    }

    @Override
    public boolean isIpOk(String data, Set<String> ipSet) {

        if (ipSet == null || ipSet.size() == 0) {
            return true;
        }

        return ipSet.contains(data);
    }
}
