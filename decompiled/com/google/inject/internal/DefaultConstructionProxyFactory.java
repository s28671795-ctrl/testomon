/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableMap;
import com.google.inject.internal.BytecodeGen;
import com.google.inject.internal.ConstructionProxy;
import com.google.inject.internal.ConstructionProxyFactory;
import com.google.inject.internal.InternalFlags;
import com.google.inject.spi.InjectionPoint;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.function.BiFunction;
import org.aopalliance.intercept.MethodInterceptor;

final class DefaultConstructionProxyFactory<T>
implements ConstructionProxyFactory<T> {
    private final InjectionPoint injectionPoint;

    DefaultConstructionProxyFactory(InjectionPoint injectionPoint) {
        this.injectionPoint = injectionPoint;
    }

    @Override
    public ConstructionProxy<T> create() {
        Constructor constructor = (Constructor)this.injectionPoint.getMember();
        if (InternalFlags.isBytecodeGenEnabled()) {
            try {
                BiFunction<Object, Object[], Object> fastConstructor = BytecodeGen.fastConstructor(constructor);
                if (fastConstructor != null) {
                    return new FastClassProxy(this.injectionPoint, constructor, fastConstructor);
                }
            }
            catch (Exception | LinkageError throwable) {
                // empty catch block
            }
        }
        return new ReflectiveProxy(this.injectionPoint, constructor);
    }

    private static final class ReflectiveProxy<T>
    implements ConstructionProxy<T> {
        final Constructor<T> constructor;
        final InjectionPoint injectionPoint;

        ReflectiveProxy(InjectionPoint injectionPoint, Constructor<T> constructor) {
            if (!Modifier.isPublic(constructor.getDeclaringClass().getModifiers()) || !Modifier.isPublic(constructor.getModifiers())) {
                constructor.setAccessible(true);
            }
            this.injectionPoint = injectionPoint;
            this.constructor = constructor;
        }

        @Override
        public T newInstance(Object ... arguments) throws InvocationTargetException {
            try {
                return this.constructor.newInstance(arguments);
            }
            catch (InstantiationException e) {
                throw new AssertionError((Object)e);
            }
            catch (IllegalAccessException e) {
                throw new AssertionError((Object)e);
            }
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
            return ImmutableMap.of();
        }
    }

    private static final class FastClassProxy<T>
    implements ConstructionProxy<T> {
        final InjectionPoint injectionPoint;
        final Constructor<T> constructor;
        final BiFunction<Object, Object[], Object> fastConstructor;

        FastClassProxy(InjectionPoint injectionPoint, Constructor<T> constructor, BiFunction<Object, Object[], Object> fastConstructor) {
            this.injectionPoint = injectionPoint;
            this.constructor = constructor;
            this.fastConstructor = fastConstructor;
        }

        @Override
        public T newInstance(Object ... arguments) throws InvocationTargetException {
            try {
                return (T)this.fastConstructor.apply(null, arguments);
            }
            catch (Throwable e) {
                throw new InvocationTargetException(e);
            }
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
            return ImmutableMap.of();
        }
    }
}

