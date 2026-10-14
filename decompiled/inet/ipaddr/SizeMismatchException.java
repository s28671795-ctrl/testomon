/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.format.AddressItem;

public class SizeMismatchException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static String errorMessage = SizeMismatchException.getMessage("ipaddress.address.error");

    static String getMessage(String key) {
        return AddressStringException.getMessage(key);
    }

    public SizeMismatchException(AddressItem one, AddressItem two) {
        super(one + ", " + two + ", " + errorMessage + " " + SizeMismatchException.getMessage("ipaddress.error.sizeMismatch"));
    }
}

