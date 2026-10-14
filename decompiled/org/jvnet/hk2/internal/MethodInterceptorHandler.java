/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import javassist.util.proxy.MethodHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.HK2Invocation;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.jvnet.hk2.internal.ServiceLocatorImpl;

public class MethodInterceptorHandler
implements MethodHandler {
    private static final boolean DEBUG_INTERCEPTION = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

        @Override
        public Boolean run() {
            return Boolean.parseBoolean(System.getProperty("org.jvnet.hk2.properties.tracing.interceptors", "false"));
        }
    });
    private final ServiceLocatorImpl locator;
    private final Map<Method, List<MethodInterceptor>> interceptorLists;
    private final ActiveDescriptor<?> underlyingDescriptor;

    MethodInterceptorHandler(ServiceLocatorImpl locator, ActiveDescriptor<?> underlyingDescriptor, Map<Method, List<MethodInterceptor>> interceptorLists) {
        this.locator = locator;
        this.interceptorLists = interceptorLists;
        this.underlyingDescriptor = underlyingDescriptor;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args2) throws Throwable {
        if (thisMethod.getName().equals("__getUnderlyingDescriptor")) {
            return this.underlyingDescriptor;
        }
        List<MethodInterceptor> interceptors = this.interceptorLists.get(thisMethod);
        if (interceptors == null || interceptors.isEmpty()) {
            return ReflectionHelper.invoke(self, proceed, args2, this.locator.getNeutralContextClassLoader());
        }
        if (!(interceptors instanceof RandomAccess)) {
            interceptors = new ArrayList<MethodInterceptor>(interceptors);
        }
        MethodInterceptor nextInterceptor = interceptors.get(0);
        long aggregateInterceptionTime = 0L;
        if (DEBUG_INTERCEPTION) {
            aggregateInterceptionTime = System.currentTimeMillis();
            Logger.getLogger().debug("Invoking interceptor " + nextInterceptor.getClass().getName() + " index 0 in stack of " + interceptors.size() + " of method " + thisMethod);
        }
        try {
            Object object = nextInterceptor.invoke(new MethodInvocationImpl(args2, thisMethod, self, interceptors, 0, proceed, null));
            return object;
        }
        finally {
            if (DEBUG_INTERCEPTION) {
                aggregateInterceptionTime = System.currentTimeMillis() - aggregateInterceptionTime;
                Logger.getLogger().debug("Interceptor " + nextInterceptor.getClass().getName() + " index 0 took an aggregate of " + aggregateInterceptionTime + " milliseconds");
            }
        }
    }

    private class MethodInvocationImpl
    implements MethodInvocation,
    HK2Invocation {
        private final Object[] arguments;
        private final Method method;
        private final Object myself;
        private final List<MethodInterceptor> interceptors;
        private final int index;
        private final Method proceed;
        private HashMap<String, Object> userData;

        private MethodInvocationImpl(Object[] arguments, Method method2, Object myself, List<MethodInterceptor> interceptors, int index, Method proceed, HashMap<String, Object> userData) {
            this.arguments = arguments;
            this.method = method2;
            this.myself = myself;
            this.interceptors = interceptors;
            this.index = index;
            this.proceed = proceed;
            this.userData = userData;
        }

        @Override
        public Object[] getArguments() {
            return this.arguments;
        }

        @Override
        public AccessibleObject getStaticPart() {
            return this.method;
        }

        @Override
        public Object getThis() {
            return this.myself;
        }

        @Override
        public Method getMethod() {
            return this.method;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object proceed() throws Throwable {
            int newIndex = this.index + 1;
            if (newIndex >= this.interceptors.size()) {
                long methodTime = 0L;
                if (DEBUG_INTERCEPTION) {
                    methodTime = System.currentTimeMillis();
                }
                try {
                    Object object = ReflectionHelper.invoke(this.myself, this.proceed, this.arguments, MethodInterceptorHandler.this.locator.getNeutralContextClassLoader());
                    return object;
                }
                finally {
                    if (DEBUG_INTERCEPTION) {
                        methodTime = System.currentTimeMillis() - methodTime;
                        Logger.getLogger().debug("Time to call actual intercepted method " + this.method + " is " + methodTime + " milliseconds");
                    }
                }
            }
            MethodInterceptor nextInterceptor = this.interceptors.get(newIndex);
            long aggregateInterceptionTime = 0L;
            if (DEBUG_INTERCEPTION) {
                aggregateInterceptionTime = System.currentTimeMillis();
                Logger.getLogger().debug("Invoking interceptor " + nextInterceptor.getClass().getName() + " index " + newIndex + " in stack of " + this.interceptors.size() + " of method " + this.method);
            }
            try {
                Object object = nextInterceptor.invoke(new MethodInvocationImpl(this.arguments, this.method, this.myself, this.interceptors, newIndex, this.proceed, this.userData));
                return object;
            }
            finally {
                if (DEBUG_INTERCEPTION) {
                    aggregateInterceptionTime = System.currentTimeMillis() - aggregateInterceptionTime;
                    Logger.getLogger().debug("Interceptor " + nextInterceptor.getClass().getName() + " index " + newIndex + " took an aggregate of " + aggregateInterceptionTime + " milliseconds");
                }
            }
        }

        @Override
        public void setUserData(String key, Object data) {
            if (key == null) {
                throw new IllegalArgumentException();
            }
            if (this.userData == null) {
                this.userData = new HashMap();
            }
            if (data == null) {
                this.userData.remove(key);
            } else {
                this.userData.put(key, data);
            }
        }

        @Override
        public Object getUserData(String key) {
            if (key == null) {
                throw new IllegalArgumentException();
            }
            if (this.userData == null) {
                return null;
            }
            return this.userData.get(key);
        }
    }
}

