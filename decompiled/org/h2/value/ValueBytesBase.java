/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.util.Arrays;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.util.Bits;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.CompareMode;
import org.h2.value.Value;

abstract class ValueBytesBase
extends Value {
    byte[] value;
    int hash;

    ValueBytesBase(byte[] byArray) {
        int n = byArray.length;
        if (n > 1000000000) {
            throw DbException.getValueTooLongException(ValueBytesBase.getTypeName(this.getValueType()), StringUtils.convertBytesToHex(byArray, 41), n);
        }
        this.value = byArray;
    }

    @Override
    public final byte[] getBytes() {
        return Utils.cloneByteArray(this.value);
    }

    @Override
    public final byte[] getBytesNoCopy() {
        return this.value;
    }

    @Override
    public final int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        return Bits.compareNotNullUnsigned(this.value, ((ValueBytesBase)value).value);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return StringUtils.convertBytesToHex(stringBuilder.append("X'"), this.value).append('\'');
    }

    @Override
    public final int hashCode() {
        int n = this.hash;
        if (n == 0) {
            n = this.getClass().hashCode() ^ Utils.getByteArrayHash(this.value);
            if (n == 0) {
                n = 1234570417;
            }
            this.hash = n;
        }
        return n;
    }

    @Override
    public int getMemory() {
        return this.value.length + 24;
    }

    @Override
    public final boolean equals(Object object) {
        return object != null && this.getClass() == object.getClass() && Arrays.equals(this.value, ((ValueBytesBase)object).value);
    }
}

