package com.github.knightliao.hermesjsonrpc.server.test.secret;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.knightliao.hermesjsonrpc.core.auth.AuthController;
import com.github.knightliao.hermesjsonrpc.server.secret.SecretProcessor;
import com.github.knightliao.hermesjsonrpc.server.secret.impl.DefaultSecretProcessor;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class DefaultSecretProcessorTestCase {

    @Test
    public void isAuthOkTest() {

        SecretProcessor secretProcessor = new DefaultSecretProcessor();

        String userName = "hello";
        String password = "hermesjsonrpc";
        String encoding = "UTF-8";

        String data = AuthController.getAuth(userName, password, encoding);
        boolean ret = secretProcessor.isAuthOk(data, encoding, userName,
                password);
        Assert.assertEquals(ret, true);

        data = AuthController.getAuth(userName + "1", password, encoding);
        ret = secretProcessor.isAuthOk(data, encoding, userName, password);
        Assert.assertEquals(ret, false);

        data = AuthController.getAuth(null, null, encoding);
        ret = secretProcessor.isAuthOk(data, encoding, null, null);
        Assert.assertEquals(ret, true);
    }

    @Test
    public void isIpOkTest() {

        SecretProcessor secretProcessor = new DefaultSecretProcessor();

        Set<String> ipSet = new HashSet<String>();
        ipSet.add("127.0.0.1");

        Assert.assertEquals(true, secretProcessor.isIpOk("127.0.0.1", ipSet));

        Assert.assertEquals(false, secretProcessor.isIpOk("127.0.0.2", ipSet));
    }
}
