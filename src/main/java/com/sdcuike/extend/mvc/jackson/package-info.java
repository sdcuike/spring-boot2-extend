/**
 * 本包主要实现场景：数据脱敏处理，对DTO对象中的某些属性忽略序列化，为什么没用 com.fasterxml.jackson.annotation.JsonIgnore,
 * 因为项目中有很多DTO被多个接口复用，有的接口需要将这些属性忽略，而有的接口需要加密或其他脱敏处理，故发生了我的改造需求了
 *
 * @author sdcuike
 * @date 2019/10/15
 */
package com.sdcuike.extend.mvc.jackson;