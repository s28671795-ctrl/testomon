/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.standard;

import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.IPAddressGenericDivision;
import inet.ipaddr.format.standard.AddressDivision;
import inet.ipaddr.format.standard.AddressDivisionGrouping;
import inet.ipaddr.format.util.AddressSegmentParams;

public abstract class IPAddressDivision
extends AddressDivision
implements IPAddressGenericDivision {
    private static final long serialVersionUID = 4L;
    private final Integer divisionNetworkPrefix;
    protected transient String cachedString;
    private transient Boolean isSinglePrefixBlock;

    protected IPAddressDivision() {
        this(null);
    }

    protected IPAddressDivision(Integer networkPrefixLength) {
        if (networkPrefixLength != null && networkPrefixLength < 0) {
            throw new PrefixLenException(networkPrefixLength);
        }
        this.divisionNetworkPrefix = networkPrefixLength;
    }

    @Override
    public boolean isPrefixed() {
        return this.divisionNetworkPrefix != null;
    }

    @Override
    public Integer getDivisionPrefixLength() {
        return this.divisionNetworkPrefix;
    }

    public boolean matchesWithPrefixMask(long value, Integer divisionPrefixLen) {
        if (divisionPrefixLen == null) {
            return this.matches(value);
        }
        long mask = this.getDivisionNetworkMask(divisionPrefixLen);
        long matchingValue = value & mask;
        return matchingValue == (this.getDivisionValue() & mask) && matchingValue == (this.getUpperDivisionValue() & mask);
    }

    protected abstract long getDivisionNetworkMask(int var1);

    protected abstract long getDivisionHostMask(int var1);

    public Integer getBlockMaskPrefixLength(boolean network) {
        int hostLength = this.getTrailingBitCount(network);
        long shifted = network ? ((this.getDivisionValue() ^ 0xFFFFFFFFFFFFFFFFL) & this.getMaxValue()) >>> hostLength : this.getDivisionValue() >>> hostLength;
        return shifted == 0L ? AddressDivisionGrouping.cacheBits(this.getBitCount() - hostLength) : null;
    }

    public int getTrailingBitCount(boolean network) {
        if (network) {
            return Long.numberOfTrailingZeros(this.getDivisionValue() | -1L << this.getBitCount());
        }
        return Long.numberOfTrailingZeros(this.getDivisionValue() ^ 0xFFFFFFFFFFFFFFFFL);
    }

    public int getLeadingBitCount(boolean network) {
        int extraLeading = 64 - this.getBitCount();
        if (network) {
            return Long.numberOfLeadingZeros((this.getDivisionValue() ^ 0xFFFFFFFFFFFFFFFFL) & this.getMaxValue()) - extraLeading;
        }
        return Long.numberOfLeadingZeros(this.getDivisionValue()) - extraLeading;
    }

    @Override
    protected boolean isPrefixBlock(long segmentValue, long upperValue, int divisionPrefixLen) {
        if (divisionPrefixLen == 0) {
            return segmentValue == 0L && upperValue == this.getMaxValue();
        }
        return IPAddressDivision.testRange(segmentValue, upperValue, upperValue, this.getDivisionNetworkMask(divisionPrefixLen), this.getDivisionHostMask(divisionPrefixLen));
    }

    @Override
    protected boolean isSinglePrefixBlock(long segmentValue, long upperValue, int divisionPrefixLen) {
        if (divisionPrefixLen == 0) {
            return segmentValue == 0L && upperValue == this.getMaxValue();
        }
        return IPAddressDivision.testRange(segmentValue, segmentValue, upperValue, this.getDivisionNetworkMask(divisionPrefixLen), this.getDivisionHostMask(divisionPrefixLen));
    }

    boolean isSinglePrefixBlock(long segmentValue, int divisionPrefixLen) {
        return this.isSinglePrefixBlock(segmentValue, this.getUpperDivisionValue(), divisionPrefixLen);
    }

    @Override
    public boolean containsPrefixBlock(int divisionPrefixLen) {
        return this.isPrefixBlock(this.getDivisionValue(), this.getUpperDivisionValue(), divisionPrefixLen);
    }

    @Override
    public boolean isPrefixBlock() {
        return this.isPrefixed() && this.containsPrefixBlock(this.getDivisionPrefixLength());
    }

    @Override
    public boolean containsSinglePrefixBlock(int divisionPrefixLen) {
        return this.isSinglePrefixBlock(this.getDivisionValue(), this.getUpperDivisionValue(), divisionPrefixLen);
    }

    @Override
    public boolean isSinglePrefixBlock() {
        if (this.isSinglePrefixBlock == null) {
            this.isSinglePrefixBlock = this.isPrefixed() && this.containsSinglePrefixBlock(this.getDivisionPrefixLength());
        }
        return this.isSinglePrefixBlock;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getString() {
        String result = this.cachedString;
        if (result == null) {
            IPAddressDivision iPAddressDivision = this;
            synchronized (iPAddressDivision) {
                result = this.cachedString;
                if (result == null) {
                    if (this.isSinglePrefixBlock() || !this.isMultiple()) {
                        result = this.getDefaultLowerString();
                    } else if (!this.isFullRange() || (result = this.getDefaultSegmentWildcardString()) == null) {
                        long upperValue = this.getUpperDivisionValue();
                        if (this.isPrefixBlock()) {
                            upperValue &= this.getDivisionNetworkMask(this.getDivisionPrefixLength());
                        }
                        result = this.getDefaultRangeString(this.getDivisionValue(), upperValue, this.getDefaultTextualRadix());
                    }
                    this.cachedString = result;
                }
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getWildcardString() {
        String result = this.cachedWildcardString;
        if (result == null) {
            IPAddressDivision iPAddressDivision = this;
            synchronized (iPAddressDivision) {
                result = this.cachedWildcardString;
                if (result == null) {
                    if (!this.isPrefixed() || !this.isMultiple()) {
                        result = this.getString();
                    } else if (!this.isFullRange() || (result = this.getDefaultSegmentWildcardString()) == null) {
                        result = this.getDefaultRangeString();
                    }
                    this.cachedWildcardString = result;
                }
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected String getCachedDefaultLowerString() {
        String result = this.cachedString;
        if (result == null) {
            IPAddressDivision iPAddressDivision = this;
            synchronized (iPAddressDivision) {
                result = this.cachedString;
                if (result == null) {
                    this.cachedString = result = this.getDefaultLowerString();
                }
            }
        }
        return result;
    }

    @Override
    protected void getUpperStringMasked(int radix, boolean uppercase, StringBuilder appendable) {
        long upperValue = this.getUpperDivisionValue();
        long mask = this.getDivisionNetworkMask(this.getDivisionPrefixLength());
        IPAddressDivision.toUnsignedStringCased(upperValue &= mask, radix, 0, uppercase, appendable);
    }

    @Override
    public int getPrefixAdjustedRangeString(int segmentIndex, AddressSegmentParams params, StringBuilder appendable) {
        return super.getPrefixAdjustedRangeString(segmentIndex, params, appendable);
    }
}

