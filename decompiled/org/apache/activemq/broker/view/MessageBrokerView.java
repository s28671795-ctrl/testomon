/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.view;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.activemq.broker.BrokerRegistry;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.view.BrokerDestinationView;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTempQueue;
import org.apache.activemq.command.ActiveMQTempTopic;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.util.LRUCache;

public class MessageBrokerView {
    private final BrokerService brokerService;
    private final Map<ActiveMQDestination, BrokerDestinationView> destinationViewMap = new LRUCache<ActiveMQDestination, BrokerDestinationView>();

    public MessageBrokerView(BrokerService brokerService) {
        this.brokerService = brokerService;
        if (brokerService == null) {
            throw new NullPointerException("BrokerService is null");
        }
        if (!brokerService.isStarted()) {
            throw new IllegalStateException("BrokerService " + brokerService.getBrokerName() + " is not started");
        }
    }

    public MessageBrokerView(String brokerName) {
        this.brokerService = BrokerRegistry.getInstance().lookup(brokerName);
        if (this.brokerService == null) {
            throw new NullPointerException("BrokerService is null");
        }
        if (!this.brokerService.isStarted()) {
            throw new IllegalStateException("BrokerService " + this.brokerService.getBrokerName() + " is not started");
        }
    }

    public String getBrokerName() {
        return this.brokerService.getBrokerName();
    }

    public String getBrokerId() {
        try {
            return this.brokerService.getBroker().getBrokerId().toString();
        }
        catch (Exception e) {
            return "";
        }
    }

    public int getMemoryPercentUsage() {
        return this.brokerService.getSystemUsage().getMemoryUsage().getPercentUsage();
    }

    public int getStorePercentUsage() {
        return this.brokerService.getSystemUsage().getStoreUsage().getPercentUsage();
    }

    public int getTempPercentUsage() {
        return this.brokerService.getSystemUsage().getTempUsage().getPercentUsage();
    }

    public int getJobSchedulerStorePercentUsage() {
        return this.brokerService.getSystemUsage().getJobSchedulerUsage().getPercentUsage();
    }

    public boolean isPersistent() {
        return this.brokerService.isPersistent();
    }

    public BrokerService getBrokerService() {
        return this.brokerService;
    }

    public Set<ActiveMQDestination> getDestinations() {
        Set<ActiveMQDestination> result;
        try {
            ActiveMQDestination[] destinations = this.brokerService.getBroker().getDestinations();
            result = new HashSet<ActiveMQDestination>();
            Collections.addAll(result, destinations);
        }
        catch (Exception e) {
            result = Collections.emptySet();
        }
        return result;
    }

    public Set<ActiveMQTopic> getTopics() {
        HashSet<ActiveMQTopic> result = new HashSet<ActiveMQTopic>();
        for (ActiveMQDestination destination : this.getDestinations()) {
            if (!destination.isTopic() || destination.isTemporary()) continue;
            result.add((ActiveMQTopic)destination);
        }
        return result;
    }

    public Set<ActiveMQQueue> getQueues() {
        HashSet<ActiveMQQueue> result = new HashSet<ActiveMQQueue>();
        for (ActiveMQDestination destination : this.getDestinations()) {
            if (!destination.isQueue() || destination.isTemporary()) continue;
            result.add((ActiveMQQueue)destination);
        }
        return result;
    }

    public Set<ActiveMQTempTopic> getTempTopics() {
        HashSet<ActiveMQTempTopic> result = new HashSet<ActiveMQTempTopic>();
        for (ActiveMQDestination destination : this.getDestinations()) {
            if (!destination.isTopic() || !destination.isTemporary()) continue;
            result.add((ActiveMQTempTopic)destination);
        }
        return result;
    }

    public Set<ActiveMQTempQueue> getTempQueues() {
        HashSet<ActiveMQTempQueue> result = new HashSet<ActiveMQTempQueue>();
        for (ActiveMQDestination destination : this.getDestinations()) {
            if (!destination.isQueue() || !destination.isTemporary()) continue;
            result.add((ActiveMQTempQueue)destination);
        }
        return result;
    }

    public BrokerDestinationView getDestinationView(String destinationName) throws Exception {
        return this.getDestinationView(destinationName, (byte)1);
    }

    public BrokerDestinationView getTopicDestinationView(String destinationName) throws Exception {
        return this.getDestinationView(destinationName, (byte)2);
    }

    public BrokerDestinationView getQueueDestinationView(String destinationName) throws Exception {
        return this.getDestinationView(destinationName, (byte)1);
    }

    public BrokerDestinationView getDestinationView(String destinationName, byte type) throws Exception {
        ActiveMQDestination activeMQDestination = ActiveMQDestination.createDestination(destinationName, type);
        return this.getDestinationView(activeMQDestination);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public BrokerDestinationView getDestinationView(ActiveMQDestination activeMQDestination) throws Exception {
        BrokerDestinationView view = null;
        Map<ActiveMQDestination, BrokerDestinationView> map = this.destinationViewMap;
        synchronized (map) {
            view = this.destinationViewMap.get(activeMQDestination);
            if (view == null) {
                Destination destination = this.brokerService.getDestination(activeMQDestination);
                view = new BrokerDestinationView(destination);
                this.destinationViewMap.put(activeMQDestination, view);
            }
        }
        return view;
    }
}

