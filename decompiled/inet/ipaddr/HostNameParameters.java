/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.IPAddressString;
import inet.ipaddr.IPAddressStringParameters;
import java.io.Serializable;

public class HostNameParameters
implements Cloneable,
Comparable<HostNameParameters>,
Serializable {
    private static final long serialVersionUID = 4L;
    public static final boolean DEFAULT_ALLOW_EMPTY = true;
    public static final boolean DEFAULT_EMPTY_IS_LOOPBACK = true;
    public static final boolean DEFAULT_ACCEPT_BRACKETED_IPV6 = true;
    public static final boolean DEFAULT_ACCEPT_BRACKETED_IPV4 = true;
    public static final boolean DEFAULT_NORMALIZE_TO_LOWER_CASE = true;
    public static final boolean DEFAULT_ALLOW_IP_ADDRESS = true;
    public static final boolean DEFAULT_ALLOW_PORT = true;
    public static final boolean DEFAULT_EXPECT_PORT = false;
    public static final boolean DEFAULT_ALLOW_SERVICE = true;
    public final boolean allowEmpty;
    public final boolean emptyIsLoopback;
    public final boolean allowBracketedIPv4;
    public final boolean allowBracketedIPv6;
    public final boolean normalizeToLowercase;
    public final boolean allowIPAddress;
    public final boolean allowPort;
    public final boolean allowService;
    public final boolean expectPort;
    public final IPAddressStringParameters addressOptions;

    public HostNameParameters(IPAddressStringParameters addressOptions, boolean allowEmpty, boolean emptyIsLoopback, boolean allowBracketedIPv6, boolean allowBracketedIPv4, boolean normalizeToLowercase, boolean allowIPAddress, boolean allowPort, boolean expectPort, boolean allowService) {
        this.allowEmpty = allowEmpty;
        this.emptyIsLoopback = emptyIsLoopback;
        this.allowBracketedIPv6 = allowBracketedIPv6;
        this.allowBracketedIPv4 = allowBracketedIPv4;
        this.normalizeToLowercase = normalizeToLowercase;
        this.allowIPAddress = allowIPAddress;
        this.allowPort = allowPort;
        this.expectPort = expectPort;
        this.allowService = allowService;
        this.addressOptions = addressOptions;
    }

    public Builder toBuilder() {
        Builder builder = new Builder();
        builder.allowEmpty = this.allowEmpty;
        builder.emptyIsLoopback = this.emptyIsLoopback;
        builder.allowBracketedIPv4 = this.allowBracketedIPv4;
        builder.allowBracketedIPv6 = this.allowBracketedIPv6;
        builder.normalizeToLowercase = this.normalizeToLowercase;
        builder.allowIPAddress = this.allowIPAddress;
        builder.allowPort = this.allowPort;
        builder.allowService = this.allowService;
        builder.addressOptionsBuilder = this.toAddressOptionsBuilder();
        return builder;
    }

    public IPAddressStringParameters.Builder toAddressOptionsBuilder() {
        return this.addressOptions.toBuilder();
    }

    public HostNameParameters clone() {
        try {
            return (HostNameParameters)super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public int compareTo(HostNameParameters o) {
        int result = Boolean.compare(this.allowEmpty, o.allowEmpty);
        if (result == 0 && (result = Boolean.compare(this.allowBracketedIPv6, o.allowBracketedIPv6)) == 0 && (result = Boolean.compare(this.allowBracketedIPv4, o.allowBracketedIPv4)) == 0 && (result = Boolean.compare(this.normalizeToLowercase, o.normalizeToLowercase)) == 0 && (result = Boolean.compare(this.allowIPAddress, o.allowIPAddress)) == 0 && (result = Boolean.compare(this.allowPort, o.allowPort)) == 0 && (result = Boolean.compare(this.expectPort, o.expectPort)) == 0 && (result = Boolean.compare(this.allowService, o.allowService)) == 0) {
            result = this.addressOptions.compareTo(o.addressOptions);
        }
        return result;
    }

    public boolean equals(Object o) {
        if (o instanceof HostNameParameters) {
            HostNameParameters other = (HostNameParameters)o;
            return this.allowEmpty == other.allowEmpty && this.allowBracketedIPv6 == other.allowBracketedIPv6 && this.allowBracketedIPv4 == other.allowBracketedIPv4 && this.normalizeToLowercase == other.normalizeToLowercase && this.allowIPAddress == other.allowIPAddress && this.allowPort == other.allowPort && this.expectPort == other.expectPort && this.allowService == other.allowService && this.addressOptions.equals(other.addressOptions);
        }
        return false;
    }

    public int hashCode() {
        int hash;
        int n = hash = this.allowIPAddress ? this.addressOptions.hashCode() : 0;
        if (this.allowEmpty) {
            hash |= 0x20000000;
        }
        if (this.allowIPAddress && (this.allowBracketedIPv6 || this.allowBracketedIPv4)) {
            hash |= Integer.MIN_VALUE;
        }
        if (this.allowPort || this.allowService || this.expectPort) {
            hash |= 0x40000000;
        }
        return hash;
    }

    public static class Builder {
        private boolean allowEmpty = true;
        private boolean emptyIsLoopback = true;
        private boolean allowBracketedIPv6 = true;
        private boolean allowBracketedIPv4 = true;
        private boolean normalizeToLowercase = true;
        private boolean allowIPAddress = true;
        private boolean allowPort = true;
        private boolean expectPort = false;
        private boolean allowService = true;
        private IPAddressStringParameters.Builder addressOptionsBuilder;

        public Builder allowPort(boolean allow) {
            this.allowPort = allow;
            return this;
        }

        public Builder expectPort(boolean expect) {
            this.expectPort = expect;
            return this;
        }

        public Builder allowService(boolean allow) {
            this.allowService = allow;
            return this;
        }

        public Builder allowIPAddress(boolean allow) {
            this.allowIPAddress = allow;
            return this;
        }

        public Builder allowEmpty(boolean allow) {
            this.allowEmpty = allow;
            return this;
        }

        public Builder setEmptyAsLoopback(boolean bool) {
            this.emptyIsLoopback = bool;
            return this;
        }

        public Builder allowBracketedIPv6(boolean allow) {
            this.allowBracketedIPv6 = allow;
            return this;
        }

        public Builder allowBracketedIPv4(boolean allow) {
            this.allowBracketedIPv4 = allow;
            return this;
        }

        public Builder setNormalizeToLowercase(boolean bool) {
            this.normalizeToLowercase = bool;
            return this;
        }

        public IPAddressStringParameters.Builder getAddressOptionsBuilder() {
            if (this.addressOptionsBuilder == null) {
                this.addressOptionsBuilder = new IPAddressStringParameters.Builder();
            }
            this.addressOptionsBuilder.parent = this;
            return this.addressOptionsBuilder;
        }

        public HostNameParameters toParams() {
            IPAddressStringParameters addressOpts = this.addressOptionsBuilder == null ? IPAddressString.DEFAULT_VALIDATION_OPTIONS : this.addressOptionsBuilder.toParams();
            return new HostNameParameters(addressOpts, this.allowEmpty, this.emptyIsLoopback, this.allowIPAddress && this.allowBracketedIPv6, this.allowIPAddress && this.allowBracketedIPv4, this.normalizeToLowercase, this.allowIPAddress, this.allowPort, this.expectPort, this.allowService);
        }
    }
}

