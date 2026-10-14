/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.keymanager;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;
import nl.altindag.ssl.keymanager.DelegatingX509ExtendedKeyManager;
import nl.altindag.ssl.util.internal.ValidationUtils;

public final class HotSwappableX509ExtendedKeyManager
extends DelegatingX509ExtendedKeyManager {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = this.readWriteLock.readLock();
    private final Lock writeLock = this.readWriteLock.writeLock();

    public HotSwappableX509ExtendedKeyManager(X509ExtendedKeyManager keyManager) {
        super(keyManager);
    }

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        return this.getObjectSafely(() -> super.chooseClientAlias(keyType, issuers, socket));
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        return this.getObjectSafely(() -> super.chooseServerAlias(keyType, issuers, socket));
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        return this.getObjectSafely(() -> super.getPrivateKey(alias));
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        return this.getObjectSafely(() -> super.getCertificateChain(alias));
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        return this.getObjectSafely(() -> super.getClientAliases(keyType, issuers));
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        return this.getObjectSafely(() -> super.getServerAliases(keyType, issuers));
    }

    @Override
    public X509ExtendedKeyManager getInnerKeyManager() {
        return this.getObjectSafely(() -> (X509ExtendedKeyManager)super.getInnerKeyManager());
    }

    @Override
    public String chooseEngineClientAlias(String[] keyTypes, Principal[] issuers, SSLEngine sslEngine) {
        return this.getObjectSafely(() -> super.chooseEngineClientAlias(keyTypes, issuers, sslEngine));
    }

    @Override
    public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine sslEngine) {
        return this.getObjectSafely(() -> super.chooseEngineServerAlias(keyType, issuers, sslEngine));
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

    public void setKeyManager(X509ExtendedKeyManager keyManager) {
        this.writeLock.lock();
        try {
            this.keyManager = ValidationUtils.requireNotNull(keyManager, (String)ValidationUtils.GENERIC_EXCEPTION_MESSAGE.apply("KeyManager"));
        }
        finally {
            this.writeLock.unlock();
        }
    }
}

