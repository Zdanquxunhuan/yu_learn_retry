package com.yu.learnRetry.retry.jdkdynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyRetry implements InvocationHandler {

    private static final Integer MAX_TIMES = 3;

    private final Object target;

    public DynamicProxyRetry(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        int invokeTimes = 0;
        while (invokeTimes < MAX_TIMES) {
            try {
                // When a proxy object calls a method on the real object,
                // it automatically jumps to the invoke method of the handler object associated with the proxy object
                method.invoke(target, args);
            } catch (Exception e) {
                invokeTimes++;
                System.out.println("进行第" + invokeTimes + "次重试");
                if (invokeTimes >= MAX_TIMES) {
                    // The records are stored in the database, and the subsequent scheduled tasks are performed again
                    //do something record...
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public static Object getProxy(Object realSubject) {

        return Proxy.newProxyInstance(
                realSubject.getClass().getClassLoader(),
                realSubject.getClass().getInterfaces(),
                new DynamicProxyRetry(realSubject)
        );
    }
}
