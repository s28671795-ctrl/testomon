/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import java.util.TreeMap;
import org.h2.engine.SessionLocal;
import org.h2.expression.aggregate.AggregateData;
import org.h2.expression.aggregate.LongDataCounter;
import org.h2.value.Value;
import org.h2.value.ValueNull;

final class AggregateDataDistinctWithCounts
extends AggregateData {
    private final boolean ignoreNulls;
    private final int maxDistinctCount;
    private TreeMap<Value, LongDataCounter> values;

    AggregateDataDistinctWithCounts(boolean bl, int n) {
        this.ignoreNulls = bl;
        this.maxDistinctCount = n;
    }

    @Override
    void add(SessionLocal sessionLocal, Value value) {
        LongDataCounter longDataCounter;
        if (this.ignoreNulls && value == ValueNull.INSTANCE) {
            return;
        }
        if (this.values == null) {
            this.values = new TreeMap(sessionLocal.getDatabase().getCompareMode());
        }
        if ((longDataCounter = this.values.get(value)) == null) {
            if (this.values.size() >= this.maxDistinctCount) {
                return;
            }
            longDataCounter = new LongDataCounter();
            this.values.put(value, longDataCounter);
        }
        ++longDataCounter.count;
    }

    @Override
    Value getValue(SessionLocal sessionLocal) {
        return null;
    }

    TreeMap<Value, LongDataCounter> getValues() {
        return this.values;
    }
}

