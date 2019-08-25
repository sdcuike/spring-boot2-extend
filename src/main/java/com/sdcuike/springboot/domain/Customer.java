package com.sdcuike.springboot.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Customer {
    private Integer customernumber;

    private String customername;

    private String contactlastname;

    private String contactfirstname;

    private String phone;

    private String addressline1;

    private String addressline2;

    private String city;

    private String state;

    private String postalcode;

    private String country;

    private Integer salesrepemployeenumber;

    private BigDecimal creditlimit;

}