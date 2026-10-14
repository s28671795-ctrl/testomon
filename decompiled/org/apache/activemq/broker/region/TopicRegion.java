/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.AbstractRegion;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFactory;
import org.apache.activemq.broker.region.DestinationFilter;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.TopicSubscription;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.command.SessionId;
import org.apache.activemq.command.SubscriptionInfo;
import org.apache.activemq.store.NoLocalSubscriptionAware;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.TopicMessageStore;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.util.LongSequenceGenerator;
import org.apache.activemq.util.SubscriptionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicRegion
extends AbstractRegion {
    private static final Logger LOG = LoggerFactory.getLogger(TopicRegion.class);
    protected final ConcurrentMap<SubscriptionKey, DurableTopicSubscription> durableSubscriptions = new ConcurrentHashMap<SubscriptionKey, DurableTopicSubscription>();
    private final LongSequenceGenerator recoveredDurableSubIdGenerator = new LongSequenceGenerator();
    private final SessionId recoveredDurableSubSessionId = new SessionId(new ConnectionId("OFFLINE"), this.recoveredDurableSubIdGenerator.getNextSequenceId());
    private boolean keepDurableSubsActive;
    private Timer cleanupTimer;
    private TimerTask cleanupTask;

    public TopicRegion(RegionBroker broker, DestinationStatistics destinationStatistics, SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        super(broker, destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
        if (broker.getBrokerService().getOfflineDurableSubscriberTaskSchedule() != -1L && broker.getBrokerService().getOfflineDurableSubscriberTimeout() != -1L) {
            this.cleanupTimer = new Timer("ActiveMQ Durable Subscriber Cleanup Timer", true);
            this.cleanupTask = new TimerTask(){

                @Override
                public void run() {
                    TopicRegion.this.doCleanup();
                }
            };
            this.cleanupTimer.schedule(this.cleanupTask, broker.getBrokerService().getOfflineDurableSubscriberTaskSchedule(), broker.getBrokerService().getOfflineDurableSubscriberTaskSchedule());
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (this.cleanupTimer != null) {
            this.cleanupTimer.cancel();
        }
    }

    public void doCleanup() {
        long now = System.currentTimeMillis();
        for (Map.Entry entry : this.durableSubscriptions.entrySet()) {
            long offline;
            DurableTopicSubscription sub = (DurableTopicSubscription)entry.getValue();
            if (sub.isActive() || (offline = sub.getOfflineTimestamp()) == -1L || now - offline < this.broker.getBrokerService().getOfflineDurableSubscriberTimeout()) continue;
            LOG.info("Destroying durable subscriber due to inactivity: {}", (Object)sub);
            try {
                RemoveSubscriptionInfo info = new RemoveSubscriptionInfo();
                info.setClientId(((SubscriptionKey)entry.getKey()).getClientId());
                info.setSubscriptionName(((SubscriptionKey)entry.getKey()).getSubscriptionName());
                ConnectionContext context = new ConnectionContext();
                context.setBroker(this.broker);
                context.setClientId(((SubscriptionKey)entry.getKey()).getClientId());
                this.removeSubscription(context, info);
            }
            catch (Exception e) {
                LOG.error("Failed to remove inactive durable subscriber", e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        if (info.isDurable()) {
            String subscriptionName;
            String clientId;
            SubscriptionKey key;
            DurableTopicSubscription sub;
            if (this.broker.getBrokerService().isRejectDurableConsumers()) {
                throw new JMSException("Durable Consumers are not allowed");
            }
            ActiveMQDestination destination = info.getDestination();
            if (!destination.isPattern()) {
                this.lookup(context, destination, true);
            }
            if ((sub = (DurableTopicSubscription)this.durableSubscriptions.get(key = new SubscriptionKey(clientId = context.getClientId(), subscriptionName = info.getSubscriptionName()))) != null) {
                if (!context.isAllowLinkStealing() && sub.isActive()) {
                    throw new JMSException("Durable consumer is in use for client: " + clientId + " and subscriptionName: " + subscriptionName);
                }
                if (this.hasDurableSubChanged(info, sub.getConsumerInfo())) {
                    this.durableSubscriptions.remove(key);
                    this.destinationsLock.readLock().lock();
                    try {
                        for (Destination dest : this.destinations.values()) {
                            if (!(dest instanceof Topic)) continue;
                            Topic topic = (Topic)dest;
                            topic.deleteSubscription(context, key);
                        }
                    }
                    finally {
                        this.destinationsLock.readLock().unlock();
                    }
                    super.removeConsumer(context, sub.getConsumerInfo());
                    super.addConsumer(context, info);
                    sub = (DurableTopicSubscription)this.durableSubscriptions.get(key);
                } else {
                    if (sub.getConsumerInfo().getConsumerId() != null) {
                        this.subscriptions.remove(sub.getConsumerInfo().getConsumerId());
                    }
                    if (sub.context != context || sub.info != info) {
                        sub.info = info;
                        sub.context = context;
                        sub.deactivate(this.keepDurableSubsActive, info.getLastDeliveredSequenceId());
                    }
                    if (info.isNoLocal()) {
                        sub.setSelector(sub.getSelector());
                    }
                    this.subscriptions.put(info.getConsumerId(), sub);
                }
            } else {
                super.addConsumer(context, info);
                sub = (DurableTopicSubscription)this.durableSubscriptions.get(key);
                if (sub == null) {
                    throw new JMSException("Cannot use the same consumerId: " + info.getConsumerId() + " for two different durable subscriptions clientID: " + key.getClientId() + " subscriberName: " + key.getSubscriptionName());
                }
            }
            sub.activate(this.usageManager, context, info, this.broker);
            return sub;
        }
        return super.addConsumer(context, info);
    }

    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        if (info.isDurable()) {
            SubscriptionKey key = new SubscriptionKey(context.getClientId(), info.getSubscriptionName());
            DurableTopicSubscription sub = (DurableTopicSubscription)this.durableSubscriptions.get(key);
            if (sub != null && sub.getContext() == context) {
                sub.deactivate(this.keepDurableSubsActive, info.getLastDeliveredSequenceId());
            }
        } else {
            super.removeConsumer(context, info);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeSubscription(ConnectionContext context, RemoveSubscriptionInfo info) throws Exception {
        SubscriptionKey key = new SubscriptionKey(info.getClientId(), info.getSubscriptionName());
        DurableTopicSubscription sub = (DurableTopicSubscription)this.durableSubscriptions.get(key);
        if (sub == null) {
            throw new InvalidDestinationException("No durable subscription exists for clientID: " + info.getClientId() + " and subscriptionName: " + info.getSubscriptionName());
        }
        if (sub.isActive()) {
            throw new JMSException("Durable consumer is in use");
        }
        this.durableSubscriptions.remove(key);
        this.destinationsLock.readLock().lock();
        try {
            for (Destination dest : this.destinations.values()) {
                if (dest instanceof Topic) {
                    Topic topic = (Topic)dest;
                    topic.deleteSubscription(context, key);
                    continue;
                }
                if (!(dest instanceof DestinationFilter)) continue;
                DestinationFilter filter2 = (DestinationFilter)dest;
                filter2.deleteSubscription(context, key);
            }
        }
        finally {
            this.destinationsLock.readLock().unlock();
        }
        if (this.subscriptions.get(sub.getConsumerInfo().getConsumerId()) != null) {
            super.removeConsumer(context, sub.getConsumerInfo());
        } else {
            this.destroySubscription(sub);
        }
    }

    public String toString() {
        return "TopicRegion: destinations=" + this.destinations.size() + ", subscriptions=" + this.subscriptions.size() + ", memory=" + this.usageManager.getMemoryUsage().getPercentUsage() + "%";
    }

    @Override
    protected List<Subscription> addSubscriptionsForDestination(ConnectionContext context, Destination dest) throws Exception {
        List<Subscription> rc = super.addSubscriptionsForDestination(context, dest);
        HashSet<Subscription> dupChecker = new HashSet<Subscription>(rc);
        TopicMessageStore store = (TopicMessageStore)dest.getMessageStore();
        if (store != null) {
            SubscriptionInfo[] infos = store.getAllSubscriptions();
            for (int i = 0; i < infos.length; ++i) {
                SubscriptionInfo info = infos[i];
                LOG.debug("Restoring durable subscription: {}", (Object)info);
                SubscriptionKey key = new SubscriptionKey(info);
                DurableTopicSubscription sub = (DurableTopicSubscription)this.durableSubscriptions.get(key);
                ConsumerInfo consumerInfo = this.createInactiveConsumerInfo(info);
                if (sub == null) {
                    ConnectionContext c = new ConnectionContext();
                    c.setBroker(context.getBroker());
                    c.setClientId(key.getClientId());
                    c.setConnectionId(consumerInfo.getConsumerId().getParentId().getParentId());
                    sub = (DurableTopicSubscription)this.createSubscription(c, consumerInfo);
                    sub.setOfflineTimestamp(System.currentTimeMillis());
                }
                if (dupChecker.contains(sub)) continue;
                dupChecker.add(sub);
                rc.add(sub);
                dest.addSubscription(context, sub);
            }
            this.durableSubscriptions.values();
            for (DurableTopicSubscription sub : this.durableSubscriptions.values()) {
                if (dupChecker.contains(sub) || !sub.matches(dest.getActiveMQDestination())) continue;
                rc.add(sub);
                dest.addSubscription(context, sub);
            }
        }
        return rc;
    }

    public ConsumerInfo createInactiveConsumerInfo(SubscriptionInfo info) {
        ConsumerInfo rc = new ConsumerInfo();
        rc.setSelector(info.getSelector());
        rc.setSubscriptionName(info.getSubscriptionName());
        rc.setDestination(info.getSubscribedDestination());
        rc.setConsumerId(this.createConsumerId());
        rc.setNoLocal(info.isNoLocal());
        return rc;
    }

    private ConsumerId createConsumerId() {
        return new ConsumerId(this.recoveredDurableSubSessionId, this.recoveredDurableSubIdGenerator.getNextSequenceId());
    }

    protected void configureTopic(Topic topic, ActiveMQDestination destination) {
        PolicyEntry entry;
        if (this.broker.getDestinationPolicy() != null && (entry = this.broker.getDestinationPolicy().getEntryFor(destination)) != null) {
            entry.configure((Broker)this.broker, topic);
        }
    }

    @Override
    protected Subscription createSubscription(ConnectionContext context, ConsumerInfo info) throws JMSException {
        ActiveMQDestination destination = info.getDestination();
        if (info.isDurable()) {
            if (AdvisorySupport.isAdvisoryTopic(info.getDestination())) {
                throw new JMSException("Cannot create a durable subscription for an advisory Topic");
            }
            SubscriptionKey key = new SubscriptionKey(context.getClientId(), info.getSubscriptionName());
            DurableTopicSubscription sub = (DurableTopicSubscription)this.durableSubscriptions.get(key);
            if (sub == null) {
                PolicyEntry entry;
                sub = new DurableTopicSubscription((Broker)this.broker, this.usageManager, context, info, this.keepDurableSubsActive);
                if (destination != null && this.broker.getDestinationPolicy() != null && (entry = this.broker.getDestinationPolicy().getEntryFor(destination)) != null) {
                    entry.configure((Broker)this.broker, this.usageManager, sub);
                }
            } else {
                throw new JMSException("Durable subscription is already active for clientID: " + context.getClientId() + " and subscriptionName: " + info.getSubscriptionName());
            }
            this.durableSubscriptions.put(key, sub);
            return sub;
        }
        try {
            PolicyEntry entry;
            TopicSubscription answer = new TopicSubscription(this.broker, context, info, this.usageManager);
            if (destination != null && this.broker.getDestinationPolicy() != null && (entry = this.broker.getDestinationPolicy().getEntryFor(destination)) != null) {
                entry.configure((Broker)this.broker, this.usageManager, answer);
            }
            answer.init();
            return answer;
        }
        catch (Exception e) {
            LOG.debug("Failed to create TopicSubscription ", e);
            JMSException jmsEx = new JMSException("Couldn't create TopicSubscription");
            jmsEx.setLinkedException(e);
            throw jmsEx;
        }
    }

    private boolean hasDurableSubChanged(ConsumerInfo info1, ConsumerInfo info2) throws IOException {
        if (info1.getSelector() != null ^ info2.getSelector() != null) {
            return true;
        }
        if (info1.getSelector() != null && !info1.getSelector().equals(info2.getSelector())) {
            return true;
        }
        PersistenceAdapter adapter = this.broker.getBrokerService().getPersistenceAdapter();
        if (adapter instanceof NoLocalSubscriptionAware && info1.isNoLocal() ^ info2.isNoLocal()) {
            return true;
        }
        return !info1.getDestination().equals(info2.getDestination());
    }

    @Override
    protected Set<ActiveMQDestination> getInactiveDestinations() {
        Set<ActiveMQDestination> inactiveDestinations = super.getInactiveDestinations();
        Iterator<ActiveMQDestination> iter = inactiveDestinations.iterator();
        while (iter.hasNext()) {
            ActiveMQDestination dest = iter.next();
            if (dest.isTopic()) continue;
            iter.remove();
        }
        return inactiveDestinations;
    }

    public DurableTopicSubscription lookupSubscription(String subscriptionName, String clientId) {
        SubscriptionKey key = new SubscriptionKey(clientId, subscriptionName);
        if (this.durableSubscriptions.containsKey(key)) {
            return (DurableTopicSubscription)this.durableSubscriptions.get(key);
        }
        return null;
    }

    public List<DurableTopicSubscription> lookupSubscriptions(String clientId) {
        ArrayList<DurableTopicSubscription> result = new ArrayList<DurableTopicSubscription>();
        for (Map.Entry subscriptionEntry : this.durableSubscriptions.entrySet()) {
            if (!((SubscriptionKey)subscriptionEntry.getKey()).getClientId().equals(clientId)) continue;
            result.add((DurableTopicSubscription)subscriptionEntry.getValue());
        }
        return result;
    }

    public boolean isKeepDurableSubsActive() {
        return this.keepDurableSubsActive;
    }

    public void setKeepDurableSubsActive(boolean keepDurableSubsActive) {
        this.keepDurableSubsActive = keepDurableSubsActive;
    }

    public boolean durableSubscriptionExists(SubscriptionKey key) {
        return this.durableSubscriptions.containsKey(key);
    }

    public DurableTopicSubscription getDurableSubscription(SubscriptionKey key) {
        return (DurableTopicSubscription)this.durableSubscriptions.get(key);
    }

    public Map<SubscriptionKey, DurableTopicSubscription> getDurableSubscriptions() {
        return this.durableSubscriptions;
    }
}

