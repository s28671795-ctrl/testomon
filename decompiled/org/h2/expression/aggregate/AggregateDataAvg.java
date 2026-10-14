/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import org.h2.api.IntervalQualifier;
import org.h2.engine.SessionLocal;
import org.h2.expression.aggregate.AggregateData;
import org.h2.util.IntervalUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueDecfloat;
import org.h2.value.ValueDouble;
import org.h2.value.ValueInterval;
import org.h2.value.ValueNull;
import org.h2.value.ValueNumeric;

final class AggregateDataAvg
extends AggregateData {
    private final TypeInfo dataType;
    private long count;
    private double doubleValue;
    private BigDecimal decimalValue;
    private BigInteger integerValue;

    AggregateDataAvg(TypeInfo typeInfo) {
        this.dataType = typeInfo;
    }

    @Override
    void add(SessionLocal sessionLocal, Value value) {
        if (value == ValueNull.INSTANCE) {
            return;
        }
        ++this.count;
        switch (this.dataType.getValueType()) {
            case 15: {
                this.doubleValue += value.getDouble();
                break;
            }
            case 13: 
            case 16: {
                BigDecimal bigDecimal = value.getBigDecimal();
                this.decimalValue = this.decimalValue == null ? bigDecimal : this.decimalValue.add(bigDecimal);
                break;
            }
            default: {
                BigInteger bigInteger = IntervalUtils.intervalToAbsolute((ValueInterval)value);
                this.integerValue = this.integerValue == null ? bigInteger : this.integerValue.add(bigInteger);
            }
        }
    }

    @Override
    Value getValue(SessionLocal sessionLocal) {
        Value value;
        if (this.count == 0L) {
            return ValueNull.INSTANCE;
        }
        int n = this.dataType.getValueType();
        switch (n) {
            case 15: {
                value = ValueDouble.get(this.doubleValue / (double)this.count);
                break;
            }
            case 13: {
                value = ValueNumeric.get(this.decimalValue.divide(BigDecimal.valueOf(this.count), this.dataType.getScale(), RoundingMode.HALF_DOWN));
                break;
            }
            case 16: {
                value = ValueDecfloat.divide(this.decimalValue, BigDecimal.valueOf(this.count), this.dataType);
                break;
            }
            default: {
                value = IntervalUtils.intervalFromAbsolute(IntervalQualifier.valueOf(n - 22), this.integerValue.divide(BigInteger.valueOf(this.count)));
            }
        }
        return value.castTo(this.dataType, sessionLocal);
    }
}

