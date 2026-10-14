/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import org.apache.activemq.Service;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.advisory.ProducerEvent;
import org.apache.activemq.advisory.ProducerListener;
import org.apache.activemq.advisory.ProducerStartedEvent;
import org.apache.activemq.advisory.ProducerStoppedEvent;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.RemoveInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerEventSource
implements Service,
MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(ProducerEventSource.class);
    private final Connection connection;
    private final ActiveMQDestination destination;
    private ProducerListener listener;
    private AtomicBoolean started = new AtomicBoolean(false);
    private AtomicInteger producerCount = new AtomicInteger();
    private Session session;
    private MessageConsumer consumer;

    public ProducerEventSource(Connection connection, Destination destination) throws JMSException {
        this.connection = connection;
        this.destination = ActiveMQDestination.transform(destination);
    }

    public void setProducerListener(ProducerListener listener) {
        this.listener = listener;
    }

    @Override
    public void start() throws Exception {
        if (this.started.compareAndSet(false, true)) {
            this.session = this.connection.createSession(false, 1);
            ActiveMQTopic advisoryTopic = AdvisorySupport.getProducerAdvisoryTopic(this.destination);
            this.consumer = this.session.createConsumer(advisoryTopic);
            this.consumer.setMessageListener(this);
        }
    }

    @Override
    public void stop() throws Exception {
        if (this.started.compareAndSet(true, false) && this.session != null) {
            this.session.close();
        }
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof ActiveMQMessage) {
            ActiveMQMessage activeMessage = (ActiveMQMessage)message;
            DataStructure command = activeMessage.getDataStructure();
            int count = 0;
            if (command instanceof ProducerInfo) {
                count = this.producerCount.incrementAndGet();
                count = this.extractProducerCountFromMessage(message, count);
                this.fireProducerEvent(new ProducerStartedEvent(this, this.destination, (ProducerInfo)command, count));
            } else if (command instanceof RemoveInfo) {
                RemoveInfo removeInfo = (RemoveInfo)command;
                if (removeInfo.isProducerRemove()) {
                    count = this.producerCount.decrementAndGet();
                    count = this.extractProducerCountFromMessage(message, count);
                    this.fireProducerEvent(new ProducerStoppedEvent(this, this.destination, (ProducerId)removeInfo.getObjectId(), count));
                }
            } else {
                LOG.warn("Unknown command: " + command);
            }
        } else {
            LOG.warn("Unknown message type: " + message + ". Message ignored");
        }
    }

    protected int extractProducerCountFromMessage(Message message, int count) {
        try {
            Object value = message.getObjectProperty("producerCount");
            if (value instanceof Number) {
                Number n = (Number)value;
                return n.intValue();
            }
            LOG.warn("No producerCount header available on the message: " + message);
        }
        catch (Exception e) {
            LOG.warn("Failed to extract producerCount from message: " + message + ".Reason: " + e, e);
        }
        return count;
    }

    protected void fireProducerEvent(ProducerEvent event) {
        if (this.listener != null) {
            this.listener.onProducerEvent(event);
        }
    }
}

