/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import javax.jms.IllegalStateException;
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.jmx.AbortSlowAckConsumerStrategyView;
import org.apache.activemq.broker.jmx.AbortSlowConsumerStrategyView;
import org.apache.activemq.broker.jmx.AsyncAnnotatedMBean;
import org.apache.activemq.broker.jmx.BrokerMBeanSupport;
import org.apache.activemq.broker.jmx.DestinationView;
import org.apache.activemq.broker.jmx.DurableSubscriptionView;
import org.apache.activemq.broker.jmx.InactiveDurableSubscriptionView;
import org.apache.activemq.broker.jmx.ManagedQueueRegion;
import org.apache.activemq.broker.jmx.ManagedTempQueueRegion;
import org.apache.activemq.broker.jmx.ManagedTempTopicRegion;
import org.apache.activemq.broker.jmx.ManagedTopicRegion;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.broker.jmx.OpenTypeSupport;
import org.apache.activemq.broker.jmx.ProducerView;
import org.apache.activemq.broker.jmx.QueueView;
import org.apache.activemq.broker.jmx.RecoveredXATransactionView;
import org.apache.activemq.broker.jmx.SubscriptionView;
import org.apache.activemq.broker.jmx.TopicSubscriptionView;
import org.apache.activemq.broker.jmx.TopicView;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFactory;
import org.apache.activemq.broker.region.DestinationInterceptor;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.NullMessageReference;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.broker.region.Region;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.TopicRegion;
import org.apache.activemq.broker.region.TopicSubscription;
import org.apache.activemq.broker.region.policy.AbortSlowAckConsumerStrategy;
import org.apache.activemq.broker.region.policy.AbortSlowConsumerStrategy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.SubscriptionInfo;
import org.apache.activemq.thread.Scheduler;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transaction.XATransaction;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.util.SubscriptionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManagedRegionBroker
extends RegionBroker {
    private static final Logger LOG = LoggerFactory.getLogger(ManagedRegionBroker.class);
    private final ManagementContext managementContext;
    private final ObjectName brokerObjectName;
    private final Map<ObjectName, DestinationView> topics = new ConcurrentHashMap<ObjectName, DestinationView>();
    private final Map<ObjectName, DestinationView> queues = new ConcurrentHashMap<ObjectName, DestinationView>();
    private final Map<ObjectName, DestinationView> temporaryQueues = new ConcurrentHashMap<ObjectName, DestinationView>();
    private final Map<ObjectName, DestinationView> temporaryTopics = new ConcurrentHashMap<ObjectName, DestinationView>();
    private final Map<ObjectName, SubscriptionView> queueSubscribers = new ConcurrentHashMap<ObjectName, SubscriptionView>();
    private final Map<ObjectName, SubscriptionView> topicSubscribers = new ConcurrentHashMap<ObjectName, SubscriptionView>();
    private final Map<ObjectName, SubscriptionView> durableTopicSubscribers = new ConcurrentHashMap<ObjectName, SubscriptionView>();
    private final Map<ObjectName, SubscriptionView> inactiveDurableTopicSubscribers = new ConcurrentHashMap<ObjectName, SubscriptionView>();
    private final Map<ObjectName, SubscriptionView> temporaryQueueSubscribers = new ConcurrentHashMap<ObjectName, SubscriptionView>();
    private final Map<ObjectName, SubscriptionView> temporaryTopicSubscribers = new ConcurrentHashMap<ObjectName, SubscriptionView>();
    private final Map<ObjectName, ProducerView> queueProducers = new ConcurrentHashMap<ObjectName, ProducerView>();
    private final Map<ObjectName, ProducerView> topicProducers = new ConcurrentHashMap<ObjectName, ProducerView>();
    private final Map<ObjectName, ProducerView> temporaryQueueProducers = new ConcurrentHashMap<ObjectName, ProducerView>();
    private final Map<ObjectName, ProducerView> temporaryTopicProducers = new ConcurrentHashMap<ObjectName, ProducerView>();
    private final Map<ObjectName, ProducerView> dynamicDestinationProducers = new ConcurrentHashMap<ObjectName, ProducerView>();
    private final Map<SubscriptionKey, ObjectName> subscriptionKeys = new ConcurrentHashMap<SubscriptionKey, ObjectName>();
    private final Map<Subscription, ObjectName> subscriptionMap = new ConcurrentHashMap<Subscription, ObjectName>();
    private final Set<ObjectName> registeredMBeans;
    private Broker contextBroker;
    private final ExecutorService asyncInvokeService;
    private final long mbeanTimeout;

    public ManagedRegionBroker(BrokerService brokerService, ManagementContext context, ObjectName brokerObjectName, TaskRunnerFactory taskRunnerFactory, SystemUsage memoryManager, DestinationFactory destinationFactory, DestinationInterceptor destinationInterceptor, Scheduler scheduler, ThreadPoolExecutor executor2) throws IOException {
        super(brokerService, taskRunnerFactory, memoryManager, destinationFactory, destinationInterceptor, scheduler, executor2);
        new ConcurrentHashMap();
        this.registeredMBeans = ConcurrentHashMap.newKeySet();
        this.managementContext = context;
        this.brokerObjectName = brokerObjectName;
        this.mbeanTimeout = brokerService.getMbeanInvocationTimeout();
        this.asyncInvokeService = this.mbeanTimeout > 0L ? executor2 : null;
    }

    @Override
    public void start() throws Exception {
        super.start();
        this.buildExistingSubscriptions();
    }

    @Override
    protected void doStop(ServiceStopper stopper) {
        super.doStop(stopper);
        for (ObjectName name : this.registeredMBeans) {
            try {
                this.managementContext.unregisterMBean(name);
            }
            catch (InstanceNotFoundException e) {
                LOG.warn("The MBean {} is no longer registered with JMX", (Object)name);
            }
            catch (Exception e) {
                stopper.onException(this, e);
            }
        }
        this.registeredMBeans.clear();
    }

    @Override
    protected Region createQueueRegion(SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        return new ManagedQueueRegion(this, this.destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
    }

    @Override
    protected Region createTempQueueRegion(SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        return new ManagedTempQueueRegion(this, this.destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
    }

    @Override
    protected Region createTempTopicRegion(SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        return new ManagedTempTopicRegion(this, this.destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
    }

    @Override
    protected Region createTopicRegion(SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        return new ManagedTopicRegion(this, this.destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
    }

    public void register(ActiveMQDestination destName, Destination destination) {
        try {
            DestinationView view;
            ObjectName objectName = BrokerMBeanSupport.createDestinationName(this.brokerObjectName, destName);
            if (destination instanceof Queue) {
                view = new QueueView(this, (Queue)destination);
            } else if (destination instanceof Topic) {
                view = new TopicView(this, (Topic)destination);
            } else {
                view = null;
                LOG.warn("JMX View is not supported for custom destination {}", (Object)destination);
            }
            if (view != null) {
                this.registerDestination(objectName, destName, view);
            }
        }
        catch (Exception e) {
            LOG.error("Failed to register destination {}", (Object)destName, (Object)e);
        }
    }

    public void unregister(ActiveMQDestination destName) {
        try {
            ObjectName objectName = BrokerMBeanSupport.createDestinationName(this.brokerObjectName, destName);
            this.unregisterDestination(objectName);
        }
        catch (Exception e) {
            LOG.error("Failed to unregister {}", (Object)destName, (Object)e);
        }
    }

    public ObjectName registerSubscription(ConnectionContext context, Subscription sub) {
        String connectionClientId = context.getClientId();
        SubscriptionKey key = new SubscriptionKey(context.getClientId(), sub.getConsumerInfo().getSubscriptionName());
        try {
            ObjectName objectName = BrokerMBeanSupport.createSubscriptionName(this.brokerObjectName, connectionClientId, sub.getConsumerInfo());
            if (sub.getConsumerInfo().getConsumerId().getConnectionId().equals("OFFLINE")) {
                SubscriptionInfo info = new SubscriptionInfo();
                info.setClientId(context.getClientId());
                info.setSubscriptionName(sub.getConsumerInfo().getSubscriptionName());
                info.setDestination(sub.getConsumerInfo().getDestination());
                info.setSelector(sub.getSelector());
                this.addInactiveSubscription(key, info, sub);
            } else {
                String userName;
                String string = userName = this.brokerService.isPopulateUserNameInMBeans() ? context.getUserName() : null;
                SubscriptionView view = sub.getConsumerInfo().isDurable() ? new DurableSubscriptionView(this, this.brokerService, context.getClientId(), userName, sub) : (sub instanceof TopicSubscription ? new TopicSubscriptionView(context.getClientId(), userName, (TopicSubscription)sub) : new SubscriptionView(context.getClientId(), userName, sub));
                this.registerSubscription(objectName, sub.getConsumerInfo(), key, view);
            }
            this.subscriptionMap.put(sub, objectName);
            return objectName;
        }
        catch (Exception e) {
            LOG.error("Failed to register subscription {}", (Object)sub, (Object)e);
            return null;
        }
    }

    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
        super.addConnection(context, info);
        this.contextBroker.getBrokerService().incrementCurrentConnections();
        this.contextBroker.getBrokerService().incrementTotalConnections();
    }

    @Override
    public void removeConnection(ConnectionContext context, ConnectionInfo info, Throwable error) throws Exception {
        super.removeConnection(context, info, error);
        this.contextBroker.getBrokerService().decrementCurrentConnections();
    }

    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        Subscription sub = super.addConsumer(context, info);
        SubscriptionKey subscriptionKey = new SubscriptionKey(sub.getContext().getClientId(), sub.getConsumerInfo().getSubscriptionName());
        ObjectName inactiveName = this.subscriptionKeys.get(subscriptionKey);
        if (inactiveName != null) {
            this.registerSubscription(context, sub);
        }
        return sub;
    }

    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        for (Subscription sub : this.subscriptionMap.keySet()) {
            if (!sub.getConsumerInfo().equals(info)) continue;
            this.unregisterSubscription(this.subscriptionMap.get(sub), true);
        }
        super.removeConsumer(context, info);
    }

    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        super.addProducer(context, info);
        String connectionClientId = context.getClientId();
        ObjectName objectName = BrokerMBeanSupport.createProducerName(this.brokerObjectName, context.getClientId(), info);
        String userName = this.brokerService.isPopulateUserNameInMBeans() ? context.getUserName() : null;
        ProducerView view = new ProducerView(info, connectionClientId, userName, this);
        this.registerProducer(objectName, info.getDestination(), view);
    }

    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        ObjectName objectName = BrokerMBeanSupport.createProducerName(this.brokerObjectName, context.getClientId(), info);
        this.unregisterProducer(objectName);
        super.removeProducer(context, info);
    }

    @Override
    public void send(ProducerBrokerExchange exchange, Message message) throws Exception {
        ActiveMQDestination dest;
        ObjectName objectName;
        ProducerView view;
        ProducerInfo info;
        if (exchange != null && exchange.getProducerState() != null && exchange.getProducerState().getInfo() != null && (info = exchange.getProducerState().getInfo()).getDestination() == null && info.getProducerId() != null && (view = this.dynamicDestinationProducers.get(objectName = BrokerMBeanSupport.createProducerName(this.brokerObjectName, exchange.getConnectionContext().getClientId(), info))) != null && (dest = message.getDestination()) != null) {
            view.setLastUsedDestinationName(dest);
        }
        super.send(exchange, message);
    }

    public void unregisterSubscription(Subscription sub) {
        ObjectName name = this.subscriptionMap.remove(sub);
        if (name != null) {
            try {
                SubscriptionKey subscriptionKey = new SubscriptionKey(sub.getContext().getClientId(), sub.getConsumerInfo().getSubscriptionName());
                ObjectName inactiveName = this.subscriptionKeys.remove(subscriptionKey);
                if (inactiveName != null) {
                    this.inactiveDurableTopicSubscribers.remove(inactiveName);
                    this.managementContext.unregisterMBean(inactiveName);
                }
            }
            catch (Exception e) {
                LOG.error("Failed to unregister subscription {}", (Object)sub, (Object)e);
            }
        }
    }

    protected void registerDestination(ObjectName key, ActiveMQDestination dest, DestinationView view) throws Exception {
        if (dest.isQueue()) {
            if (dest.isTemporary()) {
                this.temporaryQueues.put(key, view);
            } else {
                this.queues.put(key, view);
            }
        } else if (dest.isTemporary()) {
            this.temporaryTopics.put(key, view);
        } else {
            this.topics.put(key, view);
        }
        try {
            if (AsyncAnnotatedMBean.registerMBean(this.asyncInvokeService, this.mbeanTimeout, this.managementContext, view, key) != null) {
                this.registeredMBeans.add(key);
            }
        }
        catch (Throwable e) {
            LOG.warn("Failed to register MBean {}", (Object)key);
            LOG.debug("Failure reason: ", e);
        }
    }

    protected void unregisterDestination(ObjectName key) throws Exception {
        DestinationView view = this.removeAndRemember(this.topics, key, null);
        view = this.removeAndRemember(this.queues, key, view);
        view = this.removeAndRemember(this.temporaryQueues, key, view);
        view = this.removeAndRemember(this.temporaryTopics, key, view);
        if (this.registeredMBeans.remove(key)) {
            try {
                this.managementContext.unregisterMBean(key);
            }
            catch (Throwable e) {
                LOG.warn("Failed to unregister MBean {}", (Object)key);
                LOG.debug("Failure reason: ", e);
            }
        }
        if (view != null && (key = view.getSlowConsumerStrategy()) != null && this.registeredMBeans.remove(key)) {
            try {
                this.managementContext.unregisterMBean(key);
            }
            catch (Throwable e) {
                LOG.warn("Failed to unregister slow consumer strategy MBean {}", (Object)key);
                LOG.debug("Failure reason: ", e);
            }
        }
    }

    protected void registerProducer(ObjectName key, ActiveMQDestination dest, ProducerView view) throws Exception {
        if (dest != null) {
            if (dest.isQueue()) {
                if (dest.isTemporary()) {
                    this.temporaryQueueProducers.put(key, view);
                } else {
                    this.queueProducers.put(key, view);
                }
            } else if (dest.isTemporary()) {
                this.temporaryTopicProducers.put(key, view);
            } else {
                this.topicProducers.put(key, view);
            }
        } else {
            this.dynamicDestinationProducers.put(key, view);
        }
        try {
            if (AsyncAnnotatedMBean.registerMBean(this.asyncInvokeService, this.mbeanTimeout, this.managementContext, view, key) != null) {
                this.registeredMBeans.add(key);
            }
        }
        catch (Throwable e) {
            LOG.warn("Failed to register MBean {}", (Object)key);
            LOG.debug("Failure reason: ", e);
        }
    }

    protected void unregisterProducer(ObjectName key) throws Exception {
        this.queueProducers.remove(key);
        this.topicProducers.remove(key);
        this.temporaryQueueProducers.remove(key);
        this.temporaryTopicProducers.remove(key);
        this.dynamicDestinationProducers.remove(key);
        if (this.registeredMBeans.remove(key)) {
            try {
                this.managementContext.unregisterMBean(key);
            }
            catch (Throwable e) {
                LOG.warn("Failed to unregister MBean {}", (Object)key);
                LOG.debug("Failure reason: ", e);
            }
        }
    }

    private DestinationView removeAndRemember(Map<ObjectName, DestinationView> map, ObjectName key, DestinationView view) {
        DestinationView candidate = map.remove(key);
        if (candidate != null && view == null) {
            view = candidate;
        }
        return candidate != null ? candidate : view;
    }

    protected void registerSubscription(ObjectName key, ConsumerInfo info, SubscriptionKey subscriptionKey, SubscriptionView view) throws Exception {
        ActiveMQDestination dest = info.getDestination();
        if (dest.isQueue()) {
            if (dest.isTemporary()) {
                this.temporaryQueueSubscribers.put(key, view);
            } else {
                this.queueSubscribers.put(key, view);
            }
        } else if (dest.isTemporary()) {
            this.temporaryTopicSubscribers.put(key, view);
        } else if (info.isDurable()) {
            this.durableTopicSubscribers.put(key, view);
            try {
                ObjectName inactiveName = this.subscriptionKeys.get(subscriptionKey);
                if (inactiveName != null) {
                    this.inactiveDurableTopicSubscribers.remove(inactiveName);
                    this.registeredMBeans.remove(inactiveName);
                    this.managementContext.unregisterMBean(inactiveName);
                }
            }
            catch (Throwable e) {
                LOG.error("Unable to unregister inactive durable subscriber {}", (Object)subscriptionKey, (Object)e);
            }
        } else {
            this.topicSubscribers.put(key, view);
        }
        try {
            if (AsyncAnnotatedMBean.registerMBean(this.asyncInvokeService, this.mbeanTimeout, this.managementContext, view, key) != null) {
                this.registeredMBeans.add(key);
            }
        }
        catch (Throwable e) {
            LOG.warn("Failed to register MBean {}", (Object)key);
            LOG.debug("Failure reason: ", e);
        }
    }

    protected void unregisterSubscription(ObjectName key, boolean addToInactive) throws Exception {
        DurableSubscriptionView view;
        this.queueSubscribers.remove(key);
        this.topicSubscribers.remove(key);
        this.temporaryQueueSubscribers.remove(key);
        this.temporaryTopicSubscribers.remove(key);
        if (this.registeredMBeans.remove(key)) {
            try {
                this.managementContext.unregisterMBean(key);
            }
            catch (Throwable e) {
                LOG.warn("Failed to unregister MBean {}", (Object)key);
                LOG.debug("Failure reason: ", e);
            }
        }
        if ((view = (DurableSubscriptionView)this.durableTopicSubscribers.remove(key)) != null) {
            SubscriptionKey subscriptionKey = new SubscriptionKey(view.getClientId(), view.getSubscriptionName());
            if (addToInactive) {
                SubscriptionInfo info = new SubscriptionInfo();
                info.setClientId(subscriptionKey.getClientId());
                info.setSubscriptionName(subscriptionKey.getSubscriptionName());
                info.setDestination(new ActiveMQTopic(view.getDestinationName()));
                info.setSelector(view.getSelector());
                this.addInactiveSubscription(subscriptionKey, info, this.brokerService.isKeepDurableSubsActive() ? view.subscription : null);
            }
        }
    }

    protected void buildExistingSubscriptions() throws Exception {
        HashMap<SubscriptionKey, SubscriptionInfo> subscriptions = new HashMap<SubscriptionKey, SubscriptionInfo>();
        Set<ActiveMQDestination> destinations = this.destinationFactory.getDestinations();
        if (destinations != null) {
            for (ActiveMQDestination activeMQDestination : destinations) {
                SubscriptionInfo[] infos;
                if (!activeMQDestination.isTopic() || (infos = this.destinationFactory.getAllDurableSubscriptions((ActiveMQTopic)activeMQDestination)) == null) continue;
                for (int i = 0; i < infos.length; ++i) {
                    SubscriptionInfo info = infos[i];
                    SubscriptionKey key = new SubscriptionKey(info);
                    if (this.alreadyKnown(key)) continue;
                    LOG.debug("Restoring durable subscription MBean {}", (Object)info);
                    subscriptions.put(key, info);
                }
            }
        }
        for (Map.Entry entry : subscriptions.entrySet()) {
            this.addInactiveSubscription((SubscriptionKey)entry.getKey(), (SubscriptionInfo)entry.getValue(), null);
        }
    }

    private boolean alreadyKnown(SubscriptionKey key) {
        boolean known = false;
        known = ((TopicRegion)this.getTopicRegion()).durableSubscriptionExists(key);
        LOG.trace("Sub with key: {}, {} already registered", (Object)key, (Object)(known ? "" : "not"));
        return known;
    }

    protected void addInactiveSubscription(SubscriptionKey key, SubscriptionInfo info, Subscription subscription) {
        try {
            ConsumerInfo offlineConsumerInfo = subscription != null ? subscription.getConsumerInfo() : ((TopicRegion)this.getTopicRegion()).createInactiveConsumerInfo(info);
            ObjectName objectName = BrokerMBeanSupport.createSubscriptionName(this.brokerObjectName, info.getClientId(), offlineConsumerInfo);
            InactiveDurableSubscriptionView view = new InactiveDurableSubscriptionView(this, this.brokerService, key.getClientId(), info, subscription);
            try {
                if (AsyncAnnotatedMBean.registerMBean(this.asyncInvokeService, this.mbeanTimeout, this.managementContext, view, objectName) != null) {
                    this.registeredMBeans.add(objectName);
                }
            }
            catch (Throwable e) {
                LOG.warn("Failed to register MBean {}", (Object)key);
                LOG.debug("Failure reason: ", e);
            }
            this.inactiveDurableTopicSubscribers.put(objectName, view);
            this.subscriptionKeys.put(key, objectName);
        }
        catch (Exception e) {
            LOG.error("Failed to register subscription {}", (Object)info, (Object)e);
        }
    }

    public CompositeData[] browse(SubscriptionView view) throws OpenDataException {
        Message[] messages = this.getSubscriberMessages(view);
        CompositeData[] c = new CompositeData[messages.length];
        for (int i = 0; i < c.length; ++i) {
            try {
                c[i] = OpenTypeSupport.convert(messages[i]);
                continue;
            }
            catch (Throwable e) {
                LOG.error("Failed to browse: {}", (Object)view, (Object)e);
            }
        }
        return c;
    }

    public TabularData browseAsTable(SubscriptionView view) throws OpenDataException {
        OpenTypeSupport.OpenTypeFactory factory2 = OpenTypeSupport.getFactory(ActiveMQMessage.class);
        Message[] messages = this.getSubscriberMessages(view);
        CompositeType ct = factory2.getCompositeType();
        TabularType tt = new TabularType("MessageList", "MessageList", ct, new String[]{"JMSMessageID"});
        TabularDataSupport rc = new TabularDataSupport(tt);
        for (int i = 0; i < messages.length; ++i) {
            rc.put(new CompositeDataSupport(ct, factory2.getFields(messages[i])));
        }
        return rc;
    }

    public void remove(SubscriptionView view, String messageId) throws Exception {
        ActiveMQDestination destination = this.getTopicDestination(view);
        if (destination != null) {
            Destination topic = this.getTopicRegion().getDestinationMap().get(destination);
            MessageAck messageAck = new MessageAck();
            messageAck.setMessageID(new MessageId(messageId));
            messageAck.setDestination(destination);
            topic.getMessageStore().removeMessage(this.brokerService.getAdminConnectionContext(), messageAck);
            if (view.subscription instanceof DurableTopicSubscription) {
                DurableTopicSubscription durableTopicSubscription = (DurableTopicSubscription)view.subscription;
                NullMessageReference messageReference = new NullMessageReference();
                messageReference.getMessage().setMessageId(messageAck.getFirstMessageId());
                durableTopicSubscription.getPending().remove(messageReference);
            }
        } else {
            throw new IllegalStateException("can't determine topic for sub:" + view);
        }
    }

    protected Message[] getSubscriberMessages(SubscriptionView view) {
        ActiveMQDestination destination = this.getTopicDestination(view);
        if (destination != null) {
            Destination topic = this.getTopicRegion().getDestinationMap().get(destination);
            return topic.browse();
        }
        LOG.warn("can't determine topic to browse for sub:" + view);
        return new Message[0];
    }

    private ActiveMQDestination getTopicDestination(SubscriptionView view) {
        ActiveMQDestination destination = null;
        if (view.subscription instanceof DurableTopicSubscription) {
            destination = new ActiveMQTopic(view.getDestinationName());
        } else if (view instanceof InactiveDurableSubscriptionView) {
            destination = ((InactiveDurableSubscriptionView)view).subscriptionInfo.getDestination();
        }
        return destination;
    }

    private ObjectName[] onlyNonSuppressed(Set<ObjectName> set) {
        ArrayList<ObjectName> nonSuppressed = new ArrayList<ObjectName>();
        for (ObjectName key : set) {
            if (!this.managementContext.isAllowedToRegister(key)) continue;
            nonSuppressed.add(key);
        }
        return nonSuppressed.toArray(new ObjectName[nonSuppressed.size()]);
    }

    protected ObjectName[] getTopics() {
        Set<ObjectName> set = this.topics.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getTopicsNonSuppressed() {
        return this.onlyNonSuppressed(this.topics.keySet());
    }

    protected ObjectName[] getQueues() {
        Set<ObjectName> set = this.queues.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getQueuesNonSuppressed() {
        return this.onlyNonSuppressed(this.queues.keySet());
    }

    protected ObjectName[] getTemporaryTopics() {
        Set<ObjectName> set = this.temporaryTopics.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getTemporaryTopicsNonSuppressed() {
        return this.onlyNonSuppressed(this.temporaryTopics.keySet());
    }

    protected ObjectName[] getTemporaryQueues() {
        Set<ObjectName> set = this.temporaryQueues.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getTemporaryQueuesNonSuppressed() {
        return this.onlyNonSuppressed(this.temporaryQueues.keySet());
    }

    protected ObjectName[] getTopicSubscribers() {
        Set<ObjectName> set = this.topicSubscribers.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getTopicSubscribersNonSuppressed() {
        return this.onlyNonSuppressed(this.topicSubscribers.keySet());
    }

    protected ObjectName[] getDurableTopicSubscribers() {
        Set<ObjectName> set = this.durableTopicSubscribers.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getDurableTopicSubscribersNonSuppressed() {
        return this.onlyNonSuppressed(this.durableTopicSubscribers.keySet());
    }

    protected ObjectName[] getQueueSubscribers() {
        Set<ObjectName> set = this.queueSubscribers.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getQueueSubscribersNonSuppressed() {
        return this.onlyNonSuppressed(this.queueSubscribers.keySet());
    }

    protected ObjectName[] getTemporaryTopicSubscribers() {
        Set<ObjectName> set = this.temporaryTopicSubscribers.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getTemporaryTopicSubscribersNonSuppressed() {
        return this.onlyNonSuppressed(this.temporaryTopicSubscribers.keySet());
    }

    protected ObjectName[] getTemporaryQueueSubscribers() {
        Set<ObjectName> set = this.temporaryQueueSubscribers.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getTemporaryQueueSubscribersNonSuppressed() {
        return this.onlyNonSuppressed(this.temporaryQueueSubscribers.keySet());
    }

    protected ObjectName[] getInactiveDurableTopicSubscribers() {
        Set<ObjectName> set = this.inactiveDurableTopicSubscribers.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getInactiveDurableTopicSubscribersNonSuppressed() {
        return this.onlyNonSuppressed(this.inactiveDurableTopicSubscribers.keySet());
    }

    protected ObjectName[] getTopicProducers() {
        Set<ObjectName> set = this.topicProducers.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getTopicProducersNonSuppressed() {
        return this.onlyNonSuppressed(this.topicProducers.keySet());
    }

    protected ObjectName[] getQueueProducers() {
        Set<ObjectName> set = this.queueProducers.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getQueueProducersNonSuppressed() {
        return this.onlyNonSuppressed(this.queueProducers.keySet());
    }

    protected ObjectName[] getTemporaryTopicProducers() {
        Set<ObjectName> set = this.temporaryTopicProducers.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getTemporaryTopicProducersNonSuppressed() {
        return this.onlyNonSuppressed(this.temporaryTopicProducers.keySet());
    }

    protected ObjectName[] getTemporaryQueueProducers() {
        Set<ObjectName> set = this.temporaryQueueProducers.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getTemporaryQueueProducersNonSuppressed() {
        return this.onlyNonSuppressed(this.temporaryQueueProducers.keySet());
    }

    protected ObjectName[] getDynamicDestinationProducers() {
        Set<ObjectName> set = this.dynamicDestinationProducers.keySet();
        return set.toArray(new ObjectName[set.size()]);
    }

    protected ObjectName[] getDynamicDestinationProducersNonSuppressed() {
        return this.onlyNonSuppressed(this.dynamicDestinationProducers.keySet());
    }

    public Broker getContextBroker() {
        return this.contextBroker;
    }

    public void setContextBroker(Broker contextBroker) {
        this.contextBroker = contextBroker;
    }

    public ObjectName registerSlowConsumerStrategy(AbortSlowConsumerStrategy strategy) throws MalformedObjectNameException {
        ObjectName objectName = null;
        try {
            objectName = BrokerMBeanSupport.createAbortSlowConsumerStrategyName(this.brokerObjectName, strategy);
            if (!this.registeredMBeans.contains(objectName)) {
                AbortSlowConsumerStrategyView view = null;
                view = strategy instanceof AbortSlowAckConsumerStrategy ? new AbortSlowAckConsumerStrategyView(this, (AbortSlowAckConsumerStrategy)strategy) : new AbortSlowConsumerStrategyView(this, strategy);
                if (AsyncAnnotatedMBean.registerMBean(this.asyncInvokeService, this.mbeanTimeout, this.managementContext, view, objectName) != null) {
                    this.registeredMBeans.add(objectName);
                }
            }
        }
        catch (Exception e) {
            LOG.warn("Failed to register MBean {}", (Object)strategy);
            LOG.debug("Failure reason: ", e);
        }
        return objectName;
    }

    public void registerRecoveredTransactionMBean(XATransaction transaction) {
        try {
            RecoveredXATransactionView view;
            ObjectName objectName = BrokerMBeanSupport.createXATransactionName(this.brokerObjectName, transaction);
            if (!this.registeredMBeans.contains(objectName) && AsyncAnnotatedMBean.registerMBean(this.asyncInvokeService, this.mbeanTimeout, this.managementContext, view = new RecoveredXATransactionView(this, transaction), objectName) != null) {
                this.registeredMBeans.add(objectName);
            }
        }
        catch (Exception e) {
            LOG.warn("Failed to register prepared transaction MBean {}", (Object)transaction);
            LOG.debug("Failure reason: ", e);
        }
    }

    public void unregister(XATransaction transaction) {
        try {
            ObjectName objectName = BrokerMBeanSupport.createXATransactionName(this.brokerObjectName, transaction);
            if (this.registeredMBeans.remove(objectName)) {
                try {
                    this.managementContext.unregisterMBean(objectName);
                }
                catch (Throwable e) {
                    LOG.warn("Failed to unregister MBean {}", (Object)objectName);
                    LOG.debug("Failure reason: ", e);
                }
            }
        }
        catch (Exception e) {
            LOG.warn("Failed to create object name to unregister {}", (Object)transaction, (Object)e);
        }
    }

    public ObjectName getSubscriberObjectName(Subscription key) {
        return this.subscriptionMap.get(key);
    }

    public Subscription getSubscriber(ObjectName key) {
        Subscription sub = null;
        for (Map.Entry<Subscription, ObjectName> entry : this.subscriptionMap.entrySet()) {
            if (!entry.getValue().equals(key)) continue;
            sub = entry.getKey();
            break;
        }
        return sub;
    }

    public Map<ObjectName, DestinationView> getQueueViews() {
        return this.queues;
    }

    public Map<ObjectName, DestinationView> getTopicViews() {
        return this.topics;
    }

    public DestinationView getQueueView(String queueName) throws MalformedObjectNameException {
        ObjectName objName = BrokerMBeanSupport.createDestinationName(this.brokerObjectName.toString(), "Queue", queueName);
        return this.queues.get(objName);
    }

    public Set<ObjectName> getRegisteredMbeans() {
        return this.registeredMBeans;
    }
}

