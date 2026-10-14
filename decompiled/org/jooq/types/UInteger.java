/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.types;

import java.io.ObjectStreamException;
import java.math.BigInteger;
import org.jooq.types.UNumber;

public final class UInteger
extends UNumber
implements Comparable<UInteger> {
    private static final Class<UInteger> CLASS = UInteger.class;
    private static final String CLASS_NAME = CLASS.getName();
    private static final String PRECACHE_PROPERTY = CLASS_NAME + ".precacheSize";
    private static final int DEFAULT_PRECACHE_SIZE = 256;
    private static final long serialVersionUID = -6821055240959745390L;
    private static final UInteger[] VALUES = UInteger.mkValues();
    public static final long MIN_VALUE = 0L;
    public static final long MAX_VALUE = 0xFFFFFFFFL;
    public static final UInteger MIN = UInteger.valueOf(0L);
    public static final UInteger MAX = UInteger.valueOf(0xFFFFFFFFL);
    private final long value;

    private static final int getPrecacheSize() {
        long propParsed;
        String prop = null;
        try {
            prop = System.getProperty(PRECACHE_PROPERTY);
        }
        catch (SecurityException e) {
            return 256;
        }
        if (prop == null) {
            return 256;
        }
        if (prop.length() <= 0) {
            return 256;
        }
        try {
            propParsed = Long.parseLong(prop);
        }
        catch (NumberFormatException e) {
            return 256;
        }
        if (propParsed < 0L) {
            return 0;
        }
        if (propParsed > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int)propParsed;
    }

    private static final UInteger[] mkValues() {
        int precacheSize = UInteger.getPrecacheSize();
        if (precacheSize <= 0) {
            return null;
        }
        UInteger[] ret = new UInteger[precacheSize];
        for (int i = 0; i < precacheSize; ++i) {
            ret[i] = new UInteger(i);
        }
        return ret;
    }

    private UInteger(long value, boolean unused2) {
        this.value = value;
    }

    private static UInteger getCached(long value) {
        if (VALUES != null && value < (long)VALUES.length) {
            return VALUES[(int)value];
        }
        return null;
    }

    private static UInteger valueOfUnchecked(long value) {
        UInteger cached = UInteger.getCached(value);
        if (cached != null) {
            return cached;
        }
        return new UInteger(value, true);
    }

    public static UInteger valueOf(String value) throws NumberFormatException {
        return UInteger.valueOfUnchecked(UInteger.rangeCheck(Long.parseLong(value)));
    }

    public static UInteger valueOf(int value) {
        return UInteger.valueOfUnchecked((long)value & 0xFFFFFFFFL);
    }

    public static UInteger valueOf(long value) throws NumberFormatException {
        return UInteger.valueOfUnchecked(UInteger.rangeCheck(value));
    }

    private UInteger(long value) throws NumberFormatException {
        this.value = UInteger.rangeCheck(value);
    }

    private UInteger(int value) {
        this.value = (long)value & 0xFFFFFFFFL;
    }

    private UInteger(String value) throws NumberFormatException {
        this.value = UInteger.rangeCheck(Long.parseLong(value));
    }

    private static long rangeCheck(long value) throws NumberFormatException {
        if (value < 0L || value > 0xFFFFFFFFL) {
            throw new NumberFormatException("Value is out of range : " + value);
        }
        return value;
    }

    private Object readResolve() throws ObjectStreamException {
        UInteger.rangeCheck(this.value);
        UInteger cached = UInteger.getCached(this.value);
        if (cached != null) {
            return cached;
        }
        return this;
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
        return Long.valueOf(this.value).hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof UInteger) {
            return this.value == ((UInteger)obj).value;
        }
        return false;
    }

    public String toString() {
        return Long.valueOf(this.value).toString();
    }

    @Override
    public int compareTo(UInteger o) {
        return this.value < o.value ? -1 : (this.value == o.value ? 0 : 1);
    }

    public UInteger add(UInteger val) {
        return UInteger.valueOf(this.value + val.value);
    }

    public UInteger add(int val) {
        return UInteger.valueOf(this.value + (long)val);
    }

    public UInteger subtract(UInteger val) {
        return UInteger.valueOf(this.value - val.value);
    }

    public UInteger subtract(int val) {
        return UInteger.valueOf(this.value - (long)val);
    }
}

