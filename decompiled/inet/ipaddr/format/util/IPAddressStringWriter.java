/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.AddressDivisionWriter;

public interface IPAddressStringWriter<T extends IPAddressStringDivisionSeries>
extends AddressDivisionWriter {
    public int getTrailingSeparatorCount(T var1);

    public char getTrailingSegmentSeparator();

    public String toString(T var1);

    public String toString(T var1, CharSequence var2);
}

