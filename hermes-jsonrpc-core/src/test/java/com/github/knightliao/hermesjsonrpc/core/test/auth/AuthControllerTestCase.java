package com.github.knightliao.hermesjsonrpc.core.test.auth;

import org.junit.Assert;
import org.junit.Test;

import com.github.knightliao.hermesjsonrpc.core.auth.AuthController;

/**
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class AuthControllerTestCase {

    /**
     *
     */
    @Test
    public void getAuthTest() {

        String data = AuthController.getAuth("hello", "hermesjsonrpc", "UTF-8");

        String data2 = AuthController.getAuth("hello", "hermesjsonrpc", "UTF-8");

        Assert.assertEquals(data, data2);
    }

}
