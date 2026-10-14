/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.jsse;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import org.apache.cxf.configuration.security.CertificateConstraintsType;
import org.apache.cxf.configuration.security.FiltersType;

public class TLSParameterBase {
    protected KeyManager[] keyManagers;
    protected TrustManager[] trustManagers;
    protected String provider;
    protected List<String> ciphersuites = new ArrayList<String>();
    protected FiltersType cipherSuiteFilters;
    protected CertificateConstraintsType certConstraints;
    protected SecureRandom secureRandom;
    protected String protocol;
    protected String certAlias;

    public final void setJsseProvider(String prov) {
        this.provider = prov;
    }

    public String getJsseProvider() {
        return this.provider;
    }

    public final void setKeyManagers(KeyManager[] keyMgrs) {
        this.keyManagers = keyMgrs;
    }

    public KeyManager[] getKeyManagers() {
        return this.keyManagers;
    }

    public final void setTrustManagers(TrustManager[] trustMgrs) {
        this.trustManagers = trustMgrs;
    }

    public TrustManager[] getTrustManagers() {
        return this.trustManagers;
    }

    public final void setCipherSuites(List<String> cs) {
        this.ciphersuites = cs;
    }

    public List<String> getCipherSuites() {
        if (this.ciphersuites == null) {
            this.ciphersuites = new ArrayList<String>();
        }
        return this.ciphersuites;
    }

    public final void setCipherSuitesFilter(FiltersType filters) {
        this.cipherSuiteFilters = filters;
    }

    public FiltersType getCipherSuitesFilter() {
        return this.cipherSuiteFilters;
    }

    public final void setSecureRandom(SecureRandom random) {
        this.secureRandom = random;
    }

    public CertificateConstraintsType getCertConstraints() {
        return this.certConstraints;
    }

    public final void setCertConstraints(CertificateConstraintsType constraints) {
        this.certConstraints = constraints;
    }

    public SecureRandom getSecureRandom() {
        return this.secureRandom;
    }

    public final void setSecureSocketProtocol(String proto) {
        this.protocol = proto;
    }

    public String getSecureSocketProtocol() {
        return this.protocol;
    }

    public final void setCertAlias(String ctAlias) {
        this.certAlias = ctAlias;
    }

    public String getCertAlias() {
        return this.certAlias;
    }
}

