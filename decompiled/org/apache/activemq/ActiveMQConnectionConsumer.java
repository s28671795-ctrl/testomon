/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.ConnectionConsumer;
import javax.jms.IllegalStateException;
import javax.jms.JMSException;
import javax.jms.ServerSession;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQDispatcher;
import org.apache.activemq.ActiveMQQueueSession;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.ActiveMQTopicSession;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.MessageDispatch;

public class ActiveMQConnectionConsumer
implements ConnectionConsumer,
ActiveMQDispatcher {
    private ActiveMQConnection connection;
    private ServerSessionPool sessionPool;
    private ConsumerInfo consumerInfo;
    private boolean closed;

    protected ActiveMQConnectionConsumer(ActiveMQConnection theConnection, ServerSessionPool theSessionPool, ConsumerInfo theConsumerInfo) throws JMSException {
        this.connection = theConnection;
        this.sessionPool = theSessionPool;
        this.consumerInfo = theConsumerInfo;
        this.connection.addConnectionConsumer(this);
        this.connection.addDispatcher(this.consumerInfo.getConsumerId(), this);
        this.connection.syncSendPacket(this.consumerInfo);
    }

    @Override
    public ServerSessionPool getServerSessionPool() throws JMSException {
        if (this.closed) {
            throw new IllegalStateException("The Connection Consumer is closed");
        }
        return this.sessionPool;
    }

    @Override
    public void close() throws JMSException {
        if (!this.closed) {
            this.dispose();
            this.connection.asyncSendPacket(this.consumerInfo.createRemoveCommand());
        }
    }

    public void dispose() {
        if (!this.closed) {
            this.connection.removeDispatcher(this.consumerInfo.getConsumerId());
            this.connection.removeConnectionConsumer(this);
            this.closed = true;
        }
    }

    @Override
    public void dispatch(MessageDispatch messageDispatch) {
        try {
            messageDispatch.setConsumer(this);
            ServerSession serverSession = this.sessionPool.getServerSession();
            Session s2 = serverSession.getSession();
            ActiveMQSession session = null;
            if (s2 instanceof ActiveMQSession) {
                session = (ActiveMQSession)s2;
            } else if (s2 instanceof ActiveMQTopicSession) {
                ActiveMQTopicSession topicSession = (ActiveMQTopicSession)s2;
                session = (ActiveMQSession)topicSession.getNext();
            } else if (s2 instanceof ActiveMQQueueSession) {
                ActiveMQQueueSession queueSession = (ActiveMQQueueSession)s2;
                session = (ActiveMQSession)queueSession.getNext();
            } else {
                this.connection.onClientInternalException(new JMSException("Session pool provided an invalid session type: " + s2.getClass()));
                return;
            }
            session.dispatch(messageDispatch);
            serverSession.start();
        }
        catch (JMSException e) {
            this.connection.onAsyncException(e);
        }
    }

    public String toString() {
        return "ActiveMQConnectionConsumer { value=" + this.consumerInfo.getConsumerId() + " }";
    }

    public void clearMessagesInProgress(AtomicInteger transportInterruptionProcessingComplete) {
    }

    public ConsumerInfo getConsumerInfo() {
        return this.consumerInfo;
    }
}

