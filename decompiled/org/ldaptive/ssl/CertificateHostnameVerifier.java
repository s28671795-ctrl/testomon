/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.security.cert.X509Certificate;

public interface CertificateHostnameVerifier {
    public boolean verify(String var1, X509Certificate var2);
}

