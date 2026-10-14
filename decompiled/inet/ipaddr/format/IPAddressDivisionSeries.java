/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format;

import inet.ipaddr.format.AddressDivisionSeries;
import inet.ipaddr.format.IPAddressGenericDivision;
import inet.ipaddr.format.string.IPAddressStringDivisionSeries;

public interface IPAddressDivisionSeries
extends AddressDivisionSeries,
IPAddressStringDivisionSeries {
    public Integer getNetworkPrefixLength();

    @Override
    public IPAddressGenericDivision getDivision(int var1);
}

