/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.Operation2;
import org.h2.expression.ValueExpression;
import org.h2.message.DbException;
import org.h2.util.DateTimeUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueDate;
import org.h2.value.ValueNull;
import org.h2.value.ValueTime;
import org.h2.value.ValueTimeTimeZone;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;

public class CompatibilityDatePlusTimeOperation
extends Operation2 {
    public CompatibilityDatePlusTimeOperation(Expression expression, Expression expression2) {
        super(expression, expression2);
        int n;
        TypeInfo typeInfo = expression.getType();
        TypeInfo typeInfo2 = expression2.getType();
        switch (typeInfo.getValueType()) {
            case 21: {
                if (typeInfo2.getValueType() == 19) {
                    throw DbException.getUnsupportedException("TIMESTAMP WITH TIME ZONE + TIME WITH TIME ZONE");
                }
            }
            case 18: {
                n = typeInfo2.getValueType() == 17 ? 20 : typeInfo.getValueType();
                break;
            }
            case 19: {
                if (typeInfo2.getValueType() == 19) {
                    throw DbException.getUnsupportedException("TIME WITH TIME ZONE + TIME WITH TIME ZONE");
                }
                n = typeInfo2.getValueType() == 17 ? 21 : typeInfo.getValueType();
                break;
            }
            case 20: {
                n = typeInfo2.getValueType() == 19 ? 21 : 20;
                break;
            }
            default: {
                throw DbException.getUnsupportedException(Value.getTypeName(typeInfo.getValueType()) + " + " + Value.getTypeName(typeInfo2.getValueType()));
            }
        }
        this.type = TypeInfo.getTypeInfo(n, 0L, Math.max(typeInfo.getScale(), typeInfo2.getScale()), null);
    }

    @Override
    public boolean needParentheses() {
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        this.left.getSQL(stringBuilder, n, 0).append(" + ");
        return this.right.getSQL(stringBuilder, n, 0);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Object object;
        Value value = this.left.getValue(sessionLocal);
        Value value2 = this.right.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        switch (value.getValueType()) {
            case 18: {
                if (value2.getValueType() != 17) break;
                return ValueTimestamp.fromDateValueAndNanos(((ValueDate)value2).getDateValue(), ((ValueTime)value).getNanos());
            }
            case 19: {
                if (value2.getValueType() != 17) break;
                ValueTimeTimeZone valueTimeTimeZone = (ValueTimeTimeZone)value;
                return ValueTimestampTimeZone.fromDateValueAndNanos(((ValueDate)value2).getDateValue(), valueTimeTimeZone.getNanos(), valueTimeTimeZone.getTimeZoneOffsetSeconds());
            }
            case 20: {
                if (value2.getValueType() != 19) break;
                object = (ValueTimestamp)value;
                value = ValueTimestampTimeZone.fromDateValueAndNanos(((ValueTimestamp)object).getDateValue(), ((ValueTimestamp)object).getTimeNanos(), ((ValueTimeTimeZone)value2).getTimeZoneOffsetSeconds());
            }
        }
        object = DateTimeUtils.dateAndTimeFromValue(value, sessionLocal);
        long l = object[0];
        reference var7_7 = object[1] + (value2 instanceof ValueTime ? ((ValueTime)value2).getNanos() : ((ValueTimeTimeZone)value2).getNanos());
        if (var7_7 >= 86400000000000L) {
            var7_7 -= 86400000000000L;
            l = DateTimeUtils.incrementDateValue(l);
        }
        return DateTimeUtils.dateTimeToValue(value, l, (long)var7_7);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        this.right = this.right.optimize(sessionLocal);
        if (this.left.isConstant() && this.right.isConstant()) {
            return ValueExpression.get(this.getValue(sessionLocal));
        }
        return this;
    }
}

