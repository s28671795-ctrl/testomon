/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.format.AddressItem;

public class AddressPositionException
extends AddressValueException {
    private static final long serialVersionUID = 1L;

    static String getMessage(String key) {
        return AddressStringException.getMessage(key);
    }

    public AddressPositionException(int position) {
        super(position + ", " + errorMessage + " " + AddressPositionException.getMessage("ipaddress.error.invalid.position"));
    }

    public AddressPositionException(AddressItem item, int position) {
        super(item + ", " + position + ", " + errorMessage + " " + AddressPositionException.getMessage("ipaddress.error.invalid.position"));
    }

    public AddressPositionException(AddressItem item, int position, int otherPosition) {
        super(item + ", " + position + ", " + otherPosition + ", " + errorMessage + " " + AddressPositionException.getMessage("ipaddress.error.invalid.position"));
    }
}

