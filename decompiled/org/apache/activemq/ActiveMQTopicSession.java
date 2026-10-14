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
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

public class ActiveMQTopicSession
implements TopicSession {
    private final TopicSession next;

    public ActiveMQTopicSession(TopicSession next) {
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
        throw new IllegalStateException("Operation not supported by a TopicSession");
    }

    @Override
    public QueueBrowser createBrowser(Queue queue, String messageSelector) throws JMSException {
        throw new IllegalStateException("Operation not supported by a TopicSession");
    }

    @Override
    public BytesMessage createBytesMessage() throws JMSException {
        return this.next.createBytesMessage();
    }

    @Override
    public MessageConsumer createConsumer(Destination destination) throws JMSException {
        if (destination instanceof Queue) {
            throw new InvalidDestinationException("Queues are not supported by a TopicSession");
        }
        return this.next.createConsumer(destination);
    }

    @Override
    public MessageConsumer createConsumer(Destination destination, String messageSelector) throws JMSException {
        if (destination instanceof Queue) {
            throw new InvalidDestinationException("Queues are not supported by a TopicSession");
        }
        return this.next.createConsumer(destination, messageSelector);
    }

    @Override
    public MessageConsumer createConsumer(Destination destination, String messageSelector, boolean noLocal) throws JMSException {
        if (destination instanceof Queue) {
            throw new InvalidDestinationException("Queues are not supported by a TopicSession");
        }
        return this.next.createConsumer(destination, messageSelector, noLocal);
    }

    @Override
    public TopicSubscriber createDurableSubscriber(Topic topic, String name) throws JMSException {
        return this.next.createDurableSubscriber(topic, name);
    }

    @Override
    public TopicSubscriber createDurableSubscriber(Topic topic, String name, String messageSelector, boolean noLocal) throws JMSException {
        return this.next.createDurableSubscriber(topic, name, messageSelector, noLocal);
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
        if (destination instanceof Queue) {
            throw new InvalidDestinationException("Queues are not supported by a TopicSession");
        }
        return this.next.createProducer(destination);
    }

    @Override
    public TopicPublisher createPublisher(Topic topic) throws JMSException {
        return this.next.createPublisher(topic);
    }

    @Override
    public Queue createQueue(String queueName) throws JMSException {
        throw new IllegalStateException("Operation not supported by a TopicSession");
    }

    @Override
    public StreamMessage createStreamMessage() throws JMSException {
        return this.next.createStreamMessage();
    }

    @Override
    public TopicSubscriber createSubscriber(Topic topic) throws JMSException {
        return this.next.createSubscriber(topic);
    }

    @Override
    public TopicSubscriber createSubscriber(Topic topic, String messageSelector, boolean noLocal) throws JMSException {
        return this.next.createSubscriber(topic, messageSelector, noLocal);
    }

    @Override
    public TemporaryQueue createTemporaryQueue() throws JMSException {
        throw new IllegalStateException("Operation not supported by a TopicSession");
    }

    @Override
    public TemporaryTopic createTemporaryTopic() throws JMSException {
        return this.next.createTemporaryTopic();
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
        return this.next.createTopic(topicName);
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
        this.next.unsubscribe(name);
    }

    public TopicSession getNext() {
        return this.next;
    }
}

