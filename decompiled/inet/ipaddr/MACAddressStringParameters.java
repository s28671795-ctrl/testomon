/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.Address;
import inet.ipaddr.AddressStringParameters;
import inet.ipaddr.mac.MACAddressNetwork;

public class MACAddressStringParameters
extends AddressStringParameters
implements Comparable<MACAddressStringParameters> {
    private static final long serialVersionUID = 4L;
    public static final boolean DEFAULT_ALLOW_DASHED = true;
    public static final boolean DEFAULT_ALLOW_SINGLE_DASHED = true;
    public static final boolean DEFAULT_ALLOW_COLON_DELIMITED = true;
    public static final boolean DEFAULT_ALLOW_DOTTED = true;
    public static final boolean DEFAULT_ALLOW_SPACE_DELIMITED = true;
    public final AddressSize addressSize;
    public final boolean allowDashed;
    public final boolean allowSingleDashed;
    public final boolean allowColonDelimited;
    public final boolean allowDotted;
    public final boolean allowSpaceDelimited;
    private final MACAddressNetwork network;
    private MACAddressStringFormatParameters formatOpts;

    public MACAddressStringParameters(boolean allowEmpty, boolean allowAll, AddressSize allAddresses, boolean allowSingleSegment, boolean allowDashed, boolean allowSingleDashed, boolean allowColonDelimited, boolean allowDotted, boolean allowSpaceDelimited, MACAddressStringFormatParameters formatOpts, MACAddressNetwork network) {
        super(allowEmpty, allowAll, allowSingleSegment);
        this.allowDashed = allowDashed;
        this.allowSingleDashed = allowSingleDashed;
        this.allowColonDelimited = allowColonDelimited;
        this.allowDotted = allowDotted;
        this.allowSpaceDelimited = allowSpaceDelimited;
        this.formatOpts = formatOpts;
        this.addressSize = allAddresses;
        this.network = network;
    }

    public Builder toBuilder() {
        Builder builder = new Builder();
        super.toBuilder(builder);
        builder.allowDashed = this.allowDashed;
        builder.allowSingleDashed = this.allowSingleDashed;
        builder.allowColonDelimited = this.allowColonDelimited;
        builder.allowDotted = this.allowDotted;
        builder.allowSpaceDelimited = this.allowSpaceDelimited;
        builder.formatBuilder = this.formatOpts.toBuilder();
        builder.allAddresses = this.addressSize;
        builder.network = this.network;
        return builder;
    }

    public MACAddressNetwork getNetwork() {
        if (this.network == null) {
            return Address.defaultMACNetwork();
        }
        return this.network;
    }

    public MACAddressStringFormatParameters getFormatParameters() {
        return this.formatOpts;
    }

    @Override
    public MACAddressStringParameters clone() {
        MACAddressStringParameters result = (MACAddressStringParameters)super.clone();
        result.formatOpts = this.formatOpts.clone();
        return result;
    }

    @Override
    public int compareTo(MACAddressStringParameters o) {
        int result = super.compareTo(o);
        if (result == 0 && (result = this.formatOpts.compareTo(o.formatOpts)) == 0 && (result = Boolean.compare(this.allowDashed, o.allowDashed)) == 0 && (result = Boolean.compare(this.allowSingleDashed, o.allowSingleDashed)) == 0 && (result = Boolean.compare(this.allowColonDelimited, o.allowColonDelimited)) == 0 && (result = Boolean.compare(this.allowDotted, o.allowDotted)) == 0 && (result = Boolean.compare(this.allowSpaceDelimited, o.allowSpaceDelimited)) == 0) {
            result = this.addressSize.ordinal() - o.addressSize.ordinal();
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MACAddressStringParameters) {
            MACAddressStringParameters other = (MACAddressStringParameters)o;
            return super.equals(o) && this.formatOpts.equals(other.formatOpts) && this.allowDashed == other.allowDashed && this.allowSingleDashed == other.allowSingleDashed && this.allowColonDelimited == other.allowColonDelimited && this.allowDotted == other.allowDotted && this.allowSpaceDelimited == other.allowSpaceDelimited && this.addressSize == other.addressSize;
        }
        return false;
    }

    public int hashCode() {
        int hash = this.formatOpts.hashCode();
        if (this.allowAll) {
            hash |= 0x80;
        }
        if (this.allowDashed) {
            hash |= 0x100;
        }
        if (this.allowColonDelimited) {
            hash |= 0x200;
        }
        if (this.allowDotted) {
            hash |= 0x400;
        }
        if (this.allowSpaceDelimited) {
            hash |= 0x800;
        }
        if (this.allowSingleSegment) {
            hash |= 0x1000;
        }
        if (this.addressSize == AddressSize.MAC) {
            hash |= 0x2000;
        } else if (this.addressSize == AddressSize.EUI64) {
            hash |= 0x4000;
        }
        if (this.allowSingleDashed) {
            hash |= 0x8000;
        }
        if (this.allowEmpty) {
            hash |= 0x10000;
        }
        return hash;
    }

    public static class MACAddressStringFormatParameters
    extends AddressStringParameters.AddressStringFormatParameters
    implements Comparable<MACAddressStringFormatParameters> {
        private static final long serialVersionUID = 4L;
        public static final boolean DEFAULT_ALLOW_SHORT_SEGMENTS = true;
        public final boolean allowShortSegments;

        public MACAddressStringFormatParameters(boolean allowShortSegments, boolean allowLeadingZeros, boolean allowUnlimitedLeadingZeros, AddressStringParameters.RangeParameters rangeOptions, boolean allowWildcardedSeparator) {
            super(allowLeadingZeros, allowUnlimitedLeadingZeros, rangeOptions, allowWildcardedSeparator);
            this.allowShortSegments = allowShortSegments;
        }

        public Builder toBuilder() {
            Builder builder = new Builder();
            super.toBuilder(builder);
            builder.allowShortSegments = this.allowShortSegments;
            return builder;
        }

        @Override
        public int compareTo(MACAddressStringFormatParameters o) {
            int result = super.compareTo(o);
            if (result == 0) {
                result = Boolean.compare(this.allowShortSegments, o.allowShortSegments);
            }
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof MACAddressStringFormatParameters) {
                MACAddressStringFormatParameters other = (MACAddressStringFormatParameters)o;
                return super.equals(o) && this.allowShortSegments == other.allowShortSegments;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = super.hashCode();
            if (this.allowShortSegments) {
                hash |= 0x40;
            }
            return hash;
        }

        public MACAddressStringFormatParameters clone() {
            try {
                return (MACAddressStringFormatParameters)super.clone();
            }
            catch (CloneNotSupportedException e) {
                return null;
            }
        }

        public static class Builder
        extends AddressStringParameters.AddressStringFormatParameters.BuilderBase {
            boolean allowShortSegments = true;
            inet.ipaddr.MACAddressStringParameters$Builder parent;

            public inet.ipaddr.MACAddressStringParameters$Builder getParentBuilder() {
                return this.parent;
            }

            public Builder allowShortSegments(boolean allow) {
                this.allowShortSegments = allow;
                return this;
            }

            @Override
            public Builder setRangeOptions(AddressStringParameters.RangeParameters rangeOptions) {
                return (Builder)super.setRangeOptions(rangeOptions);
            }

            @Override
            public Builder allowWildcardedSeparator(boolean allow) {
                return (Builder)super.allowWildcardedSeparator(allow);
            }

            @Override
            public Builder allowLeadingZeros(boolean allow) {
                return (Builder)super.allowLeadingZeros(allow);
            }

            @Override
            public Builder allowUnlimitedLeadingZeros(boolean allow) {
                return (Builder)super.allowUnlimitedLeadingZeros(allow);
            }

            MACAddressStringFormatParameters toParams() {
                return new MACAddressStringFormatParameters(this.allowShortSegments, this.allowLeadingZeros, this.allowUnlimitedLeadingZeros, this.rangeOptions, this.allowWildcardedSeparator);
            }
        }
    }

    public static class Builder
    extends AddressStringParameters.BuilderBase {
        private boolean allowDashed = true;
        private boolean allowSingleDashed = true;
        private boolean allowColonDelimited = true;
        private boolean allowDotted = true;
        private boolean allowSpaceDelimited = true;
        private AddressSize allAddresses = AddressSize.ANY;
        private MACAddressNetwork network;
        MACAddressStringFormatParameters.Builder formatBuilder;
        private static MACAddressStringFormatParameters DEFAULT_FORMAT_OPTS = new MACAddressStringFormatParameters.Builder().toParams();

        @Override
        public Builder allowEmpty(boolean allow) {
            return (Builder)super.allowEmpty(allow);
        }

        @Override
        public Builder allowSingleSegment(boolean allow) {
            return (Builder)super.allowSingleSegment(allow);
        }

        public Builder allowDashed(boolean bool) {
            this.allowDashed = bool;
            return this;
        }

        public Builder allowColonDelimited(boolean allow) {
            this.allowColonDelimited = allow;
            return this;
        }

        public Builder allowDotted(boolean allow) {
            this.allowDotted = allow;
            return this;
        }

        public Builder allowSpaceDelimited(boolean allow) {
            this.allowSpaceDelimited = allow;
            return this;
        }

        @Override
        public Builder allowAll(boolean allow) {
            return (Builder)super.allowAll(allow);
        }

        public Builder setAllAddresses(AddressSize all) {
            this.allAddresses = all;
            return this;
        }

        public Builder setNetwork(MACAddressNetwork network) {
            this.network = network;
            return this;
        }

        public Builder allowWildcardedSeparator(boolean allow) {
            this.getFormatBuilder().allowWildcardedSeparator(allow);
            return this;
        }

        public Builder setRangeOptions(AddressStringParameters.RangeParameters rangeOptions) {
            this.getFormatBuilder().setRangeOptions(rangeOptions);
            return this;
        }

        public MACAddressStringFormatParameters.Builder getFormatBuilder() {
            if (this.formatBuilder == null) {
                this.formatBuilder = new MACAddressStringFormatParameters.Builder();
            }
            this.formatBuilder.parent = this;
            return this.formatBuilder;
        }

        public MACAddressStringParameters toParams() {
            MACAddressStringFormatParameters formatOpts = this.formatBuilder == null ? DEFAULT_FORMAT_OPTS : this.formatBuilder.toParams();
            return new MACAddressStringParameters(this.allowEmpty, this.allowAll, this.allAddresses, this.allowSingleSegment, this.allowDashed, this.allowSingleDashed, this.allowColonDelimited, this.allowDotted, this.allowSpaceDelimited, formatOpts, this.network);
        }
    }

    public static enum AddressSize {
        MAC,
        EUI64,
        ANY;

    }
}

