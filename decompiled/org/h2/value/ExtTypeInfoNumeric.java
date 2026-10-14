/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import org.h2.value.ExtTypeInfo;

public final class ExtTypeInfoNumeric
extends ExtTypeInfo {
    public static final ExtTypeInfoNumeric DECIMAL = new ExtTypeInfoNumeric();

    private ExtTypeInfoNumeric() {
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append("DECIMAL");
    }
}

