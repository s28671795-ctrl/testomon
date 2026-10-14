/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.io.IOException;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostnameVerifyingListener
implements HandshakeCompletedListener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HostnameVerifier hostnameVerifier;
    private boolean invoked;
    private boolean verified;
    private String hostname;

    public HostnameVerifyingListener(HostnameVerifier verifier) {
        this.hostnameVerifier = verifier;
    }

    public HostnameVerifyingListener(HostnameVerifier verifier, String name) {
        this.hostnameVerifier = verifier;
        this.hostname = name;
    }

    @Override
    public void handshakeCompleted(HandshakeCompletedEvent event) {
        this.invoked = true;
        if (this.hostname == null) {
            this.hostname = event.getSession().getPeerHost();
        }
        if (!this.hostnameVerifier.verify(this.hostname, event.getSession())) {
            try {
                event.getSocket().close();
            }
            catch (IOException e) {
                this.logger.warn("Error closing SSL socket", e);
            }
            event.getSession().invalidate();
        } else {
            this.verified = true;
        }
    }

    public void peerVerified() throws SSLPeerUnverifiedException {
        if (!this.invoked) {
            throw new IllegalStateException("Handshake has not completed");
        }
        if (!this.verified) {
            throw new SSLPeerUnverifiedException(String.format("Hostname '%s' does not match the hostname in the server's certificate", this.hostname));
        }
    }
}

