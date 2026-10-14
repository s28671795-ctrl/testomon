/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv6;

import inet.ipaddr.Address;
import inet.ipaddr.AddressConversionException;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressPositionException;
import inet.ipaddr.AddressSection;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.IPAddressSegmentSeries;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.InconsistentPrefixException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.SizeMismatchException;
import inet.ipaddr.format.AddressDivisionGroupingBase;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.large.IPAddressLargeDivision;
import inet.ipaddr.format.large.IPAddressLargeDivisionGrouping;
import inet.ipaddr.format.standard.AddressCreator;
import inet.ipaddr.format.standard.AddressDivisionGrouping;
import inet.ipaddr.format.standard.IPAddressBitsDivision;
import inet.ipaddr.format.standard.IPAddressDivision;
import inet.ipaddr.format.standard.IPAddressDivisionGrouping;
import inet.ipaddr.format.string.AddressStringDivision;
import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.format.util.IPAddressPartConfiguredString;
import inet.ipaddr.format.util.IPAddressPartStringCollection;
import inet.ipaddr.format.util.IPAddressPartStringSubCollection;
import inet.ipaddr.format.util.IPAddressStringWriter;
import inet.ipaddr.format.util.sql.IPAddressSQLTranslator;
import inet.ipaddr.format.util.sql.SQLStringMatcher;
import inet.ipaddr.ipv4.IPv4Address;
import inet.ipaddr.ipv4.IPv4AddressNetwork;
import inet.ipaddr.ipv4.IPv4AddressSection;
import inet.ipaddr.ipv6.IPv6Address;
import inet.ipaddr.ipv6.IPv6AddressNetwork;
import inet.ipaddr.ipv6.IPv6AddressSegment;
import inet.ipaddr.mac.MACAddress;
import inet.ipaddr.mac.MACAddressNetwork;
import inet.ipaddr.mac.MACAddressSection;
import inet.ipaddr.mac.MACAddressSegment;
import java.math.BigInteger;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IPv6AddressSection
extends IPAddressSection
implements Iterable<IPv6AddressSection> {
    private static final long serialVersionUID = 4L;
    private static IPv6AddressNetwork.IPv6AddressCreator[] creators = new IPv6AddressNetwork.IPv6AddressCreator[8];
    private transient IPv6StringCache stringCache;
    private transient AddressDivisionGrouping.SectionCache<IPv6AddressSection> sectionCache;
    transient IPv4AddressSection embeddedIPv4Section;
    transient IPv6v4MixedAddressSection defaultMixedAddressSection;
    public final int addressSegmentIndex;
    private transient IPAddressDivisionGrouping.RangeList zeroSegments;
    private transient IPAddressDivisionGrouping.RangeList zeroRanges;
    private static final BigInteger[] MAX_VALUES_BY_SEGMENT = new BigInteger[]{BigInteger.ZERO, BigInteger.valueOf(65535L), BigInteger.valueOf(0xFFFFFFFFL), BigInteger.valueOf(0xFFFFFFFFFFFFL), BigInteger.valueOf(1L).shiftLeft(64).subtract(BigInteger.ONE), BigInteger.valueOf(1L).shiftLeft(80).subtract(BigInteger.ONE), BigInteger.valueOf(1L).shiftLeft(96).subtract(BigInteger.ONE), BigInteger.valueOf(1L).shiftLeft(112).subtract(BigInteger.ONE), BigInteger.valueOf(1L).shiftLeft(128).subtract(BigInteger.ONE)};

    public IPv6AddressSection(IPv6AddressSegment segment) {
        this(new IPv6AddressSegment[]{segment}, 0, false);
    }

    public IPv6AddressSection(IPv6AddressSegment segment, int startIndex) throws AddressValueException {
        this(new IPv6AddressSegment[]{segment}, startIndex, false);
    }

    public IPv6AddressSection(IPv6AddressSegment[] segments) throws AddressValueException {
        this(segments, 0, true);
    }

    public IPv6AddressSection(IPv6AddressSegment[] segments, Integer networkPrefixLength) throws AddressValueException {
        this(segments, 0, networkPrefixLength);
    }

    public IPv6AddressSection(IPv6AddressSegment[] segments, int startIndex, Integer networkPrefixLength) throws AddressValueException {
        this(segments, startIndex, true, networkPrefixLength, false);
    }

    protected IPv6AddressSection(IPv6AddressSegment[] segments, int startIndex, boolean cloneSegments, Integer networkPrefixLength, boolean singleOnly) throws AddressValueException {
        this(segments, startIndex, cloneSegments, networkPrefixLength == null);
        if (networkPrefixLength != null) {
            if (networkPrefixLength < 0) {
                throw new PrefixLenException(networkPrefixLength);
            }
            int max = segments.length << 4;
            if (networkPrefixLength > max) {
                if (networkPrefixLength > 128) {
                    throw new PrefixLenException(networkPrefixLength);
                }
                networkPrefixLength = max;
            }
            if (segments.length > 0) {
                if (this.cachedPrefixLength != NO_PREFIX_LENGTH && this.cachedPrefixLength < networkPrefixLength) {
                    networkPrefixLength = this.cachedPrefixLength;
                }
                IPv6AddressNetwork network = this.getNetwork();
                IPv6AddressSection.setPrefixedSegments((AddressNetwork)network, (int)networkPrefixLength, (AddressSegment[])this.getSegmentsInternal(), (int)this.getBitsPerSegment(), (int)this.getBytesPerSegment(), (AddressNetwork.AddressSegmentCreator)network.getAddressCreator(), !singleOnly && IPv6AddressSection.isPrefixSubnetSegs(segments, networkPrefixLength, network, false) ? IPv6AddressSegment::toNetworkSegment : IPv6AddressSegment::toPrefixedSegment);
            }
            this.cachedPrefixLength = networkPrefixLength;
        }
    }

    protected IPv6AddressSection(IPv6AddressSegment[] segments, int startIndex, boolean cloneSegments) throws AddressValueException {
        this(segments, startIndex, cloneSegments, true);
    }

    IPv6AddressSection(IPv6AddressSegment[] segments, int startIndex, boolean cloneSegments, boolean normalizeSegments) throws AddressValueException {
        super(segments, cloneSegments, true);
        if (normalizeSegments && this.isPrefixed()) {
            IPv6AddressSection.normalizePrefixBoundary((int)this.getNetworkPrefixLength(), (IPAddressSegment[])this.getSegmentsInternal(), (int)16, (int)2, IPv6AddressSegment::toPrefixNormalizedSeg);
        }
        this.addressSegmentIndex = startIndex;
        if (startIndex < 0) {
            throw new AddressPositionException(startIndex);
        }
        if (startIndex + segments.length > 8) {
            throw new AddressValueException(startIndex + segments.length);
        }
    }

    public IPv6AddressSection(Address.SegmentValueProvider valueProvider, int segmentCount, Integer networkPrefixLength) throws AddressValueException {
        this(valueProvider, valueProvider, segmentCount, networkPrefixLength);
    }

    public IPv6AddressSection(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, int segmentCount, Integer networkPrefixLength) throws AddressValueException {
        super(new IPv6AddressSegment[segmentCount], false, false);
        AddressSegment[] segs = this.getSegmentsInternal();
        IPv6AddressNetwork network = this.getNetwork();
        IPv6AddressSection.createSegments((AddressSegment[])segs, (Address.SegmentValueProvider)lowerValueProvider, (Address.SegmentValueProvider)upperValueProvider, (int)this.getBytesPerSegment(), (int)this.getBitsPerSegment(), (AddressNetwork)network, (Integer)networkPrefixLength);
        if (networkPrefixLength != null) {
            if (networkPrefixLength < 0 || networkPrefixLength > 128) {
                throw new PrefixLenException(networkPrefixLength);
            }
            if (network.getPrefixConfiguration().zeroHostsAreSubnets() && IPv6AddressSection.isPrefixSubnetSegs((IPAddressSegment[])segs, networkPrefixLength, network, false)) {
                IPv6AddressSection.setPrefixedSegments((AddressNetwork)network, (int)networkPrefixLength, (AddressSegment[])this.getSegmentsInternal(), (int)this.getBitsPerSegment(), (int)this.getBytesPerSegment(), (AddressNetwork.AddressSegmentCreator)network.getAddressCreator(), IPv6AddressSegment::toNetworkSegment);
            }
            this.cachedPrefixLength = networkPrefixLength;
        } else {
            this.cachedPrefixLength = NO_PREFIX_LENGTH;
        }
        this.addressSegmentIndex = 0;
    }

    public IPv6AddressSection(Address.SegmentValueProvider valueProvider, int segmentCount) throws AddressValueException {
        this(valueProvider, valueProvider, segmentCount);
    }

    public IPv6AddressSection(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, int segmentCount) {
        this(lowerValueProvider, upperValueProvider, segmentCount, null);
    }

    protected IPv6AddressSection(byte[] bytes, int segmentCount, Integer networkPrefixLength, boolean cloneBytes, boolean singleOnly) throws AddressValueException {
        this(bytes, 0, bytes.length, segmentCount, networkPrefixLength, cloneBytes, singleOnly);
    }

    protected IPv6AddressSection(byte[] bytes, int byteStartIndex, int byteEndIndex, int segmentCount, Integer networkPrefixLength, boolean cloneBytes, boolean singleOnly) throws AddressValueException {
        super(new IPv6AddressSegment[segmentCount >= 0 ? segmentCount : Math.max(0, byteEndIndex - byteStartIndex) + 2 - 1 >> 1], false, false);
        boolean byteLengthIsExact;
        AddressSegment[] segs = this.getSegmentsInternal();
        IPv6AddressNetwork network = this.getNetwork();
        IPv6AddressSection.toSegments((AddressSegment[])segs, (byte[])bytes, (int)byteStartIndex, (int)byteEndIndex, (int)this.getBytesPerSegment(), (int)this.getBitsPerSegment(), (AddressNetwork)network, (Integer)networkPrefixLength);
        boolean bl = byteLengthIsExact = bytes.length == segs.length << 1;
        if (networkPrefixLength != null) {
            if (networkPrefixLength < 0) {
                throw new PrefixLenException(networkPrefixLength);
            }
            int max = segs.length << 4;
            if (networkPrefixLength > max) {
                if (networkPrefixLength > 128) {
                    throw new PrefixLenException(networkPrefixLength);
                }
                networkPrefixLength = max;
            }
            if (segs.length > 0) {
                if (network.getPrefixConfiguration().zeroHostsAreSubnets() && !singleOnly) {
                    if (IPv6AddressSection.isPrefixSubnetSegs((IPAddressSegment[])segs, networkPrefixLength, network, false)) {
                        IPv6AddressSection.setPrefixedSegments((AddressNetwork)network, (int)networkPrefixLength, (AddressSegment[])segs, (int)this.getBitsPerSegment(), (int)this.getBytesPerSegment(), (AddressNetwork.AddressSegmentCreator)network.getAddressCreator(), IPv6AddressSegment::toNetworkSegment);
                    } else if (byteLengthIsExact && networkPrefixLength >= this.getBitCount()) {
                        this.setBytes(cloneBytes ? (byte[])bytes.clone() : bytes);
                    }
                } else if (byteLengthIsExact && network.getPrefixConfiguration().prefixedSubnetsAreExplicit() || networkPrefixLength >= this.getBitCount()) {
                    this.setBytes(cloneBytes ? (byte[])bytes.clone() : bytes);
                }
            } else if (byteLengthIsExact) {
                this.setBytes(bytes);
            }
            this.cachedPrefixLength = networkPrefixLength;
        } else {
            this.cachedPrefixLength = NO_PREFIX_LENGTH;
            if (byteLengthIsExact) {
                this.setBytes(cloneBytes ? (byte[])bytes.clone() : bytes);
            }
        }
        this.addressSegmentIndex = 0;
    }

    protected IPv6AddressSection(byte[] bytes, int segmentCount, Integer prefix, boolean cloneBytes) throws AddressValueException {
        this(bytes, 0, bytes.length, segmentCount, prefix, cloneBytes, false);
    }

    public IPv6AddressSection(byte[] bytes, int byteStartIndex, int byteEndIndex, Integer prefix) throws AddressValueException {
        this(bytes, byteStartIndex, byteEndIndex, -1, prefix, true, false);
    }

    public IPv6AddressSection(byte[] bytes, int byteStartIndex, int byteEndIndex) throws AddressValueException {
        this(bytes, byteStartIndex, byteEndIndex, -1, null, true, false);
    }

    public IPv6AddressSection(byte[] bytes, Integer prefix) throws AddressValueException {
        this(bytes, 0, bytes.length, -1, prefix, true, false);
    }

    public IPv6AddressSection(byte[] bytes) throws AddressValueException {
        this(bytes, 0, bytes.length, -1, null, true, false);
    }

    public IPv6AddressSection(BigInteger val, int segmentCount, Integer networkPrefixLength) throws AddressValueException {
        this(val.toByteArray(), segmentCount, networkPrefixLength, false);
    }

    public IPv6AddressSection(BigInteger val, int segmentCount) throws AddressValueException {
        this(val, segmentCount, null);
    }

    public IPv6AddressSection(long highBytes, long lowBytes, int segmentCount, Integer networkPrefixLength) throws PrefixLenException {
        super(new IPv6AddressSegment[segmentCount], false, false);
        AddressSegment[] segs = this.getSegmentsInternal();
        IPv6AddressNetwork network = this.getNetwork();
        IPv6AddressSection.createSegments((AddressSegment[])segs, (long)highBytes, (long)lowBytes, (int)this.getBitsPerSegment(), (AddressNetwork)network, (Integer)networkPrefixLength);
        if (networkPrefixLength != null) {
            if (networkPrefixLength < 0 || networkPrefixLength > 128) {
                throw new PrefixLenException(networkPrefixLength);
            }
            if (network.getPrefixConfiguration().zeroHostsAreSubnets() && IPv6AddressSection.isPrefixSubnetSegs((IPAddressSegment[])segs, networkPrefixLength, network, false)) {
                IPv6AddressSection.setPrefixedSegments((AddressNetwork)network, (int)networkPrefixLength, (AddressSegment[])this.getSegmentsInternal(), (int)this.getBitsPerSegment(), (int)this.getBytesPerSegment(), (AddressNetwork.AddressSegmentCreator)network.getAddressCreator(), IPv6AddressSegment::toNetworkSegment);
            }
            this.cachedPrefixLength = networkPrefixLength;
        } else {
            this.cachedPrefixLength = NO_PREFIX_LENGTH;
        }
        this.addressSegmentIndex = 0;
    }

    public IPv6AddressSection(long highBytes, long lowBytes, int segmentCount) {
        this(highBytes, lowBytes, segmentCount, null);
    }

    public IPv6AddressSection(MACAddress eui) {
        this(eui.getSection(), 4, 4);
    }

    public IPv6AddressSection(MACAddressSection eui) {
        this(eui, IPv6AddressSection.getIPv6StartIndex(eui), IPv6AddressSection.getIPv6SegmentCount(eui));
    }

    private IPv6AddressSection(MACAddressSection eui, int ipv6StartIndex, int ipv6SegmentCount) throws IncompatibleAddressException {
        super(ipv6SegmentCount <= 0 ? IPv6AddressNetwork.EMPTY_SEGMENTS : new IPv6AddressSegment[ipv6SegmentCount], false, false);
        this.cachedPrefixLength = NO_PREFIX_LENGTH;
        this.addressSegmentIndex = ipv6StartIndex;
        IPv6AddressSegment[] segs = this.getSegmentsInternal();
        IPv6Address.toEUI64Segments(segs, 0, eui, eui.addressSegmentIndex, eui.isExtended(), this.getNetwork().getAddressCreator(), this.getMACNetwork().getAddressCreator(), null);
        this.checkSegments(segs);
    }

    private static int getIPv6SegmentCount(MACAddressSection eui) {
        int euiStartIndex = eui.addressSegmentIndex;
        int euiEndIndex = euiStartIndex + eui.getSegmentCount();
        int result = euiEndIndex + 1 >> 1;
        result -= euiStartIndex >> 1;
        if (!eui.isExtended() && euiStartIndex <= 2 && euiEndIndex >= 4) {
            ++result;
        }
        return result;
    }

    private static int getIPv6StartIndex(MACAddressSection eui) {
        int euiStartIndex = eui.addressSegmentIndex;
        int result = 4 + (euiStartIndex >> 1);
        if (!eui.isExtended() && euiStartIndex >= 3) {
            ++result;
        }
        return result;
    }

    @Override
    protected void initCachedValues(Integer prefixLen, boolean network, Integer cachedNetworkPrefix, Integer cachedMinPrefix, Integer cachedEquivalentPrefix, BigInteger cachedCount, IPAddressDivisionGrouping.RangeList zeroSegments, IPAddressDivisionGrouping.RangeList zeroRanges) {
        super.initCachedValues(prefixLen, network, cachedNetworkPrefix, cachedMinPrefix, cachedEquivalentPrefix, cachedCount, zeroSegments, zeroRanges);
        this.zeroSegments = zeroSegments;
        this.zeroRanges = zeroRanges;
    }

    @Override
    protected void setInetAddress(InetAddress addr) {
        super.setInetAddress(addr);
    }

    @Override
    public IPv6AddressSection getSection() {
        return this;
    }

    @Override
    public IPv6AddressSection getSection(int index) {
        return this.getSection(index, this.getSegmentCount());
    }

    @Override
    public IPv6AddressSection getSection(int index, int endIndex) {
        return IPv6AddressSection.getSection(index, endIndex, this, this.getAddressCreator(this.addressSegmentIndex + index));
    }

    public IPv6AddressSegment[] getSegments() {
        return (IPv6AddressSegment[])this.getDivisionsInternal().clone();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void cache(IPv6AddressSection lower, IPv6AddressSection upper) {
        AddressDivisionGrouping.SectionCache<IPv6AddressSection> cache = this.sectionCache;
        if ((lower != null || upper != null) && (cache == null || lower != null && cache.lower == null || upper != null && cache.upper == null)) {
            IPv6AddressSection iPv6AddressSection = this;
            synchronized (iPv6AddressSection) {
                boolean create;
                cache = this.sectionCache;
                boolean bl = create = cache == null;
                if (create) {
                    this.sectionCache = cache = new AddressDivisionGrouping.SectionCache();
                    cache.lower = lower;
                    cache.upper = upper;
                } else {
                    if (cache.lower == null) {
                        cache.lower = lower;
                    }
                    if (cache.upper == null) {
                        cache.upper = upper;
                    }
                }
            }
        }
    }

    protected IPv6AddressSection getSingleLowestOrHighestSection() {
        return IPv6AddressSection.getSingleLowestOrHighestSection(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    IPv6AddressSection getLowestOrHighestSection(boolean lowest, boolean excludeZeroHost) {
        IPv6AddressSection result = this.getSingleLowestOrHighestSection();
        if (result == null) {
            AddressDivisionGrouping.SectionCache<IPv6AddressSection> cache = this.sectionCache;
            if (cache == null || (lowest ? (excludeZeroHost ? (result = (IPv6AddressSection)cache.lowerNonZeroHost) == null && !cache.lowerNonZeroHostIsNull : (result = (IPv6AddressSection)cache.lower) == null) : (result = (IPv6AddressSection)cache.upper) == null)) {
                IPv6AddressSection iPv6AddressSection = this;
                synchronized (iPv6AddressSection) {
                    boolean create;
                    cache = this.sectionCache;
                    boolean bl = create = cache == null;
                    if (create) {
                        this.sectionCache = cache = new AddressDivisionGrouping.SectionCache();
                    } else if (lowest) {
                        create = excludeZeroHost ? (result = (IPv6AddressSection)cache.lowerNonZeroHost) == null && !cache.lowerNonZeroHostIsNull : (result = (IPv6AddressSection)cache.lower) == null;
                    } else {
                        result = (IPv6AddressSection)cache.upper;
                        boolean bl2 = create = result == null;
                    }
                    if (create) {
                        result = IPv6AddressSection.createLowestOrHighestSection(this, this.getAddressCreator(), this::segmentsNonZeroHostIterator, i -> lowest ? this.getSegment(i).getLower() : this.getSegment(i).getUpper(), lowest, excludeZeroHost);
                        if (result == null) {
                            cache.lowerNonZeroHostIsNull = true;
                        } else if (lowest) {
                            if (excludeZeroHost) {
                                cache.lowerNonZeroHost = result;
                            } else {
                                cache.lower = result;
                            }
                        } else {
                            cache.upper = result;
                        }
                    }
                }
            }
        } else if (excludeZeroHost && this.includesZeroHost()) {
            return null;
        }
        return result;
    }

    @Override
    public IPv6AddressSection getLowerNonZeroHost() {
        return this.getLowestOrHighestSection(true, true);
    }

    @Override
    public IPv6AddressSection getLower() {
        return this.getLowestOrHighestSection(true, false);
    }

    @Override
    public IPv6AddressSection getUpper() {
        return this.getLowestOrHighestSection(false, false);
    }

    @Override
    public IPv6AddressSection reverseBits(boolean perByte) {
        return IPv6AddressSection.reverseBits(perByte, this, this.getAddressCreator(), i -> this.getSegment(i).reverseBits(perByte), true);
    }

    @Override
    public IPv6AddressSection reverseBytes() {
        return this.reverseBytes(false);
    }

    @Override
    public IPv6AddressSection reverseBytesPerSegment() {
        return this.reverseBytes(true);
    }

    private IPv6AddressSection reverseBytes(boolean perSegment) {
        return IPv6AddressSection.reverseBytes(perSegment, this, this.getAddressCreator(), i -> this.getSegment(i).reverseBytes(), true);
    }

    @Override
    public IPv6AddressSection reverseSegments() {
        if (this.getSegmentCount() <= 1) {
            return this;
        }
        return IPv6AddressSection.reverseSegments(this, this.getAddressCreator(), i -> this.getSegment(i).withoutPrefixLength(), true);
    }

    public Iterable<IPv6AddressSection> getIterable() {
        return this;
    }

    private Iterator<IPv6AddressSection> iterator(Predicate<IPv6AddressSegment[]> excludeFunc) {
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        boolean useOriginal = !this.isMultiple() && (!isAllSubnets || !this.isPrefixed());
        IPv6AddressSection original = !useOriginal || excludeFunc != null && excludeFunc.test(this.getSegmentsInternal()) ? null : this;
        return IPv6AddressSection.iterator(useOriginal, original, this.getAddressCreator(), useOriginal ? null : this.segmentsIterator(excludeFunc), isAllSubnets ? null : this.getPrefixLength());
    }

    private AddressComponentSpliterator<IPv6AddressSection> prefixSpliterator(boolean isBlockIterator) {
        Integer prefLength = this.getPrefixLength();
        if (prefLength == null || prefLength > this.getBitCount()) {
            return this.spliterator(false);
        }
        return this.prefixSpliterator(isBlockIterator, prefLength);
    }

    private AddressComponentSpliterator<IPv6AddressSection> prefixSpliterator(boolean isBlockIterator, int prefixLength) {
        if (prefixLength > this.getBitCount() || prefixLength < 0) {
            throw new PrefixLenException((AddressItem)this, prefixLength);
        }
        Integer prefLength = IPv6AddressSection.cacheBits(prefixLength);
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getAddressCreator();
        int networkSegIndex = IPv6AddressSection.getNetworkSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int hostSegIndex = IPv6AddressSection.getHostSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        return IPv6AddressSection.createSeriesSpliterator(this.setPrefixLength(prefixLength, false), spliterator -> IPv6AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv6AddressSection)IPv6AddressSection.createIteratedSection((AddressSegment[])segs, (AddressCreator)creator, (Integer)prefLength), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv6AddressSection)spliterator.getAddressItem()).getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)prefLength), isBlockIterator ? (isLowest, isHighest, section) -> section.prefixBlockIterator() : (!this.isSequential() ? (isLowest, isHighest, section) -> section.prefixIterator() : (isLowest, isHighest, section) -> isLowest || isHighest ? section.prefixIterator() : section.prefixBlockIterator()), AddressDivisionGroupingBase::getPrefixCount, section -> section.getPrefixCount().compareTo(LONG_MAX) <= 0, section -> IPv6AddressSection.longPrefixCount(section, prefixLength));
    }

    public Iterator<IPv6AddressSection> nonZeroHostIterator() {
        return this.iterator(this.excludeNonZeroHosts());
    }

    @Override
    public Iterator<IPv6AddressSection> iterator() {
        return this.iterator(null);
    }

    @Override
    public AddressComponentSpliterator<IPv6AddressSection> spliterator() {
        return this.spliterator(false);
    }

    public Stream<IPv6AddressSection> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Iterator<IPv6AddressSection> prefixIterator() {
        return this.prefixIterator(false);
    }

    public AddressComponentSpliterator<IPv6AddressSection> prefixSpliterator() {
        return this.prefixSpliterator(false);
    }

    public Stream<IPv6AddressSection> prefixStream() {
        return StreamSupport.stream(this.prefixSpliterator(), false);
    }

    public Iterator<IPv6AddressSection> prefixBlockIterator() {
        return this.prefixIterator(true);
    }

    public AddressComponentSpliterator<IPv6AddressSection> prefixBlockSpliterator() {
        return this.prefixSpliterator(true);
    }

    public Stream<IPv6AddressSection> prefixBlockStream() {
        return StreamSupport.stream(this.prefixBlockSpliterator(), false);
    }

    private Iterator<IPv6AddressSection> prefixIterator(boolean isBlockIterator) {
        Integer prefLength = this.getPrefixLength();
        if (prefLength == null || prefLength > this.getBitCount()) {
            return this.iterator();
        }
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getAddressCreator();
        boolean useOriginal = isBlockIterator ? this.isSinglePrefixBlock() : this.getPrefixCount().equals(BigInteger.ONE);
        int networkSegIndex = IPv6AddressSection.getNetworkSegmentIndex(prefLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int hostSegIndex = IPv6AddressSection.getHostSegmentIndex(prefLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int segCount = this.getSegmentCount();
        return IPv6AddressSection.iterator(useOriginal, this, creator, useOriginal ? null : IPv6AddressSection.segmentsIterator(segCount, creator, null, index -> this.getSegment(index).iterator(), null, networkSegIndex, hostSegIndex, isBlockIterator ? index -> this.getSegment(index).prefixBlockIterator() : index -> this.getSegment(index).prefixIterator()), prefLength);
    }

    public Iterator<IPv6AddressSection> blockIterator(int segmentCount) {
        if (segmentCount < 0) {
            throw new IllegalArgumentException();
        }
        if (segmentCount >= this.getSegmentCount()) {
            return this.iterator();
        }
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getAddressCreator();
        boolean useOriginal = true;
        for (int i = 0; i <= segmentCount - 1; ++i) {
            if (!this.getSegment(i).isMultiple()) continue;
            useOriginal = false;
            break;
        }
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        int segCount = this.getSegmentCount();
        return IPv6AddressSection.iterator(useOriginal, this, creator, useOriginal ? null : IPv6AddressSection.segmentsIterator(segCount, creator, null, index -> this.getSegment(index).iterator(!isAllSubnets), null, networkSegIndex, hostSegIndex, index -> this.getSegment(index).identityIterator()), isAllSubnets ? null : this.getPrefixLength());
    }

    public AddressComponentSpliterator<IPv6AddressSection> blockSpliterator(int segmentCount) {
        IPv6AddressSection forIteration;
        Integer iterationsPrefix;
        Integer prefLength;
        if (segmentCount < 0) {
            throw new IllegalArgumentException();
        }
        if (segmentCount >= this.getSegmentCount()) {
            return this.spliterator();
        }
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getAddressCreator();
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        Integer n = prefLength = isAllSubnets ? null : this.getPrefixLength();
        if (isAllSubnets) {
            iterationsPrefix = null;
            forIteration = this.withoutPrefixLength();
        } else {
            iterationsPrefix = prefLength;
            forIteration = this;
        }
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return IPv6AddressSection.createSeriesSpliterator(forIteration, spliterator -> IPv6AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv6AddressSection)IPv6AddressSection.createIteratedSection((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv6AddressSection)spliterator.getAddressItem()).getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), (isLowest, isHighest, section) -> section.blockIterator(segmentCount), section -> section.getBlockCount(segmentCount), section -> section.getBlockCount(segmentCount).compareTo(LONG_MAX) <= 0, section -> IPv6AddressSection.longCount(section, segmentCount));
    }

    public Stream<IPv6AddressSection> blockStream(int segmentCount) {
        return StreamSupport.stream(this.blockSpliterator(segmentCount), false);
    }

    public Iterator<IPv6AddressSection> sequentialBlockIterator() {
        return super.sequentialBlockIterator();
    }

    public AddressComponentSpliterator<IPv6AddressSection> sequentialBlockSpliterator() {
        return super.sequentialBlockSpliterator();
    }

    public Stream<IPv6AddressSection> sequentialBlockStream() {
        return super.sequentialBlockStream();
    }

    private Iterator<IPv6AddressSegment[]> segmentsIterator(Predicate<IPv6AddressSegment[]> excludeFunc) {
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        return IPAddressSection.segmentsIterator(this.getSegmentCount(), this.getSegmentCreator(), this.isMultiple() ? null : () -> this.getLower().getSegments(), index -> this.getSegment(index).iterator(!isAllSubnets), excludeFunc);
    }

    private Predicate<IPv6AddressSegment[]> excludeNonZeroHosts() {
        if (this.isPrefixed()) {
            int prefLength = this.getNetworkPrefixLength();
            return segments -> this.isZeroHost((IPv6AddressSegment[])segments, prefLength);
        }
        return null;
    }

    public Iterator<IPv6AddressSegment[]> segmentsNonZeroHostIterator() {
        return this.segmentsIterator(this.excludeNonZeroHosts());
    }

    public Iterator<IPv6AddressSegment[]> segmentsIterator() {
        return this.segmentsIterator(null);
    }

    public AddressComponentRangeSpliterator<IPv6AddressSection, IPv6AddressSegment[]> segmentsSpliterator() {
        IPv6AddressSection forIteration;
        Integer iterationsPrefix;
        int segmentCount = this.getSegmentCount();
        Integer prefixLength = this.getNetworkPrefixLength();
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getAddressCreator();
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (isAllSubnets) {
            iterationsPrefix = null;
            forIteration = this.withoutPrefixLength();
        } else {
            iterationsPrefix = prefixLength;
            forIteration = this;
        }
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return IPv6AddressSection.createItemSpliterator(forIteration, spliterator -> IPv6AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv6AddressSection)IPv6AddressSection.createIteratedSection((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv6AddressSection)spliterator.getAddressItem()).getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), (isLowest, isHighest, section) -> section.segmentsIterator(), AddressDivisionGroupingBase::getCount, section -> section.getCount().compareTo(LONG_MAX) <= 0, section -> IPv6AddressSection.longCount(section, segmentCount));
    }

    AddressComponentRangeSpliterator<IPv6Address, IPv6AddressSegment[]> segmentsSpliterator(IPv6Address address, IPv6AddressNetwork.IPv6AddressCreator creator) {
        IPv6Address forIteration;
        Integer iterationsPrefix;
        int segmentCount = this.getSegmentCount();
        Integer prefixLength = this.getNetworkPrefixLength();
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (isAllSubnets) {
            iterationsPrefix = null;
            forIteration = address.withoutPrefixLength();
        } else {
            iterationsPrefix = prefixLength;
            forIteration = address;
        }
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return IPv6AddressSection.createItemSpliterator(forIteration, spliterator -> IPv6AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv6Address)IPv6AddressSection.createIteratedAddress((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv6Address)spliterator.getAddressItem()).getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), (isLowest, isHighest, addr) -> addr.segmentsIterator(), Address::getCount, addr -> addr.getCount().compareTo(LONG_MAX) <= 0, addr -> IPv6AddressSection.longCount(addr.getSection(), segmentCount));
    }

    public Stream<IPv6AddressSegment[]> segmentsStream() {
        return StreamSupport.stream(this.segmentsSpliterator(), false);
    }

    protected IPv6AddressSegment[] getSegmentsInternal() {
        return (IPv6AddressSegment[])super.getDivisionsInternal();
    }

    protected AddressComponentSpliterator<IPv6AddressSection> spliterator(boolean excludeZeroHosts) {
        Function<IPv6AddressSection, BigInteger> sizer;
        ToLongFunction<IPv6AddressSection> longSizer;
        AddressDivisionGroupingBase.IteratorProvider iteratorProvider;
        IPv6AddressSection forIteration;
        Integer iterationsPrefix;
        int segmentCount = this.getSegmentCount();
        Integer prefixLength = this.getNetworkPrefixLength();
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getAddressCreator();
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (isAllSubnets) {
            iterationsPrefix = null;
            forIteration = this.withoutPrefixLength();
        } else {
            iterationsPrefix = prefixLength;
            forIteration = this;
        }
        if (excludeZeroHosts && this.includesZeroHost()) {
            int prefLength = prefixLength;
            iteratorProvider = (isLowest, isHighest, section) -> section.iterator(segs -> this.isZeroHost((IPv6AddressSegment[])segs, prefLength));
            longSizer = section -> IPv6AddressSection.longCount(section, segmentCount) - section.longZeroHostCount(prefLength, segmentCount);
            sizer = section -> section.getCount().subtract(section.getZeroHostCountImpl(prefLength, segmentCount));
        } else {
            iteratorProvider = (isLowest, isHighest, section) -> section.iterator();
            longSizer = section -> IPv6AddressSection.longCount(section, segmentCount);
            sizer = AddressDivisionGroupingBase::getCount;
        }
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return IPv6AddressSection.createSeriesSpliterator(forIteration, spliterator -> IPv6AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv6AddressSection)IPv6AddressSection.createIteratedSection((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv6AddressSection)spliterator.getAddressItem()).getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), iteratorProvider, sizer, section -> section.getCount().compareTo(LONG_MAX) <= 0, longSizer);
    }

    protected Iterator<IPv6Address> iterator(IPv6Address original, AddressCreator<IPv6Address, ?, ?, IPv6AddressSegment> creator, Predicate<IPv6AddressSegment[]> excludeFunc) {
        boolean useOriginal;
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        boolean bl = useOriginal = !this.isMultiple() && (!isAllSubnets || !this.isPrefixed());
        if (!useOriginal || excludeFunc != null && excludeFunc.test(this.getSegmentsInternal())) {
            original = null;
        }
        return IPv6AddressSection.iterator(useOriginal, original, creator, useOriginal ? null : IPv6AddressSection.segmentsIterator(this.getSegmentCount(), creator, this.isMultiple() ? null : () -> this.getLower().getSegmentsInternal(), index -> this.getSegment(index).iterator(!isAllSubnets), excludeFunc), isAllSubnets ? null : this.getPrefixLength());
    }

    protected AddressComponentSpliterator<IPv6Address> spliterator(IPv6Address original, IPv6AddressNetwork.IPv6AddressCreator creator, boolean excludeZeroHosts) {
        Function<IPv6Address, BigInteger> sizer;
        ToLongFunction<IPv6Address> longSizer;
        AddressDivisionGroupingBase.IteratorProvider iteratorProvider;
        IPv6Address forIteration;
        Integer iterationsPrefix;
        int segmentCount = this.getSegmentCount();
        Integer prefixLength = this.getNetworkPrefixLength();
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (isAllSubnets) {
            iterationsPrefix = null;
            forIteration = original.withoutPrefixLength();
        } else {
            iterationsPrefix = prefixLength;
            forIteration = original;
        }
        if (excludeZeroHosts && this.includesZeroHost()) {
            int prefLength = prefixLength;
            iteratorProvider = (isLowest, isHighest, addr) -> addr.getSection().iterator((IPv6Address)addr, addr.getCreator(), s2 -> this.isZeroHost((IPv6AddressSegment[])s2, prefLength));
            longSizer = addr -> IPv6AddressSection.longCount(addr.getSection(), segmentCount) - addr.getSection().longZeroHostCount(prefLength, segmentCount);
            sizer = addr -> addr.getSection().getCount().subtract(addr.getSection().getZeroHostCountImpl(prefLength, segmentCount));
        } else {
            iteratorProvider = (isLowest, isHighest, addr) -> addr.iterator();
            longSizer = addr -> IPv6AddressSection.longCount(addr.getSection(), segmentCount);
            sizer = Address::getCount;
        }
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return IPv6AddressSection.createSeriesSpliterator(forIteration, spliterator -> IPv6AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv6Address)IPv6AddressSection.createIteratedAddress((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv6Address)spliterator.getAddressItem()).getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), iteratorProvider, sizer, addr -> addr.getCount().compareTo(LONG_MAX) <= 0, longSizer);
    }

    Iterator<IPv6Address> prefixIterator(IPv6Address original, AddressCreator<IPv6Address, ?, ?, IPv6AddressSegment> creator, boolean isBlockIterator) {
        Integer prefLength = this.getPrefixLength();
        if (prefLength == null || prefLength > this.getBitCount()) {
            return this.iterator(original, creator, null);
        }
        return this.prefixIterator(original, creator, isBlockIterator, prefLength);
    }

    Iterator<IPv6Address> prefixIterator(IPv6Address original, AddressCreator<IPv6Address, ?, ?, IPv6AddressSegment> creator, boolean isBlockIterator, int prefLength) {
        boolean useOriginal;
        if (prefLength > this.getBitCount() || prefLength < 0) {
            throw new PrefixLenException((AddressItem)original, prefLength);
        }
        boolean bl = useOriginal = isBlockIterator ? this.containsSinglePrefixBlock(prefLength) : this.getPrefixCount(prefLength).equals(BigInteger.ONE);
        if (useOriginal) {
            original = original.setPrefixLength(prefLength, false);
        }
        int networkSegIndex = IPv6AddressSection.getNetworkSegmentIndex(prefLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int hostSegIndex = IPv6AddressSection.getHostSegmentIndex(prefLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int segCount = this.getSegmentCount();
        return IPv6AddressSection.iterator(useOriginal, original, creator, useOriginal ? null : IPv6AddressSection.segmentsIterator(segCount, creator, null, index -> this.getSegment(index).iterator(), null, networkSegIndex, hostSegIndex, isBlockIterator ? index -> this.getSegment(index).prefixBlockIterator() : index -> this.getSegment(index).prefixIterator()), IPv6AddressSection.cacheBits(prefLength));
    }

    AddressComponentSpliterator<IPv6Address> prefixSpliterator(IPv6Address original, IPv6AddressNetwork.IPv6AddressCreator creator, boolean isBlockIterator) {
        Integer prefLength = this.getPrefixLength();
        if (prefLength == null || prefLength > this.getBitCount()) {
            return this.spliterator(original, creator, false);
        }
        return this.prefixSpliterator(original, creator, isBlockIterator, prefLength);
    }

    AddressComponentSpliterator<IPv6Address> prefixSpliterator(IPv6Address original, IPv6AddressNetwork.IPv6AddressCreator creator, boolean isBlockIterator, int prefixLength) {
        if (prefixLength > this.getBitCount() || prefixLength < 0) {
            throw new PrefixLenException((AddressItem)original, prefixLength);
        }
        Integer prefLength = IPv6AddressSection.cacheBits(prefixLength);
        int networkSegIndex = IPv6AddressSection.getNetworkSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int hostSegIndex = IPv6AddressSection.getHostSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        return IPv6AddressSection.createSeriesSpliterator(original.setPrefixLength(prefixLength, false), spliterator -> IPv6AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv6Address)IPv6AddressSection.createIteratedAddress((AddressSegment[])segs, (AddressCreator)creator, (Integer)prefLength), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv6Address)spliterator.getAddressItem()).getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)prefLength), isBlockIterator ? (isLowest, isHighest, addr) -> addr.prefixBlockIterator() : (!this.isSequential() ? (isLowest, isHighest, addr) -> addr.prefixIterator() : (isLowest, isHighest, addr) -> isLowest || isHighest ? addr.prefixIterator() : addr.prefixBlockIterator()), Address::getPrefixCount, addr -> addr.getPrefixCount().compareTo(LONG_MAX) <= 0, addr -> IPv6AddressSection.longPrefixCount(addr.getSection(), prefixLength));
    }

    Iterator<IPv6Address> blockIterator(IPv6Address original, AddressCreator<IPv6Address, ?, ?, IPv6AddressSegment> creator, int segmentCount) {
        if (segmentCount < 0) {
            throw new IllegalArgumentException();
        }
        if (segmentCount > this.getSegmentCount()) {
            return this.iterator(original, creator, null);
        }
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        boolean useOriginal = true;
        for (int i = 0; i < segmentCount; ++i) {
            if (!this.getSegment(i).isMultiple()) continue;
            useOriginal = false;
            break;
        }
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        int segCount = this.getSegmentCount();
        return IPv6AddressSection.iterator(useOriginal, original, creator, useOriginal ? null : IPv6AddressSection.segmentsIterator(segCount, creator, null, index -> this.getSegment(index).iterator(!isAllSubnets), null, networkSegIndex, hostSegIndex, index -> this.getSegment(index).identityIterator()), isAllSubnets ? null : this.getPrefixLength());
    }

    AddressComponentSpliterator<IPv6Address> blockSpliterator(IPv6Address original, IPv6AddressNetwork.IPv6AddressCreator creator, int segmentCount) {
        IPv6Address forIteration;
        Integer iterationsPrefix;
        Integer prefLength;
        if (segmentCount < 0) {
            throw new IllegalArgumentException();
        }
        if (segmentCount >= this.getSegmentCount()) {
            return this.spliterator(original, creator, false);
        }
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        Integer n = prefLength = isAllSubnets ? null : this.getPrefixLength();
        if (isAllSubnets) {
            iterationsPrefix = null;
            forIteration = original.withoutPrefixLength();
        } else {
            iterationsPrefix = prefLength;
            forIteration = original;
        }
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return IPv6AddressSection.createSeriesSpliterator(forIteration, spliterator -> IPv6AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv6Address)IPv6AddressSection.createIteratedAddress((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv6Address)spliterator.getAddressItem()).getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), (isLowest, isHighest, addr) -> addr.blockIterator(segmentCount), addr -> addr.getBlockCount(segmentCount), addr -> addr.getBlockCount(segmentCount).compareTo(LONG_MAX) <= 0, addr -> IPv6AddressSection.longCount(addr.getSection(), segmentCount));
    }

    protected boolean isZeroHost(IPv6AddressSegment[] segments, int prefixLength) {
        return super.isZeroHost((IPAddressSegment[])segments, prefixLength);
    }

    public static BigInteger getMaxValue(int segmentCount) {
        return MAX_VALUES_BY_SEGMENT[segmentCount];
    }

    @Override
    public IPv6AddressSection incrementBoundary(long increment) {
        if (increment <= 0L) {
            if (increment == 0L) {
                return this;
            }
            return this.getLower().increment(increment);
        }
        return this.getUpper().increment(increment);
    }

    @Override
    public IPv6AddressSection increment(long increment) {
        if (increment == 0L && !this.isMultiple()) {
            return this;
        }
        BigInteger lowerValue = this.getValue();
        BigInteger upperValue = this.getUpperValue();
        BigInteger count = this.getCount();
        BigInteger bigIncrement = BigInteger.valueOf(increment);
        IPv6AddressSection.checkOverflow(increment, bigIncrement, lowerValue, upperValue, count, () -> IPv6AddressSection.getMaxValue(this.getSegmentCount()));
        Integer prefixLength = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() ? null : this.getPrefixLength();
        IPv6AddressSection result = IPv6AddressSection.fastIncrement(this, increment, this.getAddressCreator(), this::getLower, this::getUpper, prefixLength);
        if (result != null) {
            return result;
        }
        return IPv6AddressSection.increment(this, increment, bigIncrement, this.getAddressCreator(), this::getLower, this::getUpper, prefixLength);
    }

    @Override
    protected BigInteger getCountImpl(int segCount) {
        if (!this.isMultiple()) {
            return BigInteger.ONE;
        }
        BigInteger result = IPv6AddressSection.getCountIPv6(i -> this.getSegment(i).getValueCount(), segCount);
        return result;
    }

    private static BigInteger getCountIPv6(IntUnaryOperator segmentValueCountProvider, int segCount) {
        if (segCount < 0) {
            throw new IllegalArgumentException();
        }
        return IPv6AddressSection.count(segmentValueCountProvider, segCount, 2, 0x7FFFFFFFFFFFL);
    }

    @Override
    protected BigInteger getZeroHostCountImpl(int prefixLength, int segCount) {
        if (this.includesZeroHost(prefixLength)) {
            if (this.isMultiple()) {
                int prefixedSegment = IPv6AddressSection.getNetworkSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
                BigInteger zeroHostCount = IPv6AddressSection.getCountIPv6(i -> {
                    if (i == prefixedSegment) {
                        IPv6AddressSegment seg = this.getSegment(i);
                        int shift = seg.getBitCount() - IPv6AddressSection.getPrefixedSegmentPrefixLength(this.getBitsPerSegment(), prefixLength, i);
                        int count = (seg.getUpperSegmentValue() >>> shift) - (seg.getSegmentValue() >>> shift) + 1;
                        return count;
                    }
                    return this.getSegment(i).getValueCount();
                }, prefixedSegment + 1);
                return zeroHostCount;
            }
            return BigInteger.ONE;
        }
        return BigInteger.ZERO;
    }

    @Override
    protected BigInteger getPrefixCountImpl() {
        Integer prefixLength = this.getPrefixLength();
        if (prefixLength == null || prefixLength >= this.getBitCount()) {
            return this.getCount();
        }
        return this.getPrefixCount(prefixLength);
    }

    @Override
    public BigInteger getPrefixCount(int prefixLength) {
        IPv6AddressSection.checkSubnet(this, prefixLength);
        if (this.isMultiple()) {
            int hostSegmentIndex;
            int networkSegmentIndex = IPv6AddressSection.getNetworkSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
            boolean hasPrefixedSegment = networkSegmentIndex == (hostSegmentIndex = IPv6AddressSection.getHostSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment()));
            return IPv6AddressSection.getCountIPv6(i -> {
                if (hasPrefixedSegment && i == networkSegmentIndex) {
                    return this.getSegment(i).getPrefixValueCount();
                }
                return this.getSegment(i).getValueCount();
            }, networkSegmentIndex + 1);
        }
        return BigInteger.ONE;
    }

    private AddressNetwork.AddressSegmentCreator<IPv6AddressSegment> getSegmentCreator() {
        return this.getNetwork().getAddressCreator();
    }

    private IPv6AddressNetwork.IPv6AddressCreator getAddressCreator() {
        return this.getAddressCreator(this.addressSegmentIndex);
    }

    protected IPv6AddressNetwork.IPv6AddressCreator getAddressCreator(final int startIndex) {
        boolean useCached;
        IPv6AddressNetwork.IPv6AddressCreator creator = null;
        IPv6AddressNetwork.IPv6AddressCreator defaultCreator = this.getNetwork().getAddressCreator();
        boolean bl = useCached = startIndex < 8;
        if (useCached) {
            creator = creators[startIndex];
        }
        if (creator != null && (useCached |= creator.getNetwork().equals(this.getNetwork()))) {
            return creator;
        }
        creator = new IPv6AddressNetwork.IPv6AddressCreator(this.getNetwork(), defaultCreator.cache){
            private static final long serialVersionUID = 4L;

            @Override
            protected IPv6AddressSection createSectionInternal(IPv6AddressSegment[] segments) {
                return this.getNetwork().getAddressCreator().createSectionInternal(segments, startIndex);
            }

            @Override
            protected IPv6AddressSection createPrefixedSectionInternal(IPv6AddressSegment[] segments, Integer prefix, boolean singleOnly) {
                return new IPv6AddressSection(segments, startIndex, false, prefix, singleOnly);
            }
        };
        creator.useSegmentCache = defaultCreator.useSegmentCache;
        if (useCached) {
            IPv6AddressSection.creators[startIndex] = creator;
        }
        return creator;
    }

    @Override
    public IPv6AddressSegment getDivision(int index) {
        return (IPv6AddressSegment)super.getDivision(index);
    }

    @Override
    public IPv6AddressSegment getSegment(int index) {
        return (IPv6AddressSegment)super.getSegment(index);
    }

    public void getSegments(Collection<? super IPv6AddressSegment> segs) {
        this.getSegments(0, this.getSegmentCount(), segs);
    }

    public void getSegments(int start2, int end, Collection<? super IPv6AddressSegment> segs) {
        for (int i = start2; i < end; ++i) {
            segs.add(this.getSegment(i));
        }
    }

    public boolean isEUI64() {
        return this.isEUI64(false);
    }

    public boolean isEUI64(boolean partial) {
        int segmentCount = this.getSegmentCount();
        int endIndex = this.addressSegmentIndex + segmentCount;
        if (this.addressSegmentIndex <= 5) {
            if (endIndex > 6) {
                int index3 = 5 - this.addressSegmentIndex;
                IPv6AddressSegment seg3 = this.getSegment(index3);
                IPv6AddressSegment seg4 = this.getSegment(index3 + 1);
                return seg4.matchesWithMask(65024, 65280) && seg3.matchesWithMask(255, 255);
            }
            if (partial && endIndex == 6) {
                IPv6AddressSegment seg3 = this.getSegment(5 - this.addressSegmentIndex);
                return seg3.matchesWithMask(255, 255);
            }
        } else if (partial && this.addressSegmentIndex == 6 && endIndex > 6) {
            IPv6AddressSegment seg4 = this.getSegment(6 - this.addressSegmentIndex);
            return seg4.matchesWithMask(65024, 65280);
        }
        return partial;
    }

    public MACAddressSection toEUI(boolean extended) {
        MACAddressSegment[] segs = this.toEUISegments(extended);
        if (segs == null) {
            return null;
        }
        MACAddressNetwork.MACAddressCreator creator = this.getMACNetwork().getAddressCreator();
        return IPv6AddressSection.createSectionInternal(creator, segs, Math.max(0, this.addressSegmentIndex - 4) << 1, extended);
    }

    protected static MACAddressSection createSectionInternal(MACAddressNetwork.MACAddressCreator creator, MACAddressSegment[] segments, int startIndex, boolean extended) {
        return (MACAddressSection)AddressDivisionGrouping.createSectionInternal((AddressCreator)creator, (AddressSegment[])segments, (int)startIndex, (boolean)extended);
    }

    MACAddressSegment[] toEUISegments(boolean extended) {
        int segmentIndex;
        int start2 = this.addressSegmentIndex;
        int segmentCount = this.getSegmentCount();
        if (start2 < 4) {
            start2 = 0;
            segmentIndex = 4 - start2;
        } else {
            start2 -= 4;
            segmentIndex = 0;
        }
        int originalSegmentIndex = segmentIndex;
        IPv6AddressSegment seg0 = start2 == 0 && segmentIndex < segmentCount ? this.getSegment(segmentIndex++) : null;
        IPv6AddressSegment seg1 = start2 <= 1 && segmentIndex < segmentCount ? this.getSegment(segmentIndex++) : null;
        IPv6AddressSegment seg2 = start2 <= 2 && segmentIndex < segmentCount ? this.getSegment(segmentIndex++) : null;
        IPv6AddressSegment seg3 = start2 <= 3 && segmentIndex < segmentCount ? this.getSegment(segmentIndex++) : null;
        int macSegCount = segmentIndex - originalSegmentIndex << 1;
        if (!extended) {
            macSegCount -= 2;
        }
        if (seg1 != null && !seg1.matchesWithMask(255, 255) || seg2 != null && !seg2.matchesWithMask(65024, 65280) || macSegCount == 0) {
            return null;
        }
        MACAddressNetwork.MACAddressCreator creator = this.getMACNetwork().getAddressCreator();
        MACAddressSegment ZERO_SEGMENT = creator.createSegment(0);
        AddressSegment[] newSegs = creator.createSegmentArray(macSegCount);
        int macStartIndex = 0;
        if (seg0 != null) {
            seg0.getSplitSegments(newSegs, macStartIndex, creator);
            AddressSegment macSegment0 = newSegs[0];
            int lower0 = ((MACAddressSegment)macSegment0).getSegmentValue();
            int upper0 = ((MACAddressSegment)macSegment0).getUpperSegmentValue();
            int mask2ndBit = 2;
            if (!((MACAddressSegment)macSegment0).matchesWithMask(mask2ndBit & lower0, mask2ndBit)) {
                return null;
            }
            newSegs[0] = creator.createSegment(lower0 ^= mask2ndBit, upper0 ^= mask2ndBit, null);
            macStartIndex += 2;
        }
        if (seg1 != null) {
            seg1.getSplitSegments(newSegs, macStartIndex, creator);
            if (!extended) {
                newSegs[macStartIndex + 1] = ZERO_SEGMENT;
            }
            macStartIndex += 2;
        }
        if (seg2 != null) {
            if (!extended) {
                if (seg1 != null) {
                    AddressSegment first = newSegs[macStartIndex -= 2];
                    seg2.getSplitSegments(newSegs, macStartIndex, creator);
                    newSegs[macStartIndex] = first;
                } else {
                    seg2.getSplitSegments(newSegs, macStartIndex, creator);
                    newSegs[macStartIndex] = ZERO_SEGMENT;
                }
            } else {
                seg2.getSplitSegments(newSegs, macStartIndex, creator);
            }
            macStartIndex += 2;
        }
        if (seg3 != null) {
            seg3.getSplitSegments(newSegs, macStartIndex, creator);
        }
        return newSegs;
    }

    public IPv4AddressSection getEmbeddedIPv4AddressSection(int startIndex, int endIndex) {
        IPv6AddressSegment ipv6Segment;
        if (startIndex == 6 - this.addressSegmentIndex << 1 && endIndex == this.getSegmentCount() << 1) {
            return this.getEmbeddedIPv4AddressSection();
        }
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getIPv4Network().getAddressCreator();
        AddressSegment[] segments = creator.createSegmentArray(endIndex - startIndex);
        int i = startIndex;
        int j = 0;
        int bytesPerSegment = this.getBytesPerSegment();
        if (i % bytesPerSegment == 1) {
            ipv6Segment = this.getSegment(i >> 1);
            ++i;
            ipv6Segment.getSplitSegments(segments, j - 1, creator);
            ++j;
        }
        while (i < endIndex) {
            ipv6Segment = this.getSegment(i >> 1);
            ipv6Segment.getSplitSegments(segments, j, creator);
            i += bytesPerSegment;
            j += bytesPerSegment;
        }
        return (IPv4AddressSection)IPv6AddressSection.createEmbeddedSection((IPAddressNetwork.IPAddressCreator)creator, (IPAddressSegment[])segments, (IPAddressSection)this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IPv4AddressSection getEmbeddedIPv4AddressSection() {
        if (this.embeddedIPv4Section == null) {
            IPv6AddressSection iPv6AddressSection = this;
            synchronized (iPv6AddressSection) {
                if (this.embeddedIPv4Section == null) {
                    AddressSegment[] mixed;
                    int mixedCount = this.getSegmentCount() - Math.max(6 - this.addressSegmentIndex, 0);
                    int lastIndex = this.getSegmentCount() - 1;
                    IPv4AddressNetwork.IPv4AddressCreator creator = this.getIPv4Network().getAddressCreator();
                    if (mixedCount == 0) {
                        mixed = creator.createSegmentArray(0);
                    } else if (mixedCount == 1) {
                        mixed = creator.createSegmentArray(this.getBytesPerSegment());
                        IPv6AddressSegment last = this.getSegment(lastIndex);
                        last.getSplitSegments(mixed, 0, creator);
                    } else {
                        mixed = creator.createSegmentArray(this.getBytesPerSegment() << 1);
                        IPv6AddressSegment low = this.getSegment(lastIndex);
                        IPv6AddressSegment high = this.getSegment(lastIndex - 1);
                        high.getSplitSegments(mixed, 0, creator);
                        low.getSplitSegments(mixed, this.getBytesPerSegment(), creator);
                    }
                    this.embeddedIPv4Section = (IPv4AddressSection)IPv6AddressSection.createEmbeddedSection((IPAddressNetwork.IPAddressCreator)creator, (IPAddressSegment[])mixed, (IPAddressSection)this);
                }
            }
        }
        return this.embeddedIPv4Section;
    }

    public IPv6AddressSection createNonMixedSection() {
        int mixedCount = this.getSegmentCount() - Math.max(6 - this.addressSegmentIndex, 0);
        if (mixedCount <= 0) {
            return this;
        }
        int nonMixedCount = Math.max(0, this.getSegmentCount() - mixedCount);
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getNetwork().getAddressCreator();
        AddressSegment[] nonMixed = creator.createSegmentArray(nonMixedCount);
        this.getSegments(0, nonMixedCount, nonMixed, 0);
        return creator.createEmbeddedSectionInternal(this, (IPv6AddressSegment[])nonMixed, this.addressSegmentIndex);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IPv6v4MixedAddressSection getMixedAddressSection() {
        if (this.defaultMixedAddressSection == null) {
            IPv6AddressSection iPv6AddressSection = this;
            synchronized (iPv6AddressSection) {
                if (this.defaultMixedAddressSection == null) {
                    this.defaultMixedAddressSection = new IPv6v4MixedAddressSection(this.createNonMixedSection(), this.getEmbeddedIPv4AddressSection());
                }
            }
        }
        return this.defaultMixedAddressSection;
    }

    public static IPv6AddressSection createSection(IPv6AddressNetwork.IPv6AddressCreator creator, IPv6AddressSegment[] nonMixedSection, IPv4Address mixedSection) throws IncompatibleAddressException {
        IPv4AddressSection ipv4Section = mixedSection.getSection();
        IPv6AddressSegment[] newSegs = creator.createSegmentArray(nonMixedSection.length + 2);
        newSegs[0] = nonMixedSection[0];
        newSegs[1] = nonMixedSection[1];
        newSegs[2] = nonMixedSection[2];
        newSegs[3] = nonMixedSection[3];
        newSegs[4] = nonMixedSection[4];
        newSegs[5] = nonMixedSection[5];
        newSegs[6] = ipv4Section.getSegment(0).join(creator, ipv4Section.getSegment(1));
        newSegs[7] = ipv4Section.getSegment(2).join(creator, ipv4Section.getSegment(3));
        IPv6AddressSection result = creator.createSectionInternal(newSegs);
        result.embeddedIPv4Section = ipv4Section;
        return result;
    }

    public IPv6Address getIPv6Address(IPv4Address mixedSection) {
        return mixedSection.getIPv6Address(this.getSegmentsInternal());
    }

    @Override
    public int getBitsPerSegment() {
        return 16;
    }

    @Override
    public int getBytesPerSegment() {
        return 2;
    }

    @Override
    public int getBitCount() {
        return this.getSegmentCount() << 4;
    }

    @Override
    public int getByteCount() {
        return this.getSegmentCount() << 1;
    }

    @Override
    protected byte[] getBytesImpl(boolean low) {
        byte[] bytes = new byte[this.getByteCount()];
        int segmentCount = this.getSegmentCount();
        for (int i = 0; i < segmentCount; ++i) {
            IPv6AddressSegment seg = this.getSegment(i);
            int byteIndex = i << 1;
            int val = low ? seg.getSegmentValue() : seg.getUpperSegmentValue();
            bytes[byteIndex] = (byte)(val >>> 8);
            bytes[byteIndex + 1] = (byte)val;
        }
        return bytes;
    }

    @Override
    protected byte[] getBytesInternal() {
        return super.getBytesInternal();
    }

    public boolean hasUppercaseVariations(int base, boolean lowerOnly) {
        if (base > 10) {
            int count = this.getSegmentCount();
            for (int i = 0; i < count; ++i) {
                IPv6AddressSegment seg = this.getSegment(i);
                if (!seg.hasUppercaseVariations(base, lowerOnly)) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIPv6() {
        return true;
    }

    @Override
    public IPAddress.IPVersion getIPVersion() {
        return IPAddress.IPVersion.IPV6;
    }

    public IPv6AddressSection append(IPv6AddressSection other) {
        int count = this.getSegmentCount();
        return this.replace(count, count, other, 0, other.getSegmentCount());
    }

    public IPv6AddressSection appendToNetwork(IPv6AddressSection other) {
        Integer prefixLength = this.getNetworkPrefixLength();
        if (prefixLength == null) {
            return this.append(other);
        }
        IPv6AddressSection thizz = this;
        int bitsPerSegment = this.getBitsPerSegment();
        int adjustment = prefixLength % bitsPerSegment;
        if (adjustment != 0) {
            prefixLength = prefixLength + (bitsPerSegment - adjustment);
            thizz = this.setPrefixLength(prefixLength, false);
        }
        int index = prefixLength >>> 4;
        if (other.isPrefixed() && other.getPrefixLength() == 0) {
            return this.insert(index, other);
        }
        return thizz.replace(index, index, other, 0, other.getSegmentCount(), true);
    }

    public IPv6AddressSection insert(int index, IPv6AddressSection other) {
        return this.replace(index, index, other, 0, other.getSegmentCount());
    }

    public IPv6AddressSection replace(int index, IPv6AddressSection replacement) {
        return this.replace(index, index + replacement.getSegmentCount(), replacement, 0, replacement.getSegmentCount());
    }

    public IPv6AddressSection replace(int startIndex, int endIndex, IPv6AddressSection replacement, int replacementStartIndex, int replacementEndIndex) {
        return this.replace(startIndex, endIndex, replacement, replacementStartIndex, replacementEndIndex, false);
    }

    public IPv6AddressSection replace(int startIndex, int endIndex, IPv6AddressSection replacement, int replacementStartIndex, int replacementEndIndex, boolean appendNetwork) {
        int segmentCount = this.getSegmentCount();
        int replacedCount = endIndex - startIndex;
        int replacementCount = replacementEndIndex - replacementStartIndex;
        if (replacedCount < 0 || replacementCount < 0 || startIndex < 0 || replacementStartIndex < 0 || replacementEndIndex > replacement.getSegmentCount() || endIndex > segmentCount) {
            throw new IndexOutOfBoundsException();
        }
        IPv6AddressSection thizz = this;
        if (this.addressSegmentIndex + segmentCount + replacementCount - replacedCount > 8) {
            throw new AddressValueException((AddressItem)this, replacement);
        }
        if (replacementCount == 0 && replacedCount == 0) {
            return this;
        }
        if (this.addressSegmentIndex == replacement.addressSegmentIndex && segmentCount == replacedCount) {
            return replacement;
        }
        if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            if (appendNetwork) {
                thizz = this.withoutPrefixLength();
                int replacementEndBits = replacementEndIndex << 4;
                if (!replacement.isPrefixed() || replacement.getNetworkPrefixLength() > replacementEndBits) {
                    replacement = replacement.setPrefixLength(replacementEndBits, false);
                }
            }
        } else {
            Integer prefixLength = this.getPrefixLength();
            if (appendNetwork) {
                int additionalSegs = segmentCount - endIndex;
                if (additionalSegs > 0) {
                    thizz = this.getSection(0, startIndex).withoutPrefixLength();
                    replacement = replacement.insert(replacementEndIndex, this.getSection(endIndex));
                    replacementEndIndex += additionalSegs;
                    endIndex = startIndex;
                } else {
                    thizz = this.withoutPrefixLength();
                    int replacementEndBits = replacementEndIndex << 4;
                    if (!replacement.isPrefixed() || replacement.getNetworkPrefixLength() > replacementEndBits) {
                        replacement = replacement.setPrefixLength(replacementEndBits, false);
                    }
                }
            } else if (prefixLength != null && prefixLength <= startIndex << 4) {
                replacement = replacement.setPrefixLength(0, false);
            } else if (endIndex < segmentCount) {
                int replacementEndBits = replacementEndIndex << 4;
                if (replacement.isPrefixed() && replacement.getNetworkPrefixLength() <= replacementEndBits) {
                    int thisNextIndexBits = endIndex << 4;
                    if (prefixLength == null || prefixLength > thisNextIndexBits) {
                        if (replacedCount > 0 || replacement.getPrefixLength() == 0) {
                            thizz = this.setPrefixLength(thisNextIndexBits, false);
                        } else {
                            int additionalSegs = segmentCount - endIndex;
                            thizz = this.getSection(0, startIndex);
                            replacement = replacement.insert(replacementEndIndex, this.getSection(endIndex));
                            replacementEndIndex += additionalSegs;
                        }
                    }
                }
            }
        }
        return IPv6AddressSection.replace(thizz, startIndex, endIndex, replacement, replacementStartIndex, replacementEndIndex, this.getAddressCreator(), appendNetwork, false);
    }

    @Override
    public boolean contains(AddressSection other) {
        return other instanceof IPv6AddressSection && this.addressSegmentIndex == ((IPv6AddressSection)other).addressSegmentIndex && super.contains(other);
    }

    @Override
    protected boolean containsNonZeroHostsImpl(IPAddressSection other, int otherPrefixLength) {
        if (other instanceof IPv6AddressSection) {
            IPv6AddressSection[] remaining = ((IPv6AddressSection)other).subtract(this);
            if (remaining != null) {
                for (int i = 0; i < remaining.length; ++i) {
                    if (remaining[i].isZeroHost(otherPrefixLength)) continue;
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean prefixEquals(AddressSection o) {
        if (o == this) {
            return true;
        }
        if (o instanceof IPv6AddressSection) {
            IPv6AddressSection other = (IPv6AddressSection)o;
            if (this.addressSegmentIndex >= other.addressSegmentIndex) {
                return IPv6AddressSection.prefixEquals(this, other, this.addressSegmentIndex - other.addressSegmentIndex);
            }
        }
        return false;
    }

    @Override
    public boolean prefixContains(IPAddressSection o) {
        if (o == this) {
            return true;
        }
        if (o instanceof IPv6AddressSection) {
            IPv6AddressSection other = (IPv6AddressSection)o;
            if (this.addressSegmentIndex >= other.addressSegmentIndex) {
                return IPv6AddressSection.prefixContains(this, other, this.addressSegmentIndex - other.addressSegmentIndex);
            }
        }
        return false;
    }

    @Override
    public boolean matchesWithMask(IPAddressSection other, IPAddressSection mask) {
        return other instanceof IPv6AddressSection && mask instanceof IPv6AddressSection && super.matchesWithMask(other, mask);
    }

    @Override
    protected boolean isSameGrouping(AddressDivisionGroupingBase other) {
        return other instanceof IPv6AddressSection && super.isSameGrouping(other);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof IPv6AddressSection) {
            IPv6AddressSection other = (IPv6AddressSection)o;
            return this.addressSegmentIndex == other.addressSegmentIndex && other.isSameGrouping(this);
        }
        return false;
    }

    public IPv6AddressSection intersect(IPv6AddressSection other) throws SizeMismatchException {
        return IPv6AddressSection.intersect(this, other, this.getAddressCreator(), this::getSegment, other::getSegment);
    }

    public IPv6AddressSection[] subtract(IPv6AddressSection other) throws SizeMismatchException {
        return (IPv6AddressSection[])IPv6AddressSection.subtract((IPAddressSection)this, (IPAddressSection)other, (IPAddressNetwork.IPAddressCreator)this.getAddressCreator(), this::getSegment, (R section, int prefix) -> section.setPrefixLength(prefix, false, true));
    }

    @Override
    public IPv6AddressNetwork getNetwork() {
        return Address.defaultIpv6Network();
    }

    public IPv4AddressNetwork getIPv4Network() {
        return Address.defaultIpv4Network();
    }

    public MACAddressNetwork getMACNetwork() {
        return Address.defaultMACNetwork();
    }

    @Override
    public IPv6AddressSection adjustPrefixBySegment(boolean nextSegment) {
        return this.adjustPrefixBySegment(nextSegment, true);
    }

    @Override
    public IPv6AddressSection adjustPrefixBySegment(boolean nextSegment, boolean zeroed) {
        return (IPv6AddressSection)super.adjustPrefixBySegment(nextSegment, zeroed);
    }

    @Override
    public IPv6AddressSection adjustPrefixLength(int adjustment) {
        return this.adjustPrefixLength(adjustment, true);
    }

    @Override
    public IPv6AddressSection adjustPrefixLength(int adjustment, boolean zeroed) {
        return (IPv6AddressSection)IPv6AddressSection.adjustPrefixLength(this, adjustment, zeroed, this.getAddressCreator(), (section, i) -> section.getSegment(i));
    }

    @Override
    @Deprecated
    public IPv6AddressSection applyPrefixLength(int networkPrefixLength) throws PrefixLenException {
        return this.setPrefixLength(networkPrefixLength, true, true, true);
    }

    @Override
    public IPv6AddressSection setPrefixLength(int networkPrefixLength) throws PrefixLenException {
        return this.setPrefixLength(networkPrefixLength, true, false, true);
    }

    @Override
    public IPv6AddressSection setPrefixLength(int networkPrefixLength, boolean withZeros) throws PrefixLenException {
        return this.setPrefixLength(networkPrefixLength, withZeros, false, true);
    }

    @Override
    public IPv6AddressSection setPrefixLength(int networkPrefixLength, boolean withZeros, boolean zeroHostIsBlock) throws PrefixLenException {
        return this.setPrefixLength(networkPrefixLength, withZeros, false, zeroHostIsBlock);
    }

    private IPv6AddressSection setPrefixLength(int networkPrefixLength, boolean withZeros, boolean noShrink, boolean zeroHostIsBlock) {
        return IPv6AddressSection.setPrefixLength(this, this.getAddressCreator(), networkPrefixLength, withZeros, noShrink, !zeroHostIsBlock, (section, i) -> section.getSegment(i));
    }

    @Override
    @Deprecated
    public IPv6AddressSection removePrefixLength() {
        return this.removePrefixLength(true);
    }

    @Override
    public IPv6AddressSection withoutPrefixLength() {
        return this.removePrefixLength(false);
    }

    @Override
    @Deprecated
    public IPv6AddressSection removePrefixLength(boolean zeroed) {
        return IPv6AddressSection.removePrefixLength(this, zeroed, this.getAddressCreator(), IPv6AddressSection::getSegment);
    }

    public IPv6AddressSection bitwiseOrNetwork(IPv6AddressSection mask, int networkPrefixLength) throws IncompatibleAddressException, PrefixLenException, SizeMismatchException {
        this.checkMaskSectionCount(mask);
        IPv6AddressSection networkMask = (IPv6AddressSection)this.getNetwork().getNetworkMaskSection(networkPrefixLength);
        return IPv6AddressSection.getOredSegments(this, IPv6AddressSection.cacheBits(networkPrefixLength), this.getAddressCreator(), true, this::getSegment, i -> {
            int val1 = mask.getSegment(i).getSegmentValue();
            int val2 = networkMask.getSegment(i).getSegmentValue();
            return val1 & val2;
        });
    }

    public IPv6AddressSection bitwiseOr(IPv6AddressSection mask) throws IncompatibleAddressException {
        return this.bitwiseOr(mask, false);
    }

    public IPv6AddressSection bitwiseOr(IPv6AddressSection mask, boolean retainPrefix) throws IncompatibleAddressException, SizeMismatchException {
        this.checkMaskSectionCount(mask);
        return IPv6AddressSection.getOredSegments(this, retainPrefix ? this.getPrefixLength() : null, this.getAddressCreator(), true, this::getSegment, i -> mask.getSegment(i).getSegmentValue());
    }

    @Override
    public IPv6AddressSection toZeroHost() throws IncompatibleAddressException {
        if (!this.isPrefixed()) {
            IPv6AddressNetwork network;
            AddressNetwork.PrefixConfiguration config = (network = this.getNetwork()).getPrefixConfiguration();
            IPv6Address networkMask = (IPv6Address)network.getNetworkMask(0, !config.allPrefixedAddressesAreSubnets());
            if (config.zeroHostsAreSubnets()) {
                networkMask = networkMask.getLower();
            }
            return networkMask.getSection(0, this.getSegmentCount());
        }
        if (this.includesZeroHost() && this.isSingleNetwork()) {
            return this.getLower();
        }
        return this.createZeroHost(false);
    }

    IPv6AddressSection createZeroHost(boolean boundariesOnly) {
        int prefixLength = this.getNetworkPrefixLength();
        IPv6AddressNetwork network = this.getNetwork();
        IPv6Address mask = (IPv6Address)network.getNetworkMask(prefixLength);
        return IPv6AddressSection.getSubnetSegments(this, network.getPrefixConfiguration().allPrefixedAddressesAreSubnets() ? null : IPv6AddressSection.cacheBits(prefixLength), this.getAddressCreator(), !boundariesOnly, this::getSegment, i -> mask.getSegment(i).getSegmentValue(), true);
    }

    @Override
    public IPv6AddressSection toZeroHost(int prefixLength) {
        if (this.isPrefixed() && prefixLength == this.getNetworkPrefixLength()) {
            return this.toZeroHost();
        }
        IPv6Address mask = (IPv6Address)this.getNetwork().getNetworkMask(prefixLength);
        return IPv6AddressSection.getSubnetSegments(this, null, this.getAddressCreator(), false, this::getSegment, i -> mask.getSegment(i).getSegmentValue(), true);
    }

    @Override
    public IPv6AddressSection toZeroNetwork() {
        if (!this.isPrefixed()) {
            IPv6Address hostMask = (IPv6Address)this.getNetwork().getHostMask(this.getBitCount());
            return hostMask.getSection(0, this.getSegmentCount());
        }
        return this.createZeroNetwork();
    }

    IPv6AddressSection createZeroNetwork() {
        Integer prefixLength = this.getNetworkPrefixLength();
        IPv6Address mask = (IPv6Address)this.getNetwork().getHostMask(prefixLength);
        return IPv6AddressSection.getSubnetSegments(this, prefixLength, this.getAddressCreator(), false, this::getSegment, i -> mask.getSegment(i).getSegmentValue(), true);
    }

    @Override
    public IPv6AddressSection toMaxHost() throws IncompatibleAddressException {
        if (!this.isPrefixed()) {
            IPv6Address resultNoPrefix = (IPv6Address)this.getNetwork().getHostMask(0);
            if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                return resultNoPrefix.getSection(0, this.getSegmentCount());
            }
            return resultNoPrefix.setPrefixLength(0).getSection(0, this.getSegmentCount());
        }
        if (this.includesMaxHost() && this.isSingleNetwork()) {
            return this.getUpper();
        }
        return this.createMaxHost();
    }

    public IPv6AddressSection createMaxHost() {
        Integer prefixLength = this.getNetworkPrefixLength();
        IPv6Address mask = (IPv6Address)this.getNetwork().getHostMask(prefixLength);
        return IPv6AddressSection.getOredSegments(this, this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() ? null : prefixLength, this.getAddressCreator(), false, this::getSegment, i -> mask.getSegment(i).getSegmentValue());
    }

    @Override
    public IPv6AddressSection toMaxHost(int prefixLength) {
        if (this.isPrefixed() && prefixLength == this.getNetworkPrefixLength()) {
            return this.toMaxHost();
        }
        IPv6Address mask = (IPv6Address)this.getNetwork().getHostMask(prefixLength);
        return IPv6AddressSection.getOredSegments(this, null, this.getAddressCreator(), false, this::getSegment, i -> mask.getSegment(i).getSegmentValue());
    }

    public IPv6AddressSection mask(IPv6AddressSection mask, boolean retainPrefix) throws IncompatibleAddressException, SizeMismatchException {
        this.checkMaskSectionCount(mask);
        return IPv6AddressSection.getSubnetSegments(this, retainPrefix ? this.getPrefixLength() : null, this.getAddressCreator(), true, this::getSegment, i -> mask.getSegment(i).getSegmentValue(), false);
    }

    public IPv6AddressSection mask(IPv6AddressSection mask) throws IncompatibleAddressException, SizeMismatchException {
        return this.mask(mask, false);
    }

    public IPv6AddressSection maskNetwork(IPv6AddressSection mask, int networkPrefixLength) throws IncompatibleAddressException, PrefixLenException, SizeMismatchException {
        this.checkMaskSectionCount(mask);
        if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            return IPv6AddressSection.getSubnetSegments(this, IPv6AddressSection.cacheBits(networkPrefixLength), this.getAddressCreator(), true, this::getSegment, i -> mask.getSegment(i).getSegmentValue(), false);
        }
        IPv6AddressSection hostMask = (IPv6AddressSection)this.getNetwork().getHostMaskSection(networkPrefixLength);
        return IPv6AddressSection.getSubnetSegments(this, IPv6AddressSection.cacheBits(networkPrefixLength), this.getAddressCreator(), true, this::getSegment, i -> {
            int val1 = mask.getSegment(i).getSegmentValue();
            int val2 = hostMask.getSegment(i).getSegmentValue();
            return val1 | val2;
        }, false);
    }

    protected static Integer cacheBits(int i) {
        return IPAddressSection.cacheBits(i);
    }

    @Override
    public IPv6AddressSection getHostMask() {
        return (IPv6AddressSection)super.getHostMask();
    }

    @Override
    public IPv6AddressSection getNetworkMask() {
        return (IPv6AddressSection)super.getNetworkMask();
    }

    @Override
    public IPv6AddressSection getNetworkSection() {
        if (this.isPrefixed()) {
            return this.getNetworkSection(this.getNetworkPrefixLength());
        }
        return this.getNetworkSection(this.getBitCount());
    }

    @Override
    public IPv6AddressSection getNetworkSection(int networkPrefixLength) throws PrefixLenException {
        return this.getNetworkSection(networkPrefixLength, true);
    }

    @Override
    public IPv6AddressSection getNetworkSection(int networkPrefixLength, boolean withPrefixLength) throws PrefixLenException {
        return IPv6AddressSection.getNetworkSection(this, networkPrefixLength, withPrefixLength, this.getAddressCreator(), (prefix, i) -> this.getSegment(i).toNetworkSegment((Integer)prefix, withPrefixLength));
    }

    @Override
    public IPv6AddressSection getHostSection() {
        if (this.isPrefixed()) {
            return this.getHostSection(this.getNetworkPrefixLength());
        }
        return this.getHostSection(0);
    }

    @Override
    public IPv6AddressSection getHostSection(int networkPrefixLength) throws PrefixLenException {
        int hostSegmentCount = this.getHostSegmentCount(networkPrefixLength);
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getAddressCreator(this.addressSegmentIndex + (this.getSegmentCount() - hostSegmentCount));
        return IPv6AddressSection.getHostSection(this, networkPrefixLength, hostSegmentCount, creator, (prefix, i) -> this.getSegment(i).toHostSegment((Integer)prefix));
    }

    @Override
    public IPv6AddressSection toPrefixBlock() {
        Integer prefixLength = this.getNetworkPrefixLength();
        if (prefixLength == null || this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            return this;
        }
        return this.toPrefixBlock(prefixLength);
    }

    @Override
    public IPv6AddressSection assignPrefixForSingleBlock() {
        return (IPv6AddressSection)super.assignPrefixForSingleBlock();
    }

    @Override
    public IPv6AddressSection assignMinPrefixForBlock() {
        return (IPv6AddressSection)super.assignMinPrefixForBlock();
    }

    @Override
    public IPv6AddressSection toPrefixBlock(int networkPrefixLength) throws PrefixLenException {
        return IPv6AddressSection.toPrefixBlock(this, networkPrefixLength, this.getAddressCreator(), (prefix, i) -> this.getSegment(i).toNetworkSegment((Integer)prefix, true));
    }

    @Override
    public IPv6AddressSection coverWithPrefixBlock() {
        return (IPv6AddressSection)IPv6AddressSection.coverWithPrefixBlock(this, this.getLower(), this.getUpper());
    }

    public IPv6AddressSection coverWithPrefixBlock(IPv6AddressSection other) throws AddressConversionException {
        this.checkSectionCount(other);
        return IPv6AddressSection.coverWithPrefixBlock(this, other, IPv6AddressSection::getLower, IPv6AddressSection::getUpper, Address.ADDRESS_LOW_VALUE_COMPARATOR::compare);
    }

    protected static <T extends IPAddressSegmentSeries> T coverWithPrefixBlock(T first, T other, UnaryOperator<T> getLower, UnaryOperator<T> getUpper, Comparator<T> comparator) throws AddressConversionException {
        return IPAddressSection.coverWithPrefixBlock(first, other, getLower, getUpper, comparator);
    }

    protected static IPAddressSegmentSeries coverWithPrefixBlock(IPAddressSegmentSeries original, IPAddressSegmentSeries lower, IPAddressSegmentSeries upper) {
        return IPAddressSection.coverWithPrefixBlock(original, lower, upper);
    }

    public IPv6AddressSection[] spanWithPrefixBlocks() {
        if (this.isSequential()) {
            if (this.isSinglePrefixBlock()) {
                return new IPv6AddressSection[]{this};
            }
            return this.spanWithPrefixBlocks(this);
        }
        ArrayList list = (ArrayList)this.spanWithBlocks(true);
        return list.toArray(new IPv6AddressSection[list.size()]);
    }

    public IPv6AddressSection[] spanWithPrefixBlocks(IPv6AddressSection other) throws AddressPositionException {
        if (other.addressSegmentIndex != this.addressSegmentIndex) {
            throw new AddressPositionException((AddressItem)other, other.addressSegmentIndex, this.addressSegmentIndex);
        }
        return (IPv6AddressSection[])IPv6AddressSection.getSpanningPrefixBlocks((IPAddressSection)this, (IPAddressSection)other, IPv6AddressSection::getLower, IPv6AddressSection::getUpper, Address.ADDRESS_LOW_VALUE_COMPARATOR::compare, IPv6AddressSection::assignPrefixForSingleBlock, IPv6AddressSection::withoutPrefixLength, this.getAddressCreator()::createSectionArray);
    }

    @Deprecated
    public IPv6AddressSection[] spanWithRangedSegments(IPv6AddressSection other) {
        return this.spanWithSequentialBlocks(other);
    }

    public IPv6AddressSection[] spanWithSequentialBlocks() throws AddressConversionException {
        if (this.isSequential()) {
            return new IPv6AddressSection[]{this.withoutPrefixLength()};
        }
        ArrayList list = (ArrayList)this.spanWithBlocks(false);
        return list.toArray(new IPv6AddressSection[list.size()]);
    }

    public IPv6AddressSection[] spanWithSequentialBlocks(IPv6AddressSection other) throws AddressPositionException {
        if (other.addressSegmentIndex != this.addressSegmentIndex) {
            throw new AddressPositionException((AddressItem)other, other.addressSegmentIndex, this.addressSegmentIndex);
        }
        return (IPv6AddressSection[])IPv6AddressSection.getSpanningSequentialBlocks((IPAddressSection)this, (IPAddressSection)other, IPv6AddressSection::getLower, IPv6AddressSection::getUpper, Address.ADDRESS_LOW_VALUE_COMPARATOR::compare, IPv6AddressSection::withoutPrefixLength, (IPAddressNetwork.IPAddressCreator)this.getAddressCreator());
    }

    @Deprecated
    public IPv6AddressSection[] mergePrefixBlocks(IPv6AddressSection ... sections) throws SizeMismatchException {
        return this.mergeToPrefixBlocks(sections);
    }

    public IPv6AddressSection[] mergeToPrefixBlocks(IPv6AddressSection ... sections) throws SizeMismatchException, AddressPositionException {
        this.checkSectionsMergeable(sections);
        IPAddressSegmentSeries[] converted = this.getCloned(sections);
        List<IPAddressSegmentSeries> blocks = IPv6AddressSection.getMergedPrefixBlocks(converted);
        return blocks.toArray(new IPv6AddressSection[blocks.size()]);
    }

    private IPv6AddressSection[] getCloned(IPv6AddressSection ... sections) {
        IPv6AddressSection[] converted = new IPv6AddressSection[sections.length + 1];
        System.arraycopy(sections, 0, converted, 1, sections.length);
        converted[0] = this;
        return converted;
    }

    private void checkSectionsMergeable(IPv6AddressSection[] sections) {
        for (int i = 0; i < sections.length; ++i) {
            IPv6AddressSection section = sections[i];
            if (section == null) continue;
            if (section.addressSegmentIndex != this.addressSegmentIndex) {
                throw new AddressPositionException((AddressItem)section, section.addressSegmentIndex, this.addressSegmentIndex);
            }
            if (section.getSegmentCount() == this.getSegmentCount()) continue;
            throw new SizeMismatchException(this, section);
        }
    }

    public IPv6AddressSection[] mergeToSequentialBlocks(IPv6AddressSection ... sections) throws SizeMismatchException {
        this.checkSectionsMergeable(sections);
        IPAddressSegmentSeries[] converted = this.getCloned(sections);
        List<IPAddressSegmentSeries> blocks = IPv6AddressSection.getMergedSequentialBlocks(converted, this.getAddressCreator()::createSequentialBlockSection);
        return blocks.toArray(new IPv6AddressSection[blocks.size()]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected boolean hasNoStringCache() {
        if (this.stringCache == null) {
            IPv6AddressSection iPv6AddressSection = this;
            synchronized (iPv6AddressSection) {
                if (this.stringCache == null) {
                    this.stringCache = new IPv6StringCache();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected IPv6StringCache getStringCache() {
        return this.stringCache;
    }

    @Override
    public String toCompressedString() {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().compressedString) == null) {
            this.getStringCache().compressedString = result = this.toNormalizedString(IPv6StringCache.compressedParams);
        }
        return result;
    }

    @Override
    public String toCanonicalString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.canonicalString) == null) {
            this.stringCache.canonicalString = result = this.toNormalizedString(IPv6StringCache.canonicalParams);
        }
        return result;
    }

    public String toMixedString() {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().mixedString) == null) {
            this.getStringCache().mixedString = result = this.toNormalizedString(IPv6StringCache.mixedParams);
        }
        return result;
    }

    @Override
    public String toFullString() {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().fullString) == null) {
            this.getStringCache().fullString = result = this.toNormalizedString(IPv6StringCache.fullParams);
        }
        return result;
    }

    @Override
    public String toCompressedWildcardString() {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().compressedWildcardString) == null) {
            this.getStringCache().compressedWildcardString = result = this.toNormalizedString(IPv6StringCache.wildcardCompressedParams);
        }
        return result;
    }

    @Override
    public String toPrefixLengthString() {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().networkPrefixLengthString) == null) {
            this.getStringCache().networkPrefixLengthString = result = this.toNormalizedString(IPv6StringCache.networkPrefixLengthParams);
        }
        return result;
    }

    @Override
    public String toSubnetString() {
        return this.toPrefixLengthString();
    }

    @Override
    public String toCanonicalWildcardString() {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().canonicalWildcardString) == null) {
            this.getStringCache().canonicalWildcardString = result = this.toNormalizedString(IPv6StringCache.wildcardCanonicalParams);
        }
        return result;
    }

    @Override
    public String toNormalizedWildcardString() {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().normalizedWildcardString) == null) {
            this.getStringCache().normalizedWildcardString = result = this.toNormalizedString(IPv6StringCache.wildcardNormalizedParams);
        }
        return result;
    }

    @Override
    public String toSQLWildcardString() {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().sqlWildcardString) == null) {
            this.getStringCache().sqlWildcardString = result = this.toNormalizedString(IPv6StringCache.sqlWildcardParams);
        }
        return result;
    }

    @Override
    public String toNormalizedString() {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().normalizedString) == null) {
            this.getStringCache().normalizedString = result = this.toNormalizedString(IPv6StringCache.normalizedParams);
        }
        return result;
    }

    public String toBase85String() throws IncompatibleAddressException {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().base85String) == null) {
            this.getStringCache().base85String = result = this.toBase85String(null);
        }
        return result;
    }

    String toBase85String(String zone) {
        Integer prefixLength = this.getNetworkPrefixLength();
        IPAddressLargeDivision largeDiv = this.isDualString() ? new IPAddressLargeDivision(this.getBytesInternal(), this.getUpperBytesInternal(), this.getBitCount(), 85, this.getNetwork(), prefixLength) : new IPAddressLargeDivision(this.getBytesInternal(), this.getBitCount(), 85, this.getNetwork(), prefixLength);
        IPAddressLargeDivisionGrouping part = new IPAddressLargeDivisionGrouping(new IPAddressLargeDivision[]{largeDiv}, this.getNetwork());
        return IPv6AddressSection.toNormalizedString(IPv6StringCache.base85Params, zone, part);
    }

    @Override
    protected void cacheNormalizedString(String str) {
        if (this.hasNoStringCache() || this.getStringCache().normalizedString == null) {
            this.getStringCache().normalizedString = str;
        }
    }

    @Override
    public String toReverseDNSLookupString() {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().reverseDNSString) == null) {
            IPv6StringCache stringCache = this.getStringCache();
            stringCache.reverseDNSString = result = this.toNormalizedString(IPv6StringCache.reverseDNSParams, (CharSequence)"");
        }
        return result;
    }

    @Override
    public String toSegmentedBinaryString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.segmentedBinaryString) == null) {
            this.getStringCache().segmentedBinaryString = result = this.toSegmentedBinaryString(null);
        }
        return result;
    }

    protected String toSegmentedBinaryString(CharSequence zone) {
        return this.toNormalizedString(IPv6StringCache.segmentedBinaryParams, zone);
    }

    @Override
    protected String toBinaryString(CharSequence zone) throws IncompatibleAddressException {
        if (this.isDualString()) {
            AddressDivisionGroupingBase.IPAddressStringParams<IPAddressStringDivisionSeries> params = IPv6AddressSection.toIPParams(IPAddressSection.IPStringCache.binaryParams);
            return IPv6AddressSection.toNormalizedStringRange(params, this.getLower(), this.getUpper(), zone);
        }
        return this.toNormalizedString(IPAddressSection.IPStringCache.binaryParams, zone);
    }

    @Override
    protected String toHexString(boolean with0xPrefix, CharSequence zone) throws IncompatibleAddressException {
        if (this.isDualString()) {
            AddressDivisionGroupingBase.IPAddressStringParams<IPAddressStringDivisionSeries> params = IPv6AddressSection.toIPParams(with0xPrefix ? IPAddressSection.IPStringCache.hexPrefixedParams : IPAddressSection.IPStringCache.hexParams);
            return IPv6AddressSection.toNormalizedStringRange(params, this.getLower(), this.getUpper(), zone);
        }
        return this.toNormalizedString(with0xPrefix ? IPAddressSection.IPStringCache.hexPrefixedParams : IPAddressSection.IPStringCache.hexParams, zone);
    }

    @Override
    protected String toOctalString(boolean with0Prefix, CharSequence zone) throws IncompatibleAddressException {
        if (zone == null) {
            return super.toOctalString(with0Prefix, null);
        }
        AddressDivisionGroupingBase.IPAddressStringParams<IPAddressStringDivisionSeries> params = IPv6AddressSection.toIPParams(with0Prefix ? IPAddressSection.IPStringCache.octalPrefixedParams : IPAddressSection.IPStringCache.octalParams);
        if (this.isDualString()) {
            IPv6AddressSection lower = this.getLower();
            IPv6AddressSection upper = this.getUpper();
            IPAddressDivision[] lowerDivs = (IPAddressBitsDivision[])lower.createNewDivisions(3, IPAddressBitsDivision::new, IPAddressBitsDivision[]::new);
            IPAddressDivisionGrouping lowerPart = new IPAddressDivisionGrouping(lowerDivs, this.getNetwork());
            IPAddressDivision[] upperDivs = (IPAddressBitsDivision[])upper.createNewDivisions(3, IPAddressBitsDivision::new, IPAddressBitsDivision[]::new);
            IPAddressDivisionGrouping upperPart = new IPAddressDivisionGrouping(upperDivs, this.getNetwork());
            return IPv6AddressSection.toNormalizedStringRange(params, lowerPart, upperPart, zone);
        }
        IPAddressDivision[] divs = (IPAddressBitsDivision[])this.createNewPrefixedDivisions(3, null, null, IPAddressBitsDivision::new, IPAddressBitsDivision[]::new);
        IPAddressDivisionGrouping part = new IPAddressDivisionGrouping(divs, this.getNetwork());
        return params.toString((IPAddressStringDivisionSeries)part, zone);
    }

    @Override
    public String toNormalizedString(IPAddressSection.IPStringOptions options) {
        if (options instanceof IPv6StringOptions) {
            return this.toNormalizedString((IPv6StringOptions)options);
        }
        return super.toNormalizedString(options);
    }

    public String toNormalizedString(IPv6StringOptions options) {
        return this.toNormalizedString(options, (CharSequence)null);
    }

    private String toNormalizedMixedString(IPv6v4MixedParams mixedParams, CharSequence zone) {
        IPv6v4MixedAddressSection mixed = this.getMixedAddressSection();
        String result = mixedParams.toString(mixed, zone);
        return result;
    }

    String toNormalizedString(IPAddressSection.IPStringOptions options, CharSequence zone) {
        if (zone == null) {
            return this.toNormalizedString(options);
        }
        if (options instanceof IPv6StringOptions) {
            return this.toNormalizedString((IPv6StringOptions)options, zone);
        }
        AddressDivisionGroupingBase.IPAddressStringParams<IPAddressStringDivisionSeries> params = IPv6AddressSection.toIPParams(options);
        return params.toString((IPAddressStringDivisionSeries)this, zone);
    }

    public String toNormalizedString(IPv6StringOptions options, CharSequence zone) {
        IPv6StringParams stringParams;
        if (options.isCacheable()) {
            IPAddressStringWriter cachedParams = (IPAddressStringWriter)IPv6AddressSection.getCachedParams(options);
            if (cachedParams == null) {
                stringParams = options.from(this);
                if (options.makeMixed()) {
                    IPv6v4MixedParams mixedParams = new IPv6v4MixedParams(stringParams, options.ipv4Opts);
                    IPv6AddressSection.setCachedParams(options, mixedParams);
                    return this.toNormalizedMixedString(mixedParams, zone);
                }
                IPv6AddressSection.setCachedParams(options, stringParams);
            } else {
                if (cachedParams instanceof IPv6v4MixedParams) {
                    return this.toNormalizedMixedString((IPv6v4MixedParams)cachedParams, zone);
                }
                stringParams = (IPv6StringParams)cachedParams;
            }
        } else {
            stringParams = options.from(this);
            if (options.makeMixed() && stringParams.nextUncompressedIndex <= 6 - this.addressSegmentIndex) {
                return this.toNormalizedMixedString(new IPv6v4MixedParams(stringParams, options.ipv4Opts), zone);
            }
        }
        return stringParams.toString(this, zone);
    }

    public static String toNormalizedString(IPAddressSection.IPStringOptions options, CharSequence zone, IPAddressStringDivisionSeries part) {
        AddressDivisionGroupingBase.IPAddressStringParams<IPAddressStringDivisionSeries> params = IPv6AddressSection.toIPParams(options);
        String result = params.toString(part, zone);
        return result;
    }

    @Override
    public IPAddressPartStringCollection toStandardStringCollection() {
        return this.toStringCollection(IPv6StringBuilderOptions.STANDARD_OPTS);
    }

    @Override
    public IPAddressPartStringCollection toAllStringCollection() {
        return this.toStringCollection(IPv6StringBuilderOptions.ALL_OPTS);
    }

    @Override
    public IPAddressPartStringCollection toDatabaseSearchStringCollection() {
        return this.toStringCollection(IPv6StringBuilderOptions.DATABASE_SEARCH_OPTS);
    }

    @Override
    public IPAddressPartStringCollection toStringCollection(IPAddressSection.IPStringBuilderOptions options) {
        return this.toStringCollection(IPv6StringBuilderOptions.from(options));
    }

    public IPAddressPartStringCollection toStringCollection(IPv6StringBuilderOptions opts) {
        return this.toStringCollection(opts, null);
    }

    IPv6StringCollection toStringCollection(IPv6StringBuilderOptions opts, CharSequence zone) {
        IPv6StringCollection collection = new IPv6StringCollection();
        int mixedCount = this.getSegmentCount() - Math.max(6 - this.addressSegmentIndex, 0);
        if (mixedCount > 0 && opts.includes(2)) {
            IPv6v4MixedAddressSection mixed = this.getMixedAddressSection();
            IPv6StringCollection.IPv6v4MixedStringBuilder mixedBuilder = new IPv6StringCollection.IPv6v4MixedStringBuilder(mixed, opts, zone);
            IPv6v4MixedStringCollection mixedCollection = (IPv6v4MixedStringCollection)mixedBuilder.getVariations();
            collection.add((IPAddressPartStringSubCollection<?, ?, ?>)mixedCollection);
        }
        if (opts.includes(1)) {
            IPv6StringCollection.IPv6StringBuilder ipv6Builder = new IPv6StringCollection.IPv6StringBuilder(this, opts, zone);
            IPv6AddressSectionStringCollection ipv6Collection = (IPv6AddressSectionStringCollection)ipv6Builder.getVariations();
            collection.add((IPAddressPartStringSubCollection<?, ?, ?>)ipv6Collection);
        }
        return collection;
    }

    @Override
    public IPAddressStringDivisionSeries[] getParts(IPAddressSection.IPStringBuilderOptions opts) {
        return this.getParts(IPv6StringBuilderOptions.from(opts));
    }

    public IPAddressStringDivisionSeries[] getParts(IPv6StringBuilderOptions opts) {
        if (opts.includes(2)) {
            if (opts.includes(1)) {
                return new IPAddressStringDivisionSeries[]{this, this.getMixedAddressSection()};
            }
            return new IPAddressStringDivisionSeries[]{this.getMixedAddressSection()};
        }
        return super.getParts(opts);
    }

    @Override
    public IPAddressDivisionGrouping.RangeList getZeroSegments() {
        if (this.zeroSegments == null) {
            this.zeroSegments = super.getZeroSegments();
        }
        return this.zeroSegments;
    }

    @Override
    public IPAddressDivisionGrouping.RangeList getZeroRangeSegments() {
        if (this.zeroRanges == null) {
            this.zeroRanges = super.getZeroRangeSegments();
        }
        return this.zeroRanges;
    }

    @Override
    public boolean isZero() {
        IPAddressDivisionGrouping.RangeList ranges = this.getZeroSegments();
        return ranges.size() == 1 && ranges.getRange((int)0).length == this.getSegmentCount();
    }

    private int[] getCompressIndexAndCount(CompressOptions options) {
        return this.getCompressIndexAndCount(options, false);
    }

    private int[] getCompressIndexAndCount(CompressOptions options, boolean createMixed) {
        if (options != null) {
            CompressOptions.CompressionChoiceOptions rangeSelection = options.rangeSelection;
            IPAddressDivisionGrouping.RangeList compressibleSegs = rangeSelection.compressHost() ? this.getZeroRangeSegments() : this.getZeroSegments();
            int maxIndex = -1;
            int maxCount = 0;
            int segmentCount = this.getSegmentCount();
            boolean compressMixed = createMixed && options.compressMixedOptions.compressMixed(this);
            boolean preferHost = rangeSelection == CompressOptions.CompressionChoiceOptions.HOST_PREFERRED;
            boolean preferMixed = createMixed && rangeSelection == CompressOptions.CompressionChoiceOptions.MIXED_PREFERRED;
            for (int i = compressibleSegs.size() - 1; i >= 0; --i) {
                IPAddressDivisionGrouping.Range range = compressibleSegs.getRange(i);
                int index = range.index;
                int count = range.length;
                if (createMixed) {
                    int mixedIndex = 6 - this.addressSegmentIndex;
                    if (!compressMixed || index > mixedIndex || index + count < segmentCount) {
                        count = Math.min(count, mixedIndex - index);
                    }
                }
                if (count > 0 && count >= maxCount && (options.compressSingle || count > 1)) {
                    maxIndex = index;
                    maxCount = count;
                }
                if (preferHost && this.isPrefixed() && (index + count) * this.getBitsPerSegment() > this.getNetworkPrefixLength() || preferMixed && index + count >= segmentCount) break;
            }
            if (maxIndex >= 0) {
                return new int[]{maxIndex, maxCount};
            }
        }
        return null;
    }

    public static class IPv6StringBuilderOptions
    extends IPAddressSection.IPStringBuilderOptions {
        public static final int MIXED = 2;
        public static final int UPPERCASE = 4;
        public static final int COMPRESSION_CANONICAL = 256;
        public static final int COMPRESSION_SINGLE = 768;
        public static final int COMPRESSION_LARGEST = 1792;
        public static final int COMPRESSION_ALL_FULL = 3840;
        public static final int COMPRESSION_ALL_PARTIAL = 7936;
        public static final int IPV4_CONVERSIONS = 65536;
        public final IPv4AddressSection.IPv4StringBuilderOptions mixedOptions;
        public final IPv4AddressSection.IPv4StringBuilderOptions ipv4ConverterOptions;
        public final IPv4Address.IPv4AddressConverter converter;
        public static final IPv6StringBuilderOptions STANDARD_OPTS = new IPv6StringBuilderOptions(3861, new IPv4AddressSection.IPv4StringBuilderOptions(17));
        public static final IPv6StringBuilderOptions ALL_OPTS = new IPv6StringBuilderOptions(69431, new IPv4AddressSection.IPv4StringBuilderOptions(49), null, new IPv4AddressSection.IPv4StringBuilderOptions(831));
        public static final IPv6StringBuilderOptions DATABASE_SEARCH_OPTS = new IPv6StringBuilderOptions(1793);

        public IPv6StringBuilderOptions(int options) {
            this(options, null, null, null);
        }

        public IPv6StringBuilderOptions(int options, IPv4AddressSection.IPv4StringBuilderOptions mixedOptions) {
            this(options, mixedOptions, null, null);
        }

        public IPv6StringBuilderOptions(int options, IPv4AddressSection.IPv4StringBuilderOptions mixedOptions, IPv4Address.IPv4AddressConverter ipv4AddressConverter, IPv4AddressSection.IPv4StringBuilderOptions ipv4ConverterOptions) {
            super(options | (mixedOptions == null ? 0 : 2) | (ipv4ConverterOptions == null ? 0 : 65536));
            if (this.includes(2) && mixedOptions == null) {
                mixedOptions = new IPv4AddressSection.IPv4StringBuilderOptions();
            }
            this.mixedOptions = mixedOptions;
            if (this.includes(65536)) {
                if (ipv4ConverterOptions == null) {
                    ipv4ConverterOptions = new IPv4AddressSection.IPv4StringBuilderOptions();
                }
                if (ipv4AddressConverter == null) {
                    ipv4AddressConverter = IPAddress.DEFAULT_ADDRESS_CONVERTER;
                }
            }
            this.ipv4ConverterOptions = ipv4ConverterOptions;
            this.converter = ipv4AddressConverter;
        }

        public static IPv6StringBuilderOptions from(IPAddressSection.IPStringBuilderOptions opts) {
            if (opts instanceof IPv6StringBuilderOptions) {
                return (IPv6StringBuilderOptions)opts;
            }
            return new IPv6StringBuilderOptions(opts.options & 0xFFFEE0F9);
        }
    }

    private static class IPv6AddressSectionString
    extends IPAddressPartConfiguredString<IPv6AddressSection, IPv6StringParams> {
        private final CharSequence zone;

        IPv6AddressSectionString(IPv6AddressSection addr, IPv6StringParams stringParams, CharSequence zone) {
            super(addr, stringParams);
            this.zone = zone;
        }

        public IPv6StringMatcher getNetworkStringMatcher(boolean isEntireAddress, IPAddressSQLTranslator translator) {
            return new IPv6StringMatcher(this, translator);
        }

        public boolean endIsCompressed() {
            return ((IPv6StringParams)this.stringParams).endIsCompressed(this.addr);
        }

        public boolean isCompressed() {
            return ((IPv6StringParams)this.stringParams).isCompressed(this.addr);
        }

        @Override
        public String getString() {
            if (this.string == null) {
                this.string = ((IPv6StringParams)this.stringParams).toString((IPv6AddressSection)this.addr, this.zone);
            }
            return this.string;
        }
    }

    static class IPv6StringCollection
    extends IPAddressPartStringCollection {
        IPv6StringCollection() {
        }

        protected void add(IPAddressPartStringSubCollection<?, ?, ?> collection) {
            super.add(collection);
        }

        @Override
        protected void addAll(IPAddressPartStringCollection collections) {
            super.addAll(collections);
        }

        static class IPv6v4MixedStringBuilder
        extends IPAddressPartStringCollection.AddressPartStringBuilder<IPv6v4MixedAddressSection, IPv6v4MixedParams, IPAddressPartConfiguredString<IPv6v4MixedAddressSection, IPv6v4MixedParams>, IPv6v4MixedStringCollection, IPv6StringBuilderOptions> {
            private final CharSequence zone;

            IPv6v4MixedStringBuilder(IPv6v4MixedAddressSection address, IPv6StringBuilderOptions opts, CharSequence zone) {
                super(address, opts, new IPv6v4MixedStringCollection(address, zone));
                this.zone = zone;
            }

            @Override
            protected void addAllVariations() {
                IPv6StringBuilder ipv6Builder = new IPv6StringBuilder(((IPv6v4MixedAddressSection)this.addressSection).ipv6Section, (IPv6StringBuilderOptions)this.options, this.zone);
                IPv6AddressSectionStringCollection ipv6Variations = (IPv6AddressSectionStringCollection)ipv6Builder.getVariations();
                IPAddressPartStringCollection ipv4Collection = ((IPv6v4MixedAddressSection)this.addressSection).ipv4Section.toStringCollection(((IPv6StringBuilderOptions)this.options).mixedOptions);
                for (IPv6AddressSectionString ipv6Variation : ipv6Variations) {
                    for (IPAddressPartConfiguredString<?, ?> ipv4Variation : ipv4Collection) {
                        IPv6v4MixedParams mixed = new IPv6v4MixedParams(ipv6Variation, ipv4Variation);
                        this.addStringParam(mixed);
                    }
                }
            }
        }

        static class IPv6StringBuilder
        extends IPAddressPartStringCollection.AddressPartStringBuilder<IPv6AddressSection, IPv6StringParams, IPv6AddressSectionString, IPv6AddressSectionStringCollection, IPv6StringBuilderOptions> {
            IPv6StringBuilder(IPv6AddressSection address, IPv6StringBuilderOptions opts, CharSequence zone) {
                super(address, opts, new IPv6AddressSectionStringCollection(address, zone));
            }

            private void addUppercaseVariations(ArrayList<IPv6StringParams> allParams, int base) {
                boolean lowerOnly = true;
                if (((IPv6StringBuilderOptions)this.options).includes(4) && ((IPv6AddressSection)this.addressSection).hasUppercaseVariations(base, lowerOnly)) {
                    int len = allParams.size();
                    for (int j = 0; j < len; ++j) {
                        IPv6StringParams clone = allParams.get(j);
                        clone = clone.clone();
                        clone.setUppercase(true);
                        allParams.add(clone);
                    }
                }
            }

            private void addAllExpansions(int firstCompressedIndex, int count, int segmentCount) {
                boolean isExpandable;
                IPv6StringParams stringParams = new IPv6StringParams(firstCompressedIndex, count);
                int base = stringParams.getRadix();
                ArrayList<IPv6StringParams> allParams = new ArrayList<IPv6StringParams>();
                allParams.add(stringParams);
                int radix = 16;
                if (((IPv6StringBuilderOptions)this.options).includes(48)) {
                    int[] expandables = this.getExpandableSegments(radix);
                    int nextUncompressedIndex = firstCompressedIndex + count;
                    int ipv6SegmentEnd = ((IPv6AddressSection)this.addressSection).getSegmentCount();
                    block0: for (int i = 0; i < ipv6SegmentEnd; ++i) {
                        if (i >= firstCompressedIndex && i < nextUncompressedIndex) continue;
                        int len = allParams.size();
                        for (int expansionLength = expandables[i]; expansionLength > 0; --expansionLength) {
                            for (int j = 0; j < len; ++j) {
                                IPv6StringParams clone = allParams.get(j);
                                clone = clone.clone();
                                clone.expandSegment(i, expansionLength, ((IPv6AddressSection)this.addressSection).getSegmentCount());
                                allParams.add(clone);
                            }
                            if (!((IPv6StringBuilderOptions)this.options).includes(112)) continue block0;
                        }
                    }
                } else if (((IPv6StringBuilderOptions)this.options).includes(16) && (isExpandable = this.isExpandableOutsideRange(radix, firstCompressedIndex, count))) {
                    int len = allParams.size();
                    for (int j = 0; j < len; ++j) {
                        IPv6StringParams clone = allParams.get(j);
                        clone = clone.clone();
                        clone.expandSegments(true);
                        allParams.add(clone);
                    }
                }
                this.addUppercaseVariations(allParams, base);
                for (int i = 0; i < allParams.size(); ++i) {
                    IPv6StringParams param2 = allParams.get(i);
                    this.addStringParam(param2);
                }
            }

            private void addAllCompressedStrings(int zeroStartIndex, int count, boolean partial, int segmentCount) {
                int end = zeroStartIndex + count;
                if (partial) {
                    for (int i = zeroStartIndex; i < end; ++i) {
                        for (int j = i + 1; j <= end; ++j) {
                            this.addAllExpansions(i, j - i, segmentCount);
                        }
                    }
                } else {
                    int len = end - zeroStartIndex;
                    if (len > 0) {
                        this.addAllExpansions(zeroStartIndex, len, segmentCount);
                    }
                }
            }

            @Override
            protected void addAllVariations() {
                CompressOptions opts;
                int[] indexes;
                int segmentCount = ((IPv6AddressSection)this.addressSection).getSegmentCount();
                this.addAllExpansions(-1, 0, segmentCount);
                if (((IPv6StringBuilderOptions)this.options).includes(3840)) {
                    IPAddressDivisionGrouping.RangeList zeroSegs = ((IPv6AddressSection)this.addressSection).getZeroSegments();
                    for (int i = 0; i < zeroSegs.size(); ++i) {
                        IPAddressDivisionGrouping.Range range = zeroSegs.getRange(i);
                        this.addAllCompressedStrings(range.index, range.length, ((IPv6StringBuilderOptions)this.options).includes(7936), segmentCount);
                    }
                } else if (((IPv6StringBuilderOptions)this.options).includes(256) && (indexes = ((IPv6AddressSection)this.addressSection).getCompressIndexAndCount(opts = new CompressOptions(((IPv6StringBuilderOptions)this.options).includes(768), CompressOptions.CompressionChoiceOptions.ZEROS))) != null) {
                    if (((IPv6StringBuilderOptions)this.options).includes(1792)) {
                        int maxCount = indexes[1];
                        IPAddressDivisionGrouping.RangeList zeroSegs = ((IPv6AddressSection)this.addressSection).getZeroSegments();
                        for (int i = 0; i < zeroSegs.size(); ++i) {
                            IPAddressDivisionGrouping.Range range = zeroSegs.getRange(i);
                            int count = range.length;
                            if (count != maxCount) continue;
                            this.addAllCompressedStrings(range.index, count, ((IPv6StringBuilderOptions)this.options).includes(7936), segmentCount);
                        }
                    } else {
                        int maxIndex = indexes[0];
                        int maxCount = indexes[1];
                        this.addAllCompressedStrings(maxIndex, maxCount, false, segmentCount);
                    }
                }
            }
        }
    }

    static class IPv6v4MixedStringCollection
    extends IPAddressPartStringSubCollection<IPv6v4MixedAddressSection, IPv6v4MixedParams, IPAddressPartConfiguredString<IPv6v4MixedAddressSection, IPv6v4MixedParams>> {
        private final CharSequence zone;

        public IPv6v4MixedStringCollection(IPv6v4MixedAddressSection part, CharSequence zone) {
            super(part);
            this.zone = zone;
        }

        @Override
        public Iterator<IPAddressPartConfiguredString<IPv6v4MixedAddressSection, IPv6v4MixedParams>> iterator() {
            return new IPAddressPartStringSubCollection.IPAddressConfigurableStringIterator(){

                @Override
                public IPAddressPartConfiguredString<IPv6v4MixedAddressSection, IPv6v4MixedParams> next() {
                    return new IPAddressPartConfiguredString<IPv6v4MixedAddressSection, IPv6v4MixedParams>((IPv6v4MixedAddressSection)part, (IPv6v4MixedParams)this.iterator.next()){

                        @Override
                        public String getString() {
                            if (this.string == null) {
                                this.string = ((IPv6v4MixedParams)this.stringParams).toString((IPv6v4MixedAddressSection)this.addr, zone);
                            }
                            return this.string;
                        }
                    };
                }
            };
        }
    }

    static class IPv6AddressSectionStringCollection
    extends IPAddressPartStringSubCollection<IPv6AddressSection, IPv6StringParams, IPv6AddressSectionString> {
        private final CharSequence zone;

        IPv6AddressSectionStringCollection(IPv6AddressSection addr, CharSequence zone) {
            super(addr);
            this.zone = zone;
        }

        @Override
        public Iterator<IPv6AddressSectionString> iterator() {
            return new IPAddressPartStringSubCollection.IPAddressConfigurableStringIterator(){

                @Override
                public IPv6AddressSectionString next() {
                    return new IPv6AddressSectionString((IPv6AddressSection)part, (IPv6StringParams)this.iterator.next(), zone);
                }
            };
        }
    }

    static class IPv6v4MixedParams
    implements IPAddressStringWriter<IPv6v4MixedAddressSection>,
    Cloneable {
        private AddressDivisionGroupingBase.IPAddressStringParams<IPAddressStringDivisionSeries> ipv4Params;
        private IPv6StringParams ipv6Params;

        IPv6v4MixedParams(IPv6AddressSectionString ipv6Variation, IPAddressPartConfiguredString<?, ?> ipv4Variation) {
            this.ipv4Params = (AddressDivisionGroupingBase.IPAddressStringParams)ipv4Variation.stringParams;
            this.ipv6Params = (IPv6StringParams)ipv6Variation.stringParams;
        }

        IPv6v4MixedParams(IPv6StringParams ipv6Params, IPAddressSection.IPStringOptions ipv4Opts) {
            this.ipv4Params = IPv6AddressSection.toIPParams(ipv4Opts);
            this.ipv6Params = ipv6Params;
        }

        @Override
        public char getTrailingSegmentSeparator() {
            return this.ipv4Params.getTrailingSegmentSeparator();
        }

        @Override
        public int getTrailingSeparatorCount(IPv6v4MixedAddressSection addr) {
            return this.ipv4Params.getTrailingSeparatorCount(addr.ipv4Section);
        }

        public int getStringLength(IPv6v4MixedAddressSection addr, CharSequence zone) {
            int ipv6length = this.ipv6Params.getSegmentsStringLength(addr.ipv6Section);
            int ipv4length = this.ipv4Params.getSegmentsStringLength(addr.ipv4Section);
            int length = ipv6length + ipv4length;
            if (this.ipv6Params.nextUncompressedIndex < addr.ipv6Section.getSegmentCount()) {
                ++length;
            }
            length += this.getPrefixStringLength(addr);
            length += this.ipv6Params.getZoneLength(zone);
            length += this.ipv6Params.getAddressSuffixLength();
            return length += this.ipv6Params.getAddressLabelLength();
        }

        @Override
        public String toString(IPv6v4MixedAddressSection addr) {
            return this.toString(addr, (CharSequence)null);
        }

        @Override
        public String toString(IPv6v4MixedAddressSection addr, CharSequence zone) {
            int length = this.getStringLength(addr, zone);
            StringBuilder builder = new StringBuilder(length);
            this.append(builder, addr, zone);
            AddressDivisionGroupingBase.AddressStringParams.checkLengths(length, builder);
            return builder.toString();
        }

        @Override
        public int getDivisionStringLength(AddressStringDivision seg) {
            return this.ipv6Params.getDivisionStringLength(seg);
        }

        @Override
        public StringBuilder appendDivision(StringBuilder builder, AddressStringDivision seg) {
            return this.ipv6Params.appendDivision(builder, seg);
        }

        public StringBuilder append(StringBuilder builder, IPv6v4MixedAddressSection addr, CharSequence zone) {
            this.ipv6Params.appendLabel(builder);
            this.ipv6Params.appendSegments(builder, addr.ipv6Section);
            if (this.ipv6Params.nextUncompressedIndex < addr.ipv6Section.getSegmentCount()) {
                builder.append(this.ipv6Params.getTrailingSegmentSeparator());
            }
            this.ipv4Params.appendSegments(builder, addr.ipv4Section);
            this.ipv6Params.appendZone(builder, zone);
            this.ipv6Params.appendSuffix(builder);
            this.appendPrefixIndicator(builder, addr);
            return builder;
        }

        protected int getPrefixStringLength(IPv6v4MixedAddressSection addr) {
            if (this.requiresPrefixIndicator(addr.ipv6Section) || this.requiresPrefixIndicator(addr.ipv4Section)) {
                return AddressDivisionGroupingBase.IPAddressStringParams.getPrefixIndicatorStringLength(addr);
            }
            return 0;
        }

        public void appendPrefixIndicator(StringBuilder builder, IPv6v4MixedAddressSection addr) {
            if (this.requiresPrefixIndicator(addr.ipv6Section) || this.requiresPrefixIndicator(addr.ipv4Section)) {
                this.ipv6Params.appendPrefixIndicator(builder, addr);
            }
        }

        protected boolean requiresPrefixIndicator(IPv4AddressSection ipv4Section) {
            return ipv4Section.isPrefixed() && !this.ipv4Params.preferWildcards();
        }

        protected boolean requiresPrefixIndicator(IPv6AddressSection ipv6Section) {
            return ipv6Section.isPrefixed() && (!this.ipv6Params.preferWildcards() || this.ipv6Params.hostCompressed);
        }

        public IPv6v4MixedParams clone() {
            try {
                IPv6v4MixedParams params = (IPv6v4MixedParams)super.clone();
                params.ipv6Params = this.ipv6Params.clone();
                params.ipv4Params = this.ipv4Params.clone();
                return params;
            }
            catch (CloneNotSupportedException e) {
                return null;
            }
        }
    }

    public static class IPv6v4MixedAddressSection
    extends IPAddressDivisionGrouping {
        private static final long serialVersionUID = 4L;
        private final IPv6AddressSection ipv6Section;
        private final IPv4AddressSection ipv4Section;
        private String string;

        private IPv6v4MixedAddressSection(IPv6AddressSection ipv6Section, IPv4AddressSection ipv4Section) {
            super(IPv6v4MixedAddressSection.createSegments(ipv6Section, ipv4Section), ipv6Section.getNetwork());
            if (ipv6Section.isPrefixed()) {
                if (!ipv4Section.isPrefixed() || ipv4Section.getNetworkPrefixLength() != 0) {
                    throw new InconsistentPrefixException((AddressItem)ipv6Section, (AddressItem)ipv4Section, ipv4Section.getNetworkPrefixLength());
                }
                this.cachedPrefixLength = ipv6Section.getNetworkPrefixLength();
            } else {
                this.cachedPrefixLength = ipv4Section.isPrefixed() ? IPv6v4MixedAddressSection.cacheBits(ipv4Section.getNetworkPrefixLength() + ipv6Section.getBitCount()) : NO_PREFIX_LENGTH;
            }
            this.ipv4Section = ipv4Section;
            this.ipv6Section = ipv6Section;
        }

        private static IPAddressDivision[] createSegments(IPv6AddressSection ipv6Section, IPv4AddressSection ipv4Section) {
            int ipv4Len;
            int ipv6Len = ipv6Section.getSegmentCount();
            if (ipv6Len + ((ipv4Len = ipv4Section.getSegmentCount()) + 1 >> 1) + ipv6Section.addressSegmentIndex > 8) {
                throw new AddressValueException((AddressItem)ipv6Section, ipv4Section);
            }
            AddressSegment[] allSegs = new IPAddressSegment[ipv6Len + ipv4Len];
            ipv6Section.getSegments(0, ipv6Len, allSegs, 0);
            ipv4Section.getSegments(0, ipv4Len, allSegs, ipv6Len);
            return allSegs;
        }

        @Override
        public int getByteCount() {
            return this.ipv6Section.getByteCount() + this.ipv4Section.getByteCount();
        }

        @Override
        public int getBitCount() {
            return this.ipv6Section.getBitCount() + this.ipv4Section.getBitCount();
        }

        @Override
        public boolean isPrefixBlock() {
            Integer networkPrefixLength = this.getNetworkPrefixLength();
            if (networkPrefixLength == null) {
                return false;
            }
            if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                return true;
            }
            if (this.ipv6Section.isPrefixed()) {
                return this.ipv6Section.isPrefixBlock() && this.ipv4Section.isFullRange();
            }
            return this.ipv4Section.isPrefixBlock();
        }

        @Override
        public String toString() {
            if (this.string == null) {
                IPv6StringOptions mixedParams = IPv6StringCache.mixedParams;
                IPv6StringParams ipv6Params = mixedParams.from(this.ipv6Section);
                IPAddressSection.IPStringOptions ipv4Opts = mixedParams.ipv4Opts;
                IPv6v4MixedParams parms = new IPv6v4MixedParams(ipv6Params, ipv4Opts);
                this.string = parms.toString(this);
            }
            return this.string;
        }

        @Override
        protected boolean isSameGrouping(AddressDivisionGroupingBase o) {
            if (o instanceof IPv6v4MixedAddressSection) {
                IPv6v4MixedAddressSection other = (IPv6v4MixedAddressSection)o;
                return this.ipv6Section.equals(other.ipv6Section) && this.ipv4Section.equals(other.ipv4Section);
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof IPv6v4MixedAddressSection) {
                IPv6v4MixedAddressSection other = (IPv6v4MixedAddressSection)o;
                return this.ipv6Section.equals(other.ipv6Section) && this.ipv4Section.equals(other.ipv4Section);
            }
            return false;
        }
    }

    static class EmbeddedIPv6AddressSection
    extends IPv6AddressSection {
        private static final long serialVersionUID = 4L;
        private final IPAddressSection encompassingSection;

        EmbeddedIPv6AddressSection(IPAddressSection encompassingSection, IPv6AddressSegment[] subSegments, int startIndex) {
            super(subSegments, startIndex, false);
            this.encompassingSection = encompassingSection;
        }

        @Override
        public boolean isPrefixBlock() {
            return this.encompassingSection.isPrefixBlock();
        }
    }

    static class IPv6StringParams
    extends AddressDivisionGroupingBase.IPAddressStringParams<IPv6AddressSection> {
        int firstCompressedSegmentIndex;
        int nextUncompressedIndex;
        boolean hostCompressed;

        IPv6StringParams() {
            this(-1, 0);
        }

        IPv6StringParams(int firstCompressedSegmentIndex, int compressedCount) {
            this(false, firstCompressedSegmentIndex, compressedCount, false, ':', '%');
        }

        private IPv6StringParams(boolean expandSegments, int firstCompressedSegmentIndex, int compressedCount, boolean uppercase, char separator, char zoneSeparator) {
            super(16, Character.valueOf(separator), uppercase, zoneSeparator);
            this.expandSegments(expandSegments);
            this.firstCompressedSegmentIndex = firstCompressedSegmentIndex;
            this.nextUncompressedIndex = firstCompressedSegmentIndex + compressedCount;
        }

        public boolean endIsCompressed(IPAddressStringDivisionSeries addr) {
            return this.nextUncompressedIndex >= addr.getDivisionCount();
        }

        public boolean isCompressed(IPAddressStringDivisionSeries addr) {
            return this.firstCompressedSegmentIndex >= 0;
        }

        @Override
        public int getTrailingSeparatorCount(IPv6AddressSection addr) {
            return this.getTrailingSepCount(addr);
        }

        public int getTrailingSepCount(IPAddressStringDivisionSeries addr) {
            int divisionCount = addr.getDivisionCount();
            if (divisionCount == 0) {
                return 0;
            }
            int count = divisionCount - 1;
            if (this.isCompressed(addr)) {
                count -= this.nextUncompressedIndex - this.firstCompressedSegmentIndex - 1;
                if (this.firstCompressedSegmentIndex == 0 || this.nextUncompressedIndex >= divisionCount) {
                    ++count;
                }
            }
            return count;
        }

        @Override
        public int getStringLength(IPv6AddressSection addr) {
            int count = this.getSegmentsStringLength(addr);
            if (!(this.isReverse() || this.preferWildcards() && !this.hostCompressed)) {
                count += IPv6StringParams.getPrefixIndicatorStringLength(addr);
            }
            count += this.getAddressSuffixLength();
            return count += this.getAddressLabelLength();
        }

        @Override
        public StringBuilder append(StringBuilder builder, IPv6AddressSection addr, CharSequence zone) {
            this.appendSuffix(this.appendZone(this.appendSegments(this.appendLabel(builder), addr), zone));
            if (!(this.isReverse() || this.preferWildcards() && !this.hostCompressed)) {
                this.appendPrefixIndicator(builder, addr);
            }
            return builder;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public StringBuilder appendSegments(StringBuilder builder, IPv6AddressSection addr) {
            int divisionCount = addr.getDivisionCount();
            if (divisionCount <= 0) {
                return builder;
            }
            int lastIndex = divisionCount - 1;
            Character separator = this.getSeparator();
            boolean reverse = this.isReverse();
            int i = 0;
            while (true) {
                int segIndex;
                int n = segIndex = reverse ? lastIndex - i : i;
                if (segIndex < this.firstCompressedSegmentIndex || segIndex >= this.nextUncompressedIndex) {
                    this.appendSegment(segIndex, builder, addr);
                    if (++i > lastIndex) return builder;
                    if (separator == null) continue;
                    builder.append(separator);
                    continue;
                }
                if (segIndex == (reverse ? this.nextUncompressedIndex - 1 : this.firstCompressedSegmentIndex) && separator != null) {
                    builder.append(separator);
                    if (i == 0) {
                        builder.append(separator);
                    }
                }
                if (++i > lastIndex) return builder;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public int getSegmentsStringLength(IPv6AddressSection part) {
            int count = 0;
            int divCount = part.getDivisionCount();
            if (divCount == 0) return count;
            Character separator = this.getSeparator();
            int i = 0;
            while (true) {
                if (i < this.firstCompressedSegmentIndex || i >= this.nextUncompressedIndex) {
                    count += this.appendSegment(i, (StringBuilder)null, part);
                    if (++i >= divCount) return count;
                    if (separator == null) continue;
                    ++count;
                    continue;
                }
                if (i == this.firstCompressedSegmentIndex && separator != null) {
                    ++count;
                    if (i == 0) {
                        ++count;
                    }
                }
                if (++i >= divCount) return count;
            }
        }

        @Override
        public IPv6StringParams clone() {
            return (IPv6StringParams)super.clone();
        }
    }

    public static class IPv6StringOptions
    extends IPAddressSection.IPStringOptions {
        public final IPAddressSection.IPStringOptions ipv4Opts;
        public final CompressOptions compressOptions;

        IPv6StringOptions(int base, boolean expandSegments, IPAddressSection.WildcardOptions.WildcardOption wildcardOption, AddressDivisionGrouping.StringOptions.Wildcards wildcards, String segmentStrPrefix, boolean makeMixed, IPAddressSection.IPStringOptions ipv4Opts, CompressOptions compressOptions, Character separator, char zoneSeparator, String addressPrefix, String addressSuffix, boolean reverse, boolean splitDigits, boolean uppercase) {
            super(base, expandSegments, wildcardOption, wildcards, segmentStrPrefix, separator, zoneSeparator, addressPrefix, addressSuffix, reverse, splitDigits, uppercase);
            this.compressOptions = compressOptions;
            if (makeMixed) {
                if (ipv4Opts == null) {
                    ipv4Opts = new IPv4AddressSection.IPv4StringOptions.Builder().setExpandedSegments(expandSegments).setWildcardOption(wildcardOption).setWildcards(wildcards).toOptions();
                }
                this.ipv4Opts = ipv4Opts;
            } else {
                this.ipv4Opts = null;
            }
        }

        boolean isCacheable() {
            return this.compressOptions == null;
        }

        boolean makeMixed() {
            return this.ipv4Opts != null;
        }

        private IPv6StringParams from(IPv6AddressSection addr) {
            boolean makeMixed;
            int[] vals;
            IPv6StringParams result = new IPv6StringParams();
            if (this.compressOptions != null && (vals = addr.getCompressIndexAndCount(this.compressOptions, makeMixed = this.makeMixed())) != null) {
                int maxIndex = vals[0];
                int maxCount = vals[1];
                result.firstCompressedSegmentIndex = maxIndex;
                result.nextUncompressedIndex = maxIndex + maxCount;
                result.hostCompressed = this.compressOptions.rangeSelection.compressHost() && addr.isPrefixed() && result.nextUncompressedIndex > IPv6AddressSection.getHostSegmentIndex(addr.getNetworkPrefixLength(), 2, 16);
            }
            result.expandSegments(this.expandSegments);
            result.setWildcardOption(this.wildcardOption);
            result.setWildcards(this.wildcards);
            result.setSeparator(this.separator);
            result.setAddressSuffix(this.addrSuffix);
            result.setAddressLabel(this.addrLabel);
            result.setReverse(this.reverse);
            result.setSplitDigits(this.splitDigits);
            result.setZoneSeparator(this.zoneSeparator);
            result.setUppercase(this.uppercase);
            result.setRadix(this.base);
            result.setSegmentStrPrefix(this.segmentStrPrefix);
            return result;
        }

        public static IPv6StringOptions from(IPAddressSection.IPStringOptions opts) {
            if (opts instanceof IPv6StringOptions) {
                return (IPv6StringOptions)opts;
            }
            return new IPv6StringOptions(opts.base, opts.expandSegments, opts.wildcardOption, opts.wildcards, opts.segmentStrPrefix, false, null, null, opts.separator, '%', opts.addrLabel, opts.addrSuffix, opts.reverse, opts.splitDigits, opts.uppercase);
        }

        public static class Builder
        extends IPAddressSection.IPStringOptions.Builder {
            private boolean makeMixed;
            private IPAddressSection.IPStringOptions ipv4Options;
            private CompressOptions compressOptions;

            public Builder() {
                super(16, ':');
            }

            public Builder setCompressOptions(CompressOptions compressOptions) {
                this.compressOptions = compressOptions;
                return this;
            }

            public Builder setMakeMixed(boolean makeMixed) {
                this.makeMixed = makeMixed;
                return this;
            }

            public Builder setMakeMixed(IPAddressSection.IPStringOptions ipv4Options) {
                this.makeMixed = true;
                this.ipv4Options = ipv4Options;
                return this;
            }

            @Override
            public Builder setWildcardOptions(IPAddressSection.WildcardOptions wildcardOptions) {
                return (Builder)super.setWildcardOptions(wildcardOptions);
            }

            @Override
            public Builder setExpandedSegments(boolean expandSegments) {
                return (Builder)super.setExpandedSegments(expandSegments);
            }

            @Override
            public Builder setRadix(int base) {
                return (Builder)super.setRadix(base);
            }

            @Override
            public Builder setSeparator(Character separator) {
                return (Builder)super.setSeparator(separator);
            }

            @Override
            public Builder setZoneSeparator(char separator) {
                return (Builder)super.setZoneSeparator(separator);
            }

            @Override
            public Builder setAddressSuffix(String suffix) {
                return (Builder)super.setAddressSuffix(suffix);
            }

            @Override
            public Builder setSegmentStrPrefix(String prefix) {
                return (Builder)super.setSegmentStrPrefix(prefix);
            }

            @Override
            public Builder setReverse(boolean reverse) {
                return (Builder)super.setReverse(reverse);
            }

            @Override
            public Builder setUppercase(boolean upper) {
                return (Builder)super.setUppercase(upper);
            }

            @Override
            public Builder setSplitDigits(boolean splitDigits) {
                return (Builder)super.setSplitDigits(splitDigits);
            }

            @Override
            public IPv6StringOptions toOptions() {
                return new IPv6StringOptions(this.base, this.expandSegments, this.wildcardOption, this.wildcards, this.segmentStrPrefix, this.makeMixed, this.ipv4Options, this.compressOptions, this.separator, this.zoneSeparator, this.addrLabel, this.addrSuffix, this.reverse, this.splitDigits, this.uppercase);
            }
        }
    }

    public static class CompressOptions {
        public final boolean compressSingle;
        public final CompressionChoiceOptions rangeSelection;
        public final MixedCompressionOptions compressMixedOptions;

        public CompressOptions(boolean compressSingle, CompressionChoiceOptions rangeSelection) {
            this(compressSingle, rangeSelection, MixedCompressionOptions.YES);
        }

        public CompressOptions(boolean compressSingle, CompressionChoiceOptions rangeSelection, MixedCompressionOptions compressMixedOptions) {
            this.compressSingle = compressSingle;
            this.rangeSelection = rangeSelection;
            this.compressMixedOptions = compressMixedOptions == null ? MixedCompressionOptions.YES : compressMixedOptions;
        }

        public static enum MixedCompressionOptions {
            NO,
            NO_HOST,
            COVERED_BY_HOST,
            YES;


            boolean compressMixed(IPv6AddressSection addressSection) {
                switch (this) {
                    default: {
                        return true;
                    }
                    case NO: {
                        return false;
                    }
                    case NO_HOST: {
                        return !addressSection.isPrefixed();
                    }
                    case COVERED_BY_HOST: 
                }
                if (addressSection.isPrefixed()) {
                    int mixedDistance = 6 - addressSection.addressSegmentIndex;
                    int mixedCount = addressSection.getSegmentCount() - Math.max(mixedDistance, 0);
                    if (mixedCount > 0) {
                        return mixedDistance * addressSection.getBitsPerSegment() >= addressSection.getNetworkPrefixLength();
                    }
                }
                return true;
            }
        }

        public static enum CompressionChoiceOptions {
            HOST_PREFERRED,
            MIXED_PREFERRED,
            ZEROS_OR_HOST,
            ZEROS;


            boolean compressHost() {
                return this != ZEROS;
            }
        }
    }

    private static class IPv6StringMatcher
    extends SQLStringMatcher<IPv6AddressSection, IPv6StringParams, IPv6AddressSectionString> {
        IPv6StringMatcher(IPv6AddressSectionString networkString, IPAddressSQLTranslator translator) {
            super(networkString, ((IPv6AddressSection)networkString.addr).isEntireAddress(), translator);
        }

        @Override
        public StringBuilder getSQLCondition(StringBuilder builder, String columnName) {
            if (((IPv6AddressSection)((IPv6AddressSectionString)this.networkString).addr).isEntireAddress()) {
                this.matchString(builder, columnName, ((IPv6AddressSectionString)this.networkString).getString());
            } else if (((IPv6AddressSectionString)this.networkString).endIsCompressed()) {
                char sep = ((IPv6AddressSectionString)this.networkString).getTrailingSegmentSeparator();
                String searchStr = ((IPv6AddressSectionString)this.networkString).getString().substring(0, ((IPv6AddressSectionString)this.networkString).getString().length() - 1);
                builder.append('(');
                this.matchSubString(builder, columnName, sep, ((IPv6AddressSectionString)this.networkString).getTrailingSeparatorCount(), searchStr);
                int extraSeparatorCountMax = 7 - ((IPv6AddressSection)((IPv6AddressSectionString)this.networkString).addr).getSegmentCount();
                builder.append(") AND (");
                this.boundSeparatorCount(builder, columnName, sep, extraSeparatorCountMax + ((IPv6AddressSectionString)this.networkString).getTrailingSeparatorCount());
                builder.append(')');
            } else if (((IPv6AddressSectionString)this.networkString).isCompressed()) {
                char sep = ((IPv6AddressSectionString)this.networkString).getTrailingSegmentSeparator();
                builder.append('(');
                this.matchSubString(builder, columnName, sep, ((IPv6AddressSectionString)this.networkString).getTrailingSeparatorCount() + 1, ((IPv6AddressSectionString)this.networkString).getString());
                int extraSeparatorCount = 8 - ((IPv6AddressSection)((IPv6AddressSectionString)this.networkString).addr).getSegmentCount();
                builder.append(") AND (");
                this.matchSeparatorCount(builder, columnName, sep, extraSeparatorCount + ((IPv6AddressSectionString)this.networkString).getTrailingSeparatorCount());
                builder.append(')');
            } else {
                this.matchSubString(builder, columnName, ((IPv6AddressSectionString)this.networkString).getTrailingSegmentSeparator(), ((IPv6AddressSectionString)this.networkString).getTrailingSeparatorCount() + 1, ((IPv6AddressSectionString)this.networkString).getString());
            }
            return builder;
        }
    }

    static class IPv6AddressCache
    extends AddressDivisionGrouping.SectionCache<IPv6Address> {
        public Inet6Address inetAddress;

        IPv6AddressCache() {
        }
    }

    static class IPv6StringCache
    extends IPAddressSection.IPStringCache {
        static final IPv6StringOptions mixedParams;
        static final IPv6StringOptions fullParams;
        static final IPv6StringOptions normalizedParams;
        static final IPv6StringOptions canonicalParams;
        static final IPv6StringOptions uncParams;
        static final IPv6StringOptions compressedParams;
        static final IPv6StringOptions wildcardNormalizedParams;
        static final IPv6StringOptions wildcardCanonicalParams;
        static final IPv6StringOptions sqlWildcardParams;
        static final IPv6StringOptions wildcardCompressedParams;
        static final IPv6StringOptions networkPrefixLengthParams;
        static final IPv6StringOptions reverseDNSParams;
        static final IPAddressSection.IPStringOptions base85Params;
        static final IPAddressSection.IPStringOptions segmentedBinaryParams;
        public String normalizedString;
        public String compressedString;
        public String mixedString;
        public String compressedWildcardString;
        public String canonicalWildcardString;
        public String networkPrefixLengthString;
        public String base85String;
        public String uncString;

        IPv6StringCache() {
        }

        static {
            CompressOptions compressAll = new CompressOptions(true, CompressOptions.CompressionChoiceOptions.ZEROS_OR_HOST);
            CompressOptions compressMixed = new CompressOptions(true, CompressOptions.CompressionChoiceOptions.MIXED_PREFERRED);
            CompressOptions compressAllNoSingles = new CompressOptions(false, CompressOptions.CompressionChoiceOptions.ZEROS_OR_HOST);
            CompressOptions compressHostPreferred = new CompressOptions(true, CompressOptions.CompressionChoiceOptions.HOST_PREFERRED);
            CompressOptions compressZeros = new CompressOptions(true, CompressOptions.CompressionChoiceOptions.ZEROS);
            CompressOptions compressZerosNoSingles = new CompressOptions(false, CompressOptions.CompressionChoiceOptions.ZEROS);
            mixedParams = new IPv6StringOptions.Builder().setMakeMixed(true).setCompressOptions(compressMixed).toOptions();
            fullParams = new IPv6StringOptions.Builder().setExpandedSegments(true).setWildcardOptions(new IPAddressSection.WildcardOptions(IPAddressSection.WildcardOptions.WildcardOption.NETWORK_ONLY, new AddressDivisionGrouping.StringOptions.Wildcards(IPAddress.RANGE_SEPARATOR_STR))).toOptions();
            canonicalParams = new IPv6StringOptions.Builder().setCompressOptions(compressAllNoSingles).toOptions();
            uncParams = new IPv6StringOptions.Builder().setSeparator(Character.valueOf('-')).setZoneSeparator('s').setAddressSuffix(".ipv6-literal.net").setWildcardOptions(new IPAddressSection.WildcardOptions(IPAddressSection.WildcardOptions.WildcardOption.NETWORK_ONLY, new AddressDivisionGrouping.StringOptions.Wildcards(IPv6Address.UNC_RANGE_SEPARATOR_STR, IPAddress.SEGMENT_WILDCARD_STR, null))).toOptions();
            compressedParams = new IPv6StringOptions.Builder().setCompressOptions(compressAll).toOptions();
            normalizedParams = new IPv6StringOptions.Builder().toOptions();
            IPAddressSection.WildcardOptions allWildcards = new IPAddressSection.WildcardOptions(IPAddressSection.WildcardOptions.WildcardOption.ALL);
            IPAddressSection.WildcardOptions allSQLWildcards = new IPAddressSection.WildcardOptions(IPAddressSection.WildcardOptions.WildcardOption.ALL, new AddressDivisionGrouping.StringOptions.Wildcards(IPAddress.SEGMENT_SQL_WILDCARD_STR, IPAddress.SEGMENT_SQL_SINGLE_WILDCARD_STR));
            wildcardCanonicalParams = new IPv6StringOptions.Builder().setWildcardOptions(allWildcards).setCompressOptions(compressZerosNoSingles).toOptions();
            wildcardNormalizedParams = new IPv6StringOptions.Builder().setWildcardOptions(allWildcards).toOptions();
            sqlWildcardParams = new IPv6StringOptions.Builder().setWildcardOptions(allSQLWildcards).toOptions();
            wildcardCompressedParams = new IPv6StringOptions.Builder().setWildcardOptions(allWildcards).setCompressOptions(compressZeros).toOptions();
            networkPrefixLengthParams = new IPv6StringOptions.Builder().setCompressOptions(compressHostPreferred).toOptions();
            reverseDNSParams = new IPv6StringOptions.Builder().setReverse(true).setAddressSuffix(".ip6.arpa").setSplitDigits(true).setExpandedSegments(true).setSeparator(Character.valueOf('.')).toOptions();
            base85Params = new IPAddressSection.IPStringOptions.Builder(85).setExpandedSegments(true).setWildcards(new AddressDivisionGrouping.StringOptions.Wildcards(Address.ALTERNATIVE_RANGE_SEPARATOR_STR)).setZoneSeparator('\u00a7').toOptions();
            segmentedBinaryParams = new IPAddressSection.IPStringOptions.Builder(2).setSeparator(Character.valueOf(':')).setSegmentStrPrefix("0b").setExpandedSegments(true).toOptions();
        }
    }
}

