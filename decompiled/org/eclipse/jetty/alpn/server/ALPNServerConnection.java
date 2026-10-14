/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.alpn.server;

import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.NegotiatingServerConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ALPNServerConnection
extends NegotiatingServerConnection {
    private static final Logger LOG = LoggerFactory.getLogger(ALPNServerConnection.class);

    public ALPNServerConnection(Connector connector, EndPoint endPoint, SSLEngine engine, List<String> protocols, String defaultProtocol) {
        super(connector, endPoint, engine, protocols, defaultProtocol);
    }

    public void unsupported() {
        this.select(Collections.emptyList());
    }

    public void select(List<String> clientProtocols) {
        SSLEngine sslEngine = this.getSSLEngine();
        List<String> serverProtocols = this.getProtocols();
        SSLSession sslSession = sslEngine.getHandshakeSession();
        if (sslSession == null) {
            sslSession = sslEngine.getSession();
        }
        String tlsProtocol = sslSession.getProtocol();
        String tlsCipher = sslSession.getCipherSuite();
        String negotiated = null;
        for (String serverProtocol : serverProtocols) {
            if (!clientProtocols.contains(serverProtocol)) continue;
            ConnectionFactory factory2 = this.getConnector().getConnectionFactory(serverProtocol);
            if (factory2 instanceof NegotiatingServerConnection.CipherDiscriminator && !((NegotiatingServerConnection.CipherDiscriminator)((Object)factory2)).isAcceptable(serverProtocol, tlsProtocol, tlsCipher)) {
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("Protocol {} not acceptable to {} for {}/{} on {}", serverProtocol, factory2, tlsProtocol, tlsCipher, this.getEndPoint());
                continue;
            }
            negotiated = serverProtocol;
            break;
        }
        if (negotiated == null) {
            if (clientProtocols.isEmpty()) {
                negotiated = this.getDefaultProtocol();
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Could not negotiate protocol from client{} and server{} on {}", clientProtocols, serverProtocols, this.getEndPoint());
                }
                throw new IllegalStateException();
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Protocol selected {} from client{} and server{} on {}", negotiated, clientProtocols, serverProtocols, this.getEndPoint());
        }
        this.setProtocol(negotiated);
    }
}

