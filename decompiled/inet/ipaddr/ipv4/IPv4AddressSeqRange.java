/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv4;

import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.NetworkMismatchException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.format.validate.ParsedAddressGrouping;
import inet.ipaddr.ipv4.IPv4Address;
import inet.ipaddr.ipv4.IPv4AddressNetwork;
import inet.ipaddr.ipv4.IPv4AddressSection;
import inet.ipaddr.ipv4.IPv4AddressSegment;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IPv4AddressSeqRange
extends IPAddressSeqRange
implements Iterable<IPv4Address> {
    private static final long serialVersionUID = 1L;
    private static final IPv4AddressSeqRange[] EMPTY = new IPv4AddressSeqRange[0];

    IPv4AddressSeqRange(IPv4Address first, IPv4Address second, boolean preSet) {
        super(first, second, preSet);
    }

    public IPv4AddressSeqRange(IPv4Address first, IPv4Address second) {
        super(first, second, IPv4Address::getLower, IPv4Address::getUpper, IPv4Address::withoutPrefixLength);
        if (!first.getNetwork().isCompatible(second.getNetwork())) {
            throw new NetworkMismatchException(first, second);
        }
    }

    private IPv4AddressSeqRange(IPAddress first, IPAddress second) {
        super(first, second);
    }

    @Override
    public IPv4Address getLower() {
        return (IPv4Address)super.getLower();
    }

    @Override
    public IPv4Address getUpper() {
        return (IPv4Address)super.getUpper();
    }

    private IPv4AddressNetwork.IPv4AddressCreator getAddressCreator() {
        return this.getLower().getNetwork().getAddressCreator();
    }

    public long getIPv4Count() {
        return this.getUpper().longValue() - this.getLower().longValue() + 1L;
    }

    public long getIPv4PrefixCount(int prefixLength) {
        if (prefixLength < 0) {
            throw new PrefixLenException((AddressItem)this, prefixLength);
        }
        int bitCount = this.getBitCount();
        if (bitCount <= prefixLength) {
            return this.getIPv4Count();
        }
        int shiftAdjustment = bitCount - prefixLength;
        long upperAdjusted = this.getUpper().longValue() >>> shiftAdjustment;
        long lowerAdjusted = this.getLower().longValue() >>> shiftAdjustment;
        return upperAdjusted - lowerAdjusted + 1L;
    }

    @Override
    protected BigInteger getCountImpl() {
        return BigInteger.valueOf(this.getIPv4Count());
    }

    @Override
    public BigInteger getPrefixCount(int prefixLength) {
        return BigInteger.valueOf(this.getIPv4PrefixCount(prefixLength));
    }

    public Iterable<IPv4Address> getIterable() {
        return this;
    }

    @Override
    public Iterator<IPv4Address> iterator() {
        IPv4Address lower = this.getLower();
        IPv4Address upper = this.getUpper();
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getAddressCreator();
        if (!this.isMultiple()) {
            return IPv4AddressSeqRange.iterator(lower, creator);
        }
        int divCount = lower.getSegmentCount();
        return IPv4AddressSeqRange.iterator(lower, upper, creator, IPv4Address::getSegment, (seg, segIndex) -> seg.iterator(), (addr1, addr2, index) -> addr1.getSegment(index).getSegmentValue() == addr2.getSegment(index).getSegmentValue(), divCount - 1, divCount, null);
    }

    public AddressComponentRangeSpliterator<IPv4AddressSeqRange, IPv4Address> spliterator() {
        int segmentCount = this.getLower().getSegmentCount();
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getAddressCreator();
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return IPv4AddressSeqRange.createSpliterator(this, sink2 -> {
            IPv4AddressSeqRange range = (IPv4AddressSeqRange)sink2.getAddressItem();
            return IPv4AddressSeqRange.split((IPAddressSeqRange.IPAddressSeqRangeSplitterSink)sink2, (T segsLower, U segsUpper) -> new IPv4AddressSeqRange(creator.createAddressInternal((IPv4AddressSegment[])segsLower), creator.createAddressInternal((IPv4AddressSegment[])segsUpper)), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])range.getLower().getSection().getSegmentsInternal(), (AddressSegment[])range.getUpper().getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, null);
        }, (lowest, highest, range) -> range.iterator(), IPv4AddressSeqRange::getIPv4Count);
    }

    public Stream<IPv4Address> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Iterator<IPv4Address> prefixBlockIterator(int prefLength) {
        if (prefLength < 0) {
            throw new PrefixLenException(prefLength);
        }
        IPv4Address lower = this.getLower();
        IPv4Address upper = this.getUpper();
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getAddressCreator();
        int bitsPerSegment = lower.getBitsPerSegment();
        int bytesPerSegment = lower.getBytesPerSegment();
        int segCount = lower.getSegmentCount();
        Integer[] prefLengths = new Integer[segCount];
        int[] shifts = new int[segCount];
        int networkSegIndex = 0;
        if (prefLength > 0) {
            networkSegIndex = IPv4AddressSeqRange.getNetworkSegmentIndex(prefLength, bytesPerSegment, bitsPerSegment);
        }
        for (int i = networkSegIndex; i < segCount; ++i) {
            Integer segPrefLength;
            prefLengths[i] = segPrefLength = ParsedAddressGrouping.getPrefixedSegmentPrefixLength(bitsPerSegment, prefLength, i);
            shifts[i] = bitsPerSegment - segPrefLength;
        }
        int hostSegIndex = IPv4AddressSeqRange.getHostSegmentIndex(prefLength, bytesPerSegment, bitsPerSegment);
        return IPv4AddressSeqRange.iterator(lower, upper, creator, IPv4Address::getSegment, (seg, segIndex) -> seg.iterator(), (addr1, addr2, index) -> {
            Integer segPrefLength = prefLengths[index];
            if (segPrefLength == null) {
                return addr1.getSegment(index).getSegmentValue() == addr2.getSegment(index).getSegmentValue();
            }
            int shift = shifts[index];
            return addr1.getSegment(index).getSegmentValue() >>> shift == addr2.getSegment(index).getSegmentValue() >>> shift;
        }, networkSegIndex, hostSegIndex, (seg, index) -> {
            Integer segPrefLength = prefLengths[index];
            if (segPrefLength == null) {
                return seg.iterator();
            }
            return seg.prefixBlockIterator(segPrefLength);
        });
    }

    public AddressComponentRangeSpliterator<IPv4AddressSeqRange, IPv4Address> prefixBlockSpliterator(int prefLength) {
        if (prefLength < 0) {
            throw new PrefixLenException(prefLength);
        }
        IPv4Address lower = this.getLower();
        int bitsPerSegment = lower.getBitsPerSegment();
        int bytesPerSegment = lower.getBytesPerSegment();
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getAddressCreator();
        Integer prefixLength = IPv4AddressSection.cacheBits(prefLength);
        int networkSegIndex = IPv4AddressSeqRange.getNetworkSegmentIndex(prefixLength, bytesPerSegment, bitsPerSegment);
        int hostSegIndex = IPv4AddressSeqRange.getHostSegmentIndex(prefixLength, bytesPerSegment, bitsPerSegment);
        return IPv4AddressSeqRange.createSpliterator(this, sink2 -> {
            IPv4AddressSeqRange range = (IPv4AddressSeqRange)sink2.getAddressItem();
            return IPv4AddressSeqRange.split((IPAddressSeqRange.IPAddressSeqRangeSplitterSink)sink2, (T segsLower, U segsUpper) -> new IPv4AddressSeqRange(creator.createAddressInternal((IPv4AddressSegment[])segsLower), creator.createAddressInternal((IPv4AddressSegment[])segsUpper)), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])range.getLower().getSection().getSegmentsInternal(), (AddressSegment[])range.getUpper().getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)prefixLength);
        }, (isLowest, isHighest, range) -> range.prefixBlockIterator(prefLength), range -> range.getIPv4PrefixCount(prefLength));
    }

    public Stream<IPv4Address> prefixBlockStream(int prefLength) {
        return StreamSupport.stream(this.prefixBlockSpliterator(prefLength), false);
    }

    public Iterator<IPv4AddressSeqRange> prefixIterator(int prefixLength) {
        return super.prefixIterator(prefixLength);
    }

    public Stream<IPv4AddressSeqRange> prefixStream(int prefLength) {
        return StreamSupport.stream(this.prefixSpliterator(prefLength), false);
    }

    public AddressComponentSpliterator<IPv4AddressSeqRange> prefixSpliterator(int prefLength) {
        if (prefLength < 0) {
            throw new PrefixLenException(prefLength);
        }
        IPv4Address lower = this.getLower();
        int bitsPerSegment = lower.getBitsPerSegment();
        int bytesPerSegment = lower.getBytesPerSegment();
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getAddressCreator();
        Integer prefixLength = IPv4AddressSection.cacheBits(prefLength);
        int networkSegIndex = IPv4AddressSeqRange.getNetworkSegmentIndex(prefixLength, bytesPerSegment, bitsPerSegment);
        int hostSegIndex = IPv4AddressSeqRange.getHostSegmentIndex(prefixLength, bytesPerSegment, bitsPerSegment);
        return IPv4AddressSeqRange.createPrefixSpliterator(this, sink2 -> {
            IPv4AddressSeqRange range = (IPv4AddressSeqRange)sink2.getAddressItem();
            return IPv4AddressSeqRange.split((IPAddressSeqRange.IPAddressSeqRangeSplitterSink)sink2, (T segsLower, U segsUpper) -> new IPv4AddressSeqRange(creator.createAddressInternal((IPv4AddressSegment[])segsLower), creator.createAddressInternal((IPv4AddressSegment[])segsUpper)), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])range.getLower().getSection().getSegmentsInternal(), (AddressSegment[])range.getUpper().getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)prefixLength);
        }, (isLowest, isHighest, range) -> isLowest || isHighest ? range.prefixIterator(prefLength) : IPv4AddressSeqRange.rangedIterator(range.prefixBlockIterator(prefLength)), range -> range.getIPv4PrefixCount(prefLength));
    }

    @Override
    public IPv4Address coverWithPrefixBlock() {
        return this.getLower().coverWithPrefixBlock(this.getUpper());
    }

    public IPv4Address[] spanWithPrefixBlocks() {
        return this.getLower().spanWithPrefixBlocks(this.getUpper());
    }

    public IPv4Address[] spanWithSequentialBlocks() {
        return this.getLower().spanWithSequentialBlocks(this.getUpper());
    }

    @Override
    public int getMinPrefixLengthForBlock() {
        int upperOnes;
        int result = this.getBitCount();
        int lowerZeros = Integer.numberOfTrailingZeros(this.getLower().intValue());
        if (lowerZeros != 0 && (upperOnes = Integer.numberOfTrailingZeros(~this.getUpper().intValue())) != 0) {
            int prefixedBitCount = Math.min(lowerZeros, upperOnes);
            result -= prefixedBitCount;
        }
        return result;
    }

    @Override
    public Integer getPrefixLengthForSingleBlock() {
        int shift;
        int divPrefix = this.getMinPrefixLengthForBlock();
        int lowerValue = this.getLower().intValue();
        int upperValue = this.getUpper().intValue();
        int bitCount = this.getBitCount();
        if (divPrefix == bitCount ? lowerValue == upperValue : lowerValue >>> (shift = bitCount - divPrefix) == upperValue >>> shift) {
            return IPv4AddressSection.cacheBits(divPrefix);
        }
        return null;
    }

    @Override
    protected IPv4AddressSeqRange create(IPAddress lower, IPAddress upper) {
        return new IPv4AddressSeqRange(lower, upper);
    }

    protected IPv4AddressSeqRange[] createPair(IPAddress lower1, IPAddress upper1, IPAddress lower2, IPAddress upper2) {
        return new IPv4AddressSeqRange[]{this.create(lower1, upper1), this.create(lower2, upper2)};
    }

    protected IPv4AddressSeqRange[] createSingle(IPAddress lower, IPAddress upper) {
        return new IPv4AddressSeqRange[]{this.create(lower, upper)};
    }

    protected IPv4AddressSeqRange[] createSingle() {
        return new IPv4AddressSeqRange[]{this};
    }

    protected IPv4AddressSeqRange[] createEmpty() {
        return EMPTY;
    }

    public String toIPv4String(Function<IPv4Address, String> lowerStringer, String separator, Function<IPv4Address, String> upperStringer) {
        return lowerStringer.apply(this.getLower()) + separator + upperStringer.apply(this.getUpper());
    }

    @Override
    public IPv4AddressSeqRange intersect(IPAddressSeqRange other) {
        return (IPv4AddressSeqRange)super.intersect(other);
    }

    @Override
    public IPv4AddressSeqRange join(IPAddressSeqRange other) {
        return (IPv4AddressSeqRange)super.join(other);
    }

    public IPv4AddressSeqRange[] subtract(IPAddressSeqRange other) {
        return (IPv4AddressSeqRange[])super.subtract(other);
    }

    @Override
    public IPv4AddressSeqRange toSequentialRange() {
        return this;
    }
}

