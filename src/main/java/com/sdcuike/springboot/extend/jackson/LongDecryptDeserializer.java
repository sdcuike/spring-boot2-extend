package com.sdcuike.springboot.extend.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * @author sdcuike
 * @DATE 2019/10/17
 */
@JacksonStdImpl
class LongDecryptDeserializer extends StdScalarDeserializer<Long> implements ContextualDeserializer {
    protected DecryptToLongValue decryptToLongValue;

    protected LongDecryptDeserializer() {
        super(Long.class);
    }

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        JsonNodeType nodeType = node.getNodeType();
        if (nodeType == JsonNodeType.STRING) {
            String value = node.asText();
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            byte[] decode = Base64.getDecoder().decode(value.getBytes(StandardCharsets.UTF_8));
            String v = new String(decode, StandardCharsets.UTF_8);
            return Long.valueOf(v);
        }

        throw new UnsupportedOperationException();
    }


    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctx, BeanProperty property) throws JsonMappingException {
        DecryptToLongValue annotation = property.getAnnotation(DecryptToLongValue.class);
        if (Objects.isNull(annotation)) {
            annotation = property.getContextAnnotation(DecryptToLongValue.class);
        }
        if (Objects.nonNull(annotation)) {
            LongDecryptDeserializer longDecryptDeserializer = new LongDecryptDeserializer();
            longDecryptDeserializer.decryptToLongValue = annotation;
            return longDecryptDeserializer;
        }

        if (property.getType().isPrimitive()) {
            return primitiveInstance;
        }
        return wrapperInstance;
    }

    final static NumberDeserializers.LongDeserializer primitiveInstance = new NumberDeserializers.LongDeserializer(Long.TYPE, 0L);
    final static NumberDeserializers.LongDeserializer wrapperInstance = new NumberDeserializers.LongDeserializer(Long.class, null);
}
