/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.lang.reflect.Method;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Topic;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.UnresolvedDestinationTransformer;

public class DefaultUnresolvedDestinationTransformer
implements UnresolvedDestinationTransformer {
    @Override
    public ActiveMQDestination transform(Destination dest) throws JMSException {
        String queueName = ((Queue)dest).getQueueName();
        String topicName = ((Topic)dest).getTopicName();
        if (queueName == null && topicName == null) {
            throw new JMSException("Unresolvable destination: Both queue and topic names are null: " + dest);
        }
        try {
            Method isQueueMethod = dest.getClass().getMethod("isQueue", new Class[0]);
            Method isTopicMethod = dest.getClass().getMethod("isTopic", new Class[0]);
            Boolean isQueue = (Boolean)isQueueMethod.invoke((Object)dest, new Object[0]);
            Boolean isTopic = (Boolean)isTopicMethod.invoke((Object)dest, new Object[0]);
            if (isQueue.booleanValue()) {
                return new ActiveMQQueue(queueName);
            }
            if (isTopic.booleanValue()) {
                return new ActiveMQTopic(topicName);
            }
            throw new JMSException("Unresolvable destination: Neither Queue nor Topic: " + dest);
        }
        catch (Exception e) {
            throw new JMSException("Unresolvable destination: " + e.getMessage() + ": " + dest);
        }
    }

    @Override
    public ActiveMQDestination transform(String dest) throws JMSException {
        return new ActiveMQQueue(dest);
    }
}

