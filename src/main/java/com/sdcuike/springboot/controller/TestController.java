package com.sdcuike.springboot.controller;

import com.sdcuike.extend.dynamic.datasource.annotation.DS;
import com.sdcuike.springboot.config.DataSourceConfig;
import com.sdcuike.springboot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sdcuike
 * @date 2019-08-19
 */
@RestController
public class TestController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/test")
    @DS(DataSourceConfig.DEFAULT_DS)
    @Transactional
    public Object test() {
        return customerService.get(103);
    }


}
