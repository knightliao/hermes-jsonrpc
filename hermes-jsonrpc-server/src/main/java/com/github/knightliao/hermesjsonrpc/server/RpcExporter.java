package com.github.knightliao.hermesjsonrpc.server;

import java.util.HashSet;
import java.util.Set;

/**
 * 用于在spring配置文件里指定服务的辅助类
 * 
 * 
 */
public class RpcExporter {

    // 接口
    private String serviceInterface;

    // 处理类
    private Object serviceBean;

    // 用户名&密码
    private String userName = "";
    private String password = "";

    // IP白名单
    private Set<String> ipSet = new HashSet<String>();

    /**
     * 获取对外服务的接口名称
     * 
     * @return 对外服务的接口名
     */
    public String getServiceInterfaceName() {
        return serviceInterface;
    }

    /**
     * 获取对外服务的接口类型
     * 
     * @return 对外服务的接口类型
     * @throws ClassNotFoundException
     */
    public Class<?> getServiceInterface() throws ClassNotFoundException {
        return Class.forName(serviceInterface);
    }

    /**
     * 设置对外服务的接口名称
     * 
     * @param serviceInterface
     */
    public void setServiceInterfaceName(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    /**
     * 获取对外服务的Java Bean
     * 
     * @return 对外服务的对象
     */
    public Object getServiceBean() {
        return serviceBean;
    }

    /**
     * 设置对外服务的Java Bean
     * 
     * @param serviceBean
     */
    public void setServiceBean(Object serviceBean) {
        this.serviceBean = serviceBean;
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

    public Set<String> getIpSet() {
        return ipSet;
    }

    public void setIpSet(Set<String> ipSet) {
        this.ipSet = ipSet;
    }

    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

}
