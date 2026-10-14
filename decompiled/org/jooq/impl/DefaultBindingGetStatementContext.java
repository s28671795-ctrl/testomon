/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.CallableStatement;
import org.jooq.BindingGetStatementContext;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.ExecuteContext;
import org.jooq.impl.AbstractExecuteScope;

class DefaultBindingGetStatementContext<U>
extends AbstractExecuteScope
implements BindingGetStatementContext<U> {
    private final CallableStatement statement;
    private final int index;
    private U value;

    DefaultBindingGetStatementContext(ExecuteContext ctx, CallableStatement statement, int index) {
        super(ctx);
        this.statement = statement;
        this.index = index;
    }

    @Override
    public final CallableStatement statement() {
        return this.statement;
    }

    @Override
    public final int index() {
        return this.index;
    }

    @Override
    public void value(U v) {
        this.value = v;
    }

    final U value() {
        return this.value;
    }

    @Override
    public final <T> BindingGetStatementContext<T> convert(final Converter<? super T, ? extends U> converter) {
        final DefaultBindingGetStatementContext outer = this;
        return new DefaultBindingGetStatementContext<T>(this.ctx, this.statement, this.index){

            @Override
            public void value(T v) {
                outer.value(ContextConverter.scoped(converter).from(v, this.converterContext()));
            }
        };
    }

    public String toString() {
        return "DefaultBindingGetStatementContext [index=" + this.index + ", value=" + String.valueOf(this.value) + "]";
    }
}

