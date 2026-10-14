/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TemporaryQueue;

public class QueueRequestor {
    private QueueSession session;
    private TemporaryQueue temporaryQueue;
    private QueueSender sender;
    private QueueReceiver receiver;

    public QueueRequestor(QueueSession session, Queue queue) throws JMSException {
        if (queue == null) {
            throw new InvalidDestinationException("Invalid queue");
        }
        this.setSession(session);
        this.setTemporaryQueue(session.createTemporaryQueue());
        this.setSender(session.createSender(queue));
        this.setReceiver(session.createReceiver(this.getTemporaryQueue()));
    }

    public Message request(Message message) throws JMSException {
        message.setJMSReplyTo(this.getTemporaryQueue());
        this.getSender().send(message);
        return this.getReceiver().receive();
    }

    public void close() throws JMSException {
        this.getSession().close();
        this.getTemporaryQueue().delete();
    }

    private void setReceiver(QueueReceiver receiver) {
        this.receiver = receiver;
    }

    private QueueReceiver getReceiver() {
        return this.receiver;
    }

    private void setSender(QueueSender sender) {
        this.sender = sender;
    }

    private QueueSender getSender() {
        return this.sender;
    }

    private void setSession(QueueSession session) {
        this.session = session;
    }

    private QueueSession getSession() {
        return this.session;
    }

    private void setTemporaryQueue(TemporaryQueue temporaryQueue) {
        this.temporaryQueue = temporaryQueue;
    }

    private TemporaryQueue getTemporaryQueue() {
        return this.temporaryQueue;
    }
}

