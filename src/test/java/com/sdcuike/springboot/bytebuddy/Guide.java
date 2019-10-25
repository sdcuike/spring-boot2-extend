package com.sdcuike.springboot.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Modifier;

/**
 * @author sdcuike
 * @DATE 2019/10/25
 */
public class Guide {
    public static void main(String[] args) throws Throwable {

        createClassDemo();
        methodDelegationDemo();
        methodAndFieldDefinitionDemo();
        redefineAnExistingClass();
    }

    /**
     * //动态生成类
     *
     * @throws ReflectiveOperationException
     */
    private static void createClassDemo() throws ReflectiveOperationException {
        Class<?> newClass = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.isToString())
                .intercept(FixedValue.value("hello sdcuke"))
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded();
        System.out.println("动态生成类:" + newClass.getConstructor().newInstance());
    }

    /**
     * 方法代理
     *
     * @throws ReflectiveOperationException
     */
    private static void methodDelegationDemo() throws ReflectiveOperationException {
        Class<? extends Demo1> rebasedClass = new ByteBuddy()
                .subclass(Demo1.class)
                .method(ElementMatchers.named("hello").and(ElementMatchers.isDeclaredBy(Demo1.class)))
                .intercept(MethodDelegation.to(Demo1Interceptor.class))
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded();
        Demo1 instance = rebasedClass.getConstructor().newInstance();
        System.out.println("methodDelegationDemo: " + instance.hello());
    }

    /**
     * 动态增加属性和方法
     *
     * @throws ReflectiveOperationException
     */
    private static void methodAndFieldDefinitionDemo() throws ReflectiveOperationException {
        Class<?> aClass = new ByteBuddy()
                .subclass(Object.class)
                .name("com.sdcuike.springboot.bytebuddy.SdCuiKe")
                .defineMethod("who", String.class, Modifier.PUBLIC)
                .intercept(MethodDelegation.to(Demo1Interceptor.class))
                .defineField("name", String.class, Modifier.PUBLIC)
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded();
        Object newInstance = aClass.getConstructor().newInstance();
        System.out.println("methodAndFieldDefinitionDemo : " + aClass.getDeclaredMethod("who").invoke(newInstance));
        System.out.println("methodAndFieldDefinitionDemo: " + aClass.getDeclaredField("name"));
    }

    /**
     * 修改已加载的类
     *
     * @throws ReflectiveOperationException
     */
    private static void redefineAnExistingClass() throws ReflectiveOperationException {
        ByteBuddyAgent.install();
        Class<? extends Demo1> rebasedClass = new ByteBuddy()
                .redefine(Demo1.class)
                .method(ElementMatchers.named("hello").and(ElementMatchers.isDeclaredBy(Demo1.class)))
                .intercept(MethodDelegation.to(Demo1Interceptor.class))
                .make()
                .load(Thread.currentThread().getContextClassLoader(), ClassReloadingStrategy.fromInstalledAgent())
                .getLoaded();
        Demo1 instance = rebasedClass.getConstructor().newInstance();
        System.out.println("redefineAnExistingClass: " + instance.hello());
    }

    public static class Demo1 {
        public String hello() {
            return "who are you";
        }
    }

    public static class Demo1Interceptor {

        /**
         * 方法必须是static
         *
         * @return
         */
        @BindingPriority(0)
        public static String hello() {
            return "who are you from Demo1Interceptor";
        }
    }
}
