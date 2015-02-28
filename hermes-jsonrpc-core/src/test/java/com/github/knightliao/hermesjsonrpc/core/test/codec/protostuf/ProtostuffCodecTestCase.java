package com.github.knightliao.hermesjsonrpc.core.test.codec.protostuf;

import org.junit.Assert;
import org.junit.Test;

import com.github.knightliao.hermesjsonrpc.core.codec.Codec;
import com.github.knightliao.hermesjsonrpc.core.codec.protostuff.ProtostuffCodec;
import com.github.knightliao.hermesjsonrpc.core.test.codec.TestData;
import com.github.knightliao.hermesjsonrpc.core.test.codec.gson.GsonCodecTestCase;

/**
 * @author liaoqiqi
 * @version 2014-9-2
 */
public class ProtostuffCodecTestCase {

    Codec proCodec = new ProtostuffCodec();

    public static final String encoding = "UTF-8";

    /**
     *
     */
    @Test
    public void test() {

        TestData testData = new TestData();

        try {

            long start = GsonCodecTestCase.getCurrentTime();

            int length = 0;
            for (int i = 0; i < 100000; ++i) {

                byte data[];

                data = proCodec.encode(encoding, TestData.class, testData);

                length = data.length;
                TestData testData2 = proCodec.decode(encoding, TestData.class, data);

                Assert.assertEquals(testData, testData2);
            }
            System.out.println("length: " + length);

            long end = GsonCodecTestCase.getCurrentTime();

            System.out.println(end - start);

        } catch (Exception e) {

            Assert.assertTrue(false);
        }

    }
}
