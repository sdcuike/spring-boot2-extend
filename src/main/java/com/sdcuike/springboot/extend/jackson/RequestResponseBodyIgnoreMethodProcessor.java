package com.sdcuike.springboot.extend.jackson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

/**
 * @author sdcuike
 * @date 2019/10/13
 */
final class RequestResponseBodyIgnoreMethodProcessor extends RequestResponseBodyMethodProcessor {

    @Autowired
    public RequestResponseBodyIgnoreMethodProcessor(@Qualifier("customWebMappingJackson2HttpMessageConverter") List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(EnableJacksonExtend.class);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasMethodAnnotation(EnableJacksonExtend.class);
    }
}
