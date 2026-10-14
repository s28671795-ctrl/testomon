/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.format.AddressComponentRange;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Spliterator;

public interface AddressComponentRangeSpliterator<S extends AddressComponentRange, T>
extends Spliterator<T> {
    public BigInteger getSize();

    public S getAddressItem();

    public AddressComponentRangeSpliterator<S, T> trySplit();

    @Override
    default public Comparator<? super T> getComparator() {
        return null;
    }
}

