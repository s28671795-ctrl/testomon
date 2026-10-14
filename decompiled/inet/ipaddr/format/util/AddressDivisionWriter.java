/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.format.string.AddressStringDivision;

public interface AddressDivisionWriter {
    public StringBuilder appendDivision(StringBuilder var1, AddressStringDivision var2);

    public int getDivisionStringLength(AddressStringDivision var1);
}

