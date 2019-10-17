package com.sdcuike.springboot.extend.ignore;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author sdcuike
 * @DATE 2019/10/17
 */
@JacksonStdImpl
class EncryptSerializer extends StdSerializer<Object> {
    EncryptSerializer() {
        super(Object.class);
    }


    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeObject(value);
            return;
        }
        String encode = Base64.getEncoder().encodeToString(value.toString().getBytes(StandardCharsets.UTF_8));
        gen.writeObject(encode);
    }
}
