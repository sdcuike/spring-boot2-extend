package com.sdcuike.springboot.extend.ignore;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sdcuike
 * @date 2019/10/13
 */
@Configuration
class CustomWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private ObjectMapper objectMapper;

    static RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Autowired
    public void setRequestMappingHandlerAdapter(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        CustomWebMvcConfigurer.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
    }


    @Override

    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        ObjectMapper mapper = objectMapper.copy();
        mapper.registerModule(new SimpleModule() {
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
        });
        converters.add(new CustomWebMappingJackson2HttpMessageConverter(mapper));
        RequestResponseBodyIgnoreMethodProcessor processor = new RequestResponseBodyIgnoreMethodProcessor(converters);
        handlers.add(processor);
    }

}
