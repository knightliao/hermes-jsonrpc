package com.github.knightliao.hermesjsonrpc.client.test.protocol.base;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.github.knightliao.apollo.utils.data.GsonUtils;
import com.github.knightliao.hermesjsonrpc.client.test.protocol.common.DemoServiceDriver.Response;
import com.github.knightliao.hermesjsonrpc.core.codec.Codec;
import com.github.knightliao.hermesjsonrpc.core.codec.protostuff.ProtostuffCodec;
import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
import com.github.knightliao.hermesjsonrpc.core.dto.ResponseDto;
import com.github.knightliao.hermesjsonrpc.core.dto.ResponseDto.ResponseDtoBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

/**
 * 
 * @author liaoqiqi
 * @version 2014-9-2
 */
public class BaseTestCase {

    @ClassRule
    @Rule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8089);

    public static String URL = "/export/DemoServiceDriver";
    public static final String CONTENT_TYPE = "application/json";
    public static final Integer DEFAULT_VALUE = 4;

    /**
     * 
     */
    protected static void setupGsonRpc() {

        Response response = new Response(DEFAULT_VALUE);

        ResponseDto responseDto = ResponseDtoBuilder.getResponseDto(response,
                null, "2.0", 0L);

        System.out.println(GsonUtils.toJson(responseDto));

        stubFor(post(urlEqualTo(URL)).willReturn(
                aResponse().withHeader("Content-Type", CONTENT_TYPE)
                        .withStatus(200)
                        .withBody(GsonUtils.toJson(responseDto))));

    }

    /**
     * 
     */
    protected static void setupProtostuffRpc() {

        Response response = new Response(DEFAULT_VALUE);

        ResponseDto responseDto = ResponseDtoBuilder.getResponseDto(response,
                null, "2.0", 0L);

        System.out.println(GsonUtils.toJson(responseDto));

        Codec protostuffCodec = new ProtostuffCodec();

        try {
            stubFor(post(urlEqualTo(URL)).willReturn(
                    aResponse()
                            .withHeader("Content-Type", CONTENT_TYPE)
                            .withStatus(200)
                            .withBody(
                                    protostuffCodec.encode(
                                            Constants.DEFAULT_CODING,
                                            ResponseDto.class, responseDto))));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }

    }

    @Test
    public void test() {

    }
}
