/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format;

import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.format.AddressComponentRange;
import inet.ipaddr.format.util.AddressComponentRangeSpliterator;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import java.util.Iterator;
import java.util.stream.Stream;

public interface IPAddressRange
extends AddressComponentRange {
    public boolean contains(IPAddressSeqRange var1);

    public boolean contains(IPAddress var1);

    @Override
    public IPAddress getLower();

    @Override
    public IPAddress getUpper();

    public Iterable<? extends IPAddress> getIterable();

    public Iterator<? extends IPAddress> iterator();

    public AddressComponentRangeSpliterator<? extends IPAddressRange, ? extends IPAddress> spliterator();

    public Iterator<? extends IPAddress> prefixBlockIterator(int var1);

    public AddressComponentRangeSpliterator<? extends IPAddressRange, ? extends IPAddress> prefixBlockSpliterator(int var1);

    public Stream<? extends IPAddress> prefixBlockStream(int var1);

    public Iterator<? extends IPAddressRange> prefixIterator(int var1);

    public AddressComponentSpliterator<? extends IPAddressRange> prefixSpliterator(int var1);

    public Stream<? extends IPAddressRange> prefixStream(int var1);

    public IPAddress coverWithPrefixBlock();

    public IPAddress[] spanWithPrefixBlocks();

    public IPAddress[] spanWithSequentialBlocks();

    public boolean isSequential();

    public IPAddressSeqRange toSequentialRange();

    public String toNormalizedString();

    public String toCanonicalString();
}

