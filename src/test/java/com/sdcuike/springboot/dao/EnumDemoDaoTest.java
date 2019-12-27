package com.sdcuike.springboot.dao;

import com.sdcuike.springboot.SpringBootPracticeApplication;
import com.sdcuike.springboot.domain.Demo1Enum;
import com.sdcuike.springboot.domain.Demo2Enum;
import com.sdcuike.springboot.domain.EnumDemo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sdcuike
 * @DATE 2019/12/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootPracticeApplication.class})
class EnumDemoDaoTest {

    @Autowired
    private EnumDemoDao enumDemoDao;
    @Test
    void insert() {
        enumDemoDao.insert(new EnumDemo(Demo1Enum.Hello, Demo2Enum.Hello2));
        List<EnumDemo> select = enumDemoDao.select();
        System.out.println("=================="+select);
    }
}