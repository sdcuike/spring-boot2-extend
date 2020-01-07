package com.sdcuike.extend.gracefulshutdown.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.NamedBeanHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 还有某种情况没考虑
 * <p>
 * 逻辑参考ScheduledAnnotationBeanPostProcessor
 * <p>
 * 没用SchedulingConfigurer，里面的线程池获取不好处理，逻辑有点多
 *
 * @author sdcuike
 * @DATE 2020/1/7
 */
@Slf4j
public class SchedulingShutDown implements Shutdown, ApplicationContextAware {
    public static final String DEFAULT_TASK_SCHEDULER_BEAN_NAME = "taskScheduler";

    private ApplicationContext applicationContext;

    @Override
    public void pause() throws InterruptedException {

    }

    @Override
    public void shutdown(Integer delay) throws InterruptedException {
        try {
            doShutdown(delay);
        } catch (Throwable e) {
            log.warn("shutdown error", e);
        }
    }


    public void doShutdown(Integer delay) throws InterruptedException {
        // 寻找bean代码copy自ScheduledAnnotationBeanPostProcessor
        TaskScheduler bean = getSchedulerBean(applicationContext);
        if (bean != null) {
            if (bean instanceof ThreadPoolTaskScheduler) {
                ((ThreadPoolTaskScheduler) bean).setWaitForTasksToCompleteOnShutdown(true);
                ((ThreadPoolTaskScheduler) bean).setAwaitTerminationSeconds(delay);

                log.info("ThreadPoolTaskScheduler_shutdown");
                ((ThreadPoolTaskScheduler) bean).shutdown();
            }
        } else {
            ScheduledExecutorService executorService = getScheduledExecutorService(applicationContext);
            if (Objects.isNull(executorService)) {
                return;
            }
            executorService.shutdown();
            if (!executorService.awaitTermination(delay, TimeUnit.SECONDS)) {
                log.warn("TaskScheduler pool did not shut down gracefully within " + delay + " second(s). Proceeding with force shutdown");
            } else {
                log.debug("TTaskScheduler pool is empty, we stop now");
            }
        }

    }

    private TaskScheduler getSchedulerBean(BeanFactory beanFactory) {
        try {
            // Search for TaskScheduler bean...
            return resolveSchedulerBean(beanFactory, TaskScheduler.class, false);
        } catch (NoUniqueBeanDefinitionException ex) {
            log.warn("Could not find unique TaskScheduler bean", ex);
            try {
                return resolveSchedulerBean(beanFactory, TaskScheduler.class, true);
            } catch (NoSuchBeanDefinitionException ex2) {
            }
        } catch (NoSuchBeanDefinitionException ex) {
            log.warn("Could not find default TaskScheduler bean", ex);
            // Search for ScheduledExecutorService bean next...
        }
        return null;
    }

    private <T> T resolveSchedulerBean(BeanFactory beanFactory, Class<T> schedulerType, boolean byName) {
        if (byName) {
            return beanFactory.getBean(DEFAULT_TASK_SCHEDULER_BEAN_NAME, schedulerType);

        } else if (beanFactory instanceof AutowireCapableBeanFactory) {
            NamedBeanHolder<T> holder = ((AutowireCapableBeanFactory) beanFactory).resolveNamedBean(schedulerType);

            return holder.getBeanInstance();
        } else {
            return beanFactory.getBean(schedulerType);
        }
    }

    private ScheduledExecutorService getScheduledExecutorService(BeanFactory beanFactory) {
        try {
            return resolveSchedulerBean(beanFactory, ScheduledExecutorService.class, false);
        } catch (NoUniqueBeanDefinitionException ex2) {
            log.warn("Could not find unique ScheduledExecutorService bean", ex2);
            try {
                return resolveSchedulerBean(beanFactory, ScheduledExecutorService.class, true);
            } catch (NoSuchBeanDefinitionException ex3) {
            }
        } catch (NoSuchBeanDefinitionException ex2) {
        }

        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
