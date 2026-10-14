/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.format.AddressItem;

public class InconsistentPrefixException
extends AddressValueException {
    private static final long serialVersionUID = 1L;

    static String getMessage(String key) {
        return AddressStringException.getMessage(key);
    }

    public InconsistentPrefixException(AddressItem one, AddressItem two, Integer prefixLength) {
        super(one + ", " + two + ", " + prefixLength + ", " + errorMessage + " " + InconsistentPrefixException.getMessage("ipaddress.error.inconsistent.prefixes"));
    }
}

