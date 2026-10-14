/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.validate;

import inet.ipaddr.IPAddress;
import inet.ipaddr.format.validate.AddressParseData;
import inet.ipaddr.format.validate.ParsedHost;
import inet.ipaddr.format.validate.ParsedHostIdentifierStringQualifier;
import inet.ipaddr.format.validate.ParsedIPAddress;

class IPAddressParseData
extends AddressParseData {
    private static final long serialVersionUID = 4L;
    private ParsedHostIdentifierStringQualifier qualifier = ParsedHost.NO_QUALIFIER;
    private int qualifierIndex = -1;
    private boolean hasPrefixSeparator;
    private boolean isZoned;
    private IPAddress.IPVersion ipVersion;
    private boolean is_inet_aton_joined;
    protected boolean has_inet_aton_value;
    protected boolean hasIPv4LeadingZeros;
    protected boolean isBinary;
    ParsedIPAddress mixedParsedAddress;
    private boolean isBase85;
    private boolean isBase85Zoned;

    IPAddressParseData(CharSequence str) {
        super(str);
    }

    AddressParseData getAddressParseData() {
        return this;
    }

    public IPAddress.IPVersion getProviderIPVersion() {
        return this.ipVersion;
    }

    void setVersion(IPAddress.IPVersion val) {
        this.ipVersion = val;
    }

    public boolean isProvidingIPv6() {
        IPAddress.IPVersion version = this.getProviderIPVersion();
        return version != null && version.isIPv6();
    }

    public boolean isProvidingIPv4() {
        IPAddress.IPVersion version = this.getProviderIPVersion();
        return version != null && version.isIPv4();
    }

    void set_inet_aton_joined(boolean val) {
        this.is_inet_aton_joined = val;
    }

    boolean is_inet_aton_joined() {
        return this.is_inet_aton_joined;
    }

    void set_has_inet_aton_value(boolean val) {
        this.has_inet_aton_value = val;
    }

    boolean has_inet_aton_value() {
        return this.has_inet_aton_value;
    }

    void setHasIPv4LeadingZeros(boolean val) {
        this.hasIPv4LeadingZeros = val;
    }

    boolean hasIPv4LeadingZeros() {
        return this.hasIPv4LeadingZeros;
    }

    void setHasBinaryDigits(boolean val) {
        this.isBinary = val;
    }

    boolean hasBinaryDigits() {
        return this.isBinary;
    }

    ParsedHostIdentifierStringQualifier getQualifier() {
        return this.qualifier;
    }

    void setQualifier(ParsedHostIdentifierStringQualifier qualifier) {
        this.qualifier = qualifier;
    }

    void clearQualifier() {
        this.qualifierIndex = -1;
        this.isZoned = false;
        this.hasPrefixSeparator = false;
        this.isBase85Zoned = false;
        this.qualifier = ParsedHost.NO_QUALIFIER;
    }

    void setQualifierIndex(int index) {
        this.qualifierIndex = index;
    }

    int getQualifierIndex() {
        return this.qualifierIndex;
    }

    boolean isZoned() {
        return this.isZoned;
    }

    void setZoned(boolean val) {
        this.isZoned = val;
    }

    void setHasPrefixSeparator(boolean val) {
        this.hasPrefixSeparator = val;
    }

    public boolean hasPrefixSeparator() {
        return this.hasPrefixSeparator;
    }

    public boolean isProvidingBase85IPv6() {
        return this.isBase85;
    }

    void setBase85(boolean val) {
        this.isBase85 = val;
    }

    boolean isBase85Zoned() {
        return this.isBase85Zoned;
    }

    void setBase85Zoned(boolean val) {
        this.isBase85Zoned = val;
    }

    boolean isCompressed() {
        return this.getAddressParseData().getConsecutiveSeparatorIndex() >= 0;
    }

    boolean isCompressed(int index, int[] segmentData) {
        int end = AddressParseData.getIndex(index, 15, segmentData);
        int start2 = AddressParseData.getIndex(index, 6, segmentData);
        return start2 == end;
    }

    boolean isCompressed(int index) {
        AddressParseData addressParseData = this.getAddressParseData();
        return this.isCompressed(index, addressParseData.getSegmentData());
    }

    public boolean isProvidingMixedIPv6() {
        return this.mixedParsedAddress != null;
    }

    void setMixedParsedAddress(ParsedIPAddress val) {
        this.mixedParsedAddress = val;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.toString(builder);
        builder.append("ip version: ").append((Object)this.getProviderIPVersion());
        if (this.isProvidingIPv6()) {
            if (this.isProvidingMixedIPv6()) {
                if (this.isZoned()) {
                    builder.append(", with zone ");
                    this.printQualifier(builder);
                }
                if (this.hasPrefixSeparator()) {
                    builder.append(", with prefix length ");
                    this.printQualifier(builder);
                }
                builder.append(", with IPv4 embedded address: ").append('\n').append(this.mixedParsedAddress);
            } else {
                if (this.isProvidingBase85IPv6()) {
                    builder.append(" base 85");
                    if (this.isBase85Zoned()) {
                        builder.append(", with zone ");
                        this.printQualifier(builder);
                    }
                } else if (this.isZoned()) {
                    builder.append(", with zone ");
                    this.printQualifier(builder);
                }
                if (this.hasPrefixSeparator()) {
                    builder.append(", with prefix length ");
                    this.printQualifier(builder);
                }
                builder.append('\n');
            }
        } else if (this.isProvidingIPv4()) {
            if (this.hasPrefixSeparator()) {
                builder.append(", with prefix length  ");
                this.printQualifier(builder);
            }
            if (this.is_inet_aton_joined()) {
                builder.append(", with joined segments");
            }
            if (this.has_inet_aton_value()) {
                builder.append(", with at least one hex or octal value");
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    private void printQualifier(StringBuilder builder) {
        AddressParseData addressParseData = this.getAddressParseData();
        int qualifierIndex = this.getQualifierIndex();
        if (qualifierIndex >= 0) {
            CharSequence str = addressParseData.getString();
            builder.append(str.subSequence(qualifierIndex, str.length()));
        } else {
            builder.append("unknown");
        }
    }
}

