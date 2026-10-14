/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

abstract class ValueStringBase
extends Value {
    String value;
    private TypeInfo type;

    ValueStringBase(String string) {
        int n = string.length();
        if (n > 1000000000) {
            throw DbException.getValueTooLongException(ValueStringBase.getTypeName(this.getValueType()), string, n);
        }
        this.value = string;
    }

    @Override
    public final TypeInfo getType() {
        TypeInfo typeInfo = this.type;
        if (typeInfo == null) {
            int n = this.value.length();
            this.type = typeInfo = new TypeInfo(this.getValueType(), n, 0, null);
        }
        return typeInfo;
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        return compareMode.compareString(this.value, ((ValueStringBase)value).value, false);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode() ^ this.value.hashCode();
    }

    @Override
    public final String getString() {
        return this.value;
    }

    @Override
    public final byte[] getBytes() {
        return this.value.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public final boolean getBoolean() {
        String string = this.value.trim();
        if (string.equalsIgnoreCase("true") || string.equalsIgnoreCase("t") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("y")) {
            return true;
        }
        if (string.equalsIgnoreCase("false") || string.equalsIgnoreCase("f") || string.equalsIgnoreCase("no") || string.equalsIgnoreCase("n")) {
            return false;
        }
        try {
            return new BigDecimal(string).signum() != 0;
        }
        catch (NumberFormatException numberFormatException) {
            throw this.getDataConversionError(8);
        }
    }

    @Override
    public final byte getByte() {
        try {
            return Byte.parseByte(this.value.trim());
        }
        catch (NumberFormatException numberFormatException) {
            throw DbException.get(22018, numberFormatException, this.value);
        }
    }

    @Override
    public final short getShort() {
        try {
            return Short.parseShort(this.value.trim());
        }
        catch (NumberFormatException numberFormatException) {
            throw DbException.get(22018, numberFormatException, this.value);
        }
    }

    @Override
    public final int getInt() {
        try {
            return Integer.parseInt(this.value.trim());
        }
        catch (NumberFormatException numberFormatException) {
            throw DbException.get(22018, numberFormatException, this.value);
        }
    }

    @Override
    public final long getLong() {
        try {
            return Long.parseLong(this.value.trim());
        }
        catch (NumberFormatException numberFormatException) {
            throw DbException.get(22018, numberFormatException, this.value);
        }
    }

    @Override
    public final BigDecimal getBigDecimal() {
        try {
            return new BigDecimal(this.value.trim());
        }
        catch (NumberFormatException numberFormatException) {
            throw DbException.get(22018, numberFormatException, this.value);
        }
    }

    @Override
    public final float getFloat() {
        try {
            return Float.parseFloat(this.value.trim());
        }
        catch (NumberFormatException numberFormatException) {
            throw DbException.get(22018, numberFormatException, this.value);
        }
    }

    @Override
    public final double getDouble() {
        try {
            return Double.parseDouble(this.value.trim());
        }
        catch (NumberFormatException numberFormatException) {
            throw DbException.get(22018, numberFormatException, this.value);
        }
    }

    @Override
    public final int getMemory() {
        return this.value.length() * 2 + 94;
    }

    @Override
    public boolean equals(Object object) {
        return object != null && this.getClass() == object.getClass() && this.value.equals(((ValueStringBase)object).value);
    }
}

