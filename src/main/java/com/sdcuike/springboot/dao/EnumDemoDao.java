package com.sdcuike.springboot.dao;

import com.sdcuike.springboot.domain.EnumDemo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EnumDemoDao {

    int insert(EnumDemo record);

    List<EnumDemo> select();
}