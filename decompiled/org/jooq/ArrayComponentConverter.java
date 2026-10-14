/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.lang.reflect.Array;
import org.jooq.ContextConverter;
import org.jooq.ConverterContext;
import org.jooq.impl.AbstractContextConverter;

final class ArrayComponentConverter<T, U>
extends AbstractContextConverter<T, U> {
    final ContextConverter<T[], U[]> converter;

    public ArrayComponentConverter(ContextConverter<T[], U[]> converter) {
        super(converter.fromType().getComponentType(), converter.toType().getComponentType());
        this.converter = converter;
    }

    @Override
    public final U from(T t2, ConverterContext scope) {
        if (t2 == null) {
            return null;
        }
        Object[] a = (Object[])Array.newInstance(this.fromType(), 1);
        a[0] = t2;
        return this.converter.from(a, scope)[0];
    }

    @Override
    public final T to(U u, ConverterContext scope) {
        if (u == null) {
            return null;
        }
        Object[] a = (Object[])Array.newInstance(this.fromType(), 1);
        a[0] = u;
        return this.converter.to(a, scope)[0];
    }
}

