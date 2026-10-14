/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.view;

import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.broker.BrokerRegistry;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.view.MessageBrokerView;

public class MessageBrokerViewRegistry {
    private static final MessageBrokerViewRegistry INSTANCE = new MessageBrokerViewRegistry();
    private final Object mutex = new Object();
    private final Map<String, MessageBrokerView> brokerViews = new HashMap<String, MessageBrokerView>();

    public static MessageBrokerViewRegistry getInstance() {
        return INSTANCE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public MessageBrokerView lookup(String brokerName) {
        MessageBrokerView result = null;
        Object object = this.mutex;
        synchronized (object) {
            BrokerService brokerService;
            result = this.brokerViews.get(brokerName);
            if (result == null && (brokerService = BrokerRegistry.getInstance().lookup(brokerName)) != null) {
                result = new MessageBrokerView(brokerService);
                this.brokerViews.put(brokerName, result);
            }
        }
        return result;
    }
}

