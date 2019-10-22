package com.sdcuike.springboot.extend.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * @author sdcuike
 * @DATE 2019/10/17
 */
@JacksonStdImpl
class StringDecryptDeserializer extends StdScalarDeserializer<String> implements ContextualDeserializer {
    protected DecryptToStringValue decryptToStringValue;

    protected StringDecryptDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        JsonNodeType nodeType = node.getNodeType();
        if (nodeType == JsonNodeType.STRING) {
            String value = node.asText();
            byte[] decode = Base64.getDecoder().decode(value.getBytes(StandardCharsets.UTF_8));
            return new String(decode, StandardCharsets.UTF_8);
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        DecryptToStringValue annotation = property.getAnnotation(DecryptToStringValue.class);
        if (Objects.isNull(annotation)) {
            annotation = property.getContextAnnotation(DecryptToStringValue.class);
        }
        if (Objects.nonNull(annotation)) {
            StringDecryptDeserializer stringDecryptDeserializer = new StringDecryptDeserializer();
            stringDecryptDeserializer.decryptToStringValue = annotation;
            return stringDecryptDeserializer;
        }

        return new StringDeserializer();
    }
}
