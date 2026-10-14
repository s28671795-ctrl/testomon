/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.ContextConverter;
import org.jooq.ConverterContext;

public final class IdentityConverter<T>
implements ContextConverter<T, T> {
    private final Class<T> type;

    public IdentityConverter(Class<T> type) {
        this.type = type;
    }

    @Override
    public final T from(T t2, ConverterContext scope) {
        return t2;
    }

    @Override
    public final T to(T t2, ConverterContext scope) {
        return t2;
    }

    @Override
    public final Class<T> fromType() {
        return this.type;
    }

    @Override
    public final Class<T> toType() {
        return this.type;
    }

    public String toString() {
        return "IdentityConverter [ " + this.fromType().getName() + " ]";
    }
}

