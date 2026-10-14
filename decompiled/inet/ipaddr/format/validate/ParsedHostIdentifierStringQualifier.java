/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.validate;

import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressStringParameters;
import inet.ipaddr.format.validate.ParsedAddressGrouping;
import inet.ipaddr.format.validate.ParsedIPAddress;
import java.io.Serializable;

public class ParsedHostIdentifierStringQualifier
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 4L;
    private Integer networkPrefixLength;
    private final Integer port;
    private final CharSequence service;
    private ParsedIPAddress mask;
    private IPAddress mergedMask;
    private final CharSequence zone;

    ParsedHostIdentifierStringQualifier() {
        this(null, null, null, null, null);
    }

    ParsedHostIdentifierStringQualifier(CharSequence zone) {
        this(null, null, zone, null, null);
    }

    public ParsedHostIdentifierStringQualifier(CharSequence zone, int port) {
        this(null, null, zone, ParsedHostIdentifierStringQualifier.cachePorts(port), null);
    }

    public ParsedHostIdentifierStringQualifier(Integer networkPrefixLength, CharSequence zone) {
        this(networkPrefixLength, null, zone, null, null);
    }

    ParsedHostIdentifierStringQualifier(ParsedIPAddress mask, CharSequence zone) {
        this(null, mask, zone, null, null);
    }

    ParsedHostIdentifierStringQualifier(CharSequence zone, CharSequence service2) {
        this(null, null, zone, null, service2);
        if (zone != null && service2 != null) {
            throw new IllegalArgumentException();
        }
    }

    private ParsedHostIdentifierStringQualifier(Integer networkPrefixLength, ParsedIPAddress mask, CharSequence zone, Integer port, CharSequence service2) {
        this.networkPrefixLength = networkPrefixLength;
        this.mask = mask;
        this.zone = zone;
        this.port = port;
        this.service = service2;
    }

    private static Integer cachePorts(int i) {
        return ParsedAddressGrouping.cache(i);
    }

    private void overrideMask(ParsedHostIdentifierStringQualifier other) {
        if (other.mask != null) {
            this.mask = other.mask;
        }
    }

    private void overridePrefixLength(ParsedHostIdentifierStringQualifier other) {
        if (other.networkPrefixLength != null) {
            this.networkPrefixLength = other.networkPrefixLength;
        }
    }

    void overridePrefix(ParsedHostIdentifierStringQualifier other) {
        this.overridePrefixLength(other);
        this.overrideMask(other);
    }

    void merge(ParsedHostIdentifierStringQualifier other) {
        if (this.networkPrefixLength == null || other.networkPrefixLength != null && other.networkPrefixLength < this.networkPrefixLength) {
            this.networkPrefixLength = other.networkPrefixLength;
        }
        if (this.mask == null) {
            if (other.mask != null) {
                this.mask = other.mask;
            }
        } else if (other.mask != null) {
            this.mergedMask = this.getMaskLower().mask(other.getMaskLower());
        }
    }

    IPAddress getMaskLower() {
        if (this.mergedMask != null) {
            return this.mergedMask;
        }
        if (this.mask != null) {
            return this.mask.getValForMask();
        }
        return null;
    }

    Integer getEquivalentPrefixLength() {
        IPAddress mask;
        Integer pref = this.getNetworkPrefixLength();
        if (pref == null && (mask = this.getMaskLower()) != null) {
            pref = mask.getBlockMaskPrefixLength(true);
        }
        return pref;
    }

    CharSequence getZone() {
        return this.zone;
    }

    Integer getNetworkPrefixLength() {
        return this.networkPrefixLength;
    }

    Integer getPort() {
        return this.port;
    }

    CharSequence getService() {
        return this.service;
    }

    IPAddress.IPVersion inferVersion(IPAddressStringParameters validationOptions) {
        if (this.networkPrefixLength != null) {
            if (this.networkPrefixLength > IPAddress.getBitCount(IPAddress.IPVersion.IPV4) && !validationOptions.getIPv4Parameters().allowPrefixesBeyondAddressSize) {
                return IPAddress.IPVersion.IPV6;
            }
        } else if (this.mask != null) {
            if (this.mask.isProvidingIPv6()) {
                return IPAddress.IPVersion.IPV6;
            }
            if (this.mask.isProvidingIPv4()) {
                return IPAddress.IPVersion.IPV4;
            }
        }
        if (this.zone != null) {
            return IPAddress.IPVersion.IPV6;
        }
        return null;
    }

    protected ParsedHostIdentifierStringQualifier clone() {
        try {
            return (ParsedHostIdentifierStringQualifier)super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String toString() {
        return "network prefix length: " + this.networkPrefixLength + " mask: " + this.mask + " zone: " + this.zone + " port: " + this.port + " service: " + this.service;
    }
}

