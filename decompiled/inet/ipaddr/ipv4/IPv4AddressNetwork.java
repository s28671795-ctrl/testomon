/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv4;

import inet.ipaddr.Address;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.ipv4.IPv4Address;
import inet.ipaddr.ipv4.IPv4AddressSection;
import inet.ipaddr.ipv4.IPv4AddressSegment;
import java.io.Serializable;
import java.net.Inet4Address;
import java.util.function.BiFunction;
import java.util.function.Function;

public class IPv4AddressNetwork
extends IPAddressNetwork<IPv4Address, IPv4AddressSection, IPv4AddressSection, IPv4AddressSegment, Inet4Address> {
    private static final long serialVersionUID = 4L;
    private static AddressNetwork.PrefixConfiguration defaultPrefixConfiguration = AddressNetwork.getDefaultPrefixConfiguration();
    private static boolean CACHE_SEGMENTS_BY_PREFIX = true;
    private static final IPv4AddressSegment[] EMPTY_SEGMENTS = new IPv4AddressSegment[0];
    private static final IPv4AddressSection[] EMPTY_SECTION = new IPv4AddressSection[0];
    private static final IPv4Address[] EMPTY_ADDRESS = new IPv4Address[0];

    public IPv4AddressNetwork() {
        super(IPv4Address.class);
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

    protected boolean isCompatible(IPv4AddressNetwork other) {
        return super.isCompatible(other);
    }

    @Override
    protected BiFunction<IPv4Address, Integer, IPv4AddressSegment> getSegmentProducer() {
        return (address, index) -> address.getSegment((int)index);
    }

    @Override
    protected Function<IPv4Address, IPv4AddressSection> getSectionProducer() {
        return IPv4Address::getSection;
    }

    protected IPv4AddressCreator createAddressCreator() {
        return new IPv4AddressCreator(this);
    }

    @Override
    protected IPv4Address createLoopback() {
        IPv4AddressCreator creator = this.getAddressCreator();
        IPv4AddressSegment zero = creator.createSegment(0);
        IPv4AddressSegment[] segs = creator.createSegmentArray(4);
        segs[0] = creator.createSegment(127);
        segs[1] = segs[2] = zero;
        segs[3] = creator.createSegment(1);
        return creator.createAddressInternal(segs);
    }

    @Override
    public IPv4AddressCreator getAddressCreator() {
        return (IPv4AddressCreator)super.getAddressCreator();
    }

    @Override
    public boolean isIPv4() {
        return true;
    }

    @Override
    public IPAddress.IPVersion getIPVersion() {
        return IPAddress.IPVersion.IPV4;
    }

    public static class IPv4AddressCreator
    extends IPAddressNetwork.IPAddressCreator<IPv4Address, IPv4AddressSection, IPv4AddressSection, IPv4AddressSegment, Inet4Address> {
        private static final long serialVersionUID = 4L;
        Cache cache;
        boolean useSegmentCache = true;

        public IPv4AddressCreator(IPv4AddressNetwork network) {
            super(network);
            this.cache = new Cache();
        }

        protected IPv4AddressCreator(IPv4AddressNetwork network, Cache cache) {
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
        public IPv4AddressNetwork getNetwork() {
            return (IPv4AddressNetwork)super.getNetwork();
        }

        @Override
        public int getMaxValuePerSegment() {
            return 255;
        }

        @Override
        protected int getAddressSegmentCount() {
            return 4;
        }

        public IPv4AddressSegment[] createSegmentArray(int length) {
            if (length == 0) {
                return EMPTY_SEGMENTS;
            }
            return new IPv4AddressSegment[length];
        }

        @Override
        public IPv4AddressSegment createSegment(int value) {
            if (this.useSegmentCache && value >= 0 && value <= 255) {
                IPv4AddressSegment result;
                IPv4AddressSegment[] cache = this.cache.segmentCache;
                if (cache == null) {
                    cache = new IPv4AddressSegment[256];
                    Cache.access$102(this.cache, cache);
                    cache[value] = result = new IPv4AddressSegment(value);
                } else {
                    result = cache[value];
                    if (result == null) {
                        cache[value] = result = new IPv4AddressSegment(value);
                    }
                }
                return result;
            }
            return new IPv4AddressSegment(value);
        }

        @Override
        public IPv4AddressSegment createSegment(int value, Integer segmentPrefixLength) {
            if (segmentPrefixLength == null) {
                return this.createSegment(value);
            }
            if (this.useSegmentCache && value >= 0 && value <= 255 && segmentPrefixLength >= 0 && segmentPrefixLength <= 32) {
                if (segmentPrefixLength == 0 && this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                    IPv4AddressSegment result = this.cache.ZERO_PREFIX_SEGMENT;
                    if (result == null) {
                        result = new IPv4AddressSegment(0, 0);
                        this.cache.ZERO_PREFIX_SEGMENT = result;
                    }
                    return result;
                }
                if (CACHE_SEGMENTS_BY_PREFIX) {
                    IPv4AddressSegment result;
                    int mask = this.getNetwork().getSegmentNetworkMask(segmentPrefixLength);
                    int prefixIndex = segmentPrefixLength;
                    boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
                    int valueIndex = isAllSubnets ? (value &= mask) >>> 8 - segmentPrefixLength : value;
                    IPv4AddressSegment[][] cache = this.cache.segmentPrefixCache;
                    if (cache == null) {
                        cache = new IPv4AddressSegment[9][];
                        Cache.access$402(this.cache, cache);
                        IPv4AddressSegment[] block = new IPv4AddressSegment[isAllSubnets ? 1 << prefixIndex : 256];
                        cache[prefixIndex] = block;
                        block[valueIndex] = result = new IPv4AddressSegment(value, segmentPrefixLength);
                    } else {
                        IPv4AddressSegment[] block = cache[prefixIndex];
                        if (block == null) {
                            block = new IPv4AddressSegment[isAllSubnets ? 1 << prefixIndex : 256];
                            cache[prefixIndex] = block;
                            block[valueIndex] = result = new IPv4AddressSegment(value, segmentPrefixLength);
                        } else {
                            result = block[valueIndex];
                            if (result == null) {
                                block[valueIndex] = result = new IPv4AddressSegment(value, segmentPrefixLength);
                            }
                        }
                    }
                    return result;
                }
            }
            IPv4AddressSegment result = new IPv4AddressSegment(value, segmentPrefixLength);
            return result;
        }

        @Override
        public IPv4AddressSegment createSegment(int lower, int upper, Integer segmentPrefixLength) {
            if (segmentPrefixLength == null) {
                if (lower == upper) {
                    return this.createSegment(lower);
                }
                if (this.useSegmentCache && lower == 0 && upper == 255) {
                    IPv4AddressSegment result = this.cache.ALL_RANGE_SEGMENT;
                    if (result == null) {
                        result = new IPv4AddressSegment(0, 255, null);
                        this.cache.ALL_RANGE_SEGMENT = result;
                    }
                    return result;
                }
            } else {
                if (lower == upper) {
                    return this.createSegment(lower, segmentPrefixLength);
                }
                if (this.useSegmentCache && lower >= 0 && lower <= 255 && upper >= 0 && upper <= 255 && segmentPrefixLength >= 0 && segmentPrefixLength <= 32) {
                    if (segmentPrefixLength == 0 && this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                        return this.createSegment(0, IPv4AddressNetwork.cacheBits(0));
                    }
                    if (CACHE_SEGMENTS_BY_PREFIX) {
                        int bitsPerSegment = 8;
                        if (segmentPrefixLength > bitsPerSegment) {
                            segmentPrefixLength = bitsPerSegment;
                        }
                        if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                            int mask = this.getNetwork().getSegmentNetworkMask(segmentPrefixLength);
                            if ((upper & mask) == (lower &= mask)) {
                                return this.createSegment(lower, segmentPrefixLength);
                            }
                            if (lower == 0 && upper >= mask) {
                                IPv4AddressSegment result;
                                int prefixIndex = segmentPrefixLength;
                                IPv4AddressSegment[] cache = this.cache.allPrefixedCache;
                                if (cache == null) {
                                    cache = new IPv4AddressSegment[9];
                                    Cache.access$702(this.cache, cache);
                                    cache[prefixIndex] = result = new IPv4AddressSegment(0, 255, segmentPrefixLength);
                                } else {
                                    result = cache[prefixIndex];
                                    if (result == null) {
                                        cache[prefixIndex] = result = new IPv4AddressSegment(0, 255, segmentPrefixLength);
                                    }
                                }
                                return result;
                            }
                        } else if (lower == 0 && upper == 255) {
                            IPv4AddressSegment result;
                            int prefixIndex = segmentPrefixLength;
                            IPv4AddressSegment[] cache = this.cache.allPrefixedCache;
                            if (cache == null) {
                                cache = new IPv4AddressSegment[9];
                                Cache.access$702(this.cache, cache);
                                cache[prefixIndex] = result = new IPv4AddressSegment(0, 255, segmentPrefixLength);
                            } else {
                                result = cache[prefixIndex];
                                if (result == null) {
                                    cache[prefixIndex] = result = new IPv4AddressSegment(0, 255, segmentPrefixLength);
                                }
                            }
                            return result;
                        }
                    }
                }
            }
            IPv4AddressSegment result = new IPv4AddressSegment(lower, upper, segmentPrefixLength);
            return result;
        }

        protected IPv4AddressSection[] createSectionArray(int length) {
            if (length == 0) {
                return EMPTY_SECTION;
            }
            return new IPv4AddressSection[length];
        }

        protected IPv4AddressSection createSectionInternal(IPv4AddressSegment[] segments) {
            return new IPv4AddressSection(segments, false);
        }

        protected IPv4AddressSection createPrefixedSectionInternal(IPv4AddressSegment[] segments, Integer prefix, boolean singleOnly) {
            return new IPv4AddressSection(segments, false, prefix, singleOnly);
        }

        protected IPv4AddressSection createSectionInternal(int value) {
            return new IPv4AddressSection(value);
        }

        protected IPv4AddressSection createSectionInternal(int value, Integer prefix) {
            return new IPv4AddressSection(value, prefix);
        }

        @Override
        public IPv4AddressSection createFullSectionInternal(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, Integer prefix) {
            return new IPv4AddressSection(lowerValueProvider, upperValueProvider, 4, prefix);
        }

        @Override
        protected IPv4AddressSection createSectionInternal(byte[] bytes, int segmentCount, Integer prefix, boolean singleOnly) {
            return new IPv4AddressSection(bytes, segmentCount, prefix, false, singleOnly);
        }

        protected IPv4AddressSection createSectionInternal(IPv4AddressSegment[] segments, int startIndex, boolean extended) {
            return new IPv4AddressSection(segments);
        }

        @Override
        public IPv4AddressSection createSection(byte[] bytes, int byteStartIndex, int byteEndIndex, Integer prefix) {
            return new IPv4AddressSection(bytes, byteStartIndex, byteEndIndex, -1, prefix, true, false);
        }

        @Override
        public IPv4AddressSection createSection(byte[] bytes, Integer prefix) {
            return new IPv4AddressSection(bytes, prefix);
        }

        @Override
        protected IPv4AddressSection createSection(byte[] bytes, int byteStartIndex, int byteEndIndex, int segmentCount, Integer prefix) {
            return new IPv4AddressSection(bytes, byteStartIndex, byteEndIndex, segmentCount, prefix);
        }

        public IPv4AddressSection createSection(IPv4AddressSegment[] segments, Integer networkPrefixLength) {
            return new IPv4AddressSection(segments, networkPrefixLength);
        }

        public IPv4AddressSection createSection(IPv4AddressSegment[] segments) {
            return new IPv4AddressSection(segments);
        }

        protected IPv4AddressSection createEmbeddedSectionInternal(IPAddressSection encompassingSection, IPv4AddressSegment[] segments) {
            return new IPv4AddressSection.EmbeddedIPv4AddressSection(encompassingSection, segments);
        }

        protected IPv4Address[] createAddressArray(int length) {
            if (length == 0) {
                return EMPTY_ADDRESS;
            }
            return new IPv4Address[length];
        }

        protected IPv4Address createAddressInternal(IPv4AddressSegment[] segments) {
            return (IPv4Address)super.createAddressInternal((IPAddressSegment[])segments);
        }

        @Override
        protected IPv4Address createAddressInternal(IPv4AddressSection section, CharSequence zone, HostIdentifierString from, IPv4Address lower, IPv4Address upper) {
            IPv4Address result = (IPv4Address)this.createAddressInternal(section, from);
            result.cache(lower, upper);
            return result;
        }

        @Override
        protected IPv4Address createAddressInternal(IPv4AddressSection section, CharSequence zone) {
            return this.createAddress(section);
        }

        @Override
        public IPv4Address createAddress(IPv4AddressSection section) {
            return new IPv4Address(section);
        }

        @Override
        public IPv4Address createAddress(Inet4Address addr, Integer networkPrefixLength) {
            return new IPv4Address(addr, networkPrefixLength);
        }

        @Override
        public IPv4Address createAddress(Inet4Address addr) {
            return new IPv4Address(addr);
        }

        protected static class Cache
        implements Serializable {
            private static final long serialVersionUID = 1L;
            private transient IPv4AddressSegment ZERO_PREFIX_SEGMENT;
            private transient IPv4AddressSegment ALL_RANGE_SEGMENT;
            private transient IPv4AddressSegment[] segmentCache;
            private transient IPv4AddressSegment[][] segmentPrefixCache;
            private transient IPv4AddressSegment[] allPrefixedCache;

            protected Cache() {
            }

            void clear() {
                this.segmentCache = null;
                this.allPrefixedCache = null;
                this.segmentPrefixCache = null;
                this.ZERO_PREFIX_SEGMENT = null;
                this.ALL_RANGE_SEGMENT = null;
            }

            static /* synthetic */ IPv4AddressSegment[] access$102(Cache x0, IPv4AddressSegment[] x1) {
                x0.segmentCache = x1;
                return x1;
            }

            static /* synthetic */ IPv4AddressSegment[][] access$402(Cache x0, IPv4AddressSegment[][] x1) {
                x0.segmentPrefixCache = x1;
                return x1;
            }

            static /* synthetic */ IPv4AddressSegment[] access$702(Cache x0, IPv4AddressSegment[] x1) {
                x0.allPrefixedCache = x1;
                return x1;
            }
        }
    }
}

