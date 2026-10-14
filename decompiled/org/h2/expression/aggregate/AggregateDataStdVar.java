/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import org.h2.engine.SessionLocal;
import org.h2.expression.aggregate.AggregateData;
import org.h2.expression.aggregate.AggregateType;
import org.h2.message.DbException;
import org.h2.value.Value;
import org.h2.value.ValueDouble;
import org.h2.value.ValueNull;

final class AggregateDataStdVar
extends AggregateData {
    private final AggregateType aggregateType;
    private long count;
    private double m2;
    private double mean;

    AggregateDataStdVar(AggregateType aggregateType) {
        this.aggregateType = aggregateType;
    }

    @Override
    void add(SessionLocal sessionLocal, Value value) {
        if (value == ValueNull.INSTANCE) {
            return;
        }
        double d = value.getDouble();
        if (++this.count == 1L) {
            this.mean = d;
            this.m2 = 0.0;
        } else {
            double d2 = d - this.mean;
            this.mean += d2 / (double)this.count;
            this.m2 += d2 * (d - this.mean);
        }
    }

    @Override
    Value getValue(SessionLocal sessionLocal) {
        double d;
        switch (this.aggregateType) {
            case STDDEV_SAMP: 
            case VAR_SAMP: {
                if (this.count < 2L) {
                    return ValueNull.INSTANCE;
                }
                d = this.m2 / (double)(this.count - 1L);
                if (this.aggregateType != AggregateType.STDDEV_SAMP) break;
                d = Math.sqrt(d);
                break;
            }
            case STDDEV_POP: 
            case VAR_POP: {
                if (this.count < 1L) {
                    return ValueNull.INSTANCE;
                }
                d = this.m2 / (double)this.count;
                if (this.aggregateType != AggregateType.STDDEV_POP) break;
                d = Math.sqrt(d);
                break;
            }
            case REGR_SXX: 
            case REGR_SYY: {
                if (this.count < 1L) {
                    return ValueNull.INSTANCE;
                }
                d = this.m2;
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + (Object)((Object)this.aggregateType));
            }
        }
        return ValueDouble.get(d);
    }
}

