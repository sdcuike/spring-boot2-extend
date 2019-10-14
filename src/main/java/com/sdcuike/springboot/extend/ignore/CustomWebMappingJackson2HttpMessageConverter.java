package com.sdcuike.springboot.extend.ignore;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author sdcuike
 * @date 2019/10/15
 */
final class CustomWebMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public CustomWebMappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return true;
    }
}
