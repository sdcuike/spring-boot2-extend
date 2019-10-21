package com.sdcuike.springboot.controller;

import com.sdcuike.springboot.extend.jackson.JacksonIgnore;
import com.sdcuike.springboot.model.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sdcuike
 * @date 2019/10/13
 */
@RestController
@RequestMapping("/jackson-ignore")
public class JacksonIgnoreTestController {

    @GetMapping("/test")
    public Response<IgnoreDto> testNoJsonView() {
        Response<IgnoreDto> dtoResponse = new Response<>();
        Person person = new Person(11L, "doctor who");
        IgnoreDto dto = new IgnoreDto(1L, "sdcuike", person);
        dtoResponse.setData(dto);
        return dtoResponse;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IgnoreDto {
        private Long id;

        @JacksonIgnore
        private String userName;

        private Person person;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Person {
        private Long id;

        @JacksonIgnore
        private String userName;
    }
}
