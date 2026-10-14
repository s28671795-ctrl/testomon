/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format;

import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressGenericDivision;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.string.AddressStringDivisionSeries;
import java.math.BigInteger;

public interface AddressDivisionSeries
extends AddressItem,
AddressStringDivisionSeries {
    default public int isMore(AddressDivisionSeries other) {
        if (!this.isMultiple()) {
            return other.isMultiple() ? -1 : 0;
        }
        if (!other.isMultiple()) {
            return 1;
        }
        return this.getCount().compareTo(other.getCount());
    }

    @Override
    public AddressGenericDivision getDivision(int var1);

    public String[] getDivisionStrings();

    public Integer getPrefixLength();

    public boolean isPrefixed();

    public boolean isPrefixBlock();

    public boolean isSinglePrefixBlock();

    default public BigInteger getPrefixCount() {
        Integer prefixLength = this.getPrefixLength();
        if (prefixLength == null || prefixLength >= this.getBitCount()) {
            return this.getCount();
        }
        return this.getPrefixCount(prefixLength);
    }

    @Override
    default public BigInteger getPrefixCount(int prefixLength) {
        if (prefixLength < 0 || prefixLength > this.getBitCount()) {
            throw new PrefixLenException((AddressItem)this, prefixLength);
        }
        BigInteger result = BigInteger.ONE;
        if (this.isMultiple()) {
            int divisionCount = this.getDivisionCount();
            int divPrefixLength = prefixLength;
            for (int i = 0; i < divisionCount; ++i) {
                AddressGenericDivision division = this.getDivision(i);
                int divBitCount = division.getBitCount();
                if (division.isMultiple()) {
                    BigInteger divCount = divPrefixLength < divBitCount ? division.getPrefixCount(divPrefixLength) : division.getCount();
                    result = result.multiply(divCount);
                }
                if (divPrefixLength <= divBitCount) break;
                divPrefixLength -= divBitCount;
            }
        }
        return result;
    }

    @Override
    default public BigInteger getCount() {
        BigInteger result = BigInteger.ONE;
        int count = this.getDivisionCount();
        if (count > 0) {
            for (int i = 0; i < count; ++i) {
                AddressGenericDivision div = this.getDivision(i);
                if (!div.isMultiple()) continue;
                BigInteger divCount = div.getCount();
                result = result.multiply(divCount);
            }
        }
        return result;
    }

    default public BigInteger getBlockCount(int divCount) {
        if (divCount < 0) {
            throw new IllegalArgumentException();
        }
        BigInteger result = BigInteger.ONE;
        int divisionCount = this.getDivisionCount();
        if (divCount < divisionCount) {
            divisionCount = divCount;
        }
        for (int i = 0; i < divisionCount; ++i) {
            AddressGenericDivision division = this.getDivision(i);
            if (!division.isMultiple()) continue;
            result = result.multiply(division.getCount());
        }
        return result;
    }

    @Override
    default public int getBitCount() {
        int count = this.getDivisionCount();
        int bitCount = 0;
        for (int i = 0; i < count; ++i) {
            bitCount += this.getDivision(i).getBitCount();
        }
        return bitCount;
    }

    default public boolean isSequential() {
        int count = this.getDivisionCount();
        if (count > 1) {
            for (int i = 0; i < count; ++i) {
                if (!this.getDivision(i).isMultiple()) continue;
                ++i;
                while (i < count) {
                    if (!this.getDivision(i).isFullRange()) {
                        return false;
                    }
                    ++i;
                }
                return true;
            }
        }
        return true;
    }

    default public int getSequentialBlockIndex() {
        int segCount = this.getDivisionCount();
        if (segCount == 0) {
            return 0;
        }
        --segCount;
        while (segCount > 0 && this.getDivision(segCount).isFullRange()) {
            --segCount;
        }
        return segCount;
    }
}

