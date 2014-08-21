package com.github.knightliao.hermesjsonrpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.github.knightliao.hermesjsonrpc.client.core.jsonrpc.JsonRpcProxy;
import com.github.knightliao.hermesjsonrpc.client.exception.RpcServiceException;
import com.github.knightliao.hermesjsonrpc.client.selector.ServiceInvoker;
import com.github.knightliao.hermesjsonrpc.client.selector.ServiceSelector;
import com.github.knightliao.hermesjsonrpc.client.selector.impl.RandomServiceSelector;

/**
 * 通用的基于JDK的Mcpack客户端调用方式
 * <P>
 * 只依赖于MCPACK
 * 
 * @author liaoqiqi
 * @version 2013-12-5
 */
@SuppressWarnings("rawtypes")
public class RpcProxyFactorySpring implements FactoryBean, InitializingBean {

    protected static final Logger LOG = LoggerFactory
            .getLogger(RpcProxyFactorySpring.class);

    /** 服务列表，非空，由server+url生成 */
    protected String[] services;

    /** 配置的服务器列表，含端口 */
    protected String[] servers;

    /** 协议前缀 */
    protected String protocol = "http://";

    /** 调用的Url */
    protected String serviceUrl;

    /** 编码 */
    protected String encoding = "UTF-8";

    /** 重试次数，会取min(services.length, retryTimes)的较小值来重试 */
    protected int retryTimes = 3;

    //
    protected Class serviceInterface;

    /** 出错后是否直接退出,默认是false */
    protected boolean errorExit = false;

    /** 连接超时，毫秒数 */
    private int connectionTimeout;

    /** 读超时，毫秒数 */
    private int readTimeout;

    /**
     * 
     * @author liaoqiqi
     * @version 2014-8-21
     */
    class SelectorProxy implements InvocationHandler {

        @SuppressWarnings("unchecked")
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {

            final Class targetClass = method.getDeclaringClass();// 待调用的接口；
            Method targetMethodmethod = method;// 待调用的方法
            Object[] targetArgs = args;// 待传入参数

            // 具体调用时实现重试功能
            List<ServiceInvoker> serviceInvokers = new ArrayList<ServiceInvoker>(
                    services.length);

            for (int i = 0; i < services.length; i++) {

                final String serviceUrl = services[i];

                ServiceInvoker service = new ServiceInvoker() {

                    public Object getInvoker() throws RpcServiceException {

                        JsonRpcProxy jsonRpcProxy = RpcProxyFactory
                                .getJsonRpcProxy(serviceUrl, encoding);

                        if (connectionTimeout > 0) {
                            jsonRpcProxy.setConnectTimeout(connectionTimeout);
                        }

                        if (readTimeout > 0) {
                            jsonRpcProxy.setReadTimeout(readTimeout);
                        }

                        return RpcProxyFactory.createProxy(targetClass,
                                jsonRpcProxy);
                    }

                };
                serviceInvokers.add(service);
            }

            ServiceSelector serviceSelector = new RandomServiceSelector(
                    serviceInvokers, retryTimes, targetMethodmethod, targetArgs);
            return serviceSelector.invoke(errorExit);
        }
    }

    /**
     * 
     */
    public Object getObject() throws Exception {

        // 生成一个执行代理
        return Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[] { serviceInterface }, new SelectorProxy());
    }

    public Class getObjectType() {
        return getServiceInterface();
    }

    public boolean isSingleton() {
        return true;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Class getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class serviceInterface) {
        if (serviceInterface == null || !serviceInterface.isInterface()) {
            throw new IllegalArgumentException(
                    "'serviceInterface' must be an interface");
        }
        this.serviceInterface = serviceInterface;
    }

    public String[] getServices() {
        return services;
    }

    public void setServices(String[] services) {
        if (services == null || services.length < 1) {
            throw new IllegalArgumentException(
                    "'services' must be an nonempty array");
        }
        this.services = services;
    }

    public boolean isErrorExit() {
        return errorExit;
    }

    public void setErrorExit(boolean errorExit) {
        this.errorExit = errorExit;
    }

    public String[] getServers() {
        return servers;
    }

    public void setServers(String[] servers) {
        this.servers = servers;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * 
     */
    public void afterPropertiesSet() throws Exception {

        if (services == null || services.length < 1) {

            if (servers == null || servers.length < 1) {

                throw new IllegalArgumentException(
                        "either servers or services is required!");
            }
            services = new String[servers.length];
            for (int i = 0; i < servers.length; i++) {
                services[i] = protocol + servers[i] + serviceUrl;
            }
        }
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

}
