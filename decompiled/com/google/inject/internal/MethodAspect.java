/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.InterceptorBinding;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;

final class MethodAspect {
    private final Matcher<? super Class<?>> classMatcher;
    private final Matcher<? super Method> methodMatcher;
    private final List<MethodInterceptor> interceptors;

    static MethodAspect fromBinding(InterceptorBinding binding) {
        return new MethodAspect(binding.getClassMatcher(), binding.getMethodMatcher(), binding.getInterceptors());
    }

    MethodAspect(Matcher<? super Class<?>> classMatcher, Matcher<? super Method> methodMatcher, List<MethodInterceptor> interceptors) {
        this.classMatcher = Preconditions.checkNotNull(classMatcher, "class matcher");
        this.methodMatcher = Preconditions.checkNotNull(methodMatcher, "method matcher");
        this.interceptors = Preconditions.checkNotNull(interceptors, "interceptors");
    }

    MethodAspect(Matcher<? super Class<?>> classMatcher, Matcher<? super Method> methodMatcher, MethodInterceptor ... interceptors) {
        this(classMatcher, methodMatcher, Arrays.asList(interceptors));
    }

    boolean matches(Class<?> clazz) {
        return this.classMatcher.matches(clazz);
    }

    boolean matches(Method method2) {
        return this.methodMatcher.matches(method2);
    }

    List<MethodInterceptor> interceptors() {
        return this.interceptors;
    }
}

