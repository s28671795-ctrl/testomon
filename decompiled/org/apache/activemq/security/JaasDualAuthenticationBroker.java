/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import java.security.cert.X509Certificate;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.Connector;
import org.apache.activemq.broker.EmptyBroker;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.security.AuthenticationBroker;
import org.apache.activemq.security.JaasAuthenticationBroker;
import org.apache.activemq.security.JaasCertificateAuthenticationBroker;
import org.apache.activemq.security.SecurityContext;

public class JaasDualAuthenticationBroker
extends BrokerFilter
implements AuthenticationBroker {
    private final JaasCertificateAuthenticationBroker sslBroker;
    private final JaasAuthenticationBroker nonSslBroker;

    public JaasDualAuthenticationBroker(Broker next, String jaasConfiguration, String jaasSslConfiguration) {
        super(next);
        this.nonSslBroker = new JaasAuthenticationBroker(new EmptyBroker(), jaasConfiguration);
        this.sslBroker = new JaasCertificateAuthenticationBroker(new EmptyBroker(), jaasSslConfiguration);
    }

    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
        if (context.getSecurityContext() == null) {
            if (this.isSSL(context, info)) {
                this.sslBroker.addConnection(context, info);
            } else {
                this.nonSslBroker.addConnection(context, info);
            }
            super.addConnection(context, info);
        }
    }

    @Override
    public void removeConnection(ConnectionContext context, ConnectionInfo info, Throwable error) throws Exception {
        super.removeConnection(context, info, error);
        if (this.isSSL(context, info)) {
            this.sslBroker.removeConnection(context, info, error);
        } else {
            this.nonSslBroker.removeConnection(context, info, error);
        }
    }

    private boolean isSSL(ConnectionContext context, ConnectionInfo info) throws Exception {
        boolean sslCapable = false;
        Connector connector = context.getConnector();
        if (connector instanceof TransportConnector) {
            TransportConnector transportConnector = (TransportConnector)connector;
            sslCapable = transportConnector.getServer().isSslServer();
        }
        if (!sslCapable && info.getTransportContext() instanceof X509Certificate[]) {
            sslCapable = true;
        }
        return sslCapable;
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        this.sslBroker.removeDestination(context, destination, timeout2);
        this.nonSslBroker.removeDestination(context, destination, timeout2);
        super.removeDestination(context, destination, timeout2);
    }

    @Override
    public SecurityContext authenticate(String username, String password, X509Certificate[] peerCertificates2) throws SecurityException {
        if (peerCertificates2 != null) {
            return this.sslBroker.authenticate(username, password, peerCertificates2);
        }
        return this.nonSslBroker.authenticate(username, password, peerCertificates2);
    }
}

