/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv4;

import inet.ipaddr.Address;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.ipv4.IPv4AddressNetwork;
import inet.ipaddr.ipv4.IPv4AddressSection;
import inet.ipaddr.ipv6.IPv6AddressNetwork;
import inet.ipaddr.ipv6.IPv6AddressSegment;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IPv4AddressSegment
extends IPAddressSegment
implements Iterable<IPv4AddressSegment> {
    private static final long serialVersionUID = 4L;
    public static final int MAX_CHARS = 3;

    public IPv4AddressSegment(int value) throws AddressValueException {
        super(value);
        if (value > 255) {
            throw new AddressValueException(value);
        }
    }

    public IPv4AddressSegment(int value, Integer segmentPrefixLength) throws AddressValueException {
        super(value, segmentPrefixLength);
        if (value > 255) {
            throw new AddressValueException(value);
        }
        if (segmentPrefixLength != null && segmentPrefixLength > 32) {
            throw new PrefixLenException(segmentPrefixLength);
        }
    }

    public IPv4AddressSegment(int lower, int upper, Integer segmentPrefixLength) throws AddressValueException {
        super(lower, upper, segmentPrefixLength);
        if (this.getUpperSegmentValue() > 255) {
            throw new AddressValueException(this.getUpperSegmentValue());
        }
        if (segmentPrefixLength != null && segmentPrefixLength > 32) {
            throw new PrefixLenException(segmentPrefixLength);
        }
    }

    @Override
    public long getMaxValue() {
        return 255L;
    }

    @Override
    public boolean isIPv4() {
        return true;
    }

    @Override
    public IPAddress.IPVersion getIPVersion() {
        return IPAddress.IPVersion.IPV4;
    }

    @Override
    protected byte[] getBytesImpl(boolean low) {
        return new byte[]{(byte)(low ? this.getSegmentValue() : this.getUpperSegmentValue())};
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
        return IPv4AddressSegment.getMaxSegmentValue(IPAddress.IPVersion.IPV4);
    }

    protected IPv4AddressSegment toPrefixNormalizedSeg() {
        return this.getSegmentCreator().createSegment(this.getSegmentValue(), this.getUpperSegmentValue(), IPv4AddressSection.cacheBits(this.getBitCount()));
    }

    protected IPv4AddressSegment toPrefixedSegment(Integer segmentPrefixLength) {
        if (this.isChangedByPrefix(segmentPrefixLength, this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets())) {
            return super.toPrefixedSegment(segmentPrefixLength, this.getSegmentCreator());
        }
        return this;
    }

    @Override
    public IPv4AddressSegment toNetworkSegment(Integer segmentPrefixLength) {
        return this.toNetworkSegment(segmentPrefixLength, true);
    }

    @Override
    public IPv4AddressSegment toNetworkSegment(Integer segmentPrefixLength, boolean withPrefixLength) {
        if (this.isNetworkChangedByPrefix(segmentPrefixLength, withPrefixLength)) {
            return super.toNetworkSegment(segmentPrefixLength, withPrefixLength, this.getSegmentCreator());
        }
        return this;
    }

    @Override
    public IPv4AddressSegment toHostSegment(Integer bits) {
        if (this.isHostChangedByPrefix(bits)) {
            return super.toHostSegment(bits, this.getSegmentCreator());
        }
        return this;
    }

    @Override
    public IPv4AddressSegment getLower() {
        return IPv4AddressSegment.getLowestOrHighest(this, this.getSegmentCreator(), true);
    }

    @Override
    public IPv4AddressSegment getUpper() {
        return IPv4AddressSegment.getLowestOrHighest(this, this.getSegmentCreator(), false);
    }

    @Override
    public IPv4AddressNetwork getNetwork() {
        return Address.defaultIpv4Network();
    }

    public IPv4AddressNetwork.IPv4AddressCreator getSegmentCreator() {
        return this.getNetwork().getAddressCreator();
    }

    public Iterable<IPv4AddressSegment> getIterable() {
        return this;
    }

    Iterator<IPv4AddressSegment> iterator(boolean withPrefix) {
        IPv4AddressSegment original = !withPrefix && this.isPrefixed() && !this.isMultiple() ? this.withoutPrefixLength() : this;
        return IPv4AddressSegment.iterator(original, this.getSegmentCreator(), withPrefix ? this.getSegmentPrefixLength() : null, false, false);
    }

    @Override
    public Iterator<IPv4AddressSegment> iterator() {
        return this.iterator(!this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets());
    }

    @Override
    public AddressComponentSpliterator<IPv4AddressSegment> spliterator() {
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getSegmentCreator();
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        Integer segPrefLength = isAllSubnets ? null : this.getSegmentPrefixLength();
        int bitCount = this.getBitCount();
        return IPv4AddressSegment.createSegmentSpliterator(this, this.getSegmentValue(), this.getUpperSegmentValue(), this::iterator, (isLowest, isHighest, value, upperValue) -> IPv4AddressSegment.iterator(null, value, upperValue, bitCount, creator, segPrefLength, false, false), (value, upperValue) -> creator.createSegment(value, upperValue, segPrefLength));
    }

    public Stream<IPv4AddressSegment> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Iterator<IPv4AddressSegment> prefixBlockIterator() {
        return IPv4AddressSegment.iterator(this, this.getSegmentCreator(), this.getSegmentPrefixLength(), true, true);
    }

    public AddressComponentSpliterator<IPv4AddressSegment> prefixBlockSpliterator() {
        Integer segPrefLength = this.getSegmentPrefixLength();
        if (segPrefLength == null) {
            return this.spliterator();
        }
        return this.prefixBlockSpliterator(segPrefLength);
    }

    public Stream<IPv4AddressSegment> prefixBlockStream() {
        return StreamSupport.stream(this.prefixBlockSpliterator(), false);
    }

    public Iterator<IPv4AddressSegment> prefixIterator() {
        return IPv4AddressSegment.iterator(this, this.getSegmentCreator(), this.getSegmentPrefixLength(), true, false);
    }

    public AddressComponentSpliterator<IPv4AddressSegment> prefixSpliterator() {
        Integer segPrefLength = this.getSegmentPrefixLength();
        if (segPrefLength == null) {
            return this.spliterator();
        }
        return IPv4AddressSegment.prefixSpliterator(this, segPrefLength, this.getSegmentCreator(), this::prefixIterator);
    }

    public Stream<IPv4AddressSegment> prefixStream() {
        return StreamSupport.stream(this.prefixSpliterator(), false);
    }

    public Iterator<IPv4AddressSegment> prefixBlockIterator(int prefixLength) {
        if (prefixLength < 0) {
            throw new PrefixLenException(prefixLength);
        }
        return IPv4AddressSegment.iterator(this, this.getSegmentCreator(), IPv4AddressSection.cacheBits(prefixLength), true, true);
    }

    public AddressComponentSpliterator<IPv4AddressSegment> prefixBlockSpliterator(int segPrefLength) {
        return IPv4AddressSegment.prefixBlockSpliterator(this, segPrefLength, this.getSegmentCreator(), this::prefixBlockIterator);
    }

    public Stream<IPv4AddressSegment> prefixBlockStream(int segPrefLength) {
        return StreamSupport.stream(this.prefixBlockSpliterator(segPrefLength), false);
    }

    Iterator<IPv4AddressSegment> identityIterator() {
        return IPv4AddressSegment.identityIterator(this);
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
    public int getDefaultTextualRadix() {
        return 10;
    }

    @Override
    public int getMaxDigitCount() {
        return 3;
    }

    @Override
    public IPv4AddressSegment reverseBits(boolean perByte) {
        return this.reverseBits();
    }

    public IPv4AddressSegment reverseBits() {
        int newVal;
        if (this.isMultiple()) {
            if (IPv4AddressSegment.isReversibleRange(this)) {
                if (this.isPrefixed()) {
                    IPv4AddressNetwork.IPv4AddressCreator creator = this.getSegmentCreator();
                    return (IPv4AddressSegment)creator.createSegment(this.getSegmentValue(), this.getUpperSegmentValue(), null);
                }
                return this;
            }
            throw new IncompatibleAddressException(this, "ipaddress.error.reverseRange");
        }
        int oldVal = this.getSegmentValue();
        if (oldVal == (newVal = IPv4AddressSegment.reverseBits((byte)oldVal)) && !this.isPrefixed()) {
            return this;
        }
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getSegmentCreator();
        return (IPv4AddressSegment)creator.createSegment(newVal);
    }

    @Override
    public IPv4AddressSegment reverseBytes() {
        return IPv4AddressSegment.removePrefix(this, false, this.getSegmentCreator());
    }

    @Override
    public IPv4AddressSegment toZeroHost() {
        return IPv4AddressSegment.toZeroHost(this, this.getSegmentCreator());
    }

    @Override
    @Deprecated
    public IPv4AddressSegment removePrefixLength(boolean zeroed) {
        return IPv4AddressSegment.removePrefix(this, zeroed, this.getSegmentCreator());
    }

    @Override
    @Deprecated
    public IPv4AddressSegment removePrefixLength() {
        return this.removePrefixLength(true);
    }

    @Override
    public IPv4AddressSegment withoutPrefixLength() {
        return IPv4AddressSegment.removePrefix(this, false, this.getSegmentCreator());
    }

    @Override
    public boolean prefixContains(IPAddressSegment other, int segmentPrefixLength) {
        return this == other || super.prefixContains(other, segmentPrefixLength) && other instanceof IPv4AddressSegment;
    }

    @Override
    public boolean prefixEquals(AddressSegment other, int segmentPrefixLength) {
        return this == other || super.prefixEquals(other, segmentPrefixLength) && other instanceof IPv4AddressSegment;
    }

    @Override
    public boolean contains(AddressSegment other) {
        return this == other || this.containsSeg(other) && other instanceof IPv4AddressSegment;
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof IPv4AddressSegment && ((IPv4AddressSegment)other).isSameValues(this);
    }

    @Override
    protected boolean isSameValues(AddressDivisionBase other) {
        return other instanceof IPv4AddressSegment && this.isSameValues((AddressSegment)((Object)other));
    }

    public IPv6AddressSegment join(IPv6AddressNetwork.IPv6AddressCreator creator, IPv4AddressSegment low) throws IncompatibleAddressException {
        int shift = 8;
        Integer prefix = IPv4AddressSegment.getJoinedSegmentPrefixLength(shift, this.getSegmentPrefixLength(), low.getSegmentPrefixLength());
        if (this.isMultiple() && !low.isFullRange()) {
            throw new IncompatibleAddressException((AddressItem)this, low, "ipaddress.error.invalidMixedRange");
        }
        return creator.createSegment(this.getSegmentValue() << shift | low.getSegmentValue(), this.getUpperSegmentValue() << shift | low.getUpperSegmentValue(), prefix);
    }

    static Integer getJoinedSegmentPrefixLength(int bitsPerSegment, Integer highBits, Integer lowBits) {
        if (lowBits == null) {
            return null;
        }
        if (lowBits == 0) {
            return highBits;
        }
        return IPv4AddressSection.cacheBits(lowBits + bitsPerSegment);
    }
}

