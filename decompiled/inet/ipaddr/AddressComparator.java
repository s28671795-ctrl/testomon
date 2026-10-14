/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.Address;
import inet.ipaddr.AddressSection;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.format.AddressDivisionSeries;
import inet.ipaddr.format.AddressGenericDivision;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.large.IPAddressLargeDivision;
import inet.ipaddr.format.large.IPAddressLargeDivisionGrouping;
import inet.ipaddr.format.standard.AddressBitsDivision;
import inet.ipaddr.format.standard.AddressDivision;
import inet.ipaddr.format.standard.AddressDivisionGrouping;
import inet.ipaddr.format.standard.IPAddressBitsDivision;
import inet.ipaddr.format.standard.IPAddressDivisionGrouping;
import inet.ipaddr.ipv4.IPv4AddressSection;
import inet.ipaddr.ipv4.IPv4AddressSegment;
import inet.ipaddr.ipv4.IPv4AddressSeqRange;
import inet.ipaddr.ipv4.IPv4JoinedSegments;
import inet.ipaddr.ipv6.IPv6Address;
import inet.ipaddr.ipv6.IPv6AddressSection;
import inet.ipaddr.ipv6.IPv6AddressSegment;
import inet.ipaddr.ipv6.IPv6AddressSeqRange;
import inet.ipaddr.mac.MACAddressSection;
import inet.ipaddr.mac.MACAddressSegment;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Objects;

public abstract class AddressComparator
implements Comparator<AddressItem> {
    protected final boolean equalsConsistent;

    AddressComparator(boolean equalsConsistent) {
        this.equalsConsistent = equalsConsistent;
    }

    @Override
    public int compare(Address one, Address two) {
        if (one == two) {
            return 0;
        }
        int result = this.compare(one.getSection(), two.getSection());
        if (result == 0 && one instanceof IPv6Address) {
            IPv6Address oneIPv6 = (IPv6Address)one;
            IPv6Address twoIPv6 = (IPv6Address)two;
            result = Objects.compare(oneIPv6.getZone(), twoIPv6.getZone(), Comparator.nullsFirst(String::compareTo));
        }
        return result;
    }

    private static int mapGrouping(AddressDivisionSeries series) {
        if (series instanceof IPv6AddressSection) {
            return 6;
        }
        if (series instanceof IPv4AddressSection) {
            return 4;
        }
        if (series instanceof IPv6AddressSection.IPv6v4MixedAddressSection) {
            return 5;
        }
        if (series instanceof MACAddressSection) {
            return 3;
        }
        if (series instanceof IPAddressDivisionGrouping) {
            return -1;
        }
        if (series instanceof IPAddressLargeDivisionGrouping) {
            return -2;
        }
        if (series instanceof AddressDivisionGrouping) {
            return -3;
        }
        return 0;
    }

    private static int mapDivision(AddressGenericDivision div) {
        if (div instanceof IPv6AddressSegment) {
            return 4;
        }
        if (div instanceof IPv4AddressSegment) {
            return 3;
        }
        if (div instanceof MACAddressSegment) {
            return 1;
        }
        if (div instanceof IPv4JoinedSegments) {
            return 2;
        }
        if (div instanceof IPAddressLargeDivision) {
            return -1;
        }
        if (div instanceof IPAddressBitsDivision) {
            return -2;
        }
        if (div instanceof AddressBitsDivision) {
            return -3;
        }
        return 0;
    }

    private static int mapRange(IPAddressSeqRange range) {
        if (range instanceof IPv4AddressSeqRange) {
            return 1;
        }
        if (range instanceof IPv6AddressSeqRange) {
            return 2;
        }
        return 0;
    }

    @Override
    public int compare(AddressSection one, AddressSection two) {
        int result;
        if (one == two) {
            return 0;
        }
        if (!one.getClass().equals(two.getClass()) && (result = AddressComparator.mapGrouping(one) - AddressComparator.mapGrouping(two)) != 0) {
            return result;
        }
        if (one instanceof IPv6AddressSection) {
            IPv6AddressSection o1 = (IPv6AddressSection)one;
            IPv6AddressSection o2 = (IPv6AddressSection)two;
            int result2 = o2.addressSegmentIndex - o1.addressSegmentIndex;
            if (result2 != 0) {
                return result2;
            }
        } else if (one instanceof MACAddressSection) {
            MACAddressSection o1 = (MACAddressSection)one;
            MACAddressSection o2 = (MACAddressSection)two;
            int result3 = o2.addressSegmentIndex - o1.addressSegmentIndex;
            if (result3 != 0) {
                return result3;
            }
        }
        return this.compareParts(one, two);
    }

    @Override
    public int compare(AddressItem one, AddressItem two) {
        int bitDiff;
        if (one instanceof AddressDivisionSeries) {
            if (two instanceof AddressDivisionSeries) {
                return this.compare((AddressDivisionSeries)one, (AddressDivisionSeries)two);
            }
            if (this.equalsConsistent) {
                return 1;
            }
            if (one.isMultiple()) {
                AddressDivisionSeries oneSeries = (AddressDivisionSeries)one;
                if (oneSeries.getDivisionCount() > 0) {
                    return 1;
                }
                one = oneSeries.getDivision(0);
            }
        }
        if (one instanceof AddressGenericDivision) {
            if (two instanceof AddressGenericDivision) {
                return this.compare((AddressGenericDivision)one, (AddressGenericDivision)two);
            }
            if (this.equalsConsistent) {
                return -1;
            }
        } else if (one instanceof IPAddressSeqRange) {
            if (two instanceof IPAddressSeqRange) {
                return this.compare((IPAddressSeqRange)one, (IPAddressSeqRange)two);
            }
            if (this.equalsConsistent) {
                if (two instanceof AddressDivisionSeries) {
                    return -1;
                }
                return 1;
            }
        }
        if (this.equalsConsistent) {
            if (two instanceof AddressDivisionSeries) {
                return -1;
            }
            if (two instanceof AddressGenericDivision) {
                return 1;
            }
            if (two instanceof IPAddressSeqRange) {
                return -1;
            }
        }
        if (one == two) {
            return 0;
        }
        if (this.equalsConsistent && (bitDiff = one.getBitCount() - two.getBitCount()) != 0) {
            return bitDiff;
        }
        if (two instanceof AddressDivisionSeries) {
            AddressDivisionSeries twoSeries = (AddressDivisionSeries)two;
            if (two.isMultiple() && twoSeries.getDivisionCount() > 0) {
                return 1;
            }
            if (one instanceof AddressGenericDivision) {
                return this.compare((AddressGenericDivision)one, twoSeries.getDivision(0));
            }
            two = twoSeries.getDivision(0);
        }
        return this.compareValues(one.getUpperValue(), one.getValue(), two.getUpperValue(), two.getValue());
    }

    @Override
    public int compare(AddressDivisionSeries one, AddressDivisionSeries two) {
        int result;
        if (one instanceof Address) {
            if (two instanceof Address) {
                return this.compare((Address)one, (Address)two);
            }
            if (this.equalsConsistent) {
                return -1;
            }
            one = ((Address)one).getSection();
        } else if (two instanceof Address) {
            if (this.equalsConsistent) {
                return 1;
            }
            two = ((Address)two).getSection();
        }
        if (one instanceof AddressSection && two instanceof AddressSection) {
            return this.compare((AddressSection)one, (AddressSection)two);
        }
        if (one == two) {
            return 0;
        }
        if (!one.getClass().equals(two.getClass()) && (result = AddressComparator.mapGrouping(one) - AddressComparator.mapGrouping(two)) != 0) {
            return result;
        }
        return this.compareParts(one, two);
    }

    protected static int compareDivBitCounts(AddressDivisionSeries oneSeries, AddressDivisionSeries twoSeries) {
        int count = oneSeries.getDivisionCount();
        int result = count - twoSeries.getDivisionCount();
        if (result == 0) {
            for (int i = 0; i < count && (result = oneSeries.getDivision(i).getBitCount() - twoSeries.getDivision(i).getBitCount()) == 0; ++i) {
            }
        }
        return result;
    }

    @Override
    public int compare(AddressSegment one, AddressSegment two) {
        int result;
        if (one == two) {
            return 0;
        }
        if (!one.getClass().equals(two.getClass()) && (result = AddressComparator.mapDivision(one) - AddressComparator.mapDivision(two)) != 0) {
            return result;
        }
        return this.compareValues(one.getUpperSegmentValue(), one.getSegmentValue(), two.getUpperSegmentValue(), two.getSegmentValue());
    }

    @Override
    public int compare(IPAddressSeqRange one, IPAddressSeqRange two) {
        int result;
        if (one == two) {
            return 0;
        }
        if (!one.getClass().equals(two.getClass()) && (result = AddressComparator.mapRange(one) - AddressComparator.mapRange(two)) != 0) {
            return result;
        }
        if (one instanceof IPv4AddressSeqRange && two instanceof IPv4AddressSeqRange) {
            IPv4AddressSeqRange gOne = (IPv4AddressSeqRange)one;
            IPv4AddressSeqRange gTwo = (IPv4AddressSeqRange)two;
            return this.compareValues(gOne.getUpper().longValue(), gOne.getLower().longValue(), gTwo.getUpper().longValue(), gTwo.getLower().longValue());
        }
        return this.compareValues(one.getUpperValue(), one.getValue(), two.getUpperValue(), two.getValue());
    }

    @Override
    public int compare(AddressGenericDivision one, AddressGenericDivision two) {
        int bitDiff;
        int result;
        if (one instanceof AddressSegment && two instanceof AddressSegment) {
            return this.compare((AddressSegment)one, (AddressSegment)two);
        }
        if (one == two) {
            return 0;
        }
        if (!one.getClass().equals(two.getClass()) && (result = AddressComparator.mapDivision(one) - AddressComparator.mapDivision(two)) != 0) {
            return result;
        }
        if (this.equalsConsistent && (bitDiff = one.getBitCount() - two.getBitCount()) != 0) {
            return bitDiff;
        }
        if (one instanceof AddressDivision && two instanceof AddressDivision) {
            AddressDivision gOne = (AddressDivision)one;
            AddressDivision gTwo = (AddressDivision)two;
            return this.compareValues(gOne.getUpperDivisionValue(), gOne.getDivisionValue(), gTwo.getUpperDivisionValue(), gTwo.getDivisionValue());
        }
        return this.compareValues(one.getUpperValue(), one.getValue(), two.getUpperValue(), two.getValue());
    }

    protected abstract int compareParts(AddressDivisionSeries var1, AddressDivisionSeries var2);

    protected abstract int compareParts(AddressSection var1, AddressSection var2);

    protected abstract int compareValues(BigInteger var1, BigInteger var2, BigInteger var3, BigInteger var4);

    protected abstract int compareValues(long var1, long var3, long var5, long var7);

    protected abstract int compareValues(int var1, int var2, int var3, int var4);

    static int convertResult(long v) {
        return v == 0L ? 0 : (v > 0L ? 1 : -1);
    }

    public static class CountComparator
    extends AddressComparator {
        public CountComparator() {
            this(true);
        }

        public CountComparator(boolean equalsConsistent) {
            super(equalsConsistent);
        }

        private static int compareCount(AddressDivisionSeries one, AddressDivisionSeries two) {
            return one.isMore(two);
        }

        @Override
        protected int compareParts(AddressSection one, AddressSection two) {
            int result = one.getBitCount() - two.getBitCount();
            if (result == 0 && (result = CountComparator.compareCount(one, two)) == 0) {
                result = this.compareEqualSizedSections(one, two);
            }
            return result;
        }

        @Override
        protected int compareParts(AddressDivisionSeries one, AddressDivisionSeries two) {
            int result = one.getBitCount() - two.getBitCount();
            if (result == 0 && (result = CountComparator.compareCount(one, two)) == 0) {
                result = this.compareDivisionGroupings(one, two);
            }
            return result;
        }

        private int compareDivisionGroupings(AddressDivisionSeries oneSeries, AddressDivisionSeries twoSeries) {
            int result;
            AddressDivisionGrouping two;
            AddressDivisionGrouping one;
            if (oneSeries instanceof AddressDivisionGrouping && twoSeries instanceof AddressDivisionGrouping) {
                one = (AddressDivisionGrouping)oneSeries;
                two = (AddressDivisionGrouping)twoSeries;
            } else {
                two = null;
                one = null;
            }
            if ((this.equalsConsistent || oneSeries.isMultiple() || twoSeries.isMultiple()) && (result = CountComparator.compareDivBitCounts(oneSeries, twoSeries)) != 0) {
                return result;
            }
            int oneSeriesByteCount = oneSeries.getByteCount();
            int twoSeriesByteCount = twoSeries.getByteCount();
            byte[] oneUpperBytes = new byte[oneSeriesByteCount];
            byte[] oneLowerBytes = new byte[oneSeriesByteCount];
            byte[] twoUpperBytes = new byte[twoSeriesByteCount];
            byte[] twoLowerBytes = new byte[twoSeriesByteCount];
            int twoTotalBitCount = 0;
            int oneTotalBitCount = 0;
            int twoByteCount = 0;
            int oneByteCount = 0;
            int twoByteIndex = 0;
            int oneByteIndex = 0;
            int twoIndex = 0;
            int oneIndex = 0;
            int twoBitCount = 0;
            int oneBitCount = 0;
            long twoLower = 0L;
            long twoUpper = 0L;
            long oneLower = 0L;
            long oneUpper = 0L;
            while (oneIndex < oneSeries.getDivisionCount() || twoIndex < twoSeries.getDivisionCount()) {
                int result2;
                if (one != null) {
                    if (oneBitCount == 0) {
                        AddressDivision oneCombo = one.getDivision(oneIndex++);
                        oneBitCount = oneCombo.getBitCount();
                        oneUpper = oneCombo.getUpperDivisionValue();
                        oneLower = oneCombo.getDivisionValue();
                    }
                    if (twoBitCount == 0) {
                        AddressDivision twoCombo = two.getDivision(twoIndex++);
                        twoBitCount = twoCombo.getBitCount();
                        twoUpper = twoCombo.getUpperDivisionValue();
                        twoLower = twoCombo.getDivisionValue();
                    }
                } else {
                    byte upperByte;
                    int shortCount;
                    byte lowerByte;
                    byte upperByte2;
                    if (oneBitCount == 0) {
                        if (oneByteCount == 0) {
                            AddressGenericDivision oneCombo = oneSeries.getDivision(oneIndex++);
                            oneUpperBytes = oneCombo.getUpperBytes(oneUpperBytes);
                            oneLowerBytes = oneCombo.getBytes(oneLowerBytes);
                            oneTotalBitCount = oneCombo.getBitCount();
                            oneByteCount = oneCombo.getByteCount();
                            oneByteIndex = 0;
                        }
                        int count = 7;
                        oneLower = 0L;
                        oneUpper = 0L;
                        if (count < oneByteCount) {
                            oneBitCount = count << 3;
                            oneTotalBitCount -= oneBitCount;
                            oneByteCount -= count;
                            while (count-- > 0) {
                                upperByte2 = oneUpperBytes[oneByteIndex];
                                lowerByte = oneLowerBytes[oneByteIndex++];
                                oneUpper = oneUpper << 8 | (long)upperByte2;
                                oneLower = oneLower << 8 | (long)lowerByte;
                            }
                        } else {
                            byte lowerByte2;
                            shortCount = oneByteCount - 1;
                            int lastBitsCount = oneTotalBitCount - (shortCount << 3);
                            while (shortCount-- > 0) {
                                upperByte = oneUpperBytes[oneByteIndex];
                                lowerByte2 = oneLowerBytes[oneByteIndex++];
                                oneUpper = oneUpper << 8 | (long)upperByte;
                                oneLower = oneLower << 8 | (long)lowerByte2;
                            }
                            upperByte = oneUpperBytes[oneByteIndex];
                            lowerByte2 = oneLowerBytes[oneByteIndex++];
                            oneUpper = oneUpper << lastBitsCount | (long)(upperByte >>> 8 - lastBitsCount);
                            oneLower = oneLower << lastBitsCount | (long)(lowerByte2 >>> 8 - lastBitsCount);
                            oneBitCount = oneTotalBitCount;
                            oneByteCount = 0;
                            oneTotalBitCount = 0;
                        }
                    }
                    if (twoBitCount == 0) {
                        if (twoByteCount == 0) {
                            AddressGenericDivision twoCombo = twoSeries.getDivision(twoIndex++);
                            twoUpperBytes = twoCombo.getUpperBytes(twoUpperBytes);
                            twoLowerBytes = twoCombo.getBytes(twoLowerBytes);
                            twoTotalBitCount = twoCombo.getBitCount();
                            twoByteCount = twoCombo.getByteCount();
                            twoByteIndex = 0;
                        }
                        int count = 7;
                        twoLower = 0L;
                        twoUpper = 0L;
                        if (count < twoByteCount) {
                            twoBitCount = count << 3;
                            twoTotalBitCount -= twoBitCount;
                            twoByteCount -= count;
                            while (count-- > 0) {
                                upperByte2 = twoUpperBytes[twoByteIndex];
                                lowerByte = twoLowerBytes[twoByteIndex++];
                                twoUpper = twoUpper << 8 | (long)upperByte2;
                                twoLower = twoLower << 8 | (long)lowerByte;
                            }
                        } else {
                            shortCount = twoByteCount - 1;
                            int lastBitsCount = twoTotalBitCount - (shortCount << 3);
                            while (shortCount-- > 0) {
                                upperByte = twoUpperBytes[twoByteIndex];
                                byte lowerByte3 = twoLowerBytes[twoByteIndex++];
                                twoUpper = twoUpper << 8 | (long)upperByte;
                                twoLower = twoLower << 8 | (long)lowerByte3;
                            }
                            upperByte = twoUpperBytes[twoByteIndex];
                            byte lowerByte4 = twoLowerBytes[twoByteIndex++];
                            twoUpper = twoUpper << lastBitsCount | (long)(upperByte >>> 8 - lastBitsCount);
                            twoLower = twoLower << lastBitsCount | (long)(lowerByte4 >>> 8 - lastBitsCount);
                            twoBitCount = twoTotalBitCount;
                            twoByteCount = 0;
                            twoTotalBitCount = 0;
                        }
                    }
                }
                long oneResultUpper = oneUpper;
                long oneResultLower = oneLower;
                long twoResultUpper = twoUpper;
                long twoResultLower = twoLower;
                if (twoBitCount == oneBitCount) {
                    twoBitCount = 0;
                    oneBitCount = 0;
                } else {
                    long mask;
                    int diffBits = twoBitCount - oneBitCount;
                    if (diffBits > 0) {
                        twoResultUpper >>>= diffBits;
                        twoResultLower >>>= diffBits;
                        mask = -1L << diffBits ^ 0xFFFFFFFFFFFFFFFFL;
                        twoUpper &= mask;
                        twoLower &= mask;
                        twoBitCount = diffBits;
                        oneBitCount = 0;
                    } else {
                        diffBits = -diffBits;
                        oneResultUpper >>>= diffBits;
                        oneResultLower >>>= diffBits;
                        mask = -1L << diffBits ^ 0xFFFFFFFFFFFFFFFFL;
                        oneUpper &= mask;
                        oneLower &= mask;
                        oneBitCount = diffBits;
                        twoBitCount = 0;
                    }
                }
                if ((result2 = this.compareValues(oneResultUpper, oneResultLower, twoResultUpper, twoResultLower)) == 0) continue;
                return result2;
            }
            return 0;
        }

        protected int compareEqualSizedSections(AddressSection one, AddressSection two) {
            int segCount = one.getSegmentCount();
            for (int i = 0; i < segCount; ++i) {
                int twoLower;
                AddressSegment segOne = one.getSegment(i);
                AddressSegment segTwo = two.getSegment(i);
                int oneUpper = segOne.getUpperSegmentValue();
                int twoUpper = segTwo.getUpperSegmentValue();
                int oneLower = segOne.getSegmentValue();
                int result = this.compareValues(oneUpper, oneLower, twoUpper, twoLower = segTwo.getSegmentValue());
                if (result == 0) continue;
                return result;
            }
            return 0;
        }

        @Override
        protected int compareValues(int oneUpper, int oneLower, int twoUpper, int twoLower) {
            int result = oneUpper - oneLower - (twoUpper - twoLower);
            if (result == 0) {
                result = oneLower - twoLower;
            }
            return result;
        }

        @Override
        protected int compareValues(long oneUpper, long oneLower, long twoUpper, long twoLower) {
            long size1 = oneUpper - oneLower;
            long size2 = twoUpper - twoLower;
            if (size1 == size2) {
                if (oneLower == twoLower) {
                    return 0;
                }
                if (oneLower > twoLower) {
                    return 1;
                }
            } else if (size1 > size2) {
                return 1;
            }
            return -1;
        }

        @Override
        protected int compareValues(BigInteger oneUpper, BigInteger oneLower, BigInteger twoUpper, BigInteger twoLower) {
            BigInteger twoCount;
            BigInteger oneCount = oneUpper.subtract(oneLower);
            int result = oneCount.compareTo(twoCount = twoUpper.subtract(twoLower));
            if (result == 0) {
                result = oneLower.compareTo(twoLower);
            }
            return result;
        }
    }

    public static class ValueComparator
    extends AddressComparator {
        private final boolean compareHighValue;
        private final boolean flipSecond;

        public ValueComparator(boolean compareHighValue) {
            this(true, compareHighValue);
        }

        public ValueComparator(boolean equalsConsistent, boolean compareHighValue) {
            this(true, compareHighValue, false);
        }

        public ValueComparator(boolean equalsConsistent, boolean compareHighValue, boolean flipSecond) {
            super(equalsConsistent);
            this.compareHighValue = compareHighValue;
            this.flipSecond = flipSecond;
        }

        @Override
        protected int compareParts(AddressSection one, AddressSection two) {
            int sizeResult = one.getByteCount() - two.getByteCount();
            if (sizeResult != 0) {
                return sizeResult;
            }
            boolean compareHigh = this.compareHighValue;
            do {
                int segCount = one.getSegmentCount();
                for (int i = 0; i < segCount; ++i) {
                    int result;
                    AddressSegment segOne = one.getSegment(i);
                    AddressSegment segTwo = two.getSegment(i);
                    int n = result = compareHigh ? segOne.getUpperSegmentValue() - segTwo.getUpperSegmentValue() : segOne.getSegmentValue() - segTwo.getSegmentValue();
                    if (result == 0) continue;
                    if (this.flipSecond && compareHigh != this.compareHighValue) {
                        return -result;
                    }
                    return result;
                }
            } while ((compareHigh = !compareHigh) != this.compareHighValue);
            return 0;
        }

        @Override
        protected int compareParts(AddressDivisionSeries oneSeries, AddressDivisionSeries twoSeries) {
            AddressDivisionGrouping two;
            AddressDivisionGrouping one;
            int result;
            int sizeResult = oneSeries.getBitCount() - twoSeries.getBitCount();
            if (sizeResult != 0) {
                return sizeResult;
            }
            if ((this.equalsConsistent || oneSeries.isMultiple() || twoSeries.isMultiple()) && (result = ValueComparator.compareDivBitCounts(oneSeries, twoSeries)) != 0) {
                return result;
            }
            boolean compareHigh = this.compareHighValue;
            if (oneSeries instanceof AddressDivisionGrouping && twoSeries instanceof AddressDivisionGrouping) {
                one = (AddressDivisionGrouping)oneSeries;
                two = (AddressDivisionGrouping)twoSeries;
            } else {
                two = null;
                one = null;
            }
            int oneSeriesByteCount = oneSeries.getByteCount();
            int twoSeriesByteCount = twoSeries.getByteCount();
            byte[] oneBytes = new byte[oneSeriesByteCount];
            byte[] twoBytes = new byte[twoSeriesByteCount];
            do {
                int twoTotalBitCount = 0;
                int oneTotalBitCount = 0;
                int twoByteCount = 0;
                int oneByteCount = 0;
                int twoByteIndex = 0;
                int oneByteIndex = 0;
                int twoIndex = 0;
                int oneIndex = 0;
                int twoBitCount = 0;
                int oneBitCount = 0;
                long twoValue = 0L;
                long oneValue = 0L;
                while (oneIndex < oneSeries.getDivisionCount() || twoIndex < twoSeries.getDivisionCount()) {
                    long result2;
                    if (one != null) {
                        if (oneBitCount == 0) {
                            AddressDivision oneCombo = one.getDivision(oneIndex++);
                            oneBitCount = oneCombo.getBitCount();
                            long l = oneValue = compareHigh ? oneCombo.getUpperDivisionValue() : oneCombo.getDivisionValue();
                        }
                        if (twoBitCount == 0) {
                            AddressDivision twoCombo = two.getDivision(twoIndex++);
                            twoBitCount = twoCombo.getBitCount();
                            twoValue = compareHigh ? twoCombo.getUpperDivisionValue() : twoCombo.getDivisionValue();
                        }
                    } else {
                        int lastBitsCount;
                        int shortCount;
                        if (oneBitCount == 0) {
                            if (oneByteCount == 0) {
                                AddressGenericDivision oneCombo = oneSeries.getDivision(oneIndex++);
                                oneBytes = compareHigh ? oneCombo.getUpperBytes(oneBytes) : oneCombo.getBytes(oneBytes);
                                oneTotalBitCount = oneCombo.getBitCount();
                                oneByteCount = oneCombo.getByteCount();
                                oneByteIndex = 0;
                            }
                            int count = 7;
                            oneValue = 0L;
                            if (count < oneByteCount) {
                                oneBitCount = count << 3;
                                oneTotalBitCount -= oneBitCount;
                                oneByteCount -= count;
                                while (count-- > 0) {
                                    oneValue = oneValue << 8 | (long)oneBytes[oneByteIndex++];
                                }
                            } else {
                                shortCount = oneByteCount - 1;
                                lastBitsCount = oneTotalBitCount - (shortCount << 3);
                                while (shortCount-- > 0) {
                                    oneValue = oneValue << 8 | (long)oneBytes[oneByteIndex++];
                                }
                                oneValue = oneValue << lastBitsCount | (long)(oneBytes[oneByteIndex++] >>> 8 - lastBitsCount);
                                oneBitCount = oneTotalBitCount;
                                oneByteCount = 0;
                                oneTotalBitCount = 0;
                            }
                        }
                        if (twoBitCount == 0) {
                            if (twoByteCount == 0) {
                                AddressGenericDivision twoCombo = twoSeries.getDivision(twoIndex++);
                                twoBytes = compareHigh ? twoCombo.getUpperBytes(twoBytes) : twoCombo.getBytes(twoBytes);
                                twoTotalBitCount = twoCombo.getBitCount();
                                twoByteCount = twoCombo.getByteCount();
                                twoByteIndex = 0;
                            }
                            int count = 7;
                            twoValue = 0L;
                            if (count < twoByteCount) {
                                twoBitCount = count << 3;
                                twoTotalBitCount -= twoBitCount;
                                twoByteCount -= count;
                                while (count-- > 0) {
                                    twoValue = twoValue << 8 | (long)twoBytes[twoByteIndex++];
                                }
                            } else {
                                shortCount = twoByteCount - 1;
                                lastBitsCount = twoTotalBitCount - (shortCount << 3);
                                while (shortCount-- > 0) {
                                    twoValue = twoValue << 8 | (long)twoBytes[twoByteIndex++];
                                }
                                twoValue = twoValue << lastBitsCount | (long)(twoBytes[twoByteIndex++] >>> 8 - lastBitsCount);
                                twoBitCount = twoTotalBitCount;
                                twoByteCount = 0;
                                twoTotalBitCount = 0;
                            }
                        }
                    }
                    long oneResultValue = oneValue;
                    long twoResultValue = twoValue;
                    if (twoBitCount == oneBitCount) {
                        twoBitCount = 0;
                        oneBitCount = 0;
                    } else {
                        int diffBits = twoBitCount - oneBitCount;
                        if (diffBits > 0) {
                            twoResultValue >>= diffBits;
                            twoValue &= -1L << diffBits ^ 0xFFFFFFFFFFFFFFFFL;
                            twoBitCount = diffBits;
                            oneBitCount = 0;
                        } else {
                            diffBits = -diffBits;
                            oneResultValue >>= diffBits;
                            oneValue &= -1L << diffBits ^ 0xFFFFFFFFFFFFFFFFL;
                            oneBitCount = diffBits;
                            twoBitCount = 0;
                        }
                    }
                    if ((result2 = oneResultValue - twoResultValue) == 0L) continue;
                    if (this.flipSecond && compareHigh != this.compareHighValue) {
                        result2 = -result2;
                    }
                    return ValueComparator.convertResult(result2);
                }
            } while ((compareHigh = !compareHigh) != this.compareHighValue);
            return 0;
        }

        @Override
        protected int compareValues(BigInteger oneUpper, BigInteger oneLower, BigInteger twoUpper, BigInteger twoLower) {
            int result;
            if (this.compareHighValue) {
                result = oneUpper.compareTo(twoUpper);
                if (result == 0) {
                    result = oneLower.compareTo(twoLower);
                    if (this.flipSecond) {
                        result = -result;
                    }
                }
            } else {
                result = oneLower.compareTo(twoLower);
                if (result == 0) {
                    result = oneUpper.compareTo(twoUpper);
                    if (this.flipSecond) {
                        result = -result;
                    }
                }
            }
            return ValueComparator.convertResult(result);
        }

        @Override
        protected int compareValues(long oneUpper, long oneLower, long twoUpper, long twoLower) {
            long result;
            if (this.compareHighValue) {
                result = oneUpper - twoUpper;
                if (result == 0L) {
                    result = oneLower - twoLower;
                    if (this.flipSecond) {
                        result = -result;
                    }
                }
            } else {
                result = oneLower - twoLower;
                if (result == 0L) {
                    result = oneUpper - twoUpper;
                    if (this.flipSecond) {
                        result = -result;
                    }
                }
            }
            return ValueComparator.convertResult(result);
        }

        @Override
        protected int compareValues(int oneUpper, int oneLower, int twoUpper, int twoLower) {
            int result;
            if (this.compareHighValue) {
                result = oneUpper - twoUpper;
                if (result == 0) {
                    result = oneLower - twoLower;
                    if (this.flipSecond) {
                        result = -result;
                    }
                }
            } else {
                result = oneLower - twoLower;
                if (result == 0) {
                    result = oneUpper - twoUpper;
                    if (this.flipSecond) {
                        result = -result;
                    }
                }
            }
            return result;
        }
    }
}

