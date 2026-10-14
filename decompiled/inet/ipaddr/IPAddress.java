/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.Address;
import inet.ipaddr.AddressComparator;
import inet.ipaddr.AddressConversionException;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressSection;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.HostName;
import inet.ipaddr.IPAddressConverter;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.IPAddressSegmentSeries;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.IPAddressStringParameters;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.IPAddressRange;
import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.format.util.IPAddressPartStringCollection;
import inet.ipaddr.format.util.sql.IPAddressSQLTranslator;
import inet.ipaddr.format.validate.IPAddressProvider;
import inet.ipaddr.format.validate.ParsedHost;
import inet.ipaddr.ipv4.IPv4Address;
import inet.ipaddr.ipv6.IPv6Address;
import java.lang.invoke.LambdaMetafactory;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public abstract class IPAddress
extends Address
implements IPAddressSegmentSeries,
IPAddressRange {
    private static final long serialVersionUID = 4L;
    public static final char PREFIX_LEN_SEPARATOR = '/';
    public static final String BINARY_STR_PREFIX = "0b";
    public static final IPAddressConverter DEFAULT_ADDRESS_CONVERTER = new IPAddressConverter.DefaultAddressConverter();
    HostName fromHost;
    private HostName canonicalHost;

    protected IPAddress(IPAddressSection section) {
        super(section);
    }

    protected IPAddress(Function<Address, AddressSection> supplier) {
        super(supplier);
    }

    @Override
    public IPAddressString toAddressString() {
        if (this.fromString == null) {
            IPAddressStringParameters params = this.createFromStringParams();
            this.fromString = new IPAddressString(this.toCanonicalString(), this, params);
        }
        return this.getAddressfromString();
    }

    protected abstract IPAddressStringParameters createFromStringParams();

    protected IPAddressString getAddressfromString() {
        return (IPAddressString)this.fromString;
    }

    public HostName toHostName() {
        HostName host2 = this.fromHost;
        if (host2 == null) {
            this.fromHost = host2 = this.toCanonicalHostName();
        }
        return host2;
    }

    void cache(HostIdentifierString string) {
        if (string instanceof HostName) {
            this.fromHost = (HostName)string;
            this.fromString = new IPAddressString(this.fromHost.toString(), this, this.fromHost.validationOptions.addressOptions);
        } else if (string instanceof IPAddressString) {
            this.fromString = (IPAddressString)string;
        }
    }

    protected IPAddressProvider getProvider() {
        if (this.isPrefixed()) {
            if (this.getNetwork().getPrefixConfiguration().prefixedSubnetsAreExplicit() || !this.isPrefixBlock()) {
                return IPAddressProvider.getProviderFor(this, this.withoutPrefixLength());
            }
            return IPAddressProvider.getProviderFor(this, this.toZeroHost(true).withoutPrefixLength());
        }
        return IPAddressProvider.getProviderFor(this, this);
    }

    public HostName toCanonicalHostName() {
        HostName host2 = this.canonicalHost;
        if (host2 == null) {
            if (this.isMultiple()) {
                throw new IncompatibleAddressException(this, "ipaddress.error.unavailable.numeric");
            }
            InetAddress inetAddress = this.toInetAddress();
            String hostStr = inetAddress.getCanonicalHostName();
            if (hostStr.equals(inetAddress.getHostAddress())) {
                host2 = new HostName(hostStr, new ParsedHost(hostStr, this.getProvider()));
                host2.resolvedAddresses = new IPAddress[]{this};
            } else {
                host2 = new HostName(hostStr);
            }
        }
        return host2;
    }

    @Override
    public abstract IPAddressNetwork<?, ?, ?, ?, ?> getNetwork();

    @Override
    public IPAddressSection getSection() {
        return (IPAddressSection)super.getSection();
    }

    @Override
    public IPAddressSection getSection(int index) {
        return this.getSection().getSection(index);
    }

    @Override
    public IPAddressSection getSection(int index, int endIndex) {
        return this.getSection().getSection(index, endIndex);
    }

    public IPAddressStringDivisionSeries[] getParts(IPAddressSection.IPStringBuilderOptions options) {
        return new IPAddressStringDivisionSeries[]{this.getSection()};
    }

    @Override
    public int getMaxSegmentValue() {
        return IPAddressSegment.getMaxSegmentValue(this.getIPVersion());
    }

    static int getMaxSegmentValue(IPVersion version) {
        return IPAddressSegment.getMaxSegmentValue(version);
    }

    @Override
    public BigInteger getNonZeroHostCount() {
        return this.getSection().getNonZeroHostCount();
    }

    @Override
    public int getBytesPerSegment() {
        return IPAddressSegment.getByteCount(this.getIPVersion());
    }

    static int getBytesPerSegment(IPVersion version) {
        return IPAddressSegment.getByteCount(version);
    }

    @Override
    public int getBitsPerSegment() {
        return IPAddressSegment.getBitCount(this.getIPVersion());
    }

    static int getBitsPerSegment(IPVersion version) {
        return IPAddressSegment.getBitCount(version);
    }

    @Override
    public int getByteCount() {
        return this.getSection().getByteCount();
    }

    public static int getByteCount(IPVersion version) {
        return version.isIPv4() ? 4 : 16;
    }

    public static int getSegmentCount(IPVersion version) {
        return version.isIPv4() ? 4 : 8;
    }

    public static int getBitCount(IPVersion version) {
        return version.isIPv4() ? 32 : 128;
    }

    protected abstract IPAddress convertArg(IPAddress var1) throws AddressConversionException;

    public <V> V applyToBounds(BiFunction<? super IPAddress, ? super IPAddress, V> func, IPAddress ... series) {
        AddressComparator lowComparator = Address.ADDRESS_LOW_VALUE_COMPARATOR;
        AddressComparator highComparator = Address.ADDRESS_HIGH_VALUE_COMPARATOR;
        IPAddress lowest = this;
        IPAddress highest = this;
        for (int i = 0; i < series.length; ++i) {
            IPAddress next = series[i];
            if (next == null) continue;
            if (lowComparator.compare(next = this.convertArg(next), lowest) < 0) {
                lowest = next;
            }
            if (highComparator.compare(next, highest) <= 0) continue;
            highest = next;
        }
        return func.apply(lowest.getLower(), highest.getUpper());
    }

    @Override
    public abstract IPAddress getLowerNonZeroHost();

    @Override
    public abstract IPAddress getLower();

    @Override
    public abstract IPAddress getUpper();

    @Override
    public abstract IPAddress reverseBits(boolean var1);

    @Override
    public abstract IPAddress reverseBytes();

    @Override
    public abstract IPAddress reverseBytesPerSegment();

    @Override
    public abstract IPAddress reverseSegments();

    @Override
    public abstract Iterator<? extends IPAddress> iterator();

    @Override
    public abstract AddressComponentSpliterator<? extends IPAddress> spliterator();

    public abstract Stream<? extends IPAddress> stream();

    public abstract Iterator<? extends IPAddress> nonZeroHostIterator();

    public abstract Iterator<? extends IPAddress> prefixIterator();

    public abstract AddressComponentSpliterator<? extends IPAddress> prefixSpliterator();

    public abstract Stream<? extends IPAddress> prefixStream();

    public abstract Iterator<? extends IPAddress> prefixBlockIterator();

    public abstract AddressComponentSpliterator<? extends IPAddress> prefixBlockSpliterator();

    public abstract Stream<? extends IPAddress> prefixBlockStream();

    public abstract Iterator<? extends IPAddress> blockIterator(int var1);

    public abstract AddressComponentSpliterator<? extends IPAddress> blockSpliterator(int var1);

    public abstract Stream<? extends IPAddress> blockStream(int var1);

    public Iterator<? extends IPAddress> sequentialBlockIterator() {
        return this.blockIterator(this.getSequentialBlockIndex());
    }

    public AddressComponentSpliterator<? extends IPAddress> sequentialBlockSpliterator() {
        return this.blockSpliterator(this.getSequentialBlockIndex());
    }

    public Stream<? extends IPAddress> sequentialBlockStream() {
        return this.blockStream(this.getSequentialBlockIndex());
    }

    @Override
    public BigInteger getSequentialBlockCount() {
        return this.getSection().getSequentialBlockCount();
    }

    @Override
    public abstract Iterable<? extends IPAddress> getIterable();

    @Override
    public abstract IPAddress increment(long var1) throws AddressValueException;

    @Override
    public abstract IPAddress incrementBoundary(long var1) throws AddressValueException;

    @Override
    public boolean isIPAddress() {
        return true;
    }

    @Override
    public IPAddress toIPAddress() {
        return this;
    }

    public boolean isIPv4() {
        return false;
    }

    public boolean isIPv6() {
        return false;
    }

    @Override
    public IPVersion getIPVersion() {
        return this.getSection().getIPVersion();
    }

    public IPv4Address toIPv4() {
        return null;
    }

    public IPv6Address toIPv6() {
        return null;
    }

    public abstract boolean isIPv4Convertible();

    public abstract boolean isIPv6Convertible();

    public abstract boolean isLinkLocal();

    @Override
    public abstract boolean isLocal();

    public boolean isUnspecified() {
        return this.isZero();
    }

    public boolean isAnyLocal() {
        return this.isZero();
    }

    public abstract boolean isLoopback();

    public InetAddress toUpperInetAddress() {
        return this.getUpper().toInetAddress();
    }

    public InetAddress toInetAddress() {
        return this.getSection().toInetAddress(this);
    }

    protected InetAddress toInetAddressImpl() {
        try {
            return InetAddress.getByAddress(this.getSection().getBytesInternal());
        }
        catch (UnknownHostException e) {
            return null;
        }
    }

    @Override
    public abstract IPAddressSeqRange toSequentialRange();

    @Deprecated
    public abstract IPAddressSeqRange toSequentialRange(IPAddress var1) throws AddressConversionException;

    public boolean matches(IPAddressString otherString) {
        if (this.isFromSameString(otherString)) {
            return true;
        }
        IPAddress otherAddr = otherString.getAddress();
        return otherAddr != null && this.isSameAddress(otherAddr);
    }

    @Override
    protected boolean isFromSameString(HostIdentifierString other) {
        if (this.fromString != null && other instanceof IPAddressString) {
            IPAddressString fromString = (IPAddressString)this.fromString;
            IPAddressString otherString = (IPAddressString)other;
            return fromString == otherString || fromString.fullAddr.equals(otherString.fullAddr) && fromString.validationOptions == otherString.validationOptions;
        }
        return false;
    }

    @Override
    public boolean contains(IPAddress other) {
        return super.contains(other);
    }

    public boolean containsNonZeroHosts(IPAddress other) {
        if (other == this) {
            return true;
        }
        return this.getSection().containsNonZeroHosts(other.getSection());
    }

    public boolean prefixContains(IPAddress other) {
        if (other == this) {
            return true;
        }
        return this.getSection().prefixContains(other.getSection());
    }

    public boolean isZeroHost() {
        return this.getSection().isZeroHost();
    }

    public boolean isZeroHost(int networkPrefixLength) {
        return this.getSection().isZeroHost(networkPrefixLength);
    }

    @Override
    public boolean contains(IPAddressSeqRange otherRange) {
        if (IPAddress.compareLowValues(otherRange.getLower(), this.getLower()) >= 0 && IPAddress.compareLowValues(otherRange.getUpper(), this.getUpper()) <= 0) {
            if (this.isSequential()) {
                return true;
            }
            Iterator<? extends IPAddress> iterator2 = this.sequentialBlockIterator();
            while (iterator2.hasNext()) {
                IPAddress sequential = iterator2.next();
                if (!sequential.contains(otherRange)) continue;
                return true;
            }
        }
        return false;
    }

    static int compareLowValues(IPAddress one, IPAddress two) {
        return Address.ADDRESS_LOW_VALUE_COMPARATOR.compare(one, two);
    }

    public boolean matchesWithMask(IPAddress other, IPAddress mask) {
        return this.getSection().matchesWithMask(other.getSection(), mask.getSection());
    }

    public static void toNormalizedString(IPAddressValueProvider provider, StringBuilder builder) {
        IPVersion version = provider.getIPVersion();
        if (version.isIPv4()) {
            IPAddress.toNormalizedString(IPAddress.defaultIpv4Network().getPrefixConfiguration(), provider.getValues(), provider.getUpperValues(), provider.getPrefixLength(), 4, 1, 8, 255, '.', 10, null, builder);
        } else if (version.isIPv6()) {
            IPAddress.toNormalizedString(IPAddress.defaultIpv6Network().getPrefixConfiguration(), provider.getValues(), provider.getUpperValues(), provider.getPrefixLength(), 8, 2, 16, 65535, ':', 16, provider.getZone(), builder);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static String toNormalizedString(IPAddressValueProvider provider) {
        IPVersion version = provider.getIPVersion();
        if (version.isIPv4()) {
            return IPv4Address.toNormalizedString(IPAddress.defaultIpv4Network(), provider.getValues(), provider.getUpperValues(), provider.getPrefixLength());
        }
        if (version.isIPv6()) {
            return IPv6Address.toNormalizedString(IPAddress.defaultIpv6Network(), provider.getValues(), provider.getUpperValues(), provider.getPrefixLength(), provider.getZone());
        }
        throw new IllegalArgumentException();
    }

    protected static String toNormalizedString(AddressNetwork.PrefixConfiguration prefixConfiguration, Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength, int segmentCount, int bytesPerSegment, int bitsPerSegment, int segmentMaxValue, char separator, int radix, CharSequence zone) {
        int length = IPAddress.toNormalizedString(prefixConfiguration, lowerValueProvider, upperValueProvider, prefixLength, segmentCount, bytesPerSegment, bitsPerSegment, segmentMaxValue, separator, radix, zone, null);
        StringBuilder builder = new StringBuilder(length);
        IPAddress.toNormalizedString(prefixConfiguration, lowerValueProvider, upperValueProvider, prefixLength, segmentCount, bytesPerSegment, bitsPerSegment, segmentMaxValue, separator, radix, zone, builder);
        IPAddressSection.checkLengths(length, builder);
        return builder.toString();
    }

    protected static int toNormalizedString(AddressNetwork.PrefixConfiguration prefixConfiguration, Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength, int segmentCount, int bytesPerSegment, int bitsPerSegment, int segmentMaxValue, char separator, int radix, CharSequence zone, StringBuilder builder) {
        boolean adjustByPrefixLength;
        int count = 0;
        int segmentIndex = 0;
        if (prefixLength != null && prefixConfiguration.allPrefixedAddressesAreSubnets()) {
            if (prefixLength <= 0) {
                adjustByPrefixLength = true;
            } else {
                int totalBitCount = bitsPerSegment == 8 ? segmentCount << 3 : (bitsPerSegment == 16 ? segmentCount << 4 : segmentCount * bitsPerSegment);
                adjustByPrefixLength = prefixLength < totalBitCount;
            }
        } else {
            adjustByPrefixLength = false;
        }
        while (true) {
            Integer segmentPrefixLength = IPAddressSection.getSegmentPrefixLength(bitsPerSegment, prefixLength, segmentIndex);
            if (adjustByPrefixLength && segmentPrefixLength != null && segmentPrefixLength == 0) {
                if (builder == null) {
                    ++count;
                } else {
                    builder.append('0');
                }
            } else {
                int value = 0;
                int value2 = 0;
                if (lowerValueProvider == null) {
                    value = upperValueProvider.getValue(segmentIndex);
                } else {
                    value = lowerValueProvider.getValue(segmentIndex);
                    if (upperValueProvider != null) {
                        value2 = upperValueProvider.getValue(segmentIndex);
                    }
                }
                if (lowerValueProvider == null || upperValueProvider == null) {
                    if (adjustByPrefixLength && segmentPrefixLength != null) {
                        value &= -1 << bitsPerSegment - segmentPrefixLength;
                    }
                    if (builder == null) {
                        count += IPAddressSegment.toUnsignedStringLength(value, radix);
                    } else {
                        IPAddressSegment.toUnsignedString(value, radix, builder);
                    }
                } else {
                    if (adjustByPrefixLength && segmentPrefixLength != null) {
                        int mask = -1 << bitsPerSegment - segmentPrefixLength;
                        value &= mask;
                        value2 &= mask;
                    }
                    if (value == value2) {
                        if (builder == null) {
                            count += IPAddressSegment.toUnsignedStringLength(value, radix);
                        } else {
                            IPAddressSegment.toUnsignedString(value, radix, builder);
                        }
                    } else {
                        if (value > value2) {
                            int tmp = value2;
                            value2 = value;
                            value = tmp;
                        }
                        if (value == 0 && value2 == segmentMaxValue) {
                            if (builder == null) {
                                count += SEGMENT_WILDCARD_STR.length();
                            } else {
                                builder.append(SEGMENT_WILDCARD_STR);
                            }
                        } else if (builder == null) {
                            count += IPAddressSegment.toUnsignedStringLength(value, radix) + IPAddressSegment.toUnsignedStringLength(value2, radix) + RANGE_SEPARATOR_STR.length();
                        } else {
                            IPAddressSegment.toUnsignedString(value2, radix, IPAddressSegment.toUnsignedString(value, radix, builder).append(RANGE_SEPARATOR_STR));
                        }
                    }
                }
            }
            if (++segmentIndex >= segmentCount) break;
            if (builder == null) continue;
            builder.append(separator);
        }
        if (builder == null) {
            count += segmentCount;
            --count;
        }
        if (zone != null && zone.length() > 0) {
            if (builder == null) {
                count += zone.length() + 1;
            } else {
                builder.append('%').append(zone);
            }
        }
        if (prefixLength != null) {
            if (builder == null) {
                count += IPAddressSegment.toUnsignedStringLength(prefixLength, 10) + 1;
            } else {
                builder.append('/').append(prefixLength);
            }
        }
        return count;
    }

    @Override
    public String toFullString() {
        return this.getSection().toFullString();
    }

    protected void cacheNormalizedString(String str) {
        this.getSection().cacheNormalizedString(str);
    }

    @Override
    public String toSubnetString() {
        return this.getSection().toSubnetString();
    }

    @Override
    public String toNormalizedWildcardString() {
        return this.getSection().toNormalizedWildcardString();
    }

    @Override
    public String toCanonicalWildcardString() {
        return this.getSection().toCanonicalWildcardString();
    }

    @Override
    public String toCompressedWildcardString() {
        return this.getSection().toCompressedWildcardString();
    }

    @Override
    public String toSQLWildcardString() {
        return this.getSection().toSQLWildcardString();
    }

    @Override
    public String toPrefixLengthString() {
        return this.getSection().toPrefixLengthString();
    }

    public String toConvertedString() {
        return this.toNormalizedString();
    }

    public abstract String toUNCHostName();

    @Override
    public String toReverseDNSLookupString() {
        return this.getSection().toReverseDNSLookupString();
    }

    @Override
    public String toBinaryString() throws IncompatibleAddressException {
        return this.getSection().toBinaryString();
    }

    @Override
    public String toOctalString(boolean with0Prefix) throws IncompatibleAddressException {
        return this.getSection().toOctalString(with0Prefix);
    }

    @Override
    public String toNormalizedString(IPAddressSection.IPStringOptions params) {
        return this.getSection().toNormalizedString(params);
    }

    public String[] toStandardStrings() {
        return this.toStandardStringCollection().toStrings();
    }

    public String[] toAllStrings() {
        return this.toAllStringCollection().toStrings();
    }

    public String[] toStrings(IPAddressSection.IPStringBuilderOptions options) {
        return this.toStringCollection(options).toStrings();
    }

    public IPAddressPartStringCollection toStandardStringCollection() {
        return this.getSection().toStandardStringCollection();
    }

    public IPAddressPartStringCollection toAllStringCollection() {
        return this.getSection().toAllStringCollection();
    }

    @Override
    public IPAddressPartStringCollection toStringCollection(IPAddressSection.IPStringBuilderOptions options) {
        return this.getSection().toStringCollection(options);
    }

    public static String toDelimitedSQLStrs(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String str : strs) {
            builder.append('\'').append(str).append('\'').append(',');
        }
        return builder.substring(0, builder.length() - 1);
    }

    @Override
    public Integer getNetworkPrefixLength() {
        return this.getSection().getNetworkPrefixLength();
    }

    @Override
    public IPAddress getHostMask() {
        Integer prefLength = this.getNetworkPrefixLength();
        return ((IPAddressNetwork)this.getNetwork()).getHostMask(prefLength == null ? 0 : prefLength);
    }

    @Override
    public IPAddress getNetworkMask() {
        Integer prefLength = this.getNetworkPrefixLength();
        return ((IPAddressNetwork)this.getNetwork()).getNetworkMask(prefLength == null ? this.getBitCount() : prefLength.intValue());
    }

    @Override
    public boolean includesZeroHost() {
        return this.getSection().includesZeroHost();
    }

    @Override
    public boolean includesZeroHost(int networkPrefixLength) {
        return this.getSection().includesZeroHost(networkPrefixLength);
    }

    @Override
    public abstract IPAddress toZeroHost(int var1);

    @Override
    public abstract IPAddress toZeroHost();

    protected abstract IPAddress toZeroHost(boolean var1);

    @Override
    public abstract IPAddress toZeroNetwork();

    @Override
    public abstract IPAddress toMaxHost(int var1);

    @Override
    public abstract IPAddress toMaxHost();

    @Override
    public boolean includesMaxHost() {
        return this.getSection().includesMaxHost();
    }

    @Override
    public boolean includesMaxHost(int networkPrefixLength) {
        return this.getSection().includesMaxHost(networkPrefixLength);
    }

    public boolean isSingleNetwork() {
        return this.getSection().isSingleNetwork();
    }

    protected static <T extends IPAddress> T[] getSpanningPrefixBlocks(T first, T other, UnaryOperator<T> getLower, UnaryOperator<T> getUpper, Comparator<T> comparator, UnaryOperator<T> prefixAdder, UnaryOperator<T> prefixRemover, IntFunction<T[]> arrayProducer) {
        T result = IPAddress.checkPrefixBlockContainment(first, other, prefixAdder);
        if (result != null) {
            IPAddress[] resultArray = (IPAddress[])arrayProducer.apply(1);
            resultArray[0] = result;
            return resultArray;
        }
        List blocks = IPAddressSection.applyOperatorToLowerUpper(first, other, getLower, getUpper, comparator, prefixRemover, (orig, one, two) -> IPAddressSection.splitIntoPrefixBlocks(one, two));
        return blocks.toArray((IPAddress[])arrayProducer.apply(blocks.size()));
    }

    private static <T extends IPAddress> T checkPrefixBlockContainment(T first, T other, UnaryOperator<T> prefixAdder) {
        if (first.contains(other)) {
            return IPAddress.checkPrefixBlockFormat(first, other, true, prefixAdder);
        }
        if (other.contains(first)) {
            return IPAddress.checkPrefixBlockFormat(other, first, false, prefixAdder);
        }
        return null;
    }

    static <T extends IPAddressSegmentSeries> T checkPrefixBlockFormat(T container, T contained, boolean checkEqual, UnaryOperator<T> prefixAdder) {
        Object result = null;
        result = container.isPrefixed() && container.isSinglePrefixBlock() ? (Object)container : (checkEqual && contained.isPrefixed() && container.isMore(contained) == 0 && contained.isSinglePrefixBlock() ? contained : (IPAddressSegmentSeries)prefixAdder.apply(container));
        return (T)result;
    }

    protected static <T extends IPAddress, S extends IPAddressSegment> T[] getSpanningSequentialBlocks(T first, T other, UnaryOperator<T> getLower, UnaryOperator<T> getUpper, Comparator<T> comparator, UnaryOperator<T> prefixRemover, IPAddressNetwork.IPAddressCreator<T, ?, ?, S, ?> creator) {
        IPAddress[] result = IPAddress.checkSequentialBlockContainment(first, other, prefixRemover, (IntFunction<IPAddress[]>)LambdaMetafactory.metafactory(null, null, null, (I)Ljava/lang/Object;, createAddressArray(int ), (I)[Linet/ipaddr/IPAddress;)(creator));
        if (result != null) {
            return result;
        }
        IPAddressSection.SeriesCreator seriesCreator = creator::createSequentialBlockAddress;
        IPAddressSection.TriFunction<IPAddress, List> operatorFunctor = (orig, one, two) -> IPAddressSection.splitIntoSequentialBlocks(one, two, seriesCreator);
        List blocks = IPAddressSection.applyOperatorToLowerUpper(first, other, getLower, getUpper, comparator, prefixRemover, operatorFunctor);
        return blocks.toArray(creator.createAddressArray(blocks.size()));
    }

    private static <T extends IPAddress> T[] checkSequentialBlockContainment(T first, T other, UnaryOperator<T> prefixRemover, IntFunction<T[]> arrayProducer) {
        if (first.contains(other)) {
            return (IPAddress[])IPAddress.checkSequentialBlockFormat(first, other, (boolean)true, prefixRemover, arrayProducer);
        }
        if (other.contains(first)) {
            return (IPAddress[])IPAddress.checkSequentialBlockFormat(other, first, (boolean)false, prefixRemover, arrayProducer);
        }
        return null;
    }

    static <T extends IPAddressSegmentSeries> T[] checkSequentialBlockFormat(T container, T contained, boolean checkEqual, UnaryOperator<T> prefixRemover, IntFunction<T[]> arrayProducer) {
        IPAddressSegmentSeries result = null;
        if (!container.isPrefixed()) {
            if (container.isSequential()) {
                result = container;
            }
        } else if (checkEqual && !contained.isPrefixed() && container.equals(contained)) {
            if (contained.isSequential()) {
                result = contained;
            }
        } else if (container.isSequential()) {
            result = (IPAddressSegmentSeries)prefixRemover.apply(container);
        }
        if (result != null) {
            IPAddressSegmentSeries[] resultArray = (IPAddressSegmentSeries[])arrayProducer.apply(1);
            resultArray[0] = result;
            return resultArray;
        }
        return null;
    }

    @Override
    public abstract IPAddress toPrefixBlock();

    @Override
    public abstract IPAddress toPrefixBlock(int var1) throws PrefixLenException;

    @Override
    public IPAddress assignPrefixForSingleBlock() {
        Integer newPrefix = this.getPrefixLengthForSingleBlock();
        return newPrefix == null ? null : this.setPrefixLength(newPrefix, false);
    }

    @Override
    public IPAddress assignMinPrefixForBlock() {
        return this.setPrefixLength(this.getMinPrefixLengthForBlock(), false);
    }

    public Integer getBlockMaskPrefixLength(boolean network) {
        return this.getSection().getBlockMaskPrefixLength(network);
    }

    public int getTrailingBitCount(boolean network) {
        return this.getSection().getTrailingBitCount(network);
    }

    public int getLeadingBitCount(boolean network) {
        return this.getSection().getLeadingBitCount(network);
    }

    public abstract IPAddress coverWithPrefixBlock(IPAddress var1) throws AddressConversionException;

    public abstract IPAddress[] spanWithPrefixBlocks(IPAddress var1) throws AddressConversionException;

    public abstract IPAddress[] spanWithSequentialBlocks(IPAddress var1) throws AddressConversionException;

    protected List<? extends IPAddressSegmentSeries> spanWithBlocks(boolean prefixBlocks) {
        return IPAddress.spanWithBlocks(this, prefixBlocks);
    }

    static List<? extends IPAddressSegmentSeries> spanWithBlocks(IPAddressSegmentSeries orig, boolean prefixBlocks) {
        ArrayList list = new ArrayList();
        Iterator<? extends IPAddressSegmentSeries> iterator2 = orig.sequentialBlockIterator();
        while (iterator2.hasNext()) {
            IPAddressSegmentSeries sequential = iterator2.next();
            if (prefixBlocks) {
                Collections.addAll(list, sequential.spanWithPrefixBlocks());
                continue;
            }
            Collections.addAll(list, sequential);
        }
        return list;
    }

    public abstract IPAddressSeqRange spanWithRange(IPAddress var1) throws AddressConversionException;

    public abstract IPAddress[] mergeToPrefixBlocks(IPAddress ... var1) throws AddressConversionException;

    protected static <T extends IPAddress, S extends IPAddressSegment> List<IPAddressSegmentSeries> getMergedPrefixBlocks(IPAddressSegmentSeries[] sections) {
        return IPAddressSection.getMergedPrefixBlocks(sections);
    }

    public abstract IPAddress[] mergeToSequentialBlocks(IPAddress ... var1) throws AddressConversionException;

    protected static <T extends IPAddress, S extends IPAddressSegment> List<IPAddressSegmentSeries> getMergedSequentialBlocks(IPAddressSegmentSeries[] sections, IPAddressNetwork.IPAddressCreator<T, ?, ?, S, ?> creator) {
        return IPAddressSection.getMergedSequentialBlocks(sections, creator::createSequentialBlockAddress);
    }

    public abstract IPAddress intersect(IPAddress var1) throws AddressConversionException;

    public abstract IPAddress[] subtract(IPAddress var1) throws AddressConversionException;

    public abstract IPAddress mask(IPAddress var1) throws AddressConversionException, IncompatibleAddressException;

    public abstract IPAddress mask(IPAddress var1, boolean var2) throws AddressConversionException, IncompatibleAddressException;

    public abstract IPAddress maskNetwork(IPAddress var1, int var2) throws AddressConversionException, IncompatibleAddressException;

    public abstract IPAddress bitwiseOr(IPAddress var1) throws AddressConversionException, IncompatibleAddressException;

    public abstract IPAddress bitwiseOr(IPAddress var1, boolean var2) throws AddressConversionException, IncompatibleAddressException;

    public abstract IPAddress bitwiseOrNetwork(IPAddress var1, int var2) throws AddressConversionException, IncompatibleAddressException;

    @Override
    @Deprecated
    public abstract IPAddress removePrefixLength();

    @Override
    @Deprecated
    public abstract IPAddress removePrefixLength(boolean var1);

    @Override
    public abstract IPAddress withoutPrefixLength();

    @Override
    public abstract IPAddress adjustPrefixBySegment(boolean var1);

    @Override
    public abstract IPAddress adjustPrefixBySegment(boolean var1, boolean var2);

    @Override
    public abstract IPAddress adjustPrefixLength(int var1);

    @Override
    public abstract IPAddress adjustPrefixLength(int var1, boolean var2);

    @Override
    public abstract IPAddress setPrefixLength(int var1);

    @Override
    public abstract IPAddress setPrefixLength(int var1, boolean var2);

    public abstract IPAddress setPrefixLength(int var1, boolean var2, boolean var3);

    @Override
    @Deprecated
    public abstract IPAddress applyPrefixLength(int var1);

    public void getMatchesSQLClause(StringBuilder builder, String sqlExpression) {
        this.getSection().getStartsWithSQLClause(builder, sqlExpression);
    }

    public void getMatchesSQLClause(StringBuilder builder, String sqlExpression, IPAddressSQLTranslator translator) {
        this.getSection().getStartsWithSQLClause(builder, sqlExpression, translator);
    }

    public IPAddress removeBitCountPrefixLength() {
        if (this.isPrefixed() && this.getNetworkPrefixLength().intValue() == this.getBitCount()) {
            return this.withoutPrefixLength();
        }
        return this;
    }

    public static interface IPAddressValueProvider
    extends Address.AddressValueProvider {
        public IPVersion getIPVersion();

        default public Integer getPrefixLength() {
            return null;
        }

        default public String getZone() {
            return null;
        }
    }

    public static enum IPVersion {
        IPV4,
        IPV6;


        public boolean isIPv4() {
            return this == IPV4;
        }

        public boolean isIPv6() {
            return this == IPV6;
        }

        public String toString() {
            return this.isIPv4() ? "IPv4" : "IPv6";
        }
    }
}

