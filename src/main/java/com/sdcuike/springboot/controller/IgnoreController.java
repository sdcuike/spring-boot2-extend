package com.sdcuike.springboot.controller;

import com.sdcuike.springboot.extend.ignore.Ignore;
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
@RequestMapping("/ignore-view")
public class IgnoreController {

    @GetMapping("/test")
    @Ignore
    public Response<IgnoreDto> testNoJsonView() {
        Response<IgnoreDto> dtoResponse = new Response<>();
        IgnoreDto dto = new IgnoreDto(1L, "sdcuike");
        dtoResponse.setData(dto);
        return dtoResponse;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response<T> {
        private int code;

        private String msg;

        private T data;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IgnoreDto {
        private Long id;
        @Ignore
        private String userName;
    }
}
