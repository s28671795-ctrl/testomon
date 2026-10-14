/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv6;

import inet.ipaddr.Address;
import inet.ipaddr.AddressStringParameters;
import inet.ipaddr.IPAddressStringParameters;
import inet.ipaddr.ipv4.IPv4AddressStringParameters;
import inet.ipaddr.ipv6.IPv6AddressNetwork;
import java.util.Objects;

public class IPv6AddressStringParameters
extends IPAddressStringParameters.IPAddressStringFormatParameters
implements Comparable<IPv6AddressStringParameters> {
    private static final long serialVersionUID = 4L;
    public static final boolean DEFAULT_ALLOW_MIXED = true;
    public static final boolean DEFAULT_ALLOW_ZONE = true;
    public static final boolean DEFAULT_ALLOW_EMPTY_ZONE = true;
    public static final boolean DEFAULT_ALLOW_BASE85 = true;
    public final boolean allowMixed;
    public final boolean allowZone;
    public final boolean allowEmptyZone;
    public final boolean allowBase85;
    private final IPv6AddressNetwork network;
    private IPAddressStringParameters embeddedIPv4Options;

    @Deprecated
    public IPv6AddressStringParameters(boolean allowLeadingZeros, boolean allowCIDRPrefixLeadingZeros, boolean allowUnlmitedLeadingZeros, boolean allowMixed, IPAddressStringParameters mixedOptions, boolean allowZone, boolean allowBase85, AddressStringParameters.RangeParameters rangeOptions, boolean allowWildcardedSeparator, boolean allowPrefixesBeyondAddressSize, IPv6AddressNetwork network) {
        this(allowLeadingZeros, allowCIDRPrefixLeadingZeros, allowUnlmitedLeadingZeros, allowMixed, mixedOptions, allowZone, true, allowBase85, rangeOptions, allowWildcardedSeparator, allowPrefixesBeyondAddressSize, false, network);
    }

    public IPv6AddressStringParameters(boolean allowLeadingZeros, boolean allowCIDRPrefixLeadingZeros, boolean allowUnlmitedLeadingZeros, boolean allowMixed, IPAddressStringParameters mixedOptions, boolean allowZone, boolean allowEmptyZone, boolean allowBase85, AddressStringParameters.RangeParameters rangeOptions, boolean allowWildcardedSeparator, boolean allowPrefixesBeyondAddressSize, boolean allowBinary, IPv6AddressNetwork network) {
        super(allowBinary, allowLeadingZeros, allowCIDRPrefixLeadingZeros, allowUnlmitedLeadingZeros, rangeOptions, allowWildcardedSeparator, allowPrefixesBeyondAddressSize);
        this.allowMixed = allowMixed;
        this.allowZone = allowZone;
        this.allowEmptyZone = allowEmptyZone;
        this.allowBase85 = allowBase85;
        this.embeddedIPv4Options = mixedOptions;
        this.network = network;
    }

    public Builder toBuilder() {
        return this.toBuilder(false);
    }

    public Builder toBuilder(boolean isMixed) {
        Builder builder = new Builder();
        builder.allowMixed = this.allowMixed;
        builder.allowZone = this.allowZone;
        builder.allowEmptyZone = this.allowEmptyZone;
        builder.allowBase85 = this.allowBase85;
        builder.network = this.network;
        if (!isMixed) {
            builder.embeddedIPv4OptionsBuilder = this.embeddedIPv4Options.toBuilder(true);
        }
        return (Builder)this.toBuilder(builder);
    }

    public IPv6AddressNetwork getNetwork() {
        if (this.network == null) {
            return Address.defaultIpv6Network();
        }
        return this.network;
    }

    public IPv6AddressStringParameters clone() {
        try {
            IPv6AddressStringParameters result = (IPv6AddressStringParameters)super.clone();
            result.embeddedIPv4Options = this.embeddedIPv4Options.clone();
            return result;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    public IPAddressStringParameters getMixedParameters() {
        return this.embeddedIPv4Options;
    }

    @Override
    public int compareTo(IPv6AddressStringParameters o) {
        int result = super.compareTo(o);
        if (result == 0 && (result = this.embeddedIPv4Options.getIPv4Parameters().compareTo(o.embeddedIPv4Options.getIPv4Parameters())) == 0 && (result = Boolean.compare(this.allowMixed, o.allowMixed)) == 0 && (result = Boolean.compare(this.allowZone, o.allowZone)) == 0 && (result = Boolean.compare(this.allowEmptyZone, o.allowEmptyZone)) == 0) {
            result = Boolean.compare(this.allowBase85, o.allowBase85);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IPv6AddressStringParameters && super.equals(o)) {
            IPv6AddressStringParameters other = (IPv6AddressStringParameters)o;
            return Objects.equals(this.embeddedIPv4Options.getIPv4Parameters(), other.embeddedIPv4Options.getIPv4Parameters()) && this.allowMixed == other.allowMixed && this.allowZone == other.allowZone && this.allowEmptyZone == other.allowEmptyZone && this.allowBase85 == other.allowBase85;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash |= this.embeddedIPv4Options.getIPv4Parameters().hashCode() << 6;
        if (this.allowMixed) {
            hash |= 0x8000;
        }
        if (this.allowZone) {
            hash |= 0x10000;
        }
        if (this.allowBase85) {
            hash |= 0x20000;
        }
        return hash;
    }

    public static class Builder
    extends IPAddressStringParameters.IPAddressStringFormatParameters.BuilderBase {
        private boolean allowMixed = true;
        private boolean allowZone = true;
        private boolean allowEmptyZone = true;
        private boolean allowBase85 = true;
        private IPAddressStringParameters.Builder embeddedIPv4OptionsBuilder;
        private IPv6AddressNetwork network;
        private static IPAddressStringParameters DEFAULT_MIXED_OPTS = new IPAddressStringParameters(false, false, false, false, false, false, false, true, false, new IPv4AddressStringParameters.Builder().toParams(), new IPv6AddressStringParameters(false, false, false, false, null, true, false, false, AddressStringParameters.AddressStringFormatParameters.DEFAULT_RANGE_OPTIONS, false, false, false, null));

        public Builder allowBase85(boolean allow) {
            this.allowBase85 = allow;
            return this;
        }

        public Builder allowZone(boolean allow) {
            this.getEmbeddedIPv4ParametersBuilder().getIPv6AddressParametersBuilder().allowZone = allow;
            this.allowZone = allow;
            return this;
        }

        public Builder allowEmptyZone(boolean allow) {
            this.getEmbeddedIPv4ParametersBuilder().getIPv6AddressParametersBuilder().allowEmptyZone = allow;
            this.allowEmptyZone = allow;
            return this;
        }

        public Builder allow_mixed_inet_aton(boolean allow) {
            if (allow) {
                this.allowMixed(allow);
            }
            this.getEmbeddedIPv4ParametersBuilder().getIPv4AddressParametersBuilder().allow_inet_aton(allow);
            return this;
        }

        public Builder allowMixed(boolean allow) {
            this.allowMixed = allow;
            return this;
        }

        public IPv4AddressStringParameters.Builder getEmbeddedIPv4AddressParametersBuilder() {
            return this.getEmbeddedIPv4ParametersBuilder().getIPv4AddressParametersBuilder();
        }

        IPAddressStringParameters.Builder getEmbeddedIPv4ParametersBuilder() {
            if (this.embeddedIPv4OptionsBuilder == null) {
                this.embeddedIPv4OptionsBuilder = new IPAddressStringParameters.Builder().allowEmpty(false).allowPrefix(false).allowMask(false).allowPrefixOnly(false).allowAll(false).allowIPv6(false);
                this.embeddedIPv4OptionsBuilder.getIPv6AddressParametersBuilder().allowZone = this.allowZone;
                this.embeddedIPv4OptionsBuilder.getIPv6AddressParametersBuilder().allowEmptyZone = this.allowEmptyZone;
            }
            Builder.setMixedParentInst(this, this.embeddedIPv4OptionsBuilder.getIPv4AddressParametersBuilder());
            return this.embeddedIPv4OptionsBuilder;
        }

        public Builder setNetwork(IPv6AddressNetwork network) {
            this.network = network;
            return this;
        }

        @Override
        public Builder allowBinary(boolean allow) {
            this.getEmbeddedIPv4AddressParametersBuilder().allowBinary(allow);
            super.allowBinary(allow);
            return this;
        }

        @Override
        public Builder allowWildcardedSeparator(boolean allow) {
            this.getEmbeddedIPv4AddressParametersBuilder().allowWildcardedSeparator(allow);
            super.allowWildcardedSeparator(allow);
            return this;
        }

        @Override
        public Builder allowLeadingZeros(boolean allow) {
            this.getEmbeddedIPv4AddressParametersBuilder().allowLeadingZeros(allow);
            super.allowLeadingZeros(allow);
            return this;
        }

        @Override
        public Builder allowUnlimitedLeadingZeros(boolean allow) {
            this.getEmbeddedIPv4AddressParametersBuilder().allowUnlimitedLeadingZeros(allow);
            super.allowUnlimitedLeadingZeros(allow);
            return this;
        }

        @Override
        public Builder setRangeOptions(AddressStringParameters.RangeParameters rangeOptions) {
            this.getEmbeddedIPv4ParametersBuilder().getIPv4AddressParametersBuilder().setRangeOptions(rangeOptions);
            super.setRangeOptions(rangeOptions);
            return this;
        }

        @Override
        public Builder allowPrefixesBeyondAddressSize(boolean allow) {
            super.allowPrefixesBeyondAddressSize(allow);
            return this;
        }

        @Override
        public Builder allowPrefixLengthLeadingZeros(boolean allow) {
            super.allowPrefixLengthLeadingZeros(allow);
            return this;
        }

        public IPv6AddressStringParameters toParams() {
            IPAddressStringParameters mixedOptions = this.embeddedIPv4OptionsBuilder == null ? DEFAULT_MIXED_OPTS : this.embeddedIPv4OptionsBuilder.toParams();
            return new IPv6AddressStringParameters(this.allowLeadingZeros, this.allowPrefixLengthLeadingZeros, this.allowUnlimitedLeadingZeros, this.allowMixed, mixedOptions, this.allowZone, this.allowEmptyZone, this.allowBase85, this.rangeOptions, this.allowWildcardedSeparator, this.allowPrefixesBeyondAddressSize, this.allowBinary, this.network);
        }
    }
}

