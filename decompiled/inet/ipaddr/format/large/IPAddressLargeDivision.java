/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.large;

import inet.ipaddr.Address;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.IPAddressGenericDivision;
import inet.ipaddr.format.util.AddressSegmentParams;
import java.math.BigInteger;
import java.util.Arrays;

public class IPAddressLargeDivision
extends AddressDivisionBase
implements IPAddressGenericDivision {
    private static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);
    public static final char EXTENDED_DIGITS_RANGE_SEPARATOR = '\u00bb';
    public static final String EXTENDED_DIGITS_RANGE_SEPARATOR_STR = String.valueOf('\u00bb');
    private static final long serialVersionUID = 4L;
    private final BigInteger value;
    private final BigInteger upperValue;
    private final BigInteger maxValue;
    private final BigInteger upperValueMasked;
    private final BigInteger defaultRadix;
    private final int bitCount;
    private final Integer networkPrefixLength;
    private final boolean isSinglePrefixBlock;
    private final boolean isPrefixBlock;
    protected transient String cachedString;

    public IPAddressLargeDivision(byte[] bytes, int bitCount, int defaultRadix) throws AddressValueException {
        if (defaultRadix < 2 || defaultRadix > 85) {
            throw new IllegalArgumentException();
        }
        this.maxValue = IPAddressLargeDivision.getMaxValue(bitCount);
        this.bitCount = bitCount;
        this.defaultRadix = BigInteger.valueOf(defaultRadix);
        this.isSinglePrefixBlock = false;
        this.isPrefixBlock = false;
        this.upperValue = this.value = new BigInteger(1, bytes);
        this.upperValueMasked = this.value;
        this.networkPrefixLength = null;
        if (this.upperValue.compareTo(this.maxValue) > 0) {
            throw new AddressValueException(this.upperValue);
        }
    }

    public IPAddressLargeDivision(byte[] bytes, int bitCount, int defaultRadix, IPAddressNetwork<?, ?, ?, ?, ?> network, Integer prefixLength) throws AddressValueException {
        if (prefixLength != null && prefixLength < 0) {
            throw new PrefixLenException(prefixLength);
        }
        if (defaultRadix < 2 || defaultRadix > 85) {
            throw new IllegalArgumentException();
        }
        this.maxValue = IPAddressLargeDivision.getMaxValue(bitCount);
        this.bitCount = bitCount;
        this.defaultRadix = BigInteger.valueOf(defaultRadix);
        if (prefixLength == null || prefixLength >= bitCount) {
            if (prefixLength != null && prefixLength > bitCount) {
                prefixLength = bitCount;
            }
            this.isSinglePrefixBlock = prefixLength != null;
            this.isPrefixBlock = this.isSinglePrefixBlock;
            this.upperValue = this.value = new BigInteger(1, bytes);
            this.upperValueMasked = this.value;
        } else {
            bytes = IPAddressLargeDivision.extend(bytes, bitCount);
            byte[] upperBytes = (byte[])bytes.clone();
            int shift = bitCount - prefixLength;
            int byteShift = shift + 7 >>> 3;
            int byteIndex = bytes.length - byteShift;
            int mask = 0xFF & -1 << (shift - 1) % 8 + 1;
            if (network.getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                int n = byteIndex;
                bytes[n] = (byte)(bytes[n] & mask);
                Arrays.fill(bytes, byteIndex + 1, bytes.length, (byte)0);
                this.upperValueMasked = this.value = new BigInteger(1, bytes);
                int n2 = byteIndex;
                upperBytes[n2] = (byte)(upperBytes[n2] | ~mask);
                Arrays.fill(upperBytes, byteIndex + 1, bytes.length, (byte)-1);
                this.upperValue = new BigInteger(1, upperBytes);
                this.isSinglePrefixBlock = true;
                this.isPrefixBlock = true;
            } else {
                byte[] maskedUpperBytes = (byte[])upperBytes.clone();
                int n = byteIndex;
                maskedUpperBytes[n] = (byte)(maskedUpperBytes[n] & mask);
                Arrays.fill(maskedUpperBytes, byteIndex + 1, bytes.length, (byte)0);
                this.upperValueMasked = new BigInteger(1, maskedUpperBytes);
                this.upperValue = this.value = new BigInteger(1, bytes);
                this.isSinglePrefixBlock = false;
                this.isPrefixBlock = false;
            }
        }
        if (this.upperValue.compareTo(this.maxValue) > 0) {
            throw new AddressValueException(this.upperValue);
        }
        this.networkPrefixLength = prefixLength;
    }

    public IPAddressLargeDivision(byte[] bytes, byte[] upperBytes, int bitCount, int defaultRadix, IPAddressNetwork<?, ?, ?, ?, ?> network, Integer prefixLength) throws AddressValueException {
        if (prefixLength != null && prefixLength < 0) {
            throw new PrefixLenException(prefixLength);
        }
        if (defaultRadix < 2 || defaultRadix > 85) {
            throw new IllegalArgumentException();
        }
        bytes = IPAddressLargeDivision.extend(bytes, bitCount);
        upperBytes = IPAddressLargeDivision.extend(upperBytes, bitCount);
        this.maxValue = IPAddressLargeDivision.getMaxValue(bitCount);
        this.bitCount = bitCount;
        this.defaultRadix = BigInteger.valueOf(defaultRadix);
        if (prefixLength == null || prefixLength >= bitCount) {
            BigInteger low;
            BigInteger high;
            if (prefixLength != null && prefixLength > bitCount) {
                prefixLength = bitCount;
            }
            if (Arrays.equals(bytes, upperBytes)) {
                low = high = new BigInteger(1, bytes);
                this.isSinglePrefixBlock = prefixLength != null;
            } else {
                low = new BigInteger(1, bytes);
                high = new BigInteger(1, upperBytes);
                if (low.compareTo(high) > 0) {
                    BigInteger tmp = high;
                    high = low;
                    low = tmp;
                }
                this.isSinglePrefixBlock = false;
            }
            this.isPrefixBlock = prefixLength != null;
            this.value = low;
            this.upperValueMasked = this.upperValue = high;
        } else {
            int shift = bitCount - prefixLength;
            int byteShift = shift + 7 >>> 3;
            int byteIndex = bytes.length - byteShift;
            int mask = 0xFF & -1 << (shift - 1) % 8 + 1;
            int upperByteIndex = upperBytes.length - byteShift;
            if (network.getPrefixConfiguration().allPrefixedAddressesAreSubnets()) {
                BigInteger highMasked;
                BigInteger high;
                BigInteger low;
                while (true) {
                    int n = byteIndex;
                    bytes[n] = (byte)(bytes[n] & mask);
                    Arrays.fill(bytes, byteIndex + 1, bytes.length, (byte)0);
                    low = new BigInteger(1, bytes);
                    int n2 = upperByteIndex;
                    upperBytes[n2] = (byte)(upperBytes[n2] | ~mask);
                    Arrays.fill(upperBytes, upperByteIndex + 1, upperBytes.length, (byte)-1);
                    high = new BigInteger(1, upperBytes);
                    byte[] maskedUpperBytes = (byte[])upperBytes.clone();
                    int n3 = upperByteIndex;
                    maskedUpperBytes[n3] = (byte)(maskedUpperBytes[n3] & mask);
                    Arrays.fill(maskedUpperBytes, upperByteIndex + 1, upperBytes.length, (byte)0);
                    highMasked = new BigInteger(1, maskedUpperBytes);
                    if (low.compareTo(high) <= 0) break;
                    byte[] tmp = upperBytes;
                    upperBytes = bytes;
                    bytes = tmp;
                }
                this.value = low;
                this.upperValue = high;
                this.upperValueMasked = highMasked;
                this.isPrefixBlock = true;
                this.isSinglePrefixBlock = IPAddressLargeDivision.isPrefixSubnetBlock(bytes, upperBytes, bitCount, prefixLength, true, false);
            } else {
                BigInteger low;
                BigInteger high;
                if (Arrays.equals(bytes, upperBytes)) {
                    low = high = new BigInteger(1, bytes);
                    this.isSinglePrefixBlock = false;
                    this.isPrefixBlock = false;
                } else {
                    low = new BigInteger(1, bytes);
                    high = new BigInteger(1, upperBytes);
                    boolean backIsPrefixed = IPAddressLargeDivision.isPrefixSubnetBlock(bytes, upperBytes, bitCount, prefixLength, false, true);
                    if (backIsPrefixed) {
                        this.isPrefixBlock = true;
                        this.isSinglePrefixBlock = IPAddressLargeDivision.isPrefixSubnetBlock(bytes, upperBytes, bitCount, prefixLength, true, false);
                    } else {
                        this.isSinglePrefixBlock = false;
                        this.isPrefixBlock = false;
                    }
                    if (low.compareTo(high) > 0) {
                        BigInteger tmp = high;
                        high = low;
                        low = tmp;
                    }
                }
                this.value = low;
                this.upperValue = high;
                byte[] maskedUpperBytes = (byte[])upperBytes.clone();
                int n = byteIndex;
                maskedUpperBytes[n] = (byte)(maskedUpperBytes[n] & mask);
                Arrays.fill(maskedUpperBytes, byteIndex + 1, bytes.length, (byte)0);
                this.upperValueMasked = new BigInteger(1, maskedUpperBytes);
            }
        }
        if (this.upperValue.compareTo(this.maxValue) > 0) {
            throw new AddressValueException(this.upperValue);
        }
        this.networkPrefixLength = prefixLength;
    }

    @Override
    public BigInteger getValue() {
        return this.value;
    }

    @Override
    public BigInteger getUpperValue() {
        return this.upperValue;
    }

    private static boolean isPrefixSubnetBlock(byte[] bytes, byte[] upperBytes, int bitCount, Integer prefix, boolean front, boolean back) {
        if (prefix == null) {
            return false;
        }
        int shift = bitCount - prefix;
        int byteShift = shift + 7 >>> 3;
        int byteIndex = bytes.length - byteShift;
        int mask = 0xFF & -1 << (shift - 1) % 8 + 1;
        byte lowerByte = bytes[byteIndex];
        byte upperByte = upperBytes[byteIndex];
        if (front) {
            int lower = lowerByte & mask;
            int upper = upperByte & mask;
            if (lower != upper) {
                return false;
            }
            for (int i = byteIndex - 1; i >= 0; --i) {
                if (bytes[i] == upperBytes[i]) continue;
                return false;
            }
        }
        if (back) {
            int hostMask = 0xFF & ~mask;
            int lower = lowerByte & hostMask;
            int upper = upperByte & hostMask;
            if (lower != 0 || upper != hostMask) {
                return false;
            }
            for (int i = byteIndex + 1; i < bytes.length; ++i) {
                if (bytes[i] == 0 && upperBytes[i] == -1) continue;
                return false;
            }
        }
        return true;
    }

    private static byte[] extend(byte[] bytes, int bitCount) {
        return IPAddressLargeDivision.convert(bytes, bitCount + 7 >>> 3, "");
    }

    private static byte[] convert(byte[] bytes, int requiredByteCount, String key) {
        int len = bytes.length;
        if (len < requiredByteCount) {
            byte[] oldBytes = bytes;
            bytes = new byte[requiredByteCount];
            int diff = bytes.length - oldBytes.length;
            int mostSignificantBit = 0x80 & oldBytes[0];
            if (mostSignificantBit != 0) {
                Arrays.fill(bytes, 0, diff, (byte)-1);
            }
            System.arraycopy(oldBytes, 0, bytes, diff, oldBytes.length);
        } else if (len > requiredByteCount) {
            int i = 0;
            do {
                if (bytes[i++] == 0) continue;
                throw new AddressValueException(key, len);
            } while (--len > requiredByteCount);
            bytes = Arrays.copyOfRange(bytes, i, bytes.length);
        }
        return bytes;
    }

    @Override
    public boolean isBoundedBy(int val) {
        BigInteger bigVal = BigInteger.valueOf(val);
        return this.getUpperValue().compareTo(bigVal) < 0;
    }

    @Override
    public int getDigitCount(int radix) {
        if (!this.isMultiple() && radix == this.getDefaultTextualRadix()) {
            return this.getString().length();
        }
        return IPAddressLargeDivision.getDigitCountStatic(this.getUpperValue(), radix);
    }

    @Override
    public int getBitCount() {
        return this.bitCount;
    }

    @Override
    public boolean isMultiple() {
        return !this.getValue().equals(this.getUpperValue());
    }

    @Override
    public boolean includesZero() {
        return this.getValue().equals(BigInteger.ZERO);
    }

    @Override
    public boolean includesMax() {
        return this.getUpperValue().equals(this.maxValue);
    }

    @Override
    public boolean isMax() {
        return this.includesMax() && !this.isMultiple();
    }

    @Override
    public boolean isZero() {
        return this.includesZero() && !this.isMultiple();
    }

    @Override
    protected byte[] getBytesImpl(boolean low) {
        return IPAddressLargeDivision.convert(low ? this.getValue().toByteArray() : this.getUpperValue().toByteArray(), this.bitCount + 7 >>> 3, "");
    }

    @Override
    public int getDefaultTextualRadix() {
        return this.defaultRadix.intValue();
    }

    @Override
    public int getMaxDigitCount() {
        return IPAddressLargeDivision.getMaxDigitCount(this.defaultRadix.intValue(), this.bitCount, this.maxValue);
    }

    @Override
    public int getMaxDigitCount(int radix) {
        return IPAddressLargeDivision.getMaxDigitCount(radix, this.bitCount, this.maxValue);
    }

    @Override
    protected int adjustLowerLeadingZeroCount(int leadingZeroCount, int radix) {
        return this.adjustLeadingZeroCount(leadingZeroCount, this.getValue(), radix);
    }

    @Override
    protected int adjustUpperLeadingZeroCount(int leadingZeroCount, int radix) {
        return this.adjustLeadingZeroCount(leadingZeroCount, this.getUpperValue(), radix);
    }

    private int adjustLeadingZeroCount(int leadingZeroCount, BigInteger value, int radix) {
        if (leadingZeroCount < 0) {
            int width = this.getDigitCount(value, radix);
            return Math.max(0, this.getMaxDigitCount(radix) - width);
        }
        return leadingZeroCount;
    }

    private int getDigitCount(BigInteger val, int radix) {
        BigInteger bigRadix = this.defaultRadix.intValue() == radix ? this.defaultRadix : BigInteger.valueOf(radix);
        return IPAddressLargeDivision.getDigitCount(val, bigRadix);
    }

    private static int getDigitCountStatic(BigInteger val, int radix) {
        return IPAddressLargeDivision.getDigitCount(val, BigInteger.valueOf(radix));
    }

    private String toDefaultString(BigInteger val, int radix, boolean uppercase, int choppedDigits) {
        BigInteger bigRadix = this.defaultRadix.intValue() == radix ? this.defaultRadix : BigInteger.valueOf(radix);
        return IPAddressLargeDivision.toDefaultString(val, bigRadix, uppercase, choppedDigits, IPAddressLargeDivision.getMaxDigitCount(radix, this.bitCount, null));
    }

    private static void toDefaultStringRecursive(BigInteger val, BigInteger radix, boolean uppercase, int choppedDigits, int digitCount, char[] dig, boolean highest, StringBuilder builder) {
        if (val.compareTo(LONG_MAX) <= 0) {
            long longVal = val.longValue();
            int intRadix = radix.intValue();
            if (!highest) {
                IPAddressLargeDivision.getLeadingZeros(digitCount - IPAddressLargeDivision.toUnsignedStringLength(longVal, intRadix), builder);
            }
            IPAddressLargeDivision.toUnsignedStringCased(longVal, intRadix, choppedDigits, uppercase, builder);
        } else if (digitCount > choppedDigits) {
            int halfCount = digitCount >>> 1;
            BigInteger radixPower = IPAddressLargeDivision.getRadixPower(radix, halfCount);
            BigInteger[] highLow = val.divideAndRemainder(radixPower);
            BigInteger high = highLow[0];
            BigInteger low = highLow[1];
            if (highest && high.equals(BigInteger.ZERO)) {
                IPAddressLargeDivision.toDefaultStringRecursive(low, radix, uppercase, choppedDigits, halfCount, dig, true, builder);
            } else {
                IPAddressLargeDivision.toDefaultStringRecursive(high, radix, uppercase, Math.max(0, choppedDigits - halfCount), digitCount - halfCount, dig, highest, builder);
                IPAddressLargeDivision.toDefaultStringRecursive(low, radix, uppercase, choppedDigits, halfCount, dig, false, builder);
            }
        }
    }

    private boolean isExtendedDigits() {
        return IPAddressLargeDivision.isExtendedDigits(this.defaultRadix.intValue());
    }

    private static boolean isExtendedDigits(int radix) {
        return radix > 36;
    }

    private static char[] getDigits(int radix, boolean uppercase) {
        if (IPAddressLargeDivision.isExtendedDigits(radix)) {
            return EXTENDED_DIGITS;
        }
        return uppercase ? UPPERCASE_DIGITS : DIGITS;
    }

    @Override
    protected void appendUppercase(CharSequence str, int radix, StringBuilder appendable) {
        if (radix > 10 && !this.isExtendedDigits()) {
            for (int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                if (c >= 'a' && c <= 'z') {
                    c = (char)(c - 32);
                }
                appendable.append(c);
            }
        } else {
            appendable.append(str);
        }
    }

    private static String toDefaultString(BigInteger val, BigInteger radix, boolean uppercase, int choppedDigits, int maxDigits) {
        StringBuilder builder;
        if (radix.compareTo(BIG_MIN_RADIX) < 0 || radix.compareTo(BIG_MAX_RADIX) > 0) {
            throw new IllegalArgumentException();
        }
        if (val.equals(BigInteger.ZERO)) {
            return "0";
        }
        if (val.equals(BigInteger.ONE)) {
            return "1";
        }
        char[] dig = IPAddressLargeDivision.getDigits(radix.intValue(), uppercase);
        if (maxDigits > 0) {
            if (maxDigits <= choppedDigits) {
                return "";
            }
            builder = new StringBuilder();
            IPAddressLargeDivision.toDefaultStringRecursive(val, radix, uppercase, choppedDigits, maxDigits, dig, true, builder);
        } else {
            builder = null;
            do {
                BigInteger[] divisorRemainder = val.divideAndRemainder(radix);
                BigInteger quotient = divisorRemainder[0];
                BigInteger remainder = divisorRemainder[1];
                if (choppedDigits > 0) {
                    --choppedDigits;
                    continue;
                }
                if (builder == null) {
                    builder = new StringBuilder();
                }
                builder.append(dig[remainder.intValue()]);
                val = quotient;
            } while (!val.equals(BigInteger.ZERO));
            if (builder == null) {
                return "";
            }
            builder.reverse();
        }
        return builder.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getString() {
        String result = this.cachedString;
        if (result == null) {
            IPAddressLargeDivision iPAddressLargeDivision = this;
            synchronized (iPAddressLargeDivision) {
                result = this.cachedString;
                if (result == null) {
                    if (this.isSinglePrefixBlock() || !this.isMultiple()) {
                        result = this.getDefaultLowerString();
                    } else if (!this.isFullRange() || (result = this.getDefaultSegmentWildcardString()) == null) {
                        result = this.isPrefixBlock() ? this.getDefaultMaskedRangeString() : this.getDefaultRangeString();
                    }
                    this.cachedString = result;
                }
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected String getCachedDefaultLowerString() {
        String result = this.cachedString;
        if (result == null) {
            IPAddressLargeDivision iPAddressLargeDivision = this;
            synchronized (iPAddressLargeDivision) {
                result = this.cachedString;
                if (result == null) {
                    this.cachedString = result = this.getDefaultLowerString();
                }
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getWildcardString() {
        String result = this.cachedWildcardString;
        if (result == null) {
            IPAddressLargeDivision iPAddressLargeDivision = this;
            synchronized (iPAddressLargeDivision) {
                result = this.cachedWildcardString;
                if (result == null) {
                    if (!this.isPrefixed() || !this.isMultiple()) {
                        result = this.getString();
                    } else if (!this.isFullRange() || (result = this.getDefaultSegmentWildcardString()) == null) {
                        result = this.getDefaultRangeString();
                    }
                    this.cachedWildcardString = result;
                }
            }
        }
        return result;
    }

    @Override
    protected String getDefaultLowerString() {
        return IPAddressLargeDivision.toDefaultString(this.getValue(), this.defaultRadix, false, 0, this.getMaxDigitCount());
    }

    @Override
    protected String getDefaultRangeString() {
        int maxDigitCount = this.getMaxDigitCount();
        return IPAddressLargeDivision.toDefaultString(this.getValue(), this.defaultRadix, false, 0, maxDigitCount) + this.getDefaultRangeSeparatorString() + IPAddressLargeDivision.toDefaultString(this.getUpperValue(), this.defaultRadix, false, 0, maxDigitCount);
    }

    protected String getDefaultMaskedRangeString() {
        int maxDigitCount = this.getMaxDigitCount();
        return IPAddressLargeDivision.toDefaultString(this.getValue(), this.defaultRadix, false, 0, maxDigitCount) + this.getDefaultRangeSeparatorString() + IPAddressLargeDivision.toDefaultString(this.upperValueMasked, this.defaultRadix, false, 0, maxDigitCount);
    }

    @Override
    protected String getDefaultRangeSeparatorString() {
        return this.isExtendedDigits() ? EXTENDED_DIGITS_RANGE_SEPARATOR_STR : Address.RANGE_SEPARATOR_STR;
    }

    @Override
    protected int getLowerStringLength(int radix) {
        return this.getDigitCount(this.getValue(), radix);
    }

    @Override
    protected int getUpperStringLength(int radix) {
        return this.getDigitCount(this.getUpperValue(), radix);
    }

    @Override
    protected void getLowerString(int radix, boolean uppercase, StringBuilder appendable) {
        appendable.append(this.toDefaultString(this.getValue(), radix, uppercase, 0));
    }

    @Override
    protected void getLowerString(int radix, int choppedDigits, boolean uppercase, StringBuilder appendable) {
        appendable.append(this.toDefaultString(this.getValue(), radix, uppercase, choppedDigits));
    }

    @Override
    protected void getUpperString(int radix, boolean uppercase, StringBuilder appendable) {
        appendable.append(this.toDefaultString(this.getUpperValue(), radix, uppercase, 0));
    }

    @Override
    protected void getUpperStringMasked(int radix, boolean uppercase, StringBuilder appendable) {
        appendable.append(this.toDefaultString(this.upperValueMasked, radix, uppercase, 0));
    }

    @Override
    protected void getSplitLowerString(int radix, int choppedDigits, boolean uppercase, char splitDigitSeparator, boolean reverseSplitDigits, String stringPrefix, StringBuilder appendable) {
        StringBuilder builder = new StringBuilder();
        this.getLowerString(radix, choppedDigits, uppercase, builder);
        int prefLen = stringPrefix.length();
        for (int i = 0; i < builder.length(); ++i) {
            if (i > 0) {
                appendable.append(splitDigitSeparator);
            }
            if (prefLen > 0) {
                appendable.append(stringPrefix);
            }
            appendable.append(builder.charAt(reverseSplitDigits ? builder.length() - i - 1 : i));
        }
    }

    @Override
    protected void getSplitRangeString(String rangeSeparator, String wildcard, int radix, boolean uppercase, char splitDigitSeparator, boolean reverseSplitDigits, String stringPrefix, StringBuilder appendable) {
        StringBuilder lowerBuilder = new StringBuilder();
        StringBuilder upperBuilder = new StringBuilder();
        this.getLowerString(radix, uppercase, lowerBuilder);
        this.getUpperString(radix, uppercase, upperBuilder);
        int diff = upperBuilder.length() - lowerBuilder.length();
        if (diff > 0) {
            StringBuilder newLowerBuilder = new StringBuilder();
            while (diff-- > 0) {
                newLowerBuilder.append('0');
            }
            newLowerBuilder.append((CharSequence)lowerBuilder);
            lowerBuilder = newLowerBuilder;
        }
        boolean previousWasFull = true;
        boolean nextMustBeFull = false;
        char[] dig = IPAddressLargeDivision.getDigits(radix, uppercase);
        char zeroDigit = dig[0];
        char highestDigit = dig[radix - 1];
        int len = lowerBuilder.length();
        int prefLen = stringPrefix.length();
        for (int i = 0; i < len; ++i) {
            boolean isFullRange;
            int index = reverseSplitDigits ? len - i - 1 : i;
            char lower = lowerBuilder.charAt(index);
            char upper = upperBuilder.charAt(index);
            if (i > 0) {
                appendable.append(splitDigitSeparator);
            }
            if (lower == upper) {
                if (nextMustBeFull) {
                    throw new IncompatibleAddressException(lower, (long)upper, "ipaddress.error.splitMismatch");
                }
                if (prefLen > 0) {
                    appendable.append(stringPrefix);
                }
                appendable.append(lower);
                continue;
            }
            boolean bl = isFullRange = lower == zeroDigit && upper == highestDigit;
            if (isFullRange) {
                appendable.append(wildcard);
            } else {
                if (nextMustBeFull) {
                    throw new IncompatibleAddressException(lower, (long)upper, "ipaddress.error.splitMismatch");
                }
                if (prefLen > 0) {
                    appendable.append(stringPrefix);
                }
                appendable.append(lower);
                appendable.append(rangeSeparator);
                appendable.append(upper);
            }
            if (reverseSplitDigits) {
                if (!previousWasFull) {
                    throw new IncompatibleAddressException(lower, (long)upper, "ipaddress.error.splitMismatch");
                }
                previousWasFull = isFullRange;
                continue;
            }
            nextMustBeFull = true;
        }
    }

    @Override
    protected int getSplitRangeStringLength(String rangeSeparator, String wildcard, int leadingZeroCount, int radix, boolean uppercase, char splitDigitSeparator, boolean reverseSplitDigits, String stringPrefix) {
        int digitsLength = -1;
        int stringPrefixLength = stringPrefix.length();
        StringBuilder lowerBuilder = new StringBuilder();
        StringBuilder upperBuilder = new StringBuilder();
        this.getLowerString(radix, uppercase, lowerBuilder);
        this.getUpperString(radix, uppercase, upperBuilder);
        char[] dig = IPAddressLargeDivision.getDigits(radix, uppercase);
        char zeroDigit = dig[0];
        char highestDigit = dig[radix - 1];
        int remainingAfterLoop = leadingZeroCount;
        for (int i = 1; i <= upperBuilder.length(); ++i) {
            boolean isFullRange;
            char lower = i <= lowerBuilder.length() ? lowerBuilder.charAt(lowerBuilder.length() - i) : (char)'\u0000';
            int upperIndex = upperBuilder.length() - i;
            char upper = upperBuilder.charAt(upperIndex);
            boolean bl = isFullRange = lower == zeroDigit && upper == highestDigit;
            if (isFullRange) {
                digitsLength += wildcard.length() + 1;
                continue;
            }
            if (lower != upper) {
                digitsLength += (stringPrefixLength << 1) + 4;
                continue;
            }
            remainingAfterLoop += upperIndex + 1;
            break;
        }
        if (remainingAfterLoop > 0) {
            digitsLength += remainingAfterLoop * (stringPrefixLength + 2);
        }
        return digitsLength;
    }

    @Override
    protected int getRangeDigitCount(int radix) {
        if (radix < 2 || radix > 85) {
            throw new IllegalArgumentException();
        }
        if (!this.isMultiple()) {
            return 0;
        }
        BigInteger val = this.getValue();
        BigInteger upperVal = this.getUpperValue();
        int count = 1;
        BigInteger bigRadix = BigInteger.valueOf(radix);
        BigInteger bigUpper = BigInteger.valueOf(radix - 1);
        while (true) {
            BigInteger[] highLow = val.divideAndRemainder(bigRadix);
            BigInteger quotient = highLow[0];
            BigInteger remainder = highLow[1];
            if (!remainder.equals(BigInteger.ZERO)) break;
            highLow = upperVal.divideAndRemainder(bigRadix);
            BigInteger upperQuotient = highLow[0];
            remainder = highLow[1];
            if (!remainder.equals(bigUpper)) break;
            val = quotient;
            upperVal = upperQuotient;
            if (val.equals(upperVal)) {
                return count;
            }
            ++count;
        }
        return 0;
    }

    @Override
    public int getPrefixAdjustedRangeString(int segmentIndex, AddressSegmentParams params, StringBuilder appendable) {
        return super.getPrefixAdjustedRangeString(segmentIndex, params, appendable);
    }

    @Override
    public boolean isPrefixBlock() {
        return this.isPrefixBlock;
    }

    @Override
    public boolean isSinglePrefixBlock() {
        return this.isSinglePrefixBlock;
    }

    @Override
    public Integer getDivisionPrefixLength() {
        return this.networkPrefixLength;
    }

    @Override
    public boolean isPrefixed() {
        return this.networkPrefixLength != null;
    }

    @Override
    protected boolean isSameValues(AddressDivisionBase otherSegment) {
        return otherSegment instanceof IPAddressLargeDivision && super.isSameValues(otherSegment);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof IPAddressLargeDivision) {
            IPAddressLargeDivision otherSegments = (IPAddressLargeDivision)other;
            return this.getBitCount() == otherSegments.getBitCount() && otherSegments.isSameValues(this);
        }
        return false;
    }
}

