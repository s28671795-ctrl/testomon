/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueSender;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQDestination;

public class ActiveMQQueueSender
extends ActiveMQMessageProducer
implements QueueSender {
    protected ActiveMQQueueSender(ActiveMQSession session, ActiveMQDestination destination, int sendTimeout) throws JMSException {
        super(session, session.getNextProducerId(), destination, sendTimeout);
    }

    @Override
    public Queue getQueue() throws JMSException {
        return (Queue)super.getDestination();
    }

    @Override
    public void send(Queue queue, Message message) throws JMSException {
        super.send(queue, message);
    }

    @Override
    public void send(Queue queue, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        super.send(queue, message, deliveryMode, priority, timeToLive);
    }
}

