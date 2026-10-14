/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.model;

import java.net.Socket;
import java.security.cert.X509Certificate;
import java.util.Optional;
import javax.net.ssl.SSLEngine;

public class TrustManagerParameters {
    private final X509Certificate[] chain;
    private final String authType;
    private final Socket socket;
    private final SSLEngine sslEngine;

    public TrustManagerParameters(X509Certificate[] chain, String authType, Socket socket, SSLEngine sslEngine) {
        this.chain = chain;
        this.authType = authType;
        this.socket = socket;
        this.sslEngine = sslEngine;
    }

    public X509Certificate[] getChain() {
        return this.chain;
    }

    public String getAuthType() {
        return this.authType;
    }

    public Optional<Socket> getSocket() {
        return Optional.ofNullable(this.socket);
    }

    public Optional<SSLEngine> getSslEngine() {
        return Optional.ofNullable(this.sslEngine);
    }
}

