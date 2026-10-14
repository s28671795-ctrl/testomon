/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.Set;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFactory;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.TempQueue;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTempDestination;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.SubscriptionInfo;
import org.apache.activemq.store.MessageStore;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.TopicMessageStore;
import org.apache.activemq.thread.TaskRunnerFactory;

public class DestinationFactoryImpl
extends DestinationFactory {
    protected final TaskRunnerFactory taskRunnerFactory;
    protected final PersistenceAdapter persistenceAdapter;
    protected RegionBroker broker;
    private final BrokerService brokerService;

    public DestinationFactoryImpl(BrokerService brokerService, TaskRunnerFactory taskRunnerFactory, PersistenceAdapter persistenceAdapter) {
        this.brokerService = brokerService;
        this.taskRunnerFactory = taskRunnerFactory;
        if (persistenceAdapter == null) {
            throw new IllegalArgumentException("null persistenceAdapter");
        }
        this.persistenceAdapter = persistenceAdapter;
    }

    @Override
    public void setRegionBroker(RegionBroker broker) {
        if (broker == null) {
            throw new IllegalArgumentException("null broker");
        }
        this.broker = broker;
    }

    @Override
    public Set<ActiveMQDestination> getDestinations() {
        return this.persistenceAdapter.getDestinations();
    }

    @Override
    public Destination createDestination(ConnectionContext context, ActiveMQDestination destination, DestinationStatistics destinationStatistics) throws Exception {
        if (destination.isQueue()) {
            if (destination.isTemporary()) {
                ActiveMQTempDestination tempDest = (ActiveMQTempDestination)destination;
                TempQueue queue = new TempQueue(this.brokerService, destination, null, destinationStatistics, this.taskRunnerFactory);
                this.configureQueue(queue, destination);
                ((Queue)queue).initialize();
                return queue;
            }
            MessageStore store = this.persistenceAdapter.createQueueMessageStore((ActiveMQQueue)destination);
            Queue queue = new Queue(this.brokerService, destination, store, destinationStatistics, this.taskRunnerFactory);
            this.configureQueue(queue, destination);
            queue.initialize();
            return queue;
        }
        if (destination.isTemporary()) {
            Topic topic = new Topic(this.brokerService, destination, null, destinationStatistics, this.taskRunnerFactory);
            this.configureTopic(topic, destination);
            topic.initialize();
            return topic;
        }
        TopicMessageStore store = null;
        if (!AdvisorySupport.isAdvisoryTopic(destination)) {
            store = this.persistenceAdapter.createTopicMessageStore((ActiveMQTopic)destination);
        }
        Topic topic = new Topic(this.brokerService, destination, store, destinationStatistics, this.taskRunnerFactory);
        this.configureTopic(topic, destination);
        topic.initialize();
        return topic;
    }

    @Override
    public void removeDestination(Destination dest) {
        ActiveMQDestination destination = dest.getActiveMQDestination();
        if (!destination.isTemporary()) {
            if (destination.isQueue()) {
                this.persistenceAdapter.removeQueueMessageStore((ActiveMQQueue)destination);
            } else if (!AdvisorySupport.isAdvisoryTopic(destination)) {
                this.persistenceAdapter.removeTopicMessageStore((ActiveMQTopic)destination);
            }
        }
    }

    protected void configureQueue(Queue queue, ActiveMQDestination destination) {
        PolicyEntry entry;
        if (this.broker == null) {
            throw new IllegalStateException("broker property is not set");
        }
        if (this.broker.getDestinationPolicy() != null && (entry = this.broker.getDestinationPolicy().getEntryFor(destination)) != null) {
            entry.configure((Broker)this.broker, queue);
        }
    }

    protected void configureTopic(Topic topic, ActiveMQDestination destination) {
        PolicyEntry entry;
        if (this.broker == null) {
            throw new IllegalStateException("broker property is not set");
        }
        if (this.broker.getDestinationPolicy() != null && (entry = this.broker.getDestinationPolicy().getEntryFor(destination)) != null) {
            entry.configure((Broker)this.broker, topic);
        }
    }

    @Override
    public long getLastMessageBrokerSequenceId() throws IOException {
        return this.persistenceAdapter.getLastMessageBrokerSequenceId();
    }

    public PersistenceAdapter getPersistenceAdapter() {
        return this.persistenceAdapter;
    }

    @Override
    public SubscriptionInfo[] getAllDurableSubscriptions(ActiveMQTopic topic) throws IOException {
        return this.persistenceAdapter.createTopicMessageStore(topic).getAllSubscriptions();
    }
}

