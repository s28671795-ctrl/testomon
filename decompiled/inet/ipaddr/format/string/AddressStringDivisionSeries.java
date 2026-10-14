/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.string;

import inet.ipaddr.format.string.AddressStringDivision;
import java.io.Serializable;

public interface AddressStringDivisionSeries
extends Serializable {
    public AddressStringDivision getDivision(int var1);

    public int getDivisionCount();
}

