package com.sdcuike.springboot.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author sdcuike
 * @date 2019-08-25
 */
@Configuration
public class DataSourceConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean(name = "dataSource")
    @ConfigurationProperties("spring.datasource.sdcuike")
    public DataSource dataSource() {
        logger.info("Init DruidDataSource");
        return DruidDataSourceBuilder.create().build();
    }
}
