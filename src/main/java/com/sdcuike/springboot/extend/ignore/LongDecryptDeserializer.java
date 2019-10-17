package com.sdcuike.springboot.extend.ignore;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author sdcuike
 * @DATE 2019/10/17
 */
@JacksonStdImpl
class LongDecryptDeserializer extends StdScalarDeserializer<Long> {
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
}
