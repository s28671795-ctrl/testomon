/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.inject.internal.BytecodeGen;
import com.google.inject.internal.ConstructionProxy;
import com.google.inject.internal.ConstructionProxyFactory;
import com.google.inject.internal.DefaultConstructionProxyFactory;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.InterceptorStackCallback;
import com.google.inject.internal.MethodAspect;
import com.google.inject.spi.InjectionPoint;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aopalliance.intercept.MethodInterceptor;

final class ProxyFactory<T>
implements ConstructionProxyFactory<T> {
    private static final Logger logger = Logger.getLogger(ProxyFactory.class.getName());
    private final InjectionPoint injectionPoint;
    private final Function<String, BiFunction<Object, Object[], Object>> enhancer;
    private final ImmutableMap<Method, List<MethodInterceptor>> interceptors;
    private final InvocationHandler[] callbacks;

    ProxyFactory(InjectionPoint injectionPoint, Iterable<MethodAspect> methodAspects) throws ErrorsException {
        Method method2;
        int methodIndex;
        this.injectionPoint = injectionPoint;
        Class<?> hostClass = injectionPoint.getMember().getDeclaringClass();
        ArrayList<MethodAspect> applicableAspects = Lists.newArrayList();
        for (MethodAspect methodAspect : methodAspects) {
            if (!methodAspect.matches(hostClass)) continue;
            applicableAspects.add(methodAspect);
        }
        if (applicableAspects.isEmpty()) {
            this.enhancer = null;
            this.interceptors = ImmutableMap.of();
            this.callbacks = null;
            return;
        }
        BytecodeGen.EnhancerBuilder enhancerBuilder = BytecodeGen.enhancerBuilder(hostClass);
        Method[] methods = enhancerBuilder.getEnhanceableMethods();
        int numMethods = methods.length;
        ArrayListMultimap<Method, MethodInterceptor> matchedInterceptors = ArrayListMultimap.create();
        BitSet matchedMethodIndices = new BitSet();
        for (MethodAspect methodAspect : applicableAspects) {
            for (methodIndex = 0; methodIndex < numMethods; ++methodIndex) {
                method2 = methods[methodIndex];
                if (!methodAspect.matches(method2)) continue;
                if (method2.isSynthetic()) {
                    logger.log(Level.WARNING, "Method [{0}] is synthetic and is being intercepted by {1}. This could indicate a bug.  The method may be intercepted twice, or may not be intercepted at all.", new Object[]{method2, methodAspect.interceptors()});
                }
                matchedInterceptors.putAll(method2, methodAspect.interceptors());
                matchedMethodIndices.set(methodIndex);
            }
        }
        if (matchedMethodIndices.isEmpty()) {
            this.enhancer = null;
            this.interceptors = ImmutableMap.of();
            this.callbacks = null;
            return;
        }
        try {
            this.enhancer = enhancerBuilder.buildEnhancer(matchedMethodIndices);
        }
        catch (Throwable e) {
            throw new Errors().errorEnhancingClass(hostClass, e).toException();
        }
        this.callbacks = new InvocationHandler[matchedMethodIndices.cardinality()];
        ImmutableMap.Builder<Method, ImmutableList<MethodInterceptor>> interceptorsMapBuilder = ImmutableMap.builder();
        int callbackIndex = 0;
        methodIndex = matchedMethodIndices.nextSetBit(0);
        while (methodIndex >= 0) {
            method2 = methods[methodIndex];
            ImmutableList<MethodInterceptor> deDuplicated = ImmutableSet.copyOf(matchedInterceptors.get(method2)).asList();
            interceptorsMapBuilder.put(method2, deDuplicated);
            BiFunction<Object, Object[], Object> superInvoker = BytecodeGen.superMethod(this.enhancer, method2);
            this.callbacks[callbackIndex++] = new InterceptorStackCallback(method2, deDuplicated, superInvoker);
            methodIndex = matchedMethodIndices.nextSetBit(methodIndex + 1);
        }
        this.interceptors = interceptorsMapBuilder.buildOrThrow();
    }

    public ImmutableMap<Method, List<MethodInterceptor>> getInterceptors() {
        return this.interceptors;
    }

    @Override
    public ConstructionProxy<T> create() throws ErrorsException {
        if (this.interceptors.isEmpty()) {
            return new DefaultConstructionProxyFactory(this.injectionPoint).create();
        }
        try {
            return new ProxyConstructor(this.injectionPoint, this.enhancer, this.interceptors, this.callbacks);
        }
        catch (Throwable e) {
            throw new Errors().errorEnhancingClass(this.injectionPoint.getMember().getDeclaringClass(), e).toException();
        }
    }

    private static class ProxyConstructor<T>
    implements ConstructionProxy<T> {
        final InjectionPoint injectionPoint;
        final Constructor<T> constructor;
        final BiFunction<Object, Object[], Object> enhancedConstructor;
        final ImmutableMap<Method, List<MethodInterceptor>> interceptors;
        final InvocationHandler[] callbacks;

        ProxyConstructor(InjectionPoint injectionPoint, Function<String, BiFunction<Object, Object[], Object>> enhancer, ImmutableMap<Method, List<MethodInterceptor>> interceptors, InvocationHandler[] callbacks) {
            this.injectionPoint = injectionPoint;
            this.constructor = (Constructor)injectionPoint.getMember();
            this.enhancedConstructor = BytecodeGen.enhancedConstructor(enhancer, this.constructor);
            this.interceptors = interceptors;
            this.callbacks = callbacks;
        }

        @Override
        public T newInstance(Object ... arguments) throws InvocationTargetException {
            return (T)this.enhancedConstructor.apply(this.callbacks, arguments);
        }

        @Override
        public InjectionPoint getInjectionPoint() {
            return this.injectionPoint;
        }

        @Override
        public Constructor<T> getConstructor() {
            return this.constructor;
        }

        @Override
        public ImmutableMap<Method, List<MethodInterceptor>> getMethodInterceptors() {
            return this.interceptors;
        }
    }
}

