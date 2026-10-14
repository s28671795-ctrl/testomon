/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format;

import inet.ipaddr.format.AddressGenericDivision;
import inet.ipaddr.format.string.IPAddressStringDivision;

public interface IPAddressGenericDivision
extends AddressGenericDivision,
IPAddressStringDivision {
    @Override
    default public boolean isFullRange() {
        return AddressGenericDivision.super.isFullRange();
    }

    public boolean isPrefixed();
}

