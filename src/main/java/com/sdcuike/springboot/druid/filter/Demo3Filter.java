package com.sdcuike.springboot.druid.filter;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;

/**
 * @author sdcuike
 * @date 2019-08-25
 */
public class Demo3Filter extends FilterEventAdapter {
    @Override
    public void init(DataSourceProxy dataSource) {
        super.init(dataSource);
    }
}