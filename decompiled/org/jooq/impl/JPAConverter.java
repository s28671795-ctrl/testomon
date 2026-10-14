/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.persistence.AttributeConverter
 */
package org.jooq.impl;

import jakarta.persistence.AttributeConverter;
import java.lang.reflect.Method;
import org.jooq.ConverterContext;
import org.jooq.exception.MappingException;
import org.jooq.impl.AbstractContextConverter;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.reflect.Reflect;

public final class JPAConverter<T, U>
extends AbstractContextConverter<T, U> {
    private static final JooqLogger log = JooqLogger.getLogger(JPAConverter.class);
    private final AttributeConverter<U, T> delegate;

    public JPAConverter(Class<? extends AttributeConverter<U, T>> klass) {
        super(JPAConverter.fromType(klass), JPAConverter.toType(klass));
        try {
            this.delegate = (AttributeConverter)Reflect.on(klass).create().get();
        }
        catch (Exception e) {
            throw new MappingException("Cannot instanciate AttributeConverter", e);
        }
    }

    private static final <T> Class<T> fromType(Class<? extends AttributeConverter<?, T>> klass) {
        Method candidate = null;
        for (Method method2 : klass.getDeclaredMethods()) {
            if (!"convertToDatabaseColumn".equals(method2.getName())) continue;
            if (method2.getReturnType() == Object.class) {
                candidate = method2;
                continue;
            }
            return method2.getReturnType();
        }
        if (candidate != null) {
            log.warn("Couldn't find bridge method to detect generic type bound for " + klass.getName() + "::convertToDatabaseColumn");
            return candidate.getReturnType();
        }
        throw new IllegalArgumentException();
    }

    private static final <U> Class<U> toType(Class<? extends AttributeConverter<U, ?>> klass) {
        Method candidate = null;
        for (Method method2 : klass.getDeclaredMethods()) {
            if (!"convertToEntityAttribute".equals(method2.getName())) continue;
            if (method2.getReturnType() == Object.class) {
                candidate = method2;
                continue;
            }
            return method2.getReturnType();
        }
        if (candidate != null) {
            log.warn("Couldn't find bridge method to detect generic type bound for " + klass.getName() + "::convertToEntityAttribute");
            return candidate.getReturnType();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public final U from(T t2, ConverterContext scope) {
        return (U)this.delegate.convertToEntityAttribute(t2);
    }

    @Override
    public final T to(U u, ConverterContext scope) {
        return (T)this.delegate.convertToDatabaseColumn(u);
    }
}

