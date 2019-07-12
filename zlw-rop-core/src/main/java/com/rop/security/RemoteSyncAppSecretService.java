package com.rop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlw.commons.logging.exception.GlobalLoggerError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;



/**
 * 通过appCode从远端服务器同步秘钥对(appkey:appSecret,paramsKey:paramsSecret),以Map<String,String>数据格式返回
 *
 * @author fukui
 * @date 2018年1月11日15:07:11
 */
public class RemoteSyncAppSecretService implements RemoteSyncService<Map<String,String>>{

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String remoteHost ;

    private RestTemplate restTemplate;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, String> sync(String appCode) {
        if(logger.isDebugEnabled()){
            logger.debug("Synchroniz appCode({}) AppSecret from : {}" ,appCode,remoteHost);
        }

        String resultdata = restTemplate.getForObject(remoteHost , String.class ,appCode);

        if(logger.isDebugEnabled()){
            logger.debug("Remote server response: {}" , resultdata);
        }

        if(resultdata==null){
            return null;
        }

        try {
            Map<String, String> appSecretMap = objectMapper.readValue(resultdata, Map.class);
            return appSecretMap;
        } catch (IOException e) {
            GlobalLoggerError.error(e);
        }
        return null;
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String resultdata = restTemplate.getForObject("www.baidu.com" , String.class);
        objectMapper = new ObjectMapper();
        String json = "{\"0001\":\"fukui\",\"0002\":\"messi\",\"0003\":\"kun\"}";
        try {
            Map<String, String> appSecretMap = objectMapper.readValue(json, Map.class);
            json = "{\"0001\":\"fukui\",\"0002\":\"messi\",\"0003\":\"kun\"}";
            appSecretMap = objectMapper.readValue(json, Map.class);
            json = "{\"10001\":\"fukui\",\"10002\":\"messi\",\"10003\":\"kun\"}";
            appSecretMap = objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
