package com.sdcuike.springboot.controller;

import com.sdcuike.springboot.extend.jackson.*;
import com.sdcuike.springboot.model.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author sdcuike
 * @DATE 2019/10/17
 */
@RestController
@RequestMapping("/jackson-encrypt")
public class EncryptDecryptTestController {

    @GetMapping("/encrypt")
    @EnableJacksonExtend
    public Response<EncryptSerializerTest> testEncryptSerializer() {
        Response<EncryptSerializerTest> dtoResponse = new Response<>();
        EncryptSerializerTest test = new EncryptSerializerTest(1L, "sdcuike", 17899989898L, "Gallifrey星球", UUID.randomUUID().toString());
        dtoResponse.setData(test);
        return dtoResponse;
    }

    /**
     * 测试用例：加密的字段：不为空、不传属性、空字符串、空指针null
     * POST http://127.0.0.1:8080/jackson-encrypt/decrypt
     * Content-Type: application/json
     * <p>
     * {
     * "id": 1,
     * "name": "sdcuike",
     * "phone": "MTc4OTk5ODk4OTg=",
     * "address": "R2FsbGlmcmV55pif55CD",
     * "uuid": "fda9636c-3d03-432a-ba16-f7bb9ab1b65f"
     * }
     *
     * @param dto
     * @return
     */
    @PostMapping("/decrypt")
    @EnableJacksonExtend
    public Response<Boolean> testDecrypt(@RequestBody EncryptSerializerTest dto) {
        Response<Boolean> response = new Response<>();
        response.setData(Boolean.TRUE);
        return response;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class EncryptSerializerTest {
        @JacksonIgnore
        private Long id;

        private String name;

        @EncryptValue
        @DecryptToLongValue
        private Long phone;

        @EncryptValue
        @DecryptToStringValue
        private String address;


        private String uuid;
    }
}
