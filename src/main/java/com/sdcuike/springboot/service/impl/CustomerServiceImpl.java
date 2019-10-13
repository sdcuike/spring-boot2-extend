package com.sdcuike.springboot.service.impl;

import com.sdcuike.springboot.dao.CustomerMapper;
import com.sdcuike.springboot.domain.Customer;
import com.sdcuike.springboot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sdcuike
 * @date 2019-09-03
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Transactional
    @Override
    public Customer get(Integer customernumber) {
        return customerMapper.selectByPrimaryKey(customernumber);
    }

    @Override
    public boolean insert(Customer customer) {
        int insert = customerMapper.insert(customer);
        return insert > 0;
    }
}
