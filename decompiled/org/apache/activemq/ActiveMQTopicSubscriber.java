/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerId;

public class ActiveMQTopicSubscriber
extends ActiveMQMessageConsumer
implements TopicSubscriber {
    protected ActiveMQTopicSubscriber(ActiveMQSession theSession, ConsumerId consumerId, ActiveMQDestination dest, String name, String selector, int prefetch, int maximumPendingMessageCount, boolean noLocalValue, boolean browserValue, boolean asyncDispatch) throws JMSException {
        super(theSession, consumerId, dest, name, selector, prefetch, maximumPendingMessageCount, noLocalValue, browserValue, asyncDispatch, null);
    }

    @Override
    public Topic getTopic() throws JMSException {
        this.checkClosed();
        return (Topic)((Object)super.getDestination());
    }

    @Override
    public boolean getNoLocal() throws JMSException {
        this.checkClosed();
        return super.isNoLocal();
    }
}

