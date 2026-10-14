/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.validate;

import inet.ipaddr.Address;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.IPAddressStringParameters;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.format.AddressDivisionGroupingBase;
import inet.ipaddr.format.AddressItem;
import inet.ipaddr.format.IPAddressDivisionSeries;
import inet.ipaddr.format.large.IPAddressLargeDivision;
import inet.ipaddr.format.large.IPAddressLargeDivisionGrouping;
import inet.ipaddr.format.standard.AddressCreator;
import inet.ipaddr.format.standard.IPAddressBitsDivision;
import inet.ipaddr.format.standard.IPAddressDivision;
import inet.ipaddr.format.standard.IPAddressDivisionGrouping;
import inet.ipaddr.format.validate.ParsedAddressCreator;
import inet.ipaddr.format.validate.ParsedHost;
import inet.ipaddr.format.validate.ParsedHostIdentifierStringQualifier;
import inet.ipaddr.format.validate.ParsedIPAddress;
import inet.ipaddr.ipv6.IPv6AddressNetwork;
import java.io.Serializable;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Objects;

public interface IPAddressProvider
extends Serializable {
    public static final NullProvider INVALID_PROVIDER = new NullProvider(IPType.INVALID){

        @Override
        public boolean isInvalid() {
            return true;
        }
    };
    public static final NullProvider NO_TYPE_PROVIDER = new NullProvider(null){

        @Override
        public boolean isUninitialized() {
            return true;
        }
    };
    public static final NullProvider EMPTY_PROVIDER = new NullProvider(IPType.EMPTY){

        @Override
        public boolean isProvidingEmpty() {
            return true;
        }
    };

    public IPType getType();

    public IPAddress getProviderHostAddress() throws IncompatibleAddressException;

    public IPAddress getProviderAddress() throws IncompatibleAddressException;

    public IPAddress getProviderAddress(IPAddress.IPVersion var1) throws IncompatibleAddressException;

    default public boolean isSequential() {
        try {
            IPAddress addr = this.getProviderAddress();
            if (addr != null) {
                return addr.isSequential();
            }
        }
        catch (IncompatibleAddressException incompatibleAddressException) {
            // empty catch block
        }
        return false;
    }

    default public IPAddressSeqRange getProviderSeqRange() {
        IPAddress addr = this.getProviderAddress();
        if (addr != null) {
            return addr.toSequentialRange();
        }
        return null;
    }

    default public IPAddress getProviderMask() {
        return null;
    }

    default public IPAddressDivisionSeries getDivisionGrouping() throws IncompatibleAddressException {
        return this.getProviderAddress();
    }

    default public int providerCompare(IPAddressProvider other) throws IncompatibleAddressException {
        IPAddress otherValue;
        if (this == other) {
            return 0;
        }
        IPAddress value = this.getProviderAddress();
        if (value != null && (otherValue = other.getProviderAddress()) != null) {
            return value.compareTo(otherValue);
        }
        IPType thisType = this.getType();
        IPType otherType = other.getType();
        if (thisType == null) {
            return otherType == null ? 0 : -1;
        }
        if (otherType == null) {
            return 1;
        }
        return thisType.ordinal() - otherType.ordinal();
    }

    default public boolean providerEquals(IPAddressProvider other) throws IncompatibleAddressException {
        if (this == other) {
            return true;
        }
        IPAddress value = this.getProviderAddress();
        if (value != null) {
            IPAddress otherValue = other.getProviderAddress();
            if (otherValue != null) {
                return value.equals(otherValue);
            }
            return false;
        }
        return this.getType() == other.getType();
    }

    default public int providerHashCode() throws IncompatibleAddressException {
        IPAddress value = this.getProviderAddress();
        if (value != null) {
            return value.hashCode();
        }
        return Objects.hashCode((Object)this.getType());
    }

    default public IPAddress.IPVersion getProviderIPVersion() {
        return null;
    }

    default public boolean isProvidingIPAddress() {
        return false;
    }

    default public boolean isProvidingIPv4() {
        return false;
    }

    default public boolean isProvidingIPv6() {
        return false;
    }

    default public boolean isProvidingPrefixOnly() {
        return false;
    }

    default public boolean isProvidingAllAddresses() {
        return false;
    }

    default public boolean isProvidingEmpty() {
        return false;
    }

    default public boolean isProvidingMixedIPv6() {
        return false;
    }

    default public boolean isProvidingBase85IPv6() {
        return false;
    }

    default public Integer getProviderNetworkPrefixLength() {
        return null;
    }

    default public boolean isInvalid() {
        return false;
    }

    default public boolean isUninitialized() {
        return false;
    }

    default public Boolean contains(IPAddressProvider other) {
        return null;
    }

    default public Boolean contains(String other) {
        return null;
    }

    default public Boolean prefixEquals(String other) {
        return null;
    }

    default public Boolean prefixEquals(IPAddressProvider other) {
        return null;
    }

    default public Boolean prefixContains(String other) {
        return null;
    }

    default public Boolean prefixContains(IPAddressProvider other) {
        return null;
    }

    default public Boolean parsedEquals(IPAddressProvider other) {
        return null;
    }

    default public boolean hasPrefixSeparator() {
        return this.getProviderNetworkPrefixLength() != null;
    }

    default public IPAddressStringParameters getParameters() {
        return null;
    }

    public static IPAddressProvider getProviderFor(IPAddress address, IPAddress hostAddress) {
        return new CachedAddressProvider(address, hostAddress);
    }

    public static class AllCreator
    extends AdjustedAddressCreator {
        private static final long serialVersionUID = 4L;
        HostIdentifierString originator;
        ParsedHostIdentifierStringQualifier qualifier;

        AllCreator(ParsedHostIdentifierStringQualifier qualifier, HostIdentifierString originator, IPAddressStringParameters options) {
            super(qualifier.getEquivalentPrefixLength(), options);
            this.originator = originator;
            this.qualifier = qualifier;
        }

        AllCreator(ParsedHostIdentifierStringQualifier qualifier, IPAddress.IPVersion adjustedVersion, HostIdentifierString originator, IPAddressStringParameters options) {
            super(qualifier.getEquivalentPrefixLength(), adjustedVersion, options);
            this.originator = originator;
            this.qualifier = qualifier;
        }

        @Override
        IPAddress createVersionedAddress(IPAddress.IPVersion version) {
            return ParsedIPAddress.createAllAddress(version, this.qualifier, this.originator, this.options);
        }

        @Override
        public IPType getType() {
            if (this.adjustedVersion != null) {
                return IPType.from(this.adjustedVersion);
            }
            return IPType.ALL;
        }

        @Override
        public Boolean contains(IPAddressProvider otherProvider) {
            if (otherProvider.isInvalid()) {
                return Boolean.FALSE;
            }
            if (this.adjustedVersion == null) {
                return Boolean.TRUE;
            }
            return this.adjustedVersion == otherProvider.getProviderIPVersion();
        }

        @Override
        public boolean isProvidingAllAddresses() {
            return this.adjustedVersion == null;
        }

        @Override
        public Integer getProviderNetworkPrefixLength() {
            return this.qualifier.getEquivalentPrefixLength();
        }

        @Override
        public int providerHashCode() {
            if (this.adjustedVersion == null) {
                return IPAddress.SEGMENT_WILDCARD_STR.hashCode();
            }
            return super.hashCode();
        }

        @Override
        ParsedIPAddress.CachedIPAddresses<?> createAddresses() {
            if (this.qualifier.equals(ParsedHost.NO_QUALIFIER)) {
                return new ParsedIPAddress.CachedIPAddresses<IPAddress>(ParsedIPAddress.createAllAddress(this.adjustedVersion, this.qualifier, this.originator, this.options));
            }
            return new ParsedIPAddress.CachedIPAddresses<IPAddress>(ParsedIPAddress.createAllAddress(this.adjustedVersion, this.qualifier, this.originator, this.options), ParsedIPAddress.createAllAddress(this.adjustedVersion, this.qualifier.getZone() != null ? new ParsedHostIdentifierStringQualifier(this.qualifier.getZone()) : ParsedHost.NO_QUALIFIER, this.originator, this.options));
        }

        @Override
        public IPAddress getProviderMask() {
            return this.qualifier.getMaskLower();
        }

        @Override
        public IPAddressSeqRange getProviderSeqRange() {
            if (this.isProvidingAllAddresses()) {
                return null;
            }
            IPAddress mask = this.getProviderMask();
            if (mask != null && mask.getBlockMaskPrefixLength(true) == null) {
                IPAddress all = ParsedIPAddress.createAllAddress(this.adjustedVersion, ParsedHost.NO_QUALIFIER, null, this.options);
                IPAddress upper = all.getUpper().mask(mask);
                IPAddress lower = all.getLower();
                return lower.spanWithRange(upper);
            }
            return super.getProviderSeqRange();
        }

        @Override
        public boolean isSequential() {
            return !this.isProvidingAllAddresses();
        }

        @Override
        public IPAddressDivisionSeries getDivisionGrouping() throws IncompatibleAddressException {
            AddressDivisionGroupingBase grouping;
            if (this.isProvidingAllAddresses()) {
                return null;
            }
            IPAddressNetwork network = this.adjustedVersion.isIPv4() ? this.options.getIPv4Parameters().getNetwork() : this.options.getIPv6Parameters().getNetwork();
            IPAddress mask = this.getProviderMask();
            if (mask != null && mask.getBlockMaskPrefixLength(true) == null) {
                Integer hostMaskPrefixLen = mask.getBlockMaskPrefixLength(false);
                if (hostMaskPrefixLen == null) {
                    throw new IncompatibleAddressException((AddressItem)this.getProviderAddress(), mask, "ipaddress.error.maskMismatch");
                }
                Object hostMask = network.getHostMask(hostMaskPrefixLen);
                return ((IPAddress)hostMask).toPrefixBlock();
            }
            if (this.adjustedVersion.isIPv4()) {
                grouping = new IPAddressDivisionGrouping((IPAddressDivision[])new IPAddressBitsDivision[]{new IPAddressBitsDivision(0L, -1L, 32, 10, network, this.qualifier.getEquivalentPrefixLength())}, network);
            } else if (this.adjustedVersion.isIPv6()) {
                byte[] upperBytes = new byte[16];
                Arrays.fill(upperBytes, (byte)-1);
                grouping = new IPAddressLargeDivisionGrouping(new IPAddressLargeDivision[]{new IPAddressLargeDivision(new byte[16], upperBytes, 128, 16, network, this.qualifier.getEquivalentPrefixLength())}, network);
            } else {
                grouping = null;
            }
            return grouping;
        }
    }

    public static class LoopbackCreator
    extends VersionedAddressCreator {
        private static final long serialVersionUID = 4L;
        private final CharSequence zone;

        LoopbackCreator(IPAddressStringParameters options) {
            this(null, options);
        }

        LoopbackCreator(CharSequence zone, IPAddressStringParameters options) {
            super(options);
            this.zone = zone;
        }

        @Override
        IPAddress createVersionedAddress(IPAddress.IPVersion version) {
            if (this.values != null && version.equals((Object)((IPAddress)this.values.getAddress()).getIPVersion())) {
                return this.values.getAddress();
            }
            IPAddressNetwork network = version.isIPv4() ? this.options.getIPv4Parameters().getNetwork() : this.options.getIPv6Parameters().getNetwork();
            Object address = network.getLoopback();
            if (this.zone != null && this.zone.length() > 0 && version.isIPv6()) {
                AddressCreator addressCreator = network.getAddressCreator();
                return (IPAddress)addressCreator.createAddressInternal(((Address)address).getBytes(), this.zone);
            }
            return address;
        }

        ParsedIPAddress.CachedIPAddresses<IPAddress> createAddresses() {
            Object result;
            InetAddress loopback = InetAddress.getLoopbackAddress();
            boolean isIPv6 = loopback instanceof Inet6Address;
            if (this.zone != null && this.zone.length() > 0 && isIPv6) {
                IPv6AddressNetwork.IPv6AddressCreator addressCreator = this.options.getIPv6Parameters().getNetwork().getAddressCreator();
                result = (IPAddress)((ParsedAddressCreator)addressCreator).createAddressInternal(loopback.getAddress(), this.zone);
            } else {
                result = isIPv6 ? this.options.getIPv6Parameters().getNetwork().getLoopback() : this.options.getIPv4Parameters().getNetwork().getLoopback();
            }
            return new ParsedIPAddress.CachedIPAddresses<IPAddress>((IPAddress)result);
        }

        @Override
        public Integer getProviderNetworkPrefixLength() {
            return null;
        }
    }

    public static class MaskCreator
    extends AdjustedAddressCreator {
        private static final long serialVersionUID = 4L;

        MaskCreator(Integer networkPrefixLength, IPAddressStringParameters options) {
            super(networkPrefixLength, options);
        }

        MaskCreator(Integer networkPrefixLength, IPAddress.IPVersion adjustedVersion, IPAddressStringParameters options) {
            super(networkPrefixLength, adjustedVersion, options);
        }

        @Override
        public int providerHashCode() {
            if (this.adjustedVersion == null) {
                return this.getProviderNetworkPrefixLength();
            }
            return this.getProviderAddress().hashCode();
        }

        @Override
        public boolean providerEquals(IPAddressProvider valueProvider) {
            if (valueProvider == this) {
                return true;
            }
            if (this.adjustedVersion == null) {
                if (valueProvider.getType() == IPType.PREFIX_ONLY) {
                    return valueProvider.getProviderNetworkPrefixLength().intValue() == this.getProviderNetworkPrefixLength().intValue();
                }
                return false;
            }
            return super.providerEquals(valueProvider);
        }

        @Override
        public int providerCompare(IPAddressProvider other) throws IncompatibleAddressException {
            if (this == other) {
                return 0;
            }
            if (this.adjustedVersion == null) {
                if (other.getType() == IPType.PREFIX_ONLY) {
                    return other.getProviderNetworkPrefixLength() - this.getProviderNetworkPrefixLength();
                }
                return IPType.PREFIX_ONLY.ordinal() - other.getType().ordinal();
            }
            IPAddress otherValue = other.getProviderAddress();
            if (otherValue != null) {
                return this.getProviderAddress().compareTo(otherValue);
            }
            return IPType.from(this.adjustedVersion).ordinal() - other.getType().ordinal();
        }

        private IPAddress createVersionedMask(IPAddress.IPVersion version, int bits, boolean withPrefixLength) {
            IPAddressNetwork network = version.isIPv4() ? this.options.getIPv4Parameters().getNetwork() : this.options.getIPv6Parameters().getNetwork();
            return withPrefixLength ? network.getNetworkAddress(bits) : network.getNetworkMask(bits, false);
        }

        @Override
        IPAddress createVersionedAddress(IPAddress.IPVersion version) {
            return this.createVersionedMask(version, this.getProviderNetworkPrefixLength(), true);
        }

        @Override
        public IPType getType() {
            if (this.adjustedVersion != null) {
                return IPType.from(this.adjustedVersion);
            }
            return IPType.PREFIX_ONLY;
        }

        @Override
        public boolean isProvidingPrefixOnly() {
            return this.adjustedVersion == null;
        }

        @Override
        ParsedIPAddress.CachedIPAddresses<?> createAddresses() {
            return new ParsedIPAddress.CachedIPAddresses<IPAddress>(this.createVersionedMask(this.adjustedVersion, this.getProviderNetworkPrefixLength(), true), this.createVersionedMask(this.adjustedVersion, this.getProviderNetworkPrefixLength(), false));
        }
    }

    public static abstract class AdjustedAddressCreator
    extends VersionedAddressCreator {
        private static final long serialVersionUID = 4L;
        protected final IPAddress.IPVersion adjustedVersion;
        protected final Integer networkPrefixLength;

        AdjustedAddressCreator(Integer networkPrefixLength, IPAddressStringParameters options) {
            this(networkPrefixLength, null, options);
        }

        AdjustedAddressCreator(Integer networkPrefixLength, IPAddress.IPVersion adjustedVersion, IPAddressStringParameters options) {
            super(options);
            this.networkPrefixLength = networkPrefixLength;
            this.adjustedVersion = adjustedVersion;
        }

        @Override
        public boolean isProvidingIPAddress() {
            return this.adjustedVersion != null;
        }

        @Override
        public boolean isProvidingIPv4() {
            return this.isProvidingIPAddress() && this.adjustedVersion.isIPv4();
        }

        @Override
        public boolean isProvidingIPv6() {
            return this.isProvidingIPAddress() && this.adjustedVersion.isIPv6();
        }

        @Override
        public IPAddress.IPVersion getProviderIPVersion() {
            return this.adjustedVersion;
        }

        @Override
        public Integer getProviderNetworkPrefixLength() {
            return this.networkPrefixLength;
        }

        @Override
        public IPAddress getProviderAddress() {
            if (this.adjustedVersion == null) {
                return null;
            }
            return super.getProviderAddress();
        }

        @Override
        public IPAddress getProviderHostAddress() {
            if (this.adjustedVersion == null) {
                return null;
            }
            return super.getProviderHostAddress();
        }
    }

    public static abstract class VersionedAddressCreator
    extends CachedAddressProvider {
        private static final long serialVersionUID = 4L;
        IPAddress[] versionedValues;
        protected final IPAddressStringParameters options;

        VersionedAddressCreator(IPAddressStringParameters options) {
            this.options = options;
        }

        @Override
        public IPAddressStringParameters getParameters() {
            return this.options;
        }

        private IPAddress checkResult(IPAddress.IPVersion version, int index) {
            IPAddress result = this.versionedValues[index];
            if (result == null) {
                this.versionedValues[index] = result = this.createVersionedAddress(version);
            }
            return result;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public IPAddress getProviderAddress(IPAddress.IPVersion version) {
            IPAddress result;
            int index = version.ordinal();
            if (this.versionedValues == null) {
                VersionedAddressCreator versionedAddressCreator = this;
                synchronized (versionedAddressCreator) {
                    if (this.versionedValues == null) {
                        this.versionedValues = new IPAddress[IPAddress.IPVersion.values().length];
                        this.versionedValues[index] = result = this.createVersionedAddress(version);
                    } else {
                        result = this.checkResult(version, index);
                    }
                }
            }
            result = this.versionedValues[index];
            if (result == null) {
                VersionedAddressCreator versionedAddressCreator = this;
                synchronized (versionedAddressCreator) {
                    result = this.checkResult(version, index);
                }
            }
            return result;
        }

        abstract IPAddress createVersionedAddress(IPAddress.IPVersion var1);
    }

    public static class CachedAddressProvider
    implements IPAddressProvider {
        private static final long serialVersionUID = 4L;
        ParsedIPAddress.CachedIPAddresses<?> values;

        CachedAddressProvider() {
        }

        private CachedAddressProvider(IPAddress address, IPAddress hostAddress) {
            this.values = new ParsedIPAddress.CachedIPAddresses<IPAddress>(address, hostAddress);
        }

        @Override
        public IPAddress getProviderAddress(IPAddress.IPVersion version) {
            IPAddress.IPVersion thisVersion = this.getProviderIPVersion();
            if (!version.equals((Object)thisVersion)) {
                return null;
            }
            return this.getProviderAddress();
        }

        ParsedIPAddress.CachedIPAddresses<?> createAddresses() {
            return null;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private ParsedIPAddress.CachedIPAddresses<?> getCachedAddresses() {
            ParsedIPAddress.CachedIPAddresses<?> val = this.values;
            if (val == null) {
                CachedAddressProvider cachedAddressProvider = this;
                synchronized (cachedAddressProvider) {
                    val = this.values;
                    if (val == null) {
                        this.values = val = this.createAddresses();
                    }
                }
            }
            return val;
        }

        @Override
        public IPAddress getProviderHostAddress() {
            return this.getCachedAddresses().getHostAddress();
        }

        @Override
        public IPAddress getProviderAddress() {
            return this.getCachedAddresses().getAddress();
        }

        @Override
        public Integer getProviderNetworkPrefixLength() {
            return this.getProviderAddress().getNetworkPrefixLength();
        }

        @Override
        public IPAddress.IPVersion getProviderIPVersion() {
            return this.getProviderAddress().getIPVersion();
        }

        @Override
        public IPType getType() {
            return IPType.from(this.getProviderIPVersion());
        }

        @Override
        public boolean isProvidingIPAddress() {
            return true;
        }

        @Override
        public boolean isProvidingIPv4() {
            return this.getProviderAddress().isIPv4();
        }

        @Override
        public boolean isProvidingIPv6() {
            return this.getProviderAddress().isIPv6();
        }

        public String toString() {
            return String.valueOf(this.getProviderAddress());
        }
    }

    public static abstract class NullProvider
    implements IPAddressProvider {
        private static final long serialVersionUID = 4L;
        private IPType type;

        public NullProvider(IPType type) {
            this.type = type;
        }

        @Override
        public IPType getType() {
            return this.type;
        }

        @Override
        public IPAddress getProviderHostAddress() {
            return null;
        }

        @Override
        public IPAddress getProviderAddress() {
            return null;
        }

        @Override
        public IPAddress getProviderAddress(IPAddress.IPVersion version) {
            return null;
        }

        @Override
        public int providerHashCode() {
            return Objects.hashCode((Object)this.getType());
        }

        @Override
        public boolean providerEquals(IPAddressProvider o) {
            if (this == o) {
                return true;
            }
            if (o instanceof NullProvider) {
                NullProvider other = (NullProvider)o;
                return this.getType() == other.getType();
            }
            return false;
        }

        public String toString() {
            return String.valueOf((Object)this.getType());
        }
    }

    public static enum IPType {
        INVALID,
        EMPTY,
        IPV4,
        IPV6,
        PREFIX_ONLY,
        ALL;


        static IPType from(IPAddress.IPVersion version) {
            switch (version) {
                case IPV4: {
                    return IPV4;
                }
                case IPV6: {
                    return IPV6;
                }
            }
            return null;
        }
    }
}

