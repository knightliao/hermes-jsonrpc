package com.github.knightliao.hermesjsonrpc.server.secret;

import java.util.Set;

/**
 * @author liaoqiqi
 * @version 2014-8-28
 */
public interface SecretProcessor {

    /**
     * 权限验证
     *
     * @param data
     *
     * @return
     */
    boolean isAuthOk(String data, String encoding, String userName, String password);

    /**
     * IP白名单
     *
     * @param data
     *
     * @return
     */
    boolean isIpOk(String data, Set<String> ipSet);
}
