/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.jms.Destination;
import javax.jms.IllegalStateException;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.Message;
import org.apache.activemq.ActiveMQMessageProducerSupport;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.AsyncCallback;
import org.apache.activemq.ConfigurationException;
import org.apache.activemq.Disposable;
import org.apache.activemq.MessageTransformer;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ProducerAck;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.management.JMSProducerStatsImpl;
import org.apache.activemq.management.StatsCapable;
import org.apache.activemq.management.StatsImpl;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.util.IntrospectionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQMessageProducer
extends ActiveMQMessageProducerSupport
implements StatsCapable,
Disposable {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQMessageProducer.class);
    protected ProducerInfo info;
    protected boolean closed;
    private final JMSProducerStatsImpl stats;
    private AtomicLong messageSequence;
    private final long startTime;
    private MessageTransformer transformer;
    private MemoryUsage producerWindow;

    protected ActiveMQMessageProducer(ActiveMQSession session, ProducerId producerId, ActiveMQDestination destination, int sendTimeout) throws JMSException {
        super(session);
        this.info = new ProducerInfo(producerId);
        this.info.setWindowSize(session.connection.getProducerWindowSize());
        if (destination != null && destination.getOptions() != null) {
            Map<String, Object> options = IntrospectionSupport.extractProperties(new HashMap<String, String>(destination.getOptions()), "producer.");
            IntrospectionSupport.setProperties(this.info, options);
            if (options.size() > 0) {
                String msg = "There are " + options.size() + " producer options that couldn't be set on the producer. Check the options are spelled correctly. Unknown parameters=[" + options + "]. This producer cannot be started.";
                LOG.warn(msg);
                throw new ConfigurationException(msg);
            }
        }
        this.info.setDestination(destination);
        if (session.connection.getProtocolVersion() >= 3 && this.info.getWindowSize() > 0) {
            this.producerWindow = new MemoryUsage("Producer Window: " + producerId);
            this.producerWindow.setExecutor(session.getConnectionExecutor());
            this.producerWindow.setLimit(this.info.getWindowSize());
            this.producerWindow.start();
        }
        this.defaultDeliveryMode = 2;
        this.defaultPriority = 4;
        this.defaultTimeToLive = 0L;
        this.startTime = System.currentTimeMillis();
        this.messageSequence = new AtomicLong(0L);
        this.stats = new JMSProducerStatsImpl(session.getSessionStats(), destination);
        try {
            this.session.addProducer(this);
            this.session.syncSendPacket(this.info);
        }
        catch (JMSException e) {
            this.session.removeProducer(this);
            throw e;
        }
        this.setSendTimeout(sendTimeout);
        this.setTransformer(session.getTransformer());
    }

    @Override
    public StatsImpl getStats() {
        return this.stats;
    }

    public JMSProducerStatsImpl getProducerStats() {
        return this.stats;
    }

    @Override
    public Destination getDestination() throws JMSException {
        this.checkClosed();
        return this.info.getDestination();
    }

    @Override
    public void close() throws JMSException {
        if (!this.closed) {
            this.dispose();
            this.session.asyncSendPacket(this.info.createRemoveCommand());
        }
    }

    @Override
    public void dispose() {
        if (!this.closed) {
            this.session.removeProducer(this);
            if (this.producerWindow != null) {
                this.producerWindow.stop();
            }
            this.closed = true;
        }
    }

    @Override
    protected void checkClosed() throws IllegalStateException {
        if (this.closed) {
            throw new IllegalStateException("The producer is closed");
        }
    }

    @Override
    public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        this.send(destination, message, deliveryMode, priority, timeToLive, null);
    }

    public void send(Message message, AsyncCallback onComplete) throws JMSException {
        this.send(this.getDestination(), message, this.defaultDeliveryMode, this.defaultPriority, this.defaultTimeToLive, onComplete);
    }

    public void send(Destination destination, Message message, AsyncCallback onComplete) throws JMSException {
        this.send(destination, message, this.defaultDeliveryMode, this.defaultPriority, this.defaultTimeToLive, onComplete);
    }

    public void send(Message message, int deliveryMode, int priority, long timeToLive, AsyncCallback onComplete) throws JMSException {
        this.send(this.getDestination(), message, deliveryMode, priority, timeToLive, onComplete);
    }

    public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, AsyncCallback onComplete) throws JMSException {
        Message transformedMessage;
        ActiveMQDestination dest;
        this.checkClosed();
        if (destination == null) {
            if (this.info.getDestination() == null) {
                throw new UnsupportedOperationException("A destination must be specified.");
            }
            throw new InvalidDestinationException("Don't understand null destinations");
        }
        if (destination.equals(this.info.getDestination())) {
            dest = (ActiveMQDestination)destination;
        } else if (this.info.getDestination() == null) {
            dest = ActiveMQDestination.transform(destination);
        } else {
            throw new UnsupportedOperationException("This producer can only send messages to: " + this.info.getDestination().getPhysicalName());
        }
        if (dest == null) {
            throw new JMSException("No destination specified");
        }
        if (this.transformer != null && (transformedMessage = this.transformer.producerTransform(this.session, this, message)) != null) {
            message = transformedMessage;
        }
        if (this.producerWindow != null) {
            try {
                this.producerWindow.waitForSpace();
            }
            catch (InterruptedException e) {
                throw new JMSException("Send aborted due to thread interrupt.");
            }
        }
        this.session.send(this, dest, message, deliveryMode, priority, timeToLive, this.producerWindow, this.sendTimeout, onComplete);
        this.stats.onMessage();
    }

    public MessageTransformer getTransformer() {
        return this.transformer;
    }

    public void setTransformer(MessageTransformer transformer) {
        this.transformer = transformer;
    }

    protected long getStartTime() {
        return this.startTime;
    }

    protected long getMessageSequence() {
        return this.messageSequence.incrementAndGet();
    }

    protected void setMessageSequence(AtomicLong messageSequence) {
        this.messageSequence = messageSequence;
    }

    protected ProducerInfo getProducerInfo() {
        return this.info != null ? this.info : null;
    }

    protected void setProducerInfo(ProducerInfo info) {
        this.info = info;
    }

    public String toString() {
        return "ActiveMQMessageProducer { value=" + this.info.getProducerId() + " }";
    }

    public void onProducerAck(ProducerAck pa) {
        if (this.producerWindow != null) {
            this.producerWindow.decreaseUsage(pa.getSize());
        }
    }
}

