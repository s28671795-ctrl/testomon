/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.List;
import org.apache.activemq.Service;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.DeadLetterStrategy;
import org.apache.activemq.broker.region.policy.SharedDeadLetterStrategy;
import org.apache.activemq.broker.region.policy.SlowConsumerStrategy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.store.MessageStore;
import org.apache.activemq.thread.Task;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.TempUsage;
import org.apache.activemq.usage.Usage;

public interface Destination
extends Service,
Task,
Message.MessageDestination {
    public static final DeadLetterStrategy DEFAULT_DEAD_LETTER_STRATEGY = new SharedDeadLetterStrategy();
    public static final long DEFAULT_BLOCKED_PRODUCER_WARNING_INTERVAL = 30000L;

    public void addSubscription(ConnectionContext var1, Subscription var2) throws Exception;

    public void removeSubscription(ConnectionContext var1, Subscription var2, long var3) throws Exception;

    public void addProducer(ConnectionContext var1, ProducerInfo var2) throws Exception;

    public void removeProducer(ConnectionContext var1, ProducerInfo var2) throws Exception;

    public void send(ProducerBrokerExchange var1, Message var2) throws Exception;

    public void acknowledge(ConnectionContext var1, Subscription var2, MessageAck var3, MessageReference var4) throws IOException;

    public long getInactiveTimeoutBeforeGC();

    public void markForGC(long var1);

    public boolean canGC();

    public void gc();

    public ActiveMQDestination getActiveMQDestination();

    @Override
    public MemoryUsage getMemoryUsage();

    public void setMemoryUsage(MemoryUsage var1);

    public TempUsage getTempUsage();

    public void dispose(ConnectionContext var1) throws IOException;

    public boolean isDisposed();

    public DestinationStatistics getDestinationStatistics();

    public DeadLetterStrategy getDeadLetterStrategy();

    public Message[] browse();

    public String getName();

    public MessageStore getMessageStore();

    public boolean isProducerFlowControl();

    public void setProducerFlowControl(boolean var1);

    public boolean isAlwaysRetroactive();

    public void setAlwaysRetroactive(boolean var1);

    public void setBlockedProducerWarningInterval(long var1);

    public long getBlockedProducerWarningInterval();

    public int getMaxProducersToAudit();

    public void setMaxProducersToAudit(int var1);

    public int getMaxAuditDepth();

    public void setMaxAuditDepth(int var1);

    public boolean isEnableAudit();

    public void setEnableAudit(boolean var1);

    public boolean isActive();

    public int getMaxPageSize();

    public void setMaxPageSize(int var1);

    public int getMaxBrowsePageSize();

    public void setMaxBrowsePageSize(int var1);

    public boolean isUseCache();

    public void setUseCache(boolean var1);

    @Override
    public int getMinimumMessageSize();

    public void setMinimumMessageSize(int var1);

    public int getCursorMemoryHighWaterMark();

    public void setCursorMemoryHighWaterMark(int var1);

    public void wakeup();

    public boolean isLazyDispatch();

    public void setLazyDispatch(boolean var1);

    public void messageExpired(ConnectionContext var1, Subscription var2, MessageReference var3);

    public void messageConsumed(ConnectionContext var1, MessageReference var2);

    public void messageDelivered(ConnectionContext var1, MessageReference var2);

    public void messageDiscarded(ConnectionContext var1, Subscription var2, MessageReference var3);

    public void slowConsumer(ConnectionContext var1, Subscription var2);

    public void fastProducer(ConnectionContext var1, ProducerInfo var2);

    public void isFull(ConnectionContext var1, Usage<?> var2);

    public List<Subscription> getConsumers();

    public void processDispatchNotification(MessageDispatchNotification var1) throws Exception;

    public boolean isPrioritizedMessages();

    public SlowConsumerStrategy getSlowConsumerStrategy();

    public boolean isDoOptimzeMessageStorage();

    public void setDoOptimzeMessageStorage(boolean var1);

    public void clearPendingMessages();

    public void duplicateFromStore(Message var1, Subscription var2);
}

