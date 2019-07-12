package com.rop.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 方法级别权限验证，针对不同的APPKey认证是否有权限访问当前访问接口/api（方法）
 * APP-->api和APPKey的权限映射关系可以由数据库等存储
 * <p/>
 *
 * @author fukui
 * @date 2018年1月4日16:21:45
 */
public class RemoteMethodAuthorizationManager implements MethodAuthorizationManager {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *应用程序名称，需要与配置权限地方的应用代码匹配,对当前系统是一个固定常亮
     */
    private String appCode;

    /**
     * 是否开启定时同步
     */
    private boolean sync;

    /**
     * 同步时间间隔 默认10s
     */
    private int interval ;

    /**
     * 将当前APP的所有资源及资源对应的appkey访问权限缓存本地  map ---> appkey : method.version
     */
    private Map<String, Set<String>> appkeyResources = new ConcurrentHashMap<>();


    public RemoteMethodAuthorizationManager() {

    }

    public RemoteMethodAuthorizationManager(String appCode) {
        this.appCode = appCode;
    }

    /**
     * 验证APP是否有权限调用服务方法
     *
     * @param appKey
     * @param methodName
     * @param version
     * @return
     */
    @Override
    public boolean isAuthorized(String appKey, String methodName, String version) {
        String method = methodName + "." + version;

        if (appkeyResources.get(appKey) == null) {
            logger.error("appkey:{}未在权限系统中授权，不能够访问{}的所有资源", appKey, appCode);
            return Boolean.FALSE;
        }

        if (appkeyResources.get(appKey).contains(method)) {
            return Boolean.TRUE;
        }

        logger.error("appkey:{}没有权限访问{}资源", appKey, (methodName + "." + appCode));
        return Boolean.FALSE;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

}