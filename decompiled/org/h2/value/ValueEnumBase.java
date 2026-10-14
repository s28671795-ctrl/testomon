/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.math.BigDecimal;
import org.h2.engine.CastDataProvider;
import org.h2.util.StringUtils;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueInteger;

public class ValueEnumBase
extends Value {
    final String label;
    private final int ordinal;

    protected ValueEnumBase(String string, int n) {
        this.label = string;
        this.ordinal = n;
    }

    @Override
    public Value add(Value value) {
        ValueInteger valueInteger = value.convertToInt(null);
        return this.convertToInt(null).add(valueInteger);
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        return Integer.compare(this.getInt(), value.getInt());
    }

    @Override
    public Value divide(Value value, TypeInfo typeInfo) {
        ValueInteger valueInteger = value.convertToInt(null);
        return this.convertToInt(null).divide(valueInteger, typeInfo);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ValueEnumBase && this.getInt() == ((ValueEnumBase)object).getInt();
    }

    public static ValueEnumBase get(String string, int n) {
        return new ValueEnumBase(string, n);
    }

    @Override
    public int getInt() {
        return this.ordinal;
    }

    @Override
    public long getLong() {
        return this.ordinal;
    }

    @Override
    public BigDecimal getBigDecimal() {
        return BigDecimal.valueOf(this.ordinal);
    }

    @Override
    public float getFloat() {
        return this.ordinal;
    }

    @Override
    public double getDouble() {
        return this.ordinal;
    }

    @Override
    public int getSignum() {
        return Integer.signum(this.ordinal);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return StringUtils.quoteStringSQL(stringBuilder, this.label);
    }

    @Override
    public String getString() {
        return this.label;
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_ENUM_UNDEFINED;
    }

    @Override
    public int getValueType() {
        return 36;
    }

    @Override
    public int getMemory() {
        return 120;
    }

    @Override
    public int hashCode() {
        int n = 31;
        n += this.getString().hashCode();
        return n += this.getInt();
    }

    @Override
    public Value modulus(Value value) {
        ValueInteger valueInteger = value.convertToInt(null);
        return this.convertToInt(null).modulus(valueInteger);
    }

    @Override
    public Value multiply(Value value) {
        ValueInteger valueInteger = value.convertToInt(null);
        return this.convertToInt(null).multiply(valueInteger);
    }

    @Override
    public Value subtract(Value value) {
        ValueInteger valueInteger = value.convertToInt(null);
        return this.convertToInt(null).subtract(valueInteger);
    }
}

