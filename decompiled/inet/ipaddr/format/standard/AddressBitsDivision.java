/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.standard;

import inet.ipaddr.AddressValueException;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.standard.AddressDivision;

public class AddressBitsDivision
extends AddressDivision {
    private static final long serialVersionUID = 4L;
    protected final int value;
    protected final int upperValue;
    private final int bitCount;
    private final int defaultRadix;

    public AddressBitsDivision(int value, int bitCount, int defaultRadix) {
        this(value, value, bitCount, defaultRadix);
    }

    public AddressBitsDivision(int lower, int upper, int bitCount, int defaultRadix) {
        if (lower > upper) {
            int tmp = lower;
            lower = upper;
            upper = tmp;
        }
        if (lower < 0) {
            throw new AddressValueException(lower);
        }
        if (bitCount < 0 || bitCount >= 32 || defaultRadix < 2 || defaultRadix > 85) {
            throw new IllegalArgumentException();
        }
        this.bitCount = bitCount;
        if ((long)upper > this.getMaxValue()) {
            throw new AddressValueException(upper);
        }
        this.value = lower;
        this.upperValue = upper;
        this.defaultRadix = defaultRadix;
    }

    @Override
    public long getDivisionValue() {
        return this.value;
    }

    @Override
    public long getUpperDivisionValue() {
        return this.upperValue;
    }

    @Override
    protected byte[] getBytesImpl(boolean low) {
        byte[] byArray;
        if (low) {
            byte[] byArray2 = new byte[4];
            byArray2[0] = (byte)(this.value >>> 24);
            byArray2[1] = (byte)(this.value >>> 16);
            byArray2[2] = (byte)(this.value >>> 8);
            byArray = byArray2;
            byArray2[3] = (byte)this.value;
        } else {
            byte[] byArray3 = new byte[4];
            byArray3[0] = (byte)(this.value >>> 24);
            byArray3[1] = (byte)(this.value >>> 16);
            byArray3[2] = (byte)(this.upperValue >>> 8);
            byArray = byArray3;
            byArray3[3] = (byte)this.upperValue;
        }
        return byArray;
    }

    @Override
    public int getBitCount() {
        return this.bitCount;
    }

    @Override
    public int getMaxDigitCount() {
        return AddressBitsDivision.getMaxDigitCount(this.getDefaultTextualRadix(), this.getBitCount(), this.getMaxValue());
    }

    @Override
    protected boolean isSameValues(AddressDivisionBase other) {
        if (other instanceof AddressBitsDivision) {
            return this.isSameValues((AddressBitsDivision)other);
        }
        return false;
    }

    protected boolean isSameValues(AddressBitsDivision otherSegment) {
        return this.value == otherSegment.value && this.upperValue == otherSegment.upperValue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof AddressBitsDivision) {
            AddressBitsDivision otherSegments = (AddressBitsDivision)other;
            return this.getBitCount() == otherSegments.getBitCount() && otherSegments.isSameValues(this);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.value | this.upperValue << this.getBitCount();
    }

    @Override
    public int getDefaultTextualRadix() {
        return this.defaultRadix;
    }
}

