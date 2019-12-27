package com.sdcuike.springboot.extend.mybatis;

/**
 * 为了mybatis 统一处理枚举，自定义接口
 *
 * @author sdcuike
 * @DATE 2019-12-27
 */
public interface IEnumTypeHandler {
    int index();

    static <T extends IEnumTypeHandler> T of(Class<? extends T> classZ, int index) {
        T[] enumConstants = classZ.getEnumConstants();
        for (T t : enumConstants) {
            if (t.index() == index) {
                return t;
            }
        }
        return null;
    }
}
