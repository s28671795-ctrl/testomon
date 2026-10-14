/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.util.UUID;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.util.Bits;
import org.h2.util.MathUtils;
import org.h2.util.StringUtils;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class ValueUuid
extends Value {
    static final int PRECISION = 16;
    static final int DISPLAY_SIZE = 36;
    private final long high;
    private final long low;

    private ValueUuid(long l, long l2) {
        this.high = l;
        this.low = l2;
    }

    @Override
    public int hashCode() {
        return (int)(this.high >>> 32 ^ this.high ^ this.low >>> 32 ^ this.low);
    }

    public static ValueUuid getNewRandom() {
        long l = MathUtils.secureRandomLong();
        long l2 = MathUtils.secureRandomLong();
        l = l & 0xFFFFFFFFFFFF0FFFL | 0x4000L;
        l2 = l2 & 0x3FFFFFFFFFFFFFFFL | Long.MIN_VALUE;
        return new ValueUuid(l, l2);
    }

    public static ValueUuid get(byte[] byArray) {
        int n = byArray.length;
        if (n != 16) {
            throw DbException.get(22018, "UUID requires 16 bytes, got " + n);
        }
        return ValueUuid.get(Bits.readLong(byArray, 0), Bits.readLong(byArray, 8));
    }

    public static ValueUuid get(long l, long l2) {
        return (ValueUuid)Value.cache(new ValueUuid(l, l2));
    }

    public static ValueUuid get(UUID uUID) {
        return ValueUuid.get(uUID.getMostSignificantBits(), uUID.getLeastSignificantBits());
    }

    public static ValueUuid get(String string) {
        long l = 0L;
        long l2 = 0L;
        int n = 0;
        int n2 = string.length();
        for (int i = 0; i < n2; ++i) {
            char c = string.charAt(i);
            if (c >= '0' && c <= '9') {
                l = l << 4 | (long)(c - 48);
            } else if (c >= 'a' && c <= 'f') {
                l = l << 4 | (long)(c - 87);
            } else {
                if (c == '-') continue;
                if (c >= 'A' && c <= 'F') {
                    l = l << 4 | (long)(c - 55);
                } else {
                    if (c <= ' ') continue;
                    throw DbException.get(22018, string);
                }
            }
            if (++n != 16) continue;
            l2 = l;
            l = 0L;
        }
        if (n != 32) {
            throw DbException.get(22018, string);
        }
        return ValueUuid.get(l2, l);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return this.addString(stringBuilder.append("UUID '")).append('\'');
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_UUID;
    }

    @Override
    public int getMemory() {
        return 32;
    }

    @Override
    public int getValueType() {
        return 39;
    }

    @Override
    public String getString() {
        return this.addString(new StringBuilder(36)).toString();
    }

    @Override
    public byte[] getBytes() {
        return Bits.uuidToBytes(this.high, this.low);
    }

    private StringBuilder addString(StringBuilder stringBuilder) {
        StringUtils.appendHex(stringBuilder, this.high >> 32, 4).append('-');
        StringUtils.appendHex(stringBuilder, this.high >> 16, 2).append('-');
        StringUtils.appendHex(stringBuilder, this.high, 2).append('-');
        StringUtils.appendHex(stringBuilder, this.low >> 48, 2).append('-');
        return StringUtils.appendHex(stringBuilder, this.low, 6);
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        if (value == this) {
            return 0;
        }
        ValueUuid valueUuid = (ValueUuid)value;
        int n = Long.compareUnsigned(this.high, valueUuid.high);
        return n != 0 ? n : Long.compareUnsigned(this.low, valueUuid.low);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ValueUuid)) {
            return false;
        }
        ValueUuid valueUuid = (ValueUuid)object;
        return this.high == valueUuid.high && this.low == valueUuid.low;
    }

    public UUID getUuid() {
        return new UUID(this.high, this.low);
    }

    public long getHigh() {
        return this.high;
    }

    public long getLow() {
        return this.low;
    }

    @Override
    public long charLength() {
        return 36L;
    }

    @Override
    public long octetLength() {
        return 16L;
    }
}

