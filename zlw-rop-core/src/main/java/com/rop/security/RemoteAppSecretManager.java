package com.rop.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通过远程数据库服务等存储管理秘钥对
 *
 *
 * @author fukui
 * @dare 2018年1月4日17:47:12
 */
public class RemoteAppSecretManager implements AppSecretManager{


    private final Logger logger = LoggerFactory.getLogger(getClass());


    //应用程序名称，需要与配置权限地方的应用代码匹配,对当前系统是一个固定常亮
    private String appCode;

    //本地缓存appKey对应的加密+签名秘钥对
    private Map<String,String> appKeySecrets = new ConcurrentHashMap<String,String>();


    private RemoteSyncService<Map<String,String>> remoteSyncService;

    public void init(){
        appKeySecrets = remoteSyncService.sync(appCode);
    }


    /**
     * 获取应用程序的密钥
     *
     * @param appKey
     * @return
     */
    @Override
    public String getSecret(String appKey) {
        String secret = appKeySecrets.get(appKey);
        if (secret == null) {
            logger.error("不存在应用键为{}的密钥,请检查应用密钥的配置文件。", appKey);
        }
        return secret;
    }

    /**
     * 是否是合法的appKey
     *
     * @param appKey
     * @return
     */
    @Override
    public boolean isValidAppKey(String appKey) {
        return getSecret(appKey) != null;
    }

}
