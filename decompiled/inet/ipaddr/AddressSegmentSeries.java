/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressComponent;
import inet.ipaddr.AddressSection;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.format.AddressDivisionSeries;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import java.util.Iterator;
import java.util.stream.Stream;

public interface AddressSegmentSeries
extends AddressDivisionSeries,
AddressComponent {
    public int getSegmentCount();

    public int getBitsPerSegment();

    public int getBytesPerSegment();

    public int getMaxSegmentValue();

    public AddressSection getSection();

    public AddressSection getSection(int var1);

    public AddressSection getSection(int var1, int var2);

    public AddressSegment getSegment(int var1);

    public String[] getSegmentStrings();

    public void getSegments(AddressSegment[] var1);

    public void getSegments(int var1, int var2, AddressSegment[] var3, int var4);

    public AddressSegment[] getSegments();

    @Override
    public AddressSegmentSeries getLower();

    @Override
    public AddressSegmentSeries getUpper();

    default public boolean testBit(int n) {
        return this.isOneBit(this.getBitCount() - (n + 1));
    }

    default public boolean isOneBit(int prefixBitIndex) {
        int bitsPerSegment = this.getBitsPerSegment();
        AddressSegment segment = this.getSegment(prefixBitIndex / bitsPerSegment);
        int segmentBitIndex = prefixBitIndex % bitsPerSegment;
        int value = segment.getSegmentValue();
        return (value & 1 << bitsPerSegment - (segmentBitIndex + 1)) != 0;
    }

    public Iterable<? extends AddressSegmentSeries> getIterable();

    public Iterator<? extends AddressSegmentSeries> iterator();

    @Override
    public AddressComponentSpliterator<? extends AddressSegmentSeries> spliterator();

    public Stream<? extends AddressSegmentSeries> stream();

    public Iterator<? extends AddressSegmentSeries> prefixIterator();

    public AddressComponentSpliterator<? extends AddressSegmentSeries> prefixSpliterator();

    public Iterator<? extends AddressSegmentSeries> prefixBlockIterator();

    public Stream<? extends AddressSegmentSeries> prefixStream();

    public AddressComponentSpliterator<? extends AddressSegmentSeries> prefixBlockSpliterator();

    public Stream<? extends AddressSegmentSeries> prefixBlockStream();

    public Iterator<? extends AddressSegment[]> segmentsIterator();

    public AddressComponentRangeSpliterator<? extends AddressSegmentSeries, ? extends AddressSegment[]> segmentsSpliterator();

    public Stream<? extends AddressSegment[]> segmentsStream();

    public AddressSegmentSeries increment(long var1) throws AddressValueException;

    public AddressSegmentSeries incrementBoundary(long var1) throws AddressValueException;

    public String toCanonicalString();

    public String toCompressedString();

    public AddressSegmentSeries reverseSegments();

    @Override
    public AddressSegmentSeries reverseBits(boolean var1);

    @Override
    public AddressSegmentSeries reverseBytes();

    public AddressSegmentSeries reverseBytesPerSegment();

    public AddressSegmentSeries toPrefixBlock();

    @Deprecated
    public AddressSegmentSeries removePrefixLength();

    public AddressSegmentSeries withoutPrefixLength();

    @Deprecated
    public AddressSegmentSeries removePrefixLength(boolean var1);

    public AddressSegmentSeries adjustPrefixBySegment(boolean var1);

    public AddressSegmentSeries adjustPrefixBySegment(boolean var1, boolean var2);

    public AddressSegmentSeries adjustPrefixLength(int var1);

    public AddressSegmentSeries adjustPrefixLength(int var1, boolean var2);

    public AddressSegmentSeries setPrefixLength(int var1);

    public AddressSegmentSeries setPrefixLength(int var1, boolean var2);

    @Deprecated
    public AddressSegmentSeries applyPrefixLength(int var1);
}

