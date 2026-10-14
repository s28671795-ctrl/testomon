/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.standard;

import inet.ipaddr.AddressValueException;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.InconsistentPrefixException;
import inet.ipaddr.format.AddressDivisionGroupingBase;
import inet.ipaddr.format.AddressDivisionSeries;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.IPAddressDivisionSeries;
import inet.ipaddr.format.standard.AddressDivisionGrouping;
import inet.ipaddr.format.standard.IPAddressDivision;
import java.util.Arrays;

public class IPAddressDivisionGrouping
extends AddressDivisionGrouping
implements IPAddressDivisionSeries {
    private static final long serialVersionUID = 4L;
    private final IPAddressNetwork<?, ?, ?, ?, ?> network;
    protected static final RangeCache ZEROS_CACHE = new RangeCache();

    public IPAddressDivisionGrouping(IPAddressDivision[] divisions, IPAddressNetwork<?, ?, ?, ?, ?> network) throws AddressValueException {
        super(divisions);
        if (network == null) {
            throw new NullPointerException(IPAddressDivisionGrouping.getMessage("ipaddress.error.nullNetwork"));
        }
        this.network = network;
        int totalPrefixBits = 0;
        for (int i = 0; i < divisions.length; ++i) {
            IPAddressDivision division = divisions[i];
            Integer divPrefix = division.getDivisionPrefixLength();
            if (divPrefix != null) {
                this.cachedPrefixLength = IPAddressDivisionGrouping.cacheBits(totalPrefixBits + divPrefix);
                ++i;
                while (i < divisions.length) {
                    division = divisions[i];
                    divPrefix = division.getDivisionPrefixLength();
                    if (divPrefix == null || divPrefix != 0) {
                        throw new InconsistentPrefixException((AddressItem)divisions[i - 1], (AddressItem)division, divPrefix);
                    }
                    ++i;
                }
                return;
            }
            totalPrefixBits += division.getBitCount();
        }
        this.cachedPrefixLength = NO_PREFIX_LENGTH;
    }

    protected IPAddressDivisionGrouping(IPAddressDivision[] divisions, boolean checkSegs) {
        super(divisions, checkSegs);
        this.network = this.getNetwork();
        if (this.network == null) {
            throw new NullPointerException(IPAddressDivisionGrouping.getMessage("ipaddress.error.nullNetwork"));
        }
    }

    @Override
    public IPAddressNetwork<?, ?, ?, ?, ?> getNetwork() {
        return this.network;
    }

    @Override
    public IPAddressDivision getDivision(int index) {
        return (IPAddressDivision)super.getDivision(index);
    }

    @Override
    public int isMore(AddressDivisionSeries other) {
        if (!this.isMultiple()) {
            return other.isMultiple() ? -1 : 0;
        }
        if (!other.isMultiple()) {
            return 1;
        }
        if (this.isSinglePrefixBlock() && other.isSinglePrefixBlock()) {
            int bits = this.getBitCount() - this.getPrefixLength();
            int otherBits = other.getBitCount() - other.getPrefixLength();
            return bits - otherBits;
        }
        return this.getCount().compareTo(other.getCount());
    }

    @Override
    public Integer getPrefixLength() {
        return this.getNetworkPrefixLength();
    }

    @Override
    public Integer getNetworkPrefixLength() {
        Integer ret = this.cachedPrefixLength;
        if (ret == null) {
            Integer result = IPAddressDivisionGrouping.calculatePrefix(this);
            if (result != null) {
                this.cachedPrefixLength = result;
                return this.cachedPrefixLength;
            }
            this.cachedPrefixLength = NO_PREFIX_LENGTH;
            return null;
        }
        if (ret.intValue() == NO_PREFIX_LENGTH.intValue()) {
            return null;
        }
        return ret;
    }

    public int getTrailingBitCount(boolean network) {
        int count = this.getDivisionCount();
        if (count == 0) {
            return 0;
        }
        long back = network ? 0L : this.getDivision(0).getMaxValue();
        int bitLen = 0;
        for (int i = count - 1; i >= 0; --i) {
            IPAddressDivision seg = this.getDivision(i);
            long value = seg.getDivisionValue();
            if (value != back) {
                return bitLen + seg.getTrailingBitCount(network);
            }
            bitLen += seg.getBitCount();
        }
        return bitLen;
    }

    public int getLeadingBitCount(boolean network) {
        int count = this.getDivisionCount();
        if (count == 0) {
            return 0;
        }
        long front = network ? this.getDivision(0).getMaxValue() : 0L;
        int prefixLen = 0;
        for (int i = 0; i < count; ++i) {
            IPAddressDivision seg = this.getDivision(i);
            long value = seg.getDivisionValue();
            if (value != front) {
                return prefixLen + seg.getLeadingBitCount(network);
            }
            prefixLen += seg.getBitCount();
        }
        return prefixLen;
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
        return this.containsPrefixBlock(networkPrefixLength);
    }

    @Override
    public boolean containsPrefixBlock(int prefixLength) {
        return IPAddressDivisionGrouping.containsPrefixBlock(this, prefixLength);
    }

    @Override
    public boolean containsSinglePrefixBlock(int prefixLength) {
        return IPAddressDivisionGrouping.containsSinglePrefixBlock(this, prefixLength);
    }

    @Override
    public boolean isSinglePrefixBlock() {
        Integer networkPrefixLength = this.getNetworkPrefixLength();
        if (networkPrefixLength == null) {
            return false;
        }
        return this.containsSinglePrefixBlock(networkPrefixLength);
    }

    @Override
    public Integer getPrefixLengthForSingleBlock() {
        return IPAddressDivisionGrouping.getPrefixLengthForSingleBlock(this);
    }

    public boolean includesZeroHost() {
        Integer networkPrefixLength = this.getNetworkPrefixLength();
        if (networkPrefixLength == null || networkPrefixLength >= this.getBitCount()) {
            return false;
        }
        if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            return true;
        }
        int divCount = this.getDivisionCount();
        for (int i = 0; i < divCount; ++i) {
            IPAddressDivision div = this.getDivision(i);
            Integer segmentPrefixLength = div.getDivisionPrefixLength();
            if (segmentPrefixLength == null) continue;
            long mask = -1L << div.getBitCount() - segmentPrefixLength ^ 0xFFFFFFFFFFFFFFFFL;
            if ((mask & div.getDivisionValue()) != 0L) {
                return false;
            }
            ++i;
            while (i < divCount) {
                div = this.getDivision(i);
                if (!div.includesZero()) {
                    return false;
                }
                ++i;
            }
        }
        return true;
    }

    @Override
    protected boolean isSameGrouping(AddressDivisionGroupingBase other) {
        return other instanceof IPAddressDivisionGrouping && super.isSameGrouping(other);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof IPAddressDivisionGrouping) {
            IPAddressDivisionGrouping other = (IPAddressDivisionGrouping)o;
            return other.isSameGrouping(this);
        }
        return false;
    }

    protected static boolean prefixContains(IPAddressSection first, IPAddressSection other, int otherIndex) {
        int prefixedSection;
        if (otherIndex < 0) {
            return false;
        }
        Integer prefixLength = first.getPrefixLength();
        if (prefixLength == null) {
            prefixedSection = first.getSegmentCount();
            int oIndex = prefixedSection + otherIndex;
            if (oIndex > other.getSegmentCount()) {
                return false;
            }
        } else {
            prefixedSection = IPAddressDivisionGrouping.getNetworkSegmentIndex(prefixLength, first.getBytesPerSegment(), first.getBitsPerSegment());
            if (prefixedSection >= 0) {
                int segPrefixLength;
                IPAddressSegment two;
                int oIndex = prefixedSection + otherIndex;
                if (oIndex >= other.getSegmentCount()) {
                    return false;
                }
                IPAddressSegment one = first.getSegment(prefixedSection);
                if (!one.prefixContains(two = other.getSegment(oIndex), segPrefixLength = IPAddressDivisionGrouping.getPrefixedSegmentPrefixLength(one.getBitCount(), prefixLength, prefixedSection).intValue())) {
                    return false;
                }
            }
        }
        while (--prefixedSection >= 0) {
            IPAddressSegment two;
            IPAddressSegment one = first.getSegment(prefixedSection);
            if (one.contains(two = other.getSegment(prefixedSection + otherIndex))) continue;
            return false;
        }
        return true;
    }

    public RangeList getZeroSegments() {
        return this.getZeroSegments(false);
    }

    public RangeList getZeroRangeSegments() {
        if (this.isPrefixed()) {
            return this.getZeroSegments(true);
        }
        return this.getZeroSegments();
    }

    protected static RangeList getNoZerosRange() {
        return RangeCache.NO_ZEROS;
    }

    protected static RangeList getSingleRange(int index, int len) {
        RangeCache cache = ZEROS_CACHE.addRange(index, -1, len);
        return cache.get();
    }

    protected RangeList getZeroSegments(boolean includeRanges) {
        RangeCache cache = ZEROS_CACHE;
        int divisionCount = this.getDivisionCount();
        boolean isFullRangeHost = !this.getNetwork().getPrefixConfiguration().prefixedSubnetsAreExplicit() && this.isPrefixBlock();
        includeRanges &= isFullRangeHost;
        int currentIndex = -1;
        int lastIndex = -1;
        int currentCount = 0;
        for (int i = 0; i < divisionCount; ++i) {
            boolean isCompressible;
            IPAddressDivision division = this.getDivision(i);
            boolean bl = isCompressible = division.isZero() || includeRanges && division.isPrefixed() && division.isSinglePrefixBlock(0L, division.getDivisionPrefixLength());
            if (isCompressible) {
                if (++currentCount == 1) {
                    currentIndex = i;
                }
                if (i != divisionCount - 1) continue;
                cache = cache.addRange(currentIndex, lastIndex, currentCount);
                lastIndex = currentIndex + currentCount;
                continue;
            }
            if (currentCount <= 0) continue;
            cache = cache.addRange(currentIndex, lastIndex, currentCount);
            lastIndex = currentIndex + currentCount;
            currentCount = 0;
        }
        return cache.get();
    }

    static {
        if (RangeCache.PRELOAD_CACHE) {
            ZEROS_CACHE.preloadCache(-1);
        }
    }

    private static class RangeCache {
        static boolean PRELOAD_CACHE;
        static final int MAX_DIVISION_COUNT = 8;
        static final RangeList NO_ZEROS;
        RangeCache[][] nextRange;
        RangeCache parent;
        RangeList zeroRanges;
        Range range;

        RangeCache() {
            this(null, 8, null);
            this.zeroRanges = NO_ZEROS;
        }

        private RangeCache(RangeCache parent, int potentialZeroOffsets, Range range) {
            if (potentialZeroOffsets > 0) {
                this.nextRange = new RangeCache[potentialZeroOffsets][];
                for (int i = 0; i < potentialZeroOffsets; ++i) {
                    this.nextRange[i] = new RangeCache[potentialZeroOffsets - i];
                }
            }
            this.parent = parent;
            this.range = range;
        }

        private void get(Range[] ranges, int rangesIndex) {
            ranges[--rangesIndex] = this.range;
            if (rangesIndex > 0) {
                this.parent.get(ranges, rangesIndex);
            }
        }

        public RangeList get() {
            RangeList result = this.zeroRanges;
            if (result == null) {
                int depth = 0;
                RangeCache up = this.parent;
                while (up != null) {
                    ++depth;
                    up = up.parent;
                }
                Range[] ranges = new Range[depth];
                if (depth > 0) {
                    ranges[--depth] = this.range;
                    if (depth > 0) {
                        this.parent.get(ranges, depth);
                    }
                }
                this.zeroRanges = result = new RangeList(ranges);
            }
            return result;
        }

        void preloadCache(int lastIndex) {
            if (this.nextRange != null) {
                int j;
                RangeCache[] next;
                int i;
                for (i = 0; i < this.nextRange.length; ++i) {
                    next = this.nextRange[i];
                    for (j = 0; j < next.length; ++j) {
                        Range newRange = lastIndex == -1 ? new Range(i + lastIndex + 1, j + 1) : IPAddressDivisionGrouping.ZEROS_CACHE.nextRange[i + lastIndex + 1][j].range;
                        int nextPotentialZeroIndex = i + lastIndex + j + 3;
                        int remainingPotentialZeroOffsets = 8 - nextPotentialZeroIndex;
                        RangeCache newRangeCache = new RangeCache(this, remainingPotentialZeroOffsets, newRange);
                        newRangeCache.get();
                        next[j] = newRangeCache;
                    }
                }
                for (i = 0; i < this.nextRange.length; ++i) {
                    next = this.nextRange[i];
                    for (j = 0; j < next.length; ++j) {
                        RangeCache nextCache = next[j];
                        Range nextRange = nextCache.range;
                        nextCache.preloadCache(nextRange.index + nextRange.length);
                    }
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public RangeCache addRange(int currentIndex, int lastIndex, int currentCount) {
            int offset = currentIndex - lastIndex;
            int cacheOffset = offset - 1;
            int cacheCount = currentCount - 1;
            RangeCache next = this.nextRange[cacheOffset][cacheCount];
            if (next == null) {
                RangeCache rangeCache = this;
                synchronized (rangeCache) {
                    next = this.nextRange[cacheOffset][cacheCount];
                    if (next == null) {
                        Range newRange;
                        int nextPotentialZeroIndex = lastIndex + 1;
                        int remainingPotentialZeroOffsets = 8 - nextPotentialZeroIndex;
                        if (this == ZEROS_CACHE) {
                            newRange = new Range(currentIndex, currentCount);
                        } else {
                            RangeCache rootNext = IPAddressDivisionGrouping.ZEROS_CACHE.nextRange[currentIndex][currentCount - 1];
                            if (rootNext == null) {
                                newRange = new Range(currentIndex, currentCount);
                                IPAddressDivisionGrouping.ZEROS_CACHE.nextRange[currentIndex][currentCount - 1] = new RangeCache(ZEROS_CACHE, 8, newRange);
                            } else {
                                newRange = rootNext.range;
                            }
                        }
                        this.nextRange[cacheOffset][cacheCount] = next = new RangeCache(this, remainingPotentialZeroOffsets, newRange);
                    }
                }
            }
            return next;
        }

        static {
            NO_ZEROS = new RangeList(new Range[0]);
        }
    }

    public static class RangeList {
        final Range[] ranges;

        RangeList(Range[] ranges) {
            if (ranges == null) {
                throw new NullPointerException();
            }
            this.ranges = ranges;
        }

        public int size() {
            return this.ranges.length;
        }

        public Range getRange(int index) {
            return this.ranges[index];
        }

        public String toString() {
            return Arrays.asList(this.ranges).toString();
        }
    }

    public static class Range {
        public final int index;
        public final int length;

        Range(int index, int length) {
            this.index = index;
            this.length = length;
        }

        public String toString() {
            return "[" + this.index + ',' + (this.index + this.length) + ']';
        }
    }
}

