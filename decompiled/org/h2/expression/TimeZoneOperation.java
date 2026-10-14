/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Operation1_2;
import org.h2.expression.ValueExpression;
import org.h2.message.DbException;
import org.h2.util.DateTimeUtils;
import org.h2.util.TimeZoneProvider;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueInterval;
import org.h2.value.ValueNull;
import org.h2.value.ValueTimeTimeZone;
import org.h2.value.ValueTimestampTimeZone;

public final class TimeZoneOperation
extends Operation1_2 {
    public TimeZoneOperation(Expression expression, Expression expression2) {
        super(expression, expression2);
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        this.left.getSQL(stringBuilder, n, 0).append(" AT ");
        if (this.right != null) {
            this.right.getSQL(stringBuilder.append("TIME ZONE "), n, 0);
        } else {
            stringBuilder.append("LOCAL");
        }
        return stringBuilder;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value;
        Value value2 = this.left.getValue(sessionLocal);
        Value value3 = value2.convertTo(this.type, (CastDataProvider)sessionLocal);
        if (value3 == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        if (this.right == null) {
            int n = value2.getValueType();
            if (n == 18 || n == 20) {
                return value3;
            }
            value = null;
        } else {
            value = this.right.getValue(sessionLocal);
            if (value == ValueNull.INSTANCE) {
                return ValueNull.INSTANCE;
            }
        }
        if (value3.getValueType() == 21) {
            int n;
            ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)value3;
            long l = valueTimestampTimeZone.getDateValue();
            long l2 = valueTimestampTimeZone.getTimeNanos();
            int n2 = valueTimestampTimeZone.getTimeZoneOffsetSeconds();
            int n3 = n = value != null ? TimeZoneOperation.parseTimeZone(value, l, l2, n2, true) : sessionLocal.currentTimeZone().getTimeZoneOffsetUTC(DateTimeUtils.getEpochSeconds(l, l2, n2));
            if (n2 != n) {
                value3 = DateTimeUtils.timestampTimeZoneAtOffset(l, l2, n2, n);
            }
        } else {
            int n;
            ValueTimeTimeZone valueTimeTimeZone = (ValueTimeTimeZone)value3;
            long l = valueTimeTimeZone.getNanos();
            int n4 = valueTimeTimeZone.getTimeZoneOffsetSeconds();
            int n5 = n = value != null ? TimeZoneOperation.parseTimeZone(value, 1008673L, l, n4, false) : sessionLocal.currentTimeZone().getTimeZoneOffsetUTC(DateTimeUtils.getEpochSeconds(sessionLocal.currentTimestamp().getDateValue(), l, n4));
            if (n4 != n) {
                value3 = ValueTimeTimeZone.fromNanos(DateTimeUtils.normalizeNanosOfDay(l += (long)(n - n4) * 1000000000L), n);
            }
        }
        return value3;
    }

    private static int parseTimeZone(Value value, long l, long l2, int n, boolean bl) {
        if (DataType.isCharacterStringType(value.getValueType())) {
            TimeZoneProvider timeZoneProvider;
            try {
                timeZoneProvider = TimeZoneProvider.ofId(value.getString());
            }
            catch (RuntimeException runtimeException) {
                throw DbException.getInvalidValueException("time zone", value.getTraceSQL());
            }
            if (!bl && !timeZoneProvider.hasFixedOffset()) {
                throw DbException.getInvalidValueException("time zone", value.getTraceSQL());
            }
            return timeZoneProvider.getTimeZoneOffsetUTC(DateTimeUtils.getEpochSeconds(l, l2, n));
        }
        return TimeZoneOperation.parseInterval(value);
    }

    public static int parseInterval(Value value) {
        ValueInterval valueInterval = (ValueInterval)value.convertTo(TypeInfo.TYPE_INTERVAL_HOUR_TO_SECOND);
        long l = valueInterval.getLeading();
        long l2 = valueInterval.getRemaining();
        if (l > 18L || l == 18L && l2 != 0L || l2 % 1000000000L != 0L) {
            throw DbException.getInvalidValueException("time zone", valueInterval.getTraceSQL());
        }
        int n = (int)(l * 3600L + l2 / 1000000000L);
        if (valueInterval.isNegative()) {
            n = -n;
        }
        return n;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        if (this.right != null) {
            this.right = this.right.optimize(sessionLocal);
        }
        TypeInfo typeInfo = this.left.getType();
        int n = 21;
        int n2 = 9;
        int n3 = typeInfo.getValueType();
        switch (n3) {
            case 20: 
            case 21: {
                n2 = typeInfo.getScale();
                break;
            }
            case 18: 
            case 19: {
                n = 19;
                n2 = typeInfo.getScale();
                break;
            }
            default: {
                StringBuilder stringBuilder = this.left.getSQL(new StringBuilder(), 3, 0);
                int n4 = stringBuilder.length();
                stringBuilder.append(" AT ");
                if (this.right != null) {
                    this.right.getSQL(stringBuilder.append("TIME ZONE "), 3, 0);
                } else {
                    stringBuilder.append("LOCAL");
                }
                throw DbException.getSyntaxError(stringBuilder.toString(), n4, "time, timestamp");
            }
        }
        this.type = TypeInfo.getTypeInfo(n, -1L, n2, null);
        if (this.left.isConstant() && (n3 == 19 || n3 == 21) && this.right != null && this.right.isConstant()) {
            return ValueExpression.get(this.getValue(sessionLocal));
        }
        return this;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        if (expressionVisitor.getType() == 2) {
            if (this.right == null) {
                return false;
            }
            int n = this.left.getType().getValueType();
            if (n == 18 || n == 20) {
                return false;
            }
        }
        return this.left.isEverything(expressionVisitor) && (this.right == null || this.right.isEverything(expressionVisitor));
    }
}

