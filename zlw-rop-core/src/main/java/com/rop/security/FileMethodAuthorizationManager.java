package com.rop.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;


public class FileMethodAuthorizationManager implements MethodAuthorizationManager {
    private static final String ROP_APP_METHOD_AUTHORIZATION_PROPERTIES = "rop.method.authorization.properties";
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private String methodAuthorizationProperties = ROP_APP_METHOD_AUTHORIZATION_PROPERTIES;
    private Properties properties;
    private boolean fileExists=true;

    @Override
    public boolean isAuthorized(String appKey, String methodName, String version) {
        if (fileExists) {
            if (properties == null) {
                try {
                    DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
                    Resource resource = resourceLoader.getResource(methodAuthorizationProperties);
                    properties = PropertiesLoaderUtils.loadProperties(resource);
                } catch (IOException e) {
                    fileExists=false;
                    logger.warn("在类路径下找不到rop.method.authorization.properties的属性文件");
                    return true;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(appKey).append(",").append(methodName).append(",").append(version);
            return "allowed".equalsIgnoreCase((String) properties.get(sb.toString()));
        }else{
            //限制文件不存在，默认所有方法授权访问
            return true;
        }
    }
}
