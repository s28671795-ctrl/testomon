/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.types;

import java.math.BigInteger;
import org.jooq.types.UNumber;

public final class UShort
extends UNumber
implements Comparable<UShort> {
    private static final long serialVersionUID = -6821055240959745390L;
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 65535;
    public static final UShort MIN = UShort.valueOf(0);
    public static final UShort MAX = UShort.valueOf(65535);
    private final int value;

    public static UShort valueOf(String value) throws NumberFormatException {
        return new UShort(value);
    }

    public static UShort valueOf(short value) {
        return new UShort(value);
    }

    public static UShort valueOf(int value) throws NumberFormatException {
        return new UShort(value);
    }

    private UShort(int value) throws NumberFormatException {
        this.value = value;
        this.rangeCheck();
    }

    private UShort(short value) {
        this.value = value & 0xFFFF;
    }

    private UShort(String value) throws NumberFormatException {
        this.value = Integer.parseInt(value);
        this.rangeCheck();
    }

    private void rangeCheck() throws NumberFormatException {
        if (this.value < 0 || this.value > 65535) {
            throw new NumberFormatException("Value is out of range : " + this.value);
        }
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

    @Override
    public BigInteger toBigInteger() {
        return BigInteger.valueOf(this.value);
    }

    public int hashCode() {
        return Integer.valueOf(this.value).hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof UShort) {
            return this.value == ((UShort)obj).value;
        }
        return false;
    }

    public String toString() {
        return Integer.valueOf(this.value).toString();
    }

    @Override
    public int compareTo(UShort o) {
        return this.value < o.value ? -1 : (this.value == o.value ? 0 : 1);
    }

    public UShort add(UShort val) throws NumberFormatException {
        return UShort.valueOf(this.value + val.value);
    }

    public UShort add(int val) throws NumberFormatException {
        return UShort.valueOf(this.value + val);
    }

    public UShort subtract(UShort val) {
        return UShort.valueOf(this.value - val.value);
    }

    public UShort subtract(int val) {
        return UShort.valueOf(this.value - val);
    }
}

