/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.jms.ResourceAllocationException;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.DeadLetterStrategy;
import org.apache.activemq.broker.region.policy.SlowConsumerStrategy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.security.SecurityContext;
import org.apache.activemq.state.ProducerState;
import org.apache.activemq.store.MessageStore;
import org.apache.activemq.thread.Scheduler;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.usage.TempUsage;
import org.apache.activemq.usage.Usage;
import org.slf4j.Logger;

public abstract class BaseDestination
implements Destination {
    public static final int MAX_PAGE_SIZE = 200;
    public static final int MAX_BROWSE_PAGE_SIZE = 400;
    public static final long EXPIRE_MESSAGE_PERIOD = 30000L;
    public static final long DEFAULT_INACTIVE_TIMEOUT_BEFORE_GC = 60000L;
    public static final int MAX_PRODUCERS_TO_AUDIT = 64;
    public static final int MAX_AUDIT_DEPTH = 10000;
    protected final AtomicBoolean started = new AtomicBoolean();
    protected final ActiveMQDestination destination;
    protected final Broker broker;
    protected final MessageStore store;
    protected SystemUsage systemUsage;
    protected MemoryUsage memoryUsage;
    private boolean producerFlowControl = true;
    private boolean alwaysRetroactive = false;
    protected long lastBlockedProducerWarnTime = 0L;
    protected long blockedProducerWarningInterval = 30000L;
    private int maxProducersToAudit = 1024;
    private int maxAuditDepth = 2048;
    private boolean enableAudit = true;
    private int maxPageSize = 200;
    private int maxBrowsePageSize = 400;
    private boolean useCache = true;
    private int minimumMessageSize = 1024;
    private boolean lazyDispatch = false;
    private boolean advisoryForSlowConsumers;
    private boolean advisoryForFastProducers;
    private boolean advisoryForDiscardingMessages;
    private boolean advisoryWhenFull;
    private boolean advisoryForDelivery;
    private boolean advisoryForConsumed;
    private boolean sendAdvisoryIfNoConsumers;
    private boolean includeBodyForAdvisory;
    protected final DestinationStatistics destinationStatistics = new DestinationStatistics();
    protected final BrokerService brokerService;
    protected final Broker regionBroker;
    protected DeadLetterStrategy deadLetterStrategy = DEFAULT_DEAD_LETTER_STRATEGY;
    protected long expireMessagesPeriod = 30000L;
    private int maxExpirePageSize = 400;
    protected int cursorMemoryHighWaterMark = 70;
    protected int storeUsageHighWaterMark = 100;
    private SlowConsumerStrategy slowConsumerStrategy;
    private boolean prioritizedMessages;
    private long inactiveTimeoutBeforeGC = 60000L;
    private boolean gcIfInactive;
    private boolean gcWithNetworkConsumers;
    private long lastActiveTime = 0L;
    private boolean reduceMemoryFootprint = false;
    protected final Scheduler scheduler;
    private boolean disposed = false;
    private boolean doOptimzeMessageStorage = true;
    private int optimizeMessageStoreInFlightLimit = 10;
    private boolean persistJMSRedelivered;

    public BaseDestination(BrokerService brokerService, MessageStore store, ActiveMQDestination destination, DestinationStatistics parentStats) throws Exception {
        this.brokerService = brokerService;
        this.broker = brokerService.getBroker();
        this.store = store;
        this.destination = destination;
        this.destinationStatistics.setEnabled(parentStats.isEnabled());
        this.destinationStatistics.setParent(parentStats);
        this.systemUsage = new SystemUsage(brokerService.getProducerSystemUsage(), destination.toString());
        this.memoryUsage = this.systemUsage.getMemoryUsage();
        this.memoryUsage.setUsagePortion(1.0f);
        this.regionBroker = brokerService.getRegionBroker();
        this.scheduler = brokerService.getBroker().getScheduler();
    }

    public void initialize() throws Exception {
        if (this.store != null) {
            this.store.setMemoryUsage(this.memoryUsage);
        }
    }

    @Override
    public boolean isProducerFlowControl() {
        return this.producerFlowControl;
    }

    @Override
    public void setProducerFlowControl(boolean producerFlowControl) {
        this.producerFlowControl = producerFlowControl;
    }

    @Override
    public boolean isAlwaysRetroactive() {
        return this.alwaysRetroactive;
    }

    @Override
    public void setAlwaysRetroactive(boolean alwaysRetroactive) {
        this.alwaysRetroactive = alwaysRetroactive;
    }

    @Override
    public void setBlockedProducerWarningInterval(long blockedProducerWarningInterval) {
        this.blockedProducerWarningInterval = blockedProducerWarningInterval;
    }

    @Override
    public long getBlockedProducerWarningInterval() {
        return this.blockedProducerWarningInterval;
    }

    @Override
    public int getMaxProducersToAudit() {
        return this.maxProducersToAudit;
    }

    @Override
    public void setMaxProducersToAudit(int maxProducersToAudit) {
        this.maxProducersToAudit = maxProducersToAudit;
    }

    @Override
    public int getMaxAuditDepth() {
        return this.maxAuditDepth;
    }

    @Override
    public void setMaxAuditDepth(int maxAuditDepth) {
        this.maxAuditDepth = maxAuditDepth;
    }

    @Override
    public boolean isEnableAudit() {
        return this.enableAudit;
    }

    @Override
    public void setEnableAudit(boolean enableAudit) {
        this.enableAudit = enableAudit;
    }

    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        this.destinationStatistics.getProducers().increment();
        this.lastActiveTime = 0L;
    }

    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        this.destinationStatistics.getProducers().decrement();
    }

    @Override
    public void addSubscription(ConnectionContext context, Subscription sub) throws Exception {
        this.destinationStatistics.getConsumers().increment();
        this.lastActiveTime = 0L;
    }

    @Override
    public void removeSubscription(ConnectionContext context, Subscription sub, long lastDeliveredSequenceId) throws Exception {
        this.destinationStatistics.getConsumers().decrement();
        this.lastActiveTime = 0L;
    }

    @Override
    public final MemoryUsage getMemoryUsage() {
        return this.memoryUsage;
    }

    @Override
    public void setMemoryUsage(MemoryUsage memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    @Override
    public TempUsage getTempUsage() {
        return this.systemUsage.getTempUsage();
    }

    @Override
    public DestinationStatistics getDestinationStatistics() {
        return this.destinationStatistics;
    }

    @Override
    public ActiveMQDestination getActiveMQDestination() {
        return this.destination;
    }

    @Override
    public final String getName() {
        return this.getActiveMQDestination().getPhysicalName();
    }

    @Override
    public final MessageStore getMessageStore() {
        return this.store;
    }

    @Override
    public boolean isActive() {
        boolean isActive;
        boolean bl = isActive = this.destinationStatistics.getConsumers().getCount() > 0L || this.destinationStatistics.getProducers().getCount() > 0L;
        if (isActive && this.isGcWithNetworkConsumers() && this.destinationStatistics.getConsumers().getCount() > 0L) {
            isActive = this.hasRegularConsumers(this.getConsumers());
        }
        return isActive;
    }

    @Override
    public int getMaxPageSize() {
        return this.maxPageSize;
    }

    @Override
    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    @Override
    public int getMaxBrowsePageSize() {
        return this.maxBrowsePageSize;
    }

    @Override
    public void setMaxBrowsePageSize(int maxPageSize) {
        this.maxBrowsePageSize = maxPageSize;
    }

    public int getMaxExpirePageSize() {
        return this.maxExpirePageSize;
    }

    public void setMaxExpirePageSize(int maxPageSize) {
        this.maxExpirePageSize = maxPageSize;
    }

    public void setExpireMessagesPeriod(long expireMessagesPeriod) {
        this.expireMessagesPeriod = expireMessagesPeriod;
    }

    public long getExpireMessagesPeriod() {
        return this.expireMessagesPeriod;
    }

    @Override
    public boolean isUseCache() {
        return this.useCache;
    }

    @Override
    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    @Override
    public int getMinimumMessageSize() {
        return this.minimumMessageSize;
    }

    @Override
    public void setMinimumMessageSize(int minimumMessageSize) {
        this.minimumMessageSize = minimumMessageSize;
    }

    @Override
    public boolean isLazyDispatch() {
        return this.lazyDispatch;
    }

    @Override
    public void setLazyDispatch(boolean lazyDispatch) {
        this.lazyDispatch = lazyDispatch;
    }

    protected long getDestinationSequenceId() {
        return this.regionBroker.getBrokerSequenceId();
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

    public boolean isSendAdvisoryIfNoConsumers() {
        return this.sendAdvisoryIfNoConsumers;
    }

    public void setSendAdvisoryIfNoConsumers(boolean sendAdvisoryIfNoConsumers) {
        this.sendAdvisoryIfNoConsumers = sendAdvisoryIfNoConsumers;
    }

    public boolean isIncludeBodyForAdvisory() {
        return this.includeBodyForAdvisory;
    }

    public void setIncludeBodyForAdvisory(boolean includeBodyForAdvisory) {
        this.includeBodyForAdvisory = includeBodyForAdvisory;
    }

    @Override
    public DeadLetterStrategy getDeadLetterStrategy() {
        return this.deadLetterStrategy;
    }

    public void setDeadLetterStrategy(DeadLetterStrategy deadLetterStrategy) {
        this.deadLetterStrategy = deadLetterStrategy;
    }

    @Override
    public int getCursorMemoryHighWaterMark() {
        return this.cursorMemoryHighWaterMark;
    }

    @Override
    public void setCursorMemoryHighWaterMark(int cursorMemoryHighWaterMark) {
        this.cursorMemoryHighWaterMark = cursorMemoryHighWaterMark;
    }

    @Override
    public void messageConsumed(ConnectionContext context, MessageReference messageReference) {
        if (this.advisoryForConsumed) {
            this.broker.messageConsumed(context, messageReference);
        }
    }

    @Override
    public void messageDelivered(ConnectionContext context, MessageReference messageReference) {
        this.lastActiveTime = 0L;
        if (this.advisoryForDelivery) {
            this.broker.messageDelivered(context, messageReference);
        }
    }

    @Override
    public void messageDiscarded(ConnectionContext context, Subscription sub, MessageReference messageReference) {
        if (this.advisoryForDiscardingMessages) {
            this.broker.messageDiscarded(context, sub, messageReference);
        }
    }

    @Override
    public void slowConsumer(ConnectionContext context, Subscription subs) {
        if (this.advisoryForSlowConsumers) {
            this.broker.slowConsumer(context, this, subs);
        }
        if (this.slowConsumerStrategy != null) {
            this.slowConsumerStrategy.slowConsumer(context, subs);
        }
    }

    @Override
    public void fastProducer(ConnectionContext context, ProducerInfo producerInfo) {
        if (this.advisoryForFastProducers) {
            this.broker.fastProducer(context, producerInfo, this.getActiveMQDestination());
        }
    }

    @Override
    public void isFull(ConnectionContext context, Usage<?> usage) {
        if (this.advisoryWhenFull) {
            this.broker.isFull(context, this, usage);
        }
    }

    @Override
    public void dispose(ConnectionContext context) throws IOException {
        if (this.store != null) {
            this.store.removeAllMessages(context);
            this.store.dispose(context);
        }
        this.destinationStatistics.setParent(null);
        this.memoryUsage.stop();
        this.disposed = true;
    }

    @Override
    public boolean isDisposed() {
        return this.disposed;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void onMessageWithNoConsumers(ConnectionContext context, Message msg) throws Exception {
        if (!msg.isPersistent() && this.isSendAdvisoryIfNoConsumers() && (this.destination.isQueue() || !AdvisorySupport.isAdvisoryTopic(this.destination))) {
            Message message = msg.copy();
            if (message.getOriginalDestination() != null) {
                message.setOriginalDestination(message.getDestination());
            }
            if (message.getOriginalTransactionId() != null) {
                message.setOriginalTransactionId(message.getTransactionId());
            }
            ActiveMQTopic advisoryTopic = this.destination.isQueue() ? AdvisorySupport.getNoQueueConsumersAdvisoryTopic(this.destination) : AdvisorySupport.getNoTopicConsumersAdvisoryTopic(this.destination);
            message.setDestination(advisoryTopic);
            message.setTransactionId(null);
            boolean originalFlowControl = context.isProducerFlowControl();
            try {
                context.setProducerFlowControl(false);
                ProducerBrokerExchange producerExchange = new ProducerBrokerExchange();
                producerExchange.setMutable(false);
                producerExchange.setConnectionContext(context);
                producerExchange.setProducerState(new ProducerState(new ProducerInfo()));
                context.getBroker().send(producerExchange, message);
            }
            finally {
                context.setProducerFlowControl(originalFlowControl);
            }
        }
    }

    @Override
    public void processDispatchNotification(MessageDispatchNotification messageDispatchNotification) throws Exception {
    }

    public final int getStoreUsageHighWaterMark() {
        return this.storeUsageHighWaterMark;
    }

    public void setStoreUsageHighWaterMark(int storeUsageHighWaterMark) {
        this.storeUsageHighWaterMark = storeUsageHighWaterMark;
    }

    protected final void waitForSpace(ConnectionContext context, ProducerBrokerExchange producerBrokerExchange, Usage<?> usage, String warning) throws IOException, InterruptedException, ResourceAllocationException {
        this.waitForSpace(context, producerBrokerExchange, usage, 100, warning);
    }

    protected final void waitForSpace(ConnectionContext context, ProducerBrokerExchange producerBrokerExchange, Usage<?> usage, int highWaterMark, String warning) throws IOException, InterruptedException, ResourceAllocationException {
        if (!context.isNetworkConnection() && this.systemUsage.isSendFailIfNoSpace()) {
            this.getLog().debug("sendFailIfNoSpace, forcing exception on send, usage: {}: {}", (Object)usage, (Object)warning);
            throw new ResourceAllocationException(warning);
        }
        if (!context.isNetworkConnection() && this.systemUsage.getSendFailIfNoSpaceAfterTimeout() != 0L) {
            if (!usage.waitForSpace(this.systemUsage.getSendFailIfNoSpaceAfterTimeout(), highWaterMark)) {
                this.getLog().debug("sendFailIfNoSpaceAfterTimeout expired, forcing exception on send, usage: {}: {}", (Object)usage, (Object)warning);
                throw new ResourceAllocationException(warning);
            }
        } else {
            long start2 = System.currentTimeMillis();
            producerBrokerExchange.blockingOnFlowControl(true);
            this.destinationStatistics.getBlockedSends().increment();
            while (!usage.waitForSpace(1000L, highWaterMark)) {
                if (context.getStopping().get()) {
                    throw new IOException("Connection closed, send aborted.");
                }
                if (!this.isFlowControlLogRequired()) continue;
                this.getLog().info("{}: {} (blocking for: {}s)", usage, warning, new Long((System.currentTimeMillis() - start2) / 1000L));
            }
            long finish = System.currentTimeMillis();
            long totalTimeBlocked = finish - start2;
            this.destinationStatistics.getBlockedTime().addTime(totalTimeBlocked);
            producerBrokerExchange.incrementTimeBlocked(this, totalTimeBlocked);
            producerBrokerExchange.blockingOnFlowControl(false);
        }
    }

    protected boolean isFlowControlLogRequired() {
        long now;
        boolean answer = false;
        if (this.blockedProducerWarningInterval > 0L && this.lastBlockedProducerWarnTime + this.blockedProducerWarningInterval <= (now = System.currentTimeMillis())) {
            this.lastBlockedProducerWarnTime = now;
            answer = true;
        }
        return answer;
    }

    protected abstract Logger getLog();

    public void setSlowConsumerStrategy(SlowConsumerStrategy slowConsumerStrategy) {
        this.slowConsumerStrategy = slowConsumerStrategy;
    }

    @Override
    public SlowConsumerStrategy getSlowConsumerStrategy() {
        return this.slowConsumerStrategy;
    }

    @Override
    public boolean isPrioritizedMessages() {
        return this.prioritizedMessages;
    }

    public void setPrioritizedMessages(boolean prioritizedMessages) {
        this.prioritizedMessages = prioritizedMessages;
        if (this.store != null) {
            this.store.setPrioritizedMessages(prioritizedMessages);
        }
    }

    @Override
    public long getInactiveTimeoutBeforeGC() {
        return this.inactiveTimeoutBeforeGC;
    }

    public void setInactiveTimeoutBeforeGC(long inactiveTimeoutBeforeGC) {
        this.inactiveTimeoutBeforeGC = inactiveTimeoutBeforeGC;
    }

    public boolean isGcIfInactive() {
        return this.gcIfInactive;
    }

    public void setGcIfInactive(boolean gcIfInactive) {
        this.gcIfInactive = gcIfInactive;
    }

    public void setGcWithNetworkConsumers(boolean gcWithNetworkConsumers) {
        this.gcWithNetworkConsumers = gcWithNetworkConsumers;
    }

    public boolean isGcWithNetworkConsumers() {
        return this.gcWithNetworkConsumers;
    }

    @Override
    public void markForGC(long timeStamp) {
        if (this.isGcIfInactive() && this.lastActiveTime == 0L && !this.isActive() && this.destinationStatistics.messages.getCount() == 0L && this.getInactiveTimeoutBeforeGC() > 0L) {
            this.lastActiveTime = timeStamp;
        }
    }

    @Override
    public boolean canGC() {
        boolean result = false;
        long currentLastActiveTime = this.lastActiveTime;
        if (this.isGcIfInactive() && currentLastActiveTime != 0L && this.destinationStatistics.messages.getCount() == 0L && System.currentTimeMillis() - currentLastActiveTime >= this.getInactiveTimeoutBeforeGC()) {
            result = true;
        }
        return result;
    }

    public void setReduceMemoryFootprint(boolean reduceMemoryFootprint) {
        this.reduceMemoryFootprint = reduceMemoryFootprint;
    }

    public boolean isReduceMemoryFootprint() {
        return this.reduceMemoryFootprint;
    }

    @Override
    public boolean isDoOptimzeMessageStorage() {
        return this.doOptimzeMessageStorage;
    }

    @Override
    public void setDoOptimzeMessageStorage(boolean doOptimzeMessageStorage) {
        this.doOptimzeMessageStorage = doOptimzeMessageStorage;
    }

    public int getOptimizeMessageStoreInFlightLimit() {
        return this.optimizeMessageStoreInFlightLimit;
    }

    public void setOptimizeMessageStoreInFlightLimit(int optimizeMessageStoreInFlightLimit) {
        this.optimizeMessageStoreInFlightLimit = optimizeMessageStoreInFlightLimit;
    }

    @Override
    public abstract List<Subscription> getConsumers();

    protected boolean hasRegularConsumers(List<Subscription> consumers) {
        boolean hasRegularConsumers = false;
        for (Subscription subscription : consumers) {
            if (subscription.getConsumerInfo().isNetworkSubscription()) continue;
            hasRegularConsumers = true;
            break;
        }
        return hasRegularConsumers;
    }

    public ConnectionContext createConnectionContext() {
        ConnectionContext answer = new ConnectionContext();
        answer.setBroker(this.broker);
        answer.getMessageEvaluationContext().setDestination(this.getActiveMQDestination());
        answer.setSecurityContext(SecurityContext.BROKER_SECURITY_CONTEXT);
        return answer;
    }

    protected MessageAck convertToNonRangedAck(MessageAck ack, MessageReference node) {
        if (ack.getMessageCount() > 0) {
            MessageAck a = new MessageAck();
            ack.copy(a);
            ack = a;
            ack.setMessageCount(1);
        }
        ack.setFirstMessageId(node.getMessageId());
        ack.setLastMessageId(node.getMessageId());
        return ack;
    }

    protected boolean isDLQ() {
        return this.destination.isDLQ();
    }

    @Override
    public void duplicateFromStore(Message message, Subscription durableSub) {
        ConnectionContext connectionContext = this.createConnectionContext();
        this.getLog().warn("duplicate message from store {}, redirecting for dlq processing", (Object)message.getMessageId());
        Throwable cause = new Throwable("duplicate from store for " + this.destination);
        message.setRegionDestination(this);
        this.broker.getRoot().sendToDeadLetterQueue(connectionContext, message, null, cause);
        MessageAck messageAck = new MessageAck(message, 1, 1);
        messageAck.setPoisonCause(cause);
        try {
            this.acknowledge(connectionContext, durableSub, messageAck, message);
        }
        catch (IOException e) {
            this.getLog().error("Failed to acknowledge duplicate message {} from {} with {}", message.getMessageId(), this.destination, messageAck);
        }
    }

    public void setPersistJMSRedelivered(boolean persistJMSRedelivered) {
        this.persistJMSRedelivered = persistJMSRedelivered;
    }

    public boolean isPersistJMSRedelivered() {
        return this.persistJMSRedelivered;
    }

    public SystemUsage getSystemUsage() {
        return this.systemUsage;
    }
}

