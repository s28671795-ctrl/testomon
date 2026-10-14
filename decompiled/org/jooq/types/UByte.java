/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.types;

import java.io.ObjectStreamException;
import java.math.BigInteger;
import org.jooq.types.UNumber;

public final class UByte
extends UNumber
implements Comparable<UByte> {
    private static final long serialVersionUID = -6821055240959745390L;
    private static final UByte[] VALUES = UByte.mkValues();
    public static final short MIN_VALUE = 0;
    public static final short MAX_VALUE = 255;
    public static final UByte MIN = UByte.valueOf(0);
    public static final UByte MAX = UByte.valueOf(255);
    private final short value;

    private static final UByte[] mkValues() {
        UByte[] ret = new UByte[256];
        for (int i = -128; i <= 127; ++i) {
            ret[i & 0xFF] = new UByte((byte)i);
        }
        return ret;
    }

    public static UByte valueOf(String value) throws NumberFormatException {
        return UByte.valueOfUnchecked(UByte.rangeCheck(Short.parseShort(value)));
    }

    public static UByte valueOf(byte value) {
        return UByte.valueOfUnchecked((short)(value & 0xFF));
    }

    private static UByte valueOfUnchecked(short value) throws NumberFormatException {
        return VALUES[value & 0xFF];
    }

    public static UByte valueOf(short value) throws NumberFormatException {
        return UByte.valueOfUnchecked(UByte.rangeCheck(value));
    }

    public static UByte valueOf(int value) throws NumberFormatException {
        return UByte.valueOfUnchecked(UByte.rangeCheck(value));
    }

    public static UByte valueOf(long value) throws NumberFormatException {
        return UByte.valueOfUnchecked(UByte.rangeCheck(value));
    }

    private UByte(long value) throws NumberFormatException {
        this.value = UByte.rangeCheck(value);
    }

    private UByte(int value) throws NumberFormatException {
        this.value = UByte.rangeCheck(value);
    }

    private UByte(short value) throws NumberFormatException {
        this.value = UByte.rangeCheck(value);
    }

    private UByte(byte value) {
        this.value = (short)(value & 0xFF);
    }

    private UByte(String value) throws NumberFormatException {
        this.value = UByte.rangeCheck(Short.parseShort(value));
    }

    private static short rangeCheck(short value) throws NumberFormatException {
        if (value < 0 || value > 255) {
            throw new NumberFormatException("Value is out of range : " + value);
        }
        return value;
    }

    private static short rangeCheck(int value) throws NumberFormatException {
        if (value < 0 || value > 255) {
            throw new NumberFormatException("Value is out of range : " + value);
        }
        return (short)value;
    }

    private static short rangeCheck(long value) throws NumberFormatException {
        if (value < 0L || value > 255L) {
            throw new NumberFormatException("Value is out of range : " + value);
        }
        return (short)value;
    }

    private Object readResolve() throws ObjectStreamException {
        return UByte.valueOf(this.value);
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    public int hashCode() {
        return Short.valueOf(this.value).hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof UByte) {
            return this.value == ((UByte)obj).value;
        }
        return false;
    }

    public String toString() {
        return Short.valueOf(this.value).toString();
    }

    @Override
    public int compareTo(UByte o) {
        return this.value < o.value ? -1 : (this.value == o.value ? 0 : 1);
    }

    @Override
    public BigInteger toBigInteger() {
        return BigInteger.valueOf(this.value);
    }

    public UByte add(UByte val) throws NumberFormatException {
        return UByte.valueOf(this.value + val.value);
    }

    public UByte add(int val) throws NumberFormatException {
        return UByte.valueOf(this.value + val);
    }

    public UByte subtract(UByte val) {
        return UByte.valueOf(this.value - val.value);
    }

    public UByte subtract(int val) {
        return UByte.valueOf(this.value - val);
    }
}

