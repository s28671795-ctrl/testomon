/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import org.ldaptive.ssl.CertificateHostnameVerifier;

public class AllowAnyHostnameVerifier
implements HostnameVerifier,
CertificateHostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }

    @Override
    public boolean verify(String hostname, X509Certificate cert) {
        return true;
    }
}

