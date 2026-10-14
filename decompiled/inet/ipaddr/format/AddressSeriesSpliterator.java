/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format;

import inet.ipaddr.AddressComponent;
import inet.ipaddr.format.AddressDivisionGroupingBase;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import java.math.BigInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;

class AddressSeriesSpliterator<T extends AddressComponent>
extends AddressDivisionGroupingBase.AddressItemRangeSpliterator<T, T>
implements AddressComponentSpliterator<T> {
    AddressSeriesSpliterator(T forIteration, Predicate<AddressDivisionGroupingBase.SplitterSink<T, T>> splitter, AddressDivisionGroupingBase.IteratorProvider<T, T> iteratorProvider, Function<T, BigInteger> sizer, Predicate<T> downSizer, ToLongFunction<T> longSizer) {
        super(forIteration, splitter, iteratorProvider, sizer, downSizer, longSizer);
    }

    AddressSeriesSpliterator(T forIteration, Predicate<AddressDivisionGroupingBase.SplitterSink<T, T>> splitter, AddressDivisionGroupingBase.IteratorProvider<T, T> iteratorProvider, boolean isLowest, Function<T, BigInteger> sizer, Predicate<T> downSizer, ToLongFunction<T> longSizer) {
        super(forIteration, splitter, iteratorProvider, isLowest, false, sizer, downSizer, longSizer);
    }

    protected AddressSeriesSpliterator<T> createSpliterator(T split, boolean isLowest, Function<T, BigInteger> sizer, Predicate<T> downSizer, ToLongFunction<T> longSizer) {
        return new AddressSeriesSpliterator<T>(split, this.splitter, this.iteratorProvider, isLowest, sizer, downSizer, longSizer);
    }

    @Override
    public AddressSeriesSpliterator<T> trySplit() {
        return (AddressSeriesSpliterator)super.trySplit();
    }
}

