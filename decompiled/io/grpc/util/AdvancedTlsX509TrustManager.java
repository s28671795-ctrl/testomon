/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import io.grpc.ExperimentalApi;
import io.grpc.util.CertificateUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedTrustManager;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/8024")
@IgnoreJRERequirement
public final class AdvancedTlsX509TrustManager
extends X509ExtendedTrustManager {
    private static final Logger log = Logger.getLogger(AdvancedTlsX509TrustManager.class.getName());
    private final Verification verification;
    private final SslSocketAndEnginePeerVerifier socketAndEnginePeerVerifier;
    private volatile X509ExtendedTrustManager delegateManager = null;

    private AdvancedTlsX509TrustManager(Verification verification, SslSocketAndEnginePeerVerifier socketAndEnginePeerVerifier) throws CertificateException {
        this.verification = verification;
        this.socketAndEnginePeerVerifier = socketAndEnginePeerVerifier;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        throw new CertificateException("Not enough information to validate peer. SSLEngine or Socket required.");
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.checkTrusted(chain, authType, null, socket, false);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        this.checkTrusted(chain, authType, engine, null, false);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        this.checkTrusted(chain, authType, engine, null, true);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        throw new CertificateException("Not enough information to validate peer. SSLEngine or Socket required.");
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.checkTrusted(chain, authType, null, socket, true);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        if (this.delegateManager == null) {
            return new X509Certificate[0];
        }
        return this.delegateManager.getAcceptedIssuers();
    }

    public void useSystemDefaultTrustCerts() throws CertificateException, KeyStoreException, NoSuchAlgorithmException {
        this.delegateManager = AdvancedTlsX509TrustManager.createDelegateTrustManager(null);
    }

    public void updateTrustCredentials(X509Certificate[] trustCerts) throws IOException, GeneralSecurityException {
        X509ExtendedTrustManager newDelegateManager;
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        int i = 1;
        for (X509Certificate cert : trustCerts) {
            String alias = Integer.toString(i);
            keyStore.setCertificateEntry(alias, cert);
            ++i;
        }
        this.delegateManager = newDelegateManager = AdvancedTlsX509TrustManager.createDelegateTrustManager(keyStore);
    }

    private static X509ExtendedTrustManager createDelegateTrustManager(KeyStore keyStore) throws CertificateException, KeyStoreException, NoSuchAlgorithmException {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);
        X509ExtendedTrustManager delegateManager = null;
        TrustManager[] tms = tmf.getTrustManagers();
        for (int j = 0; j < tms.length; ++j) {
            if (!(tms[j] instanceof X509ExtendedTrustManager)) continue;
            delegateManager = (X509ExtendedTrustManager)tms[j];
            break;
        }
        if (delegateManager == null) {
            throw new CertificateException("Failed to find X509ExtendedTrustManager with default TrustManager algorithm " + TrustManagerFactory.getDefaultAlgorithm());
        }
        return delegateManager;
    }

    private void checkTrusted(X509Certificate[] chain, String authType, SSLEngine sslEngine, Socket socket, boolean checkingServer) throws CertificateException {
        if (chain == null || chain.length == 0) {
            throw new IllegalArgumentException("Want certificate verification but got null or empty certificates");
        }
        if (sslEngine == null && socket == null) {
            throw new CertificateException("Not enough information to validate peer. SSLEngine or Socket required.");
        }
        if (this.verification != Verification.INSECURELY_SKIP_ALL_VERIFICATION) {
            X509ExtendedTrustManager currentDelegateManager = this.delegateManager;
            if (currentDelegateManager == null) {
                throw new CertificateException("No trust roots configured");
            }
            if (checkingServer) {
                String algorithm;
                String string = algorithm = this.verification == Verification.CERTIFICATE_AND_HOST_NAME_VERIFICATION ? "HTTPS" : "";
                if (sslEngine != null) {
                    SSLParameters sslParams = sslEngine.getSSLParameters();
                    sslParams.setEndpointIdentificationAlgorithm(algorithm);
                    sslEngine.setSSLParameters(sslParams);
                    currentDelegateManager.checkServerTrusted(chain, authType, sslEngine);
                } else {
                    if (!(socket instanceof SSLSocket)) {
                        throw new CertificateException("socket is not a type of SSLSocket");
                    }
                    SSLSocket sslSocket = (SSLSocket)socket;
                    SSLParameters sslParams = sslSocket.getSSLParameters();
                    sslParams.setEndpointIdentificationAlgorithm(algorithm);
                    sslSocket.setSSLParameters(sslParams);
                    currentDelegateManager.checkServerTrusted(chain, authType, sslSocket);
                }
            } else {
                currentDelegateManager.checkClientTrusted(chain, authType, sslEngine);
            }
        }
        if (this.socketAndEnginePeerVerifier != null) {
            if (sslEngine != null) {
                this.socketAndEnginePeerVerifier.verifyPeerCertificate(chain, authType, sslEngine);
            } else {
                this.socketAndEnginePeerVerifier.verifyPeerCertificate(chain, authType, socket);
            }
        }
    }

    public Closeable updateTrustCredentialsFromFile(File trustCertFile, long period, TimeUnit unit, ScheduledExecutorService executor2) throws IOException, GeneralSecurityException {
        long updatedTime = this.readAndUpdate(trustCertFile, 0L);
        if (updatedTime == 0L) {
            throw new GeneralSecurityException("Files were unmodified before their initial update. Probably a bug.");
        }
        final ScheduledFuture<?> future = executor2.scheduleWithFixedDelay(new LoadFilePathExecution(trustCertFile), period, period, unit);
        return new Closeable(){

            @Override
            public void close() {
                future.cancel(false);
            }
        };
    }

    public void updateTrustCredentialsFromFile(File trustCertFile) throws IOException, GeneralSecurityException {
        long updatedTime = this.readAndUpdate(trustCertFile, 0L);
        if (updatedTime == 0L) {
            throw new GeneralSecurityException("Files were unmodified before their initial update. Probably a bug.");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private long readAndUpdate(File trustCertFile, long oldTime) throws IOException, GeneralSecurityException {
        long newTime = trustCertFile.lastModified();
        if (newTime == oldTime) {
            return oldTime;
        }
        try (FileInputStream inputStream2 = new FileInputStream(trustCertFile);){
            X509Certificate[] certificates = CertificateUtils.getX509Certificates(inputStream2);
            this.updateTrustCredentials(certificates);
            long l = newTime;
            return l;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Verification verification = Verification.CERTIFICATE_AND_HOST_NAME_VERIFICATION;
        private SslSocketAndEnginePeerVerifier socketAndEnginePeerVerifier;

        private Builder() {
        }

        public Builder setVerification(Verification verification) {
            this.verification = verification;
            return this;
        }

        public Builder setSslSocketAndEnginePeerVerifier(SslSocketAndEnginePeerVerifier verifier) {
            this.socketAndEnginePeerVerifier = verifier;
            return this;
        }

        public AdvancedTlsX509TrustManager build() throws CertificateException {
            return new AdvancedTlsX509TrustManager(this.verification, this.socketAndEnginePeerVerifier);
        }
    }

    public static interface SslSocketAndEnginePeerVerifier {
        public void verifyPeerCertificate(X509Certificate[] var1, String var2, Socket var3) throws CertificateException;

        public void verifyPeerCertificate(X509Certificate[] var1, String var2, SSLEngine var3) throws CertificateException;
    }

    public static enum Verification {
        CERTIFICATE_AND_HOST_NAME_VERIFICATION,
        CERTIFICATE_ONLY_VERIFICATION,
        INSECURELY_SKIP_ALL_VERIFICATION;

    }

    public static interface Closeable
    extends java.io.Closeable {
        @Override
        public void close();
    }

    private class LoadFilePathExecution
    implements Runnable {
        File file;
        long currentTime;

        public LoadFilePathExecution(File file) {
            this.file = file;
            this.currentTime = 0L;
        }

        @Override
        public void run() {
            try {
                this.currentTime = AdvancedTlsX509TrustManager.this.readAndUpdate(this.file, this.currentTime);
            }
            catch (IOException | GeneralSecurityException e) {
                log.log(Level.SEVERE, "Failed refreshing trust CAs from file. Using previous CAs", e);
            }
        }
    }
}

