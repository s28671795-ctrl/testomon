/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.HostIdentifierException;

public class AddressStringException
extends HostIdentifierException {
    private static final long serialVersionUID = 4L;
    private static final String errorMessage = AddressStringException.getMessage("ipaddress.address.error");

    public AddressStringException(CharSequence str, String key, Throwable cause) {
        super(str, errorMessage, key, cause);
    }

    public AddressStringException(CharSequence str, String key) {
        super(str, errorMessage, key);
    }

    public AddressStringException(CharSequence str, String key, int characterIndex) {
        super((CharSequence)(str.toString() + ' ' + errorMessage + ' ' + AddressStringException.getMessage(key) + ' ' + characterIndex));
    }

    public AddressStringException(CharSequence str, int characterIndex) {
        this(str, characterIndex, false);
    }

    public AddressStringException(CharSequence str, int characterIndex, boolean combo) {
        super((CharSequence)(str.toString() + ' ' + errorMessage + ' ' + AddressStringException.getMessage(combo ? "ipaddress.error.invalid.character.combination.at.index" : "ipaddress.error.invalid.character.at.index") + ' ' + characterIndex));
    }

    public AddressStringException(String key) {
        super((CharSequence)errorMessage, key);
    }
}

