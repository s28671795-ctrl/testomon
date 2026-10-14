/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.mac;

import inet.ipaddr.Address;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.AddressDivisionGroupingBase;
import inet.ipaddr.format.standard.AddressDivision;
import inet.ipaddr.format.standard.AddressDivisionGrouping;
import inet.ipaddr.format.string.AddressStringDivisionSeries;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.mac.MACAddress;
import inet.ipaddr.mac.MACAddressNetwork;
import inet.ipaddr.mac.MACAddressSection;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MACAddressSegment
extends AddressDivision
implements AddressSegment,
Iterable<MACAddressSegment> {
    private static final long serialVersionUID = 4L;
    public static final int MAX_CHARS = 2;
    private final int value;
    private final int upperValue;

    public MACAddressSegment(int value) {
        if (value < 0 || value > 255) {
            throw new AddressValueException(value);
        }
        this.value = this.upperValue = value;
    }

    public MACAddressSegment(int lower, int upper) {
        if (lower > upper) {
            int tmp = lower;
            lower = upper;
            upper = tmp;
        }
        if (lower < 0 || upper < 0 || upper > 255) {
            throw new AddressValueException(lower < 0 ? (long)lower : (long)upper);
        }
        this.value = lower;
        this.upperValue = upper;
    }

    @Override
    protected byte[] getBytesImpl(boolean low) {
        return new byte[]{(byte)(low ? this.getSegmentValue() : this.getUpperSegmentValue())};
    }

    protected boolean isPrefixBlock(int segmentPrefixLength) {
        if (segmentPrefixLength < 8) {
            int newLower;
            int mask = -1 << 8 - segmentPrefixLength;
            int lower = this.getSegmentValue();
            if (lower != (newLower = lower & mask)) {
                return false;
            }
            int upper = this.getUpperSegmentValue();
            return upper == (upper | ~mask);
        }
        return true;
    }

    protected MACAddressSegment toPrefixBlockSegment(int segmentPrefixLength) {
        if (segmentPrefixLength < 8 && !this.isPrefixBlock(segmentPrefixLength)) {
            int lower = this.getSegmentValue();
            int upper = this.getUpperSegmentValue();
            int mask = -1 << 8 - segmentPrefixLength;
            return this.getSegmentCreator().createRangeSegment(lower &= mask, upper |= ~mask);
        }
        return this;
    }

    protected MACAddressSegment setPrefixedSegment(Integer oldPrefixLength, Integer segmentPrefixLength, boolean zeroed) {
        return MACAddressSegment.setPrefixedSegment(this, oldPrefixLength, segmentPrefixLength, zeroed, this.getSegmentCreator());
    }

    private MACAddressNetwork.MACAddressCreator getSegmentCreator() {
        return this.getNetwork().getAddressCreator();
    }

    public MACAddressNetwork getNetwork() {
        return MACAddress.defaultMACNetwork();
    }

    @Override
    public int getValueCount() {
        return this.getUpperSegmentValue() - this.getSegmentValue() + 1;
    }

    @Override
    public int getPrefixValueCount(int segmentPrefixLength) {
        return MACAddressSegment.getPrefixValueCount(this, segmentPrefixLength);
    }

    @Override
    public int getBitCount() {
        return 8;
    }

    @Override
    public int getByteCount() {
        return 1;
    }

    @Override
    public long getMaxValue() {
        return 255L;
    }

    @Override
    public long getDivisionValue() {
        return this.getSegmentValue();
    }

    @Override
    public long getUpperDivisionValue() {
        return this.getUpperSegmentValue();
    }

    @Override
    public int getSegmentValue() {
        return this.value;
    }

    @Override
    public int getUpperSegmentValue() {
        return this.upperValue;
    }

    private MACAddressSegment getLowestOrHighest(boolean lowest) {
        if (!this.isMultiple()) {
            return this;
        }
        return this.getSegmentCreator().createSegment(lowest ? this.getSegmentValue() : this.getUpperSegmentValue());
    }

    @Override
    public MACAddressSegment getLower() {
        return this.getLowestOrHighest(true);
    }

    @Override
    public MACAddressSegment getUpper() {
        return this.getLowestOrHighest(false);
    }

    @Override
    public MACAddressSegment reverseBits(boolean perByte) {
        return this.reverseBits();
    }

    public MACAddressSegment reverseBits() {
        if (this.isMultiple()) {
            if (MACAddressSegment.isReversibleRange(this)) {
                return this;
            }
            throw new IncompatibleAddressException(this, "ipaddress.error.reverseRange");
        }
        int oldValue = this.value;
        int newValue = MACAddressSegment.reverseBits((byte)oldValue);
        if (oldValue == newValue) {
            return this;
        }
        MACAddressNetwork.MACAddressCreator creator = this.getSegmentCreator();
        return (MACAddressSegment)creator.createSegment(newValue);
    }

    @Override
    public MACAddressSegment reverseBytes() {
        return this;
    }

    @Override
    public boolean isBoundedBy(int value) {
        return this.getUpperSegmentValue() < value;
    }

    @Override
    protected boolean isSameValues(AddressDivisionBase other) {
        return other instanceof MACAddressSegment && this.isSameValues((MACAddressSegment)other);
    }

    protected boolean isSameValues(MACAddressSegment otherSegment) {
        return this.value == otherSegment.value && this.upperValue == otherSegment.upperValue;
    }

    @Override
    public int hashCode() {
        return MACAddressSegment.hash(this.value, this.upperValue, this.getBitCount());
    }

    static int hash(int lower, int upper, int bitCount) {
        return lower | upper << bitCount;
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof MACAddressSegment && ((MACAddressSegment)other).isSameValues(this);
    }

    public boolean contains(MACAddressSegment other) {
        return other.value >= this.value && other.upperValue <= this.upperValue;
    }

    @Override
    public int getDefaultTextualRadix() {
        return 16;
    }

    @Override
    public int getMaxDigitCount() {
        return 2;
    }

    @Override
    public boolean matches(int value) {
        return super.matches(value);
    }

    @Override
    public boolean matchesWithMask(int value, int mask) {
        return super.matchesWithMask(value, mask);
    }

    @Override
    public boolean matchesWithMask(int lowerValue, int upperValue, int mask) {
        return super.matchesWithMask(lowerValue, upperValue, mask);
    }

    void setString(CharSequence addressStr, boolean isStandardString, int lowerStringStartIndex, int lowerStringEndIndex, int originalLowerValue) {
        if (this.cachedWildcardString == null && isStandardString && (long)originalLowerValue == this.getDivisionValue()) {
            this.cachedWildcardString = addressStr.subSequence(lowerStringStartIndex, lowerStringEndIndex).toString();
        }
    }

    void setString(CharSequence addressStr, boolean isStandardRangeString, int lowerStringStartIndex, int upperStringEndIndex, int rangeLower, int rangeUpper) {
        if (this.cachedWildcardString == null) {
            if (this.isFullRange()) {
                this.cachedWildcardString = MACAddress.SEGMENT_WILDCARD_STR;
            } else if (isStandardRangeString && (long)rangeLower == this.getDivisionValue() && (long)rangeUpper == this.getUpperDivisionValue()) {
                this.cachedWildcardString = addressStr.subSequence(lowerStringStartIndex, upperStringEndIndex).toString();
            }
        }
    }

    public Iterable<MACAddressSegment> getIterable() {
        return this;
    }

    @Override
    public Iterator<MACAddressSegment> iterator() {
        return MACAddressSegment.iterator(this, this.getSegmentCreator(), null, false, false);
    }

    @Override
    public AddressComponentSpliterator<MACAddressSegment> spliterator() {
        MACAddressNetwork.MACAddressCreator creator = this.getSegmentCreator();
        int bitCount = this.getBitCount();
        return MACAddressSegment.createSegmentSpliterator(this, this.getSegmentValue(), this.getUpperSegmentValue(), this::iterator, (isLowest, isHighest, value, upperValue) -> MACAddressSegment.iterator(null, value, upperValue, bitCount, creator, null, false, false), (value, upperValue) -> creator.createSegment(value, upperValue, null));
    }

    public Stream<MACAddressSegment> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    Iterator<MACAddressSegment> prefixBlockIterator(int segmentPrefixLength) {
        return MACAddressSegment.iterator(this, this.getSegmentCreator(), segmentPrefixLength, true, true);
    }

    Iterator<MACAddressSegment> prefixIterator(int segmentPrefixLength) {
        return MACAddressSegment.iterator(this, this.getSegmentCreator(), segmentPrefixLength, true, false);
    }

    @Override
    public int getMaxSegmentValue() {
        return 255;
    }

    @Override
    public boolean prefixEquals(AddressSegment o, int segmentPrefixLength) {
        if (segmentPrefixLength < 0) {
            throw new PrefixLenException(segmentPrefixLength);
        }
        if (o instanceof MACAddressSegment) {
            MACAddressSegment other = (MACAddressSegment)o;
            int shift = this.getBitCount() - segmentPrefixLength;
            if (shift <= 0) {
                return this.isSameValues(other);
            }
            return other.getSegmentValue() >>> shift == this.getSegmentValue() >>> shift && other.getUpperSegmentValue() >>> shift <= this.getUpperSegmentValue() >>> shift;
        }
        return false;
    }

    @Override
    public boolean contains(AddressSegment other) {
        return other instanceof MACAddressSegment && other.getSegmentValue() >= this.value && other.getUpperSegmentValue() <= this.upperValue;
    }

    @Override
    protected String getDefaultSegmentWildcardString() {
        return Address.SEGMENT_WILDCARD_STR;
    }

    @Override
    public String toHexString(boolean with0xPrefix) {
        return this.toNormalizedString(with0xPrefix ? MACAddressSection.MACStringCache.hexPrefixedParams : MACAddressSection.MACStringCache.hexParams);
    }

    @Override
    public String toNormalizedString() {
        return this.toNormalizedString(MACAddressSection.MACStringCache.canonicalParams);
    }

    public String toNormalizedString(AddressDivisionGrouping.StringOptions options) {
        AddressDivisionGroupingBase.AddressStringParams<AddressStringDivisionSeries> params = MACAddressSection.toParams(options);
        StringBuilder builder = new StringBuilder(params.getDivisionStringLength(this));
        return params.appendDivision(builder, this).toString();
    }

    @Override
    public boolean containsPrefixBlock(int divisionPrefixLen) {
        return this.isPrefixBlock(this.getDivisionValue(), this.getUpperDivisionValue(), divisionPrefixLen);
    }

    @Override
    public boolean containsSinglePrefixBlock(int divisionPrefixLen) {
        return this.isSinglePrefixBlock(this.getDivisionValue(), this.getUpperDivisionValue(), divisionPrefixLen);
    }
}

