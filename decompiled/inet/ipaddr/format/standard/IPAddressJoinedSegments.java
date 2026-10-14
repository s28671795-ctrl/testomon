/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.standard;

import inet.ipaddr.AddressValueException;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.standard.IPAddressDivision;

public abstract class IPAddressJoinedSegments
extends IPAddressDivision {
    private static final long serialVersionUID = 4L;
    protected final int joinedCount;
    protected final long value;
    protected final long upperValue;

    public IPAddressJoinedSegments(int joinedCount, int value) {
        if (value < 0) {
            throw new AddressValueException(value);
        }
        if (joinedCount <= 0) {
            throw new AddressValueException(joinedCount);
        }
        this.value = this.upperValue = (long)value;
        this.joinedCount = joinedCount;
    }

    public IPAddressJoinedSegments(int joinedCount, long value, Integer segmentPrefixLength) {
        this(joinedCount, value, value, segmentPrefixLength);
    }

    public IPAddressJoinedSegments(int joinedCount, long lower, long upper, Integer segmentPrefixLength) {
        super(segmentPrefixLength);
        if (lower < 0L || upper < 0L) {
            throw new AddressValueException(lower < 0L ? lower : upper);
        }
        if (joinedCount <= 0) {
            throw new AddressValueException(joinedCount);
        }
        if (lower > upper) {
            long tmp = lower;
            lower = upper;
            upper = tmp;
        }
        this.value = lower;
        this.upperValue = upper;
        this.joinedCount = joinedCount;
    }

    public int getJoinedCount() {
        return this.joinedCount;
    }

    @Override
    public long getDivisionValue() {
        return this.value;
    }

    @Override
    public long getUpperDivisionValue() {
        return this.upperValue;
    }

    protected abstract int getBitsPerSegment();

    @Override
    public int getBitCount() {
        return (this.joinedCount + 1) * this.getBitsPerSegment();
    }

    @Override
    public int getMaxDigitCount() {
        return IPAddressJoinedSegments.getMaxDigitCount(this.getDefaultTextualRadix(), this.getBitCount(), this.getMaxValue());
    }

    @Override
    protected boolean isSameValues(AddressDivisionBase other) {
        if (other instanceof IPAddressJoinedSegments) {
            return this.isSameValues((IPAddressJoinedSegments)other);
        }
        return false;
    }

    protected boolean isSameValues(IPAddressJoinedSegments otherSegment) {
        return this.value == otherSegment.value && this.upperValue == otherSegment.upperValue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof IPAddressJoinedSegments) {
            IPAddressJoinedSegments otherSegments = (IPAddressJoinedSegments)other;
            return this.getBitCount() == otherSegments.getBitCount() && otherSegments.isSameValues(this);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int)(this.value | this.upperValue << this.getBitCount());
    }
}

