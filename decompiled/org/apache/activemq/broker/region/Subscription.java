/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.List;
import javax.jms.InvalidSelectorException;
import javax.management.ObjectName;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.SubscriptionRecovery;
import org.apache.activemq.broker.region.SubscriptionStatistics;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.command.MessagePull;
import org.apache.activemq.command.Response;
import org.apache.activemq.filter.MessageEvaluationContext;

public interface Subscription
extends SubscriptionRecovery {
    public void add(MessageReference var1) throws Exception;

    public void acknowledge(ConnectionContext var1, MessageAck var2) throws Exception;

    public Response pullMessage(ConnectionContext var1, MessagePull var2) throws Exception;

    public boolean isWildcard();

    public boolean matches(MessageReference var1, MessageEvaluationContext var2) throws IOException;

    public boolean matches(ActiveMQDestination var1);

    public void add(ConnectionContext var1, Destination var2) throws Exception;

    public List<MessageReference> remove(ConnectionContext var1, Destination var2) throws Exception;

    public ConsumerInfo getConsumerInfo();

    public void gc();

    public void processMessageDispatchNotification(MessageDispatchNotification var1) throws Exception;

    public int getPendingQueueSize();

    public long getPendingMessageSize();

    public int getDispatchedQueueSize();

    public long getDispatchedCounter();

    public long getEnqueueCounter();

    public long getDequeueCounter();

    public SubscriptionStatistics getSubscriptionStatistics();

    public String getSelector();

    public void setSelector(String var1) throws InvalidSelectorException, UnsupportedOperationException;

    public ObjectName getObjectName();

    public void setObjectName(ObjectName var1);

    public boolean isLowWaterMark();

    public boolean isHighWaterMark();

    public boolean isFull();

    public void updateConsumerPrefetch(int var1);

    public void destroy();

    public int getPrefetchSize();

    public int getInFlightSize();

    public long getInFlightMessageSize();

    public int getInFlightUsage();

    public boolean isRecoveryRequired();

    public boolean isBrowser();

    public int countBeforeFull();

    public ConnectionContext getContext();

    public int getCursorMemoryHighWaterMark();

    public void setCursorMemoryHighWaterMark(int var1);

    public boolean isSlowConsumer();

    public void unmatched(MessageReference var1) throws IOException;

    public long getTimeOfLastMessageAck();

    public long getConsumedCount();

    public void incrementConsumedCount();

    public void resetConsumedCount();
}

