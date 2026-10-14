/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import org.h2.engine.SessionLocal;
import org.h2.expression.aggregate.AggregateData;
import org.h2.expression.aggregate.AggregateType;
import org.h2.expression.function.BitFunction;
import org.h2.message.DbException;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;

final class AggregateDataDefault
extends AggregateData {
    private final AggregateType aggregateType;
    private final TypeInfo dataType;
    private Value value;

    AggregateDataDefault(AggregateType aggregateType, TypeInfo typeInfo) {
        this.aggregateType = aggregateType;
        this.dataType = typeInfo;
    }

    @Override
    void add(SessionLocal sessionLocal, Value value) {
        if (value == ValueNull.INSTANCE) {
            return;
        }
        switch (this.aggregateType) {
            case SUM: {
                if (this.value == null) {
                    this.value = value.convertTo(this.dataType.getValueType());
                    break;
                }
                value = value.convertTo(this.value.getValueType());
                this.value = this.value.add(value);
                break;
            }
            case MIN: {
                if (this.value != null && sessionLocal.compare(value, this.value) >= 0) break;
                this.value = value;
                break;
            }
            case MAX: {
                if (this.value != null && sessionLocal.compare(value, this.value) <= 0) break;
                this.value = value;
                break;
            }
            case EVERY: {
                value = value.convertToBoolean();
                if (this.value == null) {
                    this.value = value;
                    break;
                }
                this.value = ValueBoolean.get(this.value.getBoolean() && value.getBoolean());
                break;
            }
            case ANY: {
                value = value.convertToBoolean();
                if (this.value == null) {
                    this.value = value;
                    break;
                }
                this.value = ValueBoolean.get(this.value.getBoolean() || value.getBoolean());
                break;
            }
            case BIT_AND_AGG: 
            case BIT_NAND_AGG: {
                if (this.value == null) {
                    this.value = value;
                    break;
                }
                this.value = BitFunction.getBitwise(0, this.dataType, this.value, value);
                break;
            }
            case BIT_OR_AGG: 
            case BIT_NOR_AGG: {
                if (this.value == null) {
                    this.value = value;
                    break;
                }
                this.value = BitFunction.getBitwise(1, this.dataType, this.value, value);
                break;
            }
            case BIT_XOR_AGG: 
            case BIT_XNOR_AGG: {
                if (this.value == null) {
                    this.value = value;
                    break;
                }
                this.value = BitFunction.getBitwise(2, this.dataType, this.value, value);
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + (Object)((Object)this.aggregateType));
            }
        }
    }

    @Override
    Value getValue(SessionLocal sessionLocal) {
        Value value = this.value;
        if (value == null) {
            return ValueNull.INSTANCE;
        }
        switch (this.aggregateType) {
            case BIT_NAND_AGG: 
            case BIT_NOR_AGG: 
            case BIT_XNOR_AGG: {
                value = BitFunction.getBitwise(3, this.dataType, value, null);
            }
        }
        return value.convertTo(this.dataType);
    }
}

