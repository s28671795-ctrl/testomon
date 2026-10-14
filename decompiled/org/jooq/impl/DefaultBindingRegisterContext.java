/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.CallableStatement;
import org.jooq.BindingRegisterContext;
import org.jooq.Converter;
import org.jooq.ExecuteContext;
import org.jooq.impl.AbstractExecuteScope;

class DefaultBindingRegisterContext<U>
extends AbstractExecuteScope
implements BindingRegisterContext<U> {
    private final CallableStatement statement;
    private final int index;

    DefaultBindingRegisterContext(ExecuteContext ctx, CallableStatement statement, int index) {
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
    public final <T> BindingRegisterContext<T> convert(Converter<? super T, ? extends U> converter) {
        return new DefaultBindingRegisterContext<U>(this.ctx, this.statement, this.index);
    }

    public String toString() {
        return "DefaultBindingRegisterContext [index=" + this.index + "]";
    }
}

