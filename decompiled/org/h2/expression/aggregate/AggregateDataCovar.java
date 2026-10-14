/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import org.h2.engine.SessionLocal;
import org.h2.expression.aggregate.AggregateDataBinarySet;
import org.h2.expression.aggregate.AggregateType;
import org.h2.message.DbException;
import org.h2.value.Value;
import org.h2.value.ValueDouble;
import org.h2.value.ValueNull;

final class AggregateDataCovar
extends AggregateDataBinarySet {
    private final AggregateType aggregateType;
    private long count;
    private double sumY;
    private double sumX;
    private double sumYX;

    AggregateDataCovar(AggregateType aggregateType) {
        this.aggregateType = aggregateType;
    }

    @Override
    void add(SessionLocal sessionLocal, Value value, Value value2) {
        double d = value.getDouble();
        double d2 = value2.getDouble();
        this.sumY += d;
        this.sumX += d2;
        this.sumYX += d * d2;
        ++this.count;
    }

    @Override
    Value getValue(SessionLocal sessionLocal) {
        double d;
        switch (this.aggregateType) {
            case COVAR_POP: {
                if (this.count < 1L) {
                    return ValueNull.INSTANCE;
                }
                d = (this.sumYX - this.sumX * this.sumY / (double)this.count) / (double)this.count;
                break;
            }
            case COVAR_SAMP: {
                if (this.count < 2L) {
                    return ValueNull.INSTANCE;
                }
                d = (this.sumYX - this.sumX * this.sumY / (double)this.count) / (double)(this.count - 1L);
                break;
            }
            case REGR_SXY: {
                if (this.count < 1L) {
                    return ValueNull.INSTANCE;
                }
                d = this.sumYX - this.sumX * this.sumY / (double)this.count;
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + (Object)((Object)this.aggregateType));
            }
        }
        return ValueDouble.get(d);
    }
}

