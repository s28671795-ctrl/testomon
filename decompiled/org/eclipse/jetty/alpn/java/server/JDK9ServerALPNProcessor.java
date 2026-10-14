/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.alpn.java.server;

import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLEngine;
import org.eclipse.jetty.alpn.server.ALPNServerConnection;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.ssl.ALPNProcessor;
import org.eclipse.jetty.io.ssl.SslConnection;
import org.eclipse.jetty.io.ssl.SslHandshakeListener;
import org.eclipse.jetty.util.JavaVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDK9ServerALPNProcessor
implements ALPNProcessor.Server,
SslHandshakeListener {
    private static final Logger LOG = LoggerFactory.getLogger(JDK9ServerALPNProcessor.class);

    @Override
    public void init() {
        if (JavaVersion.VERSION.getPlatform() < 9) {
            throw new IllegalStateException(String.valueOf(this) + " not applicable for java " + String.valueOf(JavaVersion.VERSION));
        }
    }

    @Override
    public boolean appliesTo(SSLEngine sslEngine) {
        Module module = sslEngine.getClass().getModule();
        return module != null && "java.base".equals(module.getName());
    }

    @Override
    public void configure(SSLEngine sslEngine, Connection connection) {
        sslEngine.setHandshakeApplicationProtocolSelector(new ALPNCallback((ALPNServerConnection)connection));
    }

    private static final class ALPNCallback
    implements BiFunction<SSLEngine, List<String>, String>,
    SslHandshakeListener {
        private final ALPNServerConnection alpnConnection;

        private ALPNCallback(ALPNServerConnection connection) {
            this.alpnConnection = connection;
            ((SslConnection.DecryptedEndPoint)this.alpnConnection.getEndPoint()).getSslConnection().addHandshakeListener(this);
        }

        @Override
        public String apply(SSLEngine engine, List<String> protocols) {
            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("apply {} {}", (Object)this.alpnConnection, (Object)protocols);
                }
                this.alpnConnection.select(protocols);
                return this.alpnConnection.getProtocol();
            }
            catch (Throwable x) {
                return null;
            }
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

