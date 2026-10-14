/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Converter;

public abstract class AbstractConverter<T, U>
implements Converter<T, U> {
    private final Class<T> fromType;
    private final Class<U> toType;

    public AbstractConverter(Class<T> fromType, Class<U> toType) {
        this.fromType = fromType;
        this.toType = toType;
    }

    @Override
    public final Class<T> fromType() {
        return this.fromType;
    }

    @Override
    public final Class<U> toType() {
        return this.toType;
    }

    public String toString() {
        return "Converter [ " + this.fromType().getName() + " -> " + this.toType().getName() + " ]";
    }
}

