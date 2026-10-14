/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format;

import inet.ipaddr.AddressComponent;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.HostIdentifierException;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressComponentRange;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.AddressDivisionSeries;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.AddressItemSpliteratorBase;
import inet.ipaddr.format.AddressSeriesSpliterator;
import inet.ipaddr.format.IPAddressDivisionSeries;
import inet.ipaddr.format.IPAddressGenericDivision;
import inet.ipaddr.format.standard.AddressDivisionGrouping;
import inet.ipaddr.format.string.AddressStringDivision;
import inet.ipaddr.format.string.AddressStringDivisionSeries;
import inet.ipaddr.format.string.IPAddressStringDivision;
import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.format.util.AddressDivisionWriter;
import inet.ipaddr.format.util.AddressSegmentParams;
import inet.ipaddr.format.util.IPAddressStringWriter;
import inet.ipaddr.format.validate.ParsedAddressGrouping;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;

public abstract class AddressDivisionGroupingBase
implements AddressDivisionSeries {
    private static final long serialVersionUID = 1L;
    protected static final Integer NO_PREFIX_LENGTH = -1;
    static final BigInteger ALL_ONES = BigInteger.ZERO.not();
    protected static BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);
    static ResourceBundle bundle;
    protected transient ValueCache valueCache;
    private final AddressDivisionBase[] divisions;
    protected Integer cachedPrefixLength;
    private transient Boolean isMultiple;
    private transient BigInteger cachedCount;
    private transient BigInteger cachedPrefixCount;
    protected transient int hashCode;

    public AddressDivisionGroupingBase(AddressDivisionBase[] divisions) {
        this(divisions, true);
    }

    public AddressDivisionGroupingBase(AddressDivisionBase[] divisions, boolean checkDivisions) {
        this.divisions = divisions;
        if (checkDivisions) {
            for (int i = 0; i < divisions.length; ++i) {
                if (divisions[i] != null) continue;
                throw new NullPointerException(AddressDivisionGroupingBase.getMessage("ipaddress.error.null.segment"));
            }
        }
    }

    protected static String getMessage(String key) {
        if (bundle != null) {
            try {
                return bundle.getString(key);
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        return key;
    }

    @Override
    public AddressDivisionBase getDivision(int index) {
        return this.getDivisionsInternal()[index];
    }

    protected void initCachedValues(Integer cachedNetworkPrefixLength, BigInteger cachedCount) {
        this.cachedPrefixLength = cachedNetworkPrefixLength == null ? NO_PREFIX_LENGTH : cachedNetworkPrefixLength;
        this.cachedCount = cachedCount;
    }

    @Override
    public int getDivisionCount() {
        return this.getDivisionsInternal().length;
    }

    @Override
    public byte[] getBytes() {
        return (byte[])this.getBytesInternal().clone();
    }

    protected byte[] getBytesInternal() {
        byte[] cached;
        block3: {
            block2: {
                if (this.hasNoValueCache()) break block2;
                cached = this.valueCache.lowerBytes;
                if (this.valueCache.lowerBytes != null) break block3;
            }
            this.valueCache.lowerBytes = cached = this.getBytesImpl(true);
        }
        return cached;
    }

    @Override
    public byte[] getBytes(byte[] bytes, int index) {
        return AddressDivisionGroupingBase.getBytesCopy(bytes, index, this.getBytesInternal());
    }

    @Override
    public byte[] getBytes(byte[] bytes) {
        return this.getBytes(bytes, 0);
    }

    private static byte[] getBytesCopy(byte[] bytes, int startIndex, byte[] cached) {
        int byteCount = cached.length;
        if (bytes == null || bytes.length < byteCount + startIndex) {
            if (startIndex > 0) {
                byte[] bytes2 = new byte[byteCount + startIndex];
                if (bytes != null) {
                    System.arraycopy(bytes, 0, bytes2, 0, Math.min(startIndex, bytes.length));
                }
                System.arraycopy(cached, 0, bytes2, startIndex, byteCount);
                return bytes2;
            }
            return (byte[])cached.clone();
        }
        System.arraycopy(cached, 0, bytes, startIndex, byteCount);
        return bytes;
    }

    @Override
    public byte[] getUpperBytes() {
        return (byte[])this.getUpperBytesInternal().clone();
    }

    protected byte[] getUpperBytesInternal() {
        byte[] cached;
        if (this.hasNoValueCache()) {
            ValueCache cache = this.valueCache;
            cache.upperBytes = cached = this.getBytesImpl(false);
            if (!this.isMultiple()) {
                cache.lowerBytes = cached;
            }
        } else {
            ValueCache cache = this.valueCache;
            cached = cache.upperBytes;
            if (cache.upperBytes == null) {
                if (!this.isMultiple()) {
                    cached = cache.lowerBytes;
                    if (cache.lowerBytes != null) {
                        cache.upperBytes = cached;
                    } else {
                        cache.upperBytes = cached = this.getBytesImpl(false);
                        cache.lowerBytes = cached;
                    }
                } else {
                    cache.upperBytes = cached = this.getBytesImpl(false);
                }
            }
        }
        return cached;
    }

    @Override
    public byte[] getUpperBytes(byte[] bytes, int index) {
        return AddressDivisionGroupingBase.getBytesCopy(bytes, index, this.getUpperBytesInternal());
    }

    @Override
    public byte[] getUpperBytes(byte[] bytes) {
        return this.getBytes(bytes, 0);
    }

    protected abstract byte[] getBytesImpl(boolean var1);

    protected void setBytes(byte[] bytes) {
        if (this.valueCache == null) {
            this.valueCache = new ValueCache();
        }
        this.valueCache.lowerBytes = bytes;
    }

    protected void setUpperBytes(byte[] bytes) {
        if (this.valueCache == null) {
            this.valueCache = new ValueCache();
        }
        this.valueCache.upperBytes = bytes;
    }

    @Override
    public BigInteger getValue() {
        BigInteger cached;
        if (this.hasNoValueCache() || (cached = this.valueCache.value) == null) {
            this.valueCache.value = cached = new BigInteger(1, this.getBytesInternal());
        }
        return cached;
    }

    @Override
    public BigInteger getUpperValue() {
        BigInteger cached;
        if (this.hasNoValueCache()) {
            ValueCache cache = this.valueCache;
            cache.upperValue = cached = new BigInteger(1, this.getUpperBytesInternal());
            if (!this.isMultiple()) {
                cache.value = cached;
            }
        } else {
            ValueCache cache = this.valueCache;
            cached = cache.upperValue;
            if (cached == null) {
                if (!this.isMultiple()) {
                    cached = cache.value;
                    if (cached != null) {
                        cache.upperValue = cached;
                    } else {
                        cache.upperValue = cached = new BigInteger(1, this.getUpperBytesInternal());
                        cache.value = cached;
                    }
                } else {
                    cache.upperValue = cached = new BigInteger(1, this.getUpperBytesInternal());
                }
            }
        }
        return cached;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean hasNoValueCache() {
        if (this.valueCache == null) {
            AddressDivisionGroupingBase addressDivisionGroupingBase = this;
            synchronized (addressDivisionGroupingBase) {
                if (this.valueCache == null) {
                    this.valueCache = new ValueCache();
                    return true;
                }
            }
        }
        return false;
    }

    protected void setInetAddress(InetAddress addr) {
        if (this.valueCache == null) {
            this.valueCache = new ValueCache();
        }
        this.valueCache.inetAddress = addr;
    }

    @Override
    public boolean isPrefixed() {
        return this.getPrefixLength() != null;
    }

    @Override
    public Integer getPrefixLength() {
        return this.cachedPrefixLength;
    }

    protected static Integer calculatePrefix(IPAddressDivisionSeries series) {
        int count = series.getDivisionCount();
        if (count > 0) {
            if (series.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets() && !series.getDivision(count - 1).isPrefixed()) {
                return null;
            }
            int result = 0;
            for (int i = 0; i < count; ++i) {
                IPAddressGenericDivision div = series.getDivision(i);
                Integer prefix = div.getDivisionPrefixLength();
                if (prefix != null) {
                    return ParsedAddressGrouping.cache(result += prefix.intValue());
                }
                result += div.getBitCount();
            }
        }
        return null;
    }

    @Override
    public int getMinPrefixLengthForBlock() {
        int count = this.getDivisionCount();
        int totalPrefix = this.getBitCount();
        for (int i = count - 1; i >= 0; --i) {
            AddressDivisionBase div = this.getDivision(i);
            int segBitCount = div.getBitCount();
            int segPrefix = div.getMinPrefixLengthForBlock();
            if (segPrefix == segBitCount) break;
            totalPrefix -= segBitCount;
            if (segPrefix == 0) continue;
            totalPrefix += segPrefix;
            break;
        }
        return totalPrefix;
    }

    @Override
    public Integer getPrefixLengthForSingleBlock() {
        int totalPrefix;
        if (!this.isMultiple()) {
            totalPrefix = this.getBitCount();
        } else {
            int count = this.getDivisionCount();
            totalPrefix = 0;
            for (int i = 0; i < count; ++i) {
                AddressDivisionBase div = this.getDivision(i);
                Integer divPrefix = div.getPrefixLengthForSingleBlock();
                if (divPrefix == null) {
                    return null;
                }
                totalPrefix += divPrefix.intValue();
                if (divPrefix >= div.getBitCount()) continue;
                ++i;
                while (i < count) {
                    AddressDivisionBase laterDiv = this.getDivision(i);
                    if (!laterDiv.isFullRange()) {
                        return null;
                    }
                    ++i;
                }
            }
        }
        return AddressDivisionGroupingBase.cacheBits(totalPrefix);
    }

    protected static Integer getPrefixLengthForSingleBlock(IPAddressDivisionSeries series) {
        int totalPrefix;
        if (!series.isMultiple()) {
            totalPrefix = series.getBitCount();
        } else {
            int count = series.getDivisionCount();
            totalPrefix = 0;
            boolean isAutoSubnets = series.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
            for (int i = 0; i < count; ++i) {
                IPAddressGenericDivision div = series.getDivision(i);
                Integer divPrefix = div.getPrefixLengthForSingleBlock();
                if (divPrefix == null) {
                    return null;
                }
                totalPrefix += divPrefix.intValue();
                if (isAutoSubnets && div.isPrefixed()) {
                    return AddressDivisionGroupingBase.cacheBits(totalPrefix);
                }
                if (divPrefix >= div.getBitCount()) continue;
                ++i;
                while (i < count) {
                    IPAddressGenericDivision laterDiv = series.getDivision(i);
                    if (!laterDiv.isFullRange()) {
                        return null;
                    }
                    if (isAutoSubnets && laterDiv.isPrefixed()) {
                        return AddressDivisionGroupingBase.cacheBits(totalPrefix);
                    }
                    ++i;
                }
            }
        }
        return AddressDivisionGroupingBase.cacheBits(totalPrefix);
    }

    protected static Integer cacheBits(int i) {
        return ParsedAddressGrouping.cache(i);
    }

    @Override
    public BigInteger getCount() {
        BigInteger cached = this.cachedCount;
        if (cached == null) {
            this.cachedCount = cached = this.getCountImpl();
        }
        return cached;
    }

    protected BigInteger getCountImpl() {
        return AddressDivisionSeries.super.getCount();
    }

    @Override
    public BigInteger getPrefixCount() {
        BigInteger cached = this.cachedPrefixCount;
        if (cached == null) {
            Integer prefixLength = this.getPrefixLength();
            this.cachedPrefixCount = prefixLength == null || prefixLength >= this.getBitCount() ? (cached = this.getCount()) : (cached = this.getPrefixCountImpl());
        }
        return cached;
    }

    protected BigInteger getPrefixCountImpl() {
        return AddressDivisionSeries.super.getPrefixCount();
    }

    @Override
    public boolean isMultiple() {
        Boolean result = this.isMultiple;
        if (result == null) {
            for (int i = this.getDivisionCount() - 1; i >= 0; --i) {
                AddressDivisionBase seg = this.getDivision(i);
                if (!seg.isMultiple()) continue;
                this.isMultiple = Boolean.TRUE;
                return this.isMultiple;
            }
            this.isMultiple = Boolean.FALSE;
            return this.isMultiple;
        }
        return result;
    }

    protected static int adjustHashCode(int currentHash, long lowerValue, long upperValue) {
        return AddressDivisionBase.adjustHashCode(currentHash, lowerValue, upperValue);
    }

    public int hashCode() {
        int res = this.hashCode;
        if (res == 0) {
            res = 1;
            int count = this.getDivisionCount();
            for (int i = 0; i < count; ++i) {
                AddressDivisionBase combo = this.getDivision(i);
                BigInteger lower = combo.getValue();
                BigInteger upper = combo.getUpperValue();
                int longBits = 64;
                do {
                    long low = lower.longValue();
                    long up = upper.longValue();
                    lower = lower.shiftRight(longBits);
                    upper = upper.shiftRight(longBits);
                    res = AddressDivisionGroupingBase.adjustHashCode(res, low, up);
                } while (!upper.equals(BigInteger.ZERO));
            }
            this.hashCode = res;
        }
        return res;
    }

    protected boolean isSameGrouping(AddressDivisionGroupingBase other) {
        int count = this.getDivisionCount();
        if (count != other.getDivisionCount()) {
            return false;
        }
        for (int i = 0; i < count; ++i) {
            AddressDivisionBase two;
            AddressDivisionBase one = this.getDivision(i);
            if (one.equals(two = other.getDivision(i))) continue;
            return false;
        }
        return true;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AddressDivisionGroupingBase) {
            AddressDivisionGroupingBase other = (AddressDivisionGroupingBase)o;
            return other.isSameGrouping(this);
        }
        return false;
    }

    protected AddressDivisionBase[] getDivisionsInternal() {
        return this.divisions;
    }

    public String toString() {
        return Arrays.asList(this.getDivisionsInternal()).toString();
    }

    @Override
    public String[] getDivisionStrings() {
        String[] result = new String[this.getDivisionCount()];
        Arrays.setAll(result, i -> this.getDivision(i).getWildcardString());
        return result;
    }

    @Override
    public boolean isZero() {
        int divCount = this.getDivisionCount();
        for (int i = 0; i < divCount; ++i) {
            if (this.getDivision(i).isZero()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean includesZero() {
        int divCount = this.getDivisionCount();
        for (int i = 0; i < divCount; ++i) {
            if (this.getDivision(i).includesZero()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isMax() {
        int divCount = this.getDivisionCount();
        for (int i = 0; i < divCount; ++i) {
            if (this.getDivision(i).isMax()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean includesMax() {
        int divCount = this.getDivisionCount();
        for (int i = 0; i < divCount; ++i) {
            if (this.getDivision(i).includesMax()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isFullRange() {
        int divCount = this.getDivisionCount();
        for (int i = 0; i < divCount; ++i) {
            AddressDivisionBase div = this.getDivision(i);
            if (div.isFullRange()) continue;
            return false;
        }
        return true;
    }

    protected static void checkSubnet(AddressDivisionSeries series, int prefixLength) throws PrefixLenException {
        if (prefixLength < 0 || prefixLength > series.getBitCount()) {
            throw new PrefixLenException((AddressItem)series, prefixLength);
        }
    }

    @Override
    public boolean isSinglePrefixBlock() {
        return this.isPrefixed() && this.containsSinglePrefixBlock(this.getPrefixLength());
    }

    @Override
    public boolean isPrefixBlock() {
        return this.isPrefixed() && this.containsPrefixBlock(this.getPrefixLength());
    }

    protected static boolean containsPrefixBlock(IPAddressDivisionSeries series, int prefixLength) {
        AddressDivisionGroupingBase.checkSubnet(series, prefixLength);
        boolean isAllSubnets = series.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (isAllSubnets && series.isPrefixed() && series.getNetworkPrefixLength() <= prefixLength) {
            return true;
        }
        int prevBitCount = 0;
        int divCount = series.getDivisionCount();
        for (int i = 0; i < divCount; ++i) {
            IPAddressGenericDivision div = series.getDivision(i);
            int bitCount = div.getBitCount();
            int totalBitCount = bitCount + prevBitCount;
            if (prefixLength < totalBitCount) {
                int divPrefixLen = Math.max(0, prefixLength - prevBitCount);
                if (!div.containsPrefixBlock(divPrefixLen)) {
                    return false;
                }
                if (isAllSubnets && div.isPrefixed()) {
                    return true;
                }
                ++i;
                while (i < divCount) {
                    div = series.getDivision(i);
                    if (!div.isFullRange()) {
                        return false;
                    }
                    if (isAllSubnets && div.isPrefixed()) {
                        return true;
                    }
                    ++i;
                }
                return true;
            }
            prevBitCount = totalBitCount;
        }
        return true;
    }

    protected static boolean containsSinglePrefixBlock(IPAddressDivisionSeries series, int prefixLength) {
        AddressDivisionGroupingBase.checkSubnet(series, prefixLength);
        boolean isAllSubnets = series.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets();
        if (isAllSubnets && series.isPrefixed() && series.getNetworkPrefixLength() < prefixLength) {
            return false;
        }
        int prevBitCount = 0;
        int divCount = series.getDivisionCount();
        for (int i = 0; i < divCount; ++i) {
            IPAddressGenericDivision div = series.getDivision(i);
            int bitCount = div.getBitCount();
            int totalBitCount = bitCount + prevBitCount;
            if (prefixLength >= totalBitCount) {
                if (div.isMultiple()) {
                    return false;
                }
            } else {
                int divPrefixLen = Math.max(0, prefixLength - prevBitCount);
                if (!div.containsSinglePrefixBlock(divPrefixLen)) {
                    return false;
                }
                if (isAllSubnets && div.isPrefixed()) {
                    return true;
                }
                ++i;
                while (i < divCount) {
                    div = series.getDivision(i);
                    if (!div.isFullRange()) {
                        return false;
                    }
                    if (isAllSubnets && div.isPrefixed()) {
                        return true;
                    }
                    ++i;
                }
                return true;
            }
            prevBitCount = totalBitCount;
        }
        return true;
    }

    protected static <S extends AddressComponentRange, T> AddressComponentRangeSpliterator<S, T> createItemSpliterator(S forIteration, Predicate<SplitterSink<S, T>> splitter, IteratorProvider<S, T> iteratorProvider, Function<S, BigInteger> sizer, Predicate<S> downSizer, ToLongFunction<S> longSizer) {
        return new AddressItemRangeSpliterator<S, T>(forIteration, splitter, iteratorProvider, sizer, downSizer, longSizer);
    }

    protected static <T extends AddressComponent> AddressComponentSpliterator<T> createSeriesSpliterator(T forIteration, Predicate<SplitterSink<T, T>> splitter, IteratorProvider<T, T> iteratorProvider, Function<T, BigInteger> sizer, Predicate<T> downSizer, ToLongFunction<T> longSizer) {
        return new AddressSeriesSpliterator<T>(forIteration, splitter, iteratorProvider, sizer, downSizer, longSizer);
    }

    protected static AddressDivisionWriter getCachedParams(StringOptionsBase opts) {
        return opts.cachedParams;
    }

    protected static void setCachedParams(StringOptionsBase opts, AddressDivisionWriter cachedParams) {
        opts.cachedParams = cachedParams;
    }

    protected static AddressStringParams<IPAddressStringDivisionSeries> toIPParams(IPAddressSection.IPStringOptions opts) {
        return AddressDivisionBase.toParams(opts);
    }

    static {
        String propertyFileName = "IPAddressResources";
        String name = HostIdentifierException.class.getPackage().getName() + '.' + propertyFileName;
        try {
            bundle = ResourceBundle.getBundle(name);
        }
        catch (MissingResourceException e) {
            System.err.println("bundle " + name + " is missing");
        }
    }

    protected static class StringOptionsBase {
        AddressDivisionWriter cachedParams;

        protected StringOptionsBase() {
        }
    }

    protected static class IPAddressStringParams<T extends IPAddressStringDivisionSeries>
    extends AddressStringParams<T>
    implements IPAddressStringWriter<T> {
        public static final IPAddressSection.WildcardOptions.WildcardOption DEFAULT_WILDCARD_OPTION = IPAddressSection.WildcardOptions.WildcardOption.NETWORK_ONLY;
        protected static final int EXTRA_SPACE = 16;
        private IPAddressSection.WildcardOptions.WildcardOption wildcardOption = DEFAULT_WILDCARD_OPTION;
        private int[] expandSegment;
        private String addressSuffix = "";

        public IPAddressStringParams(int radix, Character separator, boolean uppercase) {
            this(radix, separator, uppercase, '\u0000');
        }

        public IPAddressStringParams(int radix, Character separator, boolean uppercase, char zoneSeparator) {
            super(radix, separator, uppercase, zoneSeparator);
        }

        public String getAddressSuffix() {
            return this.addressSuffix;
        }

        public void setAddressSuffix(String suffix) {
            this.addressSuffix = suffix;
        }

        @Override
        public boolean preferWildcards() {
            return this.wildcardOption == IPAddressSection.WildcardOptions.WildcardOption.ALL;
        }

        public void setWildcardOption(IPAddressSection.WildcardOptions.WildcardOption option) {
            this.wildcardOption = option;
        }

        public int getExpandedSegmentLength(int segmentIndex) {
            if (this.expandSegment == null || this.expandSegment.length <= segmentIndex) {
                return 0;
            }
            return this.expandSegment[segmentIndex];
        }

        public void expandSegment(int index, int expansionLength, int segmentCount) {
            if (this.expandSegment == null) {
                this.expandSegment = new int[segmentCount];
            }
            this.expandSegment[index] = expansionLength;
        }

        @Override
        public char getTrailingSegmentSeparator() {
            return this.separator.charValue();
        }

        public StringBuilder appendSuffix(StringBuilder builder) {
            String suffix = this.getAddressSuffix();
            if (suffix != null) {
                builder.append(suffix);
            }
            return builder;
        }

        public int getAddressSuffixLength() {
            String suffix = this.getAddressSuffix();
            if (suffix != null) {
                return suffix.length();
            }
            return 0;
        }

        @Override
        public int getLeadingZeros(int segmentIndex) {
            if (this.expandSegments) {
                return -1;
            }
            if (this.expandSegment != null && this.expandSegment.length > segmentIndex) {
                return this.expandSegment[segmentIndex];
            }
            return 0;
        }

        @Override
        public IPAddressStringParams<T> clone() {
            IPAddressStringParams parms = (IPAddressStringParams)super.clone();
            if (this.expandSegment != null) {
                parms.expandSegment = (int[])this.expandSegment.clone();
            }
            return parms;
        }

        @Override
        public int getTrailingSeparatorCount(T addr) {
            int count = addr.getDivisionCount();
            if (count > 0) {
                return count - 1;
            }
            return 0;
        }

        public static int getPrefixIndicatorStringLength(IPAddressStringDivisionSeries addr) {
            if (addr.isPrefixed()) {
                int value = addr.getPrefixLength();
                if (value < 10) {
                    return 2;
                }
                if (value < 100) {
                    return 3;
                }
                return 4;
            }
            return 0;
        }

        @Override
        public int getStringLength(T addr) {
            int count = this.getSegmentsStringLength(addr);
            if (!this.isReverse() && !this.preferWildcards()) {
                count += IPAddressStringParams.getPrefixIndicatorStringLength(addr);
            }
            return count + this.getAddressSuffixLength() + this.getAddressLabelLength();
        }

        public void appendPrefixIndicator(StringBuilder builder, IPAddressStringDivisionSeries addr) {
            if (addr.isPrefixed()) {
                builder.append('/').append(addr.getPrefixLength());
            }
        }

        @Override
        public StringBuilder append(StringBuilder builder, T addr, CharSequence zone) {
            this.appendSuffix(this.appendZone(this.appendSegments(this.appendLabel(builder), addr), zone));
            if (!this.isReverse() && !this.preferWildcards()) {
                this.appendPrefixIndicator(builder, (IPAddressStringDivisionSeries)addr);
            }
            return builder;
        }

        @Override
        protected int appendSegment(int segmentIndex, StringBuilder builder, T part) {
            Integer prefix;
            IPAddressStringDivision seg = part.getDivision(segmentIndex);
            AddressNetwork.PrefixConfiguration config = part.getNetwork().getPrefixConfiguration();
            if (config.prefixedSubnetsAreExplicit() || this.preferWildcards() || (prefix = seg.getDivisionPrefixLength()) == null || prefix >= seg.getBitCount() || config.zeroHostsAreSubnets() && !part.isPrefixBlock() || this.isSplitDigits()) {
                return seg.getStandardString(segmentIndex, this, builder);
            }
            if (seg.isSinglePrefixBlock()) {
                return seg.getLowerStandardString(segmentIndex, this, builder);
            }
            return seg.getPrefixAdjustedRangeString(segmentIndex, this, builder);
        }
    }

    protected static class AddressStringParams<T extends AddressStringDivisionSeries>
    implements AddressDivisionWriter,
    AddressSegmentParams,
    Cloneable {
        public static final AddressDivisionGrouping.StringOptions.Wildcards DEFAULT_WILDCARDS = new AddressDivisionGrouping.StringOptions.Wildcards();
        private AddressDivisionGrouping.StringOptions.Wildcards wildcards = DEFAULT_WILDCARDS;
        protected boolean expandSegments;
        private String segmentStrPrefix = "";
        private int radix;
        protected Character separator;
        private boolean uppercase;
        private boolean reverse;
        private boolean splitDigits;
        private String addressLabel = "";
        private char zoneSeparator;

        public AddressStringParams(int radix, Character separator, boolean uppercase) {
            this(radix, separator, uppercase, '\u0000');
        }

        public AddressStringParams(int radix, Character separator, boolean uppercase, char zoneSeparator) {
            if (radix < 2 || radix > 85) {
                throw new IllegalArgumentException();
            }
            this.radix = radix;
            this.separator = separator;
            this.uppercase = uppercase;
            this.zoneSeparator = zoneSeparator;
        }

        public void setZoneSeparator(char zoneSeparator) {
            this.zoneSeparator = zoneSeparator;
        }

        public String getAddressLabel() {
            return this.addressLabel;
        }

        public void setAddressLabel(String str) {
            this.addressLabel = str;
        }

        public Character getSeparator() {
            return this.separator;
        }

        public void setSeparator(Character separator) {
            this.separator = separator;
        }

        @Override
        public AddressDivisionGrouping.StringOptions.Wildcards getWildcards() {
            return this.wildcards;
        }

        public void setWildcards(AddressDivisionGrouping.StringOptions.Wildcards wc) {
            this.wildcards = wc;
        }

        @Override
        public boolean preferWildcards() {
            return true;
        }

        @Override
        public int getLeadingZeros(int segmentIndex) {
            if (this.expandSegments) {
                return -1;
            }
            return 0;
        }

        @Override
        public String getSegmentStrPrefix() {
            return this.segmentStrPrefix;
        }

        public void setSegmentStrPrefix(String segmentStrPrefix) {
            if (segmentStrPrefix == null) {
                throw new NullPointerException();
            }
            this.segmentStrPrefix = segmentStrPrefix;
        }

        @Override
        public int getRadix() {
            return this.radix;
        }

        public void setRadix(int radix) {
            this.radix = radix;
        }

        public void setUppercase(boolean uppercase) {
            this.uppercase = uppercase;
        }

        @Override
        public boolean isUppercase() {
            return this.uppercase;
        }

        public void setSplitDigits(boolean split) {
            this.splitDigits = split;
        }

        @Override
        public boolean isSplitDigits() {
            return this.splitDigits;
        }

        @Override
        public Character getSplitDigitSeparator() {
            return this.separator;
        }

        @Override
        public boolean isReverseSplitDigits() {
            return this.reverse;
        }

        public void setReverse(boolean rev) {
            this.reverse = rev;
        }

        public boolean isReverse() {
            return this.reverse;
        }

        public void expandSegments(boolean expand) {
            this.expandSegments = expand;
        }

        public StringBuilder appendLabel(StringBuilder builder) {
            String str = this.getAddressLabel();
            if (str != null && str.length() > 0) {
                builder.append(str);
            }
            return builder;
        }

        public int getAddressLabelLength() {
            String str = this.getAddressLabel();
            if (str != null) {
                return str.length();
            }
            return 0;
        }

        public int getSegmentsStringLength(T part) {
            int count = 0;
            if (part.getDivisionCount() != 0) {
                int divCount = part.getDivisionCount();
                for (int i = 0; i < divCount; ++i) {
                    count += this.appendSegment(i, null, part);
                }
                Character separator = this.getSeparator();
                if (separator != null) {
                    count += divCount - 1;
                }
            }
            return count;
        }

        public StringBuilder appendSegments(StringBuilder builder, T part) {
            int count = part.getDivisionCount();
            if (count != 0) {
                boolean reverse = this.isReverse();
                int i = 0;
                Character separator = this.getSeparator();
                while (true) {
                    int segIndex = reverse ? count - i - 1 : i;
                    this.appendSegment(segIndex, builder, part);
                    if (++i == count) break;
                    if (separator == null) continue;
                    builder.append(separator);
                }
            }
            return builder;
        }

        public int appendSingleDivision(AddressStringDivision seg, StringBuilder builder) {
            if (builder == null) {
                return this.getAddressLabelLength() + seg.getStandardString(0, this, null);
            }
            this.appendLabel(builder);
            seg.getStandardString(0, this, builder);
            return 0;
        }

        protected int appendSegment(int segmentIndex, StringBuilder builder, T part) {
            AddressStringDivision seg = part.getDivision(segmentIndex);
            return seg.getStandardString(segmentIndex, this, builder);
        }

        public int getZoneLength(CharSequence zone) {
            if (zone != null && zone.length() > 0) {
                return zone.length() + 1;
            }
            return 0;
        }

        public int getStringLength(T addr, CharSequence zone) {
            int result = this.getStringLength(addr);
            if (zone != null) {
                result += this.getZoneLength(zone);
            }
            return result;
        }

        public int getStringLength(T addr) {
            return this.getAddressLabelLength() + this.getSegmentsStringLength(addr);
        }

        public StringBuilder appendZone(StringBuilder builder, CharSequence zone) {
            if (zone != null && zone.length() > 0) {
                builder.append(this.zoneSeparator).append(zone);
            }
            return builder;
        }

        public StringBuilder append(StringBuilder builder, T addr, CharSequence zone) {
            return this.appendZone(this.appendSegments(this.appendLabel(builder), addr), zone);
        }

        public StringBuilder append(StringBuilder builder, T addr) {
            return this.append(builder, addr, null);
        }

        @Override
        public int getDivisionStringLength(AddressStringDivision seg) {
            return this.appendSingleDivision(seg, null);
        }

        @Override
        public StringBuilder appendDivision(StringBuilder builder, AddressStringDivision seg) {
            this.appendSingleDivision(seg, builder);
            return builder;
        }

        public String toString(T addr, CharSequence zone) {
            int length = this.getStringLength(addr, zone);
            StringBuilder builder = new StringBuilder(length);
            this.append(builder, addr, zone);
            AddressStringParams.checkLengths(length, builder);
            return builder.toString();
        }

        public String toString(T addr) {
            return this.toString(addr, null);
        }

        public static void checkLengths(int length, StringBuilder builder) {
        }

        public static AddressStringParams<AddressStringDivisionSeries> toParams(AddressDivisionGrouping.StringOptions opts) {
            AddressStringParams result = (AddressStringParams)AddressDivisionGroupingBase.getCachedParams(opts);
            if (result == null) {
                result = new AddressStringParams(opts.base, opts.separator, opts.uppercase);
                result.expandSegments(opts.expandSegments);
                result.setWildcards(opts.wildcards);
                result.setSegmentStrPrefix(opts.segmentStrPrefix);
                result.setAddressLabel(opts.addrLabel);
                result.setReverse(opts.reverse);
                result.setSplitDigits(opts.splitDigits);
                AddressDivisionGroupingBase.setCachedParams(opts, result);
            }
            return result;
        }

        public AddressStringParams<T> clone() {
            try {
                AddressStringParams parms = (AddressStringParams)super.clone();
                return parms;
            }
            catch (CloneNotSupportedException e) {
                return null;
            }
        }
    }

    protected static interface SplitterSink<S, T> {
        public void setSplitValues(S var1, S var2);

        public S getAddressItem();
    }

    protected static class AddressItemRangeSpliterator<S extends AddressComponentRange, T>
    extends AddressItemSpliteratorBase<S, T>
    implements SplitterSink<S, T> {
        private S forIteration;
        private Iterator<T> iterator;
        private S split1;
        private S split2;
        protected final IteratorProvider<S, T> iteratorProvider;
        private boolean isLowest;
        private final boolean isHighest;
        private Function<S, BigInteger> sizer;
        private Predicate<S> downSizer;
        private final ToLongFunction<S> longSizer;
        private long longSize;
        private BigInteger bigSize;
        final Predicate<SplitterSink<S, T>> splitter;

        protected AddressItemRangeSpliterator(S forIteration, Predicate<SplitterSink<S, T>> splitter, IteratorProvider<S, T> iteratorProvider, Function<S, BigInteger> sizer, Predicate<S> downSizer, ToLongFunction<S> longSizer) {
            this(forIteration, splitter, iteratorProvider, true, true, sizer, downSizer, longSizer);
            this.updateSizers();
        }

        protected AddressItemRangeSpliterator(S forIteration, Predicate<SplitterSink<S, T>> splitter, IteratorProvider<S, T> iteratorProvider, boolean isLowest, boolean isHighest, Function<S, BigInteger> sizer, Predicate<S> downSizer, ToLongFunction<S> longSizer) {
            this.forIteration = forIteration;
            this.iteratorProvider = iteratorProvider;
            this.isLowest = isLowest;
            this.isHighest = isHighest;
            this.longSizer = longSizer;
            this.sizer = sizer;
            this.downSizer = downSizer;
            this.splitter = splitter;
            this.updateSizers();
        }

        void updateSizers() {
            if (this.sizer != null) {
                boolean bl = this.isBig = this.downSizer == null || !this.downSizer.test(this.forIteration);
                if (!this.isBig) {
                    this.sizer = null;
                    this.downSizer = null;
                }
            } else {
                this.isBig = false;
            }
            this.longSize = -1L;
            this.bigSize = null;
        }

        private long originalLongSize() {
            long size = this.longSize;
            if (size < 0L) {
                this.longSize = size = this.longSizer.applyAsLong(this.forIteration);
            }
            return size;
        }

        private long currentLongSize() {
            return this.originalLongSize() - this.iteratedCountL;
        }

        @Override
        public long estimateSize() {
            if (this.isBig) {
                if (this.currentBigSize().compareTo(LONG_MAX) <= 0) {
                    return this.currentBigSize().longValue();
                }
                return Long.MAX_VALUE;
            }
            return this.currentLongSize();
        }

        private BigInteger originalBigSize() {
            BigInteger size = this.bigSize;
            if (this.bigSize == null) {
                this.bigSize = size = this.sizer.apply(this.forIteration);
            }
            return size;
        }

        private BigInteger currentBigSize() {
            return this.originalBigSize().subtract(this.iteratedCountB);
        }

        @Override
        public BigInteger getSize() {
            if (this.isBig) {
                return this.currentBigSize().subtract(BigInteger.valueOf(this.iteratedCountI));
            }
            return BigInteger.valueOf(this.currentLongSize());
        }

        @Override
        public S getAddressItem() {
            return this.forIteration;
        }

        @Override
        public int characteristics() {
            if (this.isBig) {
                return 4373;
            }
            return super.characteristics();
        }

        private Iterator<T> provideIterator() {
            if (this.iterator == null) {
                this.iterator = this.iteratorProvider.apply(this.isLowest, this.isHighest, this.forIteration);
            }
            return this.iterator;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (this.inForEach) {
                return false;
            }
            if (this.isBig ? this.iteratedCountB.signum() <= 0 || this.iteratedCountB.compareTo(this.originalBigSize()) < 0 : this.iteratedCountL < this.originalLongSize()) {
                return this.tryAdvance(this.provideIterator(), action);
            }
            return false;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            if (this.inForEach) {
                return;
            }
            this.inForEach = true;
            try {
                if (this.isBig) {
                    this.forEachRemaining(this.provideIterator(), action, this.originalBigSize());
                } else {
                    this.forEachRemaining(this.provideIterator(), action, this.originalLongSize());
                }
                return;
            }
            finally {
                this.inForEach = false;
            }
        }

        protected boolean canSplit() {
            if (this.inForEach) {
                return false;
            }
            return this.isBig ? this.iteratedCountB.compareTo(this.originalBigSize().shiftRight(1)) < 0 : this.iteratedCountL < this.originalLongSize() >> 1;
        }

        protected boolean split() {
            return this.splitter.test(this);
        }

        protected AddressItemRangeSpliterator<S, T> createSpliterator(S split, boolean isLowest, Function<S, BigInteger> sizer, Predicate<S> downSizer, ToLongFunction<S> longSizer) {
            return new AddressItemRangeSpliterator<S, T>(split, this.splitter, this.iteratorProvider, isLowest, false, sizer, downSizer, longSizer);
        }

        @Override
        public AddressItemRangeSpliterator<S, T> trySplit() {
            if (!this.canSplit() || !this.split()) {
                return null;
            }
            boolean hasIterated = this.isBig ? this.iteratedCountB.signum() > 0 : this.iteratedCountL > 0L;
            BigInteger splitSizeBig = null;
            long splitSize = -1L;
            if (hasIterated && (this.isBig ? this.iteratedCountB.compareTo(splitSizeBig = this.sizer.apply(this.split1)) >= 0 : this.iteratedCountL >= (splitSize = this.longSizer.applyAsLong(this.split1)))) {
                return null;
            }
            AddressItemRangeSpliterator<S, T> splitOff = this.createSpliterator(this.split1, this.isLowest, this.sizer, this.downSizer, this.longSizer);
            if (hasIterated) {
                if (this.isBig) {
                    if (splitOff.isBig) {
                        splitOff.iteratedCountB = this.iteratedCountB;
                    } else {
                        splitOff.iteratedCountL = this.iteratedCountB.longValue();
                    }
                    this.iteratedCountB = BigInteger.ZERO;
                } else {
                    splitOff.iteratedCountL = this.iteratedCountL;
                    this.iteratedCountL = 0L;
                }
                splitOff.iterator = this.iterator;
                this.iterator = null;
                splitOff.bigSize = splitSizeBig;
                splitOff.longSize = splitSize;
            }
            this.forIteration = this.split2;
            this.isLowest = false;
            this.updateSizers();
            return splitOff;
        }

        @Override
        public void setSplitValues(S left, S right) {
            this.split1 = left;
            this.split2 = right;
        }
    }

    @FunctionalInterface
    protected static interface IteratorProvider<S, T> {
        public Iterator<T> apply(boolean var1, boolean var2, S var3);
    }

    protected static class ValueCache {
        public byte[] lowerBytes;
        public byte[] upperBytes;
        public BigInteger value;
        public BigInteger upperValue;
        public InetAddress inetAddress;

        protected ValueCache() {
        }
    }
}

