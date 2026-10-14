/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv4;

import inet.ipaddr.Address;
import inet.ipaddr.AddressConversionException;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressConverter;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSegmentSeries;
import inet.ipaddr.IPAddressStringParameters;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.format.util.IPAddressPartStringCollection;
import inet.ipaddr.ipv4.IPv4AddressNetwork;
import inet.ipaddr.ipv4.IPv4AddressSection;
import inet.ipaddr.ipv4.IPv4AddressSegment;
import inet.ipaddr.ipv4.IPv4AddressSeqRange;
import inet.ipaddr.ipv6.IPv6Address;
import inet.ipaddr.ipv6.IPv6AddressNetwork;
import inet.ipaddr.ipv6.IPv6AddressSection;
import inet.ipaddr.ipv6.IPv6AddressSegment;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IPv4Address
extends IPAddress
implements Iterable<IPv4Address> {
    private static final long serialVersionUID = 4L;
    public static final char SEGMENT_SEPARATOR = '.';
    public static final int BITS_PER_SEGMENT = 8;
    public static final int BYTES_PER_SEGMENT = 1;
    public static final int SEGMENT_COUNT = 4;
    public static final int BYTE_COUNT = 4;
    public static final int BIT_COUNT = 32;
    public static final int DEFAULT_TEXTUAL_RADIX = 10;
    public static final int MAX_VALUE_PER_SEGMENT = 255;
    public static final int MAX_VALUE = -1;
    public static final String REVERSE_DNS_SUFFIX = ".in-addr.arpa";
    transient IPv4AddressSection.IPv4AddressCache addressCache;

    public IPv4Address(IPv4AddressSegment[] segments) throws AddressValueException {
        this(segments, null);
    }

    public IPv4Address(IPv4AddressSegment[] segments, Integer networkPrefixLength) throws AddressValueException {
        super((Address thisAddress) -> ((IPv4Address)thisAddress).getAddressCreator().createSection(segments, networkPrefixLength));
        if (this.getSegmentCount() != 4) {
            throw new AddressValueException("ipaddress.error.ipv4.invalid.segment.count", this.getSegmentCount());
        }
    }

    public IPv4Address(IPv4AddressSection section) throws AddressValueException {
        super(section);
        if (section.getSegmentCount() != 4) {
            throw new AddressValueException("ipaddress.error.ipv4.invalid.segment.count", section.getSegmentCount());
        }
    }

    public IPv4Address(int address) {
        this(address, null);
    }

    public IPv4Address(int address, Integer networkPrefixLength) throws AddressValueException {
        super((Address thisAddress) -> ((IPv4Address)thisAddress).getAddressCreator().createSectionInternal(address, networkPrefixLength));
    }

    public IPv4Address(Inet4Address inet4Address, Integer networkPrefixLength) {
        this(inet4Address, inet4Address.getAddress(), networkPrefixLength);
    }

    public IPv4Address(Inet4Address inet4Address) {
        this(inet4Address, inet4Address.getAddress(), null);
    }

    private IPv4Address(Inet4Address inet4Address, byte[] bytes, Integer networkPrefixLength) throws AddressValueException {
        super((Address thisAddress) -> ((IPv4Address)thisAddress).getAddressCreator().createSection(bytes, 0, bytes.length, 4, networkPrefixLength));
        this.getSection().setInetAddress(inet4Address);
    }

    public IPv4Address(byte[] bytes) throws AddressValueException {
        this(bytes, null);
    }

    public IPv4Address(byte[] bytes, int byteStartIndex, int byteEndIndex) throws AddressValueException {
        this(bytes, byteStartIndex, byteEndIndex, null);
    }

    public IPv4Address(byte[] bytes, Integer networkPrefixLength) throws AddressValueException {
        this(bytes, 0, bytes.length, networkPrefixLength);
    }

    public IPv4Address(byte[] bytes, int byteStartIndex, int byteEndIndex, Integer networkPrefixLength) throws AddressValueException {
        super((Address thisAddress) -> ((IPv4Address)thisAddress).getAddressCreator().createSection(bytes, byteStartIndex, byteEndIndex, 4, networkPrefixLength));
    }

    public IPv4Address(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer networkPrefixLength) throws AddressValueException {
        super((Address thisAddress) -> ((IPv4Address)thisAddress).getAddressCreator().createFullSectionInternal(lowerValueProvider, upperValueProvider, networkPrefixLength));
    }

    public IPv4Address(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider) {
        this(lowerValueProvider, upperValueProvider, null);
    }

    public IPv4Address(Address.SegmentValueProvider valueProvider, Integer networkPrefixLength) throws AddressValueException {
        this(valueProvider, valueProvider, networkPrefixLength);
    }

    public IPv4Address(Address.SegmentValueProvider valueProvider) {
        this(valueProvider, (Integer)null);
    }

    @Override
    public IPv4AddressSection getSection() {
        return (IPv4AddressSection)super.getSection();
    }

    @Override
    public IPv4AddressSection getSection(int index) {
        return this.getSection().getSection(index);
    }

    @Override
    public IPv4AddressSection getSection(int index, int endIndex) {
        return this.getSection().getSection(index, endIndex);
    }

    @Override
    public IPv4AddressSegment getDivision(int index) {
        return this.getSegment(index);
    }

    @Override
    public IPv4AddressSegment getSegment(int index) {
        return this.getSection().getSegment(index);
    }

    public IPv4AddressSegment[] getSegments() {
        return this.getSection().getSegments();
    }

    @Override
    public IPAddressStringDivisionSeries[] getParts(IPAddressSection.IPStringBuilderOptions options) {
        return this.getParts(IPv4AddressSection.IPv4StringBuilderOptions.from(options));
    }

    public IPAddressStringDivisionSeries[] getParts(IPv4AddressSection.IPv4StringBuilderOptions options) {
        IPAddressStringDivisionSeries[] parts = this.getSection().getParts(options);
        IPv6Address ipv6Addr = this.getConverted(options);
        if (ipv6Addr != null) {
            IPAddressStringDivisionSeries[] ipv6Parts = ipv6Addr.getParts(options.ipv6ConverterOptions);
            IPAddressStringDivisionSeries[] tmp = parts;
            parts = new IPAddressStringDivisionSeries[tmp.length + ipv6Parts.length];
            System.arraycopy(tmp, 0, parts, 0, tmp.length);
            System.arraycopy(ipv6Parts, 0, parts, tmp.length, ipv6Parts.length);
        }
        return parts;
    }

    @Override
    public int getSegmentCount() {
        return 4;
    }

    @Override
    public int getByteCount() {
        return 4;
    }

    @Override
    public int getBitCount() {
        return 32;
    }

    @Override
    public boolean isIPv4() {
        return true;
    }

    @Override
    public IPv4Address toIPv4() {
        return this;
    }

    @Override
    public boolean isIPv4Convertible() {
        return true;
    }

    public IPv6Address getIPv6Address(IPv6AddressSegment[] segs) {
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getIPv6Network().getAddressCreator();
        return creator.createAddress(IPv6AddressSection.createSection(creator, segs, this));
    }

    public IPv6Address getIPv4MappedAddress() {
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getIPv6Network().getAddressCreator();
        IPv6AddressSegment zero = creator.createSegment(0);
        IPv6AddressSegment[] segs = creator.createSegmentArray(6);
        segs[3] = segs[4] = zero;
        segs[2] = segs[4];
        segs[1] = segs[4];
        segs[0] = segs[4];
        segs[5] = creator.createSegment(65535);
        return this.getIPv6Address(segs);
    }

    @Override
    public boolean isIPv6Convertible() {
        IPAddressConverter conv = DEFAULT_ADDRESS_CONVERTER;
        return conv.isIPv6Convertible(this);
    }

    @Override
    public IPv6Address toIPv6() {
        IPAddressConverter conv = DEFAULT_ADDRESS_CONVERTER;
        return conv.toIPv6(this);
    }

    public IPv4Address toBroadcastAddress() {
        return this.toMaxHost();
    }

    public IPv4Address toNetworkAddress() {
        return this.toZeroHost();
    }

    void cache(IPv4Address lower, IPv4Address upper) {
        this.getSection().cache(this, lower, upper);
    }

    @Override
    public IPv4Address getLowerNonZeroHost() {
        return this.getSection().getLowestOrHighest(this, true, true);
    }

    @Override
    public IPv4Address getLower() {
        return this.getSection().getLowestOrHighest(this, true, false);
    }

    @Override
    public IPv4Address getUpper() {
        return this.getSection().getLowestOrHighest(this, false, false);
    }

    public int intValue() {
        return this.getSection().intValue();
    }

    public int upperIntValue() {
        return this.getSection().upperIntValue();
    }

    public long longValue() {
        return this.getSection().longValue();
    }

    public long upperLongValue() {
        return this.getSection().upperLongValue();
    }

    public IPv4Address replace(int startIndex, int endIndex, IPv4Address replacement, int replacementIndex) {
        return this.checkIdentity(this.getSection().replace(startIndex, endIndex, replacement.getSection(), replacementIndex, replacementIndex + (endIndex - startIndex)));
    }

    @Override
    public IPv4Address reverseBits(boolean perByte) {
        return this.checkIdentity(this.getSection().reverseBits(perByte));
    }

    @Override
    public IPv4Address reverseBytes() {
        return this.checkIdentity(this.getSection().reverseBytes());
    }

    @Override
    public IPv4Address reverseBytesPerSegment() {
        return this;
    }

    @Override
    public IPv4Address reverseSegments() {
        return this.checkIdentity(this.getSection().reverseSegments());
    }

    private IPv4Address checkIdentity(IPv4AddressSection newSection) {
        IPv4AddressSection section = this.getSection();
        if (newSection == section) {
            return this;
        }
        return this.getAddressCreator().createAddress(newSection);
    }

    @Override
    public IPv4Address adjustPrefixBySegment(boolean nextSegment) {
        return this.checkIdentity(this.getSection().adjustPrefixBySegment(nextSegment));
    }

    @Override
    public IPv4Address adjustPrefixBySegment(boolean nextSegment, boolean zeroed) {
        return this.checkIdentity(this.getSection().adjustPrefixBySegment(nextSegment, zeroed));
    }

    @Override
    public IPv4Address adjustPrefixLength(int adjustment) {
        return this.checkIdentity(this.getSection().adjustPrefixLength(adjustment));
    }

    @Override
    public IPv4Address adjustPrefixLength(int adjustment, boolean zeroed) {
        return this.checkIdentity(this.getSection().adjustPrefixLength(adjustment, zeroed));
    }

    @Override
    public IPv4Address setPrefixLength(int prefixLength) {
        return this.setPrefixLength(prefixLength, true);
    }

    @Override
    public IPv4Address setPrefixLength(int prefixLength, boolean zeroed) {
        return this.checkIdentity(this.getSection().setPrefixLength(prefixLength, zeroed));
    }

    @Override
    public IPv4Address setPrefixLength(int prefixLength, boolean zeroed, boolean zeroHostIsBlock) throws PrefixLenException {
        return this.checkIdentity(this.getSection().setPrefixLength(prefixLength, zeroed, zeroHostIsBlock));
    }

    @Override
    @Deprecated
    public IPv4Address applyPrefixLength(int networkPrefixLength) throws PrefixLenException {
        return this.checkIdentity(this.getSection().applyPrefixLength(networkPrefixLength));
    }

    @Override
    @Deprecated
    public IPv4Address removePrefixLength(boolean zeroed) {
        return this.checkIdentity(this.getSection().removePrefixLength(zeroed));
    }

    @Override
    public IPv4Address withoutPrefixLength() {
        return this.removePrefixLength(false);
    }

    @Override
    @Deprecated
    public IPv4Address removePrefixLength() {
        return this.removePrefixLength(true);
    }

    public Iterator<IPv4AddressSegment[]> segmentsNonZeroHostIterator() {
        return this.getSection().segmentsNonZeroHostIterator();
    }

    public Iterator<IPv4AddressSegment[]> segmentsIterator() {
        return this.getSection().segmentsIterator();
    }

    public AddressComponentRangeSpliterator<IPv4Address, IPv4AddressSegment[]> segmentsSpliterator() {
        return this.getSection().segmentsSpliterator(this, this.getAddressCreator());
    }

    public Stream<IPv4AddressSegment[]> segmentsStream() {
        return StreamSupport.stream(this.segmentsSpliterator(), false);
    }

    @Override
    public Iterator<IPv4Address> iterator() {
        return this.getSection().iterator(this, this.getAddressCreator(), null);
    }

    @Override
    public AddressComponentSpliterator<IPv4Address> spliterator() {
        return this.getSection().spliterator(this, this.getAddressCreator(), false);
    }

    public Stream<IPv4Address> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Iterator<IPv4Address> nonZeroHostIterator() {
        Predicate<IPv4AddressSegment[]> excludeFunc = null;
        if (this.includesZeroHost()) {
            int prefLength = this.getNetworkPrefixLength();
            excludeFunc = s2 -> this.getSection().isZeroHost((IPv4AddressSegment[])s2, prefLength);
        }
        return this.getSection().iterator(this, this.getAddressCreator(), excludeFunc);
    }

    public Iterator<IPv4Address> prefixBlockIterator() {
        return this.getSection().prefixIterator(this, this.getAddressCreator(), true);
    }

    public AddressComponentSpliterator<IPv4Address> prefixBlockSpliterator() {
        return this.getSection().prefixSpliterator(this, this.getAddressCreator(), true);
    }

    public Stream<IPv4Address> prefixBlockStream() {
        return StreamSupport.stream(this.prefixBlockSpliterator(), false);
    }

    public Iterator<IPv4Address> prefixBlockIterator(int prefixLength) {
        return this.getSection().prefixIterator(this, this.getAddressCreator(), true, prefixLength);
    }

    public AddressComponentSpliterator<IPv4Address> prefixBlockSpliterator(int prefixLength) {
        return this.getSection().prefixSpliterator(this, this.getAddressCreator(), true, prefixLength);
    }

    public Stream<IPv4Address> prefixBlockStream(int prefixLength) {
        return StreamSupport.stream(this.prefixBlockSpliterator(prefixLength), false);
    }

    public Iterator<IPv4Address> prefixIterator() {
        return this.getSection().prefixIterator(this, this.getAddressCreator(), false);
    }

    public AddressComponentSpliterator<IPv4Address> prefixSpliterator() {
        return this.getSection().prefixSpliterator(this, this.getAddressCreator(), false);
    }

    public Stream<IPv4Address> prefixStream() {
        return StreamSupport.stream(this.prefixSpliterator(), false);
    }

    public Iterator<IPv4Address> prefixIterator(int prefixLength) {
        return this.getSection().prefixIterator(this, this.getAddressCreator(), false, prefixLength);
    }

    public AddressComponentSpliterator<IPv4Address> prefixSpliterator(int prefixLength) {
        return this.getSection().prefixSpliterator(this, this.getAddressCreator(), false, prefixLength);
    }

    public Stream<IPv4Address> prefixStream(int prefixLength) {
        return StreamSupport.stream(this.prefixSpliterator(prefixLength), false);
    }

    public Iterator<IPv4Address> blockIterator(int segmentCount) {
        return this.getSection().blockIterator(this, this.getAddressCreator(), segmentCount);
    }

    public AddressComponentSpliterator<IPv4Address> blockSpliterator(int segmentCount) {
        return this.getSection().blockSpliterator(this, this.getAddressCreator(), segmentCount);
    }

    public Stream<IPv4Address> blockStream(int segmentCount) {
        return StreamSupport.stream(this.blockSpliterator(segmentCount), false);
    }

    public Iterator<IPv4Address> sequentialBlockIterator() {
        return super.sequentialBlockIterator();
    }

    public AddressComponentSpliterator<IPv4Address> sequentialBlockSpliterator() {
        return super.sequentialBlockSpliterator();
    }

    public Stream<IPv4Address> sequentialBlockStream() {
        return super.sequentialBlockStream();
    }

    public Iterable<IPv4Address> getIterable() {
        return this;
    }

    @Override
    public IPv4Address increment(long increment) {
        return this.checkIdentity(this.getSection().increment(increment));
    }

    @Override
    public IPv4Address incrementBoundary(long increment) {
        return this.checkIdentity(this.getSection().incrementBoundary(increment));
    }

    IPv4AddressNetwork.IPv4AddressCreator getAddressCreator() {
        return this.getNetwork().getAddressCreator();
    }

    @Override
    public IPv4AddressNetwork getNetwork() {
        return IPv4Address.defaultIpv4Network();
    }

    public IPv6AddressNetwork getIPv6Network() {
        return IPv4Address.defaultIpv6Network();
    }

    @Override
    protected IPv4Address convertArg(IPAddress arg) throws AddressConversionException {
        IPv4Address converted = arg.toIPv4();
        if (converted == null) {
            throw new AddressConversionException(this, arg);
        }
        return converted;
    }

    @Override
    public IPv4Address intersect(IPAddress other) throws AddressConversionException {
        IPv4AddressSection thisSection = this.getSection();
        IPv4AddressSection section = thisSection.intersect(this.convertArg(other).getSection());
        if (section == null) {
            return null;
        }
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getAddressCreator();
        IPv4Address result = creator.createAddress(section);
        return result;
    }

    public IPv4Address[] subtract(IPAddress other) throws AddressConversionException {
        IPv4AddressSection thisSection = this.getSection();
        IPv4AddressSection[] sections = thisSection.subtract(this.convertArg(other).getSection());
        if (sections == null) {
            return null;
        }
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getAddressCreator();
        IPv4Address[] result = new IPv4Address[sections.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = creator.createAddress(sections[i]);
        }
        return result;
    }

    @Override
    public IPv4Address toZeroHost() {
        return this.toZeroHost(false);
    }

    @Override
    protected IPv4Address toZeroHost(boolean boundariesOnly) {
        if (!this.isPrefixed()) {
            IPv4AddressNetwork network;
            AddressNetwork.PrefixConfiguration config = (network = this.getNetwork()).getPrefixConfiguration();
            IPv4Address addr = (IPv4Address)network.getNetworkMask(0, !config.allPrefixedAddressesAreSubnets());
            if (config.zeroHostsAreSubnets()) {
                addr = addr.getLower();
            }
            return addr;
        }
        if (this.includesZeroHost() && this.isSingleNetwork()) {
            return this.getLower();
        }
        return this.checkIdentity(this.getSection().createZeroHost(boundariesOnly));
    }

    @Override
    public IPv4Address toZeroHost(int prefixLength) {
        if (this.isPrefixed() && prefixLength == this.getNetworkPrefixLength()) {
            return this.toZeroHost();
        }
        return this.checkIdentity(this.getSection().toZeroHost(prefixLength));
    }

    @Override
    public IPv4Address toZeroNetwork() {
        if (!this.isPrefixed()) {
            return (IPv4Address)this.getNetwork().getHostMask(this.getBitCount());
        }
        return this.checkIdentity(this.getSection().createZeroNetwork());
    }

    @Override
    public IPv4Address toMaxHost() {
        if (!this.isPrefixed()) {
            IPv4Address resultNoPrefix = (IPv4Address)this.getNetwork().getHostMask(0);
            if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                return resultNoPrefix;
            }
            return resultNoPrefix.setPrefixLength(0);
        }
        if (this.includesMaxHost() && this.isSingleNetwork()) {
            return this.getUpper();
        }
        return this.checkIdentity(this.getSection().createMaxHost());
    }

    @Override
    public IPv4Address toMaxHost(int prefixLength) {
        if (this.isPrefixed() && prefixLength == this.getNetworkPrefixLength()) {
            return this.toMaxHost();
        }
        return this.checkIdentity(this.getSection().toMaxHost(prefixLength));
    }

    @Override
    public IPv4Address mask(IPAddress mask, boolean retainPrefix) throws IncompatibleAddressException, AddressConversionException {
        return this.checkIdentity(this.getSection().mask(this.convertArg(mask).getSection(), retainPrefix));
    }

    @Override
    public IPv4Address mask(IPAddress mask) throws IncompatibleAddressException, AddressConversionException {
        return this.mask(mask, false);
    }

    @Override
    public IPv4Address maskNetwork(IPAddress mask, int networkPrefixLength) throws IncompatibleAddressException, PrefixLenException, AddressConversionException {
        return this.checkIdentity(this.getSection().maskNetwork(this.convertArg(mask).getSection(), networkPrefixLength));
    }

    @Override
    public IPv4Address bitwiseOr(IPAddress mask, boolean retainPrefix) throws IncompatibleAddressException, AddressConversionException {
        return this.checkIdentity(this.getSection().bitwiseOr(this.convertArg(mask).getSection(), retainPrefix));
    }

    @Override
    public IPv4Address bitwiseOr(IPAddress mask) throws IncompatibleAddressException, AddressConversionException {
        return this.bitwiseOr(mask, false);
    }

    @Override
    public IPv4Address bitwiseOrNetwork(IPAddress mask, int networkPrefixLength) throws IncompatibleAddressException, PrefixLenException, AddressConversionException {
        return this.checkIdentity(this.getSection().bitwiseOrNetwork(this.convertArg(mask).getSection(), networkPrefixLength));
    }

    @Override
    public IPv4Address getHostMask() {
        return (IPv4Address)super.getHostMask();
    }

    @Override
    public IPv4Address getNetworkMask() {
        return (IPv4Address)super.getNetworkMask();
    }

    @Override
    public IPv4AddressSection getNetworkSection() {
        return this.getSection().getNetworkSection();
    }

    @Override
    public IPv4AddressSection getNetworkSection(int networkPrefixLength) throws PrefixLenException {
        return this.getSection().getNetworkSection(networkPrefixLength);
    }

    @Override
    public IPv4AddressSection getNetworkSection(int networkPrefixLength, boolean withPrefixLength) throws PrefixLenException {
        return this.getSection().getNetworkSection(networkPrefixLength, withPrefixLength);
    }

    @Override
    public IPv4AddressSection getHostSection() {
        return this.getSection().getHostSection();
    }

    @Override
    public IPv4AddressSection getHostSection(int networkPrefixLength) throws PrefixLenException {
        return this.getSection().getHostSection(networkPrefixLength);
    }

    @Override
    public IPv4Address toPrefixBlock() {
        Integer prefixLength = this.getNetworkPrefixLength();
        if (prefixLength == null || this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            return this;
        }
        return this.toPrefixBlock(prefixLength);
    }

    @Override
    public IPv4Address toPrefixBlock(int networkPrefixLength) throws PrefixLenException {
        return this.checkIdentity(this.getSection().toPrefixBlock(networkPrefixLength));
    }

    @Override
    public IPv4Address assignPrefixForSingleBlock() {
        return (IPv4Address)super.assignPrefixForSingleBlock();
    }

    @Override
    public IPv4Address assignMinPrefixForBlock() {
        return (IPv4Address)super.assignMinPrefixForBlock();
    }

    @Override
    public IPv4Address coverWithPrefixBlock() {
        return (IPv4Address)IPv4AddressSection.coverWithPrefixBlock(this, this.getLower(), this.getUpper());
    }

    @Override
    public IPv4Address coverWithPrefixBlock(IPAddress other) throws AddressConversionException {
        return IPv4AddressSection.coverWithPrefixBlock(this, this.convertArg(other), IPv4Address::getLower, IPv4Address::getUpper, Address.ADDRESS_LOW_VALUE_COMPARATOR::compare);
    }

    public IPv4Address[] spanWithPrefixBlocks() {
        if (this.isSequential()) {
            if (this.isSinglePrefixBlock()) {
                return new IPv4Address[]{this};
            }
            return this.spanWithPrefixBlocks(this);
        }
        ArrayList list = (ArrayList)this.spanWithBlocks(true);
        return list.toArray(new IPv4Address[list.size()]);
    }

    public IPv4Address[] spanWithPrefixBlocks(IPAddress other) throws AddressConversionException {
        return (IPv4Address[])IPAddress.getSpanningPrefixBlocks((IPAddress)this, (IPAddress)this.convertArg(other), IPv4Address::getLower, IPv4Address::getUpper, Address.ADDRESS_LOW_VALUE_COMPARATOR::compare, IPv4Address::assignPrefixForSingleBlock, IPv4Address::withoutPrefixLength, this.getAddressCreator()::createAddressArray);
    }

    public IPv4Address[] spanWithSequentialBlocks() throws AddressConversionException {
        if (this.isSequential()) {
            return new IPv4Address[]{this.withoutPrefixLength()};
        }
        ArrayList list = (ArrayList)this.spanWithBlocks(false);
        return list.toArray(new IPv4Address[list.size()]);
    }

    public IPv4Address[] spanWithSequentialBlocks(IPAddress other) throws AddressConversionException {
        return (IPv4Address[])IPAddress.getSpanningSequentialBlocks((IPAddress)this, (IPAddress)this.convertArg(other), IPv4Address::getLower, IPv4Address::getUpper, Address.ADDRESS_LOW_VALUE_COMPARATOR::compare, IPv4Address::withoutPrefixLength, (IPAddressNetwork.IPAddressCreator)this.getAddressCreator());
    }

    @Override
    public IPv4AddressSeqRange spanWithRange(IPAddress other) throws AddressConversionException {
        return this.toSequentialRange(other);
    }

    public IPv4Address[] mergeToPrefixBlocks(IPAddress ... addresses) throws AddressConversionException {
        if (addresses.length == 0 && this.isSinglePrefixBlock()) {
            return new IPv4Address[]{this};
        }
        IPAddressSegmentSeries[] converted = this.getConverted(addresses);
        List<IPAddressSegmentSeries> blocks = IPv4Address.getMergedPrefixBlocks(converted);
        return blocks.toArray(new IPv4Address[blocks.size()]);
    }

    private IPAddress[] getConverted(IPAddress ... addresses) {
        IPAddress[] converted = new IPAddress[addresses.length + 1];
        int i = 0;
        int j = 1;
        while (i < addresses.length) {
            converted[j] = this.convertArg(addresses[i]);
            i = j++;
        }
        converted[0] = this;
        return converted;
    }

    public IPv4Address[] mergeToSequentialBlocks(IPAddress ... addresses) throws AddressConversionException {
        if (addresses.length == 0 && this.isSequential()) {
            return new IPv4Address[]{this};
        }
        IPAddressSegmentSeries[] converted = this.getConverted(addresses);
        List<IPAddressSegmentSeries> blocks = IPv4Address.getMergedSequentialBlocks(converted, this.getAddressCreator());
        return blocks.toArray(new IPv4Address[blocks.size()]);
    }

    @Override
    public Inet4Address toUpperInetAddress() {
        return (Inet4Address)super.toUpperInetAddress();
    }

    @Override
    public Inet4Address toInetAddress() {
        return (Inet4Address)super.toInetAddress();
    }

    @Override
    @Deprecated
    public IPv4AddressSeqRange toSequentialRange(IPAddress other) {
        return new IPv4AddressSeqRange(this, this.convertArg(other));
    }

    @Override
    public IPv4AddressSeqRange toSequentialRange() {
        IPv4Address thiz = this.withoutPrefixLength();
        return new IPv4AddressSeqRange(thiz.getLower(), thiz.getUpper(), true);
    }

    @Override
    public boolean isLocal() {
        if (this.isMulticast()) {
            IPv4AddressSegment seg0 = this.getSegment(0);
            if (seg0.matches(239)) {
                return true;
            }
            IPv4AddressSegment seg1 = this.getSegment(1);
            IPv4AddressSegment seg2 = this.getSegment(2);
            return seg0.matches(224) && seg1.isZero() && seg2.isZero() || seg0.matches(232) && (!seg1.isZero() || !seg2.isZero());
        }
        return this.isLinkLocal() || this.isPrivate() || this.isAnyLocal();
    }

    @Override
    public boolean isLinkLocal() {
        if (this.isMulticast()) {
            return this.getSegment(0).matches(224) && this.getSegment(1).isZero() && this.getSegment(2).isZero() && this.getSegment(3).matches(252);
        }
        return this.getSegment(0).matches(169) && this.getSegment(1).matches(254);
    }

    public boolean isPrivate() {
        IPv4AddressSegment seg0 = this.getSegment(0);
        IPv4AddressSegment seg1 = this.getSegment(1);
        return seg0.matches(10) || seg0.matches(172) && seg1.matchesWithPrefixMask(16, (Integer)4) || seg0.matches(192) && seg1.matches(168);
    }

    @Override
    public boolean isMulticast() {
        return this.getSegment(0).matchesWithPrefixMask(224, (Integer)4);
    }

    @Override
    public boolean isLoopback() {
        return this.getSegment(0).matches(127);
    }

    @Override
    protected IPAddressStringParameters createFromStringParams() {
        return new IPAddressStringParameters.Builder().getIPv4AddressParametersBuilder().setNetwork(this.getNetwork()).getParentBuilder().getIPv6AddressParametersBuilder().setNetwork(this.getIPv6Network()).getParentBuilder().toParams();
    }

    public static String toNormalizedString(IPv4AddressNetwork network, Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength) {
        return IPv4Address.toNormalizedString(network.getPrefixConfiguration(), lowerValueProvider, upperValueProvider, prefixLength, 4, 1, 8, 255, '.', 10, null);
    }

    public String toInetAtonString(inet_aton_radix radix) {
        return this.getSection().toInetAtonString(radix);
    }

    public String toInetAtonString(inet_aton_radix radix, int joinedCount) throws IncompatibleAddressException {
        return this.getSection().toInetAtonString(radix, joinedCount);
    }

    @Override
    public String toSegmentedBinaryString() {
        return this.getSection().toSegmentedBinaryString();
    }

    @Override
    public String toUNCHostName() {
        return super.toCanonicalString();
    }

    @Override
    public IPAddressPartStringCollection toStandardStringCollection() {
        return this.toStringCollection(IPv4AddressSection.IPv4StringBuilderOptions.STANDARD_OPTS);
    }

    @Override
    public IPAddressPartStringCollection toAllStringCollection() {
        return this.toStringCollection(IPv4AddressSection.IPv4StringBuilderOptions.ALL_OPTS);
    }

    @Override
    public IPAddressPartStringCollection toStringCollection(IPAddressSection.IPStringBuilderOptions opts) {
        return this.toStringCollection(IPv4AddressSection.IPv4StringBuilderOptions.from(opts));
    }

    private IPv6Address getConverted(IPv4AddressSection.IPv4StringBuilderOptions opts) {
        if (opts.includes(65536)) {
            IPv6Address.IPv6AddressConverter converter = opts.converter;
            return converter.toIPv6(this);
        }
        return null;
    }

    public IPAddressPartStringCollection toStringCollection(IPv4AddressSection.IPv4StringBuilderOptions opts) {
        IPv4AddressSection.IPv4StringCollection coll = new IPv4AddressSection.IPv4StringCollection();
        IPAddressPartStringCollection sectionColl = this.getSection().toStringCollection(opts);
        coll.addAll(sectionColl);
        IPv6Address ipv6Addr = this.getConverted(opts);
        if (ipv6Addr != null) {
            IPAddressPartStringCollection ipv6StringCollection = ipv6Addr.toStringCollection(opts.ipv6ConverterOptions);
            coll.addAll(ipv6StringCollection);
        }
        return coll;
    }

    public static enum inet_aton_radix {
        OCTAL,
        HEX,
        DECIMAL;


        int getRadix() {
            if (this == OCTAL) {
                return 8;
            }
            if (this == HEX) {
                return 16;
            }
            return 10;
        }

        String getSegmentStrPrefix() {
            if (this == OCTAL) {
                return "0";
            }
            if (this == HEX) {
                return "0x";
            }
            return null;
        }

        public String toString() {
            if (this == OCTAL) {
                return "octal";
            }
            if (this == HEX) {
                return "hexadecimal";
            }
            return "decimal";
        }
    }

    public static interface IPv4AddressConverter {
        public IPv4Address toIPv4(IPAddress var1);
    }
}

