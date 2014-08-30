package com.github.knightliao.hermesjsonrpc.core.test.gson;

import org.junit.Assert;
import org.junit.Test;

import com.github.knightliao.hermesjsonrpc.core.utils.gson.GsonFactory;
import com.google.gson.Gson;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-28
 */
public class GsonFatoryTestCase {

    /**
     * 
     */
    @Test
    public void getGsonTest() {

        Gson gson = GsonFactory.getGson();

        Assert.assertEquals("[1,2]", gson.toJson(new int[] { 1, 2 }));
    }
}
