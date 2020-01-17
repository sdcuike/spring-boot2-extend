package com.sdcuike.extend.dynamic.datasource.config;

import com.sdcuike.extend.dynamic.datasource.DynamicDataSource;
import com.sdcuike.extend.dynamic.datasource.annotation.DS;
import com.sdcuike.extend.dynamic.datasource.annotation.DSAnnotationAdvisor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * mybatis 参考org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration，定制了注解扫描
 *
 * @author sdcuike
 * @DATE 2020/1/13
 */
@EnableTransactionManagement(order = Ordered.LOWEST_PRECEDENCE - 1)
@Configuration
public class DynamicDatasourceAutoConfigure {

    @Bean("dsAnnotationAdvisor")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor dsAnnotationAdvisor() {
        Set<Class<? extends Annotation>> annotationTypes = new HashSet<>();
        annotationTypes.add(DS.class);
        return new DSAnnotationAdvisor(Ordered.LOWEST_PRECEDENCE - 10);
    }

    @Bean("dynamicDataSource")
    @Primary
    public DataSource dynamicDataSource(@Autowired Map<String, DataSource> targetDataSources) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        targetDataSources.remove("dynamicDataSource");
        dynamicDataSource.setTargetDataSources(new HashMap<>(targetDataSources));
        return dynamicDataSource;
    }

    @ConditionalOnClass(MapperScan.class)
    @MapperScan(basePackages = "com.sdcuike", annotationClass = DS.class)
    @Configuration
    public class MybatisConfiguration{

    }
}
