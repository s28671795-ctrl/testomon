/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressComponent;
import inet.ipaddr.format.AddressGenericDivision;
import inet.ipaddr.format.util.AddressComponentSpliterator;
import java.util.Iterator;
import java.util.stream.Stream;

public interface AddressSegment
extends AddressComponent,
AddressGenericDivision {
    public int getValueCount();

    public int getPrefixValueCount(int var1);

    public int getSegmentValue();

    public int getUpperSegmentValue();

    @Override
    public AddressSegment getLower();

    @Override
    public AddressSegment getUpper();

    @Override
    public AddressSegment reverseBits(boolean var1);

    @Override
    public AddressSegment reverseBytes();

    public Iterable<? extends AddressSegment> getIterable();

    public Iterator<? extends AddressSegment> iterator();

    @Override
    public AddressComponentSpliterator<? extends AddressSegment> spliterator();

    public Stream<? extends AddressSegment> stream();

    public boolean matches(int var1);

    public boolean matchesWithMask(int var1, int var2);

    public boolean matchesWithMask(int var1, int var2, int var3);

    public boolean contains(AddressSegment var1);

    public boolean equals(Object var1);

    public boolean prefixEquals(AddressSegment var1, int var2);

    default public boolean testBit(int n) {
        int value = this.getSegmentValue();
        int bitCount = this.getBitCount();
        if (n < 0 || n >= bitCount) {
            throw new IndexOutOfBoundsException();
        }
        return (value & 1 << n) != 0;
    }

    default public boolean isOneBit(int segmentBitIndex) {
        int value = this.getSegmentValue();
        int bitCount = this.getBitCount();
        if (segmentBitIndex < 0 || segmentBitIndex >= bitCount) {
            throw new IndexOutOfBoundsException();
        }
        return (value & 1 << bitCount - (segmentBitIndex + 1)) != 0;
    }

    public int getMaxSegmentValue();
}

