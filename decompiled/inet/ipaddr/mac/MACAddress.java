/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.mac;

import inet.ipaddr.Address;
import inet.ipaddr.AddressPositionException;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.MACAddressString;
import inet.ipaddr.format.standard.AddressDivisionGrouping;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.ipv6.IPv6Address;
import inet.ipaddr.ipv6.IPv6AddressNetwork;
import inet.ipaddr.ipv6.IPv6AddressSection;
import inet.ipaddr.mac.MACAddressNetwork;
import inet.ipaddr.mac.MACAddressSection;
import inet.ipaddr.mac.MACAddressSegment;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MACAddress
extends Address
implements Iterable<MACAddress> {
    private static final long serialVersionUID = 4L;
    public static final char COLON_SEGMENT_SEPARATOR = ':';
    public static final char DASH_SEGMENT_SEPARATOR = '-';
    public static final char SPACE_SEGMENT_SEPARATOR = ' ';
    public static final char DOTTED_SEGMENT_SEPARATOR = '.';
    public static final char DASHED_SEGMENT_RANGE_SEPARATOR = '|';
    public static final String DASHED_SEGMENT_RANGE_SEPARATOR_STR = String.valueOf('|');
    public static final int BITS_PER_SEGMENT = 8;
    public static final int BYTES_PER_SEGMENT = 1;
    public static final int MEDIA_ACCESS_CONTROL_SEGMENT_COUNT = 6;
    public static final int MEDIA_ACCESS_CONTROL_BIT_COUNT = 48;
    public static final int MEDIA_ACCESS_CONTROL_DOTTED_SEGMENT_COUNT = 3;
    public static final int MEDIA_ACCESS_CONTROL_DOTTED_64_SEGMENT_COUNT = 4;
    public static final int MEDIA_ACCESS_CONTROL_DOTTED_BITS_PER_SEGMENT = 16;
    public static final int MEDIA_ACCESS_CONTROL_SINGLE_DASHED_SEGMENT_COUNT = 2;
    public static final int EXTENDED_UNIQUE_IDENTIFIER_48_SEGMENT_COUNT = 6;
    public static final int EXTENDED_UNIQUE_IDENTIFIER_64_SEGMENT_COUNT = 8;
    public static final int EXTENDED_UNIQUE_IDENTIFIER_48_BIT_COUNT = 48;
    public static final int EXTENDED_UNIQUE_IDENTIFIER_64_BIT_COUNT = 64;
    public static final int DEFAULT_TEXTUAL_RADIX = 16;
    public static final int MAX_VALUE_PER_SEGMENT = 255;
    public static final int MAX_VALUE_PER_DOTTED_SEGMENT = 65535;
    public static final int ORGANIZATIONAL_UNIQUE_IDENTIFIER_SEGMENT_COUNT = 3;
    public static final int ORGANIZATIONAL_UNIQUE_IDENTIFIER_BIT_COUNT = 24;
    transient MACAddressSection.MACAddressCache sectionCache;

    public MACAddress(MACAddressSegment[] segments) throws AddressValueException {
        super((Address thisAddress) -> ((MACAddress)thisAddress).getAddressCreator().createSection(segments, segments.length == 8));
        int segCount = segments.length;
        if (segCount != 6 && segCount != 8) {
            throw new AddressValueException("ipaddress.error.mac.invalid.segment.count", segCount);
        }
    }

    public MACAddress(MACAddressSection section) throws AddressValueException {
        super(section);
        int segCount = section.getSegmentCount();
        if (segCount != 6 && segCount != 8) {
            throw new AddressValueException("ipaddress.error.mac.invalid.segment.count", segCount);
        }
        if (section.addressSegmentIndex != 0) {
            throw new AddressPositionException(section.addressSegmentIndex);
        }
    }

    public MACAddress(long address) throws AddressValueException {
        this(address, false);
    }

    public MACAddress(NetworkInterface ni) throws SocketException {
        this(ni.getHardwareAddress());
    }

    public MACAddress(long address, boolean extended) throws AddressValueException {
        super((Address thisAddress) -> ((MACAddress)thisAddress).getAddressCreator().createSection(address, 0, extended));
    }

    public MACAddress(byte[] bytes) throws AddressValueException {
        super((Address thisAddress) -> MACAddress.createSection((MACAddress)thisAddress, bytes));
    }

    public MACAddress(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, boolean extended) {
        super((Address thisAddress) -> ((MACAddress)thisAddress).getAddressCreator().createSection(lowerValueProvider, upperValueProvider, 0, extended));
    }

    public MACAddress(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider) {
        this(lowerValueProvider, upperValueProvider, false);
    }

    public MACAddress(Address.SegmentValueProvider valueProvider, boolean extended) throws AddressValueException {
        this(valueProvider, valueProvider, extended);
    }

    public MACAddress(Address.SegmentValueProvider valueProvider) {
        this(valueProvider, false);
    }

    private static MACAddressSection createSection(MACAddress addr, byte[] bytes) {
        int segCount;
        int len = bytes.length;
        if (len < 8) {
            segCount = 6;
            if (len > 6) {
                int i = 0;
                do {
                    if (bytes[i++] == 0) continue;
                    segCount = 8;
                    break;
                } while (--len > 6);
            }
        } else {
            segCount = 8;
        }
        return addr.getAddressCreator().createSection(bytes, 0, segCount, segCount == 8);
    }

    protected static String getMessage(String key) {
        return Address.getMessage(key);
    }

    public MACAddressNetwork getNetwork() {
        return MACAddress.defaultMACNetwork();
    }

    public IPv6AddressNetwork getIPv6Network() {
        return MACAddress.defaultIpv6Network();
    }

    public MACAddressNetwork.MACAddressCreator getAddressCreator() {
        return this.getNetwork().getAddressCreator();
    }

    public boolean isExtended() {
        return this.getSection().isExtended();
    }

    @Override
    public boolean isMACAddress() {
        return true;
    }

    @Override
    public MACAddress toMACAddress() {
        return this;
    }

    public boolean isAllAddresses() {
        return this.getSection().isFullRange();
    }

    @Override
    public MACAddressSection getSection() {
        return (MACAddressSection)super.getSection();
    }

    @Override
    public MACAddressSegment getDivision(int index) {
        return this.getSegment(index);
    }

    @Override
    public MACAddressSegment getSegment(int index) {
        return this.getSection().getSegment(index);
    }

    public MACAddressSegment[] getSegments() {
        return this.getSection().getSegments();
    }

    public static int maxSegmentValue() {
        return 255;
    }

    @Override
    public int getMaxSegmentValue() {
        return 255;
    }

    @Override
    public int getByteCount() {
        return this.getSection().getByteCount();
    }

    @Override
    public int getBitCount() {
        return this.getSection().getBitCount();
    }

    @Override
    public int getBytesPerSegment() {
        return 1;
    }

    @Override
    public int getBitsPerSegment() {
        return 8;
    }

    @Override
    protected boolean isFromSameString(HostIdentifierString other) {
        if (this.fromString != null && other instanceof MACAddressString) {
            MACAddressString fromString = (MACAddressString)this.fromString;
            MACAddressString otherString = (MACAddressString)other;
            return fromString == otherString || fromString.toString().equals(otherString.toString()) && fromString.getValidationOptions() == otherString.getValidationOptions();
        }
        return false;
    }

    public Iterable<MACAddress> getIterable() {
        return this;
    }

    @Override
    public Iterator<MACAddress> iterator() {
        return this.getSection().iterator(this, this.getAddressCreator());
    }

    @Override
    public AddressComponentSpliterator<MACAddress> spliterator() {
        return this.getSection().spliterator(this, this.getAddressCreator());
    }

    public Stream<MACAddress> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Iterator<MACAddress> prefixBlockIterator() {
        return this.getSection().prefixIterator(this, this.getAddressCreator(), true);
    }

    public AddressComponentSpliterator<MACAddress> prefixBlockSpliterator() {
        return this.getSection().prefixSpliterator(this, this.getAddressCreator(), true);
    }

    public Stream<MACAddress> prefixBlockStream() {
        return StreamSupport.stream(this.prefixBlockSpliterator(), false);
    }

    public Iterator<MACAddress> prefixIterator() {
        return this.getSection().prefixIterator(this, this.getAddressCreator(), false);
    }

    public AddressComponentSpliterator<MACAddress> prefixSpliterator() {
        return this.getSection().prefixSpliterator(this, this.getAddressCreator(), false);
    }

    public Stream<MACAddress> prefixStream() {
        return StreamSupport.stream(this.prefixSpliterator(), false);
    }

    public Iterator<MACAddressSegment[]> segmentsIterator() {
        return this.getSection().segmentsIterator();
    }

    public AddressComponentRangeSpliterator<MACAddress, MACAddressSegment[]> segmentsSpliterator() {
        return this.getSection().segmentsSpliterator(this, this.getAddressCreator());
    }

    public Stream<MACAddressSegment[]> segmentsStream() {
        return StreamSupport.stream(this.segmentsSpliterator(), false);
    }

    @Override
    public MACAddress increment(long increment) {
        return this.checkIdentity(this.getSection().increment(increment));
    }

    @Override
    public MACAddress incrementBoundary(long increment) {
        return this.checkIdentity(this.getSection().incrementBoundary(increment));
    }

    @Override
    public MACAddress getLower() {
        return this.getLowestOrHighest(true);
    }

    @Override
    public MACAddress getUpper() {
        return this.getLowestOrHighest(false);
    }

    private MACAddress getLowestOrHighest(boolean lowest) {
        return this.getSection().getLowestOrHighest(this, lowest);
    }

    public long longValue() {
        return this.getSection().longValue();
    }

    public long upperLongValue() {
        return this.getSection().upperLongValue();
    }

    @Override
    public MACAddress reverseBits(boolean perByte) {
        return this.checkIdentity(this.getSection().reverseBits(perByte));
    }

    @Override
    public MACAddress reverseBytes() {
        return this.checkIdentity(this.getSection().reverseBytes());
    }

    @Override
    public MACAddress reverseBytesPerSegment() {
        return this;
    }

    @Override
    public MACAddress reverseSegments() {
        return this.checkIdentity(this.getSection().reverseSegments());
    }

    private MACAddress checkIdentity(MACAddressSection newSection) {
        MACAddressSection section = this.getSection();
        if (newSection == section) {
            return this;
        }
        return this.getAddressCreator().createAddress(newSection);
    }

    @Override
    @Deprecated
    public MACAddress removePrefixLength() {
        return this.removePrefixLength(true);
    }

    @Override
    public MACAddress withoutPrefixLength() {
        return this.removePrefixLength(false);
    }

    @Override
    @Deprecated
    public MACAddress removePrefixLength(boolean zeroed) {
        return this.checkIdentity(this.getSection().removePrefixLength(zeroed));
    }

    @Override
    @Deprecated
    public MACAddress applyPrefixLength(int prefixLength) {
        return this.checkIdentity(this.getSection().applyPrefixLength(prefixLength));
    }

    @Override
    public MACAddress adjustPrefixBySegment(boolean nextSegment) {
        return this.checkIdentity(this.getSection().adjustPrefixBySegment(nextSegment));
    }

    @Override
    public MACAddress adjustPrefixBySegment(boolean nextSegment, boolean zeroed) {
        return this.checkIdentity(this.getSection().adjustPrefixBySegment(nextSegment, zeroed));
    }

    @Override
    public MACAddress adjustPrefixLength(int adjustment) {
        return this.checkIdentity(this.getSection().adjustPrefixLength(adjustment));
    }

    @Override
    public MACAddress adjustPrefixLength(int adjustment, boolean zeroed) {
        return this.checkIdentity(this.getSection().adjustPrefixLength(adjustment, zeroed));
    }

    @Override
    public MACAddress setPrefixLength(int prefixLength) {
        return this.checkIdentity(this.getSection().setPrefixLength(prefixLength));
    }

    @Override
    public MACAddress setPrefixLength(int prefixLength, boolean zeroed) {
        return this.checkIdentity(this.getSection().setPrefixLength(prefixLength, zeroed));
    }

    @Override
    public MACAddressSection getSection(int index) {
        return this.getSection().getSection(index);
    }

    @Override
    public MACAddressSection getSection(int index, int endIndex) {
        return this.getSection().getSection(index, endIndex);
    }

    public MACAddressSection getODISection() {
        return this.getSection().getODISection();
    }

    public MACAddressSection getOUISection() {
        return this.getSection().getOUISection();
    }

    public MACAddress toOUIPrefixBlock() {
        return this.checkIdentity(this.getSection().toOUIPrefixBlock());
    }

    @Override
    public MACAddress toPrefixBlock() {
        return this.checkIdentity(this.getSection().toPrefixBlock());
    }

    public IPv6Address toLinkLocalIPv6() {
        IPv6AddressNetwork network = this.getIPv6Network();
        IPv6AddressSection linkLocalPrefix = network.getLinkLocalPrefix();
        IPv6AddressNetwork.IPv6AddressCreator creator = network.getAddressCreator();
        return creator.createAddress(linkLocalPrefix.append(this.toEUI64IPv6()));
    }

    public IPv6AddressSection toEUI64IPv6() {
        return this.getIPv6Network().getAddressCreator().createSection(this);
    }

    public boolean isEUI64(boolean asMAC) {
        if (this.isExtended()) {
            MACAddressSection section = this.getSection();
            MACAddressSegment seg3 = section.getSegment(3);
            MACAddressSegment seg4 = section.getSegment(4);
            return seg3.matches(255) && seg4.matches(asMAC ? 255 : 254);
        }
        return false;
    }

    public MACAddress toEUI64(boolean asMAC) {
        if (!this.isExtended()) {
            MACAddressNetwork.MACAddressCreator creator = this.getAddressCreator();
            AddressSegment[] segs = creator.createSegmentArray(8);
            MACAddressSection section = this.getSection();
            section.getSegments(0, 3, segs, 0);
            MACAddressSegment ffSegment = creator.createSegment(255);
            segs[3] = ffSegment;
            segs[4] = asMAC ? ffSegment : creator.createSegment(254);
            section.getSegments(3, 6, segs, 5);
            Integer prefLength = this.getPrefixLength();
            if (prefLength != null) {
                MACAddressSection resultSection = creator.createSectionInternal((MACAddressSegment[])segs, true);
                if (prefLength >= 24) {
                    prefLength = prefLength + 16;
                }
                resultSection.assignPrefixLength(prefLength);
                return creator.createAddress(resultSection);
            }
            return creator.createAddressInternal((MACAddressSegment[])segs);
        }
        MACAddressSection section = this.getSection();
        MACAddressSegment seg3 = section.getSegment(3);
        MACAddressSegment seg4 = section.getSegment(4);
        if (seg3.matches(255) && seg4.matches(asMAC ? 255 : 254)) {
            return this;
        }
        throw new IncompatibleAddressException(this, "ipaddress.mac.error.not.eui.convertible");
    }

    public MACAddress replace(int startIndex, int endIndex, MACAddress replacement, int replacementIndex) {
        return this.checkIdentity(this.getSection().replace(startIndex, endIndex, replacement.getSection(), replacementIndex, replacementIndex + (endIndex - startIndex)));
    }

    public AddressDivisionGrouping getDottedAddress() {
        return this.getSection().getDottedGrouping();
    }

    void cache(HostIdentifierString string) {
        if (this.fromString instanceof MACAddressString) {
            this.fromString = string;
        }
    }

    @Override
    public MACAddressString toAddressString() {
        if (this.fromString == null) {
            this.fromString = new MACAddressString(this);
        }
        return (MACAddressString)this.fromString;
    }

    public String toNormalizedString(AddressDivisionGrouping.StringOptions stringOptions) {
        return this.getSection().toNormalizedString(stringOptions);
    }

    public String toDottedString() throws IncompatibleAddressException {
        return this.getSection().toDottedString();
    }

    public String toDashedString() {
        return this.toCanonicalString();
    }

    public String toColonDelimitedString() {
        return this.toNormalizedString();
    }

    public String toSpaceDelimitedString() {
        return this.getSection().toSpaceDelimitedString();
    }

    @Override
    public String toString() {
        return this.toNormalizedString();
    }

    public boolean isUnicast() {
        return !this.isMulticast();
    }

    @Override
    public boolean isMulticast() {
        return this.getSegment(0).matchesWithMask(1, 1);
    }

    public boolean isUniversal() {
        return !this.isLocal();
    }

    @Override
    public boolean isLocal() {
        return this.getSegment(0).matchesWithMask(2, 2);
    }
}

