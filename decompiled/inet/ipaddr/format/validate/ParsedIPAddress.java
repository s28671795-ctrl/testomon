/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.validate;

import inet.ipaddr.Address;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.IPAddressStringParameters;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.IPAddressDivisionSeries;
import inet.ipaddr.format.large.IPAddressLargeDivision;
import inet.ipaddr.format.large.IPAddressLargeDivisionGrouping;
import inet.ipaddr.format.standard.IPAddressBitsDivision;
import inet.ipaddr.format.standard.IPAddressDivision;
import inet.ipaddr.format.standard.IPAddressDivisionGrouping;
import inet.ipaddr.format.validate.AddressParseData;
import inet.ipaddr.format.validate.IPAddressParseData;
import inet.ipaddr.format.validate.IPAddressProvider;
import inet.ipaddr.format.validate.ParsedAddressCreator;
import inet.ipaddr.format.validate.ParsedAddressGrouping;
import inet.ipaddr.format.validate.ParsedHostIdentifierStringQualifier;
import inet.ipaddr.ipv4.IPv4Address;
import inet.ipaddr.ipv4.IPv4AddressNetwork;
import inet.ipaddr.ipv4.IPv4AddressSection;
import inet.ipaddr.ipv4.IPv4AddressSegment;
import inet.ipaddr.ipv4.IPv4AddressSeqRange;
import inet.ipaddr.ipv6.IPv6Address;
import inet.ipaddr.ipv6.IPv6AddressNetwork;
import inet.ipaddr.ipv6.IPv6AddressSection;
import inet.ipaddr.ipv6.IPv6AddressSegment;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

public class ParsedIPAddress
extends IPAddressParseData
implements IPAddressProvider {
    private static final long serialVersionUID = 4L;
    private static final ExtendedMasker DEFAULT_MASKER = new ExtendedMasker(true);
    private static final ExtendedMasker DEFAULT_NON_SEQUENTIAL_MASKER = new ExtendedMasker(false);
    private static final ExtendedFullRangeMasker[] EXTENDED_FULL_RANGE_MASKERS = new ExtendedFullRangeMasker[129];
    private static final ExtendedFullRangeMasker[] EXTENDED_SEQUENTIAL_FULL_RANGE_MASKERS = new ExtendedFullRangeMasker[129];
    private static final WrappedMasker[] WRAPPED_FULL_RANGE_MASKERS = new WrappedMasker[65];
    private static final WrappedMasker[] WRAPPED_SEQUENTIAL_FULL_RANGE_MASKERS = new WrappedMasker[65];
    private static final FullRangeMasker[] FULL_RANGE_MASKERS = new FullRangeMasker[65];
    private static final FullRangeMasker[] SEQUENTIAL_FULL_RANGE_MASKERS = new FullRangeMasker[65];
    private static final BitwiseOrer DEFAULT_OR_MASKER = new BitwiseOrer(true);
    private static final BitwiseOrer DEFAULT_NON_SEQUENTIAL_OR_MASKER = new BitwiseOrer(false);
    private static final FullRangeBitwiseOrer[] FULL_RANGE_OR_MASKERS = new FullRangeBitwiseOrer[65];
    private static final FullRangeBitwiseOrer[] SEQUENTIAL_FULL_RANGE_OR_MASKERS = new FullRangeBitwiseOrer[65];
    private static final BigInteger ONE_EXTENDED = new BigInteger(1, new byte[]{1, 0, 0, 0, 0, 0, 0, 0, 0});
    private static final BigInteger HIGH_BIT = new BigInteger(1, new byte[]{-128, 0, 0, 0, 0, 0, 0, 0});
    private static final BigInteger[] ONE_SHIFTED = new BigInteger[64];
    private static final BigInteger[] ONE_SHIFTED_EXTENDED = new BigInteger[64];
    private static final BigInteger[] NETWORK_MASK_EXTENDED = new BigInteger[64];
    private static final BigInteger[] HOST_MASK_EXTENDED = new BigInteger[64];
    private final IPAddressStringParameters options;
    private final HostIdentifierString originator;
    private TranslatedResult<?, ?> values;
    private Masker[] maskers;
    private Masker[] mixedMaskers;

    ParsedIPAddress(HostIdentifierString from, CharSequence addressString, IPAddressStringParameters options) {
        super(addressString);
        this.options = options;
        this.originator = from;
    }

    private IPv6AddressNetwork.IPv6AddressCreator getIPv6AddressCreator() {
        return this.getParameters().getIPv6Parameters().getNetwork().getAddressCreator();
    }

    private IPv4AddressNetwork.IPv4AddressCreator getIPv4AddressCreator() {
        return this.getParameters().getIPv4Parameters().getNetwork().getAddressCreator();
    }

    @Override
    public boolean isProvidingIPAddress() {
        return true;
    }

    @Override
    public IPAddressProvider.IPType getType() {
        return IPAddressProvider.IPType.from(this.getProviderIPVersion());
    }

    @Override
    public IPAddressStringParameters getParameters() {
        return this.options;
    }

    void createSections(boolean doAddress, boolean doRangeBoundaries, boolean withUpper) {
        IPAddress.IPVersion version = this.getProviderIPVersion();
        if (version.isIPv4()) {
            this.createIPv4Sections(doAddress, doRangeBoundaries, withUpper);
        } else if (version.isIPv6()) {
            this.createIPv6Sections(doAddress, doRangeBoundaries, withUpper);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public IPAddressSeqRange getProviderSeqRange() {
        TranslatedResult<?, ?> val = this.values;
        if (val == null || ((TranslatedResult)val).range == null) {
            ParsedIPAddress parsedIPAddress = this;
            synchronized (parsedIPAddress) {
                val = this.values;
                if (val == null || ((TranslatedResult)val).range == null) {
                    if (val != null && !val.withoutSections() && val.withoutAddressException()) {
                        ((TranslatedResult)val).range = ((IPAddress)val.getAddress()).toSequentialRange();
                    } else {
                        this.createSections(false, true, true);
                        val = this.values;
                        val.createRange();
                        if (this.isDoneTranslating()) {
                            this.releaseSegmentData();
                        }
                    }
                }
            }
        }
        return ((TranslatedResult)val).range;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    IPAddress getValForMask() {
        TranslatedResult<?, ?> val = this.values;
        if (val == null || !val.hasLowerSection()) {
            ParsedIPAddress parsedIPAddress = this;
            synchronized (parsedIPAddress) {
                val = this.values;
                if (val == null || !val.hasLowerSection()) {
                    this.createSections(false, true, false);
                    val = this.values;
                    this.releaseSegmentData();
                }
            }
        }
        return val.getValForMask();
    }

    @Override
    public IPAddress getProviderMask() {
        return this.getQualifier().getMaskLower();
    }

    boolean isDoneTranslating() {
        TranslatedResult<?, ?> val = this.values;
        return !val.withoutSections() && (val.withoutAddressException() || !val.withoutRange()) && !val.withoutGrouping();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    TranslatedResult<?, ?> getCachedAddresses(boolean forHostAddr) {
        TranslatedResult<?, ?> val = this.values;
        if (val == null || val.withoutSections()) {
            ParsedIPAddress parsedIPAddress = this;
            synchronized (parsedIPAddress) {
                val = this.values;
                if (val == null || val.withoutSections()) {
                    this.createSections(true, false, false);
                    val = this.values;
                    if (this.isDoneTranslating()) {
                        this.releaseSegmentData();
                    }
                }
                if (forHostAddr) {
                    val.getHostAddress();
                } else {
                    val.getAddress();
                }
            }
        }
        if (forHostAddr ? !val.hasHostAddress() : !val.hasAddress()) {
            ParsedIPAddress parsedIPAddress = this;
            synchronized (parsedIPAddress) {
                if (forHostAddr) {
                    val.getHostAddress();
                } else {
                    val.getAddress();
                }
            }
        }
        return val;
    }

    @Override
    public IPAddress getProviderHostAddress() throws IncompatibleAddressException {
        TranslatedResult<?, ?> addrs = this.getCachedAddresses(true);
        if (((TranslatedResult)addrs).mixedException != null) {
            throw ((TranslatedResult)addrs).mixedException;
        }
        if (((TranslatedResult)addrs).joinHostException != null) {
            throw ((TranslatedResult)addrs).joinHostException;
        }
        return addrs.getHostAddress();
    }

    @Override
    public IPAddress getProviderAddress() throws IncompatibleAddressException {
        TranslatedResult<?, ?> addrs = this.getCachedAddresses(false);
        if (((TranslatedResult)addrs).mixedException != null) {
            throw ((TranslatedResult)addrs).mixedException;
        }
        if (((TranslatedResult)addrs).maskException != null) {
            throw ((TranslatedResult)addrs).maskException;
        }
        if (((TranslatedResult)addrs).joinAddressException != null) {
            throw ((TranslatedResult)addrs).joinAddressException;
        }
        return addrs.getAddress();
    }

    @Override
    public IPAddress getProviderAddress(IPAddress.IPVersion version) throws IncompatibleAddressException {
        IPAddress.IPVersion thisVersion = this.getProviderIPVersion();
        if (!version.equals((Object)thisVersion)) {
            return null;
        }
        return this.getProviderAddress();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public IPAddressDivisionSeries getDivisionGrouping() throws IncompatibleAddressException {
        Serializable serializable;
        TranslatedResult<?, ?> val = this.values;
        IPAddressDivisionSeries grouping = null;
        if (val != null && (grouping = ((TranslatedResult)val).series) != null) {
            return grouping;
        }
        if (val == null || val.withoutSections() && val.withoutRange()) {
            serializable = this;
            synchronized (serializable) {
                val = this.values;
                if (val == null || val.withoutSections() && val.withoutRange()) {
                    this.createSections(true, false, false);
                }
            }
        }
        if ((grouping = ((TranslatedResult)(val = this.values)).series) == null) {
            serializable = val;
            synchronized (serializable) {
                grouping = ((TranslatedResult)val).series;
                if (grouping == null) {
                    int divRadix;
                    boolean isMergedMixed;
                    int segmentCount;
                    IPAddressNetwork network;
                    int defaultRadix;
                    ParsedHostIdentifierStringQualifier qualifier = this.getQualifier();
                    IPAddress.IPVersion version = this.getProviderIPVersion();
                    if (version.isIPv4()) {
                        defaultRadix = 10;
                        network = this.getParameters().getIPv4Parameters().getNetwork();
                    } else {
                        defaultRadix = 16;
                        network = this.getParameters().getIPv6Parameters().getNetwork();
                    }
                    AddressNetwork.PrefixConfiguration prefixConfiguration = network.getPrefixConfiguration();
                    boolean mixed = this.isProvidingMixedIPv6();
                    AddressParseData addrParseData = this.getAddressParseData();
                    int totalCount = segmentCount = addrParseData.getSegmentCount();
                    if (mixed) {
                        totalCount += this.mixedParsedAddress.getSegmentCount();
                    }
                    Integer prefLength = ParsedIPAddress.getPrefixLength(qualifier);
                    IPAddress mask = this.getProviderMask();
                    if (mask != null && mask.getBlockMaskPrefixLength(true) != null) {
                        mask = null;
                    }
                    boolean hasMask = mask != null;
                    boolean isPrefixSubnet = false;
                    if (prefLength != null) {
                        if (prefixConfiguration.allPrefixedAddressesAreSubnets()) {
                            isPrefixSubnet = true;
                        } else if (prefixConfiguration.zeroHostsAreSubnets()) {
                            if (mixed) {
                                int k = segmentCount;
                                isPrefixSubnet = ParsedAddressGrouping.isPrefixSubnet(i -> i < k ? addrParseData.getValue(i, 2) : this.mixedParsedAddress.getValue(i - k, 2), i -> i < k ? addrParseData.getValue(i, 4) : this.mixedParsedAddress.getValue(i - k, 4), i -> i < k ? addrParseData.getValue(i, 10) : this.mixedParsedAddress.getValue(i - k, 10), i -> i < k ? addrParseData.getValue(i, 12) : this.mixedParsedAddress.getValue(i - k, 12), i -> i < k ? addrParseData.getBitLength(i) : this.mixedParsedAddress.getBitLength(i - k), totalCount, prefLength, prefixConfiguration, false);
                            } else {
                                isPrefixSubnet = ParsedAddressGrouping.isPrefixSubnet(i -> addrParseData.getValue(i, 2), i -> addrParseData.getValue(i, 4), i -> addrParseData.getValue(i, 10), i -> addrParseData.getValue(i, 12), i -> addrParseData.getBitLength(i), totalCount, prefLength, prefixConfiguration, false);
                            }
                        } else {
                            isPrefixSubnet = false;
                        }
                    }
                    boolean isLarge = false;
                    for (int i2 = 0; i2 < segmentCount; ++i2) {
                        int bitLength = addrParseData.getBitLength(i2);
                        if (bitLength < 64) continue;
                        isLarge = true;
                        break;
                    }
                    if (mixed && (isMergedMixed = addrParseData.isMergedMixed(segmentCount - 1))) {
                        --totalCount;
                        if (!isLarge && addrParseData.getBitLength(--segmentCount) + this.mixedParsedAddress.getBitLength(0) >= 64) {
                            isLarge = true;
                        }
                    } else {
                        isMergedMixed = false;
                    }
                    long maskVal = 0L;
                    long extendedMaskVal = 0L;
                    int maskBits = 0;
                    if (hasMask) {
                        int bitsPerSegment = mask.getBitsPerSegment();
                        for (int i3 = 0; i3 < 4; ++i3) {
                            maskVal = maskVal << bitsPerSegment | (long)((IPAddressSegment)mask.getSegment(i3)).getSegmentValue();
                        }
                        if (mask.isIPv6()) {
                            extendedMaskVal = maskVal;
                            maskVal = 0L;
                            int remainingSegs = 4;
                            for (int i4 = 0; i4 < remainingSegs; ++i4) {
                                maskVal = maskVal << bitsPerSegment | (long)((IPAddressSegment)mask.getSegment(i4 + 4)).getSegmentValue();
                            }
                            maskBits = bitsPerSegment * 8;
                        } else {
                            maskBits = bitsPerSegment * 4;
                        }
                    }
                    int bitsSoFar = 0;
                    if (isLarge) {
                        IPAddressLargeDivision[] divs = new IPAddressLargeDivision[totalCount];
                        for (int i5 = 0; i5 < totalCount; ++i5) {
                            Integer divPrefixLength;
                            long extendedUpper;
                            long upper;
                            long extendedLower;
                            long lower;
                            boolean isExtended;
                            int bitLength;
                            boolean isNotMixed;
                            boolean bl = isNotMixed = i5 < segmentCount;
                            if (isNotMixed) {
                                bitLength = addrParseData.getBitLength(i5);
                                boolean bl2 = isExtended = bitLength > 64;
                                if (addrParseData.isWildcard(i5)) {
                                    lower = 0L;
                                    extendedLower = 0L;
                                    if (isExtended) {
                                        upper = -1L;
                                        int shift = bitLength - 64;
                                        extendedUpper = shift == 64 ? -1L : -1L << shift ^ 0xFFFFFFFFFFFFFFFFL;
                                    } else {
                                        extendedUpper = 0L;
                                        upper = bitLength == 64 ? -1L : -1L << bitLength ^ 0xFFFFFFFFFFFFFFFFL;
                                    }
                                } else {
                                    lower = addrParseData.getValue(i5, 2);
                                    upper = addrParseData.getValue(i5, 10);
                                    extendedLower = addrParseData.getValue(i5, 4);
                                    extendedUpper = addrParseData.getValue(i5, 12);
                                }
                                divRadix = defaultRadix;
                            } else if (isMergedMixed && i5 == segmentCount) {
                                isNotMixed = true;
                                bitLength = addrParseData.getBitLength(i5) + this.mixedParsedAddress.getBitLength(0);
                                lower = 0L;
                                extendedLower = 0L;
                                boolean bl3 = isExtended = bitLength > 64;
                                if (isExtended) {
                                    upper = -1L;
                                    int shift = bitLength - 64;
                                    extendedUpper = shift == 64 ? -1L : -1L << shift ^ 0xFFFFFFFFFFFFFFFFL;
                                } else {
                                    upper = bitLength == 64 ? -1L : -1L << bitLength ^ 0xFFFFFFFFFFFFFFFFL;
                                    extendedUpper = 0L;
                                }
                                divRadix = defaultRadix;
                            } else {
                                int adjusted = i5 - segmentCount;
                                bitLength = this.mixedParsedAddress.getBitLength(adjusted);
                                isExtended = false;
                                extendedUpper = 0L;
                                extendedLower = 0L;
                                if (this.mixedParsedAddress.isWildcard(adjusted)) {
                                    lower = 0L;
                                    upper = -1L << bitLength ^ 0xFFFFFFFFFFFFFFFFL;
                                } else {
                                    lower = this.mixedParsedAddress.getValue(adjusted, 2);
                                    upper = this.mixedParsedAddress.getValue(adjusted, 10);
                                }
                                divRadix = 10;
                            }
                            if (prefLength == null) {
                                divPrefixLength = null;
                                if (hasMask) {
                                    long divMask;
                                    ExtendedMasker masker = (ExtendedMasker)(isNotMixed ? this.maskers[i5] : this.mixedParsedAddress.maskers[i5]);
                                    if (!masker.isSequential()) {
                                        throw new IncompatibleAddressException(lower, upper, extendedMaskVal << 64 | maskVal, "ipaddress.error.maskMismatch");
                                    }
                                    if (isExtended) {
                                        int extraMaskBits = maskBits - bitLength;
                                        long extendedDivMask = extendedMaskVal >>> extraMaskBits;
                                        divMask = maskVal >>> extraMaskBits | extendedMaskVal << 64 - extraMaskBits;
                                        extendedLower = masker.getExtendedMaskedLower(extendedLower, extendedDivMask);
                                        extendedUpper = masker.getExtendedMaskedUpper(extendedUpper, extendedDivMask);
                                        lower = masker.getMaskedLower(lower, divMask);
                                        upper = masker.getMaskedUpper(upper, divMask);
                                    } else {
                                        if (maskBits > 64) {
                                            int extendedBits = maskBits - 64;
                                            if (extendedBits >= bitLength) {
                                                divMask = extendedMaskVal >>> extendedBits - bitLength;
                                            } else {
                                                int shortBits = bitLength - extendedBits;
                                                divMask = extendedMaskVal << shortBits | maskVal >> 64 - shortBits;
                                            }
                                        } else {
                                            divMask = maskVal >>> maskBits - bitLength;
                                        }
                                        lower = masker.getMaskedLower(lower, divMask);
                                        upper = masker.getMaskedUpper(upper, divMask);
                                    }
                                    maskBits -= bitLength;
                                }
                            } else {
                                divPrefixLength = ParsedAddressGrouping.getDivisionPrefixLength(bitLength, prefLength - bitsSoFar);
                                if (isPrefixSubnet && divPrefixLength != null && divPrefixLength < bitLength) {
                                    int unextendedDivPrefixLength;
                                    int unextendedBitLength;
                                    if (isExtended) {
                                        int extendedDivBitLength = bitLength - 64;
                                        unextendedBitLength = 64;
                                        if (divPrefixLength > extendedDivBitLength) {
                                            unextendedDivPrefixLength = divPrefixLength - extendedDivBitLength;
                                        } else {
                                            unextendedDivPrefixLength = 0;
                                            int shift = extendedDivBitLength - divPrefixLength;
                                            if (shift == 64) {
                                                extendedLower = 0L;
                                                extendedUpper = -1L;
                                            } else {
                                                long networkMask = -1L << shift;
                                                extendedLower &= networkMask;
                                                extendedUpper |= networkMask ^ 0xFFFFFFFFFFFFFFFFL;
                                            }
                                        }
                                    } else {
                                        unextendedBitLength = bitLength;
                                        unextendedDivPrefixLength = divPrefixLength;
                                    }
                                    int shift = unextendedBitLength - unextendedDivPrefixLength;
                                    if (shift == 64) {
                                        lower = 0L;
                                        upper = -1L;
                                    } else {
                                        long networkMask = -1L << shift;
                                        lower &= networkMask;
                                        upper |= networkMask ^ 0xFFFFFFFFFFFFFFFFL;
                                    }
                                }
                            }
                            int numBytes = (bitLength + 7) / 8;
                            byte[] lowerBytes = ParsedIPAddress.toBytes(lower, extendedLower, numBytes);
                            byte[] upperBytes = ParsedIPAddress.toBytes(upper, extendedUpper, numBytes);
                            divs[i5] = new IPAddressLargeDivision(lowerBytes, upperBytes, bitLength, divRadix, network, divPrefixLength);
                            bitsSoFar += bitLength;
                        }
                        grouping = new IPAddressLargeDivisionGrouping(divs, network);
                    } else {
                        IPAddressDivision[] divs = new IPAddressBitsDivision[totalCount];
                        for (int i6 = 0; i6 < totalCount; ++i6) {
                            Integer divPrefixLength;
                            long upper;
                            long lower;
                            int bitLength;
                            if (i6 < segmentCount) {
                                bitLength = addrParseData.getBitLength(i6);
                                if (addrParseData.isWildcard(i6)) {
                                    lower = 0L;
                                    upper = -1L << bitLength ^ 0xFFFFFFFFFFFFFFFFL;
                                } else {
                                    lower = addrParseData.getValue(i6, 2);
                                    upper = addrParseData.getValue(i6, 10);
                                }
                                divRadix = defaultRadix;
                            } else if (isMergedMixed && i6 == segmentCount) {
                                bitLength = addrParseData.getBitLength(i6) + this.mixedParsedAddress.getBitLength(0);
                                lower = 0L;
                                upper = -1L << bitLength ^ 0xFFFFFFFFFFFFFFFFL;
                                divRadix = defaultRadix;
                            } else {
                                int adjusted = i6 - segmentCount;
                                bitLength = this.mixedParsedAddress.getBitLength(adjusted);
                                if (this.mixedParsedAddress.isWildcard(adjusted)) {
                                    lower = 0L;
                                    upper = -1L << bitLength ^ 0xFFFFFFFFFFFFFFFFL;
                                } else {
                                    lower = this.mixedParsedAddress.getValue(adjusted, 2);
                                    upper = this.mixedParsedAddress.getValue(adjusted, 10);
                                }
                                divRadix = 10;
                            }
                            if (prefLength == null) {
                                divPrefixLength = null;
                                if (hasMask) {
                                    long divMask;
                                    Masker masker = this.maskers[i6];
                                    if (!masker.isSequential()) {
                                        throw new IncompatibleAddressException(lower, upper, maskVal, "ipaddress.error.maskMismatch");
                                    }
                                    if (maskBits > 64) {
                                        int extendedBits = maskBits - 64;
                                        if (extendedBits >= bitLength) {
                                            divMask = extendedMaskVal >>> extendedBits - bitLength;
                                        } else {
                                            int shortBits = bitLength - extendedBits;
                                            divMask = extendedMaskVal << shortBits | maskVal >> 64 - shortBits;
                                        }
                                    } else {
                                        divMask = maskVal >>> maskBits - bitLength;
                                    }
                                    maskBits -= bitLength;
                                    lower = masker.getMaskedLower(lower, divMask);
                                    upper = masker.getMaskedUpper(upper, divMask);
                                }
                            } else {
                                divPrefixLength = ParsedAddressGrouping.getDivisionPrefixLength(bitLength, prefLength - bitsSoFar);
                                if (isPrefixSubnet && divPrefixLength != null) {
                                    long networkMask = -1L << bitLength - divPrefixLength;
                                    lower &= networkMask;
                                    upper |= networkMask ^ 0xFFFFFFFFFFFFFFFFL;
                                }
                            }
                            divs[i6] = new IPAddressBitsDivision(lower, upper, bitLength, divRadix, network, divPrefixLength);
                            bitsSoFar += bitLength;
                        }
                        grouping = new IPAddressDivisionGrouping(divs, network);
                    }
                    ((TranslatedResult)val).series = grouping;
                    if (this.isDoneTranslating()) {
                        this.releaseSegmentData();
                    }
                }
            }
        }
        return grouping;
    }

    @Deprecated
    public static ExtendedMasker maskRange(long value, long extendedValue, long upperValue, long extendedUpperValue, long maskValue, long extendedMaskValue, long maxValue, long extendedMaxValue) {
        return ParsedIPAddress.maskExtendedRange(value, extendedValue, upperValue, extendedUpperValue, maskValue, extendedMaskValue, maxValue, extendedMaxValue);
    }

    public static ExtendedMasker maskExtendedRange(long value, long extendedValue, long upperValue, long extendedUpperValue, long maskValue, long extendedMaskValue, long maxValue, long extendedMaxValue) {
        int highestDifferingBitMasked;
        ExtendedFullRangeMasker[] cache;
        ExtendedFullRangeMasker result;
        long extendedDiffering = extendedValue ^ extendedUpperValue;
        if (extendedDiffering == 0L) {
            Masker masker = ParsedIPAddress.maskRange(value, upperValue, maskValue, maxValue);
            if (masker == DEFAULT_MASKER) {
                return DEFAULT_MASKER;
            }
            if (masker instanceof FullRangeMasker) {
                int fullRangeBit = ((FullRangeMasker)masker).fullRangeBit;
                WrappedMasker[] cache2 = masker.isSequential() ? WRAPPED_SEQUENTIAL_FULL_RANGE_MASKERS : WRAPPED_FULL_RANGE_MASKERS;
                WrappedMasker result2 = cache2[fullRangeBit];
                if (result2 == null) {
                    cache2[fullRangeBit] = result2 = new WrappedMasker(masker);
                }
                return result2;
            }
            return new WrappedMasker(masker);
        }
        if (extendedValue > extendedUpperValue) {
            throw new IllegalArgumentException("value > upper value");
        }
        if (maskValue == maxValue && extendedMaskValue == extendedMaxValue || maskValue == 0L && extendedMaskValue == 0L) {
            return DEFAULT_MASKER;
        }
        int highestDifferingBitInRange = Long.numberOfLeadingZeros(extendedDiffering);
        long extendedDifferingMasked = extendedMaskValue & -1L >>> highestDifferingBitInRange;
        if (extendedDifferingMasked != 0L) {
            boolean maskedIsSequential;
            boolean differingIsLowestBit = extendedDifferingMasked == 1L;
            int highestDifferingBitMasked2 = Long.numberOfLeadingZeros(extendedDifferingMasked);
            long hostMask = -1L >>> highestDifferingBitMasked2 + 1;
            if (!differingIsLowestBit) {
                maskedIsSequential = (extendedMaskValue & hostMask) == hostMask && maskValue == maxValue;
            } else {
                boolean bl = maskedIsSequential = maskValue == maxValue;
            }
            if (value == 0L && extendedValue == 0L && upperValue == maxValue && extendedUpperValue == extendedMaxValue) {
                if (maskedIsSequential) {
                    return DEFAULT_MASKER;
                }
                return DEFAULT_NON_SEQUENTIAL_MASKER;
            }
            if (highestDifferingBitMasked2 > highestDifferingBitInRange) {
                ExtendedFullRangeMasker[] cache3;
                ExtendedFullRangeMasker result3;
                if (maskedIsSequential) {
                    BigInteger lowerBig;
                    BigInteger upperBig;
                    BigInteger count;
                    int shift = 64 - highestDifferingBitMasked2;
                    BigInteger countRequiredForSequential = ONE_SHIFTED_EXTENDED[shift];
                    if (countRequiredForSequential == null) {
                        countRequiredForSequential = ParsedIPAddress.ONE_SHIFTED_EXTENDED[shift] = BigInteger.valueOf(1L << shift).shiftLeft(64);
                    }
                    maskedIsSequential = (count = (upperBig = new BigInteger(1, ParsedIPAddress.toBytesSizeAdjusted(upperValue, extendedUpperValue, 16))).subtract(lowerBig = new BigInteger(1, ParsedIPAddress.toBytesSizeAdjusted(value, extendedValue, 16))).add(BigInteger.ONE)).compareTo(countRequiredForSequential) >= 0;
                }
                if ((result3 = (cache3 = maskedIsSequential ? EXTENDED_SEQUENTIAL_FULL_RANGE_MASKERS : EXTENDED_FULL_RANGE_MASKERS)[highestDifferingBitMasked2]) == null) {
                    cache3[highestDifferingBitMasked2] = result3 = new ExtendedFullRangeMasker(highestDifferingBitMasked2, maskedIsSequential);
                }
                return result3;
            }
            if (!maskedIsSequential) {
                BigInteger bigHostZeroed;
                BigInteger bigHostMask = HOST_MASK_EXTENDED[highestDifferingBitMasked2];
                if (bigHostMask == null) {
                    bigHostMask = BigInteger.valueOf(hostMask);
                    bigHostMask = bigHostMask.shiftLeft(64);
                    byte b = -1;
                    ParsedIPAddress.HOST_MASK_EXTENDED[highestDifferingBitMasked2] = bigHostMask = bigHostMask.or(new BigInteger(1, new byte[]{b, b, b, b, b, b, b, b}));
                }
                if ((bigHostZeroed = NETWORK_MASK_EXTENDED[highestDifferingBitMasked2]) == null) {
                    bigHostZeroed = ParsedIPAddress.NETWORK_MASK_EXTENDED[highestDifferingBitMasked2] = bigHostMask.not();
                }
                BigInteger upperBig = new BigInteger(1, ParsedIPAddress.toBytesSizeAdjusted(upperValue, extendedUpperValue, 16));
                BigInteger lowerBig = new BigInteger(1, ParsedIPAddress.toBytesSizeAdjusted(value, extendedValue, 16));
                BigInteger upperToBeMaskedBig = upperBig.and(bigHostZeroed);
                BigInteger lowerToBeMaskedBig = lowerBig.or(bigHostMask);
                BigInteger maskBig = new BigInteger(1, ParsedIPAddress.toBytesSizeAdjusted(maskValue, extendedMaskValue, 16));
                for (int nextBit = 128 - (highestDifferingBitMasked2 + 1) - 1; nextBit >= 0; --nextBit) {
                    if (!maskBig.testBit(nextBit)) continue;
                    BigInteger candidate = upperToBeMaskedBig.setBit(nextBit);
                    if (candidate.compareTo(upperBig) <= 0) {
                        upperToBeMaskedBig = candidate;
                    }
                    if ((candidate = lowerToBeMaskedBig.clearBit(nextBit)).compareTo(lowerBig) < 0) continue;
                    lowerToBeMaskedBig = candidate;
                }
                return new ExtendedSpecificValueMasker(lowerToBeMaskedBig.shiftRight(64).longValue(), lowerToBeMaskedBig.longValue(), upperToBeMaskedBig.shiftRight(64).longValue(), upperToBeMaskedBig.longValue());
            }
            return DEFAULT_MASKER;
        }
        if (maskValue == 0L) {
            return DEFAULT_MASKER;
        }
        boolean maskedIsSequential = true;
        int highestDifferingBitMaskedLow = Long.numberOfLeadingZeros(maskValue);
        if (maskValue != maxValue && highestDifferingBitMaskedLow < 63) {
            long hostMask = -1L >>> highestDifferingBitMaskedLow + 1;
            boolean bl = maskedIsSequential = (maskValue & hostMask) == hostMask;
        }
        if (maskedIsSequential) {
            BigInteger countRequiredForSequential;
            if (highestDifferingBitMaskedLow == 0) {
                countRequiredForSequential = ONE_EXTENDED;
            } else if (highestDifferingBitMaskedLow == 1) {
                countRequiredForSequential = HIGH_BIT;
            } else {
                int shift = 64 - highestDifferingBitMaskedLow;
                countRequiredForSequential = ONE_SHIFTED[shift];
                if (countRequiredForSequential == null) {
                    countRequiredForSequential = ParsedIPAddress.ONE_SHIFTED[shift] = BigInteger.valueOf(1L << shift);
                }
            }
            BigInteger upperBig = new BigInteger(1, ParsedIPAddress.toBytesSizeAdjusted(upperValue, extendedUpperValue, 16));
            BigInteger lowerBig = new BigInteger(1, ParsedIPAddress.toBytesSizeAdjusted(value, extendedValue, 16));
            BigInteger count = upperBig.subtract(lowerBig).add(BigInteger.ONE);
            maskedIsSequential = count.compareTo(countRequiredForSequential) >= 0;
        }
        if ((result = (cache = maskedIsSequential ? EXTENDED_SEQUENTIAL_FULL_RANGE_MASKERS : EXTENDED_FULL_RANGE_MASKERS)[highestDifferingBitMasked = highestDifferingBitMaskedLow + 64]) == null) {
            cache[highestDifferingBitMasked] = result = new ExtendedFullRangeMasker(highestDifferingBitMasked, maskedIsSequential);
        }
        return result;
    }

    public static Masker maskRange(long value, long upperValue, long maskValue) {
        return ParsedIPAddress.maskRange(value, upperValue, maskValue, -1L);
    }

    public static Masker maskRange(long value, long upperValue, long maskValue, long maxValue) {
        if (value == upperValue) {
            return DEFAULT_MASKER;
        }
        if (value > upperValue) {
            throw new IllegalArgumentException("value > upper value");
        }
        if (maskValue == 0L || maskValue == maxValue) {
            return DEFAULT_MASKER;
        }
        long differing = value ^ upperValue;
        if (differing != 1L) {
            boolean foundDiffering;
            int highestDifferingBitInRange = Long.numberOfLeadingZeros(differing);
            long maskMask = -1L >>> highestDifferingBitInRange;
            long differingMasked = maskValue & maskMask;
            boolean bl = foundDiffering = differingMasked != 0L;
            if (foundDiffering) {
                boolean maskedIsSequential;
                int highestDifferingBitMasked = Long.numberOfLeadingZeros(differingMasked);
                long hostMask = highestDifferingBitMasked == 63 ? 0L : -1L >>> highestDifferingBitMasked + 1;
                boolean bl2 = maskedIsSequential = (maskValue & hostMask) == hostMask;
                if (!(maxValue != -1L || maskedIsSequential && highestDifferingBitMasked <= highestDifferingBitInRange)) {
                    int highestOneBit = Long.numberOfLeadingZeros(upperValue);
                    maxValue = -1L >>> highestOneBit;
                }
                if (value == 0L && upperValue == maxValue) {
                    if (maskedIsSequential) {
                        return DEFAULT_MASKER;
                    }
                    return DEFAULT_NON_SEQUENTIAL_MASKER;
                }
                if (highestDifferingBitMasked > highestDifferingBitInRange) {
                    FullRangeMasker[] cache;
                    FullRangeMasker result;
                    long countRequiredForSequential;
                    long count;
                    if (maskedIsSequential && highestDifferingBitMasked < 63 && (count = upperValue - value + 1L) < (countRequiredForSequential = 1L << 64 - highestDifferingBitMasked)) {
                        maskedIsSequential = false;
                    }
                    if ((result = (cache = maskedIsSequential ? SEQUENTIAL_FULL_RANGE_MASKERS : FULL_RANGE_MASKERS)[highestDifferingBitMasked]) == null) {
                        cache[highestDifferingBitMasked] = result = new FullRangeMasker(highestDifferingBitMasked, maskedIsSequential);
                    }
                    return result;
                }
                if (!maskedIsSequential) {
                    long hostZeroed = hostMask ^ 0xFFFFFFFFFFFFFFFFL;
                    long upperToBeMasked = upperValue & hostZeroed;
                    long lowerToBeMasked = value | hostMask;
                    for (long nextBit = (long)(1 << 64 - (highestDifferingBitMasked + 1) - 1); nextBit != 0L; nextBit >>>= 1) {
                        if ((maskValue & nextBit) == 0L) continue;
                        long candidate = upperToBeMasked | nextBit;
                        if (candidate <= upperValue) {
                            upperToBeMasked = candidate;
                        }
                        if ((candidate = lowerToBeMasked & (nextBit ^ 0xFFFFFFFFFFFFFFFFL)) < value) continue;
                        lowerToBeMasked = candidate;
                    }
                    return new SpecificValueMasker(lowerToBeMasked, upperToBeMasked);
                }
            }
        }
        return DEFAULT_MASKER;
    }

    public static BitwiseOrer bitwiseOrRange(long value, long upperValue, long maskValue) {
        return ParsedIPAddress.bitwiseOrRange(value, upperValue, maskValue, -1L);
    }

    public static BitwiseOrer bitwiseOrRange(long value, long upperValue, long maskValue, long maxValue) {
        if (value == upperValue) {
            return DEFAULT_OR_MASKER;
        }
        if (value > upperValue) {
            throw new IllegalArgumentException("value > upper value");
        }
        if (maskValue == 0L || maskValue == maxValue) {
            return DEFAULT_OR_MASKER;
        }
        long differing = value ^ upperValue;
        if (differing != 1L) {
            boolean foundDiffering;
            int highestDifferingBitInRange = Long.numberOfLeadingZeros(differing);
            long maskMask = -1L >>> highestDifferingBitInRange;
            long differingMasked = maskValue & maskMask;
            boolean bl = foundDiffering = differingMasked != maskMask;
            if (foundDiffering) {
                boolean maskedIsSequential;
                int highestDifferingBitMasked = Long.numberOfLeadingZeros((differingMasked ^ 0xFFFFFFFFFFFFFFFFL) & maskMask);
                long hostMask = highestDifferingBitMasked == 63 ? 0L : -1L >>> highestDifferingBitMasked + 1;
                boolean bl2 = maskedIsSequential = (maskValue & hostMask) == 0L;
                if (!(maxValue != -1L || maskedIsSequential && highestDifferingBitMasked <= highestDifferingBitInRange)) {
                    int highestOneBit = Long.numberOfLeadingZeros(upperValue);
                    maxValue = -1L >>> highestOneBit;
                }
                if (value == 0L && upperValue == maxValue) {
                    if (maskedIsSequential) {
                        return DEFAULT_OR_MASKER;
                    }
                    return DEFAULT_NON_SEQUENTIAL_OR_MASKER;
                }
                if (highestDifferingBitMasked > highestDifferingBitInRange) {
                    FullRangeBitwiseOrer[] cache;
                    FullRangeBitwiseOrer result;
                    long countRequiredForSequential;
                    long count;
                    if (maskedIsSequential && highestDifferingBitMasked < 63 && (count = upperValue - value + 1L) < (countRequiredForSequential = 1L << 64 - highestDifferingBitMasked)) {
                        maskedIsSequential = false;
                    }
                    if ((result = (cache = maskedIsSequential ? SEQUENTIAL_FULL_RANGE_OR_MASKERS : FULL_RANGE_OR_MASKERS)[highestDifferingBitMasked]) == null) {
                        cache[highestDifferingBitMasked] = result = new FullRangeBitwiseOrer(highestDifferingBitMasked, maskedIsSequential);
                    }
                    return result;
                }
                if (!maskedIsSequential) {
                    long hostZeroed = hostMask ^ 0xFFFFFFFFFFFFFFFFL;
                    long upperToBeMasked = upperValue & hostZeroed;
                    long lowerToBeMasked = value | hostMask;
                    for (long nextBit = 1L << 64 - (highestDifferingBitMasked + 1) - 1; nextBit != 0L; nextBit >>>= 1) {
                        if ((maskValue & nextBit) != 0L) continue;
                        long candidate = upperToBeMasked | nextBit;
                        if (candidate <= upperValue) {
                            upperToBeMasked = candidate;
                        }
                        if ((candidate = lowerToBeMasked & (nextBit ^ 0xFFFFFFFFFFFFFFFFL)) < value) continue;
                        lowerToBeMasked = candidate;
                    }
                    return new SpecificValueBitwiseOrer(lowerToBeMasked, upperToBeMasked);
                }
            }
        }
        return DEFAULT_OR_MASKER;
    }

    static byte[] toBytesSizeAdjusted(long val, long extended, int numBytes) {
        byte b;
        int adjustedNumBytes = numBytes;
        int boundary = numBytes - 8;
        int adj = numBytes + boundary;
        for (int j = 1; j <= numBytes && (b = j <= boundary ? (byte)(extended >>> (numBytes - j << 3)) : (byte)(val >>> (adj - j << 3))) == 0; ++j) {
            --adjustedNumBytes;
        }
        return ParsedIPAddress.toBytes(val, extended, adjustedNumBytes);
    }

    static byte[] toBytes(long val, long extended, int numBytes) {
        byte[] bytes = new byte[numBytes];
        int boundary = numBytes - 8;
        for (int j = numBytes - 1; j >= 0; --j) {
            if (j >= boundary) {
                bytes[j] = (byte)(val & 0xFFL);
                val >>>= 8;
                continue;
            }
            bytes[j] = (byte)(extended & 0xFFL);
            extended >>>= 8;
        }
        return bytes;
    }

    private boolean groupingIsSequential() {
        try {
            return this.getDivisionGrouping().isSequential();
        }
        catch (IncompatibleAddressException e) {
            return false;
        }
    }

    @Override
    public boolean isSequential() {
        TranslatedResult<?, ?> val = this.values;
        if (val != null) {
            if (!val.withoutSections()) {
                if (val.withoutAddressException()) {
                    return ((Address)val.getAddress()).isSequential();
                }
                return this.groupingIsSequential();
            }
            if (!val.withoutGrouping()) {
                return this.groupingIsSequential();
            }
        }
        if ((val = this.getCachedAddresses(false)).withoutAddressException()) {
            return ((Address)val.getAddress()).isSequential();
        }
        return this.groupingIsSequential();
    }

    private boolean skipContains(boolean skipMixed) {
        IPAddress mask;
        AddressParseData parseData = this.getAddressParseData();
        int segmentCount = parseData.getSegmentCount();
        if (this.isProvidingIPv4()) {
            if (segmentCount != 4) {
                return true;
            }
        } else {
            int expectedSegmentCount;
            if (this.isProvidingMixedIPv6()) {
                if (skipMixed) {
                    return true;
                }
                expectedSegmentCount = 6;
            } else {
                expectedSegmentCount = 8;
            }
            if (segmentCount != expectedSegmentCount && !this.isCompressed()) {
                return true;
            }
        }
        return (mask = this.getProviderMask()) != null && mask.getBlockMaskPrefixLength(true) == null;
    }

    @Override
    public Boolean contains(String other) {
        AddressParseData parseData = this.getAddressParseData();
        int[] segmentData = parseData.getSegmentData();
        if (segmentData == null) {
            return null;
        }
        if (this.skipContains(true)) {
            return null;
        }
        if (this.has_inet_aton_value || this.hasIPv4LeadingZeros || this.isBinary) {
            return null;
        }
        Integer pref = this.getProviderNetworkPrefixLength();
        IPAddressStringParameters options = this.getParameters();
        IPAddressNetwork<?, ?, ?, ?, ?> network = (this.isProvidingIPv4() ? options.getIPv4Parameters() : options.getIPv6Parameters()).getNetwork();
        if (pref != null && !this.isPrefixSubnet(pref, network, segmentData)) {
            return null;
        }
        return this.matchesPrefix(other, segmentData);
    }

    @Override
    public Boolean prefixContains(String other) {
        Boolean b = this.prefixEquals(other);
        if (b != null && b.booleanValue()) {
            return b;
        }
        return null;
    }

    @Override
    public Boolean prefixEquals(String other) {
        AddressParseData parseData = this.getAddressParseData();
        int[] segmentData = parseData.getSegmentData();
        if (segmentData == null) {
            return null;
        }
        if (this.skipContains(true)) {
            return null;
        }
        if (this.has_inet_aton_value || this.hasIPv4LeadingZeros || this.isBinary) {
            return null;
        }
        return this.matchesPrefix(other, segmentData);
    }

    private Boolean matchesPrefix(String other, int[] segmentData) {
        char otherChar;
        int bytesPerSegment;
        int bitsPerSegment;
        boolean prefixIsMidSegment;
        int prefixEndCharIndex;
        int expectedCount;
        int otherLen = other.length();
        boolean isIPv4 = this.isProvidingIPv4();
        if (otherLen >= 4) {
            char prefixLenSep = '/';
            if (other.charAt(otherLen - 2) == prefixLenSep || other.charAt(otherLen - 3) == prefixLenSep) {
                return null;
            }
            if (!isIPv4 && other.charAt(otherLen - 4) == prefixLenSep) {
                return null;
            }
        }
        AddressParseData parseData = this.getAddressParseData();
        Integer pref = this.getProviderNetworkPrefixLength();
        boolean compressedAlready = false;
        boolean networkSegIsCompressed = false;
        int adjustment = 0;
        int networkSegsCount = 0;
        int networkSegIndex = 0;
        int networkSegCharIndex = 0;
        int remainingSegsCharIndex = 0;
        if (pref == null) {
            expectedCount = isIPv4 ? 4 : 8;
            networkSegIndex = expectedCount - 1;
            prefixEndCharIndex = ParsedIPAddress.getIndex(networkSegIndex, 15, segmentData);
            if (otherLen > prefixEndCharIndex) {
                return null;
            }
            prefixIsMidSegment = false;
        } else if (pref == 0) {
            prefixIsMidSegment = false;
            expectedCount = isIPv4 ? 4 : 8;
            prefixEndCharIndex = 0;
        } else if (isIPv4) {
            expectedCount = 4;
            bitsPerSegment = 8;
            bytesPerSegment = 1;
            networkSegIndex = ParsedAddressGrouping.getNetworkSegmentIndex(pref, bytesPerSegment, bitsPerSegment);
            prefixEndCharIndex = ParsedIPAddress.getIndex(networkSegIndex, 15, segmentData);
            Integer segPrefLength = ParsedAddressGrouping.getPrefixedSegmentPrefixLength(bitsPerSegment, pref, networkSegIndex);
            prefixIsMidSegment = segPrefLength != bitsPerSegment;
            networkSegsCount = networkSegIndex + 1;
            remainingSegsCharIndex = prefixEndCharIndex + 1;
            if (prefixIsMidSegment) {
                networkSegCharIndex = ParsedIPAddress.getIndex(networkSegIndex, 6, segmentData);
            }
        } else {
            expectedCount = 8;
            bitsPerSegment = 16;
            bytesPerSegment = 2;
            networkSegIndex = ParsedAddressGrouping.getNetworkSegmentIndex(pref, bytesPerSegment, bitsPerSegment);
            int missingSegmentCount = 8 - parseData.getSegmentCount();
            int compressedSegIndex = this.getConsecutiveSeparatorSegmentIndex();
            compressedAlready = compressedSegIndex <= networkSegIndex;
            networkSegIsCompressed = compressedAlready && compressedSegIndex + missingSegmentCount >= networkSegIndex;
            Integer segPrefLength = ParsedAddressGrouping.getPrefixedSegmentPrefixLength(bitsPerSegment, pref, networkSegIndex);
            if (networkSegIsCompressed) {
                prefixIsMidSegment = segPrefLength != bitsPerSegment;
                networkSegsCount = networkSegIndex + 1;
                prefixEndCharIndex = ParsedIPAddress.getIndex(compressedSegIndex, 15, segmentData) + 1;
                if (prefixIsMidSegment && compressedSegIndex > 0) {
                    networkSegCharIndex = ParsedIPAddress.getIndex(compressedSegIndex, 6, segmentData);
                }
                remainingSegsCharIndex = prefixEndCharIndex + 1;
            } else {
                int actualNetworkSegIndex = compressedSegIndex < networkSegIndex ? networkSegIndex - missingSegmentCount : networkSegIndex;
                prefixEndCharIndex = ParsedIPAddress.getIndex(actualNetworkSegIndex, 15, segmentData);
                adjustment = 4 - (segPrefLength + 3 >> 2);
                if (adjustment > 0) {
                    prefixIsMidSegment = true;
                    remainingSegsCharIndex = ParsedIPAddress.getIndex(actualNetworkSegIndex, 14, segmentData);
                    if (remainingSegsCharIndex + adjustment > prefixEndCharIndex) {
                        adjustment = prefixEndCharIndex - remainingSegsCharIndex;
                    }
                    prefixEndCharIndex -= adjustment;
                    networkSegsCount = networkSegIndex;
                    networkSegCharIndex = ParsedIPAddress.getIndex(actualNetworkSegIndex, 6, segmentData);
                } else {
                    prefixIsMidSegment = segPrefLength != bitsPerSegment;
                    networkSegsCount = actualNetworkSegIndex + 1;
                    remainingSegsCharIndex = prefixEndCharIndex + 1;
                    if (prefixIsMidSegment) {
                        networkSegCharIndex = ParsedIPAddress.getIndex(actualNetworkSegIndex, 6, segmentData);
                    }
                }
            }
        }
        CharSequence str = this.str;
        int otherSegmentCount = 0;
        boolean currentSegHasNonZeroDigits = false;
        for (int i = 0; i < prefixEndCharIndex; ++i) {
            boolean isSegmentEnd;
            char c = str.charAt(i);
            if (c != (otherChar = i < otherLen ? other.charAt(i) : (char)'\u0000')) {
                int k;
                char adjustedChar;
                if (!(c >= '1' && c <= '9' || c >= 'a' && c <= 'f')) {
                    if (c >= 'A' && c <= 'F') {
                        adjustedChar = (char)(c - -32);
                        if (c == adjustedChar) {
                            continue;
                        }
                    } else if (c <= '-' && c >= '%' ? c == '*' || c == '-' || c == '%' : c == '_') {
                        return null;
                    }
                }
                if (otherChar >= 'A' && otherChar <= 'F' && otherChar == (adjustedChar = (char)(otherChar - -32))) continue;
                if (prefixIsMidSegment && (i >= networkSegCharIndex || networkSegCharIndex == 1)) {
                    return null;
                }
                if (this.hasRange(otherSegmentCount)) {
                    return null;
                }
                if (!(otherChar >= '1' && otherChar <= '9' || otherChar >= 'a' && otherChar <= 'f')) {
                    if (otherChar <= '-' && otherChar >= '%' ? otherChar == '*' || otherChar == '-' || otherChar == '%' : otherChar == '_') {
                        return null;
                    }
                    if (!currentSegHasNonZeroDigits) {
                        if (c == '0') {
                            char nextChar;
                            if (otherChar == ':' || otherChar == '\u0000') {
                                return null;
                            }
                            k = i + 1;
                            if (k < str.length() && (nextChar = str.charAt(k)) != '.' && nextChar != ':') {
                                return null;
                            }
                        } else if (otherChar == '0') {
                            char nextChar;
                            if (c == ':') {
                                return null;
                            }
                            k = i + 1;
                            if (k < otherLen && (nextChar = other.charAt(k)) != '.' && nextChar != ':') {
                                return null;
                            }
                            return Boolean.FALSE;
                        }
                    }
                    if (otherChar == ':') {
                        return Boolean.FALSE;
                    }
                    if (otherChar == '.') {
                        if (!isIPv4) {
                            return null;
                        }
                        ++otherSegmentCount;
                    }
                }
                for (k = i + 1; k < otherLen; ++k) {
                    otherChar = other.charAt(k);
                    if (otherChar == ':') {
                        return Boolean.FALSE;
                    }
                    if (otherChar > '/' || otherChar < '%') continue;
                    if (otherChar == '.') {
                        if (!isIPv4) {
                            return null;
                        }
                        ++otherSegmentCount;
                        continue;
                    }
                    if (otherChar != '/' && otherChar != '*' && otherChar != '-' && otherChar != '%' && otherChar != '_') continue;
                    return null;
                }
                if (isIPv4 ? otherSegmentCount + 1 == 4 : otherSegmentCount > 0) {
                    return Boolean.FALSE;
                }
                return null;
            }
            if (c == '0') continue;
            boolean bl = isSegmentEnd = c == ':' || c == '.';
            if (isSegmentEnd) {
                ++otherSegmentCount;
                currentSegHasNonZeroDigits = false;
                continue;
            }
            currentSegHasNonZeroDigits = true;
        }
        if (pref != null) {
            int digitCount;
            int segmentEndIndex;
            if (prefixEndCharIndex == otherLen) {
                if (!(networkSegsCount == expectedCount || compressedAlready && networkSegsCount <= expectedCount)) {
                    return null;
                }
            } else if (isIPv4) {
                if (pref != 0) {
                    segmentEndIndex = prefixEndCharIndex + adjustment;
                    if (otherLen < segmentEndIndex) {
                        return null;
                    }
                    if (otherLen != segmentEndIndex && other.charAt(segmentEndIndex) != '.') {
                        return null;
                    }
                    for (int n = prefixEndCharIndex; n < segmentEndIndex; ++n) {
                        otherChar = other.charAt(n);
                        if (otherChar != '.') continue;
                        return null;
                    }
                }
                digitCount = 0;
                int remainingSegCount = 0;
                boolean firstIsHighIPv4 = false;
                for (int i = remainingSegsCharIndex; i < otherLen; ++i) {
                    char otherChar2 = other.charAt(i);
                    if (otherChar2 <= '9' && otherChar2 >= '0') {
                        if (digitCount == 0 && otherChar2 >= '3') {
                            firstIsHighIPv4 = true;
                        }
                        ++digitCount;
                        continue;
                    }
                    if (otherChar2 == '.') {
                        if (digitCount == 0) {
                            return Boolean.FALSE;
                        }
                        if (firstIsHighIPv4) {
                            if (digitCount >= 3) {
                                return Boolean.FALSE;
                            }
                        } else if (digitCount > 3) {
                            return null;
                        }
                        digitCount = 0;
                        ++remainingSegCount;
                        firstIsHighIPv4 = false;
                        continue;
                    }
                    return null;
                }
                if (digitCount == 0) {
                    return Boolean.FALSE;
                }
                if (digitCount > 3) {
                    return null;
                }
                if (firstIsHighIPv4 && digitCount == 3) {
                    return null;
                }
                int totalSegCount = networkSegsCount + remainingSegCount + 1;
                if (totalSegCount != expectedCount) {
                    return null;
                }
            } else {
                int totalSegCount;
                int i;
                if (pref != 0) {
                    segmentEndIndex = prefixEndCharIndex + adjustment;
                    if (otherLen < segmentEndIndex) {
                        return null;
                    }
                    if (otherLen != segmentEndIndex && other.charAt(segmentEndIndex) != ':') {
                        return null;
                    }
                    for (int n = prefixEndCharIndex; n < segmentEndIndex; ++n) {
                        otherChar = other.charAt(n);
                        if (otherChar != ':') continue;
                        return null;
                    }
                }
                digitCount = 0;
                int remainingSegCount = 0;
                for (i = remainingSegsCharIndex; i < otherLen; ++i) {
                    char otherChar3 = other.charAt(i);
                    if (otherChar3 <= '9' && otherChar3 >= '0') {
                        ++digitCount;
                        continue;
                    }
                    if (otherChar3 >= 'a' && otherChar3 <= 'f' || otherChar3 >= 'A' && otherChar3 <= 'F') {
                        ++digitCount;
                        continue;
                    }
                    if (otherChar3 == '.') {
                        return null;
                    }
                    if (otherChar3 == ':') {
                        if (digitCount > 4) {
                            return null;
                        }
                        if (digitCount == 0) {
                            if (compressedAlready) {
                                return Boolean.FALSE;
                            }
                            compressedAlready = true;
                        } else {
                            digitCount = 0;
                        }
                        ++remainingSegCount;
                        continue;
                    }
                    return null;
                }
                if (digitCount == 0) {
                    int prevIndex = i - 1;
                    if (prevIndex < 0) {
                        return Boolean.FALSE;
                    }
                    char prevChar = other.charAt(prevIndex);
                    if (prevChar != ':') {
                        return Boolean.FALSE;
                    }
                } else if (digitCount > 4) {
                    return null;
                }
                if ((totalSegCount = networkSegsCount + remainingSegCount + 1) > expectedCount || totalSegCount < expectedCount && !compressedAlready) {
                    return null;
                }
                if (networkSegIsCompressed && expectedCount - remainingSegCount <= networkSegIndex) {
                    return null;
                }
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean contains(IPAddressProvider other) {
        if (other instanceof ParsedIPAddress) {
            TranslatedResult<?, ?> vals = this.values;
            TranslatedResult<?, ?> otherVals = this.values;
            if (vals == null || otherVals == null) {
                return this.contains((ParsedIPAddress)other, false, false);
            }
        }
        return null;
    }

    @Override
    public Boolean parsedEquals(IPAddressProvider other) {
        if (other instanceof ParsedIPAddress) {
            ParsedIPAddress parsedOther;
            Boolean result;
            TranslatedResult<?, ?> vals = this.values;
            TranslatedResult<?, ?> otherVals = this.values;
            if ((vals == null || otherVals == null) && (result = this.contains(parsedOther = (ParsedIPAddress)other, false, true)) != null) {
                return result != false && Objects.equals(this.getQualifier().getZone(), parsedOther.getQualifier().getZone());
            }
        }
        return null;
    }

    @Override
    public Boolean prefixContains(IPAddressProvider other) {
        if (other instanceof ParsedIPAddress) {
            TranslatedResult<?, ?> vals = this.values;
            TranslatedResult<?, ?> otherVals = this.values;
            if (vals == null || otherVals == null) {
                return this.contains((ParsedIPAddress)other, true, false);
            }
        }
        return null;
    }

    @Override
    public Boolean prefixEquals(IPAddressProvider other) {
        if (other instanceof ParsedIPAddress) {
            TranslatedResult<?, ?> vals = this.values;
            TranslatedResult<?, ?> otherVals = this.values;
            if (vals == null || otherVals == null) {
                return this.contains((ParsedIPAddress)other, true, true);
            }
        }
        return null;
    }

    private Boolean contains(ParsedIPAddress other, boolean networkOnly, boolean equals) {
        int networkSegIndex;
        int otherHostAllSegIndex;
        int hostAllSegIndex;
        int hostSegIndex;
        boolean otherCompressedAlready;
        boolean compressedAlready;
        IPAddressNetwork network;
        int bytesPerSegment;
        int bitsPerSegment;
        int expectedSegCount;
        int expectedOtherSegCount;
        int max;
        boolean skipMixed;
        AddressParseData parseData = this.getAddressParseData();
        AddressParseData otherParseData = other.getAddressParseData();
        int[] segmentData = parseData.getSegmentData();
        int[] otherSegmentData = otherParseData.getSegmentData();
        if (segmentData == null || otherSegmentData == null) {
            return null;
        }
        Integer pref = this.getProviderNetworkPrefixLength();
        boolean bl = skipMixed = !networkOnly || pref == null || pref > 96;
        if (this.skipContains(skipMixed) || other.skipContains(skipMixed)) {
            return null;
        }
        IPAddress.IPVersion ipVersion = this.getProviderIPVersion();
        if (!ipVersion.equals((Object)other.getProviderIPVersion())) {
            return Boolean.FALSE;
        }
        IPAddressStringParameters options = this.getParameters();
        int segmentCount = parseData.getSegmentCount();
        int otherSegmentCount = otherParseData.getSegmentCount();
        if (this.isProvidingIPv4()) {
            max = 255;
            expectedOtherSegCount = 4;
            expectedSegCount = 4;
            bitsPerSegment = 8;
            bytesPerSegment = 1;
            network = options.getIPv4Parameters().getNetwork();
            compressedAlready = true;
            otherCompressedAlready = true;
        } else {
            max = 65535;
            expectedOtherSegCount = 8;
            expectedSegCount = 8;
            if (this.isProvidingMixedIPv6()) {
                expectedSegCount -= 2;
            }
            if (other.isProvidingMixedIPv6()) {
                expectedOtherSegCount -= 2;
            }
            bitsPerSegment = 16;
            bytesPerSegment = 2;
            network = options.getIPv6Parameters().getNetwork();
            compressedAlready = expectedSegCount == segmentCount;
            otherCompressedAlready = expectedOtherSegCount == otherSegmentCount;
        }
        AddressNetwork.PrefixConfiguration prefConf = network.getPrefixConfiguration();
        boolean zeroHostsAreSubnets = prefConf.zeroHostsAreSubnets();
        boolean allPrefixedAddressesAreSubnets = prefConf.allPrefixedAddressesAreSubnets();
        Integer otherPref = other.getProviderNetworkPrefixLength();
        int endIndex = segmentCount;
        Integer adjustedOtherPref = null;
        if (pref == null) {
            networkOnly = false;
            hostAllSegIndex = hostSegIndex = expectedSegCount;
            otherHostAllSegIndex = expectedOtherSegCount;
            networkSegIndex = hostSegIndex - 1;
        } else if (networkOnly) {
            otherHostAllSegIndex = hostSegIndex = ParsedAddressGrouping.getHostSegmentIndex(pref, bytesPerSegment, bitsPerSegment);
            hostAllSegIndex = hostSegIndex;
            networkSegIndex = ParsedAddressGrouping.getNetworkSegmentIndex(pref, bytesPerSegment, bitsPerSegment);
            adjustedOtherPref = pref;
        } else {
            otherHostAllSegIndex = expectedOtherSegCount;
            hostSegIndex = ParsedAddressGrouping.getHostSegmentIndex(pref, bytesPerSegment, bitsPerSegment);
            networkSegIndex = ParsedAddressGrouping.getNetworkSegmentIndex(pref, bytesPerSegment, bitsPerSegment);
            if (allPrefixedAddressesAreSubnets || zeroHostsAreSubnets && this.isPrefixSubnet(pref, network, segmentData)) {
                hostAllSegIndex = hostSegIndex;
                if (!equals) {
                    networkOnly = true;
                }
            } else {
                hostAllSegIndex = expectedSegCount;
            }
        }
        if (otherPref != null) {
            int otherPrefLen = otherPref;
            if (adjustedOtherPref == null || otherPrefLen < adjustedOtherPref) {
                int otherHostIndex = ParsedAddressGrouping.getHostSegmentIndex(otherPrefLen, bytesPerSegment, bitsPerSegment);
                if (otherHostIndex < otherHostAllSegIndex && (allPrefixedAddressesAreSubnets || zeroHostsAreSubnets && other.isPrefixSubnet(otherPrefLen, network, otherSegmentData))) {
                    otherHostAllSegIndex = otherHostIndex;
                }
            } else {
                otherPref = adjustedOtherPref;
            }
        } else {
            otherPref = adjustedOtherPref;
        }
        int i = 0;
        int j = 0;
        int otherCompressedCount = 0;
        int compressedCount = 0;
        for (int normalizedCount = 0; !(i >= endIndex && compressedCount <= 0 || networkOnly && normalizedCount > networkSegIndex); ++normalizedCount) {
            long otherUpper;
            long otherLower;
            long lower;
            long upper;
            if (compressedCount > 0) {
                upper = 0L;
                lower = 0L;
            } else {
                lower = ParsedIPAddress.getValue(i, 2, segmentData);
                upper = ParsedIPAddress.getValue(i, 10, segmentData);
            }
            if (normalizedCount >= hostAllSegIndex) {
                Integer segPrefLength = ParsedAddressGrouping.getSegmentPrefixLength(bitsPerSegment, pref, normalizedCount);
                lower &= (long)network.getSegmentNetworkMask(segPrefLength);
                upper |= (long)network.getSegmentHostMask(segPrefLength);
            }
            if (normalizedCount > otherHostAllSegIndex) {
                otherLower = 0L;
                otherUpper = max;
            } else {
                if (otherCompressedCount > 0) {
                    otherUpper = 0L;
                    otherLower = 0L;
                } else {
                    otherLower = ParsedIPAddress.getValue(j, 2, otherSegmentData);
                    otherUpper = ParsedIPAddress.getValue(j, 10, otherSegmentData);
                }
                if (normalizedCount == otherHostAllSegIndex) {
                    Integer segPrefLength = ParsedAddressGrouping.getSegmentPrefixLength(bitsPerSegment, otherPref, normalizedCount);
                    otherLower &= (long)network.getSegmentNetworkMask(segPrefLength);
                    otherUpper |= (long)network.getSegmentHostMask(segPrefLength);
                }
            }
            if (equals ? lower != otherLower || upper != otherUpper : lower > otherLower || upper < otherUpper) {
                return Boolean.FALSE;
            }
            if (!compressedAlready) {
                if (compressedCount > 0) {
                    if (--compressedCount == 0) {
                        compressedAlready = true;
                    }
                } else if (this.isCompressed(i, segmentData)) {
                    ++i;
                    compressedCount = expectedSegCount - segmentCount;
                } else {
                    ++i;
                }
            } else {
                ++i;
            }
            if (!otherCompressedAlready) {
                if (otherCompressedCount > 0) {
                    if (--otherCompressedCount != 0) continue;
                    otherCompressedAlready = true;
                    continue;
                }
                if (other.isCompressed(j, otherSegmentData)) {
                    ++j;
                    otherCompressedCount = expectedOtherSegCount - otherSegmentCount;
                    continue;
                }
                ++j;
                continue;
            }
            ++j;
        }
        return Boolean.TRUE;
    }

    protected boolean isPrefixSubnet(Integer networkPrefixLength, IPAddressNetwork<?, ?, ?, ?, ?> network, int[] segmentData) {
        IPAddress.IPVersion version = network.getIPVersion();
        int bytesPerSegment = IPAddressSection.bytesPerSegment(version);
        int bitsPerSegment = IPAddressSection.bitsPerSegment(version);
        int max = IPAddressSegment.getMaxSegmentValue(version);
        AddressNetwork.PrefixConfiguration prefConf = network.getPrefixConfiguration();
        AddressParseData addressParseData = this.getAddressParseData();
        int segmentCount = addressParseData.getSegmentCount();
        if (this.isCompressed()) {
            int compressedCount = 8 - segmentCount;
            int compressedIndex = addressParseData.getConsecutiveSeparatorSegmentIndex();
            return ParsedAddressGrouping.isPrefixSubnet(segmentIndex -> {
                if (segmentIndex >= compressedIndex) {
                    if (segmentIndex - compressedIndex < compressedCount) {
                        return 0;
                    }
                    segmentIndex -= compressedCount;
                }
                return (int)ParsedIPAddress.getValue(segmentIndex, 2, segmentData);
            }, segmentIndex -> {
                if (segmentIndex >= compressedIndex) {
                    if (segmentIndex - compressedIndex < compressedCount) {
                        return 0;
                    }
                    segmentIndex -= compressedCount;
                }
                return (int)ParsedIPAddress.getValue(segmentIndex, 10, segmentData);
            }, segmentCount + compressedCount, bytesPerSegment, bitsPerSegment, max, networkPrefixLength, prefConf, false);
        }
        return ParsedAddressGrouping.isPrefixSubnet(segmentIndex -> (int)ParsedIPAddress.getValue(segmentIndex, 2, segmentData), segmentIndex -> (int)ParsedIPAddress.getValue(segmentIndex, 10, segmentData), segmentCount, bytesPerSegment, bitsPerSegment, max, networkPrefixLength, prefConf, false);
    }

    @Override
    public Integer getProviderNetworkPrefixLength() {
        return this.getQualifier().getEquivalentPrefixLength();
    }

    private static <S extends IPAddressSegment> S[] allocateSegments(S[] segments, S[] originalSegments, AddressNetwork.AddressSegmentCreator<S> creator, int segmentCount, int originalCount) {
        if (segments == null) {
            segments = (IPAddressSegment[])creator.createSegmentArray(segmentCount);
            if (originalCount > 0) {
                System.arraycopy(originalSegments, 0, segments, 0, originalCount);
            }
        }
        return segments;
    }

    private void createIPv4Sections(boolean doAddress, boolean doRangeBoundaries, boolean withUpper) {
        AddressSegment[] lowerSegments;
        AddressSegment[] segments;
        ParsedHostIdentifierStringQualifier qualifier = this.getQualifier();
        IPAddress mask = this.getProviderMask();
        if (mask != null && mask.getBlockMaskPrefixLength(true) != null) {
            mask = null;
        }
        boolean hasMask = mask != null;
        AddressParseData addrParseData = this.getAddressParseData();
        int segmentCount = addrParseData.getSegmentCount();
        if (hasMask && this.maskers == null) {
            this.maskers = new Masker[segmentCount];
        }
        IPv4AddressNetwork.IPv4AddressCreator creator = this.getIPv4AddressCreator();
        int ipv4SegmentCount = 4;
        int missingCount = ipv4SegmentCount - segmentCount;
        IPAddressSegment[] upperSegments = null;
        upperSegments = null;
        IPAddressSegment[] hostSegments = null;
        if (doAddress) {
            segments = creator.createSegmentArray(ipv4SegmentCount);
            lowerSegments = null;
        } else if (doRangeBoundaries) {
            lowerSegments = creator.createSegmentArray(ipv4SegmentCount);
            segments = null;
        } else {
            return;
        }
        TranslatedResult<IPv4Address, IPv4AddressSection> finalResult = this.values;
        if (this.values == null) {
            this.values = finalResult = new TranslatedResult<IPv4Address, IPv4AddressSection>(){
                private static final long serialVersionUID = 1L;

                @Override
                ParsedAddressCreator<IPv4Address, IPv4AddressSection, ?, ?> getCreator() {
                    return ParsedIPAddress.this.getIPv4AddressCreator();
                }
            };
        }
        boolean expandedSegments = missingCount <= 0;
        int expandedEnd = -1;
        int expandedStart = -1;
        CharSequence addressString = this.str;
        boolean maskedIsDifferent = false;
        int normalizedSegmentIndex = 0;
        for (int i = 0; i < segmentCount; ++i) {
            long lower = addrParseData.getValue(i, 2);
            long upper = addrParseData.getValue(i, 10);
            if (!expandedSegments) {
                boolean isLastSegment = i == segmentCount - 1;
                boolean isWildcard = addrParseData.isWildcard(i);
                expandedSegments = isLastSegment;
                if (!expandedSegments) {
                    boolean bl = expandedSegments = !this.is_inet_aton_joined() && isWildcard;
                    if (expandedSegments) {
                        for (int j = i + 1; j < segmentCount; ++j) {
                            if (!addrParseData.isWildcard(j)) continue;
                            expandedSegments = false;
                            break;
                        }
                    }
                }
                if (expandedSegments) {
                    long maskedUpper;
                    long maskedLower;
                    if (isWildcard) {
                        upper = -1 >>> (3 - missingCount << 3);
                    } else {
                        expandedStart = i;
                        expandedEnd = i + missingCount;
                    }
                    int bits = 8 * (missingCount + 1);
                    if (hasMask) {
                        long divMask = 0L;
                        for (int k = 0; k <= missingCount; ++k) {
                            divMask = divMask << 8 | (long)((IPAddressSegment)mask.getSegment(normalizedSegmentIndex + k)).getSegmentValue();
                        }
                        Masker masker = this.maskers[i];
                        if (masker == null) {
                            long maxValue = bits == 32 ? 0xFFFFFFFFL : (long)(~(-1 << bits));
                            this.maskers[i] = masker = ParsedIPAddress.maskRange(lower, upper, divMask, maxValue);
                        }
                        if (!masker.isSequential() && ((TranslatedResult)finalResult).maskException == null) {
                            ((TranslatedResult)finalResult).maskException = new IncompatibleAddressException(lower, upper, divMask, "ipaddress.error.maskMismatch");
                        }
                        maskedLower = masker.getMaskedLower(lower, divMask);
                        maskedUpper = masker.getMaskedUpper(upper, divMask);
                        maskedIsDifferent = maskedIsDifferent || maskedLower != lower || maskedUpper != upper;
                    } else {
                        maskedLower = lower;
                        maskedUpper = upper;
                    }
                    int shift = bits;
                    for (int count = missingCount; count >= 0; --count) {
                        int maskedSegUpper;
                        int maskedSegLower;
                        int hostSegUpper;
                        Integer currentPrefix = ParsedIPAddress.getSegmentPrefixLength(normalizedSegmentIndex, 8, qualifier);
                        int segmentBitsMask = 255;
                        int hostSegLower = (int)(lower >>> (shift -= 8)) & segmentBitsMask;
                        int n = hostSegUpper = lower == upper ? hostSegLower : (int)(upper >>> shift) & segmentBitsMask;
                        if (hasMask) {
                            maskedSegLower = (int)(maskedLower >>> shift) & segmentBitsMask;
                            maskedSegUpper = maskedLower == maskedUpper ? maskedSegLower : (int)(maskedUpper >>> shift) & segmentBitsMask;
                        } else {
                            maskedSegLower = hostSegLower;
                            maskedSegUpper = hostSegUpper;
                        }
                        if (doAddress) {
                            if (maskedIsDifferent || currentPrefix != null) {
                                hostSegments = (IPv4AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])hostSegments, (IPAddressSegment[])segments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv4SegmentCount, (int)normalizedSegmentIndex);
                                hostSegments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV4, hostSegLower, hostSegUpper, false, i, null, creator);
                            }
                            segments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV4, maskedSegLower, maskedSegUpper, false, i, currentPrefix, creator);
                        }
                        if (doRangeBoundaries) {
                            boolean isRange;
                            boolean bl = isRange = maskedSegLower != maskedSegUpper;
                            if (!doAddress || isRange) {
                                if (doAddress) {
                                    lowerSegments = (IPv4AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])lowerSegments, (IPAddressSegment[])segments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv4SegmentCount, (int)normalizedSegmentIndex);
                                }
                                lowerSegments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV4, maskedSegLower, maskedSegLower, false, i, currentPrefix, creator);
                            } else if (lowerSegments != null) {
                                lowerSegments[normalizedSegmentIndex] = segments[normalizedSegmentIndex];
                            }
                            if (withUpper) {
                                if (isRange) {
                                    upperSegments = (IPv4AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])upperSegments, (IPAddressSegment[])lowerSegments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv4SegmentCount, (int)normalizedSegmentIndex);
                                    upperSegments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV4, maskedSegUpper, maskedSegUpper, false, i, currentPrefix, creator);
                                } else if (upperSegments != null) {
                                    upperSegments[normalizedSegmentIndex] = lowerSegments[normalizedSegmentIndex];
                                }
                            }
                        }
                        ++normalizedSegmentIndex;
                    }
                    addrParseData.setBitLength(i, bits);
                    continue;
                }
            }
            long hostLower = lower;
            long hostUpper = upper;
            Masker masker = null;
            boolean unmasked = true;
            if (hasMask) {
                masker = this.maskers[i];
                int maskInt = ((IPAddressSegment)mask.getSegment(normalizedSegmentIndex)).getSegmentValue();
                if (masker == null) {
                    this.maskers[i] = masker = ParsedIPAddress.maskRange(lower, upper, maskInt, creator.getMaxValuePerSegment());
                }
                if (!masker.isSequential() && ((TranslatedResult)finalResult).maskException == null) {
                    ((TranslatedResult)finalResult).maskException = new IncompatibleAddressException(lower, upper, maskInt, "ipaddress.error.maskMismatch");
                }
                lower = (int)masker.getMaskedLower(lower, maskInt);
                upper = (int)masker.getMaskedUpper(upper, maskInt);
                unmasked = hostLower == lower && hostUpper == upper;
                maskedIsDifferent = maskedIsDifferent || !unmasked;
            }
            Integer segmentPrefixLength = ParsedIPAddress.getSegmentPrefixLength(normalizedSegmentIndex, 8, qualifier);
            if (doAddress) {
                if (maskedIsDifferent || segmentPrefixLength != null) {
                    hostSegments = (IPv4AddressSegment[])ParsedIPAddress.allocateSegments(hostSegments, (IPAddressSegment[])segments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv4SegmentCount, (int)normalizedSegmentIndex);
                    hostSegments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV4, (int)hostLower, (int)hostUpper, true, i, null, creator);
                }
                segments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV4, (int)lower, (int)upper, unmasked, i, segmentPrefixLength, creator);
            }
            if (doRangeBoundaries) {
                boolean isRange;
                boolean bl = isRange = lower != upper;
                if (!doAddress || isRange) {
                    if (doAddress) {
                        lowerSegments = (IPv4AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])lowerSegments, (IPAddressSegment[])segments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv4SegmentCount, (int)normalizedSegmentIndex);
                    }
                    lowerSegments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV4, (int)lower, (int)lower, false, i, segmentPrefixLength, creator);
                } else if (lowerSegments != null) {
                    lowerSegments[normalizedSegmentIndex] = segments[normalizedSegmentIndex];
                }
                if (withUpper) {
                    if (isRange) {
                        upperSegments = (IPv4AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])upperSegments, (IPAddressSegment[])lowerSegments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv4SegmentCount, (int)normalizedSegmentIndex);
                        upperSegments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV4, (int)upper, (int)upper, false, i, segmentPrefixLength, creator);
                    } else if (upperSegments != null) {
                        upperSegments[normalizedSegmentIndex] = lowerSegments[normalizedSegmentIndex];
                    }
                }
            }
            ++normalizedSegmentIndex;
            addrParseData.setBitLength(i, 8);
        }
        IPv4AddressNetwork.IPv4AddressCreator addressCreator = creator;
        Integer prefLength = ParsedIPAddress.getPrefixLength(qualifier);
        IPv4AddressSection hostResult = null;
        if (doAddress) {
            IPv4AddressSection result = (IPv4AddressSection)addressCreator.createPrefixedSectionInternal(segments, prefLength);
            ((TranslatedResult)finalResult).section = result;
            if (hostSegments != null) {
                hostResult = (IPv4AddressSection)addressCreator.createSectionInternal(hostSegments);
                ((TranslatedResult)finalResult).hostSection = hostResult;
                if (ParsedIPAddress.checkExpandedValues(hostResult, expandedStart, expandedEnd)) {
                    ((TranslatedResult)finalResult).joinHostException = new IncompatibleAddressException(addressString, "ipaddress.error.invalid.joined.ranges");
                }
            }
            if (ParsedIPAddress.checkExpandedValues(result, expandedStart, expandedEnd)) {
                ((TranslatedResult)finalResult).joinAddressException = new IncompatibleAddressException(addressString, "ipaddress.error.invalid.joined.ranges");
                if (hostResult == null) {
                    ((TranslatedResult)finalResult).joinHostException = ((TranslatedResult)finalResult).joinAddressException;
                }
            }
        }
        if (doRangeBoundaries) {
            boolean isPrefixSubnet;
            Integer prefixLength = ParsedIPAddress.getPrefixLength(qualifier);
            if (prefixLength != null) {
                AddressSegment[] lowerSegs;
                AddressSegment[] upperSegs;
                IPv4AddressNetwork network = this.getParameters().getIPv4Parameters().getNetwork();
                if (doAddress) {
                    upperSegs = segments;
                    lowerSegs = segments;
                } else {
                    lowerSegs = lowerSegments;
                    upperSegs = upperSegments == null ? lowerSegments : upperSegments;
                }
                isPrefixSubnet = ParsedAddressGrouping.isPrefixSubnet(arg_0 -> ParsedIPAddress.lambda$createIPv4Sections$14((IPv4AddressSegment[])lowerSegs, arg_0), arg_0 -> ParsedIPAddress.lambda$createIPv4Sections$15((IPv4AddressSegment[])upperSegs, arg_0), lowerSegs.length, 1, 8, 255, prefixLength, ((AddressNetwork)network).getPrefixConfiguration(), false);
                if (isPrefixSubnet) {
                    if (lowerSegments == null) {
                        lowerSegments = (IPv4AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])lowerSegments, (IPAddressSegment[])segments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv4SegmentCount, (int)ipv4SegmentCount);
                    }
                    if (upperSegments == null) {
                        upperSegments = (IPv4AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])upperSegments, (IPAddressSegment[])lowerSegments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv4SegmentCount, (int)ipv4SegmentCount);
                    }
                }
            } else {
                isPrefixSubnet = false;
            }
            if (lowerSegments != null) {
                ((TranslatedResult)finalResult).lowerSection = ((IPv4AddressSection)addressCreator.createPrefixedSectionInternal(lowerSegments, prefLength, true)).getLower();
            }
            if (upperSegments != null) {
                IPv4AddressSection section = (IPv4AddressSection)addressCreator.createPrefixedSectionInternal(upperSegments, prefLength);
                if (isPrefixSubnet) {
                    section = section.toPrefixBlock();
                }
                ((TranslatedResult)finalResult).upperSection = section.getUpper();
            }
        }
    }

    private void createIPv6Sections(boolean doAddress, boolean doRangeBoundaries, boolean withUpper) {
        AddressSegment[] lowerSegments;
        AddressSegment[] segments;
        ParsedHostIdentifierStringQualifier qualifier = this.getQualifier();
        IPAddress mask = this.getProviderMask();
        if (mask != null && mask.getBlockMaskPrefixLength(true) != null) {
            mask = null;
        }
        boolean hasMask = mask != null;
        AddressParseData addressParseData = this.getAddressParseData();
        int segmentCount = addressParseData.getSegmentCount();
        if (hasMask && this.maskers == null) {
            this.maskers = new Masker[segmentCount];
        }
        IPv6AddressNetwork.IPv6AddressCreator creator = this.getIPv6AddressCreator();
        int ipv6SegmentCount = 8;
        IPAddressSegment[] upperSegments = null;
        upperSegments = null;
        IPAddressSegment[] hostSegments = null;
        if (doAddress) {
            segments = creator.createSegmentArray(ipv6SegmentCount);
            lowerSegments = null;
        } else if (doRangeBoundaries) {
            lowerSegments = creator.createSegmentArray(ipv6SegmentCount);
            segments = null;
        } else {
            return;
        }
        TranslatedResult<IPv6Address, IPv6AddressSection> finalResult = this.values;
        if (this.values == null) {
            this.values = finalResult = new TranslatedResult<IPv6Address, IPv6AddressSection>(){
                private static final long serialVersionUID = 1L;

                @Override
                ParsedAddressCreator<IPv6Address, IPv6AddressSection, ?, ?> getCreator() {
                    return ParsedIPAddress.this.getIPv6AddressCreator();
                }
            };
        }
        boolean mixed = this.isProvidingMixedIPv6();
        int normalizedSegmentIndex = 0;
        int missingSegmentCount = (mixed ? 6 : ipv6SegmentCount) - segmentCount;
        boolean expandedSegments = missingSegmentCount <= 0;
        int expandedEnd = -1;
        int expandedStart = -1;
        CharSequence addressString = this.str;
        boolean maskedIsDifferent = false;
        for (int i = 0; i < segmentCount; ++i) {
            long lower = addressParseData.getValue(i, 2);
            long upper = addressParseData.getValue(i, 10);
            if (!expandedSegments) {
                boolean isLastSegment = i == segmentCount - 1;
                boolean isWildcard = addressParseData.isWildcard(i);
                boolean isCompressed = this.isCompressed(i);
                boolean bl = expandedSegments = isLastSegment || isCompressed;
                if (!expandedSegments && (expandedSegments = isWildcard)) {
                    for (int j = i + 1; j < segmentCount; ++j) {
                        if (!addressParseData.isWildcard(j) && !this.isCompressed(j)) continue;
                        expandedSegments = false;
                        break;
                    }
                }
                if (expandedSegments) {
                    boolean maskedIsRange;
                    long maskedLower;
                    long maskedUpper;
                    long maskedLowerHighBytes;
                    long maskedUpperHighBytes;
                    boolean hostIsRange;
                    long lowerHighBytes;
                    long upperHighBytes;
                    if (isCompressed) {
                        upperHighBytes = 0L;
                        lowerHighBytes = 0L;
                        upper = 0L;
                        lower = 0L;
                        hostIsRange = false;
                    } else if (isWildcard) {
                        if (missingSegmentCount > 3) {
                            upperHighBytes = -1L >>> (7 - missingSegmentCount << 4);
                            upper = -1L;
                        } else {
                            upperHighBytes = 0L;
                            upper = -1L >>> (3 - missingSegmentCount << 4);
                        }
                        lowerHighBytes = 0L;
                        lower = 0L;
                        hostIsRange = true;
                    } else {
                        if (missingSegmentCount > 3) {
                            lowerHighBytes = addressParseData.getValue(i, 4);
                            upperHighBytes = addressParseData.getValue(i, 12);
                            hostIsRange = lower != upper || lowerHighBytes != upperHighBytes;
                        } else {
                            upperHighBytes = 0L;
                            lowerHighBytes = 0L;
                            hostIsRange = lower != upper;
                        }
                        expandedStart = i;
                        expandedEnd = i + missingSegmentCount;
                    }
                    int bits = 16 * (missingSegmentCount + 1);
                    if (hasMask) {
                        if (isCompressed) {
                            this.maskers[i] = DEFAULT_MASKER;
                            maskedUpperHighBytes = 0L;
                            maskedLowerHighBytes = 0L;
                            maskedUpper = 0L;
                            maskedLower = 0L;
                            maskedIsRange = false;
                        } else {
                            Masker masker;
                            int bitsPerSegment = 16;
                            long maskVal = 0L;
                            if (missingSegmentCount >= 4) {
                                int k;
                                masker = (ExtendedMasker)this.maskers[i];
                                long extendedMaskVal = 0L;
                                int extendedCount = missingSegmentCount - 3;
                                for (k = 0; k < extendedCount; ++k) {
                                    extendedMaskVal = extendedMaskVal << bitsPerSegment | (long)((IPAddressSegment)mask.getSegment(normalizedSegmentIndex + k)).getSegmentValue();
                                }
                                for (k = extendedCount; k <= missingSegmentCount; ++k) {
                                    maskVal = maskVal << bitsPerSegment | (long)((IPAddressSegment)mask.getSegment(normalizedSegmentIndex + k)).getSegmentValue();
                                }
                                if (masker == null) {
                                    long extendedMaxValue = bits == 64 ? -1L : -1L << bits - 64 ^ 0xFFFFFFFFFFFFFFFFL;
                                    this.maskers[i] = masker = ParsedIPAddress.maskExtendedRange(lower, lowerHighBytes, upper, upperHighBytes, maskVal, extendedMaskVal, -1L, extendedMaxValue);
                                }
                                if (!masker.isSequential() && ((TranslatedResult)finalResult).maskException == null) {
                                    int byteCount = (missingSegmentCount + 1) * 2;
                                    ((TranslatedResult)finalResult).maskException = new IncompatibleAddressException(new BigInteger(1, ParsedIPAddress.toBytesSizeAdjusted(lower, lowerHighBytes, byteCount)).toString(), new BigInteger(1, ParsedIPAddress.toBytesSizeAdjusted(upper, upperHighBytes, byteCount)).toString(), new BigInteger(1, ParsedIPAddress.toBytesSizeAdjusted(maskVal, extendedMaskVal, byteCount)).toString(), "ipaddress.error.maskMismatch");
                                }
                                maskedLowerHighBytes = ((ExtendedMasker)masker).getExtendedMaskedLower(lowerHighBytes, extendedMaskVal);
                                maskedUpperHighBytes = ((ExtendedMasker)masker).getExtendedMaskedUpper(upperHighBytes, extendedMaskVal);
                                maskedLower = masker.getMaskedLower(lower, maskVal);
                                maskedIsRange = maskedLower != (maskedUpper = masker.getMaskedUpper(upper, maskVal)) || maskedLowerHighBytes != maskedUpperHighBytes;
                                maskedIsDifferent = maskedIsDifferent || maskedLower != lower || maskedUpper != upper || maskedLowerHighBytes != lowerHighBytes || maskedUpperHighBytes != upperHighBytes;
                            } else {
                                masker = this.maskers[i];
                                for (int k = 0; k <= missingSegmentCount; ++k) {
                                    maskVal = maskVal << bitsPerSegment | (long)((IPAddressSegment)mask.getSegment(normalizedSegmentIndex + k)).getSegmentValue();
                                }
                                if (masker == null) {
                                    long maxValue = bits == 64 ? -1L : -1L << bits ^ 0xFFFFFFFFFFFFFFFFL;
                                    this.maskers[i] = masker = ParsedIPAddress.maskRange(lower, upper, maskVal, maxValue);
                                }
                                if (!masker.isSequential() && ((TranslatedResult)finalResult).maskException == null) {
                                    ((TranslatedResult)finalResult).maskException = new IncompatibleAddressException(lower, upper, maskVal, "ipaddress.error.maskMismatch");
                                }
                                maskedUpperHighBytes = 0L;
                                maskedLowerHighBytes = 0L;
                                maskedLower = masker.getMaskedLower(lower, maskVal);
                                maskedIsRange = maskedLower != (maskedUpper = masker.getMaskedUpper(upper, maskVal));
                                maskedIsDifferent = maskedIsDifferent || maskedLower != lower || maskedUpper != upper;
                            }
                        }
                    } else {
                        maskedLowerHighBytes = lowerHighBytes;
                        maskedUpperHighBytes = upperHighBytes;
                        maskedLower = lower;
                        maskedUpper = upper;
                        maskedIsRange = hostIsRange;
                    }
                    int shift = bits;
                    for (int count = missingSegmentCount; count >= 0; --count) {
                        int hostSegLower;
                        int hostSegUpper;
                        int maskedSegLower;
                        int maskedSegUpper;
                        Integer currentPrefix = ParsedIPAddress.getSegmentPrefixLength(normalizedSegmentIndex, 16, qualifier);
                        if (isCompressed) {
                            maskedSegUpper = 0;
                            maskedSegLower = 0;
                            hostSegUpper = 0;
                            hostSegLower = 0;
                        } else {
                            shift -= 16;
                            int segmentBitsMask = 65535;
                            if (count >= 4) {
                                int shorterShift = shift - 64;
                                hostSegLower = (int)(lowerHighBytes >>> shorterShift) & segmentBitsMask;
                                int n = hostSegUpper = hostIsRange ? (int)(upperHighBytes >>> shorterShift) & segmentBitsMask : hostSegLower;
                                if (hasMask) {
                                    maskedSegLower = (int)(maskedLowerHighBytes >>> shorterShift) & segmentBitsMask;
                                    maskedSegUpper = maskedIsRange ? (int)(maskedUpperHighBytes >>> shorterShift) & segmentBitsMask : maskedSegLower;
                                } else {
                                    maskedSegLower = hostSegLower;
                                    maskedSegUpper = hostSegUpper;
                                }
                            } else {
                                hostSegLower = (int)(lower >>> shift) & segmentBitsMask;
                                int n = hostSegUpper = hostIsRange ? (int)(upper >>> shift) & segmentBitsMask : hostSegLower;
                                if (hasMask) {
                                    maskedSegLower = (int)(maskedLower >>> shift) & segmentBitsMask;
                                    maskedSegUpper = maskedIsRange ? (int)(maskedUpper >>> shift) & segmentBitsMask : maskedSegLower;
                                } else {
                                    maskedSegLower = hostSegLower;
                                    maskedSegUpper = hostSegUpper;
                                }
                            }
                        }
                        if (doAddress) {
                            if (maskedIsDifferent || currentPrefix != null) {
                                hostSegments = (IPv6AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])hostSegments, (IPAddressSegment[])segments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv6SegmentCount, (int)normalizedSegmentIndex);
                                hostSegments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV6, hostSegLower, hostSegUpper, false, i, null, creator);
                            }
                            segments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV6, maskedSegLower, maskedSegUpper, false, i, currentPrefix, creator);
                        }
                        if (doRangeBoundaries) {
                            boolean isSegRange;
                            boolean bl2 = isSegRange = maskedSegLower != maskedSegUpper;
                            if (!doAddress || isSegRange) {
                                if (doAddress) {
                                    lowerSegments = (IPv6AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])lowerSegments, (IPAddressSegment[])segments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv6SegmentCount, (int)normalizedSegmentIndex);
                                }
                                lowerSegments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV6, maskedSegLower, maskedSegLower, false, i, currentPrefix, creator);
                            } else if (lowerSegments != null) {
                                lowerSegments[normalizedSegmentIndex] = segments[normalizedSegmentIndex];
                            }
                            if (withUpper) {
                                if (isSegRange) {
                                    upperSegments = (IPv6AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])upperSegments, (IPAddressSegment[])lowerSegments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv6SegmentCount, (int)normalizedSegmentIndex);
                                    upperSegments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV6, maskedSegUpper, maskedSegUpper, false, i, currentPrefix, creator);
                                } else if (upperSegments != null) {
                                    upperSegments[normalizedSegmentIndex] = lowerSegments[normalizedSegmentIndex];
                                }
                            }
                        }
                        ++normalizedSegmentIndex;
                    }
                    addressParseData.setBitLength(i, bits);
                    continue;
                }
            }
            long hostLower = lower;
            long hostUpper = upper;
            Masker masker = null;
            boolean unmasked = true;
            if (hasMask) {
                masker = this.maskers[i];
                int maskInt = ((IPAddressSegment)mask.getSegment(normalizedSegmentIndex)).getSegmentValue();
                if (masker == null) {
                    this.maskers[i] = masker = ParsedIPAddress.maskRange(lower, upper, maskInt, creator.getMaxValuePerSegment());
                }
                if (!masker.isSequential() && ((TranslatedResult)finalResult).maskException == null) {
                    ((TranslatedResult)finalResult).maskException = new IncompatibleAddressException(lower, upper, maskInt, "ipaddress.error.maskMismatch");
                }
                lower = (int)masker.getMaskedLower(lower, maskInt);
                upper = (int)masker.getMaskedUpper(upper, maskInt);
                unmasked = hostLower == lower && hostUpper == upper;
                maskedIsDifferent = maskedIsDifferent || !unmasked;
            }
            Integer segmentPrefixLength = ParsedIPAddress.getSegmentPrefixLength(normalizedSegmentIndex, 16, qualifier);
            if (doAddress) {
                if (maskedIsDifferent || segmentPrefixLength != null) {
                    hostSegments = (IPv6AddressSegment[])ParsedIPAddress.allocateSegments(hostSegments, (IPAddressSegment[])segments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv6SegmentCount, (int)normalizedSegmentIndex);
                    hostSegments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV6, (int)hostLower, (int)hostUpper, true, i, null, creator);
                }
                segments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV6, (int)lower, (int)upper, unmasked, i, segmentPrefixLength, creator);
            }
            if (doRangeBoundaries) {
                boolean isRange;
                boolean bl = isRange = lower != upper;
                if (!doAddress || isRange) {
                    if (doAddress) {
                        lowerSegments = (IPv6AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])lowerSegments, (IPAddressSegment[])segments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv6SegmentCount, (int)normalizedSegmentIndex);
                    }
                    lowerSegments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV6, (int)lower, (int)lower, false, i, segmentPrefixLength, creator);
                } else if (lowerSegments != null) {
                    lowerSegments[normalizedSegmentIndex] = segments[normalizedSegmentIndex];
                }
                if (withUpper) {
                    if (isRange) {
                        upperSegments = (IPv6AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])upperSegments, (IPAddressSegment[])lowerSegments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv6SegmentCount, (int)normalizedSegmentIndex);
                        upperSegments[normalizedSegmentIndex] = this.createSegment(addressString, IPAddress.IPVersion.IPV6, (int)upper, (int)upper, false, i, segmentPrefixLength, creator);
                    } else if (upperSegments != null) {
                        upperSegments[normalizedSegmentIndex] = lowerSegments[normalizedSegmentIndex];
                    }
                }
            }
            ++normalizedSegmentIndex;
            addressParseData.setBitLength(i, 16);
        }
        IPv6AddressNetwork.IPv6AddressCreator addressCreator = creator;
        Integer prefLength = ParsedIPAddress.getPrefixLength(qualifier);
        if (mixed) {
            IPv4AddressSeqRange ipv4Range = (IPv4AddressSeqRange)this.mixedParsedAddress.getProviderSeqRange();
            if (hasMask && this.mixedMaskers == null) {
                this.mixedMaskers = new Masker[4];
            }
            for (int n = 0; n < 2; ++n) {
                boolean isRange;
                int m4 = n << 1;
                Integer segmentPrefixLength = ParsedIPAddress.getSegmentPrefixLength(normalizedSegmentIndex, 16, qualifier);
                IPv4AddressSegment oneLow = ipv4Range.getLower().getSegment(m4);
                int o = m4 + 1;
                IPv4AddressSegment twoLow = ipv4Range.getLower().getSegment(o);
                IPv4AddressSegment oneUp = ipv4Range.getUpper().getSegment(m4);
                IPv4AddressSegment twoUp = ipv4Range.getUpper().getSegment(o);
                int oneLower = oneLow.getSegmentValue();
                int twoLower = twoLow.getSegmentValue();
                int oneUpper = oneUp.getSegmentValue();
                int twoUpper = twoUp.getSegmentValue();
                int originalOneLower = oneLower;
                int originalTwoLower = twoLower;
                int originalOneUpper = oneUpper;
                int originalTwoUpper = twoUpper;
                if (hasMask) {
                    int maskInt = ((IPAddressSegment)mask.getSegment(normalizedSegmentIndex)).getSegmentValue();
                    int shift = 8;
                    int shiftedMask = maskInt >> shift;
                    Masker masker = this.mixedMaskers[m4];
                    if (masker == null) {
                        this.mixedMaskers[m4] = masker = ParsedIPAddress.maskRange(oneLower, oneUpper, shiftedMask, 255L);
                    }
                    if (!masker.isSequential() && ((TranslatedResult)finalResult).maskException == null) {
                        ((TranslatedResult)finalResult).maskException = new IncompatibleAddressException(oneLower, oneUpper, shiftedMask, "ipaddress.error.maskMismatch");
                    }
                    oneLower = (int)masker.getMaskedLower(oneLower, shiftedMask);
                    oneUpper = (int)masker.getMaskedUpper(oneUpper, shiftedMask);
                    masker = this.mixedMaskers[m4 + 1];
                    if (masker == null) {
                        this.mixedMaskers[m4 + 1] = masker = ParsedIPAddress.maskRange(twoLower, twoUpper, maskInt, 255L);
                    }
                    if (!masker.isSequential() && ((TranslatedResult)finalResult).maskException == null) {
                        ((TranslatedResult)finalResult).maskException = new IncompatibleAddressException(twoLower, twoUpper, maskInt, "ipaddress.error.maskMismatch");
                    }
                    twoLower = (int)masker.getMaskedLower(twoLower, maskInt);
                    twoUpper = (int)masker.getMaskedUpper(twoUpper, maskInt);
                    maskedIsDifferent = maskedIsDifferent || oneLower != originalOneLower || oneUpper != originalOneUpper || twoLower != originalTwoLower || twoUpper != originalTwoUpper;
                }
                boolean bl = isRange = oneLower != oneUpper || twoLower != twoUpper;
                if (doAddress) {
                    boolean doHostSegment;
                    boolean bl3 = doHostSegment = maskedIsDifferent || segmentPrefixLength != null;
                    if (doHostSegment) {
                        hostSegments = (IPv6AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])hostSegments, (IPAddressSegment[])segments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv6SegmentCount, (int)normalizedSegmentIndex);
                    }
                    if (!isRange) {
                        if (doHostSegment) {
                            hostSegments[normalizedSegmentIndex] = this.createIPv6Segment(originalOneLower, originalTwoLower, null, creator);
                        }
                        segments[normalizedSegmentIndex] = this.createIPv6Segment(oneLower, twoLower, segmentPrefixLength, creator);
                    } else {
                        if (doHostSegment) {
                            hostSegments[normalizedSegmentIndex] = ParsedIPAddress.createIPv6RangeSegment(finalResult, ipv4Range, originalOneLower, originalOneUpper, originalTwoLower, originalTwoUpper, null, creator);
                        }
                        segments[normalizedSegmentIndex] = ParsedIPAddress.createIPv6RangeSegment(finalResult, ipv4Range, oneLower, oneUpper, twoLower, twoUpper, segmentPrefixLength, creator);
                    }
                }
                if (doRangeBoundaries) {
                    if (!doAddress || isRange) {
                        if (doAddress) {
                            lowerSegments = (IPv6AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])lowerSegments, (IPAddressSegment[])segments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv6SegmentCount, (int)normalizedSegmentIndex);
                        }
                        lowerSegments[normalizedSegmentIndex] = this.createIPv6Segment(oneLower, twoLower, segmentPrefixLength, creator);
                    } else if (lowerSegments != null) {
                        lowerSegments[normalizedSegmentIndex] = segments[normalizedSegmentIndex];
                    }
                    if (withUpper) {
                        if (isRange) {
                            upperSegments = (IPv6AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])upperSegments, (IPAddressSegment[])lowerSegments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv6SegmentCount, (int)normalizedSegmentIndex);
                            upperSegments[normalizedSegmentIndex] = this.createIPv6Segment(oneUpper, twoUpper, segmentPrefixLength, creator);
                        } else if (upperSegments != null) {
                            upperSegments[normalizedSegmentIndex] = lowerSegments[normalizedSegmentIndex];
                        }
                    }
                }
                ++normalizedSegmentIndex;
            }
        }
        IPv6AddressSection hostResult = null;
        if (doAddress) {
            if (hostSegments != null) {
                hostResult = (IPv6AddressSection)addressCreator.createSectionInternal(hostSegments);
                ((TranslatedResult)finalResult).hostSection = hostResult;
                if (ParsedIPAddress.checkExpandedValues(hostResult, expandedStart, expandedEnd)) {
                    ((TranslatedResult)finalResult).joinHostException = new IncompatibleAddressException(addressString, "ipaddress.error.invalid.joined.ranges");
                }
            }
            IPv6AddressSection result = (IPv6AddressSection)addressCreator.createPrefixedSectionInternal(segments, prefLength);
            ((TranslatedResult)finalResult).section = result;
            if (ParsedIPAddress.checkExpandedValues(result, expandedStart, expandedEnd)) {
                ((TranslatedResult)finalResult).joinAddressException = new IncompatibleAddressException(addressString, "ipaddress.error.invalid.joined.ranges");
                if (hostResult == null) {
                    ((TranslatedResult)finalResult).joinHostException = ((TranslatedResult)finalResult).joinAddressException;
                }
            }
        }
        if (doRangeBoundaries) {
            boolean isPrefixSubnet;
            Integer prefixLength = ParsedIPAddress.getPrefixLength(qualifier);
            if (prefixLength != null) {
                AddressSegment[] lowerSegs;
                AddressSegment[] upperSegs;
                IPv6AddressNetwork network = this.getParameters().getIPv6Parameters().getNetwork();
                if (doAddress) {
                    upperSegs = segments;
                    lowerSegs = segments;
                } else {
                    lowerSegs = lowerSegments;
                    upperSegs = upperSegments == null ? lowerSegments : upperSegments;
                }
                isPrefixSubnet = ParsedAddressGrouping.isPrefixSubnet(arg_0 -> ParsedIPAddress.lambda$createIPv6Sections$16((IPv6AddressSegment[])lowerSegs, arg_0), arg_0 -> ParsedIPAddress.lambda$createIPv6Sections$17((IPv6AddressSegment[])upperSegs, arg_0), lowerSegs.length, 2, 16, 65535, prefixLength, ((AddressNetwork)network).getPrefixConfiguration(), false);
                if (isPrefixSubnet) {
                    if (lowerSegments == null) {
                        lowerSegments = (IPv6AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])lowerSegments, (IPAddressSegment[])segments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv6SegmentCount, (int)ipv6SegmentCount);
                    }
                    if (upperSegments == null) {
                        upperSegments = (IPv6AddressSegment[])ParsedIPAddress.allocateSegments((IPAddressSegment[])upperSegments, (IPAddressSegment[])lowerSegments, (AddressNetwork.AddressSegmentCreator)creator, (int)ipv6SegmentCount, (int)ipv6SegmentCount);
                    }
                }
            } else {
                isPrefixSubnet = false;
            }
            if (lowerSegments != null) {
                ((TranslatedResult)finalResult).lowerSection = ((IPv6AddressSection)addressCreator.createPrefixedSectionInternal(lowerSegments, prefLength, true)).getLower();
            }
            if (upperSegments != null) {
                IPv6AddressSection section = (IPv6AddressSection)addressCreator.createPrefixedSectionInternal(upperSegments, prefLength);
                if (isPrefixSubnet) {
                    section = section.toPrefixBlock();
                }
                ((TranslatedResult)finalResult).upperSection = section.getUpper();
            }
        }
    }

    private static boolean checkExpandedValues(IPAddressSection section, int start2, int end) {
        if (section != null && start2 < end) {
            IPAddressSegment seg = section.getSegment(start2);
            boolean lastWasRange = seg.isMultiple();
            do {
                seg = section.getSegment(++start2);
                if (lastWasRange) {
                    if (seg.isFullRange()) continue;
                    return true;
                }
                lastWasRange = seg.isMultiple();
            } while (start2 < end);
        }
        return false;
    }

    private <S extends IPAddressSegment> S createSegment(CharSequence addressString, IPAddress.IPVersion version, int val, int upperVal, boolean useFlags, int parsedSegIndex, Integer segmentPrefixLength, ParsedAddressCreator<?, ?, ?, S> creator) {
        AddressParseData parseData = this.getAddressParseData();
        if (val != upperVal) {
            return ParsedIPAddress.createRangeSeg(addressString, version, val, upperVal, useFlags, parseData, parsedSegIndex, segmentPrefixLength, creator);
        }
        IPAddressSegment result = !useFlags ? (IPAddressSegment)creator.createSegment(val, val, segmentPrefixLength) : (IPAddressSegment)creator.createSegmentInternal(val, segmentPrefixLength, addressString, val, parseData.getFlag(parsedSegIndex, 262144), parseData.getIndex(parsedSegIndex, 6), parseData.getIndex(parsedSegIndex, 7));
        return (S)result;
    }

    private IPv6AddressSegment createIPv6Segment(int value1, int value2, Integer segmentPrefixLength, IPv6AddressNetwork.IPv6AddressCreator creator) {
        int value = value1 << 8 | value2;
        IPv6AddressSegment result = creator.createSegment(value, segmentPrefixLength);
        return result;
    }

    private static IPv6AddressSegment createIPv6RangeSegment(TranslatedResult<?, ?> finalResult, AddressItem item, int upperRangeLower, int upperRangeUpper, int lowerRangeLower, int lowerRangeUpper, Integer segmentPrefixLength, IPv6AddressNetwork.IPv6AddressCreator creator) {
        int shift = 8;
        if (upperRangeLower != upperRangeUpper) {
            if (segmentPrefixLength != null && creator.getNetwork().getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                if (segmentPrefixLength > shift) {
                    int lowerPrefixLength = segmentPrefixLength - shift;
                    int fullMask = ~(-1 << shift);
                    int networkMask = fullMask & fullMask << shift - lowerPrefixLength;
                    int hostMask = ~networkMask & fullMask;
                    if (((TranslatedResult)finalResult).mixedException == null && (lowerRangeLower &= networkMask) != 0 || (lowerRangeUpper |= hostMask) != 255) {
                        ((TranslatedResult)finalResult).mixedException = new IncompatibleAddressException(item, "ipaddress.error.invalidMixedRange");
                    }
                } else {
                    lowerRangeLower = 0;
                    lowerRangeUpper = 255;
                }
            } else if (((TranslatedResult)finalResult).mixedException == null && lowerRangeLower != 0 || lowerRangeUpper != 255) {
                ((TranslatedResult)finalResult).mixedException = new IncompatibleAddressException(item, "ipaddress.error.invalidMixedRange");
            }
        }
        return creator.createSegment(upperRangeLower << shift | lowerRangeLower, upperRangeUpper << shift | lowerRangeUpper, segmentPrefixLength);
    }

    private static <S extends IPAddressSegment> S createRangeSeg(CharSequence addressString, IPAddress.IPVersion version, int stringLower, int stringUpper, boolean useFlags, AddressParseData parseData, int parsedSegIndex, Integer segmentPrefixLength, ParsedAddressCreator<?, ?, ?, S> creator) {
        int lower = stringLower;
        int upper = stringUpper;
        IPAddressSegment result = !useFlags ? (IPAddressSegment)creator.createSegment(lower, upper, segmentPrefixLength) : (IPAddressSegment)creator.createRangeSegmentInternal(lower, upper, segmentPrefixLength, addressString, stringLower, stringUpper, parseData.getFlag(parsedSegIndex, 262144), parseData.getFlag(parsedSegIndex, 524288), parseData.getIndex(parsedSegIndex, 6), parseData.getIndex(parsedSegIndex, 7), parseData.getIndex(parsedSegIndex, 15));
        return (S)result;
    }

    private static <S extends IPAddressSegment> S createFullRangeSegment(IPAddress.IPVersion version, int stringLower, int stringUpper, int parsedSegIndex, Integer segmentPrefixLength, Integer mask, ParsedAddressCreator<?, ?, ?, S> creator) {
        boolean hasMask;
        boolean bl = hasMask = mask != null;
        if (hasMask) {
            int maskInt = mask;
            Masker masker = ParsedIPAddress.maskRange(stringLower, stringUpper, maskInt, creator.getMaxValuePerSegment());
            if (!masker.isSequential()) {
                throw new IncompatibleAddressException(stringLower, stringUpper, maskInt, "ipaddress.error.maskMismatch");
            }
            stringLower = (int)masker.getMaskedLower(stringLower, maskInt);
            stringUpper = (int)masker.getMaskedUpper(stringUpper, maskInt);
        }
        S result = ParsedIPAddress.createRangeSeg(null, version, stringLower, stringUpper, false, null, parsedSegIndex, segmentPrefixLength, creator);
        return result;
    }

    static IPAddress createAllAddress(IPAddress.IPVersion version, ParsedHostIdentifierStringQualifier qualifier, HostIdentifierString originator, IPAddressStringParameters options) {
        int segmentCount = IPAddress.getSegmentCount(version);
        IPAddress mask = qualifier.getMaskLower();
        if (mask != null && mask.getBlockMaskPrefixLength(true) != null) {
            mask = null;
        }
        boolean hasMask = mask != null;
        Integer prefLength = ParsedIPAddress.getPrefixLength(qualifier);
        if (version.isIPv4()) {
            IPv4AddressNetwork.IPv4AddressCreator creator = options.getIPv4Parameters().getNetwork().getAddressCreator();
            AddressSegment[] segments = (IPv4AddressSegment[])creator.createSegmentArray(segmentCount);
            for (int i = 0; i < segmentCount; ++i) {
                Integer segmentMask = hasMask ? ParsedIPAddress.cacheSegmentMask(((IPAddressSegment)mask.getSegment(i)).getSegmentValue()) : null;
                segments[i] = ParsedIPAddress.createFullRangeSegment(version, 0, 255, i, ParsedIPAddress.getSegmentPrefixLength(i, version, qualifier), segmentMask, creator);
            }
            return (IPAddress)creator.createAddressInternal(segments, originator, prefLength);
        }
        IPv6AddressNetwork.IPv6AddressCreator creator = options.getIPv6Parameters().getNetwork().getAddressCreator();
        AddressSegment[] segments = (IPv6AddressSegment[])creator.createSegmentArray(segmentCount);
        for (int i = 0; i < segmentCount; ++i) {
            Integer segmentMask = hasMask ? ParsedIPAddress.cacheSegmentMask(((IPAddressSegment)mask.getSegment(i)).getSegmentValue()) : null;
            segments[i] = ParsedIPAddress.createFullRangeSegment(version, 0, 65535, i, ParsedIPAddress.getSegmentPrefixLength(i, version, qualifier), segmentMask, creator);
        }
        return (IPAddress)creator.createAddressInternal(segments, qualifier.getZone(), originator, prefLength);
    }

    private static Integer getPrefixLength(ParsedHostIdentifierStringQualifier qualifier) {
        return qualifier.getEquivalentPrefixLength();
    }

    private static Integer getSegmentPrefixLength(int segmentIndex, int bitsPerSegment, ParsedHostIdentifierStringQualifier qualifier) {
        Integer bits = ParsedIPAddress.getPrefixLength(qualifier);
        return ParsedAddressGrouping.getSegmentPrefixLength(bitsPerSegment, bits, segmentIndex);
    }

    private static Integer getSegmentPrefixLength(int segmentIndex, IPAddress.IPVersion version, ParsedHostIdentifierStringQualifier qualifier) {
        return ParsedIPAddress.getSegmentPrefixLength(segmentIndex, IPAddressSection.bitsPerSegment(version), qualifier);
    }

    private static Integer cacheSegmentMask(int i) {
        return ParsedAddressGrouping.cache(i);
    }

    private static /* synthetic */ int lambda$createIPv6Sections$17(IPv6AddressSegment[] upperSegs, int segmentIndex) {
        return upperSegs[segmentIndex].getUpperSegmentValue();
    }

    private static /* synthetic */ int lambda$createIPv6Sections$16(IPv6AddressSegment[] lowerSegs, int segmentIndex) {
        return lowerSegs[segmentIndex].getSegmentValue();
    }

    private static /* synthetic */ int lambda$createIPv4Sections$15(IPv4AddressSegment[] upperSegs, int segmentIndex) {
        return upperSegs[segmentIndex].getUpperSegmentValue();
    }

    private static /* synthetic */ int lambda$createIPv4Sections$14(IPv4AddressSegment[] lowerSegs, int segmentIndex) {
        return lowerSegs[segmentIndex].getSegmentValue();
    }

    public static class WrappedMasker
    extends ExtendedMasker {
        private static final long serialVersionUID = 1L;
        private final Masker masker;

        WrappedMasker(Masker masker) {
            super(masker.isSequential());
            this.masker = masker;
        }

        @Override
        public long getMaskedLower(long value, long maskValue) {
            return this.masker.getMaskedLower(value, maskValue);
        }

        @Override
        public long getMaskedUpper(long upperValue, long maskValue) {
            return this.masker.getMaskedUpper(upperValue, maskValue);
        }
    }

    public static class ExtendedSpecificValueMasker
    extends ExtendedMasker {
        private static final long serialVersionUID = 1L;
        private final long extendedLower;
        private final long lower;
        private final long extendedUpper;
        private final long upper;

        public ExtendedSpecificValueMasker(long extendedLower, long lower, long extendedUpper, long upper) {
            super(false);
            this.lower = lower;
            this.upper = upper;
            this.extendedLower = extendedLower;
            this.extendedUpper = extendedUpper;
        }

        @Override
        public long getMaskedLower(long value, long maskValue) {
            return super.getMaskedLower(this.lower, maskValue);
        }

        @Override
        public long getMaskedUpper(long upperValue, long maskValue) {
            return super.getMaskedUpper(this.upper, maskValue);
        }

        @Override
        public long getExtendedMaskedLower(long extendedValue, long extendedMaskValue) {
            return super.getExtendedMaskedLower(this.extendedLower, extendedMaskValue);
        }

        @Override
        public long getExtendedMaskedUpper(long extendedUpperValue, long extendedMaskValue) {
            return super.getExtendedMaskedUpper(this.extendedUpper, extendedMaskValue);
        }
    }

    public static class ExtendedFullRangeMasker
    extends ExtendedMasker {
        private static final long serialVersionUID = 1L;
        private final long upperMask;
        private final long extendedUpperMask;

        ExtendedFullRangeMasker(int fullRangeBit, boolean isSequential) {
            super(isSequential);
            if (fullRangeBit >= 64) {
                this.extendedUpperMask = 0L;
                this.upperMask = -1L >>> fullRangeBit - 64;
            } else {
                this.extendedUpperMask = -1L >>> fullRangeBit;
                this.upperMask = -1L;
            }
        }

        @Override
        public long getMaskedLower(long value, long maskValue) {
            return super.getMaskedLower(value & (this.upperMask ^ 0xFFFFFFFFFFFFFFFFL), maskValue);
        }

        @Override
        public long getMaskedUpper(long upperValue, long maskValue) {
            return super.getMaskedUpper(upperValue | this.upperMask, maskValue);
        }

        @Override
        public long getExtendedMaskedLower(long extendedValue, long extendedMaskValue) {
            return super.getExtendedMaskedLower(extendedValue & (this.extendedUpperMask ^ 0xFFFFFFFFFFFFFFFFL), extendedMaskValue);
        }

        @Override
        public long getExtendedMaskedUpper(long extendedUpperValue, long extendedMaskValue) {
            return super.getMaskedUpper(extendedUpperValue | this.extendedUpperMask, extendedMaskValue);
        }
    }

    public static class ExtendedMasker
    extends Masker {
        private static final long serialVersionUID = 1L;

        public ExtendedMasker(boolean isSequential) {
            super(isSequential);
        }

        @Deprecated
        public long getExtendedLowerMasked(long extendedValue, long extendedMaskValue) {
            return this.getExtendedMaskedLower(extendedValue, extendedMaskValue);
        }

        @Deprecated
        public long getExtendedUpperMasked(long extendedUpperValue, long extendedMaskValue) {
            return this.getExtendedMaskedUpper(extendedUpperValue, extendedMaskValue);
        }

        public long getExtendedMaskedLower(long extendedValue, long extendedMaskValue) {
            return extendedValue & extendedMaskValue;
        }

        public long getExtendedMaskedUpper(long extendedUpperValue, long extendedMaskValue) {
            return extendedUpperValue & extendedMaskValue;
        }
    }

    public static class SpecificValueMasker
    extends Masker {
        private static final long serialVersionUID = 1L;
        private final long lower;
        private final long upper;

        public SpecificValueMasker(long lower, long upper) {
            super(false);
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public long getMaskedLower(long value, long maskValue) {
            return super.getMaskedLower(this.lower, maskValue);
        }

        @Override
        public long getMaskedUpper(long upperValue, long maskValue) {
            return super.getMaskedUpper(this.upper, maskValue);
        }
    }

    public static class FullRangeMasker
    extends Masker {
        private static final long serialVersionUID = 1L;
        private final long upperMask;
        public final int fullRangeBit;

        public FullRangeMasker(int fullRangeBit, boolean isSequential) {
            super(isSequential);
            this.fullRangeBit = fullRangeBit;
            this.upperMask = -1L >>> fullRangeBit;
        }

        @Override
        public long getMaskedLower(long value, long maskValue) {
            return super.getMaskedLower(value & (this.upperMask ^ 0xFFFFFFFFFFFFFFFFL), maskValue);
        }

        @Override
        public long getMaskedUpper(long upperValue, long maskValue) {
            return super.getMaskedUpper(upperValue | this.upperMask, maskValue);
        }
    }

    public static abstract class Masker
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final boolean isSequential;

        public Masker(boolean isSequential) {
            this.isSequential = isSequential;
        }

        public long getMaskedLower(long value, long maskValue) {
            return value & maskValue;
        }

        public long getMaskedUpper(long upperValue, long maskValue) {
            return upperValue & maskValue;
        }

        public boolean isSequential() {
            return this.isSequential;
        }
    }

    public static class SpecificValueBitwiseOrer
    extends BitwiseOrer {
        private static final long serialVersionUID = 1L;
        private final long lower;
        private final long upper;

        public SpecificValueBitwiseOrer(long lower, long upper) {
            super(false);
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public long getOredLower(long value, long maskValue) {
            return super.getOredLower(this.lower, maskValue);
        }

        @Override
        public long getOredUpper(long upperValue, long maskValue) {
            return super.getOredUpper(this.upper, maskValue);
        }
    }

    public static class FullRangeBitwiseOrer
    extends BitwiseOrer {
        private static final long serialVersionUID = 1L;
        private final long upperMask;
        public final int fullRangeBit;

        public FullRangeBitwiseOrer(int fullRangeBit, boolean isSequential) {
            super(isSequential);
            this.fullRangeBit = fullRangeBit;
            this.upperMask = -1L >>> fullRangeBit;
        }

        @Override
        public long getOredLower(long value, long maskValue) {
            return super.getOredLower(value & (this.upperMask ^ 0xFFFFFFFFFFFFFFFFL), maskValue);
        }

        @Override
        public long getOredUpper(long upperValue, long maskValue) {
            return super.getOredUpper(upperValue | this.upperMask, maskValue);
        }
    }

    public static class BitwiseOrer
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final boolean isSequential;

        public BitwiseOrer(boolean isSequential) {
            this.isSequential = isSequential;
        }

        public long getOredLower(long value, long maskValue) {
            return value | maskValue;
        }

        public long getOredUpper(long upperValue, long maskValue) {
            return upperValue | maskValue;
        }

        public boolean isSequential() {
            return this.isSequential;
        }
    }

    abstract class TranslatedResult<T extends IPAddress, R extends IPAddressSection>
    extends CachedIPAddresses<T> {
        private static final long serialVersionUID = 4L;
        private R section;
        private R hostSection;
        private R lowerSection;
        private R upperSection;
        private IncompatibleAddressException joinHostException;
        private IncompatibleAddressException joinAddressException;
        private IncompatibleAddressException mixedException;
        private IncompatibleAddressException maskException;
        private IPAddressSeqRange range;
        private T rangeLower;
        private T rangeUpper;
        private IPAddressDivisionSeries series;

        TranslatedResult() {
        }

        abstract ParsedAddressCreator<T, R, ?, ?> getCreator();

        @Override
        public T getAddress() {
            if (this.address == null) {
                this.address = this.range == null ? (IPAddress)this.getCreator().createAddressInternal(this.section, this.getZone(), ParsedIPAddress.this.originator) : (IPAddress)this.getCreator().createAddressInternal(this.section, this.getZone(), ParsedIPAddress.this.originator, this.rangeLower, this.rangeUpper);
            }
            return (T)this.address;
        }

        boolean hasLowerSection() {
            return this.lowerSection != null;
        }

        boolean hasHostAddress() {
            return this.hostAddress != null;
        }

        boolean hasAddress() {
            return this.address != null;
        }

        @Override
        public T getHostAddress() {
            if (this.hostSection == null) {
                return this.getAddress();
            }
            if (this.hostAddress == null) {
                this.hostAddress = (IPAddress)this.getCreator().createAddressInternal(this.hostSection, this.getZone(), null);
            }
            return (T)this.hostAddress;
        }

        R getSection() {
            return this.section;
        }

        private CharSequence getZone() {
            return ParsedIPAddress.this.getQualifier().getZone();
        }

        boolean withoutSections() {
            return this.section == null;
        }

        boolean withoutAddressException() {
            return this.joinAddressException == null && this.mixedException == null && this.maskException == null;
        }

        boolean withoutRange() {
            return this.range == null;
        }

        boolean withoutGrouping() {
            return this.series == null;
        }

        IPAddressSeqRange createRange() {
            this.rangeLower = (IPAddress)this.getCreator().createAddressInternal(this.lowerSection, this.getZone(), null);
            this.rangeUpper = this.upperSection == null ? this.rangeLower : (IPAddress)this.getCreator().createAddressInternal(this.upperSection, this.getZone(), null);
            this.range = ((IPAddress)this.rangeLower).spanWithRange((IPAddress)this.rangeUpper);
            return this.range;
        }

        IPAddress getValForMask() {
            return (IPAddress)this.getCreator().createAddressInternal(this.lowerSection, null, null);
        }
    }

    static class CachedIPAddresses<T extends IPAddress>
    implements Serializable {
        private static final long serialVersionUID = 4L;
        protected T address;
        protected T hostAddress;

        CachedIPAddresses() {
        }

        public CachedIPAddresses(T address) {
            this(address, address);
        }

        public CachedIPAddresses(T address, T hostAddress) {
            this.address = address;
            this.hostAddress = hostAddress;
        }

        public T getAddress() {
            return this.address;
        }

        public T getHostAddress() {
            return this.hostAddress;
        }
    }
}

