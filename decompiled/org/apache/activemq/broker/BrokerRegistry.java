/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrokerRegistry {
    private static final Logger LOG = LoggerFactory.getLogger(BrokerRegistry.class);
    private static final BrokerRegistry INSTANCE = new BrokerRegistry();
    private final Object mutex = new Object();
    private final Map<String, BrokerService> brokers = new HashMap<String, BrokerService>();

    public static BrokerRegistry getInstance() {
        return INSTANCE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public BrokerService lookup(String brokerName) {
        BrokerService result = null;
        Object object = this.mutex;
        synchronized (object) {
            result = this.brokers.get(brokerName);
            if (result == null && brokerName != null && brokerName.equals("localhost") && (result = this.findFirst()) != null) {
                LOG.warn("Broker localhost not started so using {} instead", (Object)result.getBrokerName());
            }
            if (result == null && (brokerName == null || brokerName.isEmpty() || brokerName.equals("null"))) {
                result = this.findFirst();
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public BrokerService findFirst() {
        Object object = this.mutex;
        synchronized (object) {
            Iterator<BrokerService> iter = this.brokers.values().iterator();
            if (iter.hasNext()) {
                return iter.next();
            }
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void bind(String brokerName, BrokerService broker) {
        Object object = this.mutex;
        synchronized (object) {
            this.brokers.put(brokerName, broker);
            this.mutex.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void unbind(String brokerName) {
        Object object = this.mutex;
        synchronized (object) {
            this.brokers.remove(brokerName);
        }
    }

    public Object getRegistryMutext() {
        return this.mutex;
    }

    public Map<String, BrokerService> getBrokers() {
        return Collections.unmodifiableMap(this.brokers);
    }
}

