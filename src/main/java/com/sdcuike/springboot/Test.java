package com.sdcuike.springboot;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author sdcuike
 * @date 2019-08-11
 */
@Component
public class Test implements ApplicationContextAware {

    public static ApplicationContext appCtx;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCtx = applicationContext;
    }
}
