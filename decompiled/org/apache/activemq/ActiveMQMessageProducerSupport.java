/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import javax.jms.Destination;
import javax.jms.IllegalStateException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.Closeable;

public abstract class ActiveMQMessageProducerSupport
implements MessageProducer,
Closeable {
    protected ActiveMQSession session;
    protected boolean disableMessageID;
    protected boolean disableMessageTimestamp;
    protected int defaultDeliveryMode;
    protected int defaultPriority;
    protected long defaultTimeToLive;
    protected int sendTimeout = 0;

    public ActiveMQMessageProducerSupport(ActiveMQSession session) {
        this.session = session;
        this.disableMessageTimestamp = session.connection.isDisableTimeStampsByDefault();
    }

    @Override
    public void setDisableMessageID(boolean value) throws JMSException {
        this.checkClosed();
        this.disableMessageID = value;
    }

    @Override
    public boolean getDisableMessageID() throws JMSException {
        this.checkClosed();
        return this.disableMessageID;
    }

    @Override
    public void setDisableMessageTimestamp(boolean value) throws JMSException {
        this.checkClosed();
        this.disableMessageTimestamp = value;
    }

    @Override
    public boolean getDisableMessageTimestamp() throws JMSException {
        this.checkClosed();
        return this.disableMessageTimestamp;
    }

    @Override
    public void setDeliveryMode(int newDeliveryMode) throws JMSException {
        if (newDeliveryMode != 2 && newDeliveryMode != 1) {
            throw new IllegalStateException("unknown delivery mode: " + newDeliveryMode);
        }
        this.checkClosed();
        this.defaultDeliveryMode = newDeliveryMode;
    }

    @Override
    public int getDeliveryMode() throws JMSException {
        this.checkClosed();
        return this.defaultDeliveryMode;
    }

    @Override
    public void setPriority(int newDefaultPriority) throws JMSException {
        if (newDefaultPriority < 0 || newDefaultPriority > 9) {
            throw new IllegalStateException("default priority must be a value between 0 and 9");
        }
        this.checkClosed();
        this.defaultPriority = newDefaultPriority;
    }

    @Override
    public int getPriority() throws JMSException {
        this.checkClosed();
        return this.defaultPriority;
    }

    @Override
    public void setTimeToLive(long timeToLive) throws JMSException {
        if (timeToLive < 0L) {
            throw new IllegalStateException("cannot set a negative timeToLive");
        }
        this.checkClosed();
        this.defaultTimeToLive = timeToLive;
    }

    @Override
    public long getTimeToLive() throws JMSException {
        this.checkClosed();
        return this.defaultTimeToLive;
    }

    @Override
    public void send(Message message) throws JMSException {
        this.send(this.getDestination(), message, this.defaultDeliveryMode, this.defaultPriority, this.defaultTimeToLive);
    }

    @Override
    public void send(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        this.send(this.getDestination(), message, deliveryMode, priority, timeToLive);
    }

    @Override
    public void send(Destination destination, Message message) throws JMSException {
        this.send(destination, message, this.defaultDeliveryMode, this.defaultPriority, this.defaultTimeToLive);
    }

    protected abstract void checkClosed() throws IllegalStateException;

    public int getSendTimeout() {
        return this.sendTimeout;
    }

    public void setSendTimeout(int sendTimeout) {
        this.sendTimeout = sendTimeout;
    }
}

