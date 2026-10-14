/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.vm;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerFactoryHandler;
import org.apache.activemq.broker.BrokerRegistry;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.transport.MarshallingTransportFilter;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.vm.VMTransport;
import org.apache.activemq.transport.vm.VMTransportServer;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.ServiceSupport;
import org.apache.activemq.util.URISupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class VMTransportFactory
extends TransportFactory {
    public static final ConcurrentMap<String, BrokerService> BROKERS = new ConcurrentHashMap<String, BrokerService>();
    public static final ConcurrentMap<String, TransportConnector> CONNECTORS = new ConcurrentHashMap<String, TransportConnector>();
    public static final ConcurrentMap<String, VMTransportServer> SERVERS = new ConcurrentHashMap<String, VMTransportServer>();
    private static final Logger LOG = LoggerFactory.getLogger(VMTransportFactory.class);
    BrokerFactoryHandler brokerFactoryHandler;

    @Override
    public Transport doConnect(URI location) throws Exception {
        return VMTransportServer.configure(this.doCompositeConnect(location));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Transport doCompositeConnect(URI location) throws Exception {
        Map<String, String> options;
        String host2;
        URI brokerURI;
        boolean create = true;
        int waitForStart = -1;
        URISupport.CompositeData data = URISupport.parseComposite(location);
        if (data.getComponents().length == 1 && "broker".equals(data.getComponents()[0].getScheme())) {
            brokerURI = data.getComponents()[0];
            URISupport.CompositeData brokerData = URISupport.parseComposite(brokerURI);
            host2 = brokerData.getParameters().get("brokerName");
            if (host2 == null) {
                host2 = "localhost";
            }
            if (brokerData.getPath() != null) {
                host2 = brokerData.getPath();
            }
            options = data.getParameters();
            location = new URI("vm://" + host2);
        } else {
            try {
                String waitForStartString;
                host2 = VMTransportFactory.extractHost(location);
                options = URISupport.parseParameters(location);
                String config = options.remove("brokerConfig");
                if (config != null) {
                    brokerURI = new URI(config);
                } else {
                    Map<String, Object> brokerOptions = IntrospectionSupport.extractProperties(options, "broker.");
                    brokerURI = new URI("broker://()/" + host2 + "?" + URISupport.createQueryString(brokerOptions));
                }
                if ("false".equals(options.remove("create"))) {
                    create = false;
                }
                if ((waitForStartString = options.remove("waitForStart")) != null) {
                    waitForStart = Integer.parseInt(waitForStartString);
                }
            }
            catch (URISyntaxException e1) {
                throw IOExceptionSupport.create(e1);
            }
            location = new URI("vm://" + host2);
        }
        if (host2 == null) {
            host2 = "localhost";
        }
        VMTransportServer server = (VMTransportServer)SERVERS.get(host2);
        if (!this.validateBroker(host2) || server == null) {
            BrokerService broker = null;
            Object object = BrokerRegistry.getInstance().getRegistryMutext();
            synchronized (object) {
                broker = this.lookupBroker(BrokerRegistry.getInstance(), host2, waitForStart);
                if (broker == null) {
                    if (!create) {
                        throw new IOException("Broker named '" + host2 + "' does not exist.");
                    }
                    try {
                        broker = this.brokerFactoryHandler != null ? this.brokerFactoryHandler.createBroker(brokerURI) : BrokerFactory.createBroker(brokerURI);
                        broker.start();
                        MDC.put("activemq.broker", broker.getBrokerName());
                    }
                    catch (URISyntaxException e) {
                        throw IOExceptionSupport.create(e);
                    }
                    BROKERS.put(host2, broker);
                    BrokerRegistry.getInstance().getRegistryMutext().notifyAll();
                }
                if ((server = (VMTransportServer)SERVERS.get(host2)) == null) {
                    server = (VMTransportServer)this.bind(location, true);
                    TransportConnector connector = new TransportConnector(server);
                    connector.setBrokerService(broker);
                    connector.setUri(location);
                    connector.setTaskRunnerFactory(broker.getTaskRunnerFactory());
                    connector.start();
                    CONNECTORS.put(host2, connector);
                }
            }
        }
        VMTransport vmtransport = server.connect();
        IntrospectionSupport.setProperties(vmtransport.peer, new HashMap<String, String>(options));
        IntrospectionSupport.setProperties(vmtransport, options);
        Transport transport = vmtransport;
        if (vmtransport.isMarshal()) {
            HashMap<String, String> optionsCopy = new HashMap<String, String>(options);
            transport = new MarshallingTransportFilter(transport, this.createWireFormat(options), this.createWireFormat(optionsCopy));
        }
        if (!options.isEmpty()) {
            throw new IllegalArgumentException("Invalid connect parameters: " + options);
        }
        return transport;
    }

    private static String extractHost(URI location) {
        String host2 = location.getHost();
        if (!(host2 != null && host2.length() != 0 || (host2 = location.getAuthority()) != null && host2.length() != 0)) {
            host2 = "localhost";
        }
        return host2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private BrokerService lookupBroker(BrokerRegistry registry, String brokerName, int waitForStart) {
        BrokerService broker = null;
        Object object = registry.getRegistryMutext();
        synchronized (object) {
            broker = registry.lookup(brokerName);
            if (broker == null || waitForStart > 0) {
                long expiry = System.currentTimeMillis() + (long)waitForStart;
                while (!(broker != null && broker.isStarted() || System.currentTimeMillis() - expiry >= 0L)) {
                    long timeout2 = Math.max(0L, expiry - System.currentTimeMillis());
                    if (broker == null) {
                        try {
                            LOG.debug("waiting for broker named: " + brokerName + " to enter registry");
                            registry.getRegistryMutext().wait(timeout2);
                            broker = registry.lookup(brokerName);
                        }
                        catch (InterruptedException interruptedException) {
                            // empty catch block
                        }
                    }
                    if (broker == null || broker.isStarted()) continue;
                    LOG.debug("waiting for broker named: " + brokerName + " to start");
                    timeout2 = Math.max(0L, expiry - System.currentTimeMillis());
                    if (broker.waitUntilStarted(timeout2)) continue;
                    broker = null;
                    break;
                }
            }
        }
        return broker;
    }

    @Override
    public TransportServer doBind(URI location) throws IOException {
        return this.bind(location, false);
    }

    private TransportServer bind(URI location, boolean dispose) throws IOException {
        String host2 = VMTransportFactory.extractHost(location);
        LOG.debug("binding to broker: " + host2);
        VMTransportServer server = new VMTransportServer(location, dispose);
        Object currentBoundValue = SERVERS.get(host2);
        if (currentBoundValue != null) {
            throw new IOException("VMTransportServer already bound at: " + location);
        }
        SERVERS.put(host2, server);
        return server;
    }

    public static void stopped(VMTransportServer server) {
        String host2 = VMTransportFactory.extractHost(server.getBindURI());
        VMTransportFactory.stopped(host2);
    }

    public static void stopped(String host2) {
        SERVERS.remove(host2);
        TransportConnector connector = (TransportConnector)CONNECTORS.remove(host2);
        if (connector != null) {
            LOG.debug("Shutting down VM connectors for broker: " + host2);
            ServiceSupport.dispose(connector);
            BrokerService broker = (BrokerService)BROKERS.remove(host2);
            if (broker != null) {
                ServiceSupport.dispose(broker);
            }
            MDC.remove("activemq.broker");
        }
    }

    public BrokerFactoryHandler getBrokerFactoryHandler() {
        return this.brokerFactoryHandler;
    }

    public void setBrokerFactoryHandler(BrokerFactoryHandler brokerFactoryHandler) {
        this.brokerFactoryHandler = brokerFactoryHandler;
    }

    private boolean validateBroker(String host2) {
        boolean result = true;
        if (BROKERS.containsKey(host2) || SERVERS.containsKey(host2) || CONNECTORS.containsKey(host2)) {
            TransportConnector connector = (TransportConnector)CONNECTORS.get(host2);
            if (BrokerRegistry.getInstance().lookup(host2) == null || connector != null && connector.getBroker().isStopped()) {
                result = false;
                BROKERS.remove(host2);
                SERVERS.remove(host2);
                if (connector != null) {
                    CONNECTORS.remove(host2);
                    if (connector != null) {
                        ServiceSupport.dispose(connector);
                    }
                }
            }
        }
        return result;
    }
}

