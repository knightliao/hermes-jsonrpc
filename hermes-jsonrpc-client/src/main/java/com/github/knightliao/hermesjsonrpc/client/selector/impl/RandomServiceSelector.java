package com.github.knightliao.hermesjsonrpc.client.selector.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.hermesjsonrpc.client.selector.ServiceInvoker;
import com.github.knightliao.hermesjsonrpc.client.selector.ServiceSelector;

/**
 * 实现动态调用Service该类要实现以下两个功能：负载均衡和互备。
 *
 * @author liaoqiqi
 * @version 2014-8-21
 */
public class RandomServiceSelector implements ServiceSelector {

    protected static final Logger LOG = LoggerFactory.getLogger(RandomServiceSelector.class);

    private List<ServiceInvoker> invokerList = null;
    private int retryTime = 0;

    private Method method;
    private Object[] args;

    private Random randomer = new Random();

    /**
     * @param serviceList 服务列表
     * @param retryTimes  重试次数
     * @param method      方法入口
     * @param args        方法参数
     */
    public RandomServiceSelector(List<ServiceInvoker> serviceList, int retryTimes, Method method, Object[] args) {

        if (CollectionUtils.isEmpty(serviceList)) {
            throw new IllegalArgumentException("serviceList should not be empty!");
        }
        invokerList = serviceList;

        if (retryTimes < 1) {
            this.retryTime = 1;
        } else {
            this.retryTime = retryTimes;
        }

        this.method = method;
        this.args = args;
    }

    /**
     *
     */
    public Object invoke(boolean errorExit) {

        // 需要实现负载均衡和互备功能
        if (invokerList == null) {
            return null;
        }

        // 1、生成一个随机数
        int start = Math.abs(randomer.nextInt()) % invokerList.size();

        for (int retry = 0; retry < retryTime && retry < invokerList.size(); retry++) {

            ServiceInvoker service = invokerList.get(start);

            // 2、以1生成的数为基准，+1生成下一个数，但不能超出random.size()的界限
            start++;
            start %= invokerList.size();

            try {

                return method.invoke(service.getInvoker(), args);

            } catch (Exception e) {
                printErrMessage(e);
                if (errorExit) {
                    throw new RuntimeException(e);
                }
                continue;
            }
        }
        return null;
    }

    /**
     * @param e
     */
    private void printErrMessage(Throwable e) {

        if (e == null) {
            return;
        }

        Throwable baseCause = e;
        while (baseCause.getCause() != null) {
            baseCause = baseCause.getCause();
        }

        // "o"=origin, "d"=direct, "e"=exception, "m"=message
        String errMsg =
            "RPC invoke error,Target=[" + method.getDeclaringClass().getCanonicalName() + "#" + method.getName() +
                "()], oe=[" + baseCause.getClass().getCanonicalName() + "], om=[" + baseCause.getMessage() + "], de=[" +
                e.getClass().getCanonicalName() + "], dm=[" + e.getMessage() + "]";

        LOG.error(errMsg, e);
    }

}
