/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.jms.JMSException;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.QueueMessageReference;
import org.apache.activemq.broker.region.QueueSubscription;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.usage.SystemUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueBrowserSubscription
extends QueueSubscription {
    protected static final Logger LOG = LoggerFactory.getLogger(QueueBrowserSubscription.class);
    int queueRefs;
    boolean browseDone;
    boolean destinationsAdded;
    private final ConcurrentMap<MessageId, Object> audit = new ConcurrentHashMap<MessageId, Object>();
    private long maxMessages;

    public QueueBrowserSubscription(Broker broker, SystemUsage usageManager, ConnectionContext context, ConsumerInfo info) throws JMSException {
        super(broker, usageManager, context, info);
    }

    @Override
    protected boolean canDispatch(MessageReference node) {
        return !((QueueMessageReference)node).isAcked();
    }

    @Override
    public synchronized String toString() {
        return "QueueBrowserSubscription: consumer=" + this.info.getConsumerId() + ", destinations=" + this.destinations.size() + ", dispatched=" + this.dispatched.size() + ", delivered=" + this.prefetchExtension + ", pending=" + this.getPendingQueueSize();
    }

    public synchronized void destinationsAdded() throws Exception {
        this.destinationsAdded = true;
        this.checkDone();
    }

    public boolean isDuplicate(MessageId messageId) {
        return this.audit.putIfAbsent(messageId, Boolean.TRUE) != null;
    }

    private void checkDone() throws Exception {
        if (!this.browseDone && this.queueRefs == 0 && this.destinationsAdded) {
            this.browseDone = true;
            this.add(QueueMessageReference.NULL_MESSAGE);
        }
    }

    @Override
    public boolean matches(MessageReference node, MessageEvaluationContext context) throws IOException {
        return !this.browseDone && super.matches(node, context);
    }

    @Override
    protected void acknowledge(ConnectionContext context, MessageAck ack, MessageReference n) throws IOException {
        if (this.info.isNetworkSubscription()) {
            super.acknowledge(context, ack, n);
        }
    }

    public synchronized void incrementQueueRef() {
        ++this.queueRefs;
    }

    public synchronized void decrementQueueRef() throws Exception {
        if (this.queueRefs > 0) {
            --this.queueRefs;
        }
        this.checkDone();
    }

    @Override
    public List<MessageReference> remove(ConnectionContext context, Destination destination) throws Exception {
        super.remove(context, destination);
        return new ArrayList<MessageReference>();
    }

    public boolean atMax() {
        return this.maxMessages > 0L && this.getEnqueueCounter() >= this.maxMessages;
    }

    public void setMaxMessages(long max) {
        this.maxMessages = max;
    }
}

