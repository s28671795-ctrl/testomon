/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.model;

import javax.net.ssl.SSLSession;

public final class HostnameVerifierParameters {
    private final String hostname;
    private final SSLSession session;

    public HostnameVerifierParameters(String hostname, SSLSession session) {
        this.hostname = hostname;
        this.session = session;
    }

    public String getHostname() {
        return this.hostname;
    }

    public SSLSession getSession() {
        return this.session;
    }
}

