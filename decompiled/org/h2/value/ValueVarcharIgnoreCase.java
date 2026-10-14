/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import org.h2.engine.CastDataProvider;
import org.h2.engine.SysProperties;
import org.h2.util.StringUtils;
import org.h2.value.CompareMode;
import org.h2.value.Value;
import org.h2.value.ValueStringBase;

public final class ValueVarcharIgnoreCase
extends ValueStringBase {
    private static final ValueVarcharIgnoreCase EMPTY = new ValueVarcharIgnoreCase("");
    private int hash;

    private ValueVarcharIgnoreCase(String string) {
        super(string);
    }

    @Override
    public int getValueType() {
        return 4;
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        return compareMode.compareString(this.value, ((ValueStringBase)value).value, true);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ValueVarcharIgnoreCase && this.value.equalsIgnoreCase(((ValueVarcharIgnoreCase)object).value);
    }

    @Override
    public int hashCode() {
        if (this.hash == 0) {
            this.hash = this.value.toUpperCase().hashCode();
        }
        return this.hash;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if ((n & 4) == 0) {
            return StringUtils.quoteStringSQL(stringBuilder.append("CAST("), this.value).append(" AS VARCHAR_IGNORECASE(").append(this.value.length()).append("))");
        }
        return StringUtils.quoteStringSQL(stringBuilder, this.value);
    }

    public static ValueVarcharIgnoreCase get(String string) {
        int n = string.length();
        if (n == 0) {
            return EMPTY;
        }
        ValueVarcharIgnoreCase valueVarcharIgnoreCase = new ValueVarcharIgnoreCase(StringUtils.cache(string));
        if (n > SysProperties.OBJECT_CACHE_MAX_PER_ELEMENT_SIZE) {
            return valueVarcharIgnoreCase;
        }
        ValueVarcharIgnoreCase valueVarcharIgnoreCase2 = (ValueVarcharIgnoreCase)Value.cache(valueVarcharIgnoreCase);
        if (valueVarcharIgnoreCase2.value.equals(string)) {
            return valueVarcharIgnoreCase2;
        }
        return valueVarcharIgnoreCase;
    }
}

