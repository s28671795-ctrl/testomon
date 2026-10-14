/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.Serializable;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jooq.ConverterContext;
import org.jooq.Converters;
import org.jooq.impl.AbstractContextConverter;

public interface Converter<T, U>
extends Serializable {
    public U from(T var1);

    public T to(U var1);

    @NotNull
    public Class<T> fromType();

    @NotNull
    public Class<U> toType();

    @NotNull
    default public Converter<U, T> inverse() {
        return Converters.inverse(this);
    }

    @NotNull
    default public <X> Converter<T, X> andThen(Converter<? super U, X> converter) {
        return Converters.of(this, converter);
    }

    @NotNull
    default public Converter<T[], U[]> forArrays() {
        return Converters.forArrays(this);
    }

    @NotNull
    public static <T, U> Converter<T, U> of(Class<T> fromType, Class<U> toType, final Function<? super T, ? extends U> from, final Function<? super U, ? extends T> to) {
        return new AbstractContextConverter<T, U>(fromType, toType){

            @Override
            public final U from(T t2, ConverterContext scope) {
                return from.apply(t2);
            }

            @Override
            public final T to(U u, ConverterContext scope) {
                return to.apply(u);
            }
        };
    }

    @NotNull
    public static <T, U> Converter<T, U> from(Class<T> fromType, Class<U> toType, Function<? super T, ? extends U> from) {
        return Converter.of(fromType, toType, from, Converters.notImplemented());
    }

    @NotNull
    public static <T, U> Converter<T, U> to(Class<T> fromType, Class<U> toType, Function<? super U, ? extends T> to) {
        return Converter.of(fromType, toType, Converters.notImplemented(), to);
    }

    @NotNull
    public static <T, U> Converter<T, U> ofNullable(Class<T> fromType, Class<U> toType, Function<? super T, ? extends U> from, Function<? super U, ? extends T> to) {
        return Converter.of(fromType, toType, Converters.nullable(from), Converters.nullable(to));
    }

    @NotNull
    public static <T, U> Converter<T, U> fromNullable(Class<T> fromType, Class<U> toType, Function<? super T, ? extends U> from) {
        return Converter.of(fromType, toType, Converters.nullable(from), Converters.notImplemented());
    }

    @NotNull
    public static <T, U> Converter<T, U> toNullable(Class<T> fromType, Class<U> toType, Function<? super U, ? extends T> to) {
        return Converter.of(fromType, toType, Converters.notImplemented(), Converters.nullable(to));
    }
}

