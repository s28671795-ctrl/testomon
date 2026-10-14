/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.codehaus.commons.nullanalysis.NotNullByDefault;

public final class ApiLog {
    private ApiLog() {
    }

    public static Object logMethodInvocations(final Object delegate) {
        if (!Boolean.getBoolean(ApiLog.class.getName() + ".enable")) {
            return delegate;
        }
        Class<?> c = delegate.getClass();
        return Proxy.newProxyInstance(c.getClassLoader(), ApiLog.getAllInterfaces(c), new InvocationHandler(){

            @Override
            @NotNullByDefault(value=false)
            public Object invoke(Object proxy, Method method2, Object[] arguments) throws Throwable {
                Object returnValue;
                try {
                    returnValue = method2.invoke(delegate, arguments);
                }
                catch (InvocationTargetException ite) {
                    Throwable targetException = ite.getTargetException();
                    System.err.printf("%s.%s(%s) throws %s%n", delegate.getClass().getSimpleName(), method2.getName(), ApiLog.truncate(Arrays.deepToString(arguments)), targetException);
                    throw targetException;
                }
                System.err.printf("%s.%s(%s) => %s%n", delegate.getClass().getSimpleName(), method2.getName(), ApiLog.truncate(Arrays.deepToString(arguments)), ApiLog.truncate(String.valueOf(returnValue)));
                return returnValue;
            }
        });
    }

    protected static String truncate(String s2) {
        return s2.length() < 200 ? s2 : s2.substring(0, 100) + "...";
    }

    private static Class<?>[] getAllInterfaces(Class<?> c) {
        HashSet result = new HashSet();
        ApiLog.getAllInterfaces(c, result);
        return result.toArray(new Class[result.size()]);
    }

    private static void getAllInterfaces(Class<?> c, Set<Class<?>> result) {
        if (c.isInterface()) {
            result.add(c);
        }
        for (Class<?> i : c.getInterfaces()) {
            ApiLog.getAllInterfaces(i, result);
        }
        Class<?> sc = c.getSuperclass();
        if (sc != null) {
            ApiLog.getAllInterfaces(sc, result);
        }
    }
}

