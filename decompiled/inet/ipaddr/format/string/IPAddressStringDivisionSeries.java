/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.string;

import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.format.string.AddressStringDivisionSeries;
import inet.ipaddr.format.string.IPAddressStringDivision;

public interface IPAddressStringDivisionSeries
extends AddressStringDivisionSeries {
    public IPAddressNetwork<?, ?, ?, ?, ?> getNetwork();

    @Override
    public IPAddressStringDivision getDivision(int var1);

    public boolean isPrefixBlock();

    public boolean isPrefixed();

    public Integer getPrefixLength();
}

