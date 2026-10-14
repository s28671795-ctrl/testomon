/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.PreparedStatement;
import org.jooq.BindingSetStatementContext;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.ExecuteContext;
import org.jooq.impl.AbstractExecuteScope;
import org.jooq.impl.ResourceManagingScopeTrait;

class DefaultBindingSetStatementContext<U>
extends AbstractExecuteScope
implements BindingSetStatementContext<U>,
ResourceManagingScopeTrait {
    private final PreparedStatement statement;
    private final int index;
    private final U value;

    DefaultBindingSetStatementContext(ExecuteContext ctx, PreparedStatement statement, int index, U value) {
        super(ctx);
        this.statement = statement;
        this.index = index;
        this.value = value;
    }

    @Override
    public final PreparedStatement statement() {
        return this.statement;
    }

    @Override
    public final int index() {
        return this.index;
    }

    @Override
    public final U value() {
        return this.value;
    }

    @Override
    public final <T> BindingSetStatementContext<T> convert(Converter<? extends T, ? super U> converter) {
        return new DefaultBindingSetStatementContext<T>(this.ctx, this.statement, this.index, ContextConverter.scoped(converter).to(this.value, this.converterContext()));
    }

    public String toString() {
        return "DefaultBindingSetStatementContext [index=" + this.index + ", value=" + String.valueOf(this.value) + "]";
    }
}

