/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressSegmentSeries;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import java.util.Iterator;
import java.util.stream.Stream;

public interface AddressSection
extends AddressSegmentSeries {
    public boolean contains(AddressSection var1);

    public boolean prefixEquals(AddressSection var1);

    @Override
    public AddressSection getLower();

    @Override
    public AddressSection getUpper();

    @Override
    public AddressSection reverseSegments();

    @Override
    public AddressSection reverseBits(boolean var1);

    @Override
    public AddressSection reverseBytes();

    @Override
    public AddressSection reverseBytesPerSegment();

    @Override
    public AddressSection toPrefixBlock();

    @Override
    @Deprecated
    public AddressSection removePrefixLength();

    @Override
    public AddressSection withoutPrefixLength();

    @Override
    @Deprecated
    public AddressSection removePrefixLength(boolean var1);

    @Override
    public AddressSection adjustPrefixBySegment(boolean var1);

    @Override
    public AddressSection adjustPrefixBySegment(boolean var1, boolean var2);

    @Override
    public AddressSection adjustPrefixLength(int var1);

    @Override
    public AddressSection adjustPrefixLength(int var1, boolean var2);

    @Override
    public AddressSection setPrefixLength(int var1);

    @Override
    public AddressSection setPrefixLength(int var1, boolean var2);

    @Override
    @Deprecated
    public AddressSection applyPrefixLength(int var1);

    public Iterable<? extends AddressSection> getIterable();

    public Iterator<? extends AddressSection> iterator();

    @Override
    public AddressComponentSpliterator<? extends AddressSection> spliterator();

    public Stream<? extends AddressSection> stream();

    public Iterator<? extends AddressSection> prefixIterator();

    public AddressComponentSpliterator<? extends AddressSection> prefixSpliterator();

    public Stream<? extends AddressSection> prefixStream();

    public Iterator<? extends AddressSection> prefixBlockIterator();

    public AddressComponentSpliterator<? extends AddressSection> prefixBlockSpliterator();

    public Stream<? extends AddressSection> prefixBlockStream();

    @Override
    public AddressSection increment(long var1);

    @Override
    public AddressSection incrementBoundary(long var1);
}

