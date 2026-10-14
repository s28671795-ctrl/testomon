/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv6;

import inet.ipaddr.Address;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.ipv4.IPv4AddressSection;
import inet.ipaddr.ipv6.IPv6Address;
import inet.ipaddr.ipv6.IPv6AddressSection;
import inet.ipaddr.ipv6.IPv6AddressSegment;
import inet.ipaddr.mac.MACAddress;
import inet.ipaddr.mac.MACAddressSection;
import java.io.Serializable;
import java.net.Inet6Address;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiFunction;
import java.util.function.Function;

public class IPv6AddressNetwork
extends IPAddressNetwork<IPv6Address, IPv6AddressSection, IPv4AddressSection, IPv6AddressSegment, Inet6Address> {
    private static final long serialVersionUID = 4L;
    private static AddressNetwork.PrefixConfiguration defaultPrefixConfiguration = AddressNetwork.getDefaultPrefixConfiguration();
    static final IPv6AddressSegment[] EMPTY_SEGMENTS = new IPv6AddressSegment[0];
    private static final IPv6AddressSection[] EMPTY_SECTION = new IPv6AddressSection[0];
    private static final IPv6Address[] EMPTY_ADDRESS = new IPv6Address[0];
    private static boolean CACHE_SEGMENTS_BY_PREFIX = true;
    private IPv6AddressSection linkLocalPrefix;

    public IPv6AddressNetwork() {
        super(IPv6Address.class);
    }

    @Override
    public AddressNetwork.PrefixConfiguration getPrefixConfiguration() {
        return defaultPrefixConfiguration;
    }

    public static void setDefaultPrefixConfiguration(AddressNetwork.PrefixConfiguration config) {
        defaultPrefixConfiguration = config;
    }

    public static AddressNetwork.PrefixConfiguration getDefaultPrefixConfiguration() {
        return defaultPrefixConfiguration;
    }

    protected boolean isCompatible(IPv6AddressNetwork other) {
        return super.isCompatible(other);
    }

    @Override
    protected BiFunction<IPv6Address, Integer, IPv6AddressSegment> getSegmentProducer() {
        return (address, index) -> address.getSegment((int)index);
    }

    @Override
    protected Function<IPv6Address, IPv6AddressSection> getSectionProducer() {
        return IPv6Address::getSection;
    }

    protected IPv6AddressCreator createAddressCreator() {
        return new IPv6AddressCreator(this);
    }

    @Override
    protected IPv6Address createLoopback() {
        IPv6AddressCreator creator = this.getAddressCreator();
        IPv6AddressSegment zero = creator.createSegment(0);
        IPv6AddressSegment[] segs = creator.createSegmentArray(8);
        segs[5] = segs[6] = zero;
        segs[4] = segs[6];
        segs[3] = segs[6];
        segs[2] = segs[6];
        segs[1] = segs[6];
        segs[0] = segs[6];
        segs[7] = creator.createSegment(1);
        return creator.createAddressInternal(segs);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IPv6AddressSection getLinkLocalPrefix() {
        if (this.linkLocalPrefix == null) {
            IPv6AddressNetwork iPv6AddressNetwork = this;
            synchronized (iPv6AddressNetwork) {
                if (this.linkLocalPrefix == null) {
                    this.linkLocalPrefix = this.createLinkLocalPrefix();
                }
            }
        }
        return this.linkLocalPrefix;
    }

    private IPv6AddressSection createLinkLocalPrefix() {
        IPv6AddressCreator creator = this.getAddressCreator();
        IPv6AddressSegment zeroSeg = creator.createSegment(0);
        IPv6AddressSection linkLocalPrefix = creator.createSectionInternal(new IPv6AddressSegment[]{creator.createSegment(65152), zeroSeg, zeroSeg, zeroSeg});
        return linkLocalPrefix;
    }

    @Override
    public IPv6AddressCreator getAddressCreator() {
        return (IPv6AddressCreator)super.getAddressCreator();
    }

    @Override
    public boolean isIPv6() {
        return true;
    }

    @Override
    public IPAddress.IPVersion getIPVersion() {
        return IPAddress.IPVersion.IPV6;
    }

    public static class IPv6AddressCreator
    extends IPAddressNetwork.IPAddressCreator<IPv6Address, IPv6AddressSection, IPv4AddressSection, IPv6AddressSegment, Inet6Address> {
        private static final long serialVersionUID = 4L;
        Cache cache;
        boolean useSegmentCache = true;

        public IPv6AddressCreator(IPv6AddressNetwork network) {
            super(network);
            this.cache = new Cache();
        }

        protected IPv6AddressCreator(IPv6AddressNetwork network, Cache cache) {
            super(network);
            this.cache = cache;
        }

        @Override
        public void clearCaches() {
            super.clearCaches();
            this.cache.clear();
        }

        @Override
        public void setSegmentCaching(boolean enable) {
            this.useSegmentCache = enable;
        }

        @Override
        public IPv6AddressNetwork getNetwork() {
            return (IPv6AddressNetwork)super.getNetwork();
        }

        @Override
        public int getMaxValuePerSegment() {
            return 65535;
        }

        @Override
        protected int getAddressSegmentCount() {
            return 8;
        }

        public IPv6AddressSegment[] createSegmentArray(int length) {
            if (length == 0) {
                return EMPTY_SEGMENTS;
            }
            return new IPv6AddressSegment[length];
        }

        @Override
        public IPv6AddressSegment createSegment(int value) {
            if (this.useSegmentCache && value >= 0 && value <= 65535) {
                IPv6AddressSegment result;
                IPv6AddressSegment[][] cache = this.cache.segmentCache;
                int blockIndex = value >>> 8;
                int resultIndex = value - (blockIndex << 8);
                if (cache == null) {
                    cache = new IPv6AddressSegment[511][];
                    Cache.access$002(this.cache, cache);
                    IPv6AddressSegment[] block = new IPv6AddressSegment[256];
                    cache[blockIndex] = block;
                    result = block[resultIndex] = new IPv6AddressSegment(value);
                } else {
                    IPv6AddressSegment[] block = cache[blockIndex];
                    if (block == null) {
                        block = new IPv6AddressSegment[256];
                        cache[blockIndex] = block;
                        result = block[resultIndex] = new IPv6AddressSegment(value);
                    } else {
                        result = block[resultIndex];
                        if (result == null) {
                            result = block[resultIndex] = new IPv6AddressSegment(value);
                        }
                    }
                }
                return result;
            }
            return new IPv6AddressSegment(value);
        }

        @Override
        public IPv6AddressSegment createSegment(int value, Integer segmentPrefixLength) {
            if (segmentPrefixLength == null) {
                return this.createSegment(value);
            }
            if (this.useSegmentCache && value >= 0 && value <= 65535 && segmentPrefixLength >= 0 && segmentPrefixLength <= 128) {
                if (segmentPrefixLength == 0 && this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                    IPv6AddressSegment result = this.cache.ZERO_PREFIX_SEGMENT;
                    if (result == null) {
                        result = new IPv6AddressSegment(0, 0);
                        this.cache.ZERO_PREFIX_SEGMENT = result;
                    }
                    return result;
                }
                if (CACHE_SEGMENTS_BY_PREFIX) {
                    int prefixCacheSize;
                    IPv6AddressSegment result;
                    IPv6AddressSegment[] block;
                    IPv6AddressSegment[][] prefixCache;
                    int valueIndex;
                    int prefixIndex = segmentPrefixLength;
                    boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
                    if (isAllSubnets) {
                        int mask = this.getNetwork().getSegmentNetworkMask(segmentPrefixLength);
                        valueIndex = (value &= mask) >>> 16 - segmentPrefixLength;
                    } else {
                        valueIndex = value;
                    }
                    IPv6AddressSegment[][][] cache = this.cache.segmentPrefixCache;
                    int blockIndex = valueIndex >>> 8;
                    int resultIndex = valueIndex - (blockIndex << 8);
                    if (cache == null) {
                        cache = new IPv6AddressSegment[17][][];
                        Cache.access$302(this.cache, cache);
                        prefixCache = null;
                        block = null;
                        result = null;
                    } else {
                        prefixCache = cache[prefixIndex];
                        if (prefixCache != null) {
                            block = prefixCache[blockIndex];
                            result = block != null ? block[resultIndex] : null;
                        } else {
                            block = null;
                            result = null;
                        }
                    }
                    if (prefixCache == null) {
                        prefixCacheSize = isAllSubnets ? 1 << segmentPrefixLength : 65536;
                        prefixCache = new IPv6AddressSegment[prefixCacheSize + 256 - 1 >>> 8][];
                        cache[prefixIndex] = prefixCache;
                    }
                    if (block == null) {
                        prefixCacheSize = isAllSubnets ? 1 << segmentPrefixLength : 65536;
                        int highestIndex = prefixCacheSize >>> 8;
                        block = valueIndex >>> 8 == highestIndex ? new IPv6AddressSegment[prefixCacheSize - (highestIndex << 8)] : new IPv6AddressSegment[256];
                        prefixCache[blockIndex] = block;
                    }
                    if (result == null) {
                        block[resultIndex] = result = new IPv6AddressSegment(value, segmentPrefixLength);
                    }
                    return result;
                }
            }
            IPv6AddressSegment result = new IPv6AddressSegment(value, segmentPrefixLength);
            return result;
        }

        @Override
        public IPv6AddressSegment createSegment(int lower, int upper, Integer segmentPrefixLength) {
            if (segmentPrefixLength == null) {
                if (lower == upper) {
                    return this.createSegment(lower);
                }
                if (this.useSegmentCache && lower == 0 && upper == 65535) {
                    IPv6AddressSegment result = this.cache.ALL_RANGE_SEGMENT;
                    if (result == null) {
                        result = new IPv6AddressSegment(0, 65535, null);
                        this.cache.ALL_RANGE_SEGMENT = result;
                    }
                    return result;
                }
            } else {
                if (lower == upper) {
                    return this.createSegment(lower, segmentPrefixLength);
                }
                if (this.useSegmentCache && lower >= 0 && lower <= 65535 && upper >= 0 && upper <= 65535 && segmentPrefixLength >= 0 && segmentPrefixLength <= 128) {
                    if (segmentPrefixLength == 0 && this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                        return this.createSegment(0, segmentPrefixLength);
                    }
                    if (CACHE_SEGMENTS_BY_PREFIX) {
                        int bitsPerSegment = 16;
                        if (segmentPrefixLength > bitsPerSegment) {
                            segmentPrefixLength = bitsPerSegment;
                        }
                        if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                            int mask = this.getNetwork().getSegmentNetworkMask(segmentPrefixLength);
                            if ((upper & mask) == (lower &= mask)) {
                                return this.createSegment(lower, segmentPrefixLength);
                            }
                            int hostMask = this.getNetwork().getSegmentHostMask(segmentPrefixLength);
                            upper |= hostMask;
                        }
                        if (lower == 0 && upper == 65535) {
                            IPv6AddressSegment result;
                            int prefixIndex = segmentPrefixLength;
                            IPv6AddressSegment[] cache = this.cache.allPrefixedCache;
                            if (cache == null) {
                                cache = new IPv6AddressSegment[17];
                                Cache.access$502(this.cache, cache);
                                cache[prefixIndex] = result = new IPv6AddressSegment(0, 65535, segmentPrefixLength);
                            } else {
                                result = cache[prefixIndex];
                                if (result == null) {
                                    cache[prefixIndex] = result = new IPv6AddressSegment(0, 65535, segmentPrefixLength);
                                }
                            }
                            return result;
                        }
                    }
                }
            }
            IPv6AddressSegment result = new IPv6AddressSegment(lower, upper, segmentPrefixLength);
            return result;
        }

        @Override
        public IPv6AddressSection createFullSectionInternal(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefix) {
            return new IPv6AddressSection(lowerValueProvider, upperValueProvider, 8, prefix);
        }

        @Override
        protected IPv6AddressSection createSectionInternal(byte[] bytes, int segmentCount, Integer prefix, boolean singleOnly) {
            return new IPv6AddressSection(bytes, segmentCount, prefix, false, singleOnly);
        }

        protected IPv6AddressSection createSectionInternal(IPv6AddressSegment[] segments) {
            return new IPv6AddressSection(segments, 0, false);
        }

        protected IPv6AddressSection createPrefixedSectionInternal(IPv6AddressSegment[] segments, Integer prefix, boolean singleOnly) {
            return new IPv6AddressSection(segments, 0, false, prefix, singleOnly);
        }

        protected IPv6AddressSection createSectionInternal(IPv6AddressSegment[] segments, IPv4AddressSection embeddedSection) {
            IPv6AddressSection result = new IPv6AddressSection(segments, 0, false);
            result.embeddedIPv4Section = embeddedSection;
            return result;
        }

        protected IPv6AddressSection createSectionInternal(IPv6AddressSegment[] segments, IPv4AddressSection embeddedSection, Integer prefix) {
            IPv6AddressSection result = new IPv6AddressSection(segments, 0, false, prefix, false);
            result.embeddedIPv4Section = embeddedSection;
            return result;
        }

        protected IPv6AddressSection createSectionInternal(IPv6AddressSegment[] segments, IPv4AddressSection embeddedSection, Integer prefix, boolean singleOnly) {
            IPv6AddressSection result = new IPv6AddressSection(segments, 0, false, prefix, singleOnly);
            result.embeddedIPv4Section = embeddedSection;
            return result;
        }

        protected IPv6AddressSection createEmbeddedSectionInternal(IPv6AddressSection encompassingSection, IPv6AddressSegment[] segments, int startIndex) {
            return new IPv6AddressSection.EmbeddedIPv6AddressSection(encompassingSection, segments, startIndex);
        }

        protected IPv6AddressSection createEmbeddedSectionInternal(IPAddressSection encompassingSection, IPv6AddressSegment[] segments) {
            return new IPv6AddressSection.EmbeddedIPv6AddressSection((IPv6AddressSection)encompassingSection, segments, 0);
        }

        protected IPv6AddressSection createSectionInternal(IPv6AddressSegment[] segments, int startIndex) {
            return new IPv6AddressSection(segments, startIndex, false);
        }

        protected IPv6AddressSection createSectionInternal(IPv6AddressSegment[] segments, int startIndex, boolean extended) {
            return new IPv6AddressSection(segments, startIndex, false);
        }

        protected IPv6AddressSection[] createSectionArray(int length) {
            if (length == 0) {
                return EMPTY_SECTION;
            }
            return new IPv6AddressSection[length];
        }

        @Override
        public IPv6AddressSection createSection(byte[] bytes, int byteStartIndex, int byteEndIndex, Integer prefix) {
            return new IPv6AddressSection(bytes, byteStartIndex, byteEndIndex, -1, prefix, true, false);
        }

        @Override
        protected IPv6AddressSection createSection(byte[] bytes, int byteStartIndex, int byteEndIndex, int segmentCount, Integer prefix) {
            return new IPv6AddressSection(bytes, byteStartIndex, byteEndIndex, segmentCount, prefix, true, false);
        }

        @Override
        public IPv6AddressSection createSection(byte[] bytes, Integer prefix) {
            return new IPv6AddressSection(bytes, prefix);
        }

        public IPv6AddressSection createSection(IPv6AddressSegment[] segments) {
            return new IPv6AddressSection(segments);
        }

        public IPv6AddressSection createSection(IPv6AddressSegment[] segments, Integer networkPrefixLength) {
            return new IPv6AddressSection(segments, networkPrefixLength);
        }

        public IPv6AddressSection createSection(MACAddress eui) {
            return new IPv6AddressSection(eui);
        }

        public IPv6AddressSection createSection(MACAddressSection eui) {
            return new IPv6AddressSection(eui);
        }

        protected IPv6Address[] createAddressArray(int length) {
            if (length == 0) {
                return EMPTY_ADDRESS;
            }
            return new IPv6Address[length];
        }

        protected IPv6Address createAddressInternal(IPv6AddressSegment[] segments) {
            return (IPv6Address)super.createAddressInternal((IPAddressSegment[])segments);
        }

        @Override
        protected IPv6Address createAddressInternal(IPv6AddressSection section, CharSequence zone) {
            if (zone == null || zone.length() == 0) {
                return this.createAddress(section);
            }
            String zoneStr = zone.toString().trim();
            if (zoneStr.length() == 0) {
                return this.createAddress(section);
            }
            IPv6Address.IPv6Zone zoneObj = this.getCacheZoneObj(zoneStr);
            return this.createAddress(section, zoneObj);
        }

        private IPv6Address.IPv6Zone getCacheZoneObj(String zoneStr) {
            IPv6Address.IPv6Zone zoneObj;
            int scope = IPv6Address.IPv6Zone.checkIfScope(zoneStr);
            if (scope >= 0) {
                if (scope < this.cache.scopedZoneCache.length) {
                    zoneObj = this.cache.scopedZoneCache[scope];
                    if (zoneObj == null) {
                        ((Cache)this.cache).scopedZoneCache[scope] = zoneObj = new IPv6Address.IPv6Zone(scope);
                    }
                } else {
                    zoneObj = new IPv6Address.IPv6Zone(scope);
                }
                zoneObj.zoneStr = zoneStr;
            } else {
                Lock readLock = this.cache.zoneInterfaceCacheLock.readLock();
                readLock.lock();
                zoneObj = (IPv6Address.IPv6Zone)this.cache.zoneInterfaceCache.get(zoneStr);
                readLock.unlock();
                if (zoneObj == null) {
                    IPv6Address.IPv6Zone newZoneObj = new IPv6Address.IPv6Zone(zoneStr);
                    Lock writeLock = this.cache.zoneInterfaceCacheLock.writeLock();
                    writeLock.lock();
                    zoneObj = (IPv6Address.IPv6Zone)this.cache.zoneInterfaceCache.get(zoneStr);
                    if (zoneObj == null) {
                        zoneObj = newZoneObj;
                        this.cache.zoneInterfaceCache.put(zoneStr, zoneObj);
                    }
                    writeLock.unlock();
                }
            }
            return zoneObj;
        }

        public IPv6Address createAddress(IPv6AddressSegment[] segments, IPv6Address.IPv6Zone zone) {
            if (zone == null) {
                return this.createAddressInternal(segments);
            }
            return this.createAddress(this.createSectionInternal(segments), zone);
        }

        public IPv6Address createAddress(IPv6AddressSection section, IPv6Address.IPv6Zone zone) {
            if (zone == null) {
                return this.createAddress(section);
            }
            return new IPv6Address(section, zone);
        }

        @Override
        protected IPv6Address createAddressInternal(IPv6AddressSection section, CharSequence zone, HostIdentifierString from, IPv6Address lower, IPv6Address upper) {
            IPv6Address result = (IPv6Address)this.createAddressInternal(section, zone, from);
            result.cache(lower, upper);
            return result;
        }

        @Override
        public IPv6Address createAddress(IPv6AddressSection section) {
            return new IPv6Address(section);
        }

        @Override
        public IPv6Address createAddress(Inet6Address addr, Integer networkPrefixLength) {
            return new IPv6Address(addr, networkPrefixLength);
        }

        @Override
        public IPv6Address createAddress(Inet6Address addr) {
            return new IPv6Address(addr);
        }

        protected static class Cache
        implements Serializable {
            private static final long serialVersionUID = 1L;
            private transient IPv6AddressSegment ZERO_PREFIX_SEGMENT;
            private transient IPv6AddressSegment ALL_RANGE_SEGMENT;
            private transient IPv6AddressSegment[][] segmentCache;
            private transient IPv6AddressSegment[][][] segmentPrefixCache;
            private transient IPv6AddressSegment[] allPrefixedCache;
            private static final int MAX_ZONE_ENTRIES = 100;
            private transient LinkedHashMap<String, IPv6Address.IPv6Zone> zoneInterfaceCache = new LinkedHashMap<String, IPv6Address.IPv6Zone>(16, 0.75f, true){

                @Override
                protected boolean removeEldestEntry(Map.Entry<String, IPv6Address.IPv6Zone> eldest) {
                    return this.size() > 100;
                }
            };
            private ReadWriteLock zoneInterfaceCacheLock = new ReentrantReadWriteLock();
            private transient IPv6Address.IPv6Zone[] scopedZoneCache = new IPv6Address.IPv6Zone[256];

            protected Cache() {
            }

            void clear() {
                this.segmentCache = null;
                this.allPrefixedCache = null;
                this.segmentPrefixCache = null;
                this.ZERO_PREFIX_SEGMENT = null;
                this.ALL_RANGE_SEGMENT = null;
                Object[] scopedZoneCache2 = this.scopedZoneCache;
                this.scopedZoneCache = new IPv6Address.IPv6Zone[256];
                Arrays.fill(scopedZoneCache2, null);
                Lock lock = this.zoneInterfaceCacheLock.writeLock();
                lock.lock();
                this.zoneInterfaceCache.clear();
                lock.unlock();
            }

            static /* synthetic */ IPv6AddressSegment[][] access$002(Cache x0, IPv6AddressSegment[][] x1) {
                x0.segmentCache = x1;
                return x1;
            }

            static /* synthetic */ IPv6AddressSegment[][][] access$302(Cache x0, IPv6AddressSegment[][][] x1) {
                x0.segmentPrefixCache = x1;
                return x1;
            }

            static /* synthetic */ IPv6AddressSegment[] access$502(Cache x0, IPv6AddressSegment[] x1) {
                x0.allPrefixedCache = x1;
                return x1;
            }
        }
    }
}

