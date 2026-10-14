/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.security;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.configuration.security.CertStoreType;
import org.apache.cxf.configuration.security.CertificateConstraintsType;
import org.apache.cxf.configuration.security.CipherSuites;
import org.apache.cxf.configuration.security.ClientAuthentication;
import org.apache.cxf.configuration.security.DNConstraintsType;
import org.apache.cxf.configuration.security.ExcludeProtocols;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.configuration.security.IncludeProtocols;
import org.apache.cxf.configuration.security.KeyManagersType;
import org.apache.cxf.configuration.security.KeyStoreType;
import org.apache.cxf.configuration.security.ProxyAuthorizationPolicy;
import org.apache.cxf.configuration.security.SecureRandomParameters;
import org.apache.cxf.configuration.security.TLSClientParametersType;
import org.apache.cxf.configuration.security.TLSServerParametersType;
import org.apache.cxf.configuration.security.TrustManagersType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Authorization_QNAME = new QName("http://cxf.apache.org/configuration/security", "authorization");

    public AuthorizationPolicy createAuthorizationPolicy() {
        return new AuthorizationPolicy();
    }

    public ProxyAuthorizationPolicy createProxyAuthorizationPolicy() {
        return new ProxyAuthorizationPolicy();
    }

    public FiltersType createFiltersType() {
        return new FiltersType();
    }

    public ClientAuthentication createClientAuthentication() {
        return new ClientAuthentication();
    }

    public KeyStoreType createKeyStoreType() {
        return new KeyStoreType();
    }

    public CertStoreType createCertStoreType() {
        return new CertStoreType();
    }

    public KeyManagersType createKeyManagersType() {
        return new KeyManagersType();
    }

    public TrustManagersType createTrustManagersType() {
        return new TrustManagersType();
    }

    public CipherSuites createCipherSuites() {
        return new CipherSuites();
    }

    public ExcludeProtocols createExcludeProtocols() {
        return new ExcludeProtocols();
    }

    public IncludeProtocols createIncludeProtocols() {
        return new IncludeProtocols();
    }

    public SecureRandomParameters createSecureRandomParameters() {
        return new SecureRandomParameters();
    }

    public CertificateConstraintsType createCertificateConstraintsType() {
        return new CertificateConstraintsType();
    }

    public DNConstraintsType createDNConstraintsType() {
        return new DNConstraintsType();
    }

    public TLSClientParametersType createTLSClientParametersType() {
        return new TLSClientParametersType();
    }

    public TLSServerParametersType createTLSServerParametersType() {
        return new TLSServerParametersType();
    }

    @XmlElementDecl(namespace="http://cxf.apache.org/configuration/security", name="authorization")
    public JAXBElement<AuthorizationPolicy> createAuthorization(AuthorizationPolicy value) {
        return new JAXBElement<AuthorizationPolicy>(_Authorization_QNAME, AuthorizationPolicy.class, null, value);
    }
}

