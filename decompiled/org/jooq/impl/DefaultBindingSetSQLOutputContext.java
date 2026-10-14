/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.SQLOutput;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.ExecuteContext;
import org.jooq.impl.AbstractExecuteScope;
import org.jooq.impl.ResourceManagingScopeTrait;

class DefaultBindingSetSQLOutputContext<U>
extends AbstractExecuteScope
implements BindingSetSQLOutputContext<U>,
ResourceManagingScopeTrait {
    private final SQLOutput output;
    private final U value;

    DefaultBindingSetSQLOutputContext(ExecuteContext ctx, SQLOutput output, U value) {
        super(ctx);
        this.output = output;
        this.value = value;
    }

    @Override
    public final SQLOutput output() {
        return this.output;
    }

    @Override
    public final U value() {
        return this.value;
    }

    @Override
    public final <T> BindingSetSQLOutputContext<T> convert(Converter<? extends T, ? super U> converter) {
        return new DefaultBindingSetSQLOutputContext<T>(this.ctx, this.output, ContextConverter.scoped(converter).to(this.value, this.converterContext()));
    }

    public String toString() {
        return "DefaultBindingSetSQLOutputContext [value=" + String.valueOf(this.value) + "]";
    }
}

