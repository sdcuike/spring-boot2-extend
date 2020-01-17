package com.sdcuike.extend.dynamic.datasource.annotation;

import com.sdcuike.extend.dynamic.datasource.config.DynamicDatasourceAutoConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 动态数据源功能，mybatis的mapper需要使用注解
 * com.sdcuike.extend.dynamic.datasource.annotation.DS
 *
 * @author sdcuike
 * @DATE 2020/1/13
 * @see com.sdcuike.extend.dynamic.datasource.annotation.DS
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DynamicDatasourceAutoConfigure.class)
public @interface EnableDynamicDatasource {
}
