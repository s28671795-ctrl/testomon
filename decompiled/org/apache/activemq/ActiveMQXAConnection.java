/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.XAConnection;
import javax.jms.XAQueueConnection;
import javax.jms.XAQueueSession;
import javax.jms.XASession;
import javax.jms.XATopicConnection;
import javax.jms.XATopicSession;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQXASession;
import org.apache.activemq.management.JMSStatsImpl;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.util.IdGenerator;

public class ActiveMQXAConnection
extends ActiveMQConnection
implements XATopicConnection,
XAQueueConnection,
XAConnection {
    private int xaAckMode;

    protected ActiveMQXAConnection(Transport transport, IdGenerator clientIdGenerator, IdGenerator connectionIdGenerator, JMSStatsImpl factoryStats) throws Exception {
        super(transport, clientIdGenerator, connectionIdGenerator, factoryStats);
    }

    @Override
    public XASession createXASession() throws JMSException {
        return (XASession)this.createSession(true, 0);
    }

    @Override
    public XATopicSession createXATopicSession() throws JMSException {
        return (XATopicSession)this.createSession(true, 0);
    }

    @Override
    public XAQueueSession createXAQueueSession() throws JMSException {
        return (XAQueueSession)this.createSession(true, 0);
    }

    @Override
    public Session createSession(boolean transacted, int acknowledgeMode) throws JMSException {
        this.checkClosedOrFailed();
        this.ensureConnectionInfoSent();
        return new ActiveMQXASession(this, this.getNextSessionId(), this.getAckMode(), this.isDispatchAsync());
    }

    private int getAckMode() {
        return this.xaAckMode > 0 ? this.xaAckMode : 0;
    }

    public void setXaAckMode(int xaAckMode) {
        this.xaAckMode = xaAckMode;
    }

    public int getXaAckMode() {
        return this.xaAckMode;
    }
}

