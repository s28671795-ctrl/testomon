/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.io.IOException;
import java.util.Set;
import javax.jms.InvalidSelectorException;
import javax.management.ObjectName;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.jmx.BrokerMBeanSupport;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.broker.jmx.SubscriptionViewMBean;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.util.IOExceptionSupport;

public class SubscriptionView
implements SubscriptionViewMBean {
    protected final Subscription subscription;
    protected final String clientId;
    protected final String userName;

    public SubscriptionView(String clientId, String userName, Subscription subs) {
        this.clientId = clientId;
        this.subscription = subs;
        this.userName = userName;
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public ObjectName getConnection() {
        BrokerService service2;
        ManagementContext managementCtx;
        ConnectionContext ctx;
        ObjectName result = null;
        if (this.clientId != null && this.subscription != null && (ctx = this.subscription.getContext()) != null && ctx.getBroker() != null && ctx.getBroker().getBrokerService() != null && (managementCtx = (service2 = ctx.getBroker().getBrokerService()).getManagementContext()) != null) {
            try {
                ObjectName query = this.createConnectionQuery(managementCtx, service2.getBrokerName());
                Set<ObjectName> names = managementCtx.queryNames(query, null);
                if (names.size() == 1) {
                    result = names.iterator().next();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return result;
    }

    private ObjectName createConnectionQuery(ManagementContext ctx, String brokerName) throws IOException {
        try {
            return BrokerMBeanSupport.createConnectionQuery(ctx.getJmxDomainName(), brokerName, this.clientId);
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create(e);
        }
    }

    @Override
    public String getConnectionId() {
        ConsumerInfo info = this.getConsumerInfo();
        if (info != null) {
            return info.getConsumerId().getConnectionId();
        }
        return "NOTSET";
    }

    @Override
    public long getSessionId() {
        ConsumerInfo info = this.getConsumerInfo();
        if (info != null) {
            return info.getConsumerId().getSessionId();
        }
        return 0L;
    }

    @Override
    public long getSubscriptionId() {
        ConsumerInfo info = this.getConsumerInfo();
        if (info != null) {
            return info.getConsumerId().getValue();
        }
        return 0L;
    }

    @Override
    public String getDestinationName() {
        ConsumerInfo info = this.getConsumerInfo();
        if (info != null) {
            ActiveMQDestination dest = info.getDestination();
            return dest.getPhysicalName();
        }
        return "NOTSET";
    }

    @Override
    public String getSelector() {
        if (this.subscription != null) {
            return this.subscription.getSelector();
        }
        return null;
    }

    @Override
    public void setSelector(String selector) throws InvalidSelectorException, UnsupportedOperationException {
        if (this.subscription == null) {
            throw new UnsupportedOperationException("No subscription object");
        }
        this.subscription.setSelector(selector);
    }

    @Override
    public boolean isDestinationQueue() {
        ConsumerInfo info = this.getConsumerInfo();
        if (info != null) {
            ActiveMQDestination dest = info.getDestination();
            return dest.isQueue();
        }
        return false;
    }

    @Override
    public boolean isDestinationTopic() {
        ConsumerInfo info = this.getConsumerInfo();
        if (info != null) {
            ActiveMQDestination dest = info.getDestination();
            return dest.isTopic();
        }
        return false;
    }

    @Override
    public boolean isDestinationTemporary() {
        ConsumerInfo info = this.getConsumerInfo();
        if (info != null) {
            ActiveMQDestination dest = info.getDestination();
            return dest.isTemporary();
        }
        return false;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public boolean isNetwork() {
        ConsumerInfo info = this.getConsumerInfo();
        if (info != null) {
            return info.isNetworkSubscription();
        }
        return false;
    }

    public void gc() {
        if (this.subscription != null) {
            this.subscription.gc();
        }
    }

    @Override
    public boolean isRetroactive() {
        ConsumerInfo info = this.getConsumerInfo();
        return info != null ? info.isRetroactive() : false;
    }

    @Override
    public boolean isExclusive() {
        ConsumerInfo info = this.getConsumerInfo();
        return info != null ? info.isExclusive() : false;
    }

    @Override
    public boolean isDurable() {
        ConsumerInfo info = this.getConsumerInfo();
        return info != null ? info.isDurable() : false;
    }

    @Override
    public boolean isNoLocal() {
        ConsumerInfo info = this.getConsumerInfo();
        return info != null ? info.isNoLocal() : false;
    }

    @Override
    public boolean isDispatchAsync() {
        ConsumerInfo info = this.getConsumerInfo();
        return info != null ? info.isDispatchAsync() : false;
    }

    @Override
    public int getMaximumPendingMessageLimit() {
        ConsumerInfo info = this.getConsumerInfo();
        return info != null ? info.getMaximumPendingMessageLimit() : 0;
    }

    @Override
    public byte getPriority() {
        ConsumerInfo info = this.getConsumerInfo();
        return info != null ? info.getPriority() : (byte)0;
    }

    @Override
    public String getSubscriptionName() {
        ConsumerInfo info = this.getConsumerInfo();
        return info != null ? info.getSubscriptionName() : null;
    }

    @Override
    public int getPendingQueueSize() {
        return this.subscription != null ? this.subscription.getPendingQueueSize() : 0;
    }

    @Override
    public int getDispatchedQueueSize() {
        return this.subscription != null ? this.subscription.getDispatchedQueueSize() : 0;
    }

    @Override
    public int getMessageCountAwaitingAcknowledge() {
        return this.getDispatchedQueueSize();
    }

    @Override
    public long getDispatchedCounter() {
        return this.subscription != null ? this.subscription.getDispatchedCounter() : 0L;
    }

    @Override
    public long getEnqueueCounter() {
        return this.subscription != null ? this.subscription.getEnqueueCounter() : 0L;
    }

    @Override
    public long getDequeueCounter() {
        return this.subscription != null ? this.subscription.getDequeueCounter() : 0L;
    }

    protected ConsumerInfo getConsumerInfo() {
        return this.subscription != null ? this.subscription.getConsumerInfo() : null;
    }

    public String toString() {
        return "SubscriptionView: " + this.getClientId() + ":" + this.getConnectionId();
    }

    @Override
    public int getPrefetchSize() {
        return this.subscription != null ? this.subscription.getPrefetchSize() : 0;
    }

    @Override
    public boolean isMatchingQueue(String queueName) {
        if (this.isDestinationQueue()) {
            return this.matchesDestination(new ActiveMQQueue(queueName));
        }
        return false;
    }

    @Override
    public boolean isMatchingTopic(String topicName) {
        if (this.isDestinationTopic()) {
            return this.matchesDestination(new ActiveMQTopic(topicName));
        }
        return false;
    }

    public boolean matchesDestination(ActiveMQDestination destination) {
        ActiveMQDestination subscriptionDestination = this.subscription.getActiveMQDestination();
        DestinationFilter filter2 = DestinationFilter.parseFilter(subscriptionDestination);
        return filter2.matches(destination);
    }

    @Override
    public boolean isSlowConsumer() {
        return this.subscription.isSlowConsumer();
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public void resetStatistics() {
        if (this.subscription != null && this.subscription.getSubscriptionStatistics() != null) {
            this.subscription.getSubscriptionStatistics().reset();
        }
    }

    @Override
    public long getConsumedCount() {
        return this.subscription != null ? this.subscription.getConsumedCount() : 0L;
    }
}

