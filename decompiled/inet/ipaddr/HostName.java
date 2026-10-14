/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.HostIdentifierString;
import inet.ipaddr.HostNameException;
import inet.ipaddr.HostNameParameters;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.IPAddressStringParameters;
import inet.ipaddr.format.validate.HostIdentifierStringValidator;
import inet.ipaddr.format.validate.ParsedHost;
import inet.ipaddr.format.validate.ParsedHostIdentifierStringQualifier;
import inet.ipaddr.format.validate.Validator;
import inet.ipaddr.ipv6.IPv6Address;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.function.Function;

public class HostName
implements HostIdentifierString,
Comparable<HostName> {
    private static final long serialVersionUID = 4L;
    private static IPAddress[] EMPTY_ADDRS = new IPAddress[0];
    public static final char LABEL_SEPARATOR = '.';
    public static final char IPV6_START_BRACKET = '[';
    public static final char IPV6_END_BRACKET = ']';
    public static final char PORT_SEPARATOR = ':';
    public static final HostNameParameters DEFAULT_VALIDATION_OPTIONS = new HostNameParameters.Builder().toParams();
    private static final HostNameParameters DEFAULT_SOCKET_ADDR_VALIDATION_OPTIONS = DEFAULT_VALIDATION_OPTIONS.toBuilder().expectPort(true).toParams();
    private final String host;
    private transient String normalizedString;
    private transient String normalizedWildcardString;
    private ParsedHost parsedHost;
    private HostNameException validationException;
    IPAddress[] resolvedAddresses;
    final HostNameParameters validationOptions;

    public HostName(IPAddress addr) {
        this.host = addr.toNormalizedString();
        this.parsedHost = new ParsedHost(this.host, addr.getProvider());
        this.validationOptions = null;
    }

    public HostName(IPAddress addr, int port) {
        ParsedHostIdentifierStringQualifier qualifier = new ParsedHostIdentifierStringQualifier(null, port);
        this.host = HostName.toNormalizedString(addr, port);
        this.parsedHost = new ParsedHost(this.host, addr.getProvider(), qualifier);
        this.validationOptions = null;
    }

    public HostName(InetSocketAddress inetSocketAddr) {
        if (!inetSocketAddr.isUnresolved()) {
            this.resolvedAddresses = new IPAddress[]{HostName.toIPAddress(inetSocketAddr.getAddress(), IPAddressString.DEFAULT_VALIDATION_OPTIONS)};
        }
        int port = inetSocketAddr.getPort();
        String hostStr = inetSocketAddr.getHostString().trim();
        StringBuilder builder = new StringBuilder(hostStr.length() + 6);
        HostName.toNormalizedString(port, builder.append(hostStr));
        this.host = builder.toString();
        this.validationOptions = DEFAULT_SOCKET_ADDR_VALIDATION_OPTIONS;
    }

    public HostName(InetAddress inetAddr, Integer prefixLength) {
        this(HostName.toIPAddress(inetAddr, IPAddressString.DEFAULT_VALIDATION_OPTIONS, prefixLength));
    }

    public HostName(InterfaceAddress interfaceAddr) {
        this(interfaceAddr.getAddress(), IPAddressNetwork.cacheBits(interfaceAddr.getNetworkPrefixLength()));
    }

    public HostName(InetAddress inetAddr) {
        this(inetAddr, IPAddressString.DEFAULT_VALIDATION_OPTIONS);
    }

    public HostName(InetAddress inetAddr, IPAddressStringParameters addressOptions) {
        this(HostName.toIPAddress(inetAddr, addressOptions));
    }

    private static IPAddress toIPAddress(InetAddress inetAddr, IPAddressStringParameters addressOptions) {
        return inetAddr instanceof Inet4Address ? addressOptions.getIPv4Parameters().getNetwork().getAddressCreator().createAddress((Inet4Address)inetAddr) : addressOptions.getIPv6Parameters().getNetwork().getAddressCreator().createAddress((Inet6Address)inetAddr);
    }

    private static IPAddress toIPAddress(InetAddress inetAddr, IPAddressStringParameters addressOptions, Integer prefixLength) {
        return inetAddr instanceof Inet4Address ? addressOptions.getIPv4Parameters().getNetwork().getAddressCreator().createAddress((Inet4Address)inetAddr, prefixLength) : addressOptions.getIPv6Parameters().getNetwork().getAddressCreator().createAddress((Inet6Address)inetAddr, prefixLength);
    }

    HostName(String hostStr, ParsedHost parsed) {
        this.host = hostStr;
        this.parsedHost = parsed;
        this.validationOptions = null;
    }

    public HostName(String host2) {
        this(host2, DEFAULT_VALIDATION_OPTIONS);
    }

    public HostName(String host2, HostNameParameters options) {
        if (options == null) {
            throw new NullPointerException();
        }
        this.validationOptions = options;
        this.host = host2 == null ? "" : host2.trim();
    }

    void cacheAddress(IPAddress addr) {
        if (this.parsedHost == null) {
            this.parsedHost = new ParsedHost(this.host, addr.getProvider());
        }
    }

    public HostNameParameters getValidationOptions() {
        return this.validationOptions;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void validate() throws HostNameException {
        if (this.parsedHost != null) {
            return;
        }
        if (this.validationException != null) {
            throw this.validationException;
        }
        HostName hostName = this;
        synchronized (hostName) {
            if (this.parsedHost != null) {
                return;
            }
            if (this.validationException != null) {
                throw this.validationException;
            }
            try {
                this.parsedHost = this.getValidator().validateHost(this);
            }
            catch (HostNameException e) {
                this.validationException = e;
                throw e;
            }
        }
    }

    protected HostIdentifierStringValidator getValidator() {
        return Validator.VALIDATOR;
    }

    public boolean isValid() {
        if (this.parsedHost != null) {
            return true;
        }
        if (this.validationException != null) {
            return false;
        }
        try {
            this.validate();
            return true;
        }
        catch (HostNameException e) {
            return false;
        }
    }

    public boolean resolvesToSelf() {
        return this.isSelf() || this.getAddress() != null && this.resolvedAddresses[0].isLoopback();
    }

    public boolean isSelf() {
        return this.isLocalHost() || this.isLoopback();
    }

    public boolean isLocalHost() {
        return this.isValid() && this.host.equalsIgnoreCase("localhost");
    }

    public boolean isLoopback() {
        return this.isAddress() && this.asAddress().isLoopback();
    }

    public InetAddress toInetAddress() throws HostNameException, UnknownHostException {
        this.validate();
        return this.toAddress().toInetAddress();
    }

    @Override
    public String toNormalizedString() {
        String result = this.normalizedString;
        if (result == null) {
            this.normalizedString = result = this.toNormalizedString(false);
        }
        return result;
    }

    private String toNormalizedWildcardString() {
        String result = this.normalizedWildcardString;
        if (result == null) {
            this.normalizedWildcardString = result = this.toNormalizedString(true);
        }
        return result;
    }

    private static void translateReserved(IPv6Address addr, String str, StringBuilder builder) {
        if (!addr.hasZone()) {
            builder.append(str);
            return;
        }
        int index = str.indexOf(37);
        StringBuilder translated = builder;
        translated.append(str, 0, index);
        translated.append("%25");
        for (int i = index + 1; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (Validator.isReserved(c)) {
                translated.append('%');
                IPAddressSegment.toUnsignedString(c, 16, translated);
                continue;
            }
            translated.append(c);
        }
    }

    private static String toNormalizedString(IPAddress addr, int port) {
        StringBuilder builder = new StringBuilder();
        HostName.toNormalizedString(addr, false, builder);
        HostName.toNormalizedString(port, builder);
        return builder.toString();
    }

    private String toNormalizedString(boolean wildcard) {
        if (this.isValid()) {
            StringBuilder builder = new StringBuilder();
            if (this.isAddress()) {
                HostName.toNormalizedString(this.asAddress(), wildcard, builder);
            } else if (this.isAddressString()) {
                builder.append(this.asAddressString().toNormalizedString());
            } else {
                builder.append(this.parsedHost.getHost());
                Integer networkPrefixLength = this.parsedHost.getEquivalentPrefixLength();
                if (networkPrefixLength != null) {
                    builder.append('/');
                    IPAddressSegment.toUnsignedString(networkPrefixLength, 10, builder);
                } else {
                    IPAddress mask = this.parsedHost.getMask();
                    if (mask != null) {
                        builder.append('/').append(mask.toNormalizedString());
                    }
                }
            }
            Integer port = this.parsedHost.getPort();
            if (port != null) {
                HostName.toNormalizedString(port, builder);
            } else {
                String service2 = this.parsedHost.getService();
                if (service2 != null) {
                    builder.append(':').append(service2);
                }
            }
            return builder.toString();
        }
        return this.host;
    }

    private static void toNormalizedString(int port, StringBuilder builder) {
        builder.append(':');
        IPAddressSegment.toUnsignedString(port, 10, builder);
    }

    private static void toNormalizedString(IPAddress addr, boolean wildcard, StringBuilder builder) {
        if (addr.isIPv6()) {
            if (!wildcard && addr.isPrefixed()) {
                builder.append('[');
                String normalized = addr.toNormalizedString();
                int index = normalized.indexOf(47);
                HostName.translateReserved(addr.toIPv6(), normalized.substring(0, index), builder);
                builder.append(']').append(normalized.substring(index));
            } else {
                builder.append('[');
                HostName.translateReserved(addr.toIPv6(), addr.toNormalizedWildcardString(), builder);
                builder.append(']');
            }
        } else {
            builder.append(wildcard ? addr.toNormalizedWildcardString() : addr.toNormalizedString());
        }
    }

    public boolean equals(Object o) {
        return o instanceof HostName && this.matches((HostName)o);
    }

    public int hashCode() {
        return this.toNormalizedWildcardString().hashCode();
    }

    public String[] getNormalizedLabels() {
        if (this.isValid()) {
            return this.parsedHost.getNormalizedLabels();
        }
        if (this.host.length() == 0) {
            return new String[0];
        }
        return new String[]{this.host};
    }

    public String getHost() {
        if (this.isValid()) {
            return this.parsedHost.getHost();
        }
        return null;
    }

    public boolean matches(HostName host2) {
        if (this == host2) {
            return true;
        }
        if (this.isValid()) {
            if (host2.isValid()) {
                String otherHost;
                ParsedHost parsedHost = this.parsedHost;
                ParsedHost otherParsedHost = host2.parsedHost;
                if (parsedHost.isAddressString()) {
                    return otherParsedHost.isAddressString() && parsedHost.asGenericAddressString().equals(otherParsedHost.asGenericAddressString()) && Objects.equals(parsedHost.getPort(), otherParsedHost.getPort()) && Objects.equals(parsedHost.getService(), otherParsedHost.getService());
                }
                if (otherParsedHost.isAddressString()) {
                    return false;
                }
                String thisHost = parsedHost.getHost();
                if (!thisHost.equals(otherHost = otherParsedHost.getHost())) {
                    return false;
                }
                return Objects.equals(parsedHost.getEquivalentPrefixLength(), otherParsedHost.getEquivalentPrefixLength()) && Objects.equals(parsedHost.getMask(), otherParsedHost.getMask()) && Objects.equals(parsedHost.getPort(), otherParsedHost.getPort()) && Objects.equals(parsedHost.getService(), otherParsedHost.getService());
            }
            return false;
        }
        return !host2.isValid() && this.toString().equals(host2.toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int compareTo(HostName other) {
        if (this.isValid()) {
            if (!other.isValid()) return 1;
            ParsedHost parsedHost = this.parsedHost;
            ParsedHost otherParsedHost = other.parsedHost;
            if (parsedHost.isAddressString()) {
                if (!otherParsedHost.isAddressString()) return -1;
                int result = parsedHost.asGenericAddressString().compareTo(otherParsedHost.asGenericAddressString());
                if (result != 0) {
                    return result;
                }
            } else {
                if (otherParsedHost.isAddressString()) {
                    return 1;
                }
                String[] normalizedLabels = parsedHost.getNormalizedLabels();
                String[] otherNormalizedLabels = otherParsedHost.getNormalizedLabels();
                int oneLen = normalizedLabels.length;
                int twoLen = otherNormalizedLabels.length;
                int minLen = Math.min(oneLen, twoLen);
                for (int i = 1; i <= minLen; ++i) {
                    String one = normalizedLabels[oneLen - i];
                    String two = otherNormalizedLabels[twoLen - i];
                    int result = one.compareTo(two);
                    if (result == 0) continue;
                    return result;
                }
                if (oneLen != twoLen) {
                    return oneLen - twoLen;
                }
                Integer networkPrefixLength = parsedHost.getEquivalentPrefixLength();
                Integer otherPrefixLength = otherParsedHost.getEquivalentPrefixLength();
                if (networkPrefixLength != null) {
                    if (otherPrefixLength == null) return 1;
                    if (networkPrefixLength.intValue() != otherPrefixLength.intValue()) {
                        return otherPrefixLength - networkPrefixLength;
                    }
                } else {
                    if (otherPrefixLength != null) {
                        return -1;
                    }
                    IPAddress mask = parsedHost.getMask();
                    IPAddress otherMask = otherParsedHost.getMask();
                    if (mask != null) {
                        if (otherMask == null) return 1;
                        int ret = mask.compareTo(otherMask);
                        if (ret != 0) {
                            return ret;
                        }
                    } else if (otherMask != null) {
                        return -1;
                    }
                }
            }
            Integer portOne = parsedHost.getPort();
            Integer portTwo = otherParsedHost.getPort();
            if (portOne != null) {
                if (portTwo == null) return 1;
                int ret = portOne - portTwo;
                if (ret != 0) {
                    return ret;
                }
            } else if (portTwo != null) {
                return -1;
            }
            String serviceOne = parsedHost.getService();
            String serviceTwo = otherParsedHost.getService();
            if (serviceOne != null) {
                if (serviceTwo == null) return 1;
                int ret = serviceOne.compareTo(serviceTwo);
                if (ret == 0) return 0;
                return ret;
            }
            if (serviceTwo == null) return 0;
            return -1;
        }
        if (!other.isValid()) return this.toString().compareTo(other.toString());
        return -1;
    }

    @Deprecated
    public boolean isAddress(IPAddress.IPVersion version) {
        return this.isValid() && this.parsedHost.isAddressString() && this.parsedHost.asAddress(version) != null;
    }

    public boolean isAddress() {
        return this.isAddressString() && this.parsedHost.asAddress() != null;
    }

    public boolean isAddressString() {
        return this.isValid() && this.parsedHost.isAddressString();
    }

    public boolean isAllAddresses() {
        return this.isAddressString() && this.parsedHost.getAddressProvider().isProvidingAllAddresses();
    }

    public boolean isPrefixOnly() {
        return this.isAddressString() && this.parsedHost.getAddressProvider().isProvidingPrefixOnly();
    }

    public boolean isEmpty() {
        return this.isAddressString() && this.parsedHost.getAddressProvider().isProvidingEmpty();
    }

    public Integer getPort() {
        return this.isValid() ? this.parsedHost.getPort() : null;
    }

    public String getService() {
        return this.isValid() ? this.parsedHost.getService() : null;
    }

    public AddressStringException getAddressStringException() {
        if (this.isValid()) {
            return this.parsedHost.getAddressStringException();
        }
        return null;
    }

    public boolean isUNCIPv6Literal() {
        return this.isValid() && this.parsedHost.isUNCIPv6Literal();
    }

    public boolean isReverseDNS() {
        return this.isValid() && this.parsedHost.isReverseDNS();
    }

    public IPAddressString asAddressString() {
        if (this.isAddressString()) {
            return this.parsedHost.asGenericAddressString();
        }
        return null;
    }

    public IPAddress asAddress() {
        if (this.isAddress()) {
            return this.parsedHost.asAddress();
        }
        return null;
    }

    @Deprecated
    public IPAddress asAddress(IPAddress.IPVersion version) {
        if (this.isAddress(version)) {
            return this.parsedHost.asAddress(version);
        }
        return null;
    }

    public Integer getNetworkPrefixLength() {
        if (this.isAddress()) {
            return this.parsedHost.asAddress().getNetworkPrefixLength();
        }
        if (this.isAddressString()) {
            return this.parsedHost.asGenericAddressString().getNetworkPrefixLength();
        }
        return this.isValid() ? this.parsedHost.getEquivalentPrefixLength() : null;
    }

    public IPAddress getMask() {
        if (this.isValid()) {
            if (this.parsedHost.isAddressString()) {
                return this.parsedHost.getAddressProvider().getProviderMask();
            }
            return this.parsedHost.getMask();
        }
        return null;
    }

    public InetAddress asInetAddress() {
        IPAddress ipAddr;
        if (this.isValid() && this.isAddressString() && (ipAddr = this.asAddress()) != null) {
            return ipAddr.toInetAddress();
        }
        return null;
    }

    public InetSocketAddress asInetSocketAddress(Function<String, Integer> serviceMapper) {
        if (this.isValid()) {
            String service2;
            Integer port = this.getPort();
            if (port == null && serviceMapper != null && (service2 = this.getService()) != null) {
                port = serviceMapper.apply(service2);
            }
            if (port != null) {
                IPAddress ipAddr;
                if (this.isAddressString() && (ipAddr = this.asAddress()) != null) {
                    return new InetSocketAddress(ipAddr.toInetAddress(), (int)port);
                }
                return new InetSocketAddress(this.getHost(), (int)port);
            }
        }
        return null;
    }

    public InetSocketAddress asInetSocketAddress() {
        return this.asInetSocketAddress(null);
    }

    public IPAddress[] toAllAddresses() throws UnknownHostException, HostNameException {
        return this.toAddresses();
    }

    @Override
    public IPAddress toAddress() throws UnknownHostException, HostNameException {
        IPAddress[] addrs = this.toAddresses();
        return addrs.length == 0 ? null : addrs[0];
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IPAddress[] toAddresses() throws UnknownHostException, HostNameException {
        IPAddress[] addrs = this.resolvedAddresses;
        if (addrs == null) {
            this.validate();
            HostName hostName = this;
            synchronized (hostName) {
                addrs = this.resolvedAddresses;
                if (addrs == null) {
                    if (this.parsedHost.isAddressString()) {
                        addrs = new IPAddress[]{this.parsedHost.asAddress()};
                    } else {
                        String strHost = this.parsedHost.getHost();
                        if (strHost.length() == 0 && !this.validationOptions.emptyIsLoopback) {
                            addrs = EMPTY_ADDRS;
                        } else {
                            InetAddress[] inetAddresses = InetAddress.getAllByName(strHost);
                            addrs = new IPAddress[inetAddresses.length];
                            for (int j = 0; j < inetAddresses.length; ++j) {
                                IPAddressNetwork.IPAddressCreator creator;
                                byte[] maskBytes;
                                IPAddress mask;
                                InetAddress inetAddress = inetAddresses[j];
                                byte[] bytes = inetAddress.getAddress();
                                Integer networkPrefixLength = this.parsedHost.getNetworkPrefixLength();
                                if (networkPrefixLength == null && (mask = this.parsedHost.getMask()) != null && (maskBytes = mask.getBytes()).length == bytes.length) {
                                    for (int i = 0; i < bytes.length; ++i) {
                                        int n = i;
                                        bytes[n] = (byte)(bytes[n] & maskBytes[i]);
                                    }
                                    networkPrefixLength = mask.getBlockMaskPrefixLength(true);
                                }
                                IPAddressStringParameters addressParams = this.validationOptions.addressOptions;
                                if (bytes.length == 16) {
                                    creator = addressParams.getIPv6Parameters().getNetwork().getAddressCreator();
                                    addrs[j] = creator.createAddressInternal(bytes, networkPrefixLength, null, this);
                                    continue;
                                }
                                if (networkPrefixLength != null && networkPrefixLength > 32) {
                                    networkPrefixLength = IPAddressSection.cacheBits(32);
                                }
                                creator = addressParams.getIPv4Parameters().getNetwork().getAddressCreator();
                                addrs[j] = creator.createAddressInternal(bytes, networkPrefixLength, this);
                            }
                        }
                    }
                    this.resolvedAddresses = addrs;
                }
            }
        }
        return addrs;
    }

    @Override
    public IPAddress getAddress() {
        try {
            return this.toAddress();
        }
        catch (HostNameException | UnknownHostException exception) {
            return null;
        }
    }

    @Override
    public String toString() {
        return this.host;
    }
}

