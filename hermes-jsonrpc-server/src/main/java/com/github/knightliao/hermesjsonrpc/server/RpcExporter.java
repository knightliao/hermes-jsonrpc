package com.github.knightliao.hermesjsonrpc.server;

/**
 * 用于在spring配置文件里指定服务的辅助类
 * 
 * 
 */
public class RpcExporter {

    private String serviceInterface;
    private Object serviceBean;

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
}
