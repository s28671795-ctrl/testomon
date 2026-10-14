/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.Address;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.AddressComponentRange;
import inet.ipaddr.format.IPAddressRange;
import inet.ipaddr.format.standard.AddressCreator;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import inet.ipaddr.format.validate.ParsedAddressGrouping;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public abstract class IPAddressSeqRange
implements IPAddressRange {
    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_RANGE_SEPARATOR = " -> ";
    private static final IPAddressSeqRange[] EMPTY_RANGES = new IPAddressSeqRange[0];
    protected final IPAddress lower;
    protected final IPAddress upper;
    private transient BigInteger count;
    private transient int hashCode;

    protected <T extends IPAddress> IPAddressSeqRange(T first, T second, boolean preSet) {
        this.lower = first;
        this.upper = second;
    }

    protected <T extends IPAddress> IPAddressSeqRange(T first, T other, UnaryOperator<T> getLower, UnaryOperator<T> getUpper, UnaryOperator<T> prefixLenRemover) {
        boolean f = first.contains(other);
        if (f || other.contains(first)) {
            IPAddress addr = f ? (IPAddress)prefixLenRemover.apply(first) : (IPAddress)prefixLenRemover.apply(other);
            this.lower = (IPAddress)getLower.apply(addr);
            this.upper = (IPAddress)getUpper.apply(addr);
        } else {
            IPAddress firstLower = (IPAddress)getLower.apply(first);
            IPAddress otherLower = (IPAddress)getLower.apply(other);
            IPAddress firstUpper = (IPAddress)getUpper.apply(first);
            IPAddress otherUpper = (IPAddress)getUpper.apply(other);
            IPAddress lower = IPAddressSeqRange.compareLowValues(firstLower, otherLower) > 0 ? otherLower : firstLower;
            IPAddress upper = IPAddressSeqRange.compareLowValues(firstUpper, otherUpper) < 0 ? otherUpper : firstUpper;
            this.lower = (IPAddress)prefixLenRemover.apply(lower);
            this.upper = (IPAddress)prefixLenRemover.apply(upper);
        }
    }

    protected <T extends IPAddress> IPAddressSeqRange(T first, T second) {
        this.lower = first;
        this.upper = second;
    }

    private static int compareLowValues(IPAddress one, IPAddress two) {
        return IPAddress.compareLowValues(one, two);
    }

    @Override
    public BigInteger getCount() {
        BigInteger result = this.count;
        if (result == null) {
            this.count = result = this.getCountImpl();
        }
        return result;
    }

    @Override
    public boolean isMultiple() {
        BigInteger count = this.count;
        if (count == null) {
            return !this.getLower().equals(this.getUpper());
        }
        return IPAddressRange.super.isMultiple();
    }

    public boolean isMore(IPAddressSeqRange other) {
        return this.getCount().compareTo(other.getCount()) > 0;
    }

    protected BigInteger getCountImpl() {
        return IPAddressRange.super.getCount();
    }

    @Override
    public abstract Iterable<? extends IPAddress> getIterable();

    protected static int getNetworkSegmentIndex(int networkPrefixLength, int bytesPerSegment, int bitsPerSegment) {
        return ParsedAddressGrouping.getNetworkSegmentIndex(networkPrefixLength, bytesPerSegment, bitsPerSegment);
    }

    protected static int getHostSegmentIndex(int networkPrefixLength, int bytesPerSegment, int bitsPerSegment) {
        return ParsedAddressGrouping.getHostSegmentIndex(networkPrefixLength, bytesPerSegment, bitsPerSegment);
    }

    @Override
    public abstract Iterator<? extends IPAddress> prefixBlockIterator(int var1);

    public abstract AddressComponentRangeSpliterator<? extends IPAddressSeqRange, ? extends IPAddress> prefixBlockSpliterator(int var1);

    @Override
    public abstract Stream<? extends IPAddress> prefixBlockStream(int var1);

    protected static <S extends AddressComponentRange, T> AddressComponentRangeSpliterator<S, T> createSpliterator(S forIteration, Predicate<IPAddressSeqRangeSplitterSink<S, T>> splitter, IPAddressSeqRangeIteratorProvider<S, T> iteratorProvider, ToLongFunction<S> longSizer) {
        return new IPAddressSection.IPAddressSeqRangeSpliterator<S, T>(forIteration, splitter, iteratorProvider, longSizer);
    }

    protected static <S extends AddressComponentRange, T> AddressComponentRangeSpliterator<S, T> createSpliterator(S forIteration, Predicate<IPAddressSeqRangeSplitterSink<S, T>> splitter, IPAddressSeqRangeIteratorProvider<S, T> iteratorProvider, Function<S, BigInteger> sizer, Predicate<S> downSizer, ToLongFunction<S> longSizer) {
        return new IPAddressSection.IPAddressSeqRangeSpliterator<S, T>(forIteration, splitter, iteratorProvider, sizer, downSizer, longSizer);
    }

    protected static <S extends AddressComponentRange> AddressComponentSpliterator<S> createPrefixSpliterator(S forIteration, Predicate<IPAddressSeqRangeSplitterSink<S, S>> splitter, IPAddressSeqRangeIteratorProvider<S, S> iteratorProvider, ToLongFunction<S> longSizer) {
        return new IPAddressSection.IPAddressSeqRangePrefixSpliterator<S>(forIteration, splitter, iteratorProvider, longSizer);
    }

    protected static <S extends AddressComponentRange> AddressComponentSpliterator<S> createPrefixSpliterator(S forIteration, Predicate<IPAddressSeqRangeSplitterSink<S, S>> splitter, IPAddressSeqRangeIteratorProvider<S, S> iteratorProvider, Function<S, BigInteger> sizer, Predicate<S> downSizer, ToLongFunction<S> longSizer) {
        return new IPAddressSection.IPAddressSeqRangePrefixSpliterator<S>(forIteration, splitter, iteratorProvider, sizer, downSizer, longSizer);
    }

    protected static <R, A extends IPAddress> Iterator<R> rangedIterator(final Iterator<A> iter) {
        return new Iterator<R>(){

            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public R next() {
                return ((IPAddress)iter.next()).toSequentialRange();
            }
        };
    }

    public Iterator<? extends IPAddressSeqRange> prefixIterator(final int prefixLength) {
        if (prefixLength < 0) {
            throw new PrefixLenException(prefixLength);
        }
        if (!this.isMultiple()) {
            return new Iterator<IPAddressSeqRange>(){
                IPAddressSeqRange orig;
                {
                    this.orig = IPAddressSeqRange.this;
                }

                @Override
                public boolean hasNext() {
                    return this.orig != null;
                }

                @Override
                public IPAddressSeqRange next() {
                    if (this.orig == null) {
                        throw new NoSuchElementException();
                    }
                    IPAddressSeqRange result = this.orig;
                    this.orig = null;
                    return result;
                }
            };
        }
        return new Iterator<IPAddressSeqRange>(){
            Iterator<? extends IPAddress> prefixBlockIterator;
            private boolean first;
            {
                this.prefixBlockIterator = IPAddressSeqRange.this.prefixBlockIterator(prefixLength);
                this.first = true;
            }

            @Override
            public boolean hasNext() {
                return this.prefixBlockIterator.hasNext();
            }

            @Override
            public IPAddressSeqRange next() {
                IPAddress upper;
                IPAddress next = this.prefixBlockIterator.next();
                if (this.first) {
                    this.first = false;
                    IPAddress lower = IPAddressSeqRange.this.getLower();
                    if (this.hasNext()) {
                        if (!lower.includesZeroHost(prefixLength)) {
                            return IPAddressSeqRange.this.create(lower, next.getUpper());
                        }
                    } else {
                        IPAddress upper2 = IPAddressSeqRange.this.getUpper();
                        if (!lower.includesZeroHost(prefixLength) || !upper2.includesMaxHost(prefixLength)) {
                            return IPAddressSeqRange.this.create(lower, upper2);
                        }
                    }
                } else if (!this.hasNext() && !(upper = IPAddressSeqRange.this.getUpper()).includesMaxHost(prefixLength)) {
                    return IPAddressSeqRange.this.create(next.getLower(), upper);
                }
                return next.toSequentialRange();
            }
        };
    }

    public abstract AddressComponentSpliterator<? extends IPAddressSeqRange> prefixSpliterator(int var1);

    public abstract Stream<? extends IPAddressSeqRange> prefixStream(int var1);

    protected static <I extends IPAddressSeqRange, T extends IPAddressRange, S extends AddressSegment> boolean split(IPAddressSeqRangeSplitterSink<I, T> sink2, BiFunction<S[], S[], I> transformer, AddressNetwork.AddressSegmentCreator<S> segmentCreator, S[] originalSegmentsLower, S[] originalSegmentsUpper, int networkSegmentIndex, int hostSegmentIndex, Integer prefixLength) {
        S segUpper;
        S segLower;
        int i;
        AddressSegment upperSeg = null;
        AddressSegment lowerSeg = null;
        boolean isSplit = false;
        for (i = 0; i < hostSegmentIndex; ++i) {
            int upper;
            segLower = originalSegmentsLower[i];
            segUpper = originalSegmentsUpper[i];
            int lower = segLower.getSegmentValue();
            if (lower == (upper = segUpper.getSegmentValue())) continue;
            isSplit = true;
            int size = upper - lower;
            int mid = lower + (size >>> 1);
            lowerSeg = (AddressSegment)segmentCreator.createSegment(mid);
            upperSeg = (AddressSegment)segmentCreator.createSegment(mid + 1);
            break;
        }
        if (i == networkSegmentIndex && !isSplit) {
            segLower = originalSegmentsLower[i];
            segUpper = originalSegmentsUpper[i];
            int segBitCount = segLower.getBitCount();
            Integer pref = IPAddressSection.getSegmentPrefixLength(segBitCount, prefixLength, i);
            int shiftAdjustment = segBitCount - pref;
            int lower = segLower.getSegmentValue();
            int upper = segUpper.getSegmentValue();
            if ((lower >>>= shiftAdjustment) != (upper >>>= shiftAdjustment)) {
                isSplit = true;
                int size = upper - lower;
                int mid = lower + (size >>> 1);
                int next = mid + 1;
                mid = mid << shiftAdjustment | ~(-1 << shiftAdjustment);
                lowerSeg = segmentCreator.createSegment(mid);
                upperSeg = segmentCreator.createSegment(next <<= shiftAdjustment);
            }
        }
        if (isSplit) {
            int len = originalSegmentsLower.length;
            Object[] lowerUpperSegs = segmentCreator.createSegmentArray(len);
            Object[] upperLowerSegs = segmentCreator.createSegmentArray(len);
            System.arraycopy(originalSegmentsLower, 0, lowerUpperSegs, 0, i);
            System.arraycopy(originalSegmentsLower, 0, upperLowerSegs, 0, i);
            int j = i + 1;
            lowerUpperSegs[i] = lowerSeg;
            upperLowerSegs[i] = upperSeg;
            Arrays.fill(lowerUpperSegs, j, lowerUpperSegs.length, segmentCreator.createSegment(lowerSeg.getMaxSegmentValue()));
            Arrays.fill(upperLowerSegs, j, upperLowerSegs.length, segmentCreator.createSegment(0));
            sink2.setSplitValues((IPAddressSeqRange)transformer.apply(originalSegmentsLower, lowerUpperSegs), (IPAddressSeqRange)transformer.apply(upperLowerSegs, originalSegmentsUpper));
        }
        return isSplit;
    }

    @Override
    public abstract Iterator<? extends IPAddress> iterator();

    public abstract AddressComponentRangeSpliterator<? extends IPAddressSeqRange, ? extends IPAddress> spliterator();

    public abstract Stream<? extends IPAddress> stream();

    protected static <T extends Address, S extends AddressSegment> Iterator<T> iterator(T original, AddressCreator<T, ?, ?, S> creator) {
        return IPAddressSection.iterator(original, creator, null);
    }

    protected static <T extends IPAddress, S extends IPAddressSegment> Iterator<T> iterator(T lower, T upper, AddressCreator<T, ?, ?, S> creator, IPAddressSection.SegFunction<T, S> segProducer, IPAddressSection.SegFunction<S, Iterator<S>> segmentIteratorProducer, SegValueComparator<T> segValueComparator, int networkSegmentIndex, int hostSegmentIndex, IPAddressSection.SegFunction<S, Iterator<S>> prefixedSegIteratorProducer) {
        int divCount = lower.getSegmentCount();
        ArrayList<Supplier<Iterator>> segIteratorProducerList = new ArrayList<Supplier<Iterator>>(divCount);
        final boolean[] finalValue = new boolean[divCount + 1];
        boolean notDiffering = true;
        finalValue[0] = true;
        IPAddressSegment allSegShared = null;
        for (int i = 0; i < divCount; ++i) {
            IPAddressSection.SegFunction<Object, Iterator<Object>> segIteratorProducer = prefixedSegIteratorProducer != null && i >= networkSegmentIndex ? prefixedSegIteratorProducer : segmentIteratorProducer;
            IPAddressSegment lowerSeg = (IPAddressSegment)segProducer.apply(lower, i);
            final int indexi = i;
            if (notDiffering) {
                Iterator iterator2;
                notDiffering = segValueComparator.apply(lower, upper, i);
                if (notDiffering) {
                    finalValue[i + 1] = true;
                    iterator2 = segIteratorProducer.apply(lowerSeg, i);
                    segIteratorProducerList.add(() -> iterator2);
                    continue;
                }
                iterator2 = segIteratorProducer.apply((IPAddressSegment)creator.createSegment(lowerSeg.getSegmentValue(), ((IPAddressSegment)upper.getSegment(i)).getSegmentValue(), null), i);
                Iterator wrappedFinalIterator = new Iterator<S>(){

                    @Override
                    public boolean hasNext() {
                        return iterator2.hasNext();
                    }

                    @Override
                    public S next() {
                        IPAddressSegment next = (IPAddressSegment)iterator2.next();
                        if (!iterator2.hasNext()) {
                            finalValue[indexi + 1] = true;
                        }
                        return next;
                    }
                };
                segIteratorProducerList.add(() -> wrappedFinalIterator);
                continue;
            }
            Iterator firstIterator = segIteratorProducer.apply((IPAddressSegment)creator.createSegment(lowerSeg.getSegmentValue(), lower.getMaxSegmentValue(), null), i);
            final Iterator<S> finalIterator = segIteratorProducer.apply((IPAddressSegment)creator.createSegment(0, ((IPAddressSegment)upper.getSegment(i)).getSegmentValue(), null), i);
            Iterator wrappedFinalIterator = new Iterator<S>(){

                @Override
                public boolean hasNext() {
                    return finalIterator.hasNext();
                }

                @Override
                public S next() {
                    IPAddressSegment next = (IPAddressSegment)finalIterator.next();
                    if (!finalIterator.hasNext()) {
                        finalValue[indexi + 1] = true;
                    }
                    return next;
                }
            };
            if (allSegShared == null) {
                allSegShared = (IPAddressSegment)creator.createSegment(0, lower.getMaxSegmentValue(), null);
            }
            IPAddressSegment allSeg = allSegShared;
            Supplier<Iterator> finalIteratorProducer = () -> finalValue[indexi] ? wrappedFinalIterator : (Iterator)segIteratorProducer.apply(allSeg, indexi);
            segIteratorProducerList.add(() -> {
                segIteratorProducerList.set(indexi, finalIteratorProducer);
                return firstIterator;
            });
        }
        IntFunction iteratorProducer = iteratorIndex -> (Iterator)((Supplier)segIteratorProducerList.get(iteratorIndex)).get();
        return IPAddressSection.iterator(null, creator, IPAddressSection.iterator(lower.getSegmentCount(), creator, iteratorProducer, networkSegmentIndex, hostSegmentIndex, iteratorProducer));
    }

    @Override
    public IPAddress getLower() {
        return this.lower;
    }

    @Override
    public IPAddress getUpper() {
        return this.upper;
    }

    public String toNormalizedString(String separator) {
        Function<IPAddress, String> stringer = Address::toNormalizedString;
        return this.toString(stringer, separator, stringer);
    }

    @Override
    public String toNormalizedString() {
        return this.toNormalizedString(DEFAULT_RANGE_SEPARATOR);
    }

    public String toCanonicalString(String separator) {
        Function<IPAddress, String> stringer = Address::toCanonicalString;
        return this.toString(stringer, separator, stringer);
    }

    @Override
    public String toCanonicalString() {
        return this.toCanonicalString(DEFAULT_RANGE_SEPARATOR);
    }

    public String toString(Function<? super IPAddress, String> lowerStringer, String separator, Function<? super IPAddress, String> upperStringer) {
        return lowerStringer.apply(this.getLower()) + separator + upperStringer.apply(this.getUpper());
    }

    public String toString() {
        return this.toCanonicalString();
    }

    @Override
    public abstract IPAddress coverWithPrefixBlock();

    @Override
    public abstract IPAddress[] spanWithPrefixBlocks();

    @Override
    public abstract IPAddress[] spanWithSequentialBlocks();

    public static IPAddressSeqRange[] join(IPAddressSeqRange ... ranges) {
        if (ranges.length == 0) {
            return EMPTY_RANGES;
        }
        ranges = (IPAddressSeqRange[])ranges.clone();
        int joinedCount = 0;
        int j = ranges.length - 1;
        for (int i = 0; i <= j; ++i) {
            if (ranges[i] != null) continue;
            ++joinedCount;
            while (ranges[j] == null && j > i) {
                --j;
                ++joinedCount;
            }
            if (j <= i) continue;
            ranges[i] = ranges[j];
            ranges[j] = null;
            --j;
        }
        int len = ranges.length - joinedCount;
        Arrays.sort(ranges, 0, len, Address.ADDRESS_LOW_VALUE_COMPARATOR);
        int i = 0;
        while (i < len) {
            IPAddressSeqRange range2;
            IPAddress nextLower;
            int j2;
            IPAddressSeqRange range = ranges[i];
            IPAddress currentLower = range.getLower();
            IPAddress currentUpper = range.getUpper();
            boolean didJoin = false;
            for (j2 = i + 1; j2 < len && (IPAddressSeqRange.compareLowValues(currentUpper, nextLower = (range2 = ranges[j2]).getLower()) >= 0 || currentUpper.getIPVersion().equals((Object)nextLower.getIPVersion()) && currentUpper.increment(1L).equals(nextLower)); ++j2) {
                ++joinedCount;
                IPAddress nextUpper = range2.getUpper();
                if (IPAddressSeqRange.compareLowValues(currentUpper, nextUpper) < 0) {
                    currentUpper = nextUpper;
                }
                ranges[j2] = null;
                didJoin = true;
            }
            if (didJoin) {
                ranges[i] = range.create(currentLower, currentUpper);
            }
            i = j2;
        }
        if (joinedCount == 0) {
            return ranges;
        }
        if (ranges.length == joinedCount) {
            return EMPTY_RANGES;
        }
        IPAddressSeqRange[] joined = new IPAddressSeqRange[ranges.length - joinedCount];
        if (joined.length > 0) {
            int i2 = 0;
            int j3 = 0;
            while (true) {
                IPAddressSeqRange range;
                if ((range = ranges[i2]) != null) {
                    joined[j3++] = range;
                    if (j3 >= joined.length) break;
                }
                ++i2;
            }
        }
        return joined;
    }

    public boolean overlaps(IPAddressSeqRange other) {
        return IPAddressSeqRange.compareLowValues(other.getLower(), this.getUpper()) <= 0 && IPAddressSeqRange.compareLowValues(other.getUpper(), this.getLower()) >= 0;
    }

    private boolean containsRange(IPAddressRange other) {
        return IPAddressSeqRange.compareLowValues(other.getLower(), this.getLower()) >= 0 && IPAddressSeqRange.compareLowValues(other.getUpper(), this.getUpper()) <= 0;
    }

    @Override
    public boolean contains(IPAddress other) {
        return this.containsRange(other);
    }

    @Override
    public boolean contains(IPAddressSeqRange other) {
        return this.containsRange(other);
    }

    @Override
    public boolean isSequential() {
        return true;
    }

    public int hashCode() {
        int res = this.hashCode;
        if (res == 0) {
            this.hashCode = res = 31 * this.getLower().hashCode() + this.getUpper().hashCode();
        }
        return res;
    }

    public boolean equals(Object o) {
        if (o instanceof IPAddressSeqRange) {
            IPAddressSeqRange otherRange = (IPAddressSeqRange)o;
            return this.getLower().equals(otherRange.getLower()) && this.getUpper().equals(otherRange.getUpper());
        }
        return false;
    }

    public IPAddressSeqRange intersect(IPAddressSeqRange other) {
        IPAddress otherLower = other.getLower();
        IPAddress otherUpper = other.getUpper();
        IPAddress lower = this.getLower();
        IPAddress upper = this.getUpper();
        if (IPAddressSeqRange.compareLowValues(lower, otherLower) <= 0) {
            if (IPAddressSeqRange.compareLowValues(upper, otherUpper) >= 0) {
                return other;
            }
            if (IPAddressSeqRange.compareLowValues(upper, otherLower) < 0) {
                return null;
            }
            return this.create(otherLower, upper);
        }
        if (IPAddressSeqRange.compareLowValues(otherUpper, upper) >= 0) {
            return this;
        }
        if (IPAddressSeqRange.compareLowValues(otherUpper, lower) < 0) {
            return null;
        }
        return this.create(lower, otherUpper);
    }

    public IPAddressSeqRange join(IPAddressSeqRange other) {
        IPAddress lowestLower;
        IPAddress otherLower = other.getLower();
        IPAddress otherUpper = other.getUpper();
        IPAddress lower = this.getLower();
        IPAddress upper = this.getUpper();
        int lowerComp = IPAddressSeqRange.compareLowValues(lower, otherLower);
        if (!this.overlaps(other)) {
            if (lower.getIPVersion().equals((Object)otherLower.getIPVersion())) {
                if (lowerComp >= 0) {
                    if (otherUpper.increment(1L).equals(lower)) {
                        return this.create(otherLower, upper);
                    }
                } else if (upper.increment(1L).equals(otherLower)) {
                    return this.create(lower, otherUpper);
                }
            }
            return null;
        }
        int upperComp = IPAddressSeqRange.compareLowValues(upper, otherUpper);
        if (lowerComp >= 0) {
            if (lowerComp == 0 && upperComp == 0) {
                return this;
            }
            lowestLower = otherLower;
        } else {
            lowestLower = lower;
        }
        IPAddress highestUpper = upperComp >= 0 ? upper : otherUpper;
        return this.create(lowestLower, highestUpper);
    }

    public IPAddressSeqRange extend(IPAddressRange other) {
        IPAddress otherLower = other.getLower();
        IPAddress otherUpper = other.getUpper();
        IPAddress lower = this.getLower();
        IPAddress upper = this.getUpper();
        int lowerComp = IPAddressSeqRange.compareLowValues(lower, otherLower);
        int upperComp = IPAddressSeqRange.compareLowValues(upper, otherUpper);
        if (lowerComp > 0) {
            if (upperComp <= 0) {
                return other.toSequentialRange();
            }
            if (!upper.getIPVersion().equals((Object)otherLower.getIPVersion())) {
                return null;
            }
            return this.create(otherLower, upper);
        }
        if (upperComp >= 0) {
            return this;
        }
        if (!lower.getIPVersion().equals((Object)otherUpper.getIPVersion())) {
            return null;
        }
        return this.create(lower, otherUpper);
    }

    public IPAddressSeqRange[] subtract(IPAddressSeqRange other) {
        IPAddress otherLower = other.getLower();
        IPAddress otherUpper = other.getUpper();
        IPAddress lower = this.getLower();
        IPAddress upper = this.getUpper();
        if (IPAddressSeqRange.compareLowValues(lower, otherLower) < 0) {
            if (IPAddressSeqRange.compareLowValues(upper, otherUpper) > 0) {
                return this.createPair(lower, otherLower.increment(-1L), otherUpper.increment(1L), upper);
            }
            int comp = IPAddressSeqRange.compareLowValues(upper, otherLower);
            if (comp < 0) {
                return this.createSingle();
            }
            return this.createSingle(lower, otherLower.increment(-1L));
        }
        if (IPAddressSeqRange.compareLowValues(otherUpper, upper) >= 0) {
            return this.createEmpty();
        }
        int comp = IPAddressSeqRange.compareLowValues(otherUpper, lower);
        if (comp < 0) {
            return this.createSingle();
        }
        return this.createSingle(otherUpper.increment(1L), upper);
    }

    protected abstract IPAddressSeqRange create(IPAddress var1, IPAddress var2);

    protected abstract IPAddressSeqRange[] createPair(IPAddress var1, IPAddress var2, IPAddress var3, IPAddress var4);

    protected abstract IPAddressSeqRange[] createSingle(IPAddress var1, IPAddress var2);

    protected abstract IPAddressSeqRange[] createSingle();

    protected abstract IPAddressSeqRange[] createEmpty();

    @Override
    public boolean containsPrefixBlock(int prefixLen) {
        IPAddressSection.checkSubnet(this.lower, prefixLen);
        int divCount = this.lower.getDivisionCount();
        int bitsPerSegment = this.lower.getBitsPerSegment();
        int i = IPAddressSeqRange.getHostSegmentIndex(prefixLen, this.lower.getBytesPerSegment(), bitsPerSegment);
        if (i < divCount) {
            AddressSegment div = this.lower.getSegment(i);
            AddressSegment upperDiv = this.upper.getSegment(i);
            int segmentPrefixLength = IPAddressSection.getPrefixedSegmentPrefixLength(bitsPerSegment, prefixLen, i);
            if (!((IPAddressSegment)div).containsPrefixBlock(((IPAddressSegment)div).getSegmentValue(), ((IPAddressSegment)upperDiv).getSegmentValue(), segmentPrefixLength)) {
                return false;
            }
            ++i;
            while (i < divCount) {
                div = this.lower.getSegment(i);
                upperDiv = this.upper.getSegment(i);
                if (!((IPAddressSegment)div).includesZero() || !((IPAddressSegment)upperDiv).includesMax()) {
                    return false;
                }
                ++i;
            }
        }
        return true;
    }

    @Override
    public boolean containsSinglePrefixBlock(int prefixLen) {
        IPAddressSection.checkSubnet(this.lower, prefixLen);
        int prevBitCount = 0;
        int divCount = this.lower.getDivisionCount();
        for (int i = 0; i < divCount; ++i) {
            AddressSegment div = this.lower.getSegment(i);
            AddressSegment upperDiv = this.upper.getSegment(i);
            int bitCount = div.getBitCount();
            int totalBitCount = bitCount + prevBitCount;
            if (prefixLen >= totalBitCount) {
                if (!((IPAddressSegment)div).isSameValues(upperDiv)) {
                    return false;
                }
            } else {
                int divPrefixLen = Math.max(0, prefixLen - prevBitCount);
                if (!((IPAddressSegment)div).containsSinglePrefixBlock(((IPAddressSegment)div).getSegmentValue(), ((IPAddressSegment)upperDiv).getSegmentValue(), divPrefixLen)) {
                    return false;
                }
                ++i;
                while (i < divCount) {
                    div = this.lower.getSegment(i);
                    upperDiv = this.upper.getSegment(i);
                    if (!((IPAddressSegment)div).includesZero() || !((IPAddressSegment)upperDiv).includesMax()) {
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
    public int getBitCount() {
        return this.getLower().getBitCount();
    }

    @Override
    public byte[] getBytes() {
        return this.getLower().getBytes();
    }

    @Override
    public byte[] getBytes(byte[] bytes) {
        return this.getLower().getBytes(bytes);
    }

    @Override
    public byte[] getBytes(byte[] bytes, int index) {
        return this.getLower().getBytes(bytes, index);
    }

    @Override
    public byte[] getUpperBytes() {
        return this.getUpper().getUpperBytes();
    }

    @Override
    public byte[] getUpperBytes(byte[] bytes) {
        return this.getUpper().getUpperBytes(bytes);
    }

    @Override
    public byte[] getUpperBytes(byte[] bytes, int index) {
        return this.getUpper().getUpperBytes(bytes, index);
    }

    @Override
    public BigInteger getValue() {
        return this.getLower().getValue();
    }

    @Override
    public BigInteger getUpperValue() {
        return this.getUpper().getValue();
    }

    @Override
    public boolean isZero() {
        return this.includesZero() && !this.isMultiple();
    }

    @Override
    public boolean includesZero() {
        return this.getLower().isZero();
    }

    @Override
    public boolean isMax() {
        return this.includesMax() && !this.isMultiple();
    }

    @Override
    public boolean includesMax() {
        return this.getUpper().isMax();
    }

    @FunctionalInterface
    protected static interface SegValueComparator<T> {
        public boolean apply(T var1, T var2, int var3);
    }

    @FunctionalInterface
    protected static interface IPAddressSeqRangeIteratorProvider<S, T>
    extends IPAddressSection.SeqRangeIteratorProvider<S, T> {
    }

    protected static interface IPAddressSeqRangeSplitterSink<S, T> {
        public void setSplitValues(S var1, S var2);

        public S getAddressItem();
    }
}

