/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import io.grpc.ServerCredentials;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;

public final class TlsServerCredentials
extends ServerCredentials {
    private final boolean fakeFeature;
    private final byte[] certificateChain;
    private final byte[] privateKey;
    private final String privateKeyPassword;
    private final List<KeyManager> keyManagers;
    private final ClientAuth clientAuth;
    private final byte[] rootCertificates;
    private final List<TrustManager> trustManagers;

    public static ServerCredentials create(File certChain, File privateKey) throws IOException {
        return TlsServerCredentials.newBuilder().keyManager(certChain, privateKey).build();
    }

    public static ServerCredentials create(InputStream certChain, InputStream privateKey) throws IOException {
        return TlsServerCredentials.newBuilder().keyManager(certChain, privateKey).build();
    }

    TlsServerCredentials(Builder builder) {
        this.fakeFeature = builder.fakeFeature;
        this.certificateChain = builder.certificateChain;
        this.privateKey = builder.privateKey;
        this.privateKeyPassword = builder.privateKeyPassword;
        this.keyManagers = builder.keyManagers;
        this.clientAuth = builder.clientAuth;
        this.rootCertificates = builder.rootCertificates;
        this.trustManagers = builder.trustManagers;
    }

    public byte[] getCertificateChain() {
        if (this.certificateChain == null) {
            return null;
        }
        return Arrays.copyOf(this.certificateChain, this.certificateChain.length);
    }

    public byte[] getPrivateKey() {
        if (this.privateKey == null) {
            return null;
        }
        return Arrays.copyOf(this.privateKey, this.privateKey.length);
    }

    public String getPrivateKeyPassword() {
        return this.privateKeyPassword;
    }

    public List<KeyManager> getKeyManagers() {
        return this.keyManagers;
    }

    public ClientAuth getClientAuth() {
        return this.clientAuth;
    }

    public byte[] getRootCertificates() {
        if (this.rootCertificates == null) {
            return null;
        }
        return Arrays.copyOf(this.rootCertificates, this.rootCertificates.length);
    }

    public List<TrustManager> getTrustManagers() {
        return this.trustManagers;
    }

    public Set<Feature> incomprehensible(Set<Feature> understoodFeatures) {
        EnumSet<Feature> incomprehensible = EnumSet.noneOf(Feature.class);
        if (this.fakeFeature) {
            TlsServerCredentials.requiredFeature(understoodFeatures, incomprehensible, Feature.FAKE);
        }
        if (this.clientAuth != ClientAuth.NONE) {
            TlsServerCredentials.requiredFeature(understoodFeatures, incomprehensible, Feature.MTLS);
        }
        if (this.keyManagers != null || this.trustManagers != null) {
            TlsServerCredentials.requiredFeature(understoodFeatures, incomprehensible, Feature.CUSTOM_MANAGERS);
        }
        return Collections.unmodifiableSet(incomprehensible);
    }

    private static void requiredFeature(Set<Feature> understoodFeatures, Set<Feature> incomprehensible, Feature feature) {
        if (!understoodFeatures.contains((Object)feature)) {
            incomprehensible.add(feature);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static enum ClientAuth {
        NONE,
        OPTIONAL,
        REQUIRE;

    }

    public static final class Builder {
        private boolean fakeFeature;
        private byte[] certificateChain;
        private byte[] privateKey;
        private String privateKeyPassword;
        private List<KeyManager> keyManagers;
        private ClientAuth clientAuth = ClientAuth.NONE;
        private byte[] rootCertificates;
        private List<TrustManager> trustManagers;

        private Builder() {
        }

        public Builder requireFakeFeature() {
            this.fakeFeature = true;
            return this;
        }

        public Builder keyManager(File certChain, File privateKey) throws IOException {
            return this.keyManager(certChain, privateKey, null);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public Builder keyManager(File certChain, File privateKey, String privateKeyPassword) throws IOException {
            try (FileInputStream certChainIs = new FileInputStream(certChain);){
                Builder builder;
                FileInputStream privateKeyIs = new FileInputStream(privateKey);
                try {
                    builder = this.keyManager(certChainIs, privateKeyIs, privateKeyPassword);
                }
                catch (Throwable throwable) {
                    ((InputStream)privateKeyIs).close();
                    throw throwable;
                }
                ((InputStream)privateKeyIs).close();
                return builder;
            }
        }

        public Builder keyManager(InputStream certChain, InputStream privateKey) throws IOException {
            return this.keyManager(certChain, privateKey, null);
        }

        public Builder keyManager(InputStream certChain, InputStream privateKey, String privateKeyPassword) throws IOException {
            byte[] certChainBytes = ByteStreams.toByteArray(certChain);
            byte[] privateKeyBytes = ByteStreams.toByteArray(privateKey);
            this.clearKeyManagers();
            this.certificateChain = certChainBytes;
            this.privateKey = privateKeyBytes;
            this.privateKeyPassword = privateKeyPassword;
            return this;
        }

        public Builder keyManager(KeyManager ... keyManagers) {
            List<KeyManager> keyManagerList = Collections.unmodifiableList(new ArrayList<KeyManager>(Arrays.asList(keyManagers)));
            this.clearKeyManagers();
            this.keyManagers = keyManagerList;
            return this;
        }

        private void clearKeyManagers() {
            this.certificateChain = null;
            this.privateKey = null;
            this.privateKeyPassword = null;
            this.keyManagers = null;
        }

        public Builder clientAuth(ClientAuth clientAuth) {
            Preconditions.checkNotNull(clientAuth, "clientAuth");
            this.clientAuth = clientAuth;
            return this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public Builder trustManager(File rootCerts) throws IOException {
            try (FileInputStream rootCertsIs = new FileInputStream(rootCerts);){
                Builder builder = this.trustManager(rootCertsIs);
                return builder;
            }
        }

        public Builder trustManager(InputStream rootCerts) throws IOException {
            byte[] rootCertsBytes = ByteStreams.toByteArray(rootCerts);
            this.clearTrustManagers();
            this.rootCertificates = rootCertsBytes;
            return this;
        }

        public Builder trustManager(TrustManager ... trustManagers) {
            List<TrustManager> trustManagerList = Collections.unmodifiableList(new ArrayList<TrustManager>(Arrays.asList(trustManagers)));
            this.clearTrustManagers();
            this.trustManagers = trustManagerList;
            return this;
        }

        private void clearTrustManagers() {
            this.rootCertificates = null;
            this.trustManagers = null;
        }

        public ServerCredentials build() {
            if (this.certificateChain == null && this.keyManagers == null) {
                throw new IllegalStateException("A key manager is required");
            }
            return new TlsServerCredentials(this);
        }
    }

    public static enum Feature {
        FAKE,
        MTLS,
        CUSTOM_MANAGERS;

    }
}

