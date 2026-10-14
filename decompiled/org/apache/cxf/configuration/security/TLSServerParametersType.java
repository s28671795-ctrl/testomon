/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.configuration.security.CertificateConstraintsType;
import org.apache.cxf.configuration.security.CipherSuites;
import org.apache.cxf.configuration.security.ClientAuthentication;
import org.apache.cxf.configuration.security.ExcludeProtocols;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.configuration.security.IncludeProtocols;
import org.apache.cxf.configuration.security.KeyManagersType;
import org.apache.cxf.configuration.security.SecureRandomParameters;
import org.apache.cxf.configuration.security.TrustManagersType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TLSServerParametersType", namespace="http://cxf.apache.org/configuration/security", propOrder={})
public class TLSServerParametersType {
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected KeyManagersType keyManagers;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected TrustManagersType trustManagers;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected CipherSuites cipherSuites;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected FiltersType cipherSuitesFilter;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected ExcludeProtocols excludeProtocols;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected IncludeProtocols includeProtocols;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected SecureRandomParameters secureRandomParameters;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected ClientAuthentication clientAuthentication;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected CertificateConstraintsType certConstraints;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected String certAlias;
    @XmlAttribute(name="jsseProvider")
    protected String jsseProvider;
    @XmlAttribute(name="secureSocketProtocol")
    protected String secureSocketProtocol;
    @XmlAttribute(name="enableRevocation")
    protected Boolean enableRevocation;

    public KeyManagersType getKeyManagers() {
        return this.keyManagers;
    }

    public void setKeyManagers(KeyManagersType value) {
        this.keyManagers = value;
    }

    public boolean isSetKeyManagers() {
        return this.keyManagers != null;
    }

    public TrustManagersType getTrustManagers() {
        return this.trustManagers;
    }

    public void setTrustManagers(TrustManagersType value) {
        this.trustManagers = value;
    }

    public boolean isSetTrustManagers() {
        return this.trustManagers != null;
    }

    public CipherSuites getCipherSuites() {
        return this.cipherSuites;
    }

    public void setCipherSuites(CipherSuites value) {
        this.cipherSuites = value;
    }

    public boolean isSetCipherSuites() {
        return this.cipherSuites != null;
    }

    public FiltersType getCipherSuitesFilter() {
        return this.cipherSuitesFilter;
    }

    public void setCipherSuitesFilter(FiltersType value) {
        this.cipherSuitesFilter = value;
    }

    public boolean isSetCipherSuitesFilter() {
        return this.cipherSuitesFilter != null;
    }

    public ExcludeProtocols getExcludeProtocols() {
        return this.excludeProtocols;
    }

    public void setExcludeProtocols(ExcludeProtocols value) {
        this.excludeProtocols = value;
    }

    public boolean isSetExcludeProtocols() {
        return this.excludeProtocols != null;
    }

    public IncludeProtocols getIncludeProtocols() {
        return this.includeProtocols;
    }

    public void setIncludeProtocols(IncludeProtocols value) {
        this.includeProtocols = value;
    }

    public boolean isSetIncludeProtocols() {
        return this.includeProtocols != null;
    }

    public SecureRandomParameters getSecureRandomParameters() {
        return this.secureRandomParameters;
    }

    public void setSecureRandomParameters(SecureRandomParameters value) {
        this.secureRandomParameters = value;
    }

    public boolean isSetSecureRandomParameters() {
        return this.secureRandomParameters != null;
    }

    public ClientAuthentication getClientAuthentication() {
        return this.clientAuthentication;
    }

    public void setClientAuthentication(ClientAuthentication value) {
        this.clientAuthentication = value;
    }

    public boolean isSetClientAuthentication() {
        return this.clientAuthentication != null;
    }

    public CertificateConstraintsType getCertConstraints() {
        return this.certConstraints;
    }

    public void setCertConstraints(CertificateConstraintsType value) {
        this.certConstraints = value;
    }

    public boolean isSetCertConstraints() {
        return this.certConstraints != null;
    }

    public String getCertAlias() {
        return this.certAlias;
    }

    public void setCertAlias(String value) {
        this.certAlias = value;
    }

    public boolean isSetCertAlias() {
        return this.certAlias != null;
    }

    public String getJsseProvider() {
        return this.jsseProvider;
    }

    public void setJsseProvider(String value) {
        this.jsseProvider = value;
    }

    public boolean isSetJsseProvider() {
        return this.jsseProvider != null;
    }

    public String getSecureSocketProtocol() {
        return this.secureSocketProtocol;
    }

    public void setSecureSocketProtocol(String value) {
        this.secureSocketProtocol = value;
    }

    public boolean isSetSecureSocketProtocol() {
        return this.secureSocketProtocol != null;
    }

    public void setEnableRevocation(boolean value) {
        this.enableRevocation = value;
    }

    public void unsetEnableRevocation() {
        this.enableRevocation = null;
    }

    public boolean isSetEnableRevocation() {
        return this.enableRevocation != null;
    }

    public boolean isEnableRevocation() {
        if (null == this.enableRevocation) {
            return false;
        }
        return this.enableRevocation;
    }
}

