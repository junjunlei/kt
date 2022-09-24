package com.jerry.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jerry.common.enums.CommonErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author jerry
 * @Description 数据统一返回实体类
 * @Date 2022-09-24 22:22
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> implements Serializable {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 操作编码
     */
    private String code;

    /**
     * 操作消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 时间戳
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;


    public static <T> Result<T> success() {
        return new Result<T>(true, "200", "操作成功!!!", null, new Date());
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(true, "200", "操作成功!!!", data, new Date());
    }

    public static <T> Result<T> fail(String code, String message) {
        return new Result<T>(true, code, message, null, new Date());
    }

    public static <T> Result<T> fail(CommonErrorCode commonErrorCode) {
        return new Result<T>(true, commonErrorCode.getCode(), commonErrorCode.getMessage(), null, new Date());
    }
}
