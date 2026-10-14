/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.ContextConverter;
import org.jooq.ConverterContext;
import org.jooq.Converters;
import org.jooq.impl.AbstractContextConverter;
import org.jooq.impl.Internal;
import org.jooq.tools.Convert;

final class ArrayConverter<T, U>
extends AbstractContextConverter<T[], U[]> {
    final ContextConverter<T, U> converter;
    final ContextConverter<U, T> inverse;

    public ArrayConverter(ContextConverter<T, U> converter) {
        super(Internal.arrayType(converter.fromType()), Internal.arrayType(converter.toType()));
        this.converter = converter;
        this.inverse = Converters.inverse(converter);
    }

    @Override
    public final U[] from(T[] t2, ConverterContext scope) {
        return Convert.convertArray((Object[])t2, this.converter);
    }

    @Override
    public final T[] to(U[] t2, ConverterContext scope) {
        return Convert.convertArray((Object[])t2, this.inverse);
    }
}

