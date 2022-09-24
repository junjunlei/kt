package com.jerry.common.advice;

import com.alibaba.fastjson.JSON;
import com.jerry.common.response.Result;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author jerry
 * @Description 全局统一返回处理
 * @Date 2022-09-24 22:18
 * @Version 1.0
 **/
@Configuration
@RestControllerAdvice
public class CommonResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        List<String> adviceFilterPackage = new ArrayList<>();
        adviceFilterPackage.add("springfox.documentation");
        adviceFilterPackage.add("org.springframework");
        Class<?> declaringClass = methodParameter.getDeclaringClass();
        // 检查过滤包路径
        long count = adviceFilterPackage.stream()
                .filter(l -> declaringClass.getName().contains(l)).count();
        if (count > 0) {
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        // 返回值为 Object 类型  并且返回为空是  AbstractMessageConverterMethodProcessor#writeWithMessageConverters 方法
        // 无法触发调用本类的 beforeBodyWrite 处理，开发在 Controller 尽量避免直接使用 Object 类型返回。

        // o is null -> return response
        if (o == null) {
            // 当 o 返回类型为 string 并且为null会出现 java.lang.ClassCastException: Result cannot be cast to java.lang.String
            if (methodParameter.getParameterType().getName().equals("java.lang.String")) {
                return JSON.toJSONString(Result.success());
            }
            return Result.success();
        }
        // o is instanceof  Result-> return o
        if (o instanceof Result) {
            return (Result<Object>) o;
        }
        // string 特殊处理 java.lang.ClassCastException: Result cannot be cast to java.lang.String
        if (o instanceof String) {
            return JSON.toJSONString(Result.success(o));
        }
        return Result.success(o);
    }
}
