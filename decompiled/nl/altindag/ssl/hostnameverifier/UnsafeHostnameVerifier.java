/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.hostnameverifier;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public final class UnsafeHostnameVerifier
implements HostnameVerifier {
    private static final HostnameVerifier INSTANCE = new UnsafeHostnameVerifier();

    private UnsafeHostnameVerifier() {
    }

    @Override
    public boolean verify(String host2, SSLSession sslSession) {
        return true;
    }

    public static HostnameVerifier getInstance() {
        return INSTANCE;
    }
}

