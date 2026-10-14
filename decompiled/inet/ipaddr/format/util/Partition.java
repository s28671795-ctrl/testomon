/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.Address;
import inet.ipaddr.AddressSegmentSeries;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSegmentSeries;
import inet.ipaddr.format.util.AbstractTree;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Partition<E> {
    public final E original;
    public final E single;
    public final Iterator<? extends E> blocks;
    public final BigInteger count;

    public Partition(E original) {
        this.count = BigInteger.ONE;
        this.single = original;
        this.blocks = null;
        this.original = original;
    }

    public Partition(E original, E single) {
        this.count = BigInteger.ONE;
        this.single = single;
        this.blocks = null;
        this.original = original;
    }

    public Partition(E original, Iterator<? extends E> blocks, BigInteger count) {
        this.count = count;
        this.single = null;
        this.blocks = blocks;
        this.original = original;
    }

    public Partition(E original, Iterator<? extends E> blocks, int count) {
        this.count = BigInteger.valueOf(count);
        this.single = null;
        this.blocks = blocks;
        this.original = original;
    }

    public <R> Map<E, R> applyForEach(Function<? super E, ? extends R> func) {
        TreeMap results = new TreeMap();
        this.forEach(address -> {
            Object result = func.apply((Object)address);
            if (result != null) {
                results.put(address, result);
            }
        });
        return results;
    }

    public void forEach(Consumer<? super E> action) {
        if (this.blocks == null) {
            action.accept(this.single);
        } else {
            Iterator<E> iterator2 = this.blocks;
            while (iterator2.hasNext()) {
                action.accept(iterator2.next());
            }
        }
    }

    public boolean predicateForEach(Predicate<? super E> predicate) {
        return this.predicateForEach(predicate, false);
    }

    public boolean predicateForEach(Predicate<? super E> predicate, boolean returnEarly) {
        if (this.blocks == null) {
            return predicate.test(this.single);
        }
        boolean result = true;
        Iterator<E> iterator2 = this.blocks;
        while (iterator2.hasNext()) {
            if (predicate.test(iterator2.next())) continue;
            result = false;
            if (!returnEarly) continue;
            break;
        }
        return result;
    }

    public boolean predicateForAny(Predicate<? super E> predicate, boolean returnEarly) {
        return !this.predicateForEach(addr -> !predicate.test((Object)addr), returnEarly);
    }

    public boolean predicateForAny(Predicate<? super E> predicate) {
        return this.predicateForAny(predicate, false);
    }

    public static <E extends IPAddress> Partition<E> partitionWithSpanningBlocks(E newAddr) {
        if (!newAddr.isMultiple()) {
            if (!newAddr.isPrefixed()) {
                return new Partition<E>(newAddr);
            }
            return new Partition<IPAddress>(newAddr, newAddr.withoutPrefixLength());
        }
        if (newAddr.isSinglePrefixBlock()) {
            return new Partition<E>(newAddr);
        }
        IPAddress[] blocks = newAddr.spanWithPrefixBlocks();
        Iterator<IPAddress> blocksIterator = Arrays.asList(blocks).iterator();
        return new Partition<IPAddress>(newAddr, blocksIterator, blocks.length);
    }

    public static <E extends AddressSegmentSeries> Partition<E> partitionWithSingleBlockSize(E newAddr) {
        if (!newAddr.isMultiple()) {
            if (!newAddr.isPrefixed()) {
                return new Partition<E>(newAddr);
            }
            return new Partition<AddressSegmentSeries>(newAddr, newAddr.withoutPrefixLength());
        }
        if (newAddr.isSinglePrefixBlock()) {
            return new Partition<E>(newAddr);
        }
        if (newAddr instanceof IPAddressSegmentSeries) {
            IPAddressSegmentSeries series = ((IPAddressSegmentSeries)newAddr).assignMinPrefixForBlock();
            if (series.getPrefixLength().intValue() != newAddr.getBitCount()) {
                Iterator<? extends IPAddressSegmentSeries> iterator2 = series.prefixBlockIterator();
                return new Partition<IPAddressSegmentSeries>((IPAddressSegmentSeries)newAddr, iterator2, series.getPrefixCount(series.getPrefixLength()));
            }
        } else {
            int prefLen = newAddr.getMinPrefixLengthForBlock();
            if (prefLen != newAddr.getBitCount()) {
                AddressSegmentSeries series = newAddr.setPrefixLength(prefLen, false);
                Iterator<? extends AddressSegmentSeries> iterator3 = series.prefixBlockIterator();
                return new Partition<AddressSegmentSeries>(newAddr, iterator3, series.getPrefixCount(series.getPrefixLength()));
            }
        }
        Iterator<? extends AddressSegmentSeries> iterator4 = newAddr.withoutPrefixLength().iterator();
        return new Partition<AddressSegmentSeries>(newAddr, iterator4, newAddr.getCount());
    }

    public static <E extends Address> E checkBlockOrAddress(E addr) {
        return AbstractTree.checkBlockOrAddress(addr, false);
    }
}

