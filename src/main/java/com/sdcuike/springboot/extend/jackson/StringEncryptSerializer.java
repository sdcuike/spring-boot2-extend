package com.sdcuike.springboot.extend.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * @author sdcuike
 * @DATE 2019/10/17
 */
@JacksonStdImpl
class StringEncryptSerializer extends StdSerializer<String> implements ContextualSerializer {

    private EncryptValue encryptValue;

    protected StringEncryptSerializer(Class<String> t) {
        super(t);
    }


    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        if (encryptValue == null) {
            gen.writeString(value);
            return;
        }

        String encode = Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
        gen.writeString(encode);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property == null) {
            return prov.findNullValueSerializer(property);
        }

        encryptValue = property.getContextAnnotation(EncryptValue.class);
        if (Objects.isNull(encryptValue)) {
            encryptValue = property.getAnnotation(EncryptValue.class);
        }

        StringEncryptSerializer encryptSerializer = new StringEncryptSerializer(String.class);
        encryptSerializer.encryptValue = encryptValue;
        return encryptSerializer;
    }
}
