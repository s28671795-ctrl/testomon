/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import org.apache.cxf.common.logging.LogUtils;

class AllowAllHostnameVerifier
implements HostnameVerifier {
    private static final Logger LOG = LogUtils.getL7dLogger(AllowAllHostnameVerifier.class);

    AllowAllHostnameVerifier() {
    }

    @Override
    public boolean verify(String host2, SSLSession session) {
        try {
            Certificate[] certs = session.getPeerCertificates();
            return certs != null && certs[0] instanceof X509Certificate;
        }
        catch (SSLException e) {
            if (LOG.isLoggable(Level.FINE)) {
                LOG.log(Level.FINE, e.getMessage(), e);
            }
            throw new RuntimeException("HostnameVerifier, socket reset for TTL");
        }
    }

    public boolean verify(String host2, String certHostname) {
        return certHostname != null && !certHostname.isEmpty();
    }
}

