/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.ContextConverter;
import org.jooq.ConverterContext;
import org.jooq.impl.AbstractContextConverter;

public class DelegatingConverter<T, U>
extends AbstractContextConverter<T, U> {
    private final ContextConverter<T, U> delegate;

    public DelegatingConverter(ContextConverter<T, U> delegate) {
        super(delegate.fromType(), delegate.toType());
        this.delegate = delegate;
    }

    @Override
    public final U from(T t2, ConverterContext scope) {
        return this.delegate.from(t2, scope);
    }

    @Override
    public final U from(T t2) {
        return this.delegate.from(t2);
    }

    @Override
    public final T to(U u, ConverterContext scope) {
        return this.delegate.to(u, scope);
    }

    @Override
    public final T to(U u) {
        return this.delegate.to(u);
    }

    @Override
    public String toString() {
        return "Converter [ " + this.fromType().getName() + " -> " + this.toType().getName() + " ]";
    }
}

