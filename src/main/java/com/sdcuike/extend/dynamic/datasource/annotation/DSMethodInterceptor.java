package com.sdcuike.extend.dynamic.datasource.annotation;

import com.sdcuike.extend.dynamic.datasource.DynamicDataSourceKeyHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author sdcuike
 * @DATE 2020/1/8
 */
class DSMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        DynamicDataSourceKeyHolder.setDataSourceKey(determineDatasource(invocation));
        try {
            return invocation.proceed();
        } finally {
            DynamicDataSourceKeyHolder.clear();
        }
    }

    private String determineDatasource(MethodInvocation invocation) {
        Class<?> classZ = invocation.getMethod().getDeclaringClass();
        Method method = invocation.getMethod();
        DS ds = method.isAnnotationPresent(DS.class) ? method.getAnnotation(DS.class) : AnnotationUtils.findAnnotation(classZ, DS.class);
        if (Objects.isNull(ds)) {
            return null;
        }
        return ds.value();
    }


}
