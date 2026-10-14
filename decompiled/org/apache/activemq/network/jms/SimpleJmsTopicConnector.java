/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.NamingException;
import org.apache.activemq.network.jms.InboundTopicBridge;
import org.apache.activemq.network.jms.JmsConnector;
import org.apache.activemq.network.jms.OutboundTopicBridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJmsTopicConnector
extends JmsConnector {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleJmsTopicConnector.class);
    private String outboundTopicConnectionFactoryName;
    private String localConnectionFactoryName;
    private TopicConnectionFactory outboundTopicConnectionFactory;
    private TopicConnectionFactory localTopicConnectionFactory;
    private InboundTopicBridge[] inboundTopicBridges;
    private OutboundTopicBridge[] outboundTopicBridges;

    public InboundTopicBridge[] getInboundTopicBridges() {
        return this.inboundTopicBridges;
    }

    public void setInboundTopicBridges(InboundTopicBridge[] inboundTopicBridges) {
        this.inboundTopicBridges = inboundTopicBridges;
    }

    public OutboundTopicBridge[] getOutboundTopicBridges() {
        return this.outboundTopicBridges;
    }

    public void setOutboundTopicBridges(OutboundTopicBridge[] outboundTopicBridges) {
        this.outboundTopicBridges = outboundTopicBridges;
    }

    public TopicConnectionFactory getLocalTopicConnectionFactory() {
        return this.localTopicConnectionFactory;
    }

    public void setLocalTopicConnectionFactory(TopicConnectionFactory localTopicConnectionFactory) {
        this.localTopicConnectionFactory = localTopicConnectionFactory;
    }

    public TopicConnectionFactory getOutboundTopicConnectionFactory() {
        return this.outboundTopicConnectionFactory;
    }

    public String getOutboundTopicConnectionFactoryName() {
        return this.outboundTopicConnectionFactoryName;
    }

    public void setOutboundTopicConnectionFactoryName(String foreignTopicConnectionFactoryName) {
        this.outboundTopicConnectionFactoryName = foreignTopicConnectionFactoryName;
    }

    public String getLocalConnectionFactoryName() {
        return this.localConnectionFactoryName;
    }

    public void setLocalConnectionFactoryName(String localConnectionFactoryName) {
        this.localConnectionFactoryName = localConnectionFactoryName;
    }

    public TopicConnection getLocalTopicConnection() {
        return (TopicConnection)this.localConnection.get();
    }

    public void setLocalTopicConnection(TopicConnection localTopicConnection) {
        this.localConnection.set(localTopicConnection);
    }

    public TopicConnection getOutboundTopicConnection() {
        return (TopicConnection)this.foreignConnection.get();
    }

    public void setOutboundTopicConnection(TopicConnection foreignTopicConnection) {
        this.foreignConnection.set(foreignTopicConnection);
    }

    public void setOutboundTopicConnectionFactory(TopicConnectionFactory foreignTopicConnectionFactory) {
        this.outboundTopicConnectionFactory = foreignTopicConnectionFactory;
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
                if (this.outboundTopicConnectionFactory == null) {
                    if (this.outboundTopicConnectionFactoryName == null) throw new JMSException("Cannot create foreignConnection - no information");
                    this.outboundTopicConnectionFactory = this.jndiOutboundTemplate.lookup(this.outboundTopicConnectionFactoryName, TopicConnectionFactory.class);
                    newConnection = this.outboundUsername != null ? this.outboundTopicConnectionFactory.createTopicConnection(this.outboundUsername, this.outboundPassword) : this.outboundTopicConnectionFactory.createTopicConnection();
                } else {
                    newConnection = this.outboundUsername != null ? this.outboundTopicConnectionFactory.createTopicConnection(this.outboundUsername, this.outboundPassword) : this.outboundTopicConnectionFactory.createTopicConnection();
                }
            } else {
                newConnection = this.foreignConnection.getAndSet(null);
            }
            newConnection.setExceptionListener(new ExceptionListener(){

                @Override
                public void onException(JMSException exception) {
                    SimpleJmsTopicConnector.this.handleConnectionFailure((Connection)SimpleJmsTopicConnector.this.foreignConnection.get());
                }
            });
            if (this.outboundClientId != null && this.outboundClientId.length() > 0) {
                newConnection.setClientID(this.getOutboundClientId());
            }
            newConnection.start();
            this.outboundMessageConvertor.setConnection(newConnection);
            this.initializeInboundDestinationBridgesOutboundSide((TopicConnection)newConnection);
            this.initializeOutboundDestinationBridgesOutboundSide((TopicConnection)newConnection);
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
                if (this.localTopicConnectionFactory == null) {
                    if (this.embeddedConnectionFactory == null) {
                        if (this.localConnectionFactoryName == null) throw new JMSException("Cannot create localConnection - no information");
                        this.localTopicConnectionFactory = this.jndiLocalTemplate.lookup(this.localConnectionFactoryName, TopicConnectionFactory.class);
                        newConnection = this.localUsername != null ? this.localTopicConnectionFactory.createTopicConnection(this.localUsername, this.localPassword) : this.localTopicConnectionFactory.createTopicConnection();
                    } else {
                        newConnection = this.embeddedConnectionFactory.createTopicConnection();
                    }
                } else {
                    newConnection = this.localUsername != null ? this.localTopicConnectionFactory.createTopicConnection(this.localUsername, this.localPassword) : this.localTopicConnectionFactory.createTopicConnection();
                }
            } else {
                newConnection = this.localConnection.getAndSet(null);
            }
            newConnection.setExceptionListener(new ExceptionListener(){

                @Override
                public void onException(JMSException exception) {
                    SimpleJmsTopicConnector.this.handleConnectionFailure((Connection)SimpleJmsTopicConnector.this.localConnection.get());
                }
            });
            if (this.localClientId != null && this.localClientId.length() > 0) {
                newConnection.setClientID(this.getLocalClientId());
            }
            newConnection.start();
            this.inboundMessageConvertor.setConnection(newConnection);
            this.initializeInboundDestinationBridgesLocalSide((TopicConnection)newConnection);
            this.initializeOutboundDestinationBridgesLocalSide((TopicConnection)newConnection);
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

    protected void initializeInboundDestinationBridgesOutboundSide(TopicConnection connection) throws JMSException {
        if (this.inboundTopicBridges != null) {
            TopicSession outboundSession = connection.createTopicSession(false, 1);
            for (InboundTopicBridge bridge : this.inboundTopicBridges) {
                String TopicName = bridge.getInboundTopicName();
                Topic foreignTopic = this.createForeignTopic(outboundSession, TopicName);
                bridge.setConsumer(null);
                bridge.setConsumerTopic(foreignTopic);
                bridge.setConsumerConnection(connection);
                bridge.setJmsConnector(this);
                this.addInboundBridge(bridge);
            }
            outboundSession.close();
        }
    }

    protected void initializeInboundDestinationBridgesLocalSide(TopicConnection connection) throws JMSException {
        if (this.inboundTopicBridges != null) {
            TopicSession localSession = connection.createTopicSession(false, 1);
            for (InboundTopicBridge bridge : this.inboundTopicBridges) {
                String localTopicName = bridge.getLocalTopicName();
                Topic activemqTopic = this.createActiveMQTopic(localSession, localTopicName);
                bridge.setProducerTopic(activemqTopic);
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

    protected void initializeOutboundDestinationBridgesOutboundSide(TopicConnection connection) throws JMSException {
        if (this.outboundTopicBridges != null) {
            TopicSession outboundSession = connection.createTopicSession(false, 1);
            for (OutboundTopicBridge bridge : this.outboundTopicBridges) {
                String topicName = bridge.getOutboundTopicName();
                Topic foreignTopic = this.createForeignTopic(outboundSession, topicName);
                bridge.setProducerTopic(foreignTopic);
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

    protected void initializeOutboundDestinationBridgesLocalSide(TopicConnection connection) throws JMSException {
        if (this.outboundTopicBridges != null) {
            TopicSession localSession = connection.createTopicSession(false, 1);
            for (OutboundTopicBridge bridge : this.outboundTopicBridges) {
                String localTopicName = bridge.getLocalTopicName();
                Topic activemqTopic = this.createActiveMQTopic(localSession, localTopicName);
                bridge.setConsumer(null);
                bridge.setConsumerTopic(activemqTopic);
                bridge.setConsumerConnection(connection);
                bridge.setJmsConnector(this);
                this.addOutboundBridge(bridge);
            }
            localSession.close();
        }
    }

    @Override
    protected Destination createReplyToBridge(Destination destination, Connection replyToProducerConnection, Connection replyToConsumerConnection) {
        Topic replyToProducerTopic = (Topic)destination;
        boolean isInbound = replyToProducerConnection.equals(this.localConnection.get());
        if (isInbound) {
            InboundTopicBridge bridge = (InboundTopicBridge)this.replyToBridges.get(replyToProducerTopic);
            if (bridge == null) {
                bridge = new InboundTopicBridge(){

                    @Override
                    protected Destination processReplyToDestination(Destination destination) {
                        return null;
                    }
                };
                try {
                    TopicSession replyToConsumerSession = ((TopicConnection)replyToConsumerConnection).createTopicSession(false, 1);
                    TemporaryTopic replyToConsumerTopic = replyToConsumerSession.createTemporaryTopic();
                    replyToConsumerSession.close();
                    bridge.setConsumerTopic(replyToConsumerTopic);
                    bridge.setProducerTopic(replyToProducerTopic);
                    bridge.setProducerConnection((TopicConnection)replyToProducerConnection);
                    bridge.setConsumerConnection((TopicConnection)replyToConsumerConnection);
                    bridge.setDoHandleReplyTo(false);
                    if (bridge.getJmsMessageConvertor() == null) {
                        bridge.setJmsMessageConvertor(this.getInboundMessageConvertor());
                    }
                    bridge.setJmsConnector(this);
                    bridge.start();
                    LOG.info("Created replyTo bridge for {}", (Object)replyToProducerTopic);
                }
                catch (Exception e) {
                    LOG.error("Failed to create replyTo bridge for topic: {}", (Object)replyToProducerTopic, (Object)e);
                    return null;
                }
                this.replyToBridges.put(replyToProducerTopic, bridge);
            }
            return bridge.getConsumerTopic();
        }
        OutboundTopicBridge bridge = (OutboundTopicBridge)this.replyToBridges.get(replyToProducerTopic);
        if (bridge == null) {
            bridge = new OutboundTopicBridge(){

                @Override
                protected Destination processReplyToDestination(Destination destination) {
                    return null;
                }
            };
            try {
                TopicSession replyToConsumerSession = ((TopicConnection)replyToConsumerConnection).createTopicSession(false, 1);
                TemporaryTopic replyToConsumerTopic = replyToConsumerSession.createTemporaryTopic();
                replyToConsumerSession.close();
                bridge.setConsumerTopic(replyToConsumerTopic);
                bridge.setProducerTopic(replyToProducerTopic);
                bridge.setProducerConnection((TopicConnection)replyToProducerConnection);
                bridge.setConsumerConnection((TopicConnection)replyToConsumerConnection);
                bridge.setDoHandleReplyTo(false);
                if (bridge.getJmsMessageConvertor() == null) {
                    bridge.setJmsMessageConvertor(this.getOutboundMessageConvertor());
                }
                bridge.setJmsConnector(this);
                bridge.start();
                LOG.info("Created replyTo bridge for {}", (Object)replyToProducerTopic);
            }
            catch (Exception e) {
                LOG.error("Failed to create replyTo bridge for topic: {}", (Object)replyToProducerTopic, (Object)e);
                return null;
            }
            this.replyToBridges.put(replyToProducerTopic, bridge);
        }
        return bridge.getConsumerTopic();
    }

    protected Topic createActiveMQTopic(TopicSession session, String topicName) throws JMSException {
        return session.createTopic(topicName);
    }

    protected Topic createForeignTopic(TopicSession session, String topicName) throws JMSException {
        Topic result = null;
        if (this.preferJndiDestinationLookup) {
            try {
                result = this.jndiOutboundTemplate.lookup(topicName, Topic.class);
            }
            catch (NamingException e) {
                try {
                    result = session.createTopic(topicName);
                }
                catch (JMSException e1) {
                    String errStr = "Failed to look-up or create Topic for name: " + topicName;
                    LOG.error(errStr, e);
                    JMSException jmsEx = new JMSException(errStr);
                    jmsEx.setLinkedException(e1);
                    throw jmsEx;
                }
            }
        } else {
            try {
                result = session.createTopic(topicName);
            }
            catch (JMSException e) {
                try {
                    result = this.jndiOutboundTemplate.lookup(topicName, Topic.class);
                }
                catch (NamingException e1) {
                    String errStr = "Failed to look-up Topic for name: " + topicName;
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

