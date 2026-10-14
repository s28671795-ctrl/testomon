/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format;

import inet.ipaddr.AddressSegment;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.format.AddressDivisionGroupingBase;
import inet.ipaddr.format.AddressGenericDivision;
import inet.ipaddr.format.AddressSegmentSpliterator;
import inet.ipaddr.format.large.IPAddressLargeDivision;
import inet.ipaddr.format.standard.AddressDivisionGrouping;
import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.format.util.AddressSegmentParams;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.function.Supplier;

public abstract class AddressDivisionBase
implements AddressGenericDivision {
    private static final long serialVersionUID = 4L;
    private static final IPAddressSection.IPStringOptions OCTAL_PARAMS;
    private static final IPAddressSection.IPStringOptions HEX_PARAMS;
    private static final IPAddressSection.IPStringOptions DECIMAL_PARAMS;
    private static final String[] zeros;
    protected static final char[] DIGITS;
    public static final char[] EXTENDED_DIGITS;
    protected static final int MIN_RADIX = 2;
    protected static final int MAX_RADIX = 85;
    protected static final BigInteger BIG_MIN_RADIX;
    protected static final BigInteger BIG_MAX_RADIX;
    protected static final char[] UPPERCASE_DIGITS;
    protected static final char[] DOUBLE_DIGITS_DEC;
    private static TreeMap<Long, Integer> maxDigitMap;
    private static TreeMap<Long, BigInteger> radixPowerMap;
    protected transient String cachedWildcardString;
    private transient byte[] lowerBytes;
    private transient byte[] upperBytes;
    protected transient int hashCode;

    protected AddressDivisionBase() {
    }

    protected boolean isSameValues(AddressDivisionBase other) {
        return this.getValue().equals(other.getValue()) && this.getUpperValue().equals(other.getUpperValue());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AddressDivisionBase) {
            AddressDivisionBase other = (AddressDivisionBase)o;
            return this.getBitCount() == other.getBitCount() && other.isSameValues(this);
        }
        return false;
    }

    protected static int createHashCode(long value, long upperValue) {
        return AddressDivisionBase.adjustHashCode(1, value, upperValue);
    }

    static int adjustHashCode(int currentHash, long value, long upperValue) {
        long shifted = value >>> 32;
        int adjusted = (int)(shifted == 0L ? value : value ^ shifted);
        currentHash = 31 * currentHash + adjusted;
        if (upperValue != value) {
            shifted = upperValue >>> 32;
            adjusted = (int)(shifted == 0L ? upperValue : upperValue ^ shifted);
            currentHash = 31 * currentHash + adjusted;
        }
        return currentHash;
    }

    public int hashCode() {
        int res = this.hashCode;
        if (res == 0) {
            res = 1;
            BigInteger lower = this.getValue();
            BigInteger upper = this.getUpperValue();
            int longBits = 64;
            do {
                long low = lower.longValue();
                long up = upper.longValue();
                lower = lower.shiftRight(longBits);
                upper = upper.shiftRight(longBits);
                res = AddressDivisionBase.adjustHashCode(res, low, up);
            } while (!upper.equals(BigInteger.ZERO));
            this.hashCode = res;
        }
        return res;
    }

    @Override
    public byte[] getBytes() {
        byte[] cached = this.lowerBytes;
        if (cached == null) {
            this.lowerBytes = cached = this.getBytesImpl(true);
        }
        return (byte[])cached.clone();
    }

    @Override
    public byte[] getBytes(byte[] bytes, int index) {
        byte[] cached = this.lowerBytes;
        if (cached == null) {
            this.lowerBytes = cached = this.getBytesImpl(true);
        }
        return this.getBytes(bytes, index, cached);
    }

    @Override
    public byte[] getBytes(byte[] bytes) {
        return this.getBytes(bytes, 0);
    }

    private byte[] getBytes(byte[] provided, int startIndex, byte[] cached) {
        int byteCount = this.getBitCount() + 7 >> 3;
        if (provided == null || provided.length < byteCount + startIndex) {
            if (startIndex > 0) {
                byte[] bytes2 = new byte[byteCount + startIndex];
                if (provided != null) {
                    System.arraycopy(provided, 0, bytes2, 0, Math.min(startIndex, provided.length));
                }
                System.arraycopy(cached, 0, bytes2, startIndex, cached.length);
                return bytes2;
            }
            return (byte[])cached.clone();
        }
        System.arraycopy(cached, 0, provided, startIndex, byteCount);
        return provided;
    }

    @Override
    public byte[] getUpperBytes() {
        if (!this.isMultiple()) {
            return this.getBytes();
        }
        byte[] cached = this.upperBytes;
        if (cached == null) {
            this.upperBytes = cached = this.getBytesImpl(false);
        }
        return (byte[])cached.clone();
    }

    @Override
    public byte[] getUpperBytes(byte[] bytes, int index) {
        if (!this.isMultiple()) {
            return this.getBytes(bytes, index);
        }
        byte[] cached = this.upperBytes;
        if (cached == null) {
            this.upperBytes = cached = this.getBytesImpl(false);
        }
        return this.getBytes(bytes, index, cached);
    }

    @Override
    public byte[] getUpperBytes(byte[] bytes) {
        return this.getUpperBytes(bytes, 0);
    }

    protected abstract byte[] getBytesImpl(boolean var1);

    protected abstract int getDefaultTextualRadix();

    protected abstract int getMaxDigitCount();

    protected static int getMaxDigitCount(int radix, int bitCount, BigInteger maxValue) {
        long key = (long)radix << 32 | (long)bitCount;
        Integer digs = maxDigitMap.get(key);
        if (digs == null) {
            if (maxValue == null) {
                maxValue = AddressDivisionBase.getMaxValue(bitCount);
            }
            digs = AddressDivisionBase.getDigitCount(maxValue, BigInteger.valueOf(radix));
            TreeMap newMaxDigitMap = (TreeMap)maxDigitMap.clone();
            newMaxDigitMap.put(key, digs);
            maxDigitMap = newMaxDigitMap;
        }
        return digs;
    }

    protected static BigInteger getMaxValue(int bitCount) {
        if (bitCount < 0) {
            throw new IllegalArgumentException();
        }
        int maxBytes = bitCount + 7 >>> 3;
        byte[] max = new byte[maxBytes];
        if (maxBytes > 0) {
            int topBits = bitCount % 8;
            if (topBits == 0) {
                topBits = 8;
            }
            max[0] = (byte)(~(-1 << topBits));
            for (int i = 1; i < max.length; ++i) {
                max[i] = -1;
            }
        }
        return new BigInteger(1, max);
    }

    public static int getDigitCount(BigInteger val, BigInteger radix) {
        if (radix.compareTo(BIG_MIN_RADIX) < 0 || radix.compareTo(BIG_MAX_RADIX) > 0) {
            throw new IllegalArgumentException();
        }
        if (val.equals(BigInteger.ZERO) || val.equals(BigInteger.ONE)) {
            return 1;
        }
        int result = 1;
        while (!(val = val.divide(radix)).equals(BigInteger.ZERO)) {
            ++result;
        }
        return result;
    }

    protected static int getMaxDigitCount(int radix, int bitCount, long maxValue) {
        long key = (long)radix << 32 | (long)bitCount;
        Integer digs = maxDigitMap.get(key);
        if (digs == null) {
            int digitCount = AddressDivisionBase.getDigitCount(bitCount, radix);
            if (digitCount < 0) {
                digitCount = AddressDivisionBase.getDigitCount(maxValue, radix);
            }
            digs = AddressDivisionGroupingBase.cacheBits(digitCount);
            TreeMap newMaxDigitMap = (TreeMap)maxDigitMap.clone();
            newMaxDigitMap.put(key, digs);
            maxDigitMap = newMaxDigitMap;
        }
        return digs;
    }

    private static int getDigitCount(int bitCount, int radix) {
        if (bitCount <= 0) {
            if (bitCount == 0 && radix >= 2 && radix <= 85) {
                return 1;
            }
            throw new IllegalArgumentException();
        }
        switch (radix) {
            case 16: {
                return bitCount + 3 >> 2;
            }
            case 8: {
                return (bitCount + 2) / 3;
            }
            case 4: {
                return bitCount + 1 >> 1;
            }
            case 2: {
                return bitCount;
            }
            case 10: {
                break;
            }
            default: {
                if (radix >= 2 && radix <= 85) break;
                throw new IllegalArgumentException();
            }
        }
        return -1;
    }

    public static int getDigitCount(long value, int radix) {
        int result = 1;
        if (radix == 16 && value >= 0L) {
            while (true) {
                if ((value >>>= 4) == 0L) {
                    return result;
                }
                ++result;
            }
        }
        if (radix == 10 && value > -10L) {
            if (value < 10L) {
                return 1;
            }
            if (value < 100L) {
                return 2;
            }
            if (value < 1000L) {
                return 3;
            }
            value /= 1000L;
            result = 3;
        } else {
            if (radix == 8 && value >= 0L) {
                while ((value >>>= 3) != 0L) {
                    ++result;
                }
                return result;
            }
            if (radix == 2 && value > 0L) {
                return 64 - Long.numberOfLeadingZeros(value);
            }
            if (radix < 2 || radix > 85) {
                throw new IllegalArgumentException();
            }
        }
        while ((value /= (long)radix) != 0L) {
            ++result;
        }
        return result;
    }

    protected static BigInteger getRadixPower(BigInteger radix, int power) {
        long key = (long)radix.intValue() << 32 | (long)power;
        BigInteger result = radixPowerMap.get(key);
        if (result == null) {
            BigInteger halfPower;
            if (radix.compareTo(BIG_MIN_RADIX) < 0 || radix.compareTo(BIG_MAX_RADIX) > 0) {
                throw new IllegalArgumentException();
            }
            if (power == 1) {
                result = radix;
            } else if ((power & 1) == 0) {
                halfPower = AddressDivisionBase.getRadixPower(radix, power >> 1);
                result = halfPower.multiply(halfPower);
            } else {
                halfPower = AddressDivisionBase.getRadixPower(radix, power - 1 >> 1);
                result = halfPower.multiply(halfPower).multiply(radix);
            }
            TreeMap newRadixPowerMap = (TreeMap)radixPowerMap.clone();
            newRadixPowerMap.put(key, result);
            radixPowerMap = newRadixPowerMap;
        }
        return result;
    }

    protected abstract int adjustLowerLeadingZeroCount(int var1, int var2);

    protected abstract int adjustUpperLeadingZeroCount(int var1, int var2);

    private static void getSplitChar(int count, char splitDigitSeparator, String characters, String stringPrefix, StringBuilder builder) {
        while (count-- > 0) {
            if (stringPrefix.length() > 0) {
                builder.append(stringPrefix);
            }
            builder.append(characters);
            builder.append(splitDigitSeparator);
        }
        builder.setLength(builder.length() - 1);
    }

    private static void getSplitChar(int count, char splitDigitSeparator, char character, String stringPrefix, StringBuilder builder) {
        int prefLen = stringPrefix.length();
        while (count-- > 0) {
            if (prefLen > 0) {
                builder.append(stringPrefix);
            }
            builder.append(character);
            builder.append(splitDigitSeparator);
        }
        builder.setLength(builder.length() - 1);
    }

    private static void getSplitLeadingZeros(int leadingZeroCount, char splitDigitSeparator, String stringPrefix, StringBuilder builder) {
        AddressDivisionBase.getSplitChar(leadingZeroCount, splitDigitSeparator, '0', stringPrefix, builder);
    }

    protected static void getLeadingZeros(int leadingZeroCount, StringBuilder builder) {
        if (leadingZeroCount > 0) {
            String[] stringArray = zeros;
            if (leadingZeroCount >= stringArray.length) {
                int increment = stringArray.length - 1;
                String incrementStr = stringArray[increment];
                while (leadingZeroCount >= increment) {
                    builder.append(incrementStr);
                    leadingZeroCount -= increment;
                }
                builder.append(stringArray[leadingZeroCount]);
                return;
            }
            builder.append(stringArray[leadingZeroCount]);
        }
    }

    public String toString() {
        IPAddressSection.IPStringOptions opts;
        int radix = this.getDefaultTextualRadix();
        switch (radix) {
            case 8: {
                opts = OCTAL_PARAMS;
                break;
            }
            case 16: {
                opts = HEX_PARAMS;
                break;
            }
            case 10: {
                opts = DECIMAL_PARAMS;
                break;
            }
            default: {
                opts = new IPAddressSection.IPStringOptions.Builder(radix).setWildcards(new AddressDivisionGrouping.StringOptions.Wildcards(IPAddress.RANGE_SEPARATOR_STR)).toOptions();
            }
        }
        StringBuilder builder = new StringBuilder(34);
        AddressDivisionBase.toParams(opts).appendSingleDivision(this, builder);
        return builder.toString();
    }

    protected static AddressDivisionGroupingBase.AddressStringParams<IPAddressStringDivisionSeries> toParams(IPAddressSection.IPStringOptions opts) {
        AddressDivisionGroupingBase.IPAddressStringParams result = (AddressDivisionGroupingBase.IPAddressStringParams)AddressDivisionGroupingBase.getCachedParams(opts);
        if (result == null) {
            result = new AddressDivisionGroupingBase.IPAddressStringParams(opts.base, opts.separator, opts.uppercase);
            result.expandSegments(opts.expandSegments);
            result.setWildcards(opts.wildcards);
            result.setSegmentStrPrefix(opts.segmentStrPrefix);
            result.setAddressLabel(opts.addrLabel);
            result.setReverse(opts.reverse);
            result.setSplitDigits(opts.splitDigits);
            result.setRadix(opts.base);
            result.setUppercase(opts.uppercase);
            result.setSeparator(opts.separator);
            result.setZoneSeparator(opts.zoneSeparator);
            AddressDivisionGroupingBase.setCachedParams(opts, result);
        }
        return result;
    }

    protected abstract String getDefaultLowerString();

    protected abstract String getDefaultRangeString();

    protected String getDefaultSegmentWildcardString() {
        return null;
    }

    protected abstract String getDefaultRangeSeparatorString();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected String getString() {
        String result = this.cachedWildcardString;
        if (result == null) {
            AddressDivisionBase addressDivisionBase = this;
            synchronized (addressDivisionBase) {
                result = this.cachedWildcardString;
                if (result == null) {
                    if (!this.isMultiple()) {
                        result = this.getDefaultLowerString();
                    } else if (!this.isFullRange() || (result = this.getDefaultSegmentWildcardString()) == null) {
                        result = this.getDefaultRangeString();
                    }
                    this.cachedWildcardString = result;
                }
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected String getCachedDefaultLowerString() {
        String result = this.cachedWildcardString;
        if (result == null) {
            AddressDivisionBase addressDivisionBase = this;
            synchronized (addressDivisionBase) {
                result = this.cachedWildcardString;
                if (result == null) {
                    this.cachedWildcardString = result = this.getDefaultLowerString();
                }
            }
        }
        return result;
    }

    protected String getWildcardString() {
        return this.getString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void setDefaultAsFullRangeWildcardString() {
        String result;
        if (this.cachedWildcardString == null && (result = this.getDefaultSegmentWildcardString()) != null) {
            AddressDivisionBase addressDivisionBase = this;
            synchronized (addressDivisionBase) {
                this.cachedWildcardString = result;
            }
        }
    }

    protected abstract int getLowerStringLength(int var1);

    protected abstract int getUpperStringLength(int var1);

    protected abstract void getLowerString(int var1, boolean var2, StringBuilder var3);

    protected abstract void getLowerString(int var1, int var2, boolean var3, StringBuilder var4);

    protected abstract void getUpperString(int var1, boolean var2, StringBuilder var3);

    protected abstract void getUpperStringMasked(int var1, boolean var2, StringBuilder var3);

    protected abstract void getSplitLowerString(int var1, int var2, boolean var3, char var4, boolean var5, String var6, StringBuilder var7);

    protected abstract void getSplitRangeString(String var1, String var2, int var3, boolean var4, char var5, boolean var6, String var7, StringBuilder var8);

    protected abstract int getSplitRangeStringLength(String var1, String var2, int var3, int var4, boolean var5, char var6, boolean var7, String var8);

    protected abstract int getRangeDigitCount(int var1);

    protected static int toUnsignedStringLength(long value, int radix) {
        int result;
        if (radix < 2 || radix > 85) {
            throw new IllegalArgumentException();
        }
        if (value > 65535L || (result = AddressDivisionBase.toUnsignedStringLengthFast((int)value, radix)) < 0) {
            result = AddressDivisionBase.toUnsignedStringLengthSlow(value, radix);
        }
        return result;
    }

    private static int toUnsignedStringLengthSlow(long value, int radix) {
        int intValue;
        int count = 1;
        boolean useInts = value <= Integer.MAX_VALUE;
        int n = intValue = useInts ? (int)value : radix;
        while (intValue >= radix) {
            if (useInts) {
                intValue /= radix;
            } else if ((value /= (long)radix) <= Integer.MAX_VALUE) {
                useInts = true;
                intValue = (int)value;
            }
            ++count;
        }
        return count;
    }

    private static int toUnsignedStringLengthFast(int value, int radix) {
        if (value <= 1) {
            return 1;
        }
        if (radix == 10) {
            if (value < 10) {
                return 1;
            }
            if (value < 100) {
                return 2;
            }
            if (value < 1000) {
                return 3;
            }
            if (value < 10000) {
                return 4;
            }
            return 5;
        }
        if (radix == 16) {
            if (value < 16) {
                return 1;
            }
            if (value < 256) {
                return 2;
            }
            if (value < 4096) {
                return 3;
            }
            return 4;
        }
        if (radix == 8) {
            if (value < 8) {
                return 1;
            }
            if (value < 64) {
                return 2;
            }
            if (value < 512) {
                return 3;
            }
            if (value < 4096) {
                return 4;
            }
            if (value < 32768) {
                return 5;
            }
            return 6;
        }
        if (radix == 2) {
            int digitCount = 15;
            int val = value;
            if (val >>> 8 == 0) {
                digitCount -= 8;
            } else {
                val >>>= 8;
            }
            if (val >>> 4 == 0) {
                digitCount -= 4;
            } else {
                val >>>= 4;
            }
            if (val >>> 2 == 0) {
                digitCount -= 2;
            } else {
                val >>>= 2;
            }
            if ((val & 2) != 0) {
                ++digitCount;
            }
            return digitCount;
        }
        return -1;
    }

    protected static StringBuilder toUnsignedStringCased(long value, int radix, int choppedDigits, boolean uppercase, StringBuilder appendable) {
        if (radix < 2 || radix > 85) {
            throw new IllegalArgumentException();
        }
        if (value > 65535L || !AddressDivisionBase.toUnsignedStringFastChopped((int)value, radix, choppedDigits, uppercase, appendable)) {
            AddressDivisionBase.toUnsignedStringSlow(value, radix, choppedDigits, uppercase, appendable);
        }
        return appendable;
    }

    private static boolean toUnsignedStringFastChopped(int value, int radix, int choppedDigits, boolean uppercase, StringBuilder appendable) {
        if (AddressDivisionBase.toUnsignedStringFast(value, radix, uppercase, appendable)) {
            if (choppedDigits > 0) {
                appendable.setLength(appendable.length() - choppedDigits);
            }
            return true;
        }
        return false;
    }

    private static char[] getDigits(boolean uppercase, int radix) {
        if (uppercase || radix > 36) {
            return UPPERCASE_DIGITS;
        }
        return DIGITS;
    }

    private static boolean toUnsignedStringFast(int value, int radix, boolean uppercase, StringBuilder appendable) {
        if (value <= 1) {
            appendable.append(value == 0 ? (char)'0' : '1');
            return true;
        }
        if (radix == 10) {
            int quotient;
            if (value < 10) {
                char[] dig = AddressDivisionBase.getDigits(uppercase, radix);
                appendable.append(dig[value]);
                return true;
            }
            if (value < 100) {
                char[] dig = DOUBLE_DIGITS_DEC;
                int digIndex = value << 1;
                appendable.append(dig[digIndex]);
                appendable.append(dig[digIndex + 1]);
                return true;
            }
            if (value < 200) {
                int index = appendable.length();
                appendable.append("127");
                if (value != 127) {
                    char[] dig = DOUBLE_DIGITS_DEC;
                    int digIndex = value - 100 << 1;
                    appendable.setCharAt(++index, dig[digIndex]);
                    appendable.setCharAt(index + 1, dig[digIndex + 1]);
                }
                return true;
            }
            if (value < 300) {
                int index = appendable.length();
                appendable.append("255");
                if (value != 255) {
                    char[] dig = DOUBLE_DIGITS_DEC;
                    int digIndex = value - 200 << 1;
                    appendable.setCharAt(++index, dig[digIndex]);
                    appendable.setCharAt(index + 1, dig[digIndex + 1]);
                }
                return true;
            }
            if (value < 1000) {
                appendable.append("   ");
            } else if (value < 10000) {
                appendable.append("    ");
            } else {
                appendable.append("     ");
            }
            char[] dig = DIGITS;
            int index = appendable.length();
            do {
                quotient = value * 52429 >>> 19;
                int remainder = value - ((quotient << 3) + (quotient << 1));
                appendable.setCharAt(--index, dig[remainder]);
            } while ((value = quotient) != 0);
            return true;
        }
        if (radix == 16) {
            if (value < 16) {
                char[] dig = AddressDivisionBase.getDigits(uppercase, radix);
                appendable.append(dig[value]);
                return true;
            }
            if (value < 256) {
                appendable.append("  ");
            } else if (value < 4096) {
                appendable.append("   ");
            } else {
                if (value == 65535) {
                    appendable.append(uppercase ? "FFFF" : "ffff");
                    return true;
                }
                appendable.append("    ");
            }
            char[] dig = AddressDivisionBase.getDigits(uppercase, radix);
            int index = appendable.length();
            do {
                int remainder = value & 0xF;
                appendable.setCharAt(--index, dig[remainder]);
            } while ((value >>>= 4) != 0);
            return true;
        }
        if (radix == 8) {
            char[] dig = DIGITS;
            if (value < 8) {
                appendable.append(dig[value]);
                return true;
            }
            if (value < 64) {
                appendable.append("  ");
            } else if (value < 512) {
                appendable.append("   ");
            } else if (value < 4096) {
                appendable.append("    ");
            } else if (value < 32768) {
                appendable.append("     ");
            } else {
                appendable.append("      ");
            }
            int index = appendable.length();
            do {
                int remainder = value & 7;
                appendable.setCharAt(--index, dig[remainder]);
            } while ((value >>>= 3) != 0);
            return true;
        }
        if (radix == 2) {
            int digitIndex;
            if (value >>> 8 == 0) {
                if (value == 255) {
                    appendable.append("11111111");
                    return true;
                }
                digitIndex = value >>> 4 == 0 ? 4 : 8;
            } else {
                if (value == 65535) {
                    appendable.append("1111111111111111");
                    return true;
                }
                digitIndex = value >>> 4 == 0 ? 12 : 16;
            }
            while (--digitIndex > 0) {
                int digit = value >>> digitIndex & 1;
                if (digit != 1) continue;
                appendable.append('1');
                while (--digitIndex > 0) {
                    digit = value >>> digitIndex & 1;
                    appendable.append(digit == 0 ? (char)'0' : '1');
                }
                break block3;
            }
            appendable.append((value & 1) == 0 ? (char)'0' : '1');
            return true;
        }
        return false;
    }

    private static void toUnsignedStringSlow(long value, int radix, int choppedDigits, boolean uppercase, StringBuilder appendable) {
        int front = appendable.length();
        AddressDivisionBase.appendDigits(value, radix, choppedDigits, uppercase, appendable);
        int back = appendable.length() - 1;
        while (front < back) {
            char frontChar = appendable.charAt(front);
            appendable.setCharAt(front++, appendable.charAt(back));
            appendable.setCharAt(back--, frontChar);
        }
    }

    private static void appendDigits(long value, int radix, int choppedDigits, boolean uppercase, StringBuilder appendable) {
        boolean useInts = value <= Integer.MAX_VALUE;
        int value2 = useInts ? (int)value : radix;
        char[] dig = AddressDivisionBase.getDigits(uppercase, radix);
        while (value2 >= radix) {
            int index;
            if (useInts) {
                int val2 = value2;
                value2 /= radix;
                if (choppedDigits > 0) {
                    --choppedDigits;
                    continue;
                }
                index = val2 % radix;
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
            appendable.append(dig[index]);
        }
        if (choppedDigits == 0) {
            appendable.append(dig[value2]);
        }
    }

    protected void appendUppercase(CharSequence str, int radix, StringBuilder appendable) {
        if (radix > 10) {
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

    private static int getFullRangeString(String wildcard, StringBuilder appendable) {
        if (appendable == null) {
            return wildcard.length();
        }
        appendable.append(wildcard);
        return 0;
    }

    protected int getPrefixAdjustedRangeString(int segmentIndex, AddressSegmentParams params, StringBuilder appendable) {
        int rangeDigitCount;
        int leadingZeroCount = params.getLeadingZeros(segmentIndex);
        int radix = params.getRadix();
        int lowerLeadingZeroCount = this.adjustLowerLeadingZeroCount(leadingZeroCount, radix);
        int upperLeadingZeroCount = this.adjustUpperLeadingZeroCount(leadingZeroCount, radix);
        AddressDivisionGrouping.StringOptions.Wildcards wildcards = params.getWildcards();
        String rangeSeparator = wildcards.rangeSeparator;
        int n = rangeDigitCount = wildcards.singleWildcard == null ? 0 : this.getRangeDigitCount(radix);
        if (rangeDigitCount == 0 && radix == this.getDefaultTextualRadix() && !this.isFullRange()) {
            String str = this.getString();
            String rangeSep = this.getDefaultRangeSeparatorString();
            String stringPrefix = params.getSegmentStrPrefix();
            int prefLen = stringPrefix.length();
            if (lowerLeadingZeroCount == 0 && upperLeadingZeroCount == 0 && rangeSep.equals(rangeSeparator) && prefLen == 0) {
                if (appendable == null) {
                    return str.length();
                }
                if (params.isUppercase()) {
                    this.appendUppercase(str, radix, appendable);
                } else {
                    appendable.append(str);
                }
                return 0;
            }
            if (appendable == null) {
                int count = str.length() + (rangeSeparator.length() - rangeSep.length()) + lowerLeadingZeroCount + upperLeadingZeroCount;
                if (prefLen > 0) {
                    count += prefLen << 1;
                }
                return count;
            }
            int firstEnd = str.indexOf(rangeSep);
            if (prefLen > 0) {
                appendable.append(stringPrefix);
            }
            if (lowerLeadingZeroCount > 0) {
                AddressDivisionBase.getLeadingZeros(lowerLeadingZeroCount, appendable);
            }
            appendable.append(str.substring(0, firstEnd));
            appendable.append(rangeSeparator);
            if (prefLen > 0) {
                appendable.append(stringPrefix);
            }
            if (upperLeadingZeroCount > 0) {
                AddressDivisionBase.getLeadingZeros(upperLeadingZeroCount, appendable);
            }
            appendable.append(str.substring(firstEnd + rangeSep.length()));
            return 0;
        }
        rangeDigitCount = this.adjustRangeDigits(rangeDigitCount);
        if (leadingZeroCount < 0 && appendable == null) {
            int charLength = this.getMaxDigitCount(radix);
            String stringPrefix = params.getSegmentStrPrefix();
            int prefLen = stringPrefix.length();
            if (rangeDigitCount != 0) {
                int count = charLength;
                if (prefLen > 0) {
                    count += prefLen;
                }
                return count;
            }
            int count = charLength << 1;
            if (prefLen > 0) {
                count += prefLen << 1;
            }
            return count += rangeSeparator.length();
        }
        if (rangeDigitCount != 0) {
            return this.getRangeDigitString(segmentIndex, params, appendable);
        }
        return this.getRangeString(segmentIndex, params, lowerLeadingZeroCount, upperLeadingZeroCount, true, appendable);
    }

    @Override
    public int getLowerStandardString(int segmentIndex, AddressSegmentParams params, StringBuilder appendable) {
        int count = 0;
        String stringPrefix = params.getSegmentStrPrefix();
        int prefLen = stringPrefix.length();
        if (prefLen > 0) {
            if (appendable == null) {
                count += prefLen;
            } else {
                appendable.append(stringPrefix);
            }
        }
        int radix = params.getRadix();
        int leadingZeroCount = params.getLeadingZeros(segmentIndex);
        if (leadingZeroCount != 0) {
            if (appendable == null) {
                if (leadingZeroCount < 0) {
                    return count + this.getMaxDigitCount(radix);
                }
                count += leadingZeroCount;
            } else {
                leadingZeroCount = this.adjustLowerLeadingZeroCount(leadingZeroCount, radix);
                AddressDivisionBase.getLeadingZeros(leadingZeroCount, appendable);
            }
        }
        boolean uppercase = params.isUppercase();
        if (radix == this.getDefaultTextualRadix()) {
            String str = this.getCachedDefaultLowerString();
            if (appendable == null) {
                return count + str.length();
            }
            if (uppercase) {
                this.appendUppercase(str, radix, appendable);
            } else {
                appendable.append(str);
            }
        } else {
            if (appendable == null) {
                return count + this.getLowerStringLength(radix);
            }
            this.getLowerString(radix, uppercase, appendable);
        }
        return 0;
    }

    @Override
    public int getStandardString(int segmentIndex, AddressSegmentParams params, StringBuilder appendable) {
        String wildcard;
        if (!this.isMultiple()) {
            boolean splitDigits = params.isSplitDigits();
            if (splitDigits) {
                int radix = params.getRadix();
                int leadingZeroCount = params.getLeadingZeros(segmentIndex);
                leadingZeroCount = this.adjustLowerLeadingZeroCount(leadingZeroCount, radix);
                String stringPrefix = params.getSegmentStrPrefix();
                int prefLen = stringPrefix.length();
                if (appendable == null) {
                    int len = leadingZeroCount != 0 ? (leadingZeroCount < 0 ? this.getMaxDigitCount(radix) : this.getLowerStringLength(radix) + leadingZeroCount) : this.getLowerStringLength(radix);
                    int count = (len << 1) - 1;
                    if (prefLen > 0) {
                        count += len * prefLen;
                    }
                    return count;
                }
                char splitDigitSeparator = params.getSplitDigitSeparator() == null ? (char)'\u0000' : params.getSplitDigitSeparator().charValue();
                boolean reverseSplitDigits = params.isReverseSplitDigits();
                boolean uppercase = params.isUppercase();
                if (reverseSplitDigits) {
                    this.getSplitLowerString(radix, 0, uppercase, splitDigitSeparator, reverseSplitDigits, stringPrefix, appendable);
                    if (leadingZeroCount != 0) {
                        appendable.append(splitDigitSeparator);
                        AddressDivisionBase.getSplitLeadingZeros(leadingZeroCount, splitDigitSeparator, stringPrefix, appendable);
                    }
                } else {
                    if (leadingZeroCount != 0) {
                        AddressDivisionBase.getSplitLeadingZeros(leadingZeroCount, splitDigitSeparator, stringPrefix, appendable);
                        appendable.append(splitDigitSeparator);
                    }
                    this.getSplitLowerString(radix, 0, uppercase, splitDigitSeparator, reverseSplitDigits, stringPrefix, appendable);
                }
                return 0;
            }
            return this.getLowerStandardString(segmentIndex, params, appendable);
        }
        if (this.isFullRange() && (wildcard = params.getWildcards().wildcard) != null) {
            boolean splitDigits;
            if (wildcard.equals(this.getDefaultSegmentWildcardString())) {
                this.setDefaultAsFullRangeWildcardString();
            }
            if (splitDigits = params.isSplitDigits()) {
                int radix = params.getRadix();
                if (appendable == null) {
                    int len = this.getMaxDigitCount(radix);
                    int count = len * (wildcard.length() + 1) - 1;
                    return count;
                }
                char splitDigitSeparator = params.getSplitDigitSeparator() == null ? (char)'\u0000' : params.getSplitDigitSeparator().charValue();
                AddressDivisionBase.getSplitChar(this.getMaxDigitCount(radix), splitDigitSeparator, wildcard, "", appendable);
                return 0;
            }
            return AddressDivisionBase.getFullRangeString(wildcard, appendable);
        }
        return this.getRangeString(segmentIndex, params, appendable);
    }

    protected int getRangeString(int segmentIndex, AddressSegmentParams params, StringBuilder appendable) {
        boolean splitDigits = params.isSplitDigits();
        int radix = params.getRadix();
        int leadingZeroCount = params.getLeadingZeros(segmentIndex);
        AddressDivisionGrouping.StringOptions.Wildcards wildcards = params.getWildcards();
        String rangeSeparator = wildcards.rangeSeparator;
        int rangeDigitCount = wildcards.singleWildcard == null ? 0 : this.getRangeDigitCount(radix);
        int lowerLeadingZeroCount = this.adjustLowerLeadingZeroCount(leadingZeroCount, radix);
        int upperLeadingZeroCount = this.adjustUpperLeadingZeroCount(leadingZeroCount, radix);
        if (rangeDigitCount == 0 && radix == this.getDefaultTextualRadix() && !splitDigits && !this.isFullRange()) {
            String str = this.getWildcardString();
            String rangeSep = this.getDefaultRangeSeparatorString();
            String stringPrefix = params.getSegmentStrPrefix();
            int prefLen = stringPrefix.length();
            if (lowerLeadingZeroCount == 0 && upperLeadingZeroCount == 0 && prefLen == 0 && rangeSeparator.equals(rangeSep)) {
                if (appendable == null) {
                    return str.length();
                }
                appendable.append(str);
                return 0;
            }
            if (appendable == null) {
                int count = str.length() + (rangeSeparator.length() - rangeSep.length()) + lowerLeadingZeroCount + upperLeadingZeroCount;
                if (prefLen > 0) {
                    count += prefLen << 1;
                }
                return count;
            }
            int firstEnd = str.indexOf(rangeSep);
            if (prefLen > 0) {
                appendable.append(stringPrefix);
            }
            if (lowerLeadingZeroCount > 0) {
                AddressDivisionBase.getLeadingZeros(lowerLeadingZeroCount, appendable);
            }
            appendable.append(str.substring(0, firstEnd));
            appendable.append(rangeSeparator);
            if (prefLen > 0) {
                appendable.append(stringPrefix);
            }
            if (upperLeadingZeroCount > 0) {
                AddressDivisionBase.getLeadingZeros(upperLeadingZeroCount, appendable);
            }
            appendable.append(str.substring(firstEnd + rangeSep.length()));
            return 0;
        }
        if (!splitDigits && leadingZeroCount < 0 && appendable == null) {
            String stringPrefix = params.getSegmentStrPrefix();
            int prefLen = stringPrefix.length();
            int charLength = this.getMaxDigitCount(radix);
            if (rangeDigitCount != 0) {
                int count = charLength;
                if (prefLen > 0) {
                    count += prefLen;
                }
                return count;
            }
            int count = charLength << 1;
            if (prefLen > 0) {
                count += prefLen << 1;
            }
            return count += rangeSeparator.length();
        }
        if ((rangeDigitCount = this.adjustRangeDigits(rangeDigitCount)) != 0) {
            if (splitDigits) {
                return this.getSplitRangeDigitString(segmentIndex, params, appendable);
            }
            return this.getRangeDigitString(segmentIndex, params, appendable);
        }
        if (splitDigits) {
            return this.getSplitRangeString(segmentIndex, params, appendable);
        }
        return this.getRangeString(segmentIndex, params, lowerLeadingZeroCount, upperLeadingZeroCount, false, appendable);
    }

    protected int getSplitRangeDigitString(int segmentIndex, AddressSegmentParams params, StringBuilder appendable) {
        int radix = params.getRadix();
        int leadingZerosCount = params.getLeadingZeros(segmentIndex);
        leadingZerosCount = this.adjustLowerLeadingZeroCount(leadingZerosCount, radix);
        String stringPrefix = params.getSegmentStrPrefix();
        if (appendable == null) {
            int len = this.getLowerStringLength(radix) + leadingZerosCount;
            int count = (len << 1) - 1;
            int prefLen = stringPrefix.length();
            if (prefLen > 0) {
                count += len * prefLen;
            }
            return count;
        }
        AddressDivisionGrouping.StringOptions.Wildcards wildcards = params.getWildcards();
        int rangeDigits = this.adjustRangeDigits(this.getRangeDigitCount(radix));
        char splitDigitSeparator = params.getSplitDigitSeparator() == null ? (char)'\u0000' : params.getSplitDigitSeparator().charValue();
        boolean reverseSplitDigits = params.isReverseSplitDigits();
        boolean uppercase = params.isUppercase();
        if (reverseSplitDigits) {
            AddressDivisionBase.getSplitChar(rangeDigits, splitDigitSeparator, wildcards.singleWildcard, stringPrefix, appendable);
            appendable.append(splitDigitSeparator);
            this.getSplitLowerString(radix, rangeDigits, uppercase, splitDigitSeparator, reverseSplitDigits, stringPrefix, appendable);
            if (leadingZerosCount > 0) {
                appendable.append(splitDigitSeparator);
                AddressDivisionBase.getSplitLeadingZeros(leadingZerosCount, splitDigitSeparator, stringPrefix, appendable);
            }
        } else {
            if (leadingZerosCount != 0) {
                AddressDivisionBase.getSplitLeadingZeros(leadingZerosCount, splitDigitSeparator, stringPrefix, appendable);
                appendable.append(splitDigitSeparator);
            }
            this.getSplitLowerString(radix, rangeDigits, uppercase, splitDigitSeparator, reverseSplitDigits, stringPrefix, appendable);
            appendable.append(splitDigitSeparator);
            AddressDivisionBase.getSplitChar(rangeDigits, splitDigitSeparator, wildcards.singleWildcard, stringPrefix, appendable);
        }
        return 0;
    }

    protected int getRangeDigitString(int segmentIndex, AddressSegmentParams params, StringBuilder appendable) {
        int radix = params.getRadix();
        int leadingZerosCount = params.getLeadingZeros(segmentIndex);
        leadingZerosCount = this.adjustLowerLeadingZeroCount(leadingZerosCount, radix);
        String stringPrefix = params.getSegmentStrPrefix();
        int prefLen = stringPrefix.length();
        AddressDivisionGrouping.StringOptions.Wildcards wildcards = params.getWildcards();
        int rangeDigits = this.adjustRangeDigits(this.getRangeDigitCount(radix));
        if (appendable == null) {
            return this.getLowerStringLength(radix) + leadingZerosCount + prefLen;
        }
        if (prefLen > 0) {
            appendable.append(stringPrefix);
        }
        if (leadingZerosCount > 0) {
            AddressDivisionBase.getLeadingZeros(leadingZerosCount, appendable);
        }
        boolean uppercase = params.isUppercase();
        this.getLowerString(radix, rangeDigits, uppercase, appendable);
        for (int i = 0; i < rangeDigits; ++i) {
            appendable.append(wildcards.singleWildcard);
        }
        return 0;
    }

    int adjustRangeDigits(int rangeDigits) {
        if (!(rangeDigits == 0 || this.includesZero() && rangeDigits != 1)) {
            return rangeDigits;
        }
        return 0;
    }

    protected int getRangeString(int segmentIndex, AddressSegmentParams params, int lowerLeadingZerosCount, int upperLeadingZerosCount, boolean maskUpper, StringBuilder appendable) {
        String stringPrefix = params.getSegmentStrPrefix();
        int radix = params.getRadix();
        String rangeSeparator = params.getWildcards().rangeSeparator;
        boolean uppercase = params.isUppercase();
        return this.getRangeString(rangeSeparator, lowerLeadingZerosCount, upperLeadingZerosCount, stringPrefix, radix, uppercase, maskUpper, appendable);
    }

    protected int getRangeString(String rangeSeparator, int lowerLeadingZerosCount, int upperLeadingZerosCount, String stringPrefix, int radix, boolean uppercase, boolean maskUpper, StringBuilder appendable) {
        boolean hasStringPrefix;
        if (radix < 2 || radix > 85) {
            throw new IllegalArgumentException();
        }
        int prefLen = stringPrefix.length();
        boolean bl = hasStringPrefix = prefLen > 0;
        if (appendable == null) {
            int count = lowerLeadingZerosCount + upperLeadingZerosCount + this.getLowerStringLength(radix) + this.getUpperStringLength(radix) + rangeSeparator.length();
            if (hasStringPrefix) {
                count += prefLen << 1;
            }
            return count;
        }
        if (hasStringPrefix) {
            appendable.append(stringPrefix);
        }
        if (lowerLeadingZerosCount > 0) {
            AddressDivisionBase.getLeadingZeros(lowerLeadingZerosCount, appendable);
        }
        this.getLowerString(radix, uppercase, appendable);
        appendable.append(rangeSeparator);
        if (hasStringPrefix) {
            appendable.append(stringPrefix);
        }
        if (upperLeadingZerosCount > 0) {
            AddressDivisionBase.getLeadingZeros(upperLeadingZerosCount, appendable);
        }
        if (maskUpper) {
            this.getUpperStringMasked(radix, uppercase, appendable);
        } else {
            this.getUpperString(radix, uppercase, appendable);
        }
        return 0;
    }

    protected int getSplitRangeString(int segmentIndex, AddressSegmentParams params, StringBuilder appendable) {
        boolean hasLeadingZeros;
        String stringPrefix = params.getSegmentStrPrefix();
        int radix = params.getRadix();
        int leadingZeroCount = params.getLeadingZeros(segmentIndex);
        leadingZeroCount = this.adjustUpperLeadingZeroCount(leadingZeroCount, radix);
        AddressDivisionGrouping.StringOptions.Wildcards wildcards = params.getWildcards();
        boolean uppercase = params.isUppercase();
        char splitDigitSeparator = params.getSplitDigitSeparator() == null ? (char)'\u0000' : params.getSplitDigitSeparator().charValue();
        boolean reverseSplitDigits = params.isReverseSplitDigits();
        String rangeSeparator = wildcards.rangeSeparator;
        if (appendable == null) {
            return this.getSplitRangeStringLength(rangeSeparator, wildcards.wildcard, leadingZeroCount, radix, uppercase, splitDigitSeparator, reverseSplitDigits, stringPrefix);
        }
        boolean bl = hasLeadingZeros = leadingZeroCount != 0;
        if (hasLeadingZeros && !reverseSplitDigits) {
            AddressDivisionBase.getSplitLeadingZeros(leadingZeroCount, splitDigitSeparator, stringPrefix, appendable);
            appendable.append(splitDigitSeparator);
            hasLeadingZeros = false;
        }
        this.getSplitRangeString(rangeSeparator, wildcards.wildcard, radix, uppercase, splitDigitSeparator, reverseSplitDigits, stringPrefix, appendable);
        if (hasLeadingZeros) {
            appendable.append(splitDigitSeparator);
            AddressDivisionBase.getSplitLeadingZeros(leadingZeroCount, splitDigitSeparator, stringPrefix, appendable);
        }
        return 0;
    }

    protected static <T extends AddressSegment> AddressComponentSpliterator<T> createSegmentSpliterator(T splitForIteration, int value, int upperValue, Supplier<Iterator<T>> iteratorProvider, IntBinaryIteratorProvider<T> subIteratorProvider, SegmentCreator<T> itemProvider) {
        return new AddressSegmentSpliterator<T>(splitForIteration, value, upperValue, iteratorProvider, subIteratorProvider, itemProvider);
    }

    static boolean testRange(BigInteger lowerValue, BigInteger upperValue, BigInteger finalUpperValue, BigInteger networkMask, BigInteger hostMask) {
        return lowerValue.equals(lowerValue.and(networkMask)) && finalUpperValue.equals(upperValue.or(hostMask));
    }

    static boolean testRange(BigInteger lowerValue, BigInteger upperValue, BigInteger finalUpperValue, int bitCount, int divisionPrefixLen) {
        BigInteger networkMask = AddressDivisionGroupingBase.ALL_ONES.shiftLeft(bitCount - divisionPrefixLen);
        BigInteger hostMask = networkMask.not();
        return AddressDivisionBase.testRange(lowerValue, upperValue, finalUpperValue, networkMask, hostMask);
    }

    static {
        AddressDivisionGrouping.StringOptions.Wildcards rangeWildcard = new AddressDivisionGrouping.StringOptions.Wildcards(IPAddress.RANGE_SEPARATOR_STR);
        OCTAL_PARAMS = new IPAddressSection.IPStringOptions.Builder(8).setSegmentStrPrefix("0").setWildcards(rangeWildcard).toOptions();
        HEX_PARAMS = new IPAddressSection.IPStringOptions.Builder(16).setSegmentStrPrefix("0x").setWildcards(rangeWildcard).toOptions();
        DECIMAL_PARAMS = new IPAddressSection.IPStringOptions.Builder(10).setWildcards(rangeWildcard).toOptions();
        int zerosLength = 20;
        zeros = new String[zerosLength];
        AddressDivisionBase.zeros[0] = "";
        for (int i = 1; i < zerosLength; ++i) {
            AddressDivisionBase.zeros[i] = zeros[i - 1] + '0';
        }
        DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        EXTENDED_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '!', '#', '$', '%', '&', '(', ')', '*', '+', '-', ';', '<', '=', '>', '?', '@', '^', '_', '`', '{', '|', '}', '~'};
        BIG_MIN_RADIX = BigInteger.valueOf(2L);
        BIG_MAX_RADIX = BigInteger.valueOf(85L);
        UPPERCASE_DIGITS = IPAddressLargeDivision.EXTENDED_DIGITS;
        DOUBLE_DIGITS_DEC = new char[]{'0', '0', '0', '1', '0', '2', '0', '3', '0', '4', '0', '5', '0', '6', '0', '7', '0', '8', '0', '9', '1', '0', '1', '1', '1', '2', '1', '3', '1', '4', '1', '5', '1', '6', '1', '7', '1', '8', '1', '9', '2', '0', '2', '1', '2', '2', '2', '3', '2', '4', '2', '5', '2', '6', '2', '7', '2', '8', '2', '9', '3', '0', '3', '1', '3', '2', '3', '3', '3', '4', '3', '5', '3', '6', '3', '7', '3', '8', '3', '9', '4', '0', '4', '1', '4', '2', '4', '3', '4', '4', '4', '5', '4', '6', '4', '7', '4', '8', '4', '9', '5', '0', '5', '1', '5', '2', '5', '3', '5', '4', '5', '5', '5', '6', '5', '7', '5', '8', '5', '9', '6', '0', '6', '1', '6', '2', '6', '3', '6', '4', '6', '5', '6', '6', '6', '7', '6', '8', '6', '9', '7', '0', '7', '1', '7', '2', '7', '3', '7', '4', '7', '5', '7', '6', '7', '7', '7', '8', '7', '9', '8', '0', '8', '1', '8', '2', '8', '3', '8', '4', '8', '5', '8', '6', '8', '7', '8', '8', '8', '9', '9', '0', '9', '1', '9', '2', '9', '3', '9', '4', '9', '5', '9', '6', '9', '7', '9', '8', '9', '9'};
        maxDigitMap = new TreeMap();
        radixPowerMap = new TreeMap();
    }

    @FunctionalInterface
    protected static interface IntBinaryIteratorProvider<R> {
        public Iterator<R> applyAsInt(boolean var1, boolean var2, int var3, int var4);
    }

    @FunctionalInterface
    protected static interface SegmentCreator<R extends AddressSegment> {
        public R applyAsInt(int var1, int var2);
    }
}

