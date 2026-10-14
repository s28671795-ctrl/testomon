/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.sql.Array;
import java.sql.Struct;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;
import org.jooq.ArrayRecord;
import org.jooq.Converter;
import org.jooq.ConverterProvider;
import org.jooq.EnumType;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.QualifiedRecord;
import org.jooq.Record;
import org.jooq.XML;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.Convert;
import org.jooq.impl.Tools;
import org.jooq.tools.reflect.Reflect;

public final class DefaultConverterProvider
implements ConverterProvider,
Serializable {
    @Override
    @Nullable
    public final <T, U> Converter<T, U> provide(Class<T> tType, Class<U> uType) {
        Class uWrapper;
        Class tWrapper = Reflect.wrapper(tType);
        if (tWrapper == (uWrapper = Reflect.wrapper(uType)) || uWrapper.isAssignableFrom(tWrapper) || this.isCollection(tWrapper) && this.isCollection(uWrapper) || tWrapper == Optional.class || uWrapper == Optional.class || uWrapper == String.class || uWrapper == byte[].class || uWrapper == ByteBuffer.class || Number.class.isAssignableFrom(uWrapper) || Boolean.class.isAssignableFrom(uWrapper) || Character.class.isAssignableFrom(uWrapper) || uWrapper == URI.class && tWrapper == String.class || uWrapper == URL.class && tWrapper == String.class || uWrapper == File.class && tWrapper == String.class || this.isDate(tWrapper) && this.isDate(uWrapper) || this.isEnum(tWrapper) && this.isEnum(uWrapper) || this.isUUID(tWrapper) && this.isUUID(uWrapper) || this.isJSON(tWrapper) || this.isXML(tWrapper) || Record.class.isAssignableFrom(tWrapper) || Struct.class.isAssignableFrom(tWrapper) && QualifiedRecord.class.isAssignableFrom(uWrapper) || Tools.findAny(uWrapper.getDeclaredConstructors(), c -> {
            Class<?>[] types = c.getParameterTypes();
            return types.length == 1 && types[0] != uWrapper && this.provide(tType, types[0]) != null;
        }) != null) {
            return Converter.of(tType, uType, t2 -> Convert.convert(t2, uType), u -> Convert.convert(u, tType));
        }
        if (tWrapper.isAssignableFrom(uWrapper)) {
            return Converter.ofNullable(tWrapper, uWrapper, t2 -> {
                if (uWrapper.isInstance(t2)) {
                    return uWrapper.cast(t2);
                }
                throw new DataTypeException("Cannot cast from " + String.valueOf(tWrapper) + " (instance type: " + String.valueOf(t2.getClass()) + " to " + String.valueOf(tWrapper));
            }, u -> tWrapper.cast(u));
        }
        return null;
    }

    private final boolean isJSON(Class<?> type) {
        return type == JSON.class || type == JSONB.class;
    }

    private final boolean isXML(Class<?> type) {
        return type == XML.class;
    }

    private final boolean isUUID(Class<?> type) {
        return type == String.class || type == byte[].class || type == UUID.class;
    }

    private final boolean isEnum(Class<?> type) {
        return Enum.class.isAssignableFrom(type) || type == String.class || EnumType.class.isAssignableFrom(type);
    }

    private final boolean isDate(Class<?> type) {
        return Date.class.isAssignableFrom(type) || Calendar.class.isAssignableFrom(type) || Temporal.class.isAssignableFrom(type) || type == Long.class || type == String.class;
    }

    private final boolean isCollection(Class<?> type) {
        return type.isArray() || Collection.class.isAssignableFrom(type) || ArrayRecord.class.isAssignableFrom(type) || type == Array.class;
    }
}

