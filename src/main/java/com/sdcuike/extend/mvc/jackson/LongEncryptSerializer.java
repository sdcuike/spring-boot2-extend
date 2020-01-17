package com.sdcuike.extend.mvc.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sdcuike.extend.mvc.jackson.annotation.EncryptValue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * @author sdcuike
 * @DATE 2019/10/18
 */
class LongEncryptSerializer extends StdSerializer<Long> implements ContextualSerializer {
    protected EncryptValue encryptValue;

    protected LongEncryptSerializer(Class<Long> t) {
        super(t);
    }

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        if (encryptValue == null) {
            gen.writeNumber(value);
            return;
        }

        String encode = Base64.getEncoder().encodeToString(value.toString().getBytes(StandardCharsets.UTF_8));
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

        LongEncryptSerializer encryptSerializer = new LongEncryptSerializer(Long.class);
        encryptSerializer.encryptValue = encryptValue;
        return encryptSerializer;
    }
}
