/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.advisory.DestinationFilterVirtualDestinationMatcher;
import org.apache.activemq.advisory.VirtualDestinationMatcher;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.region.BaseDestination;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.TopicRegion;
import org.apache.activemq.broker.region.TopicSubscription;
import org.apache.activemq.broker.region.virtual.VirtualDestination;
import org.apache.activemq.broker.region.virtual.VirtualTopic;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.command.SessionId;
import org.apache.activemq.filter.DestinationPath;
import org.apache.activemq.security.SecurityContext;
import org.apache.activemq.state.ProducerState;
import org.apache.activemq.usage.Usage;
import org.apache.activemq.util.IdGenerator;
import org.apache.activemq.util.LongSequenceGenerator;
import org.apache.activemq.util.SubscriptionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdvisoryBroker
extends BrokerFilter {
    private static final Logger LOG = LoggerFactory.getLogger(AdvisoryBroker.class);
    private static final IdGenerator ID_GENERATOR = new IdGenerator();
    protected final ConcurrentMap<ConnectionId, ConnectionInfo> connections = new ConcurrentHashMap<ConnectionId, ConnectionInfo>();
    private final ReentrantReadWriteLock consumersLock = new ReentrantReadWriteLock();
    protected final Map<ConsumerId, ConsumerInfo> consumers = new LinkedHashMap<ConsumerId, ConsumerInfo>();
    protected final Set<VirtualDestination> virtualDestinations = Collections.newSetFromMap(new ConcurrentHashMap());
    protected final ConcurrentMap<ConsumerInfo, VirtualDestination> virtualDestinationConsumers = new ConcurrentHashMap<ConsumerInfo, VirtualDestination>();
    protected final ConcurrentMap<VirtualConsumerPair, ConsumerInfo> brokerConsumerDests = new ConcurrentHashMap<VirtualConsumerPair, ConsumerInfo>();
    protected final ConcurrentMap<ProducerId, ProducerInfo> producers = new ConcurrentHashMap<ProducerId, ProducerInfo>();
    protected final ConcurrentMap<ActiveMQDestination, DestinationInfo> destinations = new ConcurrentHashMap<ActiveMQDestination, DestinationInfo>();
    protected final ConcurrentMap<BrokerInfo, ActiveMQMessage> networkBridges = new ConcurrentHashMap<BrokerInfo, ActiveMQMessage>();
    protected final ProducerId advisoryProducerId = new ProducerId();
    private final LongSequenceGenerator messageIdGenerator = new LongSequenceGenerator();
    private VirtualDestinationMatcher virtualDestinationMatcher = new DestinationFilterVirtualDestinationMatcher();
    private final IdGenerator connectionIdGenerator = new IdGenerator("advisory");
    private final LongSequenceGenerator sessionIdGenerator = new LongSequenceGenerator();
    private final LongSequenceGenerator consumerIdGenerator = new LongSequenceGenerator();

    public AdvisoryBroker(Broker next) {
        super(next);
        this.advisoryProducerId.setConnectionId(ID_GENERATOR.generateId());
    }

    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
        super.addConnection(context, info);
        ActiveMQTopic topic = AdvisorySupport.getConnectionAdvisoryTopic();
        ConnectionInfo copy = info.copy();
        copy.setPassword("");
        this.fireAdvisory(context, topic, copy);
        this.connections.put(copy.getConnectionId(), copy);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        Subscription answer = super.addConsumer(context, info);
        if (!AdvisorySupport.isAdvisoryTopic(info.getDestination())) {
            ActiveMQTopic topic = AdvisorySupport.getConsumerAdvisoryTopic(info.getDestination());
            this.consumersLock.writeLock().lock();
            try {
                this.consumers.put(info.getConsumerId(), info);
                if (this.getBrokerService().isUseVirtualDestSubs()) {
                    for (VirtualDestination virtualDestination : this.virtualDestinations) {
                        if (!this.virtualDestinationMatcher.matches(virtualDestination, info.getDestination())) continue;
                        this.fireVirtualDestinationAddAdvisory(context, info, info.getDestination(), virtualDestination);
                    }
                }
            }
            finally {
                this.consumersLock.writeLock().unlock();
            }
            this.fireConsumerAdvisory(context, info.getDestination(), topic, info);
        } else {
            ActiveMQTopic topic;
            if (AdvisorySupport.isConnectionAdvisoryTopic(info.getDestination())) {
                for (ConnectionInfo connectionInfo : this.connections.values()) {
                    topic = AdvisorySupport.getConnectionAdvisoryTopic();
                    this.fireAdvisory(context, topic, connectionInfo, info.getConsumerId());
                }
            }
            if (AdvisorySupport.isTempDestinationAdvisoryTopic(info.getDestination())) {
                for (DestinationInfo destinationInfo : this.destinations.values()) {
                    if (!destinationInfo.getDestination().isTemporary()) continue;
                    topic = AdvisorySupport.getDestinationAdvisoryTopic(destinationInfo.getDestination());
                    this.fireAdvisory(context, topic, destinationInfo, info.getConsumerId());
                }
            } else if (AdvisorySupport.isDestinationAdvisoryTopic(info.getDestination())) {
                for (DestinationInfo destinationInfo : this.destinations.values()) {
                    topic = AdvisorySupport.getDestinationAdvisoryTopic(destinationInfo.getDestination());
                    this.fireAdvisory(context, topic, destinationInfo, info.getConsumerId());
                }
            }
            if (AdvisorySupport.isProducerAdvisoryTopic(info.getDestination())) {
                for (ProducerInfo producerInfo : this.producers.values()) {
                    topic = AdvisorySupport.getProducerAdvisoryTopic(producerInfo.getDestination());
                    this.fireProducerAdvisory(context, producerInfo.getDestination(), topic, producerInfo, info.getConsumerId());
                }
            }
            if (AdvisorySupport.isConsumerAdvisoryTopic(info.getDestination())) {
                this.consumersLock.readLock().lock();
                try {
                    for (ConsumerInfo consumerInfo : this.consumers.values()) {
                        topic = AdvisorySupport.getConsumerAdvisoryTopic(consumerInfo.getDestination());
                        this.fireConsumerAdvisory(context, consumerInfo.getDestination(), topic, consumerInfo, info.getConsumerId());
                    }
                }
                finally {
                    this.consumersLock.readLock().unlock();
                }
            }
            if (AdvisorySupport.isVirtualDestinationConsumerAdvisoryTopic(info.getDestination())) {
                for (ConsumerInfo consumerInfo : this.virtualDestinationConsumers.keySet()) {
                    topic = AdvisorySupport.getVirtualDestinationConsumerAdvisoryTopic(consumerInfo.getDestination());
                    this.fireConsumerAdvisory(context, consumerInfo.getDestination(), topic, consumerInfo);
                }
            }
            if (AdvisorySupport.isNetworkBridgeAdvisoryTopic(info.getDestination())) {
                for (BrokerInfo brokerInfo : this.networkBridges.keySet()) {
                    topic = AdvisorySupport.getNetworkBridgeAdvisoryTopic();
                    this.fireAdvisory(context, topic, brokerInfo, null, (ActiveMQMessage)this.networkBridges.get(brokerInfo));
                }
            }
        }
        return answer;
    }

    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        super.addProducer(context, info);
        if ((info.getDestination() != null || this.getBrokerService().isAnonymousProducerAdvisorySupport()) && !AdvisorySupport.isAdvisoryTopic(info.getDestination())) {
            ActiveMQTopic topic = AdvisorySupport.getProducerAdvisoryTopic(info.getDestination());
            this.fireProducerAdvisory(context, info.getDestination(), topic, info);
            this.producers.put(info.getProducerId(), info);
        }
    }

    @Override
    public Destination addDestination(ConnectionContext context, ActiveMQDestination destination, boolean create) throws Exception {
        Destination answer = super.addDestination(context, destination, create);
        if (!AdvisorySupport.isAdvisoryTopic(destination)) {
            DestinationInfo info;
            DestinationInfo previous;
            if (this.getBrokerService().isUseVirtualDestSubsOnCreation() && destination.isQueue()) {
                for (VirtualDestination virtualDestination : this.virtualDestinations) {
                    if (!this.virtualDestinationMatcher.matches(virtualDestination, destination)) continue;
                    this.fireVirtualDestinationAddAdvisory(context, null, destination, virtualDestination);
                }
            }
            if ((previous = this.destinations.putIfAbsent(destination, info = new DestinationInfo(context.getConnectionId(), 0, destination))) == null) {
                ActiveMQTopic topic = AdvisorySupport.getDestinationAdvisoryTopic(destination);
                this.fireAdvisory(context, topic, info);
            }
        }
        return answer;
    }

    @Override
    public void addDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        DestinationInfo previous;
        ActiveMQDestination destination = info.getDestination();
        this.next.addDestinationInfo(context, info);
        if (!AdvisorySupport.isAdvisoryTopic(destination) && (previous = this.destinations.putIfAbsent(destination, info)) == null) {
            ActiveMQTopic topic = AdvisorySupport.getDestinationAdvisoryTopic(destination);
            this.fireAdvisory(context, topic, info);
        }
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        super.removeDestination(context, destination, timeout2);
        DestinationInfo info = (DestinationInfo)this.destinations.remove(destination);
        if (info != null) {
            ActiveMQTopic[] advisoryDestinations;
            if (this.getBrokerService().isUseVirtualDestSubs()) {
                for (ConsumerInfo consumerInfo : this.virtualDestinationConsumers.keySet()) {
                    VirtualConsumerPair key;
                    ConsumerInfo i;
                    VirtualDestination virtualDestination = (VirtualDestination)this.virtualDestinationConsumers.get(consumerInfo);
                    if (!this.virtualDestinationMatcher.matches(virtualDestination, destination) || !consumerInfo.equals(i = (ConsumerInfo)this.brokerConsumerDests.get(key = new VirtualConsumerPair(virtualDestination, destination))) || this.brokerConsumerDests.remove(key) == null) continue;
                    LOG.debug("Virtual consumer pair removed: {} for consumer: {} ", (Object)key, (Object)i);
                    this.fireVirtualDestinationRemoveAdvisory(context, consumerInfo);
                    break;
                }
            }
            info = info.copy();
            info.setDestination(destination);
            info.setOperationType((byte)1);
            ActiveMQTopic topic = AdvisorySupport.getDestinationAdvisoryTopic(destination);
            this.fireAdvisory(context, topic, info);
            for (ActiveMQTopic advisoryDestination : advisoryDestinations = AdvisorySupport.getAllDestinationAdvisoryTopics(destination)) {
                try {
                    this.next.removeDestination(context, advisoryDestination, -1L);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }

    @Override
    public void removeDestinationInfo(ConnectionContext context, DestinationInfo destInfo) throws Exception {
        super.removeDestinationInfo(context, destInfo);
        DestinationInfo info = (DestinationInfo)this.destinations.remove(destInfo.getDestination());
        if (info != null) {
            ActiveMQTopic[] advisoryDestinations;
            info = info.copy();
            info.setDestination(destInfo.getDestination());
            info.setOperationType((byte)1);
            ActiveMQTopic topic = AdvisorySupport.getDestinationAdvisoryTopic(destInfo.getDestination());
            this.fireAdvisory(context, topic, info);
            for (ActiveMQTopic advisoryDestination : advisoryDestinations = AdvisorySupport.getAllDestinationAdvisoryTopics(destInfo.getDestination())) {
                try {
                    this.next.removeDestination(context, advisoryDestination, -1L);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }

    @Override
    public void removeConnection(ConnectionContext context, ConnectionInfo info, Throwable error) throws Exception {
        super.removeConnection(context, info, error);
        ActiveMQTopic topic = AdvisorySupport.getConnectionAdvisoryTopic();
        this.fireAdvisory(context, topic, info.createRemoveCommand());
        this.connections.remove(info.getConnectionId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        super.removeConsumer(context, info);
        ActiveMQDestination dest = info.getDestination();
        if (!AdvisorySupport.isAdvisoryTopic(dest)) {
            ActiveMQTopic topic = AdvisorySupport.getConsumerAdvisoryTopic(dest);
            this.consumersLock.writeLock().lock();
            try {
                this.consumers.remove(info.getConsumerId());
                if (this.getBrokerService().isUseVirtualDestSubs()) {
                    this.fireVirtualDestinationRemoveAdvisory(context, info);
                }
            }
            finally {
                this.consumersLock.writeLock().unlock();
            }
            if (!dest.isTemporary() || this.destinations.containsKey(dest)) {
                this.fireConsumerAdvisory(context, dest, topic, info.createRemoveCommand());
            }
        }
    }

    @Override
    public void removeSubscription(ConnectionContext context, RemoveSubscriptionInfo info) throws Exception {
        SubscriptionKey key = new SubscriptionKey(context.getClientId(), info.getSubscriptionName());
        RegionBroker regionBroker = null;
        if (this.next instanceof RegionBroker) {
            regionBroker = (RegionBroker)this.next;
        } else {
            BrokerService service2 = this.next.getBrokerService();
            regionBroker = (RegionBroker)service2.getRegionBroker();
        }
        if (regionBroker == null) {
            LOG.warn("Cannot locate a RegionBroker instance to pass along the removeSubscription call");
            throw new IllegalStateException("No RegionBroker found.");
        }
        DurableTopicSubscription sub = ((TopicRegion)regionBroker.getTopicRegion()).getDurableSubscription(key);
        super.removeSubscription(context, info);
        if (sub == null) {
            LOG.warn("We cannot send an advisory message for a durable sub removal when we don't know about the durable sub");
            return;
        }
        ActiveMQDestination dest = sub.getConsumerInfo().getDestination();
        if (!AdvisorySupport.isAdvisoryTopic(dest)) {
            ActiveMQTopic topic = AdvisorySupport.getConsumerAdvisoryTopic(dest);
            this.fireConsumerAdvisory(context, dest, topic, info);
        }
    }

    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        super.removeProducer(context, info);
        ActiveMQDestination dest = info.getDestination();
        if ((dest != null || this.getBrokerService().isAnonymousProducerAdvisorySupport()) && !AdvisorySupport.isAdvisoryTopic(dest)) {
            ActiveMQTopic topic = AdvisorySupport.getProducerAdvisoryTopic(dest);
            this.producers.remove(info.getProducerId());
            if (dest == null || !dest.isTemporary() || this.destinations.containsKey(dest)) {
                this.fireProducerAdvisory(context, dest, topic, info.createRemoveCommand());
            }
        }
    }

    @Override
    public void messageExpired(ConnectionContext context, MessageReference messageReference, Subscription subscription) {
        super.messageExpired(context, messageReference, subscription);
        try {
            if (!messageReference.isAdvisory()) {
                BaseDestination baseDestination = (BaseDestination)messageReference.getMessage().getRegionDestination();
                ActiveMQTopic topic = AdvisorySupport.getExpiredMessageTopic(baseDestination.getActiveMQDestination());
                Message payload = messageReference.getMessage().copy();
                if (!baseDestination.isIncludeBodyForAdvisory()) {
                    payload.clearBody();
                }
                ActiveMQMessage advisoryMessage = new ActiveMQMessage();
                advisoryMessage.setStringProperty("orignalMessageId", payload.getMessageId().toString());
                this.fireAdvisory(context, topic, payload, null, advisoryMessage);
            }
        }
        catch (Exception e) {
            this.handleFireFailure("expired", e);
        }
    }

    @Override
    public void messageConsumed(ConnectionContext context, MessageReference messageReference) {
        super.messageConsumed(context, messageReference);
        try {
            if (!messageReference.isAdvisory()) {
                BaseDestination baseDestination = (BaseDestination)messageReference.getMessage().getRegionDestination();
                ActiveMQTopic topic = AdvisorySupport.getMessageConsumedAdvisoryTopic(baseDestination.getActiveMQDestination());
                Message payload = messageReference.getMessage().copy();
                if (!baseDestination.isIncludeBodyForAdvisory()) {
                    payload.clearBody();
                }
                ActiveMQMessage advisoryMessage = new ActiveMQMessage();
                advisoryMessage.setStringProperty("orignalMessageId", payload.getMessageId().toString());
                advisoryMessage.setStringProperty("orignalDestination", baseDestination.getActiveMQDestination().getQualifiedName());
                this.fireAdvisory(context, topic, payload, null, advisoryMessage);
            }
        }
        catch (Exception e) {
            this.handleFireFailure("consumed", e);
        }
    }

    @Override
    public void messageDelivered(ConnectionContext context, MessageReference messageReference) {
        super.messageDelivered(context, messageReference);
        try {
            if (!messageReference.isAdvisory()) {
                BaseDestination baseDestination = (BaseDestination)messageReference.getMessage().getRegionDestination();
                ActiveMQTopic topic = AdvisorySupport.getMessageDeliveredAdvisoryTopic(baseDestination.getActiveMQDestination());
                Message payload = messageReference.getMessage().copy();
                if (!baseDestination.isIncludeBodyForAdvisory()) {
                    payload.clearBody();
                }
                ActiveMQMessage advisoryMessage = new ActiveMQMessage();
                advisoryMessage.setStringProperty("orignalMessageId", payload.getMessageId().toString());
                advisoryMessage.setStringProperty("orignalDestination", baseDestination.getActiveMQDestination().getQualifiedName());
                this.fireAdvisory(context, topic, payload, null, advisoryMessage);
            }
        }
        catch (Exception e) {
            this.handleFireFailure("delivered", e);
        }
    }

    @Override
    public void messageDiscarded(ConnectionContext context, Subscription sub, MessageReference messageReference) {
        super.messageDiscarded(context, sub, messageReference);
        try {
            if (!messageReference.isAdvisory()) {
                BaseDestination baseDestination = (BaseDestination)messageReference.getMessage().getRegionDestination();
                ActiveMQTopic topic = AdvisorySupport.getMessageDiscardedAdvisoryTopic(baseDestination.getActiveMQDestination());
                Message payload = messageReference.getMessage().copy();
                if (!baseDestination.isIncludeBodyForAdvisory()) {
                    payload.clearBody();
                }
                ActiveMQMessage advisoryMessage = new ActiveMQMessage();
                if (sub instanceof TopicSubscription) {
                    advisoryMessage.setIntProperty("discardedCount", ((TopicSubscription)sub).discarded());
                }
                advisoryMessage.setStringProperty("orignalMessageId", payload.getMessageId().toString());
                advisoryMessage.setStringProperty("consumerId", sub.getConsumerInfo().getConsumerId().toString());
                advisoryMessage.setStringProperty("orignalDestination", baseDestination.getActiveMQDestination().getQualifiedName());
                this.fireAdvisory(context, topic, payload, null, advisoryMessage);
            }
        }
        catch (Exception e) {
            this.handleFireFailure("discarded", e);
        }
    }

    @Override
    public void slowConsumer(ConnectionContext context, Destination destination, Subscription subs) {
        super.slowConsumer(context, destination, subs);
        try {
            if (!AdvisorySupport.isAdvisoryTopic(destination.getActiveMQDestination())) {
                ActiveMQTopic topic = AdvisorySupport.getSlowConsumerAdvisoryTopic(destination.getActiveMQDestination());
                ActiveMQMessage advisoryMessage = new ActiveMQMessage();
                advisoryMessage.setStringProperty("consumerId", subs.getConsumerInfo().getConsumerId().toString());
                this.fireAdvisory(context, topic, subs.getConsumerInfo(), null, advisoryMessage);
            }
        }
        catch (Exception e) {
            this.handleFireFailure("slow consumer", e);
        }
    }

    @Override
    public void fastProducer(ConnectionContext context, ProducerInfo producerInfo, ActiveMQDestination destination) {
        super.fastProducer(context, producerInfo, destination);
        try {
            if (!AdvisorySupport.isAdvisoryTopic(destination)) {
                ActiveMQTopic topic = AdvisorySupport.getFastProducerAdvisoryTopic(destination);
                ActiveMQMessage advisoryMessage = new ActiveMQMessage();
                advisoryMessage.setStringProperty("producerId", producerInfo.getProducerId().toString());
                this.fireAdvisory(context, topic, producerInfo, null, advisoryMessage);
            }
        }
        catch (Exception e) {
            this.handleFireFailure("fast producer", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void virtualDestinationAdded(ConnectionContext context, VirtualDestination virtualDestination) {
        block9: {
            super.virtualDestinationAdded(context, virtualDestination);
            if (this.virtualDestinations.add(virtualDestination)) {
                LOG.debug("Virtual destination added: {}", (Object)virtualDestination);
                try {
                    if (AdvisorySupport.isAdvisoryTopic(virtualDestination.getVirtualDestination())) break block9;
                    this.consumersLock.readLock().lock();
                    try {
                        if (this.getBrokerService().isUseVirtualDestSubsOnCreation()) {
                            for (ActiveMQDestination destination : this.destinations.keySet()) {
                                if (!destination.isQueue() || !this.virtualDestinationMatcher.matches(virtualDestination, destination)) continue;
                                this.fireVirtualDestinationAddAdvisory(context, null, destination, virtualDestination);
                            }
                        }
                        for (ConsumerInfo info : this.consumers.values()) {
                            if (!this.virtualDestinationMatcher.matches(virtualDestination, info.getDestination())) continue;
                            this.fireVirtualDestinationAddAdvisory(context, info, info.getDestination(), virtualDestination);
                        }
                    }
                    finally {
                        this.consumersLock.readLock().unlock();
                    }
                }
                catch (Exception e) {
                    this.handleFireFailure("virtualDestinationAdded", e);
                }
            }
        }
    }

    private void fireVirtualDestinationAddAdvisory(ConnectionContext context, ConsumerInfo info, ActiveMQDestination activeMQDest, VirtualDestination virtualDestination) throws Exception {
        if (info == null) {
            ConnectionId connectionId;
            SessionId sessionId;
            ConsumerId consumerId;
            VirtualConsumerPair pair = new VirtualConsumerPair(virtualDestination, activeMQDest);
            if (this.brokerConsumerDests.get(pair) == null && this.brokerConsumerDests.putIfAbsent(pair, info = new ConsumerInfo(consumerId = new ConsumerId(sessionId = new SessionId(connectionId = new ConnectionId(this.connectionIdGenerator.generateId()), this.sessionIdGenerator.getNextSequenceId()), this.consumerIdGenerator.getNextSequenceId()))) == null) {
                LOG.debug("Virtual consumer pair added: {} for consumer: {} ", (Object)pair, (Object)info);
                this.setConsumerInfoVirtualDest(info, virtualDestination, activeMQDest);
                ActiveMQTopic topic = AdvisorySupport.getVirtualDestinationConsumerAdvisoryTopic(info.getDestination());
                if (this.virtualDestinationConsumers.putIfAbsent(info, virtualDestination) == null) {
                    LOG.debug("Virtual consumer added: {}, for virtual destination: {}", (Object)info, (Object)virtualDestination);
                    this.fireConsumerAdvisory(context, info.getDestination(), topic, info);
                }
            }
        } else {
            info = info.copy();
            this.setConsumerInfoVirtualDest(info, virtualDestination, activeMQDest);
            ActiveMQTopic topic = AdvisorySupport.getVirtualDestinationConsumerAdvisoryTopic(info.getDestination());
            if (this.virtualDestinationConsumers.putIfAbsent(info, virtualDestination) == null) {
                LOG.debug("Virtual consumer added: {}, for virtual destination: {}", (Object)info, (Object)virtualDestination);
                this.fireConsumerAdvisory(context, info.getDestination(), topic, info);
            }
        }
    }

    private void setConsumerInfoVirtualDest(ConsumerInfo info, VirtualDestination virtualDestination, ActiveMQDestination activeMQDest) {
        info.setDestination(virtualDestination.getVirtualDestination());
        if (virtualDestination instanceof VirtualTopic) {
            String[] postfixPaths;
            String postfix;
            VirtualTopic vt = (VirtualTopic)virtualDestination;
            String prefix = vt.getPrefix() != null ? vt.getPrefix() : "";
            String string = postfix = vt.getPostfix() != null ? vt.getPostfix() : "";
            if (prefix.endsWith(".")) {
                prefix = prefix.substring(0, prefix.length() - 1);
            }
            if (postfix.startsWith(".")) {
                postfix = postfix.substring(1, postfix.length());
            }
            ActiveMQTopic prefixDestination = prefix.length() > 0 ? new ActiveMQTopic(prefix) : null;
            ActiveMQTopic postfixDestination = postfix.length() > 0 ? new ActiveMQTopic(postfix) : null;
            String[] prefixPaths = prefixDestination != null ? prefixDestination.getDestinationPaths() : new String[]{};
            String[] activeMQDestPaths = activeMQDest.getDestinationPaths();
            String[] stringArray = postfixPaths = postfixDestination != null ? postfixDestination.getDestinationPaths() : new String[]{};
            if (activeMQDestPaths.length > prefixPaths.length + postfixPaths.length) {
                String[] topicPath = Arrays.copyOfRange(activeMQDestPaths, 0 + prefixPaths.length, activeMQDestPaths.length - postfixPaths.length);
                ActiveMQTopic newTopic = new ActiveMQTopic(DestinationPath.toString(topicPath));
                info.setDestination(newTopic);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void virtualDestinationRemoved(ConnectionContext context, VirtualDestination virtualDestination) {
        super.virtualDestinationRemoved(context, virtualDestination);
        if (this.virtualDestinations.remove(virtualDestination)) {
            LOG.debug("Virtual destination removed: {}", (Object)virtualDestination);
            try {
                this.consumersLock.readLock().lock();
                try {
                    if (this.getBrokerService().isUseVirtualDestSubsOnCreation() && !AdvisorySupport.isAdvisoryTopic(virtualDestination.getVirtualDestination())) {
                        for (ConsumerInfo info : this.virtualDestinationConsumers.keySet()) {
                            if (!((VirtualDestination)this.virtualDestinationConsumers.get(info)).equals(virtualDestination)) continue;
                            this.fireVirtualDestinationRemoveAdvisory(context, info);
                            for (VirtualConsumerPair activeMQDest : this.brokerConsumerDests.keySet()) {
                                ConsumerInfo i = (ConsumerInfo)this.brokerConsumerDests.get(activeMQDest);
                                if (!info.equals(i) || this.brokerConsumerDests.remove(activeMQDest) == null) continue;
                                LOG.debug("Virtual consumer pair removed: {} for consumer: {} ", (Object)activeMQDest, (Object)i);
                            }
                        }
                    }
                }
                finally {
                    this.consumersLock.readLock().unlock();
                }
            }
            catch (Exception e) {
                this.handleFireFailure("virtualDestinationAdded", e);
            }
        }
    }

    private void fireVirtualDestinationRemoveAdvisory(ConnectionContext context, ConsumerInfo info) throws Exception {
        VirtualDestination virtualDestination = (VirtualDestination)this.virtualDestinationConsumers.remove(info);
        if (virtualDestination != null) {
            LOG.debug("Virtual consumer removed: {}, for virtual destination: {}", (Object)info, (Object)virtualDestination);
            ActiveMQTopic topic = AdvisorySupport.getVirtualDestinationConsumerAdvisoryTopic(virtualDestination.getVirtualDestination());
            ActiveMQDestination dest = info.getDestination();
            if (!dest.isTemporary() || this.destinations.containsKey(dest)) {
                this.fireConsumerAdvisory(context, dest, topic, info.createRemoveCommand());
            }
        }
    }

    @Override
    public void isFull(ConnectionContext context, Destination destination, Usage<?> usage) {
        super.isFull(context, destination, usage);
        if (!AdvisorySupport.isAdvisoryTopic(destination.getActiveMQDestination())) {
            try {
                ActiveMQTopic topic = AdvisorySupport.getFullAdvisoryTopic(destination.getActiveMQDestination());
                ActiveMQMessage advisoryMessage = new ActiveMQMessage();
                advisoryMessage.setStringProperty("usageName", usage.getName());
                advisoryMessage.setLongProperty("usageCount", usage.getUsage());
                this.fireAdvisory(context, topic, null, null, advisoryMessage);
            }
            catch (Exception e) {
                this.handleFireFailure("is full", e);
            }
        }
    }

    @Override
    public void nowMasterBroker() {
        super.nowMasterBroker();
        try {
            ActiveMQTopic topic = AdvisorySupport.getMasterBrokerAdvisoryTopic();
            ActiveMQMessage advisoryMessage = new ActiveMQMessage();
            ConnectionContext context = new ConnectionContext();
            context.setSecurityContext(SecurityContext.BROKER_SECURITY_CONTEXT);
            context.setBroker(this.getBrokerService().getBroker());
            this.fireAdvisory(context, topic, null, null, advisoryMessage);
        }
        catch (Exception e) {
            this.handleFireFailure("now master broker", e);
        }
    }

    @Override
    public boolean sendToDeadLetterQueue(ConnectionContext context, MessageReference messageReference, Subscription subscription, Throwable poisonCause) {
        boolean wasDLQd = super.sendToDeadLetterQueue(context, messageReference, subscription, poisonCause);
        if (wasDLQd) {
            try {
                if (!messageReference.isAdvisory()) {
                    BaseDestination baseDestination = (BaseDestination)messageReference.getMessage().getRegionDestination();
                    ActiveMQTopic topic = AdvisorySupport.getMessageDLQdAdvisoryTopic(baseDestination.getActiveMQDestination());
                    Message payload = messageReference.getMessage().copy();
                    if (!baseDestination.isIncludeBodyForAdvisory()) {
                        payload.clearBody();
                    }
                    this.fireAdvisory(context, topic, payload);
                }
            }
            catch (Exception e) {
                this.handleFireFailure("add to DLQ", e);
            }
        }
        return wasDLQd;
    }

    @Override
    public void networkBridgeStarted(BrokerInfo brokerInfo, boolean createdByDuplex, String remoteIp) {
        try {
            if (brokerInfo != null) {
                ActiveMQMessage advisoryMessage = new ActiveMQMessage();
                advisoryMessage.setBooleanProperty("started", true);
                advisoryMessage.setBooleanProperty("createdByDuplex", createdByDuplex);
                advisoryMessage.setStringProperty("remoteIp", remoteIp);
                this.networkBridges.putIfAbsent(brokerInfo, advisoryMessage);
                ActiveMQTopic topic = AdvisorySupport.getNetworkBridgeAdvisoryTopic();
                ConnectionContext context = new ConnectionContext();
                context.setSecurityContext(SecurityContext.BROKER_SECURITY_CONTEXT);
                context.setBroker(this.getBrokerService().getBroker());
                this.fireAdvisory(context, topic, brokerInfo, null, advisoryMessage);
            }
        }
        catch (Exception e) {
            this.handleFireFailure("network bridge started", e);
        }
    }

    @Override
    public void networkBridgeStopped(BrokerInfo brokerInfo) {
        try {
            if (brokerInfo != null) {
                ActiveMQMessage advisoryMessage = new ActiveMQMessage();
                advisoryMessage.setBooleanProperty("started", false);
                this.networkBridges.remove(brokerInfo);
                ActiveMQTopic topic = AdvisorySupport.getNetworkBridgeAdvisoryTopic();
                ConnectionContext context = new ConnectionContext();
                context.setSecurityContext(SecurityContext.BROKER_SECURITY_CONTEXT);
                context.setBroker(this.getBrokerService().getBroker());
                this.fireAdvisory(context, topic, brokerInfo, null, advisoryMessage);
            }
        }
        catch (Exception e) {
            this.handleFireFailure("network bridge stopped", e);
        }
    }

    private void handleFireFailure(String message, Throwable cause) {
        LOG.warn("Failed to fire {} advisory, reason: {}", (Object)message, (Object)cause);
        LOG.debug("{} detail: {}", message, cause, cause);
    }

    protected void fireAdvisory(ConnectionContext context, ActiveMQTopic topic, Command command) throws Exception {
        this.fireAdvisory(context, topic, command, null);
    }

    protected void fireAdvisory(ConnectionContext context, ActiveMQTopic topic, Command command, ConsumerId targetConsumerId) throws Exception {
        ActiveMQMessage advisoryMessage = new ActiveMQMessage();
        this.fireAdvisory(context, topic, command, targetConsumerId, advisoryMessage);
    }

    protected void fireConsumerAdvisory(ConnectionContext context, ActiveMQDestination consumerDestination, ActiveMQTopic topic, Command command) throws Exception {
        this.fireConsumerAdvisory(context, consumerDestination, topic, command, null);
    }

    protected void fireConsumerAdvisory(ConnectionContext context, ActiveMQDestination consumerDestination, ActiveMQTopic topic, Command command, ConsumerId targetConsumerId) throws Exception {
        ActiveMQMessage advisoryMessage = new ActiveMQMessage();
        int count = 0;
        Set<Destination> set = this.getDestinations(consumerDestination);
        if (set != null) {
            for (Destination dest : set) {
                count = (int)((long)count + dest.getDestinationStatistics().getConsumers().getCount());
            }
        }
        advisoryMessage.setIntProperty("consumerCount", count);
        this.fireAdvisory(context, topic, command, targetConsumerId, advisoryMessage);
    }

    protected void fireProducerAdvisory(ConnectionContext context, ActiveMQDestination producerDestination, ActiveMQTopic topic, Command command) throws Exception {
        this.fireProducerAdvisory(context, producerDestination, topic, command, null);
    }

    protected void fireProducerAdvisory(ConnectionContext context, ActiveMQDestination producerDestination, ActiveMQTopic topic, Command command, ConsumerId targetConsumerId) throws Exception {
        Set<Destination> set;
        ActiveMQMessage advisoryMessage = new ActiveMQMessage();
        int count = 0;
        if (producerDestination != null && (set = this.getDestinations(producerDestination)) != null) {
            for (Destination dest : set) {
                count = (int)((long)count + dest.getDestinationStatistics().getProducers().getCount());
            }
        }
        advisoryMessage.setIntProperty("producerCount", count);
        this.fireAdvisory(context, topic, command, targetConsumerId, advisoryMessage);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void fireAdvisory(ConnectionContext context, ActiveMQTopic topic, Command command, ConsumerId targetConsumerId, ActiveMQMessage advisoryMessage) throws Exception {
        advisoryMessage.setStringProperty("originBrokerName", this.getBrokerName());
        String id = this.getBrokerId() != null ? this.getBrokerId().getValue() : "NOT_SET";
        advisoryMessage.setStringProperty("originBrokerId", id);
        String url2 = this.getBrokerService().getVmConnectorURI().toString();
        if (context.getConnector() instanceof TransportConnector && ((TransportConnector)context.getConnector()).getPublishableConnectString() != null) {
            url2 = ((TransportConnector)context.getConnector()).getPublishableConnectString();
        } else if (this.getBrokerService().getDefaultSocketURIString() != null) {
            url2 = this.getBrokerService().getDefaultSocketURIString();
        }
        advisoryMessage.setStringProperty("originBrokerURL", url2);
        advisoryMessage.setDataStructure(command);
        advisoryMessage.setPersistent(false);
        advisoryMessage.setType("Advisory");
        advisoryMessage.setMessageId(new MessageId(this.advisoryProducerId, this.messageIdGenerator.getNextSequenceId()));
        advisoryMessage.setTargetConsumerId(targetConsumerId);
        advisoryMessage.setDestination(topic);
        advisoryMessage.setResponseRequired(false);
        advisoryMessage.setProducerId(this.advisoryProducerId);
        boolean originalFlowControl = context.isProducerFlowControl();
        ProducerBrokerExchange producerExchange = new ProducerBrokerExchange();
        producerExchange.setConnectionContext(context);
        producerExchange.setMutable(true);
        producerExchange.setProducerState(new ProducerState(new ProducerInfo()));
        try {
            context.setProducerFlowControl(false);
            this.next.send(producerExchange, advisoryMessage);
        }
        finally {
            context.setProducerFlowControl(originalFlowControl);
        }
    }

    public Map<ConnectionId, ConnectionInfo> getAdvisoryConnections() {
        return this.connections;
    }

    public Collection<ConsumerInfo> getAdvisoryConsumers() {
        this.consumersLock.readLock().lock();
        try {
            ArrayList<ConsumerInfo> arrayList = new ArrayList<ConsumerInfo>(this.consumers.values());
            return arrayList;
        }
        finally {
            this.consumersLock.readLock().unlock();
        }
    }

    public Map<ProducerId, ProducerInfo> getAdvisoryProducers() {
        return this.producers;
    }

    public Map<ActiveMQDestination, DestinationInfo> getAdvisoryDestinations() {
        return this.destinations;
    }

    public ConcurrentMap<ConsumerInfo, VirtualDestination> getVirtualDestinationConsumers() {
        return this.virtualDestinationConsumers;
    }

    private class VirtualConsumerPair {
        private final VirtualDestination virtualDestination;
        private final ActiveMQDestination activeMQDestination;

        public VirtualConsumerPair(VirtualDestination virtualDestination, ActiveMQDestination activeMQDestination) {
            this.virtualDestination = virtualDestination;
            this.activeMQDestination = activeMQDestination;
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + this.getOuterType().hashCode();
            result = 31 * result + (this.activeMQDestination == null ? 0 : this.activeMQDestination.hashCode());
            result = 31 * result + (this.virtualDestination == null ? 0 : this.virtualDestination.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            VirtualConsumerPair other = (VirtualConsumerPair)obj;
            if (!this.getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (this.activeMQDestination == null ? other.activeMQDestination != null : !this.activeMQDestination.equals(other.activeMQDestination)) {
                return false;
            }
            return !(this.virtualDestination == null ? other.virtualDestination != null : !this.virtualDestination.equals(other.virtualDestination));
        }

        public String toString() {
            return "VirtualConsumerPair [virtualDestination=" + this.virtualDestination + ", activeMQDestination=" + this.activeMQDestination + "]";
        }

        private AdvisoryBroker getOuterType() {
            return AdvisoryBroker.this;
        }
    }
}

