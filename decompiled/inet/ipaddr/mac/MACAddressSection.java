/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.mac;

import inet.ipaddr.Address;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressPositionException;
import inet.ipaddr.AddressSection;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressDivisionGroupingBase;
import inet.ipaddr.format.standard.AddressBitsDivision;
import inet.ipaddr.format.standard.AddressCreator;
import inet.ipaddr.format.standard.AddressDivision;
import inet.ipaddr.format.standard.AddressDivisionGrouping;
import inet.ipaddr.format.string.AddressStringDivisionSeries;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.ipv6.IPv6AddressNetwork;
import inet.ipaddr.ipv6.IPv6AddressSection;
import inet.ipaddr.mac.MACAddress;
import inet.ipaddr.mac.MACAddressNetwork;
import inet.ipaddr.mac.MACAddressSegment;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MACAddressSection
extends AddressDivisionGrouping
implements AddressSection,
Iterable<MACAddressSection> {
    private static final long serialVersionUID = 4L;
    private static final long[] MAX_VALUES_LONG = new long[]{0L, 255L, 65535L, 0xFFFFFFL, 0xFFFFFFFFL, 0xFFFFFFFFFFL, 0xFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFL};
    private static final BigInteger[] MAX_VALUES = new BigInteger[]{BigInteger.ZERO, BigInteger.valueOf(MAX_VALUES_LONG[1]), BigInteger.valueOf(MAX_VALUES_LONG[2]), BigInteger.valueOf(MAX_VALUES_LONG[3]), BigInteger.valueOf(MAX_VALUES_LONG[4]), BigInteger.valueOf(MAX_VALUES_LONG[5]), BigInteger.valueOf(MAX_VALUES_LONG[6]), BigInteger.valueOf(MAX_VALUES_LONG[7]), BigInteger.valueOf(1L).shiftLeft(64).subtract(BigInteger.ONE)};
    private static MACAddressNetwork.MACAddressCreator[][] creators = new MACAddressNetwork.MACAddressCreator[2][8];
    private transient MACStringCache stringCache;
    private transient AddressDivisionGrouping.SectionCache<MACAddressSection> sectionCache;
    public final int addressSegmentIndex;
    public final boolean extended;

    public MACAddressSection(MACAddressSegment segment) {
        super(new MACAddressSegment[]{segment});
        this.addressSegmentIndex = 0;
        this.extended = false;
    }

    public MACAddressSection(MACAddressSegment segment, int startIndex, boolean extended) {
        this(false, new MACAddressSegment[]{segment}, startIndex, extended);
    }

    public MACAddressSection(MACAddressSegment[] segments) {
        this(segments, 0, segments.length > 6);
    }

    public MACAddressSection(MACAddressSegment[] segments, int startIndex, boolean extended) {
        this(true, segments, startIndex, extended);
    }

    protected MACAddressSection(boolean cloneSegments, MACAddressSegment[] segments, int startIndex, boolean extended) {
        super(cloneSegments ? (AddressDivision[])segments.clone() : segments);
        this.addressSegmentIndex = startIndex;
        this.extended = extended;
        if (startIndex < 0 || startIndex > (extended ? 8 : 6)) {
            throw new AddressPositionException(startIndex);
        }
        if (startIndex + segments.length > (extended ? 8 : 6)) {
            throw new AddressValueException(segments.length);
        }
    }

    public MACAddressSection(Address.SegmentValueProvider valueProvider) {
        this(valueProvider, valueProvider, 0, false);
    }

    public MACAddressSection(Address.SegmentValueProvider valueProvider, int startIndex, boolean extended) {
        this(valueProvider, valueProvider, startIndex, extended);
    }

    public MACAddressSection(Address.SegmentValueProvider lowerValueProvider, Address.SegmentValueProvider upperValueProvider, int startIndex, boolean extended) {
        super(new MACAddressSegment[Math.max(0, (extended ? 8 : 6) - startIndex)], false);
        MACAddressSection.createSegments((AddressSegment[])this.getSegmentsInternal(), (Address.SegmentValueProvider)lowerValueProvider, (Address.SegmentValueProvider)upperValueProvider, (int)this.getBytesPerSegment(), (int)this.getBitsPerSegment(), (AddressNetwork)this.getNetwork(), null);
        if (startIndex < 0 || startIndex > (extended ? 8 : 6)) {
            throw new AddressPositionException(startIndex);
        }
        this.addressSegmentIndex = startIndex;
        this.extended = extended;
    }

    protected MACAddressSection(byte[] bytes, int segmentCount, int startIndex, boolean extended, boolean cloneBytes) {
        this(bytes, 0, bytes.length, segmentCount, startIndex, extended, cloneBytes);
    }

    protected MACAddressSection(byte[] bytes, int byteStartIndex, int byteEndIndex, int segmentCount, int startIndex, boolean extended, boolean cloneBytes) {
        super(new MACAddressSegment[segmentCount >= 0 ? segmentCount : Math.max(0, byteEndIndex - byteStartIndex)], false);
        AddressSegment[] segs = this.getSegmentsInternal();
        MACAddressSection.toSegments((AddressSegment[])segs, (byte[])bytes, (int)byteStartIndex, (int)byteEndIndex, (int)this.getBytesPerSegment(), (int)this.getBitsPerSegment(), (AddressNetwork)this.getNetwork(), null);
        if (startIndex < 0 || startIndex > (extended ? 8 : 6)) {
            throw new AddressPositionException(startIndex);
        }
        this.addressSegmentIndex = startIndex;
        this.extended = extended;
        if (bytes.length == segs.length) {
            this.setBytes(cloneBytes ? (byte[])bytes.clone() : bytes);
        }
    }

    public MACAddressSection(byte[] bytes, int startIndex, boolean extended) {
        this(bytes, 0, bytes.length, -1, startIndex, extended, true);
    }

    public MACAddressSection(byte[] bytes) {
        this(bytes, 0, bytes.length > 6);
    }

    public MACAddressSection(long value, int startIndex, boolean extended) {
        super(new MACAddressSegment[extended ? 8 : 6], false);
        if (startIndex < 0 || startIndex > (extended ? 8 : 6)) {
            throw new AddressPositionException(startIndex);
        }
        if (!(extended || value <= 0xFFFFFFFFFFFFL && value >= 0L)) {
            throw new AddressValueException(value);
        }
        MACAddressSection.createSegments((AddressSegment[])this.getSegmentsInternal(), (long)0L, (long)value, (int)this.getBitsPerSegment(), (AddressNetwork)this.getNetwork(), null);
        this.addressSegmentIndex = startIndex;
        this.extended = extended;
    }

    public MACAddressSection(long value) {
        this(value, 0, false);
    }

    public MACAddressNetwork getNetwork() {
        return MACAddress.defaultMACNetwork();
    }

    public IPv6AddressNetwork getIPv6Network() {
        return MACAddress.defaultIpv6Network();
    }

    protected MACAddressNetwork.MACAddressCreator getAddressCreator(final int startIndex, final boolean extended) {
        MACAddressNetwork.MACAddressCreator creator = null;
        MACAddressNetwork.MACAddressCreator defaultCreator = this.getNetwork().getAddressCreator();
        boolean useCached = startIndex < 8;
        int ext = 0;
        if (useCached) {
            if (extended) {
                ext = 1;
            }
            creator = creators[ext][startIndex];
        }
        if (creator != null && (useCached |= creator.getNetwork().equals(this.getNetwork()))) {
            return creator;
        }
        creator = new MACAddressNetwork.MACAddressCreator(this.getNetwork(), defaultCreator.cache){
            private static final long serialVersionUID = 4L;

            @Override
            protected MACAddressSection createSectionInternal(MACAddressSegment[] segments) {
                return this.getNetwork().getAddressCreator().createSectionInternal(segments, startIndex, extended);
            }
        };
        if (useCached) {
            MACAddressSection.creators[ext][startIndex] = creator;
        }
        return creator;
    }

    MACAddressNetwork.MACAddressCreator getAddressCreator() {
        return this.getAddressCreator(this.addressSegmentIndex, this.extended);
    }

    private AddressNetwork.AddressSegmentCreator<MACAddressSegment> getSegmentCreator() {
        return this.getAddressCreator(0, false);
    }

    @Override
    protected boolean isSameGrouping(AddressDivisionGroupingBase other) {
        return other instanceof MACAddressSection && super.isSameGrouping(other);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof MACAddressSection) {
            MACAddressSection other = (MACAddressSection)o;
            return this.addressSegmentIndex == other.addressSegmentIndex && this.isExtended() == other.isExtended() && other.isSameGrouping(this);
        }
        return false;
    }

    protected MACStringCache getStringCache() {
        return this.stringCache;
    }

    public MACAddressSegment[] getSegments() {
        return (MACAddressSegment[])this.getDivisionsInternal().clone();
    }

    @Override
    public void getSegments(AddressSegment[] segs) {
        this.getSegments(0, this.getDivisionCount(), segs, 0);
    }

    protected MACAddressSegment[] getSegmentsInternal() {
        return (MACAddressSegment[])super.getDivisionsInternal();
    }

    @Override
    public void getSegments(int start2, int end, AddressSegment[] segs, int destIndex) {
        System.arraycopy(this.getSegmentsInternal(), start2, segs, destIndex, end - start2);
    }

    public boolean isExtended() {
        return this.extended;
    }

    @Override
    public int getSegmentCount() {
        return this.getDivisionCount();
    }

    public boolean isEntireAddress(boolean extended) {
        return this.getSegmentCount() == (extended ? 8 : 6);
    }

    @Override
    public MACAddressSegment getDivision(int index) {
        return (MACAddressSegment)super.getDivision(index);
    }

    @Override
    public MACAddressSegment getSegment(int index) {
        return (MACAddressSegment)super.getDivision(index);
    }

    public void getSegments(Collection<? super MACAddressSegment> segs) {
        this.getSegments(0, this.getSegmentCount(), segs);
    }

    public void getSegments(int start2, int end, Collection<? super MACAddressSegment> segs) {
        for (int i = start2; i < end; ++i) {
            segs.add(this.getSegment(i));
        }
    }

    @Override
    public int getMaxSegmentValue() {
        return 255;
    }

    @Override
    public int getBitsPerSegment() {
        return 8;
    }

    @Override
    public int getBytesPerSegment() {
        return 1;
    }

    @Override
    public int getByteCount() {
        return this.getSegmentCount();
    }

    @Override
    public int getBitCount() {
        return this.getSegmentCount() << 3;
    }

    @Override
    protected byte[] getBytesImpl(boolean low) {
        int segmentCount = this.getSegmentCount();
        byte[] bytes = new byte[segmentCount];
        for (int i = 0; i < segmentCount; ++i) {
            MACAddressSegment seg = this.getSegment(i);
            int val = low ? seg.getSegmentValue() : seg.getUpperSegmentValue();
            bytes[i] = (byte)val;
        }
        return bytes;
    }

    @Override
    protected BigInteger getCountImpl() {
        return this.getCountImpl(this.getSegmentCount());
    }

    private BigInteger getCountImpl(int segCount) {
        long lastValue;
        if (!this.isMultiple()) {
            return BigInteger.ONE;
        }
        long result = this.getSegment(0).getValueCount();
        int limit = Math.min(segCount, 7);
        for (int i = 1; i < limit; ++i) {
            result *= (long)this.getSegment(i).getValueCount();
        }
        if (segCount == 8 && (lastValue = (long)this.getSegment(7).getValueCount()) != 1L) {
            if (result <= 0x7FFFFFFFFFFFFFL) {
                result *= lastValue;
            } else {
                return BigInteger.valueOf(result).multiply(BigInteger.valueOf(lastValue));
            }
        }
        return BigInteger.valueOf(result);
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

    @Override
    public BigInteger getPrefixCount(int prefixLength) {
        MACAddressSection.checkSubnet(this, prefixLength);
        if (this.isMultiple()) {
            long lastValue;
            int i;
            long result = 1L;
            int networkSegmentIndex = MACAddressSection.getNetworkSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
            int hostSegmentIndex = MACAddressSection.getHostSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
            for (i = 0; i < hostSegmentIndex; ++i) {
                result *= (long)this.getSegment(i).getValueCount();
            }
            if (i == networkSegmentIndex && (lastValue = (long)this.getSegment(i).getPrefixValueCount(MACAddressSection.getSegmentPrefixLength(this.getBitsPerSegment(), prefixLength, i))) != 1L) {
                if (result <= 0x7FFFFFFFFFFFFFL) {
                    result *= lastValue;
                } else {
                    return BigInteger.valueOf(result).multiply(BigInteger.valueOf(lastValue));
                }
            }
            return BigInteger.valueOf(result);
        }
        return BigInteger.ONE;
    }

    @Override
    protected BigInteger getPrefixCountImpl() {
        Integer prefixLength = this.getPrefixLength();
        if (prefixLength == null || prefixLength >= this.getBitCount()) {
            return this.getCount();
        }
        return this.getPrefixCount(prefixLength);
    }

    @Override
    public boolean isPrefixed() {
        return this.getPrefixLength() != null;
    }

    public int getOUISegmentCount() {
        return Math.max(0, 3 - this.addressSegmentIndex);
    }

    public int getODISegmentCount() {
        return this.getSegmentCount() - this.getOUISegmentCount();
    }

    @Override
    public Integer getPrefixLength() {
        Integer ret = this.cachedPrefixLength;
        if (ret == null) {
            int prefix = this.getMinPrefixLengthForBlock();
            if (prefix == this.getBitCount()) {
                this.cachedPrefixLength = NO_PREFIX_LENGTH;
                return null;
            }
            this.cachedPrefixLength = MACAddressSection.cacheBits(prefix);
            return this.cachedPrefixLength;
        }
        if (ret.intValue() == NO_PREFIX_LENGTH.intValue()) {
            return null;
        }
        return ret;
    }

    protected void assignPrefixLength(Integer prefixLength) {
        if (prefixLength == null) {
            this.cachedPrefixLength = NO_PREFIX_LENGTH;
            return;
        }
        if (prefixLength < 0) {
            throw new PrefixLenException(prefixLength);
        }
        int max = this.getBitCount();
        if (prefixLength > max) {
            int maxPrefixLength;
            int n = maxPrefixLength = this.extended ? 64 : 48;
            if (prefixLength > maxPrefixLength) {
                throw new PrefixLenException(prefixLength);
            }
            prefixLength = max;
        }
        this.cachedPrefixLength = prefixLength;
    }

    @Override
    public MACAddressSection getSection() {
        return this;
    }

    @Override
    public MACAddressSection getSection(int index) {
        return this.getSection(index, this.getSegmentCount());
    }

    @Override
    public MACAddressSection getSection(int index, int endIndex) {
        MACAddressSection result = MACAddressSection.getSection(index, endIndex, this, this.getAddressCreator(this.addressSegmentIndex + index, this.extended));
        Integer prefix = this.getPrefixLength();
        if (prefix != null) {
            if (index > 0) {
                prefix = Math.max(0, prefix - (index << 3));
            }
            if (prefix > endIndex - index << 3) {
                prefix = null;
            }
        }
        result.assignPrefixLength(prefix);
        return result;
    }

    public MACAddressSection getOUISection() {
        int segmentCount = this.getOUISegmentCount();
        MACAddressSection result = MACAddressSection.getSection(0, segmentCount, this, this.getAddressCreator());
        Integer prefix = this.getPrefixLength();
        if (prefix != null && prefix > segmentCount << 3) {
            prefix = null;
        }
        result.assignPrefixLength(prefix);
        return result;
    }

    public MACAddressSection getODISection() {
        int segmentCount = this.getOUISegmentCount();
        MACAddressSection result = MACAddressSection.getSection(segmentCount, this.getSegmentCount(), this, this.getAddressCreator(this.addressSegmentIndex + segmentCount, this.extended));
        Integer prefix = this.getPrefixLength();
        if (prefix != null && segmentCount > 0) {
            prefix = Math.max(0, prefix - (segmentCount << 3));
        }
        result.assignPrefixLength(prefix);
        return result;
    }

    public MACAddressSection toOUIPrefixBlock() {
        int ouiSegmentCount = this.getOUISegmentCount();
        int segmentCount = this.getSegmentCount();
        Integer currentPref = this.getPrefixLength();
        int newPref = ouiSegmentCount << 3;
        boolean createNew = currentPref == null || currentPref > newPref;
        if (!createNew) {
            newPref = currentPref;
            for (int i = ouiSegmentCount; i < segmentCount; ++i) {
                MACAddressSegment segment = this.getSegment(i);
                if (segment.isFullRange()) continue;
                createNew = true;
                break;
            }
        }
        if (createNew) {
            MACAddressNetwork.MACAddressCreator creator = this.getAddressCreator();
            MACAddressSegment allRangeSegment = creator.createRangeSegment(0, 255);
            MACAddressSegment[] newSegments = (MACAddressSegment[])MACAddressSection.setPrefixedSegments((AddressNetwork)this.getNetwork(), (int)newPref, (AddressSegment[])this.getSegments(), (int)this.getBitsPerSegment(), (int)this.getBytesPerSegment(), (AddressNetwork.AddressSegmentCreator)creator, (T seg, U prefixLength) -> prefixLength == 0 ? allRangeSegment : seg);
            MACAddressSection result = creator.createSectionInternal(newSegments);
            result.assignPrefixLength(newPref);
            return result;
        }
        return this;
    }

    public IPv6AddressSection toEUI64IPv6() {
        return this.getIPv6Network().getAddressCreator().createSection(this);
    }

    public boolean isEUI64(boolean asMAC) {
        return this.isEUI64(asMAC, false);
    }

    public boolean isEUI64(boolean asMAC, boolean partial) {
        if (this.isExtended()) {
            int segmentCount = this.getSegmentCount();
            int endIndex = this.addressSegmentIndex + segmentCount;
            if (this.addressSegmentIndex <= 3) {
                if (endIndex > 4) {
                    int index3 = 3 - this.addressSegmentIndex;
                    MACAddressSegment seg3 = this.getSegment(index3);
                    MACAddressSegment seg4 = this.getSegment(index3 + 1);
                    return seg4.matches(asMAC ? 255 : 254) && seg3.matches(255);
                }
                if (partial && endIndex == 4) {
                    MACAddressSegment seg3 = this.getSegment(3 - this.addressSegmentIndex);
                    return seg3.matches(255);
                }
            } else if (partial && this.addressSegmentIndex == 4 && endIndex > 4) {
                MACAddressSegment seg4 = this.getSegment(4 - this.addressSegmentIndex);
                return seg4.matches(asMAC ? 255 : 254);
            }
            return partial;
        }
        return false;
    }

    public MACAddressSection toEUI64(boolean asMAC) {
        MACAddressSegment seg4;
        int originalSegmentCount = this.getSegmentCount();
        if (!this.isExtended()) {
            MACAddressSegment ffSegment;
            int frontCount;
            MACAddressNetwork.MACAddressCreator creator = this.getAddressCreator(this.addressSegmentIndex, true);
            if (this.addressSegmentIndex + originalSegmentCount < 3 || this.addressSegmentIndex > 3) {
                return this;
            }
            AddressSegment[] segs = creator.createSegmentArray(originalSegmentCount + 2);
            if (this.addressSegmentIndex < 3) {
                frontCount = 3 - this.addressSegmentIndex;
                this.getSegments(0, frontCount, segs, 0);
            } else {
                frontCount = 0;
            }
            segs[frontCount] = ffSegment = creator.createSegment(255);
            segs[frontCount + 1] = asMAC ? ffSegment : creator.createSegment(254);
            Integer prefLength = this.getPrefixLength();
            if (originalSegmentCount > frontCount) {
                this.getSegments(frontCount, originalSegmentCount, segs, frontCount + 2);
                if (prefLength != null && prefLength > frontCount << 3) {
                    prefLength = prefLength + (this.getBitsPerSegment() << 1);
                }
            }
            MACAddressSection result = creator.createSectionInternal((MACAddressSegment[])segs, this.addressSegmentIndex, true);
            result.assignPrefixLength(prefLength);
            return result;
        }
        int endIndex = this.addressSegmentIndex + originalSegmentCount;
        if (this.addressSegmentIndex <= 3) {
            MACAddressSegment seg3;
            if (endIndex > 4) {
                int index3 = 3 - this.addressSegmentIndex;
                MACAddressSegment seg32 = this.getSegment(index3);
                MACAddressSegment seg42 = this.getSegment(index3 + 1);
                if (!seg42.matches(asMAC ? 255 : 254) || !seg32.matches(255)) {
                    throw new IncompatibleAddressException(this, "ipaddress.mac.error.not.eui.convertible");
                }
            } else if (endIndex == 4 && !(seg3 = this.getSegment(3 - this.addressSegmentIndex)).matches(255)) {
                throw new IncompatibleAddressException(this, "ipaddress.mac.error.not.eui.convertible");
            }
        } else if (this.addressSegmentIndex == 4 && endIndex > 4 && !(seg4 = this.getSegment(4 - this.addressSegmentIndex)).matches(asMAC ? 255 : 254)) {
            throw new IncompatibleAddressException(this, "ipaddress.mac.error.not.eui.convertible");
        }
        return this;
    }

    public MACAddressSection append(MACAddressSection other) {
        int count = this.getSegmentCount();
        return this.replace(count, count, other, 0, other.getSegmentCount());
    }

    public MACAddressSection appendToPrefix(MACAddressSection other) {
        Integer prefixLength = this.getPrefixLength();
        if (prefixLength == null) {
            return this.append(other);
        }
        MACAddressSection thizz = this;
        int bitsPerSegment = this.getBitsPerSegment();
        int adjustment = prefixLength % bitsPerSegment;
        if (adjustment != 0) {
            prefixLength = prefixLength + (bitsPerSegment - adjustment);
            thizz = this.setPrefixLength(prefixLength, false, false);
        }
        int index = prefixLength >>> 3;
        if (other.isPrefixed() && other.getPrefixLength() == 0) {
            return this.insert(index, other);
        }
        return thizz.replace(index, index, other, 0, other.getSegmentCount(), true);
    }

    public MACAddressSection insert(int index, MACAddressSection other) {
        return this.replace(index, index, other, 0, other.getSegmentCount());
    }

    public MACAddressSection replace(int index, MACAddressSection replacement) {
        return this.replace(index, index + replacement.getSegmentCount(), replacement, 0, replacement.getSegmentCount());
    }

    public MACAddressSection replace(int startIndex, int endIndex, MACAddressSection replacement, int replacementStartIndex, int replacementEndIndex) {
        return this.replace(startIndex, endIndex, replacement, replacementStartIndex, replacementEndIndex, false);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private MACAddressSection replace(int startIndex, int endIndex, MACAddressSection replacement, int replacementStartIndex, int replacementEndIndex, boolean appendNetwork) {
        int segmentCount = this.getSegmentCount();
        int replacedCount = endIndex - startIndex;
        int replacementCount = replacementEndIndex - replacementStartIndex;
        if (replacedCount < 0 || replacementCount < 0 || startIndex < 0 || replacementStartIndex < 0 || replacementEndIndex > replacement.getSegmentCount() || endIndex > segmentCount) {
            throw new IndexOutOfBoundsException();
        }
        int diff = replacementCount - replacedCount;
        int totalSegmentCount = segmentCount + diff;
        if (this.addressSegmentIndex + totalSegmentCount > 8) {
            throw new AddressValueException(this, replacement, this.addressSegmentIndex + totalSegmentCount);
        }
        if (replacementCount == 0) {
            if (this.isPrefixed()) {
                if (!replacement.isPrefixed() || replacement.getPrefixLength() > replacementEndIndex << 3) return this;
                if (this.getPrefixLength() <= startIndex << 3) {
                    return this;
                }
            } else if (!replacement.isPrefixed()) {
                return this;
            }
        }
        if (segmentCount == replacedCount && this.addressSegmentIndex == replacement.addressSegmentIndex && this.extended == replacement.extended && (!this.isPrefixed() || replacement.isPrefixed() && replacement.getPrefixLength() == 0)) {
            return replacement;
        }
        MACAddressSection result = MACAddressSection.replace(this, startIndex, endIndex, replacement, replacementStartIndex, replacementEndIndex, this.getAddressCreator(), appendNetwork, true);
        if (this.isPrefixed()) {
            Integer prefLength = this.getPrefixLength();
            int startBits = startIndex << 3;
            if (!appendNetwork && prefLength <= startBits) {
                result.assignPrefixLength(prefLength);
                return result;
            } else if (replacement.isPrefixed() && replacement.getPrefixLength() <= replacementEndIndex << 3) {
                result.assignPrefixLength(Math.max(0, replacement.getPrefixLength() - (replacementStartIndex << 3)) + startBits);
                return result;
            } else if (prefLength <= endIndex << 3) {
                result.assignPrefixLength(startBits + (replacementCount << 3));
                return result;
            } else {
                result.assignPrefixLength(prefLength + (diff << 3));
            }
            return result;
        } else if (replacement.isPrefixed() && replacement.getPrefixLength() <= replacementEndIndex << 3) {
            result.assignPrefixLength(Math.max(0, replacement.getPrefixLength() - (replacementStartIndex << 3)) + (startIndex << 3));
            return result;
        } else {
            result.assignPrefixLength(null);
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private MACAddressSection getLowestOrHighestSection(boolean lowest) {
        MACAddressSection result = MACAddressSection.getSingleLowestOrHighestSection(this);
        if (result == null && (this.sectionCache == null || (result = lowest ? (MACAddressSection)this.sectionCache.lower : (MACAddressSection)this.sectionCache.upper) == null)) {
            MACAddressSection mACAddressSection = this;
            synchronized (mACAddressSection) {
                boolean create;
                boolean bl = create = this.sectionCache == null;
                if (create) {
                    this.sectionCache = new AddressDivisionGrouping.SectionCache();
                } else if (lowest) {
                    result = (MACAddressSection)this.sectionCache.lower;
                    create = result == null;
                } else {
                    result = (MACAddressSection)this.sectionCache.upper;
                    boolean bl2 = create = result == null;
                }
                if (create) {
                    Integer prefLength;
                    MACAddressNetwork.MACAddressCreator creator = this.getAddressCreator();
                    MACAddressSegment[] segs = (MACAddressSegment[])MACAddressSection.createSingle((AddressSection)this, (AddressNetwork.AddressSegmentCreator)creator, (int i) -> lowest ? this.getSegment(i).getLower() : this.getSegment(i).getUpper());
                    MACAddressSection mACAddressSection2 = result = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() || (prefLength = this.getPrefixLength()) == null ? creator.createSectionInternal(segs) : creator.createPrefixedSectionInternal(segs, prefLength, true);
                    if (lowest) {
                        this.sectionCache.lower = result;
                    } else {
                        this.sectionCache.upper = result;
                    }
                }
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    MACAddress getLowestOrHighest(MACAddress addr, boolean lowest) {
        MACAddressSection sectionResult = this.getLowestOrHighestSection(lowest);
        if (sectionResult == this) {
            return addr;
        }
        MACAddress result = null;
        MACAddressCache cache = addr.sectionCache;
        if (cache == null || (result = lowest ? (MACAddress)cache.lower : (MACAddress)cache.upper) == null) {
            MACAddressSection mACAddressSection = this;
            synchronized (mACAddressSection) {
                boolean create;
                cache = addr.sectionCache;
                boolean bl = create = cache == null;
                if (create) {
                    cache = addr.sectionCache = new MACAddressCache();
                } else if (lowest) {
                    result = (MACAddress)cache.lower;
                    create = result == null;
                } else {
                    result = (MACAddress)cache.upper;
                    boolean bl2 = create = result == null;
                }
                if (create) {
                    result = this.getAddressCreator().createAddress(sectionResult);
                    if (lowest) {
                        cache.lower = result;
                    } else {
                        cache.upper = result;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public MACAddressSection getLower() {
        return this.getLowestOrHighestSection(true);
    }

    @Override
    public MACAddressSection getUpper() {
        return this.getLowestOrHighestSection(false);
    }

    public long longValue() {
        return this.getLongValue(true);
    }

    public long upperLongValue() {
        return this.getLongValue(false);
    }

    private long getLongValue(boolean lower) {
        int segCount = this.getSegmentCount();
        long result = 0L;
        for (int i = 0; i < segCount; ++i) {
            MACAddressSegment seg = this.getSegment(i);
            result = result << this.getBitsPerSegment() | (long)(lower ? seg.getSegmentValue() : seg.getUpperSegmentValue());
        }
        return result;
    }

    @Override
    public MACAddressSection reverseBits(boolean perByte) {
        MACAddressSection result = MACAddressSection.reverseBits(perByte, this, this.getAddressCreator(), i -> this.getSegment(i).reverseBits(perByte), false);
        result.assignPrefixLength(null);
        return result;
    }

    @Override
    public MACAddressSection reverseBytes() {
        return this.reverseSegments();
    }

    @Override
    public MACAddressSection reverseBytesPerSegment() {
        return this;
    }

    @Override
    public MACAddressSection reverseSegments() {
        if (this.getSegmentCount() <= 1) {
            return this;
        }
        MACAddressSection result = MACAddressSection.reverseSegments(this, this.getAddressCreator(), this::getSegment, false);
        result.assignPrefixLength(null);
        return result;
    }

    @Override
    @Deprecated
    public MACAddressSection removePrefixLength() {
        return this.removePrefixLength(true);
    }

    @Override
    public MACAddressSection withoutPrefixLength() {
        return this.removePrefixLength(false);
    }

    @Override
    @Deprecated
    public MACAddressSection removePrefixLength(boolean zeroed) {
        if (this.getPrefixLength() == null) {
            return this;
        }
        return this.removePrefix(zeroed);
    }

    private MACAddressSection removePrefix(boolean zeroed) {
        AddressSegment[] oldSegs = this.getSegmentsInternal();
        MACAddressSegment[] newSegs = (MACAddressSegment[])MACAddressSection.removePrefix((AddressSection)this, (AddressSegment[])oldSegs, (int)this.getBitsPerSegment(), (S seg, Integer oldPrefLength, Integer newPrefLength) -> seg.setPrefixedSegment(oldPrefLength, newPrefLength, zeroed));
        MACAddressSection result = this.getAddressCreator().createSectionInternal(newSegs);
        result.assignPrefixLength(null);
        return result;
    }

    @Override
    public MACAddressSection adjustPrefixBySegment(boolean nextSegment) {
        return this.adjustPrefixBySegment(nextSegment, true);
    }

    @Override
    public MACAddressSection adjustPrefixBySegment(boolean nextSegment, boolean zeroed) {
        Integer existing = this.getPrefixLength();
        if (existing == null && nextSegment) {
            return this;
        }
        int prefix = this.getAdjustedPrefix(nextSegment, this.getBitsPerSegment(), true);
        return this.setPrefixLength(prefix, zeroed);
    }

    @Override
    public MACAddressSection adjustPrefixLength(int adjustment) {
        return this.adjustPrefixLength(adjustment, true);
    }

    @Override
    public MACAddressSection adjustPrefixLength(int adjustment, boolean zeroed) {
        if (adjustment == 0) {
            return this;
        }
        int prefix = this.getAdjustedPrefix(adjustment, true, false);
        if (prefix > this.getBitCount()) {
            return this.removePrefix(zeroed);
        }
        return this.setPrefixLength(prefix, zeroed);
    }

    @Override
    @Deprecated
    public MACAddressSection applyPrefixLength(int prefixLength) {
        return this.setPrefixLength(prefixLength, true, true);
    }

    @Override
    public MACAddressSection setPrefixLength(int prefixLength) {
        return this.setPrefixLength(prefixLength, true, false);
    }

    @Override
    public MACAddressSection setPrefixLength(int prefixLength, boolean zeroed) {
        return this.setPrefixLength(prefixLength, zeroed, false);
    }

    private MACAddressSection setPrefixLength(int prefixLength, boolean zeroed, boolean noShrink) {
        boolean prefixGrowing;
        Integer oldPrefix;
        if (prefixLength < 0) {
            throw new PrefixLenException(prefixLength);
        }
        int max = this.getBitCount();
        if (prefixLength > max) {
            if (prefixLength > (this.extended ? 64 : 48)) {
                throw new PrefixLenException(prefixLength);
            }
            prefixLength = max;
        }
        boolean prefixShrinking = (oldPrefix = this.getPrefixLength()) == null || oldPrefix > prefixLength;
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (prefixShrinking) {
            prefixGrowing = false;
        } else {
            boolean bl = prefixGrowing = !noShrink && oldPrefix < prefixLength;
            if (!prefixGrowing && !isAllSubnets) {
                return this;
            }
        }
        MACAddressNetwork.MACAddressCreator creator = this.getAddressCreator();
        MACAddressSegment[] oldSegs = this.getSegmentsInternal();
        int segmentBitCount = this.getBitsPerSegment();
        int segmentByteCount = this.getBytesPerSegment();
        if (isAllSubnets) {
            if (prefixShrinking) {
                MACAddressSegment[] newSegs = (MACAddressSegment[])MACAddressSection.setPrefixedSegments((AddressNetwork)this.getNetwork(), (int)prefixLength, (AddressSegment[])((MACAddressSegment[])oldSegs.clone()), (int)segmentBitCount, (int)segmentByteCount, (AddressNetwork.AddressSegmentCreator)creator, MACAddressSegment::toPrefixBlockSegment);
                MACAddressSection result = creator.createSectionInternal(newSegs);
                result.assignPrefixLength(prefixLength);
                return result;
            }
            if (!prefixGrowing) {
                return this.toPrefixBlock();
            }
        }
        Object[] newSegs = (MACAddressSegment[])oldSegs.clone();
        for (int i = 0; i < newSegs.length; ++i) {
            Integer newPref = MACAddressSection.getPrefixedSegmentPrefixLength(this.getBitsPerSegment(), prefixLength, i);
            Integer oldPref = oldPrefix == null ? null : MACAddressSection.getPrefixedSegmentPrefixLength(this.getBitsPerSegment(), oldPrefix, i);
            newSegs[i] = newSegs[i].setPrefixedSegment(oldPref, newPref, zeroed);
            if (!isAllSubnets || newPref == null || ++i >= newSegs.length) continue;
            MACAddressSegment zeroSeg = creator.createRangeSegment(0, 255);
            Arrays.fill(newSegs, i, newSegs.length, zeroSeg);
            break;
        }
        MACAddressSection result = creator.createSectionInternal((MACAddressSegment[])newSegs);
        result.assignPrefixLength(prefixLength);
        return result;
    }

    @Override
    public MACAddressSection toPrefixBlock() {
        Integer prefixLength = this.getPrefixLength();
        if (prefixLength != null) {
            int segmentBitCount = this.getBitsPerSegment();
            int segmentByteCount = this.getBytesPerSegment();
            MACAddressSegment[] oldSegs = this.getSegmentsInternal();
            for (int i = MACAddressSection.getHostSegmentIndex(prefixLength, segmentByteCount, segmentBitCount); i < oldSegs.length; ++i) {
                Integer pref = MACAddressSection.getPrefixedSegmentPrefixLength(segmentBitCount, prefixLength, i);
                MACAddressSegment seg = oldSegs[i];
                if (pref == null || seg.isPrefixBlock(pref)) continue;
                MACAddressNetwork.MACAddressCreator creator = this.getAddressCreator();
                MACAddressSegment[] newSegs = (MACAddressSegment[])MACAddressSection.setPrefixedSegments((AddressNetwork)this.getNetwork(), (int)prefixLength, (AddressSegment[])((MACAddressSegment[])oldSegs.clone()), (int)segmentBitCount, (int)segmentByteCount, (AddressNetwork.AddressSegmentCreator)creator, MACAddressSegment::toPrefixBlockSegment);
                MACAddressSection result = creator.createSectionInternal(newSegs);
                result.assignPrefixLength(prefixLength);
                return result;
            }
        }
        return this;
    }

    public Iterable<MACAddressSection> getIterable() {
        return this;
    }

    @Override
    public Iterator<MACAddressSection> iterator() {
        MACAddressNetwork.MACAddressCreator creator = this.getAddressCreator();
        boolean isSingle = !this.isMultiple();
        return MACAddressSection.iterator(isSingle, this, creator, isSingle ? null : this.segmentsIterator(), this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() ? null : this.getPrefixLength());
    }

    public Iterator<MACAddressSection> prefixIterator() {
        return this.prefixIterator(false);
    }

    public Iterator<MACAddressSection> prefixBlockIterator() {
        return this.prefixIterator(true);
    }

    private Iterator<MACAddressSection> prefixIterator(boolean isBlockIterator) {
        Integer prefLength = this.getPrefixLength();
        if (prefLength == null || prefLength > this.getBitCount()) {
            return this.iterator();
        }
        MACAddressNetwork.MACAddressCreator creator = this.getAddressCreator();
        boolean useOriginal = isBlockIterator ? this.isSinglePrefixBlock() : this.getPrefixCount().equals(BigInteger.ONE);
        int networkSegIndex = MACAddressSection.getNetworkSegmentIndex(prefLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int hostSegIndex = MACAddressSection.getHostSegmentIndex(prefLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int segCount = this.getSegmentCount();
        return MACAddressSection.iterator(useOriginal, this, creator, useOriginal ? null : MACAddressSection.segmentsIterator(segCount, creator, null, index -> this.getSegment(index).iterator(), null, networkSegIndex, hostSegIndex, isBlockIterator ? index -> this.getSegment(index).prefixBlockIterator(MACAddressSection.getSegmentPrefixLength(this.getBitsPerSegment(), prefLength, index)) : index -> this.getSegment(index).prefixIterator(MACAddressSection.getSegmentPrefixLength(this.getBitsPerSegment(), prefLength, index))), prefLength);
    }

    public Iterator<MACAddressSegment[]> segmentsIterator() {
        return MACAddressSection.segmentsIterator(this.getSegmentCount(), this.getSegmentCreator(), this.isMultiple() ? null : () -> this.getLower().getSegments(), index -> this.getSegment(index).iterator(), null);
    }

    public AddressComponentRangeSpliterator<MACAddressSection, MACAddressSegment[]> segmentsSpliterator() {
        MACAddressSection forIteration;
        Integer iterationsPrefix;
        int segmentCount = this.getSegmentCount();
        Integer prefixLength = this.getPrefixLength();
        MACAddressNetwork.MACAddressCreator creator = this.getAddressCreator();
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (isAllSubnets) {
            iterationsPrefix = null;
            forIteration = this.withoutPrefixLength();
        } else {
            iterationsPrefix = prefixLength;
            forIteration = this;
        }
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return MACAddressSection.createItemSpliterator(forIteration, spliterator -> MACAddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (MACAddressSection)MACAddressSection.createIteratedSection((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((MACAddressSection)spliterator.getAddressItem()).getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), (isLowest, isHighest, section) -> section.segmentsIterator(), AddressDivisionGroupingBase::getCount, section -> section.getCount().compareTo(LONG_MAX) <= 0, section -> MACAddressSection.longCount(section, segmentCount));
    }

    public Stream<MACAddressSegment[]> segmentsStream() {
        return StreamSupport.stream(this.segmentsSpliterator(), false);
    }

    AddressComponentRangeSpliterator<MACAddress, MACAddressSegment[]> segmentsSpliterator(MACAddress address, MACAddressNetwork.MACAddressCreator creator) {
        MACAddress forIteration;
        Integer iterationsPrefix;
        int segmentCount = this.getSegmentCount();
        Integer prefixLength = this.getPrefixLength();
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (isAllSubnets) {
            iterationsPrefix = null;
            forIteration = address.withoutPrefixLength();
        } else {
            iterationsPrefix = prefixLength;
            forIteration = address;
        }
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return MACAddressSection.createItemSpliterator(forIteration, spliterator -> MACAddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (MACAddress)MACAddressSection.createIteratedAddress((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((MACAddress)spliterator.getAddressItem()).getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), (isLowest, isHighest, addr) -> addr.segmentsIterator(), Address::getCount, addr -> addr.getCount().compareTo(LONG_MAX) <= 0, addr -> MACAddressSection.longCount(addr.getSection(), segmentCount));
    }

    @Override
    public AddressComponentSpliterator<MACAddressSection> spliterator() {
        MACAddressSection forIteration;
        Integer iterationsPrefix;
        int segmentCount = this.getSegmentCount();
        Integer prefixLength = this.getPrefixLength();
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (isAllSubnets) {
            iterationsPrefix = null;
            forIteration = this.withoutPrefixLength();
        } else {
            iterationsPrefix = prefixLength;
            forIteration = this;
        }
        MACAddressNetwork.MACAddressCreator creator = this.getAddressCreator();
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return MACAddressSection.createSeriesSpliterator(forIteration, spliterator -> MACAddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (MACAddressSection)MACAddressSection.createIteratedSection((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((MACAddressSection)spliterator.getAddressItem()).getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), (isLowest, isHighest, section) -> section.iterator(), AddressDivisionGroupingBase::getCount, section -> section.getCount().compareTo(LONG_MAX) <= 0, section -> MACAddressSection.longCount(section, segmentCount));
    }

    public Stream<MACAddressSection> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    protected AddressComponentSpliterator<MACAddress> spliterator(MACAddress original, MACAddressNetwork.MACAddressCreator creator) {
        MACAddress forIteration;
        Integer iterationsPrefix;
        int segmentCount = this.getSegmentCount();
        Integer prefixLength = this.getPrefixLength();
        boolean isAllSubnets = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (isAllSubnets) {
            iterationsPrefix = null;
            forIteration = original.withoutPrefixLength();
        } else {
            iterationsPrefix = prefixLength;
            forIteration = original;
        }
        int networkSegIndex = segmentCount - 1;
        int hostSegIndex = segmentCount;
        return MACAddressSection.createSeriesSpliterator(forIteration, spliterator -> MACAddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (MACAddress)MACAddressSection.createIteratedAddress((AddressSegment[])segs, (AddressCreator)creator, (Integer)iterationsPrefix), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((MACAddress)spliterator.getAddressItem()).getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)iterationsPrefix), (isLowest, isHighest, addr) -> addr.iterator(), Address::getCount, addr -> addr.getCount().compareTo(LONG_MAX) <= 0, addr -> MACAddressSection.longCount(addr.getSection(), segmentCount));
    }

    protected Iterator<MACAddress> iterator(MACAddress original, MACAddressNetwork.MACAddressCreator creator) {
        boolean isSingle = !this.isMultiple();
        return MACAddressSection.iterator(isSingle, original, creator, isSingle ? null : this.segmentsIterator(), this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() ? null : this.getPrefixLength());
    }

    Iterator<MACAddress> prefixIterator(MACAddress original, MACAddressNetwork.MACAddressCreator creator, boolean isBlockIterator) {
        Integer prefLength = this.getPrefixLength();
        if (prefLength == null || prefLength > this.getBitCount()) {
            return this.iterator(original, creator);
        }
        boolean useOriginal = this.isSinglePrefixBlock();
        int networkSegIndex = MACAddressSection.getNetworkSegmentIndex(prefLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int hostSegIndex = MACAddressSection.getHostSegmentIndex(prefLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int segCount = this.getSegmentCount();
        return MACAddressSection.iterator(useOriginal, original, creator, useOriginal ? null : MACAddressSection.segmentsIterator(segCount, creator, null, index -> this.getSegment(index).iterator(), null, networkSegIndex, hostSegIndex, isBlockIterator ? index -> this.getSegment(index).prefixBlockIterator(MACAddressSection.getSegmentPrefixLength(this.getBitsPerSegment(), prefLength, index)) : index -> this.getSegment(index).prefixIterator(MACAddressSection.getSegmentPrefixLength(this.getBitsPerSegment(), prefLength, index))), prefLength);
    }

    AddressComponentSpliterator<MACAddress> prefixSpliterator(MACAddress original, MACAddressNetwork.MACAddressCreator creator, boolean isBlockIterator) {
        Integer prefLength = this.getPrefixLength();
        if (prefLength == null || prefLength > this.getBitCount()) {
            return this.spliterator(original, creator);
        }
        int prefixLength = prefLength;
        int networkSegIndex = MACAddressSection.getNetworkSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int hostSegIndex = MACAddressSection.getHostSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        return MACAddressSection.createSeriesSpliterator(original, spliterator -> MACAddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (MACAddress)MACAddressSection.createIteratedAddress((AddressSegment[])segs, (AddressCreator)creator, (Integer)prefLength), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((MACAddress)spliterator.getAddressItem()).getSection().getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)prefLength), isBlockIterator ? (isLowest, isHighest, addr) -> addr.prefixBlockIterator() : (!this.isSequential() ? (isLowest, isHighest, addr) -> addr.prefixIterator() : (isLowest, isHighest, addr) -> isLowest || isHighest ? addr.prefixIterator() : addr.prefixBlockIterator()), Address::getPrefixCount, addr -> addr.getPrefixCount().compareTo(LONG_MAX) <= 0, addr -> MACAddressSection.longPrefixCount(addr.getSection(), prefixLength));
    }

    public AddressComponentSpliterator<MACAddressSection> prefixSpliterator() {
        return this.prefixSpliterator(false);
    }

    public Stream<MACAddressSection> prefixStream() {
        return StreamSupport.stream(this.prefixSpliterator(), false);
    }

    public AddressComponentSpliterator<MACAddressSection> prefixBlockSpliterator() {
        return this.prefixSpliterator(true);
    }

    public Stream<MACAddressSection> prefixBlockStream() {
        return StreamSupport.stream(this.prefixBlockSpliterator(), false);
    }

    AddressComponentSpliterator<MACAddressSection> prefixSpliterator(boolean isBlockIterator) {
        Integer prefLength = this.getPrefixLength();
        if (prefLength == null || prefLength > this.getBitCount()) {
            return this.spliterator();
        }
        int prefixLength = prefLength;
        MACAddressNetwork.MACAddressCreator creator = this.getAddressCreator();
        int networkSegIndex = MACAddressSection.getNetworkSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        int hostSegIndex = MACAddressSection.getHostSegmentIndex(prefixLength, this.getBytesPerSegment(), this.getBitsPerSegment());
        return MACAddressSection.createSeriesSpliterator(this, spliterator -> MACAddressSection.split((AddressDivisionGroupingBase.SplitterSink)spliterator, (T segs) -> (MACAddressSection)MACAddressSection.createIteratedSection((AddressSegment[])segs, (AddressCreator)creator, (Integer)prefLength), (AddressNetwork.AddressSegmentCreator)creator, (AddressSegment[])((MACAddressSection)spliterator.getAddressItem()).getSegmentsInternal(), (int)networkSegIndex, (int)hostSegIndex, (Integer)prefLength), isBlockIterator ? (isLowest, isHighest, section) -> section.prefixBlockIterator() : (!this.isSequential() ? (isLowest, isHighest, section) -> section.prefixIterator() : (isLowest, isHighest, section) -> isLowest || isHighest ? section.prefixIterator() : section.prefixBlockIterator()), AddressDivisionGroupingBase::getPrefixCount, section -> section.getPrefixCount().compareTo(LONG_MAX) <= 0, section -> MACAddressSection.longPrefixCount(section, prefixLength));
    }

    private static long getMaxValueLong(int segmentCount) {
        return MAX_VALUES_LONG[segmentCount];
    }

    private static BigInteger getMaxValue(int segmentCount) {
        return MAX_VALUES[segmentCount];
    }

    @Override
    public MACAddressSection incrementBoundary(long increment) {
        if (increment <= 0L) {
            if (increment == 0L) {
                return this;
            }
            return this.getLower().increment(increment);
        }
        return this.getUpper().increment(increment);
    }

    @Override
    public MACAddressSection increment(long increment) {
        if (increment == 0L && !this.isMultiple()) {
            return this;
        }
        if (!this.isExtended() || this.getSegmentCount() < 8) {
            long lowerValue = this.longValue();
            long upperValue = this.upperLongValue();
            long count = this.getCount().longValue();
            MACAddressSection.checkOverflow(increment, lowerValue, upperValue, count, () -> MACAddressSection.getMaxValueLong(this.getSegmentCount()));
            return MACAddressSection.increment(this, increment, this.getAddressCreator(), this.getCount().longValue(), this.longValue(), this.upperLongValue(), this::getLower, this::getUpper, this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() ? null : this.getPrefixLength());
        }
        BigInteger lowerValue = this.getValue();
        BigInteger upperValue = this.getUpperValue();
        BigInteger count = this.getCount();
        BigInteger bigIncrement = BigInteger.valueOf(increment);
        MACAddressSection.checkOverflow(increment, bigIncrement, lowerValue, upperValue, count, () -> MACAddressSection.getMaxValue(this.getSegmentCount()));
        Integer prefixLength = this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() ? null : this.getPrefixLength();
        MACAddressSection result = MACAddressSection.fastIncrement(this, increment, this.getAddressCreator(), this::getLower, this::getUpper, prefixLength);
        if (result != null) {
            return result;
        }
        return MACAddressSection.increment(this, increment, bigIncrement, this.getAddressCreator(), this::getLower, this::getUpper, this.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() ? null : this.getPrefixLength());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean hasNoStringCache() {
        if (this.stringCache == null) {
            MACAddressSection mACAddressSection = this;
            synchronized (mACAddressSection) {
                if (this.stringCache == null) {
                    this.stringCache = new MACStringCache();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toHexString(boolean with0xPrefix) throws IncompatibleAddressException {
        String result;
        if (this.hasNoStringCache() || (result = with0xPrefix ? this.stringCache.hexStringPrefixed : this.stringCache.hexString) == null) {
            result = this.toHexString(with0xPrefix, null);
            if (with0xPrefix) {
                this.stringCache.hexStringPrefixed = result;
            } else {
                this.stringCache.hexString = result;
            }
        }
        return result;
    }

    protected String toHexString(boolean with0xPrefix, CharSequence zone) throws IncompatibleAddressException {
        if (this.isDualString()) {
            return MACAddressSection.toNormalizedStringRange(AddressDivisionGroupingBase.AddressStringParams.toParams(with0xPrefix ? MACStringCache.hexPrefixedParams : MACStringCache.hexParams), this.getLower(), this.getUpper(), null);
        }
        return this.toNormalizedString(with0xPrefix ? MACStringCache.hexPrefixedParams : MACStringCache.hexParams);
    }

    public String toNormalizedString(AddressDivisionGrouping.StringOptions stringOptions) {
        return MACAddressSection.toNormalizedString(stringOptions, this);
    }

    public static String toNormalizedString(AddressDivisionGrouping.StringOptions opts, AddressDivisionGrouping section) {
        return MACAddressSection.toParams(opts).toString(section);
    }

    @Override
    public String toNormalizedString() {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().normalizedString) == null) {
            this.getStringCache().normalizedString = result = this.toNormalizedString(MACStringCache.normalizedParams);
        }
        return result;
    }

    @Override
    public String toCanonicalString() {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().canonicalString) == null) {
            this.getStringCache().canonicalString = result = this.toNormalizedString(MACStringCache.canonicalParams);
        }
        return result;
    }

    @Override
    public String toCompressedString() {
        String result;
        if (this.hasNoStringCache() || (result = this.getStringCache().compressedString) == null) {
            this.getStringCache().compressedString = result = this.toNormalizedString(MACStringCache.compressedParams);
        }
        return result;
    }

    public String toDottedString() throws IncompatibleAddressException {
        String result = null;
        if (this.hasNoStringCache() || (result = this.getStringCache().dottedString) == null) {
            AddressDivisionGrouping dottedGrouping = this.getDottedGrouping();
            this.getStringCache().dottedString = result = MACAddressSection.toNormalizedString(MACStringCache.dottedParams, dottedGrouping);
        }
        return result;
    }

    public String toSpaceDelimitedString() {
        String result = null;
        if (this.hasNoStringCache() || (result = this.getStringCache().spaceDelimitedString) == null) {
            this.getStringCache().spaceDelimitedString = result = this.toNormalizedString(MACStringCache.spaceDelimitedParams);
        }
        return result;
    }

    public String toDashedString() {
        return this.toCanonicalString();
    }

    public String toColonDelimitedString() {
        return this.toNormalizedString();
    }

    @Override
    public String toString() {
        return this.toNormalizedString();
    }

    @Override
    public String[] getSegmentStrings() {
        return this.getDivisionStrings();
    }

    public AddressDivisionGrouping getDottedGrouping() {
        AddressDivisionGrouping dottedGrouping;
        int newSegIndex;
        int segIndex;
        AddressDivision[] newSegs;
        int start2 = this.addressSegmentIndex;
        int segmentCount = this.getSegmentCount();
        int newSegmentBitCount = this.getBitsPerSegment() << 1;
        if ((start2 & 1) == 0) {
            int newSegmentCount = segmentCount + 1 >>> 1;
            newSegs = new AddressDivision[newSegmentCount];
            segIndex = 0;
            newSegIndex = 0;
        } else {
            int newSegmentCount = (segmentCount >>> 1) + 1;
            newSegs = new AddressDivision[newSegmentCount];
            MACAddressSegment segment = this.getSegment(0);
            newSegs[0] = new AddressBitsDivision(segment.getSegmentValue(), segment.getUpperSegmentValue(), newSegmentBitCount, 16);
            segIndex = 1;
            newSegIndex = 1;
        }
        while (segIndex + 1 < segmentCount) {
            MACAddressSegment segment1 = this.getSegment(segIndex++);
            MACAddressSegment segment2 = this.getSegment(segIndex++);
            if (segment1.isMultiple() && !segment2.isFullRange()) {
                throw new IncompatibleAddressException(segment1, segIndex - 2, segment2, segIndex - 1, "ipaddress.error.invalid.joined.ranges");
            }
            AddressBitsDivision newSeg = new AddressBitsDivision(segment1.getSegmentValue() << this.getBitsPerSegment() | segment2.getSegmentValue(), segment1.getUpperSegmentValue() << this.getBitsPerSegment() | segment2.getUpperSegmentValue(), newSegmentBitCount, 16);
            newSegs[newSegIndex++] = newSeg;
        }
        if (segIndex < segmentCount) {
            MACAddressSegment segment = this.getSegment(segIndex);
            newSegs[newSegIndex] = new AddressBitsDivision(segment.getSegmentValue() << this.getBitsPerSegment(), segment.getUpperSegmentValue() << this.getBitsPerSegment(), newSegmentBitCount, 16);
        }
        if (this.cachedPrefixLength == null) {
            dottedGrouping = new AddressDivisionGrouping(newSegs);
        } else {
            final Integer prefLength = this.cachedPrefixLength;
            dottedGrouping = new AddressDivisionGrouping(newSegs){
                {
                    super(divisions);
                    this.cachedPrefixLength = prefLength;
                }
            };
        }
        return dottedGrouping;
    }

    static String toNormalizedString(IPAddressSection.IPStringOptions opts, AddressStringDivisionSeries section) {
        return MACAddressSection.toParams(opts).toString(section);
    }

    @Override
    public boolean contains(AddressSection other) {
        return other instanceof MACAddressSection && this.contains((MACAddressSection)other);
    }

    public boolean contains(MACAddressSection other) {
        if (this.addressSegmentIndex != other.addressSegmentIndex || this.isExtended() != other.isExtended() || this.getSegmentCount() != other.getSegmentCount()) {
            return false;
        }
        for (int i = 0; i < this.getSegmentCount(); ++i) {
            if (this.getSegment(i).contains(other.getSegment(i))) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean prefixEquals(AddressSection o) {
        if (o instanceof MACAddressSection) {
            MACAddressSection other = (MACAddressSection)o;
            if (this.addressSegmentIndex >= other.addressSegmentIndex) {
                return MACAddressSection.prefixEquals(this, other, this.addressSegmentIndex - other.addressSegmentIndex);
            }
        }
        return false;
    }

    protected static AddressDivisionGroupingBase.AddressStringParams<AddressStringDivisionSeries> toParams(AddressDivisionGrouping.StringOptions opts) {
        return AddressDivisionGroupingBase.AddressStringParams.toParams(opts);
    }

    public static class MACStringOptions
    extends AddressDivisionGrouping.StringOptions {
        protected MACStringOptions(int base, boolean expandSegments, AddressDivisionGrouping.StringOptions.Wildcards wildcards, String segmentStrPrefix, Character separator, String label, boolean reverse, boolean splitDigits, boolean uppercase) {
            super(base, expandSegments, wildcards, segmentStrPrefix, separator, label, reverse, splitDigits, uppercase);
        }

        public static class Builder
        extends AddressDivisionGrouping.StringOptions.Builder {
            public Builder() {
                this(16, ':');
            }

            protected Builder(int base, char separator) {
                super(base, separator);
            }

            @Override
            public MACStringOptions toOptions() {
                return new MACStringOptions(this.base, this.expandSegments, this.wildcards, this.segmentStrPrefix, this.separator, this.addrLabel, this.reverse, this.splitDigits, this.uppercase);
            }
        }
    }

    static class MACAddressCache
    extends AddressDivisionGrouping.SectionCache<MACAddress> {
        MACAddressCache() {
        }
    }

    protected static class MACStringCache
    extends AddressDivisionGrouping.StringCache {
        static final AddressDivisionGrouping.StringOptions hexParams = new MACStringOptions.Builder().setSeparator(null).setExpandedSegments(true).setRadix(16).toOptions();
        static final AddressDivisionGrouping.StringOptions hexPrefixedParams = new MACStringOptions.Builder().setSeparator(null).setExpandedSegments(true).setRadix(16).setAddressLabel("0x").toOptions();
        static final AddressDivisionGrouping.StringOptions canonicalParams;
        static final AddressDivisionGrouping.StringOptions compressedParams;
        static final AddressDivisionGrouping.StringOptions normalizedParams;
        static final AddressDivisionGrouping.StringOptions dottedParams;
        static final AddressDivisionGrouping.StringOptions spaceDelimitedParams;
        public String compressedString;
        public String normalizedString;
        public String dottedString;
        public String spaceDelimitedString;

        protected MACStringCache() {
        }

        static {
            normalizedParams = new MACStringOptions.Builder().setSeparator(Character.valueOf(':')).setExpandedSegments(true).setRadix(16).toOptions();
            canonicalParams = new MACStringOptions.Builder().setSeparator(Character.valueOf('-')).setExpandedSegments(true).setRadix(16).setWildcards(new AddressDivisionGrouping.StringOptions.Wildcards(MACAddress.DASHED_SEGMENT_RANGE_SEPARATOR_STR, Address.SEGMENT_WILDCARD_STR, null)).toOptions();
            compressedParams = new MACStringOptions.Builder().setSeparator(Character.valueOf(':')).setRadix(16).toOptions();
            dottedParams = new MACStringOptions.Builder().setSeparator(Character.valueOf('.')).setExpandedSegments(true).setRadix(16).toOptions();
            spaceDelimitedParams = new MACStringOptions.Builder().setSeparator(Character.valueOf(' ')).setExpandedSegments(true).setRadix(16).toOptions();
        }
    }
}

