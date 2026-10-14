/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import javax.jms.JMSException;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicSession;
import javax.jms.TransactionInProgressException;
import javax.jms.XAQueueSession;
import javax.jms.XATopicSession;
import javax.transaction.xa.XAResource;
import org.apache.activemq.ActiveMQQueueSession;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.ActiveMQTopicSession;
import org.apache.activemq.ActiveMQXAConnection;
import org.apache.activemq.command.SessionId;

public class ActiveMQXASession
extends ActiveMQSession
implements QueueSession,
TopicSession,
XAQueueSession,
XATopicSession {
    public ActiveMQXASession(ActiveMQXAConnection connection, SessionId sessionId, int theAcknowlegeMode, boolean dispatchAsync) throws JMSException {
        super(connection, sessionId, theAcknowlegeMode, dispatchAsync);
    }

    @Override
    public boolean getTransacted() throws JMSException {
        this.checkClosed();
        return this.getTransactionContext().isInXATransaction();
    }

    @Override
    public void rollback() throws JMSException {
        this.checkClosed();
        throw new TransactionInProgressException("Cannot rollback() inside an XASession");
    }

    @Override
    public void commit() throws JMSException {
        this.checkClosed();
        throw new TransactionInProgressException("Cannot commit() inside an XASession");
    }

    @Override
    public Session getSession() throws JMSException {
        return this;
    }

    @Override
    public XAResource getXAResource() {
        return this.getTransactionContext();
    }

    @Override
    public QueueSession getQueueSession() throws JMSException {
        return new ActiveMQQueueSession(this);
    }

    @Override
    public TopicSession getTopicSession() throws JMSException {
        return new ActiveMQTopicSession(this);
    }

    @Override
    public boolean isAutoAcknowledge() {
        return true;
    }

    @Override
    protected void doStartTransaction() throws JMSException {
    }
}

