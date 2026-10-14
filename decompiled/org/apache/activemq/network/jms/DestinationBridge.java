/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network.jms;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import org.apache.activemq.Service;
import org.apache.activemq.network.jms.JmsConnector;
import org.apache.activemq.network.jms.JmsMesageConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DestinationBridge
implements Service,
MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(DestinationBridge.class);
    protected MessageConsumer consumer;
    protected AtomicBoolean started = new AtomicBoolean(false);
    protected JmsMesageConvertor jmsMessageConvertor;
    protected boolean doHandleReplyTo = true;
    protected JmsConnector jmsConnector;

    public MessageConsumer getConsumer() {
        return this.consumer;
    }

    public void setConsumer(MessageConsumer consumer) {
        this.consumer = consumer;
    }

    public void setJmsConnector(JmsConnector connector) {
        this.jmsConnector = connector;
    }

    public JmsMesageConvertor getJmsMessageConvertor() {
        return this.jmsMessageConvertor;
    }

    public void setJmsMessageConvertor(JmsMesageConvertor jmsMessageConvertor) {
        this.jmsMessageConvertor = jmsMessageConvertor;
    }

    protected Destination processReplyToDestination(Destination destination) {
        return this.jmsConnector.createReplyToBridge(destination, this.getConnnectionForConsumer(), this.getConnectionForProducer());
    }

    @Override
    public void start() throws Exception {
        if (this.started.compareAndSet(false, true)) {
            this.createConsumer();
            this.createProducer();
        }
    }

    @Override
    public void stop() throws Exception {
        this.started.set(false);
    }

    @Override
    public void onMessage(Message message) {
        int attempt = 0;
        int maxRetries = this.jmsConnector.getReconnectionPolicy().getMaxSendRetries();
        while (this.started.get() && message != null && (maxRetries == -1 || attempt <= maxRetries)) {
            try {
                Message converted;
                if (attempt++ > 0) {
                    try {
                        Thread.sleep(this.jmsConnector.getReconnectionPolicy().getNextDelay(attempt));
                    }
                    catch (InterruptedException e) {
                        break;
                    }
                }
                if (this.jmsMessageConvertor == null) continue;
                if (this.doHandleReplyTo) {
                    Destination replyTo = message.getJMSReplyTo();
                    converted = replyTo != null ? this.jmsMessageConvertor.convert(message, this.processReplyToDestination(replyTo)) : this.jmsMessageConvertor.convert(message);
                } else {
                    message.setJMSReplyTo(null);
                    converted = this.jmsMessageConvertor.convert(message);
                }
                try {
                    this.sendMessage(converted);
                }
                catch (Exception e) {
                    this.jmsConnector.handleConnectionFailure(this.getConnectionForProducer());
                    continue;
                }
                try {
                    message.acknowledge();
                }
                catch (Exception e) {
                    this.jmsConnector.handleConnectionFailure(this.getConnnectionForConsumer());
                    continue;
                }
                return;
            }
            catch (Exception e) {
                LOG.info("failed to forward message on attempt: {} reason: {} message: {}", attempt, e, message);
            }
        }
    }

    public boolean isDoHandleReplyTo() {
        return this.doHandleReplyTo;
    }

    public void setDoHandleReplyTo(boolean doHandleReplyTo) {
        this.doHandleReplyTo = doHandleReplyTo;
    }

    protected abstract MessageConsumer createConsumer() throws JMSException;

    protected abstract MessageProducer createProducer() throws JMSException;

    protected abstract void sendMessage(Message var1) throws JMSException;

    protected abstract Connection getConnnectionForConsumer();

    protected abstract Connection getConnectionForProducer();
}

