/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import org.ldaptive.ssl.CertificateHostnameVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostnameVerifierAdapter
implements HostnameVerifier {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CertificateHostnameVerifier hostnameVerifier;

    public HostnameVerifierAdapter(CertificateHostnameVerifier verifier) {
        this.hostnameVerifier = verifier;
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
        boolean b = false;
        if (session != null) {
            try {
                String name = null;
                if (hostname != null) {
                    name = hostname.startsWith("[") && hostname.endsWith("]") ? hostname.substring(1, hostname.length() - 1).trim() : hostname.trim();
                }
                b = this.hostnameVerifier.verify(name, (X509Certificate)session.getPeerCertificates()[0]);
            }
            catch (SSLPeerUnverifiedException e) {
                this.logger.warn("Could not get certificate from SSL session {} for hostname {}", session, hostname, e);
            }
        }
        return b;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "hostnameVerifier=" + this.hostnameVerifier + "]";
    }
}

