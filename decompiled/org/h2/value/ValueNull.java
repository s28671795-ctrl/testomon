/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class ValueNull
extends Value {
    public static final ValueNull INSTANCE = new ValueNull();
    static final int PRECISION = 1;
    static final int DISPLAY_SIZE = 4;

    private ValueNull() {
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append("NULL");
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_NULL;
    }

    @Override
    public int getValueType() {
        return 0;
    }

    @Override
    public int getMemory() {
        return 0;
    }

    @Override
    public String getString() {
        return null;
    }

    @Override
    public Reader getReader() {
        return null;
    }

    @Override
    public Reader getReader(long l, long l2) {
        return null;
    }

    @Override
    public byte[] getBytes() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public InputStream getInputStream(long l, long l2) {
        return null;
    }

    @Override
    public boolean getBoolean() {
        throw DbException.getInternalError();
    }

    @Override
    public byte getByte() {
        throw DbException.getInternalError();
    }

    @Override
    public short getShort() {
        throw DbException.getInternalError();
    }

    @Override
    public int getInt() {
        throw DbException.getInternalError();
    }

    @Override
    public long getLong() {
        throw DbException.getInternalError();
    }

    @Override
    public BigDecimal getBigDecimal() {
        return null;
    }

    @Override
    public float getFloat() {
        throw DbException.getInternalError();
    }

    @Override
    public double getDouble() {
        throw DbException.getInternalError();
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        throw DbException.getInternalError("compare null");
    }

    @Override
    public boolean containsNull() {
        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        return object == this;
    }
}

