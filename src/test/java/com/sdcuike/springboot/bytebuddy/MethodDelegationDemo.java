package com.sdcuike.springboot.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 模拟aop
 *
 * @author sdcuike
 * @DATE 2019/10/25
 */

public class MethodDelegationDemo {
    public static void main(String[] args) throws Throwable {
        Class<? extends Demo1> newClass = new ByteBuddy()
                .subclass(Demo1.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(MethodInter.class))
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded();
        Demo1 demo1 = newClass.getConstructor().newInstance();
        System.out.println(demo1.name());
    }

    public static class Demo1 {
        public String name() {
            return "sdcuike,doctor who";
        }
    }


    public static class MethodInter {
        private static final Logger LOGGER = LoggerFactory.getLogger(MethodInter.class);

        @RuntimeType
        public static Object intercept(@This Object obj, @AllArguments Object[] allArguments, @SuperCall Callable<?> superObject, @Origin Method method
        ) throws Throwable {

            LOGGER.info(" {} before invoke {} ,allArguments: {}", obj, method, allArguments);
            Object result;
            try {
                result = superObject.call();
            } catch (Throwable e) {
                LOGGER.info(" {} before invoke {} ,allArguments: {} ", obj, method, allArguments, e);
                throw e;
            }
            LOGGER.info("{} after invoke {}, allArguments :{} result: {}", obj, method, allArguments, result);
            return result;
        }

    }
}
