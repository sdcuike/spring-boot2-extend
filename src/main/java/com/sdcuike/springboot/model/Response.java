package com.sdcuike.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sdcuike
 * @DATE 2019/10/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private int code;

    private String msg;

    private T data;

}
