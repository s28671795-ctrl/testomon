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

public final class ValueChar
extends ValueStringBase {
    private ValueChar(String string) {
        super(string);
    }

    @Override
    public int getValueType() {
        return 1;
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        return compareMode.compareString(this.convertToChar().getString(), value.convertToChar().getString(), false);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if ((n & 4) == 0) {
            int n2 = this.value.length();
            return StringUtils.quoteStringSQL(stringBuilder.append("CAST("), this.value).append(" AS CHAR(").append(n2 > 0 ? n2 : 1).append("))");
        }
        return StringUtils.quoteStringSQL(stringBuilder, this.value);
    }

    public static ValueChar get(String string) {
        ValueChar valueChar = new ValueChar(StringUtils.cache(string));
        if (string.length() > SysProperties.OBJECT_CACHE_MAX_PER_ELEMENT_SIZE) {
            return valueChar;
        }
        return (ValueChar)Value.cache(valueChar);
    }
}

