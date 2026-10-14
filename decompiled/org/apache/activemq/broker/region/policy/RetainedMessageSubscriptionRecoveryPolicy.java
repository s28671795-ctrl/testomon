/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.SubscriptionRecovery;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.policy.SubscriptionRecoveryPolicy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.DestinationFilter;

public class RetainedMessageSubscriptionRecoveryPolicy
implements SubscriptionRecoveryPolicy {
    public static final String RETAIN_PROPERTY = "ActiveMQ.Retain";
    public static final String RETAINED_PROPERTY = "ActiveMQ.Retained";
    private volatile MessageReference retainedMessage;
    private SubscriptionRecoveryPolicy wrapped;

    public RetainedMessageSubscriptionRecoveryPolicy(SubscriptionRecoveryPolicy wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean add(ConnectionContext context, MessageReference node) throws Exception {
        boolean retain;
        Message message = node.getMessage();
        Object retainValue = message.getProperty(RETAIN_PROPERTY);
        boolean bl = retain = retainValue != null && Boolean.parseBoolean(retainValue.toString());
        if (retain) {
            if (message.getContent().getLength() > 0) {
                this.retainedMessage = message.copy();
                this.retainedMessage.getMessage().removeProperty(RETAIN_PROPERTY);
                this.retainedMessage.getMessage().setProperty(RETAINED_PROPERTY, true);
            } else {
                this.retainedMessage = null;
            }
            node.getMessage().removeProperty(RETAIN_PROPERTY);
        }
        return this.wrapped == null ? true : this.wrapped.add(context, node);
    }

    @Override
    public void recover(ConnectionContext context, Topic topic, SubscriptionRecovery sub) throws Exception {
        if (this.retainedMessage != null) {
            sub.addRecoveredMessage(context, this.retainedMessage);
        }
        if (this.wrapped != null) {
            boolean recover2 = true;
            if (sub instanceof DurableTopicSubscription && !((DurableTopicSubscription)sub).isEmpty(topic)) {
                recover2 = false;
            }
            if (recover2) {
                this.wrapped.recover(context, topic, sub);
            }
        }
    }

    @Override
    public void start() throws Exception {
        if (this.wrapped != null) {
            this.wrapped.start();
        }
    }

    @Override
    public void stop() throws Exception {
        if (this.wrapped != null) {
            this.wrapped.stop();
        }
    }

    @Override
    public Message[] browse(ActiveMQDestination destination) throws Exception {
        Message[] wrappedMessages;
        DestinationFilter filter2;
        ArrayList<Message> result = new ArrayList<Message>();
        if (this.retainedMessage != null && (filter2 = DestinationFilter.parseFilter(destination)).matches(this.retainedMessage.getMessage().getDestination())) {
            result.add(this.retainedMessage.getMessage());
        }
        Message[] messages = result.toArray(new Message[result.size()]);
        if (this.wrapped != null && (wrappedMessages = this.wrapped.browse(destination)) != null && wrappedMessages.length > 0) {
            int origLen = messages.length;
            messages = Arrays.copyOf(messages, origLen + wrappedMessages.length);
            System.arraycopy(wrappedMessages, 0, messages, origLen, wrappedMessages.length);
        }
        return messages;
    }

    @Override
    public SubscriptionRecoveryPolicy copy() {
        return new RetainedMessageSubscriptionRecoveryPolicy(this.wrapped);
    }

    @Override
    public void setBroker(Broker broker) {
    }

    public void setWrapped(SubscriptionRecoveryPolicy wrapped) {
        this.wrapped = wrapped;
    }

    public SubscriptionRecoveryPolicy getWrapped() {
        return this.wrapped;
    }
}

