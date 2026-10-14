/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import javax.jms.JMSException;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.IndirectMessageReference;
import org.apache.activemq.broker.region.LockOwner;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.PrefetchSubscription;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.broker.region.QueueMessageReference;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.usage.SystemUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueSubscription
extends PrefetchSubscription
implements LockOwner {
    private static final Logger LOG = LoggerFactory.getLogger(QueueSubscription.class);

    public QueueSubscription(Broker broker, SystemUsage usageManager, ConnectionContext context, ConsumerInfo info) throws JMSException {
        super(broker, usageManager, context, info);
    }

    @Override
    protected void acknowledge(ConnectionContext context, MessageAck ack, MessageReference n) throws IOException {
        this.setTimeOfLastMessageAck(System.currentTimeMillis());
        Destination q = (Destination)n.getRegionDestination();
        QueueMessageReference node = (QueueMessageReference)n;
        Queue queue = (Queue)q;
        queue.removeMessage(context, this, node, ack);
    }

    @Override
    protected boolean canDispatch(MessageReference n) throws IOException {
        boolean result = true;
        QueueMessageReference node = (QueueMessageReference)n;
        if (node.isAcked() || node.isDropped()) {
            result = false;
        }
        result = result && (this.isBrowser() || node.lock(this));
        return result;
    }

    public synchronized String toString() {
        return "QueueSubscription: consumer=" + this.info.getConsumerId() + ", destinations=" + this.destinations.size() + ", dispatched=" + this.dispatched.size() + ", delivered=" + this.prefetchExtension + ", pending=" + this.getPendingQueueSize() + ", prefetch=" + this.getPrefetchSize() + ", prefetchExtension=" + this.prefetchExtension.get();
    }

    @Override
    public int getLockPriority() {
        return this.info.getPriority();
    }

    @Override
    public boolean isLockExclusive() {
        return this.info.isExclusive();
    }

    @Override
    public void destroy() {
        this.setSlowConsumer(false);
    }

    @Override
    protected boolean isDropped(MessageReference node) {
        boolean result = false;
        if (node instanceof IndirectMessageReference) {
            QueueMessageReference qmr = (QueueMessageReference)node;
            result = qmr.isDropped();
        }
        return result;
    }
}

