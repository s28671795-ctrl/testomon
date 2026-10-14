/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import javax.management.ObjectName;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.SubscriptionStatistics;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.filter.LogicExpression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.NoLocalExpression;
import org.apache.activemq.selector.SelectorParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSubscription
implements Subscription {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractSubscription.class);
    protected Broker broker;
    protected ConnectionContext context;
    protected ConsumerInfo info;
    protected final DestinationFilter destinationFilter;
    protected final CopyOnWriteArrayList<Destination> destinations = new CopyOnWriteArrayList();
    protected final AtomicInteger prefetchExtension = new AtomicInteger(0);
    private boolean usePrefetchExtension = true;
    private BooleanExpression selectorExpression;
    private ObjectName objectName;
    private int cursorMemoryHighWaterMark = 70;
    private boolean slowConsumer;
    private long lastAckTime;
    private final SubscriptionStatistics subscriptionStatistics = new SubscriptionStatistics();

    public AbstractSubscription(Broker broker, ConnectionContext context, ConsumerInfo info) throws InvalidSelectorException {
        this.broker = broker;
        this.context = context;
        this.info = info;
        this.destinationFilter = DestinationFilter.parseFilter(info.getDestination());
        this.selectorExpression = AbstractSubscription.parseSelector(info);
        this.lastAckTime = System.currentTimeMillis();
    }

    private static BooleanExpression parseSelector(ConsumerInfo info) throws InvalidSelectorException {
        BooleanExpression rc = null;
        if (info.getSelector() != null) {
            rc = SelectorParser.parse(info.getSelector());
        }
        if (info.isNoLocal()) {
            rc = rc == null ? new NoLocalExpression(info.getConsumerId().getConnectionId()) : LogicExpression.createAND(new NoLocalExpression(info.getConsumerId().getConnectionId()), rc);
        }
        if (info.getAdditionalPredicate() != null) {
            rc = rc == null ? info.getAdditionalPredicate() : LogicExpression.createAND(info.getAdditionalPredicate(), rc);
        }
        return rc;
    }

    @Override
    public synchronized void acknowledge(ConnectionContext context, MessageAck ack) throws Exception {
        this.lastAckTime = System.currentTimeMillis();
        this.subscriptionStatistics.getConsumedCount().increment();
    }

    @Override
    public boolean matches(MessageReference node, MessageEvaluationContext context) throws IOException {
        ConsumerId targetConsumerId = node.getTargetConsumerId();
        if (targetConsumerId != null && !targetConsumerId.equals(this.info.getConsumerId())) {
            return false;
        }
        try {
            return (this.selectorExpression == null || this.selectorExpression.matches(context)) && this.context.isAllowedToConsume(node);
        }
        catch (JMSException e) {
            LOG.info("Selector failed to evaluate: {}", (Object)e.getMessage(), (Object)e);
            return false;
        }
    }

    @Override
    public boolean isWildcard() {
        return this.destinationFilter.isWildcard();
    }

    @Override
    public boolean matches(ActiveMQDestination destination) {
        return this.destinationFilter.matches(destination);
    }

    @Override
    public void add(ConnectionContext context, Destination destination) throws Exception {
        this.destinations.add(destination);
    }

    @Override
    public List<MessageReference> remove(ConnectionContext context, Destination destination) throws Exception {
        this.destinations.remove(destination);
        return Collections.EMPTY_LIST;
    }

    @Override
    public ConsumerInfo getConsumerInfo() {
        return this.info;
    }

    @Override
    public void gc() {
    }

    @Override
    public ConnectionContext getContext() {
        return this.context;
    }

    public ConsumerInfo getInfo() {
        return this.info;
    }

    public BooleanExpression getSelectorExpression() {
        return this.selectorExpression;
    }

    @Override
    public String getSelector() {
        return this.info.getSelector();
    }

    @Override
    public void setSelector(String selector) throws InvalidSelectorException {
        ConsumerInfo copy = this.info.copy();
        copy.setSelector(selector);
        BooleanExpression newSelector = AbstractSubscription.parseSelector(copy);
        this.info.setSelector(selector);
        this.selectorExpression = newSelector;
    }

    @Override
    public ObjectName getObjectName() {
        return this.objectName;
    }

    @Override
    public void setObjectName(ObjectName objectName) {
        this.objectName = objectName;
    }

    @Override
    public int getPrefetchSize() {
        return this.info.getPrefetchSize();
    }

    public boolean isUsePrefetchExtension() {
        return this.usePrefetchExtension;
    }

    public void setUsePrefetchExtension(boolean usePrefetchExtension) {
        this.usePrefetchExtension = usePrefetchExtension;
    }

    public void setPrefetchSize(int newSize) {
        this.info.setPrefetchSize(newSize);
    }

    @Override
    public boolean isRecoveryRequired() {
        return true;
    }

    @Override
    public boolean isSlowConsumer() {
        return this.slowConsumer;
    }

    public void setSlowConsumer(boolean val) {
        this.slowConsumer = val;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean addRecoveredMessage(ConnectionContext context, MessageReference message) throws Exception {
        boolean result = false;
        MessageEvaluationContext msgContext = context.getMessageEvaluationContext();
        try {
            Destination regionDestination = (Destination)message.getRegionDestination();
            msgContext.setDestination(regionDestination.getActiveMQDestination());
            msgContext.setMessageReference(message);
            result = this.matches(message, msgContext);
            if (result) {
                this.doAddRecoveredMessage(message);
            }
        }
        finally {
            msgContext.clear();
        }
        return result;
    }

    @Override
    public ActiveMQDestination getActiveMQDestination() {
        return this.info != null ? this.info.getDestination() : null;
    }

    @Override
    public boolean isBrowser() {
        return this.info != null && this.info.isBrowser();
    }

    @Override
    public long getInFlightMessageSize() {
        return this.subscriptionStatistics.getInflightMessageSize().getTotalSize();
    }

    @Override
    public int getInFlightUsage() {
        int prefetchSize = this.info.getPrefetchSize();
        if (prefetchSize > 0) {
            return this.getInFlightSize() * 100 / prefetchSize;
        }
        return Integer.MAX_VALUE;
    }

    public void addDestination(Destination destination) {
    }

    public void removeDestination(Destination destination) {
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
    public int countBeforeFull() {
        return this.info.getPrefetchSize() - this.getDispatchedQueueSize();
    }

    @Override
    public void unmatched(MessageReference node) throws IOException {
    }

    protected void doAddRecoveredMessage(MessageReference message) throws Exception {
        this.add(message);
    }

    @Override
    public long getTimeOfLastMessageAck() {
        return this.lastAckTime;
    }

    public void setTimeOfLastMessageAck(long value) {
        this.lastAckTime = value;
    }

    @Override
    public long getConsumedCount() {
        return this.subscriptionStatistics.getConsumedCount().getCount();
    }

    @Override
    public void incrementConsumedCount() {
        this.subscriptionStatistics.getConsumedCount().increment();
    }

    @Override
    public void resetConsumedCount() {
        this.subscriptionStatistics.getConsumedCount().reset();
    }

    @Override
    public SubscriptionStatistics getSubscriptionStatistics() {
        return this.subscriptionStatistics;
    }

    public void wakeupDestinationsForDispatch() {
        for (Destination dest : this.destinations) {
            dest.wakeup();
        }
    }

    public AtomicInteger getPrefetchExtension() {
        return this.prefetchExtension;
    }

    protected void contractPrefetchExtension(int amount) {
        if (this.isUsePrefetchExtension() && this.getPrefetchSize() != 0) {
            this.decrementPrefetchExtension(amount);
        }
    }

    protected void expandPrefetchExtension(int amount) {
        if (this.isUsePrefetchExtension() && this.getPrefetchSize() != 0) {
            this.incrementPrefetchExtension(amount);
        }
    }

    protected void decrementPrefetchExtension(int amount) {
        int newExtension;
        int currentExtension;
        while (!this.prefetchExtension.compareAndSet(currentExtension = this.prefetchExtension.get(), newExtension = Math.max(0, currentExtension - amount))) {
        }
    }

    private void incrementPrefetchExtension(int amount) {
        int newExtension;
        int currentExtension;
        while (!this.prefetchExtension.compareAndSet(currentExtension = this.prefetchExtension.get(), newExtension = Math.max(currentExtension, currentExtension + amount))) {
        }
    }
}

