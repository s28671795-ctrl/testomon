/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.format.AddressItem;

public class NetworkMismatchException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static String errorMessage = NetworkMismatchException.getMessage("ipaddress.address.error");

    static String getMessage(String key) {
        return AddressStringException.getMessage(key);
    }

    public NetworkMismatchException(AddressItem one) {
        super(one + ", " + errorMessage + " " + NetworkMismatchException.getMessage("ipaddress.error.mixedNetworks"));
    }

    public NetworkMismatchException(AddressItem one, AddressItem two) {
        super(one + ", " + two + ", " + errorMessage + " " + NetworkMismatchException.getMessage("ipaddress.error.mixedNetworks"));
    }
}

