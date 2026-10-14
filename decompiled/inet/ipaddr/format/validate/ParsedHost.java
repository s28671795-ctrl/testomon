/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.validate;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressNetwork;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.IncompatibleAddressException;
import inet.ipaddr.format.validate.IPAddressProvider;
import inet.ipaddr.format.validate.ParsedHostIdentifierStringQualifier;
import java.io.Serializable;

public class ParsedHost
implements Serializable {
    private static final long serialVersionUID = 4L;
    private static final EmbeddedAddress NO_EMBEDDED_ADDRESS = new EmbeddedAddress();
    static final ParsedHostIdentifierStringQualifier NO_QUALIFIER = new ParsedHostIdentifierStringQualifier();
    private String[] normalizedLabels;
    private int[] separatorIndices;
    private boolean[] normalizedFlags;
    private final ParsedHostIdentifierStringQualifier labelsQualifier;
    private String service;
    private EmbeddedAddress embeddedAddress;
    String host;
    private final String originalStr;

    public ParsedHost(String originalStr, IPAddressProvider valueProvider) {
        this(originalStr, null, null, NO_QUALIFIER, new EmbeddedAddress());
        this.embeddedAddress.addressProvider = valueProvider;
    }

    public ParsedHost(String originalStr, IPAddressProvider valueProvider, ParsedHostIdentifierStringQualifier portQualifier) {
        this(originalStr, null, null, portQualifier, new EmbeddedAddress());
        this.embeddedAddress.addressProvider = valueProvider;
    }

    ParsedHost(String originalStr, int[] separatorIndices, boolean[] normalizedFlags, ParsedHostIdentifierStringQualifier labelsQualifier) {
        this(originalStr, separatorIndices, normalizedFlags, labelsQualifier, null);
    }

    ParsedHost(String originalStr, int[] separatorIndices, boolean[] normalizedFlags, ParsedHostIdentifierStringQualifier labelsQualifier, EmbeddedAddress embeddedAddress) {
        this.labelsQualifier = labelsQualifier;
        this.normalizedFlags = normalizedFlags;
        this.separatorIndices = separatorIndices;
        this.originalStr = originalStr;
        this.embeddedAddress = embeddedAddress == null ? NO_EMBEDDED_ADDRESS : embeddedAddress;
    }

    public boolean isIPv6Address() {
        return this.hasEmbeddedAddress() && this.getAddressProvider().isProvidingIPv6();
    }

    public Integer getPort() {
        return this.labelsQualifier.getPort();
    }

    public String getService() {
        CharSequence sv;
        String serv = this.service;
        if (serv == null && (sv = this.labelsQualifier.getService()) != null) {
            this.service = serv = sv.toString();
        }
        return serv;
    }

    public Integer getNetworkPrefixLength() {
        return this.labelsQualifier.getNetworkPrefixLength();
    }

    public Integer getEquivalentPrefixLength() {
        return this.labelsQualifier.getEquivalentPrefixLength();
    }

    public IPAddress getMask() {
        return this.labelsQualifier.getMaskLower();
    }

    public IPAddressProvider getAddressProvider() {
        return this.embeddedAddress.addressProvider;
    }

    private boolean hasEmbeddedAddress() {
        return this.embeddedAddress.addressProvider != null;
    }

    public boolean isAddressString() {
        return this.getAddressProvider() != null;
    }

    public IPAddress asAddress(IPAddress.IPVersion version) throws IncompatibleAddressException {
        if (this.hasEmbeddedAddress()) {
            return this.getAddressProvider().getProviderAddress(version);
        }
        return null;
    }

    public IPAddress asAddress() throws IncompatibleAddressException {
        if (this.hasEmbeddedAddress()) {
            return this.getAddressProvider().getProviderAddress();
        }
        return null;
    }

    private String mapString(IPAddressProvider addressProvider) {
        if (addressProvider.isProvidingAllAddresses()) {
            return IPAddress.SEGMENT_WILDCARD_STR;
        }
        if (addressProvider.isProvidingPrefixOnly()) {
            return IPAddressNetwork.getPrefixString(addressProvider.getProviderNetworkPrefixLength());
        }
        if (addressProvider.isProvidingEmpty()) {
            return "";
        }
        return this.originalStr;
    }

    public IPAddressString asGenericAddressString() {
        if (this.hasEmbeddedAddress()) {
            IPAddressProvider addressProvider = this.getAddressProvider();
            if (addressProvider.isProvidingAllAddresses()) {
                return new IPAddressString(IPAddress.SEGMENT_WILDCARD_STR, addressProvider.getParameters());
            }
            if (addressProvider.isProvidingPrefixOnly()) {
                return new IPAddressString(IPAddressNetwork.getPrefixString(addressProvider.getProviderNetworkPrefixLength()), addressProvider.getParameters());
            }
            if (addressProvider.isProvidingEmpty()) {
                return new IPAddressString("", addressProvider.getParameters());
            }
            try {
                IPAddress addr = addressProvider.getProviderAddress();
                return addr.toAddressString();
            }
            catch (IncompatibleAddressException e) {
                return new IPAddressString(this.originalStr, addressProvider.getParameters());
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String[] getNormalizedLabels() {
        String[] labels = this.normalizedLabels;
        if (labels == null) {
            ParsedHost parsedHost = this;
            synchronized (parsedHost) {
                labels = this.normalizedLabels;
                if (labels == null) {
                    if (this.hasEmbeddedAddress()) {
                        IPAddressProvider addressProvider = this.getAddressProvider();
                        try {
                            IPAddress addr = addressProvider.getProviderAddress();
                            if (addr != null) {
                                IPAddressSection section = addr.getSection();
                                this.normalizedLabels = section.getSegmentStrings();
                                return this.normalizedLabels;
                            }
                        }
                        catch (IncompatibleAddressException addr) {
                            // empty catch block
                        }
                        labels = addressProvider.isProvidingEmpty() ? new String[]{} : new String[]{this.mapString(addressProvider)};
                    } else {
                        labels = new String[this.separatorIndices.length];
                        int lastSep = -1;
                        for (int i = 0; i < labels.length; ++i) {
                            int index = this.separatorIndices[i];
                            if (this.normalizedFlags != null && !this.normalizedFlags[i]) {
                                StringBuilder second = new StringBuilder(index - lastSep - 1);
                                for (int j = lastSep + 1; j < index; ++j) {
                                    char c = this.originalStr.charAt(j);
                                    second.append(c >= 'A' && c <= 'Z' ? (char)(c + 32) : c);
                                }
                                labels[i] = second.toString();
                            } else {
                                labels[i] = this.originalStr.substring(lastSep + 1, index);
                            }
                            lastSep = index;
                        }
                        this.separatorIndices = null;
                        this.normalizedFlags = null;
                    }
                    this.normalizedLabels = labels;
                }
            }
        }
        return labels;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getHost() {
        String str = this.host;
        if (str == null) {
            if (this.originalStr.length() > 0) {
                ParsedHost parsedHost = this;
                synchronized (parsedHost) {
                    str = this.host;
                    if (str == null) {
                        if (this.hasEmbeddedAddress()) {
                            IPAddressProvider addressProvider = this.getAddressProvider();
                            try {
                                IPAddress addr = addressProvider.getProviderAddress();
                                if (addr != null) {
                                    this.host = addr.getSection().toCanonicalWildcardString();
                                    return this.host;
                                }
                            }
                            catch (IncompatibleAddressException addr) {
                                // empty catch block
                            }
                            this.host = this.mapString(addressProvider);
                            return this.host;
                        }
                        StringBuilder builder = new StringBuilder(this.originalStr.length());
                        String[] labels = this.getNormalizedLabels();
                        builder.append(labels[0]);
                        for (int i = 1; i < labels.length; ++i) {
                            builder.append('.').append(labels[i]);
                        }
                        this.host = builder.toString();
                        return this.host;
                    }
                }
            }
            str = this.host = this.originalStr;
        }
        return str;
    }

    public AddressStringException getAddressStringException() {
        return this.embeddedAddress.addressStringException;
    }

    public boolean isUNCIPv6Literal() {
        return this.embeddedAddress.isUNCIPv6Literal;
    }

    public boolean isReverseDNS() {
        return this.embeddedAddress.isReverseDNS;
    }

    static class EmbeddedAddress
    implements Serializable {
        private static final long serialVersionUID = 4L;
        boolean isUNCIPv6Literal;
        boolean isReverseDNS;
        AddressStringException addressStringException;
        IPAddressProvider addressProvider;

        EmbeddedAddress() {
        }
    }
}

