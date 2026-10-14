/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.ContextConverter;
import org.jooq.ConverterContext;
import org.jooq.impl.AbstractContextConverter;

public class AutoConverter<T, U>
extends AbstractContextConverter<T, U> {
    public AutoConverter(Class<T> fromType, Class<U> toType) {
        super(fromType, toType);
    }

    @Override
    public U from(T t2, ConverterContext ctx) {
        return ContextConverter.scoped(ctx.configuration().converterProvider().provide(this.fromType(), this.toType())).from(t2, ctx);
    }

    @Override
    public T to(U u, ConverterContext ctx) {
        return ContextConverter.scoped(ctx.configuration().converterProvider().provide(this.fromType(), this.toType())).to(u, ctx);
    }

    @Override
    public String toString() {
        return "AutoConverter [ " + this.fromType().getName() + " -> " + this.toType().getName() + " ]";
    }
}

