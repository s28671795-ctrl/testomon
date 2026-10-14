/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.cxf.common.logging.LogUtils;

public final class InsecureTrustManager {
    private static final Logger LOG = LogUtils.getL7dLogger(InsecureTrustManager.class);

    private InsecureTrustManager() {
    }

    public static TrustManager[] getNoOpX509TrustManagers() {
        LOG.warning("This class essentially disables TLS trust verification and is insecure!");
        return new TrustManager[]{new NoOpX509TrustManager()};
    }

    private static final class NoOpX509TrustManager
    implements X509TrustManager {
        private NoOpX509TrustManager() {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}

