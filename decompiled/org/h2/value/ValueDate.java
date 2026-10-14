/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.util.DateTimeUtils;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class ValueDate
extends Value {
    public static final int PRECISION = 10;
    private final long dateValue;

    private ValueDate(long l) {
        if (l < -511999999967L || l > 512000000415L) {
            throw new IllegalArgumentException("dateValue out of range " + l);
        }
        this.dateValue = l;
    }

    public static ValueDate fromDateValue(long l) {
        return (ValueDate)Value.cache(new ValueDate(l));
    }

    public static ValueDate parse(String string) {
        try {
            return ValueDate.fromDateValue(DateTimeUtils.parseDateValue(string, 0, string.length()));
        }
        catch (Exception exception) {
            throw DbException.get(22007, exception, "DATE", string);
        }
    }

    public long getDateValue() {
        return this.dateValue;
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_DATE;
    }

    @Override
    public int getValueType() {
        return 17;
    }

    @Override
    public String getString() {
        return DateTimeUtils.appendDate(new StringBuilder(10), this.dateValue).toString();
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return DateTimeUtils.appendDate(stringBuilder.append("DATE '"), this.dateValue).append('\'');
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        return Long.compare(this.dateValue, ((ValueDate)value).dateValue);
    }

    @Override
    public boolean equals(Object object) {
        return this == object || object instanceof ValueDate && this.dateValue == ((ValueDate)object).dateValue;
    }

    @Override
    public int hashCode() {
        return (int)(this.dateValue ^ this.dateValue >>> 32);
    }
}

