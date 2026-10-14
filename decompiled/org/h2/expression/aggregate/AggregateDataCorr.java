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

final class AggregateDataCorr
extends AggregateDataBinarySet {
    private final AggregateType aggregateType;
    private long count;
    private double sumY;
    private double sumX;
    private double sumYX;
    private double m2y;
    private double meanY;
    private double m2x;
    private double meanX;

    AggregateDataCorr(AggregateType aggregateType) {
        this.aggregateType = aggregateType;
    }

    @Override
    void add(SessionLocal sessionLocal, Value value, Value value2) {
        double d = value.getDouble();
        double d2 = value2.getDouble();
        this.sumY += d;
        this.sumX += d2;
        this.sumYX += d * d2;
        if (++this.count == 1L) {
            this.meanY = d;
            this.meanX = d2;
            this.m2y = 0.0;
            this.m2x = 0.0;
        } else {
            double d3 = d - this.meanY;
            this.meanY += d3 / (double)this.count;
            this.m2y += d3 * (d - this.meanY);
            d3 = d2 - this.meanX;
            this.meanX += d3 / (double)this.count;
            this.m2x += d3 * (d2 - this.meanX);
        }
    }

    @Override
    Value getValue(SessionLocal sessionLocal) {
        double d;
        if (this.count < 1L) {
            return ValueNull.INSTANCE;
        }
        switch (this.aggregateType) {
            case CORR: {
                if (this.m2y == 0.0 || this.m2x == 0.0) {
                    return ValueNull.INSTANCE;
                }
                d = (this.sumYX - this.sumX * this.sumY / (double)this.count) / Math.sqrt(this.m2y * this.m2x);
                break;
            }
            case REGR_SLOPE: {
                if (this.m2x == 0.0) {
                    return ValueNull.INSTANCE;
                }
                d = (this.sumYX - this.sumX * this.sumY / (double)this.count) / this.m2x;
                break;
            }
            case REGR_INTERCEPT: {
                if (this.m2x == 0.0) {
                    return ValueNull.INSTANCE;
                }
                d = this.meanY - (this.sumYX - this.sumX * this.sumY / (double)this.count) / this.m2x * this.meanX;
                break;
            }
            case REGR_R2: {
                if (this.m2x == 0.0) {
                    return ValueNull.INSTANCE;
                }
                if (this.m2y == 0.0) {
                    return ValueDouble.ONE;
                }
                d = this.sumYX - this.sumX * this.sumY / (double)this.count;
                d = d * d / (this.m2y * this.m2x);
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + (Object)((Object)this.aggregateType));
            }
        }
        return ValueDouble.get(d);
    }
}

