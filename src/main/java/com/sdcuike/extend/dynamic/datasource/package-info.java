/**
 * 动态数据源切换:
 * (1)mybatis 的mapper接口请使用注解com.sdcuike.extend.dynamic.datasource.annotation.DS
 *（2）如果用了事务注解@Transactional，请和注解com.sdcuike.extend.dynamic.datasource.annotation.DS一起使用，以指定数据源
 *
 * @author sdcuike
 * @DATE 2020/1/8
 */
package com.sdcuike.extend.dynamic.datasource;