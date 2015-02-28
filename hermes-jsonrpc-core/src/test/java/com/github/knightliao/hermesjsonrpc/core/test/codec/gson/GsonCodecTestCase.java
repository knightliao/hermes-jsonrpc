package com.github.knightliao.hermesjsonrpc.core.test.codec.gson;

import org.junit.Assert;
import org.junit.Test;

import com.github.knightliao.hermesjsonrpc.core.codec.Codec;
import com.github.knightliao.hermesjsonrpc.core.codec.gson.GsonCodec;
import com.github.knightliao.hermesjsonrpc.core.test.codec.TestData;

/**
 * @author liaoqiqi
 * @version 2014-9-2
 */
public class GsonCodecTestCase {

    Codec gsonCodec = new GsonCodec();

    public static final String encoding = "UTF-8";

    /**
     *
     */
    @Test
    public void test() {

        TestData testData = new TestData();

        try {

            long start = getCurrentTime();

            int length = 0;
            for (int i = 0; i < 100000; ++i) {

                byte data[];

                data = gsonCodec.encode(encoding, TestData.class, testData);
                length = data.length;
                TestData testData2 = gsonCodec.decode(encoding, TestData.class, data);

                Assert.assertEquals(testData, testData2);
            }
            System.out.println("length: " + length);

            long end = getCurrentTime();

            System.out.println(end - start);

        } catch (Exception e) {

            Assert.assertTrue(false);
        }

    }

    public static long getCurrentTime() {

        java.util.Date date = new java.util.Date();
        return date.getTime();
    }
}
