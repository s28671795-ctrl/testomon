/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.ConverterContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteScope;
import org.jooq.impl.AbstractScope;

abstract class AbstractExecuteScope
extends AbstractScope
implements ExecuteScope {
    final ExecuteContext ctx;

    AbstractExecuteScope(ExecuteContext ctx) {
        super(ctx.configuration(), ctx.data());
        this.ctx = ctx;
    }

    @Override
    public final ConverterContext converterContext() {
        return this.ctx.converterContext();
    }

    @Override
    public ExecuteContext executeContext() {
        return this.ctx;
    }
}

