/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.httpclient;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.security.x509.X509Credential;

public class HttpClientSecurityParameters {
    private CredentialsProvider credentialsProvider;
    private TrustEngine<? super X509Credential> tlsTrustEngine;
    private CriteriaSet tlsCriteriaSet;
    private List<String> tlsProtocols;
    private List<String> tlsCipherSuites;
    private X509HostnameVerifier hostnameVerifier;
    private X509Credential clientTLSCredential;

    @Nullable
    public CredentialsProvider getCredentialsProvider() {
        return this.credentialsProvider;
    }

    public void setCredentialsProvider(@Nullable CredentialsProvider provider) {
        this.credentialsProvider = provider;
    }

    public void setBasicCredentials(@Nullable UsernamePasswordCredentials credentials) {
        this.setBasicCredentialsWithScope(credentials, null);
    }

    public void setBasicCredentialsWithScope(@Nullable UsernamePasswordCredentials credentials, @Nullable AuthScope scope) {
        if (credentials != null) {
            AuthScope authScope = scope;
            if (authScope == null) {
                authScope = new AuthScope(AuthScope.ANY_HOST, -1);
            }
            BasicCredentialsProvider provider = new BasicCredentialsProvider();
            provider.setCredentials(authScope, credentials);
            this.credentialsProvider = provider;
        } else {
            this.credentialsProvider = null;
        }
    }

    @Nullable
    public TrustEngine<? super X509Credential> getTLSTrustEngine() {
        return this.tlsTrustEngine;
    }

    public void setTLSTrustEngine(@Nullable TrustEngine<? super X509Credential> engine) {
        this.tlsTrustEngine = engine;
    }

    @Nullable
    public CriteriaSet getTLSCriteriaSet() {
        return this.tlsCriteriaSet;
    }

    public void setTLSCriteriaSet(@Nullable CriteriaSet criteriaSet) {
        this.tlsCriteriaSet = criteriaSet;
    }

    @Nullable
    public List<String> getTLSProtocols() {
        return this.tlsProtocols;
    }

    public void setTLSProtocols(@Nullable List<String> protocols) {
        this.tlsProtocols = new ArrayList<String>(StringSupport.normalizeStringCollection(protocols));
        if (this.tlsProtocols.isEmpty()) {
            this.tlsProtocols = null;
        }
    }

    @Nullable
    public List<String> getTLSCipherSuites() {
        return this.tlsCipherSuites;
    }

    public void setTLSCipherSuites(@Nullable List<String> cipherSuites) {
        this.tlsCipherSuites = new ArrayList<String>(StringSupport.normalizeStringCollection(cipherSuites));
        if (this.tlsCipherSuites.isEmpty()) {
            this.tlsCipherSuites = null;
        }
    }

    @Nullable
    public X509HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public void setHostnameVerifier(@Nullable X509HostnameVerifier verifier) {
        this.hostnameVerifier = verifier;
    }

    @Nullable
    public X509Credential getClientTLSCredential() {
        return this.clientTLSCredential;
    }

    public void setClientTLSCredential(@Nullable X509Credential credential) {
        this.clientTLSCredential = credential;
    }
}

