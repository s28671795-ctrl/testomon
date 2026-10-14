/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ValueExpression;
import org.h2.message.DbException;
import org.h2.schema.Sequence;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueNull;

public class SequenceOptions {
    private TypeInfo dataType;
    private Expression start;
    private Expression restart;
    private Expression increment;
    private Expression maxValue;
    private Expression minValue;
    private Sequence.Cycle cycle;
    private Expression cacheSize;
    private long[] bounds;
    private final Sequence oldSequence;

    private static Long getLong(SessionLocal sessionLocal, Expression expression) {
        Value value;
        if (expression != null && (value = expression.optimize(sessionLocal).getValue(sessionLocal)) != ValueNull.INSTANCE) {
            return value.getLong();
        }
        return null;
    }

    public SequenceOptions() {
        this.oldSequence = null;
    }

    public SequenceOptions(Sequence sequence2, TypeInfo typeInfo) {
        this.oldSequence = sequence2;
        this.dataType = typeInfo;
        this.getBounds();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TypeInfo getDataType() {
        if (this.oldSequence != null) {
            Sequence sequence2 = this.oldSequence;
            synchronized (sequence2) {
                this.copyFromOldSequence();
            }
        }
        return this.dataType;
    }

    private void copyFromOldSequence() {
        long[] lArray = this.getBounds();
        long l = Math.max(this.oldSequence.getMinValue(), lArray[0]);
        long l2 = Math.min(this.oldSequence.getMaxValue(), lArray[1]);
        if (l2 < l) {
            l = lArray[0];
            l2 = lArray[1];
        }
        this.minValue = ValueExpression.get(ValueBigint.get(l));
        this.maxValue = ValueExpression.get(ValueBigint.get(l2));
        long l3 = this.oldSequence.getStartValue();
        if (l3 >= l && l3 <= l2) {
            this.start = ValueExpression.get(ValueBigint.get(l3));
        }
        if ((l3 = this.oldSequence.getBaseValue()) >= l && l3 <= l2) {
            this.restart = ValueExpression.get(ValueBigint.get(l3));
        }
        this.increment = ValueExpression.get(ValueBigint.get(this.oldSequence.getIncrement()));
        this.cycle = this.oldSequence.getCycle();
        this.cacheSize = ValueExpression.get(ValueBigint.get(this.oldSequence.getCacheSize()));
    }

    public void setDataType(TypeInfo typeInfo) {
        this.dataType = typeInfo;
    }

    public Long getStartValue(SessionLocal sessionLocal) {
        return this.check(SequenceOptions.getLong(sessionLocal, this.start));
    }

    public void setStartValue(Expression expression) {
        this.start = expression;
    }

    public Long getRestartValue(SessionLocal sessionLocal, long l) {
        return this.check(this.restart == ValueExpression.DEFAULT ? Long.valueOf(l) : SequenceOptions.getLong(sessionLocal, this.restart));
    }

    public void setRestartValue(Expression expression) {
        this.restart = expression;
    }

    public Long getIncrement(SessionLocal sessionLocal) {
        return this.check(SequenceOptions.getLong(sessionLocal, this.increment));
    }

    public void setIncrement(Expression expression) {
        this.increment = expression;
    }

    public Long getMaxValue(Sequence sequence2, SessionLocal sessionLocal) {
        Long l = this.maxValue == ValueExpression.NULL && sequence2 != null ? Long.valueOf(Sequence.getDefaultMaxValue(this.getCurrentStart(sequence2, sessionLocal), this.increment != null ? this.getIncrement(sessionLocal).longValue() : sequence2.getIncrement(), this.getBounds())) : SequenceOptions.getLong(sessionLocal, this.maxValue);
        return this.check(l);
    }

    public void setMaxValue(Expression expression) {
        this.maxValue = expression;
    }

    public Long getMinValue(Sequence sequence2, SessionLocal sessionLocal) {
        Long l = this.minValue == ValueExpression.NULL && sequence2 != null ? Long.valueOf(Sequence.getDefaultMinValue(this.getCurrentStart(sequence2, sessionLocal), this.increment != null ? this.getIncrement(sessionLocal).longValue() : sequence2.getIncrement(), this.getBounds())) : SequenceOptions.getLong(sessionLocal, this.minValue);
        return this.check(l);
    }

    public void setMinValue(Expression expression) {
        this.minValue = expression;
    }

    private Long check(Long l) {
        if (l == null) {
            return null;
        }
        long[] lArray = this.getBounds();
        long l2 = l;
        if (l2 < lArray[0] || l2 > lArray[1]) {
            throw DbException.get(22003, Long.toString(l2));
        }
        return l;
    }

    public long[] getBounds() {
        long[] lArray = this.bounds;
        if (lArray == null) {
            this.bounds = lArray = SequenceOptions.getBounds(this.dataType);
        }
        return lArray;
    }

    public static long[] getBounds(TypeInfo typeInfo) {
        long l;
        long l2;
        switch (typeInfo.getValueType()) {
            case 9: {
                l2 = -128L;
                l = 127L;
                break;
            }
            case 10: {
                l2 = -32768L;
                l = 32767L;
                break;
            }
            case 11: {
                l2 = Integer.MIN_VALUE;
                l = Integer.MAX_VALUE;
                break;
            }
            case 12: {
                l2 = Long.MIN_VALUE;
                l = Long.MAX_VALUE;
                break;
            }
            case 14: {
                l2 = -16777216L;
                l = 0x1000000L;
                break;
            }
            case 15: {
                l2 = -9007199254740992L;
                l = 0x20000000000000L;
                break;
            }
            case 13: {
                if (typeInfo.getScale() != 0) {
                    throw DbException.getUnsupportedException(typeInfo.getTraceSQL());
                }
                long l3 = typeInfo.getPrecision() - (long)typeInfo.getScale();
                if (l3 <= 0L) {
                    throw DbException.getUnsupportedException(typeInfo.getTraceSQL());
                }
                if (l3 > 18L) {
                    l2 = Long.MIN_VALUE;
                    l = Long.MAX_VALUE;
                    break;
                }
                l = 10L;
                int n = 1;
                while ((long)n < l3) {
                    l *= 10L;
                    ++n;
                }
                l2 = -(--l);
                break;
            }
            case 16: {
                long l3 = typeInfo.getPrecision();
                if (l3 > 18L) {
                    l2 = Long.MIN_VALUE;
                    l = Long.MAX_VALUE;
                    break;
                }
                l = 10L;
                int n = 1;
                while ((long)n < l3) {
                    l *= 10L;
                    ++n;
                }
                l2 = -l;
                break;
            }
            default: {
                throw DbException.getUnsupportedException(typeInfo.getTraceSQL());
            }
        }
        long[] lArray = new long[]{l2, l};
        return lArray;
    }

    public Sequence.Cycle getCycle() {
        return this.cycle;
    }

    public void setCycle(Sequence.Cycle cycle) {
        this.cycle = cycle;
    }

    public Long getCacheSize(SessionLocal sessionLocal) {
        return SequenceOptions.getLong(sessionLocal, this.cacheSize);
    }

    public void setCacheSize(Expression expression) {
        this.cacheSize = expression;
    }

    private long getCurrentStart(Sequence sequence2, SessionLocal sessionLocal) {
        return this.start != null ? this.getStartValue(sessionLocal).longValue() : sequence2.getBaseValue();
    }
}

