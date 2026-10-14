/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.format.AddressItem;

public class IncompatibleAddressException
extends RuntimeException {
    private static final long serialVersionUID = 4L;
    private static String errorMessage = IncompatibleAddressException.getMessage("ipaddress.address.error");

    static String getMessage(String key) {
        return AddressStringException.getMessage(key);
    }

    public IncompatibleAddressException(AddressItem one, String key) {
        super(one + ", " + errorMessage + " " + IncompatibleAddressException.getMessage(key));
    }

    public IncompatibleAddressException(CharSequence one, String key) {
        super(one + ", " + errorMessage + " " + IncompatibleAddressException.getMessage(key));
    }

    public IncompatibleAddressException(long lower, long upper, String key) {
        super(lower + "-" + upper + ", " + errorMessage + " " + IncompatibleAddressException.getMessage(key));
    }

    public IncompatibleAddressException(long lower, long upper, long prefixLengthOrMask, String key) {
        super(lower + "-" + upper + " /" + prefixLengthOrMask + ", " + errorMessage + " " + IncompatibleAddressException.getMessage(key));
    }

    public IncompatibleAddressException(String lower, String upper, String prefixLengthOrMask, String key) {
        super(lower + "-" + upper + " /" + prefixLengthOrMask + ", " + errorMessage + " " + IncompatibleAddressException.getMessage(key));
    }

    public IncompatibleAddressException(AddressItem one, int prefixLengthOrMask, String key) {
        super(one + " /" + prefixLengthOrMask + ", " + errorMessage + " " + IncompatibleAddressException.getMessage(key));
    }

    public IncompatibleAddressException(AddressItem one, int oneIndex, AddressItem two, int twoIndex, String key) {
        super(oneIndex + 1 + ":" + one + ", " + (twoIndex + 1) + ":" + two + ", " + errorMessage + " " + IncompatibleAddressException.getMessage(key));
    }

    public IncompatibleAddressException(AddressItem one, AddressItem two, String key) {
        super(one + ", " + two + ", " + errorMessage + " " + IncompatibleAddressException.getMessage(key));
    }
}

