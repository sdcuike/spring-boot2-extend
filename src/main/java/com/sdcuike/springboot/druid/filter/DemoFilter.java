package com.sdcuike.springboot.druid.filter;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import org.springframework.stereotype.Component;

/**
 * @author sdcuike
 * @date 2019-08-25
 */
@Component
public class DemoFilter extends FilterEventAdapter {
    @Override
    public void init(DataSourceProxy dataSource) {
        super.init(dataSource);
    }
}
