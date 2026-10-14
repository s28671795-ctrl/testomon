/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import javax.jms.InvalidSelectorException;
import javax.management.ObjectName;
import org.apache.activemq.broker.jmx.MBeanInfo;

public interface SubscriptionViewMBean {
    @MBeanInfo(value="JMS Client id of the Connection the Subscription is on.")
    public String getClientId();

    @MBeanInfo(value="ID of the Connection the Subscription is on.")
    public String getConnectionId();

    @MBeanInfo(value="ID of the Session the Subscription is on.")
    public long getSessionId();

    @MBeanInfo(value="ID of the Subscription.")
    public long getSubscriptionId();

    @MBeanInfo(value="The name of the destination the subscription is on.")
    public String getDestinationName();

    @MBeanInfo(value="The SQL-92 message header selector or XPATH body selector of the subscription.")
    public String getSelector();

    public void setSelector(@MBeanInfo(value="selector") String var1) throws InvalidSelectorException, UnsupportedOperationException;

    @MBeanInfo(value="Subscription is on a Queue")
    public boolean isDestinationQueue();

    @MBeanInfo(value="Subscription is on a Topic")
    public boolean isDestinationTopic();

    @MBeanInfo(value="Subscription is on a temporary Queue/Topic")
    public boolean isDestinationTemporary();

    @MBeanInfo(value="Subscription is active (connected and receiving messages).")
    public boolean isActive();

    @MBeanInfo(value="Subscription was created by a demand-forwarding network bridge")
    public boolean isNetwork();

    @MBeanInfo(value="Number of messages pending delivery.")
    public int getPendingQueueSize();

    @MBeanInfo(value="Number of messages dispatched awaiting acknowledgement.")
    public int getDispatchedQueueSize();

    @MBeanInfo(value="Number of messages dispatched awaiting acknowledgement.")
    public int getMessageCountAwaitingAcknowledge();

    @MBeanInfo(value="Number of messages that sent to the client.")
    public long getDispatchedCounter();

    @MBeanInfo(value="Number of messages that matched the subscription.")
    public long getEnqueueCounter();

    @MBeanInfo(value="Number of messages were sent to and acknowledge by the client.")
    public long getDequeueCounter();

    @MBeanInfo(value="Number of messages to pre-fetch and dispatch to the client.")
    public int getPrefetchSize();

    @MBeanInfo(value="The subscriber is retroactive (tries to receive broadcasted topic messages sent prior to connecting)")
    public boolean isRetroactive();

    @MBeanInfo(value="The subscriber is exclusive (no other subscribers may receive messages from the destination as long as this one is)")
    public boolean isExclusive();

    @MBeanInfo(value="The subscription is persistent.")
    public boolean isDurable();

    @MBeanInfo(value="The subscription ignores local messages.")
    public boolean isNoLocal();

    @MBeanInfo(value="Is the consumer configured for Async Dispatch")
    public boolean isDispatchAsync();

    @MBeanInfo(value="The maximum number of pending messages allowed (in addition to the prefetch size).")
    public int getMaximumPendingMessageLimit();

    @MBeanInfo(value="The subscription priority")
    public byte getPriority();

    @MBeanInfo(value="The name of the subscription (durable subscriptions only).")
    public String getSubscriptionName();

    @MBeanInfo(value="Returns true if the subscription (which may be using wildcards) matches the given queue name")
    public boolean isMatchingQueue(String var1);

    @MBeanInfo(value="Returns true if the subscription (which may be using wildcards) matches the given topic name")
    public boolean isMatchingTopic(String var1);

    @MBeanInfo(value="Returns true if the subscription is slow")
    public boolean isSlowConsumer();

    @MBeanInfo(value="User Name used to authorize creation of this Subscription")
    public String getUserName();

    @MBeanInfo(value="ObjectName of the Connection that created this Subscription")
    public ObjectName getConnection();

    @MBeanInfo(value="Resets statistics.")
    public void resetStatistics();

    @MBeanInfo(value="Messages consumed")
    public long getConsumedCount();
}

