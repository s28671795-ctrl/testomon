/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.AddressValueException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.format.AddressItem;

public class PrefixLenException
extends AddressValueException {
    private static final long serialVersionUID = 1L;

    static String getMessage(String key) {
        return AddressStringException.getMessage(key);
    }

    public PrefixLenException(int prefixLength) {
        super(errorMessage + " " + PrefixLenException.getMessage("ipaddress.error.prefixSize"));
    }

    public PrefixLenException(AddressItem one, int prefixLength) {
        super(one + ", " + errorMessage + " " + PrefixLenException.getMessage("ipaddress.error.prefixSize"));
    }

    public PrefixLenException(int prefixLength, IPAddress.IPVersion version) {
        super((Object)((Object)version) + " /" + prefixLength + ", " + errorMessage + " " + PrefixLenException.getMessage("ipaddress.error.prefixSize"));
    }

    public PrefixLenException(CharSequence prefixLength, IPAddress.IPVersion version, Throwable cause) {
        super((Object)((Object)version) + " /" + prefixLength + ", " + errorMessage + " " + PrefixLenException.getMessage("ipaddress.error.invalidCIDRPrefix"), cause);
    }
}

