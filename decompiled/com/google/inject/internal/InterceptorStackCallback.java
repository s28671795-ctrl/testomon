/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.Lists;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

final class InterceptorStackCallback
implements InvocationHandler {
    private static final String GUICE_INTERNAL_AOP_PACKAGE = "com.google.inject.internal.aop";
    final Method method;
    final MethodInterceptor[] interceptors;
    final BiFunction<Object, Object[], Object> superInvoker;

    public InterceptorStackCallback(Method method2, List<MethodInterceptor> interceptors, BiFunction<Object, Object[], Object> superInvoker) {
        this.method = method2;
        this.interceptors = interceptors.toArray(new MethodInterceptor[interceptors.size()]);
        this.superInvoker = superInvoker;
    }

    @Override
    public Object invoke(Object proxy, Method unused2, Object[] arguments) throws Throwable {
        return new InterceptedMethodInvocation(proxy, arguments, 0).proceed();
    }

    private void pruneStacktrace(Throwable throwable) {
        for (Throwable t2 = throwable; t2 != null; t2 = t2.getCause()) {
            StackTraceElement[] stackTrace = t2.getStackTrace();
            ArrayList<StackTraceElement> pruned = Lists.newArrayList();
            for (StackTraceElement element : stackTrace) {
                String className = element.getClassName();
                if (className.startsWith(InterceptorStackCallback.class.getName()) || className.startsWith(GUICE_INTERNAL_AOP_PACKAGE) || className.contains("$$EnhancerByGuice$$")) continue;
                pruned.add(element);
            }
            t2.setStackTrace(pruned.toArray(new StackTraceElement[pruned.size()]));
        }
    }

    private class InterceptedMethodInvocation
    implements MethodInvocation {
        final Object proxy;
        final Object[] arguments;
        final int interceptorIndex;

        public InterceptedMethodInvocation(Object proxy, Object[] arguments, int interceptorIndex) {
            this.proxy = proxy;
            this.arguments = arguments;
            this.interceptorIndex = interceptorIndex;
        }

        @Override
        public Object proceed() throws Throwable {
            try {
                return this.interceptorIndex == InterceptorStackCallback.this.interceptors.length ? InterceptorStackCallback.this.superInvoker.apply(this.proxy, this.arguments) : InterceptorStackCallback.this.interceptors[this.interceptorIndex].invoke(new InterceptedMethodInvocation(this.proxy, this.arguments, this.interceptorIndex + 1));
            }
            catch (Throwable t2) {
                InterceptorStackCallback.this.pruneStacktrace(t2);
                throw t2;
            }
        }

        @Override
        public Method getMethod() {
            return InterceptorStackCallback.this.method;
        }

        @Override
        public Object[] getArguments() {
            return this.arguments;
        }

        @Override
        public Object getThis() {
            return this.proxy;
        }

        @Override
        public AccessibleObject getStaticPart() {
            return this.getMethod();
        }
    }
}

