/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import org.h2.engine.SessionLocal;
import org.h2.expression.aggregate.AggregateData;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueNull;

final class AggregateDataCount
extends AggregateData {
    private final boolean all;
    private long count;

    AggregateDataCount(boolean bl) {
        this.all = bl;
    }

    @Override
    void add(SessionLocal sessionLocal, Value value) {
        if (this.all || value != ValueNull.INSTANCE) {
            ++this.count;
        }
    }

    @Override
    Value getValue(SessionLocal sessionLocal) {
        return ValueBigint.get(this.count);
    }
}

