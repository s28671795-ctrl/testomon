/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import java.net.URI;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.BrokerServiceAware;
import org.apache.activemq.broker.SslContext;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.TransportServer;

public class TransportFactorySupport {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TransportServer bind(BrokerService brokerService, URI location) throws IOException {
        TransportFactory tf = TransportFactory.findTransportFactory(location);
        if (brokerService != null && tf instanceof BrokerServiceAware) {
            ((BrokerServiceAware)((Object)tf)).setBrokerService(brokerService);
        }
        try {
            if (brokerService != null) {
                SslContext.setCurrentSslContext(brokerService.getSslContext());
            }
            TransportServer transportServer = tf.doBind(location);
            return transportServer;
        }
        finally {
            SslContext.setCurrentSslContext(null);
        }
    }
}

