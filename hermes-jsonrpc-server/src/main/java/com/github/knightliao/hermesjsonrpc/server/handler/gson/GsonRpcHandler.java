package com.github.knightliao.hermesjsonrpc.server.handler.gson;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;

import com.github.knightliao.hermesjsonrpc.core.codec.gson.GsonCodec;
import com.github.knightliao.hermesjsonrpc.core.constant.Constants;
import com.github.knightliao.hermesjsonrpc.core.dto.RequestDto;
import com.github.knightliao.hermesjsonrpc.core.dto.RequestDto.RequestDtoBuilder;
import com.github.knightliao.hermesjsonrpc.core.dto.ResponseDto;
import com.github.knightliao.hermesjsonrpc.core.exception.InvalidParamException;
import com.github.knightliao.hermesjsonrpc.core.exception.MethodNotFoundException;
import com.github.knightliao.hermesjsonrpc.core.exception.ParseErrorException;
import com.github.knightliao.hermesjsonrpc.server.handler.JsonRpcHandlerBase;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-30
 */
public class GsonRpcHandler extends JsonRpcHandlerBase {

    private static final GsonCodec processor = new GsonCodec();

    @Override
    protected byte[] serialize(String encoding, ResponseDto responseDto)
            throws ParseErrorException {

        JsonElement jsonElement = processor.encode(responseDto,
                ResponseDto.class);
        LOGGER.debug("response: " + jsonElement.toString());

        return processor.encode(encoding, jsonElement);
    }

    @Override
    protected <T> RequestDto deserialize(String encoding, byte[] req,
            Class<T> clazz) throws ParseErrorException {

        //
        // 反序列化JSON对象
        //
        JsonElement jsonElement = processor.decode(encoding, req);
        LOGGER.debug("request: " + jsonElement.toString());

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        //
        // 拿函数名
        //

        String methodname = jsonObject.get(RequestDto.JSONRPC_METHOD)
                .getAsString();
        if (methodname == null) {
            throw new MethodNotFoundException();
        }

        //
        // 反序列化参数
        //
        for (Method ms : clazz.getMethods()) {

            //
            // 找到处理函数
            //
            if (ms.getName().equals(methodname)) {

                // 拿参数
                JsonArray jsonArray = jsonObject.get(RequestDto.JSONRPC_PARAM)
                        .getAsJsonArray();
                if (jsonArray == null) {
                    continue;
                }

                //
                // 参数数目不对
                //
                Type[] argtype = ms.getGenericParameterTypes();
                if (argtype.length != jsonArray.size()) {
                    LOGGER.debug("request: " + jsonObject.toString());
                    throw new InvalidParamException();
                }

                //
                // 组装函数参数
                //
                Object arg_obj[] = new Object[argtype.length];
                Iterator<JsonElement> e = jsonArray.iterator();
                for (int i = 0; i < jsonArray.size(); i++) {
                    arg_obj[i] = processor.decode(e.next(), argtype[i]);
                }

                // 拿版本号
                String version = "";
                JsonElement versionE = jsonObject
                        .get(Constants.JSONRPC_PROTOCOL_VERSION);
                if (versionE != null) {
                    version = versionE.getAsString();
                }

                // 拿ID
                Long id = null;
                JsonElement idElement = jsonObject.get(Constants.JSONRPC_ID);
                if (idElement != null) {
                    id = idElement.getAsLong();
                }

                return RequestDtoBuilder.getRequestDto(methodname, version,
                        arg_obj, id);
            }
        }

        // 函数找不到
        throw new MethodNotFoundException();
    }
}
