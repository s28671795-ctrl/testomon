/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager;

import java.security.cert.CertificateException;
import javax.net.ssl.X509ExtendedTrustManager;

@FunctionalInterface
interface TrustManagerConsumer {
    public void checkTrusted(X509ExtendedTrustManager var1) throws CertificateException;
}

