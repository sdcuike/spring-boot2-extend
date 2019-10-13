package com.sdcuike.springboot.service;

import com.sdcuike.springboot.domain.Customer;

/**
 * @author sdcuike
 * @date 2019-09-03
 */
public interface CustomerService {

    Customer get(Integer customernumber);

    boolean insert(Customer customer);

}
