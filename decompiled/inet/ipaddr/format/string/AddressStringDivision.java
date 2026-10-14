/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.string;

import inet.ipaddr.format.util.AddressSegmentParams;

public interface AddressStringDivision {
    public boolean isBoundedBy(int var1);

    public int getDigitCount(int var1);

    public int getMaxDigitCount(int var1);

    public int getStandardString(int var1, AddressSegmentParams var2, StringBuilder var3);

    public int getLowerStandardString(int var1, AddressSegmentParams var2, StringBuilder var3);
}

