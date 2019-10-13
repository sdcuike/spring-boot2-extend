package com.sdcuike.springboot.dao;

import com.sdcuike.springboot.SpringBootPracticeApplication;
import com.sdcuike.springboot.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author sdcuike
 * @date 2019-08-28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootPracticeApplication.class})
public class CustomerMapperTest {

    @Autowired
    private CustomerMapper customerMapper;

    @Test
    public void selectByPrimaryKey() {
        Customer customer = customerMapper.selectByPrimaryKey(112);
        System.out.println(customer);
    }
}