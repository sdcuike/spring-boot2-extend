package com.sdcuike.springboot.extend.ignore;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sdcuike
 * @date 2019/10/13
 */
@Component
final class RequestResponseBodyIgnoreMethodProcessor extends RequestResponseBodyMethodProcessor implements InitializingBean {
    public RequestResponseBodyIgnoreMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(Ignore.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RequestMappingHandlerAdapter handlerAdapter = CustomWebMvcConfigurer.requestMappingHandlerAdapter;
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>(handlerAdapter.getReturnValueHandlers());

        int requestResponseBodyMethodProcessorIndex = 0;
        int requestResponseBodyIgnoreMethodProcessorIndex = 0;
        RequestResponseBodyIgnoreMethodProcessor requestDecryptResponseEncryptBodyMethodProcessor = null;
        for (int i = 0, length = handlers.size(); i < length; i++) {
            HandlerMethodReturnValueHandler handler = handlers.get(i);
            if (handler instanceof RequestResponseBodyIgnoreMethodProcessor) {
                requestDecryptResponseEncryptBodyMethodProcessor = (RequestResponseBodyIgnoreMethodProcessor) handler;
                requestResponseBodyIgnoreMethodProcessorIndex = i;
            } else if (handler instanceof RequestResponseBodyMethodProcessor) {
                requestResponseBodyMethodProcessorIndex = i;
            }

        }

        if (requestDecryptResponseEncryptBodyMethodProcessor != null) {
            handlers.add(requestResponseBodyMethodProcessorIndex, requestDecryptResponseEncryptBodyMethodProcessor);
            handlers.remove(requestResponseBodyIgnoreMethodProcessorIndex + 1);
        }

        handlerAdapter.setReturnValueHandlers(handlers);

    }
}
