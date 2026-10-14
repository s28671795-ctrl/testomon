/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.Arrays;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.TrustManager;
import org.ldaptive.AbstractConfig;
import org.ldaptive.ssl.CertificateHostnameVerifier;
import org.ldaptive.ssl.CredentialConfig;
import org.ldaptive.ssl.DefaultSSLContextInitializer;
import org.ldaptive.ssl.SSLContextInitializer;

public class SslConfig
extends AbstractConfig {
    private CredentialConfig credentialConfig;
    private TrustManager[] trustManagers;
    private CertificateHostnameVerifier hostnameVerifier;
    private String[] enabledCipherSuites;
    private String[] enabledProtocols;
    private HandshakeCompletedListener[] handshakeCompletedListeners;
    private Duration handshakeTimeout = Duration.ofMinutes(1L);

    public SslConfig() {
    }

    public SslConfig(CredentialConfig config) {
        this.credentialConfig = config;
    }

    public SslConfig(TrustManager ... managers) {
        this.trustManagers = managers;
    }

    public SslConfig(CredentialConfig config, TrustManager ... managers) {
        this.credentialConfig = config;
        this.trustManagers = managers;
    }

    public boolean isEmpty() {
        return this.credentialConfig == null && this.trustManagers == null && this.hostnameVerifier == null && this.enabledCipherSuites == null && this.enabledProtocols == null && this.handshakeCompletedListeners == null;
    }

    public CredentialConfig getCredentialConfig() {
        return this.credentialConfig;
    }

    public void setCredentialConfig(CredentialConfig config) {
        this.checkImmutable();
        this.logger.trace("setting credentialConfig: {}", (Object)config);
        this.credentialConfig = config;
    }

    public TrustManager[] getTrustManagers() {
        return this.trustManagers;
    }

    public void setTrustManagers(TrustManager ... managers) {
        this.checkImmutable();
        this.checkArrayContainsNull(managers);
        this.logger.trace("setting trustManagers: {}", (Object)Arrays.toString(managers));
        this.trustManagers = managers;
    }

    public CertificateHostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public void setHostnameVerifier(CertificateHostnameVerifier verifier) {
        this.checkImmutable();
        this.logger.trace("setting hostnameVerifier: {}", (Object)verifier);
        this.hostnameVerifier = verifier;
    }

    public String[] getEnabledCipherSuites() {
        return this.enabledCipherSuites;
    }

    public void setEnabledCipherSuites(String ... suites) {
        this.checkImmutable();
        this.checkArrayContainsNull(suites);
        this.logger.trace("setting enabledCipherSuites: {}", (Object)Arrays.toString(suites));
        this.enabledCipherSuites = suites;
    }

    public String[] getEnabledProtocols() {
        return this.enabledProtocols;
    }

    public void setEnabledProtocols(String ... protocols) {
        this.checkImmutable();
        this.checkArrayContainsNull(protocols);
        this.logger.trace("setting enabledProtocols: {}", (Object)Arrays.toString(protocols));
        this.enabledProtocols = protocols;
    }

    public HandshakeCompletedListener[] getHandshakeCompletedListeners() {
        return this.handshakeCompletedListeners;
    }

    public void setHandshakeCompletedListeners(HandshakeCompletedListener ... listeners) {
        this.checkImmutable();
        this.checkArrayContainsNull(listeners);
        this.logger.trace("setting handshakeCompletedListeners: {}", (Object)Arrays.toString(this.handshakeCompletedListeners));
        this.handshakeCompletedListeners = listeners;
    }

    public Duration getHandshakeTimeout() {
        return this.handshakeTimeout;
    }

    public void setHandshakeTimeout(Duration time) {
        this.checkImmutable();
        if (time == null || time.isNegative()) {
            throw new IllegalArgumentException("Handshake timeout cannot be null or negative");
        }
        this.logger.trace("setting handshakeTimeout: {}", (Object)time);
        this.handshakeTimeout = time;
    }

    public static SslConfig copy(SslConfig config) {
        SslConfig sc = new SslConfig();
        sc.setCredentialConfig(config.getCredentialConfig());
        sc.setTrustManagers(config.getTrustManagers());
        sc.setHostnameVerifier(config.getHostnameVerifier());
        sc.setEnabledCipherSuites(config.getEnabledCipherSuites());
        sc.setEnabledProtocols(config.getEnabledProtocols());
        sc.setHandshakeCompletedListeners(config.getHandshakeCompletedListeners());
        sc.setHandshakeTimeout(config.getHandshakeTimeout());
        return sc;
    }

    public SSLContextInitializer createSSLContextInitializer() throws GeneralSecurityException {
        SSLContextInitializer initializer = this.credentialConfig != null ? this.credentialConfig.createSSLContextInitializer() : (this.trustManagers != null ? new DefaultSSLContextInitializer(false) : new DefaultSSLContextInitializer(true));
        if (this.trustManagers != null) {
            initializer.setTrustManagers(this.trustManagers);
        }
        return initializer;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "credentialConfig=" + this.credentialConfig + ", " + "trustManagers=" + Arrays.toString(this.trustManagers) + ", " + "hostnameVerifier=" + this.hostnameVerifier + ", " + "enabledCipherSuites=" + Arrays.toString(this.enabledCipherSuites) + ", " + "enabledProtocols=" + Arrays.toString(this.enabledProtocols) + ", " + "handshakeCompletedListeners=" + Arrays.toString(this.handshakeCompletedListeners) + ", " + "handshakeTimeout=" + this.handshakeTimeout + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final SslConfig object = new SslConfig();

        protected Builder() {
        }

        public Builder credentialConfig(CredentialConfig config) {
            this.object.setCredentialConfig(config);
            return this;
        }

        public Builder trustManagers(TrustManager ... managers) {
            this.object.setTrustManagers(managers);
            return this;
        }

        public Builder hostnameVerifier(CertificateHostnameVerifier verifier) {
            this.object.setHostnameVerifier(verifier);
            return this;
        }

        public Builder cipherSuites(String ... suites) {
            this.object.setEnabledCipherSuites(suites);
            return this;
        }

        public Builder protocols(String ... protocols) {
            this.object.setEnabledProtocols(protocols);
            return this;
        }

        public Builder handshakeListeners(HandshakeCompletedListener ... listeners) {
            this.object.setHandshakeCompletedListeners(listeners);
            return this;
        }

        public Builder handshakeTimeout(Duration timeout2) {
            this.object.setHandshakeTimeout(timeout2);
            return this;
        }

        public SslConfig build() {
            return this.object;
        }
    }
}

