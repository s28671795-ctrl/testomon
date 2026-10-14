/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.standard;

import inet.ipaddr.Address;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressSection;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.AddressSegmentSeries;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.NetworkMismatchException;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.AddressDivisionGroupingBase;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.standard.AddressCreator;
import inet.ipaddr.format.standard.AddressDivision;
import inet.ipaddr.format.standard.IPAddressDivision;
import inet.ipaddr.format.string.AddressStringDivisionSeries;
import inet.ipaddr.format.validate.ParsedAddressGrouping;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AddressDivisionGrouping
extends AddressDivisionGroupingBase {
    private static final long serialVersionUID = 4L;

    public AddressDivisionGrouping(AddressDivision[] divisions) {
        super(divisions);
    }

    public AddressDivisionGrouping(AddressDivision[] divisions, boolean checkDivisions) {
        super(divisions, checkDivisions);
    }

    @Override
    public AddressDivision getDivision(int index) {
        return (AddressDivision)super.getDivision(index);
    }

    @Override
    protected byte[] getBytesImpl(boolean low) {
        byte[] bytes = new byte[this.getBitCount() + 7 >> 3];
        int byteCount = bytes.length;
        int divCount = this.getDivisionCount();
        int byteIndex = byteCount - 1;
        int bitIndex = 8;
        block0: for (int k = divCount - 1; k >= 0; --k) {
            AddressDivision div = this.getDivision(k);
            long segmentValue = low ? div.getDivisionValue() : div.getUpperDivisionValue();
            for (int divBits = div.getBitCount(); divBits > 0; divBits -= bitIndex) {
                int n = byteIndex--;
                bytes[n] = (byte)((long)bytes[n] | segmentValue << 8 - bitIndex);
                segmentValue >>>= bitIndex;
                if (divBits < bitIndex) {
                    bitIndex -= divBits;
                    continue block0;
                }
                bitIndex = 8;
            }
        }
        return bytes;
    }

    protected static Integer cacheBits(int i) {
        return ParsedAddressGrouping.cache(i);
    }

    @Override
    public boolean containsPrefixBlock(int prefixLength) {
        AddressDivisionGrouping.checkSubnet(this, prefixLength);
        int divisionCount = this.getDivisionCount();
        int prevBitCount = 0;
        for (int i = 0; i < divisionCount; ++i) {
            AddressDivision division = this.getDivision(i);
            int bitCount = division.getBitCount();
            int totalBitCount = bitCount + prevBitCount;
            if (prefixLength < totalBitCount) {
                int divPrefixLen = Math.max(0, prefixLength - prevBitCount);
                if (!division.isPrefixBlock(division.getDivisionValue(), division.getUpperDivisionValue(), divPrefixLen)) {
                    return false;
                }
                ++i;
                while (i < divisionCount) {
                    division = this.getDivision(i);
                    if (!division.isFullRange()) {
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
    public boolean containsSinglePrefixBlock(int prefixLength) {
        AddressDivisionGrouping.checkSubnet(this, prefixLength);
        int divisionCount = this.getDivisionCount();
        int prevBitCount = 0;
        for (int i = 0; i < divisionCount; ++i) {
            AddressDivision division = this.getDivision(i);
            int bitCount = division.getBitCount();
            int totalBitCount = bitCount + prevBitCount;
            if (prefixLength >= totalBitCount) {
                if (division.isMultiple()) {
                    return false;
                }
            } else {
                int divPrefixLen = Math.max(0, prefixLength - prevBitCount);
                if (!division.isSinglePrefixBlock(division.getDivisionValue(), division.getUpperDivisionValue(), divPrefixLen)) {
                    return false;
                }
                ++i;
                while (i < divisionCount) {
                    division = this.getDivision(i);
                    if (!division.isFullRange()) {
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
    public int hashCode() {
        int res = this.hashCode;
        if (res == 0) {
            res = 1;
            int count = this.getDivisionCount();
            for (int i = 0; i < count; ++i) {
                AddressDivision combo = this.getDivision(i);
                res = AddressDivisionGrouping.adjustHashCode(res, combo.getDivisionValue(), combo.getUpperDivisionValue());
            }
            this.hashCode = res;
        }
        return res;
    }

    @Override
    protected boolean isSameGrouping(AddressDivisionGroupingBase other) {
        return other instanceof AddressDivisionGrouping && super.isSameGrouping(other);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AddressDivisionGrouping) {
            AddressDivisionGrouping other = (AddressDivisionGrouping)o;
            return other.isSameGrouping(this);
        }
        return false;
    }

    protected static long getLongCount(IntUnaryOperator countProvider, int segCount) {
        if (segCount == 0) {
            return 1L;
        }
        long result = countProvider.applyAsInt(0);
        for (int i = 1; i < segCount; ++i) {
            result *= (long)countProvider.applyAsInt(i);
        }
        return result;
    }

    protected static <R extends AddressSection, S extends AddressSegment> long longPrefixCount(R section, int prefixLength) {
        int hostSegmentIndex;
        int bitsPerSegment = section.getBitsPerSegment();
        int bytesPerSegment = section.getBytesPerSegment();
        int networkSegmentIndex = AddressDivisionGrouping.getNetworkSegmentIndex(prefixLength, bytesPerSegment, bitsPerSegment);
        boolean hasPrefixedSegment = networkSegmentIndex == (hostSegmentIndex = AddressDivisionGrouping.getHostSegmentIndex(prefixLength, bytesPerSegment, bitsPerSegment));
        return AddressDivisionGrouping.getLongCount(i -> {
            if (hasPrefixedSegment && i == networkSegmentIndex) {
                int segmentPrefixLength = AddressDivisionGrouping.getPrefixedSegmentPrefixLength(bitsPerSegment, prefixLength, i);
                return AddressDivision.getPrefixValueCount(section.getSegment(i), segmentPrefixLength);
            }
            AddressSegment segment = section.getSegment(i);
            int valueCount = segment.getUpperSegmentValue() - segment.getSegmentValue() + 1;
            return valueCount;
        }, networkSegmentIndex + 1);
    }

    protected static <R extends AddressSection, S extends AddressSegment> long longCount(R section, int segCount) {
        long result = AddressDivisionGrouping.getLongCount(i -> {
            AddressSegment segment = section.getSegment(i);
            int valueCount = segment.getUpperSegmentValue() - segment.getSegmentValue() + 1;
            return valueCount;
        }, segCount);
        return result;
    }

    protected static Integer getPrefixedSegmentPrefixLength(int bitsPerSegment, int prefixLength, int segmentIndex) {
        return ParsedAddressGrouping.getPrefixedSegmentPrefixLength(bitsPerSegment, prefixLength, segmentIndex);
    }

    protected static int getNetworkSegmentIndex(int networkPrefixLength, int bytesPerSegment, int bitsPerSegment) {
        return ParsedAddressGrouping.getNetworkSegmentIndex(networkPrefixLength, bytesPerSegment, bitsPerSegment);
    }

    protected static int getHostSegmentIndex(int networkPrefixLength, int bytesPerSegment, int bitsPerSegment) {
        return ParsedAddressGrouping.getHostSegmentIndex(networkPrefixLength, bytesPerSegment, bitsPerSegment);
    }

    protected static Integer getSegmentPrefixLength(int bitsPerSegment, Integer prefixLength, int segmentIndex) {
        return ParsedAddressGrouping.getSegmentPrefixLength(bitsPerSegment, prefixLength, segmentIndex);
    }

    protected static Integer getSegmentPrefixLength(int segmentBits, int segmentPrefixedBits) {
        return ParsedAddressGrouping.getDivisionPrefixLength(segmentBits, segmentPrefixedBits);
    }

    protected static int getNetworkPrefixLength(int bitsPerSegment, int prefixLength, int segmentIndex) {
        return ParsedAddressGrouping.getNetworkPrefixLength(bitsPerSegment, prefixLength, segmentIndex);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected int getAdjustedPrefix(boolean nextSegment, int bitsPerSegment, boolean skipBitCountPrefix) {
        Integer prefix = this.getPrefixLength();
        int bitCount = this.getBitCount();
        if (nextSegment) {
            if (prefix == null) {
                if (this.getMinPrefixLengthForBlock() != 0) return bitCount;
                return 0;
            }
            if (prefix == bitCount) {
                return bitCount;
            }
            int existingPrefixLength = prefix;
            int adjustment = existingPrefixLength % bitsPerSegment;
            return existingPrefixLength + bitsPerSegment - adjustment;
        }
        if (prefix == null) {
            if (this.getMinPrefixLengthForBlock() == 0) {
                return 0;
            }
            if (!skipBitCountPrefix) return bitCount;
            prefix = bitCount;
        } else if (prefix == 0) {
            return 0;
        }
        int existingPrefixLength = prefix;
        int adjustment = (existingPrefixLength - 1) % bitsPerSegment + 1;
        return existingPrefixLength - adjustment;
    }

    protected int getAdjustedPrefix(int adjustment, boolean floor, boolean ceiling) {
        Integer prefix = this.getPrefixLength();
        if (prefix == null) {
            prefix = this.getMinPrefixLengthForBlock() == 0 ? AddressDivisionGrouping.cacheBits(0) : AddressDivisionGrouping.cacheBits(this.getBitCount());
        }
        int result = prefix + adjustment;
        if (ceiling) {
            result = Math.min(this.getBitCount(), result);
        }
        if (floor) {
            result = Math.max(0, result);
        }
        return result;
    }

    protected static <S extends IPAddressSegment> void normalizePrefixBoundary(int sectionPrefixBits, S[] segments, int segmentBitCount, int segmentByteCount, Function<S, S> segProducer) {
        S segment;
        int networkSegmentIndex = AddressDivisionGrouping.getNetworkSegmentIndex(sectionPrefixBits, segmentByteCount, segmentBitCount);
        if (networkSegmentIndex >= 0 && !((IPAddressDivision)(segment = segments[networkSegmentIndex])).isPrefixed()) {
            segments[networkSegmentIndex] = (IPAddressSegment)segProducer.apply(segment);
        }
    }

    protected static <S extends AddressSegment> S[] setPrefixedSegments(AddressNetwork<?> network, int sectionPrefixBits, S[] segments, int segmentBitCount, int segmentByteCount, AddressNetwork.AddressSegmentCreator<S> segmentCreator, BiFunction<S, Integer, S> segProducer) {
        int i;
        boolean allPrefsSubnet = network.getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        int n = i = sectionPrefixBits == 0 ? 0 : AddressDivisionGrouping.getNetworkSegmentIndex(sectionPrefixBits, segmentByteCount, segmentBitCount);
        while (i < segments.length) {
            Integer pref = AddressDivisionGrouping.getPrefixedSegmentPrefixLength(segmentBitCount, sectionPrefixBits, i);
            if (pref != null) {
                segments[i] = (AddressSegment)segProducer.apply(segments[i], pref);
                if (allPrefsSubnet && ++i < segments.length) {
                    S allSeg = segmentCreator.createSegment(0, AddressDivisionGrouping.cacheBits(0));
                    Arrays.fill(segments, i, segments.length, allSeg);
                }
            }
            ++i;
        }
        return segments;
    }

    protected static <R extends AddressSection, S extends AddressSegment> S[] removePrefix(R original, S[] segments, int segmentBitCount, SegPrefFunction<S> prefixSetter) {
        Integer oldPrefix = original.getPrefixLength();
        if (oldPrefix != null) {
            segments = (AddressSegment[])segments.clone();
            int networkSegIndex = 0;
            if (oldPrefix > 0) {
                networkSegIndex = AddressDivisionGrouping.getNetworkSegmentIndex(oldPrefix, original.getBytesPerSegment(), segmentBitCount);
            }
            for (int i = networkSegIndex; i < segments.length; ++i) {
                Integer oldPref = AddressDivisionGrouping.getPrefixedSegmentPrefixLength(segmentBitCount, oldPrefix, i);
                segments[i] = (AddressSegment)prefixSetter.apply(segments[i], oldPref, null);
            }
        }
        return segments;
    }

    protected static boolean prefixEquals(AddressSection first, AddressSection other, int otherIndex) {
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
            prefixedSection = AddressDivisionGrouping.getNetworkSegmentIndex(prefixLength, first.getBytesPerSegment(), first.getBitsPerSegment());
            if (prefixedSection >= 0) {
                int segPrefixLength;
                AddressSegment two;
                int oIndex = prefixedSection + otherIndex;
                if (oIndex >= other.getSegmentCount()) {
                    return false;
                }
                AddressSegment one = first.getSegment(prefixedSection);
                if (!one.prefixEquals(two = other.getSegment(oIndex), segPrefixLength = AddressDivisionGrouping.getPrefixedSegmentPrefixLength(one.getBitCount(), prefixLength, prefixedSection).intValue())) {
                    return false;
                }
            }
        }
        while (--prefixedSection >= 0) {
            AddressSegment two;
            AddressSegment one = first.getSegment(prefixedSection);
            if (one.equals(two = other.getSegment(prefixedSection + otherIndex))) continue;
            return false;
        }
        return true;
    }

    protected static <S extends AddressSegment> S[] createSegments(S[] segments, long highBytes, long lowBytes, int bitsPerSegment, AddressNetwork<S> network, Integer prefixLength) {
        AddressCreator<?, ?, ?, S> creator = network.getAddressCreator();
        int segmentMask = ~(-1 << bitsPerSegment);
        int lowIndex = Math.max(0, segments.length - 64 / bitsPerSegment);
        int segmentIndex = segments.length - 1;
        long bytes = lowBytes;
        while (true) {
            Integer segmentPrefixLength;
            int value;
            Object seg;
            if (!AddressDivisionGrouping.isCompatibleNetworks(network, (seg = creator.createSegment(value = segmentMask & (int)bytes, segmentPrefixLength = AddressDivisionGrouping.getSegmentPrefixLength(bitsPerSegment, prefixLength, segmentIndex))).getNetwork())) {
                throw new NetworkMismatchException((AddressItem)seg);
            }
            segments[segmentIndex] = seg;
            if (--segmentIndex >= lowIndex) {
                bytes >>>= bitsPerSegment;
                continue;
            }
            if (lowIndex == 0) break;
            lowIndex = 0;
            bytes = highBytes;
        }
        return segments;
    }

    protected static boolean isCompatibleNetworks(AddressNetwork<?> one, AddressNetwork<?> two) {
        return one.getPrefixConfiguration().equals((Object)two.getPrefixConfiguration());
    }

    protected static <S extends AddressSegment> S[] createSegments(S[] segments, Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, int bytesPerSegment, int bitsPerSegment, AddressNetwork<S> network, Integer prefixLength) {
        AddressCreator<?, ?, ?, S> creator = network.getAddressCreator();
        int segmentCount = segments.length;
        for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
            Object seg;
            Integer segmentPrefixLength = AddressDivisionGrouping.getSegmentPrefixLength(bitsPerSegment, prefixLength, segmentIndex);
            if (segmentPrefixLength != null && segmentPrefixLength == 0 && network.getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                Object allSeg = creator.createSegment(0, AddressDivisionGrouping.cacheBits(0));
                if (!AddressDivisionGrouping.isCompatibleNetworks(network, allSeg.getNetwork())) {
                    throw new NetworkMismatchException((AddressItem)allSeg);
                }
                Arrays.fill(segments, segmentIndex, segmentCount, allSeg);
                break;
            }
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
            Object s2 = seg = lowerValueProvider != null && upperValueProvider != null ? creator.createSegment(value, value2, segmentPrefixLength) : creator.createSegment(value, segmentPrefixLength);
            if (!AddressDivisionGrouping.isCompatibleNetworks(network, seg.getNetwork())) {
                throw new NetworkMismatchException((AddressItem)seg);
            }
            segments[segmentIndex] = seg;
        }
        return segments;
    }

    protected static <S extends AddressSegment> S[] toSegments(S[] segments, byte[] bytes, int startIndex, int endIndex, int bytesPerSegment, int bitsPerSegment, AddressNetwork<S> network, Integer prefixLength) {
        if (endIndex < 0 || endIndex > bytes.length) {
            throw new AddressValueException(endIndex);
        }
        if (startIndex < 0 || startIndex > endIndex) {
            throw new AddressValueException(startIndex);
        }
        AddressCreator<?, ?, ?, S> creator = network.getAddressCreator();
        int segmentCount = segments.length;
        int expectedByteCount = segmentCount * bytesPerSegment;
        int missingBytes = expectedByteCount + startIndex - endIndex;
        if (missingBytes < 0) {
            int expectedStartIndex = endIndex - expectedByteCount;
            int higherStartIndex = expectedStartIndex - 1;
            byte expectedExtendedValue = bytes[higherStartIndex];
            if (expectedExtendedValue != 0) {
                int mostSignificantBit = bytes[expectedStartIndex] >>> 7;
                if (mostSignificantBit != 0) {
                    if (expectedExtendedValue != -1) {
                        throw new AddressValueException(expectedExtendedValue);
                    }
                } else {
                    throw new AddressValueException(expectedExtendedValue);
                }
            }
            while (startIndex < higherStartIndex) {
                if (bytes[--higherStartIndex] == expectedExtendedValue) continue;
                throw new AddressValueException(expectedExtendedValue);
            }
            startIndex = expectedStartIndex;
            missingBytes = 0;
        }
        boolean allPrefixedAddressesAreSubnets = network.getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        int i = 0;
        int segmentIndex = 0;
        while (i < expectedByteCount) {
            Integer segmentPrefixLength = AddressDivisionGrouping.getSegmentPrefixLength(bitsPerSegment, prefixLength, segmentIndex);
            if (allPrefixedAddressesAreSubnets && segmentPrefixLength != null && segmentPrefixLength == 0) {
                Object allSeg = creator.createSegment(0, AddressDivisionGrouping.cacheBits(0));
                if (!AddressDivisionGrouping.isCompatibleNetworks(network, allSeg.getNetwork())) {
                    throw new NetworkMismatchException((AddressItem)allSeg);
                }
                Arrays.fill(segments, segmentIndex, segmentCount, allSeg);
                break;
            }
            int value = 0;
            int k = bytesPerSegment + i;
            int j = i;
            if (j < missingBytes) {
                int mostSignificantBit = bytes[startIndex] >>> 7;
                if (mostSignificantBit == 0) {
                    j = missingBytes;
                } else {
                    int upper = Math.min(missingBytes, k);
                    while (j < upper) {
                        value <<= 8;
                        value |= 0xFF;
                        ++j;
                    }
                }
            }
            while (j < k) {
                int byteValue = 0xFF & bytes[startIndex + j - missingBytes];
                value <<= 8;
                value |= byteValue;
                ++j;
            }
            i = k;
            Object seg = creator.createSegment(value, segmentPrefixLength);
            if (!AddressDivisionGrouping.isCompatibleNetworks(network, seg.getNetwork())) {
                throw new NetworkMismatchException((AddressItem)seg);
            }
            segments[segmentIndex] = seg;
            ++segmentIndex;
        }
        return segments;
    }

    protected static <R extends AddressSection, S extends AddressSegment> S[] createSingle(R original, AddressNetwork.AddressSegmentCreator<S> segmentCreator, IntFunction<S> segProducer) {
        int segmentCount = original.getSegmentCount();
        AddressSegment[] segs = segmentCreator.createSegmentArray(segmentCount);
        for (int i = 0; i < segmentCount; ++i) {
            segs[i] = (AddressSegment)segProducer.apply(i);
        }
        return segs;
    }

    protected static <R extends AddressSegmentSeries> R getSingleLowestOrHighestSection(R section) {
        if (!(section.isMultiple() || section.isPrefixed() && section.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets())) {
            return section;
        }
        return null;
    }

    protected static <R extends AddressSection, S extends AddressSegment> R reverseSegments(R section, AddressCreator<?, R, ?, S> creator, IntFunction<S> segProducer, boolean removePrefix) {
        int count = section.getSegmentCount();
        AddressSegment[] newSegs = creator.createSegmentArray(count);
        int halfCount = count >>> 1;
        int i = 0;
        boolean isSame = !removePrefix || !section.isPrefixed();
        int j = count - 1;
        while (i < halfCount) {
            newSegs[j] = (AddressSegment)segProducer.apply(i);
            newSegs[i] = (AddressSegment)segProducer.apply(j);
            if (!(!isSame || newSegs[i].equals(section.getSegment(i)) && newSegs[j].equals(section.getSegment(j)))) {
                isSame = false;
            }
            ++i;
            --j;
        }
        if ((count & 1) == 1) {
            newSegs[i] = (AddressSegment)segProducer.apply(i);
            if (isSame && !newSegs[i].equals(section.getSegment(i))) {
                isSame = false;
            }
        }
        if (isSame) {
            return section;
        }
        return (R)creator.createSectionInternal(newSegs);
    }

    protected static <R extends AddressSection, S extends AddressSegment> R reverseBits(boolean perByte, R section, AddressCreator<?, R, ?, S> creator, IntFunction<S> segBitReverser, boolean removePrefix) {
        if (perByte) {
            boolean isSame = !removePrefix || !section.isPrefixed();
            int count = section.getSegmentCount();
            AddressSegment[] newSegs = creator.createSegmentArray(count);
            for (int i = 0; i < count; ++i) {
                newSegs[i] = (AddressSegment)segBitReverser.apply(i);
                if (!isSame || newSegs[i].equals(section.getSegment(i))) continue;
                isSame = false;
            }
            if (isSame) {
                return section;
            }
            return (R)creator.createSectionInternal(newSegs);
        }
        return AddressDivisionGrouping.reverseSegments(section, creator, segBitReverser, removePrefix);
    }

    protected static <R extends AddressSection, S extends AddressSegment> R reverseBytes(boolean perSegment, R section, AddressCreator<?, R, ?, S> creator, IntFunction<S> segByteReverser, boolean removePrefix) {
        if (perSegment) {
            boolean isSame = !removePrefix || !section.isPrefixed();
            int count = section.getSegmentCount();
            AddressSegment[] newSegs = creator.createSegmentArray(count);
            for (int i = 0; i < count; ++i) {
                newSegs[i] = (AddressSegment)segByteReverser.apply(i);
                if (!isSame || newSegs[i].equals(section.getSegment(i))) continue;
                isSame = false;
            }
            if (isSame) {
                return section;
            }
            return (R)creator.createSectionInternal(newSegs);
        }
        return AddressDivisionGrouping.reverseSegments(section, creator, segByteReverser, removePrefix);
    }

    protected <S extends AddressDivisionBase> S[] createNewDivisions(int bitsPerDigit, GroupingCreator<S> groupingCreator, IntFunction<S[]> groupingArrayCreator) {
        return this.createNewPrefixedDivisions(bitsPerDigit, null, null, (value, upperValue, bitCount, radix, network, prefixLength) -> groupingCreator.createDivision(value, upperValue, bitCount, radix), groupingArrayCreator);
    }

    protected <S extends AddressDivisionBase> S[] createNewPrefixedDivisions(int bitsPerDigit, IPAddressNetwork<?, ?, ?, ?, ?> network, Integer networkPrefixLength, PrefixedGroupingCreator<S> groupingCreator, IntFunction<S[]> groupingArrayCreator) {
        if (bitsPerDigit >= 32) {
            throw new AddressValueException(bitsPerDigit);
        }
        int bitCount = this.getBitCount();
        ArrayList<Integer> bitDivs = new ArrayList<Integer>(bitsPerDigit);
        int largestBitCount = 63;
        largestBitCount -= largestBitCount % bitsPerDigit;
        while (true) {
            if (bitCount <= largestBitCount) {
                int mod = bitCount % bitsPerDigit;
                int secondLast = bitCount - mod;
                if (secondLast > 0) {
                    bitDivs.add(AddressDivisionGrouping.cacheBits(secondLast));
                }
                if (mod <= 0) break;
                bitDivs.add(AddressDivisionGrouping.cacheBits(mod));
                break;
            }
            bitCount -= largestBitCount;
            bitDivs.add(AddressDivisionGrouping.cacheBits(largestBitCount));
        }
        int bitDivSize = bitDivs.size();
        AddressDivisionBase[] divs = (AddressDivisionBase[])groupingArrayCreator.apply(bitDivSize);
        int currentSegmentIndex = 0;
        AddressDivision seg = this.getDivision(currentSegmentIndex);
        long segLowerVal = seg.getDivisionValue();
        long segUpperVal = seg.getUpperDivisionValue();
        int segBits = seg.getBitCount();
        int bitsSoFar = 0;
        int radix = AddressDivision.getRadixPower(BigInteger.valueOf(2L), bitsPerDigit).intValue();
        for (int i = bitDivSize - 1; i >= 0; --i) {
            int divBitSize;
            int originalDivBitSize = divBitSize = ((Integer)bitDivs.get(i)).intValue();
            long divUpperValue = 0L;
            long divLowerValue = 0L;
            while (true) {
                int diff;
                if (segBits >= divBitSize) {
                    diff = segBits - divBitSize;
                    divLowerValue |= segLowerVal >>> diff;
                    long shift = -1L << diff ^ 0xFFFFFFFFFFFFFFFFL;
                    segLowerVal &= shift;
                    segBits = diff;
                    Integer segPrefixBits = networkPrefixLength == null ? null : AddressDivisionGrouping.getSegmentPrefixLength(originalDivBitSize, networkPrefixLength - bitsSoFar);
                    S div = groupingCreator.createDivision(divLowerValue, divUpperValue |= (segUpperVal &= shift) >>> diff, originalDivBitSize, radix, network, segPrefixBits);
                    divs[bitDivSize - i - 1] = div;
                    if (segBits != 0 || i <= 0) break;
                    seg = this.getDivision(++currentSegmentIndex);
                    segLowerVal = seg.getDivisionValue();
                    segUpperVal = seg.getUpperDivisionValue();
                    segBits = seg.getBitCount();
                    break;
                }
                diff = divBitSize - segBits;
                divLowerValue |= segLowerVal << diff;
                divUpperValue |= segUpperVal << diff;
                divBitSize = diff;
                seg = this.getDivision(++currentSegmentIndex);
                segLowerVal = seg.getDivisionValue();
                segUpperVal = seg.getUpperDivisionValue();
                segBits = seg.getBitCount();
            }
            bitsSoFar += originalDivBitSize;
        }
        return divs;
    }

    protected static <I extends AddressSegmentSeries, S extends AddressSegment> boolean split(AddressDivisionGroupingBase.SplitterSink<I, ?> beingSplit, Function<S[], I> transformer, AddressNetwork.AddressSegmentCreator<S> segmentCreator, S[] originalSegments, int networkSegmentIndex, int hostSegmentIndex, Integer prefixLength) {
        S seg;
        int i;
        Object upperSeg = null;
        Object lowerSeg = null;
        boolean isSplit = false;
        for (i = 0; i < hostSegmentIndex; ++i) {
            seg = originalSegments[i];
            if (!seg.isMultiple()) continue;
            isSplit = true;
            int lower = seg.getSegmentValue();
            int upper = seg.getUpperSegmentValue();
            int size = upper - lower;
            int mid = lower + (size >>> 1);
            Integer pref = AddressDivisionGrouping.getSegmentPrefixLength(seg.getBitCount(), prefixLength, i);
            lowerSeg = segmentCreator.createSegment(lower, mid, pref);
            upperSeg = segmentCreator.createSegment(mid + 1, upper, pref);
            break;
        }
        if (i == networkSegmentIndex && !isSplit) {
            seg = originalSegments[i];
            int segBitCount = seg.getBitCount();
            Integer pref = AddressDivisionGrouping.getSegmentPrefixLength(segBitCount, prefixLength, i);
            int shiftAdjustment = segBitCount - pref;
            int lower = seg.getSegmentValue();
            int upper = seg.getUpperSegmentValue();
            int originalLower = lower;
            int originalUpper = upper;
            if ((lower >>>= shiftAdjustment) != (upper >>>= shiftAdjustment)) {
                isSplit = true;
                int size = upper - lower;
                int mid = lower + (size >>> 1);
                int next = mid + 1;
                mid = mid << shiftAdjustment | ~(-1 << shiftAdjustment);
                lowerSeg = segmentCreator.createSegment(originalLower, mid, pref);
                upperSeg = segmentCreator.createSegment(next <<= shiftAdjustment, originalUpper, pref);
            }
        }
        if (isSplit) {
            int len = originalSegments.length;
            AddressSegment[] lowerSegs = segmentCreator.createSegmentArray(len);
            AddressSegment[] upperSegs = segmentCreator.createSegmentArray(len);
            System.arraycopy(originalSegments, 0, lowerSegs, 0, i);
            System.arraycopy(originalSegments, 0, upperSegs, 0, i);
            int j = i + 1;
            lowerSegs[i] = lowerSeg;
            upperSegs[i] = upperSeg;
            System.arraycopy(originalSegments, j, lowerSegs, j, len - j);
            System.arraycopy(originalSegments, j, upperSegs, j, len - j);
            beingSplit.setSplitValues((AddressSegmentSeries)transformer.apply(lowerSegs), (AddressSegmentSeries)transformer.apply(upperSegs));
        }
        return isSplit;
    }

    protected static <R extends AddressSection, S extends AddressSegment> Iterator<R> iterator(boolean useOriginal, final R original, final AddressCreator<?, R, ?, S> creator, final Iterator<S[]> iterator2, final Integer prefixLength) {
        if (useOriginal) {
            return new Iterator<R>(){
                R orig;
                {
                    this.orig = original;
                }

                @Override
                public R next() {
                    if (this.orig == null) {
                        throw new NoSuchElementException();
                    }
                    Object result = this.orig;
                    this.orig = null;
                    return result;
                }

                @Override
                public boolean hasNext() {
                    return this.orig != null;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
        return new Iterator<R>(){

            @Override
            public R next() {
                if (!iterator2.hasNext()) {
                    throw new NoSuchElementException();
                }
                AddressSegment[] next = (AddressSegment[])iterator2.next();
                return AddressDivisionGrouping.createIteratedSection((AddressSegment[])next, (AddressCreator)creator, (Integer)prefixLength);
            }

            @Override
            public boolean hasNext() {
                return iterator2.hasNext();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    protected static <T extends Address, S extends AddressSegment> T createIteratedAddress(S[] next, AddressCreator<T, ?, ?, S> creator, Integer prefixLength) {
        return (T)creator.createAddressInternal((AddressSegment[])next, prefixLength, true);
    }

    protected static <R extends AddressSection, S extends AddressSegment> R createIteratedSection(S[] next, AddressCreator<?, R, ?, S> creator, Integer prefixLength) {
        return (R)creator.createPrefixedSectionInternal((AddressSegment[])next, prefixLength, true);
    }

    protected static <S extends AddressSegment> Iterator<S[]> segmentsIterator(int divCount, AddressNetwork.AddressSegmentCreator<S> segmentCreator, Supplier<S[]> segSupplier, IntFunction<Iterator<S>> segIteratorProducer, Predicate<S[]> excludeFunc) {
        return AddressDivisionGrouping.segmentsIterator(divCount, segmentCreator, segSupplier, segIteratorProducer, excludeFunc, divCount - 1, divCount, null);
    }

    protected static <S extends AddressSegment> Iterator<S[]> segmentsIterator(final int divCount, final AddressNetwork.AddressSegmentCreator<S> segmentCreator, final Supplier<S[]> segSupplier, final IntFunction<Iterator<S>> segIteratorProducer, final Predicate<S[]> excludeFunc, final int networkSegmentIndex, final int hostSegmentIndex, final IntFunction<Iterator<S>> hostSegIteratorProducer) {
        if (segSupplier != null) {
            return new Iterator<S[]>(){
                S[] result;
                {
                    this.result = (AddressSegment[])segSupplier.get();
                    if (excludeFunc != null && excludeFunc.test(this.result)) {
                        this.result = null;
                    }
                }

                @Override
                public boolean hasNext() {
                    return this.result != null;
                }

                @Override
                public S[] next() {
                    if (this.result == null) {
                        throw new NoSuchElementException();
                    }
                    S[] res = this.result;
                    this.result = null;
                    return res;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
        return new Iterator<S[]>(){
            private boolean done;
            private final Iterator<S>[] variations;
            private S[] nextSet;
            {
                this.variations = new Iterator[divCount];
                this.nextSet = segmentCreator.createSegmentArray(divCount);
                this.updateVariations(0);
                for (int i = networkSegmentIndex + 1; i < divCount; ++i) {
                    this.variations[i] = (Iterator)hostSegIteratorProducer.apply(i);
                    this.nextSet[i] = (AddressSegment)this.variations[i].next();
                }
                if (excludeFunc != null && excludeFunc.test(this.nextSet)) {
                    this.increment();
                }
            }

            private void updateVariations(int start2) {
                int i;
                for (i = start2; i < hostSegmentIndex; ++i) {
                    this.variations[i] = (Iterator)segIteratorProducer.apply(i);
                    this.nextSet[i] = (AddressSegment)this.variations[i].next();
                }
                if (i == networkSegmentIndex) {
                    this.variations[i] = (Iterator)hostSegIteratorProducer.apply(i);
                    this.nextSet[i] = (AddressSegment)this.variations[i].next();
                }
            }

            @Override
            public boolean hasNext() {
                return !this.done;
            }

            @Override
            public S[] next() {
                if (this.done) {
                    throw new NoSuchElementException();
                }
                return this.increment();
            }

            private S[] increment() {
                Object[] previousSegs = null;
                for (int j = networkSegmentIndex; j >= 0; --j) {
                    while (this.variations[j].hasNext()) {
                        if (previousSegs == null) {
                            previousSegs = (AddressSegment[])this.nextSet.clone();
                        }
                        this.nextSet[j] = (AddressSegment)this.variations[j].next();
                        this.updateVariations(j + 1);
                        if (excludeFunc != null && excludeFunc.test(this.nextSet)) {
                            j = networkSegmentIndex;
                            continue;
                        }
                        return previousSegs;
                    }
                }
                this.done = true;
                return previousSegs == null ? this.nextSet : previousSegs;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    protected static <T extends Address, S extends AddressSegment> Iterator<T> iterator(boolean useOriginal, final T original, final AddressCreator<T, ?, ?, S> creator, final Iterator<S[]> iterator2, final Integer prefixLength) {
        if (useOriginal) {
            return new Iterator<T>(){
                T orig;
                {
                    this.orig = original;
                }

                @Override
                public boolean hasNext() {
                    return this.orig != null;
                }

                @Override
                public T next() {
                    if (this.orig == null) {
                        throw new NoSuchElementException();
                    }
                    Object result = this.orig;
                    this.orig = null;
                    return result;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
        return new Iterator<T>(){

            @Override
            public boolean hasNext() {
                return iterator2.hasNext();
            }

            @Override
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                AddressSegment[] next = (AddressSegment[])iterator2.next();
                return AddressDivisionGrouping.createIteratedAddress((AddressSegment[])next, (AddressCreator)creator, (Integer)prefixLength);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    protected static void checkOverflow(long increment, long lowerValue, long upperValue, long count, LongSupplier maxValue) {
        if (increment < 0L) {
            if (lowerValue < -increment) {
                throw new AddressValueException(increment);
            }
        } else {
            if (count > 1L) {
                increment -= count - 1L;
            }
            if (increment > maxValue.getAsLong() - upperValue) {
                throw new AddressValueException(increment);
            }
        }
    }

    protected static void checkOverflow(long increment, BigInteger bigIncrement, BigInteger lowerValue, BigInteger upperValue, BigInteger count, Supplier<BigInteger> maxValue) {
        boolean isMultiple;
        boolean bl = isMultiple = count.compareTo(BigInteger.ONE) > 0;
        if (increment < 0L) {
            if (lowerValue.compareTo(bigIncrement.negate()) < 0) {
                throw new AddressValueException(increment);
            }
        } else {
            if (isMultiple) {
                bigIncrement = bigIncrement.subtract(count.subtract(BigInteger.ONE));
            }
            if (bigIncrement.compareTo(maxValue.get().subtract(upperValue)) > 0) {
                throw new AddressValueException(increment);
            }
        }
    }

    protected static <R extends AddressSection, S extends AddressSegment> R fastIncrement(R section, long increment, AddressCreator<?, R, ?, S> addrCreator, Supplier<R> lowerProducer, Supplier<R> upperProducer, Integer prefixLength) {
        if (increment >= 0L) {
            BigInteger count = section.getCount();
            if (count.compareTo(LONG_MAX) <= 0) {
                BigInteger upperValue;
                long longCount = count.longValue();
                if (longCount > increment) {
                    if (longCount == increment + 1L) {
                        return (R)((AddressSection)upperProducer.get());
                    }
                    return AddressDivisionGrouping.incrementRange(section, increment, addrCreator, lowerProducer, prefixLength);
                }
                BigInteger value = section.getValue();
                if (value.compareTo(LONG_MAX) <= 0 && (upperValue = section.getUpperValue()).compareTo(LONG_MAX) <= 0) {
                    return AddressDivisionGrouping.increment(section, increment, addrCreator, count.longValue(), value.longValue(), upperValue.longValue(), lowerProducer, upperProducer, prefixLength);
                }
            }
        } else {
            BigInteger value = section.getValue();
            if (value.compareTo(LONG_MAX) <= 0) {
                return (R)AddressDivisionGrouping.add((AddressSection)lowerProducer.get(), value.longValue(), increment, addrCreator, prefixLength);
            }
        }
        return null;
    }

    protected static <R extends AddressSection, S extends AddressSegment> R increment(R section, long increment, AddressCreator<?, R, ?, S> addrCreator, long count, long lowerValue, long upperValue, Supplier<R> lowerProducer, Supplier<R> upperProducer, Integer prefixLength) {
        boolean isDecrement;
        if (!section.isMultiple()) {
            return AddressDivisionGrouping.add(section, lowerValue, increment, addrCreator, prefixLength);
        }
        boolean bl = isDecrement = increment <= 0L;
        if (isDecrement) {
            return (R)AddressDivisionGrouping.add((AddressSection)lowerProducer.get(), lowerValue, increment, addrCreator, prefixLength);
        }
        if (count > increment) {
            if (count == increment + 1L) {
                return (R)((AddressSection)upperProducer.get());
            }
            return AddressDivisionGrouping.incrementRange(section, increment, addrCreator, lowerProducer, prefixLength);
        }
        if (increment <= Long.MAX_VALUE - upperValue) {
            return (R)AddressDivisionGrouping.add((AddressSection)upperProducer.get(), upperValue, increment - (count - 1L), addrCreator, prefixLength);
        }
        return (R)AddressDivisionGrouping.add((AddressSection)upperProducer.get(), BigInteger.valueOf(increment - (count - 1L)), addrCreator, prefixLength);
    }

    protected static <R extends AddressSection, S extends AddressSegment> R increment(R section, long increment, BigInteger bigIncrement, AddressCreator<?, R, ?, S> addrCreator, Supplier<R> lowerProducer, Supplier<R> upperProducer, Integer prefixLength) {
        BigInteger incrementPlus1;
        boolean isDecrement;
        if (!section.isMultiple()) {
            return AddressDivisionGrouping.add(section, bigIncrement, addrCreator, prefixLength);
        }
        boolean bl = isDecrement = increment <= 0L;
        if (isDecrement) {
            return (R)AddressDivisionGrouping.add((AddressSection)lowerProducer.get(), bigIncrement, addrCreator, prefixLength);
        }
        BigInteger count = section.getCount();
        int countCompare = count.compareTo(incrementPlus1 = bigIncrement.add(BigInteger.ONE));
        if (countCompare <= 0) {
            if (countCompare == 0) {
                return (R)((AddressSection)upperProducer.get());
            }
            return (R)AddressDivisionGrouping.add((AddressSection)upperProducer.get(), incrementPlus1.subtract(count), addrCreator, prefixLength);
        }
        return AddressDivisionGrouping.incrementRange(section, increment, addrCreator, lowerProducer, prefixLength);
    }

    protected static <R extends AddressSection, S extends AddressSegment> R incrementRange(R section, long increment, AddressCreator<?, R, ?, S> addrCreator, Supplier<R> lowerProducer, Integer prefixLength) {
        if (increment == 0L) {
            return (R)((AddressSection)lowerProducer.get());
        }
        int segCount = section.getSegmentCount();
        AddressSegment[] newSegments = addrCreator.createSegmentArray(segCount);
        for (int i = segCount - 1; i >= 0; --i) {
            AddressSegment seg = section.getSegment(i);
            long segRange = (long)(seg.getUpperSegmentValue() - seg.getSegmentValue()) + 1L;
            long revolutions = increment / segRange;
            int remainder = (int)(increment % segRange);
            Object newSegment = addrCreator.createSegment(seg.getSegmentValue() + remainder);
            newSegments[i] = newSegment;
            if (revolutions == 0L) {
                --i;
                while (i >= 0) {
                    AddressSegment original = section.getSegment(i);
                    newSegments[i] = addrCreator.createSegment(original.getSegmentValue());
                    --i;
                }
                break;
            }
            increment = revolutions;
        }
        return (R)AddressDivisionGrouping.createIteratedSection((AddressSegment[])newSegments, addrCreator, (Integer)prefixLength);
    }

    protected static <R extends AddressSection, S extends AddressSegment> R add(R section, BigInteger increment, AddressCreator<?, R, ?, S> addrCreator, Integer prefixLength) {
        if (section.isMultiple()) {
            throw new IllegalArgumentException();
        }
        int segCount = section.getSegmentCount();
        BigInteger fullValue = section.getValue();
        fullValue = fullValue.add(increment);
        byte[] bytes = fullValue.toByteArray();
        return addrCreator.createSectionInternal(bytes, segCount, prefixLength, true);
    }

    protected static <R extends AddressSection, S extends AddressSegment> R add(R section, long fullValue, long increment, AddressCreator<?, R, ?, S> addrCreator, Integer prefixLength) {
        if (section.isMultiple()) {
            throw new IllegalArgumentException();
        }
        int segCount = section.getSegmentCount();
        AddressSegment[] newSegs = addrCreator.createSegmentArray(segCount);
        AddressDivisionGrouping.createSegments((AddressSegment[])newSegs, (long)0L, (long)(fullValue + increment), (int)section.getBitsPerSegment(), addrCreator.getNetwork(), (Integer)prefixLength);
        return (R)AddressDivisionGrouping.createIteratedSection((AddressSegment[])newSegs, addrCreator, (Integer)prefixLength);
    }

    protected static BigInteger count(IntUnaryOperator segmentValueCountProvider, int segCount, int safeMultiplies, long safeLimit) {
        int i = 0;
        BigInteger result = BigInteger.ONE;
        if (segCount == 0) {
            return result;
        }
        while (true) {
            long curResult = segmentValueCountProvider.applyAsInt(i++);
            if (i == segCount) {
                return AddressDivisionGrouping.mult(result, curResult);
            }
            int limit = i + safeMultiplies;
            if (segCount <= limit) {
                while (i < segCount) {
                    curResult *= (long)segmentValueCountProvider.applyAsInt(i++);
                }
                return AddressDivisionGrouping.mult(result, curResult);
            }
            while (i < limit) {
                curResult *= (long)segmentValueCountProvider.applyAsInt(i++);
            }
            while (curResult <= safeLimit) {
                curResult *= (long)segmentValueCountProvider.applyAsInt(i++);
                if (i != segCount) continue;
                return AddressDivisionGrouping.mult(result, curResult);
            }
            result = AddressDivisionGrouping.mult(result, curResult);
        }
    }

    private static BigInteger mult(BigInteger currentResult, long newResult) {
        if (newResult == 1L) {
            return currentResult;
        }
        BigInteger newBig = BigInteger.valueOf(newResult);
        if (currentResult == BigInteger.ONE) {
            return newBig;
        }
        return currentResult.multiply(newBig);
    }

    protected static <R extends AddressSection, S extends AddressSegment> R getSection(int index, int endIndex, R section, AddressCreator<?, R, ?, S> creator) {
        if (index == 0 && endIndex == section.getSegmentCount()) {
            return section;
        }
        int segmentCount = endIndex - index;
        if (segmentCount < 0) {
            throw new IndexOutOfBoundsException();
        }
        AddressSegment[] segs = creator.createSegmentArray(segmentCount);
        section.getSegments(index, endIndex, segs, 0);
        return (R)creator.createSectionInternal(segs);
    }

    protected static <R extends AddressSection, S extends AddressSegment> R append(R section, R other, AddressCreator<?, R, ?, S> creator) {
        int otherSegmentCount = other.getSegmentCount();
        int segmentCount = section.getSegmentCount();
        int totalSegmentCount = segmentCount + otherSegmentCount;
        Object[] segs = creator.createSegmentArray(totalSegmentCount);
        section.getSegments(0, segmentCount, (AddressSegment[])segs, 0);
        if (section.isPrefixed() && section.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
            Object allSegment = creator.createSegment(0, AddressDivisionGrouping.cacheBits(0));
            Arrays.fill(segs, segmentCount, totalSegmentCount, allSegment);
        } else {
            other.getSegments(0, otherSegmentCount, (AddressSegment[])segs, segmentCount);
        }
        return (R)creator.createSectionInternal((AddressSegment[])segs);
    }

    protected static <R extends AddressSection, S extends AddressSegment> R replace(R section, int index, int endIndex, R replacement, int replacementStartIndex, int replacementEndIndex, AddressCreator<?, R, ?, S> creator, boolean appendNetwork, boolean isMac) {
        int otherSegmentCount = replacementEndIndex - replacementStartIndex;
        int segmentCount = section.getSegmentCount();
        int totalSegmentCount = segmentCount + otherSegmentCount - (endIndex - index);
        Object[] segs = creator.createSegmentArray(totalSegmentCount);
        section.getSegments(0, index, (AddressSegment[])segs, 0);
        if (index < totalSegmentCount) {
            if (section.isPrefixed() && section.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() && (appendNetwork ? AddressDivisionGrouping.getHostSegmentIndex(section.getPrefixLength(), section.getBytesPerSegment(), section.getBitsPerSegment()) < index : AddressDivisionGrouping.getNetworkSegmentIndex(section.getPrefixLength(), section.getBytesPerSegment(), section.getBitsPerSegment()) < index) && (isMac || index > 0)) {
                Object allSegment = creator.createSegment(0, AddressDivisionGrouping.cacheBits(0));
                Arrays.fill(segs, index, totalSegmentCount, allSegment);
                return (R)creator.createSectionInternal((AddressSegment[])segs);
            }
            replacement.getSegments(replacementStartIndex, replacementEndIndex, (AddressSegment[])segs, index);
            if (index + otherSegmentCount < totalSegmentCount) {
                if (replacement.isPrefixed() && section.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() && AddressDivisionGrouping.getNetworkSegmentIndex(replacement.getPrefixLength(), replacement.getBytesPerSegment(), replacement.getBitsPerSegment()) < replacementEndIndex && (isMac || otherSegmentCount > 0)) {
                    Object allSegment = creator.createSegment(0, AddressDivisionGrouping.cacheBits(0));
                    Arrays.fill(segs, index + otherSegmentCount, totalSegmentCount, allSegment);
                } else {
                    section.getSegments(endIndex, segmentCount, (AddressSegment[])segs, index + otherSegmentCount);
                }
            }
        }
        return (R)creator.createSectionInternal((AddressSegment[])segs);
    }

    protected static <R extends AddressSection, S extends AddressSegment> R createSectionInternal(AddressCreator<?, R, ?, S> creator, S[] segments, int startIndex, boolean extended) {
        return (R)creator.createSectionInternal((AddressSegment[])segments, startIndex, extended);
    }

    protected boolean isDualString() throws IncompatibleAddressException {
        int count = this.getDivisionCount();
        for (int i = 0; i < count; ++i) {
            AddressDivision division = this.getDivision(i);
            if (!division.isMultiple()) continue;
            boolean isLastFull = true;
            AddressDivision lastDivision = null;
            for (int j = count - 1; j >= 0; --j) {
                division = this.getDivision(j);
                if (division.isMultiple()) {
                    if (!isLastFull) {
                        throw new IncompatibleAddressException(division, i, lastDivision, i + 1, "ipaddress.error.segmentMismatch");
                    }
                    isLastFull = division.isFullRange();
                } else {
                    isLastFull = false;
                }
                lastDivision = division;
            }
            return true;
        }
        return false;
    }

    protected static <T extends AddressStringDivisionSeries, E extends AddressStringDivisionSeries> String toNormalizedStringRange(AddressDivisionGroupingBase.AddressStringParams<T> params, T lower, T upper, CharSequence zone) {
        StringBuilder builder;
        int length = params.getStringLength(lower, null) + params.getStringLength(upper, zone);
        String separator = params.getWildcards().rangeSeparator;
        if (separator != null) {
            builder = new StringBuilder(length += separator.length());
            params.append(params.append(builder, lower, null).append(separator), upper, zone);
        } else {
            builder = new StringBuilder(length);
            params.append(params.append(builder, lower, null), upper, zone);
        }
        AddressDivisionGroupingBase.AddressStringParams.checkLengths(length, builder);
        return builder.toString();
    }

    public static class StringOptions
    extends AddressDivisionGroupingBase.StringOptionsBase {
        public final Wildcards wildcards;
        public final boolean expandSegments;
        public final int base;
        public final String segmentStrPrefix;
        public final Character separator;
        public final String addrLabel;
        public final boolean reverse;
        public final boolean splitDigits;
        public final boolean uppercase;

        protected StringOptions(int base, boolean expandSegments, Wildcards wildcards, String segmentStrPrefix, Character separator, String label, boolean reverse, boolean splitDigits, boolean uppercase) {
            this.expandSegments = expandSegments;
            this.wildcards = wildcards;
            this.base = base;
            if (segmentStrPrefix == null) {
                throw new NullPointerException("segment str");
            }
            this.segmentStrPrefix = segmentStrPrefix;
            this.separator = separator;
            if (label == null) {
                throw new NullPointerException("label");
            }
            this.addrLabel = label;
            this.reverse = reverse;
            this.splitDigits = splitDigits;
            this.uppercase = uppercase;
        }

        public static class Builder {
            public static final Wildcards DEFAULT_WILDCARDS = new Wildcards();
            protected Wildcards wildcards = DEFAULT_WILDCARDS;
            protected boolean expandSegments;
            protected int base;
            protected String segmentStrPrefix = "";
            protected Character separator;
            protected String addrLabel = "";
            protected boolean reverse;
            protected boolean splitDigits;
            protected boolean uppercase;

            public Builder(int base) {
                this.base = base;
                this.separator = Character.valueOf(' ');
            }

            public Builder(int base, char separator) {
                this.base = base;
                this.separator = Character.valueOf(separator);
            }

            public Builder setWildcards(Wildcards wildcards) {
                this.wildcards = wildcards;
                return this;
            }

            public Builder setReverse(boolean reverse) {
                this.reverse = reverse;
                return this;
            }

            public Builder setUppercase(boolean uppercase) {
                this.uppercase = uppercase;
                return this;
            }

            public Builder setSplitDigits(boolean splitDigits) {
                this.splitDigits = splitDigits;
                return this;
            }

            public Builder setExpandedSegments(boolean expandSegments) {
                this.expandSegments = expandSegments;
                return this;
            }

            public Builder setRadix(int base) {
                this.base = base;
                return this;
            }

            public Builder setSeparator(Character separator) {
                this.separator = separator;
                return this;
            }

            public Builder setAddressLabel(String label) {
                this.addrLabel = label;
                return this;
            }

            public Builder setSegmentStrPrefix(String prefix) {
                this.segmentStrPrefix = prefix;
                return this;
            }

            public StringOptions toOptions() {
                return new StringOptions(this.base, this.expandSegments, this.wildcards, this.segmentStrPrefix, this.separator, this.addrLabel, this.reverse, this.splitDigits, this.uppercase);
            }
        }

        public static class Wildcards {
            public final String rangeSeparator;
            public final String wildcard;
            public final String singleWildcard;

            public Wildcards() {
                this(Address.RANGE_SEPARATOR_STR, Address.SEGMENT_WILDCARD_STR, null);
            }

            public Wildcards(String wildcard, String singleWildcard) {
                this(Address.RANGE_SEPARATOR_STR, wildcard, singleWildcard);
            }

            public Wildcards(String rangeSeparator) {
                this(rangeSeparator, null, null);
            }

            public Wildcards(String rangeSeparator, String wildcard, String singleWildcard) {
                if (rangeSeparator == null) {
                    rangeSeparator = Address.RANGE_SEPARATOR_STR;
                }
                this.rangeSeparator = rangeSeparator;
                this.wildcard = wildcard;
                this.singleWildcard = singleWildcard;
            }

            public String toString() {
                return "range separator: " + this.rangeSeparator + "\nwildcard: " + this.wildcard + "\nsingle wildcard: " + this.singleWildcard;
            }
        }
    }

    protected static interface PrefixedGroupingCreator<S extends AddressDivisionBase> {
        public S createDivision(long var1, long var3, int var5, int var6, IPAddressNetwork<?, ?, ?, ?, ?> var7, Integer var8);
    }

    protected static interface GroupingCreator<S extends AddressDivisionBase> {
        public S createDivision(long var1, long var3, int var5, int var6);
    }

    @FunctionalInterface
    protected static interface SegPrefFunction<S> {
        public S apply(S var1, Integer var2, Integer var3);
    }

    protected static class StringCache {
        public String canonicalString;
        public String hexString;
        public String hexStringPrefixed;

        protected StringCache() {
        }
    }

    protected static class SectionCache<R extends AddressSegmentSeries> {
        public R lower;
        public R lowerNonZeroHost;
        public R upper;
        public boolean lowerNonZeroHostIsNull;
    }

    public static interface DivisionLengthProvider {
        public int getLength(int var1);
    }

    public static interface DivisionValueProvider {
        public long getValue(int var1);
    }
}

