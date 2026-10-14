/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.security.transport.TLSSessionInfo;
import org.apache.cxf.transport.http.MessageTrustDecider;
import org.apache.cxf.transport.http.UntrustedURLConnectionIOException;
import org.apache.cxf.transport.https.CertConstraints;
import org.apache.cxf.transport.https.HttpsMessageTrustDecider;

public final class CertConstraintsInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final CertConstraintsInterceptor INSTANCE = new CertConstraintsInterceptor();
    static final Logger LOG = LogUtils.getL7dLogger(CertConstraintsInterceptor.class);

    private CertConstraintsInterceptor() {
        super("pre-stream");
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        CertConstraints certConstraints = (CertConstraints)message.getContextualProperty(CertConstraints.class.getName());
        if (certConstraints == null) {
            return;
        }
        if (this.isRequestor(message)) {
            try {
                String scheme2 = (String)message.get("http.scheme");
                if (!"https".equals(scheme2)) {
                    throw new UntrustedURLConnectionIOException("TLS is not in use");
                }
                MessageTrustDecider orig = message.get(MessageTrustDecider.class);
                HttpsMessageTrustDecider trust = new HttpsMessageTrustDecider(certConstraints, orig);
                message.put(MessageTrustDecider.class, trust);
            }
            catch (UntrustedURLConnectionIOException ex) {
                throw new Fault(ex);
            }
        }
        try {
            TLSSessionInfo tlsInfo = message.get(TLSSessionInfo.class);
            Certificate[] certs = tlsInfo.getPeerCertificates();
            if (certs == null || certs.length == 0) {
                throw new UntrustedURLConnectionIOException("No client certificates were found");
            }
            X509Certificate[] x509Certs = (X509Certificate[])certs;
            if (!certConstraints.matches(x509Certs[0])) {
                throw new UntrustedURLConnectionIOException("The client certificate does not match the defined cert constraints");
            }
        }
        catch (UntrustedURLConnectionIOException ex) {
            throw new Fault(ex);
        }
    }
}

