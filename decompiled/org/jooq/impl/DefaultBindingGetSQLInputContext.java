/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.SQLInput;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.ExecuteContext;
import org.jooq.impl.AbstractExecuteScope;

class DefaultBindingGetSQLInputContext<U>
extends AbstractExecuteScope
implements BindingGetSQLInputContext<U> {
    private final SQLInput input;
    private U value;

    DefaultBindingGetSQLInputContext(ExecuteContext ctx, SQLInput input) {
        super(ctx);
        this.input = input;
    }

    @Override
    public final SQLInput input() {
        return this.input;
    }

    @Override
    public void value(U v) {
        this.value = v;
    }

    final U value() {
        return this.value;
    }

    @Override
    public final <T> BindingGetSQLInputContext<T> convert(final Converter<? super T, ? extends U> converter) {
        final DefaultBindingGetSQLInputContext outer = this;
        return new DefaultBindingGetSQLInputContext<T>(this.ctx, this.input){

            @Override
            public void value(T v) {
                outer.value(ContextConverter.scoped(converter).from(v, this.converterContext()));
            }
        };
    }

    public String toString() {
        return "DefaultBindingGetSQLInputContext [value=" + String.valueOf(this.value) + "]";
    }
}

