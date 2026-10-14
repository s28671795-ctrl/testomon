/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv6;

import inet.ipaddr.Address;
import inet.ipaddr.AddressConversionException;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressPositionException;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressConverter;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSegmentSeries;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.IPAddressStringParameters;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.format.util.IPAddressPartStringCollection;
import inet.ipaddr.format.validate.Validator;
import inet.ipaddr.ipv4.IPv4Address;
import inet.ipaddr.ipv4.IPv4AddressNetwork;
import inet.ipaddr.ipv4.IPv4AddressSection;
import inet.ipaddr.ipv6.IPv6AddressNetwork;
import inet.ipaddr.ipv6.IPv6AddressSection;
import inet.ipaddr.ipv6.IPv6AddressSegment;
import inet.ipaddr.ipv6.IPv6AddressSeqRange;
import inet.ipaddr.mac.MACAddress;
import inet.ipaddr.mac.MACAddressNetwork;
import inet.ipaddr.mac.MACAddressSection;
import inet.ipaddr.mac.MACAddressSegment;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IPv6Address
extends IPAddress
implements Iterable<IPv6Address> {
    private static final long serialVersionUID = 4L;
    public static final char SEGMENT_SEPARATOR = ':';
    public static final char ZONE_SEPARATOR = '%';
    public static final char ALTERNATIVE_ZONE_SEPARATOR = '\u00a7';
    public static final char UNC_SEGMENT_SEPARATOR = '-';
    public static final char UNC_ZONE_SEPARATOR = 's';
    public static final char UNC_RANGE_SEPARATOR = '\u00bb';
    public static final String UNC_RANGE_SEPARATOR_STR = String.valueOf('\u00bb');
    public static final String UNC_SUFFIX = ".ipv6-literal.net";
    public static final String REVERSE_DNS_SUFFIX = ".ip6.arpa";
    public static final String REVERSE_DNS_SUFFIX_DEPRECATED = ".ip6.int";
    public static final int BITS_PER_SEGMENT = 16;
    public static final int BYTES_PER_SEGMENT = 2;
    public static final int SEGMENT_COUNT = 8;
    public static final int MIXED_REPLACED_SEGMENT_COUNT = 2;
    public static final int MIXED_ORIGINAL_SEGMENT_COUNT = 6;
    public static final int BYTE_COUNT = 16;
    public static final int BIT_COUNT = 128;
    public static final int DEFAULT_TEXTUAL_RADIX = 16;
    public static final int BASE_85_RADIX = 85;
    public static final int MAX_VALUE_PER_SEGMENT = 65535;
    private final IPv6Zone zone;
    private transient IPv6AddressSection.IPv6StringCache stringCache;
    transient IPv6AddressSection.IPv6AddressCache addressCache;

    IPv6Address(IPv6AddressSection section, CharSequence zone, boolean checkZone) throws AddressValueException {
        this(section, checkZone ? IPv6Address.checkZone(zone) : (zone != null && zone.length() > 0 ? new IPv6Zone(zone.toString()) : null));
    }

    public IPv6Address(IPv6AddressSection section, IPv6Zone zone) throws AddressValueException {
        super(section);
        if (section.getSegmentCount() != 8) {
            throw new AddressValueException("ipaddress.error.ipv6.invalid.segment.count", section.getSegmentCount());
        }
        if (section.addressSegmentIndex != 0) {
            throw new AddressPositionException(section.addressSegmentIndex);
        }
        this.zone = zone;
    }

    @Deprecated
    public IPv6Address(IPv6AddressSection section, CharSequence zone) throws AddressValueException {
        this(section, zone, true);
    }

    public IPv6Address(IPv6AddressSection section) throws AddressValueException {
        this(section, (CharSequence)null);
    }

    public IPv6Address(IPv6AddressSegment[] segments) throws AddressValueException {
        this(segments, null, null);
    }

    public IPv6Address(IPv6AddressSegment[] segments, Integer networkPrefixLength) throws AddressValueException {
        this(segments, networkPrefixLength, null);
    }

    @Deprecated
    public IPv6Address(IPv6AddressSegment[] segments, CharSequence zone) throws AddressValueException {
        this(segments, IPv6Address.checkZone(zone));
    }

    public IPv6Address(IPv6AddressSegment[] segments, IPv6Zone zone) throws AddressValueException {
        this(segments, null, zone);
    }

    private IPv6Address(IPv6AddressSegment[] segments, Integer networkPrefixLength, IPv6Zone zone) throws AddressValueException {
        super((Address thisAddress) -> ((IPv6Address)thisAddress).getDefaultCreator().createSection(segments, networkPrefixLength));
        if (segments.length != 8) {
            throw new AddressValueException("ipaddress.error.ipv6.invalid.segment.count", segments.length);
        }
        this.zone = zone;
    }

    public IPv6Address(Inet6Address inet6Address) {
        this(inet6Address, inet6Address.getAddress(), null, IPv6Address.getZone(inet6Address));
    }

    public IPv6Address(Inet6Address inet6Address, Integer networkPrefixLength) {
        this(inet6Address, inet6Address.getAddress(), networkPrefixLength, IPv6Address.getZone(inet6Address));
    }

    private IPv6Address(Inet6Address inet6Address, byte[] bytes, Integer networkPrefixLength, IPv6Zone zone) throws AddressValueException {
        super((Address thisAddress) -> ((IPv6Address)thisAddress).getDefaultCreator().createSection(bytes, 0, bytes.length, 8, networkPrefixLength));
        this.zone = zone;
        this.getSection().setInetAddress(inet6Address);
    }

    @Deprecated
    public IPv6Address(byte[] bytes, CharSequence zone) throws AddressValueException {
        this(bytes, IPv6Address.checkZone(zone));
    }

    public IPv6Address(byte[] bytes, IPv6Zone zone) throws AddressValueException {
        this(bytes, null, zone);
    }

    public IPv6Address(byte[] bytes) throws AddressValueException {
        this(bytes, null, null);
    }

    public IPv6Address(byte[] bytes, int byteStartIndex, int byteEndIndex) throws AddressValueException {
        this(bytes, byteStartIndex, byteEndIndex, null, null);
    }

    public IPv6Address(byte[] bytes, Integer networkPrefixLength) throws AddressValueException {
        this(bytes, networkPrefixLength, null);
    }

    public IPv6Address(byte[] bytes, int byteStartIndex, int byteEndIndex, Integer networkPrefixLength) throws AddressValueException {
        this(bytes, byteStartIndex, byteEndIndex, networkPrefixLength, null);
    }

    public IPv6Address(BigInteger val) throws AddressValueException {
        this(val, null, (IPv6Zone)null);
    }

    public IPv6Address(BigInteger val, Integer networkPrefixLength) throws AddressValueException {
        this(val, networkPrefixLength, (IPv6Zone)null);
    }

    @Deprecated
    public IPv6Address(BigInteger val, CharSequence zone) throws AddressValueException {
        this(val, IPv6Address.checkZone(zone));
    }

    public IPv6Address(BigInteger val, IPv6Zone zone) throws AddressValueException {
        this(val, null, zone);
    }

    @Deprecated
    public IPv6Address(BigInteger val, Integer networkPrefixLength, CharSequence zone) throws AddressValueException {
        this(val, networkPrefixLength, IPv6Address.checkZone(zone));
    }

    public IPv6Address(BigInteger val, Integer networkPrefixLength, IPv6Zone zone) throws AddressValueException {
        super((Address thisAddress) -> ((IPv6Address)thisAddress).getDefaultCreator().createSectionInternal(val.toByteArray(), 8, networkPrefixLength, false));
        this.zone = zone;
    }

    private IPv6Address(byte[] bytes, Integer networkPrefixLength, IPv6Zone zone) throws AddressValueException {
        this(bytes, 0, bytes.length, networkPrefixLength, zone);
    }

    private IPv6Address(byte[] bytes, int byteStartIndex, int byteEndIndex, Integer networkPrefixLength, IPv6Zone zone) throws AddressValueException {
        super((Address thisAddress) -> ((IPv6Address)thisAddress).getDefaultCreator().createSection(bytes, byteStartIndex, byteEndIndex, 8, networkPrefixLength));
        this.zone = zone;
    }

    public IPv6Address(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer networkPrefixLength) throws AddressValueException {
        this(lowerValueProvider, upperValueProvider, networkPrefixLength, null);
    }

    public IPv6Address(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider) {
        this(lowerValueProvider, upperValueProvider, null, null);
    }

    public IPv6Address(Address.SegmentValueProvider valueProvider, Integer networkPrefixLength) throws AddressValueException {
        this(valueProvider, valueProvider, networkPrefixLength);
    }

    public IPv6Address(Address.SegmentValueProvider valueProvider) {
        this(valueProvider, (Integer)null);
    }

    @Deprecated
    public IPv6Address(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, CharSequence zone) throws AddressValueException {
        this(lowerValueProvider, upperValueProvider, IPv6Address.checkZone(zone));
    }

    public IPv6Address(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, IPv6Zone zone) throws AddressValueException {
        this(lowerValueProvider, upperValueProvider, null, zone);
    }

    private IPv6Address(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer networkPrefixLength, IPv6Zone zone) throws AddressValueException {
        super((Address thisAddress) -> ((IPv6Address)thisAddress).getDefaultCreator().createFullSectionInternal(lowerValueProvider, upperValueProvider, networkPrefixLength));
        this.zone = zone;
    }

    public IPv6Address(IPv6Address prefix, MACAddress eui) throws IncompatibleAddressException {
        this(prefix.getSection(), eui.getSection());
    }

    public IPv6Address(IPv6AddressSection section, MACAddress eui) throws IncompatibleAddressException, AddressValueException {
        this(section, eui.getSection());
    }

    public IPv6Address(IPv6AddressSection section, MACAddressSection eui) throws IncompatibleAddressException, AddressValueException {
        this(section, eui, (IPv6Zone)null);
    }

    @Deprecated
    public IPv6Address(IPv6AddressSection section, MACAddressSection eui, CharSequence zone) throws IncompatibleAddressException, AddressValueException {
        this(section, eui, IPv6Address.checkZone(zone));
    }

    public IPv6Address(IPv6AddressSection section, MACAddressSection eui, IPv6Zone zone) throws IncompatibleAddressException, AddressValueException {
        super((Address thisAddress) -> IPv6Address.toFullEUI64Section(section, eui, ((IPv6Address)thisAddress).getDefaultCreator(), ((IPv6Address)thisAddress).getMACNetwork().getAddressCreator()));
        this.zone = zone;
    }

    static IPv6Zone checkZone(CharSequence zone) throws AddressValueException {
        if (zone == null) {
            return null;
        }
        String zoneStr = zone.toString().trim();
        if (zone.length() == 0) {
            return null;
        }
        int invalidIndex = Validator.validateZone(zoneStr);
        if (invalidIndex >= 0) {
            throw new AddressValueException("ipaddress.error.invalid.zone", invalidIndex);
        }
        return new IPv6Zone(zoneStr);
    }

    IPv6AddressNetwork.IPv6AddressCreator getDefaultCreator() {
        return this.getNetwork().getAddressCreator();
    }

    IPv6AddressNetwork.IPv6AddressCreator getCreator() {
        IPv6AddressNetwork.IPv6AddressCreator defaultCreator = this.getDefaultCreator();
        if (!this.hasZone()) {
            return defaultCreator;
        }
        IPv6AddressNetwork.IPv6AddressCreator creator = new IPv6AddressNetwork.IPv6AddressCreator(this.getNetwork(), defaultCreator.cache){
            private static final long serialVersionUID = 4L;

            @Override
            protected IPv6Address createAddressInternal(IPv6AddressSegment[] segments) {
                IPv6AddressNetwork.IPv6AddressCreator creator = IPv6Address.this.getDefaultCreator();
                return creator.createAddress(segments, IPv6Address.this.zone);
            }

            @Override
            public IPv6Address createAddress(IPv6AddressSection section) {
                IPv6AddressNetwork.IPv6AddressCreator creator = IPv6Address.this.getDefaultCreator();
                return creator.createAddress(section, IPv6Address.this.zone);
            }
        };
        creator.useSegmentCache = defaultCreator.useSegmentCache;
        return creator;
    }

    private static IPv6Zone getZone(Inet6Address inet6Address) {
        NetworkInterface networkInterface = inet6Address.getScopedInterface();
        if (networkInterface != null) {
            return new IPv6Zone(networkInterface);
        }
        int scopeId = inet6Address.getScopeId();
        if (scopeId != 0) {
            return new IPv6Zone(scopeId);
        }
        return null;
    }

    private static IPv6AddressSection toFullEUI64Section(IPv6AddressSection section, MACAddressSection eui, IPv6AddressNetwork.IPv6AddressCreator creator, MACAddressNetwork.MACAddressCreator macCreator) throws AddressValueException, IncompatibleAddressException {
        boolean euiIsExtended = eui.isExtended();
        if (eui.addressSegmentIndex != 0) {
            throw new AddressPositionException((AddressItem)eui, eui.addressSegmentIndex);
        }
        if (section.addressSegmentIndex != 0) {
            throw new AddressPositionException((AddressItem)section, section.addressSegmentIndex);
        }
        if (section.getSegmentCount() < 4) {
            throw new AddressValueException((AddressItem)section, "ipaddress.mac.error.not.eui.convertible");
        }
        if (eui.getSegmentCount() != (euiIsExtended ? 8 : 6)) {
            throw new AddressValueException((AddressItem)eui, "ipaddress.mac.error.not.eui.convertible");
        }
        AddressSegment[] segments = creator.createSegmentArray(8);
        section.getSegments(0, 4, segments, 0);
        Integer prefLength = section.getNetworkPrefixLength();
        Integer prefixLength = prefLength != null && prefLength <= 64 ? prefLength : null;
        IPv6Address.toEUI64Segments((IPv6AddressSegment[])segments, 4, eui, 0, eui.isExtended(), creator, macCreator, prefixLength);
        return creator.createSectionInternal((IPv6AddressSegment[])segments);
    }

    static IPv6AddressSegment[] toEUI64Segments(IPv6AddressSegment[] segments, int ipv6StartIndex, MACAddressSection eui, int euiStartIndex, boolean isExtended, IPv6AddressNetwork.IPv6AddressCreator creator, MACAddressNetwork.MACAddressCreator macCreator, Integer prefixLength) throws IncompatibleAddressException {
        boolean isNotNull;
        int euiSegmentIndex = 0;
        int euiSegmentCount = eui.getSegmentCount();
        MACAddressSegment seg0 = euiStartIndex == 0 && euiSegmentIndex < euiSegmentCount ? eui.getSegment(euiSegmentIndex++) : null;
        MACAddressSegment seg1 = euiStartIndex <= 1 && euiSegmentIndex < euiSegmentCount ? eui.getSegment(euiSegmentIndex++) : null;
        MACAddressSegment seg2 = euiStartIndex <= 2 && euiSegmentIndex < euiSegmentCount ? eui.getSegment(euiSegmentIndex++) : null;
        MACAddressSegment seg3 = euiStartIndex <= 3 && euiSegmentIndex < euiSegmentCount ? eui.getSegment(euiSegmentIndex++) : null;
        MACAddressSegment seg4 = euiStartIndex <= 4 && euiSegmentIndex < euiSegmentCount ? eui.getSegment(euiSegmentIndex++) : null;
        MACAddressSegment seg5 = euiStartIndex <= 5 && euiSegmentIndex < euiSegmentCount ? eui.getSegment(euiSegmentIndex++) : null;
        MACAddressSegment seg6 = euiStartIndex <= 6 && euiSegmentIndex < euiSegmentCount ? eui.getSegment(euiSegmentIndex++) : null;
        MACAddressSegment seg7 = euiStartIndex <= 7 && euiSegmentIndex < euiSegmentCount ? eui.getSegment(euiSegmentIndex) : null;
        MACAddressSegment zeroSegment = macCreator.createSegment(0);
        MACAddressSegment ffSegment = macCreator.createSegment(255);
        MACAddressSegment feSegment = macCreator.createSegment(254);
        Integer currentPrefix = null;
        if (prefixLength != null) {
            currentPrefix = 0;
        }
        if ((isNotNull = seg0 != null) || seg1 != null) {
            if (isNotNull) {
                if (seg1 == null) {
                    seg1 = zeroSegment;
                }
            } else {
                seg0 = zeroSegment;
            }
            segments[ipv6StartIndex++] = IPv6Address.join(creator, seg0, seg1, true, currentPrefix);
        }
        if (isExtended) {
            isNotNull = seg2 != null;
            if (isNotNull || seg3 != null) {
                if (!isNotNull) {
                    seg2 = zeroSegment;
                    if (!seg3.matches(255)) {
                        throw new IncompatibleAddressException(eui, "ipaddress.mac.error.not.eui.convertible");
                    }
                }
                segments[ipv6StartIndex++] = IPv6Address.join(creator, seg2, ffSegment, currentPrefix);
            }
            if ((isNotNull = seg4 != null) || seg5 != null) {
                if (isNotNull) {
                    if (!seg4.matches(254)) {
                        throw new IncompatibleAddressException(eui, "ipaddress.mac.error.not.eui.convertible");
                    }
                    if (seg5 == null) {
                        seg5 = zeroSegment;
                    }
                }
                segments[ipv6StartIndex++] = IPv6Address.join(creator, feSegment, seg5, currentPrefix);
            }
        } else {
            if (seg2 != null) {
                segments[ipv6StartIndex++] = IPv6Address.join(creator, seg2, ffSegment, currentPrefix);
            }
            if (seg3 != null) {
                segments[ipv6StartIndex++] = IPv6Address.join(creator, feSegment, seg3, currentPrefix);
            }
            if ((isNotNull = seg4 != null) || seg5 != null) {
                if (isNotNull) {
                    if (seg5 == null) {
                        seg5 = zeroSegment;
                    }
                } else {
                    seg4 = zeroSegment;
                }
                segments[ipv6StartIndex++] = IPv6Address.join(creator, seg4, seg5, currentPrefix);
            }
        }
        if ((isNotNull = seg6 != null) || seg7 != null) {
            if (isNotNull) {
                if (seg7 == null) {
                    seg7 = zeroSegment;
                }
            } else {
                seg6 = zeroSegment;
            }
            segments[ipv6StartIndex] = IPv6Address.join(creator, seg6, seg7, currentPrefix);
        }
        return segments;
    }

    private static IPv6AddressSegment join(IPv6AddressNetwork.IPv6AddressCreator creator, MACAddressSegment macSegment0, MACAddressSegment macSegment1, Integer prefixLength) {
        return IPv6Address.join(creator, macSegment0, macSegment1, false, prefixLength);
    }

    private static IPv6AddressSegment join(IPv6AddressNetwork.IPv6AddressCreator creator, MACAddressSegment macSegment0, MACAddressSegment macSegment1, boolean flip, Integer prefixLength) {
        if (macSegment0.isMultiple() && !macSegment1.isFullRange()) {
            throw new IncompatibleAddressException((AddressItem)macSegment0, macSegment1, "ipaddress.error.invalidMACIPv6Range");
        }
        int lower0 = macSegment0.getSegmentValue();
        int upper0 = macSegment0.getUpperSegmentValue();
        if (flip) {
            int mask2ndBit = 2;
            if (!macSegment0.matchesWithMask(mask2ndBit & lower0, mask2ndBit)) {
                throw new IncompatibleAddressException(macSegment0, "ipaddress.mac.error.not.eui.convertible");
            }
            lower0 ^= mask2ndBit;
            upper0 ^= mask2ndBit;
        }
        return creator.createSegment(lower0 << 8 | macSegment1.getSegmentValue(), upper0 << 8 | macSegment1.getUpperSegmentValue(), prefixLength);
    }

    @Override
    public IPv6AddressNetwork getNetwork() {
        return IPv6Address.defaultIpv6Network();
    }

    public MACAddressNetwork getMACNetwork() {
        return IPv6Address.defaultMACNetwork();
    }

    public IPv4AddressNetwork getIPv4Network() {
        return IPv6Address.defaultIpv4Network();
    }

    @Override
    public IPv6AddressSection getSection() {
        return (IPv6AddressSection)super.getSection();
    }

    @Override
    public IPv6AddressSection getSection(int index) {
        return this.getSection().getSection(index);
    }

    @Override
    public IPv6AddressSection getSection(int index, int endIndex) {
        return this.getSection().getSection(index, endIndex);
    }

    @Override
    public IPv6AddressSegment getDivision(int index) {
        return this.getSegment(index);
    }

    @Override
    public IPv6AddressSegment getSegment(int index) {
        return this.getSection().getSegment(index);
    }

    public IPv6AddressSegment[] getSegments() {
        return this.getSection().getSegments();
    }

    public boolean isEUI64() {
        return this.getSection().isEUI64();
    }

    public MACAddress toEUI(boolean extended) {
        MACAddressSection section = this.getSection().toEUI(extended);
        if (section == null) {
            return null;
        }
        MACAddressNetwork.MACAddressCreator creator = this.getMACNetwork().getAddressCreator();
        return creator.createAddress(section);
    }

    @Override
    public IPAddressStringDivisionSeries[] getParts(IPAddressSection.IPStringBuilderOptions options) {
        return this.getParts(IPv6AddressSection.IPv6StringBuilderOptions.from(options));
    }

    public IPAddressStringDivisionSeries[] getParts(IPv6AddressSection.IPv6StringBuilderOptions options) {
        IPAddressStringDivisionSeries[] parts = this.getSection().getParts(options);
        IPv4Address ipv4Addr = this.getConverted(options);
        if (ipv4Addr != null) {
            IPAddressStringDivisionSeries[] ipv4Parts = ipv4Addr.getParts(options.ipv4ConverterOptions);
            IPAddressStringDivisionSeries[] tmp = parts;
            parts = new IPAddressStringDivisionSeries[tmp.length + ipv4Parts.length];
            System.arraycopy(tmp, 0, parts, 0, tmp.length);
            System.arraycopy(ipv4Parts, 0, parts, tmp.length, ipv4Parts.length);
        }
        return parts;
    }

    @Override
    public int getSegmentCount() {
        return 8;
    }

    @Override
    public int getByteCount() {
        return 16;
    }

    @Override
    public int getBitCount() {
        return 128;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void cache(IPv6Address lower, IPv6Address upper) {
        if ((lower != null || upper != null) && this.getSection().getSingleLowestOrHighestSection() == null) {
            this.getSection().cache(lower != null ? lower.getSection() : null, upper != null ? upper.getSection() : null);
            IPv6AddressSection.IPv6AddressCache cache = this.addressCache;
            if (cache == null || lower != null && cache.lower == null || upper != null && cache.upper == null) {
                IPv6Address iPv6Address = this;
                synchronized (iPv6Address) {
                    boolean create;
                    cache = this.addressCache;
                    boolean bl = create = cache == null;
                    if (create) {
                        this.addressCache = cache = new IPv6AddressSection.IPv6AddressCache();
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
    private IPv6Address getLowestOrHighest(boolean lowest, boolean excludeZeroHost) {
        IPv6Address result;
        block18: {
            IPv6AddressSection.IPv6AddressCache cache;
            IPv6AddressSection sectionResult;
            block17: {
                IPv6AddressSection currentSection = this.getSection();
                sectionResult = currentSection.getLowestOrHighestSection(lowest, excludeZeroHost);
                if (sectionResult == currentSection) {
                    return this;
                }
                if (sectionResult == null) {
                    return null;
                }
                result = null;
                cache = this.addressCache;
                if (cache == null) break block17;
                IPv6Address iPv6Address = lowest ? (excludeZeroHost ? (IPv6Address)cache.lowerNonZeroHost : (IPv6Address)cache.lower) : (result = (IPv6Address)cache.upper);
                if (iPv6Address != null) break block18;
            }
            IPv6Address iPv6Address = this;
            synchronized (iPv6Address) {
                boolean create;
                cache = this.addressCache;
                boolean bl = create = cache == null;
                if (create) {
                    this.addressCache = cache = new IPv6AddressSection.IPv6AddressCache();
                } else if (lowest) {
                    create = excludeZeroHost ? (result = (IPv6Address)cache.lowerNonZeroHost) == null : (result = (IPv6Address)cache.lower) == null;
                } else {
                    result = (IPv6Address)cache.upper;
                    boolean bl2 = create = result == null;
                }
                if (create) {
                    result = this.getCreator().createAddress(sectionResult);
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
    public IPv6Address getLowerNonZeroHost() {
        return this.getLowestOrHighest(true, true);
    }

    @Override
    public IPv6Address getLower() {
        return this.getLowestOrHighest(true, false);
    }

    @Override
    public IPv6Address getUpper() {
        return this.getLowestOrHighest(false, false);
    }

    public IPv6Address replace(int startIndex, int endIndex, IPv6Address replacement, int replacementIndex) {
        return this.checkIdentity(this.getSection().replace(startIndex, endIndex, replacement.getSection(), replacementIndex, replacementIndex + (endIndex - startIndex)));
    }

    @Override
    public IPv6Address reverseBits(boolean perByte) {
        return this.getCreator().createAddress(this.getSection().reverseBits(perByte));
    }

    @Override
    public IPv6Address reverseBytes() {
        return this.checkIdentity(this.getSection().reverseBytes());
    }

    @Override
    public IPv6Address reverseBytesPerSegment() {
        return this.checkIdentity(this.getSection().reverseBytesPerSegment());
    }

    @Override
    public IPv6Address reverseSegments() {
        return this.checkIdentity(this.getSection().reverseSegments());
    }

    public Iterator<IPv6AddressSegment[]> segmentsNonZeroHostIterator() {
        return this.getSection().segmentsNonZeroHostIterator();
    }

    public Iterator<IPv6AddressSegment[]> segmentsIterator() {
        return this.getSection().segmentsIterator();
    }

    public AddressComponentRangeSpliterator<IPv6Address, IPv6AddressSegment[]> segmentsSpliterator() {
        return this.getSection().segmentsSpliterator(this, this.getCreator());
    }

    public Stream<IPv6AddressSegment[]> segmentsStream() {
        return StreamSupport.stream(this.segmentsSpliterator(), false);
    }

    public Iterator<IPv6Address> prefixBlockIterator() {
        return this.getSection().prefixIterator(this, this.getCreator(), true);
    }

    public AddressComponentSpliterator<IPv6Address> prefixBlockSpliterator() {
        return this.getSection().prefixSpliterator(this, this.getCreator(), true);
    }

    public Stream<IPv6Address> prefixBlockStream() {
        return StreamSupport.stream(this.prefixBlockSpliterator(), false);
    }

    public Iterator<IPv6Address> prefixBlockIterator(int prefixLength) {
        return this.getSection().prefixIterator(this, this.getCreator(), true, prefixLength);
    }

    public AddressComponentSpliterator<IPv6Address> prefixBlockSpliterator(int prefixLength) {
        return this.getSection().prefixSpliterator(this, this.getCreator(), true, prefixLength);
    }

    public Stream<IPv6Address> prefixBlockStream(int prefixLength) {
        return StreamSupport.stream(this.prefixBlockSpliterator(prefixLength), false);
    }

    public Iterator<IPv6Address> prefixIterator() {
        return this.getSection().prefixIterator(this, this.getCreator(), false);
    }

    public AddressComponentSpliterator<IPv6Address> prefixSpliterator() {
        return this.getSection().prefixSpliterator(this, this.getCreator(), false);
    }

    public Stream<IPv6Address> prefixStream() {
        return StreamSupport.stream(this.prefixSpliterator(), false);
    }

    public Iterator<IPv6Address> prefixIterator(int prefixLength) {
        return this.getSection().prefixIterator(this, this.getCreator(), false, prefixLength);
    }

    public AddressComponentSpliterator<IPv6Address> prefixSpliterator(int prefixLength) {
        return this.getSection().prefixSpliterator(this, this.getCreator(), false, prefixLength);
    }

    public Stream<IPv6Address> prefixStream(int prefixLength) {
        return StreamSupport.stream(this.prefixSpliterator(prefixLength), false);
    }

    public Iterator<IPv6Address> blockIterator(int segmentCount) {
        return this.getSection().blockIterator(this, this.getCreator(), segmentCount);
    }

    public AddressComponentSpliterator<IPv6Address> blockSpliterator(int segmentCount) {
        return this.getSection().blockSpliterator(this, this.getCreator(), segmentCount);
    }

    public Stream<IPv6Address> blockStream(int segmentCount) {
        return StreamSupport.stream(this.blockSpliterator(segmentCount), false);
    }

    public Iterator<IPv6Address> sequentialBlockIterator() {
        return super.sequentialBlockIterator();
    }

    public AddressComponentSpliterator<IPv6Address> sequentialBlockSpliterator() {
        return super.sequentialBlockSpliterator();
    }

    public Stream<IPv6Address> sequentialBlockStream() {
        return super.sequentialBlockStream();
    }

    @Override
    public Iterator<IPv6Address> iterator() {
        return this.getSection().iterator(this, this.getCreator(), null);
    }

    @Override
    public AddressComponentSpliterator<IPv6Address> spliterator() {
        return this.getSection().spliterator(this, this.getCreator(), false);
    }

    public Stream<IPv6Address> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Iterator<IPv6Address> nonZeroHostIterator() {
        Predicate<IPv6AddressSegment[]> excludeFunc = null;
        if (this.includesZeroHost()) {
            int prefLength = this.getNetworkPrefixLength();
            excludeFunc = segments -> this.getSection().isZeroHost((IPv6AddressSegment[])segments, prefLength);
        }
        return this.getSection().iterator(this, this.getCreator(), excludeFunc);
    }

    public Iterable<IPv6Address> getIterable() {
        return this;
    }

    @Override
    public IPv6Address increment(long increment) {
        return this.checkIdentity(this.getSection().increment(increment));
    }

    @Override
    public IPv6Address incrementBoundary(long increment) {
        return this.checkIdentity(this.getSection().incrementBoundary(increment));
    }

    @Override
    public IPv4Address toIPv4() {
        IPAddressConverter conv = DEFAULT_ADDRESS_CONVERTER;
        return conv.toIPv4(this);
    }

    @Override
    public IPv6Address toIPv6() {
        return this;
    }

    @Override
    public boolean isIPv6() {
        return true;
    }

    @Override
    public boolean isIPv4Convertible() {
        IPAddressConverter conv = DEFAULT_ADDRESS_CONVERTER;
        return conv.isIPv4Convertible(this);
    }

    @Override
    public boolean isIPv6Convertible() {
        return true;
    }

    public IPv4AddressSection toMappedIPv4Segments() {
        if (this.isIPv4Mapped()) {
            return this.getSection().getEmbeddedIPv4AddressSection();
        }
        return null;
    }

    public IPv4Address get6To4IPv4Address() {
        return this.getEmbeddedIPv4Address(2);
    }

    public IPv4Address getEmbeddedIPv4Address() {
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getIPv4Network().getAddressCreator();
        return creator.createAddress(this.getSection().getEmbeddedIPv4AddressSection());
    }

    public IPv4Address getEmbeddedIPv4Address(int byteIndex) {
        if (byteIndex == 12) {
            return this.getEmbeddedIPv4Address();
        }
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getIPv4Network().getAddressCreator();
        return creator.createAddress(this.getSection().getEmbeddedIPv4AddressSection(byteIndex, byteIndex + 4));
    }

    @Override
    public boolean isLocal() {
        if (this.isMulticast()) {
            IPv6AddressSegment firstSeg = this.getSegment(0);
            if (firstSeg.matchesWithMask(8, 15)) {
                return true;
            }
            if (firstSeg.getValueCount() <= 5 && (firstSeg.getSegmentValue() & 0xF) >= 1 && (firstSeg.getUpperSegmentValue() & 0xF) <= 5) {
                return true;
            }
            if (firstSeg.matchesWithPrefixMask(65328, (Integer)12) && this.getSegment(6).matchesWithPrefixMask(32768, (Integer)1)) {
                return true;
            }
        }
        return this.isLinkLocal() || this.isSiteLocal() || this.isUniqueLocal() || this.isAnyLocal();
    }

    @Override
    public boolean isLinkLocal() {
        IPv6AddressSegment firstSeg = this.getSegment(0);
        return this.isMulticast() && firstSeg.matchesWithMask(2, 15) || firstSeg.matchesWithPrefixMask(65152, (Integer)10);
    }

    public boolean isSiteLocal() {
        IPv6AddressSegment firstSeg = this.getSegment(0);
        return this.isMulticast() && firstSeg.matchesWithMask(5, 15) || firstSeg.matchesWithPrefixMask(65216, (Integer)10);
    }

    public boolean isUniqueLocal() {
        return this.getSegment(0).matchesWithPrefixMask(64512, (Integer)7);
    }

    public boolean isIPv4Mapped() {
        if (this.getSegment(5).matches(65535)) {
            for (int i = 0; i < 5; ++i) {
                if (this.getSegment(i).isZero()) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean isIPv4Compatible() {
        return this.getSegment(0).isZero() && this.getSegment(1).isZero() && this.getSegment(2).isZero() && this.getSegment(3).isZero() && this.getSegment(4).isZero() && this.getSegment(5).isZero();
    }

    public boolean is6To4() {
        return this.getSegment(0).matches(8194);
    }

    public boolean is6Over4() {
        return this.getSegment(0).matches(65152) && this.getSegment(1).isZero() && this.getSegment(2).isZero() && this.getSegment(3).isZero() && this.getSegment(4).isZero() && this.getSegment(5).isZero();
    }

    public boolean isTeredo() {
        return this.getSegment(0).matches(8193) && this.getSegment(1).isZero();
    }

    public boolean isIsatap() {
        return this.getSegment(0).matches(65152) && this.getSegment(1).isZero() && this.getSegment(2).isZero() && this.getSegment(3).isZero() && (this.getSegment(4).isZero() || this.getSegment(4).matches(512)) && this.getSegment(5).matches(24318);
    }

    public boolean isIPv4Translatable() {
        return this.getSegment(4).matches(65535) && this.getSegment(5).isZero() && this.getSegment(0).isZero() && this.getSegment(1).isZero() && this.getSegment(2).isZero() && this.getSegment(3).isZero();
    }

    public boolean isWellKnownIPv4Translatable() {
        if (this.getSegment(0).matches(100) && this.getSegment(1).matches(65435)) {
            for (int i = 2; i <= 5; ++i) {
                if (this.getSegment(i).isZero()) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isMulticast() {
        return this.getSegment(0).matchesWithPrefixMask(65280, (Integer)8);
    }

    @Override
    public boolean isLoopback() {
        int i;
        for (i = 0; i < this.getSegmentCount() - 1; ++i) {
            if (this.getSegment(i).isZero()) continue;
            return false;
        }
        return this.getSegment(i).matches(1);
    }

    @Override
    public IPv6Address intersect(IPAddress other) throws AddressConversionException {
        IPv6Address otherAddr;
        IPv6AddressSection thisSection = this.getSection();
        IPv6AddressSection section = thisSection.intersect((otherAddr = this.convertArg(other)).getSection());
        if (section == null) {
            return null;
        }
        IPv6AddressNetwork.IPv6AddressCreator creator = this.isSameZone(otherAddr) ? this.getCreator() : this.getDefaultCreator();
        IPv6Address result = creator.createAddress(section);
        return result;
    }

    public IPv6Address[] subtract(IPAddress other) throws AddressConversionException {
        IPv6AddressSection thisSection = this.getSection();
        IPv6AddressSection[] sections = thisSection.subtract(this.convertArg(other).getSection());
        if (sections == null) {
            return null;
        }
        IPv6Address[] result = new IPv6Address[sections.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = this.getCreator().createAddress(sections[i]);
        }
        return result;
    }

    private IPv6Address checkIdentity(IPv6AddressSection newSection) {
        if (newSection == this.getSection()) {
            return this;
        }
        return this.getCreator().createAddress(newSection);
    }

    @Override
    public IPv6Address adjustPrefixBySegment(boolean nextSegment) {
        return this.checkIdentity(this.getSection().adjustPrefixBySegment(nextSegment));
    }

    @Override
    public IPv6Address adjustPrefixBySegment(boolean nextSegment, boolean zeroed) {
        return this.checkIdentity(this.getSection().adjustPrefixBySegment(nextSegment, zeroed));
    }

    @Override
    public IPv6Address adjustPrefixLength(int adjustment) {
        return this.checkIdentity(this.getSection().adjustPrefixLength(adjustment));
    }

    @Override
    public IPv6Address adjustPrefixLength(int adjustment, boolean zeroed) {
        return this.checkIdentity(this.getSection().adjustPrefixLength(adjustment, zeroed));
    }

    @Override
    public IPv6Address setPrefixLength(int prefixLength) throws PrefixLenException {
        return this.setPrefixLength(prefixLength, true);
    }

    @Override
    public IPv6Address setPrefixLength(int prefixLength, boolean zeroed) throws PrefixLenException {
        return this.checkIdentity(this.getSection().setPrefixLength(prefixLength, zeroed));
    }

    @Override
    public IPv6Address setPrefixLength(int prefixLength, boolean zeroed, boolean zeroHostIsBlock) throws PrefixLenException {
        return this.checkIdentity(this.getSection().setPrefixLength(prefixLength, zeroed, zeroHostIsBlock));
    }

    @Override
    @Deprecated
    public IPv6Address applyPrefixLength(int networkPrefixLength) throws PrefixLenException {
        return this.checkIdentity(this.getSection().applyPrefixLength(networkPrefixLength));
    }

    @Override
    @Deprecated
    public IPv6Address removePrefixLength() {
        return this.removePrefixLength(true);
    }

    @Override
    public IPv6Address withoutPrefixLength() {
        return this.removePrefixLength(false);
    }

    @Override
    @Deprecated
    public IPv6Address removePrefixLength(boolean zeroed) {
        return this.checkIdentity(this.getSection().removePrefixLength(zeroed));
    }

    @Override
    protected IPv6Address convertArg(IPAddress arg) throws AddressConversionException {
        IPv6Address converted = arg.toIPv6();
        if (converted == null) {
            throw new AddressConversionException(this, arg);
        }
        return converted;
    }

    @Override
    public IPv6Address toZeroHost() {
        return this.toZeroHost(false);
    }

    @Override
    protected IPv6Address toZeroHost(boolean boundariesOnly) {
        if (!this.isPrefixed()) {
            IPv6AddressNetwork network;
            AddressNetwork.PrefixConfiguration config = (network = this.getNetwork()).getPrefixConfiguration();
            IPv6Address addr = (IPv6Address)network.getNetworkMask(0, !config.allPrefixedAddressesAreSubnets());
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
    public IPv6Address toZeroHost(int prefixLength) {
        if (this.isPrefixed() && prefixLength == this.getNetworkPrefixLength()) {
            return this.toZeroHost();
        }
        return this.checkIdentity(this.getSection().toZeroHost(prefixLength));
    }

    @Override
    public IPv6Address toZeroNetwork() {
        if (!this.isPrefixed()) {
            return (IPv6Address)this.getNetwork().getHostMask(this.getBitCount());
        }
        return this.checkIdentity(this.getSection().createZeroNetwork());
    }

    @Override
    public IPv6Address toMaxHost() {
        if (!this.isPrefixed()) {
            IPv6Address resultNoPrefix = (IPv6Address)this.getNetwork().getHostMask(0);
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
    public IPv6Address toMaxHost(int prefixLength) {
        if (this.isPrefixed() && prefixLength == this.getNetworkPrefixLength()) {
            return this.toMaxHost();
        }
        return this.checkIdentity(this.getSection().toMaxHost(prefixLength));
    }

    @Override
    public IPv6Address mask(IPAddress mask, boolean retainPrefix) throws IncompatibleAddressException, AddressConversionException {
        return this.checkIdentity(this.getSection().mask(this.convertArg(mask).getSection(), retainPrefix));
    }

    @Override
    public IPv6Address mask(IPAddress mask) throws IncompatibleAddressException, AddressConversionException {
        return this.mask(mask, false);
    }

    @Override
    public IPv6Address maskNetwork(IPAddress mask, int networkPrefixLength) throws IncompatibleAddressException, PrefixLenException, AddressConversionException {
        return this.checkIdentity(this.getSection().maskNetwork(this.convertArg(mask).getSection(), networkPrefixLength));
    }

    @Override
    public IPv6Address bitwiseOr(IPAddress mask, boolean retainPrefix) throws IncompatibleAddressException, AddressConversionException {
        return this.checkIdentity(this.getSection().bitwiseOr(this.convertArg(mask).getSection(), retainPrefix));
    }

    @Override
    public IPv6Address bitwiseOr(IPAddress mask) throws IncompatibleAddressException, AddressConversionException {
        return this.bitwiseOr(mask, false);
    }

    @Override
    public IPv6Address bitwiseOrNetwork(IPAddress mask, int networkPrefixLength) throws IncompatibleAddressException, PrefixLenException, AddressConversionException {
        return this.checkIdentity(this.getSection().bitwiseOrNetwork(this.convertArg(mask).getSection(), networkPrefixLength));
    }

    @Override
    public IPv6Address getHostMask() {
        return (IPv6Address)super.getHostMask();
    }

    @Override
    public IPv6Address getNetworkMask() {
        return (IPv6Address)super.getNetworkMask();
    }

    @Override
    public IPv6AddressSection getNetworkSection() {
        return this.getSection().getNetworkSection();
    }

    @Override
    public IPv6AddressSection getNetworkSection(int networkPrefixLength) throws PrefixLenException {
        return this.getSection().getNetworkSection(networkPrefixLength);
    }

    @Override
    public IPv6AddressSection getNetworkSection(int networkPrefixLength, boolean withPrefixLength) throws PrefixLenException {
        return this.getSection().getNetworkSection(networkPrefixLength, withPrefixLength);
    }

    @Override
    public IPv6AddressSection getHostSection(int networkPrefixLength) throws PrefixLenException {
        return this.getSection().getHostSection(networkPrefixLength);
    }

    @Override
    public IPv6AddressSection getHostSection() {
        return this.getSection().getHostSection();
    }

    @Override
    public IPv6Address toPrefixBlock() {
        Integer prefixLength = this.getNetworkPrefixLength();
        if (prefixLength == null || this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            return this;
        }
        return this.toPrefixBlock(prefixLength);
    }

    @Override
    public IPv6Address toPrefixBlock(int networkPrefixLength) throws PrefixLenException {
        return this.checkIdentity(this.getSection().toPrefixBlock(networkPrefixLength));
    }

    @Override
    public IPv6Address assignPrefixForSingleBlock() {
        return (IPv6Address)super.assignPrefixForSingleBlock();
    }

    @Override
    public IPv6Address assignMinPrefixForBlock() {
        return (IPv6Address)super.assignMinPrefixForBlock();
    }

    @Override
    public IPv6Address coverWithPrefixBlock() {
        return (IPv6Address)IPv6AddressSection.coverWithPrefixBlock(this, this.getLower(), this.getUpper());
    }

    @Override
    public IPv6Address coverWithPrefixBlock(IPAddress other) throws AddressConversionException {
        return IPv6AddressSection.coverWithPrefixBlock(this.removeZone(), this.convertArg(other).removeZone(), IPv6Address::getLower, IPv6Address::getUpper, Address.ADDRESS_LOW_VALUE_COMPARATOR::compare);
    }

    public IPv6Address[] spanWithPrefixBlocks() {
        if (this.isSequential()) {
            if (this.isSinglePrefixBlock()) {
                return new IPv6Address[]{this.removeZone()};
            }
            return this.spanWithPrefixBlocks(this);
        }
        ArrayList list = (ArrayList)this.removeZone().spanWithBlocks(true);
        return list.toArray(new IPv6Address[list.size()]);
    }

    public IPv6Address[] spanWithPrefixBlocks(IPAddress other) throws AddressConversionException {
        return (IPv6Address[])IPAddress.getSpanningPrefixBlocks((IPAddress)this.removeZone(), (IPAddress)this.convertArg(other).removeZone(), IPv6Address::getLower, IPv6Address::getUpper, Address.ADDRESS_LOW_VALUE_COMPARATOR::compare, IPv6Address::assignPrefixForSingleBlock, IPv6Address::withoutPrefixLength, this.getCreator()::createAddressArray);
    }

    public IPv6Address[] spanWithSequentialBlocks() throws AddressConversionException {
        if (this.isSequential()) {
            return new IPv6Address[]{this.withoutPrefixLength().removeZone()};
        }
        ArrayList list = (ArrayList)this.removeZone().spanWithBlocks(false);
        return list.toArray(new IPv6Address[list.size()]);
    }

    public IPv6Address[] spanWithSequentialBlocks(IPAddress other) throws AddressConversionException {
        return (IPv6Address[])IPAddress.getSpanningSequentialBlocks((IPAddress)this.removeZone(), (IPAddress)this.convertArg(other).removeZone(), IPv6Address::getLower, IPv6Address::getUpper, Address.ADDRESS_LOW_VALUE_COMPARATOR::compare, IPv6Address::withoutPrefixLength, (IPAddressNetwork.IPAddressCreator)this.getDefaultCreator());
    }

    @Override
    public IPv6AddressSeqRange spanWithRange(IPAddress other) throws AddressConversionException {
        return this.toSequentialRange(other);
    }

    public IPv6Address[] mergeToPrefixBlocks(IPAddress ... addresses) throws AddressConversionException {
        if (addresses.length == 0 && this.isSinglePrefixBlock()) {
            return new IPv6Address[]{this.removeZone()};
        }
        IPAddressSegmentSeries[] converted = this.getConverted(addresses);
        List<IPAddressSegmentSeries> blocks = IPv6Address.getMergedPrefixBlocks(converted);
        return blocks.toArray(new IPv6Address[blocks.size()]);
    }

    private IPAddress[] getConverted(IPAddress ... addresses) {
        IPAddress[] converted = new IPAddress[addresses.length + 1];
        int i = 0;
        int j = 1;
        while (i < addresses.length) {
            converted[j] = this.convertArg(addresses[i]).removeZone();
            i = j++;
        }
        converted[0] = this.removeZone();
        return converted;
    }

    public IPv6Address[] mergeToSequentialBlocks(IPAddress ... addresses) throws AddressConversionException {
        if (addresses.length == 0 && this.isSequential()) {
            return new IPv6Address[]{this.removeZone()};
        }
        addresses = (IPAddress[])addresses.clone();
        for (int i = 0; i < addresses.length; ++i) {
            addresses[i] = this.convertArg(addresses[i]).removeZone();
        }
        IPAddressSegmentSeries[] converted = this.getConverted(addresses);
        List<IPAddressSegmentSeries> blocks = IPv6Address.getMergedSequentialBlocks(converted, this.getDefaultCreator());
        return blocks.toArray(new IPv6Address[blocks.size()]);
    }

    public boolean hasZone() {
        return this.zone != null;
    }

    public String getZone() {
        return this.getZoneString();
    }

    public IPv6Address setZone(IPv6Zone newZone) {
        if (newZone == null) {
            return this.removeZone();
        }
        return this.getDefaultCreator().createAddress(this.getSection(), newZone);
    }

    public IPv6Zone getIPv6Zone() {
        return this.zone;
    }

    public IPv6Address removeZone() {
        if (this.hasZone()) {
            return this.getDefaultCreator().createAddress(this.getSection());
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean hasNoValueCache() {
        if (this.addressCache == null) {
            IPv6Address iPv6Address = this;
            synchronized (iPv6Address) {
                if (this.addressCache == null) {
                    this.addressCache = new IPv6AddressSection.IPv6AddressCache();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Inet6Address toInetAddress() {
        if (this.hasZone()) {
            Inet6Address result;
            if (this.hasNoValueCache() || (result = this.addressCache.inetAddress) == null) {
                this.addressCache.inetAddress = result = this.toInetAddressImpl();
            }
            return result;
        }
        return (Inet6Address)super.toInetAddress();
    }

    @Override
    public Inet6Address toUpperInetAddress() {
        return (Inet6Address)super.toUpperInetAddress();
    }

    @Override
    protected Inet6Address toInetAddressImpl() {
        Inet6Address result;
        byte[] bytes = this.getSection().getBytesInternal();
        try {
            IPv6Address adjusted;
            InetAddress resultIP;
            result = this.hasZone() ? (this.zone.referencesScopeId() ? Inet6Address.getByAddress(null, bytes, this.zone.getAssociatedScopeId()) : (this.zone.referencesIntf() && this.zone.getAssociatedIntf() != null ? Inet6Address.getByAddress(null, bytes, this.zone.getAssociatedIntf()) : ((resultIP = InetAddress.getByName((adjusted = this.getLower().withoutPrefixLength()).toNormalizedString())) instanceof Inet6Address ? (Inet6Address)resultIP : Inet6Address.getByAddress(null, bytes, null)))) : Inet6Address.getByAddress(null, bytes, null);
        }
        catch (UnknownHostException e) {
            result = null;
        }
        return result;
    }

    @Override
    @Deprecated
    public IPv6AddressSeqRange toSequentialRange(IPAddress other) {
        return new IPv6AddressSeqRange(this, this.convertArg(other));
    }

    @Override
    public IPv6AddressSeqRange toSequentialRange() {
        IPv6Address thiz = this.removeZone().withoutPrefixLength();
        return new IPv6AddressSeqRange(thiz.getLower(), thiz.getUpper(), true);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        if (this.hasZone()) {
            result *= this.zone.getName().hashCode();
        }
        return result;
    }

    @Override
    public boolean isSameAddress(Address other) {
        return other instanceof IPv6Address && super.isSameAddress(other) && this.isSameZone((IPv6Address)other);
    }

    private boolean isSameZone(IPv6Address otherIPv6Address) {
        return Objects.equals(this.zone, otherIPv6Address.zone);
    }

    @Override
    public boolean contains(Address other) {
        if (super.contains(other)) {
            if (other != this && this.hasZone()) {
                return this.isSameZone((IPv6Address)other);
            }
            return true;
        }
        return false;
    }

    @Override
    protected IPAddressStringParameters createFromStringParams() {
        return new IPAddressStringParameters.Builder().getIPv4AddressParametersBuilder().setNetwork(this.getIPv4Network()).getParentBuilder().getIPv6AddressParametersBuilder().setNetwork(this.getNetwork()).getParentBuilder().toParams();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean hasNoStringCache() {
        if (this.stringCache == null) {
            IPv6Address iPv6Address = this;
            synchronized (iPv6Address) {
                if (this.stringCache == null) {
                    if (this.hasZone()) {
                        this.stringCache = new IPv6AddressSection.IPv6StringCache();
                        return true;
                    }
                    IPv6AddressSection section = this.getSection();
                    boolean result = section.hasNoStringCache();
                    this.stringCache = section.getStringCache();
                    return result;
                }
            }
        }
        return false;
    }

    public String toMixedString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.mixedString) == null) {
            if (this.hasZone()) {
                this.stringCache.mixedString = result = this.toNormalizedString(IPv6AddressSection.IPv6StringCache.mixedParams);
            } else {
                result = this.getSection().toMixedString();
            }
        }
        return result;
    }

    @Override
    public String toCanonicalString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.canonicalString) == null) {
            if (this.hasZone()) {
                this.stringCache.canonicalString = result = this.toNormalizedString(IPv6AddressSection.IPv6StringCache.canonicalParams);
            } else {
                result = this.getSection().toCanonicalString();
            }
        }
        return result;
    }

    @Override
    public String toFullString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.fullString) == null) {
            if (this.hasZone()) {
                this.stringCache.fullString = result = this.toNormalizedString(IPv6AddressSection.IPv6StringCache.fullParams);
            } else {
                result = this.getSection().toFullString();
            }
        }
        return result;
    }

    public static String toNormalizedString(IPv6AddressNetwork network, Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength, CharSequence zone) {
        return IPv6Address.toNormalizedString(network.getPrefixConfiguration(), lowerValueProvider, upperValueProvider, prefixLength, 8, 2, 16, 65535, ':', 16, zone);
    }

    @Override
    public String toNormalizedString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.normalizedString) == null) {
            if (this.hasZone()) {
                this.stringCache.normalizedString = result = this.toNormalizedString(IPv6AddressSection.IPv6StringCache.normalizedParams);
            } else {
                result = this.getSection().toNormalizedString();
            }
        }
        return result;
    }

    @Override
    public String toCompressedString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.compressedString) == null) {
            if (this.hasZone()) {
                this.stringCache.compressedString = result = this.toNormalizedString(IPv6AddressSection.IPv6StringCache.compressedParams);
            } else {
                result = this.getSection().toCompressedString();
            }
        }
        return result;
    }

    @Override
    public String toSubnetString() {
        return this.toPrefixLengthString();
    }

    @Override
    public String toNormalizedWildcardString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.normalizedWildcardString) == null) {
            if (this.hasZone()) {
                this.stringCache.normalizedWildcardString = result = this.toNormalizedString(IPv6AddressSection.IPv6StringCache.wildcardNormalizedParams);
            } else {
                result = this.getSection().toNormalizedWildcardString();
            }
        }
        return result;
    }

    public String toBase85String() throws IncompatibleAddressException {
        String result;
        IPAddressString originator = this.getAddressfromString();
        if (originator != null && (!this.isPrefixed() || this.getNetworkPrefixLength() == 128) && originator.isBase85IPv6()) {
            return originator.toString();
        }
        if (this.hasNoStringCache() || (result = this.stringCache.base85String) == null) {
            if (this.hasZone()) {
                this.stringCache.base85String = result = this.getSection().toBase85String(this.getZone());
            } else {
                result = this.getSection().toBase85String();
            }
        }
        return result;
    }

    @Override
    public String toCanonicalWildcardString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.canonicalWildcardString) == null) {
            if (this.hasZone()) {
                this.stringCache.canonicalWildcardString = result = this.toNormalizedString(IPv6AddressSection.IPv6StringCache.wildcardCanonicalParams);
            } else {
                result = this.getSection().toCanonicalWildcardString();
            }
        }
        return result;
    }

    @Override
    public String toCompressedWildcardString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.compressedWildcardString) == null) {
            if (this.hasZone()) {
                this.stringCache.compressedWildcardString = result = this.toNormalizedString(IPv6AddressSection.IPv6StringCache.wildcardCompressedParams);
            } else {
                result = this.getSection().toCompressedWildcardString();
            }
        }
        return result;
    }

    @Override
    public String toSQLWildcardString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.sqlWildcardString) == null) {
            if (this.hasZone()) {
                this.stringCache.sqlWildcardString = result = this.toNormalizedString(IPv6AddressSection.IPv6StringCache.sqlWildcardParams);
            } else {
                result = this.getSection().toSQLWildcardString();
            }
        }
        return result;
    }

    @Override
    public String toHexString(boolean with0xPrefix) throws IncompatibleAddressException {
        String result;
        if (this.hasNoStringCache() || (result = with0xPrefix ? this.stringCache.hexStringPrefixed : this.stringCache.hexString) == null) {
            if (this.hasZone()) {
                result = this.getSection().toHexString(with0xPrefix, this.zone.getName());
                if (with0xPrefix) {
                    this.stringCache.hexStringPrefixed = result;
                } else {
                    this.stringCache.hexString = result;
                }
            } else {
                result = this.getSection().toHexString(with0xPrefix);
            }
        }
        return result;
    }

    private String getZoneString() {
        return this.hasZone() ? this.zone.getName() : null;
    }

    @Override
    public String toBinaryString() throws IncompatibleAddressException {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.binaryString) == null) {
            if (this.hasZone()) {
                this.stringCache.binaryString = result = this.getSection().toBinaryString(this.zone.getName());
            } else {
                result = this.getSection().toBinaryString();
            }
        }
        return result;
    }

    @Override
    public String toSegmentedBinaryString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.segmentedBinaryString) == null) {
            if (this.hasZone()) {
                this.stringCache.segmentedBinaryString = result = this.getSection().toSegmentedBinaryString(this.zone.getName());
            } else {
                result = this.getSection().toSegmentedBinaryString();
            }
        }
        return result;
    }

    @Override
    public String toOctalString(boolean with0Prefix) throws IncompatibleAddressException {
        String result;
        if (this.hasNoStringCache() || (result = with0Prefix ? this.stringCache.octalStringPrefixed : this.stringCache.octalString) == null) {
            if (this.hasZone()) {
                result = this.getSection().toOctalString(with0Prefix, this.zone.getName());
                if (with0Prefix) {
                    this.stringCache.octalStringPrefixed = result;
                } else {
                    this.stringCache.octalString = result;
                }
            } else {
                result = this.getSection().toOctalString(with0Prefix);
            }
        }
        return result;
    }

    @Override
    public String toPrefixLengthString() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.networkPrefixLengthString) == null) {
            if (this.hasZone()) {
                this.stringCache.networkPrefixLengthString = result = this.toNormalizedString(IPv6AddressSection.IPv6StringCache.networkPrefixLengthParams);
            } else {
                result = this.getSection().toPrefixLengthString();
            }
        }
        return result;
    }

    @Override
    public String toConvertedString() {
        if (this.isIPv4Convertible()) {
            return this.toMixedString();
        }
        return this.toNormalizedString();
    }

    @Override
    public String toNormalizedString(IPAddressSection.IPStringOptions params) {
        return this.getSection().toNormalizedString(params, (CharSequence)this.getZoneString());
    }

    public String toNormalizedString(IPv6AddressSection.IPv6StringOptions params) {
        return this.getSection().toNormalizedString(params, (CharSequence)this.getZoneString());
    }

    public String toNormalizedString(boolean keepMixed, IPv6AddressSection.IPv6StringOptions params) {
        if (keepMixed && this.fromString != null && this.getAddressfromString().isMixedIPv6() && !params.makeMixed()) {
            params = new IPv6AddressSection.IPv6StringOptions(params.base, params.expandSegments, params.wildcardOption, params.wildcards, params.segmentStrPrefix, true, params.ipv4Opts, params.compressOptions, params.separator, params.zoneSeparator, params.addrLabel, params.addrSuffix, params.reverse, params.splitDigits, params.uppercase);
        }
        return this.toNormalizedString(params);
    }

    @Override
    public String toUNCHostName() {
        String result;
        if (this.hasNoStringCache() || (result = this.stringCache.uncString) == null) {
            String newZone = this.hasZone() ? this.zone.getName().replace('%', 's').replace(':', '-') : null;
            this.stringCache.uncString = result = this.getSection().toNormalizedString(IPv6AddressSection.IPv6StringCache.uncParams, newZone);
        }
        return result;
    }

    @Override
    public IPAddressPartStringCollection toStandardStringCollection() {
        return this.toStringCollection(IPv6AddressSection.IPv6StringBuilderOptions.STANDARD_OPTS);
    }

    @Override
    public IPAddressPartStringCollection toAllStringCollection() {
        return this.toStringCollection(IPv6AddressSection.IPv6StringBuilderOptions.ALL_OPTS);
    }

    @Override
    public IPAddressPartStringCollection toStringCollection(IPAddressSection.IPStringBuilderOptions opts) {
        return this.toStringCollection(IPv6AddressSection.IPv6StringBuilderOptions.from(opts));
    }

    private IPv4Address getConverted(IPv6AddressSection.IPv6StringBuilderOptions opts) {
        if (!this.hasZone() && opts.includes(65536)) {
            IPv4Address.IPv4AddressConverter converter = opts.converter;
            return converter.toIPv4(this);
        }
        return null;
    }

    public IPAddressPartStringCollection toStringCollection(IPv6AddressSection.IPv6StringBuilderOptions opts) {
        IPv6AddressSection.IPv6StringCollection coll = this.getSection().toStringCollection(opts, this.getZoneString());
        IPv4Address ipv4Addr = this.getConverted(opts);
        if (ipv4Addr != null) {
            IPAddressPartStringCollection ipv4StringCollection = ipv4Addr.toStringCollection(opts.ipv4ConverterOptions);
            coll.addAll(ipv4StringCollection);
        }
        return coll;
    }

    public static interface IPv6AddressConverter {
        public IPv6Address toIPv6(IPAddress var1);
    }

    public static class IPv6Zone
    implements Serializable {
        private static final long serialVersionUID = 1L;
        String zoneStr;
        private int scopeId;
        private transient NetworkInterface networkInterface;
        private Boolean referencesInterface;

        public IPv6Zone(String zoneStr) {
            if (zoneStr == null) {
                throw new NullPointerException();
            }
            this.zoneStr = zoneStr.trim();
            this.scopeId = -1;
        }

        public IPv6Zone(int scopeId) {
            if (scopeId < 0) {
                throw new IllegalArgumentException();
            }
            this.scopeId = scopeId;
            this.referencesInterface = Boolean.FALSE;
        }

        public IPv6Zone(NetworkInterface networkInterface) {
            if (networkInterface == null) {
                throw new NullPointerException();
            }
            this.networkInterface = networkInterface;
            this.referencesInterface = Boolean.TRUE;
            this.scopeId = -1;
            this.zoneStr = networkInterface.getName();
        }

        public boolean referencesIntf() {
            if (this.referencesInterface == null) {
                this.scopeId = IPv6Zone.checkIfScope(this.zoneStr);
                this.referencesInterface = this.scopeId < 0;
            }
            return this.referencesInterface;
        }

        public boolean referencesScopeId() {
            return !this.referencesIntf();
        }

        public NetworkInterface getAssociatedIntf() {
            block6: {
                try {
                    if (this.referencesIntf()) {
                        if (this.networkInterface == null) {
                            this.networkInterface = NetworkInterface.getByName(this.zoneStr);
                        }
                        break block6;
                    }
                    if (this.networkInterface != null) break block6;
                    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                    while (interfaces.hasMoreElements()) {
                        NetworkInterface nif = interfaces.nextElement();
                        Enumeration<InetAddress> addrs = nif.getInetAddresses();
                        while (addrs.hasMoreElements()) {
                            Inet6Address inetAddr;
                            InetAddress addr = addrs.nextElement();
                            if (!(addr instanceof Inet6Address) || (inetAddr = (Inet6Address)addr).getScopeId() != this.scopeId) continue;
                            this.networkInterface = nif;
                            break block6;
                        }
                    }
                }
                catch (SocketException socketException) {
                    // empty catch block
                }
            }
            return this.networkInterface;
        }

        public MACAddress getAssociatedIntfMacAddr() {
            NetworkInterface intf = this.getAssociatedIntf();
            try {
                byte[] bytes;
                if (intf != null && (bytes = intf.getHardwareAddress()) != null) {
                    return new MACAddress(bytes);
                }
            }
            catch (SocketException socketException) {
                // empty catch block
            }
            return null;
        }

        public int getAssociatedScopeId() {
            NetworkInterface nif;
            if (this.referencesIntf() && this.scopeId == -1 && (nif = this.getAssociatedIntf()) != null) {
                Enumeration<InetAddress> addrs = nif.getInetAddresses();
                int newScopeId = -1;
                while (addrs.hasMoreElements()) {
                    Inet6Address inetAddr;
                    int sid;
                    InetAddress addr = addrs.nextElement();
                    if (!(addr instanceof Inet6Address) || (sid = (inetAddr = (Inet6Address)addr).getScopeId()) == 0) continue;
                    if (newScopeId != -1 && sid != newScopeId) {
                        newScopeId = -1;
                        break;
                    }
                    newScopeId = sid;
                }
                if (newScopeId != -1) {
                    this.scopeId = newScopeId;
                }
            }
            return this.scopeId;
        }

        public int hashCode() {
            return this.toString().hashCode();
        }

        public boolean equals(Object o) {
            return o instanceof IPv6Zone && this.toString().equals(o.toString());
        }

        public String getName() {
            if (this.zoneStr == null) {
                this.zoneStr = this.referencesIntf() ? this.networkInterface.getName() : IPv6AddressSegment.toUnsignedString(this.scopeId, 10, new StringBuilder(IPv6AddressSegment.toUnsignedStringLength(this.scopeId, 10))).toString();
            }
            return this.zoneStr;
        }

        public String toString() {
            return this.getName();
        }

        static int checkIfScope(String zoneStr) {
            long digits = 0L;
            int len = zoneStr.length();
            for (int i = 0; i < len; ++i) {
                char c = zoneStr.charAt(i);
                int digit = Character.digit(c, 10);
                if (digit < 0) {
                    return -1;
                }
                if ((digits = digits * 10L + (long)digit) <= Integer.MAX_VALUE) continue;
                return -1;
            }
            return (int)digits;
        }
    }
}

