package com.sdcuike.springboot.service;

import com.sdcuike.springboot.SpringBootPracticeApplication;
import com.sdcuike.springboot.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author sdcuike
 * @date 2019-09-03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootPracticeApplication.class})
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void insert() {
        Customer customer = customerService.get(110);
        System.out.println(customer);
    }
}