/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.standard;

import inet.ipaddr.AddressValueException;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.standard.AddressDivisionGrouping;
import inet.ipaddr.format.standard.IPAddressDivision;

public class IPAddressBitsDivision
extends IPAddressDivision {
    private static final long serialVersionUID = 4L;
    private final int bitCount;
    private final int defaultRadix;
    private final int maxDigitCount;
    private final long value;
    private final long upperValue;
    private final long bitsMask;

    public IPAddressBitsDivision(long value, long upperValue, int bitCount, int defaultRadix) {
        this(value, upperValue, bitCount, defaultRadix, null, null);
    }

    public IPAddressBitsDivision(long value, long upperValue, int bitCount, int defaultRadix, Integer networkPrefixLength) {
        this(value, upperValue, bitCount, defaultRadix, null, networkPrefixLength);
    }

    public IPAddressBitsDivision(long value, long upperValue, int bitCount, int defaultRadix, IPAddressNetwork<?, ?, ?, ?, ?> network, Integer networkPrefixLength) {
        super(networkPrefixLength == null ? null : (bitCount < networkPrefixLength ? AddressDivisionGrouping.cacheBits(bitCount) : networkPrefixLength));
        this.bitCount = bitCount;
        if (value > upperValue) {
            long tmp = value;
            value = upperValue;
            upperValue = tmp;
        }
        if (value < 0L) {
            throw new AddressValueException(value);
        }
        long fullMask = -1L << bitCount;
        long max = fullMask ^ 0xFFFFFFFFFFFFFFFFL;
        if (upperValue > max) {
            throw new AddressValueException(upperValue);
        }
        networkPrefixLength = this.getDivisionPrefixLength();
        if (networkPrefixLength != null && networkPrefixLength < bitCount && network.getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            long mask = -1L << bitCount - networkPrefixLength;
            this.value = value & mask;
            this.upperValue = upperValue | mask ^ 0xFFFFFFFFFFFFFFFFL;
        } else {
            this.value = value;
            this.upperValue = upperValue;
        }
        this.defaultRadix = defaultRadix;
        this.bitsMask = max;
        this.maxDigitCount = IPAddressBitsDivision.getMaxDigitCount(defaultRadix, bitCount, max);
    }

    @Override
    public int getBitCount() {
        return this.bitCount;
    }

    @Override
    protected long getDivisionNetworkMask(int bits) {
        int bitShift = this.bitCount - bits;
        return this.bitsMask & -1L << bitShift;
    }

    @Override
    protected long getDivisionHostMask(int bits) {
        int bitShift = this.bitCount - bits;
        return -1L << bitShift ^ 0xFFFFFFFFFFFFFFFFL;
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
    protected boolean isSameValues(AddressDivisionBase other) {
        if (other instanceof IPAddressBitsDivision) {
            return this.isSameValues((IPAddressBitsDivision)other);
        }
        return false;
    }

    protected boolean isSameValues(IPAddressBitsDivision otherSegment) {
        return this.value == otherSegment.value && this.upperValue == otherSegment.upperValue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof IPAddressBitsDivision) {
            IPAddressBitsDivision otherSegments = (IPAddressBitsDivision)other;
            return this.getBitCount() == otherSegments.getBitCount() && otherSegments.isSameValues(this);
        }
        return false;
    }

    @Override
    public int getDefaultTextualRadix() {
        return this.defaultRadix;
    }

    @Override
    public int getMaxDigitCount() {
        return this.maxDigitCount;
    }
}

