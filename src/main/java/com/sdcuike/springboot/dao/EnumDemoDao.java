package com.sdcuike.springboot.dao;

import com.sdcuike.extend.dynamic.datasource.annotation.DS;
import com.sdcuike.springboot.domain.EnumDemo;

import java.util.List;

@DS("dataSource")
public interface EnumDemoDao {

    int insert(EnumDemo record);

    List<EnumDemo> select();
}