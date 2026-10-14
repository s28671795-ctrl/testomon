/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.List;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.SubscriptionRecovery;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.policy.SubscriptionRecoveryPolicy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.memory.list.DestinationBasedMessageList;
import org.apache.activemq.memory.list.MessageList;
import org.apache.activemq.memory.list.SimpleMessageList;

public class FixedSizedSubscriptionRecoveryPolicy
implements SubscriptionRecoveryPolicy {
    private MessageList buffer;
    private int maximumSize = 65536;
    private boolean useSharedBuffer = true;

    @Override
    public SubscriptionRecoveryPolicy copy() {
        FixedSizedSubscriptionRecoveryPolicy rc = new FixedSizedSubscriptionRecoveryPolicy();
        rc.setMaximumSize(this.maximumSize);
        rc.setUseSharedBuffer(this.useSharedBuffer);
        return rc;
    }

    @Override
    public boolean add(ConnectionContext context, MessageReference message) throws Exception {
        this.buffer.add(message);
        return true;
    }

    @Override
    public void recover(ConnectionContext context, Topic topic, SubscriptionRecovery sub) throws Exception {
        List copy = this.buffer.getMessages(sub.getActiveMQDestination());
        if (!copy.isEmpty()) {
            for (MessageReference node : copy) {
                sub.addRecoveredMessage(context, node);
            }
        }
    }

    @Override
    public void start() throws Exception {
        this.buffer = this.createMessageList();
    }

    @Override
    public void stop() throws Exception {
        this.buffer.clear();
    }

    public MessageList getBuffer() {
        return this.buffer;
    }

    public void setBuffer(MessageList buffer) {
        this.buffer = buffer;
    }

    public int getMaximumSize() {
        return this.maximumSize;
    }

    public void setMaximumSize(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    public boolean isUseSharedBuffer() {
        return this.useSharedBuffer;
    }

    public void setUseSharedBuffer(boolean useSharedBuffer) {
        this.useSharedBuffer = useSharedBuffer;
    }

    @Override
    public Message[] browse(ActiveMQDestination destination) throws Exception {
        return this.buffer.browse(destination);
    }

    @Override
    public void setBroker(Broker broker) {
    }

    protected MessageList createMessageList() {
        if (this.useSharedBuffer) {
            return new SimpleMessageList(this.maximumSize);
        }
        return new DestinationBasedMessageList(this.maximumSize);
    }
}

