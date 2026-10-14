/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.TemporaryQueue;
import javax.naming.NamingException;
import org.apache.activemq.network.jms.InboundQueueBridge;
import org.apache.activemq.network.jms.JmsConnector;
import org.apache.activemq.network.jms.OutboundQueueBridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJmsQueueConnector
extends JmsConnector {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleJmsQueueConnector.class);
    private String outboundQueueConnectionFactoryName;
    private String localConnectionFactoryName;
    private QueueConnectionFactory outboundQueueConnectionFactory;
    private QueueConnectionFactory localQueueConnectionFactory;
    private InboundQueueBridge[] inboundQueueBridges;
    private OutboundQueueBridge[] outboundQueueBridges;

    public InboundQueueBridge[] getInboundQueueBridges() {
        return this.inboundQueueBridges;
    }

    public void setInboundQueueBridges(InboundQueueBridge[] inboundQueueBridges) {
        this.inboundQueueBridges = inboundQueueBridges;
    }

    public OutboundQueueBridge[] getOutboundQueueBridges() {
        return this.outboundQueueBridges;
    }

    public void setOutboundQueueBridges(OutboundQueueBridge[] outboundQueueBridges) {
        this.outboundQueueBridges = outboundQueueBridges;
    }

    public QueueConnectionFactory getLocalQueueConnectionFactory() {
        return this.localQueueConnectionFactory;
    }

    public void setLocalQueueConnectionFactory(QueueConnectionFactory localConnectionFactory) {
        this.localQueueConnectionFactory = localConnectionFactory;
    }

    public QueueConnectionFactory getOutboundQueueConnectionFactory() {
        return this.outboundQueueConnectionFactory;
    }

    public String getOutboundQueueConnectionFactoryName() {
        return this.outboundQueueConnectionFactoryName;
    }

    public void setOutboundQueueConnectionFactoryName(String foreignQueueConnectionFactoryName) {
        this.outboundQueueConnectionFactoryName = foreignQueueConnectionFactoryName;
    }

    public String getLocalConnectionFactoryName() {
        return this.localConnectionFactoryName;
    }

    public void setLocalConnectionFactoryName(String localConnectionFactoryName) {
        this.localConnectionFactoryName = localConnectionFactoryName;
    }

    public QueueConnection getLocalQueueConnection() {
        return (QueueConnection)this.localConnection.get();
    }

    public void setLocalQueueConnection(QueueConnection localQueueConnection) {
        this.localConnection.set(localQueueConnection);
    }

    public QueueConnection getOutboundQueueConnection() {
        return (QueueConnection)this.foreignConnection.get();
    }

    public void setOutboundQueueConnection(QueueConnection foreignQueueConnection) {
        this.foreignConnection.set(foreignQueueConnection);
    }

    public void setOutboundQueueConnectionFactory(QueueConnectionFactory foreignQueueConnectionFactory) {
        this.outboundQueueConnectionFactory = foreignQueueConnectionFactory;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected void initializeForeignConnection() throws NamingException, JMSException {
        Connection newConnection = null;
        try {
            if (this.foreignConnection.get() == null) {
                if (this.outboundQueueConnectionFactory == null) {
                    if (this.outboundQueueConnectionFactoryName == null) throw new JMSException("Cannot create foreignConnection - no information");
                    this.outboundQueueConnectionFactory = this.jndiOutboundTemplate.lookup(this.outboundQueueConnectionFactoryName, QueueConnectionFactory.class);
                    newConnection = this.outboundUsername != null ? this.outboundQueueConnectionFactory.createQueueConnection(this.outboundUsername, this.outboundPassword) : this.outboundQueueConnectionFactory.createQueueConnection();
                } else {
                    newConnection = this.outboundUsername != null ? this.outboundQueueConnectionFactory.createQueueConnection(this.outboundUsername, this.outboundPassword) : this.outboundQueueConnectionFactory.createQueueConnection();
                }
            } else {
                newConnection = this.foreignConnection.getAndSet(null);
            }
            newConnection.setExceptionListener(new ExceptionListener(){

                @Override
                public void onException(JMSException exception) {
                    SimpleJmsQueueConnector.this.handleConnectionFailure((Connection)SimpleJmsQueueConnector.this.foreignConnection.get());
                }
            });
            if (this.outboundClientId != null && this.outboundClientId.length() > 0) {
                newConnection.setClientID(this.getOutboundClientId());
            }
            newConnection.start();
            this.outboundMessageConvertor.setConnection(newConnection);
            this.initializeInboundDestinationBridgesOutboundSide((QueueConnection)newConnection);
            this.initializeOutboundDestinationBridgesOutboundSide((QueueConnection)newConnection);
            this.foreignConnection.set(newConnection);
            return;
        }
        catch (Exception ex) {
            if (newConnection == null) throw ex;
            try {
                newConnection.close();
                throw ex;
            }
            catch (Exception exception) {
                // empty catch block
            }
            throw ex;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected void initializeLocalConnection() throws NamingException, JMSException {
        Connection newConnection = null;
        try {
            if (this.localConnection.get() == null) {
                if (this.localQueueConnectionFactory == null) {
                    if (this.embeddedConnectionFactory == null) {
                        if (this.localConnectionFactoryName == null) throw new JMSException("Cannot create localConnection - no information");
                        this.localQueueConnectionFactory = this.jndiLocalTemplate.lookup(this.localConnectionFactoryName, QueueConnectionFactory.class);
                        newConnection = this.localUsername != null ? this.localQueueConnectionFactory.createQueueConnection(this.localUsername, this.localPassword) : this.localQueueConnectionFactory.createQueueConnection();
                    } else {
                        newConnection = this.embeddedConnectionFactory.createQueueConnection();
                    }
                } else {
                    newConnection = this.localUsername != null ? this.localQueueConnectionFactory.createQueueConnection(this.localUsername, this.localPassword) : this.localQueueConnectionFactory.createQueueConnection();
                }
            } else {
                newConnection = this.localConnection.getAndSet(null);
            }
            newConnection.setExceptionListener(new ExceptionListener(){

                @Override
                public void onException(JMSException exception) {
                    SimpleJmsQueueConnector.this.handleConnectionFailure((Connection)SimpleJmsQueueConnector.this.localConnection.get());
                }
            });
            if (this.localClientId != null && this.localClientId.length() > 0) {
                newConnection.setClientID(this.getLocalClientId());
            }
            newConnection.start();
            this.inboundMessageConvertor.setConnection(newConnection);
            this.initializeInboundDestinationBridgesLocalSide((QueueConnection)newConnection);
            this.initializeOutboundDestinationBridgesLocalSide((QueueConnection)newConnection);
            this.localConnection.set(newConnection);
            return;
        }
        catch (Exception ex) {
            if (newConnection == null) throw ex;
            try {
                newConnection.close();
                throw ex;
            }
            catch (Exception exception) {
                // empty catch block
            }
            throw ex;
        }
    }

    protected void initializeInboundDestinationBridgesOutboundSide(QueueConnection connection) throws JMSException {
        if (this.inboundQueueBridges != null) {
            QueueSession outboundSession = connection.createQueueSession(false, 1);
            for (InboundQueueBridge bridge : this.inboundQueueBridges) {
                String queueName = bridge.getInboundQueueName();
                Queue foreignQueue = this.createForeignQueue(outboundSession, queueName);
                bridge.setConsumer(null);
                bridge.setConsumerQueue(foreignQueue);
                bridge.setConsumerConnection(connection);
                bridge.setJmsConnector(this);
                this.addInboundBridge(bridge);
            }
            outboundSession.close();
        }
    }

    protected void initializeInboundDestinationBridgesLocalSide(QueueConnection connection) throws JMSException {
        if (this.inboundQueueBridges != null) {
            QueueSession localSession = connection.createQueueSession(false, 1);
            for (InboundQueueBridge bridge : this.inboundQueueBridges) {
                String localQueueName = bridge.getLocalQueueName();
                Queue activemqQueue = this.createActiveMQQueue(localSession, localQueueName);
                bridge.setProducerQueue(activemqQueue);
                bridge.setProducerConnection(connection);
                if (bridge.getJmsMessageConvertor() == null) {
                    bridge.setJmsMessageConvertor(this.getInboundMessageConvertor());
                }
                bridge.setJmsConnector(this);
                this.addInboundBridge(bridge);
            }
            localSession.close();
        }
    }

    protected void initializeOutboundDestinationBridgesOutboundSide(QueueConnection connection) throws JMSException {
        if (this.outboundQueueBridges != null) {
            QueueSession outboundSession = connection.createQueueSession(false, 1);
            for (OutboundQueueBridge bridge : this.outboundQueueBridges) {
                String queueName = bridge.getOutboundQueueName();
                Queue foreignQueue = this.createForeignQueue(outboundSession, queueName);
                bridge.setProducerQueue(foreignQueue);
                bridge.setProducerConnection(connection);
                if (bridge.getJmsMessageConvertor() == null) {
                    bridge.setJmsMessageConvertor(this.getOutboundMessageConvertor());
                }
                bridge.setJmsConnector(this);
                this.addOutboundBridge(bridge);
            }
            outboundSession.close();
        }
    }

    protected void initializeOutboundDestinationBridgesLocalSide(QueueConnection connection) throws JMSException {
        if (this.outboundQueueBridges != null) {
            QueueSession localSession = connection.createQueueSession(false, 1);
            for (OutboundQueueBridge bridge : this.outboundQueueBridges) {
                String localQueueName = bridge.getLocalQueueName();
                Queue activemqQueue = this.createActiveMQQueue(localSession, localQueueName);
                bridge.setConsumer(null);
                bridge.setConsumerQueue(activemqQueue);
                bridge.setConsumerConnection(connection);
                bridge.setJmsConnector(this);
                this.addOutboundBridge(bridge);
            }
            localSession.close();
        }
    }

    @Override
    protected Destination createReplyToBridge(Destination destination, Connection replyToProducerConnection, Connection replyToConsumerConnection) {
        Queue replyToProducerQueue = (Queue)destination;
        boolean isInbound = replyToProducerConnection.equals(this.localConnection.get());
        if (isInbound) {
            InboundQueueBridge bridge = (InboundQueueBridge)this.replyToBridges.get(replyToProducerQueue);
            if (bridge == null) {
                bridge = new InboundQueueBridge(){

                    @Override
                    protected Destination processReplyToDestination(Destination destination) {
                        return null;
                    }
                };
                try {
                    QueueSession replyToConsumerSession = ((QueueConnection)replyToConsumerConnection).createQueueSession(false, 1);
                    TemporaryQueue replyToConsumerQueue = replyToConsumerSession.createTemporaryQueue();
                    replyToConsumerSession.close();
                    bridge.setConsumerQueue(replyToConsumerQueue);
                    bridge.setProducerQueue(replyToProducerQueue);
                    bridge.setProducerConnection((QueueConnection)replyToProducerConnection);
                    bridge.setConsumerConnection((QueueConnection)replyToConsumerConnection);
                    bridge.setDoHandleReplyTo(false);
                    if (bridge.getJmsMessageConvertor() == null) {
                        bridge.setJmsMessageConvertor(this.getInboundMessageConvertor());
                    }
                    bridge.setJmsConnector(this);
                    bridge.start();
                    LOG.info("Created replyTo bridge for {}", (Object)replyToProducerQueue);
                }
                catch (Exception e) {
                    LOG.error("Failed to create replyTo bridge for queue: {}", (Object)replyToProducerQueue, (Object)e);
                    return null;
                }
                this.replyToBridges.put(replyToProducerQueue, bridge);
            }
            return bridge.getConsumerQueue();
        }
        OutboundQueueBridge bridge = (OutboundQueueBridge)this.replyToBridges.get(replyToProducerQueue);
        if (bridge == null) {
            bridge = new OutboundQueueBridge(){

                @Override
                protected Destination processReplyToDestination(Destination destination) {
                    return null;
                }
            };
            try {
                QueueSession replyToConsumerSession = ((QueueConnection)replyToConsumerConnection).createQueueSession(false, 1);
                TemporaryQueue replyToConsumerQueue = replyToConsumerSession.createTemporaryQueue();
                replyToConsumerSession.close();
                bridge.setConsumerQueue(replyToConsumerQueue);
                bridge.setProducerQueue(replyToProducerQueue);
                bridge.setProducerConnection((QueueConnection)replyToProducerConnection);
                bridge.setConsumerConnection((QueueConnection)replyToConsumerConnection);
                bridge.setDoHandleReplyTo(false);
                if (bridge.getJmsMessageConvertor() == null) {
                    bridge.setJmsMessageConvertor(this.getOutboundMessageConvertor());
                }
                bridge.setJmsConnector(this);
                bridge.start();
                LOG.info("Created replyTo bridge for {}", (Object)replyToProducerQueue);
            }
            catch (Exception e) {
                LOG.error("Failed to create replyTo bridge for queue: {}", (Object)replyToProducerQueue, (Object)e);
                return null;
            }
            this.replyToBridges.put(replyToProducerQueue, bridge);
        }
        return bridge.getConsumerQueue();
    }

    protected Queue createActiveMQQueue(QueueSession session, String queueName) throws JMSException {
        return session.createQueue(queueName);
    }

    protected Queue createForeignQueue(QueueSession session, String queueName) throws JMSException {
        Queue result = null;
        if (this.preferJndiDestinationLookup) {
            try {
                result = this.jndiOutboundTemplate.lookup(queueName, Queue.class);
            }
            catch (NamingException e) {
                try {
                    result = session.createQueue(queueName);
                }
                catch (JMSException e1) {
                    String errStr = "Failed to look-up or create Queue for name: " + queueName;
                    LOG.error(errStr, e);
                    JMSException jmsEx = new JMSException(errStr);
                    jmsEx.setLinkedException(e1);
                    throw jmsEx;
                }
            }
        } else {
            try {
                result = session.createQueue(queueName);
            }
            catch (JMSException e) {
                try {
                    result = this.jndiOutboundTemplate.lookup(queueName, Queue.class);
                }
                catch (NamingException e1) {
                    String errStr = "Failed to look-up Queue for name: " + queueName;
                    LOG.error(errStr, e);
                    JMSException jmsEx = new JMSException(errStr);
                    jmsEx.setLinkedException(e1);
                    throw jmsEx;
                }
            }
        }
        return result;
    }
}

