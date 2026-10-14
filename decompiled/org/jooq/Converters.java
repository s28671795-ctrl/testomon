/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.Serializable;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.ArrayComponentConverter;
import org.jooq.ArrayConverter;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.ConverterContext;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.AbstractContextConverter;
import org.jooq.impl.IdentityConverter;

public final class Converters<T, U>
extends AbstractContextConverter<T, U> {
    final ContextConverter[] chain;

    @NotNull
    public static <T> ContextConverter<T, T> identity(Class<T> type) {
        return new IdentityConverter<T>(type);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @NotNull
    public static <T, U> ContextConverter<T, U> of() {
        return new Converters<T, U>(new ContextConverter[0]);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @NotNull
    public static <T, U> ContextConverter<T, U> of(Converter<T, U> converter) {
        return new Converters<T, U>(ContextConverter.scoped(converter));
    }

    @NotNull
    public static <T, X1, U> ContextConverter<T, U> of(Converter<T, ? extends X1> c1, Converter<? super X1, U> c2) {
        return new Converters<T, U>(ContextConverter.scoped(c1), ContextConverter.scoped(c2));
    }

    @NotNull
    public static <T, X1, X2, U> ContextConverter<T, U> of(Converter<T, ? extends X1> c1, Converter<? super X1, ? extends X2> c2, Converter<? super X2, U> c3) {
        return new Converters<T, U>(ContextConverter.scoped(c1), ContextConverter.scoped(c2), ContextConverter.scoped(c3));
    }

    @NotNull
    public static <T, X1, X2, X3, U> ContextConverter<T, U> of(Converter<T, ? extends X1> c1, Converter<? super X1, ? extends X2> c2, Converter<? super X2, ? extends X3> c3, Converter<? super X3, U> c4) {
        return new Converters<T, U>(ContextConverter.scoped(c1), ContextConverter.scoped(c2), ContextConverter.scoped(c3), ContextConverter.scoped(c4));
    }

    public static <T, U> Converter<U, T> inverse(Converter<T, U> converter) {
        return Converters.inverse(ContextConverter.scoped(converter));
    }

    public static <T, U> ContextConverter<U, T> inverse(ContextConverter<T, U> converter) {
        if (converter instanceof IdentityConverter) {
            return converter;
        }
        return ContextConverter.of(converter.toType(), converter.fromType(), converter::to, converter::from);
    }

    public static <T, U> Converter<T[], U[]> forArrays(Converter<T, U> converter) {
        return Converters.forArrays(ContextConverter.scoped(converter));
    }

    public static <T, U> ContextConverter<T[], U[]> forArrays(ContextConverter<T, U> converter) {
        if (converter instanceof ArrayComponentConverter) {
            ArrayComponentConverter a = (ArrayComponentConverter)converter;
            return a.converter;
        }
        return new ArrayConverter<T, U>(converter);
    }

    public static <T, U> Converter<T, U> forArrayComponents(Converter<T[], U[]> converter) {
        return Converters.forArrayComponents(ContextConverter.scoped(converter));
    }

    public static <T, U> Converter<T, U> forArrayComponents(ContextConverter<T[], U[]> converter) {
        if (converter instanceof ArrayConverter) {
            ArrayConverter a = (ArrayConverter)converter;
            return a.converter;
        }
        return new ArrayComponentConverter(converter);
    }

    Converters(ContextConverter ... chain) {
        super(chain[0].fromType(), chain[chain.length - 1].toType());
        this.chain = chain;
    }

    @Override
    public final U from(T t2, ConverterContext scope) {
        Object result = t2;
        for (int i = 0; i < this.chain.length; ++i) {
            result = this.chain[i].from(result, scope);
        }
        return (U)result;
    }

    @Override
    public final T to(U u, ConverterContext scope) {
        Object result = u;
        for (int i = this.chain.length - 1; i >= 0; --i) {
            result = this.chain[i].to(result, scope);
        }
        return (T)result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String separator = " -> ";
        sb.append("Converters [ ");
        sb.append(this.fromType().getName());
        for (ContextConverter converter : this.chain) {
            sb.append(separator);
            sb.append(converter.toType().getName());
        }
        sb.append(" ]");
        return sb.toString();
    }

    static final <T, U> Function<T, U> nullable(Function<? super T, ? extends U> f) {
        return f instanceof Serializable ? (Function<Object, Object> & Serializable)t2 -> t2 == null ? null : f.apply(t2) : t2 -> t2 == null ? null : f.apply(t2);
    }

    static final <T, U> BiFunction<T, ConverterContext, U> nullable(BiFunction<? super T, ? super ConverterContext, ? extends U> f) {
        return f instanceof Serializable ? (BiFunction<Object, ConverterContext, Object> & Serializable)(t2, x) -> t2 == null ? null : f.apply((Object)t2, (ConverterContext)x) : (t2, x) -> t2 == null ? null : f.apply((Object)t2, (ConverterContext)x);
    }

    static final <T, U> Function<T, U> notImplemented() {
        return t2 -> {
            throw new DataTypeException("Conversion function not implemented");
        };
    }

    static final <T, U> BiFunction<T, ConverterContext, U> notImplementedBiFunction() {
        return (t2, x) -> {
            throw new DataTypeException("Conversion function not implemented");
        };
    }

    @ApiStatus.Internal
    public static final class UnknownType {
        private UnknownType() {
        }
    }
}

