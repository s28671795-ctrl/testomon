/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.Address;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.AddressDivisionGroupingBase;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.standard.AddressDivision;
import inet.ipaddr.format.standard.IPAddressDivision;
import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.format.validate.ParsedIPAddress;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class IPAddressSegment
extends IPAddressDivision
implements AddressSegment {
    private static final long serialVersionUID = 4L;
    private final int value;
    private final int upperValue;

    protected IPAddressSegment(int value) {
        if (value < 0) {
            throw new AddressValueException(value);
        }
        this.value = this.upperValue = value;
    }

    protected IPAddressSegment(int value, Integer segmentPrefixLength) {
        this(value, value, segmentPrefixLength);
    }

    protected IPAddressSegment(int lower, int upper, Integer segmentPrefixLength) {
        super(segmentPrefixLength);
        if (lower < 0 || upper < 0) {
            throw new AddressValueException(lower < 0 ? (long)lower : (long)upper);
        }
        if (lower > upper) {
            int tmp = lower;
            lower = upper;
            upper = tmp;
        }
        if ((segmentPrefixLength = this.getSegmentPrefixLength()) == null || segmentPrefixLength >= this.getBitCount() || !this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            this.value = lower;
            this.upperValue = upper;
        } else {
            int mask = this.getSegmentNetworkMask(segmentPrefixLength);
            this.value = lower & mask;
            this.upperValue = upper | this.getSegmentHostMask(segmentPrefixLength);
        }
    }

    public abstract IPAddressNetwork<?, ?, ?, ?, ?> getNetwork();

    public boolean isIPv4() {
        return false;
    }

    public boolean isIPv6() {
        return false;
    }

    public abstract IPAddress.IPVersion getIPVersion();

    protected static Integer getSplitSegmentPrefix(int bitsPerSegment, Integer networkPrefixLength, int segmentIndex) {
        return IPAddressSection.getSegmentPrefixLength(bitsPerSegment, networkPrefixLength, segmentIndex);
    }

    @Override
    protected long getDivisionNetworkMask(int bits) {
        return this.getSegmentNetworkMask(bits);
    }

    @Override
    protected long getDivisionHostMask(int bits) {
        return this.getSegmentHostMask(bits);
    }

    protected abstract int getSegmentNetworkMask(int var1);

    protected abstract int getSegmentHostMask(int var1);

    @Override
    public int getMinPrefixLengthForBlock() {
        if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() && this.isPrefixed() && this.getSegmentPrefixLength() == 0) {
            return 0;
        }
        return super.getMinPrefixLengthForBlock();
    }

    public static int getMaxSegmentValue(IPAddress.IPVersion version) {
        return version.isIPv4() ? 255 : 65535;
    }

    protected boolean isChangedByPrefix(Integer bits, boolean smallerOnly) {
        boolean hasBits;
        boolean bl = hasBits = bits != null;
        if (hasBits && (bits < 0 || bits > this.getBitCount())) {
            throw new PrefixLenException((AddressItem)this, bits);
        }
        if (smallerOnly) {
            if (this.isPrefixed()) {
                return hasBits && bits < this.getSegmentPrefixLength();
            }
        } else if (this.isPrefixed()) {
            return !hasBits || bits.intValue() != this.getSegmentPrefixLength().intValue();
        }
        return hasBits;
    }

    protected <S extends IPAddressSegment> S toPrefixedSegment(Integer segmentPrefixLength, AddressNetwork.AddressSegmentCreator<S> creator) {
        boolean hasBits;
        int lower = this.getSegmentValue();
        int upper = this.getUpperSegmentValue();
        boolean bl = hasBits = segmentPrefixLength != null;
        if (lower != upper) {
            if (!hasBits) {
                return (S)((IPAddressSegment)creator.createSegment(lower, upper, null));
            }
            return (S)((IPAddressSegment)creator.createSegment(lower, upper, segmentPrefixLength));
        }
        return (S)(hasBits ? (IPAddressSegment)creator.createSegment(lower, segmentPrefixLength) : (IPAddressSegment)creator.createSegment(lower));
    }

    @Override
    public boolean isPrefixBlock() {
        return this.isPrefixed() && this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() || super.isPrefixBlock();
    }

    protected boolean isNetworkChangedByPrefix(Integer bits, boolean withPrefixLength) {
        boolean hasBits;
        boolean bl = hasBits = bits != null;
        if (hasBits && (bits < 0 || bits > this.getBitCount())) {
            throw new PrefixLenException((AddressItem)this, bits);
        }
        boolean thisHasPrefix = this.isPrefixed();
        if ((withPrefixLength &= hasBits) != thisHasPrefix) {
            return true;
        }
        if (!hasBits || bits != this.getDivisionPrefixLength()) {
            return true;
        }
        return !this.containsPrefixBlock(bits);
    }

    protected boolean isNetworkChangedByPrefixNonNull(int prefixBitCount) {
        return !this.isPrefixed() || prefixBitCount != this.getDivisionPrefixLength() || !this.containsPrefixBlock(prefixBitCount);
    }

    public IPAddressSegment toNetworkSegment(Integer segmentPrefixLength) {
        return this.toNetworkSegment(segmentPrefixLength, true);
    }

    public abstract IPAddressSegment toNetworkSegment(Integer var1, boolean var2);

    protected <S extends IPAddressSegment> S toNetworkSegment(Integer segmentPrefixLength, boolean withPrefixLength, AddressNetwork.AddressSegmentCreator<S> creator) {
        boolean hasPrefLen;
        int newLower = this.getSegmentValue();
        int newUpper = this.getUpperSegmentValue();
        boolean bl = hasPrefLen = segmentPrefixLength != null;
        if (hasPrefLen) {
            int mask = this.getSegmentNetworkMask(segmentPrefixLength);
            newLower &= mask;
            newUpper |= this.getSegmentHostMask(segmentPrefixLength);
        }
        boolean bl2 = withPrefixLength = withPrefixLength && hasPrefLen;
        if (newLower != newUpper) {
            if (!withPrefixLength) {
                return (S)((IPAddressSegment)creator.createSegment(newLower, newUpper, null));
            }
            return (S)((IPAddressSegment)creator.createSegment(newLower, newUpper, segmentPrefixLength));
        }
        return (S)(withPrefixLength ? (IPAddressSegment)creator.createSegment(newLower, segmentPrefixLength) : (IPAddressSegment)creator.createSegment(newLower));
    }

    public abstract IPAddressSegment toHostSegment(Integer var1);

    protected <S extends IPAddressSegment> S toHostSegment(Integer segmentPrefixLength, AddressNetwork.AddressSegmentCreator<S> creator) {
        int newUpper;
        int upper;
        int mask = segmentPrefixLength == null ? 0 : this.getSegmentHostMask(segmentPrefixLength);
        int lower = this.getSegmentValue();
        ParsedIPAddress.Masker masker = IPAddressSegment.maskRange(lower, upper = this.getUpperSegmentValue(), mask, this.getMaxValue());
        int newLower = (int)masker.getMaskedLower(lower, mask);
        if (newLower != (newUpper = (int)masker.getMaskedUpper(upper, mask))) {
            return (S)((IPAddressSegment)creator.createSegment(newLower, newUpper, null));
        }
        return (S)((IPAddressSegment)creator.createSegment(newLower));
    }

    protected boolean isHostChangedByPrefix(Integer bits) {
        boolean hasBits;
        boolean bl = hasBits = bits != null;
        if (hasBits && (bits < 0 || bits > this.getBitCount())) {
            throw new PrefixLenException((AddressItem)this, bits);
        }
        if (this.isPrefixed()) {
            return true;
        }
        int mask = !hasBits ? 0 : this.getSegmentHostMask(bits);
        int value = this.getSegmentValue();
        int upperValue = this.getUpperSegmentValue();
        return value != (value & mask) || upperValue != (upperValue & mask);
    }

    protected boolean isChangedBy(int newValue, int newUpperValue, Integer segmentPrefixLength) throws IncompatibleAddressException {
        int value = this.getSegmentValue();
        int upperValue = this.getUpperSegmentValue();
        return value != newValue || upperValue != newUpperValue || (this.isPrefixed() ? !this.getSegmentPrefixLength().equals(segmentPrefixLength) : segmentPrefixLength != null);
    }

    protected static ParsedIPAddress.Masker maskRange(long value, long upperValue, long maskValue, long maxValue) {
        return AddressDivision.maskRange(value, upperValue, maskValue, maxValue);
    }

    public AddressDivision.MaskResult maskRange(int maskValue) {
        int value = this.getSegmentValue();
        int upperValue = this.getUpperSegmentValue();
        ParsedIPAddress.Masker masker = AddressDivision.maskRange(value, upperValue, maskValue, this.getMaxSegmentValue());
        return new AddressDivision.MaskResult(value, upperValue, maskValue, masker);
    }

    protected static ParsedIPAddress.BitwiseOrer bitwiseOrRange(long value, long upperValue, long maskValue, long maxValue) {
        return AddressDivision.bitwiseOrRange(value, upperValue, maskValue, maxValue);
    }

    public AddressDivision.BitwiseOrResult bitwiseOrRange(int maskValue) {
        int value = this.getSegmentValue();
        int upperValue = this.getUpperSegmentValue();
        ParsedIPAddress.BitwiseOrer orer = AddressDivision.bitwiseOrRange(value, upperValue, maskValue, this.getMaxSegmentValue());
        return new AddressDivision.BitwiseOrResult(value, upperValue, maskValue, orer);
    }

    @Override
    public abstract IPAddressSegment getLower();

    @Override
    public abstract IPAddressSegment getUpper();

    protected static <S extends IPAddressSegment> S getLowestOrHighest(S original, AddressNetwork.AddressSegmentCreator<S> segmentCreator, boolean lowest) {
        boolean isAllSubnets = original.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (!(original.isMultiple() || isAllSubnets && original.isPrefixed())) {
            return original;
        }
        return (S)((IPAddressSegment)segmentCreator.createSegment(lowest ? original.getSegmentValue() : original.getUpperSegmentValue(), isAllSubnets ? null : original.getSegmentPrefixLength()));
    }

    public abstract Iterable<? extends IPAddressSegment> getIterable();

    public abstract Iterator<? extends IPAddressSegment> iterator();

    @Override
    public abstract AddressComponentSpliterator<? extends IPAddressSegment> spliterator();

    public abstract Stream<? extends IPAddressSegment> stream();

    public abstract Iterator<? extends IPAddressSegment> prefixBlockIterator();

    public abstract AddressComponentSpliterator<? extends IPAddressSegment> prefixBlockSpliterator();

    public abstract Stream<? extends IPAddressSegment> prefixBlockStream();

    public abstract Iterator<? extends IPAddressSegment> prefixIterator();

    public abstract AddressComponentSpliterator<? extends IPAddressSegment> prefixSpliterator();

    protected static <S extends IPAddressSegment> AddressComponentSpliterator<S> prefixSpliterator(S seg, int segPrefLength, IPAddressNetwork.IPAddressCreator<?, ?, ?, S, ?> creator, Supplier<Iterator<S>> iteratorProvider) {
        int upperShiftMask;
        int bitCount = seg.getBitCount();
        int shiftAdjustment = bitCount - segPrefLength;
        if (shiftAdjustment > 0) {
            int shiftMask = -1 << shiftAdjustment;
            upperShiftMask = ~shiftMask;
        } else {
            int shiftMask = -1;
            upperShiftMask = 0;
        }
        int originalValue = seg.getSegmentValue();
        int originalUpperValue = seg.getUpperSegmentValue();
        int originalValuePrefix = originalValue >>> shiftAdjustment;
        int originalUpperValuePrefix = originalUpperValue >>> shiftAdjustment;
        Integer segPrefixLength = IPAddressSection.cacheBits(segPrefLength);
        AddressDivisionBase.IntBinaryIteratorProvider subIteratorProvider = (isLowest, isHighest, value, upperValue) -> {
            if (isLowest || isHighest) {
                value = isLowest ? originalValue : value << shiftAdjustment;
                upperValue = isHighest ? originalUpperValue : upperValue << shiftAdjustment | upperShiftMask;
                return IPAddressSegment.iterator(null, value, upperValue, bitCount, creator, segPrefixLength, true, false);
            }
            return IPAddressSegment.iterator(null, value << shiftAdjustment, upperValue << shiftAdjustment, bitCount, creator, segPrefixLength, true, true);
        };
        return IPAddressSegment.createSegmentSpliterator(seg, originalValuePrefix, originalUpperValuePrefix, iteratorProvider, subIteratorProvider, (value, upperValue) -> {
            value = value == originalValuePrefix ? originalValue : value << shiftAdjustment;
            upperValue = upperValue == originalUpperValuePrefix ? originalUpperValue : upperValue << shiftAdjustment | upperShiftMask;
            return (IPAddressSegment)creator.createSegment(value, upperValue, segPrefLength);
        });
    }

    public abstract Stream<? extends IPAddressSegment> prefixStream();

    public abstract Iterator<? extends IPAddressSegment> prefixBlockIterator(int var1);

    protected static <S extends IPAddressSegment> AddressComponentSpliterator<S> prefixBlockSpliterator(S seg, int segPrefLength, IPAddressNetwork.IPAddressCreator<?, ?, ?, S, ?> creator, Supplier<Iterator<S>> iteratorProvider) {
        int upperShiftMask;
        int bitCount = seg.getBitCount();
        int shiftAdjustment = bitCount - segPrefLength;
        if (shiftAdjustment > 0) {
            int shiftMask = -1 << shiftAdjustment;
            upperShiftMask = ~shiftMask;
        } else {
            int shiftMask = -1;
            upperShiftMask = 0;
        }
        Integer segmentPrefixLength = IPAddressSection.cacheBits(segPrefLength);
        return IPAddressSegment.createSegmentSpliterator(seg, seg.getSegmentValue() >>> shiftAdjustment, seg.getUpperSegmentValue() >>> shiftAdjustment, iteratorProvider, (isLowest, isHighest, value, upperValue) -> IPAddressSegment.iterator(null, value << shiftAdjustment, upperValue << shiftAdjustment | upperShiftMask, bitCount, creator, segmentPrefixLength, true, true), (value, upperValue) -> (IPAddressSegment)creator.createSegment(value << shiftAdjustment, upperValue << shiftAdjustment | upperShiftMask, segmentPrefixLength));
    }

    public abstract AddressComponentSpliterator<? extends IPAddressSegment> prefixBlockSpliterator(int var1);

    public abstract Stream<? extends IPAddressSegment> prefixBlockStream(int var1);

    public static int getBitCount(IPAddress.IPVersion version) {
        return version.isIPv4() ? 8 : 16;
    }

    public static int getByteCount(IPAddress.IPVersion version) {
        return version.isIPv4() ? 1 : 2;
    }

    public static int getDefaultTextualRadix(IPAddress.IPVersion version) {
        return version.isIPv4() ? 10 : 16;
    }

    @Override
    public boolean matches(int value) {
        return super.matches(value);
    }

    public boolean matchesWithPrefixMask(int value, Integer segmentPrefixLength) {
        return super.matchesWithPrefixMask(value, segmentPrefixLength);
    }

    @Override
    public boolean matchesWithMask(int value, int mask) {
        return super.matchesWithMask(value, mask);
    }

    @Override
    public boolean matchesWithMask(int lowerValue, int upperValue, int mask) {
        return super.matchesWithMask(lowerValue, upperValue, mask);
    }

    @Override
    public int getValueCount() {
        return this.getUpperSegmentValue() - this.getSegmentValue() + 1;
    }

    public int getPrefixValueCount() {
        Integer prefixLength = this.getSegmentPrefixLength();
        if (prefixLength == null) {
            return this.getValueCount();
        }
        return IPAddressSegment.getPrefixValueCount(this, prefixLength);
    }

    @Override
    public BigInteger getCount() {
        return BigInteger.valueOf(this.getValueCount());
    }

    @Override
    public BigInteger getPrefixCount(int segmentPrefixLength) {
        return BigInteger.valueOf(this.getPrefixValueCount(segmentPrefixLength));
    }

    @Override
    public int getPrefixValueCount(int segmentPrefixLength) {
        if (segmentPrefixLength < 0) {
            throw new PrefixLenException((AddressItem)this, segmentPrefixLength);
        }
        int bitCount = this.getBitCount();
        if (bitCount <= segmentPrefixLength) {
            return this.getValueCount();
        }
        int shiftAdjustment = bitCount - segmentPrefixLength;
        return (this.getUpperSegmentValue() >>> shiftAdjustment) - (this.getSegmentValue() >>> shiftAdjustment) + 1;
    }

    protected int highByte() {
        return IPAddressSegment.highByte(this.getSegmentValue());
    }

    protected int lowByte() {
        return IPAddressSegment.lowByte(this.getSegmentValue());
    }

    protected static int highByte(int value) {
        return value >> 8;
    }

    protected static int lowByte(int value) {
        return value & 0xFF;
    }

    @Override
    public long getMaxValue() {
        return this.getMaxSegmentValue();
    }

    @Override
    public boolean isMultiple() {
        return this.getSegmentValue() != this.getUpperSegmentValue();
    }

    @Override
    public int getSegmentValue() {
        return this.value;
    }

    @Override
    public int getUpperSegmentValue() {
        return this.upperValue;
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
    public abstract IPAddressSegment reverseBits(boolean var1);

    @Override
    public abstract IPAddressSegment reverseBytes();

    @Deprecated
    public abstract IPAddressSegment removePrefixLength();

    public abstract IPAddressSegment toZeroHost();

    @Deprecated
    public abstract IPAddressSegment removePrefixLength(boolean var1);

    public abstract IPAddressSegment withoutPrefixLength();

    protected static <S extends IPAddressSegment> S toZeroHost(S original, AddressNetwork.AddressSegmentCreator<S> creator) {
        if (original.isPrefixed()) {
            int lower = original.getSegmentValue();
            int upper = original.getUpperSegmentValue();
            Integer segmentPrefixLength = original.getSegmentPrefixLength();
            int mask = original.getSegmentNetworkMask(segmentPrefixLength);
            int newLower = lower & mask;
            int newUpper = upper & mask;
            boolean allPrefsSubnets = original.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
            if (allPrefsSubnets) {
                return (S)((IPAddressSegment)creator.createSegment(newLower, newUpper, null));
            }
            if (newLower == lower && newUpper == upper) {
                return original;
            }
            return (S)((IPAddressSegment)creator.createSegment(newLower, newUpper, segmentPrefixLength));
        }
        if (original.isZero()) {
            return original;
        }
        return (S)((IPAddressSegment)creator.createSegment(0, null));
    }

    protected static <S extends IPAddressSegment> S removePrefix(S original, boolean zeroed, AddressNetwork.AddressSegmentCreator<S> creator) {
        if (original.isPrefixed()) {
            int lower = original.getSegmentValue();
            int upper = original.getUpperSegmentValue();
            if (zeroed) {
                long maxValue;
                long upperValue;
                int maskBits = original.getSegmentNetworkMask(original.getSegmentPrefixLength());
                long value = original.getDivisionValue();
                ParsedIPAddress.Masker masker = IPAddressSegment.maskRange(value, upperValue = original.getUpperDivisionValue(), maskBits, maxValue = original.getMaxValue());
                if (!masker.isSequential()) {
                    throw new IncompatibleAddressException(original, maskBits, "ipaddress.error.maskMismatch");
                }
                return (S)((IPAddressSegment)creator.createSegment((int)masker.getMaskedLower(lower, maskBits), (int)masker.getMaskedUpper(upper, maskBits), null));
            }
            return (S)((IPAddressSegment)creator.createSegment(lower, upper, null));
        }
        return original;
    }

    @Override
    public boolean isBoundedBy(int value) {
        return this.getUpperSegmentValue() < value;
    }

    public Integer getSegmentPrefixLength() {
        return this.getDivisionPrefixLength();
    }

    @Override
    public int hashCode() {
        return IPAddressSegment.hash(this.getSegmentValue(), this.getUpperSegmentValue(), this.getBitCount());
    }

    static int hash(int lower, int upper, int bitCount) {
        return lower | upper << bitCount;
    }

    protected boolean isSameValues(AddressSegment otherSegment) {
        return this.getSegmentValue() == otherSegment.getSegmentValue() && this.getUpperSegmentValue() == otherSegment.getUpperSegmentValue();
    }

    public boolean prefixEquals(IPAddressSegment other) {
        Integer prefLength = this.getSegmentPrefixLength();
        if (prefLength == null) {
            return this.equals(other);
        }
        return this.prefixEquals(other, prefLength);
    }

    @Override
    public boolean prefixEquals(AddressSegment other, int prefixLength) {
        if (prefixLength < 0) {
            throw new PrefixLenException(prefixLength);
        }
        int shift = this.getBitCount() - prefixLength;
        if (shift <= 0) {
            return this.isSameValues(other);
        }
        return other.getSegmentValue() >>> shift == this.getSegmentValue() >>> shift && other.getUpperSegmentValue() >>> shift == this.getUpperSegmentValue() >>> shift;
    }

    public boolean prefixContains(IPAddressSegment other) {
        Integer prefLength = this.getSegmentPrefixLength();
        if (prefLength == null) {
            return this.equals(other);
        }
        return this.prefixContains(other, prefLength);
    }

    public boolean prefixContains(IPAddressSegment other, int prefixLength) {
        if (prefixLength < 0) {
            throw new PrefixLenException(prefixLength);
        }
        int shift = this.getBitCount() - prefixLength;
        if (shift <= 0) {
            return this.contains(other);
        }
        return other.getSegmentValue() >>> shift >= this.getSegmentValue() >>> shift && other.getUpperSegmentValue() >>> shift <= this.getUpperSegmentValue() >>> shift;
    }

    protected boolean containsSeg(AddressSegment other) {
        return other.getSegmentValue() >= this.getSegmentValue() && other.getUpperSegmentValue() <= this.getUpperSegmentValue();
    }

    @Override
    public boolean includesZero() {
        return this.getSegmentValue() == 0;
    }

    @Override
    public boolean includesMax() {
        return this.getUpperSegmentValue() == this.getMaxSegmentValue();
    }

    boolean containsPrefixBlock(int lowerVal, int upperVal, int divisionPrefixLen) {
        return this.isPrefixBlock(lowerVal, upperVal, divisionPrefixLen);
    }

    boolean containsSinglePrefixBlock(int lowerVal, int upperVal, int divisionPrefixLen) {
        return this.isSinglePrefixBlock(lowerVal, upperVal, divisionPrefixLen);
    }

    @Override
    protected String getDefaultSegmentWildcardString() {
        return Address.SEGMENT_WILDCARD_STR;
    }

    @Override
    public String toHexString(boolean with0xPrefix) {
        return this.toNormalizedString(with0xPrefix ? IPAddressSection.IPStringCache.hexPrefixedParams : IPAddressSection.IPStringCache.hexParams);
    }

    @Override
    public String toNormalizedString() {
        return this.toNormalizedString(IPAddressSection.IPStringCache.canonicalSegmentParams);
    }

    public String toNormalizedString(IPAddressSection.IPStringOptions options) {
        AddressDivisionGroupingBase.IPAddressStringParams<IPAddressStringDivisionSeries> params = IPAddressSection.toIPParams(options);
        StringBuilder builder = new StringBuilder(params.getDivisionStringLength(this));
        return params.appendDivision(builder, this).toString();
    }

    protected static int toUnsignedStringLength(int value, int radix) {
        return AddressDivision.toUnsignedStringLength(value, radix);
    }

    protected static StringBuilder toUnsignedString(int value, int radix, StringBuilder appendable) {
        return IPAddressSegment.toUnsignedStringCased(value, radix, 0, false, appendable);
    }

    void setStandardString(CharSequence addressStr, boolean isStandardString, int lowerStringStartIndex, int lowerStringEndIndex, int originalLowerValue) {
        if (this.cachedString == null && isStandardString && (long)originalLowerValue == this.getDivisionValue()) {
            this.cachedString = addressStr.subSequence(lowerStringStartIndex, lowerStringEndIndex).toString();
        }
    }

    void setWildcardString(CharSequence addressStr, boolean isStandardString, int lowerStringStartIndex, int lowerStringEndIndex, int lowerValue) {
        if (this.cachedWildcardString == null && isStandardString && (long)lowerValue == this.getDivisionValue() && (long)lowerValue == this.getUpperDivisionValue()) {
            this.cachedWildcardString = addressStr.subSequence(lowerStringStartIndex, lowerStringEndIndex).toString();
        }
    }

    void setStandardString(CharSequence addressStr, boolean isStandardString, boolean isStandardRangeString, int lowerStringStartIndex, int lowerStringEndIndex, int upperStringEndIndex, int rangeLower, int rangeUpper) {
        if (this.cachedString == null) {
            if (this.isSinglePrefixBlock()) {
                if (isStandardString && (long)rangeLower == this.getDivisionValue()) {
                    this.cachedString = addressStr.subSequence(lowerStringStartIndex, lowerStringEndIndex).toString();
                }
            } else if (this.isFullRange()) {
                this.cachedString = IPAddress.SEGMENT_WILDCARD_STR;
            } else if (isStandardRangeString && (long)rangeLower == this.getDivisionValue()) {
                long upper = this.getUpperDivisionValue();
                if (this.isPrefixed()) {
                    upper &= this.getDivisionNetworkMask(this.getDivisionPrefixLength());
                }
                if ((long)rangeUpper == upper) {
                    this.cachedString = addressStr.subSequence(lowerStringStartIndex, upperStringEndIndex).toString();
                }
            }
        }
    }

    void setWildcardString(CharSequence addressStr, boolean isStandardRangeString, int lowerStringStartIndex, int upperStringEndIndex, int rangeLower, int rangeUpper) {
        if (this.cachedWildcardString == null) {
            if (this.isFullRange()) {
                this.cachedWildcardString = IPAddress.SEGMENT_WILDCARD_STR;
            } else if (isStandardRangeString && (long)rangeLower == this.getDivisionValue() && (long)rangeUpper == this.getUpperDivisionValue()) {
                this.cachedWildcardString = addressStr.subSequence(lowerStringStartIndex, upperStringEndIndex).toString();
            }
        }
    }
}

