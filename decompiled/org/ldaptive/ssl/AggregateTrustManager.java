/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.stream.Stream;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import org.ldaptive.ssl.DefaultHostnameVerifier;
import org.ldaptive.ssl.X509ExtendedTrustManagerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AggregateTrustManager
extends X509ExtendedTrustManager {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final X509ExtendedTrustManager[] trustManagers;
    private final Strategy trustStrategy;

    public AggregateTrustManager(X509TrustManager ... managers) {
        this(Strategy.ALL, managers);
    }

    public AggregateTrustManager(Strategy strategy, X509TrustManager ... managers) {
        if (strategy == null) {
            throw new NullPointerException("Strategy cannot be null");
        }
        this.trustStrategy = strategy;
        if (managers == null || managers.length == 0) {
            throw new NullPointerException("Trust managers cannot be empty or null");
        }
        this.trustManagers = (X509ExtendedTrustManager[])Stream.of(managers).map(tm -> {
            if (tm instanceof X509ExtendedTrustManager) {
                return (X509ExtendedTrustManager)tm;
            }
            return new X509ExtendedTrustManagerWrapper((X509TrustManager)tm, new DefaultHostnameVerifier());
        }).toArray(X509ExtendedTrustManager[]::new);
    }

    public X509TrustManager[] getTrustManagers() {
        return this.trustManagers;
    }

    public Strategy getTrustStrategy() {
        return this.trustStrategy;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        CertificateException certEx = null;
        for (X509ExtendedTrustManager tm : this.trustManagers) {
            try {
                tm.checkClientTrusted(chain, authType, socket);
                this.logger.trace("checkClientTrusted for {} succeeded", (Object)tm);
                if (this.trustStrategy != Strategy.ANY) continue;
                return;
            }
            catch (CertificateException e) {
                this.logger.trace("checkClientTrusted for {} failed", (Object)tm);
                if (this.trustStrategy == Strategy.ALL) {
                    throw e;
                }
                if (certEx != null) continue;
                certEx = e;
            }
        }
        if (certEx != null) {
            throw certEx;
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        CertificateException certEx = null;
        for (X509ExtendedTrustManager tm : this.trustManagers) {
            try {
                tm.checkClientTrusted(chain, authType, engine);
                this.logger.trace("checkClientTrusted for {} succeeded", (Object)tm);
                if (this.trustStrategy != Strategy.ANY) continue;
                return;
            }
            catch (CertificateException e) {
                this.logger.trace("checkClientTrusted for {} failed", (Object)tm);
                if (this.trustStrategy == Strategy.ALL) {
                    throw e;
                }
                if (certEx != null) continue;
                certEx = e;
            }
        }
        if (certEx != null) {
            throw certEx;
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        CertificateException certEx = null;
        for (X509ExtendedTrustManager tm : this.trustManagers) {
            try {
                tm.checkClientTrusted(chain, authType);
                this.logger.trace("checkClientTrusted for {} succeeded", (Object)tm);
                if (this.trustStrategy != Strategy.ANY) continue;
                return;
            }
            catch (CertificateException e) {
                this.logger.trace("checkClientTrusted for {} failed", (Object)tm);
                if (this.trustStrategy == Strategy.ALL) {
                    throw e;
                }
                if (certEx != null) continue;
                certEx = e;
            }
        }
        if (certEx != null) {
            throw certEx;
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        CertificateException certEx = null;
        for (X509ExtendedTrustManager tm : this.trustManagers) {
            try {
                tm.checkServerTrusted(chain, authType, socket);
                this.logger.trace("checkServerTrusted for {} succeeded", (Object)tm);
                if (this.trustStrategy != Strategy.ANY) continue;
                return;
            }
            catch (CertificateException e) {
                this.logger.trace("checkServerTrusted for {} failed", (Object)tm);
                if (this.trustStrategy == Strategy.ALL) {
                    throw e;
                }
                if (certEx != null) continue;
                certEx = e;
            }
        }
        if (certEx != null) {
            throw certEx;
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        CertificateException certEx = null;
        for (X509ExtendedTrustManager tm : this.trustManagers) {
            try {
                tm.checkServerTrusted(chain, authType, engine);
                this.logger.trace("checkServerTrusted for {} succeeded", (Object)tm);
                if (this.trustStrategy != Strategy.ANY) continue;
                return;
            }
            catch (CertificateException e) {
                this.logger.trace("checkServerTrusted for {} failed", (Object)tm);
                if (this.trustStrategy == Strategy.ALL) {
                    throw e;
                }
                if (certEx != null) continue;
                certEx = e;
            }
        }
        if (certEx != null) {
            throw certEx;
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        CertificateException certEx = null;
        for (X509ExtendedTrustManager tm : this.trustManagers) {
            try {
                tm.checkServerTrusted(chain, authType);
                this.logger.trace("checkServerTrusted for {} succeeded", (Object)tm);
                if (this.trustStrategy != Strategy.ANY) continue;
                return;
            }
            catch (CertificateException e) {
                this.logger.trace("checkServerTrusted for {} failed", (Object)tm);
                if (this.trustStrategy == Strategy.ALL) {
                    throw e;
                }
                if (certEx != null) continue;
                certEx = e;
            }
        }
        if (certEx != null) {
            throw certEx;
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return (X509Certificate[])Stream.of(this.trustManagers).map(X509TrustManager::getAcceptedIssuers).toArray(X509Certificate[]::new);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "trustManagers=" + Arrays.toString(this.trustManagers) + ", " + "trustStrategy=" + (Object)((Object)this.trustStrategy) + "]";
    }

    public static enum Strategy {
        ALL,
        ANY;

    }
}

