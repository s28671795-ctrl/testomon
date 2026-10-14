/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.Address;
import inet.ipaddr.AddressComparator;
import inet.ipaddr.AddressConversionException;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressSection;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.HostIdentifierException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.IPAddressSegmentSeries;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.InconsistentPrefixException;
import inet.ipaddr.NetworkMismatchException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.SizeMismatchException;
import inet.ipaddr.format.AddressComponentRange;
import inet.ipaddr.format.AddressDivisionGroupingBase;
import inet.ipaddr.format.AddressDivisionSeries;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.standard.AddressCreator;
import inet.ipaddr.format.standard.AddressDivision;
import inet.ipaddr.format.standard.AddressDivisionGrouping;
import inet.ipaddr.format.standard.IPAddressBitsDivision;
import inet.ipaddr.format.standard.IPAddressDivision;
import inet.ipaddr.format.standard.IPAddressDivisionGrouping;
import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.format.util.IPAddressPartConfiguredString;
import inet.ipaddr.format.util.IPAddressPartStringCollection;
import inet.ipaddr.format.util.sql.IPAddressSQLTranslator;
import inet.ipaddr.format.util.sql.MySQLTranslator;
import inet.ipaddr.format.util.sql.SQLStringMatcher;
import inet.ipaddr.format.validate.ParsedAddressGrouping;
import inet.ipaddr.format.validate.ParsedIPAddress;
import inet.ipaddr.ipv6.IPv6AddressSegment;
import java.lang.invoke.LambdaMetafactory;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public abstract class IPAddressSection
extends IPAddressDivisionGrouping
implements IPAddressSegmentSeries,
AddressSection {
    private static final long serialVersionUID = 4L;
    private static final IPAddressStringDivisionSeries[] EMPTY_PARTS = new IPAddressStringDivisionSeries[0];
    private transient PrefixCache prefixCache;
    private transient BigInteger cachedNonzeroHostCount;
    static final Comparator<? super IPAddressSegmentSeries> mergeListComparator = (one, two) -> {
        int comparison;
        Integer prefix2;
        Integer prefix1 = one.getPrefixLength();
        int n = prefix1 == (prefix2 = two.getPrefixLength()) ? 0 : (prefix1 == null ? -1 : (comparison = prefix2 == null ? 1 : prefix2.compareTo(prefix1)));
        if (comparison != 0) {
            return comparison;
        }
        if (prefix1 == null || prefix1 != 0) {
            int twoValue;
            int oneValue;
            IPAddressSegment segTwo;
            IPAddressSegment segOne;
            int i;
            int networkSegIndex = prefix1 == null ? one.getSegmentCount() - 1 : IPAddressSection.getNetworkSegmentIndex(prefix1, one.getBytesPerSegment(), one.getBitsPerSegment());
            int hostSegIndex = prefix1 == null ? one.getSegmentCount() : IPAddressSection.getHostSegmentIndex(prefix1, one.getBytesPerSegment(), one.getBitsPerSegment());
            for (i = 0; i < hostSegIndex; ++i) {
                int twoUpperValue;
                segOne = one.getSegment(i);
                segTwo = two.getSegment(i);
                oneValue = segOne.getSegmentValue();
                twoValue = segTwo.getSegmentValue();
                int oneUpperValue = segOne.getUpperSegmentValue();
                comparison = oneUpperValue - oneValue - ((twoUpperValue = segTwo.getUpperSegmentValue()) - twoValue);
                if (comparison == 0) continue;
                return comparison;
            }
            for (i = 0; i <= networkSegIndex; ++i) {
                segOne = one.getSegment(i);
                segTwo = two.getSegment(i);
                oneValue = segOne.getSegmentValue();
                comparison = oneValue - (twoValue = segTwo.getSegmentValue());
                if (comparison == 0) continue;
                return comparison;
            }
        }
        return comparison;
    };
    private static final AddressComparator.ValueComparator REVERSE_LOW_COMPARATOR = new AddressComparator.ValueComparator(true, false, true);
    private static final AddressComparator.ValueComparator REVERSE_HIGH_COMPARATOR = new AddressComparator.ValueComparator(true, true, true);

    protected IPAddressSection(IPAddressSegment[] segments, boolean cloneSegments, boolean checkSegs) {
        super(cloneSegments ? (IPAddressDivision[])segments.clone() : segments, false);
        if (checkSegs) {
            AddressNetwork network = this.getNetwork();
            Integer previousSegmentPrefix = null;
            int bitsPerSegment = this.getBitsPerSegment();
            for (int i = 0; i < segments.length; ++i) {
                IPAddressSegment segment = segments[i];
                if (!network.isCompatible(segment.getNetwork())) {
                    throw new NetworkMismatchException(segment);
                }
                Integer segPrefix = segment.getSegmentPrefixLength();
                if (previousSegmentPrefix == null) {
                    if (segPrefix != null) {
                        this.cachedPrefixLength = IPAddressSection.cacheBits(IPAddressSection.getNetworkPrefixLength(bitsPerSegment, segPrefix, i));
                    }
                } else if (segPrefix == null || segPrefix != 0) {
                    throw new InconsistentPrefixException((AddressItem)segments[i - 1], (AddressItem)segment, segPrefix);
                }
                previousSegmentPrefix = segPrefix;
            }
            if (previousSegmentPrefix == null) {
                this.cachedPrefixLength = NO_PREFIX_LENGTH;
            }
        }
    }

    protected void checkSegments(IPv6AddressSegment[] segs) {
        AddressNetwork network = this.getNetwork();
        for (IPv6AddressSegment seg : segs) {
            if (network.isCompatible(((IPAddressSegment)seg).getNetwork())) continue;
            throw new NetworkMismatchException(seg);
        }
    }

    protected static String getMessage(String key) {
        return HostIdentifierException.getMessage(key);
    }

    protected void initCachedValues(Integer prefixLen, boolean network, Integer cachedNetworkPrefix, Integer cachedMinPrefix, Integer cachedEquivalentPrefix, BigInteger cachedCount, IPAddressDivisionGrouping.RangeList zeroSegments, IPAddressDivisionGrouping.RangeList zeroRanges) {
        if (this.prefixCache == null) {
            this.prefixCache = new PrefixCache();
        }
        if (network) {
            this.setNetworkMaskPrefix(prefixLen);
        } else {
            this.setHostMaskPrefix(prefixLen);
        }
        super.initCachedValues(cachedNetworkPrefix, cachedCount);
        this.prefixCache.cachedMinPrefix = cachedMinPrefix;
        this.prefixCache.cachedIsSinglePrefixBlock = Objects.equals(cachedEquivalentPrefix, cachedNetworkPrefix);
        this.prefixCache.cachedEquivalentPrefix = cachedEquivalentPrefix;
    }

    @Override
    public boolean isSinglePrefixBlock() {
        if (!this.hasNoPrefixCache() && this.prefixCache.cachedIsSinglePrefixBlock != null) {
            return this.prefixCache.cachedIsSinglePrefixBlock;
        }
        boolean result = super.isSinglePrefixBlock();
        this.prefixCache.cachedIsSinglePrefixBlock = result;
        if (result) {
            this.prefixCache.cachedEquivalentPrefix = this.getNetworkPrefixLength();
        }
        return result;
    }

    protected static IPAddressDivisionGrouping.RangeList getNoZerosRange() {
        return IPAddressDivisionGrouping.getNoZerosRange();
    }

    protected static IPAddressDivisionGrouping.RangeList getSingleRange(int index, int len) {
        return IPAddressDivisionGrouping.getSingleRange(index, len);
    }

    protected static boolean isCompatibleNetworks(AddressNetwork<?> one, AddressNetwork<?> two) {
        return AddressDivisionGrouping.isCompatibleNetworks(one, two);
    }

    @Override
    public int getBitCount() {
        return this.getSegmentCount() * this.getBitsPerSegment();
    }

    @Override
    public int getByteCount() {
        return this.getSegmentCount() * this.getBytesPerSegment();
    }

    @Override
    protected byte[] getBytesInternal() {
        return super.getBytesInternal();
    }

    public static int bitsPerSegment(IPAddress.IPVersion version) {
        return IPAddressSegment.getBitCount(version);
    }

    public static int bytesPerSegment(IPAddress.IPVersion version) {
        return IPAddressSegment.getBitCount(version);
    }

    protected long longCount(int segCount) {
        if (this.isMultiple()) {
            return IPAddressSection.longCount(this, segCount);
        }
        return 1L;
    }

    protected long longPrefixCount(int prefixLength) {
        if (this.isMultiple()) {
            return IPAddressSection.longPrefixCount(this, prefixLength);
        }
        return 1L;
    }

    protected long longZeroHostCount(int prefixLength, int segCount) {
        if (this.includesZeroHost(prefixLength)) {
            if (this.isMultiple()) {
                int bitsPerSegment = this.getBitsPerSegment();
                int prefixedSegment = IPAddressSection.getNetworkSegmentIndex(prefixLength, this.getBytesPerSegment(), bitsPerSegment);
                long zeroHostCount = IPAddressSection.getLongCount(i -> {
                    if (i == prefixedSegment) {
                        IPAddressSegment seg = this.getSegment(i);
                        int shift = seg.getBitCount() - IPAddressSection.getPrefixedSegmentPrefixLength(bitsPerSegment, prefixLength, i);
                        int count = (seg.getUpperSegmentValue() >>> shift) - (seg.getSegmentValue() >>> shift) + 1;
                        return count;
                    }
                    return this.getSegment(i).getValueCount();
                }, prefixedSegment + 1);
                return zeroHostCount;
            }
            return 1L;
        }
        return 0L;
    }

    protected abstract BigInteger getZeroHostCountImpl(int var1, int var2);

    @Override
    public BigInteger getNonZeroHostCount() {
        if (this.isPrefixed() && this.getNetworkPrefixLength() < this.getBitCount()) {
            BigInteger cached = this.cachedNonzeroHostCount;
            if (cached == null) {
                this.cachedNonzeroHostCount = cached = this.getCount().subtract(this.getZeroHostCountImpl(this.getNetworkPrefixLength(), this.getSegmentCount()));
            }
            return cached;
        }
        return this.getCount();
    }

    protected abstract BigInteger getCountImpl(int var1);

    @Override
    public BigInteger getCountImpl() {
        return this.getCountImpl(this.getSegmentCount());
    }

    @Override
    public BigInteger getBlockCount(int segmentCount) {
        if (segmentCount < 0) {
            throw new IllegalArgumentException();
        }
        int segCount = this.getSegmentCount();
        if (segmentCount > segCount) {
            segmentCount = segCount;
        }
        return this.getCountImpl(segmentCount);
    }

    public boolean isIPv4() {
        return false;
    }

    public boolean isIPv6() {
        return false;
    }

    @Override
    public int getMaxSegmentValue() {
        return IPAddressSegment.getMaxSegmentValue(this.getIPVersion());
    }

    protected static boolean isPrefixSubnetSegs(IPAddressSegment[] sectionSegments, Integer networkPrefixLength, IPAddressNetwork<?, ?, ?, ?, ?> network, boolean fullRangeOnly) {
        int segmentCount = sectionSegments.length;
        if (segmentCount == 0) {
            return false;
        }
        IPAddressSegment seg = sectionSegments[0];
        return ParsedAddressGrouping.isPrefixSubnet(segmentIndex -> sectionSegments[segmentIndex].getSegmentValue(), segmentIndex -> sectionSegments[segmentIndex].getUpperSegmentValue(), segmentCount, seg.getByteCount(), seg.getBitCount(), seg.getMaxSegmentValue(), networkPrefixLength, network.getPrefixConfiguration(), fullRangeOnly);
    }

    protected boolean isNetworkSection(int networkPrefixLength, boolean withPrefixLength) {
        int segmentCount = this.getSegmentCount();
        if (segmentCount == 0) {
            return true;
        }
        int bitsPerSegment = this.getBitsPerSegment();
        int prefixedSegmentIndex = IPAddressSection.getNetworkSegmentIndex(networkPrefixLength, this.getBytesPerSegment(), bitsPerSegment);
        if (prefixedSegmentIndex + 1 < segmentCount) {
            return false;
        }
        int segPrefLength = IPAddressSection.getPrefixedSegmentPrefixLength(bitsPerSegment, networkPrefixLength, prefixedSegmentIndex);
        return !this.getSegment(segmentCount - 1).isNetworkChangedByPrefix(IPAddressSection.cacheBits(segPrefLength), withPrefixLength);
    }

    protected boolean isHostSection(int networkPrefixLength) {
        int segmentCount = this.getSegmentCount();
        if (segmentCount == 0) {
            return true;
        }
        if (networkPrefixLength >= this.getBitsPerSegment()) {
            return false;
        }
        return !this.getSegment(0).isHostChangedByPrefix(IPAddressSection.cacheBits(networkPrefixLength));
    }

    protected static int getNetworkSegmentIndex(int networkPrefixLength, int bytesPerSegment, int bitsPerSegment) {
        return ParsedAddressGrouping.getNetworkSegmentIndex(networkPrefixLength, bytesPerSegment, bitsPerSegment);
    }

    protected static int getHostSegmentIndex(int networkPrefixLength, int bytesPerSegment, int bitsPerSegment) {
        return ParsedAddressGrouping.getHostSegmentIndex(networkPrefixLength, bytesPerSegment, bitsPerSegment);
    }

    private Integer checkForPrefixMask(boolean network) {
        int back;
        int front;
        int count = this.getSegmentCount();
        if (count == 0) {
            return null;
        }
        int maxval = this.getSegment(0).getMaxSegmentValue();
        if (network) {
            front = maxval;
            back = 0;
        } else {
            back = maxval;
            front = 0;
        }
        int prefixLen = 0;
        for (int i = 0; i < count; ++i) {
            IPAddressSegment seg = this.getSegment(i);
            int value = seg.getSegmentValue();
            if (value != front) {
                Integer segmentPrefixLen = seg.getBlockMaskPrefixLength(network);
                if (segmentPrefixLen == null) {
                    return null;
                }
                prefixLen += segmentPrefixLen.intValue();
                ++i;
                while (i < count) {
                    value = this.getSegment(i).getSegmentValue();
                    if (value != back) {
                        return null;
                    }
                    ++i;
                }
                continue;
            }
            prefixLen += seg.getBitCount();
        }
        return IPAddressSection.cacheBits(prefixLen);
    }

    public Integer getBlockMaskPrefixLength(boolean network) {
        Integer prefixLen;
        if (network) {
            if (this.hasNoPrefixCache() || (prefixLen = this.prefixCache.networkMaskPrefixLen) == null) {
                prefixLen = this.setNetworkMaskPrefix(this.checkForPrefixMask(network));
            }
        } else if (this.hasNoPrefixCache() || (prefixLen = this.prefixCache.hostMaskPrefixLen) == null) {
            prefixLen = this.setHostMaskPrefix(this.checkForPrefixMask(network));
        }
        if (prefixLen < 0) {
            return null;
        }
        return prefixLen;
    }

    private Integer setHostMaskPrefix(Integer prefixLen) {
        if (prefixLen == null) {
            prefixLen = this.prefixCache.hostMaskPrefixLen = IPAddressSection.NO_PREFIX_LENGTH;
        } else {
            this.prefixCache.hostMaskPrefixLen = prefixLen;
            this.prefixCache.networkMaskPrefixLen = IPAddressSection.NO_PREFIX_LENGTH;
        }
        return prefixLen;
    }

    private Integer setNetworkMaskPrefix(Integer prefixLen) {
        if (prefixLen == null) {
            prefixLen = this.prefixCache.networkMaskPrefixLen = IPAddressSection.NO_PREFIX_LENGTH;
        } else {
            this.prefixCache.networkMaskPrefixLen = prefixLen;
            this.prefixCache.hostMaskPrefixLen = IPAddressSection.NO_PREFIX_LENGTH;
        }
        return prefixLen;
    }

    protected static <T extends IPAddress, R extends IPAddressSection, S extends IPAddressSegment> R getNetworkSection(R original, int networkPrefixLength, boolean withPrefixLength, IPAddressNetwork.IPAddressCreator<T, R, ?, S, ?> creator, SegFunction<Integer, S> segProducer) {
        if (networkPrefixLength < 0 || networkPrefixLength > original.getBitCount()) {
            throw new PrefixLenException(original, networkPrefixLength);
        }
        if (original.isNetworkSection(networkPrefixLength, withPrefixLength)) {
            return original;
        }
        int bitsPerSegment = original.getBitsPerSegment();
        int networkSegmentCount = original.getNetworkSegmentCount(networkPrefixLength);
        IPAddressSegment[] result = (IPAddressSegment[])creator.createSegmentArray(networkSegmentCount);
        for (int i = 0; i < networkSegmentCount; ++i) {
            Integer prefix = IPAddressSection.getSegmentPrefixLength(bitsPerSegment, IPAddressSection.cacheBits(networkPrefixLength), i);
            result[i] = (IPAddressSegment)segProducer.apply(prefix, i);
        }
        return (R)creator.createSectionInternal(result);
    }

    protected int getNetworkSegmentCount(int networkPrefixLength) {
        return IPAddressSection.getNetworkSegmentIndex(networkPrefixLength, this.getBytesPerSegment(), this.getBitsPerSegment()) + 1;
    }

    protected static <T extends IPAddress, R extends IPAddressSection, S extends IPAddressSegment> R getHostSection(R original, int networkPrefixLength, int hostSegmentCount, IPAddressNetwork.IPAddressCreator<T, R, ?, S, ?> creator, SegFunction<Integer, S> segProducer) {
        if (networkPrefixLength < 0 || networkPrefixLength > original.getBitCount()) {
            throw new PrefixLenException(original, networkPrefixLength);
        }
        if (original.isHostSection(networkPrefixLength)) {
            return original;
        }
        int segmentCount = original.getSegmentCount();
        IPAddressSegment[] result = (IPAddressSegment[])creator.createSegmentArray(hostSegmentCount);
        if (hostSegmentCount > 0) {
            int bitsPerSegment = original.getBitsPerSegment();
            int i = hostSegmentCount - 1;
            int j = segmentCount - 1;
            while (i >= 0) {
                Integer prefix = IPAddressSection.getPrefixedSegmentPrefixLength(bitsPerSegment, networkPrefixLength, j);
                result[i] = (IPAddressSegment)segProducer.apply(prefix, j);
                --i;
                --j;
            }
        }
        return (R)creator.createSectionInternal(result);
    }

    protected int getHostSegmentCount(int networkPrefixLength) {
        return this.getSegmentCount() - IPAddressSection.getHostSegmentIndex(networkPrefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
    }

    protected static Integer cacheBits(int i) {
        return AddressDivisionGrouping.cacheBits(i);
    }

    protected static <R extends IPAddressSection, S extends IPAddressSegment> R setPrefixLength(R original, IPAddressNetwork.IPAddressCreator<?, R, ?, S, ?> creator, int networkPrefixLength, boolean withZeros, boolean noShrink, boolean singleOnly, SegFunction<R, S> segProducer) throws IncompatibleAddressException {
        int maskBits;
        Integer existingPrefixLength = original.getNetworkPrefixLength();
        if (existingPrefixLength != null) {
            if (networkPrefixLength == existingPrefixLength) {
                return original;
            }
            if (noShrink && networkPrefixLength > existingPrefixLength) {
                IPAddressSection.checkSubnet(original, networkPrefixLength);
                return original;
            }
        }
        IPAddressSection.checkSubnet(original, networkPrefixLength);
        AddressNetwork network = creator.getNetwork();
        IntUnaryOperator segmentMaskProducer = null;
        if (network.getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            maskBits = existingPrefixLength != null ? (networkPrefixLength > existingPrefixLength ? (withZeros ? existingPrefixLength : networkPrefixLength) : networkPrefixLength) : networkPrefixLength;
        } else {
            if (existingPrefixLength != null && withZeros) {
                Object rightMask;
                Object leftMask;
                if (networkPrefixLength > existingPrefixLength) {
                    leftMask = ((IPAddressNetwork)network).getNetworkMaskSection(existingPrefixLength);
                    rightMask = ((IPAddressNetwork)network).getHostMaskSection(networkPrefixLength);
                } else {
                    leftMask = ((IPAddressNetwork)network).getNetworkMaskSection(networkPrefixLength);
                    rightMask = ((IPAddressNetwork)network).getHostMaskSection(existingPrefixLength);
                }
                segmentMaskProducer = i -> {
                    int val1 = ((IPAddressSegment)segProducer.apply(leftMask, i)).getSegmentValue();
                    int val2 = ((IPAddressSegment)segProducer.apply(rightMask, i)).getSegmentValue();
                    return val1 | val2;
                };
            }
            maskBits = original.getBitCount();
        }
        if (segmentMaskProducer == null) {
            Object mask = ((IPAddressNetwork)network).getNetworkMaskSection(maskBits);
            segmentMaskProducer = i -> ((IPAddressSegment)segProducer.apply(mask, i)).getSegmentValue();
        }
        return IPAddressSection.getSubnetSegments(original, IPAddressSection.cacheBits(networkPrefixLength), creator, true, i -> (IPAddressSegment)segProducer.apply(original, i), segmentMaskProducer, singleOnly);
    }

    protected static <R extends IPAddressSection, S extends IPAddressSegment> R getSubnetSegments(R original, Integer networkPrefixLength, IPAddressNetwork.IPAddressCreator<?, R, ?, S, ?> creator, boolean verifyMask, IntFunction<S> segProducer, IntUnaryOperator segmentMaskProducer, boolean singleOnly) {
        if (networkPrefixLength != null && (networkPrefixLength < 0 || networkPrefixLength > original.getBitCount())) {
            throw new PrefixLenException(original, networkPrefixLength);
        }
        int bitsPerSegment = original.getBitsPerSegment();
        int count = original.getSegmentCount();
        boolean isAllSubnets = original.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() && !singleOnly;
        for (int i = 0; i < count; ++i) {
            Integer segmentPrefixLength = IPAddressSection.getSegmentPrefixLength(bitsPerSegment, networkPrefixLength, i);
            IPAddressSegment seg = (IPAddressSegment)segProducer.apply(i);
            int maskValue = segmentMaskProducer.applyAsInt(i);
            int value = seg.getSegmentValue();
            int upperValue = seg.getUpperSegmentValue();
            if (verifyMask) {
                ParsedIPAddress.Masker masker;
                if (isAllSubnets && segmentPrefixLength != null) {
                    int hostMask = seg.getSegmentHostMask(segmentPrefixLength);
                    maskValue |= hostMask;
                }
                if (!(masker = IPAddressSegment.maskRange(value, upperValue, maskValue, seg.getMaxValue())).isSequential()) {
                    throw new IncompatibleAddressException(seg, "ipaddress.error.maskMismatch");
                }
                value = (int)masker.getMaskedLower(value, maskValue);
                upperValue = (int)masker.getMaskedUpper(upperValue, maskValue);
            } else {
                value &= maskValue;
                upperValue &= maskValue;
            }
            if (!seg.isChangedBy(value, upperValue, segmentPrefixLength)) continue;
            Object[] newSegments = (IPAddressSegment[])creator.createSegmentArray(original.getSegmentCount());
            original.getSegments(0, i, (AddressSegment[])newSegments, 0);
            newSegments[i] = (IPAddressSegment)creator.createSegment(value, upperValue, segmentPrefixLength);
            if (isAllSubnets && segmentPrefixLength != null) {
                if (++i < count) {
                    IPAddressSegment zeroSeg = (IPAddressSegment)creator.createSegment(0, IPAddressSection.cacheBits(0));
                    Arrays.fill(newSegments, i, count, zeroSeg);
                }
            } else {
                ++i;
                while (i < count) {
                    segmentPrefixLength = IPAddressSection.getSegmentPrefixLength(bitsPerSegment, networkPrefixLength, i);
                    seg = (IPAddressSegment)segProducer.apply(i);
                    maskValue = segmentMaskProducer.applyAsInt(i);
                    value = seg.getSegmentValue();
                    upperValue = seg.getUpperSegmentValue();
                    if (verifyMask) {
                        ParsedIPAddress.Masker masker;
                        if (isAllSubnets && segmentPrefixLength != null) {
                            int hostMask = seg.getSegmentHostMask(segmentPrefixLength);
                            maskValue |= hostMask;
                        }
                        if (!(masker = IPAddressSegment.maskRange(value, upperValue, maskValue, seg.getMaxValue())).isSequential()) {
                            throw new IncompatibleAddressException(seg, "ipaddress.error.maskMismatch");
                        }
                        value = (int)masker.getMaskedLower(value, maskValue);
                        upperValue = (int)masker.getMaskedUpper(upperValue, maskValue);
                    } else {
                        value &= maskValue;
                        upperValue &= maskValue;
                    }
                    newSegments[i] = seg.isChangedBy(value, upperValue, segmentPrefixLength) ? (IPAddressSegment)creator.createSegment(value, upperValue, segmentPrefixLength) : seg;
                    if (isAllSubnets && segmentPrefixLength != null) {
                        if (++i >= count) break;
                        IPAddressSegment zeroSeg = (IPAddressSegment)creator.createSegment(0, IPAddressSection.cacheBits(0));
                        Arrays.fill(newSegments, i, count, zeroSeg);
                        break;
                    }
                    ++i;
                }
            }
            return (R)creator.createPrefixedSectionInternal((IPAddressSegment[])newSegments, networkPrefixLength, singleOnly);
        }
        return original;
    }

    protected static <R extends IPAddressSection, S extends IPAddressSegment> R getOredSegments(R original, Integer networkPrefixLength, IPAddressNetwork.IPAddressCreator<?, R, ?, S, ?> creator, boolean verifyMask, IntFunction<S> segProducer, IntUnaryOperator segmentMaskProducer) {
        if (networkPrefixLength != null && (networkPrefixLength < 0 || networkPrefixLength > original.getBitCount())) {
            throw new PrefixLenException(original, networkPrefixLength);
        }
        int bitsPerSegment = original.getBitsPerSegment();
        int count = original.getSegmentCount();
        boolean isAllSubnets = original.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        for (int i = 0; i < count; ++i) {
            Integer segmentPrefixLength = IPAddressSection.getSegmentPrefixLength(bitsPerSegment, networkPrefixLength, i);
            IPAddressSegment seg = (IPAddressSegment)segProducer.apply(i);
            int maskValue = segmentMaskProducer.applyAsInt(i);
            int value = seg.getSegmentValue();
            int upperValue = seg.getUpperSegmentValue();
            if (verifyMask) {
                ParsedIPAddress.BitwiseOrer masker;
                if (isAllSubnets && segmentPrefixLength != null) {
                    int networkMask = seg.getSegmentNetworkMask(segmentPrefixLength);
                    maskValue &= networkMask;
                }
                if (!(masker = IPAddressSegment.bitwiseOrRange(value, upperValue, maskValue, seg.getMaxValue())).isSequential()) {
                    throw new IncompatibleAddressException(seg, "ipaddress.error.maskMismatch");
                }
                value = (int)masker.getOredLower(value, maskValue);
                upperValue = (int)masker.getOredUpper(upperValue, maskValue);
            } else {
                value |= maskValue;
                upperValue |= maskValue;
            }
            if (!seg.isChangedBy(value, upperValue, segmentPrefixLength)) continue;
            Object[] newSegments = (IPAddressSegment[])creator.createSegmentArray(original.getSegmentCount());
            original.getSegments(0, i, (AddressSegment[])newSegments, 0);
            newSegments[i] = (IPAddressSegment)creator.createSegment(value, upperValue, segmentPrefixLength);
            if (isAllSubnets && segmentPrefixLength != null) {
                if (++i < count) {
                    IPAddressSegment zeroSeg = (IPAddressSegment)creator.createSegment(0, IPAddressSection.cacheBits(0));
                    Arrays.fill(newSegments, i, count, zeroSeg);
                }
            } else {
                ++i;
                while (i < count) {
                    segmentPrefixLength = IPAddressSection.getSegmentPrefixLength(bitsPerSegment, networkPrefixLength, i);
                    seg = (IPAddressSegment)segProducer.apply(i);
                    maskValue = segmentMaskProducer.applyAsInt(i);
                    value = seg.getSegmentValue();
                    upperValue = seg.getUpperSegmentValue();
                    if (verifyMask) {
                        ParsedIPAddress.BitwiseOrer masker;
                        if (isAllSubnets && segmentPrefixLength != null) {
                            int networkMask = seg.getSegmentNetworkMask(segmentPrefixLength);
                            maskValue &= networkMask;
                        }
                        if (!(masker = IPAddressSegment.bitwiseOrRange(value, upperValue, maskValue, seg.getMaxValue())).isSequential()) {
                            throw new IncompatibleAddressException(seg, "ipaddress.error.maskMismatch");
                        }
                        value = (int)masker.getOredLower(value, maskValue);
                        upperValue = (int)masker.getOredUpper(upperValue, maskValue);
                    } else {
                        value |= maskValue;
                        upperValue |= maskValue;
                    }
                    newSegments[i] = seg.isChangedBy(value, upperValue, segmentPrefixLength) ? (IPAddressSegment)creator.createSegment(value, upperValue, segmentPrefixLength) : seg;
                    if (isAllSubnets && segmentPrefixLength != null) {
                        if (++i >= count) break;
                        IPAddressSegment zeroSeg = (IPAddressSegment)creator.createSegment(0, IPAddressSection.cacheBits(0));
                        Arrays.fill(newSegments, i, count, zeroSeg);
                        break;
                    }
                    ++i;
                }
            }
            return (R)creator.createPrefixedSectionInternal((IPAddressSegment[])newSegments, networkPrefixLength);
        }
        return original;
    }

    protected static void checkSubnet(AddressDivisionSeries series, int prefixLength) throws PrefixLenException {
        AddressDivisionGrouping.checkSubnet(series, prefixLength);
    }

    protected static Integer getSegmentPrefixLength(int bitsPerSegment, Integer prefixLength, int segmentIndex) {
        return AddressDivisionGrouping.getSegmentPrefixLength(bitsPerSegment, prefixLength, segmentIndex);
    }

    protected static Integer getSegmentPrefixLength(int bitsPerSegment, int segmentPrefixedBits) {
        return AddressDivisionGrouping.getSegmentPrefixLength(bitsPerSegment, segmentPrefixedBits);
    }

    protected static Integer getPrefixedSegmentPrefixLength(int bitsPerSegment, int prefixLength, int segmentIndex) {
        return AddressDivisionGrouping.getPrefixedSegmentPrefixLength(bitsPerSegment, prefixLength, segmentIndex);
    }

    protected static <R extends IPAddressSection, S extends IPAddressSegment> R createLowestOrHighestSection(R section, IPAddressNetwork.IPAddressCreator<?, R, ?, S, ?> creator, Supplier<Iterator<S[]>> nonZeroHostIteratorSupplier, IntFunction<S> segProducer, boolean lowest, boolean excludeZeroHost) {
        boolean create = true;
        IPAddressSection result = null;
        IPAddressSegment[] segs = null;
        if (lowest && excludeZeroHost && section.includesZeroHost()) {
            Iterator<S[]> it = nonZeroHostIteratorSupplier.get();
            if (!it.hasNext()) {
                create = false;
            } else {
                segs = (IPAddressSegment[])it.next();
            }
        } else {
            segs = (IPAddressSegment[])IPAddressSection.createSingle(section, creator, segProducer);
        }
        if (create) {
            Integer prefLength;
            result = section.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() || (prefLength = section.getNetworkPrefixLength()) == null ? creator.createSectionInternal(segs) : creator.createPrefixedSectionInternal(segs, prefLength, true);
        }
        return (R)result;
    }

    @Override
    public int getSegmentCount() {
        return this.getDivisionCount();
    }

    @Override
    public IPAddressSegment getSegment(int index) {
        return this.getSegmentsInternal()[index];
    }

    @Override
    public IPAddressSegment getDivision(int index) {
        return this.getSegmentsInternal()[index];
    }

    @Override
    public boolean containsPrefixBlock(int prefixLength) {
        IPAddressSection.checkSubnet(this, prefixLength);
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (isAllSubnets && this.isPrefixed() && this.getNetworkPrefixLength() <= prefixLength) {
            return true;
        }
        int divCount = this.getDivisionCount();
        int bitsPerSegment = this.getBitsPerSegment();
        int i = IPAddressSection.getHostSegmentIndex(prefixLength, this.getBytesPerSegment(), bitsPerSegment);
        if (i < divCount) {
            int segmentPrefixLength;
            IPAddressSegment div = this.getDivision(i);
            if (!div.containsPrefixBlock(segmentPrefixLength = IPAddressSection.getPrefixedSegmentPrefixLength(bitsPerSegment, prefixLength, i).intValue())) {
                return false;
            }
            if (isAllSubnets && div.isPrefixed()) {
                return true;
            }
            ++i;
            while (i < divCount) {
                div = this.getDivision(i);
                if (!div.isFullRange()) {
                    return false;
                }
                if (isAllSubnets && div.isPrefixed()) {
                    return true;
                }
                ++i;
            }
        }
        return true;
    }

    static boolean containsPrefixBlock(int prefixLength, IPAddressSegmentSeries lower, IPAddressSegmentSeries upper) {
        IPAddressSection.checkSubnet(lower, prefixLength);
        int divCount = lower.getDivisionCount();
        int bitsPerSegment = lower.getBitsPerSegment();
        int i = IPAddressSection.getHostSegmentIndex(prefixLength, lower.getBytesPerSegment(), bitsPerSegment);
        if (i < divCount) {
            IPAddressSegment div = lower.getSegment(i);
            IPAddressSegment upperDiv = upper.getSegment(i);
            int segmentPrefixLength = IPAddressSection.getPrefixedSegmentPrefixLength(bitsPerSegment, prefixLength, i);
            if (!div.containsPrefixBlock(div.getSegmentValue(), upperDiv.getSegmentValue(), segmentPrefixLength)) {
                return false;
            }
            ++i;
            while (i < divCount) {
                div = lower.getSegment(i);
                upperDiv = upper.getSegment(i);
                if (!div.includesZero() || !upperDiv.includesMax()) {
                    return false;
                }
                ++i;
            }
        }
        return true;
    }

    static boolean containsSinglePrefixBlock(int prefixLength, IPAddressSegmentSeries lower, IPAddressSegmentSeries upper) {
        IPAddressSection.checkSubnet(lower, prefixLength);
        int prevBitCount = 0;
        int divCount = lower.getDivisionCount();
        for (int i = 0; i < divCount; ++i) {
            IPAddressSegment div = lower.getSegment(i);
            IPAddressSegment upperDiv = upper.getSegment(i);
            int bitCount = div.getBitCount();
            int totalBitCount = bitCount + prevBitCount;
            if (prefixLength >= totalBitCount) {
                if (!div.isSameValues(upperDiv)) {
                    return false;
                }
            } else {
                int divPrefixLen = Math.max(0, prefixLength - prevBitCount);
                if (!div.containsSinglePrefixBlock(div.getSegmentValue(), upperDiv.getSegmentValue(), divPrefixLen)) {
                    return false;
                }
                ++i;
                while (i < divCount) {
                    div = lower.getSegment(i);
                    upperDiv = upper.getSegment(i);
                    if (!div.includesZero() || !upperDiv.includesMax()) {
                        return false;
                    }
                    ++i;
                }
                return true;
            }
            prevBitCount = totalBitCount;
        }
        return true;
    }

    @Override
    public boolean contains(AddressSection other) {
        int endIndex;
        int count = this.getSegmentCount();
        if (count != other.getSegmentCount()) {
            return false;
        }
        boolean prefixIsSubnet = this.isPrefixed() && this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        for (int i = endIndex = prefixIsSubnet ? IPAddressSection.getNetworkSegmentIndex(this.getNetworkPrefixLength(), this.getBytesPerSegment(), this.getBitsPerSegment()) : count - 1; i >= 0; --i) {
            IPAddressSegment seg = this.getSegment(i);
            if (seg.contains(other.getSegment(i))) continue;
            return false;
        }
        return true;
    }

    public boolean containsNonZeroHosts(IPAddressSection other) {
        if (!other.isPrefixed()) {
            return this.contains(other);
        }
        int otherPrefixLength = other.getNetworkPrefixLength();
        if (otherPrefixLength == other.getBitCount()) {
            return this.contains(other);
        }
        return this.containsNonZeroHostsImpl(other, otherPrefixLength);
    }

    protected abstract boolean containsNonZeroHostsImpl(IPAddressSection var1, int var2);

    public abstract boolean prefixContains(IPAddressSection var1);

    @Override
    public boolean isFullRange() {
        int divCount = this.getDivisionCount();
        if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            for (int i = 0; i < divCount; ++i) {
                IPAddressSegment div = this.getSegment(i);
                if (!div.isFullRange()) {
                    return false;
                }
                Integer prefix = div.getSegmentPrefixLength();
                if (prefix == null) {
                    continue;
                }
                break;
            }
        } else {
            return super.isFullRange();
        }
        return true;
    }

    protected static <T extends IPAddress, R extends IPAddressSection, S extends IPAddressSegment> R intersect(R first, R other, IPAddressNetwork.IPAddressCreator<T, R, ?, S, ?> addrCreator, IntFunction<S> segProducer, IntFunction<S> otherSegProducer) {
        first.checkSectionCount(other);
        Integer pref = first.getNetworkPrefixLength();
        Integer otherPref = other.getNetworkPrefixLength();
        if (pref != null) {
            if (otherPref != null) {
                if (otherPref > pref) {
                    pref = otherPref;
                }
            } else {
                pref = null;
            }
        }
        if (other.contains(first)) {
            if (Objects.equals(pref, first.getNetworkPrefixLength())) {
                return first;
            }
        } else if (!first.isMultiple()) {
            return null;
        }
        if (first.contains(other)) {
            if (Objects.equals(pref, other.getNetworkPrefixLength())) {
                return other;
            }
        } else if (!other.isMultiple()) {
            return null;
        }
        int segCount = first.getSegmentCount();
        for (int i = 0; i < segCount; ++i) {
            IPAddressSegment seg = first.getSegment(i);
            IPAddressSegment otherSeg = other.getSegment(i);
            int lower = seg.getSegmentValue();
            int higher = seg.getUpperSegmentValue();
            int otherLower = otherSeg.getSegmentValue();
            int otherHigher = otherSeg.getUpperSegmentValue();
            if (otherLower <= higher && lower <= otherHigher) continue;
            return null;
        }
        IPAddressSegment[] segs = (IPAddressSegment[])addrCreator.createSegmentArray(segCount);
        for (int i = 0; i < segCount; ++i) {
            IPAddressSegment seg = (IPAddressSegment)segProducer.apply(i);
            IPAddressSegment otherSeg = (IPAddressSegment)otherSegProducer.apply(i);
            Integer segPref = IPAddressSection.getSegmentPrefixLength(seg.getBitCount(), pref, i);
            if (seg.contains(otherSeg) && !otherSeg.isChangedByPrefix(segPref, false)) {
                segs[i] = otherSeg;
                continue;
            }
            if (otherSeg.contains(seg) && !seg.isChangedByPrefix(segPref, false)) {
                segs[i] = seg;
                continue;
            }
            int lower = seg.getSegmentValue();
            int higher = seg.getUpperSegmentValue();
            int otherLower = otherSeg.getSegmentValue();
            int otherHigher = otherSeg.getUpperSegmentValue();
            int newLower = Math.max(lower, otherLower);
            int newHigher = Math.min(higher, otherHigher);
            segs[i] = (IPAddressSegment)addrCreator.createSegment(newLower, newHigher, segPref);
        }
        IPAddressSection result = addrCreator.createSection(segs);
        return (R)result;
    }

    static <R extends IPAddressSegmentSeries, OperatorResult> OperatorResult applyOperatorToLowerUpper(R first, R other, UnaryOperator<R> getLower, UnaryOperator<R> getUpper, Comparator<R> comparator, Function<R, R> prefixRemover, TriFunction<R, OperatorResult> operatorFunctor) {
        IPAddressSegmentSeries upper;
        Object lower;
        boolean isOther = true;
        boolean isFirst = true;
        if (first.equals(other)) {
            if (prefixRemover != null && first.isPrefixed()) {
                if (other.isPrefixed()) {
                    lower = (IPAddressSegmentSeries)prefixRemover.apply(first);
                    isFirst = false;
                    isOther = false;
                } else {
                    lower = other;
                    isFirst = false;
                }
            } else {
                isOther = false;
                lower = first;
            }
            upper = (IPAddressSegmentSeries)getUpper.apply(lower);
            lower = (IPAddressSegmentSeries)getLower.apply(lower);
        } else {
            IPAddressSegmentSeries firstLower = (IPAddressSegmentSeries)getLower.apply(first);
            IPAddressSegmentSeries otherLower = (IPAddressSegmentSeries)getLower.apply(other);
            IPAddressSegmentSeries firstUpper = (IPAddressSegmentSeries)getUpper.apply(first);
            IPAddressSegmentSeries otherUpper = (IPAddressSegmentSeries)getUpper.apply(other);
            if (comparator.compare(firstLower, otherLower) > 0) {
                lower = otherLower;
                isFirst = false;
            } else {
                lower = firstLower;
                isOther = false;
            }
            if (comparator.compare(firstUpper, otherUpper) < 0) {
                upper = otherUpper;
                isFirst = false;
            } else {
                upper = firstUpper;
                isOther = false;
            }
            if (prefixRemover != null) {
                lower = (IPAddressSegmentSeries)prefixRemover.apply(lower);
                upper = (IPAddressSegmentSeries)prefixRemover.apply(upper);
            }
        }
        return operatorFunctor.apply((IPAddressSegmentSeries)(isFirst ? first : (isOther ? other : null)), (IPAddressSegmentSeries)lower, upper);
    }

    protected static <T extends IPAddressSegmentSeries> T coverWithPrefixBlock(T first, T other, UnaryOperator<T> getLower, UnaryOperator<T> getUpper, Comparator<T> comparator) throws AddressConversionException {
        return (T)IPAddressSection.applyOperatorToLowerUpper(first, other, getLower, getUpper, comparator, null, IPAddressSection::coverWithPrefixBlock);
    }

    protected static IPAddressSegmentSeries coverWithPrefixBlock(IPAddressSegmentSeries original, IPAddressSegmentSeries lower, IPAddressSegmentSeries upper) {
        int segCount = lower.getSegmentCount();
        int bitsPerSegment = lower.getBitsPerSegment();
        int previousSegmentBits = 0;
        for (int currentSegment = 0; currentSegment < segCount; ++currentSegment) {
            int upperValue;
            IPAddressSegment lowerSeg = lower.getSegment(currentSegment);
            IPAddressSegment upperSeg = upper.getSegment(currentSegment);
            int lowerValue = lowerSeg.getSegmentValue();
            int differing = lowerValue ^ (upperValue = upperSeg.getSegmentValue());
            if (differing != 0) {
                int highestDifferingBitInRange = Integer.numberOfLeadingZeros(differing) - (32 - bitsPerSegment);
                int differingBitPrefixLen = highestDifferingBitInRange + previousSegmentBits;
                return (original != null ? original : lower).toPrefixBlock(differingBitPrefixLen);
            }
            previousSegmentBits += bitsPerSegment;
        }
        return (original != null ? original : lower).toPrefixBlock(lower.getBitCount());
    }

    private static <R extends IPAddressSection> R[] checkSequentialBlockContainment(R first, R other, UnaryOperator<R> prefixRemover, IntFunction<R[]> arrayProducer) {
        if (first.contains(other)) {
            return (IPAddressSection[])IPAddress.checkSequentialBlockFormat(first, other, (boolean)true, prefixRemover, arrayProducer);
        }
        if (other.contains(first)) {
            return (IPAddressSection[])IPAddress.checkSequentialBlockFormat(other, first, (boolean)false, prefixRemover, arrayProducer);
        }
        return null;
    }

    private static <R extends IPAddressSection> R checkPrefixBlockContainment(R first, R other, UnaryOperator<R> prefixAdder) {
        if (first.contains(other)) {
            return IPAddress.checkPrefixBlockFormat(first, other, true, prefixAdder);
        }
        if (other.contains(first)) {
            return IPAddress.checkPrefixBlockFormat(other, first, false, prefixAdder);
        }
        return null;
    }

    protected static <R extends IPAddressSection> R[] getSpanningPrefixBlocks(R first, R other, UnaryOperator<R> getLower, UnaryOperator<R> getUpper, Comparator<R> comparator, UnaryOperator<R> prefixAdder, UnaryOperator<R> prefixRemover, IntFunction<R[]> arrayProducer) {
        first.checkSectionCount(other);
        R result = IPAddressSection.checkPrefixBlockContainment(first, other, prefixAdder);
        if (result != null) {
            IPAddressSection[] resultArray = (IPAddressSection[])arrayProducer.apply(1);
            resultArray[0] = result;
            return resultArray;
        }
        List blocks = IPAddressSection.applyOperatorToLowerUpper(first, other, getLower, getUpper, comparator, prefixRemover, (orig, lower, upper) -> IPAddressSection.splitIntoPrefixBlocks(lower, upper));
        IPAddressSection[] resultArray = blocks.toArray((IPAddressSection[])arrayProducer.apply(blocks.size()));
        return resultArray;
    }

    protected static <R extends IPAddressSection, S extends IPAddressSegment> R[] getSpanningSequentialBlocks(R first, R other, UnaryOperator<R> getLower, UnaryOperator<R> getUpper, Comparator<R> comparator, UnaryOperator<R> prefixRemover, IPAddressNetwork.IPAddressCreator<?, R, ?, S, ?> creator) {
        IPAddressSection[] result = IPAddressSection.checkSequentialBlockContainment(first, other, prefixRemover, (IntFunction<IPAddressSection[]>)LambdaMetafactory.metafactory(null, null, null, (I)Ljava/lang/Object;, createSectionArray(int ), (I)[Linet/ipaddr/IPAddressSection;)(creator));
        if (result != null) {
            return result;
        }
        TriFunction<IPAddressSection, List> operatorFunctor = (orig, one, two) -> IPAddressSection.splitIntoSequentialBlocks(one, two, creator::createSequentialBlockSection);
        List blocks = IPAddressSection.applyOperatorToLowerUpper(first, other, getLower, getUpper, comparator, prefixRemover, operatorFunctor);
        return blocks.toArray(creator.createSectionArray(blocks.size()));
    }

    /*
     * Enabled aggressive block sorting
     */
    static List<IPAddressSegmentSeries> splitIntoSequentialBlocks(IPAddressSegmentSeries lower, IPAddressSegmentSeries upper, SeriesCreator seriesCreator) {
        ArrayList<IPAddressSegmentSeries> blocks = new ArrayList<IPAddressSegmentSeries>(8);
        int segCount = lower.getSegmentCount();
        if (segCount == 0) {
            blocks.add(lower);
            return blocks;
        }
        int previousSegmentBits = 0;
        int currentSegment = 0;
        int bitsPerSegment = lower.getBitsPerSegment();
        SeriesStack stack = null;
        ArrayDeque<IPAddressSegmentSeries> toAdd = null;
        while (true) {
            block15: {
                int segSegment = currentSegment;
                IPAddressSegment lowerSeg = lower.getSegment(currentSegment);
                IPAddressSegment upperSeg = upper.getSegment(currentSegment++);
                int lowerValue = lowerSeg.getSegmentValue();
                int upperValue = upperSeg.getSegmentValue();
                previousSegmentBits += bitsPerSegment;
                if (lowerValue == upperValue && currentSegment < segCount) continue;
                if (lowerValue == upperValue) {
                    blocks.add(lower);
                } else {
                    IPAddressSegmentSeries series;
                    IPAddressSegmentSeries middleUpper;
                    IPAddressSegmentSeries topLower;
                    boolean lowerIsLowest = lower.includesZeroHost(previousSegmentBits);
                    boolean higherIsHighest = upper.includesMaxHost(previousSegmentBits);
                    if (lowerIsLowest) {
                        if (higherIsHighest) {
                            IPAddressSegmentSeries series2 = seriesCreator.apply(lower, segSegment, lowerValue, upperValue);
                            blocks.add(series2);
                            break block15;
                        } else {
                            topLower = upper.toZeroHost(previousSegmentBits);
                            middleUpper = topLower.increment(-1L);
                            series = seriesCreator.apply(lower, segSegment, lowerValue, middleUpper.getSegment(segSegment).getSegmentValue());
                            blocks.add(series);
                            lower = topLower;
                            continue;
                        }
                    }
                    if (higherIsHighest) {
                        IPAddressSegmentSeries bottomUpper = lower.toMaxHost(previousSegmentBits);
                        IPAddressSegmentSeries topLower2 = bottomUpper.increment(1L);
                        series = seriesCreator.apply(topLower2, segSegment, topLower2.getSegment(segSegment).getSegmentValue(), upperValue);
                        if (toAdd == null) {
                            toAdd = new ArrayDeque<IPAddressSegmentSeries>(8);
                        }
                        toAdd.addFirst(series);
                        upper = bottomUpper;
                        continue;
                    }
                    topLower = upper.toZeroHost(previousSegmentBits);
                    middleUpper = topLower.increment(-1L);
                    IPAddressSegmentSeries bottomUpper = lower.toMaxHost(previousSegmentBits);
                    IPAddressSegmentSeries middleLower = bottomUpper.increment(1L);
                    if (middleLower.compareTo(middleUpper) <= 0) {
                        IPAddressSegmentSeries series3 = seriesCreator.apply(middleLower, segSegment, middleLower.getSegment(segSegment).getSegmentValue(), middleUpper.getSegment(segSegment).getSegmentValue());
                        if (toAdd == null) {
                            toAdd = new ArrayDeque(8);
                        }
                        toAdd.addFirst(series3);
                    }
                    if (stack == null) {
                        stack = new SeriesStack(8);
                    }
                    stack.push(topLower, upper, previousSegmentBits, currentSegment);
                    upper = bottomUpper;
                    continue;
                }
            }
            if (toAdd != null) {
                IPAddressSegmentSeries saved;
                while ((saved = (IPAddressSegmentSeries)toAdd.pollFirst()) != null) {
                    blocks.add(saved);
                }
            }
            if (stack == null || !stack.pop()) {
                return blocks;
            }
            lower = stack.lower;
            upper = stack.upper;
            previousSegmentBits = stack.previousSegmentBits;
            currentSegment = stack.currentSegment;
        }
    }

    static List<IPAddressSegmentSeries> splitIntoPrefixBlocks(IPAddressSegmentSeries lower, IPAddressSegmentSeries upper) {
        ArrayList<IPAddressSegmentSeries> blocks = new ArrayList<IPAddressSegmentSeries>();
        int previousSegmentBits = 0;
        int currentSegment = 0;
        SeriesStack stack = null;
        while (true) {
            int differing = 0;
            int segCount = lower.getSegmentCount();
            int bitsPerSegment = lower.getBitsPerSegment();
            while (currentSegment < segCount) {
                int upperValue;
                IPAddressSegment lowerSeg = lower.getSegment(currentSegment);
                IPAddressSegment upperSeg = upper.getSegment(currentSegment);
                int lowerValue = lowerSeg.getSegmentValue();
                differing = lowerValue ^ (upperValue = upperSeg.getSegmentValue());
                if (differing != 0) break;
                previousSegmentBits += bitsPerSegment;
                ++currentSegment;
            }
            if (differing == 0) {
                blocks.add(lower.toPrefixBlock(lower.getBitCount()));
            } else {
                boolean differingIsLowestBit;
                boolean bl = differingIsLowestBit = differing == 1;
                if (differingIsLowestBit && currentSegment + 1 == segCount) {
                    blocks.add(lower.toPrefixBlock(lower.getBitCount() - 1));
                } else {
                    int highestDifferingBitInRange = Integer.numberOfLeadingZeros(differing) - (32 - bitsPerSegment);
                    int differingBitPrefixLen = highestDifferingBitInRange + previousSegmentBits;
                    if (lower.includesZeroHost(differingBitPrefixLen) && upper.includesMaxHost(differingBitPrefixLen)) {
                        blocks.add(lower.toPrefixBlock(differingBitPrefixLen));
                    } else {
                        IPAddressSegmentSeries lowerTop = upper.toZeroHost(differingBitPrefixLen + 1);
                        IPAddressSegmentSeries upperBottom = lowerTop.increment(-1L);
                        if (differingIsLowestBit) {
                            previousSegmentBits += bitsPerSegment;
                            ++currentSegment;
                        }
                        if (stack == null) {
                            stack = new SeriesStack(128);
                        }
                        stack.push(lowerTop, upper, previousSegmentBits, currentSegment);
                        upper = upperBottom;
                        continue;
                    }
                }
            }
            if (stack == null || !stack.pop()) {
                return blocks;
            }
            lower = stack.lower;
            upper = stack.upper;
            previousSegmentBits = stack.previousSegmentBits;
            currentSegment = stack.currentSegment;
        }
    }

    protected static List<IPAddressSegmentSeries> getMergedSequentialBlocks(IPAddressSegmentSeries[] sections, SeriesCreator seriesCreator) {
        ArrayList<IPAddressSegmentSeries> list = new ArrayList<IPAddressSegmentSeries>(sections.length << 1);
        boolean singleElement = IPAddressSection.organizeSequentialMerge(sections, list);
        if (singleElement) {
            list.set(0, list.get(0).withoutPrefixLength());
            return list;
        }
        AddressComparator.ValueComparator reverseLowComparator = REVERSE_LOW_COMPARATOR;
        AddressComparator.ValueComparator reverseHighComparator = REVERSE_HIGH_COMPARATOR;
        int removedCount = 0;
        int j = list.size() - 1;
        int i = j - 1;
        int ithRangeSegmentIndex = -1;
        int jthRangeSegmentIndex = -1;
        block0: while (j > 0) {
            int k;
            IPAddressSegmentSeries otherItem;
            IPAddressSegmentSeries item = list.get(i);
            int compare = reverseHighComparator.compare(item, otherItem = list.get(j));
            if (compare > 0) {
                int k2;
                ++removedCount;
                for (k2 = j + 1; k2 < list.size() && list.get(k2) == null; ++k2) {
                }
                if (k2 < list.size()) {
                    list.set(j, list.get(k2));
                    list.set(k2, null);
                    jthRangeSegmentIndex = -1;
                    continue;
                }
                list.set(j, null);
                j = i--;
                jthRangeSegmentIndex = ithRangeSegmentIndex;
                ithRangeSegmentIndex = -1;
                continue;
            }
            int rcompare = reverseLowComparator.compare(item, otherItem);
            if (rcompare >= 0) {
                ++removedCount;
                list.set(i, otherItem);
                list.set(j, null);
                j = i--;
                jthRangeSegmentIndex = ithRangeSegmentIndex;
                ithRangeSegmentIndex = -1;
                continue;
            }
            if (ithRangeSegmentIndex < 0) {
                ithRangeSegmentIndex = item.getSequentialBlockIndex();
            }
            if (jthRangeSegmentIndex < 0) {
                jthRangeSegmentIndex = otherItem.getSequentialBlockIndex();
            }
            if (ithRangeSegmentIndex != jthRangeSegmentIndex) {
                j = i--;
                jthRangeSegmentIndex = ithRangeSegmentIndex;
                ithRangeSegmentIndex = -1;
                continue;
            }
            IPAddressSegment rangeSegment = item.getSegment(ithRangeSegmentIndex);
            IPAddressSegment otherRangeSegment = otherItem.getSegment(ithRangeSegmentIndex);
            int otherRangeItemValue = otherRangeSegment.getSegmentValue();
            int rangeItemUpperValue = rangeSegment.getUpperSegmentValue();
            if (rangeItemUpperValue < otherRangeItemValue && rangeItemUpperValue + 1 != otherRangeItemValue) {
                j = i--;
                ithRangeSegmentIndex = -1;
                continue;
            }
            for (int k3 = ithRangeSegmentIndex - 1; k3 >= 0; --k3) {
                int otherVal;
                IPAddressSegment itemSegment = item.getSegment(k3);
                IPAddressSegment otherItemSegment = otherItem.getSegment(k3);
                int val = itemSegment.getSegmentValue();
                if (val == (otherVal = otherItemSegment.getSegmentValue())) continue;
                j = i--;
                ithRangeSegmentIndex = -1;
                continue block0;
            }
            IPAddressSegmentSeries joinedItem = seriesCreator.apply(item, ithRangeSegmentIndex, rangeSegment.getSegmentValue(), Math.max(rangeItemUpperValue, otherRangeSegment.getUpperSegmentValue()));
            list.set(i, joinedItem);
            if (joinedItem.getSegment(ithRangeSegmentIndex).isFullRange()) {
                if (ithRangeSegmentIndex == 0) {
                    list.clear();
                    list.add(joinedItem);
                    return list;
                }
                --ithRangeSegmentIndex;
            }
            ++removedCount;
            for (k = j + 1; k < list.size() && list.get(k) == null; ++k) {
            }
            if (k < list.size()) {
                list.set(j, list.get(k));
                list.set(k, null);
                jthRangeSegmentIndex = -1;
                continue;
            }
            list.set(j, null);
            j = i--;
            jthRangeSegmentIndex = ithRangeSegmentIndex;
            ithRangeSegmentIndex = -1;
        }
        if (removedCount > 0) {
            int newSize = list.size() - removedCount;
            int k = 0;
            int l = 0;
            while (k < newSize) {
                while (list.get(l) == null) {
                    ++l;
                }
                list.set(k, list.get(l).withoutPrefixLength());
                ++k;
                ++l;
            }
            int last = list.size();
            while (removedCount-- > 0) {
                list.remove(--last);
            }
        } else {
            for (int n = 0; n < list.size(); ++n) {
                list.set(n, list.get(n).withoutPrefixLength());
            }
        }
        return list;
    }

    protected static List<IPAddressSegmentSeries> getMergedPrefixBlocks(IPAddressSegmentSeries[] sections) {
        ArrayList<IPAddressSegmentSeries> list = new ArrayList<IPAddressSegmentSeries>(sections.length << 3);
        boolean singleElement = IPAddressSection.organizeSequentially(sections, list);
        if (singleElement) {
            return list;
        }
        AddressComparator.ValueComparator reverseLowComparator = REVERSE_LOW_COMPARATOR;
        AddressComparator.ValueComparator reverseHighComparator = REVERSE_HIGH_COMPARATOR;
        IPAddressSegmentSeries first = sections[0];
        int bitCount = first.getBitCount();
        int bitsPerSegment = first.getBitsPerSegment();
        int bytesPerSegment = first.getBytesPerSegment();
        int removedCount = 0;
        int j = list.size() - 1;
        int i = j - 1;
        block0: while (j > 0) {
            int k;
            int lastMatchSegmentIndex;
            int lastBitSegmentIndex;
            int matchBitIndex;
            Integer otherPrefixLen;
            IPAddressSegmentSeries otherItem;
            IPAddressSegmentSeries item = list.get(i);
            int compare = reverseHighComparator.compare(item, otherItem = list.get(j));
            if (compare > 0) {
                int k2;
                ++removedCount;
                for (k2 = j + 1; k2 < list.size() && list.get(k2) == null; ++k2) {
                }
                if (k2 < list.size()) {
                    list.set(j, list.get(k2));
                    list.set(k2, null);
                    continue;
                }
                list.set(j, null);
                j = i--;
                continue;
            }
            int rcompare = reverseLowComparator.compare(item, otherItem);
            if (rcompare >= 0) {
                ++removedCount;
                list.set(i, otherItem);
                list.set(j, null);
                j = i--;
                continue;
            }
            Integer prefixLen = item.getPrefixLength();
            if (!Objects.equals(prefixLen, otherPrefixLen = otherItem.getPrefixLength())) {
                j = i--;
                continue;
            }
            int n = matchBitIndex = prefixLen == null ? bitCount - 1 : prefixLen - 1;
            if (matchBitIndex == 0) {
                lastBitSegmentIndex = 0;
                lastMatchSegmentIndex = 0;
            } else {
                lastMatchSegmentIndex = IPAddressSection.getNetworkSegmentIndex(matchBitIndex, bytesPerSegment, bitsPerSegment);
                lastBitSegmentIndex = IPAddressSection.getHostSegmentIndex(matchBitIndex, bytesPerSegment, bitsPerSegment);
            }
            IPAddressSegment itemSegment = item.getSegment(lastMatchSegmentIndex);
            IPAddressSegment otherItemSegment = otherItem.getSegment(lastMatchSegmentIndex);
            int itemSegmentValue = itemSegment.getSegmentValue();
            int otherItemSegmentValue = otherItemSegment.getSegmentValue();
            int segmentLastBitIndex = bitsPerSegment - 1;
            if (lastBitSegmentIndex == lastMatchSegmentIndex) {
                int segmentBitToCheck = matchBitIndex % bitsPerSegment;
                int shift = segmentLastBitIndex - segmentBitToCheck;
                itemSegmentValue >>>= shift;
                otherItemSegmentValue >>>= shift;
            } else {
                int itemBitValue = item.getSegment(lastBitSegmentIndex).getSegmentValue();
                int otherItemBitalue = otherItem.getSegment(lastBitSegmentIndex).getSegmentValue();
                itemSegmentValue = itemSegmentValue << 1 | itemBitValue >>> segmentLastBitIndex;
                otherItemSegmentValue = otherItemSegmentValue << 1 | otherItemBitalue >>> segmentLastBitIndex;
            }
            if (itemSegmentValue != otherItemSegmentValue && (itemSegmentValue ^= 1) != otherItemSegmentValue) {
                j = i--;
                continue;
            }
            for (int k3 = lastMatchSegmentIndex - 1; k3 >= 0; --k3) {
                int otherVal;
                itemSegment = item.getSegment(k3);
                otherItemSegment = otherItem.getSegment(k3);
                int val = itemSegment.getSegmentValue();
                if (val == (otherVal = otherItemSegment.getSegmentValue())) continue;
                j = i--;
                continue block0;
            }
            IPAddressSegmentSeries joinedItem = otherItem.toPrefixBlock(matchBitIndex);
            list.set(i, joinedItem);
            ++removedCount;
            for (k = j + 1; k < list.size() && list.get(k) == null; ++k) {
            }
            if (k < list.size()) {
                list.set(j, list.get(k));
                list.set(k, null);
                continue;
            }
            list.set(j, null);
            j = i--;
        }
        if (removedCount > 0) {
            int newSize = list.size() - removedCount;
            int k = 0;
            int l = 0;
            while (k < newSize) {
                while (list.get(l) == null) {
                    ++l;
                }
                if (k != l) {
                    list.set(k, list.get(l));
                }
                ++k;
                ++l;
            }
            int last = list.size();
            while (removedCount-- > 0) {
                list.remove(--last);
            }
        }
        return list;
    }

    private static boolean organizeSequentially(IPAddressSegmentSeries[] sections, List<IPAddressSegmentSeries> list) {
        IPAddressSegmentSeries[] span;
        IPAddressSegmentSeries series;
        int i;
        ArrayList<IPAddressSegmentSeries> sequentialList = null;
        for (i = 0; i < sections.length; ++i) {
            IPAddressSegmentSeries section = sections[i];
            if (section == null) continue;
            if (!section.isSequential()) {
                if (sequentialList == null) {
                    sequentialList = new ArrayList<IPAddressSegmentSeries>(sections.length);
                    for (int j = 0; j < i; ++j) {
                        IPAddressSegmentSeries series2 = sections[j];
                        if (series2 == null) continue;
                        sequentialList.add(series2);
                    }
                }
                Iterator<? extends IPAddressSegmentSeries> iterator2 = section.sequentialBlockIterator();
                while (iterator2.hasNext()) {
                    sequentialList.add(iterator2.next());
                }
                continue;
            }
            if (sequentialList == null) continue;
            sequentialList.add(section);
        }
        if (sequentialList == null) {
            for (i = 0; i < sections.length; ++i) {
                series = sections[i];
                if (series == null) continue;
                if (series.isSinglePrefixBlock()) {
                    list.add(series);
                    continue;
                }
                span = series.spanWithPrefixBlocks();
                for (int k = 0; k < span.length; ++k) {
                    list.add(span[k]);
                }
            }
        } else {
            for (int j = 0; j < sequentialList.size(); ++j) {
                series = (IPAddressSegmentSeries)sequentialList.get(j);
                if (series.isSinglePrefixBlock()) {
                    list.add(series);
                    continue;
                }
                span = series.spanWithPrefixBlocks();
                for (int k = 0; k < span.length; ++k) {
                    list.add(span[k]);
                }
            }
        }
        if (list.size() == 1) {
            return true;
        }
        list.sort(Address.ADDRESS_LOW_VALUE_COMPARATOR);
        return false;
    }

    private static boolean organizeSequentialMerge(IPAddressSegmentSeries[] sections, List<IPAddressSegmentSeries> list) {
        for (int i = 0; i < sections.length; ++i) {
            IPAddressSegmentSeries section = sections[i];
            if (section == null) continue;
            if (section.isSequential()) {
                list.add(section);
                continue;
            }
            Iterator<? extends IPAddressSegmentSeries> iterator2 = section.sequentialBlockIterator();
            while (iterator2.hasNext()) {
                list.add(iterator2.next());
            }
        }
        if (list.size() == 1) {
            return true;
        }
        list.sort(Address.ADDRESS_LOW_VALUE_COMPARATOR);
        return false;
    }

    protected List<? extends IPAddressSegmentSeries> spanWithBlocks(boolean prefixBlocks) {
        return IPAddress.spanWithBlocks(this, prefixBlocks);
    }

    protected static <T extends IPAddress, R extends IPAddressSection, S extends IPAddressSegment> R[] subtract(R first, R other, IPAddressNetwork.IPAddressCreator<T, R, ?, S, ?> addrCreator, IntFunction<S> segProducer, SegFunction<R, R> prefixApplier) {
        first.checkSectionCount(other);
        if (!first.isMultiple()) {
            if (other.contains(first)) {
                return null;
            }
            IPAddressSection[] result = addrCreator.createSectionArray(1);
            result[0] = first;
            return result;
        }
        int segCount = first.getSegmentCount();
        for (int i = 0; i < segCount; ++i) {
            IPAddressSegment seg = first.getSegment(i);
            IPAddressSegment otherSeg = other.getSegment(i);
            int lower = seg.getSegmentValue();
            int higher = seg.getUpperSegmentValue();
            int otherLower = otherSeg.getSegmentValue();
            int otherHigher = otherSeg.getUpperSegmentValue();
            if (otherLower <= higher && lower <= otherHigher) continue;
            IPAddressSection[] result = addrCreator.createSectionArray(1);
            result[0] = first;
            return result;
        }
        IPAddressSegment[] intersections = (IPAddressSegment[])addrCreator.createSegmentArray(segCount);
        ArrayList<IPAddressSection> sections = new ArrayList<IPAddressSection>();
        for (int i = 0; i < segCount; ++i) {
            IPAddressSection section;
            IPAddressSegment seg = (IPAddressSegment)segProducer.apply(i);
            IPAddressSegment otherSeg = other.getSegment(i);
            int lower = seg.getSegmentValue();
            int higher = seg.getUpperSegmentValue();
            int otherLower = otherSeg.getSegmentValue();
            int otherHigher = otherSeg.getUpperSegmentValue();
            if (lower >= otherLower) {
                if (higher <= otherHigher) {
                    if (seg.isPrefixed()) {
                        intersections[i] = (IPAddressSegment)addrCreator.createSegment(lower, higher, null);
                        continue;
                    }
                    intersections[i] = seg;
                    continue;
                }
                intersections[i] = (IPAddressSegment)addrCreator.createSegment(lower, otherHigher, null);
                section = IPAddressSection.createDiffSection(first, (int)(otherHigher + 1), (int)higher, (int)i, addrCreator, segProducer, (IPAddressSegment[])intersections);
                sections.add(section);
                continue;
            }
            section = IPAddressSection.createDiffSection(first, (int)lower, (int)(otherLower - 1), (int)i, addrCreator, segProducer, (IPAddressSegment[])intersections);
            sections.add(section);
            if (higher <= otherHigher) {
                intersections[i] = (IPAddressSegment)addrCreator.createSegment(otherLower, higher, null);
                continue;
            }
            intersections[i] = (IPAddressSegment)addrCreator.createSegment(otherLower, otherHigher, null);
            section = IPAddressSection.createDiffSection(first, (int)(otherHigher + 1), (int)higher, (int)i, addrCreator, segProducer, (IPAddressSegment[])intersections);
            sections.add(section);
        }
        if (sections.size() == 0) {
            return null;
        }
        if (first.isPrefixed()) {
            int thisPrefix = first.getNetworkPrefixLength();
            for (int i = 0; i < sections.size(); ++i) {
                int bitCount;
                IPAddressSection section = (IPAddressSection)sections.get(i);
                int totalPrefix = bitCount = section.getBitCount();
                for (int j = first.getSegmentCount() - 1; j >= 0; --j) {
                    IPAddressSegment seg = section.getSegment(j);
                    int segBitCount = seg.getBitCount();
                    int segPrefix = seg.getMinPrefixLengthForBlock();
                    if (segPrefix == segBitCount) break;
                    totalPrefix -= segBitCount;
                    if (segPrefix == 0) continue;
                    totalPrefix += segPrefix;
                    break;
                }
                if (totalPrefix == bitCount) continue;
                if (totalPrefix < thisPrefix) {
                    totalPrefix = thisPrefix;
                }
                section = (IPAddressSection)prefixApplier.apply(section, totalPrefix);
                sections.set(i, section);
            }
        }
        IPAddressSection[] result = addrCreator.createSectionArray(sections.size());
        sections.toArray(result);
        return result;
    }

    private static <T extends IPAddress, R extends IPAddressSection, S extends IPAddressSegment> R createDiffSection(R original, int lower, int upper, int diffIndex, IPAddressNetwork.IPAddressCreator<T, R, ?, S, ?> addrCreator, IntFunction<S> segProducer, S[] intersectingValues) {
        IPAddressSegment diff;
        int segCount = original.getSegmentCount();
        IPAddressSegment[] segments = (IPAddressSegment[])addrCreator.createSegmentArray(segCount);
        for (int j = 0; j < diffIndex; ++j) {
            segments[j] = intersectingValues[j];
        }
        segments[diffIndex] = diff = (IPAddressSegment)addrCreator.createSegment(lower, upper, null);
        for (int j = diffIndex + 1; j < segCount; ++j) {
            segments[j] = (IPAddressSegment)segProducer.apply(j);
        }
        IPAddressSection section = addrCreator.createSectionInternal(segments);
        return (R)section;
    }

    @Override
    public abstract IPAddressSection toZeroHost();

    @Override
    public abstract IPAddressSection toZeroHost(int var1);

    @Override
    public abstract IPAddressSection toZeroNetwork();

    @Override
    public abstract IPAddressSection toMaxHost();

    @Override
    public abstract IPAddressSection toMaxHost(int var1);

    @Override
    @Deprecated
    public abstract IPAddressSection applyPrefixLength(int var1) throws PrefixLenException;

    protected void checkSectionCount(IPAddressSection sec) throws SizeMismatchException {
        if (sec.getSegmentCount() != this.getSegmentCount()) {
            throw new SizeMismatchException(this, sec);
        }
    }

    protected void checkMaskSectionCount(IPAddressSection mask) throws SizeMismatchException {
        if (mask.getSegmentCount() < this.getSegmentCount()) {
            throw new SizeMismatchException(this, mask);
        }
    }

    @Override
    public abstract IPAddressSection coverWithPrefixBlock();

    @Override
    public abstract IPAddressSection toPrefixBlock();

    @Override
    public abstract IPAddressSection toPrefixBlock(int var1);

    @Override
    public IPAddressSection getHostMask() {
        Integer prefLen = this.getNetworkPrefixLength();
        return ((IPAddress)((IPAddressNetwork)this.getNetwork()).getHostMask(prefLen == null ? 0 : this.getNetworkPrefixLength())).getSection(0, this.getSegmentCount());
    }

    @Override
    public IPAddressSection getNetworkMask() {
        Integer prefLen = this.getNetworkPrefixLength();
        return ((IPAddress)((IPAddressNetwork)this.getNetwork()).getNetworkMask(prefLen == null ? this.getBitCount() : this.getNetworkPrefixLength().intValue())).getSection(0, this.getSegmentCount());
    }

    @Override
    public IPAddressSection assignPrefixForSingleBlock() {
        Integer newPrefix = this.getPrefixLengthForSingleBlock();
        if (newPrefix == null) {
            return null;
        }
        IPAddressSection result = this.setPrefixLength(newPrefix, false);
        if (result != this) {
            result.hasNoPrefixCache();
            PrefixCache cache = result.prefixCache;
            cache.cachedIsSinglePrefixBlock = true;
            cache.cachedEquivalentPrefix = newPrefix;
            cache.cachedMinPrefix = newPrefix;
        }
        return result;
    }

    @Override
    public IPAddressSection assignMinPrefixForBlock() {
        return this.setPrefixLength(this.getMinPrefixLengthForBlock(), false);
    }

    @Override
    public boolean includesZeroHost() {
        Integer networkPrefixLength = this.getNetworkPrefixLength();
        if (networkPrefixLength == null || networkPrefixLength >= this.getBitCount()) {
            return false;
        }
        return this.includesZeroHost(networkPrefixLength);
    }

    @Override
    public boolean includesZeroHost(int networkPrefixLength) {
        if (networkPrefixLength < 0 || networkPrefixLength > this.getBitCount()) {
            throw new PrefixLenException((AddressItem)this, networkPrefixLength);
        }
        if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() && this.isPrefixed() && this.getNetworkPrefixLength() <= networkPrefixLength) {
            return true;
        }
        int bitsPerSegment = this.getBitsPerSegment();
        int bytesPerSegment = this.getBytesPerSegment();
        int prefixedSegmentIndex = IPAddressSection.getHostSegmentIndex(networkPrefixLength, bytesPerSegment, bitsPerSegment);
        int divCount = this.getSegmentCount();
        for (int i = prefixedSegmentIndex; i < divCount; ++i) {
            IPAddressSegment div = this.getSegment(i);
            Integer segmentPrefixLength = IPAddressSection.getPrefixedSegmentPrefixLength(bitsPerSegment, networkPrefixLength, i);
            if (segmentPrefixLength == null) continue;
            int mask = div.getSegmentHostMask(segmentPrefixLength);
            if (((long)mask & div.getDivisionValue()) != 0L) {
                return false;
            }
            ++i;
            while (i < divCount) {
                div = this.getSegment(i);
                if (!div.includesZero()) {
                    return false;
                }
                ++i;
            }
        }
        return true;
    }

    @Override
    public boolean includesMaxHost() {
        Integer networkPrefixLength = this.getNetworkPrefixLength();
        if (networkPrefixLength == null || networkPrefixLength >= this.getBitCount()) {
            return false;
        }
        return this.includesMaxHost(networkPrefixLength);
    }

    @Override
    public boolean includesMaxHost(int networkPrefixLength) {
        if (networkPrefixLength < 0 || networkPrefixLength > this.getBitCount()) {
            throw new PrefixLenException((AddressItem)this, networkPrefixLength);
        }
        if (this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() && this.isPrefixed() && this.getNetworkPrefixLength() <= networkPrefixLength) {
            return true;
        }
        int bitsPerSegment = this.getBitsPerSegment();
        int bytesPerSegment = this.getBytesPerSegment();
        int prefixedSegmentIndex = IPAddressSection.getHostSegmentIndex(networkPrefixLength, bytesPerSegment, bitsPerSegment);
        int divCount = this.getSegmentCount();
        for (int i = prefixedSegmentIndex; i < divCount; ++i) {
            IPAddressSegment div = this.getSegment(i);
            Integer segmentPrefixLength = IPAddressSection.getPrefixedSegmentPrefixLength(bitsPerSegment, networkPrefixLength, i);
            if (segmentPrefixLength == null) continue;
            int mask = div.getSegmentHostMask(segmentPrefixLength);
            if ((mask & div.getUpperSegmentValue()) != mask) {
                return false;
            }
            ++i;
            while (i < divCount) {
                div = this.getSegment(i);
                if (!div.includesMax()) {
                    return false;
                }
                ++i;
            }
        }
        return true;
    }

    public boolean isSingleNetwork() {
        Integer networkPrefixLength = this.getNetworkPrefixLength();
        if (networkPrefixLength == null || networkPrefixLength >= this.getBitCount()) {
            return !this.isMultiple();
        }
        int prefixedSegmentIndex = IPAddressSection.getNetworkSegmentIndex(networkPrefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        if (prefixedSegmentIndex < 0) {
            return true;
        }
        for (int i = 0; i < prefixedSegmentIndex; ++i) {
            IPAddressSegment div = this.getSegment(i);
            if (!div.isMultiple()) continue;
            return false;
        }
        IPAddressSegment div = this.getSegment(prefixedSegmentIndex);
        int differing = div.getSegmentValue() ^ div.getUpperSegmentValue();
        if (differing == 0) {
            return true;
        }
        int bitsPerSegment = div.getBitCount();
        int highestDifferingBitInRange = Integer.numberOfLeadingZeros(differing) - (32 - bitsPerSegment);
        return IPAddressSection.getSegmentPrefixLength(bitsPerSegment, networkPrefixLength, prefixedSegmentIndex) <= highestDifferingBitInRange;
    }

    public boolean matchesWithMask(IPAddressSection other, IPAddressSection mask) {
        this.checkMaskSectionCount(mask);
        this.checkSectionCount(other);
        int divCount = this.getSegmentCount();
        for (int i = 0; i < divCount; ++i) {
            IPAddressSegment div = this.getSegment(i);
            IPAddressSegment maskSegment = mask.getSegment(i);
            IPAddressSegment otherSegment = other.getSegment(i);
            if (div.matchesWithMask(otherSegment.getSegmentValue(), otherSegment.getUpperSegmentValue(), maskSegment.getSegmentValue())) continue;
            return false;
        }
        return true;
    }

    @Override
    @Deprecated
    public abstract IPAddressSection removePrefixLength(boolean var1);

    @Override
    @Deprecated
    public abstract IPAddressSection removePrefixLength();

    @Override
    public abstract IPAddressSection withoutPrefixLength();

    protected static <T extends IPAddress, R extends IPAddressSection, S extends IPAddressSegment> R toPrefixBlock(R original, int networkPrefixLength, IPAddressNetwork.IPAddressCreator<T, R, ?, S, ?> creator, SegFunction<Integer, S> segProducer) {
        if (networkPrefixLength < 0 || networkPrefixLength > original.getBitCount()) {
            throw new PrefixLenException(original, networkPrefixLength);
        }
        if (original.isNetworkSubnet(networkPrefixLength)) {
            return original;
        }
        int bitsPerSegment = original.getBitsPerSegment();
        int segmentCount = original.getSegmentCount();
        IPAddressSegment[] result = (IPAddressSegment[])creator.createSegmentArray(segmentCount);
        for (int i = 0; i < segmentCount; ++i) {
            Integer prefix = IPAddressSection.getPrefixedSegmentPrefixLength(bitsPerSegment, networkPrefixLength, i);
            result[i] = (IPAddressSegment)segProducer.apply(prefix, i);
        }
        return (R)creator.createSectionInternal(result);
    }

    protected boolean isNetworkSubnet(int networkPrefixLength) {
        int segmentCount = this.getSegmentCount();
        if (segmentCount == 0) {
            return true;
        }
        int bitsPerSegment = this.getBitsPerSegment();
        int prefixedSegmentIndex = IPAddressSection.getHostSegmentIndex(networkPrefixLength, this.getBytesPerSegment(), bitsPerSegment);
        if (prefixedSegmentIndex >= segmentCount) {
            if (networkPrefixLength == this.getBitCount()) {
                IPAddressSegment last = this.getSegment(segmentCount - 1);
                return !last.isNetworkChangedByPrefixNonNull(last.getBitCount());
            }
            return true;
        }
        int segPrefLength = IPAddressSection.getPrefixedSegmentPrefixLength(bitsPerSegment, networkPrefixLength, prefixedSegmentIndex);
        if (this.getSegment(prefixedSegmentIndex).isNetworkChangedByPrefixNonNull(segPrefLength)) {
            return false;
        }
        if (!this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            for (int i = prefixedSegmentIndex + 1; i < segmentCount; ++i) {
                if (this.getSegment(i).isFullRange()) continue;
                return false;
            }
        }
        return true;
    }

    protected static <R extends IPAddressSection, S extends IPAddressSegment> R removePrefixLength(R original, boolean zeroed, IPAddressNetwork.IPAddressCreator<?, R, ?, S, ?> creator, SegFunction<R, S> segProducer) throws IncompatibleAddressException {
        if (!original.isPrefixed()) {
            return original;
        }
        AddressNetwork network = creator.getNetwork();
        Object mask = ((IPAddressNetwork)network).getNetworkMaskSection(zeroed ? original.getPrefixLength().intValue() : original.getBitCount());
        return IPAddressSection.getSubnetSegments(original, null, creator, zeroed, i -> (IPAddressSegment)segProducer.apply(original, i), i -> ((IPAddressSegment)segProducer.apply(mask, i)).getSegmentValue(), false);
    }

    @Override
    public IPAddressSection adjustPrefixBySegment(boolean nextSegment, boolean zeroed) {
        int prefix = this.getAdjustedPrefix(nextSegment, this.getBitsPerSegment(), false);
        Integer existing = this.getNetworkPrefixLength();
        if (existing == null) {
            if (nextSegment ? prefix == this.getBitCount() : prefix == 0) {
                return this;
            }
        } else if (existing != null && existing == prefix && prefix != 0) {
            return this.removePrefixLength(zeroed);
        }
        return this.setPrefixLength(prefix, zeroed);
    }

    @Override
    public abstract IPAddressSection adjustPrefixBySegment(boolean var1);

    @Override
    public abstract IPAddressSection adjustPrefixLength(int var1);

    @Override
    public abstract IPAddressSection adjustPrefixLength(int var1, boolean var2);

    protected static <R extends IPAddressSection, S extends IPAddressSegment> IPAddressSection adjustPrefixLength(R original, int adjustment, boolean withZeros, IPAddressNetwork.IPAddressCreator<?, R, ?, S, ?> creator, SegFunction<R, S> segProducer) throws IncompatibleAddressException {
        if (adjustment == 0 && original.isPrefixed()) {
            return original;
        }
        int prefix = original.getAdjustedPrefix(adjustment, false, false);
        if (prefix > original.getBitCount()) {
            if (!original.isPrefixed()) {
                return original;
            }
            AddressNetwork network = creator.getNetwork();
            Object mask = ((IPAddressNetwork)network).getNetworkMaskSection(withZeros ? original.getPrefixLength().intValue() : original.getBitCount());
            return IPAddressSection.getSubnetSegments(original, null, creator, withZeros, i -> (IPAddressSegment)segProducer.apply(original, i), i -> ((IPAddressSegment)segProducer.apply(mask, i)).getSegmentValue(), false);
        }
        if (prefix < 0) {
            prefix = 0;
        }
        return original.setPrefixLength(prefix, withZeros);
    }

    @Override
    public abstract IPAddressSection setPrefixLength(int var1);

    @Override
    public abstract IPAddressSection setPrefixLength(int var1, boolean var2);

    public abstract IPAddressSection setPrefixLength(int var1, boolean var2, boolean var3);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean hasNoPrefixCache() {
        if (this.prefixCache == null) {
            IPAddressSection iPAddressSection = this;
            synchronized (iPAddressSection) {
                if (this.prefixCache == null) {
                    this.prefixCache = new PrefixCache();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getMinPrefixLengthForBlock() {
        Integer result;
        if (this.hasNoPrefixCache() || (result = this.prefixCache.cachedMinPrefix) == null) {
            result = IPAddressSection.cacheBits(super.getMinPrefixLengthForBlock());
            this.prefixCache.cachedMinPrefix = result;
        }
        return result;
    }

    @Override
    public Integer getPrefixLengthForSingleBlock() {
        Integer result;
        if (!this.hasNoPrefixCache() && (result = this.prefixCache.cachedEquivalentPrefix) != null) {
            if (result < 0) {
                return null;
            }
            return result;
        }
        Integer res = super.getPrefixLengthForSingleBlock();
        if (res == null) {
            this.prefixCache.cachedEquivalentPrefix = IPAddressSection.NO_PREFIX_LENGTH;
            this.prefixCache.cachedIsSinglePrefixBlock = false;
            return null;
        }
        if (this.isPrefixed() && res.equals(this.getNetworkPrefixLength())) {
            this.prefixCache.cachedIsSinglePrefixBlock = true;
        }
        this.prefixCache.cachedEquivalentPrefix = res;
        return res;
    }

    @Override
    public abstract IPAddressSection getLowerNonZeroHost();

    @Override
    public abstract IPAddressSection getLower();

    @Override
    public abstract IPAddressSection getUpper();

    @Override
    public abstract IPAddressSection reverseSegments();

    @Override
    public abstract IPAddressSection reverseBits(boolean var1);

    @Override
    public abstract IPAddressSection reverseBytes();

    @Override
    public abstract IPAddressSection reverseBytesPerSegment();

    protected IPAddressSegment[] getSegmentsInternal() {
        return (IPAddressSegment[])this.getDivisionsInternal();
    }

    @Override
    public abstract IPAddressSection getSection(int var1);

    @Override
    public abstract IPAddressSection getSection(int var1, int var2);

    @Override
    public void getSegments(AddressSegment[] segs) {
        this.getSegments(0, this.getDivisionCount(), segs, 0);
    }

    @Override
    public void getSegments(int start2, int end, AddressSegment[] segs, int destIndex) {
        System.arraycopy(this.getDivisionsInternal(), start2, segs, destIndex, end - start2);
    }

    protected static <T extends IPAddress, R extends IPAddressSection, S extends IPAddressSegment> R createEmbeddedSection(IPAddressNetwork.IPAddressCreator<T, R, ?, S, ?> creator, S[] segs, IPAddressSection encompassingSection) {
        return (R)creator.createEmbeddedSectionInternal(encompassingSection, (IPAddressSegment[])segs);
    }

    public abstract Iterable<? extends IPAddressSection> getIterable();

    public abstract Iterator<? extends IPAddressSection> nonZeroHostIterator();

    public abstract Iterator<? extends IPAddressSection> iterator();

    @Override
    public abstract AddressComponentSpliterator<? extends IPAddressSection> spliterator();

    public abstract Stream<? extends IPAddressSection> stream();

    public abstract Iterator<? extends IPAddressSection> prefixIterator();

    public abstract AddressComponentSpliterator<? extends IPAddressSection> prefixSpliterator();

    public abstract Stream<? extends IPAddressSection> prefixStream();

    public abstract Iterator<? extends IPAddressSection> prefixBlockIterator();

    public abstract AddressComponentSpliterator<? extends IPAddressSection> prefixBlockSpliterator();

    public abstract Stream<? extends IPAddressSection> prefixBlockStream();

    public abstract Iterator<? extends IPAddressSection> blockIterator(int var1);

    public abstract AddressComponentSpliterator<? extends IPAddressSection> blockSpliterator(int var1);

    public abstract Stream<? extends IPAddressSection> blockStream(int var1);

    public Iterator<? extends IPAddressSection> sequentialBlockIterator() {
        return this.blockIterator(this.getSequentialBlockIndex());
    }

    public AddressComponentSpliterator<? extends IPAddressSection> sequentialBlockSpliterator() {
        return this.blockSpliterator(this.getSequentialBlockIndex());
    }

    public Stream<? extends IPAddressSection> sequentialBlockStream() {
        return this.blockStream(this.getSequentialBlockIndex());
    }

    @Override
    public BigInteger getSequentialBlockCount() {
        int sequentialSegCount = this.getSequentialBlockIndex();
        return this.getPrefixCount(sequentialSegCount * this.getBitsPerSegment());
    }

    static <S extends AddressSegment> Iterator<S[]> iterator(int divCount, AddressNetwork.AddressSegmentCreator<S> segmentCreator, IntFunction<Iterator<S>> segIteratorProducer, int networkSegmentIndex, int hostSegmentIndex, IntFunction<Iterator<S>> prefixedSegIteratorProducer) {
        return IPAddressSection.segmentsIterator(divCount, segmentCreator, null, segIteratorProducer, null, networkSegmentIndex, hostSegmentIndex, prefixedSegIteratorProducer);
    }

    static <T extends Address, S extends AddressSegment> Iterator<T> iterator(T original, AddressCreator<T, ?, ?, S> creator, Iterator<S[]> iterator2) {
        return IPAddressSection.iterator(original != null, original, creator, iterator2, null);
    }

    @Override
    public abstract IPAddressSection increment(long var1);

    @Override
    public abstract IPAddressSection incrementBoundary(long var1);

    public boolean isEntireAddress() {
        return this.getSegmentCount() == IPAddress.getSegmentCount(this.getIPVersion());
    }

    protected boolean isMultiple(int segmentCount) {
        for (int i = 0; i < segmentCount; ++i) {
            if (!this.getSegment(i).isMultiple()) continue;
            return true;
        }
        return false;
    }

    public boolean isZeroHost() {
        if (!this.isPrefixed()) {
            return false;
        }
        return this.isZeroHost(this.getNetworkPrefixLength());
    }

    public boolean isZeroHost(int prefixLength) {
        if (prefixLength < 0 || prefixLength > this.getBitCount()) {
            throw new PrefixLenException((AddressItem)this, prefixLength);
        }
        return IPAddressSection.isZeroHost((int)prefixLength, (IPAddressSegment[])this.getSegments(), (int)this.getBytesPerSegment(), (int)this.getBitsPerSegment(), (int)this.getBitCount());
    }

    protected <S extends IPAddressSegment> boolean isZeroHost(S[] segments) {
        if (!this.isPrefixed()) {
            return false;
        }
        return IPAddressSection.isZeroHost((int)this.getNetworkPrefixLength(), segments, (int)this.getBytesPerSegment(), (int)this.getBitsPerSegment(), (int)this.getBitCount());
    }

    protected <S extends IPAddressSegment> boolean isZeroHost(S[] segments, int prefixLength) {
        return IPAddressSection.isZeroHost((int)prefixLength, segments, (int)this.getBytesPerSegment(), (int)this.getBitsPerSegment(), (int)this.getBitCount());
    }

    protected static <S extends IPAddressSegment> boolean isZeroHost(int prefLen, S[] segments, int bytesPerSegment, int bitsPerSegment, int bitCount) {
        int prefixedSegmentIndex;
        if (segments.length == 0) {
            return false;
        }
        if (prefLen >= bitCount) {
            return false;
        }
        int divCount = segments.length;
        for (int i = prefixedSegmentIndex = IPAddressSection.getHostSegmentIndex(prefLen, bytesPerSegment, bitsPerSegment); i < divCount; ++i) {
            Integer segmentPrefixLength = IPAddressSection.getPrefixedSegmentPrefixLength(bitsPerSegment, prefLen, prefixedSegmentIndex);
            S div = segments[i];
            if (segmentPrefixLength == null) continue;
            int mask = ((IPAddressSegment)div).getSegmentHostMask(segmentPrefixLength);
            if (((IPAddressSegment)div).isMultiple() || (mask & ((IPAddressSegment)div).getSegmentValue()) != 0) {
                return false;
            }
            ++i;
            while (i < divCount) {
                div = segments[i];
                if (!((AddressDivision)div).isZero()) {
                    return false;
                }
                ++i;
            }
        }
        return true;
    }

    InetAddress toInetAddress(IPAddress address) {
        InetAddress result;
        if (this.hasNoValueCache() || (result = this.valueCache.inetAddress) == null) {
            this.valueCache.inetAddress = result = address.toInetAddressImpl();
        }
        return result;
    }

    static void checkLengths(int length, StringBuilder builder) {
        AddressDivisionGroupingBase.IPAddressStringParams.checkLengths(length, builder);
    }

    @Override
    public String toString() {
        return this.toNormalizedString();
    }

    @Override
    public String[] getSegmentStrings() {
        return this.getDivisionStrings();
    }

    protected abstract void cacheNormalizedString(String var1);

    protected abstract IPStringCache getStringCache();

    protected abstract boolean hasNoStringCache();

    @Override
    public String toBinaryString() throws IncompatibleAddressException {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().binaryString) == null) {
            IPStringCache stringCache = this.getStringCache();
            stringCache.binaryString = result = this.toBinaryString(null);
        }
        return result;
    }

    protected String toBinaryString(CharSequence zone) {
        if (this.isDualString()) {
            return IPAddressSection.toNormalizedStringRange(IPAddressSection.toIPParams(IPStringCache.binaryParams), this.getLower(), this.getUpper(), zone);
        }
        return IPAddressSection.toIPParams(IPStringCache.binaryParams).toString((IPAddressStringDivisionSeries)this, zone);
    }

    @Override
    public String toOctalString(boolean with0Prefix) throws IncompatibleAddressException {
        String result;
        if (this.hasNoStringCache() || (result = with0Prefix ? this.getStringCache().octalStringPrefixed : this.getStringCache().octalString) == null) {
            IPStringCache stringCache = this.getStringCache();
            result = this.toOctalString(with0Prefix, null);
            if (with0Prefix) {
                stringCache.octalStringPrefixed = result;
            } else {
                stringCache.octalString = result;
            }
        }
        return result;
    }

    protected String toOctalString(boolean with0Prefix, CharSequence zone) throws IncompatibleAddressException {
        if (this.isDualString()) {
            IPAddressSection lower = this.getLower();
            IPAddressSection upper = this.getUpper();
            IPAddressDivision[] lowerDivs = (IPAddressBitsDivision[])lower.createNewDivisions(3, IPAddressBitsDivision::new, IPAddressBitsDivision[]::new);
            IPAddressDivisionGrouping lowerPart = new IPAddressDivisionGrouping(lowerDivs, (IPAddressNetwork<?, ?, ?, ?, ?>)this.getNetwork());
            IPAddressDivision[] upperDivs = (IPAddressBitsDivision[])upper.createNewDivisions(3, IPAddressBitsDivision::new, IPAddressBitsDivision[]::new);
            IPAddressDivisionGrouping upperPart = new IPAddressDivisionGrouping(upperDivs, (IPAddressNetwork<?, ?, ?, ?, ?>)this.getNetwork());
            return IPAddressSection.toNormalizedStringRange(IPAddressSection.toIPParams(with0Prefix ? IPStringCache.octalPrefixedParams : IPStringCache.octalParams), lowerPart, upperPart, zone);
        }
        IPAddressDivision[] divs = (IPAddressBitsDivision[])this.createNewPrefixedDivisions(3, null, null, IPAddressBitsDivision::new, IPAddressBitsDivision[]::new);
        IPAddressDivisionGrouping part = new IPAddressDivisionGrouping(divs, (IPAddressNetwork<?, ?, ?, ?, ?>)this.getNetwork());
        return IPAddressSection.toIPParams(with0Prefix ? IPStringCache.octalPrefixedParams : IPStringCache.octalParams).toString((IPAddressStringDivisionSeries)part, zone);
    }

    @Override
    public String toHexString(boolean with0xPrefix) throws IncompatibleAddressException {
        String result;
        if (this.hasNoStringCache() || (result = with0xPrefix ? this.getStringCache().hexStringPrefixed : this.getStringCache().hexString) == null) {
            IPStringCache stringCache = this.getStringCache();
            result = this.toHexString(with0xPrefix, null);
            if (with0xPrefix) {
                stringCache.hexStringPrefixed = result;
            } else {
                stringCache.hexString = result;
            }
        }
        return result;
    }

    protected String toHexString(boolean with0xPrefix, CharSequence zone) throws IncompatibleAddressException {
        if (this.isDualString()) {
            return IPAddressSection.toNormalizedStringRange(IPAddressSection.toIPParams(with0xPrefix ? IPStringCache.hexPrefixedParams : IPStringCache.hexParams), this.getLower(), this.getUpper(), zone);
        }
        return IPAddressSection.toIPParams(with0xPrefix ? IPStringCache.hexPrefixedParams : IPStringCache.hexParams).toString((IPAddressStringDivisionSeries)this, zone);
    }

    @Override
    public String toNormalizedString(IPStringOptions stringOptions) {
        return IPAddressSection.toNormalizedString(stringOptions, this);
    }

    public static String toNormalizedString(IPStringOptions opts, IPAddressStringDivisionSeries section) {
        return IPAddressSection.toIPParams(opts).toString(section);
    }

    protected static AddressDivisionGroupingBase.IPAddressStringParams<IPAddressStringDivisionSeries> toIPParams(IPStringOptions opts) {
        AddressDivisionGroupingBase.IPAddressStringParams result = (AddressDivisionGroupingBase.IPAddressStringParams)IPAddressSection.getCachedParams(opts);
        if (result == null) {
            result = new AddressDivisionGroupingBase.IPAddressStringParams(opts.base, opts.separator, opts.uppercase);
            result.expandSegments(opts.expandSegments);
            result.setWildcards(opts.wildcards);
            result.setWildcardOption(opts.wildcardOption);
            result.setSegmentStrPrefix(opts.segmentStrPrefix);
            result.setAddressSuffix(opts.addrSuffix);
            result.setAddressLabel(opts.addrLabel);
            result.setReverse(opts.reverse);
            result.setSplitDigits(opts.splitDigits);
            result.setZoneSeparator(opts.zoneSeparator);
            IPAddressSection.setCachedParams(opts, result);
        }
        return result;
    }

    public IPAddressPartStringCollection toStandardStringCollection() {
        return this.toStringCollection(new IPStringBuilderOptions(16));
    }

    public IPAddressPartStringCollection toAllStringCollection() {
        return this.toStringCollection(new IPStringBuilderOptions(48));
    }

    public IPAddressPartStringCollection toDatabaseSearchStringCollection() {
        return this.toStringCollection(new IPStringBuilderOptions());
    }

    public IPAddressStringDivisionSeries[] getParts(IPStringBuilderOptions options) {
        if (options.includes(1)) {
            return new IPAddressStringDivisionSeries[]{this};
        }
        return EMPTY_PARTS;
    }

    public void getStartsWithSQLClause(StringBuilder builder, String expression) {
        this.getStartsWithSQLClause(builder, expression, new MySQLTranslator());
    }

    public void getStartsWithSQLClause(StringBuilder builder, String expression, IPAddressSQLTranslator translator) {
        this.getStartsWithSQLClause(builder, expression, true, translator);
    }

    private void getStartsWithSQLClause(StringBuilder builder, String expression, boolean isFirstCall, IPAddressSQLTranslator translator) {
        if (isFirstCall && this.isMultiple()) {
            Iterator<? extends IPAddressSection> sectionIterator = this.iterator();
            builder.append('(');
            boolean isNotFirst = false;
            while (sectionIterator.hasNext()) {
                if (isNotFirst) {
                    builder.append(" OR ");
                } else {
                    isNotFirst = true;
                }
                IPAddressSection next = sectionIterator.next();
                next.getStartsWithSQLClause(builder, expression, false, translator);
            }
            builder.append(')');
        } else if (this.getSegmentCount() > 0) {
            IPAddressPartStringCollection createdStringCollection = this.toDatabaseSearchStringCollection();
            boolean isNotFirst = false;
            if (createdStringCollection.size() > 1) {
                builder.append('(');
            }
            boolean isEntireAddress = this.isEntireAddress();
            for (IPAddressPartConfiguredString<?, ?> createdStr : createdStringCollection) {
                if (isNotFirst) {
                    builder.append(" OR ");
                } else {
                    isNotFirst = true;
                }
                SQLStringMatcher matcher = createdStr.getNetworkStringMatcher(isEntireAddress, translator);
                matcher.getSQLCondition(builder.append('('), expression).append(')');
            }
            if (createdStringCollection.size() > 1) {
                builder.append(')');
            }
        }
    }

    public static class IPStringBuilderOptions {
        public static final int BASIC = 1;
        public static final int LEADING_ZEROS_FULL_ALL_SEGMENTS = 16;
        public static final int LEADING_ZEROS_FULL_SOME_SEGMENTS = 48;
        public static final int LEADING_ZEROS_PARTIAL_SOME_SEGMENTS = 112;
        public final int options;

        public IPStringBuilderOptions() {
            this(1);
        }

        public IPStringBuilderOptions(int options) {
            this.options = options;
        }

        public boolean includes(int option) {
            return (option & this.options) == option;
        }

        public boolean includesAny(int option) {
            return (option & this.options) != 0;
        }

        public String toString() {
            Field[] fields;
            TreeMap<Integer, String> options = new TreeMap<Integer, String>();
            for (Field field2 : fields = this.getClass().getFields()) {
                int modifiers = field2.getModifiers();
                if (!Modifier.isFinal(modifiers) || !Modifier.isStatic(modifiers)) continue;
                try {
                    int constant = field2.getInt(null);
                    String option = field2.getName() + ": " + this.includes(constant) + System.lineSeparator();
                    options.put(constant, option);
                }
                catch (IllegalAccessException illegalAccessException) {
                    // empty catch block
                }
            }
            Collection values2 = options.values();
            StringBuilder builder = new StringBuilder();
            for (String val : values2) {
                builder.append(val);
            }
            return builder.toString();
        }
    }

    public static class IPStringOptions
    extends AddressDivisionGrouping.StringOptions {
        public final String addrSuffix;
        public final WildcardOptions.WildcardOption wildcardOption;
        public final char zoneSeparator;

        protected IPStringOptions(int base, boolean expandSegments, WildcardOptions.WildcardOption wildcardOption, AddressDivisionGrouping.StringOptions.Wildcards wildcards, String segmentStrPrefix, Character separator, char zoneSeparator, String label, String suffix, boolean reverse, boolean splitDigits, boolean uppercase) {
            super(base, expandSegments, wildcards, segmentStrPrefix, separator, label, reverse, splitDigits, uppercase);
            this.addrSuffix = suffix;
            this.wildcardOption = wildcardOption;
            this.zoneSeparator = zoneSeparator;
        }

        public static class Builder
        extends AddressDivisionGrouping.StringOptions.Builder {
            protected String addrSuffix = "";
            protected WildcardOptions.WildcardOption wildcardOption = WildcardOptions.WildcardOption.NETWORK_ONLY;
            protected char zoneSeparator = (char)37;

            public Builder(int base) {
                this(base, ' ');
            }

            protected Builder(int base, char separator) {
                super(base, separator);
            }

            @Override
            public Builder setWildcards(AddressDivisionGrouping.StringOptions.Wildcards wildcards) {
                return (Builder)super.setWildcards(wildcards);
            }

            public Builder setAddressSuffix(String suffix) {
                this.addrSuffix = suffix;
                return this;
            }

            public Builder setWildcardOptions(WildcardOptions wildcardOptions) {
                this.setWildcardOption(wildcardOptions.wildcardOption);
                return this.setWildcards(wildcardOptions.wildcards);
            }

            public Builder setWildcardOption(WildcardOptions.WildcardOption wildcardOption) {
                this.wildcardOption = wildcardOption;
                return this;
            }

            @Override
            public Builder setReverse(boolean reverse) {
                return (Builder)super.setReverse(reverse);
            }

            @Override
            public Builder setUppercase(boolean uppercase) {
                return (Builder)super.setUppercase(uppercase);
            }

            @Override
            public Builder setSplitDigits(boolean splitDigits) {
                return (Builder)super.setSplitDigits(splitDigits);
            }

            @Override
            public Builder setExpandedSegments(boolean expandSegments) {
                return (Builder)super.setExpandedSegments(expandSegments);
            }

            @Override
            public Builder setRadix(int base) {
                return (Builder)super.setRadix(base);
            }

            @Override
            public Builder setSeparator(Character separator) {
                return (Builder)super.setSeparator(separator);
            }

            public Builder setZoneSeparator(char separator) {
                this.zoneSeparator = separator;
                return this;
            }

            @Override
            public Builder setAddressLabel(String label) {
                return (Builder)super.setAddressLabel(label);
            }

            @Override
            public Builder setSegmentStrPrefix(String prefix) {
                return (Builder)super.setSegmentStrPrefix(prefix);
            }

            @Override
            public IPStringOptions toOptions() {
                return new IPStringOptions(this.base, this.expandSegments, this.wildcardOption, this.wildcards, this.segmentStrPrefix, this.separator, this.zoneSeparator, this.addrLabel, this.addrSuffix, this.reverse, this.splitDigits, this.uppercase);
            }
        }
    }

    public static class WildcardOptions {
        public final WildcardOption wildcardOption;
        public final AddressDivisionGrouping.StringOptions.Wildcards wildcards;

        public WildcardOptions() {
            this(WildcardOption.NETWORK_ONLY);
        }

        public WildcardOptions(WildcardOption wildcardOption) {
            this(wildcardOption, new AddressDivisionGrouping.StringOptions.Wildcards());
        }

        public WildcardOptions(WildcardOption wildcardOption, AddressDivisionGrouping.StringOptions.Wildcards wildcards) {
            this.wildcardOption = wildcardOption;
            this.wildcards = wildcards;
        }

        public static enum WildcardOption {
            NETWORK_ONLY,
            ALL;

        }
    }

    protected static class IPStringCache
    extends AddressDivisionGrouping.StringCache {
        public static final IPStringOptions hexParams;
        public static final IPStringOptions hexPrefixedParams;
        public static final IPStringOptions octalParams;
        public static final IPStringOptions octalPrefixedParams;
        public static final IPStringOptions binaryParams;
        public static final IPStringOptions canonicalSegmentParams;
        public String normalizedWildcardString;
        public String fullString;
        public String sqlWildcardString;
        public String reverseDNSString;
        public String octalStringPrefixed;
        public String octalString;
        public String binaryString;
        public String segmentedBinaryString;

        protected IPStringCache() {
        }

        static {
            WildcardOptions allWildcards = new WildcardOptions(WildcardOptions.WildcardOption.ALL);
            hexParams = new IPStringOptions.Builder(16).setSeparator(null).setExpandedSegments(true).setWildcardOptions(allWildcards).toOptions();
            hexPrefixedParams = new IPStringOptions.Builder(16).setSeparator(null).setExpandedSegments(true).setWildcardOptions(allWildcards).setAddressLabel("0x").toOptions();
            octalParams = new IPStringOptions.Builder(8).setSeparator(null).setExpandedSegments(true).setWildcardOptions(allWildcards).toOptions();
            octalPrefixedParams = new IPStringOptions.Builder(8).setSeparator(null).setExpandedSegments(true).setWildcardOptions(allWildcards).setAddressLabel("0").toOptions();
            binaryParams = new IPStringOptions.Builder(2).setSeparator(null).setExpandedSegments(true).setWildcardOptions(allWildcards).toOptions();
            canonicalSegmentParams = new IPStringOptions.Builder(10, ' ').toOptions();
        }
    }

    static class IPAddressSeqRangePrefixSpliterator<S extends AddressComponentRange>
    extends IPAddressSeqRangeSpliterator<S, S>
    implements AddressComponentSpliterator<S> {
        IPAddressSeqRangePrefixSpliterator(S forIteration, Predicate<IPAddressSeqRange.IPAddressSeqRangeSplitterSink<S, S>> splitter, SeqRangeIteratorProvider<S, S> iteratorProvider, ToLongFunction<S> longSizer) {
            super(forIteration, splitter, iteratorProvider, longSizer);
        }

        IPAddressSeqRangePrefixSpliterator(S forIteration, Predicate<IPAddressSeqRange.IPAddressSeqRangeSplitterSink<S, S>> splitter, SeqRangeIteratorProvider<S, S> iteratorProvider, Function<S, BigInteger> sizer, Predicate<S> downSizer, ToLongFunction<S> longSizer) {
            super(forIteration, splitter, iteratorProvider, sizer, downSizer, longSizer);
        }

        IPAddressSeqRangePrefixSpliterator(S forIteration, Predicate<IPAddressSeqRange.IPAddressSeqRangeSplitterSink<S, S>> splitter, SeqRangeIteratorProvider<S, S> iteratorProvider, boolean isLowest, Function<S, BigInteger> sizer, Predicate<S> downSizer, ToLongFunction<S> longSizer) {
            super(forIteration, splitter, iteratorProvider, isLowest, sizer, downSizer, longSizer);
        }

        protected IPAddressSeqRangePrefixSpliterator<S> createSpliterator(S split, boolean isLowest, Function<S, BigInteger> sizer, Predicate<S> downSizer, ToLongFunction<S> longSizer) {
            return new IPAddressSeqRangePrefixSpliterator<S>(split, this.splitter, (SeqRangeIteratorProvider)this.iteratorProvider, isLowest, sizer, downSizer, longSizer);
        }

        @Override
        public IPAddressSeqRangePrefixSpliterator<S> trySplit() {
            return (IPAddressSeqRangePrefixSpliterator)super.trySplit();
        }
    }

    static class IPAddressSeqRangeSpliterator<S extends AddressComponentRange, T>
    extends AddressDivisionGroupingBase.AddressItemRangeSpliterator<S, T>
    implements IPAddressSeqRange.IPAddressSeqRangeSplitterSink<S, T> {
        final Predicate<IPAddressSeqRange.IPAddressSeqRangeSplitterSink<S, T>> splitter;

        IPAddressSeqRangeSpliterator(S forIteration, Predicate<IPAddressSeqRange.IPAddressSeqRangeSplitterSink<S, T>> splitter, SeqRangeIteratorProvider<S, T> iteratorProvider, ToLongFunction<S> longSizer) {
            super(forIteration, null, iteratorProvider, null, null, longSizer);
            this.splitter = splitter;
        }

        IPAddressSeqRangeSpliterator(S forIteration, Predicate<IPAddressSeqRange.IPAddressSeqRangeSplitterSink<S, T>> splitter, SeqRangeIteratorProvider<S, T> iteratorProvider, Function<S, BigInteger> sizer, Predicate<S> downSizer, ToLongFunction<S> longSizer) {
            super(forIteration, null, iteratorProvider, sizer, downSizer, longSizer);
            this.splitter = splitter;
        }

        IPAddressSeqRangeSpliterator(S forIteration, Predicate<IPAddressSeqRange.IPAddressSeqRangeSplitterSink<S, T>> splitter, SeqRangeIteratorProvider<S, T> iteratorProvider, boolean isLowest, Function<S, BigInteger> sizer, Predicate<S> downSizer, ToLongFunction<S> longSizer) {
            super(forIteration, null, iteratorProvider, isLowest, false, sizer, downSizer, longSizer);
            this.splitter = splitter;
        }

        @Override
        protected boolean split() {
            return this.splitter.test(this);
        }

        @Override
        protected IPAddressSeqRangeSpliterator<S, T> createSpliterator(S split, boolean isLowest, Function<S, BigInteger> sizer, Predicate<S> downSizer, ToLongFunction<S> longSizer) {
            return new IPAddressSeqRangeSpliterator<S, T>(split, this.splitter, (SeqRangeIteratorProvider)this.iteratorProvider, isLowest, sizer, downSizer, longSizer);
        }
    }

    @FunctionalInterface
    static interface SeqRangeIteratorProvider<S, T>
    extends AddressDivisionGroupingBase.IteratorProvider<S, T> {
    }

    static class SeriesStack {
        int stackSize;
        int top;
        int capacity;
        IPAddressSegmentSeries[] seriesPairs;
        int[] indexPairs;
        IPAddressSegmentSeries lower;
        IPAddressSegmentSeries upper;
        int previousSegmentBits;
        int currentSegment;

        SeriesStack(int initialCapacity) {
            this.capacity = 2 * initialCapacity;
        }

        void push(IPAddressSegmentSeries lower, IPAddressSegmentSeries upper, int previousSegmentBits, int currentSegment) {
            int top = this.top;
            if (top >= this.stackSize) {
                this.resize();
            }
            IPAddressSegmentSeries[] seriesPairs = this.seriesPairs;
            int[] indexPairs = this.indexPairs;
            seriesPairs[top] = lower;
            indexPairs[top++] = previousSegmentBits;
            seriesPairs[top] = upper;
            indexPairs[top++] = currentSegment;
            this.top = top;
        }

        boolean pop() {
            if (this.top <= 0) {
                return false;
            }
            IPAddressSegmentSeries[] seriesPairs = this.seriesPairs;
            int[] indexPairs = this.indexPairs;
            int top = this.top;
            this.currentSegment = indexPairs[--top];
            this.upper = seriesPairs[top];
            this.previousSegmentBits = indexPairs[--top];
            this.lower = seriesPairs[top];
            this.top = top;
            return true;
        }

        void resize() {
            int size = this.stackSize;
            size = size == 0 ? this.capacity : (size <<= 1);
            IPAddressSegmentSeries[] newSeriesPairs = new IPAddressSegmentSeries[size];
            int[] newIndexPairs = new int[size];
            if (this.top > 0) {
                System.arraycopy(this.seriesPairs, 0, newSeriesPairs, 0, this.top);
                System.arraycopy(this.indexPairs, 0, newIndexPairs, 0, this.top);
            }
            this.seriesPairs = newSeriesPairs;
            this.indexPairs = newIndexPairs;
            this.stackSize = size;
        }
    }

    @FunctionalInterface
    public static interface SeriesCreator {
        public IPAddressSegmentSeries apply(IPAddressSegmentSeries var1, int var2, int var3, int var4);
    }

    @FunctionalInterface
    public static interface TriFunction<R, S> {
        public S apply(R var1, R var2, R var3);
    }

    @FunctionalInterface
    public static interface SegFunction<R, S> {
        public S apply(R var1, int var2);
    }

    protected static class PrefixCache {
        private Integer networkMaskPrefixLen;
        private Integer hostMaskPrefixLen;
        private Integer cachedMinPrefix;
        private Integer cachedEquivalentPrefix;
        private Boolean cachedIsSinglePrefixBlock;

        protected PrefixCache() {
        }
    }
}

