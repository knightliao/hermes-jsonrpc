package com.github.knightliao.hermesjsonrpc.server.secret.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.hermesjsonrpc.core.auth.AuthController;
import com.github.knightliao.hermesjsonrpc.server.secret.SecretProcessor;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-28
 */
public class DefaultSecretProcessor implements SecretProcessor {

    protected final Logger LOG = LoggerFactory
            .getLogger(DefaultSecretProcessor.class);

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

        String secretData = AuthController
                .getAuth(userName, password, encoding);

        if (secretData.equals(data)) {
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
