package com.rop.security;


public interface MethodAuthorizationManager {
    /**
     * 验证APP是否有权限调用服务方法
     *
     * @param appKey
     * @param methodName
     * @param version
     * @return
     */
    boolean isAuthorized(String appKey, String methodName, String version);
}
