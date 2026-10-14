/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.standard;

import inet.ipaddr.Address;
import inet.ipaddr.AddressNetwork;
import inet.ipaddr.AddressSection;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.format.validate.ParsedAddressCreator;

public abstract class AddressCreator<T extends Address, R extends AddressSection, E extends AddressSection, S extends AddressSegment>
extends ParsedAddressCreator<T, R, E, S>
implements AddressNetwork.AddressSegmentCreator<S> {
    private static final long serialVersionUID = 4L;

    public abstract AddressNetwork<S> getNetwork();

    protected abstract T createAddressInternal(S[] var1);

    protected abstract T createAddressInternal(S[] var1, Integer var2);

    protected abstract T createAddressInternal(S[] var1, Integer var2, boolean var3);

    protected abstract R createSectionInternal(byte[] var1, int var2, Integer var3, boolean var4);

    @Override
    protected abstract R createSectionInternal(S[] var1);

    @Override
    protected abstract R createPrefixedSectionInternal(S[] var1, Integer var2, boolean var3);

    protected abstract R createSectionInternal(S[] var1, int var2, boolean var3);

    public abstract T createAddress(R var1);
}

