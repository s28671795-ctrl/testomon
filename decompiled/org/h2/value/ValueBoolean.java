/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.math.BigDecimal;
import org.h2.engine.CastDataProvider;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class ValueBoolean
extends Value {
    public static final int PRECISION = 1;
    public static final int DISPLAY_SIZE = 5;
    public static final ValueBoolean TRUE = new ValueBoolean(true);
    public static final ValueBoolean FALSE = new ValueBoolean(false);
    private final boolean value;

    private ValueBoolean(boolean bl) {
        this.value = bl;
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_BOOLEAN;
    }

    @Override
    public int getValueType() {
        return 8;
    }

    @Override
    public int getMemory() {
        return 0;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append(this.getString());
    }

    @Override
    public String getString() {
        return this.value ? "TRUE" : "FALSE";
    }

    @Override
    public boolean getBoolean() {
        return this.value;
    }

    @Override
    public byte getByte() {
        return this.value ? (byte)1 : 0;
    }

    @Override
    public short getShort() {
        return this.value ? (short)1 : 0;
    }

    @Override
    public int getInt() {
        return this.value ? 1 : 0;
    }

    @Override
    public long getLong() {
        return this.value ? 1L : 0L;
    }

    @Override
    public BigDecimal getBigDecimal() {
        return this.value ? BigDecimal.ONE : BigDecimal.ZERO;
    }

    @Override
    public float getFloat() {
        return this.value ? 1.0f : 0.0f;
    }

    @Override
    public double getDouble() {
        return this.value ? 1.0 : 0.0;
    }

    @Override
    public Value negate() {
        return this.value ? FALSE : TRUE;
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        return Boolean.compare(this.value, ((ValueBoolean)value).value);
    }

    @Override
    public int hashCode() {
        return this.value ? 1 : 0;
    }

    public static ValueBoolean get(boolean bl) {
        return bl ? TRUE : FALSE;
    }

    @Override
    public boolean equals(Object object) {
        return this == object;
    }
}

