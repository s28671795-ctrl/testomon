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
import javax.jms.MessageListener;
import javax.jms.Session;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.Service;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.advisory.ConsumerEvent;
import org.apache.activemq.advisory.ConsumerListener;
import org.apache.activemq.advisory.ConsumerStartedEvent;
import org.apache.activemq.advisory.ConsumerStoppedEvent;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.RemoveInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerEventSource
implements Service,
MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerEventSource.class);
    private final Connection connection;
    private final ActiveMQDestination destination;
    private ConsumerListener listener;
    private AtomicBoolean started = new AtomicBoolean(false);
    private AtomicInteger consumerCount = new AtomicInteger();
    private Session session;
    private ActiveMQMessageConsumer consumer;

    public ConsumerEventSource(Connection connection, Destination destination) throws JMSException {
        this.connection = connection;
        this.destination = ActiveMQDestination.transform(destination);
    }

    public void setConsumerListener(ConsumerListener listener) {
        this.listener = listener;
    }

    public String getConsumerId() {
        return this.consumer != null ? this.consumer.getConsumerId().toString() : "NOT_SET";
    }

    @Override
    public void start() throws Exception {
        if (this.started.compareAndSet(false, true)) {
            this.session = this.connection.createSession(false, 1);
            ActiveMQTopic advisoryTopic = AdvisorySupport.getConsumerAdvisoryTopic(this.destination);
            this.consumer = (ActiveMQMessageConsumer)this.session.createConsumer(advisoryTopic);
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
            if (command instanceof ConsumerInfo) {
                count = this.consumerCount.incrementAndGet();
                count = this.extractConsumerCountFromMessage(message, count);
                this.fireConsumerEvent(new ConsumerStartedEvent(this, this.destination, (ConsumerInfo)command, count));
            } else if (command instanceof RemoveInfo) {
                RemoveInfo removeInfo = (RemoveInfo)command;
                if (removeInfo.isConsumerRemove()) {
                    count = this.consumerCount.decrementAndGet();
                    count = this.extractConsumerCountFromMessage(message, count);
                    this.fireConsumerEvent(new ConsumerStoppedEvent(this, this.destination, (ConsumerId)removeInfo.getObjectId(), count));
                }
            } else {
                LOG.warn("Unknown command: " + command);
            }
        } else {
            LOG.warn("Unknown message type: " + message + ". Message ignored");
        }
    }

    protected int extractConsumerCountFromMessage(Message message, int count) {
        try {
            Object value = message.getObjectProperty("consumerCount");
            if (value instanceof Number) {
                Number n = (Number)value;
                return n.intValue();
            }
            LOG.warn("No consumerCount header available on the message: " + message);
        }
        catch (Exception e) {
            LOG.warn("Failed to extract consumerCount from message: " + message + ".Reason: " + e, e);
        }
        return count;
    }

    protected void fireConsumerEvent(ConsumerEvent event) {
        if (this.listener != null) {
            this.listener.onConsumerEvent(event);
        }
    }
}

