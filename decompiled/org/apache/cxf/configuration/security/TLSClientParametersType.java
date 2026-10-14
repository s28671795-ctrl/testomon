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
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.configuration.security.KeyManagersType;
import org.apache.cxf.configuration.security.SecureRandomParameters;
import org.apache.cxf.configuration.security.TrustManagersType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TLSClientParametersType", namespace="http://cxf.apache.org/configuration/security", propOrder={})
public class TLSClientParametersType {
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected KeyManagersType keyManagers;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected TrustManagersType trustManagers;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected CipherSuites cipherSuites;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected FiltersType cipherSuitesFilter;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected SecureRandomParameters secureRandomParameters;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected CertificateConstraintsType certConstraints;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected String certAlias;
    @XmlAttribute(name="useHttpsURLConnectionDefaultSslSocketFactory")
    protected Boolean useHttpsURLConnectionDefaultSslSocketFactory;
    @XmlAttribute(name="useHttpsURLConnectionDefaultHostnameVerifier")
    protected Boolean useHttpsURLConnectionDefaultHostnameVerifier;
    @XmlAttribute(name="disableCNCheck")
    protected Boolean disableCNCheck;
    @XmlAttribute(name="enableRevocation")
    protected Boolean enableRevocation;
    @XmlAttribute(name="jsseProvider")
    protected String jsseProvider;
    @XmlAttribute(name="secureSocketProtocol")
    protected String secureSocketProtocol;
    @XmlAttribute(name="sslCacheTimeout")
    protected Integer sslCacheTimeout;

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

    public SecureRandomParameters getSecureRandomParameters() {
        return this.secureRandomParameters;
    }

    public void setSecureRandomParameters(SecureRandomParameters value) {
        this.secureRandomParameters = value;
    }

    public boolean isSetSecureRandomParameters() {
        return this.secureRandomParameters != null;
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

    public int getSslCacheTimeout() {
        return this.sslCacheTimeout;
    }

    public void setSslCacheTimeout(int value) {
        this.sslCacheTimeout = value;
    }

    public boolean isSetSslCacheTimeout() {
        return this.sslCacheTimeout != null;
    }

    public void unsetSslCacheTimeout() {
        this.sslCacheTimeout = null;
    }

    public void setUseHttpsURLConnectionDefaultSslSocketFactory(boolean value) {
        this.useHttpsURLConnectionDefaultSslSocketFactory = value;
    }

    public void unsetUseHttpsURLConnectionDefaultSslSocketFactory() {
        this.useHttpsURLConnectionDefaultSslSocketFactory = null;
    }

    public boolean isSetUseHttpsURLConnectionDefaultSslSocketFactory() {
        return this.useHttpsURLConnectionDefaultSslSocketFactory != null;
    }

    public boolean isUseHttpsURLConnectionDefaultSslSocketFactory() {
        if (null == this.useHttpsURLConnectionDefaultSslSocketFactory) {
            return false;
        }
        return this.useHttpsURLConnectionDefaultSslSocketFactory;
    }

    public void setUseHttpsURLConnectionDefaultHostnameVerifier(boolean value) {
        this.useHttpsURLConnectionDefaultHostnameVerifier = value;
    }

    public void unsetUseHttpsURLConnectionDefaultHostnameVerifier() {
        this.useHttpsURLConnectionDefaultHostnameVerifier = null;
    }

    public boolean isSetUseHttpsURLConnectionDefaultHostnameVerifier() {
        return this.useHttpsURLConnectionDefaultHostnameVerifier != null;
    }

    public boolean isUseHttpsURLConnectionDefaultHostnameVerifier() {
        if (null == this.useHttpsURLConnectionDefaultHostnameVerifier) {
            return false;
        }
        return this.useHttpsURLConnectionDefaultHostnameVerifier;
    }

    public void setDisableCNCheck(boolean value) {
        this.disableCNCheck = value;
    }

    public void unsetDisableCNCheck() {
        this.disableCNCheck = null;
    }

    public boolean isSetDisableCNCheck() {
        return this.disableCNCheck != null;
    }

    public boolean isDisableCNCheck() {
        if (null == this.disableCNCheck) {
            return false;
        }
        return this.disableCNCheck;
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

