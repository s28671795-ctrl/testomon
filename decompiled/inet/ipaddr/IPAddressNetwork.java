/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.Address;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.HostName;
import inet.ipaddr.HostNameParameters;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.IPAddressSegmentSeries;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.IPAddressStringParameters;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.standard.AddressCreator;
import inet.ipaddr.format.standard.IPAddressDivisionGrouping;
import inet.ipaddr.format.validate.HostIdentifierStringValidator;
import inet.ipaddr.ipv4.IPv4Address;
import inet.ipaddr.ipv4.IPv4AddressNetwork;
import inet.ipaddr.ipv6.IPv6Address;
import inet.ipaddr.ipv6.IPv6AddressNetwork;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class IPAddressNetwork<T extends IPAddress, R extends IPAddressSection, E extends IPAddressSection, S extends IPAddressSegment, J extends InetAddress>
extends AddressNetwork<S> {
    private static final long serialVersionUID = 4L;
    private final T[] subnetsMasksWithPrefix;
    private final T[] subnetMasks;
    private final T[] networkAddresses;
    private final T[] hostMasks;
    private final int[] networkSegmentMasks;
    private final int[] hostSegmentMasks;
    private transient T loopback;
    private transient String[] loopbackStrings;
    private IPAddressCreator<T, R, E, S, J> creator;

    protected IPAddressNetwork(Class<T> addressType) {
        IPAddress.IPVersion version = this.getIPVersion();
        int bitSize = IPAddress.getBitCount(version);
        this.subnetsMasksWithPrefix = (IPAddress[])Array.newInstance(addressType, bitSize + 1);
        this.subnetMasks = (IPAddress[])this.subnetsMasksWithPrefix.clone();
        this.networkAddresses = (IPAddress[])this.subnetsMasksWithPrefix.clone();
        this.hostMasks = (IPAddress[])this.subnetsMasksWithPrefix.clone();
        this.creator = this.createAddressCreator();
        int segmentBitSize = IPAddressSegment.getBitCount(version);
        int fullMask = ~(-1 << segmentBitSize);
        this.networkSegmentMasks = new int[segmentBitSize + 1];
        this.hostSegmentMasks = (int[])this.networkSegmentMasks.clone();
        for (int i = 0; i <= segmentBitSize; ++i) {
            int networkMask = this.networkSegmentMasks[i] = fullMask & fullMask << segmentBitSize - i;
            this.hostSegmentMasks[i] = ~networkMask & fullMask;
        }
    }

    @Override
    public void clearCaches() {
        Arrays.fill(this.subnetsMasksWithPrefix, null);
        Arrays.fill(this.subnetMasks, null);
        Arrays.fill(this.networkAddresses, null);
        Arrays.fill(this.hostMasks, null);
        this.loopback = null;
        this.loopbackStrings = null;
        super.clearCaches();
    }

    public boolean isIPv4() {
        return false;
    }

    public boolean isIPv6() {
        return false;
    }

    public abstract IPAddress.IPVersion getIPVersion();

    protected abstract BiFunction<T, Integer, S> getSegmentProducer();

    protected abstract Function<T, R> getSectionProducer();

    protected abstract IPAddressCreator<T, R, E, S, J> createAddressCreator();

    public IPAddressCreator<T, R, E, S, J> getAddressCreator() {
        return this.creator;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public T getLoopback() {
        if (this.loopback == null) {
            IPAddressNetwork iPAddressNetwork = this;
            synchronized (iPAddressNetwork) {
                if (this.loopback == null) {
                    this.loopback = this.createLoopback();
                }
            }
        }
        return this.loopback;
    }

    protected abstract T createLoopback();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String[] getStandardLoopbackStrings() {
        if (this.loopbackStrings == null) {
            IPAddressNetwork iPAddressNetwork = this;
            synchronized (iPAddressNetwork) {
                if (this.loopbackStrings == null) {
                    this.loopbackStrings = ((IPAddress)this.getLoopback()).toStandardStrings();
                }
            }
        }
        return this.loopbackStrings;
    }

    public int getSegmentNetworkMask(int segmentPrefixLength) {
        return this.networkSegmentMasks[segmentPrefixLength];
    }

    public int getSegmentHostMask(int segmentPrefixLength) {
        return this.hostSegmentMasks[segmentPrefixLength];
    }

    public T getNetworkMask(int networkPrefixLength) {
        return this.getNetworkMask(networkPrefixLength, true);
    }

    public T getNetworkAddress(int networkPrefixLength) {
        return (T)this.getMask(networkPrefixLength, (IPAddress[])this.networkAddresses, true, true, true);
    }

    public T getNetworkMask(int networkPrefixLength, boolean withPrefixLength) {
        return (T)this.getMask(networkPrefixLength, (IPAddress[])(withPrefixLength ? this.subnetsMasksWithPrefix : this.subnetMasks), true, withPrefixLength, false);
    }

    public R getNetworkMaskSection(int networkPrefixLength) {
        return (R)((IPAddressSection)this.getSectionProducer().apply(this.getNetworkMask(networkPrefixLength, true)));
    }

    public T getHostMask(int networkPrefixLength) {
        return (T)this.getMask(networkPrefixLength, (IPAddress[])this.hostMasks, false, false, false);
    }

    public R getHostMaskSection(int networkPrefixLength) {
        return (R)((IPAddressSection)this.getSectionProducer().apply(this.getHostMask(networkPrefixLength)));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private T getMask(int networkPrefixLength, T[] cache, boolean network, boolean withPrefixLength, boolean networkAddress) {
        T[] TArray;
        int zerosSubnetIndex;
        int onesSubnetIndex;
        int bits = networkPrefixLength;
        IPAddress.IPVersion version = this.getIPVersion();
        int addressBitLength = IPAddress.getBitCount(version);
        if (bits < 0) throw new PrefixLenException(bits, version);
        if (bits > addressBitLength) {
            throw new PrefixLenException(bits, version);
        }
        int cacheIndex = bits;
        Object subnet = cache[cacheIndex];
        if (subnet != null) return subnet;
        if (network) {
            onesSubnetIndex = addressBitLength;
            zerosSubnetIndex = 0;
        } else {
            onesSubnetIndex = 0;
            zerosSubnetIndex = addressBitLength;
        }
        Object onesSubnet = cache[onesSubnetIndex];
        Object zerosSubnet = cache[zerosSubnetIndex];
        if (onesSubnet == null || zerosSubnet == null) {
            Object[] newSegments;
            AddressCreator creator;
            TArray = cache;
            // MONITORENTER : cache
            int segmentCount = IPAddress.getSegmentCount(version);
            int bitsPerSegment = IPAddress.getBitsPerSegment(version);
            int bytesPerSegment = IPAddress.getBytesPerSegment(version);
            onesSubnet = cache[onesSubnetIndex];
            if (onesSubnet == null) {
                IPAddressSegment segment;
                creator = this.getAddressCreator();
                newSegments = (IPAddressSegment[])creator.createSegmentArray(segmentCount);
                int maxSegmentValue = IPAddress.getMaxSegmentValue(version);
                if (network && withPrefixLength) {
                    segment = (IPAddressSegment)creator.createSegment(maxSegmentValue, IPAddressSection.getSegmentPrefixLength(bitsPerSegment, addressBitLength));
                    Arrays.fill(newSegments, 0, newSegments.length - 1, segment);
                    IPAddressSegment lastSegment = (IPAddressSegment)creator.createSegment(maxSegmentValue, IPAddressSection.getSegmentPrefixLength(bitsPerSegment, bitsPerSegment));
                    newSegments[newSegments.length - 1] = lastSegment;
                    onesSubnet = creator.createAddressInternal((IPAddressSegment[])newSegments, IPAddressNetwork.cacheBits(addressBitLength));
                } else {
                    segment = (IPAddressSegment)creator.createSegment(maxSegmentValue);
                    Arrays.fill(newSegments, segment);
                    onesSubnet = creator.createAddressInternal((IPAddressSegment[])newSegments);
                }
                this.initMaskCachedValues(((IPAddress)onesSubnet).getSection(), network, withPrefixLength, networkAddress, addressBitLength, onesSubnetIndex, segmentCount, bitsPerSegment, bytesPerSegment);
                cache[onesSubnetIndex] = onesSubnet;
            }
            if ((zerosSubnet = cache[zerosSubnetIndex]) == null) {
                IPAddressSegment seg;
                creator = this.getAddressCreator();
                newSegments = (IPAddressSegment[])creator.createSegmentArray(segmentCount);
                if (network && withPrefixLength) {
                    seg = (IPAddressSegment)creator.createSegment(0, IPAddressSection.getSegmentPrefixLength(bitsPerSegment, 0));
                    Arrays.fill(newSegments, seg);
                    zerosSubnet = creator.createAddressInternal((IPAddressSegment[])newSegments, IPAddressNetwork.cacheBits(0));
                    if (this.getPrefixConfiguration().zeroHostsAreSubnets() && !networkAddress) {
                        zerosSubnet = ((IPAddress)zerosSubnet).getLower();
                    }
                } else {
                    seg = (IPAddressSegment)creator.createSegment(0);
                    Arrays.fill(newSegments, seg);
                    zerosSubnet = creator.createAddressInternal((IPAddressSegment[])newSegments);
                }
                this.initMaskCachedValues(((IPAddress)zerosSubnet).getSection(), network, withPrefixLength, networkAddress, addressBitLength, zerosSubnetIndex, segmentCount, bitsPerSegment, bytesPerSegment);
                cache[zerosSubnetIndex] = zerosSubnet;
            }
            // MONITOREXIT : TArray
        }
        TArray = cache;
        // MONITORENTER : cache
        subnet = cache[cacheIndex];
        if (subnet == null) {
            BiFunction<T, Integer, S> segProducer = this.getSegmentProducer();
            int segmentCount = IPAddress.getSegmentCount(version);
            int bitsPerSegment = IPAddress.getBitsPerSegment(version);
            int bytesPerSegment = IPAddress.getBytesPerSegment(version);
            int prefix = bits;
            IPAddressSegment onesSegment = (IPAddressSegment)segProducer.apply(onesSubnet, 0);
            IPAddressSegment zerosSegment = (IPAddressSegment)segProducer.apply(zerosSubnet, 0);
            AddressCreator creator = this.getAddressCreator();
            ArrayList<IPAddressSegment> segmentList = new ArrayList<IPAddressSegment>(segmentCount);
            int i = 0;
            while (bits > 0) {
                if (bits > bitsPerSegment) {
                    segmentList.add(network ? onesSegment : zerosSegment);
                } else {
                    IPAddressSegment segment = null;
                    int offset = (bits - 1) % bitsPerSegment + 1;
                    int entry = offset;
                    for (int j = 0; j < segmentCount; ++j, entry += bitsPerSegment) {
                        T prev;
                        if (entry == cacheIndex || (prev = cache[entry]) == null) continue;
                        segment = (IPAddressSegment)segProducer.apply(prev, j);
                        break;
                    }
                    if (segment == null) {
                        int mask = this.getSegmentNetworkMask(bits);
                        segment = network ? (withPrefixLength ? (IPAddressSegment)creator.createSegment(mask, IPAddressSection.getSegmentPrefixLength(bitsPerSegment, bits)) : (IPAddressSegment)creator.createSegment(mask)) : (IPAddressSegment)creator.createSegment(this.getSegmentHostMask(bits));
                    }
                    segmentList.add(segment);
                }
                ++i;
                bits -= bitsPerSegment;
            }
            while (i < segmentCount) {
                segmentList.add(network ? zerosSegment : onesSegment);
                ++i;
            }
            IPAddressSegment[] newSegments = (IPAddressSegment[])creator.createSegmentArray(segmentList.size());
            segmentList.toArray(newSegments);
            if (network && withPrefixLength) {
                subnet = creator.createAddressInternal(newSegments, IPAddressNetwork.cacheBits(prefix));
                if (this.getPrefixConfiguration().zeroHostsAreSubnets() && !networkAddress) {
                    subnet = ((IPAddress)subnet).getLower();
                }
            } else {
                subnet = creator.createAddressInternal(newSegments);
            }
            this.initMaskCachedValues(((IPAddress)subnet).getSection(), network, withPrefixLength, networkAddress, addressBitLength, prefix, segmentCount, bitsPerSegment, bytesPerSegment);
            cache[cacheIndex] = subnet;
        }
        // MONITOREXIT : TArray
        return subnet;
    }

    private void initMaskCachedValues(IPAddressSection section, boolean network, boolean withPrefixLength, boolean networkAddress, int addressBitLength, int networkPrefixLength, int segmentCount, int bitsPerSegment, int bytesPerSegment) {
        BigInteger cachedCount;
        Integer cachedNetworkPrefix;
        Integer cachedEquivalentPrefix;
        Integer cachedMinPrefix;
        IPAddressDivisionGrouping.RangeList zeroSegments;
        IPAddressDivisionGrouping.RangeList zeroRanges;
        boolean hasZeroRanges = network ? addressBitLength - networkPrefixLength >= bitsPerSegment : networkPrefixLength >= bitsPerSegment;
        IPAddressDivisionGrouping.RangeList noZeros = IPAddressSection.getNoZerosRange();
        if (hasZeroRanges) {
            int rangeLen;
            int rangeIndex;
            if (network) {
                int segmentIndex;
                rangeIndex = segmentIndex = IPAddressSection.getNetworkSegmentIndex(networkPrefixLength, bytesPerSegment, bitsPerSegment) + 1;
                rangeLen = segmentCount - segmentIndex;
            } else {
                rangeIndex = 0;
                rangeLen = IPAddressSection.getHostSegmentIndex(networkPrefixLength, bytesPerSegment, bitsPerSegment);
            }
            zeroRanges = IPAddressSection.getSingleRange(rangeIndex, rangeLen);
            zeroSegments = network && withPrefixLength && !this.getPrefixConfiguration().prefixedSubnetsAreExplicit() ? noZeros : zeroRanges;
        } else {
            zeroSegments = zeroRanges = noZeros;
        }
        Integer npl = IPAddressNetwork.cacheBits(networkPrefixLength);
        if (network && withPrefixLength) {
            if (this.getPrefixConfiguration().prefixedSubnetsAreExplicit() || this.getPrefixConfiguration().zeroHostsAreSubnets() && !networkAddress) {
                cachedEquivalentPrefix = cachedMinPrefix = IPAddressNetwork.cacheBits(addressBitLength);
                cachedNetworkPrefix = npl;
                cachedCount = BigInteger.ONE;
            } else {
                cachedMinPrefix = cachedNetworkPrefix = npl;
                cachedEquivalentPrefix = cachedNetworkPrefix;
                cachedCount = BigInteger.valueOf(2L).pow(addressBitLength - networkPrefixLength);
            }
        } else {
            cachedEquivalentPrefix = cachedMinPrefix = IPAddressNetwork.cacheBits(addressBitLength);
            cachedNetworkPrefix = null;
            cachedCount = BigInteger.ONE;
        }
        section.initCachedValues(npl, network, cachedNetworkPrefix, cachedMinPrefix, cachedEquivalentPrefix, cachedCount, zeroSegments, zeroRanges);
    }

    protected static Integer cacheBits(int i) {
        return IPAddressSection.cacheBits(i);
    }

    public static String getPrefixString(int networkPrefixLength) {
        return new StringBuilder(HostIdentifierStringValidator.MAX_PREFIX_CHARS + 1).append('/').append(networkPrefixLength).toString();
    }

    public static class HostNameGenerator
    extends AddressNetwork.HostIdentifierStringGenerator<HostName> {
        private static final long serialVersionUID = 4L;
        private final HostIDStringAddressGenerator<HostName> addressGenerator;
        private final HostNameParameters options;

        public HostNameGenerator(Map<String, HostName> backingMap, HostNameParameters options, final boolean reverseLookup) {
            super(backingMap);
            this.addressGenerator = new HostIDStringAddressGenerator<HostName>(backingMap, options.addressOptions){

                @Override
                protected HostName create(IPAddress addr) {
                    if (reverseLookup) {
                        return new HostName(addr.toInetAddress().getHostName());
                    }
                    return new HostName(addr);
                }

                @Override
                protected void cache(HostName result, IPAddress addr) {
                    result.cacheAddress(addr);
                }

                @Override
                protected void added(HostName added) {
                    this.added(added);
                }
            };
            this.options = options;
        }

        public HostNameGenerator(Map<String, HostName> backingMap) {
            this(backingMap, HostName.DEFAULT_VALIDATION_OPTIONS, false);
        }

        public HostNameGenerator(HostNameParameters options) {
            this(null, options, false);
        }

        public HostNameGenerator() {
            this(null, null, false);
        }

        @Override
        protected HostName create(String key) {
            return this.options == null ? new HostName(key) : new HostName(key, this.options);
        }

        public static Address.SegmentValueProvider getValueProvider(byte[] bytes) {
            return HostIDStringAddressGenerator.getValueProvider(bytes);
        }

        @Override
        public HostName get(byte[] bytes) {
            return this.addressGenerator.get(bytes);
        }

        @Override
        public HostName get(Address.AddressValueProvider addressProvider) {
            return this.addressGenerator.get(addressProvider);
        }

        public HostName get(IPAddress.IPVersion version, Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength) {
            return this.addressGenerator.get(version, lowerValueProvider, upperValueProvider, prefixLength);
        }

        public HostName get(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength, CharSequence zone) {
            return this.addressGenerator.get(lowerValueProvider, upperValueProvider, prefixLength, zone);
        }
    }

    public static class IPAddressStringGenerator
    extends AddressNetwork.HostIdentifierStringGenerator<IPAddressString> {
        private static final long serialVersionUID = 4L;
        private final HostIDStringAddressGenerator<IPAddressString> addressGenerator;

        public IPAddressStringGenerator(Map<String, IPAddressString> backingMap, IPAddressStringParameters options) {
            super(backingMap);
            this.addressGenerator = new HostIDStringAddressGenerator<IPAddressString>(backingMap, options){

                @Override
                protected IPAddressString create(IPAddress addr) {
                    return addr.toAddressString();
                }

                @Override
                protected void cache(IPAddressString result, IPAddress addr) {
                    result.cacheAddress(addr);
                }

                @Override
                protected void added(IPAddressString added) {
                    this.added(added);
                }
            };
        }

        public IPAddressStringGenerator(Map<String, IPAddressString> backingMap) {
            this(backingMap, null);
        }

        public IPAddressStringGenerator(IPAddressStringParameters options) {
            this(null, options);
        }

        public IPAddressStringGenerator() {
            this(null, null);
        }

        @Override
        protected IPAddressString create(String addressString) {
            IPAddressStringParameters options = ((HostIDStringAddressGenerator)this.addressGenerator).addressGenerator.options;
            return options == null ? new IPAddressString(addressString) : new IPAddressString(addressString, options);
        }

        public static Address.SegmentValueProvider getValueProvider(byte[] bytes) {
            return HostIDStringAddressGenerator.getValueProvider(bytes);
        }

        @Override
        public IPAddressString get(byte[] bytes) {
            return this.addressGenerator.get(bytes);
        }

        public IPAddressString get(IPAddress.IPAddressValueProvider addressProvider) {
            return this.addressGenerator.get(addressProvider);
        }

        @Override
        public IPAddressString get(Address.AddressValueProvider addressProvider) {
            return this.addressGenerator.get(addressProvider);
        }

        public IPAddressString get(IPAddress.IPVersion version, Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength) {
            return this.addressGenerator.get(version, lowerValueProvider, upperValueProvider, prefixLength);
        }

        public IPAddressString get(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength, CharSequence zone) {
            return this.addressGenerator.get(lowerValueProvider, upperValueProvider, prefixLength, zone);
        }
    }

    public static abstract class HostIDStringAddressGenerator<T extends HostIdentifierString>
    implements Serializable {
        private static final long serialVersionUID = 4L;
        private final IPAddressGenerator addressGenerator;
        protected final Map<String, T> backingMap;

        public HostIDStringAddressGenerator() {
            this(null, null);
        }

        public HostIDStringAddressGenerator(IPAddressStringParameters options) {
            this(null, options);
        }

        public HostIDStringAddressGenerator(Map<String, T> backingMap) {
            this(backingMap, null);
        }

        public HostIDStringAddressGenerator(Map<String, T> backingMap, IPAddressStringParameters options) {
            this.backingMap = backingMap;
            this.addressGenerator = new IPAddressGenerator(options);
        }

        public Map<String, T> getBackingMap() {
            return this.backingMap;
        }

        public static Address.SegmentValueProvider getValueProvider(byte[] bytes) {
            int segmentByteCount = bytes.length == 4 ? 1 : 2;
            return HostIDStringAddressGenerator.getValueProvider(bytes, segmentByteCount);
        }

        public static Address.SegmentValueProvider getValueProvider(byte[] bytes, int segmentByteCount) {
            return segmentIndex -> {
                int start2;
                int value = 0;
                int end = start2 + segmentByteCount;
                for (start2 = segmentIndex * segmentByteCount; start2 < end; ++start2) {
                    value = value << 8 | 0xFF & bytes[start2];
                }
                return value;
            };
        }

        public T get(byte[] bytes) {
            IPAddress.IPVersion version = bytes.length == 4 ? IPAddress.IPVersion.IPV4 : IPAddress.IPVersion.IPV6;
            int segmentByteCount = version.isIPv4() ? 1 : 2;
            return this.get(version, HostIDStringAddressGenerator.getValueProvider(bytes, segmentByteCount), null, null, null);
        }

        public T get(Address.AddressValueProvider addressProvider) {
            if (addressProvider instanceof IPAddress.IPAddressValueProvider) {
                return this.get((IPAddress.IPAddressValueProvider)addressProvider);
            }
            return this.get(addressProvider.getSegmentCount() == 4 ? IPAddress.IPVersion.IPV4 : IPAddress.IPVersion.IPV6, addressProvider.getValues(), addressProvider.getUpperValues(), null, null);
        }

        public T get(IPAddress.IPAddressValueProvider addressProvider) {
            return this.get(addressProvider.getIPVersion(), addressProvider.getValues(), addressProvider.getUpperValues(), addressProvider.getPrefixLength(), addressProvider.getZone());
        }

        public T get(IPAddress.IPVersion version, Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength) {
            return this.get(version, lowerValueProvider, upperValueProvider, prefixLength, null);
        }

        public T get(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength, CharSequence zone) {
            return this.get(IPAddress.IPVersion.IPV6, lowerValueProvider, upperValueProvider, prefixLength, zone);
        }

        private T get(IPAddress.IPVersion version, Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength, CharSequence zone) {
            if (this.backingMap == null) {
                IPAddress addr = this.addressGenerator.from(version, lowerValueProvider, upperValueProvider, prefixLength, zone);
                return this.create(addr);
            }
            String key = this.toNormalizedString(version, lowerValueProvider, upperValueProvider, prefixLength, zone);
            HostIdentifierString result = (HostIdentifierString)this.backingMap.get(key);
            if (result == null) {
                IPAddress addr = this.addressGenerator.from(version, lowerValueProvider, upperValueProvider, prefixLength, zone);
                addr.cacheNormalizedString(key);
                result = this.create(addr);
                HostIdentifierString existing = this.backingMap.putIfAbsent(key, result);
                if (existing == null) {
                    this.added(result);
                } else {
                    result = existing;
                }
            }
            return (T)result;
        }

        protected String toNormalizedString(IPAddress.IPVersion version, Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength, CharSequence zone) {
            return this.addressGenerator.toNormalizedString(version, lowerValueProvider, upperValueProvider, prefixLength, zone);
        }

        protected abstract T create(IPAddress var1);

        protected abstract void cache(T var1, IPAddress var2);

        protected abstract void added(T var1);
    }

    public static class IPAddressGenerator
    implements Serializable {
        private static final long serialVersionUID = 4L;
        protected final IPAddressStringParameters options;

        public IPAddressGenerator() {
            this(null);
        }

        public IPAddressGenerator(IPv4AddressNetwork ipv4Network, IPv6AddressNetwork ipv6Network) {
            this(new IPAddressStringParameters.Builder().getIPv4AddressParametersBuilder().setNetwork(ipv4Network).getParentBuilder().getIPv6AddressParametersBuilder().setNetwork(ipv6Network).getEmbeddedIPv4AddressParametersBuilder().setNetwork(ipv4Network).getEmbeddedIPv4AddressParentBuilder().getParentBuilder().toParams());
        }

        public IPAddressGenerator(IPAddressStringParameters options) {
            if (options == null) {
                options = IPAddressString.DEFAULT_VALIDATION_OPTIONS;
            }
            this.options = options;
        }

        protected String toNormalizedString(IPAddress.IPVersion version, Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength, CharSequence zone) {
            if (version == IPAddress.IPVersion.IPV4) {
                IPv4AddressNetwork network = this.options.getIPv4Parameters().getNetwork();
                return IPv4Address.toNormalizedString(network, lowerValueProvider, upperValueProvider, prefixLength);
            }
            if (version == IPAddress.IPVersion.IPV6) {
                IPv6AddressNetwork network = this.options.getIPv6Parameters().getNetwork();
                return IPv6Address.toNormalizedString(network, lowerValueProvider, upperValueProvider, prefixLength, zone);
            }
            throw new IllegalArgumentException();
        }

        public IPAddress from(InetAddress inetAddress) {
            if (inetAddress instanceof Inet4Address) {
                return this.getIPv4Creator().createAddress((Inet4Address)inetAddress);
            }
            if (inetAddress instanceof Inet6Address) {
                return this.getIPv6Creator().createAddress((Inet6Address)inetAddress);
            }
            return null;
        }

        public IPAddress from(InetAddress inetAddress, Integer prefixLength) {
            if (inetAddress instanceof Inet4Address) {
                return this.getIPv4Creator().createAddress((Inet4Address)inetAddress, prefixLength);
            }
            if (inetAddress instanceof Inet6Address) {
                return this.getIPv6Creator().createAddress((Inet6Address)inetAddress, prefixLength);
            }
            return null;
        }

        public IPAddress from(InterfaceAddress interfaceAddress) {
            InetAddress inetAddress = interfaceAddress.getAddress();
            if (inetAddress instanceof Inet4Address) {
                return this.getIPv4Creator().createAddress((Inet4Address)inetAddress, IPAddressNetwork.cacheBits(interfaceAddress.getNetworkPrefixLength()));
            }
            if (inetAddress instanceof Inet6Address) {
                return this.getIPv6Creator().createAddress((Inet6Address)inetAddress, IPAddressNetwork.cacheBits(interfaceAddress.getNetworkPrefixLength()));
            }
            return null;
        }

        public IPAddress from(byte[] bytes) {
            return this.from(bytes, 0, bytes.length, null, null);
        }

        public IPAddress from(byte[] bytes, int byteStartIndex, int byteEndIndex) {
            return this.from(bytes, byteStartIndex, byteEndIndex, null, null);
        }

        public IPAddress from(byte[] bytes, int byteStartIndex, int byteEndIndex, Integer networkPrefixLength) {
            return this.from(bytes, byteStartIndex, byteEndIndex, networkPrefixLength, null);
        }

        public IPAddress from(byte[] bytes, Integer prefixLength) {
            return this.from(bytes, 0, bytes.length, prefixLength, null);
        }

        private IPAddress from(byte[] bytes, int byteStartIndex, int byteEndIndex, Integer prefixLength, CharSequence zone) {
            if (byteEndIndex - byteStartIndex < 16) {
                return this.getIPv4Creator().createAddress(bytes, byteStartIndex, byteEndIndex, prefixLength);
            }
            return this.getIPv6Creator().createAddress(bytes, byteStartIndex, byteEndIndex, prefixLength, zone);
        }

        public IPAddress from(IPAddress.IPVersion version, Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength) {
            return this.from(version, lowerValueProvider, upperValueProvider, prefixLength, null);
        }

        private IPv4AddressNetwork.IPv4AddressCreator getIPv4Creator() {
            IPv4AddressNetwork network = this.options.getIPv4Parameters().getNetwork();
            IPv4AddressNetwork.IPv4AddressCreator addressCreator = network.getAddressCreator();
            return addressCreator;
        }

        private IPv6AddressNetwork.IPv6AddressCreator getIPv6Creator() {
            IPv6AddressNetwork network = this.options.getIPv6Parameters().getNetwork();
            IPv6AddressNetwork.IPv6AddressCreator addressCreator = network.getAddressCreator();
            return addressCreator;
        }

        private IPAddress from(IPAddress.IPVersion version, Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefixLength, CharSequence zone) {
            if (version == IPAddress.IPVersion.IPV4) {
                return this.getIPv4Creator().createAddress(lowerValueProvider, upperValueProvider, prefixLength);
            }
            if (version == IPAddress.IPVersion.IPV6) {
                return this.getIPv6Creator().createAddress(lowerValueProvider, upperValueProvider, prefixLength, zone);
            }
            throw new IllegalArgumentException();
        }
    }

    public static abstract class IPAddressCreator<T extends IPAddress, R extends IPAddressSection, E extends IPAddressSection, S extends IPAddressSegment, J extends InetAddress>
    extends AddressCreator<T, R, E, S> {
        private static final long serialVersionUID = 4L;
        private IPAddressNetwork<T, R, E, S, J> owner;

        protected IPAddressCreator(IPAddressNetwork<T, R, E, S, J> owner) {
            this.owner = owner;
        }

        public IPAddressNetwork<T, R, E, S, J> getNetwork() {
            return this.owner;
        }

        @Override
        protected S createSegmentInternal(int value, Integer segmentPrefixLength, CharSequence addressStr, int originalVal, boolean isStandardString, int lowerStringStartIndex, int lowerStringEndIndex) {
            IPAddressSegment segment = (IPAddressSegment)this.createSegment(value, segmentPrefixLength);
            segment.setStandardString(addressStr, isStandardString, lowerStringStartIndex, lowerStringEndIndex, originalVal);
            segment.setWildcardString(addressStr, isStandardString, lowerStringStartIndex, lowerStringEndIndex, originalVal);
            return (S)segment;
        }

        @Override
        protected S createRangeSegmentInternal(int lower, int upper, Integer segmentPrefixLength, CharSequence addressStr, int originalLower, int originalUpper, boolean isStandardString, boolean isStandardRangeString, int lowerStringStartIndex, int lowerStringEndIndex, int upperStringEndIndex) {
            IPAddressSegment segment = (IPAddressSegment)this.createSegment(lower, upper, segmentPrefixLength);
            segment.setStandardString(addressStr, isStandardString, isStandardRangeString, lowerStringStartIndex, lowerStringEndIndex, upperStringEndIndex, originalLower, originalUpper);
            segment.setWildcardString(addressStr, isStandardRangeString, lowerStringStartIndex, upperStringEndIndex, originalLower, originalUpper);
            return (S)segment;
        }

        protected abstract R[] createSectionArray(int var1);

        @Override
        protected abstract R createSectionInternal(S[] var1);

        protected abstract R createEmbeddedSectionInternal(IPAddressSection var1, S[] var2);

        @Override
        protected R createPrefixedSectionInternal(S[] segments, Integer prefix) {
            return (R)this.createPrefixedSectionInternal((IPAddressSegment[])segments, prefix, false);
        }

        @Override
        protected abstract R createPrefixedSectionInternal(S[] var1, Integer var2, boolean var3);

        public abstract R createFullSectionInternal(Address.SegmentValueProvider var1, Address.SegmentValueProvider var2, Integer var3);

        public abstract R createSection(byte[] var1, int var2, int var3, Integer var4);

        public abstract R createSection(byte[] var1, Integer var2);

        public abstract R createSection(S[] var1, Integer var2);

        public abstract R createSection(S[] var1);

        protected abstract T[] createAddressArray(int var1);

        @Override
        public T createAddress(S[] segments) {
            return this.createAddress((R)this.createSection((IPAddressSegment[])segments));
        }

        public T createAddress(S[] segments, Integer prefix) {
            return this.createAddress((R)this.createSection((IPAddressSegment[])segments, prefix));
        }

        @Override
        protected T createAddressInternal(S[] segments) {
            return this.createAddress((R)this.createSectionInternal((IPAddressSegment[])segments));
        }

        @Override
        protected T createAddressInternal(S[] segments, Integer prefix, boolean singleOnly) {
            return this.createAddress((R)this.createPrefixedSectionInternal((IPAddressSegment[])segments, prefix, singleOnly));
        }

        @Override
        protected T createAddressInternal(S[] segments, Integer prefix) {
            return this.createAddress((R)this.createPrefixedSectionInternal((IPAddressSegment[])segments, prefix));
        }

        protected T createAddressInternal(S[] segments, CharSequence zone) {
            return this.createAddressInternal(this.createSectionInternal((IPAddressSegment[])segments), zone);
        }

        public T createAddress(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefix) {
            return this.createAddress(this.createFullSectionInternal(lowerValueProvider, upperValueProvider, prefix));
        }

        public T createAddress(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefix, CharSequence zone) {
            return this.createAddressInternal(this.createFullSectionInternal(lowerValueProvider, upperValueProvider, prefix), zone);
        }

        protected R createSectionInternal(byte[] bytes, int segmentCount, Integer prefix) {
            return (R)((IPAddressSection)this.createSectionInternal(bytes, segmentCount, prefix, false));
        }

        protected abstract R createSection(byte[] var1, int var2, int var3, int var4, Integer var5);

        public T createAddress(byte[] bytes, int byteStartIndex, int byteEndIndex, Integer prefix) {
            return this.createAddress(this.createSection(bytes, byteStartIndex, byteEndIndex, this.getAddressSegmentCount(), prefix));
        }

        public T createAddress(byte[] bytes, int byteStartIndex, int byteEndIndex, Integer prefix, CharSequence zone) {
            return this.createAddressInternal(this.createSection(bytes, byteStartIndex, byteEndIndex, this.getAddressSegmentCount(), prefix), zone);
        }

        protected T createAddressInternal(byte[] bytes, Integer prefix) {
            return this.createAddress(this.createSectionInternal(bytes, this.getAddressSegmentCount(), prefix));
        }

        protected T createAddressInternal(byte[] bytes, Integer prefix, CharSequence zone) {
            return this.createAddressInternal(this.createSectionInternal(bytes, this.getAddressSegmentCount(), prefix), zone);
        }

        @Override
        protected T createAddressInternal(byte[] bytes, CharSequence zone) {
            return this.createAddressInternal(this.createSectionInternal(bytes, this.getAddressSegmentCount(), (Integer)null), zone);
        }

        protected T createAddressInternal(byte[] bytes, Integer prefix, CharSequence zone, HostName fromHost) {
            return this.createAddressInternal(this.createSectionInternal(bytes, this.getAddressSegmentCount(), prefix), zone, (HostIdentifierString)fromHost);
        }

        protected T createAddressInternal(byte[] bytes, Integer prefix, HostName fromHost) {
            return this.createAddressInternal(this.createSectionInternal(bytes, this.getAddressSegmentCount(), prefix), (HostIdentifierString)fromHost);
        }

        public T createAddress(byte[] bytes, Integer prefix) {
            return this.createAddress(this.createSection(bytes, prefix));
        }

        @Override
        public T createAddress(byte[] bytes) {
            return this.createAddress(this.createSection(bytes, (Integer)null));
        }

        @Override
        protected T createAddressInternal(R section, CharSequence zone, HostIdentifierString from) {
            T result = this.createAddressInternal(section, zone);
            ((IPAddress)result).cache(from);
            return result;
        }

        @Override
        protected T createAddressInternal(R section, HostIdentifierString from) {
            T result = this.createAddress(section);
            ((IPAddress)result).cache(from);
            return result;
        }

        @Override
        protected abstract T createAddress(J var1);

        protected abstract T createAddress(J var1, Integer var2);

        protected abstract T createAddressInternal(R var1, CharSequence var2);

        @Override
        public abstract T createAddress(R var1);

        protected abstract int getAddressSegmentCount();

        public T createSequentialBlockAddress(IPAddressSegmentSeries address, int index, int lowerVal, int upperVal) {
            IPAddressSegment[] segments = this.createSequentialBlockSegments(address, index, lowerVal, upperVal);
            return (T)this.createAddressInternal(segments);
        }

        public R createSequentialBlockSection(IPAddressSegmentSeries series, int index, int lowerVal, int upperVal) {
            IPAddressSegment[] segments = this.createSequentialBlockSegments(series, index, lowerVal, upperVal);
            return (R)this.createSectionInternal(segments);
        }

        private S[] createSequentialBlockSegments(IPAddressSegmentSeries series, int index, int lowerVal, int upperVal) {
            AddressSegment[] segments = (IPAddressSegment[])this.createSegmentArray(series.getSegmentCount());
            series.getSegments(0, index, segments, 0);
            segments[index] = (IPAddressSegment)this.createSegment(lowerVal, upperVal, null);
            if (++index < segments.length) {
                IPAddressSegment allRangeSegment = (IPAddressSegment)this.createSegment(0, this.getMaxValuePerSegment(), null);
                do {
                    segments[index] = allRangeSegment;
                } while (++index < segments.length);
            }
            return segments;
        }
    }
}

