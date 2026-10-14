/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv6;

import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.NetworkMismatchException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.format.validate.ParsedAddressGrouping;
import inet.ipaddr.ipv6.IPv6Address;
import inet.ipaddr.ipv6.IPv6AddressNetwork;
import inet.ipaddr.ipv6.IPv6AddressSection;
import inet.ipaddr.ipv6.IPv6AddressSegment;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IPv6AddressSeqRange
extends IPAddressSeqRange
implements Iterable<IPv6Address> {
    private static final long serialVersionUID = 1L;
    private static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);
    private static final IPv6AddressSeqRange[] EMPTY = new IPv6AddressSeqRange[0];

    IPv6AddressSeqRange(IPv6Address first, IPv6Address second, boolean preSet) {
        super(first, second, preSet);
    }

    public IPv6AddressSeqRange(IPv6Address first, IPv6Address second) {
        super(first, second, IPv6Address::getLower, IPv6Address::getUpper, a -> a.withoutPrefixLength().removeZone());
        if (!first.getNetwork().isCompatible(second.getNetwork())) {
            throw new NetworkMismatchException(first, second);
        }
    }

    private IPv6AddressSeqRange(IPAddress first, IPAddress second) {
        super(first, second);
    }

    @Override
    public IPv6Address getLower() {
        return (IPv6Address)super.getLower();
    }

    @Override
    public IPv6Address getUpper() {
        return (IPv6Address)super.getUpper();
    }

    private IPv6AddressNetwork.IPv6AddressCreator getAddressCreator() {
        return this.getLower().getDefaultCreator();
    }

    public Iterable<IPv6Address> getIterable() {
        return this;
    }

    @Override
    public Iterator<IPv6Address> iterator() {
        IPv6Address lower = this.getLower();
        IPv6Address upper = this.getUpper();
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getAddressCreator();
        if (!this.isMultiple()) {
            return IPv6AddressSeqRange.iterator(lower, creator);
        }
        int divCount = lower.getSegmentCount();
        return IPv6AddressSeqRange.iterator(lower, upper, creator, IPv6Address::getSegment, (seg, segIndex) -> seg.iterator(), (addr1, addr2, index) -> addr1.getSegment(index).getSegmentValue() == addr2.getSegment(index).getSegmentValue(), divCount - 1, divCount, null);
    }

    public AddressComponentRangeSpliterator<IPv6AddressSeqRange, IPv6Address> spliterator() {
        int segmentCount = this.getLower().getSegmentCount();
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getAddressCreator();
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return IPv6AddressSeqRange.createSpliterator(this, spliterator -> {
            IPv6AddressSeqRange range = (IPv6AddressSeqRange)spliterator.getAddressItem();
            return IPv6AddressSeqRange.split((IPAddressSeqRange.IPAddressSeqRangeSplitterSink)spliterator, (T segsLower, U segsUpper) -> new IPv6AddressSeqRange(creator.createAddressInternal((IPv6AddressSegment[])segsLower), creator.createAddressInternal((IPv6AddressSegment[])segsUpper)), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])range.getLower().getSection().getSegmentsInternal(), (AddressSegment[])range.getUpper().getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, null);
        }, (lowest, highest, range) -> range.iterator(), IPAddressSeqRange::getCount, range -> range.getCount().compareTo(LONG_MAX) <= 0, range -> range.getCount().longValue());
    }

    public Stream<IPv6Address> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @Override
    public IPv6Address coverWithPrefixBlock() {
        return this.getLower().coverWithPrefixBlock(this.getUpper());
    }

    public IPv6Address[] spanWithPrefixBlocks() {
        return this.getLower().spanWithPrefixBlocks(this.getUpper());
    }

    public IPv6Address[] spanWithSequentialBlocks() {
        return this.getLower().spanWithSequentialBlocks(this.getUpper());
    }

    @Override
    protected IPv6AddressSeqRange create(IPAddress lower, IPAddress upper) {
        return new IPv6AddressSeqRange(lower, upper);
    }

    protected IPv6AddressSeqRange[] createPair(IPAddress lower1, IPAddress upper1, IPAddress lower2, IPAddress upper2) {
        return new IPv6AddressSeqRange[]{this.create(lower1, upper1), this.create(lower2, upper2)};
    }

    protected IPv6AddressSeqRange[] createSingle(IPAddress lower, IPAddress upper) {
        return new IPv6AddressSeqRange[]{this.create(lower, upper)};
    }

    protected IPv6AddressSeqRange[] createSingle() {
        return new IPv6AddressSeqRange[]{this};
    }

    protected IPv6AddressSeqRange[] createEmpty() {
        return EMPTY;
    }

    public Iterator<IPv6Address> prefixBlockIterator(int prefLength) {
        if (prefLength < 0) {
            throw new PrefixLenException(prefLength);
        }
        IPv6Address lower = this.getLower();
        IPv6Address upper = this.getUpper();
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getAddressCreator();
        int bitsPerSegment = lower.getBitsPerSegment();
        int bytesPerSegment = lower.getBytesPerSegment();
        int segCount = lower.getSegmentCount();
        Integer[] prefLengths = new Integer[segCount];
        int[] shifts = new int[segCount];
        int networkSegIndex = 0;
        if (prefLength > 0) {
            networkSegIndex = IPv6AddressSeqRange.getNetworkSegmentIndex(prefLength, bytesPerSegment, bitsPerSegment);
        }
        for (int i = networkSegIndex; i < segCount; ++i) {
            Integer segPrefLength;
            prefLengths[i] = segPrefLength = ParsedAddressGrouping.getPrefixedSegmentPrefixLength(bitsPerSegment, prefLength, i);
            shifts[i] = bitsPerSegment - segPrefLength;
        }
        int hostSegIndex = IPv6AddressSeqRange.getHostSegmentIndex(prefLength, bytesPerSegment, bitsPerSegment);
        return IPv6AddressSeqRange.iterator(lower, upper, creator, IPv6Address::getSegment, (seg, segIndex) -> seg.iterator(), (addr1, addr2, index) -> {
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

    public AddressComponentRangeSpliterator<IPv6AddressSeqRange, IPv6Address> prefixBlockSpliterator(int prefLength) {
        if (prefLength < 0) {
            throw new PrefixLenException(prefLength);
        }
        IPv6Address lower = this.getLower();
        int bitsPerSegment = lower.getBitsPerSegment();
        int bytesPerSegment = lower.getBytesPerSegment();
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getAddressCreator();
        Integer prefixLength = IPv6AddressSection.cacheBits(prefLength);
        int networkSegIndex = IPv6AddressSeqRange.getNetworkSegmentIndex(prefLength, bytesPerSegment, bitsPerSegment);
        int hostSegIndex = IPv6AddressSeqRange.getHostSegmentIndex(prefLength, bytesPerSegment, bitsPerSegment);
        return IPv6AddressSeqRange.createSpliterator(this, spliterator -> {
            IPv6AddressSeqRange range = (IPv6AddressSeqRange)spliterator.getAddressItem();
            return IPv6AddressSeqRange.split((IPAddressSeqRange.IPAddressSeqRangeSplitterSink)spliterator, (T segsLower, U segsUpper) -> new IPv6AddressSeqRange(creator.createAddressInternal((IPv6AddressSegment[])segsLower), creator.createAddressInternal((IPv6AddressSegment[])segsUpper)), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])range.getLower().getSection().getSegmentsInternal(), (AddressSegment[])range.getUpper().getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)prefixLength);
        }, (isLowest, isHighest, range) -> range.prefixBlockIterator(prefLength), range -> range.getPrefixCount(prefLength), range -> range.getPrefixCount(prefLength).compareTo(LONG_MAX) <= 0, range -> range.getPrefixCount(prefLength).longValue());
    }

    public Stream<IPv6Address> prefixBlockStream(int prefLength) {
        return StreamSupport.stream(this.prefixBlockSpliterator(prefLength), false);
    }

    public Iterator<IPv6AddressSeqRange> prefixIterator(int prefixLength) {
        return super.prefixIterator(prefixLength);
    }

    public AddressComponentSpliterator<IPv6AddressSeqRange> prefixSpliterator(int prefLength) {
        if (prefLength < 0) {
            throw new PrefixLenException(prefLength);
        }
        IPv6Address lower = this.getLower();
        int bitsPerSegment = lower.getBitsPerSegment();
        int bytesPerSegment = lower.getBytesPerSegment();
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getAddressCreator();
        Integer prefixLength = IPv6AddressSection.cacheBits(prefLength);
        int networkSegIndex = IPv6AddressSeqRange.getNetworkSegmentIndex(prefLength, bytesPerSegment, bitsPerSegment);
        int hostSegIndex = IPv6AddressSeqRange.getHostSegmentIndex(prefLength, bytesPerSegment, bitsPerSegment);
        return IPv6AddressSeqRange.createPrefixSpliterator(this, spliterator -> {
            IPv6AddressSeqRange range = (IPv6AddressSeqRange)spliterator.getAddressItem();
            return IPv6AddressSeqRange.split((IPAddressSeqRange.IPAddressSeqRangeSplitterSink)spliterator, (T segsLower, U segsUpper) -> new IPv6AddressSeqRange(creator.createAddressInternal((IPv6AddressSegment[])segsLower), creator.createAddressInternal((IPv6AddressSegment[])segsUpper)), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])range.getLower().getSection().getSegmentsInternal(), (AddressSegment[])range.getUpper().getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)prefixLength);
        }, (isLowest, isHighest, range) -> isLowest || isHighest ? range.prefixIterator(prefLength) : IPv6AddressSeqRange.rangedIterator(range.prefixBlockIterator(prefLength)), range -> range.getPrefixCount(prefLength), range -> range.getPrefixCount(prefLength).compareTo(LONG_MAX) <= 0, range -> range.getPrefixCount(prefLength).longValue());
    }

    public Stream<IPv6AddressSeqRange> prefixStream(int prefLength) {
        return StreamSupport.stream(this.prefixSpliterator(prefLength), false);
    }

    public String toIPv6String(Function<IPv6Address, String> lowerStringer, String separator, Function<IPv6Address, String> upperStringer) {
        return lowerStringer.apply(this.getLower()) + separator + upperStringer.apply(this.getUpper());
    }

    @Override
    public IPv6AddressSeqRange intersect(IPAddressSeqRange other) {
        return (IPv6AddressSeqRange)super.intersect(other);
    }

    @Override
    public IPv6AddressSeqRange join(IPAddressSeqRange other) {
        return (IPv6AddressSeqRange)super.join(other);
    }

    public IPv6AddressSeqRange[] subtract(IPAddressSeqRange other) {
        return (IPv6AddressSeqRange[])super.subtract(other);
    }

    @Override
    public IPv6AddressSeqRange toSequentialRange() {
        return this;
    }
}

