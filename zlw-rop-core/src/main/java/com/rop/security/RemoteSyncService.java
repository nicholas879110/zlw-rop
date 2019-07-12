package com.rop.security;


/**
 * 远程同步数据服务接口
 *
 *
 * @author fukui
 * @date 2018年1月11日14:03:13
 *
 */
public interface RemoteSyncService <T>{


    /**
     * 通过APP Code从远程服务器拉取对应配置信息
     * @param appCode 应用代码
     * <p>
     * 1、通过appCode从远端服务器同步秘钥对(appkey:appSecret,paramsKey:paramsSecret),以Map<String,String>数据格式返回
     * 2、通过appCode从远端服务器同步appKey对应的权限配置信息,以Map<String,Set<String>>数据格式返回
     * <p/>
     */
    T sync(String appCode);


}
