/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.standard;

import inet.ipaddr.Address;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.standard.AddressDivisionGrouping;
import inet.ipaddr.format.validate.ParsedIPAddress;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public abstract class AddressDivision
extends AddressDivisionBase {
    private static final long serialVersionUID = 4L;

    protected AddressDivision() {
    }

    @Override
    protected byte[] getBytesImpl(boolean low) {
        int bitCount = this.getBitCount();
        byte[] bytes = new byte[this.getByteCount()];
        int byteIndex = bytes.length - 1;
        long segmentValue = low ? this.getDivisionValue() : this.getUpperDivisionValue();
        while (true) {
            int n = byteIndex--;
            bytes[n] = (byte)((long)bytes[n] | segmentValue);
            segmentValue >>= 8;
            if (bitCount <= 8) {
                return bytes;
            }
            bitCount -= 8;
        }
    }

    @Override
    public boolean isMultiple() {
        return this.getDivisionValue() != this.getUpperDivisionValue();
    }

    @Override
    public int getMinPrefixLengthForBlock() {
        int upperOnes;
        int result = this.getBitCount();
        if (!this.isMultiple()) {
            return result;
        }
        if (this.isFullRange()) {
            return 0;
        }
        int lowerZeros = Long.numberOfTrailingZeros(this.getDivisionValue());
        if (lowerZeros != 0 && (upperOnes = Long.numberOfTrailingZeros(this.getUpperDivisionValue() ^ 0xFFFFFFFFFFFFFFFFL)) != 0) {
            int prefixedBitCount = Math.min(lowerZeros, upperOnes);
            result -= prefixedBitCount;
        }
        return result;
    }

    @Override
    public Integer getPrefixLengthForSingleBlock() {
        int shift;
        int divPrefix = this.getMinPrefixLengthForBlock();
        long lowerValue = this.getDivisionValue();
        long upperValue = this.getUpperDivisionValue();
        int bitCount = this.getBitCount();
        if (divPrefix == bitCount ? lowerValue == upperValue : lowerValue >>> (shift = bitCount - divPrefix) == upperValue >>> shift) {
            return AddressDivisionGrouping.cacheBits(divPrefix);
        }
        return null;
    }

    @Override
    protected String getDefaultRangeSeparatorString() {
        return Address.RANGE_SEPARATOR_STR;
    }

    public long getMaxValue() {
        return -1L << this.getBitCount() ^ 0xFFFFFFFFFFFFFFFFL;
    }

    @Override
    public boolean isZero() {
        return !this.isMultiple() && this.includesZero();
    }

    @Override
    public boolean includesZero() {
        return this.getDivisionValue() == 0L;
    }

    @Override
    public boolean isMax() {
        return !this.isMultiple() && this.includesMax();
    }

    @Override
    public boolean includesMax() {
        return this.getUpperDivisionValue() == this.getMaxValue();
    }

    public abstract long getDivisionValue();

    public abstract long getUpperDivisionValue();

    @Override
    public int hashCode() {
        int res = this.hashCode;
        if (res == 0) {
            this.hashCode = res = AddressDivision.createHashCode(this.getDivisionValue(), this.getUpperDivisionValue());
        }
        return res;
    }

    @Override
    public BigInteger getValue() {
        return BigInteger.valueOf(this.getDivisionValue());
    }

    @Override
    public BigInteger getUpperValue() {
        return BigInteger.valueOf(this.getUpperDivisionValue());
    }

    static boolean testRange(long lowerValue, long upperValue, long finalUpperValue, long networkMask, long hostMask) {
        return lowerValue == (lowerValue & networkMask) && finalUpperValue == (upperValue | hostMask);
    }

    protected boolean isPrefixBlock(long divisionValue, long upperValue, int divisionPrefixLen) {
        if (divisionPrefixLen == 0) {
            return divisionValue == 0L && upperValue == this.getMaxValue();
        }
        int bitCount = this.getBitCount();
        long ones = -1L;
        long divisionBitMask = ones << bitCount ^ 0xFFFFFFFFFFFFFFFFL;
        long divisionPrefixMask = ones << bitCount - divisionPrefixLen;
        long divisionNonPrefixMask = divisionPrefixMask ^ 0xFFFFFFFFFFFFFFFFL;
        return AddressDivision.testRange(divisionValue, upperValue, upperValue, divisionPrefixMask & divisionBitMask, divisionNonPrefixMask);
    }

    protected boolean isSinglePrefixBlock(long divisionValue, long upperValue, int divisionPrefixLen) {
        long ones = -1L;
        int bitCount = this.getBitCount();
        long divisionBitMask = ones << bitCount ^ 0xFFFFFFFFFFFFFFFFL;
        long divisionPrefixMask = ones << bitCount - divisionPrefixLen;
        long divisionNonPrefixMask = divisionPrefixMask ^ 0xFFFFFFFFFFFFFFFFL;
        return AddressDivision.testRange(divisionValue, divisionValue, upperValue, divisionPrefixMask & divisionBitMask, divisionNonPrefixMask);
    }

    @Override
    public boolean isBoundedBy(int value) {
        return this.getUpperDivisionValue() < (long)value;
    }

    public boolean matches(long value) {
        return !this.isMultiple() && value == this.getDivisionValue();
    }

    public boolean matchesWithMask(long value, long mask) {
        long diffBits;
        int leadingZeros;
        long fullMask;
        if (this.isMultiple() && ((fullMask = -1L >>> (leadingZeros = Long.numberOfLeadingZeros(diffBits = this.getDivisionValue() ^ this.getUpperDivisionValue()))) & mask) != 0L) {
            return false;
        }
        return value == (this.getDivisionValue() & mask);
    }

    public boolean matchesWithMask(long lowerValue, long upperValue, long mask) {
        long thisUpperValue;
        if (lowerValue == upperValue) {
            return this.matchesWithMask(lowerValue, mask);
        }
        if (!this.isMultiple()) {
            return false;
        }
        long thisValue = this.getDivisionValue();
        ParsedIPAddress.Masker masker = AddressDivision.maskRange(thisValue, thisUpperValue = this.getUpperDivisionValue(), mask, this.getMaxValue());
        if (!masker.isSequential()) {
            return false;
        }
        return lowerValue == masker.getMaskedLower(thisValue, mask) && upperValue == masker.getMaskedUpper(thisUpperValue, mask);
    }

    @Override
    protected boolean isSameValues(AddressDivisionBase other) {
        if (other instanceof AddressDivision) {
            AddressDivision otherDivision = (AddressDivision)other;
            return this.getDivisionValue() == otherDivision.getDivisionValue() && this.getUpperDivisionValue() == otherDivision.getUpperDivisionValue();
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AddressDivision) {
            AddressDivision other = (AddressDivision)o;
            return this.getBitCount() == other.getBitCount() && other.isSameValues(this);
        }
        return false;
    }

    public boolean isMaskCompatibleWithRange(int maskValue) {
        long value = this.getDivisionValue();
        long upperValue = this.getUpperDivisionValue();
        long maxValue = this.getMaxValue();
        return AddressDivision.maskRange(value, upperValue, maskValue, maxValue).isSequential();
    }

    protected static ParsedIPAddress.Masker maskRange(long value, long upperValue, long maskValue, long maxValue) {
        return ParsedIPAddress.maskRange(value, upperValue, maskValue, maxValue);
    }

    public static MaskResult maskRange(long value, long upperValue, long maskValue) {
        ParsedIPAddress.Masker masker = ParsedIPAddress.maskRange(value, upperValue, maskValue);
        return new MaskResult(value, upperValue, maskValue, masker);
    }

    public boolean isBitwiseOrCompatibleWithRange(int maskValue) {
        long maxValue;
        long upperValue;
        if (!this.isMultiple()) {
            return true;
        }
        long value = this.getDivisionValue();
        return AddressDivision.bitwiseOrRange(value, upperValue = this.getUpperDivisionValue(), maskValue, maxValue = this.getMaxValue()) != null;
    }

    protected static ParsedIPAddress.BitwiseOrer bitwiseOrRange(long value, long upperValue, long maskValue, long maxValue) {
        return ParsedIPAddress.bitwiseOrRange(value, upperValue, maskValue, maxValue);
    }

    public static BitwiseOrResult bitwiseOrRange(long value, long upperValue, long maskValue) {
        ParsedIPAddress.BitwiseOrer masker = ParsedIPAddress.bitwiseOrRange(value, upperValue, maskValue);
        return new BitwiseOrResult(value, upperValue, maskValue, masker);
    }

    public boolean hasUppercaseVariations(int radix, boolean lowerOnly) {
        boolean isPowerOfTwo;
        if (radix < 2) {
            throw new IllegalArgumentException();
        }
        if (radix <= 10) {
            return false;
        }
        int shift = 0;
        long mask = 0L;
        switch (radix) {
            case 16: {
                isPowerOfTwo = true;
                shift = 4;
                mask = 15L;
                break;
            }
            default: {
                boolean bl = isPowerOfTwo = (radix & radix - 1) == 0;
                if (!isPowerOfTwo) break;
                shift = Integer.numberOfTrailingZeros(radix);
                mask = -1L << shift ^ 0xFFFFFFFFFFFFFFFFL;
            }
        }
        boolean handledUpper = false;
        long value = this.getDivisionValue();
        while (true) {
            if (value > 0L) {
                long checkVal;
                long l = checkVal = isPowerOfTwo ? mask & value : value % (long)radix;
                if (checkVal >= 10L) {
                    return true;
                }
                if (isPowerOfTwo) {
                    value >>>= shift;
                    continue;
                }
                value /= (long)radix;
                continue;
            }
            if (handledUpper || lowerOnly) break;
            value = this.getUpperDivisionValue();
            handledUpper = true;
        }
        return false;
    }

    @Override
    public int getDigitCount(int radix) {
        if (!this.isMultiple() && radix == this.getDefaultTextualRadix()) {
            return this.getWildcardString().length();
        }
        return AddressDivision.getDigitCount(this.getUpperDivisionValue(), radix);
    }

    @Override
    public int getMaxDigitCount(int radix) {
        int defaultRadix = this.getDefaultTextualRadix();
        if (radix == defaultRadix) {
            return this.getMaxDigitCount();
        }
        return AddressDivision.getMaxDigitCount(radix, this.getBitCount(), this.getMaxValue());
    }

    @Override
    protected int adjustLowerLeadingZeroCount(int leadingZeroCount, int radix) {
        return this.adjustLeadingZeroCount(leadingZeroCount, this.getDivisionValue(), radix);
    }

    @Override
    protected int adjustUpperLeadingZeroCount(int leadingZeroCount, int radix) {
        return this.adjustLeadingZeroCount(leadingZeroCount, this.getUpperDivisionValue(), radix);
    }

    private int adjustLeadingZeroCount(int leadingZeroCount, long value, int radix) {
        if (leadingZeroCount < 0) {
            int width = AddressDivision.getDigitCount(value, radix);
            return Math.max(0, this.getMaxDigitCount(radix) - width);
        }
        return leadingZeroCount;
    }

    @Override
    protected String getWildcardString() {
        return super.getWildcardString();
    }

    @Override
    protected int getLowerStringLength(int radix) {
        return AddressDivision.toUnsignedStringLength(this.getDivisionValue(), radix);
    }

    @Override
    protected int getUpperStringLength(int radix) {
        return AddressDivision.toUnsignedStringLength(this.getUpperDivisionValue(), radix);
    }

    @Override
    protected void getLowerString(int radix, boolean uppercase, StringBuilder appendable) {
        AddressDivision.toUnsignedStringCased(this.getDivisionValue(), radix, 0, uppercase, appendable);
    }

    @Override
    protected void getUpperString(int radix, boolean uppercase, StringBuilder appendable) {
        AddressDivision.toUnsignedStringCased(this.getUpperDivisionValue(), radix, 0, uppercase, appendable);
    }

    @Override
    protected void getUpperStringMasked(int radix, boolean uppercase, StringBuilder appendable) {
        this.getUpperString(radix, uppercase, appendable);
    }

    @Override
    protected void getLowerString(int radix, int rangeDigits, boolean uppercase, StringBuilder appendable) {
        AddressDivision.toUnsignedStringCased(this.getDivisionValue(), radix, rangeDigits, uppercase, appendable);
    }

    @Override
    protected void getSplitLowerString(int radix, int choppedDigits, boolean uppercase, char splitDigitSeparator, boolean reverseSplitDigits, String stringPrefix, StringBuilder appendable) {
        AddressDivision.toSplitUnsignedString(this.getDivisionValue(), radix, choppedDigits, uppercase, splitDigitSeparator, reverseSplitDigits, stringPrefix, appendable);
    }

    @Override
    protected void getSplitRangeString(String rangeSeparator, String wildcard, int radix, boolean uppercase, char splitDigitSeparator, boolean reverseSplitDigits, String stringPrefix, StringBuilder appendable) {
        AddressDivision.toUnsignedSplitRangeString(this.getDivisionValue(), this.getUpperDivisionValue(), rangeSeparator, wildcard, radix, uppercase, splitDigitSeparator, reverseSplitDigits, stringPrefix, appendable);
    }

    @Override
    protected int getSplitRangeStringLength(String rangeSeparator, String wildcard, int leadingZeroCount, int radix, boolean uppercase, char splitDigitSeparator, boolean reverseSplitDigits, String stringPrefix) {
        return AddressDivision.toUnsignedSplitRangeStringLength(this.getDivisionValue(), this.getUpperDivisionValue(), rangeSeparator, wildcard, leadingZeroCount, radix, uppercase, splitDigitSeparator, reverseSplitDigits, stringPrefix);
    }

    @Override
    protected String getDefaultLowerString() {
        return AddressDivision.toDefaultString(this.getDivisionValue(), this.getDefaultTextualRadix());
    }

    @Override
    protected String getDefaultRangeString() {
        return this.getDefaultRangeString(this.getDivisionValue(), this.getUpperDivisionValue(), this.getDefaultTextualRadix());
    }

    protected String getDefaultRangeString(long val1, long val2, int radix) {
        if (radix == 10) {
            int remainder;
            int quotient;
            int len1;
            int len2;
            if (val2 < 10L) {
                len2 = 1;
            } else if (val2 < 100L) {
                len2 = 2;
            } else if (val2 < 1000L) {
                len2 = 3;
            } else {
                return this.buildDefaultRangeString(radix);
            }
            int value2 = (int)val2;
            if (val1 < 10L) {
                len1 = 1;
            } else if (val1 < 100L) {
                len1 = 2;
            } else if (val1 < 1000L) {
                len1 = 3;
            } else {
                return this.buildDefaultRangeString(radix);
            }
            int value1 = (int)val1;
            char[] chars = new char[len2 += len1 + 1];
            chars[len1] = 45;
            char[] dig = DIGITS;
            do {
                quotient = value1 * 52429 >>> 19;
                remainder = value1 - ((quotient << 3) + (quotient << 1));
                chars[--len1] = dig[remainder];
            } while ((value1 = quotient) != 0);
            do {
                quotient = value2 * 52429 >>> 19;
                remainder = value2 - ((quotient << 3) + (quotient << 1));
                chars[--len2] = dig[remainder];
            } while ((value2 = quotient) != 0);
            return new String(chars);
        }
        if (radix == 16) {
            int remainder;
            int quotient;
            int len1;
            int len2;
            if (val2 < 16L) {
                len2 = 1;
            } else if (val2 < 256L) {
                len2 = 2;
            } else if (val2 < 4096L) {
                len2 = 3;
            } else if (val2 < 65536L) {
                len2 = 4;
            } else {
                return this.buildDefaultRangeString(radix);
            }
            int value2 = (int)val2;
            if (val1 < 16L) {
                len1 = 1;
            } else if (val1 < 256L) {
                len1 = 2;
            } else if (val1 < 4096L) {
                len1 = 3;
            } else if (val1 < 65536L) {
                len1 = 4;
            } else {
                return this.buildDefaultRangeString(radix);
            }
            int value1 = (int)val1;
            char[] chars = new char[len2 += len1 + 1];
            chars[len1] = 45;
            char[] dig = DIGITS;
            do {
                quotient = value1 >>> 4;
                remainder = value1 - (quotient << 4);
                chars[--len1] = dig[remainder];
            } while ((value1 = quotient) != 0);
            do {
                quotient = value2 >>> 4;
                remainder = value2 - (quotient << 4);
                chars[--len2] = dig[remainder];
            } while ((value2 = quotient) != 0);
            return new String(chars);
        }
        return this.buildDefaultRangeString(radix);
    }

    private String buildDefaultRangeString(int radix) {
        StringBuilder builder = new StringBuilder(20);
        this.getRangeString(IPAddress.RANGE_SEPARATOR_STR, 0, 0, "", radix, false, false, builder);
        return builder.toString();
    }

    protected static String toDefaultString(long val, int radix) {
        if (radix < 2 || radix > 85 || val < 0L) {
            throw new IllegalArgumentException();
        }
        if (val == 0L) {
            return "0";
        }
        if (val == 1L) {
            return "1";
        }
        if (radix == 10) {
            int quotient;
            int value;
            int len;
            if (val < 10L) {
                return String.valueOf(DIGITS, (int)val, 1);
            }
            if (val < 100L) {
                len = 2;
                value = (int)val;
            } else if (val < 1000L) {
                len = 3;
                value = (int)val;
            } else {
                return Long.toString(val, radix);
            }
            char[] chars = new char[len];
            char[] dig = DIGITS;
            do {
                quotient = value * 52429 >>> 19;
                int remainder = value - ((quotient << 3) + (quotient << 1));
                chars[--len] = dig[remainder];
            } while ((value = quotient) != 0);
            return new String(chars);
        }
        if (radix == 16) {
            int quotient;
            int value;
            int len;
            if (val < 16L) {
                return String.valueOf(DIGITS, (int)val, 1);
            }
            if (val < 256L) {
                len = 2;
                value = (int)val;
            } else if (val < 4096L) {
                len = 3;
                value = (int)val;
            } else if (val < 65536L) {
                if (val == 65535L) {
                    return "ffff";
                }
                value = (int)val;
                len = 4;
            } else {
                return Long.toString(val, radix);
            }
            char[] chars = new char[len];
            char[] dig = DIGITS;
            do {
                quotient = value >>> 4;
                int remainder = value - (quotient << 4);
                chars[--len] = dig[remainder];
            } while ((value = quotient) != 0);
            return new String(chars);
        }
        return Long.toString(val, radix);
    }

    private static int toUnsignedSplitRangeStringLength(long lower, long upper, String rangeSeparator, String wildcard, int leadingZerosCount, int radix, boolean uppercase, char splitDigitSeparator, boolean reverseSplitDigits, String stringPrefix) {
        int remaining;
        if (radix < 2 || radix > 85) {
            throw new IllegalArgumentException();
        }
        int digitsLength = -1;
        int stringPrefixLength = stringPrefix.length();
        do {
            boolean isFull;
            int upperDigit = (int)(upper % (long)radix);
            int lowerDigit = (int)(lower % (long)radix);
            boolean bl = isFull = lowerDigit == 0 && upperDigit == radix - 1;
            if (isFull) {
                digitsLength += wildcard.length() + 1;
                continue;
            }
            digitsLength += (stringPrefixLength << 1) + 4;
        } while ((upper /= (long)radix) != (lower /= (long)radix));
        int n = remaining = upper == 0L ? 0 : AddressDivision.toUnsignedStringLength(upper, radix);
        if ((remaining += leadingZerosCount) > 0) {
            digitsLength += remaining * (stringPrefixLength + 2);
        }
        return digitsLength;
    }

    protected static BigInteger getRadixPower(BigInteger radix, int power) {
        return AddressDivisionBase.getRadixPower(radix, power);
    }

    private static void toSplitUnsignedString(long value, int radix, int choppedDigits, boolean uppercase, char splitDigitSeparator, boolean reverseSplitDigits, String stringPrefix, StringBuilder appendable) {
        int front = appendable.length();
        AddressDivision.appendDigits(value, radix, choppedDigits, uppercase, splitDigitSeparator, stringPrefix, appendable);
        if (!reverseSplitDigits) {
            int stringPrefixLen = stringPrefix.length();
            front += stringPrefixLen;
            for (int back = appendable.length() - 1; front < back; front += stringPrefixLen, back -= stringPrefixLen) {
                char frontChar = appendable.charAt(front);
                appendable.setCharAt(front, appendable.charAt(back));
                appendable.setCharAt(back, frontChar);
                front += 2;
                back -= 2;
            }
        }
    }

    private static void toUnsignedSplitRangeString(long lower, long upper, String rangeSeparator, String wildcard, int radix, boolean uppercase, char splitDigitSeparator, boolean reverseSplitDigits, String stringPrefix, StringBuilder appendable) {
        int front = appendable.length();
        AddressDivision.appendDigits(lower, upper, rangeSeparator, wildcard, radix, uppercase, splitDigitSeparator, reverseSplitDigits, stringPrefix, appendable);
        if (!reverseSplitDigits) {
            int back = appendable.length() - 1;
            while (front < back) {
                char frontChar = appendable.charAt(front);
                appendable.setCharAt(front++, appendable.charAt(back));
                appendable.setCharAt(back--, frontChar);
            }
        }
    }

    private static void appendDigits(long value, int radix, int choppedDigits, boolean uppercase, char splitDigitSeparator, String stringPrefix, StringBuilder appendable) {
        if (radix < 2 || radix > 85) {
            throw new IllegalArgumentException();
        }
        boolean useInts = value <= Integer.MAX_VALUE;
        int value2 = useInts ? (int)value : radix;
        char[] dig = uppercase ? UPPERCASE_DIGITS : DIGITS;
        int prefLen = stringPrefix.length();
        while (value2 >= radix) {
            int index;
            if (useInts) {
                int val = value2;
                value2 /= radix;
                if (choppedDigits > 0) {
                    --choppedDigits;
                    continue;
                }
                index = val % radix;
            } else {
                long val = value;
                if ((value /= (long)radix) <= Integer.MAX_VALUE) {
                    useInts = true;
                    value2 = (int)value;
                }
                if (choppedDigits > 0) {
                    --choppedDigits;
                    continue;
                }
                index = (int)(val % (long)radix);
            }
            if (prefLen > 0) {
                appendable.append(stringPrefix);
            }
            appendable.append(dig[index]);
            appendable.append(splitDigitSeparator);
        }
        if (choppedDigits == 0) {
            if (prefLen > 0) {
                appendable.append(stringPrefix);
            }
            appendable.append(dig[value2]);
        }
    }

    private static void appendDigits(long lower, long upper, String rangeSeparator, String wildcard, int radix, boolean uppercase, char splitDigitSeparator, boolean reverseSplitDigits, String stringPrefix, StringBuilder appendable) {
        int lowerInt;
        int upperInt;
        boolean useInts;
        if (radix < 2 || radix > 85) {
            throw new IllegalArgumentException();
        }
        char[] dig = uppercase ? UPPERCASE_DIGITS : DIGITS;
        boolean previousWasFullRange = true;
        boolean bl = useInts = upper <= Integer.MAX_VALUE;
        if (useInts) {
            upperInt = (int)upper;
            lowerInt = (int)lower;
        } else {
            upperInt = lowerInt = radix;
        }
        int prefLen = stringPrefix.length();
        while (true) {
            int k;
            int lowerDigit;
            int upperDigit;
            if (useInts) {
                int ud = upperInt;
                upperDigit = upperInt % radix;
                upperInt /= radix;
                if (ud == lowerInt) {
                    lowerInt = upperInt;
                    lowerDigit = upperDigit;
                } else {
                    lowerDigit = lowerInt % radix;
                    lowerInt /= radix;
                }
            } else {
                long ud = upper;
                upperDigit = (int)(upper % (long)radix);
                upper /= (long)radix;
                if (ud == lower) {
                    lower = upper;
                    lowerDigit = upperDigit;
                } else {
                    lowerDigit = (int)(lower % (long)radix);
                    lower /= (long)radix;
                }
                if (upper <= Integer.MAX_VALUE) {
                    useInts = true;
                    upperInt = (int)upper;
                    lowerInt = (int)lower;
                }
            }
            if (lowerDigit == upperDigit) {
                previousWasFullRange = false;
                if (reverseSplitDigits) {
                    if (prefLen > 0) {
                        appendable.append(stringPrefix);
                    }
                    appendable.append(dig[lowerDigit]);
                } else {
                    appendable.append(dig[lowerDigit]);
                    for (k = prefLen - 1; k >= 0; --k) {
                        appendable.append(stringPrefix.charAt(k));
                    }
                }
            } else {
                if (!previousWasFullRange) {
                    throw new IncompatibleAddressException(lower, upper, "ipaddress.error.splitMismatch");
                }
                boolean bl2 = previousWasFullRange = lowerDigit == 0 && upperDigit == radix - 1;
                if (previousWasFullRange && wildcard != null) {
                    if (reverseSplitDigits) {
                        appendable.append(wildcard);
                    } else {
                        for (k = wildcard.length() - 1; k >= 0; --k) {
                            appendable.append(wildcard.charAt(k));
                        }
                    }
                } else if (reverseSplitDigits) {
                    if (prefLen > 0) {
                        appendable.append(stringPrefix);
                    }
                    appendable.append(dig[lowerDigit]);
                    appendable.append(rangeSeparator);
                    appendable.append(dig[upperDigit]);
                } else {
                    appendable.append(dig[upperDigit]);
                    appendable.append(rangeSeparator);
                    appendable.append(dig[lowerDigit]);
                    for (k = prefLen - 1; k >= 0; --k) {
                        appendable.append(stringPrefix.charAt(k));
                    }
                }
            }
            if (upperInt == 0) break;
            appendable.append(splitDigitSeparator);
        }
    }

    @Override
    protected int getRangeDigitCount(int radix) {
        if (!this.isMultiple()) {
            return 0;
        }
        if (radix == this.getDefaultTextualRadix()) {
            return this.getRangeDigitCountImpl();
        }
        if (radix < 2 || radix > 85) {
            throw new IllegalArgumentException();
        }
        return AddressDivision.calculateRangeDigitCount(radix, this.getDivisionValue(), this.getUpperDivisionValue(), this.getMaxValue());
    }

    protected int getRangeDigitCountImpl() {
        return AddressDivision.calculateRangeDigitCount(this.getDefaultTextualRadix(), this.getDivisionValue(), this.getUpperDivisionValue(), this.getMaxValue());
    }

    private static int calculateRangeDigitCount(int radix, long value, long upperValue, long maxValue) {
        long max;
        long upperRemainder;
        long lowerRemainder;
        int factor = radix;
        int numDigits = 1;
        while ((lowerRemainder = value % (long)factor) == 0L && (upperRemainder = upperValue % (long)factor) == (max = maxValue / (long)factor == upperValue / (long)factor ? maxValue % (long)factor : (long)(factor - 1))) {
            if (upperValue - upperRemainder == value) {
                return numDigits;
            }
            ++numDigits;
            factor *= radix;
        }
        return 0;
    }

    protected static int reverseBits(byte b) {
        int x = b;
        x = (x & 0xAA) >>> 1 | (x & 0x55) << 1;
        x = (x & 0xCC) >>> 2 | (x & 0x33) << 2;
        x = 0xFF & (x >>> 4 | x << 4);
        return x;
    }

    protected static int reverseBits(short b) {
        int x = b;
        x = (x & 0xAAAA) >>> 1 | (x & 0x5555) << 1;
        x = (x & 0xCCCC) >>> 2 | (x & 0x3333) << 2;
        x = (x & 0xF0F0) >>> 4 | (x & 0xF0F) << 4;
        return 0xFFFF & (x >>> 8 | x << 8);
    }

    protected static int reverseBits(int i) {
        int x = i;
        x = (x & 0xAAAAAAAA) >>> 1 | (x & 0x55555555) << 1;
        x = (x & 0xCCCCCCCC) >>> 2 | (x & 0x33333333) << 2;
        x = (x & 0xF0F0F0F0) >>> 4 | (x & 0xF0F0F0F) << 4;
        x = (x & 0xFF00FF00) >>> 8 | (x & 0xFF00FF) << 8;
        return x >>> 16 | x << 16;
    }

    protected static <S extends AddressSegment> int getPrefixValueCount(S segment, int segmentPrefixLength) {
        int shiftAdjustment = segment.getBitCount() - segmentPrefixLength;
        return (segment.getUpperSegmentValue() >>> shiftAdjustment) - (segment.getSegmentValue() >>> shiftAdjustment) + 1;
    }

    protected static <S extends AddressSegment> Iterator<S> identityIterator(final S original) {
        return new Iterator<S>(){
            boolean done;

            @Override
            public boolean hasNext() {
                return !this.done;
            }

            @Override
            public S next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.done = true;
                return original;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    protected static <S extends AddressSegment> Iterator<S> iterator(S original, AddressNetwork.AddressSegmentCreator<S> creator, Integer segmentPrefixLength, boolean isPrefixIterator, boolean isBlockIterator) {
        return AddressDivision.iterator(original, original.getSegmentValue(), original.getUpperSegmentValue(), original.getBitCount(), creator, segmentPrefixLength, isPrefixIterator, isBlockIterator);
    }

    protected static <S extends AddressSegment> Iterator<S> iterator(final S original, final int originalLower, final int originalUpper, int bitCount, final AddressNetwork.AddressSegmentCreator<S> creator, final Integer segmentPrefixLength, boolean isPrefixIterator, final boolean isBlockIterator) {
        int upperShiftMask;
        int shiftMask;
        int shiftAdjustment;
        if (isPrefixIterator) {
            shiftAdjustment = bitCount - segmentPrefixLength;
            shiftMask = -1 << shiftAdjustment;
            upperShiftMask = ~shiftMask;
        } else {
            upperShiftMask = 0;
            shiftMask = 0;
            shiftAdjustment = 0;
        }
        if (original != null && !original.isMultiple()) {
            return new Iterator<S>(){
                boolean done;

                @Override
                public boolean hasNext() {
                    return !this.done;
                }

                @Override
                public S next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.done = true;
                    if (isBlockIterator) {
                        return creator.createSegment(originalLower & shiftMask, originalUpper | upperShiftMask, segmentPrefixLength);
                    }
                    return original;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
        if (isPrefixIterator) {
            if (isBlockIterator) {
                return new Iterator<S>(){
                    private boolean notDone = true;
                    private int current = originalLower;
                    private int last = originalUpper;
                    {
                        this.current >>>= shiftAdjustment;
                        this.last >>>= shiftAdjustment;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.notDone;
                    }

                    @Override
                    public S next() {
                        if (!this.notDone) {
                            throw new NoSuchElementException();
                        }
                        int cur = this.current;
                        int blockLow = cur << shiftAdjustment;
                        Object result = creator.createSegment(blockLow, blockLow | upperShiftMask, segmentPrefixLength);
                        if (++cur > this.last) {
                            this.notDone = false;
                        } else {
                            this.current = cur;
                        }
                        return result;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
            return new Iterator<S>(){
                private boolean notDone = true;
                private boolean notFirst;
                private int current = originalLower;
                private int last = originalUpper;
                {
                    this.current >>>= shiftAdjustment;
                    this.last >>>= shiftAdjustment;
                }

                @Override
                public boolean hasNext() {
                    return this.notDone;
                }

                @Override
                public S next() {
                    int high;
                    boolean notDne;
                    int low;
                    if (!this.notDone) {
                        throw new NoSuchElementException();
                    }
                    int cur = this.current;
                    int blockLow = cur << shiftAdjustment;
                    int blockHigh = blockLow | upperShiftMask;
                    this.current = ++cur;
                    if (this.notFirst) {
                        low = blockLow;
                    } else {
                        low = originalLower;
                        this.notFirst = true;
                    }
                    boolean bl = notDne = cur <= this.last;
                    if (notDne) {
                        high = blockHigh;
                    } else {
                        high = originalUpper;
                        this.notDone = false;
                    }
                    return creator.createSegment(low, high, segmentPrefixLength);
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
        return new Iterator<S>(){
            private boolean notDone = true;
            private int current = originalLower;
            private int last = originalUpper;

            @Override
            public boolean hasNext() {
                return this.notDone;
            }

            @Override
            public S next() {
                if (!this.notDone) {
                    throw new NoSuchElementException();
                }
                Object result = creator.createSegment(this.current, segmentPrefixLength);
                this.notDone = ++this.current <= this.last;
                return result;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    protected static <S extends AddressSegment> S setPrefixedSegment(S original, Integer oldSegmentPrefixLength, Integer newSegmentPrefixLength, boolean zeroed, AddressNetwork.AddressSegmentCreator<S> creator) {
        int newUpper;
        int newLower;
        if (Objects.equals(oldSegmentPrefixLength, newSegmentPrefixLength)) {
            return original;
        }
        if (zeroed) {
            int prefixMask;
            int bitCount = original.getBitCount();
            int allOnes = -1;
            if (oldSegmentPrefixLength != null) {
                if (newSegmentPrefixLength == null) {
                    prefixMask = allOnes << bitCount - oldSegmentPrefixLength;
                } else if (oldSegmentPrefixLength > newSegmentPrefixLength) {
                    prefixMask = allOnes << bitCount - newSegmentPrefixLength;
                    prefixMask |= ~(allOnes << bitCount - oldSegmentPrefixLength);
                } else {
                    prefixMask = allOnes << bitCount - oldSegmentPrefixLength;
                    prefixMask |= ~(allOnes << bitCount - newSegmentPrefixLength);
                }
            } else {
                prefixMask = allOnes << bitCount - newSegmentPrefixLength;
            }
            int value = original.getSegmentValue();
            int upperValue = original.getUpperSegmentValue();
            long maxValue = -1L << original.getBitCount() ^ 0xFFFFFFFFFFFFFFFFL;
            ParsedIPAddress.Masker masker = AddressDivision.maskRange(value, upperValue, prefixMask, maxValue);
            if (!masker.isSequential()) {
                throw new IncompatibleAddressException(original, "ipaddress.error.maskMismatch");
            }
            newLower = (int)masker.getMaskedLower(value, prefixMask);
            newUpper = (int)masker.getMaskedUpper(upperValue, prefixMask);
        } else {
            newLower = original.getSegmentValue();
            newUpper = original.getUpperSegmentValue();
        }
        return creator.createSegment(newLower, newUpper, newSegmentPrefixLength);
    }

    protected static <S extends AddressSegment> boolean isReversibleRangePerByte(S seg) {
        int byteCount = seg.getByteCount();
        int bitCount = seg.getBitCount();
        int val = seg.getSegmentValue();
        int upperVal = seg.getUpperSegmentValue();
        for (int i = 1; i <= byteCount; ++i) {
            int bitShift = i << 3;
            int shift = bitCount - bitShift;
            int byteVal = 0xFF & val >> shift;
            int upperByteVal = 0xFF & upperVal >> shift;
            if (byteVal == upperByteVal) continue;
            if (byteVal > 1 || upperByteVal < 254) {
                return false;
            }
            if (++i <= byteCount) {
                boolean lowerIsZero = byteVal == 1;
                boolean upperIsMax = upperByteVal == 254;
                do {
                    bitShift = i << 3;
                    shift = bitCount - bitShift;
                    byteVal = 0xFF & val >> shift;
                    upperByteVal = 0xFF & upperVal >> shift;
                    if (lowerIsZero) {
                        if (byteVal != 0) {
                            return false;
                        }
                    } else {
                        if (byteVal > 1) {
                            return false;
                        }
                        boolean bl = lowerIsZero = byteVal == 1;
                    }
                    if (upperIsMax) {
                        if (upperByteVal == 255) continue;
                        return false;
                    }
                    if (upperByteVal < 254) {
                        return false;
                    }
                    boolean bl = upperIsMax = upperByteVal == 254;
                } while (++i <= byteCount);
            }
            return true;
        }
        return true;
    }

    protected static <S extends AddressSegment> boolean isReversibleRange(S segment) {
        return segment.getSegmentValue() <= 1 && segment.getUpperSegmentValue() >= segment.getMaxSegmentValue() - 1;
    }

    public static class BitwiseOrResult {
        private final long value;
        private final long upperValue;
        private final long maskValue;
        private final ParsedIPAddress.BitwiseOrer masker;

        public BitwiseOrResult(long value, long upperValue, long maskValue, ParsedIPAddress.BitwiseOrer masker) {
            this.value = value;
            this.upperValue = upperValue;
            this.maskValue = maskValue;
            this.masker = masker;
        }

        public long getOredLower() {
            return this.masker.getOredLower(this.value, this.maskValue);
        }

        public long getOredUpper() {
            return this.masker.getOredUpper(this.upperValue, this.maskValue);
        }

        public boolean isSequential() {
            return this.masker.isSequential();
        }
    }

    public static class MaskResult {
        private final long value;
        private final long upperValue;
        private final long maskValue;
        private final ParsedIPAddress.Masker masker;

        public MaskResult(long value, long upperValue, long maskValue, ParsedIPAddress.Masker masker) {
            this.value = value;
            this.upperValue = upperValue;
            this.maskValue = maskValue;
            this.masker = masker;
        }

        public long getMaskedLower() {
            return this.masker.getMaskedLower(this.value, this.maskValue);
        }

        public long getMaskedUpper() {
            return this.masker.getMaskedUpper(this.upperValue, this.maskValue);
        }

        public boolean isSequential() {
            return this.masker.isSequential();
        }
    }
}

