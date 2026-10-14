/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.BiFunction;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.jooq.ConverterContext;
import org.jooq.Converters;
import org.jooq.impl.AbstractContextConverter;
import org.jooq.impl.Internal;

public interface ContextConverter<T, U>
extends Converter<T, U> {
    public U from(T var1, ConverterContext var2);

    public T to(U var1, ConverterContext var2);

    @Override
    default public T to(U userObject) {
        return this.to(userObject, Internal.converterContext());
    }

    @Override
    default public U from(T databaseObject) {
        return this.from(databaseObject, Internal.converterContext());
    }

    @Override
    @NotNull
    default public ContextConverter<U, T> inverse() {
        return Converters.inverse(this);
    }

    @Override
    @NotNull
    default public <X> ContextConverter<T, X> andThen(Converter<? super U, X> converter) {
        return Converters.of(this, converter);
    }

    @NotNull
    default public ContextConverter<T[], U[]> forArrays() {
        return Converters.forArrays(this);
    }

    @NotNull
    public static <T, U> ContextConverter<T, U> scoped(final Converter<T, U> converter) {
        if (converter instanceof ContextConverter) {
            ContextConverter s2 = (ContextConverter)converter;
            return s2;
        }
        return new AbstractContextConverter<T, U>(converter.fromType(), converter.toType()){

            @Override
            public U from(T t2, ConverterContext scope) {
                return converter.from(t2);
            }

            @Override
            public T to(U u, ConverterContext scope) {
                return converter.to(u);
            }
        };
    }

    @NotNull
    public static <T, U> ContextConverter<T, U> from(Class<T> fromType, Class<U> toType, BiFunction<? super T, ? super ConverterContext, ? extends U> from) {
        return ContextConverter.of(fromType, toType, from, Converters.notImplementedBiFunction());
    }

    @NotNull
    public static <T, U> ContextConverter<T, U> to(Class<T> fromType, Class<U> toType, BiFunction<? super U, ? super ConverterContext, ? extends T> to) {
        return ContextConverter.of(fromType, toType, Converters.notImplementedBiFunction(), to);
    }

    @NotNull
    public static <T, U> ContextConverter<T, U> of(Class<T> fromType, Class<U> toType, final BiFunction<? super T, ? super ConverterContext, ? extends U> from, final BiFunction<? super U, ? super ConverterContext, ? extends T> to) {
        return new AbstractContextConverter<T, U>(fromType, toType){

            @Override
            public final U from(T t2, ConverterContext scope) {
                return from.apply(t2, scope);
            }

            @Override
            public final T to(U u, ConverterContext scope) {
                return to.apply(u, scope);
            }
        };
    }

    @NotNull
    public static <T, U> ContextConverter<T, U> ofNullable(Class<T> fromType, Class<U> toType, BiFunction<? super T, ? super ConverterContext, ? extends U> from, BiFunction<? super U, ? super ConverterContext, ? extends T> to) {
        return ContextConverter.of(fromType, toType, Converters.nullable(from), Converters.nullable(to));
    }

    @NotNull
    public static <T, U> Converter<T, U> fromNullable(Class<T> fromType, Class<U> toType, BiFunction<? super T, ? super ConverterContext, ? extends U> from) {
        return ContextConverter.of(fromType, toType, Converters.nullable(from), Converters.notImplementedBiFunction());
    }

    @NotNull
    public static <T, U> Converter<T, U> toNullable(Class<T> fromType, Class<U> toType, BiFunction<? super U, ? super ConverterContext, ? extends T> to) {
        return ContextConverter.of(fromType, toType, Converters.notImplementedBiFunction(), Converters.nullable(to));
    }
}

