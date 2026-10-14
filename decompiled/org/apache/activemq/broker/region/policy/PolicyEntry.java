/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.Set;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.region.BaseDestination;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.broker.region.QueueBrowserSubscription;
import org.apache.activemq.broker.region.QueueSubscription;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.TopicSubscription;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;
import org.apache.activemq.broker.region.group.GroupFactoryFinder;
import org.apache.activemq.broker.region.group.MessageGroupMapFactory;
import org.apache.activemq.broker.region.policy.DeadLetterStrategy;
import org.apache.activemq.broker.region.policy.DispatchPolicy;
import org.apache.activemq.broker.region.policy.MessageEvictionStrategy;
import org.apache.activemq.broker.region.policy.PendingDurableSubscriberMessageStoragePolicy;
import org.apache.activemq.broker.region.policy.PendingMessageLimitStrategy;
import org.apache.activemq.broker.region.policy.PendingQueueMessageStoragePolicy;
import org.apache.activemq.broker.region.policy.PendingSubscriberMessageStoragePolicy;
import org.apache.activemq.broker.region.policy.SlowConsumerStrategy;
import org.apache.activemq.broker.region.policy.SubscriptionRecoveryPolicy;
import org.apache.activemq.filter.DestinationMapEntry;
import org.apache.activemq.network.NetworkBridgeFilterFactory;
import org.apache.activemq.usage.SystemUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolicyEntry
extends DestinationMapEntry {
    private static final Logger LOG = LoggerFactory.getLogger(PolicyEntry.class);
    private DispatchPolicy dispatchPolicy;
    private SubscriptionRecoveryPolicy subscriptionRecoveryPolicy;
    private boolean sendAdvisoryIfNoConsumers;
    private DeadLetterStrategy deadLetterStrategy = Destination.DEFAULT_DEAD_LETTER_STRATEGY;
    private PendingMessageLimitStrategy pendingMessageLimitStrategy;
    private MessageEvictionStrategy messageEvictionStrategy;
    private long memoryLimit;
    private String messageGroupMapFactoryType = "cached";
    private MessageGroupMapFactory messageGroupMapFactory;
    private PendingQueueMessageStoragePolicy pendingQueuePolicy;
    private PendingDurableSubscriberMessageStoragePolicy pendingDurableSubscriberPolicy;
    private PendingSubscriberMessageStoragePolicy pendingSubscriberPolicy;
    private int maxProducersToAudit = 64;
    private int maxAuditDepth = 10000;
    private int maxQueueAuditDepth = 10000;
    private boolean enableAudit = true;
    private boolean producerFlowControl = true;
    private boolean alwaysRetroactive = false;
    private long blockedProducerWarningInterval = 30000L;
    private boolean optimizedDispatch = false;
    private int maxPageSize = 200;
    private int maxBrowsePageSize = 400;
    private boolean useCache = true;
    private long minimumMessageSize = 1024L;
    private boolean useConsumerPriority = true;
    private boolean strictOrderDispatch = false;
    private boolean lazyDispatch = false;
    private int timeBeforeDispatchStarts = 0;
    private int consumersBeforeDispatchStarts = 0;
    private boolean advisoryForSlowConsumers;
    private boolean advisoryForFastProducers;
    private boolean advisoryForDiscardingMessages;
    private boolean advisoryWhenFull;
    private boolean advisoryForDelivery;
    private boolean advisoryForConsumed;
    private boolean includeBodyForAdvisory;
    private long expireMessagesPeriod = 30000L;
    private int maxExpirePageSize = 400;
    private int queuePrefetch = 1000;
    private int queueBrowserPrefetch = 500;
    private int topicPrefetch = Short.MAX_VALUE;
    private int durableTopicPrefetch = 100;
    private boolean usePrefetchExtension = true;
    private int cursorMemoryHighWaterMark = 70;
    private int storeUsageHighWaterMark = 100;
    private SlowConsumerStrategy slowConsumerStrategy;
    private boolean prioritizedMessages;
    private boolean allConsumersExclusiveByDefault;
    private boolean gcInactiveDestinations;
    private boolean gcWithNetworkConsumers;
    private long inactiveTimeoutBeforeGC = 60000L;
    private boolean reduceMemoryFootprint;
    private NetworkBridgeFilterFactory networkBridgeFilterFactory;
    private boolean doOptimzeMessageStorage = true;
    private int maxDestinations = -1;
    private boolean useTopicSubscriptionInflightStats = true;
    private int optimizeMessageStoreInFlightLimit = 10;
    private boolean persistJMSRedelivered = false;

    public void configure(Broker broker, Queue queue) {
        this.baseConfiguration(broker, queue);
        if (this.dispatchPolicy != null) {
            queue.setDispatchPolicy(this.dispatchPolicy);
        }
        queue.setDeadLetterStrategy(this.getDeadLetterStrategy());
        queue.setMessageGroupMapFactory(this.getMessageGroupMapFactory());
        if (this.memoryLimit > 0L) {
            queue.getMemoryUsage().setLimit(this.memoryLimit);
        }
        if (this.pendingQueuePolicy != null) {
            PendingMessageCursor messages = this.pendingQueuePolicy.getQueuePendingMessageCursor(broker, queue);
            queue.setMessages(messages);
        }
        queue.setUseConsumerPriority(this.isUseConsumerPriority());
        queue.setStrictOrderDispatch(this.isStrictOrderDispatch());
        queue.setOptimizedDispatch(this.isOptimizedDispatch());
        queue.setLazyDispatch(this.isLazyDispatch());
        queue.setTimeBeforeDispatchStarts(this.getTimeBeforeDispatchStarts());
        queue.setConsumersBeforeDispatchStarts(this.getConsumersBeforeDispatchStarts());
        queue.setAllConsumersExclusiveByDefault(this.isAllConsumersExclusiveByDefault());
        queue.setPersistJMSRedelivered(this.isPersistJMSRedelivered());
    }

    public void update(Queue queue) {
        this.update(queue, null);
    }

    public void update(Queue queue, Set<String> includedProperties) {
        this.baseUpdate(queue, includedProperties);
        if (this.isUpdate("memoryLimit", includedProperties) && this.memoryLimit > 0L) {
            queue.getMemoryUsage().setLimit(this.memoryLimit);
        }
        if (this.isUpdate("useConsumerPriority", includedProperties)) {
            queue.setUseConsumerPriority(this.isUseConsumerPriority());
        }
        if (this.isUpdate("strictOrderDispatch", includedProperties)) {
            queue.setStrictOrderDispatch(this.isStrictOrderDispatch());
        }
        if (this.isUpdate("optimizedDispatch", includedProperties)) {
            queue.setOptimizedDispatch(this.isOptimizedDispatch());
        }
        if (this.isUpdate("lazyDispatch", includedProperties)) {
            queue.setLazyDispatch(this.isLazyDispatch());
        }
        if (this.isUpdate("timeBeforeDispatchStarts", includedProperties)) {
            queue.setTimeBeforeDispatchStarts(this.getTimeBeforeDispatchStarts());
        }
        if (this.isUpdate("consumersBeforeDispatchStarts", includedProperties)) {
            queue.setConsumersBeforeDispatchStarts(this.getConsumersBeforeDispatchStarts());
        }
        if (this.isUpdate("allConsumersExclusiveByDefault", includedProperties)) {
            queue.setAllConsumersExclusiveByDefault(this.isAllConsumersExclusiveByDefault());
        }
        if (this.isUpdate("persistJMSRedelivered", includedProperties)) {
            queue.setPersistJMSRedelivered(this.isPersistJMSRedelivered());
        }
    }

    public void configure(Broker broker, Topic topic) {
        this.baseConfiguration(broker, topic);
        if (this.dispatchPolicy != null) {
            topic.setDispatchPolicy(this.dispatchPolicy);
        }
        topic.setDeadLetterStrategy(this.getDeadLetterStrategy());
        if (this.subscriptionRecoveryPolicy != null) {
            SubscriptionRecoveryPolicy srp = this.subscriptionRecoveryPolicy.copy();
            srp.setBroker(broker);
            topic.setSubscriptionRecoveryPolicy(srp);
        }
        if (this.memoryLimit > 0L) {
            topic.getMemoryUsage().setLimit(this.memoryLimit);
        }
        topic.setLazyDispatch(this.isLazyDispatch());
    }

    public void update(Topic topic) {
        this.update(topic, null);
    }

    public void update(Topic topic, Set<String> includedProperties) {
        this.baseUpdate(topic, includedProperties);
        if (this.isUpdate("memoryLimit", includedProperties) && this.memoryLimit > 0L) {
            topic.getMemoryUsage().setLimit(this.memoryLimit);
        }
        if (this.isUpdate("lazyDispatch", includedProperties)) {
            topic.setLazyDispatch(this.isLazyDispatch());
        }
    }

    public void baseUpdate(BaseDestination destination) {
        this.baseUpdate(destination, null);
    }

    public void baseUpdate(BaseDestination destination, Set<String> includedProperties) {
        if (this.isUpdate("producerFlowControl", includedProperties)) {
            destination.setProducerFlowControl(this.isProducerFlowControl());
        }
        if (this.isUpdate("alwaysRetroactive", includedProperties)) {
            destination.setAlwaysRetroactive(this.isAlwaysRetroactive());
        }
        if (this.isUpdate("blockedProducerWarningInterval", includedProperties)) {
            destination.setBlockedProducerWarningInterval(this.getBlockedProducerWarningInterval());
        }
        if (this.isUpdate("maxPageSize", includedProperties)) {
            destination.setMaxPageSize(this.getMaxPageSize());
        }
        if (this.isUpdate("maxBrowsePageSize", includedProperties)) {
            destination.setMaxBrowsePageSize(this.getMaxBrowsePageSize());
        }
        if (this.isUpdate("minimumMessageSize", includedProperties)) {
            destination.setMinimumMessageSize((int)this.getMinimumMessageSize());
        }
        if (this.isUpdate("maxExpirePageSize", includedProperties)) {
            destination.setMaxExpirePageSize(this.getMaxExpirePageSize());
        }
        if (this.isUpdate("cursorMemoryHighWaterMark", includedProperties)) {
            destination.setCursorMemoryHighWaterMark(this.getCursorMemoryHighWaterMark());
        }
        if (this.isUpdate("storeUsageHighWaterMark", includedProperties)) {
            destination.setStoreUsageHighWaterMark(this.getStoreUsageHighWaterMark());
        }
        if (this.isUpdate("gcInactiveDestinations", includedProperties)) {
            destination.setGcIfInactive(this.isGcInactiveDestinations());
        }
        if (this.isUpdate("gcWithNetworkConsumers", includedProperties)) {
            destination.setGcWithNetworkConsumers(this.isGcWithNetworkConsumers());
        }
        if (this.isUpdate("inactiveTimeoutBeforeGc", includedProperties)) {
            destination.setInactiveTimeoutBeforeGC(this.getInactiveTimeoutBeforeGC());
        }
        if (this.isUpdate("reduceMemoryFootprint", includedProperties)) {
            destination.setReduceMemoryFootprint(this.isReduceMemoryFootprint());
        }
        if (this.isUpdate("doOptimizeMessageStore", includedProperties)) {
            destination.setDoOptimzeMessageStorage(this.isDoOptimzeMessageStorage());
        }
        if (this.isUpdate("optimizeMessageStoreInFlightLimit", includedProperties)) {
            destination.setOptimizeMessageStoreInFlightLimit(this.getOptimizeMessageStoreInFlightLimit());
        }
        if (this.isUpdate("advisoryForConsumed", includedProperties)) {
            destination.setAdvisoryForConsumed(this.isAdvisoryForConsumed());
        }
        if (this.isUpdate("advisoryForDelivery", includedProperties)) {
            destination.setAdvisoryForDelivery(this.isAdvisoryForDelivery());
        }
        if (this.isUpdate("advisoryForDiscardingMessages", includedProperties)) {
            destination.setAdvisoryForDiscardingMessages(this.isAdvisoryForDiscardingMessages());
        }
        if (this.isUpdate("advisoryForSlowConsumers", includedProperties)) {
            destination.setAdvisoryForSlowConsumers(this.isAdvisoryForSlowConsumers());
        }
        if (this.isUpdate("advisoryForFastProducers", includedProperties)) {
            destination.setAdvisoryForFastProducers(this.isAdvisoryForFastProducers());
        }
        if (this.isUpdate("advisoryWhenFull", includedProperties)) {
            destination.setAdvisoryWhenFull(this.isAdvisoryWhenFull());
        }
        if (this.isUpdate("includeBodyForAdvisory", includedProperties)) {
            destination.setIncludeBodyForAdvisory(this.isIncludeBodyForAdvisory());
        }
        if (this.isUpdate("sendAdvisoryIfNoConsumers", includedProperties)) {
            destination.setSendAdvisoryIfNoConsumers(this.isSendAdvisoryIfNoConsumers());
        }
    }

    public void baseConfiguration(Broker broker, BaseDestination destination) {
        this.baseUpdate(destination);
        destination.setEnableAudit(this.isEnableAudit());
        destination.setMaxAuditDepth(this.getMaxQueueAuditDepth());
        destination.setMaxProducersToAudit(this.getMaxProducersToAudit());
        destination.setUseCache(this.isUseCache());
        destination.setExpireMessagesPeriod(this.getExpireMessagesPeriod());
        SlowConsumerStrategy scs = this.getSlowConsumerStrategy();
        if (scs != null) {
            scs.setBrokerService(broker);
            scs.addDestination(destination);
        }
        destination.setSlowConsumerStrategy(scs);
        destination.setPrioritizedMessages(this.isPrioritizedMessages());
    }

    public void configure(Broker broker, SystemUsage memoryManager, TopicSubscription subscription) {
        this.configurePrefetch(subscription);
        subscription.setUsePrefetchExtension(this.isUsePrefetchExtension());
        subscription.setCursorMemoryHighWaterMark(this.getCursorMemoryHighWaterMark());
        subscription.setUseTopicSubscriptionInflightStats(this.isUseTopicSubscriptionInflightStats());
        if (this.pendingMessageLimitStrategy != null) {
            int value = this.pendingMessageLimitStrategy.getMaximumPendingMessageLimit(subscription);
            int consumerLimit = subscription.getInfo().getMaximumPendingMessageLimit();
            if (consumerLimit > 0 && (value < 0 || consumerLimit < value)) {
                value = consumerLimit;
            }
            if (value >= 0) {
                LOG.debug("Setting the maximumPendingMessages size to: {} for consumer: {}", (Object)value, (Object)subscription.getInfo().getConsumerId());
                subscription.setMaximumPendingMessages(value);
            }
        }
        if (this.messageEvictionStrategy != null) {
            subscription.setMessageEvictionStrategy(this.messageEvictionStrategy);
        }
        if (this.pendingSubscriberPolicy != null) {
            String name = subscription.getContext().getClientId() + "_" + subscription.getConsumerInfo().getConsumerId();
            int maxBatchSize = subscription.getConsumerInfo().getPrefetchSize();
            subscription.setMatched(this.pendingSubscriberPolicy.getSubscriberPendingMessageCursor(broker, name, maxBatchSize, subscription));
        }
        if (this.enableAudit) {
            subscription.setEnableAudit(this.enableAudit);
            subscription.setMaxProducersToAudit(this.maxProducersToAudit);
            subscription.setMaxAuditDepth(this.maxAuditDepth);
        }
    }

    public void configure(Broker broker, SystemUsage memoryManager, DurableTopicSubscription sub) {
        int auditDepth;
        String clientId = sub.getSubscriptionKey().getClientId();
        String subName = sub.getSubscriptionKey().getSubscriptionName();
        sub.setCursorMemoryHighWaterMark(this.getCursorMemoryHighWaterMark());
        this.configurePrefetch(sub);
        if (this.pendingDurableSubscriberPolicy != null) {
            PendingMessageCursor cursor = this.pendingDurableSubscriberPolicy.getSubscriberPendingMessageCursor(broker, clientId, subName, sub.getPrefetchSize(), sub);
            cursor.setSystemUsage(memoryManager);
            sub.setPending(cursor);
        }
        if ((auditDepth = this.getMaxAuditDepth()) == 10000 && this.isPrioritizedMessages()) {
            sub.setMaxAuditDepth(auditDepth * 10);
        } else {
            sub.setMaxAuditDepth(auditDepth);
        }
        sub.setMaxProducersToAudit(this.getMaxProducersToAudit());
        sub.setUsePrefetchExtension(this.isUsePrefetchExtension());
    }

    public void configure(Broker broker, SystemUsage memoryManager, QueueBrowserSubscription sub) {
        this.configurePrefetch(sub);
        sub.setCursorMemoryHighWaterMark(this.getCursorMemoryHighWaterMark());
        sub.setUsePrefetchExtension(this.isUsePrefetchExtension());
        sub.setMaxProducersToAudit(Integer.MAX_VALUE);
        sub.setMaxAuditDepth(Short.MAX_VALUE);
        sub.setMaxMessages(this.getMaxBrowsePageSize());
    }

    public void configure(Broker broker, SystemUsage memoryManager, QueueSubscription sub) {
        this.configurePrefetch(sub);
        sub.setCursorMemoryHighWaterMark(this.getCursorMemoryHighWaterMark());
        sub.setUsePrefetchExtension(this.isUsePrefetchExtension());
        sub.setMaxProducersToAudit(this.getMaxProducersToAudit());
    }

    public void configurePrefetch(Subscription subscription) {
        int currentPrefetch = subscription.getConsumerInfo().getPrefetchSize();
        if (subscription instanceof QueueBrowserSubscription) {
            if (currentPrefetch == 500) {
                ((QueueBrowserSubscription)subscription).setPrefetchSize(this.getQueueBrowserPrefetch());
            }
        } else if (subscription instanceof QueueSubscription) {
            if (currentPrefetch == 1000) {
                ((QueueSubscription)subscription).setPrefetchSize(this.getQueuePrefetch());
            }
        } else if (subscription instanceof DurableTopicSubscription) {
            if (currentPrefetch == 100 || subscription.getConsumerInfo().getPrefetchSize() == 1000) {
                ((DurableTopicSubscription)subscription).setPrefetchSize(this.getDurableTopicPrefetch());
            }
        } else if (subscription instanceof TopicSubscription && currentPrefetch == Short.MAX_VALUE) {
            ((TopicSubscription)subscription).setPrefetchSize(this.getTopicPrefetch());
        }
        if (currentPrefetch != 0 && subscription.getPrefetchSize() == 0) {
            subscription.updateConsumerPrefetch(0);
        }
    }

    private boolean isUpdate(String property, Set<String> includedProperties) {
        return includedProperties == null || includedProperties.contains(property);
    }

    public DispatchPolicy getDispatchPolicy() {
        return this.dispatchPolicy;
    }

    public void setDispatchPolicy(DispatchPolicy policy) {
        this.dispatchPolicy = policy;
    }

    public SubscriptionRecoveryPolicy getSubscriptionRecoveryPolicy() {
        return this.subscriptionRecoveryPolicy;
    }

    public void setSubscriptionRecoveryPolicy(SubscriptionRecoveryPolicy subscriptionRecoveryPolicy) {
        this.subscriptionRecoveryPolicy = subscriptionRecoveryPolicy;
    }

    public boolean isSendAdvisoryIfNoConsumers() {
        return this.sendAdvisoryIfNoConsumers;
    }

    public void setSendAdvisoryIfNoConsumers(boolean sendAdvisoryIfNoConsumers) {
        this.sendAdvisoryIfNoConsumers = sendAdvisoryIfNoConsumers;
    }

    public DeadLetterStrategy getDeadLetterStrategy() {
        return this.deadLetterStrategy;
    }

    public void setDeadLetterStrategy(DeadLetterStrategy deadLetterStrategy) {
        this.deadLetterStrategy = deadLetterStrategy;
    }

    public PendingMessageLimitStrategy getPendingMessageLimitStrategy() {
        return this.pendingMessageLimitStrategy;
    }

    public void setPendingMessageLimitStrategy(PendingMessageLimitStrategy pendingMessageLimitStrategy) {
        this.pendingMessageLimitStrategy = pendingMessageLimitStrategy;
    }

    public MessageEvictionStrategy getMessageEvictionStrategy() {
        return this.messageEvictionStrategy;
    }

    public void setMessageEvictionStrategy(MessageEvictionStrategy messageEvictionStrategy) {
        this.messageEvictionStrategy = messageEvictionStrategy;
    }

    public long getMemoryLimit() {
        return this.memoryLimit;
    }

    public void setMemoryLimit(long memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public MessageGroupMapFactory getMessageGroupMapFactory() {
        if (this.messageGroupMapFactory == null) {
            try {
                this.messageGroupMapFactory = GroupFactoryFinder.createMessageGroupMapFactory(this.getMessageGroupMapFactoryType());
            }
            catch (Exception e) {
                LOG.error("Failed to create message group Factory ", e);
            }
        }
        return this.messageGroupMapFactory;
    }

    public void setMessageGroupMapFactory(MessageGroupMapFactory messageGroupMapFactory) {
        this.messageGroupMapFactory = messageGroupMapFactory;
    }

    public String getMessageGroupMapFactoryType() {
        return this.messageGroupMapFactoryType;
    }

    public void setMessageGroupMapFactoryType(String messageGroupMapFactoryType) {
        this.messageGroupMapFactoryType = messageGroupMapFactoryType;
    }

    public PendingDurableSubscriberMessageStoragePolicy getPendingDurableSubscriberPolicy() {
        return this.pendingDurableSubscriberPolicy;
    }

    public void setPendingDurableSubscriberPolicy(PendingDurableSubscriberMessageStoragePolicy pendingDurableSubscriberPolicy) {
        this.pendingDurableSubscriberPolicy = pendingDurableSubscriberPolicy;
    }

    public PendingQueueMessageStoragePolicy getPendingQueuePolicy() {
        return this.pendingQueuePolicy;
    }

    public void setPendingQueuePolicy(PendingQueueMessageStoragePolicy pendingQueuePolicy) {
        this.pendingQueuePolicy = pendingQueuePolicy;
    }

    public PendingSubscriberMessageStoragePolicy getPendingSubscriberPolicy() {
        return this.pendingSubscriberPolicy;
    }

    public void setPendingSubscriberPolicy(PendingSubscriberMessageStoragePolicy pendingSubscriberPolicy) {
        this.pendingSubscriberPolicy = pendingSubscriberPolicy;
    }

    public boolean isProducerFlowControl() {
        return this.producerFlowControl;
    }

    public void setProducerFlowControl(boolean producerFlowControl) {
        this.producerFlowControl = producerFlowControl;
    }

    public boolean isAlwaysRetroactive() {
        return this.alwaysRetroactive;
    }

    public void setAlwaysRetroactive(boolean alwaysRetroactive) {
        this.alwaysRetroactive = alwaysRetroactive;
    }

    public void setBlockedProducerWarningInterval(long blockedProducerWarningInterval) {
        this.blockedProducerWarningInterval = blockedProducerWarningInterval;
    }

    public long getBlockedProducerWarningInterval() {
        return this.blockedProducerWarningInterval;
    }

    public int getMaxProducersToAudit() {
        return this.maxProducersToAudit;
    }

    public void setMaxProducersToAudit(int maxProducersToAudit) {
        this.maxProducersToAudit = maxProducersToAudit;
    }

    public int getMaxAuditDepth() {
        return this.maxAuditDepth;
    }

    public void setMaxAuditDepth(int maxAuditDepth) {
        this.maxAuditDepth = maxAuditDepth;
    }

    public boolean isEnableAudit() {
        return this.enableAudit;
    }

    public void setEnableAudit(boolean enableAudit) {
        this.enableAudit = enableAudit;
    }

    public int getMaxQueueAuditDepth() {
        return this.maxQueueAuditDepth;
    }

    public void setMaxQueueAuditDepth(int maxQueueAuditDepth) {
        this.maxQueueAuditDepth = maxQueueAuditDepth;
    }

    public boolean isOptimizedDispatch() {
        return this.optimizedDispatch;
    }

    public void setOptimizedDispatch(boolean optimizedDispatch) {
        this.optimizedDispatch = optimizedDispatch;
    }

    public int getMaxPageSize() {
        return this.maxPageSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public int getMaxBrowsePageSize() {
        return this.maxBrowsePageSize;
    }

    public void setMaxBrowsePageSize(int maxPageSize) {
        this.maxBrowsePageSize = maxPageSize;
    }

    public boolean isUseCache() {
        return this.useCache;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    public long getMinimumMessageSize() {
        return this.minimumMessageSize;
    }

    public void setMinimumMessageSize(long minimumMessageSize) {
        this.minimumMessageSize = minimumMessageSize;
    }

    public boolean isUseConsumerPriority() {
        return this.useConsumerPriority;
    }

    public void setUseConsumerPriority(boolean useConsumerPriority) {
        this.useConsumerPriority = useConsumerPriority;
    }

    public boolean isStrictOrderDispatch() {
        return this.strictOrderDispatch;
    }

    public void setStrictOrderDispatch(boolean strictOrderDispatch) {
        this.strictOrderDispatch = strictOrderDispatch;
    }

    public boolean isLazyDispatch() {
        return this.lazyDispatch;
    }

    public void setLazyDispatch(boolean lazyDispatch) {
        this.lazyDispatch = lazyDispatch;
    }

    public int getTimeBeforeDispatchStarts() {
        return this.timeBeforeDispatchStarts;
    }

    public void setTimeBeforeDispatchStarts(int timeBeforeDispatchStarts) {
        this.timeBeforeDispatchStarts = timeBeforeDispatchStarts;
    }

    public int getConsumersBeforeDispatchStarts() {
        return this.consumersBeforeDispatchStarts;
    }

    public void setConsumersBeforeDispatchStarts(int consumersBeforeDispatchStarts) {
        this.consumersBeforeDispatchStarts = consumersBeforeDispatchStarts;
    }

    public boolean isAdvisoryForSlowConsumers() {
        return this.advisoryForSlowConsumers;
    }

    public void setAdvisoryForSlowConsumers(boolean advisoryForSlowConsumers) {
        this.advisoryForSlowConsumers = advisoryForSlowConsumers;
    }

    public boolean isAdvisoryForDiscardingMessages() {
        return this.advisoryForDiscardingMessages;
    }

    public void setAdvisoryForDiscardingMessages(boolean advisoryForDiscardingMessages) {
        this.advisoryForDiscardingMessages = advisoryForDiscardingMessages;
    }

    public boolean isAdvisoryWhenFull() {
        return this.advisoryWhenFull;
    }

    public void setAdvisoryWhenFull(boolean advisoryWhenFull) {
        this.advisoryWhenFull = advisoryWhenFull;
    }

    public boolean isAdvisoryForDelivery() {
        return this.advisoryForDelivery;
    }

    public void setAdvisoryForDelivery(boolean advisoryForDelivery) {
        this.advisoryForDelivery = advisoryForDelivery;
    }

    public boolean isAdvisoryForConsumed() {
        return this.advisoryForConsumed;
    }

    public void setAdvisoryForConsumed(boolean advisoryForConsumed) {
        this.advisoryForConsumed = advisoryForConsumed;
    }

    public boolean isAdvisoryForFastProducers() {
        return this.advisoryForFastProducers;
    }

    public void setAdvisoryForFastProducers(boolean advisoryForFastProducers) {
        this.advisoryForFastProducers = advisoryForFastProducers;
    }

    public boolean isIncludeBodyForAdvisory() {
        return this.includeBodyForAdvisory;
    }

    public void setIncludeBodyForAdvisory(boolean includeBodyForAdvisory) {
        this.includeBodyForAdvisory = includeBodyForAdvisory;
    }

    public void setMaxExpirePageSize(int maxExpirePageSize) {
        this.maxExpirePageSize = maxExpirePageSize;
    }

    public int getMaxExpirePageSize() {
        return this.maxExpirePageSize;
    }

    public void setExpireMessagesPeriod(long expireMessagesPeriod) {
        this.expireMessagesPeriod = expireMessagesPeriod;
    }

    public long getExpireMessagesPeriod() {
        return this.expireMessagesPeriod;
    }

    public int getQueuePrefetch() {
        return this.queuePrefetch;
    }

    public void setQueuePrefetch(int queuePrefetch) {
        this.queuePrefetch = queuePrefetch;
    }

    public int getQueueBrowserPrefetch() {
        return this.queueBrowserPrefetch;
    }

    public void setQueueBrowserPrefetch(int queueBrowserPrefetch) {
        this.queueBrowserPrefetch = queueBrowserPrefetch;
    }

    public int getTopicPrefetch() {
        return this.topicPrefetch;
    }

    public void setTopicPrefetch(int topicPrefetch) {
        this.topicPrefetch = topicPrefetch;
    }

    public int getDurableTopicPrefetch() {
        return this.durableTopicPrefetch;
    }

    public void setDurableTopicPrefetch(int durableTopicPrefetch) {
        this.durableTopicPrefetch = durableTopicPrefetch;
    }

    public boolean isUsePrefetchExtension() {
        return this.usePrefetchExtension;
    }

    public void setUsePrefetchExtension(boolean usePrefetchExtension) {
        this.usePrefetchExtension = usePrefetchExtension;
    }

    public int getCursorMemoryHighWaterMark() {
        return this.cursorMemoryHighWaterMark;
    }

    public void setCursorMemoryHighWaterMark(int cursorMemoryHighWaterMark) {
        this.cursorMemoryHighWaterMark = cursorMemoryHighWaterMark;
    }

    public void setStoreUsageHighWaterMark(int storeUsageHighWaterMark) {
        this.storeUsageHighWaterMark = storeUsageHighWaterMark;
    }

    public int getStoreUsageHighWaterMark() {
        return this.storeUsageHighWaterMark;
    }

    public void setSlowConsumerStrategy(SlowConsumerStrategy slowConsumerStrategy) {
        this.slowConsumerStrategy = slowConsumerStrategy;
    }

    public SlowConsumerStrategy getSlowConsumerStrategy() {
        return this.slowConsumerStrategy;
    }

    public boolean isPrioritizedMessages() {
        return this.prioritizedMessages;
    }

    public void setPrioritizedMessages(boolean prioritizedMessages) {
        this.prioritizedMessages = prioritizedMessages;
    }

    public void setAllConsumersExclusiveByDefault(boolean allConsumersExclusiveByDefault) {
        this.allConsumersExclusiveByDefault = allConsumersExclusiveByDefault;
    }

    public boolean isAllConsumersExclusiveByDefault() {
        return this.allConsumersExclusiveByDefault;
    }

    public boolean isGcInactiveDestinations() {
        return this.gcInactiveDestinations;
    }

    public void setGcInactiveDestinations(boolean gcInactiveDestinations) {
        this.gcInactiveDestinations = gcInactiveDestinations;
    }

    @Deprecated
    public long getInactiveTimoutBeforeGC() {
        return this.getInactiveTimeoutBeforeGC();
    }

    @Deprecated
    public void setInactiveTimoutBeforeGC(long inactiveTimoutBeforeGC) {
        this.setInactiveTimeoutBeforeGC(inactiveTimoutBeforeGC);
    }

    public long getInactiveTimeoutBeforeGC() {
        return this.inactiveTimeoutBeforeGC;
    }

    public void setInactiveTimeoutBeforeGC(long inactiveTimeoutBeforeGC) {
        this.inactiveTimeoutBeforeGC = inactiveTimeoutBeforeGC;
    }

    public void setGcWithNetworkConsumers(boolean gcWithNetworkConsumers) {
        this.gcWithNetworkConsumers = gcWithNetworkConsumers;
    }

    public boolean isGcWithNetworkConsumers() {
        return this.gcWithNetworkConsumers;
    }

    public boolean isReduceMemoryFootprint() {
        return this.reduceMemoryFootprint;
    }

    public void setReduceMemoryFootprint(boolean reduceMemoryFootprint) {
        this.reduceMemoryFootprint = reduceMemoryFootprint;
    }

    public void setNetworkBridgeFilterFactory(NetworkBridgeFilterFactory networkBridgeFilterFactory) {
        this.networkBridgeFilterFactory = networkBridgeFilterFactory;
    }

    public NetworkBridgeFilterFactory getNetworkBridgeFilterFactory() {
        return this.networkBridgeFilterFactory;
    }

    public boolean isDoOptimzeMessageStorage() {
        return this.doOptimzeMessageStorage;
    }

    public void setDoOptimzeMessageStorage(boolean doOptimzeMessageStorage) {
        this.doOptimzeMessageStorage = doOptimzeMessageStorage;
    }

    public int getOptimizeMessageStoreInFlightLimit() {
        return this.optimizeMessageStoreInFlightLimit;
    }

    public void setOptimizeMessageStoreInFlightLimit(int optimizeMessageStoreInFlightLimit) {
        this.optimizeMessageStoreInFlightLimit = optimizeMessageStoreInFlightLimit;
    }

    public void setPersistJMSRedelivered(boolean val) {
        this.persistJMSRedelivered = val;
    }

    public boolean isPersistJMSRedelivered() {
        return this.persistJMSRedelivered;
    }

    public int getMaxDestinations() {
        return this.maxDestinations;
    }

    public void setMaxDestinations(int maxDestinations) {
        this.maxDestinations = maxDestinations;
    }

    public String toString() {
        return "PolicyEntry [" + this.destination + "]";
    }

    public boolean isUseTopicSubscriptionInflightStats() {
        return this.useTopicSubscriptionInflightStats;
    }

    public void setUseTopicSubscriptionInflightStats(boolean useTopicSubscriptionInflightStats) {
        this.useTopicSubscriptionInflightStats = useTopicSubscriptionInflightStats;
    }
}

