/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQDestination;

public class ActiveMQTopicPublisher
extends ActiveMQMessageProducer
implements TopicPublisher {
    protected ActiveMQTopicPublisher(ActiveMQSession session, ActiveMQDestination destination, int sendTimeout) throws JMSException {
        super(session, session.getNextProducerId(), destination, sendTimeout);
    }

    @Override
    public Topic getTopic() throws JMSException {
        return (Topic)super.getDestination();
    }

    @Override
    public void publish(Message message) throws JMSException {
        super.send(message);
    }

    @Override
    public void publish(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        super.send(message, deliveryMode, priority, timeToLive);
    }

    @Override
    public void publish(Topic topic, Message message) throws JMSException {
        super.send(topic, message);
    }

    @Override
    public void publish(Topic topic, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        super.send(topic, message, deliveryMode, priority, timeToLive);
    }
}

