/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.httpclient;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import net.shibboleth.utilities.java.support.httpclient.TLSSocketFactory;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.http.conn.ssl.X509HostnameVerifier;

public class TLSSocketFactoryBuilder {
    private static final String DEFAULT_CONTEXT_PROTOCOL = "TLS";
    private static final X509HostnameVerifier DEFAULT_HOSTNAME_VERIFIER = TLSSocketFactory.STRICT_HOSTNAME_VERIFIER;
    private String sslContextProtocol;
    private String sslContextProvider;
    private List<KeyManager> keyManagers;
    private List<TrustManager> trustManagers;
    private SecureRandom secureRandom;
    private X509HostnameVerifier hostnameVerifier;
    private List<String> enabledProtocols;
    private List<String> enabledCipherSuites;

    @Nullable
    public String getSSLContextProtocol() {
        return this.sslContextProtocol;
    }

    public TLSSocketFactoryBuilder setSSLContextProtocol(@Nullable String protocol) {
        this.sslContextProtocol = StringSupport.trimOrNull(protocol);
        return this;
    }

    @Nullable
    public String getSSLContextProvider() {
        return this.sslContextProvider;
    }

    public TLSSocketFactoryBuilder setSSLContextProvider(@Nullable String provider) {
        this.sslContextProvider = StringSupport.trimOrNull(provider);
        return this;
    }

    @Nullable
    public List<KeyManager> getKeyManagers() {
        return this.keyManagers;
    }

    public TLSSocketFactoryBuilder setKeyManagers(@Nullable List<KeyManager> managers) {
        if (managers == null) {
            this.keyManagers = null;
        } else {
            this.keyManagers = new ArrayList<KeyManager>(Collections2.filter(managers, Predicates.notNull()));
            if (this.keyManagers.isEmpty()) {
                this.keyManagers = null;
            }
        }
        return this;
    }

    @Nullable
    public List<TrustManager> getTrustManagers() {
        return this.trustManagers;
    }

    public TLSSocketFactoryBuilder setTrustManagers(@Nullable List<TrustManager> managers) {
        if (managers == null) {
            this.trustManagers = null;
        } else {
            this.trustManagers = new ArrayList<TrustManager>(Collections2.filter(managers, Predicates.notNull()));
            if (this.trustManagers.isEmpty()) {
                this.trustManagers = null;
            }
        }
        return this;
    }

    @Nullable
    public SecureRandom getSecureRandom() {
        return this.secureRandom;
    }

    public TLSSocketFactoryBuilder setSecureRandom(@Nullable SecureRandom random) {
        this.secureRandom = random;
        return this;
    }

    @Nullable
    public X509HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public TLSSocketFactoryBuilder setHostnameVerifier(@Nullable X509HostnameVerifier verifier) {
        this.hostnameVerifier = verifier;
        return this;
    }

    @Nullable
    public List<String> getEnabledProtocols() {
        return this.enabledProtocols;
    }

    public TLSSocketFactoryBuilder setEnabledProtocols(@Nullable List<String> protocols) {
        this.enabledProtocols = new ArrayList<String>(StringSupport.normalizeStringCollection(protocols));
        if (this.enabledProtocols.isEmpty()) {
            this.enabledProtocols = null;
        }
        return this;
    }

    @Nullable
    public List<String> getEnabledCipherSuites() {
        return this.enabledCipherSuites;
    }

    public TLSSocketFactoryBuilder setEnabledCipherSuites(@Nullable List<String> cipherSuites) {
        this.enabledCipherSuites = new ArrayList<String>(StringSupport.normalizeStringCollection(cipherSuites));
        if (this.enabledCipherSuites.isEmpty()) {
            this.enabledCipherSuites = null;
        }
        return this;
    }

    @Nonnull
    public TLSSocketFactory build() {
        X509HostnameVerifier verifier = this.hostnameVerifier;
        if (verifier == null) {
            verifier = DEFAULT_HOSTNAME_VERIFIER;
        }
        SSLContext sslcontext = this.buildSSLContext();
        return new TLSSocketFactory(sslcontext, this.enabledProtocols != null ? this.enabledProtocols.toArray(new String[0]) : null, this.enabledCipherSuites != null ? this.enabledCipherSuites.toArray(new String[0]) : null, verifier);
    }

    @Nonnull
    protected SSLContext buildSSLContext() {
        String protocol = this.sslContextProtocol;
        if (protocol == null) {
            protocol = DEFAULT_CONTEXT_PROTOCOL;
        }
        try {
            SSLContext sslcontext = this.sslContextProvider != null ? SSLContext.getInstance(protocol, this.sslContextProvider) : SSLContext.getInstance(protocol);
            sslcontext.init(this.keyManagers != null ? this.keyManagers.toArray(new KeyManager[0]) : null, this.trustManagers != null ? this.trustManagers.toArray(new TrustManager[0]) : null, this.secureRandom);
            return sslcontext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem obtaining SSLContext, unsupported protocol: " + this.sslContextProtocol, e);
        }
        catch (NoSuchProviderException e) {
            throw new RuntimeException("Problem obtaining SSLContext, invalid provider: " + this.sslContextProvider, e);
        }
        catch (KeyManagementException e) {
            throw new RuntimeException("Key Problem initializing SSLContext", e);
        }
    }
}

