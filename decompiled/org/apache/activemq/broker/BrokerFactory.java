/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.io.IOException;
import java.net.URI;
import org.apache.activemq.broker.BrokerFactoryHandler;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.util.FactoryFinder;
import org.apache.activemq.util.IOExceptionSupport;

public final class BrokerFactory {
    private static final FactoryFinder BROKER_FACTORY_HANDLER_FINDER = new FactoryFinder("META-INF/services/org/apache/activemq/broker/");
    private static final ThreadLocal<Boolean> START_DEFAULT = new ThreadLocal();

    private BrokerFactory() {
    }

    public static BrokerFactoryHandler createBrokerFactoryHandler(String type) throws IOException {
        try {
            return (BrokerFactoryHandler)BROKER_FACTORY_HANDLER_FINDER.newInstance(type);
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create("Could not load " + type + " factory:" + e, e);
        }
    }

    public static BrokerService createBroker(URI brokerURI) throws Exception {
        return BrokerFactory.createBroker(brokerURI, false);
    }

    public static BrokerService createBroker(URI brokerURI, boolean startBroker) throws Exception {
        if (brokerURI.getScheme() == null) {
            throw new IllegalArgumentException("Invalid broker URI, no scheme specified: " + brokerURI);
        }
        BrokerFactoryHandler handler = BrokerFactory.createBrokerFactoryHandler(brokerURI.getScheme());
        BrokerService broker = handler.createBroker(brokerURI);
        if (startBroker) {
            broker.start();
        }
        return broker;
    }

    public static BrokerService createBroker(String brokerURI) throws Exception {
        return BrokerFactory.createBroker(new URI(brokerURI));
    }

    public static BrokerService createBroker(String brokerURI, boolean startBroker) throws Exception {
        return BrokerFactory.createBroker(new URI(brokerURI), startBroker);
    }

    public static void setStartDefault(boolean startDefault) {
        START_DEFAULT.set(startDefault);
    }

    public static void resetStartDefault() {
        START_DEFAULT.remove();
    }

    public static boolean getStartDefault() {
        Boolean value = START_DEFAULT.get();
        if (value == null) {
            return true;
        }
        return value;
    }
}

