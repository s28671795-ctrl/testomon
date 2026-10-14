/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.IPAddressStringWriter;
import inet.ipaddr.format.util.sql.IPAddressSQLTranslator;
import inet.ipaddr.format.util.sql.SQLStringMatcher;

public class IPAddressPartConfiguredString<T extends IPAddressStringDivisionSeries, P extends IPAddressStringWriter<T>> {
    public final T addr;
    public final P stringParams;
    protected String string;

    public IPAddressPartConfiguredString(T addr, P stringParams) {
        this.stringParams = stringParams;
        this.addr = addr;
    }

    public int getTrailingSeparatorCount() {
        return this.stringParams.getTrailingSeparatorCount(this.addr);
    }

    public char getTrailingSegmentSeparator() {
        return this.stringParams.getTrailingSegmentSeparator();
    }

    public <S extends IPAddressPartConfiguredString<T, P>> SQLStringMatcher<T, P, S> getNetworkStringMatcher(boolean isEntireAddress, IPAddressSQLTranslator translator) {
        return new SQLStringMatcher(this, isEntireAddress, translator);
    }

    public String getString() {
        if (this.string == null) {
            this.string = this.stringParams.toString(this.addr);
        }
        return this.string;
    }

    public String toString() {
        return this.getString();
    }
}

