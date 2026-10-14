/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.format.AddressItem;
import java.math.BigInteger;

public class AddressValueException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    static String errorMessage = AddressValueException.getMessage("ipaddress.address.error");

    static String getMessage(String key) {
        return AddressStringException.getMessage(key);
    }

    public AddressValueException(long value) {
        super(value + ", " + errorMessage + " " + AddressValueException.getMessage("ipaddress.error.exceeds.size"));
    }

    public AddressValueException(String key, long value) {
        super(value + ", " + errorMessage + " " + AddressValueException.getMessage(key));
    }

    public AddressValueException(BigInteger value) {
        super(value + ", " + errorMessage + " " + AddressValueException.getMessage("ipaddress.error.exceeds.size"));
    }

    public AddressValueException(AddressItem one, AddressItem two, int count) {
        super(count + ", " + one + ", " + two + ", " + errorMessage + " " + AddressValueException.getMessage("ipaddress.error.exceeds.size"));
    }

    public AddressValueException(AddressItem one, AddressItem two) {
        super(one + ", " + two + ", " + errorMessage + " " + AddressValueException.getMessage("ipaddress.error.exceeds.size"));
    }

    public AddressValueException(AddressItem one, String key) {
        super(one + ", " + errorMessage + " " + AddressValueException.getMessage(key));
    }

    AddressValueException(String message) {
        super(message);
    }

    AddressValueException(String message, Throwable cause) {
        super(message, cause);
    }
}

