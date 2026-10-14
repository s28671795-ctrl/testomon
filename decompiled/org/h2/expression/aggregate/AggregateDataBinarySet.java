/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import org.h2.engine.SessionLocal;
import org.h2.expression.aggregate.AggregateData;
import org.h2.message.DbException;
import org.h2.value.Value;

abstract class AggregateDataBinarySet
extends AggregateData {
    AggregateDataBinarySet() {
    }

    abstract void add(SessionLocal var1, Value var2, Value var3);

    @Override
    final void add(SessionLocal sessionLocal, Value value) {
        throw DbException.getInternalError();
    }
}

