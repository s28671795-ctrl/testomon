/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import nl.altindag.ssl.trustmanager.DelegatingX509ExtendedTrustManager;
import nl.altindag.ssl.trustmanager.TrustManagerRunnable;
import nl.altindag.ssl.util.internal.ValidationUtils;

public class HotSwappableX509ExtendedTrustManager
extends DelegatingX509ExtendedTrustManager {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    protected final Lock readLock = this.readWriteLock.readLock();
    protected final Lock writeLock = this.readWriteLock.writeLock();

    public HotSwappableX509ExtendedTrustManager(X509ExtendedTrustManager trustManager) {
        super(trustManager);
    }

    public void setTrustManager(X509ExtendedTrustManager trustManager) {
        this.writeLock.lock();
        try {
            this.trustManager = ValidationUtils.requireNotNull(trustManager, (String)ValidationUtils.GENERIC_EXCEPTION_MESSAGE.apply("TrustManager"));
        }
        finally {
            this.writeLock.unlock();
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.checkTrusted(() -> super.checkServerTrusted(chain, authType));
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.checkTrusted(() -> super.checkServerTrusted(chain, authType, socket));
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine sslEngine) throws CertificateException {
        this.checkTrusted(() -> super.checkServerTrusted(chain, authType, sslEngine));
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.checkTrusted(() -> super.checkClientTrusted(chain, authType));
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.checkTrusted(() -> super.checkClientTrusted(chain, authType, socket));
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine sslEngine) throws CertificateException {
        this.checkTrusted(() -> super.checkClientTrusted(chain, authType, sslEngine));
    }

    private void checkTrusted(TrustManagerRunnable trustManagerRunnable) throws CertificateException {
        this.readLock.lock();
        try {
            trustManagerRunnable.run();
        }
        finally {
            this.readLock.unlock();
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return this.getObjectSafely(() -> super.getAcceptedIssuers());
    }

    @Override
    public X509ExtendedTrustManager getInnerTrustManager() {
        return this.getObjectSafely(() -> (X509ExtendedTrustManager)super.getInnerTrustManager());
    }

    private <T> T getObjectSafely(Supplier<T> supplier) {
        this.readLock.lock();
        try {
            T t2 = supplier.get();
            return t2;
        }
        finally {
            this.readLock.unlock();
        }
    }
}

