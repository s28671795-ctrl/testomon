/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format;

import inet.ipaddr.AddressComponent;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.AddressItemRange;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public interface AddressComponentRange
extends AddressItem,
AddressItemRange {
    public AddressComponent getLower();

    public AddressComponent getUpper();

    public Iterable<? extends AddressComponent> getIterable();

    public Iterator<? extends AddressComponent> iterator();

    public AddressComponentRangeSpliterator<? extends AddressComponentRange, ? extends AddressComponent> spliterator();

    public Stream<? extends AddressComponent> stream();

    @SafeVarargs
    public static <T extends AddressComponent> Stream<T> stream(Function<T, Stream<? extends T>> addrStreamFunc, T ... components) {
        return Arrays.stream(components).map(addrStreamFunc).flatMap(s2 -> s2);
    }

    public static <T extends AddressComponent> Stream<T> stream(Function<T, Stream<? extends T>> addrStreamFunc, Collection<? extends T> components) {
        return components.stream().map(addrStreamFunc).flatMap(s2 -> s2);
    }
}

