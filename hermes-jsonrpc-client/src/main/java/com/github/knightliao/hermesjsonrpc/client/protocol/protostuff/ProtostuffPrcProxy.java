package com.github.knightliao.hermesjsonrpc.client.protocol.protostuff;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.hermesjsonrpc.client.core.RpcProxyBase;
import com.github.knightliao.hermesjsonrpc.core.codec.Codec;
import com.github.knightliao.hermesjsonrpc.core.codec.protostuff.ProtostuffCodec;
import com.github.knightliao.hermesjsonrpc.core.constant.ProtocolEnum;
import com.github.knightliao.hermesjsonrpc.core.dto.RequestDto;
import com.github.knightliao.hermesjsonrpc.core.dto.ResponseDto;
import com.github.knightliao.hermesjsonrpc.core.exception.ExceptionHandler;
import com.github.knightliao.hermesjsonrpc.core.exception.ParseErrorException;

/**
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class ProtostuffPrcProxy extends RpcProxyBase {

    protected static final Logger LOG = LoggerFactory.getLogger(ProtostuffPrcProxy.class);

    /**
     * 用于放置header中需要添加的属性信息
     */
    protected Map<String, String> headerProperties = new HashMap<String, String>();

    private Codec codec = new ProtostuffCodec();

    /**
     * @param url
     * @param encoding
     * @param exceptionHandler
     * @param userName
     * @param password
     */
    public ProtostuffPrcProxy(String url, String encoding, ExceptionHandler exceptionHandler, String userName,
                              String password) {
        super(url, encoding, exceptionHandler, userName, password);
    }

    /**
     *
     */
    @Override
    protected ResponseDto deserialize(byte[] req, Type type) throws ParseErrorException {

        try {

            ResponseDto dataDto = codec.decode(encoding, ResponseDto.class, req);
            LOG.debug("response: " + dataDto.toString());

            return dataDto;

        } catch (Exception e) {

            LOG.error("deserialize byte failed", e);
            throw new ParseErrorException("deserialize byte error");
        }
    }

    /**
     *
     */
    @Override
    protected byte[] serialize(RequestDto res) throws ParseErrorException {

        try {

            LOG.debug("request: " + res.toString());
            return codec.encode(encoding, RequestDto.class, res);

        } catch (Exception e) {

            LOG.error("serialize byte failed", e);
            throw new ParseErrorException("serialize byte error");
        }
    }

    @Override
    protected String contentType() {
        return ProtocolEnum.PROTOSTUFF.getModelName();
    }

}
