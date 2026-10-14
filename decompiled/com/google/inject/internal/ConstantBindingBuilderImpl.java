/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.binder.AnnotatedConstantBindingBuilder;
import com.google.inject.binder.ConstantBindingBuilder;
import com.google.inject.internal.AbstractBindingBuilder;
import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.InstanceBindingImpl;
import com.google.inject.spi.Element;
import java.lang.annotation.Annotation;
import java.util.List;

public final class ConstantBindingBuilderImpl<T>
extends AbstractBindingBuilder<T>
implements AnnotatedConstantBindingBuilder,
ConstantBindingBuilder {
    public ConstantBindingBuilderImpl(Binder binder, List<Element> elements, Object source2) {
        super(binder, elements, source2, NULL_KEY);
    }

    @Override
    public ConstantBindingBuilder annotatedWith(Class<? extends Annotation> annotationType) {
        this.annotatedWithInternal(annotationType);
        return this;
    }

    @Override
    public ConstantBindingBuilder annotatedWith(Annotation annotation) {
        this.annotatedWithInternal(annotation);
        return this;
    }

    @Override
    public void to(String value) {
        this.toConstant(String.class, value);
    }

    @Override
    public void to(int value) {
        this.toConstant(Integer.class, value);
    }

    @Override
    public void to(long value) {
        this.toConstant(Long.class, value);
    }

    @Override
    public void to(boolean value) {
        this.toConstant(Boolean.class, value);
    }

    @Override
    public void to(double value) {
        this.toConstant(Double.class, value);
    }

    @Override
    public void to(float value) {
        this.toConstant(Float.class, Float.valueOf(value));
    }

    @Override
    public void to(short value) {
        this.toConstant(Short.class, value);
    }

    @Override
    public void to(char value) {
        this.toConstant(Character.class, Character.valueOf(value));
    }

    @Override
    public void to(byte value) {
        this.toConstant(Byte.class, value);
    }

    @Override
    public void to(Class<?> value) {
        this.toConstant(Class.class, value);
    }

    @Override
    public <E extends Enum<E>> void to(E value) {
        this.toConstant(value.getDeclaringClass(), value);
    }

    private void toConstant(Class<?> type, Object instance) {
        Class<?> typeAsClassT = type;
        Object instanceAsT = instance;
        if (this.keyTypeIsSet()) {
            this.binder.addError("Constant value is set more than once.", new Object[0]);
            return;
        }
        BindingImpl base = this.getBinding();
        Key<?> key = base.getKey().ofType(typeAsClassT);
        if (instanceAsT == null) {
            this.binder.addError("Binding to null instances is not allowed. Use toProvider(Providers.of(null)) if this is your intended behaviour.", new Object[0]);
        }
        this.setBinding(new InstanceBindingImpl<Object>(base.getSource(), key, base.getScoping(), ImmutableSet.of(), instanceAsT));
    }

    public String toString() {
        return "ConstantBindingBuilder";
    }
}

