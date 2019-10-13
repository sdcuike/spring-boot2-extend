package com.sdcuike.springboot.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sdcuike
 * @date 2019/10/12
 */
@RestController
@RequestMapping("/json-view")
public class JsonViewTestController {

    @GetMapping("/test")
    public Response<JsonViewObjectDTO> testNoJsonView() {
        Response<JsonViewObjectDTO> response = new Response<>();
        JsonViewObjectDTO dto = new JsonViewObjectDTO();
        response.setData(dto);
        dto.setId(1L);
        dto.setUserName("sdcuike");
        return response;
    }

    @GetMapping("/test2")
    public Response<JsonViewObject2DTO> testJsonView() {
        Response<JsonViewObject2DTO> response = new Response<>();
        JsonViewObject2DTO dto = new JsonViewObject2DTO();
        response.setData(dto);
        dto.setId(1L);
        dto.setUserName("sdcuike");
        return response;
    }

    @GetMapping("/test3")
    @JsonView(JsonViewProfile.Test.class)
    public Response<JsonViewObject2DTO> testHaveJsonView() {
        Response<JsonViewObject2DTO> response = new Response<>();
        JsonViewObject2DTO dto = new JsonViewObject2DTO();
        response.setData(dto);
        dto.setId(1L);
        dto.setUserName("sdcuike");
        return response;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JsonViewObjectDTO {
        private Long id;
        private String userName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JsonViewObject2DTO {
        private Long id;
        @JsonView(JsonViewProfile.Test.class)
        private String userName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response<T> {
        @JsonView(JsonViewProfile.All.class)
        private int code;

        @JsonView(JsonViewProfile.All.class)
        private String msg;

        //        @JsonView(JsonViewProfile.All.class)
        private T data;

    }

    public static class JsonViewProfile {
        public interface All {
        }

        public interface Test extends All {
        }

    }
}
