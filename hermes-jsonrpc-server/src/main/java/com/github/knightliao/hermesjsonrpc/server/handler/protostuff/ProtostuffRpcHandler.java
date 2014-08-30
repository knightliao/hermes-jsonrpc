package com.github.knightliao.hermesjsonrpc.server.handler.protostuff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.hermesjsonrpc.core.codec.Codec;
import com.github.knightliao.hermesjsonrpc.core.codec.protostuff.ProtostuffCodec;
import com.github.knightliao.hermesjsonrpc.core.dto.RequestDto;
import com.github.knightliao.hermesjsonrpc.core.dto.ResponseDto;
import com.github.knightliao.hermesjsonrpc.core.exception.ParseErrorException;
import com.github.knightliao.hermesjsonrpc.server.handler.JsonRpcHandlerBase;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class ProtostuffRpcHandler extends JsonRpcHandlerBase {

    protected static final Logger LOG = LoggerFactory
            .getLogger(ProtostuffRpcHandler.class);

    private Codec codec = new ProtostuffCodec();

    /**
     * 
     */
    @Override
    protected <T> RequestDto deserialize(String encoding, byte[] req,
            Class<T> clazz) throws ParseErrorException {

        try {

            RequestDto dataDto = codec.decode(encoding, RequestDto.class, req);

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
    protected byte[] serialize(String encoding, ResponseDto responseDto)
            throws ParseErrorException {

        try {

            LOG.debug("request: " + responseDto.toString());
            return codec.encode(encoding, ResponseDto.class, responseDto);

        } catch (Exception e) {

            LOG.error("serialize byte failed", e);
            throw new ParseErrorException("serialize byte error");
        }
    }

}
