/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https;

import java.security.cert.X509Certificate;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.MessageTrustDecider;
import org.apache.cxf.transport.http.URLConnectionInfo;
import org.apache.cxf.transport.http.UntrustedURLConnectionIOException;
import org.apache.cxf.transport.https.CertConstraints;
import org.apache.cxf.transport.https.HttpsURLConnectionInfo;

final class HttpsMessageTrustDecider
extends MessageTrustDecider {
    private final CertConstraints certConstraints;
    private final MessageTrustDecider orig;

    HttpsMessageTrustDecider(CertConstraints certConstraints, MessageTrustDecider orig) {
        this.certConstraints = certConstraints;
        this.orig = orig;
    }

    @Override
    public void establishTrust(String conduitName, URLConnectionInfo connectionInfo, Message message) throws UntrustedURLConnectionIOException {
        HttpsURLConnectionInfo info;
        if (this.orig != null) {
            this.orig.establishTrust(conduitName, connectionInfo, message);
        }
        if ((info = (HttpsURLConnectionInfo)connectionInfo).getServerCertificates() == null || info.getServerCertificates().length == 0) {
            throw new UntrustedURLConnectionIOException("No server certificates were found");
        }
        X509Certificate[] certs = (X509Certificate[])info.getServerCertificates();
        if (!this.certConstraints.matches(certs[0])) {
            throw new UntrustedURLConnectionIOException("The server certificate(s) do not match the defined cert constraints");
        }
    }
}

