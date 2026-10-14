/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv4;

import inet.ipaddr.AddressValueException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.standard.IPAddressJoinedSegments;

public class IPv4JoinedSegments
extends IPAddressJoinedSegments {
    private static final long serialVersionUID = 4L;
    private static int[] MAX_CHARS = new int[3];

    public IPv4JoinedSegments(int joinedCount, int value) {
        super(joinedCount, value);
        if (joinedCount >= 4) {
            throw new AddressValueException(joinedCount);
        }
    }

    public IPv4JoinedSegments(int joinedCount, long value, Integer segmentPrefixLength) {
        super(joinedCount, value, segmentPrefixLength);
        if (joinedCount >= 4) {
            throw new AddressValueException(joinedCount);
        }
        if (segmentPrefixLength != null && segmentPrefixLength > 32) {
            throw new PrefixLenException(segmentPrefixLength);
        }
        this.checkMax(value);
    }

    public IPv4JoinedSegments(int joinedCount, long lower, long upper, Integer segmentPrefixLength) {
        super(joinedCount, lower, upper, segmentPrefixLength);
        if (joinedCount >= 4) {
            throw new AddressValueException(joinedCount);
        }
        if (segmentPrefixLength != null && segmentPrefixLength > 32) {
            throw new PrefixLenException(segmentPrefixLength);
        }
        this.checkMax(this.getUpperDivisionValue());
    }

    private void checkMax(long val) {
        long max = 0L;
        switch (this.joinedCount) {
            case 0: {
                max = 255L;
                break;
            }
            case 1: {
                max = 65535L;
                break;
            }
            case 2: {
                max = 0xFFFFFFL;
                break;
            }
            case 3: {
                max = 0xFFFFFFFFL;
            }
        }
        if (this.value > max) {
            throw new AddressValueException(this.value);
        }
    }

    @Override
    public int getMaxDigitCount() {
        int result = MAX_CHARS[this.joinedCount - 1];
        if (result == 0) {
            int n = super.getMaxDigitCount();
            IPv4JoinedSegments.MAX_CHARS[this.joinedCount - 1] = n;
            result = n;
        }
        return result;
    }

    @Override
    protected long getDivisionNetworkMask(int bits) {
        int totalBits = 8 * (this.joinedCount + 1);
        long fullMask = -1L << totalBits ^ 0xFFFFFFFFFFFFFFFFL;
        long networkMask = fullMask & fullMask << totalBits - bits;
        return networkMask;
    }

    @Override
    protected long getDivisionHostMask(int bits) {
        int totalBits = 8 * (this.joinedCount + 1);
        long hostMask = -1L << totalBits - bits ^ 0xFFFFFFFFFFFFFFFFL;
        return hostMask;
    }

    @Override
    protected int getBitsPerSegment() {
        return 8;
    }

    @Override
    public int getDefaultTextualRadix() {
        return 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof IPv4JoinedSegments) {
            IPv4JoinedSegments other = (IPv4JoinedSegments)o;
            return this.joinedCount == other.joinedCount && other.isSameValues(this);
        }
        return false;
    }

    @Override
    protected boolean isSameValues(AddressDivisionBase other) {
        return other instanceof IPv4JoinedSegments && super.isSameValues(other);
    }
}

