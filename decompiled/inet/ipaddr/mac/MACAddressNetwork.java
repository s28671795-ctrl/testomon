/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.mac;

import inet.ipaddr.Address;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.standard.AddressCreator;
import inet.ipaddr.mac.MACAddress;
import inet.ipaddr.mac.MACAddressSection;
import inet.ipaddr.mac.MACAddressSegment;
import java.io.Serializable;

public class MACAddressNetwork
extends AddressNetwork<MACAddressSegment> {
    private static final long serialVersionUID = 4L;
    private static AddressNetwork.PrefixConfiguration defaultPrefixConfiguration = AddressNetwork.getDefaultPrefixConfiguration();
    private static final MACAddressSegment[] EMPTY_SEGMENTS = new MACAddressSegment[0];
    private MACAddressCreator creator = this.createAddressCreator();

    protected MACAddressCreator createAddressCreator() {
        return new MACAddressCreator(this);
    }

    public MACAddressCreator getAddressCreator() {
        return this.creator;
    }

    @Override
    protected boolean isCompatible(AddressNetwork<?> other) {
        return super.isCompatible(other);
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

    public static class MACAddressCreator
    extends AddressCreator<MACAddress, MACAddressSection, MACAddressSection, MACAddressSegment>
    implements AddressNetwork.AddressSegmentCreator<MACAddressSegment> {
        private static final long serialVersionUID = 4L;
        Cache cache;
        private final MACAddressNetwork owner;

        MACAddressCreator(MACAddressNetwork owner) {
            this.owner = owner;
            this.cache = new Cache();
        }

        MACAddressCreator(MACAddressNetwork owner, Cache cache) {
            this.owner = owner;
            this.cache = cache;
        }

        @Override
        public void clearCaches() {
            super.clearCaches();
            this.cache.clear();
        }

        public MACAddressNetwork getNetwork() {
            return this.owner;
        }

        @Override
        public int getMaxValuePerSegment() {
            return 255;
        }

        public MACAddressSegment[] createSegmentArray(int length) {
            if (length == 0) {
                return EMPTY_SEGMENTS;
            }
            return new MACAddressSegment[length];
        }

        @Override
        public MACAddressSegment createSegment(int value) {
            if (value >= 0 && value <= 255) {
                MACAddressSegment result;
                MACAddressSegment[] cache = this.cache.segmentCache;
                if (cache == null) {
                    cache = new MACAddressSegment[256];
                    Cache.access$102(this.cache, cache);
                    cache[value] = result = new MACAddressSegment(value);
                } else {
                    result = cache[value];
                    if (result == null) {
                        cache[value] = result = new MACAddressSegment(value);
                    }
                }
                return result;
            }
            return new MACAddressSegment(value);
        }

        @Override
        public MACAddressSegment createSegment(int value, Integer segmentPrefixLength) {
            if (segmentPrefixLength != null) {
                if (segmentPrefixLength < 0) {
                    throw new PrefixLenException(segmentPrefixLength);
                }
                if (segmentPrefixLength > 64) {
                    throw new PrefixLenException(segmentPrefixLength);
                }
                if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                    if (segmentPrefixLength == 0) {
                        MACAddressSegment result = this.cache.ALL_RANGE_SEGMENT;
                        if (result == null) {
                            result = new MACAddressSegment(0, 255);
                            this.cache.ALL_RANGE_SEGMENT = result;
                        }
                        return result;
                    }
                    int mask = -1 << 8 - segmentPrefixLength;
                    int newLower = value & mask;
                    int newUpper = value | ~mask;
                    return this.createRangeSegment(newLower, newUpper);
                }
            }
            return this.createSegment(value);
        }

        public MACAddressSegment createRangeSegment(int lower, int upper) {
            if (lower != upper) {
                if (lower == 0 && upper == 255) {
                    MACAddressSegment result = this.cache.ALL_RANGE_SEGMENT;
                    if (result == null) {
                        result = new MACAddressSegment(0, upper);
                        this.cache.ALL_RANGE_SEGMENT = result;
                    }
                    return result;
                }
                return new MACAddressSegment(lower, upper);
            }
            return this.createSegment(lower);
        }

        @Override
        public MACAddressSegment createSegment(int lower, int upper, Integer segmentPrefixLength) {
            if (segmentPrefixLength == null) {
                return this.createRangeSegment(lower, upper);
            }
            if (segmentPrefixLength < 0) {
                throw new PrefixLenException(segmentPrefixLength);
            }
            if (segmentPrefixLength > 64) {
                throw new PrefixLenException(segmentPrefixLength);
            }
            if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                if (segmentPrefixLength == 0) {
                    MACAddressSegment result = this.cache.ALL_RANGE_SEGMENT;
                    if (result == null) {
                        result = new MACAddressSegment(0, 255);
                        this.cache.ALL_RANGE_SEGMENT = result;
                    }
                    return result;
                }
                int max = 255;
                int mask = -1 << 8 - segmentPrefixLength & max;
                int newLower = lower & mask;
                int newUpper = upper | ~mask & max;
                return this.createRangeSegment(newLower, newUpper);
            }
            return this.createRangeSegment(lower, upper);
        }

        @Override
        protected MACAddressSegment createSegmentInternal(int value, Integer segmentPrefixLength, CharSequence addressStr, int originalVal, boolean isStandardString, int lowerStringStartIndex, int lowerStringEndIndex) {
            MACAddressSegment segment = this.createSegment(value, segmentPrefixLength);
            segment.setString(addressStr, isStandardString, lowerStringStartIndex, lowerStringEndIndex, originalVal);
            return segment;
        }

        @Override
        protected MACAddressSegment createRangeSegmentInternal(int lower, int upper, Integer segmentPrefixLength, CharSequence addressStr, int originalLower, int originalUpper, boolean isStandardString, boolean isStandardRangeString, int lowerStringStartIndex, int lowerStringEndIndex, int upperStringEndIndex) {
            MACAddressSegment segment = this.createSegment(lower, upper, segmentPrefixLength);
            segment.setString(addressStr, isStandardRangeString, lowerStringStartIndex, upperStringEndIndex, originalLower, originalUpper);
            return segment;
        }

        MACAddressSection createSection(long bytes, int startIndex, boolean extended, Integer prefixLength) {
            MACAddressSection result = new MACAddressSection(bytes, startIndex, extended);
            result.assignPrefixLength(prefixLength);
            return result;
        }

        MACAddressSection createSection(long bytes, int startIndex, boolean extended) {
            return new MACAddressSection(bytes, startIndex, extended);
        }

        MACAddressSection createSection(byte[] bytes, int startIndex, boolean extended, Integer prefixLength) {
            MACAddressSection result = new MACAddressSection(bytes, startIndex, extended);
            result.assignPrefixLength(prefixLength);
            return result;
        }

        MACAddressSection createSection(byte[] bytes, int startIndex, boolean extended) {
            return new MACAddressSection(bytes, startIndex, extended);
        }

        MACAddressSection createSection(byte[] bytes, int startIndex, int segmentCount, boolean extended, Integer prefixLength) {
            MACAddressSection result = new MACAddressSection(bytes, 0, bytes.length, segmentCount, startIndex, extended, true);
            result.assignPrefixLength(prefixLength);
            return result;
        }

        MACAddressSection createSection(byte[] bytes, int startIndex, int segmentCount, boolean extended) {
            return new MACAddressSection(bytes, 0, bytes.length, segmentCount, startIndex, extended, true);
        }

        MACAddressSection createSection(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, int startIndex, boolean extended, Integer prefixLength) {
            MACAddressSection result = new MACAddressSection(lowerValueProvider, upperValueProvider, startIndex, extended);
            result.assignPrefixLength(prefixLength);
            return result;
        }

        MACAddressSection createSection(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, int startIndex, boolean extended) {
            return new MACAddressSection(lowerValueProvider, upperValueProvider, startIndex, extended);
        }

        protected MACAddressSection createSectionInternal(MACAddressSegment[] segments) {
            return new MACAddressSection(false, segments, 0, segments.length > 6);
        }

        protected MACAddressSection createPrefixedSectionInternal(MACAddressSegment[] segments, Integer prefixLength, boolean singleOnly) {
            return this.createPrefixedSectionInternal(segments, prefixLength);
        }

        protected MACAddressSection createPrefixedSectionInternal(MACAddressSegment[] segments, Integer prefixLength) {
            MACAddressSection result = new MACAddressSection(false, segments, 0, segments.length > 6);
            result.assignPrefixLength(prefixLength);
            return result;
        }

        protected MACAddressSection createSectionInternal(MACAddressSegment[] segments, boolean extended) {
            return new MACAddressSection(false, segments, 0, extended);
        }

        protected MACAddressSection createSectionInternal(MACAddressSegment[] segments, int startIndex, boolean extended) {
            return new MACAddressSection(false, segments, startIndex, extended);
        }

        MACAddressSection createSection(MACAddressSegment[] segments, boolean extended) {
            return new MACAddressSection(segments, 0, extended);
        }

        MACAddressSection createSection(MACAddressSegment[] segments, boolean extended, Integer prefixLength) {
            MACAddressSection result = new MACAddressSection(segments, 0, extended);
            result.assignPrefixLength(prefixLength);
            return result;
        }

        @Override
        protected MACAddressSection createSectionInternal(byte[] bytes, int segmentCount, Integer prefixLength, boolean singleOnly) {
            MACAddressSection result = new MACAddressSection(bytes, segmentCount, 0, segmentCount > 6, false);
            result.assignPrefixLength(prefixLength);
            return result;
        }

        @Override
        protected MACAddress createAddressInternal(byte[] bytes, CharSequence zone) {
            MACAddressSection section = new MACAddressSection(bytes, bytes.length, 0, bytes.length > 6, false);
            return this.createAddress(section);
        }

        protected MACAddress createAddressInternal(MACAddressSegment[] segments) {
            return this.createAddress(this.createSectionInternal(segments));
        }

        protected MACAddress createAddressInternal(MACAddressSegment[] segments, Integer prefix) {
            return this.createAddress(this.createPrefixedSectionInternal(segments, prefix));
        }

        protected MACAddress createAddressInternal(MACAddressSegment[] segments, Integer prefix, boolean singleOnly) {
            return this.createAddressInternal(segments, prefix);
        }

        @Override
        protected MACAddress createAddressInternal(MACAddressSection section, HostIdentifierString from) {
            MACAddress result = this.createAddress(section);
            result.cache(from);
            return result;
        }

        @Override
        protected MACAddress createAddressInternal(MACAddressSection section, CharSequence zone, HostIdentifierString from) {
            MACAddress result = this.createAddress(section);
            result.cache(from);
            return result;
        }

        @Override
        protected MACAddress createAddressInternal(MACAddressSection section, CharSequence zone, HostIdentifierString from, MACAddress lower, MACAddress upper) {
            MACAddress result = this.createAddressInternal(section, zone, from);
            return result;
        }

        @Override
        public MACAddress createAddress(MACAddressSection section) {
            return new MACAddress(section);
        }

        static class Cache
        implements Serializable {
            private static final long serialVersionUID = 1L;
            private transient MACAddressSegment ALL_RANGE_SEGMENT;
            private transient MACAddressSegment[] segmentCache;

            Cache() {
            }

            void clear() {
                this.segmentCache = null;
                this.ALL_RANGE_SEGMENT = null;
            }

            static /* synthetic */ MACAddressSegment[] access$102(Cache x0, MACAddressSegment[] x1) {
                x0.segmentCache = x1;
                return x1;
            }
        }
    }
}

