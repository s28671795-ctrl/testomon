/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressNetwork;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.format.AddressComponentRange;
import inet.ipaddr.format.util.AddressComponentSpliterator;

public interface AddressComponent
extends AddressComponentRange {
    public AddressNetwork<?> getNetwork();

    public String toHexString(boolean var1) throws IncompatibleAddressException;

    public String toNormalizedString();

    public AddressComponent reverseBits(boolean var1);

    public AddressComponent reverseBytes();

    public AddressComponentSpliterator<? extends AddressComponent> spliterator();
}

