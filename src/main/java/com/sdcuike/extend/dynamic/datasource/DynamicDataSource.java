package com.sdcuike.extend.dynamic.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Collections;
import java.util.Map;

/**
 * @author sdcuike
 * @DATE 2020/1/8
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private Map<Object, Object> targetDataSources;

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceKeyHolder.getDataSourceKey();
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        this.targetDataSources = targetDataSources;
        super.setTargetDataSources(targetDataSources);
    }

    public Map<Object, Object> getTargetDataSources() {
        return Collections.unmodifiableMap(targetDataSources);
    }


}
