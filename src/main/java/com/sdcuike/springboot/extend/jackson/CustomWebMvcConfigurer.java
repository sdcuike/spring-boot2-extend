package com.sdcuike.springboot.extend.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sdcuike
 * @date 2019/10/13
 */
@Configuration
class CustomWebMvcConfigurer implements WebMvcConfigurer, InitializingBean {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        initRequestResponseBodyIgnoreMethodProcessor();
        handlers.add(processor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        initRequestResponseBodyIgnoreMethodProcessor();
        resolvers.add(processor);
    }

    @Override
    public void afterPropertiesSet() {
        //返回处理handlers排序
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

        //参数处理排序
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>(handlerAdapter.getArgumentResolvers());
        requestResponseBodyMethodProcessorIndex = 0;
        requestResponseBodyIgnoreMethodProcessorIndex = 0;
        requestDecryptResponseEncryptBodyMethodProcessor = null;
        for (int i = 0, length = argumentResolvers.size(); i < length; i++) {
            HandlerMethodArgumentResolver argumentResolver = argumentResolvers.get(i);
            if (argumentResolver instanceof RequestResponseBodyIgnoreMethodProcessor) {
                requestResponseBodyIgnoreMethodProcessorIndex = i;
                requestDecryptResponseEncryptBodyMethodProcessor = (RequestResponseBodyIgnoreMethodProcessor) argumentResolver;
            } else if (argumentResolver instanceof RequestResponseBodyMethodProcessor) {
                requestResponseBodyMethodProcessorIndex = i;
            }
        }

        if (requestDecryptResponseEncryptBodyMethodProcessor != null) {
            argumentResolvers.add(requestResponseBodyMethodProcessorIndex, requestDecryptResponseEncryptBodyMethodProcessor);
            argumentResolvers.remove(requestResponseBodyIgnoreMethodProcessorIndex + 1);
        }

        handlerAdapter.setArgumentResolvers(argumentResolvers);
    }

    private synchronized void initRequestResponseBodyIgnoreMethodProcessor() {
        if (Objects.nonNull(processor)) {
            return;
        }

        ObjectMapper mapper = objectMapper.copy();
        SimpleModule simpleModule = new SimpleModule() {
            @Override
            public void setupModule(SetupContext context) {
                super.setupModule(context);
                context.addBeanSerializerModifier(new BeanSerializerModifier() {
                    @Override
                    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {

                        if (Objects.isNull(beanProperties)) {
                            return beanProperties;
                        }

                        return beanProperties.stream().filter(t -> Objects.isNull(t.findAnnotation(JacksonIgnore.class))).collect(Collectors.toList());
                    }
                });
            }
        };

        simpleModule.addSerializer(String.class, new StringEncryptSerializer(String.class));
        simpleModule.addSerializer(Long.class, new LongEncryptSerializer(Long.class));
        simpleModule.addSerializer(Long.TYPE, new LongEncryptSerializer(Long.class));
        simpleModule.addDeserializer(Long.class, new LongDecryptDeserializer(Long.class));
        simpleModule.addDeserializer(Long.TYPE, new LongDecryptDeserializer(Long.class));
        simpleModule.addDeserializer(String.class, new StringDecryptDeserializer());

        mapper.registerModule(simpleModule);
        List<HttpMessageConverter<?>> converters = List.of(new CustomWebMappingJackson2HttpMessageConverter(mapper));

        processor = new RequestResponseBodyIgnoreMethodProcessor(converters);
    }

    private RequestResponseBodyIgnoreMethodProcessor processor;
}
