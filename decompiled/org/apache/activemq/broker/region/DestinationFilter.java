/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.PrefetchSubscription;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.policy.DeadLetterStrategy;
import org.apache.activemq.broker.region.policy.SlowConsumerStrategy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.store.MessageStore;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.TempUsage;
import org.apache.activemq.usage.Usage;
import org.apache.activemq.util.SubscriptionKey;

public class DestinationFilter
implements Destination {
    protected final Destination next;

    public DestinationFilter(Destination next) {
        this.next = next;
    }

    @Override
    public void acknowledge(ConnectionContext context, Subscription sub, MessageAck ack, MessageReference node) throws IOException {
        this.next.acknowledge(context, sub, ack, node);
    }

    @Override
    public void addSubscription(ConnectionContext context, Subscription sub) throws Exception {
        this.next.addSubscription(context, sub);
    }

    @Override
    public Message[] browse() {
        return this.next.browse();
    }

    @Override
    public void dispose(ConnectionContext context) throws IOException {
        this.next.dispose(context);
    }

    @Override
    public boolean isDisposed() {
        return this.next.isDisposed();
    }

    @Override
    public void gc() {
        this.next.gc();
    }

    @Override
    public void markForGC(long timeStamp) {
        this.next.markForGC(timeStamp);
    }

    @Override
    public boolean canGC() {
        return this.next.canGC();
    }

    @Override
    public long getInactiveTimeoutBeforeGC() {
        return this.next.getInactiveTimeoutBeforeGC();
    }

    @Override
    public ActiveMQDestination getActiveMQDestination() {
        return this.next.getActiveMQDestination();
    }

    @Override
    public DeadLetterStrategy getDeadLetterStrategy() {
        return this.next.getDeadLetterStrategy();
    }

    @Override
    public DestinationStatistics getDestinationStatistics() {
        return this.next.getDestinationStatistics();
    }

    @Override
    public String getName() {
        return this.next.getName();
    }

    @Override
    public MemoryUsage getMemoryUsage() {
        return this.next.getMemoryUsage();
    }

    @Override
    public void setMemoryUsage(MemoryUsage memoryUsage) {
        this.next.setMemoryUsage(memoryUsage);
    }

    @Override
    public TempUsage getTempUsage() {
        return this.next.getTempUsage();
    }

    @Override
    public void removeSubscription(ConnectionContext context, Subscription sub, long lastDeliveredSequenceId) throws Exception {
        this.next.removeSubscription(context, sub, lastDeliveredSequenceId);
    }

    @Override
    public void send(ProducerBrokerExchange context, Message messageSend) throws Exception {
        this.next.send(context, messageSend);
    }

    @Override
    public void start() throws Exception {
        this.next.start();
    }

    @Override
    public void stop() throws Exception {
        this.next.stop();
    }

    @Override
    public List<Subscription> getConsumers() {
        return this.next.getConsumers();
    }

    protected void send(ProducerBrokerExchange context, Message message, ActiveMQDestination destination) throws Exception {
        Broker broker = context.getConnectionContext().getBroker();
        Set<Destination> destinations = broker.getDestinations(destination);
        for (Destination dest : destinations) {
            dest.send(context, message.copy());
        }
    }

    @Override
    public MessageStore getMessageStore() {
        return this.next.getMessageStore();
    }

    @Override
    public boolean isProducerFlowControl() {
        return this.next.isProducerFlowControl();
    }

    @Override
    public void setProducerFlowControl(boolean value) {
        this.next.setProducerFlowControl(value);
    }

    @Override
    public boolean isAlwaysRetroactive() {
        return this.next.isAlwaysRetroactive();
    }

    @Override
    public void setAlwaysRetroactive(boolean value) {
        this.next.setAlwaysRetroactive(value);
    }

    @Override
    public void setBlockedProducerWarningInterval(long blockedProducerWarningInterval) {
        this.next.setBlockedProducerWarningInterval(blockedProducerWarningInterval);
    }

    @Override
    public long getBlockedProducerWarningInterval() {
        return this.next.getBlockedProducerWarningInterval();
    }

    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        this.next.addProducer(context, info);
    }

    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        this.next.removeProducer(context, info);
    }

    @Override
    public int getMaxAuditDepth() {
        return this.next.getMaxAuditDepth();
    }

    @Override
    public int getMaxProducersToAudit() {
        return this.next.getMaxProducersToAudit();
    }

    @Override
    public boolean isEnableAudit() {
        return this.next.isEnableAudit();
    }

    @Override
    public void setEnableAudit(boolean enableAudit) {
        this.next.setEnableAudit(enableAudit);
    }

    @Override
    public void setMaxAuditDepth(int maxAuditDepth) {
        this.next.setMaxAuditDepth(maxAuditDepth);
    }

    @Override
    public void setMaxProducersToAudit(int maxProducersToAudit) {
        this.next.setMaxProducersToAudit(maxProducersToAudit);
    }

    @Override
    public boolean isActive() {
        return this.next.isActive();
    }

    @Override
    public int getMaxPageSize() {
        return this.next.getMaxPageSize();
    }

    @Override
    public void setMaxPageSize(int maxPageSize) {
        this.next.setMaxPageSize(maxPageSize);
    }

    @Override
    public boolean isUseCache() {
        return this.next.isUseCache();
    }

    @Override
    public void setUseCache(boolean useCache) {
        this.next.setUseCache(useCache);
    }

    @Override
    public int getMinimumMessageSize() {
        return this.next.getMinimumMessageSize();
    }

    @Override
    public void setMinimumMessageSize(int minimumMessageSize) {
        this.next.setMinimumMessageSize(minimumMessageSize);
    }

    @Override
    public void wakeup() {
        this.next.wakeup();
    }

    @Override
    public boolean isLazyDispatch() {
        return this.next.isLazyDispatch();
    }

    @Override
    public void setLazyDispatch(boolean value) {
        this.next.setLazyDispatch(value);
    }

    public void messageExpired(ConnectionContext context, PrefetchSubscription prefetchSubscription, MessageReference node) {
        this.next.messageExpired(context, prefetchSubscription, node);
    }

    @Override
    public boolean iterate() {
        return this.next.iterate();
    }

    @Override
    public void fastProducer(ConnectionContext context, ProducerInfo producerInfo) {
        this.next.fastProducer(context, producerInfo);
    }

    @Override
    public void isFull(ConnectionContext context, Usage<?> usage) {
        this.next.isFull(context, usage);
    }

    @Override
    public void messageConsumed(ConnectionContext context, MessageReference messageReference) {
        this.next.messageConsumed(context, messageReference);
    }

    @Override
    public void messageDelivered(ConnectionContext context, MessageReference messageReference) {
        this.next.messageDelivered(context, messageReference);
    }

    @Override
    public void messageDiscarded(ConnectionContext context, Subscription sub, MessageReference messageReference) {
        this.next.messageDiscarded(context, sub, messageReference);
    }

    @Override
    public void slowConsumer(ConnectionContext context, Subscription subs) {
        this.next.slowConsumer(context, subs);
    }

    @Override
    public void messageExpired(ConnectionContext context, Subscription subs, MessageReference node) {
        this.next.messageExpired(context, subs, node);
    }

    @Override
    public int getMaxBrowsePageSize() {
        return this.next.getMaxBrowsePageSize();
    }

    @Override
    public void setMaxBrowsePageSize(int maxPageSize) {
        this.next.setMaxBrowsePageSize(maxPageSize);
    }

    @Override
    public void processDispatchNotification(MessageDispatchNotification messageDispatchNotification) throws Exception {
        this.next.processDispatchNotification(messageDispatchNotification);
    }

    @Override
    public int getCursorMemoryHighWaterMark() {
        return this.next.getCursorMemoryHighWaterMark();
    }

    @Override
    public void setCursorMemoryHighWaterMark(int cursorMemoryHighWaterMark) {
        this.next.setCursorMemoryHighWaterMark(cursorMemoryHighWaterMark);
    }

    @Override
    public boolean isPrioritizedMessages() {
        return this.next.isPrioritizedMessages();
    }

    @Override
    public SlowConsumerStrategy getSlowConsumerStrategy() {
        return this.next.getSlowConsumerStrategy();
    }

    @Override
    public boolean isDoOptimzeMessageStorage() {
        return this.next.isDoOptimzeMessageStorage();
    }

    @Override
    public void setDoOptimzeMessageStorage(boolean doOptimzeMessageStorage) {
        this.next.setDoOptimzeMessageStorage(doOptimzeMessageStorage);
    }

    @Override
    public void clearPendingMessages() {
        this.next.clearPendingMessages();
    }

    @Override
    public void duplicateFromStore(Message message, Subscription subscription) {
        this.next.duplicateFromStore(message, subscription);
    }

    public void deleteSubscription(ConnectionContext context, SubscriptionKey key) throws Exception {
        if (this.next instanceof DestinationFilter) {
            DestinationFilter filter2 = (DestinationFilter)this.next;
            filter2.deleteSubscription(context, key);
        } else if (this.next instanceof Topic) {
            Topic topic = (Topic)this.next;
            topic.deleteSubscription(context, key);
        }
    }

    public Destination getNext() {
        return this.next;
    }

    public <T> T getAdaptor(Class<? extends T> clazz) {
        if (clazz.isInstance(this)) {
            return clazz.cast(this);
        }
        if (this.next != null && clazz.isInstance(this.next)) {
            return clazz.cast(this.next);
        }
        if (this.next instanceof DestinationFilter) {
            return ((DestinationFilter)this.next).getAdaptor(clazz);
        }
        return null;
    }
}

