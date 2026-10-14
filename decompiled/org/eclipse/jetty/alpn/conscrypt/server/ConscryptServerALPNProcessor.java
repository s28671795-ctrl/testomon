/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.alpn.conscrypt.server;

import java.security.Security;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;
import org.conscrypt.ApplicationProtocolSelector;
import org.conscrypt.Conscrypt;
import org.conscrypt.OpenSSLProvider;
import org.eclipse.jetty.alpn.server.ALPNServerConnection;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.ssl.ALPNProcessor;
import org.eclipse.jetty.io.ssl.SslConnection;
import org.eclipse.jetty.io.ssl.SslHandshakeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConscryptServerALPNProcessor
implements ALPNProcessor.Server {
    private static final Logger LOG = LoggerFactory.getLogger(ConscryptServerALPNProcessor.class);

    @Override
    public void init() {
        if (Security.getProvider("Conscrypt") == null) {
            Security.addProvider(new OpenSSLProvider());
            if (LOG.isDebugEnabled()) {
                LOG.debug("Added Conscrypt provider");
            }
        }
    }

    @Override
    public boolean appliesTo(SSLEngine sslEngine) {
        return sslEngine.getClass().getName().startsWith("org.conscrypt.");
    }

    @Override
    public void configure(SSLEngine sslEngine, Connection connection) {
        try {
            Conscrypt.setApplicationProtocolSelector(sslEngine, (ApplicationProtocolSelector)new ALPNCallback((ALPNServerConnection)connection));
        }
        catch (RuntimeException x) {
            throw x;
        }
        catch (Exception x) {
            throw new RuntimeException(x);
        }
    }

    private final class ALPNCallback
    extends ApplicationProtocolSelector
    implements SslHandshakeListener {
        private final ALPNServerConnection alpnConnection;

        private ALPNCallback(ALPNServerConnection connection) {
            this.alpnConnection = connection;
            ((SslConnection.DecryptedEndPoint)this.alpnConnection.getEndPoint()).getSslConnection().addHandshakeListener(this);
        }

        @Override
        public String selectApplicationProtocol(SSLEngine engine, List<String> protocols) {
            this.alpnConnection.select(protocols);
            String protocol = this.alpnConnection.getProtocol();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Selected {} among {} for {}", protocol, protocols, this.alpnConnection);
            }
            return protocol;
        }

        @Override
        public String selectApplicationProtocol(SSLSocket socket, List<String> protocols) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void handshakeSucceeded(SslHandshakeListener.Event event) {
            String protocol = this.alpnConnection.getProtocol();
            if (LOG.isDebugEnabled()) {
                LOG.debug("TLS handshake succeeded, protocol={} for {}", (Object)protocol, (Object)this.alpnConnection);
            }
            if (protocol == null) {
                this.alpnConnection.unsupported();
            }
        }

        @Override
        public void handshakeFailed(SslHandshakeListener.Event event, Throwable failure) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("TLS handshake failed {}", (Object)this.alpnConnection, (Object)failure);
            }
        }
    }
}

