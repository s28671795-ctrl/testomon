/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;

public abstract class Operation0
extends Expression {
    protected Operation0() {
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        return this;
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
    }
}

