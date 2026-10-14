/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.IPAddressStringParameters;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.PrefixLenException;
import inet.ipaddr.format.IPAddressDivisionSeries;
import inet.ipaddr.format.validate.HostIdentifierStringValidator;
import inet.ipaddr.format.validate.IPAddressProvider;
import inet.ipaddr.format.validate.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class IPAddressString
implements HostIdentifierString,
Comparable<IPAddressString> {
    private static final long serialVersionUID = 4L;
    public static final IPAddressStringParameters DEFAULT_VALIDATION_OPTIONS = new IPAddressStringParameters.Builder().toParams();
    private static final IPAddressString ipv4MappedPrefix = new IPAddressString("::ffff:0:0/96");
    final IPAddressStringParameters validationOptions;
    final String fullAddr;
    private AddressStringException validateException;
    private IPAddressProvider addressProvider = IPAddressProvider.NO_TYPE_PROVIDER;

    public IPAddressString(String addr) {
        this(addr, DEFAULT_VALIDATION_OPTIONS);
    }

    public IPAddressString(String addr, IPAddressStringParameters valOptions) {
        if (addr == null) {
            addr = "";
            this.fullAddr = "";
        } else {
            this.fullAddr = addr = addr.trim();
        }
        this.validationOptions = valOptions;
    }

    IPAddressString(String addrString, IPAddress address, IPAddressStringParameters valOptions) {
        this.validationOptions = valOptions;
        this.fullAddr = addrString;
        this.addressProvider = address.getProvider();
    }

    void cacheAddress(IPAddress address) {
        if (this.addressProvider.isUninitialized()) {
            this.addressProvider = address.getProvider();
        }
    }

    public IPAddressStringParameters getValidationOptions() {
        return this.validationOptions;
    }

    public boolean isPrefixed() {
        return this.getNetworkPrefixLength() != null;
    }

    public Integer getNetworkPrefixLength() {
        if (this.isValid()) {
            return this.addressProvider.getProviderNetworkPrefixLength();
        }
        return null;
    }

    public IPAddress getMask() {
        if (this.isValid()) {
            return this.addressProvider.getProviderMask();
        }
        return null;
    }

    public boolean isIPAddress() {
        return this.isValid() && this.addressProvider.isProvidingIPAddress();
    }

    public boolean isAllAddresses() {
        return this.isValid() && this.addressProvider.isProvidingAllAddresses();
    }

    public boolean isPrefixOnly() {
        return this.isValid() && this.addressProvider.isProvidingPrefixOnly();
    }

    public boolean isEmpty() {
        return this.isValid() && this.addressProvider.isProvidingEmpty();
    }

    public boolean isIPv4() {
        return this.isValid() && this.addressProvider.isProvidingIPv4();
    }

    public boolean isIPv6() {
        return this.isValid() && this.addressProvider.isProvidingIPv6();
    }

    public boolean isIPv4Mapped() {
        return this.isIPv6() && ipv4MappedPrefix.prefixEquals(this);
    }

    public boolean isMixedIPv6() {
        return this.isIPv6() && this.addressProvider.isProvidingMixedIPv6();
    }

    public boolean isBase85IPv6() {
        return this.isIPv6() && this.addressProvider.isProvidingBase85IPv6();
    }

    public IPAddress.IPVersion getIPVersion() {
        if (this.isValid()) {
            return this.addressProvider.getProviderIPVersion();
        }
        return null;
    }

    public boolean isLoopback() {
        IPAddress val = this.getAddress();
        return val != null && val.isLoopback();
    }

    public boolean isZero() {
        IPAddress value = this.getAddress();
        return value != null && value.isZero();
    }

    public boolean isValid() {
        if (this.addressProvider.isUninitialized()) {
            try {
                this.validate();
                return true;
            }
            catch (AddressStringException e) {
                return false;
            }
        }
        return !this.addressProvider.isInvalid();
    }

    public AddressStringException getAddressStringException() {
        if (!this.addressProvider.isInvalid()) {
            try {
                this.validate();
            }
            catch (AddressStringException e) {
                return e;
            }
        }
        return this.validateException;
    }

    public void validateIPv4() throws AddressStringException {
        this.validate(IPAddress.IPVersion.IPV4);
        this.checkIPv4Exception();
    }

    public void validateIPv6() throws AddressStringException {
        this.validate(IPAddress.IPVersion.IPV6);
        this.checkIPv6Exception();
    }

    @Override
    public void validate() throws AddressStringException {
        this.validate(null);
    }

    private void checkIPv4Exception() throws AddressStringException {
        IPAddress.IPVersion version = this.addressProvider.getProviderIPVersion();
        if (version != null && version.isIPv6()) {
            throw new AddressStringException("ipaddress.error.address.is.ipv6");
        }
        if (this.validateException != null) {
            throw this.validateException;
        }
    }

    private void checkIPv6Exception() throws AddressStringException {
        IPAddress.IPVersion version = this.addressProvider.getProviderIPVersion();
        if (version != null && version.isIPv4()) {
            throw new AddressStringException("ipaddress.error.address.is.ipv4");
        }
        if (this.validateException != null) {
            throw this.validateException;
        }
    }

    private boolean isValidated(IPAddress.IPVersion version) throws AddressStringException {
        if (!this.addressProvider.isUninitialized()) {
            if (version == null) {
                if (this.validateException != null) {
                    throw this.validateException;
                }
            } else if (version.isIPv4()) {
                this.checkIPv4Exception();
            } else if (version.isIPv6()) {
                this.checkIPv6Exception();
            }
            return true;
        }
        return false;
    }

    protected HostIdentifierStringValidator getValidator() {
        return Validator.VALIDATOR;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void validate(IPAddress.IPVersion version) throws AddressStringException {
        if (this.isValidated(version)) {
            return;
        }
        IPAddressString iPAddressString = this;
        synchronized (iPAddressString) {
            if (this.isValidated(version)) {
                return;
            }
            try {
                this.addressProvider = this.getValidator().validateAddress(this);
            }
            catch (AddressStringException e) {
                this.validateException = e;
                this.addressProvider = IPAddressProvider.INVALID_PROVIDER;
                throw e;
            }
        }
    }

    public static int validateNetworkPrefixLength(IPAddress.IPVersion ipVersion, CharSequence networkPrefixLength) throws PrefixLenException {
        try {
            return Validator.VALIDATOR.validatePrefix(networkPrefixLength, ipVersion);
        }
        catch (AddressStringException e) {
            throw new PrefixLenException(networkPrefixLength, ipVersion, e);
        }
    }

    public static void validateNetworkPrefix(IPAddress.IPVersion ipVersion, int networkPrefixLength, boolean allowPrefixesBeyondAddressSize) throws PrefixLenException {
        boolean asIPv4 = ipVersion != null && ipVersion.isIPv4();
        if (networkPrefixLength > (asIPv4 ? 32 : 128)) {
            throw new PrefixLenException(networkPrefixLength, ipVersion);
        }
    }

    public int hashCode() {
        if (this.isValid()) {
            try {
                return this.addressProvider.providerHashCode();
            }
            catch (IncompatibleAddressException incompatibleAddressException) {
                // empty catch block
            }
        }
        return this.toString().hashCode();
    }

    @Override
    public int compareTo(IPAddressString other) {
        if (this == other) {
            return 0;
        }
        boolean isValid = this.isValid();
        boolean otherIsValid = other.isValid();
        if (isValid || otherIsValid) {
            try {
                return this.addressProvider.providerCompare(other.addressProvider);
            }
            catch (IncompatibleAddressException incompatibleAddressException) {
                // empty catch block
            }
        }
        return this.toString().compareTo(other.toString());
    }

    public boolean prefixEquals(IPAddressString other) {
        Boolean directResult;
        if (other == this && !this.isPrefixOnly()) {
            return true;
        }
        if (!this.isValid()) {
            return false;
        }
        if (other.addressProvider.isUninitialized() && (directResult = this.addressProvider.prefixEquals(other.fullAddr)) != null) {
            return directResult;
        }
        if (other.isValid()) {
            IPAddress otherAddress;
            directResult = this.addressProvider.prefixEquals(other.addressProvider);
            if (directResult != null) {
                return directResult;
            }
            IPAddress thisAddress = this.getAddress();
            if (thisAddress != null && (otherAddress = other.getAddress()) != null) {
                return thisAddress.prefixEquals(otherAddress);
            }
        }
        return false;
    }

    public boolean prefixContains(IPAddressString other) {
        Boolean directResult;
        if (other == this && !this.isPrefixOnly()) {
            return true;
        }
        if (!this.isValid()) {
            return false;
        }
        if (other.addressProvider.isUninitialized() && (directResult = this.addressProvider.prefixContains(other.fullAddr)) != null) {
            return directResult;
        }
        if (other.isValid()) {
            IPAddress otherAddress;
            directResult = this.addressProvider.prefixContains(other.addressProvider);
            if (directResult != null) {
                return directResult;
            }
            IPAddress thisAddress = this.getAddress();
            if (thisAddress != null && (otherAddress = other.getAddress()) != null) {
                return thisAddress.prefixContains(otherAddress);
            }
        }
        return false;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof IPAddressString) {
            IPAddressString other = (IPAddressString)o;
            boolean stringsMatch = this.toString().equals(other.toString());
            if (stringsMatch && this.validationOptions == other.validationOptions) {
                return true;
            }
            if (this.isValid()) {
                if (other.isValid()) {
                    Boolean directResult = this.addressProvider.parsedEquals(other.addressProvider);
                    if (directResult != null) {
                        return directResult;
                    }
                    try {
                        return this.addressProvider.providerEquals(other.addressProvider);
                    }
                    catch (IncompatibleAddressException e) {
                        return stringsMatch;
                    }
                }
            } else if (!other.isValid()) {
                return stringsMatch;
            }
        }
        return false;
    }

    public boolean contains(IPAddressString other) {
        if (this.isValid()) {
            Boolean directResult;
            if (other == this) {
                return true;
            }
            if (other.addressProvider.isUninitialized() && (directResult = this.addressProvider.contains(other.fullAddr)) != null) {
                return directResult;
            }
            if (other.isValid()) {
                IPAddress otherAddress;
                directResult = this.addressProvider.contains(other.addressProvider);
                if (directResult != null) {
                    return directResult;
                }
                IPAddress addr = this.getAddress();
                if (addr != null && (otherAddress = other.getAddress()) != null) {
                    return addr.contains(otherAddress);
                }
            }
        }
        return false;
    }

    public IPAddress getHostAddress() {
        if (!this.addressProvider.isInvalid()) {
            try {
                return this.toHostAddress();
            }
            catch (AddressStringException addressStringException) {
            }
            catch (IncompatibleAddressException incompatibleAddressException) {
                // empty catch block
            }
        }
        return null;
    }

    public IPAddress getAddress(IPAddress.IPVersion version) {
        if (!this.addressProvider.isInvalid()) {
            try {
                return this.toAddress(version);
            }
            catch (AddressStringException addressStringException) {
            }
            catch (IncompatibleAddressException incompatibleAddressException) {
                // empty catch block
            }
        }
        return null;
    }

    @Override
    public IPAddress getAddress() {
        if (!this.addressProvider.isInvalid()) {
            try {
                return this.toAddress();
            }
            catch (AddressStringException addressStringException) {
            }
            catch (IncompatibleAddressException incompatibleAddressException) {
                // empty catch block
            }
        }
        return null;
    }

    public boolean isSequential() {
        return this.isValid() && this.addressProvider.isSequential();
    }

    public IPAddressDivisionSeries getDivisionGrouping() {
        if (!this.addressProvider.isInvalid()) {
            try {
                this.validate();
                return this.addressProvider.getDivisionGrouping();
            }
            catch (AddressStringException addressStringException) {
            }
            catch (IncompatibleAddressException incompatibleAddressException) {
                // empty catch block
            }
        }
        return null;
    }

    public IPAddressSeqRange getSequentialRange() {
        if (!this.addressProvider.isInvalid()) {
            try {
                this.validate();
                return this.addressProvider.getProviderSeqRange();
            }
            catch (AddressStringException addressStringException) {
                // empty catch block
            }
        }
        return null;
    }

    public IPAddressDivisionSeries toDivisionGrouping() throws AddressStringException, IncompatibleAddressException {
        this.validate();
        return this.addressProvider.getDivisionGrouping();
    }

    public IPAddressSeqRange toSequentialRange() throws AddressStringException {
        this.validate();
        return this.addressProvider.getProviderSeqRange();
    }

    public IPAddress toHostAddress() throws AddressStringException, IncompatibleAddressException {
        this.validate();
        return this.addressProvider.getProviderHostAddress();
    }

    public IPAddress toAddress(IPAddress.IPVersion version) throws AddressStringException, IncompatibleAddressException {
        this.validate();
        return this.addressProvider.getProviderAddress(version);
    }

    @Override
    public IPAddress toAddress() throws AddressStringException, IncompatibleAddressException {
        this.validate();
        return this.addressProvider.getProviderAddress();
    }

    public IPAddressString adjustPrefixBySegment(boolean nextSegment) {
        if (this.isPrefixOnly()) {
            int newBits;
            int bitsPerSegment = 8;
            int existingPrefixLength = this.getNetworkPrefixLength();
            if (nextSegment) {
                int adjustment = existingPrefixLength % bitsPerSegment;
                newBits = Math.min(128, existingPrefixLength + bitsPerSegment - adjustment);
            } else {
                int adjustment = (existingPrefixLength - 1) % bitsPerSegment + 1;
                newBits = Math.max(0, existingPrefixLength - adjustment);
            }
            return new IPAddressString(IPAddressNetwork.getPrefixString(newBits), this.validationOptions);
        }
        IPAddress address = this.getAddress();
        if (address == null) {
            return null;
        }
        Integer prefix = address.getNetworkPrefixLength();
        if (!nextSegment && prefix != null && prefix == 0 && address.isMultiple() && address.isPrefixBlock()) {
            return new IPAddressString(IPAddress.SEGMENT_WILDCARD_STR, this.validationOptions);
        }
        return address.adjustPrefixBySegment(nextSegment).toAddressString();
    }

    public IPAddressString adjustPrefixLength(int adjustment) {
        if (this.isPrefixOnly()) {
            int newBits = adjustment > 0 ? Math.min(128, this.getNetworkPrefixLength() + adjustment) : Math.max(0, this.getNetworkPrefixLength() + adjustment);
            return new IPAddressString(IPAddressNetwork.getPrefixString(newBits), this.validationOptions);
        }
        IPAddress address = this.getAddress();
        if (address == null) {
            return null;
        }
        if (adjustment == 0 && this.isPrefixed()) {
            return this;
        }
        Integer prefix = address.getNetworkPrefixLength();
        if (prefix != null && prefix + adjustment < 0 && address.isPrefixBlock()) {
            return new IPAddressString(IPAddress.SEGMENT_WILDCARD_STR, this.validationOptions);
        }
        return address.adjustPrefixLength(adjustment).toAddressString();
    }

    public static int countDelimitedAddresses(String str) {
        int segDelimitedCount = 0;
        int result = 1;
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (IPAddressString.isDelimitedBoundary(c)) {
                if (segDelimitedCount <= 0) continue;
                result *= segDelimitedCount + 1;
                segDelimitedCount = 0;
                continue;
            }
            if (c != ',') continue;
            ++segDelimitedCount;
        }
        if (segDelimitedCount > 0) {
            result *= segDelimitedCount + 1;
        }
        return result;
    }

    private static boolean isDelimitedBoundary(char c) {
        return c == '.' || c == ':' || c == '-' || c == '|';
    }

    public static Iterator<String> parseDelimitedSegments(final String str) {
        ArrayList<List<String>> parts = null;
        int lastSegmentStartIndex = 0;
        int lastPartIndex = 0;
        int lastDelimiterIndex = 0;
        boolean anyDelimited = false;
        ArrayList<String> delimitedList = null;
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (IPAddressString.isDelimitedBoundary(c)) {
                if (delimitedList != null) {
                    if (parts == null) {
                        parts = new ArrayList(8);
                    }
                    IPAddressString.addParts(str, parts, lastSegmentStartIndex, lastPartIndex, lastDelimiterIndex, delimitedList, i);
                    lastPartIndex = i;
                    delimitedList = null;
                }
                lastSegmentStartIndex = lastDelimiterIndex = i + 1;
                continue;
            }
            if (c != ',') continue;
            anyDelimited = true;
            if (delimitedList == null) {
                delimitedList = new ArrayList<String>();
            }
            String sub = str.substring(lastDelimiterIndex, i);
            delimitedList.add(sub);
            lastDelimiterIndex = i + 1;
        }
        if (anyDelimited) {
            if (delimitedList != null) {
                if (parts == null) {
                    parts = new ArrayList<List<String>>(8);
                }
                IPAddressString.addParts(str, parts, lastSegmentStartIndex, lastPartIndex, lastDelimiterIndex, delimitedList, str.length());
            } else {
                parts.add(Arrays.asList(str.substring(lastPartIndex, str.length())));
            }
            return IPAddressString.iterator(parts);
        }
        return new Iterator<String>(){
            boolean done;

            @Override
            public boolean hasNext() {
                return !this.done;
            }

            @Override
            public String next() {
                if (this.done) {
                    throw new NoSuchElementException();
                }
                this.done = true;
                return str;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static Iterator<String> iterator(final List<List<String>> parts) {
        return new Iterator<String>(){
            private boolean done;
            final int partCount;
            private final Iterator<String>[] variations;
            private String[] nextSet;
            {
                this.partCount = parts.size();
                this.variations = new Iterator[this.partCount];
                this.nextSet = new String[this.partCount];
                this.updateVariations(0);
            }

            private void updateVariations(int start2) {
                for (int i = start2; i < this.partCount; ++i) {
                    this.variations[i] = ((List)parts.get(i)).iterator();
                    this.nextSet[i] = this.variations[i].next();
                }
            }

            @Override
            public boolean hasNext() {
                return !this.done;
            }

            @Override
            public String next() {
                if (this.done) {
                    throw new NoSuchElementException();
                }
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < this.partCount; ++i) {
                    result.append(this.nextSet[i]);
                }
                this.increment();
                return result.toString();
            }

            private void increment() {
                for (int j = this.partCount - 1; j >= 0; --j) {
                    if (!this.variations[j].hasNext()) continue;
                    this.nextSet[j] = this.variations[j].next();
                    this.updateVariations(j + 1);
                    return;
                }
                this.done = true;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static void addParts(String str, List<List<String>> parts, int lastSegmentStartIndex, int lastPartIndex, int lastDelimiterIndex, List<String> delimitedList, int i) {
        String sub = str.substring(lastDelimiterIndex, i);
        delimitedList.add(sub);
        if (lastPartIndex != lastSegmentStartIndex) {
            parts.add(Arrays.asList(str.substring(lastPartIndex, lastSegmentStartIndex)));
        }
        parts.add(delimitedList);
    }

    public String convertToPrefixLength() throws AddressStringException {
        Integer prefix;
        IPAddress address = this.getAddress();
        if (address == null ? (prefix = this.getNetworkPrefixLength()) == null : (prefix = address.getBlockMaskPrefixLength(true)) == null) {
            return null;
        }
        return IPAddressSegment.toUnsignedString(prefix, 10, new StringBuilder(IPAddressSegment.toUnsignedStringLength(prefix, 10) + 1).append('/')).toString();
    }

    private static String toNormalizedString(IPAddressProvider addressProvider) throws IncompatibleAddressException {
        String result = addressProvider.isProvidingAllAddresses() ? IPAddress.SEGMENT_WILDCARD_STR : (addressProvider.isProvidingEmpty() ? "" : (addressProvider.isProvidingPrefixOnly() ? IPAddressNetwork.getPrefixString(addressProvider.getProviderNetworkPrefixLength()) : (addressProvider.isProvidingIPAddress() ? addressProvider.getProviderAddress().toNormalizedString() : null)));
        return result;
    }

    @Override
    public String toNormalizedString() {
        if (this.isValid()) {
            try {
                return IPAddressString.toNormalizedString(this.addressProvider);
            }
            catch (IncompatibleAddressException incompatibleAddressException) {
                // empty catch block
            }
        }
        return this.toString();
    }

    @Override
    public String toString() {
        return this.fullAddr;
    }
}

