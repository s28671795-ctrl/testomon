/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager;

import java.security.cert.CertificateException;

@FunctionalInterface
interface TrustManagerRunnable {
    public void run() throws CertificateException;
}

