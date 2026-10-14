/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import org.h2.engine.CastDataProvider;
import org.h2.engine.SysProperties;
import org.h2.util.StringUtils;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueStringBase;

public final class ValueVarchar
extends ValueStringBase {
    public static final ValueVarchar EMPTY = new ValueVarchar("");

    private ValueVarchar(String string) {
        super(string);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return StringUtils.quoteStringSQL(stringBuilder, this.value);
    }

    @Override
    public int getValueType() {
        return 2;
    }

    public static Value get(String string) {
        return ValueVarchar.get(string, null);
    }

    public static Value get(String string, CastDataProvider castDataProvider) {
        if (string.isEmpty()) {
            return castDataProvider != null && castDataProvider.getMode().treatEmptyStringsAsNull ? ValueNull.INSTANCE : EMPTY;
        }
        ValueVarchar valueVarchar = new ValueVarchar(StringUtils.cache(string));
        if (string.length() > SysProperties.OBJECT_CACHE_MAX_PER_ELEMENT_SIZE) {
            return valueVarchar;
        }
        return Value.cache(valueVarchar);
    }
}

