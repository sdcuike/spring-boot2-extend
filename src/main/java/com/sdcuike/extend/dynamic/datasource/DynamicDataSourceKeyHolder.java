package com.sdcuike.extend.dynamic.datasource;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * @author sdcuike
 * @DATE 2020/1/8
 */
public final class DynamicDataSourceKeyHolder {
    /**
     * 利用ThreadLocal保存mybatis接口上使用的数据源名称；
     * ThreadLocal的内容为Deque,模拟栈结构是因为在事务中有嵌套关系存在^_^
     */
    private static ThreadLocal<Deque<String>> dataSourceKey = ThreadLocal.withInitial(ArrayDeque::new);


    public static void setDataSourceKey(String dataSourceKey) {
        DynamicDataSourceKeyHolder.dataSourceKey.get().addLast(Objects.isNull(dataSourceKey) ? "" : dataSourceKey);
    }

    public static String getDataSourceKey() {
        return dataSourceKey.get().getLast();
    }


    /**
     * 记得清理，防止内存泄漏-线程池情况下
     */
    public static void clear() {
        dataSourceKey.get().removeLast();
        if (dataSourceKey.get().isEmpty()) {
            dataSourceKey.remove();
        }
    }
}
