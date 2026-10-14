/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.MACAddressStringParameters;
import inet.ipaddr.format.validate.HostIdentifierStringValidator;
import inet.ipaddr.format.validate.MACAddressProvider;
import inet.ipaddr.format.validate.Validator;
import inet.ipaddr.mac.MACAddress;
import java.util.Iterator;

public class MACAddressString
implements HostIdentifierString,
Comparable<MACAddressString> {
    private static final long serialVersionUID = 4L;
    private static final MACAddressStringParameters DEFAULT_BASIC_VALIDATION_OPTIONS = new MACAddressStringParameters.Builder().toParams();
    public static final MACAddressString EMPTY_ADDRESS = new MACAddressString("");
    public static final MACAddressString ALL_ADDRESSES = new MACAddressString(IPAddress.SEGMENT_WILDCARD_STR);
    final MACAddressStringParameters validationOptions;
    final String fullAddr;
    private AddressStringException cachedException;
    private MACAddressProvider parsedAddress;
    private Boolean isValid;

    public MACAddressString(String addr) {
        this(addr, DEFAULT_BASIC_VALIDATION_OPTIONS);
    }

    public MACAddressString(String addr, MACAddressStringParameters valOptions) {
        if (addr == null) {
            addr = "";
            this.fullAddr = "";
        } else {
            this.fullAddr = addr = addr.trim();
        }
        this.validationOptions = valOptions;
    }

    public MACAddressString(MACAddress address) {
        this.validationOptions = null;
        this.fullAddr = address.toNormalizedString();
        this.initByAddress(address);
    }

    void cacheAddress(MACAddress address) {
        this.initByAddress(address);
    }

    void initByAddress(MACAddress address) {
        this.parsedAddress = new MACAddressProvider.ParsedMACAddressProvider(address);
        this.isValid = true;
    }

    public MACAddressStringParameters getValidationOptions() {
        return this.validationOptions;
    }

    public boolean isPrefixed() {
        MACAddress addr = this.getAddress();
        return addr != null && addr.isPrefixed();
    }

    public Integer getPrefixLength() {
        MACAddress addr = this.getAddress();
        if (addr != null) {
            return addr.getPrefixLength();
        }
        return null;
    }

    public boolean isAllAddresses() {
        MACAddress addr = this.getAddress();
        return addr != null && addr.isAllAddresses();
    }

    public boolean isEmpty() {
        if (this.isValid()) {
            try {
                return this.parsedAddress.getAddress() == null;
            }
            catch (IncompatibleAddressException incompatibleAddressException) {
                // empty catch block
            }
        }
        return false;
    }

    public boolean isZero() {
        MACAddress value = this.getAddress();
        return value != null && value.isZero();
    }

    public boolean isValid() {
        if (this.isValid == null) {
            try {
                this.validate();
                return true;
            }
            catch (AddressStringException e) {
                return false;
            }
        }
        return this.isValid;
    }

    private boolean isValidated() throws AddressStringException {
        if (this.isValid != null) {
            if (this.cachedException != null) {
                throw this.cachedException;
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void validate() throws AddressStringException {
        if (this.isValidated()) {
            return;
        }
        MACAddressString mACAddressString = this;
        synchronized (mACAddressString) {
            if (this.isValidated()) {
                return;
            }
            try {
                this.parsedAddress = this.getValidator().validateAddress(this);
                this.isValid = true;
            }
            catch (AddressStringException e) {
                this.cachedException = e;
                this.isValid = false;
                throw e;
            }
        }
    }

    protected HostIdentifierStringValidator getValidator() {
        return Validator.VALIDATOR;
    }

    public int hashCode() {
        if (this.isValid() && !this.isEmpty()) {
            return this.getAddress().hashCode();
        }
        return this.toString().hashCode();
    }

    @Override
    public int compareTo(MACAddressString other) {
        if (this == other) {
            return 0;
        }
        if (this.isValid()) {
            if (other.isValid()) {
                MACAddress otherAddr;
                MACAddress addr = this.getAddress();
                if (addr != null && (otherAddr = other.getAddress()) != null) {
                    return addr.compareTo(otherAddr);
                }
                return this.toString().compareTo(other.toString());
            }
            return 1;
        }
        if (other.isValid()) {
            return -1;
        }
        return this.toString().compareTo(other.toString());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof MACAddressString) {
            MACAddressString other = (MACAddressString)o;
            boolean stringsMatch = this.toString().equals(other.toString());
            if (stringsMatch && this.validationOptions == other.validationOptions) {
                return true;
            }
            if (this.isValid()) {
                if (other.isValid()) {
                    MACAddress value = this.getAddress();
                    if (value != null) {
                        MACAddress otherValue = other.getAddress();
                        if (otherValue != null) {
                            return value.equals(otherValue);
                        }
                        return false;
                    }
                    if (other.getAddress() != null) {
                        return false;
                    }
                    return stringsMatch;
                }
            } else if (!other.isValid()) {
                return stringsMatch;
            }
        }
        return false;
    }

    @Override
    public MACAddress getAddress() {
        if (this.isValid()) {
            try {
                return this.parsedAddress.getAddress();
            }
            catch (IncompatibleAddressException incompatibleAddressException) {
                // empty catch block
            }
        }
        return null;
    }

    @Override
    public MACAddress toAddress() throws AddressStringException, IncompatibleAddressException {
        this.validate();
        return this.parsedAddress.getAddress();
    }

    @Override
    public String toNormalizedString() {
        MACAddress addr = this.getAddress();
        if (addr != null) {
            return addr.toNormalizedString();
        }
        return this.toString();
    }

    @Override
    public String toString() {
        return this.fullAddr;
    }

    public static int countDelimitedAddresses(String str) {
        return IPAddressString.countDelimitedAddresses(str);
    }

    public static Iterator<String> parseDelimitedSegments(String str) {
        return IPAddressString.parseDelimitedSegments(str);
    }
}

