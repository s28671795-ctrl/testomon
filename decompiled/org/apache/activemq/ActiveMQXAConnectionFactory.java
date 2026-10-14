/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.net.URI;
import java.util.Properties;
import javax.jms.JMSException;
import javax.jms.XAConnection;
import javax.jms.XAConnectionFactory;
import javax.jms.XAQueueConnection;
import javax.jms.XAQueueConnectionFactory;
import javax.jms.XATopicConnection;
import javax.jms.XATopicConnectionFactory;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQXAConnection;
import org.apache.activemq.management.JMSStatsImpl;
import org.apache.activemq.transport.Transport;

public class ActiveMQXAConnectionFactory
extends ActiveMQConnectionFactory
implements XAConnectionFactory,
XAQueueConnectionFactory,
XATopicConnectionFactory {
    public ActiveMQXAConnectionFactory() {
    }

    public ActiveMQXAConnectionFactory(String userName, String password, String brokerURL) {
        super(userName, password, brokerURL);
    }

    public ActiveMQXAConnectionFactory(String userName, String password, URI brokerURL) {
        super(userName, password, brokerURL);
    }

    public ActiveMQXAConnectionFactory(String brokerURL) {
        super(brokerURL);
    }

    public ActiveMQXAConnectionFactory(URI brokerURL) {
        super(brokerURL);
    }

    @Override
    public XAConnection createXAConnection() throws JMSException {
        return (XAConnection)((Object)this.createActiveMQConnection());
    }

    @Override
    public XAConnection createXAConnection(String userName, String password) throws JMSException {
        return (XAConnection)((Object)this.createActiveMQConnection(userName, password));
    }

    @Override
    public XAQueueConnection createXAQueueConnection() throws JMSException {
        return (XAQueueConnection)((Object)this.createActiveMQConnection());
    }

    @Override
    public XAQueueConnection createXAQueueConnection(String userName, String password) throws JMSException {
        return (XAQueueConnection)((Object)this.createActiveMQConnection(userName, password));
    }

    @Override
    public XATopicConnection createXATopicConnection() throws JMSException {
        return (XATopicConnection)((Object)this.createActiveMQConnection());
    }

    @Override
    public XATopicConnection createXATopicConnection(String userName, String password) throws JMSException {
        return (XATopicConnection)((Object)this.createActiveMQConnection(userName, password));
    }

    @Override
    protected ActiveMQConnection createActiveMQConnection(Transport transport, JMSStatsImpl stats) throws Exception {
        ActiveMQXAConnection connection = new ActiveMQXAConnection(transport, this.getClientIdGenerator(), this.getConnectionIdGenerator(), stats);
        this.configureXAConnection(connection);
        return connection;
    }

    private void configureXAConnection(ActiveMQXAConnection connection) {
        connection.setXaAckMode(this.xaAckMode);
    }

    public int getXaAckMode() {
        return this.xaAckMode;
    }

    public void setXaAckMode(int xaAckMode) {
        this.xaAckMode = xaAckMode;
    }

    @Override
    public void populateProperties(Properties props) {
        super.populateProperties(props);
        props.put("xaAckMode", Integer.toString(this.xaAckMode));
    }
}

