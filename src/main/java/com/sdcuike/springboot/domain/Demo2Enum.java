package com.sdcuike.springboot.domain;

import com.sdcuike.extend.mybatis.IEnumTypeHandler;

/**
 * @author sdcuike
 * @DATE 2019/12/27
 */
public enum Demo2Enum implements IEnumTypeHandler {
    Hello2(3, "test Hell2");
    private int index;
    private String name;

    Demo2Enum(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public String toString() {
        return "Demo2Enum{" +
                "index=" + index +
                ", name='" + name + '\'' +
                '}';
    }
}
