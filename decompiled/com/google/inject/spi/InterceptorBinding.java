/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.inject.Binder;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.Element;
import com.google.inject.spi.ElementVisitor;
import java.lang.reflect.Method;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;

public final class InterceptorBinding
implements Element {
    private final Object source;
    private final Matcher<? super Class<?>> classMatcher;
    private final Matcher<? super Method> methodMatcher;
    private final ImmutableList<MethodInterceptor> interceptors;

    InterceptorBinding(Object source2, Matcher<? super Class<?>> classMatcher, Matcher<? super Method> methodMatcher, MethodInterceptor[] interceptors) {
        this.source = Preconditions.checkNotNull(source2, "source");
        this.classMatcher = Preconditions.checkNotNull(classMatcher, "classMatcher");
        this.methodMatcher = Preconditions.checkNotNull(methodMatcher, "methodMatcher");
        this.interceptors = ImmutableList.copyOf(interceptors);
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    public Matcher<? super Class<?>> getClassMatcher() {
        return this.classMatcher;
    }

    public Matcher<? super Method> getMethodMatcher() {
        return this.methodMatcher;
    }

    public List<MethodInterceptor> getInterceptors() {
        return this.interceptors;
    }

    @Override
    public <T> T acceptVisitor(ElementVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void applyTo(Binder binder) {
        binder.withSource(this.getSource()).bindInterceptor(this.classMatcher, this.methodMatcher, this.interceptors.toArray(new MethodInterceptor[this.interceptors.size()]));
    }
}

