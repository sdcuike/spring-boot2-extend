package com.sdcuike.extend.dynamic.datasource.annotation;

import java.lang.annotation.*;

/**
 * mybatis接口类或内部方法上的注解，指定其操作的数据源名
 *
 * @author sdcuike
 * @DATE 2020/1/8
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DS{
    String value();
}
