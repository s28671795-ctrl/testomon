/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.jsse;

import java.util.List;
import java.util.Objects;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.apache.cxf.configuration.jsse.TLSParameterBase;

public class TLSClientParameters
extends TLSParameterBase {
    private boolean disableCNCheck;
    private SSLSocketFactory sslSocketFactory;
    private int sslCacheTimeout = 86400;
    private boolean useHttpsURLConnectionDefaultSslSocketFactory;
    private boolean useHttpsURLConnectionDefaultHostnameVerifier;
    private HostnameVerifier hostnameVerifier;
    private SSLContext sslContext;

    public void setHostnameVerifier(HostnameVerifier verifier) {
        this.hostnameVerifier = verifier;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public void setDisableCNCheck(boolean disableCNCheck) {
        this.disableCNCheck = disableCNCheck;
    }

    public boolean isDisableCNCheck() {
        return this.disableCNCheck;
    }

    public final void setSSLSocketFactory(SSLSocketFactory factory2) {
        this.sslSocketFactory = factory2;
    }

    public final SSLSocketFactory getSSLSocketFactory() {
        return this.sslSocketFactory;
    }

    public int getSslCacheTimeout() {
        return this.sslCacheTimeout;
    }

    public void setSslCacheTimeout(int sslCacheTimeout) {
        this.sslCacheTimeout = sslCacheTimeout;
    }

    public boolean isUseHttpsURLConnectionDefaultSslSocketFactory() {
        return this.useHttpsURLConnectionDefaultSslSocketFactory;
    }

    public void setUseHttpsURLConnectionDefaultSslSocketFactory(boolean useHttpsURLConnectionDefaultSslSocketFactory) {
        this.useHttpsURLConnectionDefaultSslSocketFactory = useHttpsURLConnectionDefaultSslSocketFactory;
    }

    public boolean isUseHttpsURLConnectionDefaultHostnameVerifier() {
        return this.useHttpsURLConnectionDefaultHostnameVerifier;
    }

    public void setUseHttpsURLConnectionDefaultHostnameVerifier(boolean useHttpsURLConnectionDefaultHostnameVerifier) {
        this.useHttpsURLConnectionDefaultHostnameVerifier = useHttpsURLConnectionDefaultHostnameVerifier;
    }

    public int hashCode() {
        int hash;
        int n = hash = this.disableCNCheck ? 37 : 17;
        if (this.sslSocketFactory != null) {
            hash = hash * 41 + System.identityHashCode(this.sslSocketFactory);
        }
        if (this.sslContext != null) {
            hash = hash * 41 + System.identityHashCode(this.sslContext);
        }
        hash = this.hash(hash, this.useHttpsURLConnectionDefaultSslSocketFactory);
        hash = this.hash(hash, this.useHttpsURLConnectionDefaultHostnameVerifier);
        hash = this.hash(hash, this.sslCacheTimeout);
        hash = this.hash(hash, this.secureRandom);
        hash = this.hash(hash, this.protocol);
        hash = this.hash(hash, this.certAlias);
        hash = this.hash(hash, this.provider);
        for (String cs : this.ciphersuites) {
            hash = this.hash(hash, cs);
        }
        hash = this.hash(hash, this.keyManagers);
        hash = this.hash(hash, this.trustManagers);
        if (this.cipherSuiteFilters != null) {
            hash = this.hash(hash, this.cipherSuiteFilters.getInclude());
            hash = this.hash(hash, this.cipherSuiteFilters.getExclude());
        }
        if (this.certConstraints != null) {
            hash = this.hash(hash, this.certConstraints.getIssuerDNConstraints());
            hash = this.hash(hash, this.certConstraints.getSubjectDNConstraints());
        }
        return hash;
    }

    private int hash(int i, Object o) {
        if (o != null) {
            return i * 37 + o.hashCode();
        }
        return i;
    }

    private int hash(int i, Object[] os) {
        if (os == null) {
            return i;
        }
        for (Object o : os) {
            i = this.hash(i, o);
        }
        return i;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof TLSClientParameters) {
            TLSClientParameters that = (TLSClientParameters)o;
            boolean eq2 = this.disableCNCheck == that.disableCNCheck;
            eq2 &= this.sslSocketFactory == that.sslSocketFactory;
            eq2 &= this.sslContext == that.sslContext;
            eq2 &= this.useHttpsURLConnectionDefaultSslSocketFactory == that.useHttpsURLConnectionDefaultSslSocketFactory;
            eq2 &= this.useHttpsURLConnectionDefaultHostnameVerifier == that.useHttpsURLConnectionDefaultHostnameVerifier;
            eq2 &= this.sslCacheTimeout == that.sslCacheTimeout;
            eq2 &= this.secureRandom == that.secureRandom;
            eq2 &= Objects.equals(this.certAlias, that.certAlias);
            eq2 &= Objects.equals(this.protocol, that.protocol);
            eq2 &= Objects.equals(this.provider, that.provider);
            eq2 &= TLSClientParameters.equals(this.ciphersuites, that.ciphersuites);
            eq2 &= Objects.deepEquals(this.keyManagers, that.keyManagers);
            eq2 &= Objects.deepEquals(this.trustManagers, that.trustManagers);
            if (this.cipherSuiteFilters != null) {
                if (that.cipherSuiteFilters != null) {
                    eq2 &= TLSClientParameters.equals(this.cipherSuiteFilters.getExclude(), that.cipherSuiteFilters.getExclude());
                    eq2 &= TLSClientParameters.equals(this.cipherSuiteFilters.getInclude(), that.cipherSuiteFilters.getInclude());
                } else {
                    eq2 = false;
                }
            } else {
                eq2 &= that.cipherSuiteFilters == null;
            }
            if (this.certConstraints != null) {
                if (that.certConstraints != null) {
                    eq2 &= Objects.equals(this.certConstraints.getIssuerDNConstraints(), that.certConstraints.getIssuerDNConstraints());
                    eq2 &= Objects.equals(this.certConstraints.getSubjectDNConstraints(), that.certConstraints.getSubjectDNConstraints());
                } else {
                    eq2 = false;
                }
            } else {
                eq2 &= that.certConstraints == null;
            }
            return eq2;
        }
        return false;
    }

    private static boolean equals(List<?> obj1, List<?> obj2) {
        return obj1.equals(obj2);
    }

    public SSLContext getSslContext() {
        return this.sslContext;
    }

    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }
}

