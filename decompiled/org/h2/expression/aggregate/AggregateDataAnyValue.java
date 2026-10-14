/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import java.util.ArrayList;
import java.util.Random;
import org.h2.engine.SessionLocal;
import org.h2.expression.aggregate.AggregateData;
import org.h2.value.Value;
import org.h2.value.ValueNull;

final class AggregateDataAnyValue
extends AggregateData {
    private static final int MAX_VALUES = 256;
    ArrayList<Value> values = new ArrayList();
    private long filter = -1L;

    AggregateDataAnyValue() {
    }

    @Override
    void add(SessionLocal sessionLocal, Value value) {
        if (value == ValueNull.INSTANCE) {
            return;
        }
        long l = this.filter;
        if (l == Long.MIN_VALUE || (sessionLocal.getRandom().nextLong() | l) == l) {
            this.values.add(value);
            if (this.values.size() == 256) {
                this.compact(sessionLocal);
            }
        }
    }

    private void compact(SessionLocal sessionLocal) {
        this.filter <<= 1;
        Random random = sessionLocal.getRandom();
        int n = 0;
        for (int i = 0; i < 128; ++i) {
            int n2 = n;
            if (random.nextBoolean()) {
                ++n2;
            }
            this.values.set(i, this.values.get(n2));
            n += 2;
        }
        this.values.subList(128, 256).clear();
    }

    @Override
    Value getValue(SessionLocal sessionLocal) {
        int n = this.values.size();
        if (n == 0) {
            return ValueNull.INSTANCE;
        }
        return this.values.get(sessionLocal.getRandom().nextInt(n));
    }
}

