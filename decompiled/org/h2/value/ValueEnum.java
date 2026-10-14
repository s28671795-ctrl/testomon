/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import org.h2.util.StringUtils;
import org.h2.value.ExtTypeInfoEnum;
import org.h2.value.TypeInfo;
import org.h2.value.ValueEnumBase;

public final class ValueEnum
extends ValueEnumBase {
    private final ExtTypeInfoEnum enumerators;

    ValueEnum(ExtTypeInfoEnum extTypeInfoEnum, String string, int n) {
        super(string, n);
        this.enumerators = extTypeInfoEnum;
    }

    @Override
    public TypeInfo getType() {
        return this.enumerators.getType();
    }

    public ExtTypeInfoEnum getEnumerators() {
        return this.enumerators;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if ((n & 4) == 0) {
            StringUtils.quoteStringSQL(stringBuilder.append("CAST("), this.label).append(" AS ");
            return this.enumerators.getType().getSQL(stringBuilder, n).append(')');
        }
        return StringUtils.quoteStringSQL(stringBuilder, this.label);
    }
}

