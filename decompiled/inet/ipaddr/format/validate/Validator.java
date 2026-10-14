/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.validate;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.AddressStringParameters;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.HostName;
import inet.ipaddr.HostNameException;
import inet.ipaddr.HostNameParameters;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.IPAddressStringParameters;
import inet.ipaddr.MACAddressString;
import inet.ipaddr.MACAddressStringParameters;
import inet.ipaddr.format.large.IPAddressLargeDivision;
import inet.ipaddr.format.validate.AddressParseData;
import inet.ipaddr.format.validate.HostIdentifierStringValidator;
import inet.ipaddr.format.validate.IPAddressParseData;
import inet.ipaddr.format.validate.IPAddressProvider;
import inet.ipaddr.format.validate.MACAddressParseData;
import inet.ipaddr.format.validate.MACAddressProvider;
import inet.ipaddr.format.validate.ParsedAddressGrouping;
import inet.ipaddr.format.validate.ParsedHost;
import inet.ipaddr.format.validate.ParsedHostIdentifierStringQualifier;
import inet.ipaddr.format.validate.ParsedIPAddress;
import inet.ipaddr.format.validate.ParsedMACAddress;
import inet.ipaddr.ipv4.IPv4AddressStringParameters;
import inet.ipaddr.ipv6.IPv6AddressStringParameters;
import java.math.BigInteger;

public class Validator
implements HostIdentifierStringValidator {
    private static final int[] extendedChars;
    private static final int[] chars;
    private static final int MAX_HOST_LENGTH = 253;
    private static final int MAX_HOST_SEGMENTS = 127;
    private static final int MAX_LABEL_LENGTH = 63;
    private static final long MAC_MAX_TRIPLE = 0xFFFFFFL;
    private static final long MAC_MAX_QUINTUPLE = 0xFFFFFFFFFFL;
    private static final int MAC_DOUBLE_SEGMENT_DIGIT_COUNT = 6;
    private static final int MAC_EXTENDED_DOUBLE_SEGMENT_DIGIT_COUNT = 10;
    private static final int MAC_SINGLE_SEGMENT_DIGIT_COUNT = 12;
    private static final int MAC_EXTENDED_SINGLE_SEGMENT_DIGIT_COUNT = 16;
    private static final int IPV6_SINGLE_SEGMENT_DIGIT_COUNT = 32;
    private static final int IPV6_BINARY_SINGLE_SEGMENT_DIGIT_COUNT = 128;
    private static final int IPV4_BINARY_SINGLE_SEGMENT_DIGIT_COUNT = 32;
    private static final int IPV6_BASE85_SINGLE_SEGMENT_DIGIT_COUNT = 20;
    private static final int MAX_WILDCARDS = 19;
    private static final int IPV4_SINGLE_SEGMENT_OCTAL_DIGIT_COUNT = 11;
    private static final int LONG_HEX_DIGITS = 16;
    private static final int LONG_BINARY_DIGITS = 64;
    private static final char IPvFUTURE_UPPERCASE;
    private static final int[] EMPTY_INDICES;
    private static final ParsedHostIdentifierStringQualifier[] PREFIX_CACHE;
    static final IPAddressProvider.MaskCreator[][] MASK_CACHE;
    private static final IPAddressProvider.LoopbackCreator LOOPBACK_CACHE;
    private static final ParsedHost DEFAULT_EMPTY_HOST;
    public static final HostIdentifierStringValidator VALIDATOR;
    private static final IPAddressStringParameters DEFAULT_UNC_OPTS;
    private static final IPAddressStringParameters REVERSE_DNS_IPV4_OPTS;
    private static final IPAddressStringParameters REVERSE_DNS_IPV6_OPTS;
    private static final BigInteger[] BASE_85_POWERS;
    private static final BigInteger LOW_BITS_MASK;
    private static final long[] MAX_VALUES;
    private static final int[][] MAX_IPv4_STRING_LEN;

    private Validator() {
    }

    @Override
    public IPAddressProvider validateAddress(IPAddressString fromString) throws AddressStringException {
        return this.validateIPAddressStr(fromString);
    }

    public IPAddressProvider validateIPAddressStr(IPAddressString fromString) throws AddressStringException {
        String str = fromString.toString();
        IPAddressStringParameters validationOptions = fromString.getValidationOptions();
        ParsedIPAddress pa = new ParsedIPAddress(fromString, str, validationOptions);
        Validator.validateIPAddress(validationOptions, str, 0, str.length(), pa, false);
        ParsedHostIdentifierStringQualifier parsedQual = Validator.parseAddressQualifier(str, validationOptions, null, pa, str.length());
        return Validator.chooseIPAddressProvider(fromString, str, validationOptions, pa, parsedQual);
    }

    @Override
    public MACAddressProvider validateAddress(MACAddressString fromString) throws AddressStringException {
        return this.validateMACAddressStr(fromString);
    }

    public MACAddressProvider validateMACAddressStr(MACAddressString fromString) throws AddressStringException {
        String str = fromString.toString();
        MACAddressStringParameters validationOptions = fromString.getValidationOptions();
        ParsedMACAddress pa = new ParsedMACAddress(fromString, str);
        Validator.validateMACAddress(validationOptions, str, 0, str.length(), pa);
        AddressParseData addressParseData = pa.getAddressParseData();
        return Validator.chooseMACAddressProvider(fromString, validationOptions, pa, addressParseData);
    }

    private static void validateIPAddress(IPAddressStringParameters validationOptions, CharSequence str, int strStartIndex, int strEndIndex, IPAddressParseData parseData, boolean isEmbeddedIPv4) throws AddressStringException {
        Validator.validateAddress(validationOptions, null, str, strStartIndex, strEndIndex, parseData, null, isEmbeddedIPv4);
    }

    private static void validateMACAddress(MACAddressStringParameters validationOptions, String str, int strStartIndex, int strEndIndex, MACAddressParseData parseData) throws AddressStringException {
        Validator.validateAddress(null, validationOptions, str, strStartIndex, strEndIndex, null, parseData, false);
    }

    private static boolean isSingleSegmentIPv6(int totalDigits, boolean isRange, int frontTotalDigits, IPv6AddressStringParameters ipv6SpecificOptions) throws AddressStringException {
        boolean backIsIpv6;
        boolean bl = backIsIpv6 = totalDigits == 32 || ipv6SpecificOptions.allowBinary && totalDigits == 130 || isRange && totalDigits == 0 && (frontTotalDigits == 32 || ipv6SpecificOptions.allowBinary && frontTotalDigits == 130);
        if (backIsIpv6 && isRange && totalDigits != 0) {
            boolean frontIsIpv6;
            boolean bl2 = frontIsIpv6 = frontTotalDigits == 32 || ipv6SpecificOptions.allowBinary && frontTotalDigits == 130 || frontTotalDigits == 0;
            if (!frontIsIpv6) {
                throw new AddressStringException("ipaddress.error.too.few.segments.digit.count");
            }
        }
        return backIsIpv6;
    }

    private static boolean isSingleSegmentIPv4(int nonZeroDigits, int totalDigits, boolean isRange, int frontNonZeroDigits, int frontTotalDigits, IPv4AddressStringParameters ipv4SpecificOptions) throws AddressStringException {
        boolean backIsIpv4;
        boolean bl = backIsIpv4 = nonZeroDigits <= 11 || ipv4SpecificOptions.allowBinary && totalDigits == 34 || isRange && totalDigits == 0 && (frontTotalDigits <= 11 || ipv4SpecificOptions.allowBinary && frontTotalDigits == 34);
        if (backIsIpv4 && isRange && totalDigits != 0) {
            boolean frontIsIpv4;
            boolean bl2 = frontIsIpv4 = frontNonZeroDigits <= 11 || ipv4SpecificOptions.allowBinary && frontTotalDigits == 34 || frontTotalDigits == 0;
            if (!frontIsIpv4) {
                throw new AddressStringException("ipaddress.error.too.few.segments.digit.count");
            }
        }
        return backIsIpv4;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void validateAddress(IPAddressStringParameters validationOptions, MACAddressStringParameters macOptions, CharSequence str, int strStartIndex, int strEndIndex, IPAddressParseData ipAddressParseData, MACAddressParseData macAddressParseData, boolean isEmbeddedIPv4) throws AddressStringException {
        int segmentValueStartIndex;
        boolean canBeBase85;
        AddressParseData parseData;
        boolean isMac = macAddressParseData != null;
        IPv6AddressStringParameters ipv6SpecificOptions = null;
        IPv4AddressStringParameters ipv4SpecificOptions = null;
        MACAddressStringParameters.MACAddressStringFormatParameters macSpecificOptions = null;
        MACAddressParseData.MACFormat macFormat = null;
        if (isMac) {
            MACAddressStringParameters mACAddressStringParameters = macOptions;
            MACAddressStringParameters.MACAddressStringFormatParameters mACAddressStringFormatParameters = macSpecificOptions = macOptions.getFormatParameters();
            parseData = macAddressParseData.getAddressParseData();
            canBeBase85 = false;
        } else {
            IPAddressStringParameters iPAddressStringParameters = validationOptions;
            Object var10_17 = null;
            parseData = ipAddressParseData.getAddressParseData();
            ipv6SpecificOptions = validationOptions.getIPv6Parameters();
            canBeBase85 = ipv6SpecificOptions.allowBase85;
            ipv4SpecificOptions = validationOptions.getIPv4Parameters();
        }
        int index = strStartIndex;
        int[] charArray = chars;
        int frontWildcardCount = 0;
        int wildcardCount2 = 0;
        int singleWildcardCount = 0;
        int leadingZeroCount = 0;
        int frontSingleWildcardCount = 0;
        int frontLeadingZeroCount = 0;
        int frontDigitCount = 0;
        int frontHexDelimiterIndex = -1;
        int hexDelimiterIndex = -1;
        int rangeWildcardIndex = -1;
        int extendedRangeWildcardIndex = -1;
        int extendedCharacterIndex = -1;
        boolean isDoubleSegment = false;
        boolean isSingleSegment = false;
        boolean isSingleIPv6 = false;
        boolean uppercase = false;
        boolean frontUppercase = false;
        boolean firstSegmentDashedRange = false;
        boolean atEnd = false;
        boolean frontIsStandardRangeChar = false;
        boolean hasDigits = false;
        boolean leadingWithZero = false;
        boolean isSegmented = false;
        long currentFrontValueHex = 0L;
        long currentValueHex = 0L;
        boolean checkCharCounts = true;
        long extendedValue = 0L;
        int segmentStartIndex = segmentValueStartIndex = index;
        IPAddress.IPVersion version = null;
        while (true) {
            int hexMaxChars;
            boolean isRangeChar;
            boolean isSpace;
            int frontLeadingZeroStartIndex;
            int frontEndIndex;
            int frontStartIndex;
            void var10_19;
            int totalDigits;
            int currentChar;
            if (index >= strEndIndex) {
                if (index != strEndIndex) return;
                boolean bl = true;
                atEnd = bl;
                if (!atEnd) return;
                parseData.setAddressEndIndex(index);
                if (isSegmented) {
                    if (isMac) {
                        currentChar = macFormat.getSeparator();
                        isDoubleSegment = parseData.getSegmentCount() == 1 && currentChar == 45;
                        macAddressParseData.setDoubleSegment(isDoubleSegment);
                        if (isDoubleSegment) {
                            int totalDigits2 = index - segmentValueStartIndex;
                            macAddressParseData.setExtended(totalDigits2 == 10);
                        }
                    } else {
                        if (extendedCharacterIndex >= 0) {
                            throw new AddressStringException(str, extendedCharacterIndex);
                        }
                        if (version.isIPv4()) {
                            currentChar = 46;
                        } else {
                            if (index == segmentStartIndex) {
                                if (index != parseData.getConsecutiveSeparatorIndex() + 2) throw new AddressStringException(str, "ipaddress.error.cannot.end.with.single.separator");
                                return;
                            }
                            if (ipAddressParseData.isProvidingMixedIPv6()) return;
                            currentChar = 58;
                        }
                    }
                } else {
                    void var14_15;
                    int totalCharacterCount = index - strStartIndex;
                    if (totalCharacterCount == 0) {
                        if (!isMac && ipAddressParseData.hasPrefixSeparator()) {
                            if (!validationOptions.allowPrefixOnly) {
                                throw new AddressStringException(str, "ipaddress.error.prefix.only");
                            }
                        } else if (!var14_15.allowEmpty) {
                            throw new AddressStringException(str, "ipaddress.error.empty");
                        }
                        parseData.setEmpty(true);
                        return;
                    }
                    if (wildcardCount2 == totalCharacterCount && wildcardCount2 <= 19) {
                        if (!var14_15.allowAll) {
                            throw new AddressStringException(str, "ipaddress.error.all");
                        }
                        parseData.setHasWildcard();
                        parseData.setAll();
                        return;
                    }
                    totalDigits = index - segmentValueStartIndex;
                    int frontTotalDigits = frontLeadingZeroCount + frontDigitCount;
                    if (isMac) {
                        if (((totalDigits == 6 || totalDigits == 10) && (frontTotalDigits == 6 || frontWildcardCount > 0) || frontTotalDigits == 6 && wildcardCount2 > 0 || frontWildcardCount > 0 && wildcardCount2 > 0) && !firstSegmentDashedRange) {
                            MACAddressStringParameters.AddressSize addressSize = macOptions.addressSize;
                            if (addressSize == MACAddressStringParameters.AddressSize.EUI64 && totalDigits == 6) {
                                throw new AddressStringException(str, "ipaddress.error.too.few.segments");
                            }
                            if (addressSize == MACAddressStringParameters.AddressSize.MAC && totalDigits == 10) {
                                throw new AddressStringException(str, "ipaddress.error.too.many.segments");
                            }
                            if (!macOptions.allowSingleDashed) {
                                throw new AddressStringException(str, "ipaddress.mac.error.format");
                            }
                            isDoubleSegment = true;
                            macAddressParseData.setDoubleSegment(true);
                            macAddressParseData.setExtended(totalDigits == 10);
                            currentChar = 45;
                            checkCharCounts = false;
                        } else if (frontWildcardCount > 0 || wildcardCount2 > 0) {
                            if (!macOptions.allowSingleDashed) {
                                throw new AddressStringException(str, "ipaddress.mac.error.format");
                            }
                            currentChar = 45;
                        } else {
                            boolean frontIsNoDigits;
                            boolean frontIs16Digits;
                            boolean frontIs12Digits;
                            boolean isNoDigits;
                            if (!var14_15.allowSingleSegment) {
                                throw new AddressStringException(str, "ipaddress.error.single.segment");
                            }
                            boolean is12Digits = totalDigits == 12;
                            boolean is16Digits = totalDigits == 16;
                            boolean bl2 = isNoDigits = totalDigits == 0;
                            if (!is12Digits && !is16Digits) {
                                if (!isNoDigits) throw new AddressStringException("ipaddress.error.too.few.segments.digit.count");
                            }
                            if (rangeWildcardIndex >= 0) {
                                frontIs12Digits = frontTotalDigits == 12;
                                frontIs16Digits = frontTotalDigits == 16;
                                boolean bl3 = frontIsNoDigits = frontTotalDigits == 0;
                                if (is12Digits ? !frontIs12Digits && !frontIsNoDigits : (is16Digits ? !frontIs16Digits && !frontIsNoDigits : isNoDigits && !frontIs12Digits && !frontIs16Digits)) {
                                    throw new AddressStringException("ipaddress.error.front.digit.count");
                                }
                            } else {
                                if (isNoDigits) {
                                    throw new AddressStringException("ipaddress.error.too.few.segments.digit.count");
                                }
                                frontIsNoDigits = false;
                                frontIs16Digits = false;
                                frontIs12Digits = false;
                            }
                            isSingleSegment = true;
                            parseData.setSingleSegment();
                            macAddressParseData.setExtended(is16Digits || frontIs16Digits);
                            currentChar = 58;
                            checkCharCounts = false;
                        }
                    } else {
                        boolean isRange;
                        if (!var14_15.allowSingleSegment) {
                            throw new AddressStringException(str, "ipaddress.error.single.segment");
                        }
                        boolean bl4 = isRange = rangeWildcardIndex >= 0;
                        if (Validator.isSingleSegmentIPv6(totalDigits, isRange, frontTotalDigits, ipv6SpecificOptions)) {
                            if (extendedCharacterIndex >= 0) {
                                throw new AddressStringException(str, extendedCharacterIndex);
                            }
                            isSingleIPv6 = true;
                            currentChar = 58;
                        } else {
                            if (canBeBase85 && Validator.parseBase85(validationOptions, str, strStartIndex, strEndIndex, ipAddressParseData, extendedRangeWildcardIndex, totalCharacterCount, index)) return;
                            int leadingZeros = leadingZeroCount;
                            if (leadingWithZero) {
                                ++leadingZeros;
                            }
                            if (!Validator.isSingleSegmentIPv4(totalDigits - leadingZeros, totalDigits, isRange, frontDigitCount, frontTotalDigits, ipv4SpecificOptions)) throw new AddressStringException("ipaddress.error.too.few.segments.digit.count");
                            if (extendedCharacterIndex >= 0) {
                                throw new AddressStringException(str, extendedCharacterIndex);
                            }
                            currentChar = 46;
                        }
                        isSingleSegment = true;
                        parseData.setSingleSegment();
                        checkCharCounts = false;
                    }
                }
            } else {
                currentChar = str.charAt(index);
            }
            if (currentChar <= 57 && currentChar >= 48) {
                if (hasDigits) {
                    currentValueHex = currentValueHex << 4 | (long)charArray[currentChar];
                } else if (currentChar == 48) {
                    if (leadingWithZero) {
                        ++leadingZeroCount;
                    } else {
                        leadingWithZero = true;
                    }
                } else {
                    hasDigits = true;
                    currentValueHex = currentValueHex << 4 | (long)charArray[currentChar];
                }
                ++index;
                continue;
            }
            if (currentChar >= 97 && currentChar <= 102) {
                currentValueHex = currentValueHex << 4 | (long)charArray[currentChar];
                hasDigits = true;
                ++index;
                continue;
            }
            if (currentChar == 46) {
                int segCount = parseData.getSegmentCount();
                if (isMac) {
                    if (segCount == 0) {
                        if (!macOptions.allowDotted) {
                            throw new AddressStringException(str, "ipaddress.mac.error.format");
                        }
                        macFormat = MACAddressParseData.MACFormat.DOTTED;
                        macAddressParseData.setFormat(macFormat);
                        parseData.initSegmentData(4);
                        isSegmented = true;
                    } else {
                        if (macFormat != MACAddressParseData.MACFormat.DOTTED) {
                            throw new AddressStringException(str, "ipaddress.mac.error.mix.format.characters.at.index", index);
                        }
                        if (segCount >= (macOptions.addressSize == MACAddressStringParameters.AddressSize.MAC ? 3 : 4)) {
                            throw new AddressStringException(str, "ipaddress.error.too.many.segments");
                        }
                    }
                } else if (segCount == 0) {
                    if (!validationOptions.allowIPv4) {
                        throw new AddressStringException(str, "ipaddress.error.ipv4");
                    }
                    version = IPAddress.IPVersion.IPV4;
                    ipAddressParseData.setVersion(version);
                    IPv4AddressStringParameters iPv4AddressStringParameters = ipv4SpecificOptions;
                    canBeBase85 = false;
                    parseData.initSegmentData(4);
                    isSegmented = true;
                } else {
                    if (ipAddressParseData.getProviderIPVersion().isIPv6()) {
                        if (!ipv6SpecificOptions.allowMixed) {
                            throw new AddressStringException(str, "ipaddress.error.no.mixed");
                        }
                        int totalSegmentCount = segCount + 2;
                        if (totalSegmentCount > 8) {
                            throw new AddressStringException(str, "ipaddress.error.too.many.segments");
                        }
                        if (wildcardCount2 > 0 && parseData.getConsecutiveSeparatorIndex() < 0 && totalSegmentCount < 8 && ipv6SpecificOptions.allowWildcardedSeparator) {
                            parseData.setHasWildcard();
                            Validator.assign6Attributes2Values1Flags(segmentStartIndex, index, segmentStartIndex, segmentStartIndex, index, segmentStartIndex, parseData, segCount, 0L, 65535L, 0x810000);
                            parseData.incrementSegmentCount();
                        }
                        IPAddressStringParameters mixedOptions = ipv6SpecificOptions.getMixedParameters();
                        ParsedIPAddress pa = new ParsedIPAddress(null, str, mixedOptions);
                        Validator.validateIPAddress(mixedOptions, str, segmentStartIndex, strEndIndex, pa, true);
                        pa.clearQualifier();
                        Validator.checkSegments(str, mixedOptions, pa);
                        ipAddressParseData.setMixedParsedAddress(pa);
                        index = pa.getAddressParseData().getAddressEndIndex();
                        continue;
                    }
                    if (segCount >= 4) {
                        throw new AddressStringException(str, "ipaddress.error.ipv4.too.many.segments");
                    }
                }
                if (wildcardCount2 > 0) {
                    if (!var10_19.rangeOptions.allowsWildcard()) {
                        throw new AddressStringException(str, "ipaddress.error.no.wildcard");
                    }
                    totalDigits = index - segmentStartIndex;
                    if (wildcardCount2 != totalDigits) throw new AddressStringException(str, index, true);
                    if (hexDelimiterIndex >= 0) {
                        throw new AddressStringException(str, index, true);
                    }
                    parseData.setHasWildcard();
                    int startIndex = index - wildcardCount2;
                    Validator.assign6Attributes2Values1Flags(startIndex, index, startIndex, startIndex, index, startIndex, parseData, segCount, 0L, isMac ? 65535L : 255L, 65536);
                    wildcardCount2 = 0;
                } else {
                    int radix;
                    long value;
                    int flags = 0;
                    int rangeFlags = 0;
                    int digitStartIndex = segmentValueStartIndex + leadingZeroCount;
                    int digitCount = index - digitStartIndex;
                    if (leadingWithZero) {
                        if (digitCount == 1) {
                            if (leadingZeroCount == 0 && rangeWildcardIndex < 0 && hexDelimiterIndex < 0) {
                                Validator.assign4Attributes(digitStartIndex, index, parseData, segCount, 10, segmentValueStartIndex);
                                parseData.incrementSegmentCount();
                                segmentStartIndex = segmentValueStartIndex = ++index;
                                leadingWithZero = false;
                                continue;
                            }
                        } else {
                            ++leadingZeroCount;
                            ++digitStartIndex;
                            --digitCount;
                        }
                        leadingWithZero = false;
                    }
                    boolean noValuesToSet = false;
                    if (digitCount == 0) {
                        if (rangeWildcardIndex < 0) throw new AddressStringException(str, "ipaddress.error.empty.segment.at.index", index);
                        if (hexDelimiterIndex >= 0) throw new AddressStringException(str, "ipaddress.error.empty.segment.at.index", index);
                        if (!var10_19.rangeOptions.allowsInferredBoundary()) {
                            throw new AddressStringException(str, "ipaddress.error.empty.segment.at.index", index);
                        }
                        if (isMac) {
                            value = 65535L;
                            radix = 16;
                        } else {
                            value = 255L;
                            radix = 10;
                        }
                        rangeFlags = 0x400000;
                    } else {
                        boolean isBinary;
                        boolean isSingleWildcard;
                        boolean hasLeadingZeros = leadingZeroCount > 0;
                        boolean bl = isSingleWildcard = singleWildcardCount > 0;
                        if (isMac || hexDelimiterIndex >= 0) {
                            if (isMac) {
                                int maxMacChars = 4;
                                if (digitCount > maxMacChars) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", segmentValueStartIndex);
                                }
                                int totalDigits3 = digitCount + leadingZeroCount;
                                if (hexDelimiterIndex >= 0) {
                                    throw new AddressStringException(str, hexDelimiterIndex);
                                }
                                if (leadingZeroCount > 0 && !var10_19.allowLeadingZeros) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                                }
                                if (!var10_19.allowUnlimitedLeadingZeros && totalDigits3 > maxMacChars) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", segmentValueStartIndex);
                                }
                                if (!macSpecificOptions.allowShortSegments && totalDigits3 < maxMacChars) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.too.short.at.index", segmentValueStartIndex);
                                }
                            } else {
                                if (!var10_19.allowLeadingZeros) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                                }
                                if (!ipv4SpecificOptions.inet_aton_hex) {
                                    throw new AddressStringException(str, "ipaddress.error.ipv4.segment.hex");
                                }
                                if (hasLeadingZeros && !ipv4SpecificOptions.inet_aton_leading_zeros) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                                }
                                if (digitCount > 8) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", segmentValueStartIndex);
                                }
                                ipAddressParseData.set_has_inet_aton_value(true);
                            }
                            radix = 16;
                            if (isSingleWildcard) {
                                if (rangeWildcardIndex >= 0) {
                                    throw new AddressStringException(str, index, true);
                                }
                                Validator.assignSingleWildcard16(currentValueHex, str, digitStartIndex, index, singleWildcardCount, parseData, segCount, segmentValueStartIndex, (AddressStringParameters.AddressStringFormatParameters)var10_19);
                                value = 0L;
                                noValuesToSet = true;
                                singleWildcardCount = 0;
                            } else {
                                value = currentValueHex;
                            }
                            hexDelimiterIndex = -1;
                        } else if (hasLeadingZeros && ((isBinary = ipv4SpecificOptions.allowBinary && Validator.isBinaryDelimiter(str, digitStartIndex)) || ipv4SpecificOptions.inet_aton_octal)) {
                            if (!var10_19.allowLeadingZeros) {
                                throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                            }
                            if (isBinary) {
                                if (digitCount > 33) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", segmentValueStartIndex);
                                }
                                ++digitStartIndex;
                                --digitCount;
                                radix = 2;
                                ipAddressParseData.setHasBinaryDigits(true);
                                if (isSingleWildcard) {
                                    if (rangeWildcardIndex >= 0) {
                                        throw new AddressStringException(str, index, true);
                                    }
                                    if (digitCount > 16) {
                                        Validator.parseSingleSegmentSingleWildcard2(str, digitStartIndex, index, singleWildcardCount, parseData, segCount, segmentValueStartIndex, (AddressStringParameters.AddressStringFormatParameters)var10_19);
                                    } else {
                                        Validator.switchSingleWildcard2(currentValueHex, str, digitStartIndex, index, singleWildcardCount, parseData, segCount, segmentValueStartIndex, (AddressStringParameters.AddressStringFormatParameters)var10_19);
                                    }
                                    value = 0L;
                                    noValuesToSet = true;
                                    singleWildcardCount = 0;
                                } else {
                                    value = digitCount > 16 ? Validator.parseLong2(str, digitStartIndex, index) : Validator.switchValue2(currentValueHex, str, digitCount);
                                }
                            } else {
                                if (leadingZeroCount > 1 && !ipv4SpecificOptions.inet_aton_leading_zeros) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                                }
                                if (digitCount > 11) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", segmentValueStartIndex);
                                }
                                ipAddressParseData.set_has_inet_aton_value(true);
                                radix = 8;
                                if (isSingleWildcard) {
                                    if (rangeWildcardIndex >= 0) {
                                        throw new AddressStringException(str, index, true);
                                    }
                                    Validator.switchSingleWildcard8(currentValueHex, str, digitStartIndex, index, singleWildcardCount, parseData, segCount, segmentValueStartIndex, (AddressStringParameters.AddressStringFormatParameters)var10_19);
                                    value = 0L;
                                    noValuesToSet = true;
                                    singleWildcardCount = 0;
                                } else {
                                    value = Validator.switchValue8(currentValueHex, str, digitCount);
                                }
                            }
                        } else {
                            if (hasLeadingZeros) {
                                if (!var10_19.allowLeadingZeros) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                                }
                                ipAddressParseData.setHasIPv4LeadingZeros(true);
                            }
                            if (digitCount > 10) {
                                throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", segmentValueStartIndex);
                            }
                            radix = 10;
                            if (isSingleWildcard) {
                                if (rangeWildcardIndex >= 0) {
                                    throw new AddressStringException(str, index, true);
                                }
                                Validator.switchSingleWildcard10(currentValueHex, str, digitStartIndex, index, singleWildcardCount, parseData, segCount, segmentValueStartIndex, ipv4SpecificOptions);
                                value = 0L;
                                noValuesToSet = true;
                                singleWildcardCount = 0;
                            } else {
                                value = Validator.switchValue10(currentValueHex, str, digitCount);
                                flags = 262144;
                            }
                        }
                        hasDigits = false;
                        currentValueHex = 0L;
                    }
                    if (rangeWildcardIndex >= 0) {
                        boolean hasFrontLeadingZeros;
                        int frontRadix = 0;
                        long front = 0L;
                        frontStartIndex = rangeWildcardIndex - frontDigitCount;
                        frontEndIndex = rangeWildcardIndex;
                        frontLeadingZeroStartIndex = frontStartIndex - frontLeadingZeroCount;
                        if (!var10_19.rangeOptions.allowsRangeSeparator()) {
                            throw new AddressStringException(str, "ipaddress.error.no.range");
                        }
                        if (frontSingleWildcardCount > 0) throw new AddressStringException(str, rangeWildcardIndex, true);
                        if (frontWildcardCount > 0) {
                            throw new AddressStringException(str, rangeWildcardIndex, true);
                        }
                        boolean frontEmpty = frontStartIndex == frontEndIndex;
                        boolean isReversed = false;
                        boolean bl = hasFrontLeadingZeros = frontLeadingZeroCount > 0;
                        if (isMac || frontHexDelimiterIndex >= 0) {
                            if (isMac) {
                                int totalFrontDigits = frontDigitCount + frontLeadingZeroCount;
                                int maxMacChars = 4;
                                if (frontHexDelimiterIndex >= 0) {
                                    throw new AddressStringException(str, frontHexDelimiterIndex);
                                }
                                if (hasFrontLeadingZeros && !var10_19.allowLeadingZeros) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                                }
                                if (!var10_19.allowUnlimitedLeadingZeros && totalFrontDigits > maxMacChars) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", frontLeadingZeroStartIndex);
                                }
                                if (!macSpecificOptions.allowShortSegments && totalFrontDigits < maxMacChars) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.too.short.at.index", frontLeadingZeroStartIndex);
                                }
                                if (frontEmpty) {
                                    if (!var10_19.rangeOptions.allowsInferredBoundary()) {
                                        throw new AddressStringException(str, "ipaddress.error.empty.segment.at.index", index);
                                    }
                                    rangeFlags |= 0x200000;
                                    front = 0L;
                                } else {
                                    if (frontDigitCount > maxMacChars) {
                                        throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", frontLeadingZeroStartIndex);
                                    }
                                    front = currentFrontValueHex;
                                    isReversed = front > value && digitCount != 0;
                                }
                            } else {
                                if (!var10_19.allowLeadingZeros) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                                }
                                if (!ipv4SpecificOptions.inet_aton_hex) {
                                    throw new AddressStringException(str, "ipaddress.error.ipv4.segment.hex");
                                }
                                if (hasFrontLeadingZeros && !ipv4SpecificOptions.inet_aton_leading_zeros) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                                }
                                if (frontEmpty) {
                                    throw new AddressStringException(str, "ipaddress.error.empty.segment.at.index", index);
                                }
                                if (frontDigitCount > 8) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", frontLeadingZeroStartIndex);
                                }
                                ipAddressParseData.set_has_inet_aton_value(true);
                                front = currentFrontValueHex;
                                isReversed = front > value && digitCount != 0;
                            }
                            frontRadix = 16;
                        } else {
                            if (hasFrontLeadingZeros) {
                                if (!var10_19.allowLeadingZeros) {
                                    throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                                }
                                if (ipv4SpecificOptions.allowBinary && Validator.isBinaryDelimiter(str, frontStartIndex)) {
                                    if (frontDigitCount > 33) {
                                        throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", frontLeadingZeroStartIndex);
                                    }
                                    ipAddressParseData.setHasBinaryDigits(true);
                                    front = --frontDigitCount > 16 ? Validator.parseLong2(str, ++frontStartIndex, frontEndIndex) : Validator.switchValue2(currentFrontValueHex, str, frontDigitCount);
                                    frontRadix = 2;
                                    isReversed = digitCount != 0 && front > value;
                                } else if (ipv4SpecificOptions.inet_aton_octal) {
                                    if (frontLeadingZeroCount > 1 && !ipv4SpecificOptions.inet_aton_leading_zeros) {
                                        throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                                    }
                                    if (frontDigitCount > 11) {
                                        throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", frontLeadingZeroStartIndex);
                                    }
                                    ipAddressParseData.set_has_inet_aton_value(true);
                                    front = Validator.switchValue8(currentFrontValueHex, str, frontDigitCount);
                                    frontRadix = 8;
                                    boolean bl5 = isReversed = digitCount != 0 && front > value;
                                }
                            }
                            if (frontRadix == 0) {
                                frontRadix = 10;
                                if (frontEmpty) {
                                    if (!var10_19.rangeOptions.allowsInferredBoundary()) {
                                        throw new AddressStringException(str, "ipaddress.error.empty.segment.at.index", index);
                                    }
                                    rangeFlags |= 0x200000;
                                } else {
                                    if (frontDigitCount > 10) {
                                        throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", frontLeadingZeroStartIndex);
                                    }
                                    front = Validator.switchValue10(currentFrontValueHex, str, frontDigitCount);
                                    if (hasFrontLeadingZeros) {
                                        if (!var10_19.allowLeadingZeros) {
                                            throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                                        }
                                        ipAddressParseData.setHasIPv4LeadingZeros(true);
                                    }
                                    boolean bl6 = isReversed = digitCount != 0 && front > value;
                                    if (!isReversed) {
                                        rangeFlags = leadingZeroCount == 0 && (flags & 0x40000) != 0 ? (rangeFlags |= 0xC0000) : (rangeFlags |= 0x40000);
                                    }
                                }
                            }
                        }
                        int backEndIndex = index;
                        if (isReversed) {
                            if (!var10_19.rangeOptions.allowsReverseRange()) {
                                throw new AddressStringException(str, "ipaddress.error.invalidRange");
                            }
                            int tmp = frontStartIndex;
                            frontStartIndex = digitStartIndex;
                            digitStartIndex = tmp;
                            tmp = frontEndIndex;
                            frontEndIndex = backEndIndex;
                            backEndIndex = tmp;
                            tmp = frontLeadingZeroStartIndex;
                            frontLeadingZeroStartIndex = segmentValueStartIndex;
                            segmentValueStartIndex = tmp;
                            tmp = frontRadix;
                            frontRadix = radix;
                            radix = tmp;
                            long tmpl = front;
                            front = value;
                            value = tmpl;
                        }
                        Validator.assign7Attributes2Values1Flags(frontStartIndex, frontEndIndex, frontLeadingZeroStartIndex, digitStartIndex, backEndIndex, segmentValueStartIndex, parseData, segCount, front, value, rangeFlags | 0x100000 | frontRadix, radix);
                        rangeWildcardIndex = -1;
                    } else if (!noValuesToSet) {
                        Validator.assign3Attributes1Values1Flags(digitStartIndex, index, segmentValueStartIndex, parseData, segCount, value, flags | radix);
                    }
                    leadingZeroCount = 0;
                }
                parseData.incrementSegmentCount();
                segmentStartIndex = segmentValueStartIndex = ++index;
                continue;
            }
            if (currentChar == 58) {
                isSpace = false;
                isRangeChar = false;
            } else {
                isRangeChar = currentChar == 45;
                if (isRangeChar || isMac && currentChar == 124) {
                    isSpace = false;
                    boolean isDashedRangeChar = !isRangeChar;
                    boolean endOfHexSegment = false;
                    if (isMac) {
                        if (macFormat == null) {
                            if (rangeWildcardIndex >= 0 && !firstSegmentDashedRange) {
                                if (frontHexDelimiterIndex >= 0) {
                                    throw new AddressStringException(str, frontHexDelimiterIndex);
                                }
                                if (hexDelimiterIndex >= 0) {
                                    throw new AddressStringException(str, hexDelimiterIndex);
                                }
                                if (!macOptions.allowDashed) {
                                    throw new AddressStringException(str, "ipaddress.mac.error.format");
                                }
                                macFormat = MACAddressParseData.MACFormat.DASHED;
                                macAddressParseData.setFormat(macFormat);
                                checkCharCounts = false;
                                parseData.initSegmentData(8);
                                isSegmented = true;
                                if (frontWildcardCount > 0) {
                                    if (!var10_19.rangeOptions.allowsWildcard()) {
                                        throw new AddressStringException(str, "ipaddress.error.no.wildcard");
                                    }
                                    if (frontSingleWildcardCount > 0) throw new AddressStringException(str, rangeWildcardIndex, true);
                                    if (frontLeadingZeroCount > 0) throw new AddressStringException(str, rangeWildcardIndex, true);
                                    if (frontDigitCount > 0) throw new AddressStringException(str, rangeWildcardIndex, true);
                                    if (frontHexDelimiterIndex >= 0) {
                                        throw new AddressStringException(str, rangeWildcardIndex, true);
                                    }
                                    parseData.setHasWildcard();
                                    int backDigits = index - segmentValueStartIndex;
                                    long upperValue = isDoubleSegment || backDigits == 6 ? 0xFFFFFFL : 255L;
                                    int startIndex = rangeWildcardIndex - frontWildcardCount;
                                    Validator.assign6Attributes2Values1Flags(startIndex, rangeWildcardIndex, startIndex, startIndex, rangeWildcardIndex, startIndex, parseData, 0, 0L, upperValue, 65536);
                                } else {
                                    if (!var10_19.allowLeadingZeros && frontLeadingZeroCount > 0) {
                                        throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                                    }
                                    int startIndex = rangeWildcardIndex - frontDigitCount;
                                    int leadingZeroStartIndex = startIndex - frontLeadingZeroCount;
                                    if (frontSingleWildcardCount > 0) {
                                        Validator.assignSingleWildcard16(currentFrontValueHex, str, startIndex, rangeWildcardIndex, singleWildcardCount, parseData, 0, leadingZeroStartIndex, (AddressStringParameters.AddressStringFormatParameters)var10_19);
                                    } else {
                                        int flags;
                                        if (frontUppercase) {
                                            flags = 0;
                                        } else {
                                            if (frontDigitCount == 0) {
                                                throw new AddressStringException(str, "ipaddress.error.empty.segment.at.index", startIndex);
                                            }
                                            flags = 262144;
                                        }
                                        Validator.assign3Attributes1Values1Flags(startIndex, rangeWildcardIndex, leadingZeroStartIndex, parseData, 0, currentFrontValueHex, flags);
                                    }
                                }
                                segmentStartIndex = segmentValueStartIndex = rangeWildcardIndex + 1;
                                rangeWildcardIndex = -1;
                                parseData.incrementSegmentCount();
                                endOfHexSegment = isRangeChar;
                            } else if (isDashedRangeChar) {
                                firstSegmentDashedRange = true;
                            } else {
                                endOfHexSegment = firstSegmentDashedRange;
                            }
                        } else if (macFormat == MACAddressParseData.MACFormat.DASHED) {
                            endOfHexSegment = isRangeChar;
                        } else if (isDashedRangeChar) {
                            throw new AddressStringException(str, index);
                        }
                    }
                    if (!endOfHexSegment) {
                        if (extendedCharacterIndex < 0) {
                            if (rangeWildcardIndex >= 0) {
                                if (!canBeBase85) throw new AddressStringException(str, index, true);
                                extendedCharacterIndex = index++;
                                continue;
                            }
                            rangeWildcardIndex = index;
                            frontIsStandardRangeChar = isRangeChar;
                            frontDigitCount = index - segmentValueStartIndex - leadingZeroCount - wildcardCount2;
                            frontLeadingZeroCount = leadingZeroCount;
                            if (leadingWithZero && frontDigitCount != 1) {
                                ++frontLeadingZeroCount;
                                --frontDigitCount;
                            }
                            frontUppercase = uppercase;
                            frontHexDelimiterIndex = hexDelimiterIndex;
                            frontWildcardCount = wildcardCount2;
                            frontSingleWildcardCount = singleWildcardCount;
                            currentFrontValueHex = currentValueHex;
                            segmentValueStartIndex = ++index;
                            leadingWithZero = false;
                            uppercase = false;
                            hasDigits = false;
                            hexDelimiterIndex = -1;
                            singleWildcardCount = 0;
                            wildcardCount2 = 0;
                            leadingZeroCount = 0;
                            currentValueHex = 0L;
                            continue;
                        }
                        ++index;
                        continue;
                    }
                } else if (isMac && currentChar == 32) {
                    isSpace = true;
                } else {
                    boolean isZoneChar;
                    if (currentChar == 47) {
                        if (isMac) {
                            throw new AddressStringException(str, index);
                        }
                        strEndIndex = index;
                        ipAddressParseData.setHasPrefixSeparator(true);
                        ipAddressParseData.setQualifierIndex(index + 1);
                        continue;
                    }
                    if (currentChar >= 65 && currentChar <= 70) {
                        ++index;
                        currentValueHex = currentValueHex << 4 | (long)charArray[currentChar];
                        uppercase = true;
                        hasDigits = true;
                        continue;
                    }
                    boolean isSegWildcard = currentChar == 42;
                    if (isSegWildcard) {
                        isZoneChar = false;
                    } else {
                        isZoneChar = currentChar == 37;
                        isSegWildcard = isZoneChar;
                    }
                    if (isSegWildcard) {
                        if (isZoneChar && !isMac && ipv6SpecificOptions.allowZone && (parseData.getSegmentCount() > 0 && (isEmbeddedIPv4 || ipAddressParseData.getProviderIPVersion() == IPAddress.IPVersion.IPV6) || Validator.isSingleSegmentIPv6(index - segmentValueStartIndex, rangeWildcardIndex >= 0, frontLeadingZeroCount + frontDigitCount, ipv6SpecificOptions) || wildcardCount2 == index && wildcardCount2 <= 19)) {
                            canBeBase85 = false;
                            strEndIndex = index;
                            ipAddressParseData.setZoned(true);
                            ipAddressParseData.setQualifierIndex(index + 1);
                            continue;
                        }
                        ++wildcardCount2;
                        ++index;
                        continue;
                    }
                    if (currentChar == 95) {
                        hasDigits = true;
                        ++index;
                        ++singleWildcardCount;
                        continue;
                    }
                    if (Validator.isHexDelimiter((char)currentChar)) {
                        if (hasDigits || !leadingWithZero || leadingZeroCount > 0 || hexDelimiterIndex >= 0 || singleWildcardCount > 0) {
                            if (!canBeBase85) throw new AddressStringException(str, index, true);
                            if (extendedCharacterIndex < 0) {
                                extendedCharacterIndex = index;
                            }
                            ++index;
                            continue;
                        }
                        if (isMac ? parseData.getSegmentCount() > 0 : version != null && version.isIPv6()) {
                            throw new AddressStringException(str, index);
                        }
                        hexDelimiterIndex = index++;
                        leadingWithZero = false;
                        segmentValueStartIndex = index;
                        continue;
                    }
                    if (currentChar == 187) {
                        if (!canBeBase85) throw new AddressStringException(str, index);
                        if (extendedCharacterIndex < 0) {
                            extendedCharacterIndex = index;
                        } else if (extendedRangeWildcardIndex >= 0) {
                            throw new AddressStringException(str, index, true);
                        }
                        extendedRangeWildcardIndex = index++;
                        continue;
                    }
                    if (currentChar == 167) {
                        if (!canBeBase85) throw new AddressStringException(str, index);
                        if (isMac) throw new AddressStringException(str, index);
                        if (!ipv6SpecificOptions.allowZone) throw new AddressStringException(str, index);
                        strEndIndex = index;
                        ipAddressParseData.setZoned(true);
                        ipAddressParseData.setBase85Zoned(true);
                        ipAddressParseData.setQualifierIndex(index + 1);
                        continue;
                    }
                    if (!canBeBase85) throw new AddressStringException(str, index);
                    if (currentChar < 0) throw new AddressStringException(str, index);
                    if (currentChar >= extendedChars.length) {
                        throw new AddressStringException(str, index);
                    }
                    int val = extendedChars[currentChar];
                    if (val == 0) {
                        throw new AddressStringException(str, index);
                    }
                    if (extendedCharacterIndex < 0) {
                        extendedCharacterIndex = index;
                    }
                    ++index;
                    continue;
                }
            }
            int segCount = parseData.getSegmentCount();
            if (isMac) {
                if (segCount == 0) {
                    if (isSingleSegment) {
                        parseData.initSegmentData(1);
                    } else {
                        if (hexDelimiterIndex >= 0) {
                            throw new AddressStringException(str, hexDelimiterIndex);
                        }
                        if (isRangeChar) {
                            if (!macOptions.allowDashed) throw new AddressStringException(str, "ipaddress.mac.error.format");
                        } else if (isSpace) {
                            if (!macOptions.allowSpaceDelimited) throw new AddressStringException(str, "ipaddress.mac.error.format");
                        } else if (!macOptions.allowColonDelimited) {
                            throw new AddressStringException(str, "ipaddress.mac.error.format");
                        }
                        if (isRangeChar) {
                            macFormat = MACAddressParseData.MACFormat.DASHED;
                            macAddressParseData.setFormat(macFormat);
                            checkCharCounts = false;
                        } else {
                            macFormat = isSpace ? MACAddressParseData.MACFormat.SPACE_DELIMITED : MACAddressParseData.MACFormat.COLON_DELIMITED;
                            macAddressParseData.setFormat(macFormat);
                        }
                        parseData.initSegmentData(8);
                        isSegmented = true;
                    }
                } else {
                    if (isRangeChar ? macFormat != MACAddressParseData.MACFormat.DASHED : macFormat != (isSpace ? MACAddressParseData.MACFormat.SPACE_DELIMITED : MACAddressParseData.MACFormat.COLON_DELIMITED)) {
                        throw new AddressStringException(str, "ipaddress.mac.error.mix.format.characters.at.index", index);
                    }
                    if (segCount >= (macOptions.addressSize == MACAddressStringParameters.AddressSize.MAC ? 6 : 8)) {
                        throw new AddressStringException(str, "ipaddress.error.too.many.segments");
                    }
                }
                hexMaxChars = 2;
            } else {
                if (segCount == 0) {
                    if (!validationOptions.allowIPv6) {
                        throw new AddressStringException(str, "ipaddress.error.ipv6");
                    }
                    canBeBase85 = false;
                    version = IPAddress.IPVersion.IPV6;
                    ipAddressParseData.setVersion(version);
                    IPv6AddressStringParameters iPv6AddressStringParameters = ipv6SpecificOptions;
                    isSegmented = true;
                    if (index == strStartIndex) {
                        int firstIndex = index++;
                        if (index == strEndIndex) {
                            throw new AddressStringException(str, "ipaddress.error.too.few.segments");
                        }
                        if (str.charAt(index) != ':') {
                            throw new AddressStringException(str, "ipaddress.error.ipv6.cannot.start.with.single.separator");
                        }
                        parseData.initSegmentData(8);
                        parseData.setConsecutiveSeparatorSegmentIndex(0);
                        parseData.setConsecutiveSeparatorIndex(firstIndex);
                        Validator.assign3Attributes(index, index, parseData, 0, index);
                        parseData.incrementSegmentCount();
                        segmentStartIndex = segmentValueStartIndex = ++index;
                        continue;
                    }
                    if (isSingleSegment) {
                        parseData.initSegmentData(1);
                    } else {
                        if (hexDelimiterIndex >= 0) {
                            throw new AddressStringException(str, hexDelimiterIndex);
                        }
                        parseData.initSegmentData(8);
                    }
                } else {
                    if (ipAddressParseData.getProviderIPVersion().isIPv4()) {
                        throw new AddressStringException(str, "ipaddress.error.ipv6.separator");
                    }
                    if (segCount >= 8) {
                        throw new AddressStringException(str, "ipaddress.error.too.many.segments");
                    }
                }
                hexMaxChars = 4;
            }
            if (index == segmentStartIndex) {
                if (isMac) {
                    throw new AddressStringException(str, "ipaddress.error.empty.segment.at.index", index);
                }
                if (parseData.getConsecutiveSeparatorIndex() >= 0) {
                    throw new AddressStringException(str, "ipaddress.error.ipv6.ambiguous");
                }
                parseData.setConsecutiveSeparatorSegmentIndex(segCount);
                parseData.setConsecutiveSeparatorIndex(index - 1);
                Validator.assign3Attributes(index, index, parseData, segCount, index);
                parseData.incrementSegmentCount();
            } else if (wildcardCount2 > 0 && !isSingleIPv6) {
                if (!var10_19.rangeOptions.allowsWildcard()) {
                    throw new AddressStringException(str, "ipaddress.error.no.wildcard");
                }
                int totalDigits4 = index - segmentStartIndex;
                if (wildcardCount2 != totalDigits4) throw new AddressStringException(str, index, true);
                if (hexDelimiterIndex >= 0) {
                    throw new AddressStringException(str, index, true);
                }
                parseData.setHasWildcard();
                int startIndex = index - wildcardCount2;
                Validator.assign6Attributes2Values1Flags(startIndex, index, startIndex, startIndex, index, startIndex, parseData, segCount, 0L, isMac ? (isDoubleSegment ? 0xFFFFFFL : 255L) : 65535L, 65536);
                parseData.incrementSegmentCount();
                wildcardCount2 = 0;
            } else {
                int midIndex;
                int startIndex = segmentValueStartIndex;
                int digitCount = index - startIndex;
                boolean noValuesToSet = false;
                long value = 0L;
                int flags = 0;
                int rangeFlags = 0;
                if (leadingWithZero) {
                    if (digitCount == 1) {
                        if (leadingZeroCount == 0 && rangeWildcardIndex < 0) {
                            Validator.assign3Attributes(startIndex, index, parseData, segCount, segmentValueStartIndex);
                            parseData.incrementSegmentCount();
                            segmentStartIndex = segmentValueStartIndex = ++index;
                            leadingWithZero = false;
                            continue;
                        }
                    } else {
                        if (hasDigits) {
                            ++leadingZeroCount;
                        }
                        startIndex += leadingZeroCount;
                        digitCount -= leadingZeroCount;
                    }
                    leadingWithZero = false;
                }
                if (leadingZeroCount == 0) {
                    if (digitCount == 0) {
                        if (!var10_19.rangeOptions.allowsInferredBoundary()) {
                            throw new AddressStringException(str, "ipaddress.error.empty.segment.at.index", index);
                        }
                        if (isMac) {
                            value = isSingleSegment ? (macAddressParseData.isExtended() ? -1L : 0xFFFFFFFFFFFFL) : 255L;
                        } else if (isSingleIPv6) {
                            value = -1L;
                            extendedValue = -1L;
                        } else {
                            value = 65535L;
                        }
                        rangeFlags = 0x400000;
                    } else {
                        if (digitCount > hexMaxChars && checkCharCounts) {
                            throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", segmentValueStartIndex);
                        }
                        if (singleWildcardCount > 0) {
                            noValuesToSet = true;
                            if (rangeWildcardIndex >= 0) {
                                throw new AddressStringException(str, index, true);
                            }
                            if (isSingleIPv6) {
                                Validator.parseSingleSegmentSingleWildcard16(currentValueHex, str, startIndex, index, singleWildcardCount, parseData, segCount, segmentValueStartIndex, (AddressStringParameters.AddressStringFormatParameters)var10_19);
                            } else {
                                Validator.assignSingleWildcard16(currentValueHex, str, startIndex, index, singleWildcardCount, parseData, segCount, segmentValueStartIndex, (AddressStringParameters.AddressStringFormatParameters)var10_19);
                            }
                            uppercase = false;
                            singleWildcardCount = 0;
                        } else if (isSingleIPv6) {
                            midIndex = index - 16;
                            if (startIndex < midIndex) {
                                extendedValue = Validator.parseLong16(str, startIndex, midIndex);
                                value = Validator.parseLong16(str, midIndex, index);
                            } else {
                                value = currentValueHex;
                            }
                        } else {
                            value = currentValueHex;
                            if (uppercase) {
                                uppercase = false;
                            } else {
                                flags = 262144;
                            }
                        }
                        hasDigits = false;
                        currentValueHex = 0L;
                    }
                } else if (leadingZeroCount == 1 && (digitCount == 17 || digitCount == 129) && ipv6SpecificOptions.allowBinary && Validator.isBinaryDelimiter(str, startIndex)) {
                    if (!var10_19.allowLeadingZeros) {
                        throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                    }
                    ++startIndex;
                    --digitCount;
                    if (singleWildcardCount > 0) {
                        if (rangeWildcardIndex >= 0) {
                            throw new AddressStringException(str, index, true);
                        }
                        if (isSingleIPv6) {
                            Validator.parseSingleSegmentSingleWildcard2(str, startIndex, index, singleWildcardCount, parseData, segCount, segmentValueStartIndex, (AddressStringParameters.AddressStringFormatParameters)var10_19);
                        } else {
                            Validator.switchSingleWildcard2(currentValueHex, str, startIndex, index, singleWildcardCount, parseData, segCount, segmentValueStartIndex, (AddressStringParameters.AddressStringFormatParameters)var10_19);
                        }
                        noValuesToSet = true;
                        singleWildcardCount = 0;
                    } else {
                        if (isSingleIPv6) {
                            midIndex = index - 64;
                            extendedValue = Validator.parseLong2(str, startIndex, midIndex);
                            value = Validator.parseLong2(str, midIndex, index);
                        } else {
                            value = Validator.switchValue2(currentValueHex, str, digitCount);
                        }
                        flags = 2;
                    }
                    ipAddressParseData.setHasBinaryDigits(true);
                    hasDigits = false;
                    currentValueHex = 0L;
                } else {
                    if (digitCount > hexMaxChars && checkCharCounts) {
                        throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", segmentValueStartIndex);
                    }
                    if (!var10_19.allowLeadingZeros) {
                        throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                    }
                    if (!var10_19.allowUnlimitedLeadingZeros && checkCharCounts && digitCount + leadingZeroCount > hexMaxChars) {
                        throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", segmentValueStartIndex);
                    }
                    if (singleWildcardCount > 0) {
                        noValuesToSet = true;
                        if (rangeWildcardIndex >= 0) {
                            throw new AddressStringException(str, index, true);
                        }
                        if (isSingleIPv6) {
                            Validator.parseSingleSegmentSingleWildcard16(currentValueHex, str, startIndex, index, singleWildcardCount, parseData, segCount, segmentValueStartIndex, (AddressStringParameters.AddressStringFormatParameters)var10_19);
                        } else {
                            Validator.assignSingleWildcard16(currentValueHex, str, startIndex, index, singleWildcardCount, parseData, segCount, segmentValueStartIndex, (AddressStringParameters.AddressStringFormatParameters)var10_19);
                        }
                        uppercase = false;
                        singleWildcardCount = 0;
                    } else if (isSingleIPv6) {
                        midIndex = index - 16;
                        if (startIndex < midIndex) {
                            extendedValue = Validator.parseLong16(str, startIndex, midIndex);
                            value = Validator.parseLong16(str, midIndex, index);
                        } else {
                            value = currentValueHex;
                        }
                    } else {
                        value = currentValueHex;
                        if (uppercase) {
                            uppercase = false;
                        } else {
                            flags = 262144;
                        }
                    }
                    hasDigits = false;
                    currentValueHex = 0L;
                }
                if (rangeWildcardIndex >= 0) {
                    boolean isReversed;
                    long front;
                    long extendedFront;
                    boolean frontEmpty;
                    frontStartIndex = rangeWildcardIndex - frontDigitCount;
                    frontEndIndex = rangeWildcardIndex;
                    frontLeadingZeroStartIndex = frontStartIndex - frontLeadingZeroCount;
                    int frontTotalDigitCount = frontDigitCount + frontLeadingZeroCount;
                    if (!var10_19.rangeOptions.allowsRangeSeparator()) {
                        throw new AddressStringException(str, "ipaddress.error.no.range");
                    }
                    if (frontHexDelimiterIndex >= 0 && !isSingleSegment) {
                        throw new AddressStringException(str, frontHexDelimiterIndex);
                    }
                    if (frontSingleWildcardCount > 0) throw new AddressStringException(str, rangeWildcardIndex, true);
                    if (frontWildcardCount > 0) {
                        throw new AddressStringException(str, rangeWildcardIndex, true);
                    }
                    if (isMac && !macSpecificOptions.allowShortSegments && frontTotalDigitCount < 2) {
                        throw new AddressStringException(str, "ipaddress.error.segment.too.short.at.index", frontLeadingZeroStartIndex);
                    }
                    int upperRadix = 0;
                    boolean frontIsBinary = false;
                    boolean bl = frontEmpty = frontStartIndex == frontEndIndex;
                    if (frontEmpty) {
                        if (!var10_19.rangeOptions.allowsInferredBoundary()) {
                            throw new AddressStringException(str, "ipaddress.error.empty.segment.at.index", index);
                        }
                        rangeFlags |= 0x200000;
                        extendedFront = 0L;
                        front = 0L;
                        isReversed = false;
                    } else if (frontLeadingZeroCount == 1 && frontDigitCount == 17 && ipv6SpecificOptions.allowBinary && Validator.isBinaryDelimiter(str, frontStartIndex)) {
                        ++frontStartIndex;
                        front = Validator.switchValue2(currentFrontValueHex, str, --frontDigitCount);
                        rangeFlags = 2;
                        upperRadix = 2;
                        ipAddressParseData.setHasBinaryDigits(true);
                        isReversed = Long.compareUnsigned(front, value) > 0;
                        extendedFront = 0L;
                        frontIsBinary = true;
                    } else if (isSingleIPv6) {
                        int frontMidIndex;
                        if (frontDigitCount == 129) {
                            ++frontStartIndex;
                            --frontDigitCount;
                            rangeFlags = 2;
                            upperRadix = 2;
                            ipAddressParseData.setHasBinaryDigits(true);
                            frontMidIndex = frontEndIndex - 64;
                            extendedFront = Validator.parseLong2(str, frontStartIndex, frontMidIndex);
                            front = Validator.parseLong2(str, frontMidIndex, frontEndIndex);
                        } else {
                            frontMidIndex = frontEndIndex - 16;
                            if (frontStartIndex < frontMidIndex) {
                                extendedFront = Validator.parseLong16(str, frontStartIndex, frontMidIndex);
                                front = Validator.parseLong16(str, frontMidIndex, frontEndIndex);
                            } else {
                                extendedFront = 0L;
                                front = currentFrontValueHex;
                            }
                        }
                        int frontCompare = Long.compareUnsigned(extendedFront, extendedValue);
                        isReversed = frontCompare > 0 || frontCompare == 0 && Long.compareUnsigned(front, value) > 0;
                    } else {
                        if (!var10_19.allowLeadingZeros && frontLeadingZeroCount > 0) {
                            throw new AddressStringException(str, "ipaddress.error.segment.leading.zeros");
                        }
                        if (checkCharCounts) {
                            if (frontDigitCount > hexMaxChars) {
                                throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", frontLeadingZeroStartIndex);
                            }
                            if (!var10_19.allowUnlimitedLeadingZeros && frontTotalDigitCount > hexMaxChars) {
                                throw new AddressStringException(str, "ipaddress.error.segment.too.long.at.index", frontLeadingZeroStartIndex);
                            }
                        }
                        isReversed = Long.compareUnsigned(front = currentFrontValueHex, value) > 0;
                        extendedFront = 0L;
                    }
                    int backEndIndex = index;
                    if (isReversed) {
                        if (!var10_19.rangeOptions.allowsReverseRange()) {
                            throw new AddressStringException(str, "ipaddress.error.invalidRange");
                        }
                        int tmp = frontStartIndex;
                        frontStartIndex = startIndex;
                        startIndex = tmp;
                        tmp = frontEndIndex;
                        frontEndIndex = backEndIndex;
                        backEndIndex = tmp;
                        tmp = frontLeadingZeroStartIndex;
                        frontLeadingZeroStartIndex = segmentValueStartIndex;
                        segmentValueStartIndex = tmp;
                        long tmpl = front;
                        front = value;
                        value = tmpl;
                        tmpl = extendedFront;
                        extendedFront = extendedValue;
                        extendedValue = tmpl;
                    }
                    if (isSingleIPv6) {
                        Validator.assign7Attributes4Values1Flags(frontStartIndex, frontEndIndex, frontLeadingZeroStartIndex, startIndex, backEndIndex, segmentValueStartIndex, parseData, segCount, front, extendedFront, value, extendedValue, rangeFlags | 0x100000, upperRadix);
                    } else {
                        if (!(frontUppercase || frontEmpty || isReversed || frontIsBinary)) {
                            rangeFlags = leadingZeroCount == 0 && (flags & 0x40000) != 0 && frontIsStandardRangeChar ? (rangeFlags |= 0xC0000) : (rangeFlags |= 0x40000);
                        }
                        Validator.assign7Attributes2Values1Flags(frontStartIndex, frontEndIndex, frontLeadingZeroStartIndex, startIndex, backEndIndex, segmentValueStartIndex, parseData, segCount, front, value, rangeFlags | 0x100000, upperRadix);
                    }
                    rangeWildcardIndex = -1;
                } else if (!noValuesToSet) {
                    if (isSingleIPv6) {
                        Validator.assign3Attributes2Values1Flags(startIndex, index, segmentValueStartIndex, parseData, segCount, value, extendedValue, flags);
                    } else {
                        Validator.assign3Attributes1Values1Flags(startIndex, index, segmentValueStartIndex, parseData, segCount, value, flags);
                    }
                }
                parseData.incrementSegmentCount();
                leadingZeroCount = 0;
            }
            segmentStartIndex = segmentValueStartIndex = ++index;
        }
    }

    private static boolean isBinaryDelimiter(CharSequence str, int index) {
        char c = str.charAt(index);
        return c == 'b' || c == 'B';
    }

    private static boolean isHexDelimiter(char c) {
        return c == 'x' || c == 'X';
    }

    private static boolean parseBase85(IPAddressStringParameters validationOptions, CharSequence str, int strStartIndex, int strEndIndex, IPAddressParseData ipAddressParseData, int extendedRangeWildcardIndex, int totalCharacterCount, int index) throws AddressStringException {
        AddressParseData parseData = ipAddressParseData.getAddressParseData();
        if (extendedRangeWildcardIndex < 0) {
            if (totalCharacterCount == 20) {
                if (!validationOptions.allowIPv6) {
                    throw new AddressStringException(str, "ipaddress.error.ipv6");
                }
                ipAddressParseData.setVersion(IPAddress.IPVersion.IPV6);
                BigInteger val = Validator.parseBase85(str, strStartIndex, strEndIndex);
                long value = val.and(LOW_BITS_MASK).longValue();
                BigInteger shift64 = val.shiftRight(64);
                long extendedValue = shift64.longValue();
                BigInteger shiftMore = shift64.shiftRight(64);
                if (!shiftMore.equals(BigInteger.ZERO)) {
                    throw new AddressStringException(str, "ipaddress.error.address.too.large");
                }
                parseData.initSegmentData(1);
                parseData.incrementSegmentCount();
                Validator.assign3Attributes2Values1Flags(strStartIndex, strEndIndex, strStartIndex, parseData, 0, value, extendedValue, 85);
                ipAddressParseData.setBase85(true);
                return true;
            }
        } else if (totalCharacterCount == 41 || totalCharacterCount == 21 && (extendedRangeWildcardIndex == 0 || extendedRangeWildcardIndex + 1 == strEndIndex)) {
            int upperEnd;
            int upperStart;
            int lowerEnd;
            int lowerStart;
            long extendedValue2;
            long value2;
            long extendedValue;
            long value;
            if (!validationOptions.allowIPv6) {
                throw new AddressStringException(str, "ipaddress.error.ipv6");
            }
            IPv6AddressStringParameters ipv6SpecificOptions = validationOptions.getIPv6Parameters();
            if (!ipv6SpecificOptions.rangeOptions.allowsRangeSeparator()) {
                throw new AddressStringException(str, "ipaddress.error.no.range");
            }
            ipAddressParseData.setVersion(IPAddress.IPVersion.IPV6);
            int frontEndIndex = extendedRangeWildcardIndex;
            int flags = 0;
            if (frontEndIndex == strStartIndex + 20) {
                BigInteger val = Validator.parseBase85(str, strStartIndex, frontEndIndex);
                value = val.and(LOW_BITS_MASK).longValue();
                BigInteger shift64 = val.shiftRight(64);
                extendedValue = shift64.longValue();
                if (frontEndIndex + 1 < strEndIndex) {
                    BigInteger val2 = Validator.parseBase85(str, frontEndIndex + 1, strEndIndex);
                    value2 = val2.and(LOW_BITS_MASK).longValue();
                    shift64 = val2.shiftRight(64);
                    extendedValue2 = shift64.longValue();
                    BigInteger shiftMoreVal2 = shift64.shiftRight(64);
                    if (val.compareTo(val2) > 0) {
                        BigInteger shiftMoreVal = shift64.shiftRight(64);
                        if (!ipv6SpecificOptions.rangeOptions.allowsReverseRange()) {
                            throw new AddressStringException(str, "ipaddress.error.invalidRange");
                        }
                        if (!shiftMoreVal.equals(BigInteger.ZERO)) {
                            throw new AddressStringException(str, "ipaddress.error.address.too.large");
                        }
                        lowerStart = frontEndIndex + 1;
                        lowerEnd = strEndIndex;
                        upperStart = strStartIndex;
                        upperEnd = frontEndIndex;
                    } else {
                        if (!shiftMoreVal2.equals(BigInteger.ZERO)) {
                            throw new AddressStringException(str, "ipaddress.error.address.too.large");
                        }
                        lowerStart = strStartIndex;
                        lowerEnd = frontEndIndex;
                        upperStart = frontEndIndex + 1;
                        upperEnd = strEndIndex;
                    }
                } else {
                    if (!ipv6SpecificOptions.rangeOptions.allowsInferredBoundary()) {
                        throw new AddressStringException(str, "ipaddress.error.empty.segment.at.index", index);
                    }
                    lowerStart = strStartIndex;
                    lowerEnd = frontEndIndex;
                    upperStart = upperEnd = strEndIndex;
                    extendedValue2 = -1L;
                    value2 = -1L;
                    flags = 0x400000;
                }
            } else if (frontEndIndex == 0) {
                if (!ipv6SpecificOptions.rangeOptions.allowsInferredBoundary()) {
                    throw new AddressStringException(str, "ipaddress.error.empty.segment.at.index", index);
                }
                lowerEnd = 0;
                lowerStart = 0;
                extendedValue = 0L;
                value = 0L;
                flags = 0x200000;
                BigInteger val2 = Validator.parseBase85(str, frontEndIndex + 1, strEndIndex);
                value2 = val2.and(LOW_BITS_MASK).longValue();
                BigInteger shift64 = val2.shiftRight(64);
                extendedValue2 = shift64.longValue();
                BigInteger shiftMoreVal2 = shift64.shiftRight(64);
                if (!shiftMoreVal2.equals(BigInteger.ZERO)) {
                    throw new AddressStringException(str, "ipaddress.error.address.too.large");
                }
                upperStart = 1;
                upperEnd = strEndIndex;
            } else {
                throw new AddressStringException(str, extendedRangeWildcardIndex);
            }
            parseData.incrementSegmentCount();
            parseData.initSegmentData(1);
            Validator.assign7Attributes4Values1Flags(lowerStart, lowerEnd, lowerStart, upperStart, upperEnd, upperStart, parseData, 0, value, extendedValue, value2, extendedValue2, 0x100055 | flags, 85);
            ipAddressParseData.setBase85(true);
            return true;
        }
        return false;
    }

    private static void checkSegments(String fullAddr, MACAddressStringParameters validationOptions, ParsedMACAddress parseData) throws AddressStringException {
        MACAddressParseData.MACFormat format = parseData.getFormat();
        if (format != null) {
            AddressParseData addressParseData = parseData.getAddressParseData();
            boolean hasWildcardSeparator = addressParseData.hasWildcard() && validationOptions.getFormatParameters().allowWildcardedSeparator;
            int segCount = addressParseData.getSegmentCount();
            if (format == MACAddressParseData.MACFormat.DOTTED) {
                if (segCount <= 3 && validationOptions.addressSize != MACAddressStringParameters.AddressSize.EUI64) {
                    if (!hasWildcardSeparator && segCount != 3) {
                        throw new AddressStringException((CharSequence)fullAddr, "ipaddress.error.too.few.segments");
                    }
                } else {
                    if (!hasWildcardSeparator && segCount < 4) {
                        throw new AddressStringException((CharSequence)fullAddr, "ipaddress.error.too.few.segments");
                    }
                    parseData.setExtended(true);
                }
            } else if (segCount > 2) {
                if (segCount <= 6 && validationOptions.addressSize != MACAddressStringParameters.AddressSize.EUI64) {
                    if (!hasWildcardSeparator && segCount != 6) {
                        throw new AddressStringException((CharSequence)fullAddr, "ipaddress.error.too.few.segments");
                    }
                } else {
                    if (!hasWildcardSeparator && segCount < 8) {
                        throw new AddressStringException((CharSequence)fullAddr, "ipaddress.error.too.few.segments");
                    }
                    parseData.setExtended(true);
                }
                if (parseData.getFormat() == MACAddressParseData.MACFormat.DASHED) {
                    for (int i = 0; i < segCount; ++i) {
                        Validator.checkSegmentMaxValues(fullAddr, addressParseData, i, validationOptions.getFormatParameters(), 255L, 2, 2);
                    }
                }
            } else {
                if (parseData.getFormat() == MACAddressParseData.MACFormat.DASHED) {
                    if (parseData.isDoubleSegment()) {
                        MACAddressStringParameters.MACAddressStringFormatParameters params = validationOptions.getFormatParameters();
                        Validator.checkSegmentMaxValues(fullAddr, addressParseData, 0, params, 0xFFFFFFL, 6, 6);
                        if (parseData.isExtended()) {
                            Validator.checkSegmentMaxValues(fullAddr, addressParseData, 1, params, 0xFFFFFFFFFFL, 10, 10);
                        } else {
                            Validator.checkSegmentMaxValues(fullAddr, addressParseData, 1, params, 0xFFFFFFL, 6, 6);
                        }
                    }
                } else if (!hasWildcardSeparator) {
                    throw new AddressStringException((CharSequence)fullAddr, "ipaddress.error.too.few.segments");
                }
                if (validationOptions.addressSize == MACAddressStringParameters.AddressSize.EUI64) {
                    parseData.setExtended(true);
                }
            }
        }
    }

    private static MACAddressProvider chooseMACAddressProvider(MACAddressString fromString, MACAddressStringParameters validationOptions, ParsedMACAddress pa, AddressParseData addressParseData) throws AddressStringException {
        if (addressParseData.isProvidingEmpty()) {
            return MACAddressProvider.EMPTY_PROVIDER;
        }
        if (addressParseData.isAll()) {
            return MACAddressProvider.getAllProvider(validationOptions);
        }
        Validator.checkSegments(fromString.toString(), fromString.getValidationOptions(), pa);
        return pa;
    }

    private static IPAddressProvider chooseIPAddressProvider(HostIdentifierString originator, CharSequence fullAddr, IPAddressStringParameters validationOptions, ParsedIPAddress parseData, ParsedHostIdentifierStringQualifier qualifier) throws AddressStringException {
        IPAddress.IPVersion version = parseData.getProviderIPVersion();
        if (version == null) {
            version = qualifier.inferVersion(validationOptions);
            IPAddress.IPVersion optionsVersion = validationOptions.inferVersion();
            if (version == null) {
                version = optionsVersion;
                parseData.setVersion(version);
            } else if (optionsVersion != null && !version.equals((Object)optionsVersion)) {
                throw new AddressStringException(fullAddr, version == IPAddress.IPVersion.IPV6 ? "ipaddress.error.ipv6" : "ipaddress.error.ipv4");
            }
            AddressParseData addressParseData = parseData.getAddressParseData();
            if (addressParseData.isProvidingEmpty()) {
                Integer networkPrefixLength = qualifier.getNetworkPrefixLength();
                if (networkPrefixLength != null) {
                    int prefLen = networkPrefixLength;
                    if (validationOptions == IPAddressString.DEFAULT_VALIDATION_OPTIONS && networkPrefixLength <= 128) {
                        IPAddressProvider.MaskCreator result;
                        int index = version == null ? 0 : (version.isIPv4() ? 1 : 2);
                        IPAddressProvider.MaskCreator[] cached = MASK_CACHE[index];
                        if (cached == null) {
                            cached = new IPAddressProvider.MaskCreator[129];
                            Validator.MASK_CACHE[index] = cached;
                        }
                        if ((result = cached[prefLen]) == null) {
                            cached[prefLen] = result = new IPAddressProvider.MaskCreator(networkPrefixLength, version, IPAddressString.DEFAULT_VALIDATION_OPTIONS);
                        }
                        return result;
                    }
                    return new IPAddressProvider.MaskCreator(networkPrefixLength, version, validationOptions);
                }
                if (validationOptions.emptyIsLoopback) {
                    if (validationOptions == IPAddressString.DEFAULT_VALIDATION_OPTIONS) {
                        return LOOPBACK_CACHE;
                    }
                    return new IPAddressProvider.LoopbackCreator(validationOptions);
                }
                return IPAddressProvider.EMPTY_PROVIDER;
            }
            return new IPAddressProvider.AllCreator(qualifier, version, originator, validationOptions);
        }
        if (parseData.isZoned() && version.isIPv4()) {
            throw new AddressStringException(fullAddr, "ipaddress.error.only.ipv6.has.zone");
        }
        parseData.setQualifier(qualifier);
        Validator.checkSegments(fullAddr, validationOptions, parseData);
        return parseData;
    }

    private static void checkSegmentMaxValues(CharSequence fullAddr, AddressParseData parseData, int segmentIndex, AddressStringParameters.AddressStringFormatParameters params, long maxValue, int maxDigitCount, int maxUpperDigitCount) throws AddressStringException {
        if (parseData.getFlag(segmentIndex, 131072)) {
            long value = parseData.getValue(segmentIndex, 2);
            if (value > maxValue) {
                throw new AddressStringException(fullAddr, "ipaddress.error.ipv4.segment.too.large");
            }
            if (parseData.getValue(segmentIndex, 10) > maxValue) {
                parseData.setValue(segmentIndex, 10, maxValue);
            }
            if (!params.allowUnlimitedLeadingZeros) {
                int lowerRadix = parseData.getRadix(segmentIndex, 0);
                if (parseData.getIndex(segmentIndex, 7) - parseData.getIndex(segmentIndex, 1) - Validator.getStringPrefixCharCount(lowerRadix) > maxDigitCount) {
                    throw new AddressStringException(fullAddr, "ipaddress.error.segment.too.long");
                }
            }
        } else {
            long value = parseData.getValue(segmentIndex, 10);
            if (value > maxValue) {
                throw new AddressStringException(fullAddr, "ipaddress.error.ipv4.segment.too.large");
            }
            if (!params.allowUnlimitedLeadingZeros) {
                int lowerRadix = parseData.getRadix(segmentIndex, 0);
                int lowerEndIndex = parseData.getIndex(segmentIndex, 7);
                int upperEndIndex = parseData.getIndex(segmentIndex, 15);
                if (lowerEndIndex - parseData.getIndex(segmentIndex, 1) - Validator.getStringPrefixCharCount(lowerRadix) > maxDigitCount) {
                    throw new AddressStringException(fullAddr, "ipaddress.error.segment.too.long");
                }
                if (lowerEndIndex != upperEndIndex) {
                    int upperRadix = parseData.getRadix(segmentIndex, 8);
                    if (upperEndIndex - parseData.getIndex(segmentIndex, 9) - Validator.getStringPrefixCharCount(upperRadix) > maxUpperDigitCount) {
                        throw new AddressStringException(fullAddr, "ipaddress.error.segment.too.long");
                    }
                }
            }
        }
    }

    private static void checkSegments(CharSequence fullAddr, IPAddressStringParameters validationOptions, IPAddressParseData parseData) throws AddressStringException {
        AddressParseData addressParseData = parseData.getAddressParseData();
        int segCount = addressParseData.getSegmentCount();
        IPAddress.IPVersion version = parseData.getProviderIPVersion();
        if (version.isIPv4()) {
            boolean hasWildcardSeparator;
            int missingCount = 4 - segCount;
            IPv4AddressStringParameters ipv4Options = validationOptions.getIPv4Parameters();
            boolean bl = hasWildcardSeparator = addressParseData.hasWildcard() && ipv4Options.allowWildcardedSeparator;
            if (missingCount > 0 && segCount > 1) {
                if (ipv4Options.inet_aton_joinedSegments) {
                    parseData.set_inet_aton_joined(true);
                } else if (!hasWildcardSeparator) {
                    throw new AddressStringException(fullAddr, "ipaddress.error.ipv4.too.few.segments");
                }
            }
            boolean notUnlimitedLength = !ipv4Options.allowUnlimitedLeadingZeros;
            boolean hasMissingSegs = missingCount > 0 && ipv4Options.inet_aton_joinedSegments;
            for (int i = 0; i < segCount; ++i) {
                int maxDigitCount;
                int lowerRadix;
                long value;
                long max;
                if (hasMissingSegs && i == segCount - 1) {
                    max = Validator.getMaxIPv4Value(missingCount + 1);
                    if (addressParseData.isInferredUpperBoundary(i)) {
                        parseData.setValue(i, 10, max);
                        continue;
                    }
                } else {
                    max = 255L;
                }
                if (parseData.getFlag(i, 131072)) {
                    value = parseData.getValue(i, 2);
                    if (value > max) {
                        throw new AddressStringException(fullAddr, "ipaddress.error.ipv4.segment.too.large");
                    }
                    if (parseData.getValue(i, 10) > max) {
                        parseData.setValue(i, 10, max);
                    }
                    if (!notUnlimitedLength) continue;
                    lowerRadix = addressParseData.getRadix(i, 0);
                    maxDigitCount = Validator.getMaxIPv4StringLength(missingCount, lowerRadix);
                    if (parseData.getIndex(i, 7) - parseData.getIndex(i, 1) - Validator.getStringPrefixCharCount(lowerRadix) <= maxDigitCount) continue;
                    throw new AddressStringException(fullAddr, "ipaddress.error.segment.too.long");
                }
                value = parseData.getValue(i, 10);
                if (value > max) {
                    throw new AddressStringException(fullAddr, "ipaddress.error.ipv4.segment.too.large");
                }
                if (!notUnlimitedLength) continue;
                lowerRadix = addressParseData.getRadix(i, 0);
                maxDigitCount = Validator.getMaxIPv4StringLength(missingCount, lowerRadix);
                int lowerEndIndex = parseData.getIndex(i, 7);
                int upperEndIndex = parseData.getIndex(i, 15);
                if (lowerEndIndex - parseData.getIndex(i, 1) - Validator.getStringPrefixCharCount(lowerRadix) > maxDigitCount) {
                    throw new AddressStringException(fullAddr, "ipaddress.error.segment.too.long");
                }
                if (lowerEndIndex == upperEndIndex) continue;
                int upperRadix = parseData.getRadix(i, 8);
                int maxUpperDigitCount = Validator.getMaxIPv4StringLength(missingCount, upperRadix);
                if (upperEndIndex - parseData.getIndex(i, 9) - Validator.getStringPrefixCharCount(upperRadix) <= maxUpperDigitCount) continue;
                throw new AddressStringException(fullAddr, "ipaddress.error.segment.too.long");
            }
        } else {
            boolean hasWildcardSeparator;
            int totalSegmentCount = segCount;
            if (parseData.isProvidingMixedIPv6()) {
                totalSegmentCount += 2;
            }
            boolean bl = hasWildcardSeparator = addressParseData.hasWildcard() && validationOptions.getIPv6Parameters().allowWildcardedSeparator;
            if (!hasWildcardSeparator && totalSegmentCount != 1 && totalSegmentCount < 8 && !parseData.isCompressed()) {
                throw new AddressStringException(fullAddr, "ipaddress.error.too.few.segments");
            }
        }
    }

    @Override
    public int validatePrefix(CharSequence fullAddr, IPAddress.IPVersion version) throws AddressStringException {
        return this.validatePrefixLenString(fullAddr, version);
    }

    public int validatePrefixLenString(CharSequence fullAddr, IPAddress.IPVersion version) throws AddressStringException {
        ParsedHostIdentifierStringQualifier qualifier = Validator.validatePrefix(fullAddr, null, IPAddressString.DEFAULT_VALIDATION_OPTIONS, null, 0, fullAddr.length(), version);
        if (qualifier == null) {
            throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.error.invalidCIDRPrefix");
        }
        return qualifier.getNetworkPrefixLength();
    }

    private static ParsedHostIdentifierStringQualifier parsePortOrService(CharSequence fullAddr, CharSequence zone, HostNameParameters validationOptions, int index, int endIndex) throws AddressStringException {
        boolean isPort = true;
        boolean hasLetter = false;
        boolean hasDigits = false;
        boolean isAll = false;
        int charCount = 0;
        int digitCount = 0;
        int lastHyphen = -1;
        int result = 0;
        int[] charArray = chars;
        for (int i = index; i < endIndex; ++i) {
            char c = fullAddr.charAt(i);
            if (c >= '1' && c <= '9') {
                if (isPort) {
                    if (++digitCount > 5) {
                        isPort = false;
                    } else {
                        hasDigits = true;
                        result = result * 10 + charArray[c];
                    }
                }
                ++charCount;
                continue;
            }
            if (c == '0') {
                if (isPort && hasDigits) {
                    if (++digitCount > 5) {
                        isPort = false;
                    } else {
                        result *= 10;
                    }
                }
                ++charCount;
                continue;
            }
            isPort = false;
            boolean isHyphen = false;
            if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || (isHyphen = c == '-') || (isAll = c == '*')) {
                if (isHyphen) {
                    if (i == index) {
                        throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.host.error.invalid.service.hyphen.start");
                    }
                    if (i - 1 == lastHyphen) {
                        throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.host.error.invalid.service.hyphen.consecutive");
                    }
                    if (i == endIndex - 1) {
                        throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.host.error.invalid.service.hyphen.end");
                    }
                    lastHyphen = i;
                } else {
                    if (isAll) {
                        if (i > index) {
                            throw new AddressStringException((CharSequence)fullAddr.toString(), i, true);
                        }
                        if (i + 1 < endIndex) {
                            throw new AddressStringException((CharSequence)fullAddr.toString(), i + 1, true);
                        }
                        hasLetter = true;
                        ++charCount;
                        break;
                    }
                    hasLetter = true;
                }
                ++charCount;
                continue;
            }
            throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.host.error.invalid.port.service", i);
        }
        if (isPort) {
            if (!validationOptions.allowPort) {
                throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.host.error.port");
            }
            if (result == 0) {
                throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.host.error.invalidPort.no.digits");
            }
            if (result > 65535) {
                throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.host.error.invalidPort.too.large");
            }
            return new ParsedHostIdentifierStringQualifier(zone, result);
        }
        if (!validationOptions.allowService) {
            throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.host.error.service");
        }
        if (charCount == 0) {
            throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.host.error.invalidService.no.chars");
        }
        if (charCount > 15) {
            throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.host.error.invalidService.too.long");
        }
        if (!hasLetter) {
            throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.host.error.invalidService.no.letter");
        }
        CharSequence service2 = fullAddr.subSequence(index, endIndex);
        return new ParsedHostIdentifierStringQualifier(zone, service2);
    }

    private static ParsedHostIdentifierStringQualifier parseValidatedPrefix(int result, CharSequence fullAddr, CharSequence zone, IPAddressStringParameters validationOptions, int digitCount, int leadingZeros, IPAddress.IPVersion ipVersion) throws AddressStringException {
        boolean tryCache;
        boolean allowPrefixesBeyondAddressSize;
        boolean asIPv4;
        if (digitCount == 0) {
            --leadingZeros;
            ++digitCount;
        }
        boolean bl = asIPv4 = ipVersion != null && ipVersion.isIPv4();
        if (asIPv4) {
            if (leadingZeros > 0 && !validationOptions.getIPv4Parameters().allowPrefixLengthLeadingZeros) {
                throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.error.ipv4.prefix.leading.zeros");
            }
            allowPrefixesBeyondAddressSize = validationOptions.getIPv4Parameters().allowPrefixesBeyondAddressSize;
            if (!allowPrefixesBeyondAddressSize && result > 32) {
                if (validationOptions.allowSingleSegment) {
                    return null;
                }
                throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.error.prefixSize");
            }
            tryCache = result < PREFIX_CACHE.length;
        } else {
            if (leadingZeros > 0 && !validationOptions.getIPv6Parameters().allowPrefixLengthLeadingZeros) {
                throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.error.ipv6.prefix.leading.zeros");
            }
            allowPrefixesBeyondAddressSize = validationOptions.getIPv6Parameters().allowPrefixesBeyondAddressSize;
            if (!allowPrefixesBeyondAddressSize && result > 128) {
                throw new AddressStringException((CharSequence)fullAddr.toString(), "ipaddress.error.prefixSize");
            }
            boolean bl2 = tryCache = zone == null && result < PREFIX_CACHE.length;
        }
        if (tryCache) {
            ParsedHostIdentifierStringQualifier qual = PREFIX_CACHE[result];
            if (qual == null) {
                qual = Validator.PREFIX_CACHE[result] = new ParsedHostIdentifierStringQualifier(Validator.cacheBits(result), null);
            }
            return qual;
        }
        return new ParsedHostIdentifierStringQualifier(Validator.cacheBits(result), zone);
    }

    private static Integer cacheBits(int i) {
        return ParsedAddressGrouping.cache(i);
    }

    private static ParsedHostIdentifierStringQualifier validatePrefix(CharSequence fullAddr, CharSequence zone, IPAddressStringParameters validationOptions, HostNameParameters hostValidationOptions, int index, int endIndex, IPAddress.IPVersion ipVersion) throws AddressStringException {
        if (index == fullAddr.length()) {
            return null;
        }
        boolean isPrefix = true;
        int prefixEndIndex = endIndex;
        int leadingZeros = 0;
        boolean hasDigits = false;
        int result = 0;
        int[] charArray = chars;
        ParsedHostIdentifierStringQualifier portQualifier = null;
        for (int i = index; i < endIndex; ++i) {
            char c = fullAddr.charAt(i);
            if (c >= '1' && c <= '9') {
                hasDigits = true;
                result = result * 10 + charArray[c];
                continue;
            }
            if (c == '0') {
                if (hasDigits) {
                    result *= 10;
                    continue;
                }
                ++leadingZeros;
                continue;
            }
            if (c == ':' && hostValidationOptions != null && (hostValidationOptions.allowPort || hostValidationOptions.allowService) && i > index) {
                try {
                    portQualifier = Validator.parsePortOrService(fullAddr, zone, hostValidationOptions, i + 1, endIndex);
                    prefixEndIndex = i;
                    break;
                }
                catch (AddressStringException e) {
                    return null;
                }
            }
            isPrefix = false;
            break;
        }
        if (isPrefix) {
            ParsedHostIdentifierStringQualifier prefixQualifier = Validator.parseValidatedPrefix(result, fullAddr, zone, validationOptions, prefixEndIndex - index, leadingZeros, ipVersion);
            if (portQualifier != null) {
                portQualifier.overridePrefix(prefixQualifier);
                return portQualifier;
            }
            return prefixQualifier;
        }
        return null;
    }

    private static ParsedHostIdentifierStringQualifier parseAddressQualifier(CharSequence fullAddr, IPAddressStringParameters validationOptions, HostNameParameters hostValidationOptions, IPAddressParseData ipAddressParseData, int endIndex) throws AddressStringException {
        int qualifierIndex = ipAddressParseData.getQualifierIndex();
        boolean addressIsEmpty = ipAddressParseData.getAddressParseData().isProvidingEmpty();
        IPAddress.IPVersion ipVersion = ipAddressParseData.getProviderIPVersion();
        if (ipAddressParseData.hasPrefixSeparator()) {
            return Validator.parsePrefix(fullAddr, null, validationOptions, hostValidationOptions, addressIsEmpty, qualifierIndex, endIndex, ipVersion);
        }
        if (ipAddressParseData.isZoned()) {
            if (ipAddressParseData.isBase85Zoned() && !ipAddressParseData.isProvidingBase85IPv6()) {
                throw new AddressStringException(fullAddr, qualifierIndex - 1);
            }
            if (addressIsEmpty) {
                throw new AddressStringException(fullAddr, "ipaddress.error.only.zone");
            }
            return Validator.parseZone(fullAddr, validationOptions, addressIsEmpty, qualifierIndex, endIndex, ipVersion);
        }
        return ParsedHost.NO_QUALIFIER;
    }

    private static ParsedHostIdentifierStringQualifier parseHostAddressQualifier(CharSequence fullAddr, IPAddressStringParameters validationOptions, HostNameParameters hostValidationOptions, boolean isPrefixed, boolean hasPort, IPAddressParseData ipAddressParseData, int qualifierIndex, int endIndex) throws AddressStringException {
        boolean addressIsEmpty = ipAddressParseData.getAddressParseData().isProvidingEmpty();
        IPAddress.IPVersion ipVersion = ipAddressParseData.getProviderIPVersion();
        if (isPrefixed) {
            return Validator.parsePrefix(fullAddr, null, validationOptions, hostValidationOptions, addressIsEmpty, qualifierIndex, endIndex, ipVersion);
        }
        if (ipAddressParseData.isZoned()) {
            if (addressIsEmpty) {
                throw new AddressStringException(fullAddr, "ipaddress.error.only.zone");
            }
            return Validator.parseEncodedZone(fullAddr, validationOptions, addressIsEmpty, qualifierIndex, endIndex, ipVersion);
        }
        if (hasPort) {
            return Validator.parsePortOrService(fullAddr, null, hostValidationOptions, qualifierIndex, endIndex);
        }
        return ParsedHost.NO_QUALIFIER;
    }

    private static ParsedHostIdentifierStringQualifier parsePrefix(CharSequence fullAddr, CharSequence zone, IPAddressStringParameters validationOptions, HostNameParameters hostValidationOptions, boolean addressIsEmpty, int index, int endIndex, IPAddress.IPVersion ipVersion) throws AddressStringException {
        ParsedHostIdentifierStringQualifier qualifier;
        if (validationOptions.allowPrefix && (qualifier = Validator.validatePrefix(fullAddr, zone, validationOptions, hostValidationOptions, index, endIndex, ipVersion)) != null) {
            return qualifier;
        }
        if (addressIsEmpty) {
            throw new AddressStringException(fullAddr, "ipaddress.error.invalid.mask.address.empty");
        }
        if (validationOptions.allowMask) {
            try {
                IPAddressStringParameters maskOptions = Validator.toMaskOptions(validationOptions, ipVersion);
                ParsedIPAddress pa = new ParsedIPAddress(null, fullAddr, maskOptions);
                Validator.validateIPAddress(maskOptions, fullAddr, index, endIndex, pa, false);
                AddressParseData maskParseData = pa.getAddressParseData();
                if (maskParseData.isProvidingEmpty()) {
                    throw new AddressStringException(fullAddr, "ipaddress.error.invalid.mask.empty");
                }
                if (maskParseData.isAll()) {
                    throw new AddressStringException(fullAddr, "ipaddress.error.invalid.mask.wildcard");
                }
                Validator.checkSegments(fullAddr, maskOptions, pa);
                int maskEndIndex = maskParseData.getAddressEndIndex();
                if (maskEndIndex != endIndex) {
                    throw new AddressStringException(fullAddr, "ipaddress.error.invalid.mask.extra.chars", maskEndIndex + 1);
                }
                IPAddress.IPVersion maskVersion = pa.getProviderIPVersion();
                if (maskVersion.isIPv4() && maskParseData.getSegmentCount() == 1 && !maskParseData.hasWildcard() && !validationOptions.getIPv4Parameters().inet_aton_single_segment_mask) {
                    throw new AddressStringException(fullAddr, "ipaddress.error.mask.single.segment");
                }
                if (ipVersion != null && (maskVersion.isIPv4() != ipVersion.isIPv4() || maskVersion.isIPv6() != ipVersion.isIPv6())) {
                    throw new AddressStringException(fullAddr, "ipaddress.error.ipMismatch");
                }
                return new ParsedHostIdentifierStringQualifier(pa, zone);
            }
            catch (AddressStringException e) {
                throw new AddressStringException(fullAddr, "ipaddress.error.invalidCIDRPrefixOrMask", e);
            }
        }
        throw new AddressStringException(fullAddr, validationOptions.allowPrefix ? "ipaddress.error.invalidCIDRPrefixOrMask" : "ipaddress.error.CIDRNotAllowed");
    }

    private static ParsedHostIdentifierStringQualifier parseHostNameQualifier(CharSequence fullAddr, IPAddressStringParameters validationOptions, HostNameParameters hostValidationOptions, boolean isPrefixed, boolean isPort, boolean addressIsEmpty, int index, int endIndex, IPAddress.IPVersion ipVersion) throws AddressStringException {
        if (isPrefixed) {
            return Validator.parsePrefix(fullAddr, null, validationOptions, hostValidationOptions, addressIsEmpty, index, endIndex, ipVersion);
        }
        if (isPort) {
            return Validator.parsePortOrService(fullAddr, null, hostValidationOptions, index, endIndex);
        }
        return ParsedHost.NO_QUALIFIER;
    }

    public static int validateZone(CharSequence zone) {
        for (int i = 0; i < zone.length(); ++i) {
            char c = zone.charAt(i);
            if (c == '/') {
                return i;
            }
            if (c != ':') continue;
            return i;
        }
        return -1;
    }

    public static boolean isReserved(char c) {
        boolean isUnreserved = c >= '0' && c <= '9' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c == '-' || c == '.' || c == '_' || c == '~';
        return !isUnreserved;
    }

    private static ParsedHostIdentifierStringQualifier parseZone(CharSequence fullAddr, IPAddressStringParameters validationOptions, boolean addressIsEmpty, int index, int endIndex, IPAddress.IPVersion ipVersion) throws AddressStringException {
        if (index == endIndex && !validationOptions.getIPv6Parameters().allowEmptyZone) {
            throw new AddressStringException(fullAddr, "ipaddress.error.invalid.zone", index);
        }
        for (int i = index; i < endIndex; ++i) {
            char c = fullAddr.charAt(i);
            if (c == '/') {
                if (i == index && !validationOptions.getIPv6Parameters().allowEmptyZone) {
                    throw new AddressStringException(fullAddr, "ipaddress.error.invalid.zone", index);
                }
                CharSequence zone = fullAddr.subSequence(index, i);
                return Validator.parsePrefix(fullAddr, zone, validationOptions, null, addressIsEmpty, i + 1, endIndex, ipVersion);
            }
            if (c != ':') continue;
            throw new AddressStringException(fullAddr, "ipaddress.error.invalid.zone", i);
        }
        return new ParsedHostIdentifierStringQualifier(fullAddr.subSequence(index, endIndex));
    }

    private static ParsedHostIdentifierStringQualifier parseEncodedZone(CharSequence fullAddr, IPAddressStringParameters validationOptions, boolean addressIsEmpty, int index, int endIndex, IPAddress.IPVersion ipVersion) throws AddressStringException {
        if (index == endIndex && !validationOptions.getIPv6Parameters().allowEmptyZone) {
            throw new AddressStringException(fullAddr, "ipaddress.error.invalid.zone", index);
        }
        StringBuilder result = null;
        for (int i = index; i < endIndex; ++i) {
            char c = fullAddr.charAt(i);
            if (c == '%') {
                if (i + 2 >= endIndex) {
                    throw new AddressStringException(fullAddr, "ipaddress.error.invalid.zone.encoding", i);
                }
                if (result == null) {
                    result = new StringBuilder(endIndex - index);
                    result.append(fullAddr, index, i);
                }
                int[] charArray = chars;
                c = (char)(charArray[fullAddr.charAt(++i)] << 4);
                c = (char)(c | charArray[fullAddr.charAt(++i)]);
            } else {
                if (c == '/') {
                    if (i == index && !validationOptions.getIPv6Parameters().allowEmptyZone) {
                        throw new AddressStringException(fullAddr, "ipaddress.error.invalid.zone", index);
                    }
                    StringBuilder zone = result != null ? result : fullAddr.subSequence(index, i);
                    return Validator.parsePrefix(fullAddr, zone, validationOptions, null, addressIsEmpty, i + 1, endIndex, ipVersion);
                }
                if (Validator.isReserved(c)) {
                    throw new AddressStringException(fullAddr, "ipaddress.error.invalid.zone", i);
                }
            }
            if (result == null) continue;
            result.append(c);
        }
        if (result == null) {
            return new ParsedHostIdentifierStringQualifier(fullAddr.subSequence(index, endIndex));
        }
        return new ParsedHostIdentifierStringQualifier(result);
    }

    private static IPAddressStringParameters toMaskOptions(IPAddressStringParameters validationOptions, IPAddress.IPVersion ipVersion) {
        IPAddressStringParameters.Builder builder = null;
        if (ipVersion == null || ipVersion.isIPv6()) {
            IPv6AddressStringParameters ipv6Options = validationOptions.getIPv6Parameters();
            if (!ipv6Options.rangeOptions.isNoRange()) {
                builder = validationOptions.toBuilder();
                builder.getIPv6AddressParametersBuilder().setRangeOptions(AddressStringParameters.RangeParameters.NO_RANGE);
            }
            if (ipv6Options.allowMixed && !ipv6Options.getMixedParameters().getIPv4Parameters().rangeOptions.isNoRange()) {
                if (builder == null) {
                    builder = validationOptions.toBuilder();
                }
                builder.getIPv6AddressParametersBuilder().setRangeOptions(AddressStringParameters.RangeParameters.NO_RANGE);
            }
        }
        if (ipVersion == null || ipVersion.isIPv4()) {
            IPv4AddressStringParameters ipv4Options = validationOptions.getIPv4Parameters();
            if (!ipv4Options.rangeOptions.isNoRange()) {
                if (builder == null) {
                    builder = validationOptions.toBuilder();
                }
                builder.getIPv4AddressParametersBuilder().setRangeOptions(AddressStringParameters.RangeParameters.NO_RANGE);
            }
        }
        if (validationOptions.allowAll) {
            if (builder == null) {
                builder = validationOptions.toBuilder();
            }
            builder.allowAll(false);
        }
        IPAddressStringParameters maskOptions = builder == null ? validationOptions : builder.toParams();
        return maskOptions;
    }

    private static void assign3Attributes(int start2, int end, AddressParseData parseData, int parsedSegIndex, int leadingZeroStartIndex) {
        parseData.setIndex(parsedSegIndex, 1, leadingZeroStartIndex, 6, start2, 7, end, 9, leadingZeroStartIndex, 14, start2, 15, end);
    }

    private static void assign4Attributes(int start2, int end, AddressParseData parseData, int parsedSegIndex, int radix, int leadingZeroStartIndex) {
        parseData.set7IndexFlags(parsedSegIndex, 0, radix, 1, leadingZeroStartIndex, 6, start2, 7, end, 9, leadingZeroStartIndex, 14, start2, 15, end);
    }

    private static void assign7Attributes4Values1Flags(int frontStart, int frontEnd, int frontLeadingZeroStartIndex, int start2, int end, int leadingZeroStartIndex, AddressParseData parseData, int parsedSegIndex, long frontValue, long frontExtendedValue, long value, long extendedValue, int flags, int upperRadix) {
        parseData.set8Index4ValuesFlags(parsedSegIndex, 0, flags, 1, frontLeadingZeroStartIndex, 6, frontStart, 7, frontEnd, 8, upperRadix, 9, leadingZeroStartIndex, 14, start2, 15, end, 2, frontValue, 4, frontExtendedValue, 10, value, 12, extendedValue);
    }

    private static void assign6Attributes4Values1Flags(int frontStart, int frontEnd, int frontLeadingZeroStartIndex, int start2, int end, int leadingZeroStartIndex, AddressParseData parseData, int parsedSegIndex, long frontValue, long frontExtendedValue, long value, long extendedValue, int flags) {
        parseData.set7Index4ValuesFlags(parsedSegIndex, 0, flags, 1, frontLeadingZeroStartIndex, 6, frontStart, 7, frontEnd, 9, leadingZeroStartIndex, 14, start2, 15, end, 2, frontValue, 4, frontExtendedValue, 10, value, 12, extendedValue);
    }

    private static void assign6Attributes2Values1Flags(int frontStart, int frontEnd, int frontLeadingZeroStartIndex, int start2, int end, int leadingZeroStartIndex, AddressParseData parseData, int parsedSegIndex, long frontValue, long value, int flags) {
        parseData.set7Index2ValuesFlags(parsedSegIndex, 0, flags, 1, frontLeadingZeroStartIndex, 6, frontStart, 7, frontEnd, 9, leadingZeroStartIndex, 14, start2, 15, end, 2, frontValue, 10, value);
    }

    private static void assign7Attributes2Values1Flags(int frontStart, int frontEnd, int frontLeadingZeroStartIndex, int start2, int end, int leadingZeroStartIndex, AddressParseData parseData, int parsedSegIndex, long frontValue, long value, int flags, int upperRadix) {
        parseData.set8Index2ValuesFlags(parsedSegIndex, 0, flags, 1, frontLeadingZeroStartIndex, 6, frontStart, 7, frontEnd, 8, upperRadix, 9, leadingZeroStartIndex, 14, start2, 15, end, 2, frontValue, 10, value);
    }

    private static void assign3Attributes2Values1Flags(int start2, int end, int leadingZeroStart, AddressParseData parseData, int parsedSegIndex, long value, long extendedValue, int flags) {
        parseData.set7Index4ValuesFlags(parsedSegIndex, 0, flags, 1, leadingZeroStart, 6, start2, 7, end, 9, leadingZeroStart, 14, start2, 15, end, 2, value, 4, extendedValue, 10, value, 12, extendedValue);
    }

    private static void assign3Attributes1Values1Flags(int start2, int end, int leadingZeroStart, AddressParseData parseData, int parsedSegIndex, long value, int flags) {
        parseData.set7Index2ValuesFlags(parsedSegIndex, 0, flags, 9, leadingZeroStart, 1, leadingZeroStart, 14, start2, 6, start2, 15, end, 7, end, 2, value, 10, value);
    }

    private static void checkSingleWildcard(CharSequence str, int start2, int end, int digitsEnd, AddressStringParameters.AddressStringFormatParameters options) throws AddressStringException {
        if (!options.rangeOptions.allowsSingleWildcard()) {
            throw new AddressStringException(str, "ipaddress.error.no.single.wildcard");
        }
        for (int k = digitsEnd; k < end; ++k) {
            if (str.charAt(k) == '_') continue;
            throw new AddressStringException(str, "ipaddress.error.single.wildcard.order");
        }
    }

    private static void switchSingleWildcard10(long currentValueHex, CharSequence s2, int start2, int end, int numSingleWildcards, AddressParseData parseData, int parsedSegIndex, int leadingZeroStartIndex, AddressStringParameters.AddressStringFormatParameters options) throws AddressStringException {
        long upper;
        int digitsEnd = end - numSingleWildcards;
        Validator.checkSingleWildcard(s2, start2, end, digitsEnd, options);
        long lower = start2 < digitsEnd ? Validator.switchValue10(currentValueHex, s2, digitsEnd - start2) : 0L;
        switch (numSingleWildcards) {
            case 1: {
                upper = (lower *= 10L) + 9L;
                break;
            }
            case 2: {
                upper = (lower *= 100L) + 99L;
                break;
            }
            case 3: {
                upper = (lower *= 1000L) + 999L;
                break;
            }
            default: {
                long power = (long)Math.pow(10.0, numSingleWildcards);
                upper = (lower *= power) + power - 1L;
            }
        }
        int radix = 10;
        Validator.assign7Attributes2Values1Flags(start2, end, leadingZeroStartIndex, start2, end, leadingZeroStartIndex, parseData, parsedSegIndex, lower, upper, 0x20000 | radix, radix);
    }

    private static void switchSingleWildcard2(long currentValueHex, CharSequence s2, int start2, int end, int numSingleWildcards, AddressParseData parseData, int parsedSegIndex, int leadingZeroStartIndex, AddressStringParameters.AddressStringFormatParameters options) throws AddressStringException {
        long upper;
        int digitsEnd = end - numSingleWildcards;
        Validator.checkSingleWildcard(s2, start2, end, digitsEnd, options);
        long lower = start2 < digitsEnd ? Validator.switchValue2(currentValueHex, s2, digitsEnd - start2) : 0L;
        lower <<= numSingleWildcards;
        switch (numSingleWildcards) {
            case 1: {
                upper = lower | 1L;
                break;
            }
            case 2: {
                upper = lower | 3L;
                break;
            }
            case 3: {
                upper = lower | 7L;
                break;
            }
            case 4: {
                upper = lower | 0xFL;
                break;
            }
            case 5: {
                upper = lower | 0x1FL;
                break;
            }
            case 6: {
                upper = lower | 0x3FL;
                break;
            }
            case 7: {
                upper = lower | 0x7FL;
                break;
            }
            case 8: {
                upper = lower | 0xFFL;
                break;
            }
            case 9: {
                upper = lower | 0x1FFL;
                break;
            }
            case 10: {
                upper = lower | 0x3FFL;
                break;
            }
            case 11: {
                upper = lower | 0x7FFL;
                break;
            }
            case 12: {
                upper = lower | 0xFFFL;
                break;
            }
            case 13: {
                upper = lower | 0x1FFFL;
                break;
            }
            case 14: {
                upper = lower | 0x3FFFL;
                break;
            }
            case 15: {
                upper = lower | 0x7FFFL;
                break;
            }
            case 16: {
                upper = lower | 0xFFFFL;
                break;
            }
            default: {
                upper = lower | -1L << numSingleWildcards ^ 0xFFFFFFFFFFFFFFFFL;
            }
        }
        int radix = 2;
        Validator.assign7Attributes2Values1Flags(start2, end, leadingZeroStartIndex, start2, end, leadingZeroStartIndex, parseData, parsedSegIndex, lower, upper, 0x20000 | radix, radix);
    }

    private static void switchSingleWildcard8(long currentValueHex, CharSequence s2, int start2, int end, int numSingleWildcards, AddressParseData parseData, int parsedSegIndex, int leadingZeroStartIndex, AddressStringParameters.AddressStringFormatParameters options) throws AddressStringException {
        long upper;
        int digitsEnd = end - numSingleWildcards;
        Validator.checkSingleWildcard(s2, start2, end, digitsEnd, options);
        long lower = start2 < digitsEnd ? Validator.switchValue8(currentValueHex, s2, digitsEnd - start2) : 0L;
        switch (numSingleWildcards) {
            case 1: {
                upper = (lower <<= 3) | 7L;
                break;
            }
            case 2: {
                upper = (lower <<= 6) | 0x3FL;
                break;
            }
            case 3: {
                upper = (lower <<= 9) | 0x1FFL;
                break;
            }
            default: {
                int shift = numSingleWildcards * 3;
                upper = (lower <<= shift) | -1L << shift ^ 0xFFFFFFFFFFFFFFFFL;
            }
        }
        int radix = 8;
        Validator.assign7Attributes2Values1Flags(start2, end, leadingZeroStartIndex, start2, end, leadingZeroStartIndex, parseData, parsedSegIndex, lower, upper, 0x20000 | radix, radix);
    }

    private static void assignSingleWildcard16(long lower, CharSequence s2, int start2, int end, int numSingleWildcards, AddressParseData parseData, int parsedSegIndex, int leadingZeroStartIndex, AddressStringParameters.AddressStringFormatParameters options) throws AddressStringException {
        int digitsEnd = end - numSingleWildcards;
        Validator.checkSingleWildcard(s2, start2, end, digitsEnd, options);
        int shift = numSingleWildcards << 2;
        long upper = (lower <<= shift) | -1L << shift ^ 0xFFFFFFFFFFFFFFFFL;
        Validator.assign6Attributes2Values1Flags(start2, end, leadingZeroStartIndex, start2, end, leadingZeroStartIndex, parseData, parsedSegIndex, lower, upper, 131072);
    }

    private static void parseSingleSegmentSingleWildcard16(long currentValueHex, CharSequence s2, int start2, int end, int numSingleWildcards, AddressParseData parseData, int parsedSegIndex, int leadingZeroStartIndex, AddressStringParameters.AddressStringFormatParameters options) throws AddressStringException {
        long extendedUpper;
        long extendedLower;
        long upper;
        long lower;
        int digitsEnd = end - numSingleWildcards;
        Validator.checkSingleWildcard(s2, start2, end, digitsEnd, options);
        if (numSingleWildcards < 16) {
            int midIndex = end - 16;
            lower = Validator.parseLong16(s2, midIndex, digitsEnd);
            int shift = numSingleWildcards << 2;
            upper = (lower <<= shift) | -1L << shift ^ 0xFFFFFFFFFFFFFFFFL;
            extendedUpper = extendedLower = Validator.parseLong16(s2, start2, midIndex);
        } else if (numSingleWildcards == 16) {
            lower = 0L;
            upper = -1L;
            extendedUpper = extendedLower = currentValueHex;
        } else {
            lower = 0L;
            upper = -1L;
            extendedLower = currentValueHex;
            int shift = numSingleWildcards - 16 << 2;
            extendedUpper = (extendedLower <<= shift) | -1L << shift ^ 0xFFFFFFFFFFFFFFFFL;
        }
        Validator.assign6Attributes4Values1Flags(start2, end, leadingZeroStartIndex, start2, end, leadingZeroStartIndex, parseData, parsedSegIndex, lower, extendedLower, upper, extendedUpper, 131072);
    }

    private static void parseSingleSegmentSingleWildcard2(CharSequence s2, int start2, int end, int numSingleWildcards, AddressParseData parseData, int parsedSegIndex, int leadingZeroStartIndex, AddressStringParameters.AddressStringFormatParameters options) throws AddressStringException {
        long extendedUpper;
        long extendedLower;
        long upper;
        long lower;
        int digitsEnd = end - numSingleWildcards;
        Validator.checkSingleWildcard(s2, start2, end, digitsEnd, options);
        int midIndex = end - 64;
        if (numSingleWildcards < 64) {
            lower = Validator.parseLong2(s2, midIndex, digitsEnd);
            int shift = numSingleWildcards;
            upper = (lower <<= shift) | -1L << shift ^ 0xFFFFFFFFFFFFFFFFL;
            extendedUpper = extendedLower = Validator.parseLong2(s2, start2, midIndex);
        } else if (numSingleWildcards == 64) {
            lower = 0L;
            upper = -1L;
            extendedUpper = extendedLower = Validator.parseLong2(s2, start2, midIndex);
        } else {
            lower = 0L;
            upper = -1L;
            int shift = numSingleWildcards - 64;
            extendedLower = Validator.parseLong2(s2, start2, midIndex - shift);
            extendedUpper = (extendedLower <<= shift) | -1L << shift ^ 0xFFFFFFFFFFFFFFFFL;
        }
        Validator.assign6Attributes4Values1Flags(start2, end, leadingZeroStartIndex, start2, end, leadingZeroStartIndex, parseData, parsedSegIndex, lower, extendedLower, upper, extendedUpper, 131072);
    }

    private static long getMaxIPv4Value(int segmentCount) {
        return MAX_VALUES[segmentCount];
    }

    private static int getStringPrefixCharCount(int radix) {
        switch (radix) {
            case 10: {
                return 0;
            }
            case 2: 
            case 16: {
                return 2;
            }
        }
        return 1;
    }

    private static int getMaxIPv4StringLength(int additionalSegmentsCovered, int radix) {
        try {
            return MAX_IPv4_STRING_LEN[radix >>> 1][additionalSegmentsCovered];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    private static long switchValue2(long currentHexValue, CharSequence s2, int digitCount) throws AddressStringException {
        long result = 0xFL & currentHexValue;
        if (result > 1L) {
            throw new AddressStringException(s2, "ipaddress.error.ipv4.invalid.binary.digit");
        }
        int shift = 0;
        while (--digitCount > 0) {
            ++shift;
            long next = 0xFL & (currentHexValue >>>= 4);
            if (next < 1L) continue;
            if (next == 1L) {
                result |= (long)(1 << shift);
                continue;
            }
            throw new AddressStringException(s2, "ipaddress.error.ipv4.invalid.binary.digit");
        }
        return result;
    }

    private static long switchValue8(long currentHexValue, CharSequence s2, int digitCount) throws AddressStringException {
        long result = 0xFL & currentHexValue;
        if (result >= 8L) {
            throw new AddressStringException(s2, "ipaddress.error.ipv4.invalid.octal.digit");
        }
        int shift = 0;
        while (--digitCount > 0) {
            shift += 3;
            long next = 0xFL & (currentHexValue >>>= 4);
            if (next >= 8L) {
                throw new AddressStringException(s2, "ipaddress.error.ipv4.invalid.octal.digit");
            }
            result |= next << shift;
        }
        return result;
    }

    private static long switchValue10(long currentHexValue, CharSequence s2, int digitCount) throws AddressStringException {
        long result = 0xFL & currentHexValue;
        if (result >= 10L) {
            throw new AddressStringException(s2, "ipaddress.error.ipv4.invalid.decimal.digit");
        }
        if (--digitCount > 0) {
            int factor = 10;
            while (true) {
                long next;
                if ((next = 0xFL & (currentHexValue >>>= 4)) >= 10L) {
                    throw new AddressStringException(s2, "ipaddress.error.ipv4.invalid.decimal.digit");
                }
                result += next * (long)factor;
                if (--digitCount == 0) break;
                if (factor == 10) {
                    factor = 100;
                    continue;
                }
                if (factor == 100) {
                    factor = 1000;
                    continue;
                }
                factor *= 10;
            }
        }
        return result;
    }

    private static long parseLong2(CharSequence s2, int start2, int end) {
        int[] charArray = chars;
        long result = charArray[s2.charAt(start2)];
        while (++start2 < end) {
            char c = s2.charAt(start2);
            if (c == '1') {
                result = result << 1 | 1L;
                continue;
            }
            result <<= 1;
        }
        return result;
    }

    private static long parseLong8(CharSequence s2, int start2, int end) {
        int[] charArray = chars;
        long result = charArray[s2.charAt(start2)];
        while (++start2 < end) {
            result = result << 3 | (long)charArray[s2.charAt(start2)];
        }
        return result;
    }

    private static long parseLong10(CharSequence s2, int start2, int end) {
        int[] charArray = chars;
        long result = charArray[s2.charAt(start2)];
        while (++start2 < end) {
            result = result * 10L + (long)charArray[s2.charAt(start2)];
        }
        return result;
    }

    private static long parseLong16(CharSequence s2, int start2, int end) {
        int[] charArray = chars;
        long result = charArray[s2.charAt(start2)];
        while (++start2 < end) {
            result = result << 4 | (long)charArray[s2.charAt(start2)];
        }
        return result;
    }

    private static BigInteger parseBase85(CharSequence s2, int start2, int end) {
        boolean last;
        int[] charArray = extendedChars;
        BigInteger result = BigInteger.ZERO;
        do {
            int power;
            int partialEnd;
            int left;
            if (last = (left = end - start2) <= 9) {
                partialEnd = end;
                power = left;
            } else {
                partialEnd = start2 + 9;
                power = 9;
            }
            long partialResult = charArray[s2.charAt(start2)];
            while (++start2 < partialEnd) {
                int next = charArray[s2.charAt(start2)];
                partialResult = partialResult * 85L + (long)next;
            }
            result = result.multiply(BASE_85_POWERS[power]).add(BigInteger.valueOf(partialResult));
            start2 = partialEnd;
        } while (!last);
        return result;
    }

    @Override
    public ParsedHost validateHost(HostName fromHost) throws HostNameException {
        return this.validateHostName(fromHost);
    }

    public ParsedHost validateHostName(HostName fromHost) throws HostNameException {
        String str = fromHost.toString();
        HostNameParameters validationOptions = fromHost.getValidationOptions();
        int addrLen = str.length();
        if (addrLen > 253 && (addrLen > 254 || str.charAt(253) != '.')) {
            throw new HostNameException(str, "ipaddress.host.error.invalid.length");
        }
        int lastSeparatorIndex = -1;
        int index = -1;
        int qualifierIndex = -1;
        int isSpecialOnlyIndex = -1;
        int labelCount = 0;
        int maxLocalLabels = 6;
        int[] separatorIndices = null;
        boolean[] normalizedFlags = null;
        boolean hostIsEmpty = false;
        boolean hasPortOrService = false;
        boolean isPrefixed = false;
        boolean tryIPv4 = false;
        boolean tryIPv6 = false;
        boolean squareBracketed = false;
        boolean isNotNormalized = false;
        boolean segmentUppercase = false;
        boolean isPossiblyIPv4 = true;
        boolean isPossiblyIPv6 = true;
        boolean isAllDigits = true;
        int sep5 = -1;
        int sep4 = -1;
        int sep3 = -1;
        int sep2 = -1;
        int sep1 = -1;
        int sep0 = -1;
        boolean upper5 = false;
        boolean upper4 = false;
        boolean upper3 = false;
        boolean upper2 = false;
        boolean upper1 = false;
        boolean upper0 = false;
        while (++index <= addrLen) {
            int currentChar;
            if (index == addrLen) {
                boolean segmentCountMatchesIPv4;
                if (index == 0) {
                    hostIsEmpty = true;
                    break;
                }
                boolean bl = segmentCountMatchesIPv4 = isPossiblyIPv4 && labelCount + 1 == 4 || labelCount + 1 < 4 && isSpecialOnlyIndex >= 0 || labelCount + 1 < 4 && validationOptions.addressOptions.getIPv4Parameters().inet_aton_joinedSegments || labelCount == 0 && validationOptions.addressOptions.allowSingleSegment;
                if (isAllDigits) {
                    if (isPossiblyIPv4 && segmentCountMatchesIPv4) {
                        tryIPv4 = true;
                        break;
                    }
                    isPossiblyIPv4 = false;
                    if (hasPortOrService && isPossiblyIPv6) {
                        tryIPv6 = true;
                        break;
                    }
                    throw new HostNameException(str, "ipaddress.host.error.invalid");
                }
                isPossiblyIPv4 &= segmentCountMatchesIPv4;
                currentChar = 46;
            } else {
                currentChar = str.charAt(index);
            }
            if (currentChar >= 97 && currentChar <= 122) {
                if (currentChar > 102) {
                    isPossiblyIPv6 = false;
                    isPossiblyIPv4 &= currentChar == 120 && validationOptions.addressOptions.getIPv4Parameters().inet_aton_hex;
                } else if (currentChar == 98) {
                    isPossiblyIPv4 &= validationOptions.addressOptions.getIPv4Parameters().allowBinary;
                }
                isAllDigits = false;
                continue;
            }
            if (currentChar >= 48 && currentChar <= 57) continue;
            if (currentChar >= 65 && currentChar <= 90) {
                if (currentChar > 70) {
                    isPossiblyIPv6 = false;
                    isPossiblyIPv4 &= currentChar == 88 && validationOptions.addressOptions.getIPv4Parameters().inet_aton_hex;
                } else if (currentChar == 66) {
                    isPossiblyIPv4 &= validationOptions.addressOptions.getIPv4Parameters().allowBinary;
                }
                segmentUppercase = true;
                isAllDigits = false;
                continue;
            }
            if (currentChar == 46) {
                int len = index - lastSeparatorIndex - 1;
                if (len > 63) {
                    throw new HostNameException(str, "ipaddress.error.segment.too.long");
                }
                if (len == 0) {
                    if (index < addrLen) {
                        throw new HostNameException(str, "ipaddress.host.error.segment.too.short");
                    }
                    isPossiblyIPv4 = false;
                    isNotNormalized = true;
                } else {
                    if (labelCount < maxLocalLabels) {
                        if (labelCount < 3) {
                            if (labelCount == 0) {
                                sep0 = index;
                                upper0 = segmentUppercase;
                            } else if (labelCount == 1) {
                                sep1 = index;
                                upper1 = segmentUppercase;
                            } else {
                                sep2 = index;
                                upper2 = segmentUppercase;
                            }
                        } else if (labelCount == 3) {
                            sep3 = index;
                            upper3 = segmentUppercase;
                        } else if (labelCount == 4) {
                            sep4 = index;
                            upper4 = segmentUppercase;
                        } else {
                            sep5 = index;
                            upper5 = segmentUppercase;
                        }
                        ++labelCount;
                    } else if (labelCount == maxLocalLabels) {
                        separatorIndices = new int[128];
                        separatorIndices[labelCount] = index;
                        if (validationOptions.normalizeToLowercase) {
                            normalizedFlags = new boolean[128];
                            normalizedFlags[labelCount] = !segmentUppercase;
                            isNotNormalized |= segmentUppercase;
                        }
                        ++labelCount;
                    } else {
                        separatorIndices[labelCount] = index;
                        if (normalizedFlags != null) {
                            normalizedFlags[labelCount] = !segmentUppercase;
                            isNotNormalized |= segmentUppercase;
                        }
                        if (++labelCount > 127) {
                            throw new HostNameException(str, "ipaddress.host.error.too.many.segments");
                        }
                    }
                    segmentUppercase = false;
                }
                lastSeparatorIndex = index;
                isPossiblyIPv6 &= index == addrLen;
                continue;
            }
            if (currentChar == 95) {
                isAllDigits = false;
                continue;
            }
            if (currentChar == 45) {
                if (index == lastSeparatorIndex + 1 || index == addrLen - 1 || str.charAt(index + 1) == '.') {
                    throw new HostNameException(str, index);
                }
                isAllDigits = false;
                continue;
            }
            if (currentChar == 91) {
                if (index == 0 && labelCount == 0 && addrLen > 2) {
                    squareBracketed = true;
                    break;
                }
                throw new HostNameException(str, index);
            }
            if (currentChar == 47) {
                isPrefixed = true;
                qualifierIndex = index + 1;
                addrLen = index--;
                isNotNormalized = true;
                continue;
            }
            boolean b = false;
            if (currentChar == 42 || (b = currentChar == 37)) {
                IPAddressStringParameters addressOptions = validationOptions.addressOptions;
                if (b && addressOptions.getIPv6Parameters().allowZone) {
                    if (isPossiblyIPv6 && labelCount < 8) {
                        tryIPv6 = true;
                        isPossiblyIPv4 = false;
                        break;
                    }
                    throw new HostNameException(str, index);
                }
                if (isPossiblyIPv4 && addressOptions.getIPv4Parameters().rangeOptions.allowsWildcard()) {
                    if (isSpecialOnlyIndex < 0) {
                        isSpecialOnlyIndex = index;
                    }
                } else {
                    isPossiblyIPv4 = false;
                }
                if (isPossiblyIPv6 && addressOptions.getIPv6Parameters().rangeOptions.allowsWildcard()) {
                    if (isSpecialOnlyIndex < 0) {
                        isSpecialOnlyIndex = index;
                    }
                } else {
                    if (!isPossiblyIPv4) {
                        throw new HostNameException(str, index);
                    }
                    isPossiblyIPv6 = false;
                }
                isAllDigits = false;
                continue;
            }
            if (currentChar == 58) {
                if (validationOptions.allowPort || validationOptions.allowService) {
                    hasPortOrService = true;
                    qualifierIndex = index + 1;
                    addrLen = index--;
                    isNotNormalized = true;
                    continue;
                }
                isPossiblyIPv4 = false;
                if (isPossiblyIPv6) {
                    tryIPv6 = true;
                    break;
                }
                throw new HostNameException(str, index);
            }
            if (currentChar == 187) {
                isAllDigits = false;
                continue;
            }
            throw new HostNameException(str, index);
        }
        IPAddressStringParameters addressOptions = validationOptions.addressOptions;
        try {
            ParsedHost parsedHost;
            block130: {
                boolean isIPAddress;
                boolean bl = isIPAddress = squareBracketed || tryIPv4 || tryIPv6;
                if (!validationOptions.allowIPAddress) {
                    if (isIPAddress) {
                        throw new HostNameException(str, "ipaddress.host.error.ipaddress");
                    }
                } else if (isIPAddress || isPossiblyIPv4 || isPossiblyIPv6) {
                    try {
                        ParsedIPAddress pa = new ParsedIPAddress(fromHost, str, addressOptions);
                        ParsedHostIdentifierStringQualifier addrQualifier = null;
                        ParsedHostIdentifierStringQualifier hostQualifier = ParsedHost.NO_QUALIFIER;
                        if (squareBracketed) {
                            boolean endsWithQualifier;
                            int endIndex = addrLen - 1;
                            boolean bl2 = endsWithQualifier = str.charAt(endIndex) != ']';
                            if (endsWithQualifier) {
                                while (str.charAt(--endIndex) != ']') {
                                    if (endIndex != 1) continue;
                                    throw new HostNameException(str, "ipaddress.host.error.bracketed.missing.end");
                                }
                            }
                            int startIndex = 1;
                            if (str.startsWith("IPv6:", 1)) {
                                startIndex = 6;
                            } else {
                                char firstChar = str.charAt(1);
                                if (firstChar == 'v' || firstChar == IPvFUTURE_UPPERCASE) {
                                    throw new HostNameException(str, "ipaddress.host.error.invalid.mechanism");
                                }
                            }
                            Validator.validateIPAddress(addressOptions, str, startIndex, endIndex, pa, false);
                            if (endsWithQualifier) {
                                int prefixIndex = endIndex + 1;
                                char prefixChar = str.charAt(prefixIndex);
                                if (prefixChar == '/') {
                                    isPrefixed = true;
                                } else if (prefixChar == ':') {
                                    hasPortOrService = true;
                                } else {
                                    throw new HostNameException(str, prefixIndex);
                                }
                                qualifierIndex = prefixIndex + 1;
                                endIndex = str.length();
                                AddressParseData addressParseData = pa.getAddressParseData();
                                ParsedHostIdentifierStringQualifier parsedHostQualifier = Validator.parseHostNameQualifier(str, addressOptions, validationOptions, isPrefixed, hasPortOrService, addressParseData.isProvidingEmpty(), qualifierIndex, endIndex, pa.getProviderIPVersion());
                                int insideBracketsQualifierIndex = pa.getQualifierIndex();
                                if (pa.isZoned() && str.charAt(insideBracketsQualifierIndex) == '2' && str.charAt(insideBracketsQualifierIndex + 1) == '5') {
                                    insideBracketsQualifierIndex += 2;
                                }
                                addrQualifier = Validator.parseHostAddressQualifier(str, addressOptions, null, pa.hasPrefixSeparator(), false, pa, insideBracketsQualifierIndex, prefixIndex - 1);
                                if (isPrefixed) {
                                    if (addrQualifier == ParsedHost.NO_QUALIFIER) {
                                        addrQualifier = parsedHostQualifier;
                                    } else {
                                        addrQualifier = addrQualifier.clone();
                                        addrQualifier.merge(parsedHostQualifier);
                                    }
                                } else {
                                    hostQualifier = parsedHostQualifier;
                                }
                            } else {
                                qualifierIndex = pa.getQualifierIndex();
                                isPrefixed = pa.hasPrefixSeparator();
                                hasPortOrService = false;
                                if (pa.isZoned() && str.charAt(qualifierIndex) == '2' && str.charAt(qualifierIndex + 1) == '5') {
                                    qualifierIndex += 2;
                                }
                                addrQualifier = Validator.parseHostAddressQualifier(str, addressOptions, validationOptions, isPrefixed, hasPortOrService, pa, qualifierIndex, endIndex);
                            }
                            IPAddress.IPVersion version = pa.getProviderIPVersion();
                            if (version != IPAddress.IPVersion.IPV6 && !validationOptions.allowBracketedIPv4) {
                                throw new HostNameException(str, "ipaddress.host.error.bracketed.not.ipv6");
                            }
                        } else {
                            int endIndex;
                            boolean firstTrySucceeded;
                            block129: {
                                boolean isPotentiallyIPv6;
                                firstTrySucceeded = false;
                                boolean hasAddressPortOrService = false;
                                int addressQualifierIndex = -1;
                                boolean bl3 = isPotentiallyIPv6 = isPossiblyIPv6 || tryIPv6;
                                if (isPotentiallyIPv6) {
                                    if (!isPrefixed && (validationOptions.allowPort || validationOptions.allowService)) {
                                        for (int j = str.length() - 1; j >= 0; --j) {
                                            char c = str.charAt(j);
                                            if (c == ':') {
                                                hasAddressPortOrService = true;
                                                addressQualifierIndex = j + 1;
                                            } else if (c >= '0' && c <= '9' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c == '-' || c == '*') {
                                                continue;
                                            }
                                            break;
                                        }
                                    }
                                } else {
                                    hasAddressPortOrService = hasPortOrService;
                                    addressQualifierIndex = qualifierIndex;
                                }
                                if (hasAddressPortOrService) {
                                    try {
                                        ParsedHostIdentifierStringQualifier hostPortQualifier = hostQualifier = Validator.parsePortOrService(str, null, validationOptions, addressQualifierIndex, str.length());
                                        if (isPotentiallyIPv6) {
                                            boolean expectPort = validationOptions.expectPort;
                                            try {
                                                if (expectPort) {
                                                    endIndex = addressQualifierIndex - 1;
                                                } else {
                                                    endIndex = str.length();
                                                    hostQualifier = ParsedHost.NO_QUALIFIER;
                                                }
                                                Validator.validateIPAddress(addressOptions, str, 0, endIndex, pa, false);
                                                addrQualifier = Validator.parseAddressQualifier(str, addressOptions, null, pa, endIndex);
                                                firstTrySucceeded = true;
                                            }
                                            catch (AddressStringException e) {
                                                pa = new ParsedIPAddress(fromHost, str, addressOptions);
                                                if (expectPort) {
                                                    hostQualifier = ParsedHost.NO_QUALIFIER;
                                                    endIndex = str.length();
                                                    break block129;
                                                }
                                                hostQualifier = hostPortQualifier;
                                                endIndex = addressQualifierIndex - 1;
                                            }
                                            break block129;
                                        }
                                        endIndex = addressQualifierIndex - 1;
                                    }
                                    catch (AddressStringException e) {
                                        if (!isPotentiallyIPv6) {
                                            throw e;
                                        }
                                        hostQualifier = ParsedHost.NO_QUALIFIER;
                                        endIndex = str.length();
                                    }
                                } else {
                                    endIndex = str.length();
                                }
                            }
                            if (!firstTrySucceeded) {
                                Validator.validateIPAddress(addressOptions, str, 0, endIndex, pa, false);
                                addrQualifier = Validator.parseAddressQualifier(str, addressOptions, null, pa, endIndex);
                            }
                        }
                        IPAddressProvider provider = Validator.chooseIPAddressProvider(fromHost, str, addressOptions, pa, addrQualifier);
                        return new ParsedHost(str, provider, hostQualifier);
                    }
                    catch (AddressStringException e) {
                        if (!isIPAddress) break block130;
                        throw e;
                    }
                }
            }
            ParsedHostIdentifierStringQualifier qualifier = Validator.parseHostNameQualifier(str, addressOptions, validationOptions, isPrefixed, hasPortOrService, hostIsEmpty, qualifierIndex, str.length(), null);
            if (hostIsEmpty) {
                if (!validationOptions.allowEmpty) {
                    throw new HostNameException(str, "ipaddress.host.error.empty");
                }
                parsedHost = qualifier == ParsedHost.NO_QUALIFIER ? DEFAULT_EMPTY_HOST : new ParsedHost(str, EMPTY_INDICES, null, qualifier);
            } else {
                ParsedHostIdentifierStringQualifier hostQualifier;
                ParsedHostIdentifierStringQualifier addrQualifier;
                if (labelCount <= maxLocalLabels) {
                    maxLocalLabels = labelCount;
                    separatorIndices = new int[maxLocalLabels];
                    if (validationOptions.normalizeToLowercase) {
                        normalizedFlags = new boolean[labelCount];
                    }
                } else if (labelCount != separatorIndices.length) {
                    int[] trimmedSeparatorIndices = new int[labelCount];
                    System.arraycopy(separatorIndices, maxLocalLabels, trimmedSeparatorIndices, maxLocalLabels, labelCount - maxLocalLabels);
                    separatorIndices = trimmedSeparatorIndices;
                    if (normalizedFlags != null) {
                        boolean[] trimmedNormalizedFlags = new boolean[labelCount];
                        System.arraycopy(normalizedFlags, maxLocalLabels, trimmedNormalizedFlags, maxLocalLabels, labelCount - maxLocalLabels);
                        normalizedFlags = trimmedNormalizedFlags;
                    }
                }
                for (int i = 0; i < maxLocalLabels; ++i) {
                    boolean isUpper;
                    int nextSep;
                    if (i < 2) {
                        if (i == 0) {
                            nextSep = sep0;
                            isUpper = upper0;
                        } else {
                            nextSep = sep1;
                            isUpper = upper1;
                        }
                    } else if (i < 4) {
                        if (i == 2) {
                            nextSep = sep2;
                            isUpper = upper2;
                        } else {
                            nextSep = sep3;
                            isUpper = upper3;
                        }
                    } else if (i == 4) {
                        nextSep = sep4;
                        isUpper = upper4;
                    } else {
                        nextSep = sep5;
                        isUpper = upper5;
                    }
                    separatorIndices[i] = nextSep;
                    if (normalizedFlags == null) continue;
                    normalizedFlags[i] = !isUpper;
                    isNotNormalized |= isUpper;
                }
                if (isPrefixed) {
                    addrQualifier = qualifier;
                    hostQualifier = ParsedHost.NO_QUALIFIER;
                } else {
                    hostQualifier = qualifier;
                    addrQualifier = ParsedHost.NO_QUALIFIER;
                }
                ParsedHost.EmbeddedAddress addr = Validator.checkSpecialHosts(str, addrLen, addrQualifier);
                AddressStringException embeddedException = null;
                if (isSpecialOnlyIndex >= 0 && (addr == null || (embeddedException = addr.addressStringException) != null)) {
                    if (embeddedException != null) {
                        throw new HostNameException(str, isSpecialOnlyIndex, embeddedException);
                    }
                    throw new HostNameException(str, isSpecialOnlyIndex);
                }
                parsedHost = new ParsedHost(str, separatorIndices, normalizedFlags, addr == null ? qualifier : hostQualifier, addr);
                if (!isNotNormalized && addr == null) {
                    parsedHost.host = str;
                }
            }
            return parsedHost;
        }
        catch (AddressStringException e) {
            throw new HostNameException(str, e, "ipaddress.host.error.invalid");
        }
    }

    private static ParsedHost.EmbeddedAddress checkSpecialHosts(String str, int addrLen, ParsedHostIdentifierStringQualifier hostQualifier) {
        ParsedHost.EmbeddedAddress emb = null;
        try {
            String suffix3;
            int suffixStartIndex;
            String suffix = ".ipv6-literal.net";
            if (addrLen > suffix.length() && str.regionMatches(true, suffixStartIndex = addrLen - suffix.length(), suffix, 0, suffix.length())) {
                IPAddressProvider provider;
                ParsedHostIdentifierStringQualifier qual;
                StringBuilder builder = new StringBuilder(str.substring(0, suffixStartIndex));
                for (int i = 0; i < builder.length(); ++i) {
                    char c = builder.charAt(i);
                    if (c == '-') {
                        builder.setCharAt(i, ':');
                        continue;
                    }
                    if (c == '\u00bb') {
                        builder.setCharAt(i, '-');
                        continue;
                    }
                    if (c != 's') continue;
                    builder.setCharAt(i, '%');
                }
                emb = new ParsedHost.EmbeddedAddress();
                emb.isUNCIPv6Literal = true;
                IPAddressStringParameters params = DEFAULT_UNC_OPTS;
                ParsedIPAddress pa = new ParsedIPAddress(null, str, params);
                Validator.validateIPAddress(params, builder, 0, builder.length(), pa, false);
                ParsedHostIdentifierStringQualifier addrQualifier = Validator.parseAddressQualifier(builder, DEFAULT_UNC_OPTS, null, pa, builder.length());
                if (addrQualifier == ParsedHost.NO_QUALIFIER) {
                    qual = hostQualifier;
                } else if (hostQualifier == ParsedHost.NO_QUALIFIER) {
                    qual = addrQualifier;
                } else {
                    addrQualifier.overridePrefix(hostQualifier);
                    qual = addrQualifier;
                }
                emb.addressProvider = provider = Validator.chooseIPAddressProvider(null, builder, params, pa, qual);
            }
            if (addrLen > (suffix3 = ".ip6.int").length()) {
                suffix = ".in-addr.arpa";
                String suffix2 = ".ip6.arpa";
                suffixStartIndex = addrLen - suffix.length();
                boolean isIPv4 = str.regionMatches(true, suffixStartIndex, suffix, 0, suffix.length());
                if (isIPv4 || addrLen > suffix2.length() && str.regionMatches(true, suffixStartIndex = addrLen - suffix2.length(), suffix2, 0, suffix2.length()) || addrLen > suffix3.length() && str.regionMatches(true, suffixStartIndex = addrLen - suffix3.length(), suffix3, 0, suffix3.length())) {
                    IPAddressProvider provider;
                    IPAddressStringParameters params;
                    CharSequence sequence2;
                    emb = new ParsedHost.EmbeddedAddress();
                    emb.isReverseDNS = true;
                    if (isIPv4) {
                        sequence2 = Validator.convertReverseDNSIPv4(str, suffixStartIndex);
                        params = REVERSE_DNS_IPV4_OPTS;
                    } else {
                        sequence2 = Validator.convertReverseDNSIPv6(str, suffixStartIndex);
                        params = REVERSE_DNS_IPV6_OPTS;
                    }
                    ParsedIPAddress pa = new ParsedIPAddress(null, sequence2, params);
                    Validator.validateIPAddress(params, sequence2, 0, sequence2.length(), pa, false);
                    emb.addressProvider = provider = Validator.chooseIPAddressProvider(null, sequence2, params, pa, hostQualifier != null ? hostQualifier : ParsedHost.NO_QUALIFIER);
                }
            }
        }
        catch (AddressStringException e) {
            emb.addressStringException = e;
        }
        return emb;
    }

    private static CharSequence convertReverseDNSIPv4(String str, int suffixStartIndex) throws AddressStringException {
        StringBuilder builder = new StringBuilder(suffixStartIndex);
        int segCount = 0;
        int j = suffixStartIndex;
        for (int i = suffixStartIndex - 1; i > 0; --i) {
            char c1 = str.charAt(i);
            if (c1 != '.') continue;
            if (j - i <= 1) {
                throw new AddressStringException((CharSequence)str, i);
            }
            for (int k = i + 1; k < j; ++k) {
                builder.append(str.charAt(k));
            }
            builder.append(c1);
            j = i;
            ++segCount;
        }
        for (int k = 0; k < j; ++k) {
            builder.append(str.charAt(k));
        }
        if (segCount + 1 != 4) {
            throw new AddressStringException((CharSequence)str, 0);
        }
        return builder;
    }

    private static CharSequence convertReverseDNSIPv6(String str, int suffixStartIndex) throws AddressStringException {
        StringBuilder builder = new StringBuilder(suffixStartIndex);
        StringBuilder low = new StringBuilder();
        StringBuilder high = new StringBuilder();
        int segCount = 0;
        int i = suffixStartIndex - 1;
        while (i >= 0) {
            boolean isRange = false;
            for (int j = 0; j < 4; ++j) {
                char c1 = str.charAt(i--);
                if (i >= 0) {
                    char c2;
                    if ((c2 = str.charAt(i--)) == '.') {
                        if (c1 == '*') {
                            isRange = true;
                            low.append('0');
                            high.append('f');
                            continue;
                        }
                        if (isRange) {
                            throw new AddressStringException((CharSequence)str, i + 1);
                        }
                        low.append(c1);
                        high.append(c1);
                        continue;
                    }
                    if (c2 == '-') {
                        high.append(c1);
                        if (i >= 1) {
                            boolean isFullRange;
                            c2 = str.charAt(i--);
                            low.append(c2);
                            boolean bl = isFullRange = c2 == '0' && c1 == 'f';
                            if (isRange && !isFullRange) {
                                throw new AddressStringException((CharSequence)str, i + 1);
                            }
                            if ((c2 = str.charAt(i--)) != '.') {
                                throw new AddressStringException((CharSequence)str, i + 1);
                            }
                        } else {
                            throw new AddressStringException((CharSequence)str, i);
                        }
                        isRange = true;
                        continue;
                    }
                    throw new AddressStringException((CharSequence)str, i + 1);
                }
                if (j < 3) {
                    throw new AddressStringException((CharSequence)str, i + 1);
                }
                if (c1 == '*') {
                    isRange = true;
                    low.append('0');
                    high.append('f');
                    continue;
                }
                if (isRange) {
                    throw new AddressStringException((CharSequence)str, 0);
                }
                low.append(c1);
                high.append(c1);
            }
            ++segCount;
            if (builder.length() > 0) {
                builder.append(':');
            }
            builder.append((CharSequence)low);
            if (isRange) {
                builder.append('-').append((CharSequence)high);
            }
            low.setLength(0);
            high.setLength(0);
        }
        if (segCount != 8) {
            throw new AddressStringException((CharSequence)str, 0);
        }
        return builder;
    }

    static {
        int i;
        extendedChars = new int[128];
        char[] extendedDigits = IPAddressLargeDivision.EXTENDED_DIGITS;
        for (i = 0; i < extendedDigits.length; ++i) {
            Validator.extendedChars[extendedDigits[i]] = i;
        }
        int[] charArray = chars = new int[128];
        i = 0;
        int c = 48;
        while (i < 10) {
            charArray[c] = i++;
            c = (char)(c + 1);
        }
        c = 97;
        int c2 = 65;
        while (i < 26) {
            charArray[c] = charArray[c2] = i++;
            c = (char)(c + 1);
            c2 = (char)(c2 + 1);
        }
        IPvFUTURE_UPPERCASE = Character.toUpperCase('v');
        EMPTY_INDICES = new int[0];
        PREFIX_CACHE = new ParsedHostIdentifierStringQualifier[129];
        MASK_CACHE = new IPAddressProvider.MaskCreator[3][];
        LOOPBACK_CACHE = new IPAddressProvider.LoopbackCreator(IPAddressString.DEFAULT_VALIDATION_OPTIONS);
        DEFAULT_EMPTY_HOST = new ParsedHost("", EMPTY_INDICES, null, ParsedHost.NO_QUALIFIER);
        VALIDATOR = new Validator();
        DEFAULT_UNC_OPTS = new IPAddressStringParameters.Builder().allowIPv4(false).allowEmpty(false).allowMask(false).allowPrefixOnly(false).allowPrefix(false).toParams();
        REVERSE_DNS_IPV4_OPTS = new IPAddressStringParameters.Builder().allowIPv6(false).allowEmpty(false).allowMask(false).allowPrefixOnly(false).allowPrefix(false).getIPv4AddressParametersBuilder().allow_inet_aton(false).getParentBuilder().toParams();
        REVERSE_DNS_IPV6_OPTS = new IPAddressStringParameters.Builder().allowIPv4(false).allowEmpty(false).allowMask(false).allowPrefixOnly(false).allowPrefix(false).getIPv6AddressParametersBuilder().allowMixed(false).allowZone(false).getParentBuilder().toParams();
        BASE_85_POWERS = new BigInteger[10];
        LOW_BITS_MASK = BigInteger.valueOf(-1L);
        MAX_VALUES = new long[]{0L, 255L, 65535L, 0xFFFFFFL, 0xFFFFFFFFL};
        MAX_IPv4_STRING_LEN = new int[][]{{3, 6, 8, 11}, {8, 16, 24, 32}, new int[0], new int[0], {3, 6, 8, 11}, {3, 5, 8, 10}, new int[0], new int[0], {2, 4, 6, 8}};
        BigInteger eightyFive = BigInteger.valueOf(85L);
        Validator.BASE_85_POWERS[0] = BigInteger.ONE;
        for (i = 1; i < BASE_85_POWERS.length; ++i) {
            Validator.BASE_85_POWERS[i] = BASE_85_POWERS[i - 1].multiply(eightyFive);
        }
    }
}

