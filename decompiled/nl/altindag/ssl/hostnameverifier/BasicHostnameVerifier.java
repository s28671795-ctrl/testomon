/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.hostnameverifier;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public final class BasicHostnameVerifier
implements HostnameVerifier {
    private static final HostnameVerifier INSTANCE = new BasicHostnameVerifier();

    private BasicHostnameVerifier() {
    }

    @Override
    public boolean verify(String host2, SSLSession sslSession) {
        return host2.equalsIgnoreCase(sslSession.getPeerHost());
    }

    public static HostnameVerifier getInstance() {
        return INSTANCE;
    }
}

