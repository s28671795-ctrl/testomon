/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format;

import inet.ipaddr.AddressSegment;
import inet.ipaddr.format.AddressDivisionBase;
import inet.ipaddr.format.SpliteratorBase;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

class AddressSegmentSpliterator<T extends AddressSegment>
extends SpliteratorBase<T, T>
implements AddressComponentSpliterator<T> {
    private Iterator<T> iterator;
    private T splitForIteration;
    private T currentForIteration;
    private int value;
    private int upperValue;
    private Supplier<Iterator<T>> iteratorProvider;
    protected boolean isLowest;
    private final boolean isHighest;
    private final AddressDivisionBase.IntBinaryIteratorProvider<T> subIteratorProvider;
    private final AddressDivisionBase.SegmentCreator<T> itemProvider;

    AddressSegmentSpliterator(int value, int upperValue, Supplier<Iterator<T>> iteratorProvider, AddressDivisionBase.IntBinaryIteratorProvider<T> subIteratorProvider, AddressDivisionBase.SegmentCreator<T> itemProvider) {
        this(null, value, upperValue, iteratorProvider, subIteratorProvider, itemProvider);
    }

    AddressSegmentSpliterator(T splitForIteration, int value, int upperValue, Supplier<Iterator<T>> iteratorProvider, AddressDivisionBase.IntBinaryIteratorProvider<T> subIteratorProvider, AddressDivisionBase.SegmentCreator<T> itemProvider) {
        this(value, upperValue, iteratorProvider, subIteratorProvider, true, true, itemProvider);
        this.splitForIteration = splitForIteration;
    }

    private AddressSegmentSpliterator(int value, int upperValue, Supplier<Iterator<T>> iteratorProvider, AddressDivisionBase.IntBinaryIteratorProvider<T> subIteratorProvider, boolean isLowest, boolean isHighest, AddressDivisionBase.SegmentCreator<T> itemProvider) {
        this.iteratorProvider = iteratorProvider;
        this.subIteratorProvider = subIteratorProvider;
        this.isLowest = isLowest;
        this.isHighest = isHighest;
        this.itemProvider = itemProvider;
        this.value = value;
        this.upperValue = upperValue;
    }

    private int getCurrentValue() {
        return this.value + (int)this.iteratedCountL;
    }

    @Override
    public BigInteger getSize() {
        return BigInteger.valueOf(this.estimateSize());
    }

    @Override
    public long estimateSize() {
        return (long)this.upperValue - (long)this.getCurrentValue() + 1L;
    }

    public T getCurrentItem() {
        if (this.estimateSize() == 0L) {
            return null;
        }
        T item = this.currentForIteration;
        if (item == null) {
            this.currentForIteration = item = this.itemProvider.applyAsInt(this.getCurrentValue(), this.upperValue);
        }
        return item;
    }

    @Override
    public T getAddressItem() {
        T item = this.splitForIteration;
        if (item == null) {
            this.splitForIteration = item = this.itemProvider.applyAsInt(this.value, this.upperValue);
        }
        return item;
    }

    private Iterator<T> provideIterator() {
        if (this.iterator == null) {
            this.iterator = this.iteratorProvider != null ? this.iteratorProvider.get() : this.subIteratorProvider.applyAsInt(this.isLowest, this.isHighest, this.value, this.upperValue);
        }
        return this.iterator;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (!this.inForEach && this.getCurrentValue() < this.upperValue) {
            this.currentForIteration = null;
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
            this.currentForIteration = null;
            this.forEachRemaining(this.provideIterator(), action, this.upperValue - this.value + 1);
        }
        finally {
            this.inForEach = false;
        }
    }

    @Override
    public AddressComponentSpliterator<T> trySplit() {
        if (this.inForEach) {
            return null;
        }
        int lower = this.getCurrentValue();
        int size = this.upperValue - lower;
        if (size <= 1) {
            return null;
        }
        this.splitForIteration = null;
        this.currentForIteration = null;
        this.iteratorProvider = null;
        int mid = lower + (size >>> 1);
        this.value = mid + 1;
        this.iteratedCountL = 0L;
        AddressSegmentSpliterator<T> result = new AddressSegmentSpliterator<T>(lower, mid, null, this.subIteratorProvider, this.isLowest, false, this.itemProvider);
        result.iterator = this.iterator;
        this.isLowest = false;
        this.iterator = null;
        return result;
    }
}

