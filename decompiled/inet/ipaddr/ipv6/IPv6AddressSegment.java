/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv6;

import inet.ipaddr.Address;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.ipv6.IPv6AddressNetwork;
import inet.ipaddr.ipv6.IPv6AddressSection;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IPv6AddressSegment
extends IPAddressSegment
implements Iterable<IPv6AddressSegment> {
    private static final long serialVersionUID = 4L;
    public static final int MAX_CHARS = 4;
    public static final int BITS_PER_CHAR = 4;

    public IPv6AddressSegment(int value) throws AddressValueException {
        super(value);
        if (value > 65535) {
            throw new AddressValueException(value);
        }
    }

    public IPv6AddressSegment(int value, Integer segmentPrefixLength) throws AddressValueException {
        super(value, segmentPrefixLength);
        if (value > 65535) {
            throw new AddressValueException(value);
        }
        if (segmentPrefixLength != null && segmentPrefixLength > 128) {
            throw new PrefixLenException(segmentPrefixLength);
        }
    }

    public IPv6AddressSegment(int lower, int upper, Integer segmentPrefixLength) throws AddressValueException {
        super(lower, upper, segmentPrefixLength);
        if (this.getUpperSegmentValue() > 65535) {
            throw new AddressValueException(this.getUpperSegmentValue());
        }
        if (segmentPrefixLength != null && segmentPrefixLength > 128) {
            throw new PrefixLenException(segmentPrefixLength);
        }
    }

    @Override
    public long getMaxValue() {
        return 65535L;
    }

    @Override
    public boolean isIPv6() {
        return true;
    }

    @Override
    public IPAddress.IPVersion getIPVersion() {
        return IPAddress.IPVersion.IPV6;
    }

    @Override
    protected byte[] getBytesImpl(boolean low) {
        int val = low ? this.getSegmentValue() : this.getUpperSegmentValue();
        return new byte[]{(byte)(val >>> 8), (byte)(0xFF & val)};
    }

    @Override
    public IPv6AddressNetwork getNetwork() {
        return Address.defaultIpv6Network();
    }

    @Override
    protected int getSegmentNetworkMask(int bits) {
        return this.getNetwork().getSegmentNetworkMask(bits);
    }

    @Override
    protected int getSegmentHostMask(int bits) {
        return this.getNetwork().getSegmentHostMask(bits);
    }

    @Override
    public int getMaxSegmentValue() {
        return IPv6AddressSegment.getMaxSegmentValue(IPAddress.IPVersion.IPV6);
    }

    protected IPv6AddressSegment toPrefixNormalizedSeg() {
        return this.getSegmentCreator().createSegment(this.getSegmentValue(), this.getUpperSegmentValue(), IPv6AddressSection.cacheBits(this.getBitCount()));
    }

    protected IPv6AddressSegment toPrefixedSegment(Integer segmentPrefixLength) {
        if (this.isChangedByPrefix(segmentPrefixLength, this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets())) {
            return super.toPrefixedSegment(segmentPrefixLength, this.getSegmentCreator());
        }
        return this;
    }

    @Override
    public IPv6AddressSegment toNetworkSegment(Integer segmentPrefixLength) {
        return this.toNetworkSegment(segmentPrefixLength, true);
    }

    @Override
    public IPv6AddressSegment toNetworkSegment(Integer segmentPrefixLength, boolean withPrefixLength) {
        if (this.isNetworkChangedByPrefix(segmentPrefixLength, withPrefixLength)) {
            return super.toNetworkSegment(segmentPrefixLength, withPrefixLength, this.getSegmentCreator());
        }
        return this;
    }

    @Override
    public IPv6AddressSegment toHostSegment(Integer bits) {
        if (this.isHostChangedByPrefix(bits)) {
            return super.toHostSegment(bits, this.getSegmentCreator());
        }
        return this;
    }

    @Override
    public IPv6AddressSegment getLower() {
        return IPv6AddressSegment.getLowestOrHighest(this, this.getSegmentCreator(), true);
    }

    @Override
    public IPv6AddressSegment getUpper() {
        return IPv6AddressSegment.getLowestOrHighest(this, this.getSegmentCreator(), false);
    }

    @Override
    public IPv6AddressSegment reverseBits(boolean perByte) {
        if (this.isMultiple()) {
            if (perByte ? !IPv6AddressSegment.isReversibleRangePerByte(this) : !IPv6AddressSegment.isReversibleRange(this)) {
                throw new IncompatibleAddressException(this, "ipaddress.error.reverseRange");
            }
            if (this.isPrefixed()) {
                IPv6AddressNetwork.IPv6AddressCreator creator = this.getSegmentCreator();
                return (IPv6AddressSegment)creator.createSegment(this.getSegmentValue(), this.getUpperSegmentValue(), null);
            }
            return this;
        }
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getSegmentCreator();
        int oldVal = this.getSegmentValue();
        int newVal = IPv6AddressSegment.reverseBits((short)oldVal);
        if (perByte) {
            newVal = (newVal & 0xFF) << 8 | newVal >>> 8;
        }
        if (oldVal == newVal && !this.isPrefixed()) {
            return this;
        }
        return (IPv6AddressSegment)creator.createSegment(newVal);
    }

    @Override
    public IPv6AddressSegment reverseBytes() {
        int newValue;
        if (this.isMultiple()) {
            if (IPv6AddressSegment.isReversibleRange(this)) {
                if (this.isPrefixed()) {
                    IPv6AddressNetwork.IPv6AddressCreator creator = this.getSegmentCreator();
                    return (IPv6AddressSegment)creator.createSegment(this.getSegmentValue(), this.getUpperSegmentValue(), null);
                }
                return this;
            }
            throw new IncompatibleAddressException(this, "ipaddress.error.reverseRange");
        }
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getSegmentCreator();
        int value = this.getSegmentValue();
        if (value == (newValue = (value & 0xFF) << 8 | value >>> 8) && !this.isPrefixed()) {
            return this;
        }
        return (IPv6AddressSegment)creator.createSegment(newValue);
    }

    @Override
    public IPv6AddressSegment toZeroHost() {
        return IPv6AddressSegment.toZeroHost(this, this.getSegmentCreator());
    }

    @Override
    @Deprecated
    public IPv6AddressSegment removePrefixLength(boolean zeroed) {
        return IPv6AddressSegment.removePrefix(this, zeroed, this.getSegmentCreator());
    }

    @Override
    @Deprecated
    public IPv6AddressSegment removePrefixLength() {
        return this.removePrefixLength(true);
    }

    @Override
    public IPv6AddressSegment withoutPrefixLength() {
        return IPv6AddressSegment.removePrefix(this, false, this.getSegmentCreator());
    }

    protected IPv6AddressNetwork.IPv6AddressCreator getSegmentCreator() {
        return this.getNetwork().getAddressCreator();
    }

    public Iterable<IPv6AddressSegment> getIterable() {
        return this;
    }

    Iterator<IPv6AddressSegment> iterator(boolean withPrefix) {
        IPv6AddressSegment original = !withPrefix && this.isPrefixed() && !this.isMultiple() ? this.withoutPrefixLength() : this;
        return IPv6AddressSegment.iterator(original, this.getSegmentCreator(), withPrefix ? this.getSegmentPrefixLength() : null, false, false);
    }

    @Override
    public Iterator<IPv6AddressSegment> iterator() {
        return this.iterator(!this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets());
    }

    @Override
    public AddressComponentSpliterator<IPv6AddressSegment> spliterator() {
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getSegmentCreator();
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        Integer segPrefLength = isAllSubnets ? null : this.getSegmentPrefixLength();
        int bitCount = this.getBitCount();
        return IPv6AddressSegment.createSegmentSpliterator(this, this.getSegmentValue(), this.getUpperSegmentValue(), this::iterator, (isLowest, isHighest, value, upperValue) -> IPv6AddressSegment.iterator(null, value, upperValue, bitCount, creator, segPrefLength, false, false), (value, upperValue) -> creator.createSegment(value, upperValue, segPrefLength));
    }

    public Stream<IPv6AddressSegment> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Iterator<IPv6AddressSegment> prefixBlockIterator() {
        return IPv6AddressSegment.iterator(this, this.getSegmentCreator(), this.getSegmentPrefixLength(), true, true);
    }

    public AddressComponentSpliterator<IPv6AddressSegment> prefixBlockSpliterator() {
        Integer segPrefLength = this.getSegmentPrefixLength();
        if (segPrefLength == null) {
            return this.spliterator();
        }
        return this.prefixBlockSpliterator(segPrefLength);
    }

    public Stream<IPv6AddressSegment> prefixBlockStream() {
        return StreamSupport.stream(this.prefixBlockSpliterator(), false);
    }

    Iterator<IPv6AddressSegment> identityIterator() {
        return IPv6AddressSegment.identityIterator(this);
    }

    public Iterator<IPv6AddressSegment> prefixBlockIterator(int prefixLength) {
        if (prefixLength < 0) {
            throw new PrefixLenException(prefixLength);
        }
        return IPv6AddressSegment.iterator(this, this.getSegmentCreator(), IPv6AddressSection.cacheBits(prefixLength), true, true);
    }

    public AddressComponentSpliterator<IPv6AddressSegment> prefixBlockSpliterator(int segPrefLength) {
        return IPv6AddressSegment.prefixBlockSpliterator(this, segPrefLength, this.getSegmentCreator(), this::prefixBlockIterator);
    }

    public Stream<IPv6AddressSegment> prefixBlockStream(int segPrefLength) {
        return StreamSupport.stream(this.prefixBlockSpliterator(segPrefLength), false);
    }

    public Iterator<IPv6AddressSegment> prefixIterator() {
        return IPv6AddressSegment.iterator(this, this.getSegmentCreator(), this.getSegmentPrefixLength(), true, false);
    }

    public AddressComponentSpliterator<IPv6AddressSegment> prefixSpliterator() {
        Integer segPrefLength = this.getSegmentPrefixLength();
        if (segPrefLength == null) {
            return this.spliterator();
        }
        return IPv6AddressSegment.prefixSpliterator(this, segPrefLength, this.getSegmentCreator(), this::prefixIterator);
    }

    public Stream<IPv6AddressSegment> prefixStream() {
        return StreamSupport.stream(this.prefixSpliterator(), false);
    }

    @Override
    public int getBitCount() {
        return 16;
    }

    @Override
    public int getByteCount() {
        return 2;
    }

    @Override
    public int getDefaultTextualRadix() {
        return 16;
    }

    @Override
    public int getMaxDigitCount() {
        return 4;
    }

    public <S extends AddressSegment> void getSplitSegments(S[] segs, int index, AddressNetwork.AddressSegmentCreator<S> creator) {
        if (!this.isMultiple()) {
            int bitSizeSplit = 8;
            Integer myPrefix = this.getSegmentPrefixLength();
            Integer highPrefixBits = IPv6AddressSegment.getSplitSegmentPrefix(bitSizeSplit, myPrefix, 0);
            Integer lowPrefixBits = IPv6AddressSegment.getSplitSegmentPrefix(bitSizeSplit, myPrefix, 1);
            if (index >= 0 && index < segs.length) {
                segs[index] = creator.createSegment(this.highByte(), highPrefixBits);
            }
            if (++index >= 0 && index < segs.length) {
                segs[index] = creator.createSegment(this.lowByte(), lowPrefixBits);
            }
        } else {
            this.getSplitSegmentsMultiple((AddressSegment[])segs, index, creator);
        }
    }

    private <S extends AddressSegment> void getSplitSegmentsMultiple(S[] segs, int index, AddressNetwork.AddressSegmentCreator<S> creator) {
        boolean highIsMult;
        Integer myPrefix = this.getSegmentPrefixLength();
        int bitSizeSplit = 8;
        int val = this.getSegmentValue();
        int upperVal = this.getUpperSegmentValue();
        int highLower = IPv6AddressSegment.highByte(val);
        int highUpper = IPv6AddressSegment.highByte(upperVal);
        int lowLower = IPv6AddressSegment.lowByte(val);
        int lowUpper = IPv6AddressSegment.lowByte(upperVal);
        boolean bl = highIsMult = highLower != highUpper;
        if (highIsMult && (lowLower != 0 || lowUpper != ~(-1 << bitSizeSplit))) {
            throw new IncompatibleAddressException(this, "ipaddress.error.splitSeg");
        }
        if (index >= 0 && index < segs.length) {
            Integer highPrefixBits = IPv6AddressSegment.getSplitSegmentPrefix(bitSizeSplit, myPrefix, 0);
            segs[index] = highIsMult ? creator.createSegment(highLower, highUpper, highPrefixBits) : creator.createSegment(highLower, highPrefixBits);
        }
        if (++index >= 0 && index < segs.length) {
            Integer lowPrefixBits = IPv6AddressSegment.getSplitSegmentPrefix(bitSizeSplit, myPrefix, 1);
            segs[index] = lowLower == lowUpper ? creator.createSegment(lowLower, lowPrefixBits) : creator.createSegment(lowLower, lowUpper, lowPrefixBits);
        }
    }

    @Override
    public boolean prefixContains(IPAddressSegment other, int segmentPrefixLength) {
        return this == other || super.prefixContains(other, segmentPrefixLength) && other instanceof IPv6AddressSegment;
    }

    @Override
    public boolean prefixEquals(AddressSegment other, int segmentPrefixLength) {
        return this == other || super.prefixEquals(other, segmentPrefixLength) && other instanceof IPv6AddressSegment;
    }

    @Override
    public boolean contains(AddressSegment other) {
        return this == other || this.containsSeg(other) && other instanceof IPv6AddressSegment;
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof IPv6AddressSegment && ((IPv6AddressSegment)other).isSameValues(this);
    }

    @Override
    protected boolean isSameValues(AddressDivisionBase other) {
        return other instanceof IPv6AddressSegment && this.isSameValues((AddressSegment)((Object)other));
    }

    @Override
    protected int getRangeDigitCountImpl() {
        int bitsPerCharacter;
        int bitCount;
        int prefix = this.getMinPrefixLengthForBlock();
        if (prefix < (bitCount = this.getBitCount()) && this.containsSinglePrefixBlock(prefix) && prefix % (bitsPerCharacter = 4) == 0) {
            return (bitCount - prefix) / bitsPerCharacter;
        }
        return 0;
    }

    protected static int toUnsignedStringLength(int value, int radix) {
        return IPAddressSegment.toUnsignedStringLength(value, radix);
    }

    protected static StringBuilder toUnsignedString(int value, int radix, StringBuilder appendable) {
        return IPAddressSegment.toUnsignedString(value, radix, appendable);
    }
}

