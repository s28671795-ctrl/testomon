/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.TopicPublisher;
import javax.jms.TopicSubscriber;
import org.apache.activemq.ActiveMQSession;

public interface CustomDestination
extends Destination {
    public MessageConsumer createConsumer(ActiveMQSession var1, String var2);

    public MessageConsumer createConsumer(ActiveMQSession var1, String var2, boolean var3);

    public TopicSubscriber createSubscriber(ActiveMQSession var1, String var2, boolean var3);

    public TopicSubscriber createDurableSubscriber(ActiveMQSession var1, String var2, String var3, boolean var4);

    public QueueReceiver createReceiver(ActiveMQSession var1, String var2);

    public MessageProducer createProducer(ActiveMQSession var1) throws JMSException;

    public TopicPublisher createPublisher(ActiveMQSession var1) throws JMSException;

    public QueueSender createSender(ActiveMQSession var1) throws JMSException;
}

