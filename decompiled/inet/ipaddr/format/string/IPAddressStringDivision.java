/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.string;

import inet.ipaddr.format.string.AddressStringDivision;
import inet.ipaddr.format.util.AddressSegmentParams;

public interface IPAddressStringDivision
extends AddressStringDivision {
    public int getBitCount();

    public Integer getDivisionPrefixLength();

    public boolean isFullRange();

    public boolean isPrefixBlock();

    public boolean isSinglePrefixBlock();

    public int getPrefixAdjustedRangeString(int var1, AddressSegmentParams var2, StringBuilder var3);
}

