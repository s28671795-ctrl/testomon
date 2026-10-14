/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

import org.h2.engine.SessionLocal;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Operation0;
import org.h2.expression.function.NamedExpression;
import org.h2.util.DateTimeUtils;
import org.h2.util.TimeZoneProvider;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;

final class CompatibilityDateTimeValueFunction
extends Operation0
implements NamedExpression {
    static final int SYSDATE = 0;
    static final int SYSTIMESTAMP = 1;
    private static final String[] NAMES = new String[]{"SYSDATE", "SYSTIMESTAMP"};
    private final int function;
    private final int scale;
    private final TypeInfo type;

    CompatibilityDateTimeValueFunction(int n, int n2) {
        this.function = n;
        this.scale = n2;
        this.type = n == 0 ? TypeInfo.getTypeInfo(20, 0L, 0, null) : TypeInfo.getTypeInfo(21, 0L, n2, null);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        int n;
        ValueTimestampTimeZone valueTimestampTimeZone = sessionLocal.currentTimestamp();
        long l = valueTimestampTimeZone.getDateValue();
        long l2 = valueTimestampTimeZone.getTimeNanos();
        int n2 = valueTimestampTimeZone.getTimeZoneOffsetSeconds();
        if (n2 != (n = TimeZoneProvider.getDefault().getTimeZoneOffsetUTC(DateTimeUtils.getEpochSeconds(l, l2, n2)))) {
            valueTimestampTimeZone = DateTimeUtils.timestampTimeZoneAtOffset(l, l2, n2, n);
        }
        if (this.function == 0) {
            return ValueTimestamp.fromDateValueAndNanos(valueTimestampTimeZone.getDateValue(), valueTimestampTimeZone.getTimeNanos() / 1000000000L * 1000000000L);
        }
        return valueTimestampTimeZone.castTo(this.type, sessionLocal);
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append(this.getName());
        if (this.scale >= 0) {
            stringBuilder.append('(').append(this.scale).append(')');
        }
        return stringBuilder;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 2: {
                return false;
            }
        }
        return true;
    }

    @Override
    public TypeInfo getType() {
        return this.type;
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

