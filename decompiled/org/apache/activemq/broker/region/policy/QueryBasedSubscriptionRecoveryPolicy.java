/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.concurrent.atomic.AtomicLong;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import org.apache.activemq.ActiveMQMessageTransformation;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.SubscriptionRecovery;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.policy.MessageQuery;
import org.apache.activemq.broker.region.policy.SubscriptionRecoveryPolicy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.SessionId;
import org.apache.activemq.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryBasedSubscriptionRecoveryPolicy
implements SubscriptionRecoveryPolicy {
    private static final Logger LOG = LoggerFactory.getLogger(QueryBasedSubscriptionRecoveryPolicy.class);
    private MessageQuery query;
    private final AtomicLong messageSequence = new AtomicLong(0L);
    private final IdGenerator idGenerator = new IdGenerator();
    private final ProducerId producerId = this.createProducerId();

    @Override
    public SubscriptionRecoveryPolicy copy() {
        QueryBasedSubscriptionRecoveryPolicy rc = new QueryBasedSubscriptionRecoveryPolicy();
        rc.setQuery(this.query);
        return rc;
    }

    @Override
    public boolean add(ConnectionContext context, MessageReference message) throws Exception {
        return this.query.validateUpdate(message.getMessage());
    }

    @Override
    public void recover(final ConnectionContext context, final Topic topic, final SubscriptionRecovery sub) throws Exception {
        if (this.query != null) {
            ActiveMQDestination destination = sub.getActiveMQDestination();
            this.query.execute(destination, new MessageListener(){

                @Override
                public void onMessage(javax.jms.Message message) {
                    QueryBasedSubscriptionRecoveryPolicy.this.dispatchInitialMessage(message, topic, context, sub);
                }
            });
        }
    }

    @Override
    public void start() throws Exception {
        if (this.query == null) {
            throw new IllegalArgumentException("No query property configured");
        }
    }

    @Override
    public void stop() throws Exception {
    }

    public MessageQuery getQuery() {
        return this.query;
    }

    public void setQuery(MessageQuery query) {
        this.query = query;
    }

    @Override
    public Message[] browse(ActiveMQDestination dest) throws Exception {
        return new Message[0];
    }

    @Override
    public void setBroker(Broker broker) {
    }

    protected void dispatchInitialMessage(javax.jms.Message message, Destination regionDestination, ConnectionContext context, SubscriptionRecovery sub) {
        try {
            ActiveMQMessage activeMessage = ActiveMQMessageTransformation.transformMessage(message, null);
            ActiveMQDestination destination = activeMessage.getDestination();
            if (destination == null) {
                destination = sub.getActiveMQDestination();
                activeMessage.setDestination(destination);
            }
            activeMessage.setRegionDestination(regionDestination);
            this.configure(activeMessage);
            sub.addRecoveredMessage(context, activeMessage);
        }
        catch (Throwable e) {
            LOG.warn("Failed to dispatch initial message: {} into subscription. Reason: ", (Object)message, (Object)e);
        }
    }

    protected void configure(ActiveMQMessage msg) throws JMSException {
        long sequenceNumber = this.messageSequence.incrementAndGet();
        msg.setMessageId(new MessageId(this.producerId, sequenceNumber));
        msg.onSend();
        msg.setProducerId(this.producerId);
    }

    protected ProducerId createProducerId() {
        String id = this.idGenerator.generateId();
        ConnectionId connectionId = new ConnectionId(id);
        SessionId sessionId = new SessionId(connectionId, 1L);
        return new ProducerId(sessionId, 1L);
    }
}

