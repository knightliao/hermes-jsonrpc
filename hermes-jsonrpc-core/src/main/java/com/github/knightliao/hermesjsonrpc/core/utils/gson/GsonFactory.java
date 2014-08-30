package com.github.knightliao.hermesjsonrpc.core.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-20
 */
public class GsonFactory {

    /**
     * 
     * @return
     */
    public static Gson getGson() {

        return new GsonBuilder().serializeNulls().disableHtmlEscaping()
                .serializeSpecialFloatingPointValues().create();
    }
}
