/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.peer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.activemq.broker.BrokerFactoryHandler;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.vm.VMTransportFactory;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IdGenerator;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;

public class PeerTransportFactory
extends TransportFactory {
    public static final ConcurrentMap BROKERS = new ConcurrentHashMap();
    public static final ConcurrentMap CONNECTORS = new ConcurrentHashMap();
    public static final ConcurrentMap SERVERS = new ConcurrentHashMap();
    private static final IdGenerator ID_GENERATOR = new IdGenerator("peer-");

    @Override
    public Transport doConnect(URI location) throws Exception {
        VMTransportFactory vmTransportFactory = this.createTransportFactory(location);
        return vmTransportFactory.doConnect(location);
    }

    @Override
    public Transport doCompositeConnect(URI location) throws Exception {
        VMTransportFactory vmTransportFactory = this.createTransportFactory(location);
        return vmTransportFactory.doCompositeConnect(location);
    }

    private VMTransportFactory createTransportFactory(URI location) throws IOException {
        try {
            HashMap<String, String> brokerOptions;
            String group = location.getHost();
            String broker = URISupport.stripPrefix(location.getPath(), "/");
            if (group == null) {
                group = "default";
            }
            if (broker == null || broker.length() == 0) {
                broker = ID_GENERATOR.generateSanitizedId();
            }
            if (!(brokerOptions = new HashMap<String, String>(URISupport.parseParameters(location))).containsKey("persistent")) {
                brokerOptions.put("persistent", "false");
            }
            final URI finalLocation = new URI("vm://" + broker);
            final String finalBroker = broker;
            final String finalGroup = group;
            VMTransportFactory rc = new VMTransportFactory(){

                @Override
                public Transport doConnect(URI ignore) throws Exception {
                    return super.doConnect(finalLocation);
                }

                @Override
                public Transport doCompositeConnect(URI ignore) throws Exception {
                    return super.doCompositeConnect(finalLocation);
                }
            };
            rc.setBrokerFactoryHandler(new BrokerFactoryHandler(){

                @Override
                public BrokerService createBroker(URI brokerURI) throws Exception {
                    BrokerService service2 = new BrokerService();
                    IntrospectionSupport.setProperties(service2, brokerOptions);
                    service2.setBrokerName(finalBroker);
                    TransportConnector c = service2.addConnector("tcp://0.0.0.0:0");
                    c.setDiscoveryUri(new URI("multicast://default?group=" + finalGroup));
                    service2.addNetworkConnector("multicast://default?group=" + finalGroup);
                    return service2;
                }
            });
            return rc;
        }
        catch (URISyntaxException e) {
            throw IOExceptionSupport.create(e);
        }
    }

    @Override
    public TransportServer doBind(URI location) throws IOException {
        throw new IOException("This protocol does not support being bound.");
    }
}

