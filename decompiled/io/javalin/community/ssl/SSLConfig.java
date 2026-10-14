/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.community.ssl;

import io.javalin.community.ssl.SSLConfigException;
import io.javalin.community.ssl.TLSConfig;
import io.javalin.community.ssl.TrustConfig;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Provider;
import java.util.function.Consumer;
import org.eclipse.jetty.server.ServerConnector;
import org.jetbrains.annotations.Nullable;

public class SSLConfig {
    public String host = null;
    public boolean insecure = true;
    public boolean secure = true;
    public int securePort = 443;
    public int insecurePort = 80;
    public boolean redirect = false;
    public boolean http2 = true;
    public boolean sniHostCheck = true;
    public TLSConfig tlsConfig = TLSConfig.INTERMEDIATE;
    public final boolean enableHttp3 = false;
    public final boolean disableHttp3Upgrade = false;
    public final int http3Port = 443;
    public InnerConfig inner = new InnerConfig();
    @Deprecated(forRemoval=true, since="5.3.2")
    public Consumer<ServerConnector> configConnectors = null;
    public Provider securityProvider = null;
    private TrustConfig trustConfig = null;

    public void pemFromPath(String certificatePath, String privateKeyPath) {
        if (this.inner.identityLoadingType != InnerConfig.IdentityLoadingType.NONE) {
            throw new SSLConfigException(SSLConfigException.Types.MULTIPLE_IDENTITY_LOADING_OPTIONS);
        }
        this.inner.pemCertificatesPath = Paths.get(certificatePath, new String[0]);
        this.inner.pemPrivateKeyPath = Paths.get(privateKeyPath, new String[0]);
        this.inner.identityLoadingType = InnerConfig.IdentityLoadingType.PEM_FILE_PATH;
    }

    public void pemFromPath(String certificatePath, String privateKeyPath, String privateKeyPassword) {
        this.pemFromPath(certificatePath, privateKeyPath);
        this.inner.privateKeyPassword = privateKeyPassword;
    }

    public void pemFromClasspath(String certificateFile, String privateKeyFile) {
        if (this.inner.identityLoadingType != InnerConfig.IdentityLoadingType.NONE) {
            throw new SSLConfigException(SSLConfigException.Types.MULTIPLE_IDENTITY_LOADING_OPTIONS);
        }
        this.inner.pemCertificatesFile = certificateFile;
        this.inner.pemPrivateKeyFile = privateKeyFile;
        this.inner.identityLoadingType = InnerConfig.IdentityLoadingType.PEM_CLASS_PATH;
    }

    public void pemFromClasspath(String certificateFile, String privateKeyFile, String privateKeyPassword) {
        this.pemFromClasspath(certificateFile, privateKeyFile);
        this.inner.privateKeyPassword = privateKeyPassword;
    }

    public void pemFromInputStream(InputStream certificateInputStream, InputStream privateKeyInputStream) {
        if (this.inner.identityLoadingType != InnerConfig.IdentityLoadingType.NONE) {
            throw new SSLConfigException(SSLConfigException.Types.MULTIPLE_IDENTITY_LOADING_OPTIONS);
        }
        this.inner.pemCertificatesInputStream = certificateInputStream;
        this.inner.pemPrivateKeyInputStream = privateKeyInputStream;
        this.inner.identityLoadingType = InnerConfig.IdentityLoadingType.PEM_INPUT_STREAM;
    }

    public void pemFromInputStream(InputStream certificateInputStream, InputStream privateKeyInputStream, String privateKeyPassword) {
        this.pemFromInputStream(certificateInputStream, privateKeyInputStream);
        this.inner.privateKeyPassword = privateKeyPassword;
    }

    public void pemFromString(String certificateString, String privateKeyString) {
        if (this.inner.identityLoadingType != InnerConfig.IdentityLoadingType.NONE) {
            throw new SSLConfigException(SSLConfigException.Types.MULTIPLE_IDENTITY_LOADING_OPTIONS);
        }
        this.inner.pemCertificatesString = certificateString;
        this.inner.pemPrivateKeyString = privateKeyString;
        this.inner.identityLoadingType = InnerConfig.IdentityLoadingType.PEM_STRING;
    }

    public void pemFromString(String certificateString, String privateKeyString, String privateKeyPassword) {
        this.pemFromString(certificateString, privateKeyString);
        this.inner.privateKeyPassword = privateKeyPassword;
    }

    public void keystoreFromPath(String keyStorePath, String keyStorePassword) {
        if (this.inner.identityLoadingType != InnerConfig.IdentityLoadingType.NONE) {
            throw new SSLConfigException(SSLConfigException.Types.MULTIPLE_IDENTITY_LOADING_OPTIONS);
        }
        this.inner.keyStorePath = Paths.get(keyStorePath, new String[0]);
        this.inner.identityLoadingType = InnerConfig.IdentityLoadingType.KEY_STORE_FILE_PATH;
        this.inner.keyStorePassword = keyStorePassword;
    }

    public void keystoreFromInputStream(InputStream keyStoreInputStream, String keyStorePassword) {
        if (this.inner.identityLoadingType != InnerConfig.IdentityLoadingType.NONE) {
            throw new SSLConfigException(SSLConfigException.Types.MULTIPLE_IDENTITY_LOADING_OPTIONS);
        }
        this.inner.keyStoreInputStream = keyStoreInputStream;
        this.inner.identityLoadingType = InnerConfig.IdentityLoadingType.KEY_STORE_INPUT_STREAM;
        this.inner.keyStorePassword = keyStorePassword;
    }

    public void keystoreFromClasspath(String keyStoreFile, String keyStorePassword) {
        if (this.inner.identityLoadingType != InnerConfig.IdentityLoadingType.NONE) {
            throw new SSLConfigException(SSLConfigException.Types.MULTIPLE_IDENTITY_LOADING_OPTIONS);
        }
        this.inner.keyStoreFile = keyStoreFile;
        this.inner.identityLoadingType = InnerConfig.IdentityLoadingType.KEY_STORE_CLASS_PATH;
        this.inner.keyStorePassword = keyStorePassword;
    }

    public void configConnectors(Consumer<ServerConnector> configConnectors) {
        this.configConnectors = configConnectors;
    }

    public void withTrustConfig(Consumer<TrustConfig> trustConfigConsumer) {
        this.trustConfig = new TrustConfig();
        trustConfigConsumer.accept(this.trustConfig);
    }

    @Deprecated
    public Consumer<ServerConnector> getConfigConnectors() {
        return this.configConnectors;
    }

    public TrustConfig getTrustConfig() {
        return this.trustConfig;
    }

    public static class InnerConfig {
        IdentityLoadingType identityLoadingType = IdentityLoadingType.NONE;
        @Nullable
        public String pemCertificatesFile = null;
        @Nullable
        public String pemPrivateKeyFile = null;
        @Nullable
        public InputStream pemCertificatesInputStream = null;
        @Nullable
        public InputStream pemPrivateKeyInputStream = null;
        @Nullable
        public Path pemCertificatesPath = null;
        @Nullable
        public Path pemPrivateKeyPath = null;
        @Nullable
        public String pemCertificatesString = null;
        @Nullable
        public String pemPrivateKeyString = null;
        @Nullable
        public String privateKeyPassword = null;
        @Nullable
        public Path keyStorePath = null;
        @Nullable
        public String keyStoreFile = null;
        @Nullable
        public InputStream keyStoreInputStream = null;
        @Nullable
        public String keyStorePassword = null;

        public IdentityLoadingType getIdentityLoadingType() {
            return this.identityLoadingType;
        }

        public static enum IdentityLoadingType {
            NONE,
            PEM_CLASS_PATH,
            PEM_FILE_PATH,
            PEM_STRING,
            PEM_INPUT_STREAM,
            KEY_STORE_CLASS_PATH,
            KEY_STORE_FILE_PATH,
            KEY_STORE_INPUT_STREAM;

        }
    }
}

