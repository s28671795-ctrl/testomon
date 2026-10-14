/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.RemoveInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.command.TransientInitializer;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.state.CommandVisitor;

public class ConsumerInfo
extends BaseCommand
implements TransientInitializer {
    public static final byte DATA_STRUCTURE_TYPE = 5;
    public static final byte HIGH_PRIORITY = 10;
    public static final byte NORMAL_PRIORITY = 0;
    public static final byte NETWORK_CONSUMER_PRIORITY = -5;
    public static final byte LOW_PRIORITY = -10;
    protected ConsumerId consumerId;
    protected ActiveMQDestination destination;
    protected int prefetchSize;
    protected int maximumPendingMessageLimit;
    protected boolean browser;
    protected boolean dispatchAsync;
    protected String selector;
    protected String clientId;
    protected String subscriptionName;
    protected boolean noLocal;
    protected boolean exclusive;
    protected boolean retroactive;
    protected byte priority;
    protected BrokerId[] brokerPath;
    protected boolean optimizedAcknowledge;
    protected transient int currentPrefetchSize;
    protected boolean noRangeAcks;
    protected BooleanExpression additionalPredicate;
    protected transient boolean networkSubscription;
    protected transient List<ConsumerId> networkConsumerIds;
    private transient long lastDeliveredSequenceId = -1L;
    private transient Map<ActiveMQDestination, AtomicLong> assignedGroupCount = new ConcurrentHashMap<ActiveMQDestination, AtomicLong>();

    public ConsumerInfo() {
    }

    public ConsumerInfo(ConsumerId consumerId) {
        this.consumerId = consumerId;
    }

    public ConsumerInfo(SessionInfo sessionInfo, long consumerId) {
        this.consumerId = new ConsumerId(sessionInfo.getSessionId(), consumerId);
    }

    public ConsumerInfo copy() {
        ConsumerInfo info = new ConsumerInfo();
        this.copy(info);
        return info;
    }

    public void copy(ConsumerInfo info) {
        super.copy(info);
        info.consumerId = this.consumerId;
        info.destination = this.destination;
        info.prefetchSize = this.prefetchSize;
        info.maximumPendingMessageLimit = this.maximumPendingMessageLimit;
        info.browser = this.browser;
        info.dispatchAsync = this.dispatchAsync;
        info.selector = this.selector;
        info.clientId = this.clientId;
        info.subscriptionName = this.subscriptionName;
        info.noLocal = this.noLocal;
        info.exclusive = this.exclusive;
        info.retroactive = this.retroactive;
        info.priority = this.priority;
        info.brokerPath = this.brokerPath;
        info.networkSubscription = this.networkSubscription;
        if (this.networkConsumerIds != null) {
            if (info.networkConsumerIds == null) {
                info.networkConsumerIds = new ArrayList<ConsumerId>();
            }
            info.networkConsumerIds.addAll(this.networkConsumerIds);
        }
    }

    public boolean isDurable() {
        return this.subscriptionName != null;
    }

    @Override
    public byte getDataStructureType() {
        return 5;
    }

    public ConsumerId getConsumerId() {
        return this.consumerId;
    }

    public void setConsumerId(ConsumerId consumerId) {
        this.consumerId = consumerId;
    }

    public boolean isBrowser() {
        return this.browser;
    }

    public void setBrowser(boolean browser) {
        this.browser = browser;
    }

    public ActiveMQDestination getDestination() {
        return this.destination;
    }

    public void setDestination(ActiveMQDestination destination) {
        this.destination = destination;
    }

    public int getPrefetchSize() {
        return this.prefetchSize;
    }

    public void setPrefetchSize(int prefetchSize) {
        this.prefetchSize = prefetchSize;
        this.currentPrefetchSize = prefetchSize;
    }

    public int getMaximumPendingMessageLimit() {
        return this.maximumPendingMessageLimit;
    }

    public void setMaximumPendingMessageLimit(int maximumPendingMessageLimit) {
        this.maximumPendingMessageLimit = maximumPendingMessageLimit;
    }

    public boolean isDispatchAsync() {
        return this.dispatchAsync;
    }

    public void setDispatchAsync(boolean dispatchAsync) {
        this.dispatchAsync = dispatchAsync;
    }

    public String getSelector() {
        return this.selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSubscriptionName() {
        return this.subscriptionName;
    }

    public void setSubscriptionName(String durableSubscriptionId) {
        this.subscriptionName = durableSubscriptionId;
    }

    public boolean isNoLocal() {
        return this.noLocal;
    }

    public void setNoLocal(boolean noLocal) {
        this.noLocal = noLocal;
    }

    public boolean isExclusive() {
        return this.exclusive;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    public boolean isRetroactive() {
        return this.retroactive;
    }

    public void setRetroactive(boolean retroactive) {
        this.retroactive = retroactive;
    }

    public RemoveInfo createRemoveCommand() {
        RemoveInfo command = new RemoveInfo(this.getConsumerId());
        command.setResponseRequired(this.isResponseRequired());
        return command;
    }

    public byte getPriority() {
        return this.priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public BrokerId[] getBrokerPath() {
        return this.brokerPath;
    }

    public void setBrokerPath(BrokerId[] brokerPath) {
        this.brokerPath = brokerPath;
    }

    public BooleanExpression getAdditionalPredicate() {
        return this.additionalPredicate;
    }

    public void setAdditionalPredicate(BooleanExpression additionalPredicate) {
        this.additionalPredicate = additionalPredicate;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processAddConsumer(this);
    }

    public boolean isNetworkSubscription() {
        return this.networkSubscription;
    }

    public void setNetworkSubscription(boolean networkSubscription) {
        this.networkSubscription = networkSubscription;
    }

    public boolean isOptimizedAcknowledge() {
        return this.optimizedAcknowledge;
    }

    public void setOptimizedAcknowledge(boolean optimizedAcknowledge) {
        this.optimizedAcknowledge = optimizedAcknowledge;
    }

    public int getCurrentPrefetchSize() {
        return this.currentPrefetchSize;
    }

    public void setCurrentPrefetchSize(int currentPrefetchSize) {
        this.currentPrefetchSize = currentPrefetchSize;
    }

    public boolean isNoRangeAcks() {
        return this.noRangeAcks;
    }

    public void setNoRangeAcks(boolean noRangeAcks) {
        this.noRangeAcks = noRangeAcks;
    }

    public synchronized void addNetworkConsumerId(ConsumerId networkConsumerId) {
        if (this.networkConsumerIds == null) {
            this.networkConsumerIds = new ArrayList<ConsumerId>();
        }
        this.networkConsumerIds.add(networkConsumerId);
    }

    public synchronized void removeNetworkConsumerId(ConsumerId networkConsumerId) {
        if (this.networkConsumerIds != null) {
            this.networkConsumerIds.remove(networkConsumerId);
            if (this.networkConsumerIds.isEmpty()) {
                this.networkConsumerIds = null;
            }
        }
    }

    public synchronized boolean isNetworkConsumersEmpty() {
        return this.networkConsumerIds == null || this.networkConsumerIds.isEmpty();
    }

    public synchronized List<ConsumerId> getNetworkConsumerIds() {
        ArrayList<ConsumerId> result = new ArrayList<ConsumerId>();
        if (this.networkConsumerIds != null) {
            result.addAll(this.networkConsumerIds);
        }
        return result;
    }

    public int hashCode() {
        return this.consumerId == null ? 0 : this.consumerId.hashCode();
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
        ConsumerInfo other = (ConsumerInfo)obj;
        if (this.consumerId == null && other.consumerId != null) {
            return false;
        }
        return this.consumerId.equals(other.consumerId);
    }

    public ConsumerId[] getNetworkConsumerPath() {
        ConsumerId[] result = null;
        if (this.networkConsumerIds != null) {
            result = this.networkConsumerIds.toArray(new ConsumerId[0]);
        }
        return result;
    }

    public void setNetworkConsumerPath(ConsumerId[] consumerPath) {
        if (consumerPath != null) {
            for (int i = 0; i < consumerPath.length; ++i) {
                this.addNetworkConsumerId(consumerPath[i]);
            }
        }
    }

    public void setLastDeliveredSequenceId(long lastDeliveredSequenceId) {
        this.lastDeliveredSequenceId = lastDeliveredSequenceId;
    }

    public long getLastDeliveredSequenceId() {
        return this.lastDeliveredSequenceId;
    }

    public void incrementAssignedGroupCount(ActiveMQDestination dest) {
        AtomicLong value = this.assignedGroupCount.get(dest);
        if (value == null) {
            value = new AtomicLong(0L);
            this.assignedGroupCount.put(dest, value);
        }
        value.incrementAndGet();
    }

    public void clearAssignedGroupCount(ActiveMQDestination dest) {
        this.assignedGroupCount.remove(dest);
    }

    public void decrementAssignedGroupCount(ActiveMQDestination dest) {
        AtomicLong value = this.assignedGroupCount.get(dest);
        if (value != null) {
            value.decrementAndGet();
        }
    }

    public long getAssignedGroupCount(ActiveMQDestination dest) {
        long result = 0L;
        AtomicLong value = this.assignedGroupCount.get(dest);
        if (value != null) {
            result = value.longValue();
        }
        return result;
    }

    @Override
    public void initTransients() {
        this.assignedGroupCount = new ConcurrentHashMap<ActiveMQDestination, AtomicLong>();
        this.lastDeliveredSequenceId = -1L;
    }

    @Override
    public String toString() {
        HashMap<String, Object> overrideFields = new HashMap<String, Object>();
        overrideFields.put("networkConsumerIds", this.networkConsumerIds);
        return super.toString(overrideFields);
    }
}

