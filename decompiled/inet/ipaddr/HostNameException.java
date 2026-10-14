/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.HostIdentifierException;

public class HostNameException
extends HostIdentifierException {
    private static final long serialVersionUID = 4L;
    private static String errorMessage = HostNameException.getMessage("ipaddress.host.error");

    public HostNameException(String host2, int index) {
        super((CharSequence)(host2 + " " + errorMessage + " " + HostNameException.getMessage("ipaddress.host.error.invalid.character.at.index") + ' ' + index));
    }

    public HostNameException(String host2, int index, Throwable cause) {
        super((CharSequence)(host2 + " " + errorMessage + " " + HostNameException.getMessage("ipaddress.host.error.invalid.character.at.index") + ' ' + index), cause);
    }

    public HostNameException(String host2, String key) {
        super(host2, errorMessage, key);
    }

    public HostNameException(String host2, AddressStringException e, String key) {
        super((CharSequence)host2, errorMessage, key, e);
    }
}

