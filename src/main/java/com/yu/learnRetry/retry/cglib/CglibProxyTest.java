package com.yu.learnRetry.retry.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB 是一种代码生成库，它能够扩展 Java 类并在运行时实现接口。它具有功能强大、高性能和高质量的特点。
 * 使用 CGLIB 可以生成子类来代理目标对象，从而在不改变原始类的情况下，实现对其进行扩展和增强。
 * 这种技术被广泛应用于 AOP 框架、ORM 框架、缓存框架以及其他许多 Java 应用程序中。CGLIB 通过生成字节码来创建代理类，具有较高的性能。
 */
public class CglibProxyTest implements MethodInterceptor {

    private static final Integer MAX_TIMES = 3;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        int times = 0;
        while (times < MAX_TIMES) {
            try {
                //通过代理子类调用父类的方法
                return methodProxy.invokeSuper(o, objects);
            } catch (Exception e) {
                times++;

                if (times >= MAX_TIMES) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    /**
     * 获取代理类
     *
     * @param clazz 类信息
     * @return 代理类结果
     */
    public Object getProxy(Class clazz) {
        Enhancer enhancer = new Enhancer();
        //目标对象类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        //通过字节码技术创建目标对象类的子类实例作为代理
        return enhancer.create();
    }
}
