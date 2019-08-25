package com.sdcuike.springboot.controller;

import com.sdcuike.springboot.dao.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sdcuike
 * @date 2019-08-19
 */
@RestController
public class TestController {
    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping("/test")
    public Object test(){
        return customerMapper.selectByPrimaryKey(103);
    }
}
