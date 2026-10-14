/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.ArrayList;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.SubscriptionRecovery;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.policy.SubscriptionRecoveryPolicy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.DestinationFilter;

public class FixedCountSubscriptionRecoveryPolicy
implements SubscriptionRecoveryPolicy {
    private volatile MessageReference[] messages;
    private int maximumSize = 100;
    private int tail;

    @Override
    public SubscriptionRecoveryPolicy copy() {
        FixedCountSubscriptionRecoveryPolicy rc = new FixedCountSubscriptionRecoveryPolicy();
        rc.setMaximumSize(this.maximumSize);
        return rc;
    }

    @Override
    public synchronized boolean add(ConnectionContext context, MessageReference node) throws Exception {
        this.messages[this.tail++] = node;
        if (this.tail >= this.messages.length) {
            this.tail = 0;
        }
        return true;
    }

    @Override
    public synchronized void recover(ConnectionContext context, Topic topic, SubscriptionRecovery sub) throws Exception {
        int t2 = this.tail;
        if (this.messages[t2] == null) {
            t2 = 0;
        }
        if (this.messages[t2] == null) {
            return;
        }
        do {
            MessageReference node = this.messages[t2];
            sub.addRecoveredMessage(context, node);
            if (++t2 < this.messages.length) continue;
            t2 = 0;
        } while (t2 != this.tail);
    }

    @Override
    public void start() throws Exception {
        this.messages = new MessageReference[this.maximumSize];
    }

    @Override
    public void stop() throws Exception {
        this.messages = null;
    }

    public int getMaximumSize() {
        return this.maximumSize;
    }

    public void setMaximumSize(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    @Override
    public synchronized Message[] browse(ActiveMQDestination destination) throws Exception {
        ArrayList<Message> result = new ArrayList<Message>();
        DestinationFilter filter2 = DestinationFilter.parseFilter(destination);
        int t2 = this.tail;
        if (this.messages[t2] == null) {
            t2 = 0;
        }
        if (this.messages[t2] != null) {
            do {
                MessageReference ref;
                Message message;
                if (filter2.matches((message = (ref = this.messages[t2]).getMessage()).getDestination())) {
                    result.add(message);
                }
                if (++t2 < this.messages.length) continue;
                t2 = 0;
            } while (t2 != this.tail);
        }
        return result.toArray(new Message[result.size()]);
    }

    @Override
    public void setBroker(Broker broker) {
    }
}

