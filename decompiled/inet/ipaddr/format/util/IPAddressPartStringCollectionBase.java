/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.IPAddressPartConfiguredString;
import inet.ipaddr.format.util.IPAddressStringWriter;

abstract class IPAddressPartStringCollectionBase<T extends IPAddressStringDivisionSeries, P extends IPAddressStringWriter<?>, S extends IPAddressPartConfiguredString<?, ?>>
implements Iterable<S> {
    IPAddressPartStringCollectionBase() {
    }

    protected abstract int size();

    public String[] toStrings() {
        String[] strings = new String[this.size()];
        int i = 0;
        for (IPAddressPartConfiguredString createdString : this) {
            strings[i++] = createdString.getString();
        }
        return strings;
    }
}

