package com.zlw.rop.model;



import com.zlw.commons.core.ErrorCode;
import com.zlw.commons.core.ResultData;
import org.apache.commons.lang3.StringUtils;

/**
 * ROP统一返回客户端对象
 *
 * @author fukui
 * @date 2016-06-08
 */
public class ResponseModel<T> {

    public static final String SUCCESS = ErrorCode.SUCCESS;// 返回码

    private String code = SUCCESS;// 返回码

    private String message;// 错误描述

    private T data;// 数据对象

    public ResponseModel() {
    }

    public ResponseModel(String code) {
        this.code = code;
        this.message = ErrorCode.SUCCESS_MSG;
    }

    public ResponseModel(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseModel(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseModel<T> newSuccessResponseModel() {
        return new ResponseModel<T>();
    }

    public static <T> ResponseModel<T> newResponseModel(String code) {
        return new ResponseModel<T>(code);
    }

    public static <T> ResponseModel<T> newResponseModel(String code, String message) {
        return new ResponseModel<T>(code, message);
    }

    public static <T> ResponseModel<T> newResponseModel(T data) {
        return new ResponseModel<T>(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, data);
    }

    public static <T> ResponseModel<T> newResponseModel(ResultData<T> resultData) {
        if (resultData == null) {
            return new ResponseModel<>(ErrorCode.FAILOR, ErrorCode.FAILOR_MSG);
        } else {
            return new ResponseModel<>(resultData.getCode(), resultData.getMessage(), resultData.getData());
        }
    }

    public static <T> ResponseModel<T> newResponseModel(String code, String message, T data) {
        return new ResponseModel<T>(code, message, data);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        if (ErrorCode.SUCCESS.equals(code) && StringUtils.isEmpty(message)) {
            message = ErrorCode.SUCCESS_MSG;
        }
        if (message == null) {
            message = ErrorCode.FAILOR_MSG;
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
