package com.sdcuike.springboot.extend.ignore;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author sdcuike
 * @DATE 2019/10/17
 */
@JacksonStdImpl
class StringDecryptDeserializer extends StdScalarDeserializer<String> {

    protected StringDecryptDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
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
}
