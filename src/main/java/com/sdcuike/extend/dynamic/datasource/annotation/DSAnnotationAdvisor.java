package com.sdcuike.extend.dynamic.datasource.annotation;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author sdcuike
 * @DATE 2020/1/8
 */
public final class DSAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

    private Advice advice;

    private Pointcut pointcut;


    public DSAnnotationAdvisor(int order) {
        advice = buildAdvice();
        Set<Class<? extends Annotation>> annotationTypes = new LinkedHashSet<>(1);
        annotationTypes.add(DS.class);
        pointcut = buildPointcut(annotationTypes);
        setOrder(order);
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }


    private Advice buildAdvice() {
        return new DSMethodInterceptor();
    }

    /**
     * Calculate a pointcut for the given async annotation types, if any.
     *
     * @param annotationTypes the async annotation types to introspect
     * @return the applicable Pointcut object, or {@code null} if none
     */
    private Pointcut buildPointcut(Set<Class<? extends Annotation>> annotationTypes) {
        ComposablePointcut result = null;
        for (Class<? extends Annotation> type : annotationTypes) {
            Pointcut cpc = new AnnotationMatchingPointcut(type, true);
            Pointcut mpc = new AnnotationMatchingPointcut(null, type, true);
            if (result == null) {
                result = new ComposablePointcut(cpc);
            } else {
                result.union(cpc);
            }
            result = result.union(mpc);
        }
        return (result != null ? result : Pointcut.TRUE);
    }
}
