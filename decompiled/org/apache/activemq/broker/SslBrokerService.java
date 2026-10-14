/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.SslContext;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.transport.TransportFactorySupport;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.tcp.SslTransportFactory;

public class SslBrokerService
extends BrokerService {
    public TransportConnector addSslConnector(String bindAddress, KeyManager[] km4, TrustManager[] tm, SecureRandom random) throws Exception {
        return this.addSslConnector(new URI(bindAddress), km4, tm, random);
    }

    public TransportConnector addSslConnector(URI bindAddress, KeyManager[] km4, TrustManager[] tm, SecureRandom random) throws Exception {
        return this.addConnector(this.createSslTransportServer(bindAddress, km4, tm, random));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected TransportServer createSslTransportServer(URI brokerURI, KeyManager[] km4, TrustManager[] tm, SecureRandom random) throws IOException, KeyManagementException {
        if (brokerURI.getScheme().equals("ssl")) {
            SslTransportFactory transportFactory = new SslTransportFactory();
            SslContext ctx = new SslContext(km4, tm, random);
            SslContext.setCurrentSslContext(ctx);
            try {
                TransportServer transportServer = transportFactory.doBind(brokerURI);
                return transportServer;
            }
            finally {
                SslContext.setCurrentSslContext(null);
            }
        }
        return TransportFactorySupport.bind(this, brokerURI);
    }
}

