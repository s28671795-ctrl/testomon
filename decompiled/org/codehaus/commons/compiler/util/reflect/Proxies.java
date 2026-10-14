/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import org.codehaus.commons.nullanalysis.NotNullByDefault;

public final class Proxies {
    private Proxies() {
    }

    public static <T> T newInstance(final Object delegate, final Method ... methodsAndDelegateMethod) {
        Class<?> declaringInterface = methodsAndDelegateMethod[0].getDeclaringClass();
        assert (declaringInterface.isInterface());
        for (int i = 0; i < methodsAndDelegateMethod.length; i += 2) {
            Method method2 = methodsAndDelegateMethod[i];
            Method delegateMethod = methodsAndDelegateMethod[i + 1];
            assert (method2.getDeclaringClass().equals(declaringInterface));
            delegateMethod.setAccessible(true);
            assert (method2.getReturnType().equals(delegateMethod.getReturnType()));
            assert (Arrays.equals(method2.getParameterTypes(), delegateMethod.getParameterTypes()));
        }
        Object result = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{declaringInterface}, new InvocationHandler(){

            @Override
            @NotNullByDefault(value=false)
            public Object invoke(Object proxy, Method actualMethod, Object[] args2) throws Throwable {
                Method delegateMethod;
                block4: {
                    for (int i = 0; i < methodsAndDelegateMethod.length; i += 2) {
                        Method method2 = methodsAndDelegateMethod[i];
                        delegateMethod = methodsAndDelegateMethod[i + 1];
                        if (!actualMethod.equals(method2)) {
                            continue;
                        }
                        break block4;
                    }
                    String message = "Expected invocation of [";
                    for (int i = 2; i < methodsAndDelegateMethod.length; i += 2) {
                        Method method3 = methodsAndDelegateMethod[i];
                        if (i > 0) {
                            message = message + ", ";
                        }
                        message = message + method3;
                    }
                    message = message + "], but was " + actualMethod;
                    throw new AssertionError((Object)message);
                }
                return delegateMethod.invoke(delegate, args2);
            }
        });
        return (T)result;
    }
}

