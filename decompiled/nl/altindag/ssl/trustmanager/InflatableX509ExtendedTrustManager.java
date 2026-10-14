/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager;

import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import nl.altindag.ssl.exception.GenericKeyStoreException;
import nl.altindag.ssl.model.TrustManagerParameters;
import nl.altindag.ssl.trustmanager.HotSwappableX509ExtendedTrustManager;
import nl.altindag.ssl.trustmanager.TrustManagerRunnable;
import nl.altindag.ssl.util.CertificateUtils;
import nl.altindag.ssl.util.KeyStoreUtils;
import nl.altindag.ssl.util.TrustManagerUtils;
import nl.altindag.ssl.util.internal.CollectionUtils;
import nl.altindag.ssl.util.internal.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InflatableX509ExtendedTrustManager
extends HotSwappableX509ExtendedTrustManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(InflatableX509ExtendedTrustManager.class);
    private static final BiPredicate<KeyStore, X509Certificate> IGNORE_DUPLICATE_CHECKER = (trustStore, certificate) -> false;
    private final KeyStore trustStore;
    private final Path trustStorePath;
    private final char[] trustStorePassword;
    private final Predicate<TrustManagerParameters> trustManagerParametersPredicate;

    public InflatableX509ExtendedTrustManager() {
        this(null, null, null, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public InflatableX509ExtendedTrustManager(Path trustStorePath, char[] trustStorePassword, String trustStoreType, Predicate<TrustManagerParameters> trustManagerParametersPredicate) {
        super(TrustManagerUtils.createDummyTrustManager());
        this.writeLock.lock();
        try {
            this.trustStorePath = trustStorePath;
            this.trustStorePassword = trustStorePassword;
            this.trustManagerParametersPredicate = Optional.ofNullable(trustManagerParametersPredicate).orElse(trustManagerParameters -> false);
            if (trustStorePath != null && StringUtils.isNotBlank(trustStoreType)) {
                if (Files.exists(trustStorePath, new LinkOption[0])) {
                    this.trustStore = KeyStoreUtils.loadKeyStore(trustStorePath, trustStorePassword, trustStoreType);
                    if (KeyStoreUtils.containsTrustMaterial(this.trustStore)) {
                        this.setTrustManager(TrustManagerUtils.createTrustManager(this.trustStore));
                    }
                } else {
                    this.trustStore = KeyStoreUtils.createKeyStore(trustStoreType, trustStorePassword);
                }
            } else {
                this.trustStore = KeyStoreUtils.createKeyStore();
            }
        }
        finally {
            this.writeLock.unlock();
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.checkTrusted(() -> super.checkServerTrusted(chain, authType), chain, authType, null, null);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.checkTrusted(() -> super.checkServerTrusted(chain, authType, socket), chain, authType, socket, null);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine sslEngine) throws CertificateException {
        this.checkTrusted(() -> super.checkServerTrusted(chain, authType, sslEngine), chain, authType, null, sslEngine);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.checkTrusted(() -> super.checkClientTrusted(chain, authType), chain, authType, null, null);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.checkTrusted(() -> super.checkClientTrusted(chain, authType, socket), chain, authType, socket, null);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine sslEngine) throws CertificateException {
        this.checkTrusted(() -> super.checkClientTrusted(chain, authType, sslEngine), chain, authType, null, sslEngine);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void checkTrusted(TrustManagerRunnable trustManagerRunnable, X509Certificate[] chain, String authType, Socket socket, SSLEngine sslEngine) throws CertificateException {
        block11: {
            try {
                this.readLock.lock();
                try {
                    trustManagerRunnable.run();
                }
                finally {
                    this.readLock.unlock();
                }
            }
            catch (CertificateException e) {
                this.writeLock.lock();
                try {
                    trustManagerRunnable.run();
                }
                catch (CertificateException e2) {
                    TrustManagerParameters trustManagerParameters = new TrustManagerParameters(chain, authType, socket, sslEngine);
                    boolean shouldBeTrusted = this.trustManagerParametersPredicate.test(trustManagerParameters);
                    if (shouldBeTrusted) {
                        this.addCertificates(Collections.singletonList(chain[0]), IGNORE_DUPLICATE_CHECKER);
                        break block11;
                    }
                    throw e2;
                }
                finally {
                    this.writeLock.unlock();
                }
            }
        }
    }

    public void addCertificates(List<X509Certificate> certificates) {
        this.addCertificates(certificates, KeyStoreUtils::containsCertificate);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void addCertificates(List<X509Certificate> certificates, BiPredicate<KeyStore, X509Certificate> duplicateChecker) {
        this.writeLock.lock();
        try {
            if (CollectionUtils.isEmpty(certificates)) {
                return;
            }
            for (X509Certificate certificate : certificates) {
                if (duplicateChecker.test(this.trustStore, certificate)) continue;
                String alias = this.generateAlias(certificate);
                this.trustStore.setCertificateEntry(alias, certificate);
                LOGGER.info("Added certificate for [{}]", (Object)alias);
            }
            X509ExtendedTrustManager trustManager = TrustManagerUtils.createTrustManager(this.trustStore);
            this.setTrustManager(trustManager);
            this.getTrustStorePath().ifPresent(path -> KeyStoreUtils.write(path, this.trustStore, this.trustStorePassword));
        }
        catch (KeyStoreException | GenericKeyStoreException e) {
            LOGGER.error("Cannot add certificate", e);
        }
        finally {
            this.writeLock.unlock();
        }
    }

    private String generateAlias(Certificate certificate) {
        return CertificateUtils.generateUniqueAlias(certificate, alias -> {
            try {
                return this.trustStore.containsAlias((String)alias);
            }
            catch (KeyStoreException e) {
                throw new GenericKeyStoreException(e);
            }
        });
    }

    private Optional<Path> getTrustStorePath() {
        return Optional.ofNullable(this.trustStorePath);
    }
}

