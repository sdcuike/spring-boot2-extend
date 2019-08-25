package com.sdcuike.springboot.dao;


import com.sdcuike.springboot.domain.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    int deleteByPrimaryKey(Integer customernumber);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer customernumber);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
}