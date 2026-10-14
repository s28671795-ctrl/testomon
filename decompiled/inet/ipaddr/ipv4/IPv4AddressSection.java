/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv4;

import inet.ipaddr.Address;
import inet.ipaddr.AddressConversionException;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressSection;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.IPAddressSegmentSeries;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.SizeMismatchException;
import inet.ipaddr.format.AddressDivisionGroupingBase;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.standard.AddressCreator;
import inet.ipaddr.format.standard.AddressDivisionGrouping;
import inet.ipaddr.format.standard.IPAddressDivision;
import inet.ipaddr.format.standard.IPAddressDivisionGrouping;
import inet.ipaddr.format.string.IPAddressStringDivision;
import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.format.util.IPAddressPartConfiguredString;
import inet.ipaddr.format.util.IPAddressPartStringCollection;
import inet.ipaddr.format.util.IPAddressPartStringSubCollection;
import inet.ipaddr.ipv4.IPv4Address;
import inet.ipaddr.ipv4.IPv4AddressNetwork;
import inet.ipaddr.ipv4.IPv4AddressSegment;
import inet.ipaddr.ipv4.IPv4JoinedSegments;
import inet.ipaddr.ipv6.IPv6Address;
import inet.ipaddr.ipv6.IPv6AddressSection;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IPv4AddressSection
extends IPAddressSection
implements Iterable<IPv4AddressSection> {
    private static final long serialVersionUID = 4L;
    private static final long[] MAX_VALUES = new long[]{0L, 255L, 65535L, 0xFFFFFFL, 0xFFFFFFFFL};
    transient IPv4StringCache stringCache;
    private transient AddressDivisionGrouping.SectionCache<IPv4AddressSection> sectionCache;
    private transient Integer cachedLowerVal;

    public IPv4AddressSection(IPv4AddressSegment segment) {
        this(new IPv4AddressSegment[]{segment}, false);
    }

    public IPv4AddressSection(IPv4AddressSegment[] segments) throws AddressValueException {
        this(segments, true);
    }

    public IPv4AddressSection(IPv4AddressSegment[] segments, Integer networkPrefixLength) throws AddressValueException {
        this(segments, true, networkPrefixLength, false);
    }

    protected IPv4AddressSection(IPv4AddressSegment[] segments, boolean cloneSegments, Integer networkPrefixLength, boolean singleOnly) throws AddressValueException {
        this(segments, cloneSegments, networkPrefixLength == null);
        if (networkPrefixLength != null) {
            if (networkPrefixLength < 0) {
                throw new PrefixLenException(networkPrefixLength);
            }
            int max = segments.length << 3;
            if (networkPrefixLength > max) {
                if (networkPrefixLength > 32) {
                    throw new PrefixLenException(networkPrefixLength);
                }
                networkPrefixLength = max;
            }
            if (segments.length > 0) {
                if (this.cachedPrefixLength != NO_PREFIX_LENGTH && this.cachedPrefixLength < networkPrefixLength) {
                    networkPrefixLength = this.cachedPrefixLength;
                }
                IPv4AddressNetwork network = this.getNetwork();
                IPv4AddressSection.setPrefixedSegments((AddressNetwork)network, (int)networkPrefixLength, (AddressSegment[])this.getSegmentsInternal(), (int)this.getBitsPerSegment(), (int)this.getBytesPerSegment(), (AddressNetwork.AddressSegmentCreator)network.getAddressCreator(), !singleOnly && IPv4AddressSection.isPrefixSubnetSegs(segments, networkPrefixLength, network, false) ? IPv4AddressSegment::toNetworkSegment : IPv4AddressSegment::toPrefixedSegment);
            }
            this.cachedPrefixLength = networkPrefixLength;
        }
    }

    protected IPv4AddressSection(IPv4AddressSegment[] segments, boolean cloneSegments) throws AddressValueException {
        this(segments, cloneSegments, true);
    }

    IPv4AddressSection(IPv4AddressSegment[] segments, boolean cloneSegments, boolean normalizeSegments) throws AddressValueException {
        super(segments, cloneSegments, true);
        if (normalizeSegments && this.isPrefixed()) {
            IPv4AddressSection.normalizePrefixBoundary((int)this.getNetworkPrefixLength(), (IPAddressSegment[])this.getSegmentsInternal(), (int)8, (int)1, IPv4AddressSegment::toPrefixNormalizedSeg);
        }
        if (segments.length > 4) {
            throw new AddressValueException(segments.length);
        }
    }

    public IPv4AddressSection(Address.SegmentValueProvider valueProvider, int segmentCount, Integer networkPrefixLength) throws AddressValueException {
        this(valueProvider, valueProvider, segmentCount, networkPrefixLength);
    }

    public IPv4AddressSection(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, int segmentCount, Integer networkPrefixLength) throws AddressValueException {
        super(new IPv4AddressSegment[segmentCount], false, false);
        AddressSegment[] segs = this.getSegmentsInternal();
        IPv4AddressNetwork network = this.getNetwork();
        IPv4AddressSection.createSegments((AddressSegment[])segs, (Address.SegmentValueProvider)lowerValueProvider, (Address.SegmentValueProvider)upperValueProvider, (int)this.getBytesPerSegment(), (int)this.getBitsPerSegment(), (AddressNetwork)network, (Integer)networkPrefixLength);
        if (networkPrefixLength != null) {
            if (networkPrefixLength > 32) {
                throw new PrefixLenException(networkPrefixLength);
            }
            if (network.getPrefixConfiguration().zeroHostsAreSubnets() && IPv4AddressSection.isPrefixSubnetSegs((IPAddressSegment[])segs, networkPrefixLength, network, false)) {
                IPv4AddressSection.setPrefixedSegments((AddressNetwork)network, (int)networkPrefixLength, (AddressSegment[])this.getSegmentsInternal(), (int)this.getBitsPerSegment(), (int)this.getBytesPerSegment(), (AddressNetwork.AddressSegmentCreator)network.getAddressCreator(), IPv4AddressSegment::toNetworkSegment);
            }
            this.cachedPrefixLength = networkPrefixLength;
        } else {
            this.cachedPrefixLength = NO_PREFIX_LENGTH;
        }
    }

    public IPv4AddressSection(Address.SegmentValueProvider valueProvider, int segmentCount) throws AddressValueException {
        this(valueProvider, valueProvider, segmentCount);
    }

    public IPv4AddressSection(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, int segmentCount) {
        this(lowerValueProvider, upperValueProvider, segmentCount, null);
    }

    protected IPv4AddressSection(byte[] bytes, int segmentCount, Integer networkPrefixLength, boolean cloneBytes, boolean singleOnly) throws AddressValueException {
        this(bytes, 0, bytes.length, segmentCount, networkPrefixLength, cloneBytes, singleOnly);
    }

    protected IPv4AddressSection(byte[] bytes, int byteStartIndex, int byteEndIndex, int segmentCount, Integer networkPrefixLength, boolean cloneBytes, boolean singleOnly) throws AddressValueException {
        super(new IPv4AddressSegment[segmentCount >= 0 ? segmentCount : Math.max(0, byteEndIndex - byteStartIndex)], false, false);
        boolean byteLengthIsExact;
        AddressSegment[] segs = this.getSegmentsInternal();
        IPv4AddressNetwork network = this.getNetwork();
        IPv4AddressSection.toSegments((AddressSegment[])segs, (byte[])bytes, (int)byteStartIndex, (int)byteEndIndex, (int)this.getBytesPerSegment(), (int)this.getBitsPerSegment(), (AddressNetwork)network, (Integer)networkPrefixLength);
        boolean bl = byteLengthIsExact = bytes.length == segs.length;
        if (networkPrefixLength != null) {
            if (networkPrefixLength < 0) {
                throw new PrefixLenException(networkPrefixLength);
            }
            int max = segs.length << 3;
            if (networkPrefixLength > max) {
                if (networkPrefixLength > 32) {
                    throw new PrefixLenException(networkPrefixLength);
                }
                networkPrefixLength = max;
            }
            if (segs.length > 0) {
                AddressNetwork.PrefixConfiguration prefConf = network.getPrefixConfiguration();
                if (prefConf.zeroHostsAreSubnets()) {
                    if (IPv4AddressSection.isPrefixSubnetSegs((IPAddressSegment[])segs, networkPrefixLength, network, false) && !singleOnly) {
                        IPv4AddressSection.setPrefixedSegments((AddressNetwork)network, (int)networkPrefixLength, (AddressSegment[])segs, (int)this.getBitsPerSegment(), (int)this.getBytesPerSegment(), (AddressNetwork.AddressSegmentCreator)network.getAddressCreator(), IPv4AddressSegment::toNetworkSegment);
                    } else if (byteLengthIsExact && networkPrefixLength >= this.getBitCount()) {
                        this.setBytes(cloneBytes ? (byte[])bytes.clone() : bytes);
                    }
                } else if (byteLengthIsExact && (prefConf.prefixedSubnetsAreExplicit() || networkPrefixLength >= this.getBitCount())) {
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
    }

    protected IPv4AddressSection(byte[] bytes, int byteStartIndex, int byteEndIndex, int segmentCount, Integer prefix) throws AddressValueException {
        this(bytes, byteStartIndex, byteEndIndex, segmentCount, prefix, true, false);
    }

    public IPv4AddressSection(byte[] bytes, Integer prefix) throws AddressValueException {
        this(bytes, bytes.length, prefix, true, false);
    }

    public IPv4AddressSection(byte[] bytes) throws AddressValueException {
        this(bytes, bytes.length, null, true, false);
    }

    public IPv4AddressSection(byte[] bytes, int byteStartIndex, int byteEndIndex, Integer prefix) throws AddressValueException {
        this(bytes, byteStartIndex, byteEndIndex, -1, prefix, true, false);
    }

    public IPv4AddressSection(byte[] bytes, int byteStartIndex, int byteEndIndex) throws AddressValueException {
        this(bytes, byteStartIndex, byteEndIndex, -1, null, true, false);
    }

    public IPv4AddressSection(int value, Integer networkPrefixLength) throws AddressValueException {
        super(new IPv4AddressSegment[4], false, false);
        AddressSegment[] segs = this.getSegmentsInternal();
        IPv4AddressNetwork network = this.getNetwork();
        IPv4AddressSection.createSegments((AddressSegment[])segs, (long)0L, (long)value, (int)this.getBitsPerSegment(), (AddressNetwork)network, (Integer)networkPrefixLength);
        if (networkPrefixLength != null) {
            if (networkPrefixLength > 32) {
                throw new PrefixLenException(networkPrefixLength);
            }
            if (network.getPrefixConfiguration().zeroHostsAreSubnets() && IPv4AddressSection.isPrefixSubnetSegs((IPAddressSegment[])segs, networkPrefixLength, network, false)) {
                IPv4AddressSection.setPrefixedSegments((AddressNetwork)network, (int)networkPrefixLength, (AddressSegment[])this.getSegmentsInternal(), (int)this.getBitsPerSegment(), (int)this.getBytesPerSegment(), (AddressNetwork.AddressSegmentCreator)network.getAddressCreator(), IPv4AddressSegment::toNetworkSegment);
            }
            this.cachedPrefixLength = networkPrefixLength;
        } else {
            this.cachedPrefixLength = NO_PREFIX_LENGTH;
        }
    }

    public IPv4AddressSection(int value) {
        this(value, null);
    }

    public IPv4AddressSegment[] getSegments() {
        return (IPv4AddressSegment[])this.getDivisionsInternal().clone();
    }

    @Override
    public IPv4AddressSection getSection() {
        return this;
    }

    @Override
    public IPv4AddressSection getSection(int index) {
        return this.getSection(index, this.getSegmentCount());
    }

    @Override
    public IPv4AddressSection getSection(int index, int endIndex) {
        return IPv4AddressSection.getSection(index, endIndex, this, this.getAddressCreator());
    }

    @Override
    protected void setInetAddress(InetAddress addr) {
        super.setInetAddress(addr);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void cache(IPv4Address thisAddr, IPv4Address lower, IPv4Address upper) {
        if ((lower != null || upper != null) && IPv4AddressSection.getSingleLowestOrHighestSection(this) == null) {
            this.getSection().cache(lower != null ? lower.getSection() : null, upper != null ? upper.getSection() : null);
            IPv4AddressCache cache = thisAddr.addressCache;
            if (cache == null || lower != null && cache.lower == null || upper != null && cache.upper == null) {
                IPv4AddressSection iPv4AddressSection = this;
                synchronized (iPv4AddressSection) {
                    boolean create;
                    cache = thisAddr.addressCache;
                    boolean bl = create = cache == null;
                    if (create) {
                        thisAddr.addressCache = cache = new IPv4AddressCache();
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
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void cache(IPv4AddressSection lower, IPv4AddressSection upper) {
        AddressDivisionGrouping.SectionCache<IPv4AddressSection> cache = this.sectionCache;
        if ((lower != null || upper != null) && (cache == null || lower != null && cache.lower == null || upper != null && cache.upper == null)) {
            IPv4AddressSection iPv4AddressSection = this;
            synchronized (iPv4AddressSection) {
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IPv4AddressSection getLowestOrHighestSection(boolean lowest, boolean excludeZeroHost) {
        IPv4AddressSection result = IPv4AddressSection.getSingleLowestOrHighestSection(this);
        if (result == null) {
            AddressDivisionGrouping.SectionCache<IPv4AddressSection> cache = this.sectionCache;
            if (cache == null || (lowest ? (excludeZeroHost ? (result = (IPv4AddressSection)cache.lowerNonZeroHost) == null && !cache.lowerNonZeroHostIsNull : (result = (IPv4AddressSection)cache.lower) == null) : (result = (IPv4AddressSection)cache.upper) == null)) {
                IPv4AddressSection iPv4AddressSection = this;
                synchronized (iPv4AddressSection) {
                    boolean create;
                    cache = this.sectionCache;
                    boolean bl = create = cache == null;
                    if (create) {
                        this.sectionCache = cache = new AddressDivisionGrouping.SectionCache();
                    } else if (lowest) {
                        create = excludeZeroHost ? (result = (IPv4AddressSection)cache.lowerNonZeroHost) == null && !cache.lowerNonZeroHostIsNull : (result = (IPv4AddressSection)cache.lower) == null;
                    } else {
                        result = (IPv4AddressSection)cache.upper;
                        boolean bl2 = create = result == null;
                    }
                    if (create) {
                        result = IPv4AddressSection.createLowestOrHighestSection(this, this.getAddressCreator(), this::segmentsNonZeroHostIterator, i -> lowest ? this.getSegment(i).getLower() : this.getSegment(i).getUpper(), lowest, excludeZeroHost);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    IPv4Address getLowestOrHighest(IPv4Address addr, boolean lowest, boolean excludeZeroHost) {
        IPv4Address result;
        block18: {
            IPv4AddressCache cache;
            IPv4AddressSection sectionResult;
            block17: {
                sectionResult = this.getLowestOrHighestSection(lowest, excludeZeroHost);
                if (sectionResult == this) {
                    return addr;
                }
                if (sectionResult == null) {
                    return null;
                }
                result = null;
                cache = addr.addressCache;
                if (cache == null) break block17;
                IPv4Address iPv4Address = lowest ? (excludeZeroHost ? (IPv4Address)cache.lowerNonZeroHost : (IPv4Address)cache.lower) : (result = (IPv4Address)cache.upper);
                if (iPv4Address != null) break block18;
            }
            IPv4AddressSection iPv4AddressSection = this;
            synchronized (iPv4AddressSection) {
                boolean create;
                cache = addr.addressCache;
                boolean bl = create = cache == null;
                if (create) {
                    cache = addr.addressCache = new IPv4AddressCache();
                } else if (lowest) {
                    create = excludeZeroHost ? (result = (IPv4Address)cache.lowerNonZeroHost) == null : (result = (IPv4Address)cache.lower) == null;
                } else {
                    result = (IPv4Address)cache.upper;
                    boolean bl2 = create = result == null;
                }
                if (create) {
                    result = this.getAddressCreator().createAddress(sectionResult);
                    if (lowest) {
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
        return result;
    }

    @Override
    public IPv4AddressSection getLowerNonZeroHost() {
        return this.getLowestOrHighestSection(true, true);
    }

    @Override
    public IPv4AddressSection getLower() {
        return this.getLowestOrHighestSection(true, false);
    }

    @Override
    public IPv4AddressSection getUpper() {
        return this.getLowestOrHighestSection(false, false);
    }

    public int intValue() {
        return this.getIntValue(true);
    }

    public int upperIntValue() {
        return this.getIntValue(false);
    }

    public long longValue() {
        return (long)this.intValue() & 0xFFFFFFFFL;
    }

    public long upperLongValue() {
        return (long)this.upperIntValue() & 0xFFFFFFFFL;
    }

    private int calcValue(boolean lower) {
        int segCount = this.getSegmentCount();
        int result = 0;
        if (segCount != 0) {
            IPv4AddressSegment first = this.getSegment(0);
            int n = result = lower ? first.getSegmentValue() : first.getUpperSegmentValue();
            if (segCount != 1) {
                int bitsPerSegment = this.getBitsPerSegment();
                for (int i = 1; i < segCount; ++i) {
                    IPv4AddressSegment seg = this.getSegment(i);
                    result = result << bitsPerSegment | (lower ? seg.getSegmentValue() : seg.getUpperSegmentValue());
                }
            }
        }
        return result;
    }

    private int getIntValue(boolean lower) {
        int result = 0;
        if (lower) {
            Integer cachedInt = this.cachedLowerVal;
            if (cachedInt == null) {
                result = this.calcValue(true);
                this.cachedLowerVal = result;
            } else {
                result = cachedInt;
            }
        } else {
            result = this.calcValue(false);
        }
        return result;
    }

    @Override
    public IPv4AddressSection reverseBits(boolean perByte) {
        return IPv4AddressSection.reverseBits(perByte, this, this.getAddressCreator(), i -> this.getSegment(i).reverseBits(perByte), true);
    }

    @Override
    public IPv4AddressSection reverseBytes() {
        return this.reverseSegments();
    }

    @Override
    public IPv4AddressSection reverseBytesPerSegment() {
        if (!this.isPrefixed()) {
            return this;
        }
        return this.withoutPrefixLength();
    }

    @Override
    public IPv4AddressSection reverseSegments() {
        if (this.getSegmentCount() <= 1) {
            if (this.isPrefixed()) {
                return this.withoutPrefixLength();
            }
            return this;
        }
        return IPv4AddressSection.reverseSegments(this, this.getAddressCreator(), i -> this.getSegment(i).withoutPrefixLength(), true);
    }

    protected IPv4AddressSegment[] getSegmentsInternal() {
        return (IPv4AddressSegment[])super.getDivisionsInternal();
    }

    public Iterable<IPv4AddressSection> getIterable() {
        return this;
    }

    private Iterator<IPv4AddressSection> iterator(Predicate<IPv4AddressSegment[]> excludeFunc) {
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        boolean useOriginal = !this.isMultiple() && (!isAllSubnets || !this.isPrefixed());
        IPv4AddressSection original = !useOriginal || excludeFunc != null && excludeFunc.test(this.getSegmentsInternal()) ? null : this;
        return IPv4AddressSection.iterator(useOriginal, original, this.getAddressCreator(), useOriginal ? null : this.segmentsIterator(excludeFunc), isAllSubnets ? null : this.getPrefixLength());
    }

    AddressComponentSpliterator<IPv4AddressSection> spliterator(boolean excludeZeroHosts) {
        AddressDivisionGroupingBase.IteratorProvider iteratorProvider;
        ToLongFunction<IPv4AddressSection> longSizer;
        IPv4AddressSection forIteration;
        Integer iterationsPrefix;
        int segmentCount = this.getSegmentCount();
        Integer prefixLength = this.getNetworkPrefixLength();
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getAddressCreator();
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (isAllSubnets) {
            iterationsPrefix = null;
            forIteration = this.withoutPrefixLength();
        } else {
            iterationsPrefix = prefixLength;
            forIteration = this;
        }
        if (excludeZeroHosts && this.includesZeroHost()) {
            longSizer = section -> IPv4AddressSection.longCount(section, segmentCount) - section.longZeroHostCount(prefixLength, segmentCount);
            iteratorProvider = (isLowest, isHighest, section) -> section.iterator(segs -> this.isZeroHost((IPv4AddressSegment[])segs, (int)prefixLength));
        } else {
            longSizer = section -> IPv4AddressSection.longCount(section, segmentCount);
            iteratorProvider = (isLowest, isHighest, section) -> section.iterator();
        }
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return IPv4AddressSection.createSeriesSpliterator(forIteration, spliterator -> IPv4AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv4AddressSection)IPv4AddressSection.createIteratedSection((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv4AddressSection)spliterator.getAddressItem()).getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), iteratorProvider, null, null, longSizer);
    }

    Iterator<IPv4Address> iterator(IPv4Address original, AddressCreator<IPv4Address, ?, ?, IPv4AddressSegment> creator, Predicate<IPv4AddressSegment[]> excludeFunc) {
        boolean useOriginal;
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        boolean bl = useOriginal = !this.isMultiple() && (!isAllSubnets || !this.isPrefixed());
        if (useOriginal && excludeFunc != null && excludeFunc.test(original.getSection().getSegmentsInternal())) {
            original = null;
        }
        return IPv4AddressSection.iterator(useOriginal, original, creator, useOriginal ? null : IPv4AddressSection.segmentsIterator(this.getSegmentCount(), creator, this.isMultiple() ? null : () -> this.getLower().getSegmentsInternal(), index -> this.getSegment(index).iterator(!isAllSubnets), excludeFunc), isAllSubnets ? null : this.getPrefixLength());
    }

    AddressComponentSpliterator<IPv4Address> spliterator(IPv4Address original, IPv4AddressNetwork.IPv4AddressCreator creator, boolean excludeZeroHosts) {
        AddressDivisionGroupingBase.IteratorProvider iteratorProvider;
        ToLongFunction<IPv4Address> longSizer;
        IPv4Address forIteration;
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
            longSizer = addr -> IPv4AddressSection.longCount(addr.getSection(), segmentCount) - addr.getSection().longZeroHostCount(prefixLength, segmentCount);
            iteratorProvider = (isLowest, isHighest, addr) -> addr.getSection().iterator((IPv4Address)addr, addr.getAddressCreator(), s2 -> this.isZeroHost((IPv4AddressSegment[])s2, (int)prefixLength));
        } else {
            longSizer = addr -> IPv4AddressSection.longCount(addr.getSection(), segmentCount);
            iteratorProvider = (isLowest, isHighest, addr) -> addr.iterator();
        }
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return IPv4AddressSection.createSeriesSpliterator(forIteration, spliterator -> IPv4AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv4Address)IPv4AddressSection.createIteratedAddress((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv4Address)spliterator.getAddressItem()).getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), iteratorProvider, null, null, longSizer);
    }

    public Iterator<IPv4AddressSection> nonZeroHostIterator() {
        return this.iterator(this.excludeNonZeroHosts());
    }

    @Override
    public Iterator<IPv4AddressSection> iterator() {
        return this.iterator(null);
    }

    @Override
    public AddressComponentSpliterator<IPv4AddressSection> spliterator() {
        return this.spliterator(false);
    }

    public Stream<IPv4AddressSection> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Iterator<IPv4AddressSection> prefixIterator() {
        return this.prefixIterator(false);
    }

    public AddressComponentSpliterator<IPv4AddressSection> prefixSpliterator() {
        return this.prefixSpliterator(false);
    }

    public Stream<IPv4AddressSection> prefixStream() {
        return StreamSupport.stream(this.prefixSpliterator(), false);
    }

    public Iterator<IPv4AddressSection> prefixBlockIterator() {
        return this.prefixIterator(true);
    }

    public AddressComponentSpliterator<IPv4AddressSection> prefixBlockSpliterator() {
        return this.prefixSpliterator(true);
    }

    public Stream<IPv4AddressSection> prefixBlockStream() {
        return StreamSupport.stream(this.prefixBlockSpliterator(), false);
    }

    private Iterator<IPv4AddressSection> prefixIterator(boolean isBlockIterator) {
        Integer prefLength = this.getPrefixLength();
        if (prefLength == null || prefLength > this.getBitCount()) {
            return this.iterator();
        }
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getAddressCreator();
        boolean useOriginal = isBlockIterator ? this.isSinglePrefixBlock() : this.longPrefixCount(prefLength) == 1L;
        int networkSegIndex = IPv4AddressSection.getNetworkSegmentIndex(prefLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int hostSegIndex = IPv4AddressSection.getHostSegmentIndex(prefLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int segCount = this.getSegmentCount();
        return IPv4AddressSection.iterator(useOriginal, this, creator, useOriginal ? null : IPv4AddressSection.segmentsIterator(segCount, creator, null, index -> this.getSegment(index).iterator(), null, networkSegIndex, hostSegIndex, isBlockIterator ? index -> this.getSegment(index).prefixBlockIterator() : index -> this.getSegment(index).prefixIterator()), prefLength);
    }

    private AddressComponentSpliterator<IPv4AddressSection> prefixSpliterator(boolean isBlockIterator) {
        Integer prefLength = this.getPrefixLength();
        if (prefLength == null || prefLength > this.getBitCount()) {
            return this.spliterator(false);
        }
        return this.prefixSpliterator(isBlockIterator, prefLength);
    }

    private AddressComponentSpliterator<IPv4AddressSection> prefixSpliterator(boolean isBlockIterator, int prefixLength) {
        if (prefixLength > this.getBitCount() || prefixLength < 0) {
            throw new PrefixLenException((AddressItem)this, prefixLength);
        }
        Integer prefLength = IPv4AddressSection.cacheBits(prefixLength);
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getAddressCreator();
        int networkSegIndex = IPv4AddressSection.getNetworkSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int hostSegIndex = IPv4AddressSection.getHostSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        return IPv4AddressSection.createSeriesSpliterator(this.setPrefixLength(prefixLength, false), spliterator -> IPv4AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv4AddressSection)IPv4AddressSection.createIteratedSection((AddressSegment[])segs, (AddressCreator)creator, (Integer)prefLength), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv4AddressSection)spliterator.getAddressItem()).getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)prefLength), isBlockIterator ? (isLowest, isHighest, section) -> section.prefixBlockIterator() : (!this.isSequential() ? (isLowest, isHighest, section) -> section.prefixIterator() : (isLowest, isHighest, section) -> isLowest || isHighest ? section.prefixIterator() : section.prefixBlockIterator()), null, null, section -> IPv4AddressSection.longPrefixCount(section, prefixLength));
    }

    public Iterator<IPv4AddressSection> blockIterator(int segmentCount) {
        if (segmentCount < 0) {
            throw new IllegalArgumentException();
        }
        if (segmentCount >= this.getSegmentCount()) {
            return this.iterator();
        }
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getAddressCreator();
        boolean useOriginal = !this.isMultiple(segmentCount);
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        return IPv4AddressSection.iterator(useOriginal, this, creator, useOriginal ? null : IPv4AddressSection.segmentsIterator(this.getSegmentCount(), creator, null, index -> this.getSegment(index).iterator(!isAllSubnets), null, segmentCount - 1, segmentCount, index -> this.getSegment(index).identityIterator()), isAllSubnets ? null : this.getPrefixLength());
    }

    public AddressComponentSpliterator<IPv4AddressSection> blockSpliterator(int segmentCount) {
        IPv4AddressSection forIteration;
        Integer iterationsPrefix;
        Integer prefLength;
        if (segmentCount < 0) {
            throw new IllegalArgumentException();
        }
        if (segmentCount >= this.getSegmentCount()) {
            return this.spliterator();
        }
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getAddressCreator();
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
        return IPv4AddressSection.createSeriesSpliterator(forIteration, spliterator -> IPv4AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv4AddressSection)IPv4AddressSection.createIteratedSection((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv4AddressSection)spliterator.getAddressItem()).getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), (isLowest, isHighest, section) -> section.blockIterator(segmentCount), null, null, section -> IPv4AddressSection.longCount(section, segmentCount));
    }

    public Stream<IPv4AddressSection> blockStream(int segmentCount) {
        return StreamSupport.stream(this.blockSpliterator(segmentCount), false);
    }

    public Iterator<IPv4AddressSection> sequentialBlockIterator() {
        return super.sequentialBlockIterator();
    }

    public AddressComponentSpliterator<IPv4AddressSection> sequentialBlockSpliterator() {
        return super.sequentialBlockSpliterator();
    }

    public Stream<IPv4AddressSection> sequentialBlockStream() {
        return super.sequentialBlockStream();
    }

    private Iterator<IPv4AddressSegment[]> segmentsIterator(Predicate<IPv4AddressSegment[]> excludeFunc) {
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        return IPv4AddressSection.segmentsIterator(this.getSegmentCount(), this.getSegmentCreator(), this.isMultiple() ? null : () -> this.getLower().getSegments(), index -> this.getSegment(index).iterator(!isAllSubnets), excludeFunc);
    }

    private Predicate<IPv4AddressSegment[]> excludeNonZeroHosts() {
        if (this.isPrefixed()) {
            int prefLength = this.getNetworkPrefixLength();
            return segments -> this.isZeroHost((IPv4AddressSegment[])segments, prefLength);
        }
        return null;
    }

    public Iterator<IPv4AddressSegment[]> segmentsNonZeroHostIterator() {
        return this.segmentsIterator(this.excludeNonZeroHosts());
    }

    public Iterator<IPv4AddressSegment[]> segmentsIterator() {
        return this.segmentsIterator(null);
    }

    public AddressComponentRangeSpliterator<IPv4AddressSection, IPv4AddressSegment[]> segmentsSpliterator() {
        IPv4AddressSection forIteration;
        Integer iterationsPrefix;
        int segmentCount = this.getSegmentCount();
        Integer prefixLength = this.getNetworkPrefixLength();
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getAddressCreator();
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
        return IPv4AddressSection.createItemSpliterator(forIteration, spliterator -> IPv4AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv4AddressSection)IPv4AddressSection.createIteratedSection((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv4AddressSection)spliterator.getAddressItem()).getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), (isLowest, isHighest, section) -> section.segmentsIterator(), null, null, section -> IPv4AddressSection.longCount(section, segmentCount));
    }

    public Stream<IPv4AddressSegment[]> segmentsStream() {
        return StreamSupport.stream(this.segmentsSpliterator(), false);
    }

    AddressComponentRangeSpliterator<IPv4Address, IPv4AddressSegment[]> segmentsSpliterator(IPv4Address address, IPv4AddressNetwork.IPv4AddressCreator creator) {
        IPv4Address forIteration;
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
        return IPv4AddressSection.createItemSpliterator(forIteration, spliterator -> IPv4AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv4Address)IPv4AddressSection.createIteratedAddress((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv4Address)spliterator.getAddressItem()).getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), (isLowest, isHighest, addr) -> addr.segmentsIterator(), null, null, addr -> IPv4AddressSection.longCount(addr.getSection(), segmentCount));
    }

    Iterator<IPv4Address> prefixIterator(IPv4Address original, AddressCreator<IPv4Address, ?, ?, IPv4AddressSegment> creator, boolean isBlockIterator) {
        Integer prefLength = this.getPrefixLength();
        if (prefLength == null || prefLength > this.getBitCount()) {
            return this.iterator(original, creator, null);
        }
        return this.prefixIterator(original, creator, isBlockIterator, prefLength);
    }

    AddressComponentSpliterator<IPv4Address> prefixSpliterator(IPv4Address original, IPv4AddressNetwork.IPv4AddressCreator creator, boolean isBlockIterator) {
        Integer prefLength = this.getPrefixLength();
        if (prefLength == null || prefLength > this.getBitCount()) {
            return this.spliterator(original, creator, false);
        }
        return this.prefixSpliterator(original, creator, isBlockIterator, prefLength);
    }

    AddressComponentSpliterator<IPv4Address> prefixSpliterator(IPv4Address original, IPv4AddressNetwork.IPv4AddressCreator creator, boolean isBlockIterator, int prefixLength) {
        if (prefixLength > this.getBitCount() || prefixLength < 0) {
            throw new PrefixLenException((AddressItem)original, prefixLength);
        }
        Integer prefLength = IPv4AddressSection.cacheBits(prefixLength);
        int networkSegIndex = IPv4AddressSection.getNetworkSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int hostSegIndex = IPv4AddressSection.getHostSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        return IPv4AddressSection.createSeriesSpliterator(original.setPrefixLength(prefixLength, false), spliterator -> IPv4AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv4Address)IPv4AddressSection.createIteratedAddress((AddressSegment[])segs, (AddressCreator)creator, (Integer)prefLength), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv4Address)spliterator.getAddressItem()).getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)prefLength), isBlockIterator ? (isLowest, isHighest, addr) -> addr.prefixBlockIterator() : (!this.isSequential() ? (isLowest, isHighest, addr) -> addr.prefixIterator() : (isLowest, isHighest, addr) -> isLowest || isHighest ? addr.prefixIterator() : addr.prefixBlockIterator()), null, null, addr -> IPv4AddressSection.longPrefixCount(addr.getSection(), prefixLength));
    }

    Iterator<IPv4Address> prefixIterator(IPv4Address original, AddressCreator<IPv4Address, ?, ?, IPv4AddressSegment> creator, boolean isBlockIterator, int prefLength) {
        boolean useOriginal;
        if (prefLength > this.getBitCount() || prefLength < 0) {
            throw new PrefixLenException((AddressItem)original, prefLength);
        }
        boolean bl = isBlockIterator ? this.containsSinglePrefixBlock(prefLength) : (useOriginal = this.longPrefixCount(prefLength) == 1L);
        if (useOriginal) {
            original = original.setPrefixLength(prefLength, false);
        }
        int networkSegIndex = IPv4AddressSection.getNetworkSegmentIndex(prefLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int hostSegIndex = IPv4AddressSection.getHostSegmentIndex(prefLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int segCount = this.getSegmentCount();
        return IPv4AddressSection.iterator(useOriginal, original, creator, useOriginal ? null : IPv4AddressSection.segmentsIterator(segCount, creator, null, index -> this.getSegment(index).iterator(), null, networkSegIndex, hostSegIndex, isBlockIterator ? index -> this.getSegment(index).prefixBlockIterator() : index -> this.getSegment(index).prefixIterator()), IPv4AddressSection.cacheBits(prefLength));
    }

    Iterator<IPv4Address> blockIterator(IPv4Address original, AddressCreator<IPv4Address, ?, ?, IPv4AddressSegment> creator, int segmentCount) {
        if (segmentCount < 0) {
            throw new IllegalArgumentException();
        }
        if (segmentCount > this.getSegmentCount()) {
            return this.iterator(original, creator, null);
        }
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        boolean useOriginal = !this.isMultiple(segmentCount);
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        int segCount = this.getSegmentCount();
        return IPv4AddressSection.iterator(useOriginal, original, creator, useOriginal ? null : IPv4AddressSection.segmentsIterator(segCount, creator, null, index -> this.getSegment(index).iterator(!isAllSubnets), null, networkSegIndex, hostSegIndex, index -> this.getSegment(index).identityIterator()), isAllSubnets ? null : this.getPrefixLength());
    }

    AddressComponentSpliterator<IPv4Address> blockSpliterator(IPv4Address original, IPv4AddressNetwork.IPv4AddressCreator creator, int segmentCount) {
        IPv4Address forIteration;
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
        return IPv4AddressSection.createSeriesSpliterator(forIteration, spliterator -> IPv4AddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (IPv4Address)IPv4AddressSection.createIteratedAddress((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((IPv4Address)spliterator.getAddressItem()).getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), (isLowest, isHighest, addr) -> addr.blockIterator(segmentCount), null, null, addr -> IPv4AddressSection.longCount(addr.getSection(), segmentCount));
    }

    protected boolean isZeroHost(IPv4AddressSegment[] segments, int prefixLength) {
        return super.isZeroHost((IPAddressSegment[])segments, prefixLength);
    }

    private static long getMaxValue(int segmentCount) {
        return MAX_VALUES[segmentCount];
    }

    @Override
    public IPv4AddressSection incrementBoundary(long increment) {
        if (increment <= 0L) {
            if (increment == 0L) {
                return this;
            }
            return this.getLower().increment(increment);
        }
        return this.getUpper().increment(increment);
    }

    @Override
    public IPv4AddressSection increment(long increment) {
        if (increment == 0L && !this.isMultiple()) {
            return this;
        }
        long lowerValue = 0xFFFFFFFFL & (long)this.intValue();
        long upperValue = 0xFFFFFFFFL & (long)this.upperIntValue();
        long count = this.getCount().longValue();
        IPv4AddressSection.checkOverflow(increment, lowerValue, upperValue, count, () -> IPv4AddressSection.getMaxValue(this.getSegmentCount()));
        return IPv4AddressSection.increment(this, increment, this.getAddressCreator(), count, lowerValue, upperValue, this::getLower, this::getUpper, this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() ? null : this.getPrefixLength());
    }

    public long getIPv4Count(boolean excludeZeroHosts) {
        return excludeZeroHosts && this.includesZeroHost() ? this.longZeroHostCount(this.getNetworkPrefixLength(), this.getSegmentCount()) : this.longCount(this.getSegmentCount());
    }

    @Override
    protected BigInteger getCountImpl(int segCount) {
        if (!this.isMultiple()) {
            return BigInteger.ONE;
        }
        return BigInteger.valueOf(IPv4AddressSection.longCount(this, segCount));
    }

    @Override
    protected BigInteger getZeroHostCountImpl(int prefixLength, int segCount) {
        if (this.includesZeroHost(prefixLength)) {
            if (this.isMultiple()) {
                return BigInteger.valueOf(this.longZeroHostCount(prefixLength, segCount));
            }
            return BigInteger.ONE;
        }
        return BigInteger.ZERO;
    }

    public long getIPv4PrefixCount(int prefixLength) {
        IPv4AddressSection.checkSubnet(this, prefixLength);
        return this.longPrefixCount(prefixLength);
    }

    @Override
    public BigInteger getPrefixCount(int prefixLength) {
        return BigInteger.valueOf(this.getIPv4PrefixCount(prefixLength));
    }

    public long getIPv4PrefixCount() {
        Integer prefixLength = this.getPrefixLength();
        if (prefixLength == null || prefixLength >= this.getBitCount()) {
            return this.getIPv4Count(false);
        }
        return this.getIPv4PrefixCount(prefixLength);
    }

    @Override
    protected BigInteger getPrefixCountImpl() {
        return BigInteger.valueOf(this.getIPv4PrefixCount());
    }

    private IPv4AddressNetwork.IPv4AddressCreator getSegmentCreator() {
        return this.getIPv4SegmentCreator();
    }

    private IPv4AddressNetwork.IPv4AddressCreator getAddressCreator() {
        return this.getIPv4SegmentCreator();
    }

    private IPv4AddressNetwork.IPv4AddressCreator getIPv4SegmentCreator() {
        return this.getNetwork().getAddressCreator();
    }

    @Override
    public IPv4AddressSegment getDivision(int index) {
        return (IPv4AddressSegment)super.getDivision(index);
    }

    @Override
    public IPv4AddressSegment getSegment(int index) {
        return (IPv4AddressSegment)super.getSegment(index);
    }

    public void getSegments(Collection<? super IPv4AddressSegment> segs) {
        this.getSegments(0, this.getSegmentCount(), segs);
    }

    public void getSegments(int start2, int end, Collection<? super IPv4AddressSegment> segs) {
        for (int i = start2; i < end; ++i) {
            segs.add(this.getSegment(i));
        }
    }

    @Override
    public int getBitsPerSegment() {
        return 8;
    }

    @Override
    public int getBytesPerSegment() {
        return 1;
    }

    @Override
    public int getBitCount() {
        return this.getSegmentCount() << 3;
    }

    @Override
    public int getByteCount() {
        return this.getSegmentCount();
    }

    @Override
    protected byte[] getBytesImpl(boolean low) {
        int segmentCount = this.getSegmentCount();
        byte[] bytes = new byte[segmentCount];
        for (int i = 0; i < segmentCount; ++i) {
            IPv4AddressSegment seg = this.getSegment(i);
            int val = low ? seg.getSegmentValue() : seg.getUpperSegmentValue();
            bytes[i] = (byte)val;
        }
        return bytes;
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
    public boolean matchesWithMask(IPAddressSection other, IPAddressSection mask) {
        return other instanceof IPv4AddressSection && mask instanceof IPv4AddressSection && super.matchesWithMask(other, mask);
    }

    @Override
    protected boolean isSameGrouping(AddressDivisionGroupingBase other) {
        return other instanceof IPv4AddressSection && super.isSameGrouping(other);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof IPv4AddressSection && ((IPv4AddressSection)o).isSameGrouping(this);
    }

    @Override
    public boolean contains(AddressSection other) {
        return other instanceof IPv4AddressSection && super.contains(other);
    }

    @Override
    protected boolean containsNonZeroHostsImpl(IPAddressSection other, int otherPrefixLength) {
        if (other instanceof IPv4AddressSection) {
            IPv4AddressSection[] remaining = ((IPv4AddressSection)other).subtract(this);
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
    public boolean prefixEquals(AddressSection other) {
        return other == this || other instanceof IPv4AddressSection && IPv4AddressSection.prefixEquals(this, other, 0);
    }

    @Override
    public boolean prefixContains(IPAddressSection other) {
        return other == this || other instanceof IPv4AddressSection && IPv4AddressSection.prefixContains(this, other, 0);
    }

    public IPv4AddressSection append(IPv4AddressSection other) {
        int count = this.getSegmentCount();
        return this.replace(count, count, other, 0, other.getSegmentCount());
    }

    public IPv4AddressSection insert(int index, IPv4AddressSection other) {
        return this.replace(index, index, other, 0, other.getSegmentCount());
    }

    public IPv4AddressSection replace(int index, IPv4AddressSection other) {
        return this.replace(index, index + other.getSegmentCount(), other, 0, other.getSegmentCount());
    }

    public IPv4AddressSection appendToNetwork(IPv4AddressSection other) {
        Integer prefixLength = this.getNetworkPrefixLength();
        if (prefixLength == null) {
            return this.append(other);
        }
        IPv4AddressSection thizz = this;
        int bitsPerSegment = this.getBitsPerSegment();
        int adjustment = prefixLength % bitsPerSegment;
        if (adjustment != 0) {
            prefixLength = prefixLength + (bitsPerSegment - adjustment);
            thizz = this.setPrefixLength(prefixLength, false);
        }
        int index = prefixLength >>> 3;
        if (other.isPrefixed() && other.getPrefixLength() == 0) {
            return this.insert(index, other);
        }
        return thizz.replace(index, index, other, 0, other.getSegmentCount(), true);
    }

    public IPv4AddressSection replace(int startIndex, int endIndex, IPv4AddressSection replacement, int replacementStartIndex, int replacementEndIndex) {
        return this.replace(startIndex, endIndex, replacement, replacementStartIndex, replacementEndIndex, false);
    }

    private IPv4AddressSection replace(int startIndex, int endIndex, IPv4AddressSection replacement, int replacementStartIndex, int replacementEndIndex, boolean appendNetwork) {
        int segmentCount = this.getSegmentCount();
        int replacedCount = endIndex - startIndex;
        int replacementCount = replacementEndIndex - replacementStartIndex;
        if (replacedCount < 0 || replacementCount < 0 || startIndex < 0 || replacementStartIndex < 0 || replacementEndIndex > replacement.getSegmentCount() || endIndex > segmentCount) {
            throw new IndexOutOfBoundsException();
        }
        IPv4AddressSection thizz = this;
        if (segmentCount + replacementCount - replacedCount > 4) {
            throw new AddressValueException(this, replacement, segmentCount + replacementCount - replacedCount);
        }
        if (replacementCount == 0 && replacedCount == 0) {
            return this;
        }
        if (segmentCount == replacedCount) {
            return replacement;
        }
        if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            if (appendNetwork) {
                thizz = this.withoutPrefixLength();
                int replacementEndBits = replacementEndIndex << 3;
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
                    int replacementEndBits = replacementEndIndex << 3;
                    if (!replacement.isPrefixed() || replacement.getNetworkPrefixLength() > replacementEndBits) {
                        replacement = replacement.setPrefixLength(replacementEndBits, false);
                    }
                }
            } else if (prefixLength != null && !appendNetwork && prefixLength <= startIndex << 3) {
                replacement = replacement.setPrefixLength(0, false);
            } else if (endIndex < segmentCount) {
                int replacementEndBits = replacementEndIndex << 3;
                if (replacement.isPrefixed() && replacement.getNetworkPrefixLength() <= replacementEndBits) {
                    int thisNextIndexBits = endIndex << 3;
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
        return IPv4AddressSection.replace(thizz, startIndex, endIndex, replacement, replacementStartIndex, replacementEndIndex, this.getAddressCreator(), appendNetwork, false);
    }

    public IPv4AddressSection intersect(IPv4AddressSection other) throws SizeMismatchException {
        return IPv4AddressSection.intersect(this, other, this.getAddressCreator(), this::getSegment, other::getSegment);
    }

    public IPv4AddressSection[] subtract(IPv4AddressSection other) throws SizeMismatchException {
        return (IPv4AddressSection[])IPv4AddressSection.subtract((IPAddressSection)this, (IPAddressSection)other, (IPAddressNetwork.IPAddressCreator)this.getAddressCreator(), this::getSegment, (R section, int prefix) -> section.setPrefixLength(prefix, false, true));
    }

    @Override
    public IPv4AddressNetwork getNetwork() {
        return Address.defaultIpv4Network();
    }

    @Override
    public IPv4AddressSection adjustPrefixBySegment(boolean nextSegment) {
        return this.adjustPrefixBySegment(nextSegment, true);
    }

    @Override
    public IPv4AddressSection adjustPrefixBySegment(boolean nextSegment, boolean zeroed) {
        return (IPv4AddressSection)super.adjustPrefixBySegment(nextSegment, zeroed);
    }

    @Override
    public IPv4AddressSection adjustPrefixLength(int adjustment) {
        return this.adjustPrefixLength(adjustment, true);
    }

    @Override
    public IPv4AddressSection adjustPrefixLength(int adjustment, boolean zeroed) {
        return (IPv4AddressSection)IPv4AddressSection.adjustPrefixLength(this, adjustment, zeroed, this.getAddressCreator(), (section, i) -> section.getSegment(i));
    }

    @Override
    @Deprecated
    public IPv4AddressSection applyPrefixLength(int networkPrefixLength) {
        return this.setPrefixLength(networkPrefixLength, true, true, true);
    }

    @Override
    public IPv4AddressSection setPrefixLength(int networkPrefixLength) {
        return this.setPrefixLength(networkPrefixLength, true, false, true);
    }

    @Override
    public IPv4AddressSection setPrefixLength(int networkPrefixLength, boolean withZeros) {
        return this.setPrefixLength(networkPrefixLength, withZeros, false, true);
    }

    @Override
    public IPv4AddressSection setPrefixLength(int networkPrefixLength, boolean withZeros, boolean zeroHostIsBlock) throws PrefixLenException {
        return this.setPrefixLength(networkPrefixLength, withZeros, false, zeroHostIsBlock);
    }

    private IPv4AddressSection setPrefixLength(int networkPrefixLength, boolean withZeros, boolean noShrink, boolean zeroHostIsBlock) {
        return IPv4AddressSection.setPrefixLength(this, this.getAddressCreator(), networkPrefixLength, withZeros, noShrink, !zeroHostIsBlock, (section, i) -> section.getSegment(i));
    }

    @Override
    @Deprecated
    public IPv4AddressSection removePrefixLength() {
        return this.removePrefixLength(true);
    }

    @Override
    public IPv4AddressSection withoutPrefixLength() {
        return this.removePrefixLength(false);
    }

    @Override
    @Deprecated
    public IPv4AddressSection removePrefixLength(boolean zeroed) {
        return IPv4AddressSection.removePrefixLength(this, zeroed, this.getAddressCreator(), IPv4AddressSection::getSegment);
    }

    @Override
    public IPv4AddressSection toZeroHost() throws IncompatibleAddressException {
        if (!this.isPrefixed()) {
            IPv4AddressNetwork network;
            AddressNetwork.PrefixConfiguration config = (network = this.getNetwork()).getPrefixConfiguration();
            IPv4Address networkMask = (IPv4Address)network.getNetworkMask(0, !config.allPrefixedAddressesAreSubnets());
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

    IPv4AddressSection createZeroHost(boolean boundariesOnly) {
        int prefixLength = this.getNetworkPrefixLength();
        IPv4AddressNetwork network = this.getNetwork();
        IPv4Address mask = (IPv4Address)network.getNetworkMask(prefixLength);
        return IPv4AddressSection.getSubnetSegments(this, network.getPrefixConfiguration().allPrefixedAddressesAreSubnets() ? null : IPv4AddressSection.cacheBits(prefixLength), this.getAddressCreator(), !boundariesOnly, this::getSegment, i -> mask.getSegment(i).getSegmentValue(), true);
    }

    @Override
    public IPv4AddressSection toZeroHost(int prefixLength) {
        if (this.isPrefixed() && prefixLength == this.getNetworkPrefixLength()) {
            return this.toZeroHost();
        }
        IPv4Address mask = (IPv4Address)this.getNetwork().getNetworkMask(prefixLength);
        return IPv4AddressSection.getSubnetSegments(this, null, this.getAddressCreator(), false, this::getSegment, i -> mask.getSegment(i).getSegmentValue(), true);
    }

    @Override
    public IPv4AddressSection toZeroNetwork() {
        if (!this.isPrefixed()) {
            IPv4Address hostMask = (IPv4Address)this.getNetwork().getHostMask(this.getBitCount());
            return hostMask.getSection(0, this.getSegmentCount());
        }
        return this.createZeroNetwork();
    }

    IPv4AddressSection createZeroNetwork() {
        Integer prefixLength = this.getNetworkPrefixLength();
        IPv4Address mask = (IPv4Address)this.getNetwork().getHostMask(prefixLength);
        return IPv4AddressSection.getSubnetSegments(this, prefixLength, this.getAddressCreator(), false, this::getSegment, i -> mask.getSegment(i).getSegmentValue(), true);
    }

    @Override
    public IPv4AddressSection toMaxHost() throws IncompatibleAddressException {
        if (!this.isPrefixed()) {
            IPv4Address resultNoPrefix = (IPv4Address)this.getNetwork().getHostMask(0);
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

    public IPv4AddressSection createMaxHost() {
        Integer prefixLength = this.getNetworkPrefixLength();
        IPv4Address mask = (IPv4Address)this.getNetwork().getHostMask(prefixLength);
        return IPv4AddressSection.getOredSegments(this, this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() ? null : prefixLength, this.getAddressCreator(), false, this::getSegment, i -> mask.getSegment(i).getSegmentValue());
    }

    @Override
    public IPv4AddressSection toMaxHost(int prefixLength) {
        if (this.isPrefixed() && prefixLength == this.getNetworkPrefixLength()) {
            return this.toMaxHost();
        }
        IPv4Address mask = (IPv4Address)this.getNetwork().getHostMask(prefixLength);
        return IPv4AddressSection.getOredSegments(this, null, this.getAddressCreator(), false, this::getSegment, i -> mask.getSegment(i).getSegmentValue());
    }

    public IPv4AddressSection mask(IPv4AddressSection mask, boolean retainPrefix) throws IncompatibleAddressException, SizeMismatchException {
        this.checkMaskSectionCount(mask);
        return IPv4AddressSection.getSubnetSegments(this, retainPrefix ? this.getPrefixLength() : null, this.getAddressCreator(), true, this::getSegment, i -> mask.getSegment(i).getSegmentValue(), false);
    }

    public IPv4AddressSection mask(IPv4AddressSection mask) throws IncompatibleAddressException, SizeMismatchException {
        return this.mask(mask, false);
    }

    public IPv4AddressSection maskNetwork(IPv4AddressSection mask, int networkPrefixLength) throws IncompatibleAddressException, PrefixLenException, SizeMismatchException {
        this.checkMaskSectionCount(mask);
        IPv4AddressSection hostMask = (IPv4AddressSection)this.getNetwork().getHostMaskSection(networkPrefixLength);
        return IPv4AddressSection.getSubnetSegments(this, IPv4AddressSection.cacheBits(networkPrefixLength), this.getAddressCreator(), true, this::getSegment, i -> {
            int val1 = mask.getSegment(i).getSegmentValue();
            int val2 = hostMask.getSegment(i).getSegmentValue();
            return val1 | val2;
        }, false);
    }

    protected static Integer cacheBits(int i) {
        return IPAddressSection.cacheBits(i);
    }

    public IPv4AddressSection bitwiseOr(IPv4AddressSection mask) throws IncompatibleAddressException {
        return this.bitwiseOr(mask, false);
    }

    public IPv4AddressSection bitwiseOr(IPv4AddressSection mask, boolean retainPrefix) throws IncompatibleAddressException, SizeMismatchException {
        this.checkMaskSectionCount(mask);
        return IPv4AddressSection.getOredSegments(this, retainPrefix ? this.getPrefixLength() : null, this.getAddressCreator(), true, this::getSegment, i -> mask.getSegment(i).getSegmentValue());
    }

    public IPv4AddressSection bitwiseOrNetwork(IPv4AddressSection mask, int networkPrefixLength) throws IncompatibleAddressException, SizeMismatchException {
        this.checkMaskSectionCount(mask);
        IPv4AddressSection networkMask = (IPv4AddressSection)this.getNetwork().getNetworkMaskSection(networkPrefixLength);
        return IPv4AddressSection.getOredSegments(this, IPv4AddressSection.cacheBits(networkPrefixLength), this.getAddressCreator(), true, this::getSegment, i -> {
            int val1 = mask.getSegment(i).getSegmentValue();
            int val2 = networkMask.getSegment(i).getSegmentValue();
            return val1 & val2;
        });
    }

    @Override
    public IPv4AddressSection getHostMask() {
        return (IPv4AddressSection)super.getHostMask();
    }

    @Override
    public IPv4AddressSection getNetworkMask() {
        return (IPv4AddressSection)super.getNetworkMask();
    }

    @Override
    public IPv4AddressSection getNetworkSection() {
        if (this.isPrefixed()) {
            return this.getNetworkSection(this.getNetworkPrefixLength());
        }
        return this.getNetworkSection(this.getBitCount());
    }

    @Override
    public IPv4AddressSection getNetworkSection(int networkPrefixLength) throws PrefixLenException {
        return this.getNetworkSection(networkPrefixLength, true);
    }

    @Override
    public IPv4AddressSection getNetworkSection(int networkPrefixLength, boolean withPrefixLength) throws PrefixLenException {
        return IPv4AddressSection.getNetworkSection(this, networkPrefixLength, withPrefixLength, this.getAddressCreator(), (prefix, i) -> this.getSegment(i).toNetworkSegment((Integer)prefix, withPrefixLength));
    }

    @Override
    public IPv4AddressSection getHostSection() {
        if (this.isPrefixed()) {
            return this.getHostSection(this.getNetworkPrefixLength());
        }
        return this.getHostSection(0);
    }

    @Override
    public IPv4AddressSection getHostSection(int networkPrefixLength) throws PrefixLenException {
        int hostSegmentCount = this.getHostSegmentCount(networkPrefixLength);
        return IPv4AddressSection.getHostSection(this, networkPrefixLength, hostSegmentCount, this.getAddressCreator(), (prefix, i) -> this.getSegment(i).toHostSegment((Integer)prefix));
    }

    @Override
    public IPv4AddressSection toPrefixBlock() {
        Integer prefixLength = this.getNetworkPrefixLength();
        if (prefixLength == null || this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            return this;
        }
        return this.toPrefixBlock(prefixLength);
    }

    @Override
    public IPv4AddressSection toPrefixBlock(int networkPrefixLength) throws PrefixLenException {
        return IPv4AddressSection.toPrefixBlock(this, networkPrefixLength, this.getAddressCreator(), (prefix, i) -> this.getSegment(i).toNetworkSegment((Integer)prefix, true));
    }

    @Override
    public IPv4AddressSection assignPrefixForSingleBlock() {
        return (IPv4AddressSection)super.assignPrefixForSingleBlock();
    }

    @Override
    public IPv4AddressSection assignMinPrefixForBlock() {
        return (IPv4AddressSection)super.assignMinPrefixForBlock();
    }

    @Override
    public IPv4AddressSection coverWithPrefixBlock() {
        return (IPv4AddressSection)IPv4AddressSection.coverWithPrefixBlock(this, this.getLower(), this.getUpper());
    }

    public IPv4AddressSection coverWithPrefixBlock(IPv4AddressSection other) throws AddressConversionException {
        this.checkSectionCount(other);
        return IPv4AddressSection.coverWithPrefixBlock(this, other, IPv4AddressSection::getLower, IPv4AddressSection::getUpper, Address.ADDRESS_LOW_VALUE_COMPARATOR::compare);
    }

    protected static <T extends IPAddressSegmentSeries> T coverWithPrefixBlock(T first, T other, UnaryOperator<T> getLower, UnaryOperator<T> getUpper, Comparator<T> comparator) throws AddressConversionException {
        return IPAddressSection.coverWithPrefixBlock(first, other, getLower, getUpper, comparator);
    }

    protected static IPAddressSegmentSeries coverWithPrefixBlock(IPAddressSegmentSeries orig, IPAddressSegmentSeries lower, IPAddressSegmentSeries upper) {
        return IPAddressSection.coverWithPrefixBlock(orig, lower, upper);
    }

    public IPv4AddressSection[] spanWithPrefixBlocks() {
        if (this.isSequential()) {
            if (this.isSinglePrefixBlock()) {
                return new IPv4AddressSection[]{this};
            }
            return this.spanWithPrefixBlocks(this);
        }
        ArrayList list = (ArrayList)this.spanWithBlocks(true);
        return list.toArray(new IPv4AddressSection[list.size()]);
    }

    public IPv4AddressSection[] spanWithPrefixBlocks(IPv4AddressSection other) {
        return (IPv4AddressSection[])IPv4AddressSection.getSpanningPrefixBlocks((IPAddressSection)this, (IPAddressSection)other, IPv4AddressSection::getLower, IPv4AddressSection::getUpper, Address.ADDRESS_LOW_VALUE_COMPARATOR::compare, IPv4AddressSection::assignPrefixForSingleBlock, IPv4AddressSection::withoutPrefixLength, this.getAddressCreator()::createSectionArray);
    }

    @Deprecated
    public IPv4AddressSection[] spanWithRangedSegments(IPv4AddressSection other) {
        return this.spanWithSequentialBlocks(other);
    }

    public IPv4AddressSection[] spanWithSequentialBlocks() throws AddressConversionException {
        if (this.isSequential()) {
            return new IPv4AddressSection[]{this.withoutPrefixLength()};
        }
        ArrayList list = (ArrayList)this.spanWithBlocks(false);
        return list.toArray(new IPv4AddressSection[list.size()]);
    }

    public IPv4AddressSection[] spanWithSequentialBlocks(IPv4AddressSection other) {
        return (IPv4AddressSection[])IPv4AddressSection.getSpanningSequentialBlocks((IPAddressSection)this, (IPAddressSection)other, IPv4AddressSection::getLower, IPv4AddressSection::getUpper, Address.ADDRESS_LOW_VALUE_COMPARATOR::compare, IPv4AddressSection::withoutPrefixLength, (IPAddressNetwork.IPAddressCreator)this.getAddressCreator());
    }

    @Deprecated
    public IPv4AddressSection[] mergePrefixBlocks(IPv4AddressSection ... sections) throws SizeMismatchException {
        return this.mergeToPrefixBlocks(sections);
    }

    public IPv4AddressSection[] mergeToPrefixBlocks(IPv4AddressSection ... sections) throws SizeMismatchException {
        this.checkSectionsMergeable(sections);
        IPAddressSegmentSeries[] converted = this.getCloned(sections);
        List<IPAddressSegmentSeries> blocks = IPv4AddressSection.getMergedPrefixBlocks(converted);
        return blocks.toArray(new IPv4AddressSection[blocks.size()]);
    }

    private IPv4AddressSection[] getCloned(IPv4AddressSection ... sections) {
        IPv4AddressSection[] converted = new IPv4AddressSection[sections.length + 1];
        System.arraycopy(sections, 0, converted, 1, sections.length);
        converted[0] = this;
        return converted;
    }

    private void checkSectionsMergeable(IPv4AddressSection[] sections) {
        for (int i = 0; i < sections.length; ++i) {
            IPv4AddressSection section = sections[i];
            if (section == null || section.getSegmentCount() == this.getSegmentCount()) continue;
            throw new SizeMismatchException(this, section);
        }
    }

    public IPv4AddressSection[] mergeToSequentialBlocks(IPv4AddressSection ... sections) throws SizeMismatchException {
        this.checkSectionsMergeable(sections);
        IPAddressSegmentSeries[] converted = this.getCloned(sections);
        List<IPAddressSegmentSeries> blocks = IPv4AddressSection.getMergedSequentialBlocks(converted, this.getAddressCreator()::createSequentialBlockSection);
        return blocks.toArray(new IPv4AddressSection[blocks.size()]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected boolean hasNoStringCache() {
        if (this.stringCache == null) {
            IPv4AddressSection iPv4AddressSection = this;
            synchronized (iPv4AddressSection) {
                if (this.stringCache == null) {
                    this.stringCache = new IPv4StringCache();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected IPv4StringCache getStringCache() {
        return this.stringCache;
    }

    @Override
    public String toCanonicalString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.canonicalString) == null) {
            this.stringCache.canonicalString = result = this.toNormalizedString(IPv4StringCache.canonicalParams);
        }
        return result;
    }

    @Override
    public String toFullString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.fullString) == null) {
            this.stringCache.fullString = result = this.toNormalizedString(IPv4StringCache.fullParams);
        }
        return result;
    }

    @Override
    public String toCompressedString() {
        return this.toCanonicalString();
    }

    @Override
    public String toNormalizedString() {
        return this.toCanonicalString();
    }

    @Override
    protected void cacheNormalizedString(String str) {
        if (this.hasNoStringCache() || this.stringCache.canonicalString == null) {
            this.stringCache.canonicalString = str;
        }
    }

    @Override
    public String toCompressedWildcardString() {
        return this.toNormalizedWildcardString();
    }

    @Override
    public String toSubnetString() {
        return this.toNormalizedWildcardString();
    }

    @Override
    public String toPrefixLengthString() {
        return this.toCanonicalString();
    }

    public String toInetAtonString(IPv4Address.inet_aton_radix radix) {
        String result;
        if (radix == IPv4Address.inet_aton_radix.OCTAL) {
            if (this.hasNoStringCache() || (result = this.stringCache.octalString) == null) {
                this.stringCache.octalString = result = this.toNormalizedString(IPv4StringCache.inetAtonOctalParams);
            }
        } else if (radix == IPv4Address.inet_aton_radix.HEX) {
            if (this.hasNoStringCache() || (result = this.stringCache.hexString) == null) {
                this.stringCache.hexString = result = this.toNormalizedString(IPv4StringCache.inetAtonHexParams);
            }
        } else {
            result = this.toCanonicalString();
        }
        return result;
    }

    public String toInetAtonString(IPv4Address.inet_aton_radix radix, int joinedCount) throws IncompatibleAddressException {
        if (joinedCount <= 0) {
            return this.toInetAtonString(radix);
        }
        IPAddressSection.IPStringOptions stringParams = radix == IPv4Address.inet_aton_radix.OCTAL ? IPv4StringCache.inetAtonOctalParams : (radix == IPv4Address.inet_aton_radix.HEX ? IPv4StringCache.inetAtonHexParams : IPv4StringCache.canonicalParams);
        return this.toNormalizedString(stringParams, joinedCount);
    }

    @Override
    public String toNormalizedWildcardString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.normalizedWildcardString) == null) {
            this.stringCache.normalizedWildcardString = result = this.toNormalizedString(IPv4StringCache.normalizedWildcardParams);
        }
        return result;
    }

    @Override
    public String toCanonicalWildcardString() {
        return this.toNormalizedWildcardString();
    }

    @Override
    public String toSQLWildcardString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.sqlWildcardString) == null) {
            this.stringCache.sqlWildcardString = result = this.toNormalizedString(IPv4StringCache.sqlWildcardParams);
        }
        return result;
    }

    @Override
    public String toReverseDNSLookupString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.reverseDNSString) == null) {
            this.stringCache.reverseDNSString = result = this.toNormalizedString(IPv4StringCache.reverseDNSParams);
        }
        return result;
    }

    @Override
    public String toSegmentedBinaryString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.segmentedBinaryString) == null) {
            this.stringCache.segmentedBinaryString = result = this.toNormalizedString(IPv4StringCache.segmentedBinaryParams);
        }
        return result;
    }

    public String toNormalizedString(IPAddressSection.IPStringOptions stringParams, int joinCount) throws IncompatibleAddressException {
        if (joinCount <= 0) {
            return this.toNormalizedString(stringParams);
        }
        int thisCount = this.getSegmentCount();
        if (thisCount <= 1) {
            return this.toNormalizedString(stringParams);
        }
        IPAddressDivisionGrouping equivalentPart = this.toJoinedSegments(joinCount);
        return IPv4AddressSection.toNormalizedString(stringParams, equivalentPart);
    }

    public IPAddressDivisionGrouping toJoinedSegments(int joinCount) {
        int totalCount;
        int thisCount = this.getSegmentCount();
        if (joinCount <= 0 || thisCount <= 1) {
            return this;
        }
        if (joinCount >= thisCount) {
            joinCount = thisCount - 1;
            totalCount = 1;
        } else {
            totalCount = thisCount - joinCount;
        }
        int notJoinedCount = totalCount - 1;
        IPAddressDivision[] segs = new IPAddressDivision[totalCount];
        for (int i = 0; i < notJoinedCount; ++i) {
            segs[i] = this.getDivision(i);
        }
        IPv4JoinedSegments joinedSegment = this.joinSegments(joinCount);
        segs[notJoinedCount] = joinedSegment;
        IPAddressDivisionGrouping equivalentPart = new IPAddressDivisionGrouping(segs, this.getNetwork());
        return equivalentPart;
    }

    private IPv4JoinedSegments joinSegments(int joinCount) {
        long lower = 0L;
        long upper = 0L;
        int networkPrefixLength = 0;
        Integer prefix = null;
        int firstSegIndex = 0;
        IPv4AddressSegment firstRange = null;
        int firstJoinedIndex = this.getSegmentCount() - 1 - joinCount;
        for (int j = 0; j <= joinCount; ++j) {
            IPv4AddressSegment thisSeg = this.getSegment(firstJoinedIndex + j);
            if (firstRange != null) {
                if (!thisSeg.isFullRange()) {
                    throw new IncompatibleAddressException(firstRange, firstSegIndex, thisSeg, firstJoinedIndex + j, "ipaddress.error.segmentMismatch");
                }
            } else if (thisSeg.isMultiple()) {
                firstSegIndex = firstJoinedIndex + j;
                firstRange = thisSeg;
            }
            lower = lower << this.getBitsPerSegment() | (long)thisSeg.getSegmentValue();
            upper = upper << this.getBitsPerSegment() | (long)thisSeg.getUpperSegmentValue();
            if (prefix != null) continue;
            Integer thisSegPrefix = thisSeg.getSegmentPrefixLength();
            if (thisSegPrefix != null) {
                prefix = IPv4AddressSection.cacheBits(networkPrefixLength + thisSegPrefix);
                continue;
            }
            networkPrefixLength += thisSeg.getBitCount();
        }
        IPv4JoinedSegments joinedSegment = new IPv4JoinedSegments(joinCount, lower, upper, prefix);
        return joinedSegment;
    }

    @Override
    public IPAddressPartStringCollection toAllStringCollection() {
        return this.toStringCollection(IPv4StringBuilderOptions.ALL_OPTS);
    }

    @Override
    public IPAddressPartStringCollection toStandardStringCollection() {
        return this.toStringCollection(IPv4StringBuilderOptions.STANDARD_OPTS);
    }

    @Override
    public IPAddressPartStringCollection toDatabaseSearchStringCollection() {
        return this.toStringCollection(IPv4StringBuilderOptions.DATABASE_SEARCH_OPTS);
    }

    @Override
    public IPAddressPartStringCollection toStringCollection(IPAddressSection.IPStringBuilderOptions opts) {
        return this.toStringCollection(IPv4StringBuilderOptions.from(opts));
    }

    public IPAddressPartStringCollection toStringCollection(IPv4StringBuilderOptions opts) {
        IPAddressStringDivisionSeries[] parts;
        IPv4SectionStringCollection collection = new IPv4SectionStringCollection();
        for (IPAddressStringDivisionSeries part : parts = this.getParts(opts)) {
            IPv4StringCollection.IPv4StringBuilder builder = new IPv4StringCollection.IPv4StringBuilder(part, opts, new IPv4StringCollection.IPv4AddressSectionStringCollection(part));
            IPv4StringCollection.IPv4AddressSectionStringCollection subCollection = (IPv4StringCollection.IPv4AddressSectionStringCollection)builder.getVariations();
            collection.add(subCollection);
        }
        return collection;
    }

    @Override
    public IPAddressStringDivisionSeries[] getParts(IPAddressSection.IPStringBuilderOptions options) {
        return this.getParts(IPv4StringBuilderOptions.from(options));
    }

    public IPAddressStringDivisionSeries[] getParts(IPv4StringBuilderOptions options) {
        if (!options.includesAny(14)) {
            return super.getParts(options);
        }
        ArrayList<IPAddressDivisionGrouping> parts = new ArrayList<IPAddressDivisionGrouping>(4);
        if (options.includes(1)) {
            parts.add(this);
        }
        boolean[] joined = new boolean[4];
        int segmentCount = this.getSegmentCount();
        joined[Math.max((int)3, (int)(segmentCount - 1))] = options.includes(2);
        int n = Math.max(2, Math.min(2, segmentCount - 1));
        joined[n] = joined[n] | options.includes(4);
        int n2 = Math.max(1, Math.min(1, segmentCount - 1));
        joined[n2] = joined[n2] | options.includes(8);
        for (int i = 1; i < joined.length; ++i) {
            if (!joined[i]) continue;
            parts.add(this.toJoinedSegments(i));
        }
        return parts.toArray(new IPAddressStringDivisionSeries[parts.size()]);
    }

    static class IPv4StringCollection
    extends IPAddressPartStringCollection {
        IPv4StringCollection() {
        }

        @Override
        protected void addAll(IPAddressPartStringCollection collections) {
            super.addAll(collections);
        }

        static class IPv4StringBuilder
        extends IPAddressPartStringCollection.AddressPartStringBuilder<IPAddressStringDivisionSeries, IPv4StringParams, IPAddressPartConfiguredString<IPAddressStringDivisionSeries, IPv4StringParams>, IPv4AddressSectionStringCollection, IPv4StringBuilderOptions> {
            private IPv4StringBuilder(IPAddressStringDivisionSeries address, IPv4StringBuilderOptions options, IPv4AddressSectionStringCollection collection) {
                super(address, options, collection);
            }

            public static boolean isDecimalSameAsOctal(IPAddressStringDivisionSeries part) {
                int count = part.getDivisionCount();
                for (int i = 0; i < count; ++i) {
                    IPAddressStringDivision seg = part.getDivision(i);
                    if (seg.isBoundedBy(8)) continue;
                    return false;
                }
                return true;
            }

            @Override
            public void addAllVariations() {
                ArrayList allParams = new ArrayList();
                ArrayList<Integer> radices = new ArrayList<Integer>();
                radices.add(IPv4AddressSection.cacheBits(10));
                if (((IPv4StringBuilderOptions)this.options).includes(512)) {
                    radices.add(IPv4AddressSection.cacheBits(16));
                }
                boolean hasDecimalOctalDups = false;
                if (((IPv4StringBuilderOptions)this.options).includes(256)) {
                    radices.add(IPv4AddressSection.cacheBits(8));
                    hasDecimalOctalDups = ((IPv4StringBuilderOptions)this.options).includes(112) && IPv4Address.inet_aton_radix.OCTAL.getSegmentStrPrefix().equals("0") && IPv4StringBuilder.isDecimalSameAsOctal(this.addressSection);
                }
                Iterator iterator2 = radices.iterator();
                while (iterator2.hasNext()) {
                    boolean allExpandable;
                    int radix = (Integer)iterator2.next();
                    ArrayList<IPv4StringParams> radixParams = new ArrayList<IPv4StringParams>();
                    IPv4StringParams stringParams = new IPv4StringParams(radix);
                    radixParams.add(stringParams);
                    switch (radix) {
                        case 8: {
                            stringParams.setSegmentStrPrefix(IPv4Address.inet_aton_radix.OCTAL.getSegmentStrPrefix());
                            break;
                        }
                        case 16: {
                            stringParams.setSegmentStrPrefix(IPv4Address.inet_aton_radix.HEX.getSegmentStrPrefix());
                        }
                    }
                    if (((IPv4StringBuilderOptions)this.options).includes(48)) {
                        int[] expandables = this.getExpandableSegments(radix);
                        int count = this.addressSection.getDivisionCount();
                        block5: for (int i = 0; i < count; ++i) {
                            int len = radixParams.size();
                            for (int expansionLength = expandables[i]; expansionLength > 0; --expansionLength) {
                                for (int j = 0; j < len; ++j) {
                                    IPv4StringParams clone = (IPv4StringParams)radixParams.get(j);
                                    if (hasDecimalOctalDups && radix == 10) {
                                        boolean isDup = true;
                                        for (int k = 0; k < count; ++k) {
                                            int length;
                                            if (k == i || (length = clone.getExpandedSegmentLength(k)) != 0) continue;
                                            isDup = false;
                                            break;
                                        }
                                        if (isDup) continue;
                                    }
                                    clone = clone.clone();
                                    clone.expandSegment(i, expansionLength, this.addressSection.getDivisionCount());
                                    radixParams.add(clone);
                                }
                                if (!((IPv4StringBuilderOptions)this.options).includes(112)) continue block5;
                            }
                        }
                    } else if (((IPv4StringBuilderOptions)this.options).includes(16) && (allExpandable = this.isExpandable(radix))) {
                        IPv4StringParams expandParams = new IPv4StringParams(10);
                        expandParams.expandSegments(true);
                        radixParams.add(expandParams);
                    }
                    allParams.addAll(radixParams);
                }
                for (int i = 0; i < allParams.size(); ++i) {
                    IPv4StringParams param2 = (IPv4StringParams)allParams.get(i);
                    this.addStringParam(param2);
                }
            }

            @Override
            protected void addStringParam(IPv4StringParams stringParams) {
                super.addStringParam(stringParams);
            }
        }

        static class IPv4AddressSectionStringCollection
        extends IPAddressPartStringSubCollection<IPAddressStringDivisionSeries, IPv4StringParams, IPAddressPartConfiguredString<IPAddressStringDivisionSeries, IPv4StringParams>> {
            IPv4AddressSectionStringCollection(IPAddressStringDivisionSeries addr) {
                super(addr);
            }

            @Override
            public Iterator<IPAddressPartConfiguredString<IPAddressStringDivisionSeries, IPv4StringParams>> iterator() {
                return new IPAddressPartStringSubCollection.IPAddressConfigurableStringIterator(){

                    @Override
                    public IPAddressPartConfiguredString<IPAddressStringDivisionSeries, IPv4StringParams> next() {
                        return new IPAddressPartConfiguredString<IPAddressStringDivisionSeries, IPv4StringParams>(part, (IPv4StringParams)this.iterator.next());
                    }
                };
            }
        }
    }

    private static class IPv4StringParams
    extends AddressDivisionGroupingBase.IPAddressStringParams<IPAddressStringDivisionSeries> {
        IPv4StringParams(int radix) {
            super(radix, Character.valueOf('.'), false);
        }

        @Override
        public IPv4StringParams clone() {
            return (IPv4StringParams)super.clone();
        }
    }

    public static class IPv4StringOptions
    extends IPAddressSection.IPStringOptions {
        protected IPv4StringOptions(int base, boolean expandSegments, IPAddressSection.WildcardOptions.WildcardOption wildcardOption, AddressDivisionGrouping.StringOptions.Wildcards wildcards, String segmentStrPrefix, Character separator, String label, String suffix, boolean reverse, boolean splitDigits, boolean uppercase) {
            super(base, expandSegments, wildcardOption, wildcards, segmentStrPrefix, separator, ' ', label, suffix, reverse, splitDigits, uppercase);
        }

        public static class Builder
        extends IPAddressSection.IPStringOptions.Builder {
            public Builder() {
                this(10, '.');
            }

            protected Builder(int base, char separator) {
                super(base, separator);
            }

            @Override
            public IPv4StringOptions toOptions() {
                return new IPv4StringOptions(this.base, this.expandSegments, this.wildcardOption, this.wildcards, this.segmentStrPrefix, this.separator, this.addrLabel, this.addrSuffix, this.reverse, this.splitDigits, this.uppercase);
            }
        }
    }

    public static class IPv4StringBuilderOptions
    extends IPAddressSection.IPStringBuilderOptions {
        public static final int JOIN_ALL = 2;
        public static final int JOIN_TWO = 4;
        public static final int JOIN_ONE = 8;
        public static final int ALL_JOINS = 14;
        public static final int IPV6_CONVERSIONS = 65536;
        public static final int OCTAL = 256;
        public static final int HEX = 512;
        public final IPv6AddressSection.IPv6StringBuilderOptions ipv6ConverterOptions;
        public final IPv6Address.IPv6AddressConverter converter;
        public static final IPv4StringBuilderOptions STANDARD_OPTS = new IPv4StringBuilderOptions(17);
        public static final IPv4StringBuilderOptions DATABASE_SEARCH_OPTS = new IPv4StringBuilderOptions();
        public static final IPv4StringBuilderOptions ALL_OPTS = new IPv4StringBuilderOptions(66367, null, new IPv6AddressSection.IPv6StringBuilderOptions(3895));

        public IPv4StringBuilderOptions() {
            this.ipv6ConverterOptions = null;
            this.converter = null;
        }

        public IPv4StringBuilderOptions(int options) {
            this(options, null, null);
        }

        public IPv4StringBuilderOptions(int options, IPv6Address.IPv6AddressConverter ipv6AddressConverter, IPv6AddressSection.IPv6StringBuilderOptions ipv6ConverterOptions) {
            super(options | (ipv6ConverterOptions == null ? 0 : 65536));
            if (this.includes(65536)) {
                if (ipv6ConverterOptions == null) {
                    ipv6ConverterOptions = new IPv6AddressSection.IPv6StringBuilderOptions(3863);
                }
                if (ipv6AddressConverter == null) {
                    ipv6AddressConverter = IPAddress.DEFAULT_ADDRESS_CONVERTER;
                }
            }
            this.ipv6ConverterOptions = ipv6ConverterOptions;
            this.converter = ipv6AddressConverter;
        }

        public static IPv4StringBuilderOptions from(IPAddressSection.IPStringBuilderOptions opts) {
            if (opts instanceof IPv4StringBuilderOptions) {
                return (IPv4StringBuilderOptions)opts;
            }
            return new IPv4StringBuilderOptions(opts.options & 0xFFFEFCF1);
        }
    }

    static class IPv4SectionStringCollection
    extends IPAddressPartStringCollection {
        IPv4SectionStringCollection() {
        }

        @Override
        protected void add(IPAddressPartStringSubCollection<?, ?, ? extends IPAddressPartConfiguredString<?, ?>> collection) {
            super.add(collection);
        }

        @Override
        protected void addAll(IPAddressPartStringCollection collections) {
            super.addAll(collections);
        }
    }

    static class EmbeddedIPv4AddressSection
    extends IPv4AddressSection {
        private static final long serialVersionUID = 4L;
        private final IPAddressSection encompassingSection;

        EmbeddedIPv4AddressSection(IPAddressSection encompassingSection, IPv4AddressSegment[] subSegments) {
            super(subSegments, false);
            this.encompassingSection = encompassingSection;
        }

        @Override
        public boolean isPrefixBlock() {
            return this.encompassingSection.isPrefixBlock();
        }
    }

    static class IPv4AddressCache
    extends AddressDivisionGrouping.SectionCache<IPv4Address> {
        IPv4AddressCache() {
        }
    }

    static class IPv4StringCache
    extends IPAddressSection.IPStringCache {
        static final IPAddressSection.IPStringOptions fullParams;
        static final IPAddressSection.IPStringOptions normalizedWildcardParams;
        static final IPAddressSection.IPStringOptions sqlWildcardParams;
        static final IPAddressSection.IPStringOptions inetAtonOctalParams;
        static final IPAddressSection.IPStringOptions inetAtonHexParams;
        static final IPAddressSection.IPStringOptions canonicalParams;
        static final IPAddressSection.IPStringOptions reverseDNSParams;
        static final IPAddressSection.IPStringOptions segmentedBinaryParams;
        public String octalString;
        public String hexString;

        IPv4StringCache() {
        }

        static {
            IPAddressSection.WildcardOptions allWildcards = new IPAddressSection.WildcardOptions(IPAddressSection.WildcardOptions.WildcardOption.ALL);
            IPAddressSection.WildcardOptions allSQLWildcards = new IPAddressSection.WildcardOptions(IPAddressSection.WildcardOptions.WildcardOption.ALL, new AddressDivisionGrouping.StringOptions.Wildcards(IPAddress.SEGMENT_SQL_WILDCARD_STR, IPAddress.SEGMENT_SQL_SINGLE_WILDCARD_STR));
            IPAddressSection.WildcardOptions wildcardsRangeOnlyNetworkOnly = new IPAddressSection.WildcardOptions(IPAddressSection.WildcardOptions.WildcardOption.NETWORK_ONLY, new AddressDivisionGrouping.StringOptions.Wildcards(IPAddress.RANGE_SEPARATOR_STR));
            fullParams = new IPv4StringOptions.Builder().setExpandedSegments(true).setWildcardOptions(wildcardsRangeOnlyNetworkOnly).toOptions();
            normalizedWildcardParams = new IPv4StringOptions.Builder().setWildcardOptions(allWildcards).toOptions();
            sqlWildcardParams = new IPv4StringOptions.Builder().setWildcardOptions(allSQLWildcards).toOptions();
            inetAtonOctalParams = new IPv4StringOptions.Builder().setRadix(IPv4Address.inet_aton_radix.OCTAL.getRadix()).setSegmentStrPrefix(IPv4Address.inet_aton_radix.OCTAL.getSegmentStrPrefix()).toOptions();
            inetAtonHexParams = new IPv4StringOptions.Builder().setRadix(IPv4Address.inet_aton_radix.HEX.getRadix()).setSegmentStrPrefix(IPv4Address.inet_aton_radix.HEX.getSegmentStrPrefix()).toOptions();
            canonicalParams = new IPv4StringOptions.Builder().toOptions();
            reverseDNSParams = new IPv4StringOptions.Builder().setWildcardOptions(allWildcards).setReverse(true).setAddressSuffix(".in-addr.arpa").toOptions();
            segmentedBinaryParams = new IPAddressSection.IPStringOptions.Builder(2).setSeparator(Character.valueOf('.')).setSegmentStrPrefix("0b").toOptions();
        }
    }
}

