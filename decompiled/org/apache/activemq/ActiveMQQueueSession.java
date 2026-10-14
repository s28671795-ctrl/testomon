/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.io.Serializable;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.IllegalStateException;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

public class ActiveMQQueueSession
implements QueueSession {
    private final QueueSession next;

    public ActiveMQQueueSession(QueueSession next) {
        this.next = next;
    }

    @Override
    public void close() throws JMSException {
        this.next.close();
    }

    @Override
    public void commit() throws JMSException {
        this.next.commit();
    }

    @Override
    public QueueBrowser createBrowser(Queue queue) throws JMSException {
        return this.next.createBrowser(queue);
    }

    @Override
    public QueueBrowser createBrowser(Queue queue, String messageSelector) throws JMSException {
        return this.next.createBrowser(queue, messageSelector);
    }

    @Override
    public BytesMessage createBytesMessage() throws JMSException {
        return this.next.createBytesMessage();
    }

    @Override
    public MessageConsumer createConsumer(Destination destination) throws JMSException {
        if (destination instanceof Topic) {
            throw new InvalidDestinationException("Topics are not supported by a QueueSession");
        }
        return this.next.createConsumer(destination);
    }

    @Override
    public MessageConsumer createConsumer(Destination destination, String messageSelector) throws JMSException {
        if (destination instanceof Topic) {
            throw new InvalidDestinationException("Topics are not supported by a QueueSession");
        }
        return this.next.createConsumer(destination, messageSelector);
    }

    @Override
    public MessageConsumer createConsumer(Destination destination, String messageSelector, boolean noLocal) throws JMSException {
        if (destination instanceof Topic) {
            throw new InvalidDestinationException("Topics are not supported by a QueueSession");
        }
        return this.next.createConsumer(destination, messageSelector, noLocal);
    }

    @Override
    public TopicSubscriber createDurableSubscriber(Topic topic, String name) throws JMSException {
        throw new IllegalStateException("Operation not supported by a QueueSession");
    }

    @Override
    public TopicSubscriber createDurableSubscriber(Topic topic, String name, String messageSelector, boolean noLocal) throws JMSException {
        throw new IllegalStateException("Operation not supported by a QueueSession");
    }

    @Override
    public MapMessage createMapMessage() throws JMSException {
        return this.next.createMapMessage();
    }

    @Override
    public Message createMessage() throws JMSException {
        return this.next.createMessage();
    }

    @Override
    public ObjectMessage createObjectMessage() throws JMSException {
        return this.next.createObjectMessage();
    }

    @Override
    public ObjectMessage createObjectMessage(Serializable object) throws JMSException {
        return this.next.createObjectMessage(object);
    }

    @Override
    public MessageProducer createProducer(Destination destination) throws JMSException {
        if (destination instanceof Topic) {
            throw new InvalidDestinationException("Topics are not supported by a QueueSession");
        }
        return this.next.createProducer(destination);
    }

    @Override
    public Queue createQueue(String queueName) throws JMSException {
        return this.next.createQueue(queueName);
    }

    @Override
    public QueueReceiver createReceiver(Queue queue) throws JMSException {
        return this.next.createReceiver(queue);
    }

    @Override
    public QueueReceiver createReceiver(Queue queue, String messageSelector) throws JMSException {
        return this.next.createReceiver(queue, messageSelector);
    }

    @Override
    public QueueSender createSender(Queue queue) throws JMSException {
        return this.next.createSender(queue);
    }

    @Override
    public StreamMessage createStreamMessage() throws JMSException {
        return this.next.createStreamMessage();
    }

    @Override
    public TemporaryQueue createTemporaryQueue() throws JMSException {
        return this.next.createTemporaryQueue();
    }

    @Override
    public TemporaryTopic createTemporaryTopic() throws JMSException {
        throw new IllegalStateException("Operation not supported by a QueueSession");
    }

    @Override
    public TextMessage createTextMessage() throws JMSException {
        return this.next.createTextMessage();
    }

    @Override
    public TextMessage createTextMessage(String text) throws JMSException {
        return this.next.createTextMessage(text);
    }

    @Override
    public Topic createTopic(String topicName) throws JMSException {
        throw new IllegalStateException("Operation not supported by a QueueSession");
    }

    public boolean equals(Object arg0) {
        if (this != arg0) {
            return this.next.equals(arg0);
        }
        return true;
    }

    @Override
    public int getAcknowledgeMode() throws JMSException {
        return this.next.getAcknowledgeMode();
    }

    @Override
    public MessageListener getMessageListener() throws JMSException {
        return this.next.getMessageListener();
    }

    @Override
    public boolean getTransacted() throws JMSException {
        return this.next.getTransacted();
    }

    public int hashCode() {
        return this.next.hashCode();
    }

    @Override
    public void recover() throws JMSException {
        this.next.recover();
    }

    @Override
    public void rollback() throws JMSException {
        this.next.rollback();
    }

    @Override
    public void run() {
        this.next.run();
    }

    @Override
    public void setMessageListener(MessageListener listener) throws JMSException {
        this.next.setMessageListener(listener);
    }

    public String toString() {
        return this.next.toString();
    }

    @Override
    public void unsubscribe(String name) throws JMSException {
        throw new IllegalStateException("Operation not supported by a QueueSession");
    }

    public QueueSession getNext() {
        return this.next;
    }
}

