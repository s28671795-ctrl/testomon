/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.multibindings;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.internal.RealMultibinder;
import java.lang.annotation.Annotation;

public class Multibinder<T> {
    private final RealMultibinder<T> delegate;

    public static <T> Multibinder<T> newSetBinder(Binder binder, TypeLiteral<T> type) {
        return Multibinder.newSetBinder(binder, Key.get(type));
    }

    public static <T> Multibinder<T> newSetBinder(Binder binder, Class<T> type) {
        return Multibinder.newSetBinder(binder, Key.get(type));
    }

    public static <T> Multibinder<T> newSetBinder(Binder binder, TypeLiteral<T> type, Annotation annotation) {
        return Multibinder.newSetBinder(binder, Key.get(type, annotation));
    }

    public static <T> Multibinder<T> newSetBinder(Binder binder, Class<T> type, Annotation annotation) {
        return Multibinder.newSetBinder(binder, Key.get(type, annotation));
    }

    public static <T> Multibinder<T> newSetBinder(Binder binder, TypeLiteral<T> type, Class<? extends Annotation> annotationType) {
        return Multibinder.newSetBinder(binder, Key.get(type, annotationType));
    }

    public static <T> Multibinder<T> newSetBinder(Binder binder, Key<T> key) {
        return new Multibinder<T>(RealMultibinder.newRealSetBinder(binder, key));
    }

    public static <T> Multibinder<T> newSetBinder(Binder binder, Class<T> type, Class<? extends Annotation> annotationType) {
        return Multibinder.newSetBinder(binder, Key.get(type, annotationType));
    }

    private Multibinder(RealMultibinder<T> delegate) {
        this.delegate = delegate;
    }

    public Multibinder<T> permitDuplicates() {
        this.delegate.permitDuplicates();
        return this;
    }

    public LinkedBindingBuilder<T> addBinding() {
        return this.delegate.addBinding();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Multibinder) {
            return this.delegate.equals(((Multibinder)obj).delegate);
        }
        return false;
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }
}

