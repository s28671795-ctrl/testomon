/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.validate;

import java.io.Serializable;
import java.math.BigInteger;

class AddressParseData
implements Serializable {
    private static final long serialVersionUID = 4L;
    private static final int UPPER_ADJUSTMENT = 8;
    public static final int KEY_WILDCARD = 65536;
    public static final int KEY_SINGLE_WILDCARD = 131072;
    public static final int KEY_STANDARD_STR = 262144;
    public static final int KEY_STANDARD_RANGE_STR = 524288;
    public static final int KEY_RANGE_WILDCARD = 0x100000;
    public static final int KEY_INFERRED_LOWER_BOUNDARY = 0x200000;
    public static final int KEY_INFERRED_UPPER_BOUNDARY = 0x400000;
    public static final int KEY_MERGED_MIXED = 0x800000;
    private static final int KEY_RADIX = 255;
    private static final int KEY_BIT_SIZE = 65280;
    private static final int BIT_SIZE_SHIFT = 8;
    public static final int KEY_LOWER_RADIX_INDEX = 0;
    public static final int KEY_BIT_SIZE_INDEX = 0;
    public static final int FLAGS_INDEX = 0;
    public static final int KEY_UPPER_RADIX_INDEX = 8;
    public static final int KEY_LOWER = 2;
    public static final int KEY_EXTENDED_LOWER = 4;
    public static final int KEY_UPPER = 10;
    public static final int KEY_EXTENDED_UPPER = 12;
    public static final int KEY_LOWER_STR_DIGITS_INDEX = 1;
    public static final int KEY_LOWER_STR_START_INDEX = 6;
    public static final int KEY_LOWER_STR_END_INDEX = 7;
    public static final int KEY_UPPER_STR_DIGITS_INDEX = 9;
    public static final int KEY_UPPER_STR_START_INDEX = 14;
    public static final int KEY_UPPER_STR_END_INDEX = 15;
    private static final int SEGMENT_DATA_SIZE = 16;
    private static final int SEGMENT_INDEX_SHIFT = 4;
    private static final int IPV4_SEGMENT_DATA_SIZE = 64;
    private static final int IPV6_SEGMENT_DATA_SIZE = 128;
    private int[] segmentData;
    private int segmentCount;
    private boolean anyWildcard;
    private boolean isEmpty;
    private boolean isAll;
    private boolean isSingleSegment;
    private int consecutiveSepIndex = -1;
    private int consecutiveSepSegmentIndex = -1;
    private int addressEndIndex;
    protected final CharSequence str;

    AddressParseData(CharSequence str) {
        this.str = str;
    }

    CharSequence getString() {
        return this.str;
    }

    void initSegmentData(int segmentCapacity) {
        int dataSize = segmentCapacity == 4 ? 64 : (segmentCapacity == 8 ? 128 : (segmentCapacity == 1 ? 16 : segmentCapacity * 16));
        this.segmentData = new int[dataSize];
    }

    void releaseSegmentData() {
        this.segmentData = null;
    }

    int[] getSegmentData() {
        return this.segmentData;
    }

    void incrementSegmentCount() {
        ++this.segmentCount;
    }

    public int getSegmentCount() {
        return this.segmentCount;
    }

    int getConsecutiveSeparatorSegmentIndex() {
        return this.consecutiveSepSegmentIndex;
    }

    void setConsecutiveSeparatorSegmentIndex(int val) {
        this.consecutiveSepSegmentIndex = val;
    }

    int getConsecutiveSeparatorIndex() {
        return this.consecutiveSepIndex;
    }

    void setConsecutiveSeparatorIndex(int val) {
        this.consecutiveSepIndex = val;
    }

    public boolean isProvidingEmpty() {
        return this.isEmpty;
    }

    void setEmpty(boolean val) {
        this.isEmpty = val;
    }

    boolean isAll() {
        return this.isAll;
    }

    void setAll() {
        this.isAll = true;
    }

    int getAddressEndIndex() {
        return this.addressEndIndex;
    }

    void setAddressEndIndex(int val) {
        this.addressEndIndex = val;
    }

    void setSingleSegment() {
        this.isSingleSegment = true;
    }

    boolean isSingleSegment() {
        return this.isSingleSegment;
    }

    void setHasWildcard() {
        this.anyWildcard = true;
    }

    boolean hasWildcard() {
        return this.anyWildcard;
    }

    void unsetFlag(int segmentIndex, int flagIndicator) {
        int index = segmentIndex << 4 | 0;
        int[] segmentData = this.getSegmentData();
        int n = index;
        segmentData[n] = segmentData[n] & ~flagIndicator;
    }

    boolean getFlag(int segmentIndex, int flagIndicator) {
        int[] segmentData = this.getSegmentData();
        return (segmentData[segmentIndex << 4 | 0] & flagIndicator) != 0;
    }

    boolean hasEitherFlag(int segmentIndex, int flagIndicator1, int flagIndicator2) {
        return this.getFlag(segmentIndex, flagIndicator1 | flagIndicator2);
    }

    int getRadix(int segmentIndex, int indexIndicator) {
        int[] segmentData = this.getSegmentData();
        int radix = segmentData[segmentIndex << 4 | indexIndicator] & 0xFF;
        if (radix == 0) {
            return 16;
        }
        return radix;
    }

    int getBitLength(int segmentIndex) {
        int[] segmentData = this.getSegmentData();
        int bitLength = (segmentData[segmentIndex << 4 | 0] & 0xFF00) >>> 8;
        return bitLength;
    }

    void setBitLength(int segmentIndex, int length) {
        int[] segmentData = this.getSegmentData();
        int n = segmentIndex << 4 | 0;
        segmentData[n] = segmentData[n] | length << 8 & 0xFF00;
    }

    void setIndex(int segmentIndex, int indexIndicator0, int value0, int indexIndicator1, int value1, int indexIndicator2, int value2, int indexIndicator3, int value3, int indexIndicator4, int value4, int indexIndicator5, int value5) {
        int baseIndex = segmentIndex << 4;
        int[] segmentData = this.getSegmentData();
        segmentData[baseIndex | indexIndicator0] = value0;
        segmentData[baseIndex | indexIndicator1] = value1;
        segmentData[baseIndex | indexIndicator2] = value2;
        segmentData[baseIndex | indexIndicator3] = value3;
        segmentData[baseIndex | indexIndicator4] = value4;
        segmentData[baseIndex | indexIndicator5] = value5;
    }

    int getIndex(int segmentIndex, int indexIndicator) {
        return AddressParseData.getIndex(segmentIndex, indexIndicator, this.getSegmentData());
    }

    static int getIndex(int segmentIndex, int indexIndicator, int[] segmentData) {
        return segmentData[segmentIndex << 4 | indexIndicator];
    }

    void set7IndexFlags(int segmentIndex, int indexIndicator0, int value0, int indexIndicator1, int value1, int indexIndicator2, int value2, int indexIndicator3, int value3, int indexIndicator4, int value4, int indexIndicator5, int value5, int indexIndicator6, int value6) {
        int baseIndex = segmentIndex << 4;
        int[] segmentData = this.getSegmentData();
        segmentData[baseIndex | indexIndicator0] = value0;
        segmentData[baseIndex | indexIndicator1] = value1;
        segmentData[baseIndex | indexIndicator2] = value2;
        segmentData[baseIndex | indexIndicator3] = value3;
        segmentData[baseIndex | indexIndicator4] = value4;
        segmentData[baseIndex | indexIndicator5] = value5;
        segmentData[baseIndex | indexIndicator6] = value6;
    }

    void set8IndexFlags(int segmentIndex, int indexIndicator0, int value0, int indexIndicator1, int value1, int indexIndicator2, int value2, int indexIndicator3, int value3, int indexIndicator4, int value4, int indexIndicator5, int value5, int indexIndicator6, int value6, int indexIndicator7, int value7) {
        int baseIndex = segmentIndex << 4;
        int[] segmentData = this.getSegmentData();
        segmentData[baseIndex | indexIndicator0] = value0;
        segmentData[baseIndex | indexIndicator1] = value1;
        segmentData[baseIndex | indexIndicator2] = value2;
        segmentData[baseIndex | indexIndicator3] = value3;
        segmentData[baseIndex | indexIndicator4] = value4;
        segmentData[baseIndex | indexIndicator5] = value5;
        segmentData[baseIndex | indexIndicator6] = value6;
        segmentData[baseIndex | indexIndicator7] = value7;
    }

    void set8Index4ValuesFlags(int segmentIndex, int indexIndicator0, int value0, int indexIndicator1, int value1, int indexIndicator2, int value2, int indexIndicator3, int value3, int indexIndicator4, int value4, int indexIndicator5, int value5, int indexIndicator6, int value6, int indexIndicator7, int value7, int indexIndicator8, long value8, int indexIndicator9, long value9, int indexIndicator10, long value10, int indexIndicator11, long value11) {
        int baseIndex = segmentIndex << 4;
        int[] segmentData = this.getSegmentData();
        AddressParseData.setIndexValuesFlags(baseIndex, segmentData, indexIndicator0, value0, indexIndicator1, value1, indexIndicator2, value2, indexIndicator3, value3, indexIndicator4, value4, indexIndicator5, value5, indexIndicator6, value6, indexIndicator8, value8, indexIndicator9, value9);
        segmentData[baseIndex | indexIndicator7] = value7;
        int index = baseIndex | indexIndicator10;
        segmentData[index] = (int)(value10 >>> 32);
        segmentData[index | 1] = (int)(value10 & 0xFFFFFFFFFFFFFFFFL);
        index = baseIndex | indexIndicator11;
        segmentData[index] = (int)(value11 >>> 32);
        segmentData[index | 1] = (int)(value11 & 0xFFFFFFFFFFFFFFFFL);
    }

    void set7Index4ValuesFlags(int segmentIndex, int indexIndicator0, int value0, int indexIndicator1, int value1, int indexIndicator2, int value2, int indexIndicator3, int value3, int indexIndicator4, int value4, int indexIndicator5, int value5, int indexIndicator6, int value6, int indexIndicator7, long value7, int indexIndicator8, long value8, int indexIndicator9, long value9, int indexIndicator10, long value10) {
        int baseIndex = segmentIndex << 4;
        int[] segmentData = this.getSegmentData();
        AddressParseData.setIndexValuesFlags(baseIndex, segmentData, indexIndicator0, value0, indexIndicator1, value1, indexIndicator2, value2, indexIndicator3, value3, indexIndicator4, value4, indexIndicator5, value5, indexIndicator6, value6, indexIndicator7, value7, indexIndicator8, value8);
        int index = baseIndex | indexIndicator9;
        segmentData[index] = (int)(value9 >>> 32);
        segmentData[index | 1] = (int)(value9 & 0xFFFFFFFFFFFFFFFFL);
        index = baseIndex | indexIndicator10;
        segmentData[index] = (int)(value10 >>> 32);
        segmentData[index | 1] = (int)(value10 & 0xFFFFFFFFFFFFFFFFL);
    }

    void set8Index2ValuesFlags(int segmentIndex, int indexIndicator0, int value0, int indexIndicator1, int value1, int indexIndicator2, int value2, int indexIndicator3, int value3, int indexIndicator4, int value4, int indexIndicator5, int value5, int indexIndicator6, int value6, int indexIndicator7, int value7, int indexIndicator8, long value8, int indexIndicator9, long value9) {
        int baseIndex = segmentIndex << 4;
        int[] segmentData = this.getSegmentData();
        AddressParseData.setIndexValuesFlags(baseIndex, segmentData, indexIndicator0, value0, indexIndicator1, value1, indexIndicator2, value2, indexIndicator3, value3, indexIndicator4, value4, indexIndicator5, value5, indexIndicator6, value6, indexIndicator8, value8, indexIndicator9, value9);
        segmentData[baseIndex | indexIndicator7] = value7;
    }

    void set7Index2ValuesFlags(int segmentIndex, int indexIndicator0, int value0, int indexIndicator1, int value1, int indexIndicator2, int value2, int indexIndicator3, int value3, int indexIndicator4, int value4, int indexIndicator5, int value5, int indexIndicator6, int value6, int indexIndicator7, long value7, int indexIndicator8, long value8) {
        int baseIndex = segmentIndex << 4;
        int[] segmentData = this.getSegmentData();
        AddressParseData.setIndexValuesFlags(baseIndex, segmentData, indexIndicator0, value0, indexIndicator1, value1, indexIndicator2, value2, indexIndicator3, value3, indexIndicator4, value4, indexIndicator5, value5, indexIndicator6, value6, indexIndicator7, value7, indexIndicator8, value8);
    }

    private static void setIndexValuesFlags(int baseIndex, int[] segmentData, int indexIndicator0, int value0, int indexIndicator1, int value1, int indexIndicator2, int value2, int indexIndicator3, int value3, int indexIndicator4, int value4, int indexIndicator5, int value5, int indexIndicator6, int value6, int indexIndicator7, long value7, int indexIndicator8, long value8) {
        segmentData[baseIndex | indexIndicator0] = value0;
        segmentData[baseIndex | indexIndicator1] = value1;
        segmentData[baseIndex | indexIndicator2] = value2;
        segmentData[baseIndex | indexIndicator3] = value3;
        segmentData[baseIndex | indexIndicator4] = value4;
        segmentData[baseIndex | indexIndicator5] = value5;
        segmentData[baseIndex | indexIndicator6] = value6;
        int index = baseIndex | indexIndicator7;
        segmentData[index] = (int)(value7 >>> 32);
        segmentData[index | 1] = (int)(value7 & 0xFFFFFFFFFFFFFFFFL);
        index = baseIndex | indexIndicator8;
        segmentData[index] = (int)(value8 >>> 32);
        segmentData[index | 1] = (int)(value8 & 0xFFFFFFFFFFFFFFFFL);
    }

    void setValue(int segmentIndex, int indexIndicator, long value) {
        int index = segmentIndex << 4 | indexIndicator;
        int upperValue = (int)(value >>> 32);
        int lowerValue = (int)(value & 0xFFFFFFFFFFFFFFFFL);
        int[] segmentData = this.getSegmentData();
        segmentData[index] = upperValue;
        segmentData[index | 1] = lowerValue;
    }

    long getValue(int segmentIndex, int indexIndicator) {
        return AddressParseData.getValue(segmentIndex, indexIndicator, this.getSegmentData());
    }

    protected static long getValue(int segmentIndex, int indexIndicator, int[] segmentData) {
        int index = segmentIndex << 4 | indexIndicator;
        long upperValue = segmentData[index];
        long lowerValue = 0xFFFFFFFFL & (long)segmentData[index | 1];
        long value = upperValue << 32 | lowerValue;
        return value;
    }

    boolean isMergedMixed(int index) {
        return this.getFlag(index, 0x800000);
    }

    boolean isWildcard(int index) {
        return this.getFlag(index, 65536);
    }

    boolean hasRange(int index) {
        return this.hasEitherFlag(index, 131072, 0x100000);
    }

    boolean isInferredUpperBoundary(int index) {
        return this.getFlag(index, 0x400000);
    }

    void toString(StringBuilder builder) {
        CharSequence str = this.getString();
        builder.append("address string: ").append(str).append('\n');
        int addressEndIndex = this.getAddressEndIndex();
        if (addressEndIndex > 0 && addressEndIndex < str.length()) {
            builder.append("address end: ").append(str.subSequence(addressEndIndex, str.length())).append('\n');
        }
        int segmentCount = this.getSegmentCount();
        builder.append("segment count: ").append(segmentCount).append('\n');
        if (segmentCount > 0) {
            for (int i = 0; i < segmentCount; ++i) {
                BigInteger lowerResult;
                BigInteger notExtended;
                BigInteger shiftMore;
                BigInteger extended;
                builder.append("segment ").append(i).append(":\n");
                boolean isWildcard = this.isWildcard(i);
                if (isWildcard) {
                    builder.append("\tis wildcard").append('\n');
                    continue;
                }
                long lower = this.getValue(i, 2);
                long upper = this.getValue(i, 10);
                long extendedUpper = this.getValue(i, 12);
                long extendedLower = this.getValue(i, 4);
                if (extendedLower != 0L) {
                    extended = BigInteger.valueOf(extendedLower);
                    shiftMore = extended.shiftLeft(64);
                    notExtended = BigInteger.valueOf(lower);
                    lowerResult = shiftMore.or(notExtended);
                    builder.append("\tvalue: ").append(lowerResult).append('\n');
                    builder.append("\tvalue in hex: ").append(lowerResult.toString(16)).append('\n');
                } else {
                    builder.append("\tvalue: ").append(lower).append('\n');
                    builder.append("\tvalue in hex: ").append(Long.toHexString(lower)).append('\n');
                    lowerResult = null;
                }
                builder.append("\tstring: ").append(str.subSequence(this.getIndex(i, 6), this.getIndex(i, 7))).append('\n');
                builder.append("\tradix: ").append(this.getRadix(i, 0)).append('\n');
                builder.append("\tis standard: ").append(this.getFlag(i, 262144)).append('\n');
                if (extendedUpper != 0L) {
                    extended = BigInteger.valueOf(extendedUpper);
                    shiftMore = extended.shiftLeft(64);
                    BigInteger result = shiftMore.or(notExtended = BigInteger.valueOf(upper));
                    if (!result.equals(lowerResult)) {
                        builder.append("\tupper value: ").append(result).append('\n');
                        builder.append("\tupper value in hex: ").append(result.toString(16)).append('\n');
                        builder.append("\tupper string: ").append(str.subSequence(this.getIndex(i, 14), this.getIndex(i, 15))).append('\n');
                        builder.append("\tupper radix: ").append(this.getRadix(i, 8)).append('\n');
                        builder.append("\tis standard range: ").append(this.getFlag(i, 524288)).append('\n');
                    }
                } else if (upper != lower) {
                    builder.append("\tupper value: ").append(upper).append('\n');
                    builder.append("\tupper value in hex: ").append(Long.toHexString(upper)).append('\n');
                    builder.append("\tupper string: ").append(str.subSequence(this.getIndex(i, 14), this.getIndex(i, 15))).append('\n');
                    builder.append("\tupper radix: ").append(this.getRadix(i, 8)).append('\n');
                    builder.append("\tis standard range: ").append(this.getFlag(i, 524288)).append('\n');
                }
                if (!this.getFlag(i, 131072)) continue;
                builder.append("\thas single wildcard: ").append('\n');
            }
            builder.append("has a wildcard segment: ").append(this.hasWildcard()).append('\n');
            int consecutiveSepIndex = this.getConsecutiveSeparatorIndex();
            if (consecutiveSepIndex >= 0) {
                builder.append("has compressed segment(s) at character ").append(consecutiveSepIndex + 1).append('\n');
            }
            if (this.isSingleSegment()) {
                builder.append("is single segment").append('\n');
            }
        } else if (this.isProvidingEmpty()) {
            builder.append("is empty").append('\n');
        } else if (this.isAll()) {
            builder.append("is all addresses").append('\n');
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.toString(builder);
        return builder.toString();
    }
}

