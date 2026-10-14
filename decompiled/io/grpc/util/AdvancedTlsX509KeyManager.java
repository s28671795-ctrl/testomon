/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import com.google.common.base.Preconditions;
import io.grpc.ExperimentalApi;
import io.grpc.util.CertificateUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/8024")
public final class AdvancedTlsX509KeyManager
extends X509ExtendedKeyManager {
    private static final Logger log = Logger.getLogger(AdvancedTlsX509KeyManager.class.getName());
    private static final int MINIMUM_REFRESH_PERIOD_IN_MINUTES = 1;
    private volatile KeyInfo keyInfo;

    @Override
    public PrivateKey getPrivateKey(String alias) {
        if (alias.equals("default")) {
            return this.keyInfo.key;
        }
        return null;
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        if (alias.equals("default")) {
            return Arrays.copyOf(this.keyInfo.certs, this.keyInfo.certs.length);
        }
        return null;
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        return new String[]{"default"};
    }

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        return "default";
    }

    @Override
    public String chooseEngineClientAlias(String[] keyType, Principal[] issuers, SSLEngine engine) {
        return "default";
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        return new String[]{"default"};
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        return "default";
    }

    @Override
    public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine engine) {
        return "default";
    }

    public void updateIdentityCredentials(PrivateKey key, X509Certificate[] certs) {
        this.keyInfo = new KeyInfo(Preconditions.checkNotNull(key, "key"), Preconditions.checkNotNull(certs, "certs"));
    }

    public Closeable updateIdentityCredentialsFromFile(File keyFile, File certFile, long period, TimeUnit unit, ScheduledExecutorService executor2) throws IOException, GeneralSecurityException {
        UpdateResult newResult = this.readAndUpdate(keyFile, certFile, 0L, 0L);
        if (!newResult.success) {
            throw new GeneralSecurityException("Files were unmodified before their initial update. Probably a bug.");
        }
        if (Preconditions.checkNotNull(unit, "unit").toMinutes(period) < 1L) {
            log.log(Level.FINE, "Provided refresh period of {0} {1} is too small. Default value of {2} minute(s) will be used.", new Object[]{period, unit.name(), 1});
            period = 1L;
            unit = TimeUnit.MINUTES;
        }
        ScheduledFuture<?> future = Preconditions.checkNotNull(executor2, "executor").scheduleWithFixedDelay(new LoadFilePathExecution(keyFile, certFile), period, period, unit);
        return () -> future.cancel(false);
    }

    public void updateIdentityCredentialsFromFile(File keyFile, File certFile) throws IOException, GeneralSecurityException {
        UpdateResult newResult = this.readAndUpdate(keyFile, certFile, 0L, 0L);
        if (!newResult.success) {
            throw new GeneralSecurityException("Files were unmodified before their initial update. Probably a bug.");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private UpdateResult readAndUpdate(File keyFile, File certFile, long oldKeyTime, long oldCertTime) throws IOException, GeneralSecurityException {
        long newKeyTime = Preconditions.checkNotNull(keyFile, "keyFile").lastModified();
        long newCertTime = Preconditions.checkNotNull(certFile, "certFile").lastModified();
        if (newKeyTime != oldKeyTime && newCertTime != oldCertTime) {
            try (FileInputStream keyInputStream = new FileInputStream(keyFile);){
                UpdateResult updateResult;
                PrivateKey key = CertificateUtils.getPrivateKey(keyInputStream);
                FileInputStream certInputStream = new FileInputStream(certFile);
                try {
                    X509Certificate[] certs = CertificateUtils.getX509Certificates(certInputStream);
                    this.updateIdentityCredentials(key, certs);
                    updateResult = new UpdateResult(true, newKeyTime, newCertTime);
                }
                catch (Throwable throwable) {
                    certInputStream.close();
                    throw throwable;
                }
                certInputStream.close();
                return updateResult;
            }
        }
        return new UpdateResult(false, oldKeyTime, oldCertTime);
    }

    public static interface Closeable
    extends java.io.Closeable {
        @Override
        public void close();
    }

    private static class UpdateResult {
        boolean success;
        long keyTime;
        long certTime;

        public UpdateResult(boolean success, long keyTime, long certTime) {
            this.success = success;
            this.keyTime = keyTime;
            this.certTime = certTime;
        }
    }

    private class LoadFilePathExecution
    implements Runnable {
        File keyFile;
        File certFile;
        long currentKeyTime;
        long currentCertTime;

        public LoadFilePathExecution(File keyFile, File certFile) {
            this.keyFile = keyFile;
            this.certFile = certFile;
            this.currentKeyTime = 0L;
            this.currentCertTime = 0L;
        }

        @Override
        public void run() {
            try {
                UpdateResult newResult = AdvancedTlsX509KeyManager.this.readAndUpdate(this.keyFile, this.certFile, this.currentKeyTime, this.currentCertTime);
                if (newResult.success) {
                    this.currentKeyTime = newResult.keyTime;
                    this.currentCertTime = newResult.certTime;
                }
            }
            catch (IOException | GeneralSecurityException e) {
                log.log(Level.SEVERE, String.format("Failed refreshing private key and certificate chain from files. Using previous ones (keyFile lastModified = %s, certFile lastModified = %s)", this.keyFile.lastModified(), this.certFile.lastModified()), e);
            }
        }
    }

    private static class KeyInfo {
        final PrivateKey key;
        final X509Certificate[] certs;

        public KeyInfo(PrivateKey key, X509Certificate[] certs) {
            this.key = key;
            this.certs = certs;
        }
    }
}

