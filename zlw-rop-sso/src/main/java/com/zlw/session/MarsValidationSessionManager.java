package com.zlw.session;



import com.rop.session.Session;
import com.rop.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 此类只用于验证令牌（token)，不支持注册session 删除session功能
 *
 *
 * @author fukui
 * @date 2017-11-2
 */
public class MarsValidationSessionManager implements SessionManager{

    public static final String MAX_INTERVAL_SECONDS = "maxIntervalSeconds";
    public static final String CREATE_TIME = "createTime";
    public static final String UID = "uid";
    public static final String EMP_NO = "empNo";
    public static final String SESSION_ID = "sessionId";
    public static final String AD_ACCOUNT = "adAccount";
    public static final String AD_ACCOUNT1 = "adAccount";
    public static final String AP_ACCOUNT = "apAccount";
    public static final String COM_CODE = "comCode";
    public static final String EMP_NAME = "empName";

    private Logger logger = LoggerFactory.getLogger(MarsValidationSessionManager.class);


//    private AuthenticationManager authenticationManager ;
//
//    public MarsValidationSessionManager(){
//
//    }
//
//    public MarsValidationSessionManager(AuthenticationManager authenticationManager){
//        this.authenticationManager = authenticationManager;
//    }


    /**
     * 注册一个会话
     *
     * @param sessionId
     * @param session
     */
    @Override
    public void addSession(String sessionId, Session session) {
        logger.error("MarsValidationSessionManager is not support 'addSession' ");
    }

    /**
     * 从注册表中获取会话
     *
     * @param sessionId
     * @return
     */
    @Override
    public Session getSession(String sessionId) {
        if( sessionId == null || sessionId.trim().equals("") ){
            logger.error("sessionId is empty!");
            return null;
        }
//        CustomerAuthenticationManager manager = null;
//
//        try {
//            ResultData<ExpiringToken> result =  authenticationManager.isAuthenticated( sessionId );
//            if( ResultData.isSuccess(result) ){
//                ExpiringToken ssoSession = result.getData();
//                if( ssoSession != null ){
//                    return createSession(sessionId, /*ssoSession*/null);
//                }
//            }
//        }catch (Exception e){
//            logger.error("SSO验证sessionID失败:",e);
//        }
        return null;
    }



    /**
     * 移除这个会话
     *
     * @param sessionId
     * @return
     */
    @Override
    public void removeSession(String sessionId) {
        logger.error("MarsValidationSessionManager is not support 'removeSession' ");
    }

//    public AuthenticationManager getAuthenticationManager() {
//        return authenticationManager;
//    }
//
//    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }


}
