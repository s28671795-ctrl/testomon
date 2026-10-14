/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.util.Utils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBytesBase;

public final class ValueJavaObject
extends ValueBytesBase {
    private static final ValueJavaObject EMPTY = new ValueJavaObject(Utils.EMPTY_BYTES);

    private ValueJavaObject(byte[] byArray) {
        super(byArray);
    }

    public static ValueJavaObject getNoCopy(byte[] byArray) {
        int n = byArray.length;
        if (n == 0) {
            return EMPTY;
        }
        ValueJavaObject valueJavaObject = new ValueJavaObject(byArray);
        if (n > SysProperties.OBJECT_CACHE_MAX_PER_ELEMENT_SIZE) {
            return valueJavaObject;
        }
        return (ValueJavaObject)Value.cache(valueJavaObject);
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_JAVA_OBJECT;
    }

    @Override
    public int getValueType() {
        return 35;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if ((n & 4) == 0) {
            return super.getSQL(stringBuilder.append("CAST("), 0).append(" AS JAVA_OBJECT)");
        }
        return super.getSQL(stringBuilder, 0);
    }

    @Override
    public String getString() {
        throw DbException.get(22018, "JAVA_OBJECT to CHARACTER VARYING");
    }
}

