/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.format.AddressItem;

public class AddressConversionException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static String errorMessage = AddressConversionException.getMessage("ipaddress.address.error");

    static String getMessage(String key) {
        return AddressStringException.getMessage(key);
    }

    public AddressConversionException(AddressItem one) {
        super(one + ", " + errorMessage + " " + AddressConversionException.getMessage("ipaddress.error.version.mismatch"));
    }

    public AddressConversionException(AddressItem one, AddressItem two) {
        super(one + ", " + two + ", " + errorMessage + " " + AddressConversionException.getMessage("ipaddress.error.version.mismatch"));
    }

    public AddressConversionException(AddressItem one, AddressItem two, String key) {
        super(one + ", " + two + ", " + errorMessage + " " + AddressConversionException.getMessage(key));
    }
}

