/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.model.internal;

import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;

public final class SSLMaterial {
    private SSLContext sslContext;
    private X509ExtendedKeyManager keyManager;
    private X509ExtendedTrustManager trustManager;
    private HostnameVerifier hostnameVerifier;
    private SSLParameters sslParameters;
    private List<String> ciphers;
    private List<String> protocols;

    private SSLMaterial() {
    }

    public SSLContext getSslContext() {
        return this.sslContext;
    }

    public X509ExtendedKeyManager getKeyManager() {
        return this.keyManager;
    }

    public X509ExtendedTrustManager getTrustManager() {
        return this.trustManager;
    }

    public SSLParameters getSslParameters() {
        return this.sslParameters;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public List<String> getCiphers() {
        return this.ciphers;
    }

    public List<String> getProtocols() {
        return this.protocols;
    }

    public static class Builder {
        private SSLContext sslContext;
        private X509ExtendedKeyManager keyManager;
        private X509ExtendedTrustManager trustManager;
        private HostnameVerifier hostnameVerifier;
        private SSLParameters sslParameters;
        private List<String> ciphers;
        private List<String> protocols;

        public Builder withSslContext(SSLContext sslContext) {
            this.sslContext = sslContext;
            return this;
        }

        public Builder withHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public Builder withSslParameters(SSLParameters sslParameters) {
            this.sslParameters = sslParameters;
            return this;
        }

        public Builder withCiphers(List<String> ciphers) {
            this.ciphers = ciphers;
            return this;
        }

        public Builder withProtocols(List<String> protocols) {
            this.protocols = protocols;
            return this;
        }

        public Builder withKeyManager(X509ExtendedKeyManager keyManager) {
            this.keyManager = keyManager;
            return this;
        }

        public Builder withTrustManager(X509ExtendedTrustManager trustManager) {
            this.trustManager = trustManager;
            return this;
        }

        public SSLMaterial build() {
            SSLMaterial sslMaterial = new SSLMaterial();
            sslMaterial.sslContext = this.sslContext;
            sslMaterial.keyManager = this.keyManager;
            sslMaterial.trustManager = this.trustManager;
            sslMaterial.hostnameVerifier = this.hostnameVerifier;
            sslMaterial.sslParameters = this.sslParameters;
            sslMaterial.ciphers = this.ciphers;
            sslMaterial.protocols = this.protocols;
            return sslMaterial;
        }
    }
}

