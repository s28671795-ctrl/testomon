/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressSegmentSeries;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.IPAddressDivisionSeries;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.format.util.IPAddressPartStringCollection;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

public interface IPAddressSegmentSeries
extends IPAddressDivisionSeries,
AddressSegmentSeries {
    public static Comparator<? super IPAddressSegmentSeries> getPrefixLenComparator() {
        return IPAddressSection.mergeListComparator;
    }

    public IPAddress.IPVersion getIPVersion();

    public IPAddressSegmentSeries assignMinPrefixForBlock();

    public IPAddressSegmentSeries assignPrefixForSingleBlock();

    public IPAddressSegmentSeries coverWithPrefixBlock();

    public IPAddressSegmentSeries[] spanWithPrefixBlocks();

    public IPAddressSegmentSeries[] spanWithSequentialBlocks();

    @Override
    public IPAddressSegmentSeries toPrefixBlock();

    public IPAddressSegmentSeries toPrefixBlock(int var1) throws PrefixLenException;

    public IPAddressSegmentSeries getHostMask();

    public IPAddressSegmentSeries getNetworkMask();

    public IPAddressSection getHostSection();

    public IPAddressSection getHostSection(int var1);

    public IPAddressSection getNetworkSection();

    public IPAddressSection getNetworkSection(int var1);

    public IPAddressSection getNetworkSection(int var1, boolean var2);

    public String toFullString();

    public String toPrefixLengthString();

    public String toSubnetString();

    public String toNormalizedWildcardString();

    public String toCanonicalWildcardString();

    public String toCompressedWildcardString();

    public String toSQLWildcardString();

    public String toReverseDNSLookupString();

    public String toBinaryString() throws IncompatibleAddressException;

    public String toSegmentedBinaryString();

    public String toOctalString(boolean var1) throws IncompatibleAddressException;

    public IPAddressPartStringCollection toStringCollection(IPAddressSection.IPStringBuilderOptions var1);

    public String toNormalizedString(IPAddressSection.IPStringOptions var1);

    @Override
    public IPAddressNetwork<?, ?, ?, ?, ?> getNetwork();

    @Override
    public IPAddressSection getSection();

    @Override
    public IPAddressSection getSection(int var1);

    @Override
    public IPAddressSection getSection(int var1, int var2);

    @Override
    public IPAddressSegment getSegment(int var1);

    public IPAddressSegment[] getSegments();

    public BigInteger getNonZeroHostCount();

    public IPAddressSegmentSeries getLowerNonZeroHost();

    @Override
    public IPAddressSegmentSeries getLower();

    @Override
    public IPAddressSegmentSeries getUpper();

    public Iterable<? extends IPAddressSegmentSeries> getIterable();

    public Iterator<? extends IPAddressSegmentSeries> iterator();

    @Override
    public AddressComponentSpliterator<? extends IPAddressSegmentSeries> spliterator();

    public Stream<? extends IPAddressSegmentSeries> stream();

    public Iterator<? extends IPAddressSegmentSeries> prefixIterator();

    public AddressComponentSpliterator<? extends IPAddressSegmentSeries> prefixSpliterator();

    public Stream<? extends IPAddressSegmentSeries> prefixStream();

    public Iterator<? extends IPAddressSegmentSeries> prefixBlockIterator();

    public AddressComponentSpliterator<? extends IPAddressSegmentSeries> prefixBlockSpliterator();

    public Stream<? extends IPAddressSegmentSeries> prefixBlockStream();

    public Iterator<? extends IPAddressSegmentSeries> nonZeroHostIterator();

    public Iterator<? extends IPAddressSegmentSeries> blockIterator(int var1);

    public AddressComponentSpliterator<? extends IPAddressSegmentSeries> blockSpliterator(int var1);

    public Stream<? extends IPAddressSegmentSeries> blockStream(int var1);

    public Iterator<? extends IPAddressSegmentSeries> sequentialBlockIterator();

    public AddressComponentSpliterator<? extends IPAddressSegmentSeries> sequentialBlockSpliterator();

    public Stream<? extends IPAddressSegmentSeries> sequentialBlockStream();

    public BigInteger getSequentialBlockCount();

    public Iterator<? extends IPAddressSegment[]> segmentsIterator();

    public AddressComponentRangeSpliterator<? extends IPAddressSegmentSeries, ? extends IPAddressSegment[]> segmentsSpliterator();

    public Stream<? extends IPAddressSegment[]> segmentsStream();

    public Iterator<? extends IPAddressSegment[]> segmentsNonZeroHostIterator();

    @Override
    public IPAddressSegmentSeries increment(long var1);

    @Override
    public IPAddressSegmentSeries incrementBoundary(long var1);

    public IPAddressSegmentSeries toZeroHost();

    public IPAddressSegmentSeries toZeroHost(int var1);

    public IPAddressSegmentSeries toZeroNetwork();

    public boolean includesZeroHost();

    public boolean includesZeroHost(int var1);

    public IPAddressSegmentSeries toMaxHost(int var1);

    public IPAddressSegmentSeries toMaxHost();

    public boolean includesMaxHost();

    public boolean includesMaxHost(int var1);

    @Override
    public IPAddressSegmentSeries reverseSegments();

    @Override
    public IPAddressSegmentSeries reverseBits(boolean var1);

    @Override
    public IPAddressSegmentSeries reverseBytes();

    @Override
    public IPAddressSegmentSeries reverseBytesPerSegment();

    @Override
    @Deprecated
    public IPAddressSegmentSeries removePrefixLength();

    @Override
    public IPAddressSegmentSeries withoutPrefixLength();

    @Override
    @Deprecated
    public IPAddressSegmentSeries removePrefixLength(boolean var1);

    @Override
    public IPAddressSegmentSeries adjustPrefixBySegment(boolean var1);

    @Override
    public IPAddressSegmentSeries adjustPrefixBySegment(boolean var1, boolean var2);

    @Override
    public IPAddressSegmentSeries adjustPrefixLength(int var1);

    @Override
    public IPAddressSegmentSeries adjustPrefixLength(int var1, boolean var2);

    @Override
    public IPAddressSegmentSeries setPrefixLength(int var1);

    @Override
    public IPAddressSegmentSeries setPrefixLength(int var1, boolean var2);

    @Override
    @Deprecated
    public IPAddressSegmentSeries applyPrefixLength(int var1);
}

