package com.github.knightliao.hermesjsonrpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.github.knightliao.hermesjsonrpc.client.core.RpcProxyBase;
import com.github.knightliao.hermesjsonrpc.client.exception.RpcServiceException;
import com.github.knightliao.hermesjsonrpc.client.selector.ServiceInvoker;
import com.github.knightliao.hermesjsonrpc.client.selector.ServiceSelector;
import com.github.knightliao.hermesjsonrpc.client.selector.impl.RandomServiceSelector;
import com.github.knightliao.hermesjsonrpc.core.constant.ProtocolEnum;

/**
 * 最新通的通用的基于JDK的客户端调用方式<br/>
 * <p/>
 * 支持<br/>
 * 1. 普通的RPC调用 <br/>
 * 2. 带头的RPC <br/>
 * 3. 带用户名密码的验证的RPC<br/>
 *
 * @author liaoqiqi
 * @version 2014-8-22
 */
@SuppressWarnings("rawtypes")
public class RpcProxyFactorySpring implements FactoryBean, InitializingBean {

    protected static final Logger LOG = LoggerFactory.getLogger(RpcProxyFactorySpring.class);

    /**
     * 服务列表，非空，由server+url生成
     */
    private String[] services;

    /**
     * 配置的服务器列表，含端口
     */
    private String[] servers;

    /**
     * 协议前缀
     */
    private String protocol = "http://";

    /**
     * 调用的Url
     */
    private String serviceUrl;

    /**
     * 编码
     */
    private String encoding = "UTF-8";

    /**
     * 重试次数，会取min(services.length, retryTimes)的较小值来重试
     */
    private int retryTimes = 3;

    // 接口
    private Class serviceInterface;

    // codec类型
    private String codecType = "";

    /**
     * 出错后是否直接退出,默认是false
     */
    protected boolean errorExit = false;

    /**
     * 连接超时，毫秒数
     */
    private int connectionTimeout;

    /**
     * 读超时，毫秒数
     */
    private int readTimeout;

    // header map
    private Map<String, String> headerMap = new HashMap<String, String>();

    // 用户名密码
    private String userName = null;
    private String password = null;

    /**
     * @author liaoqiqi
     * @version 2014-8-21
     */
    class SelectorProxy implements InvocationHandler {

        @SuppressWarnings("unchecked")
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            // 待调用的接口
            final Class targetClass = method.getDeclaringClass();

            // 待调用的方法
            Method targetMethodmethod = method;

            // 待传入参数
            Object[] targetArgs = args;

            //
            // 具体调用时实现重试功能
            //
            List<ServiceInvoker> serviceInvokers = new ArrayList<ServiceInvoker>(services.length);
            for (int i = 0; i < services.length; i++) {

                final String curServiceUrl = services[i];

                ServiceInvoker service = new ServiceInvoker() {

                    public Object getInvoker() throws RpcServiceException {

                        RpcProxyBase rpcProxyBase = getRpcProxyBase(curServiceUrl);

                        //
                        if (connectionTimeout > 0) {
                            rpcProxyBase.setConnectTimeout(connectionTimeout);
                        }

                        //
                        if (readTimeout > 0) {
                            rpcProxyBase.setReadTimeout(readTimeout);
                        }

                        // 加头
                        if (headerMap.keySet().size() > 0) {
                            rpcProxyBase.addHeaderProperties(headerMap);
                        }

                        return RpcProxyFactory.createProxy(targetClass, rpcProxyBase);
                    }

                };
                serviceInvokers.add(service);
            }

            ServiceSelector serviceSelector =
                new RandomServiceSelector(serviceInvokers, retryTimes, targetMethodmethod, targetArgs);
            return serviceSelector.invoke(errorExit);
        }
    }

    /**
     *
     */
    public Object getObject() throws Exception {

        // 生成一个执行代理
        return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {serviceInterface}, new SelectorProxy());
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
            throw new IllegalArgumentException("'serviceInterface' must be an interface");
        }
        this.serviceInterface = serviceInterface;
    }

    public String[] getServices() {
        return services;
    }

    public void setServices(String[] services) {
        if (services == null || services.length < 1) {
            throw new IllegalArgumentException("'services' must be an nonempty array");
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

        if (servers == null || servers.length < 1) {

            throw new IllegalArgumentException("either servers or services is required!");
        }
        services = new String[servers.length];
        for (int i = 0; i < servers.length; i++) {
            services[i] = protocol + servers[i] + serviceUrl;
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

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return
     */
    private RpcProxyBase getRpcProxyBase(String curServiceUrl) {

        ProtocolEnum protocolEnum = ProtocolEnum.getByName(codecType);
        if (protocolEnum == null) {
            // 默认使用 gson
            protocolEnum = ProtocolEnum.GSON;
        }

        if (protocolEnum.equals(ProtocolEnum.GSON)) {

            return RpcProxyFactory.getGsonRpcProxy(curServiceUrl, encoding, userName, password);
        } else {

            return RpcProxyFactory.getProtostuffRpc(curServiceUrl, encoding, userName, password);
        }
    }

    public String getCodecType() {
        return codecType;
    }

    public void setCodecType(String codecType) {
        this.codecType = codecType;
    }

}
