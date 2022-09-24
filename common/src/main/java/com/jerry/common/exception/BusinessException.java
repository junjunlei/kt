package com.jerry.common.exception;

import com.jerry.common.enums.CommonErrorCode;
import lombok.Data;

/**
 * @Author jerry
 * @Description 统一业务异常
 * @Date 2022-09-24 23:11
 * @Version 1.0
 **/
@Data
public class BusinessException extends RuntimeException {

    private String code;

    /**
     * 使用CommonErrorCode枚举传参
     *
     * @param errorCode 异常枚举
     */
    public BusinessException(CommonErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    /**
     * 使用自定义消息
     *
     * @param code 值
     * @param msg  详情
     */
    public BusinessException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
