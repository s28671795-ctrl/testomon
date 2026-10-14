/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format;

import inet.ipaddr.Address;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.AddressDivisionGroupingBase;
import java.io.Serializable;
import java.math.BigInteger;

public interface AddressItem
extends Comparable<AddressItem>,
Serializable {
    @Override
    default public int compareTo(AddressItem other) {
        return Address.DEFAULT_ADDRESS_COMPARATOR.compare(this, other);
    }

    default public BigInteger getCount() {
        return this.getUpperValue().subtract(this.getValue()).add(BigInteger.ONE);
    }

    default public BigInteger getPrefixCount(int prefixLength) {
        if (prefixLength < 0) {
            throw new PrefixLenException(this, prefixLength);
        }
        int bitCount = this.getBitCount();
        if (bitCount <= prefixLength) {
            return this.getCount();
        }
        int shiftAdjustment = bitCount - prefixLength;
        BigInteger lower = this.getValue();
        BigInteger upper = this.getUpperValue();
        return upper.shiftRight(shiftAdjustment).subtract(lower.shiftRight(shiftAdjustment)).add(BigInteger.ONE);
    }

    public int getBitCount();

    default public int getByteCount() {
        return this.getBitCount() + 7 >>> 3;
    }

    default public boolean isMultiple() {
        return !this.getCount().equals(BigInteger.ONE);
    }

    public byte[] getBytes();

    public byte[] getBytes(byte[] var1);

    public byte[] getBytes(byte[] var1, int var2);

    public byte[] getUpperBytes();

    public byte[] getUpperBytes(byte[] var1);

    public byte[] getUpperBytes(byte[] var1, int var2);

    public BigInteger getValue();

    public BigInteger getUpperValue();

    public boolean isZero();

    public boolean includesZero();

    public boolean isMax();

    public boolean includesMax();

    default public boolean isFullRange() {
        return this.includesZero() && this.includesMax();
    }

    default public boolean containsPrefixBlock(int divisionPrefixLen) {
        if (divisionPrefixLen == 0) {
            return this.isFullRange();
        }
        BigInteger upper = this.getUpperValue();
        return AddressDivisionBase.testRange(this.getValue(), upper, upper, this.getBitCount(), divisionPrefixLen);
    }

    default public boolean containsSinglePrefixBlock(int divisionPrefixLen) {
        if (divisionPrefixLen == 0) {
            return this.isFullRange();
        }
        BigInteger lower = this.getValue();
        BigInteger upper = this.getUpperValue();
        return AddressDivisionBase.testRange(lower, lower, upper, this.getBitCount(), divisionPrefixLen);
    }

    default public int getMinPrefixLengthForBlock() {
        BigInteger upper;
        int result = this.getBitCount();
        BigInteger lower = this.getValue();
        if (!lower.equals(upper = this.getUpperValue())) {
            long up;
            int upperOnes;
            long low;
            int lowerZeros;
            int longBits = 64;
            while ((lowerZeros = Long.numberOfTrailingZeros(low = lower.longValue())) != 0 && (upperOnes = Long.numberOfTrailingZeros((up = upper.longValue()) ^ 0xFFFFFFFFFFFFFFFFL)) != 0) {
                int prefixedBitCount = Math.min(lowerZeros, upperOnes);
                result -= prefixedBitCount;
                if (prefixedBitCount < longBits) break;
                lower = lower.shiftRight(longBits);
                if (!(upper = upper.shiftRight(longBits)).equals(BigInteger.ZERO)) continue;
            }
        }
        return result;
    }

    default public Integer getPrefixLengthForSingleBlock() {
        int shift;
        int divPrefix = this.getMinPrefixLengthForBlock();
        BigInteger lower = this.getValue();
        BigInteger upper = this.getUpperValue();
        int bitCount = this.getBitCount();
        if (divPrefix == bitCount ? lower.equals(upper) : lower.shiftRight(shift = bitCount - divPrefix).equals(upper.shiftRight(shift))) {
            return AddressDivisionGroupingBase.cacheBits(divPrefix);
        }
        return null;
    }

    public static BigInteger getBlockSize(int bitCount) {
        return BigInteger.ONE.shiftLeft(bitCount);
    }

    public static Integer getBitsForCount(long count) {
        if (count <= 0L) {
            return null;
        }
        int logBase2 = 63 - Long.numberOfLeadingZeros(count);
        if (((-1L << logBase2 ^ 0xFFFFFFFFFFFFFFFFL) & count) != 0L) {
            ++logBase2;
        }
        return logBase2;
    }
}

