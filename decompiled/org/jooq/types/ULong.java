/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.types;

import java.math.BigInteger;
import org.jooq.types.UNumber;

public final class ULong
extends UNumber
implements Comparable<ULong> {
    private static final long serialVersionUID = -6821055240959745390L;
    public static final BigInteger MIN_VALUE = BigInteger.ZERO;
    public static final BigInteger MAX_VALUE = new BigInteger("18446744073709551615");
    public static final BigInteger MAX_VALUE_LONG = new BigInteger("9223372036854775808");
    public static final ULong MIN = ULong.valueOf(MIN_VALUE.longValue());
    public static final ULong MAX = ULong.valueOf(MAX_VALUE);
    private final long value;

    public static ULong valueOf(String value) throws NumberFormatException {
        return new ULong(value);
    }

    public static ULong valueOf(long value) {
        return new ULong(value);
    }

    public static ULong valueOf(BigInteger value) throws NumberFormatException {
        return new ULong(value);
    }

    public static int compare(long x, long y) {
        return (x += Long.MIN_VALUE) < (y += Long.MIN_VALUE) ? -1 : (x == y ? 0 : 1);
    }

    private ULong(BigInteger value) throws NumberFormatException {
        if (value.compareTo(MIN_VALUE) < 0 || value.compareTo(MAX_VALUE) > 0) {
            throw new NumberFormatException();
        }
        this.value = value.longValue();
    }

    private ULong(long value) {
        this.value = value;
    }

    private ULong(String value) throws NumberFormatException {
        if (value == null) {
            throw new NumberFormatException("null");
        }
        int length = value.length();
        if (length == 0) {
            throw new NumberFormatException("Empty input string");
        }
        if (value.charAt(0) == '-') {
            throw new NumberFormatException(String.format("Illegal leading minus sign on unsigned string %s", value));
        }
        if (length <= 18) {
            this.value = Long.parseLong(value, 10);
            return;
        }
        long first = Long.parseLong(value.substring(0, length - 1), 10);
        int second = Character.digit(value.charAt(length - 1), 10);
        if (second < 0) {
            throw new NumberFormatException("Bad digit at end of " + value);
        }
        long result = first * 10L + (long)second;
        if (ULong.compare(result, first) < 0) {
            throw new NumberFormatException(String.format("String value %s exceeds range of unsigned long", value));
        }
        this.value = result;
    }

    @Override
    public int intValue() {
        return (int)this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        if (this.value < 0L) {
            return (float)(this.value & Long.MAX_VALUE) + 9.223372E18f;
        }
        return this.value;
    }

    @Override
    public double doubleValue() {
        if (this.value < 0L) {
            return (double)(this.value & Long.MAX_VALUE) + 9.223372036854776E18;
        }
        return this.value;
    }

    public int hashCode() {
        return Long.valueOf(this.value).hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof ULong) {
            return this.value == ((ULong)obj).value;
        }
        return false;
    }

    public String toString() {
        if (this.value >= 0L) {
            return Long.toString(this.value);
        }
        return BigInteger.valueOf(this.value & Long.MAX_VALUE).add(MAX_VALUE_LONG).toString();
    }

    @Override
    public int compareTo(ULong o) {
        return ULong.compare(this.value, o.value);
    }

    public ULong add(ULong val) throws NumberFormatException {
        if (this.value < 0L && val.value < 0L) {
            throw new NumberFormatException();
        }
        long result = this.value + val.value;
        if ((this.value < 0L || val.value < 0L) && result >= 0L) {
            throw new NumberFormatException();
        }
        return ULong.valueOf(result);
    }

    public ULong add(int val) throws NumberFormatException {
        return this.add((long)val);
    }

    public ULong add(long val) throws NumberFormatException {
        if (val < 0L) {
            return this.subtract(Math.abs(val));
        }
        long result = this.value + val;
        if (this.value < 0L && result >= 0L) {
            throw new NumberFormatException();
        }
        return ULong.valueOf(result);
    }

    public ULong subtract(ULong val) {
        if (this.compareTo(val) < 0) {
            throw new NumberFormatException();
        }
        long result = this.value - val.value;
        if (this.value < 0L && result >= 0L) {
            throw new NumberFormatException();
        }
        return ULong.valueOf(result);
    }

    public ULong subtract(int val) {
        return this.subtract((long)val);
    }

    public ULong subtract(long val) {
        if (val < 0L) {
            return this.add(-val);
        }
        if (ULong.compare(this.value, val) < 0) {
            throw new NumberFormatException();
        }
        long result = this.value - val;
        if (this.value < 0L && result >= 0L) {
            throw new NumberFormatException();
        }
        return ULong.valueOf(result);
    }
}

