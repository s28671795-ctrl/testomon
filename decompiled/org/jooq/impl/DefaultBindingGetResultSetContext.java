/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.ResultSet;
import org.jooq.BindingGetResultSetContext;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.ExecuteContext;
import org.jooq.Field;
import org.jooq.impl.AbstractExecuteScope;

class DefaultBindingGetResultSetContext<U>
extends AbstractExecuteScope
implements BindingGetResultSetContext<U> {
    private final ResultSet resultSet;
    private int index;
    private Field<U> field;
    private U value;

    DefaultBindingGetResultSetContext(ExecuteContext ctx, ResultSet resultSet, int index) {
        super(ctx);
        this.resultSet = resultSet;
        this.index = index;
    }

    @Override
    public final ResultSet resultSet() {
        return this.resultSet;
    }

    @Override
    public final int index() {
        return this.index;
    }

    final void index(int i) {
        this.index = i;
    }

    @Override
    public final Field<U> field() {
        return this.field;
    }

    final void field(Field<U> f) {
        this.field = f;
    }

    @Override
    public void value(U v) {
        this.value = v;
    }

    final U value() {
        return this.value;
    }

    @Override
    public final <T> BindingGetResultSetContext<T> convert(final Converter<? super T, ? extends U> converter) {
        final DefaultBindingGetResultSetContext outer = this;
        return new DefaultBindingGetResultSetContext<T>(this.ctx, this.resultSet, this.index){

            @Override
            public void value(T v) {
                outer.value(ContextConverter.scoped(converter).from(v, this.converterContext()));
            }
        };
    }

    public String toString() {
        return "DefaultBindingGetResultSetContext [index=" + this.index + ", value=" + String.valueOf(this.value) + "]";
    }
}

