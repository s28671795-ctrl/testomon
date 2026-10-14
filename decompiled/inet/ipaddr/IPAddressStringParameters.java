/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressStringParameters;
import inet.ipaddr.HostNameParameters;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.ipv4.IPv4AddressStringParameters;
import inet.ipaddr.ipv6.IPv6AddressStringParameters;

public class IPAddressStringParameters
extends AddressStringParameters
implements Comparable<IPAddressStringParameters> {
    private static final long serialVersionUID = 4L;
    public static final boolean DEFAULT_ALLOW_PREFIX_ONLY = true;
    public static final boolean DEFAULT_EMPTY_IS_LOOPBACK = true;
    public static final boolean DEFAULT_ALLOW_PREFIX = true;
    public static final boolean DEFAULT_ALLOW_MASK = true;
    public static final boolean DEFAULT_ALLOW_IPV4 = true;
    public static final boolean DEFAULT_ALLOW_IPV6 = true;
    public final boolean allowPrefixOnly;
    public final boolean emptyIsLoopback;
    public final boolean allowPrefix;
    public final boolean allowMask;
    public final boolean allowIPv6;
    public final boolean allowIPv4;
    private IPv6AddressStringParameters ipv6Options;
    private IPv4AddressStringParameters ipv4Options;

    public IPAddress.IPVersion inferVersion() {
        if (this.allowIPv6) {
            if (!this.allowIPv4) {
                return IPAddress.IPVersion.IPV6;
            }
        } else if (this.allowIPv4) {
            return IPAddress.IPVersion.IPV4;
        }
        return null;
    }

    public Builder toBuilder() {
        return this.toBuilder(false);
    }

    public Builder toBuilder(boolean isMixed) {
        Builder builder = new Builder();
        super.toBuilder(builder);
        builder.allowPrefixOnly = this.allowPrefixOnly;
        builder.emptyIsLoopback = this.emptyIsLoopback;
        builder.allowPrefix = this.allowPrefix;
        builder.allowMask = this.allowMask;
        builder.allowIPv6 = this.allowIPv6;
        builder.allowIPv4 = this.allowIPv4;
        builder.ipv4Builder = this.ipv4Options.toBuilder();
        builder.ipv6Builder = this.ipv6Options.toBuilder(isMixed);
        builder.allowSingleSegment = this.allowSingleSegment;
        builder.allowEmpty = this.allowEmpty;
        builder.allowAll = this.allowAll;
        return builder;
    }

    public IPAddressStringParameters(boolean allowEmpty, boolean allowAll, boolean allowSingleSegment, boolean emptyIsLoopback, boolean allowPrefix, boolean allowMask, boolean allowPrefixOnly, boolean allowIPv4, boolean allowIPv6, IPv4AddressStringParameters ipv4Options, IPv6AddressStringParameters ipv6Options) {
        super(allowEmpty, allowAll, allowSingleSegment);
        this.allowPrefixOnly = allowPrefixOnly;
        this.emptyIsLoopback = emptyIsLoopback;
        this.allowPrefix = allowPrefix;
        this.allowMask = allowMask;
        this.allowIPv4 = allowIPv4;
        this.allowIPv6 = allowIPv6;
        this.ipv6Options = ipv6Options;
        this.ipv4Options = ipv4Options;
    }

    public IPv6AddressStringParameters getIPv6Parameters() {
        return this.ipv6Options;
    }

    public IPv4AddressStringParameters getIPv4Parameters() {
        return this.ipv4Options;
    }

    @Override
    public IPAddressStringParameters clone() {
        IPAddressStringParameters result = (IPAddressStringParameters)super.clone();
        result.ipv4Options = this.ipv4Options.clone();
        result.ipv6Options = this.ipv6Options.clone();
        return result;
    }

    @Override
    public int compareTo(IPAddressStringParameters o) {
        int result = super.compareTo(o);
        if (result == 0 && (result = this.ipv4Options.compareTo(o.ipv4Options)) == 0 && (result = this.ipv6Options.compareTo(o.ipv6Options)) == 0 && (result = Boolean.compare(this.emptyIsLoopback, o.emptyIsLoopback)) == 0 && (result = Boolean.compare(this.allowPrefix, o.allowPrefix)) == 0 && (result = Boolean.compare(this.allowPrefixOnly, o.allowPrefixOnly)) == 0 && (result = Boolean.compare(this.allowMask, o.allowMask)) == 0 && (result = Boolean.compare(this.allowIPv6, o.allowIPv6)) == 0) {
            result = Boolean.compare(this.allowIPv4, o.allowIPv4);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IPAddressStringParameters) {
            IPAddressStringParameters other = (IPAddressStringParameters)o;
            return super.equals(o) && this.ipv4Options.equals(other.ipv4Options) && this.ipv6Options.equals(other.ipv6Options) && this.emptyIsLoopback == other.emptyIsLoopback && this.allowPrefix == other.allowPrefix && this.allowPrefixOnly == other.allowPrefixOnly && this.allowMask == other.allowMask && this.allowIPv6 == other.allowIPv6 && this.allowIPv4 == other.allowIPv4;
        }
        return false;
    }

    public int hashCode() {
        int hash = this.ipv4Options.hashCode();
        hash |= this.ipv6Options.hashCode() << 9;
        if (this.emptyIsLoopback) {
            hash |= 0x8000000;
        }
        if (this.allowPrefix) {
            hash |= 0x10000000;
        }
        if (this.allowMask) {
            hash |= 0x20000000;
        }
        if (this.allowEmpty) {
            hash |= 0x40000000;
        }
        if (this.allowSingleSegment) {
            hash |= Integer.MIN_VALUE;
        }
        return hash;
    }

    public static abstract class IPAddressStringFormatParameters
    extends AddressStringParameters.AddressStringFormatParameters {
        private static final long serialVersionUID = 4L;
        public static final boolean DEFAULT_ALLOW_PREFIX_LENGTH_LEADING_ZEROS = true;
        public static final boolean DEFAULT_ALLOW_PREFIX_BEYOND_ADDRESS_SIZE = false;
        public static final boolean DEFAULT_ALLOW_BINARY = true;
        public final boolean allowBinary;
        public final boolean allowPrefixesBeyondAddressSize;
        public final boolean allowPrefixLengthLeadingZeros;

        @Deprecated
        public IPAddressStringFormatParameters(boolean allowLeadingZeros, boolean allowPrefixLengthLeadingZeros, boolean allowUnlimitedLeadingZeros, AddressStringParameters.RangeParameters rangeOptions, boolean allowWildcardedSeparator, boolean allowPrefixesBeyondAddressSize) {
            this(false, allowLeadingZeros, allowPrefixLengthLeadingZeros, allowUnlimitedLeadingZeros, rangeOptions, allowWildcardedSeparator, allowPrefixesBeyondAddressSize);
        }

        public IPAddressStringFormatParameters(boolean allowBinary, boolean allowLeadingZeros, boolean allowPrefixLengthLeadingZeros, boolean allowUnlimitedLeadingZeros, AddressStringParameters.RangeParameters rangeOptions, boolean allowWildcardedSeparator, boolean allowPrefixesBeyondAddressSize) {
            super(allowLeadingZeros, allowUnlimitedLeadingZeros, rangeOptions, allowWildcardedSeparator);
            this.allowBinary = allowBinary;
            this.allowPrefixLengthLeadingZeros = allowPrefixLengthLeadingZeros;
            this.allowPrefixesBeyondAddressSize = allowPrefixesBeyondAddressSize;
        }

        protected BuilderBase toBuilder(BuilderBase builder) {
            super.toBuilder(builder);
            builder.allowPrefixLengthLeadingZeros = this.allowPrefixLengthLeadingZeros;
            builder.allowPrefixesBeyondAddressSize = this.allowPrefixesBeyondAddressSize;
            builder.allowBinary = this.allowBinary;
            return builder;
        }

        public abstract IPAddressNetwork<?, ?, ?, ?, ?> getNetwork();

        protected int compareTo(IPAddressStringFormatParameters o) {
            int result = super.compareTo(o);
            if (result == 0 && (result = Boolean.compare(this.allowPrefixesBeyondAddressSize, o.allowPrefixesBeyondAddressSize)) == 0 && (result = Boolean.compare(this.allowPrefixLengthLeadingZeros, o.allowPrefixLengthLeadingZeros)) == 0) {
                result = Boolean.compare(this.allowBinary, o.allowBinary);
            }
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof IPAddressStringFormatParameters) {
                IPAddressStringFormatParameters other = (IPAddressStringFormatParameters)o;
                return super.equals(o) && this.allowPrefixesBeyondAddressSize == other.allowPrefixesBeyondAddressSize && this.allowBinary == other.allowBinary && this.allowPrefixLengthLeadingZeros == other.allowPrefixLengthLeadingZeros;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = super.hashCode();
            if (this.allowPrefixesBeyondAddressSize) {
                hash |= 8;
            }
            return hash;
        }

        protected static class BuilderBase
        extends AddressStringParameters.AddressStringFormatParameters.BuilderBase {
            protected boolean allowPrefixesBeyondAddressSize = false;
            protected boolean allowPrefixLengthLeadingZeros = true;
            protected boolean allowBinary = true;
            Builder parent;

            protected BuilderBase() {
            }

            public Builder getParentBuilder() {
                return this.parent;
            }

            @Override
            public BuilderBase setRangeOptions(AddressStringParameters.RangeParameters rangeOptions) {
                return (BuilderBase)super.setRangeOptions(rangeOptions);
            }

            public BuilderBase allowPrefixesBeyondAddressSize(boolean allow) {
                this.allowPrefixesBeyondAddressSize = allow;
                return this;
            }

            public BuilderBase allowBinary(boolean allow) {
                this.allowBinary = allow;
                return this;
            }

            @Override
            public BuilderBase allowWildcardedSeparator(boolean allow) {
                return (BuilderBase)super.allowWildcardedSeparator(allow);
            }

            @Override
            public BuilderBase allowLeadingZeros(boolean allow) {
                return (BuilderBase)super.allowLeadingZeros(allow);
            }

            public BuilderBase allowPrefixLengthLeadingZeros(boolean allow) {
                this.allowPrefixLengthLeadingZeros = allow;
                return this;
            }

            @Override
            public BuilderBase allowUnlimitedLeadingZeros(boolean allow) {
                return (BuilderBase)super.allowUnlimitedLeadingZeros(allow);
            }

            protected static void setMixedParentInst(IPv6AddressStringParameters.Builder parent, IPv4AddressStringParameters.Builder embedded) {
                IPv4AddressStringParameters.Builder base = embedded;
                ((BuilderBase)base).setMixedParent(parent);
            }

            protected void setMixedParent(IPv6AddressStringParameters.Builder parent) {
            }
        }
    }

    public static class Builder
    extends AddressStringParameters.BuilderBase {
        private boolean emptyIsLoopback = true;
        private boolean allowPrefix = true;
        private boolean allowMask = true;
        private boolean allowPrefixOnly = true;
        private boolean allowIPv4 = true;
        private boolean allowIPv6 = true;
        IPv4AddressStringParameters.Builder ipv4Builder;
        private static IPv4AddressStringParameters DEFAULT_IPV4_OPTS = new IPv4AddressStringParameters.Builder().toParams();
        IPv6AddressStringParameters.Builder ipv6Builder;
        private static IPv6AddressStringParameters DEFAULT_IPV6_OPTS = new IPv6AddressStringParameters.Builder().toParams();
        HostNameParameters.Builder parent;

        public HostNameParameters.Builder getParentBuilder() {
            return this.parent;
        }

        @Override
        public Builder allowEmpty(boolean allow) {
            return (Builder)super.allowEmpty(allow);
        }

        @Override
        public Builder allowSingleSegment(boolean allow) {
            return (Builder)super.allowSingleSegment(allow);
        }

        public Builder setEmptyAsLoopback(boolean bool) {
            this.emptyIsLoopback = bool;
            return this;
        }

        public Builder allowPrefix(boolean allow) {
            this.allowPrefix = allow;
            return this;
        }

        public Builder allowMask(boolean allow) {
            this.allowMask = allow;
            return this;
        }

        public Builder allowPrefixOnly(boolean allow) {
            this.allowPrefixOnly = allow;
            return this;
        }

        @Override
        public Builder allowAll(boolean allow) {
            return (Builder)super.allowAll(allow);
        }

        public Builder allowIPv4(boolean allow) {
            this.allowIPv4 = allow;
            return this;
        }

        public Builder allowIPv6(boolean allow) {
            this.allowIPv6 = allow;
            return this;
        }

        public Builder allowWildcardedSeparator(boolean allow) {
            this.getIPv4AddressParametersBuilder().allowWildcardedSeparator(allow);
            this.getIPv6AddressParametersBuilder().allowWildcardedSeparator(allow);
            return this;
        }

        public Builder setRangeOptions(AddressStringParameters.RangeParameters rangeOptions) {
            this.getIPv4AddressParametersBuilder().setRangeOptions(rangeOptions);
            this.getIPv6AddressParametersBuilder().setRangeOptions(rangeOptions);
            return this;
        }

        public Builder allow_inet_aton(boolean allow) {
            this.getIPv4AddressParametersBuilder().allow_inet_aton(allow);
            this.getIPv6AddressParametersBuilder().allow_mixed_inet_aton(allow);
            return this;
        }

        public void setIPv6AddressParameters(IPv6AddressStringParameters params) {
            this.ipv6Builder = params.toBuilder();
        }

        public IPv6AddressStringParameters.Builder getIPv6AddressParametersBuilder() {
            if (this.ipv6Builder == null) {
                this.ipv6Builder = new IPv6AddressStringParameters.Builder();
            }
            this.ipv6Builder.parent = this;
            return this.ipv6Builder;
        }

        public void setIPv4AddressParameters(IPv4AddressStringParameters params) {
            this.ipv4Builder = params.toBuilder();
        }

        public IPv4AddressStringParameters.Builder getIPv4AddressParametersBuilder() {
            if (this.ipv4Builder == null) {
                this.ipv4Builder = new IPv4AddressStringParameters.Builder();
            }
            this.ipv4Builder.parent = this;
            return this.ipv4Builder;
        }

        public IPAddressStringParameters toParams() {
            IPv4AddressStringParameters ipv4Opts = this.ipv4Builder == null ? DEFAULT_IPV4_OPTS : this.ipv4Builder.toParams();
            IPv6AddressStringParameters ipv6Opts = this.ipv6Builder == null ? DEFAULT_IPV6_OPTS : this.ipv6Builder.toParams();
            return new IPAddressStringParameters(this.allowEmpty, this.allowAll, this.allowSingleSegment, this.emptyIsLoopback, this.allowPrefix, this.allowMask, this.allowPrefixOnly, this.allowIPv4, this.allowIPv6, ipv4Opts, ipv6Opts);
        }
    }
}

