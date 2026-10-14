/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.format.AddressComponentRange;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;

public interface AddressComponentSpliterator<T extends AddressComponentRange>
extends AddressComponentRangeSpliterator<T, T> {
    @Override
    public AddressComponentSpliterator<T> trySplit();
}

