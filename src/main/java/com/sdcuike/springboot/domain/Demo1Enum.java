package com.sdcuike.springboot.domain;

import com.sdcuike.springboot.extend.mybatis.IEnumTypeHandler;

/**
 * @author sdcuike
 * @DATE 2019/12/27
 */
public enum Demo1Enum implements IEnumTypeHandler {
    Hello(1, "test");
    private int index;
    private String name;


    Demo1Enum(int index, String name) {
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
        return "Demo1Enum{" +
                "index=" + index +
                ", name='" + name + '\'' +
                '}';
    }
}
