/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import org.jooq.impl.AbstractConverter;
import org.jooq.impl.Convert;
import org.jooq.tools.reflect.Reflect;

public class EnumConverter<T, U extends Enum<U>>
extends AbstractConverter<T, U> {
    private final Map<T, U> lookup;
    private final Function<? super U, ? extends T> to;

    public EnumConverter(Class<T> fromType, Class<U> toType) {
        this(fromType, toType, Number.class.isAssignableFrom(Reflect.wrapper(fromType)) ? u -> Convert.convert((Object)u.ordinal(), fromType) : u -> Convert.convert((Object)u.name(), fromType));
    }

    public EnumConverter(Class<T> fromType, Class<U> toType, Function<? super U, ? extends T> to) {
        super(fromType, toType);
        this.to = to;
        this.lookup = new LinkedHashMap<T, U>();
        for (Enum u : (Enum[])toType.getEnumConstants()) {
            T key = this.to((U)u);
            if (key == null) continue;
            this.lookup.put(key, u);
        }
    }

    @Override
    public final U from(T t2) {
        if (t2 == null) {
            return null;
        }
        return (U)((Enum)this.lookup.get(t2));
    }

    @Override
    public T to(U u) {
        if (u == null) {
            return null;
        }
        return this.to.apply(u);
    }

    @Override
    public String toString() {
        return "EnumConverter [ " + this.fromType().getName() + " -> " + this.toType().getName() + " ]";
    }
}

