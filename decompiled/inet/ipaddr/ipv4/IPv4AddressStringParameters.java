/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv4;

import inet.ipaddr.Address;
import inet.ipaddr.AddressStringParameters;
import inet.ipaddr.IPAddressStringParameters;
import inet.ipaddr.ipv4.IPv4AddressNetwork;
import inet.ipaddr.ipv6.IPv6AddressStringParameters;

public class IPv4AddressStringParameters
extends IPAddressStringParameters.IPAddressStringFormatParameters
implements Comparable<IPv4AddressStringParameters> {
    private static final long serialVersionUID = 4L;
    public static final boolean DEFAULT_ALLOW_IPV4_INET_ATON = true;
    public static final boolean DEFAULT_ALLOW_IPV4_INET_ATON_SINGLE_SEGMENT_MASK = false;
    public final boolean inet_aton_hex;
    public final boolean inet_aton_octal;
    public final boolean inet_aton_leading_zeros;
    public final boolean inet_aton_joinedSegments;
    public final boolean inet_aton_single_segment_mask;
    private final IPv4AddressNetwork network;

    @Deprecated
    public IPv4AddressStringParameters(boolean allowLeadingZeros, boolean allowCIDRPrefixLeadingZeros, boolean allowUnlimitedLeadingZeros, AddressStringParameters.RangeParameters rangeOptions, boolean allowWildcardedSeparator, boolean allowPrefixesBeyondAddressSize, boolean inet_aton_hex, boolean inet_aton_octal, boolean inet_aton_leading_zeros, boolean inet_aton_joinedSegments, boolean inet_aton_single_segment_mask, IPv4AddressNetwork network) {
        this(allowLeadingZeros, allowCIDRPrefixLeadingZeros, allowUnlimitedLeadingZeros, rangeOptions, allowWildcardedSeparator, allowPrefixesBeyondAddressSize, false, inet_aton_hex, inet_aton_octal, inet_aton_leading_zeros, inet_aton_joinedSegments, inet_aton_single_segment_mask, network);
    }

    public IPv4AddressStringParameters(boolean allowLeadingZeros, boolean allowCIDRPrefixLeadingZeros, boolean allowUnlimitedLeadingZeros, AddressStringParameters.RangeParameters rangeOptions, boolean allowWildcardedSeparator, boolean allowPrefixesBeyondAddressSize, boolean allowBinary, boolean inet_aton_hex, boolean inet_aton_octal, boolean inet_aton_leading_zeros, boolean inet_aton_joinedSegments, boolean inet_aton_single_segment_mask, IPv4AddressNetwork network) {
        super(allowBinary, allowLeadingZeros, allowCIDRPrefixLeadingZeros, allowUnlimitedLeadingZeros, rangeOptions, allowWildcardedSeparator, allowPrefixesBeyondAddressSize);
        this.inet_aton_hex = inet_aton_hex;
        this.inet_aton_octal = inet_aton_octal;
        this.inet_aton_leading_zeros = inet_aton_leading_zeros;
        this.inet_aton_joinedSegments = inet_aton_joinedSegments;
        this.inet_aton_single_segment_mask = inet_aton_single_segment_mask;
        this.network = network;
    }

    public Builder toBuilder() {
        Builder builder = new Builder();
        builder.inet_aton_hex = this.inet_aton_hex;
        builder.inet_aton_octal = this.inet_aton_octal;
        builder.inet_aton_joinedSegments = this.inet_aton_joinedSegments;
        builder.inet_aton_single_segment_mask = this.inet_aton_single_segment_mask;
        builder.network = this.network;
        return (Builder)this.toBuilder(builder);
    }

    public IPv4AddressNetwork getNetwork() {
        if (this.network == null) {
            return Address.defaultIpv4Network();
        }
        return this.network;
    }

    public IPv4AddressStringParameters clone() {
        try {
            return (IPv4AddressStringParameters)super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public int compareTo(IPv4AddressStringParameters o) {
        int result = super.compareTo(o);
        if (result == 0 && (result = Boolean.compare(this.inet_aton_hex, o.inet_aton_hex)) == 0 && (result = Boolean.compare(this.inet_aton_octal, o.inet_aton_octal)) == 0 && (result = Boolean.compare(this.inet_aton_joinedSegments, o.inet_aton_joinedSegments)) == 0 && (result = Boolean.compare(this.inet_aton_leading_zeros, o.inet_aton_leading_zeros)) == 0) {
            result = Boolean.compare(this.inet_aton_single_segment_mask, o.inet_aton_single_segment_mask);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IPv4AddressStringParameters && super.equals(o)) {
            IPv4AddressStringParameters other = (IPv4AddressStringParameters)o;
            return this.inet_aton_hex == other.inet_aton_hex && this.inet_aton_octal == other.inet_aton_octal && this.inet_aton_joinedSegments == other.inet_aton_joinedSegments && this.inet_aton_leading_zeros == other.inet_aton_leading_zeros && this.inet_aton_single_segment_mask == other.inet_aton_single_segment_mask;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        if (this.inet_aton_hex) {
            hash |= 0x40;
        }
        if (this.inet_aton_octal) {
            hash |= 0x80;
        }
        if (this.inet_aton_joinedSegments) {
            hash |= 0x100;
        }
        return hash;
    }

    public static class Builder
    extends IPAddressStringParameters.IPAddressStringFormatParameters.BuilderBase {
        private boolean inet_aton_hex = true;
        private boolean inet_aton_octal = true;
        private boolean inet_aton_leading_zeros = true;
        private boolean inet_aton_joinedSegments = true;
        private boolean inet_aton_single_segment_mask = false;
        private IPv4AddressNetwork network;
        IPv6AddressStringParameters.Builder mixedParent;

        @Override
        protected void setMixedParent(IPv6AddressStringParameters.Builder parent) {
            this.mixedParent = parent;
        }

        public IPv6AddressStringParameters.Builder getEmbeddedIPv4AddressParentBuilder() {
            return this.mixedParent;
        }

        public Builder allow_inet_aton(boolean allow) {
            this.inet_aton_octal = this.inet_aton_hex = allow;
            this.inet_aton_joinedSegments = this.inet_aton_hex;
            super.allowUnlimitedLeadingZeros(allow);
            return this;
        }

        @Override
        public Builder allowBinary(boolean allow) {
            super.allowBinary(allow);
            return this;
        }

        public Builder allow_inet_aton_hex(boolean allow) {
            this.inet_aton_hex = allow;
            return this;
        }

        public Builder allow_inet_aton_octal(boolean allow) {
            this.inet_aton_octal = allow;
            return this;
        }

        public Builder allow_inet_aton_leading_zeros(boolean allow) {
            this.inet_aton_leading_zeros = allow;
            return this;
        }

        public Builder allow_inet_aton_joined_segments(boolean allow) {
            this.inet_aton_joinedSegments = allow;
            return this;
        }

        public Builder allow_inet_aton_single_segment_mask(boolean allow) {
            this.inet_aton_single_segment_mask = allow;
            return this;
        }

        public Builder setNetwork(IPv4AddressNetwork network) {
            this.network = network;
            return this;
        }

        @Override
        public Builder setRangeOptions(AddressStringParameters.RangeParameters rangeOptions) {
            super.setRangeOptions(rangeOptions);
            return this;
        }

        @Override
        public Builder allowPrefixesBeyondAddressSize(boolean allow) {
            super.allowPrefixesBeyondAddressSize(allow);
            return this;
        }

        @Override
        public Builder allowWildcardedSeparator(boolean allow) {
            super.allowWildcardedSeparator(allow);
            return this;
        }

        @Override
        public Builder allowLeadingZeros(boolean allow) {
            super.allowLeadingZeros(allow);
            return this;
        }

        @Override
        public Builder allowPrefixLengthLeadingZeros(boolean allow) {
            super.allowPrefixLengthLeadingZeros(allow);
            return this;
        }

        @Override
        public Builder allowUnlimitedLeadingZeros(boolean allow) {
            super.allowUnlimitedLeadingZeros(allow);
            return this;
        }

        public IPv4AddressStringParameters toParams() {
            return new IPv4AddressStringParameters(this.allowLeadingZeros, this.allowPrefixLengthLeadingZeros, this.allowUnlimitedLeadingZeros, this.rangeOptions, this.allowWildcardedSeparator, this.allowPrefixesBeyondAddressSize, this.allowBinary, this.inet_aton_hex, this.inet_aton_octal, this.inet_aton_leading_zeros, this.inet_aton_joinedSegments, this.inet_aton_single_segment_mask, this.network);
        }
    }
}

