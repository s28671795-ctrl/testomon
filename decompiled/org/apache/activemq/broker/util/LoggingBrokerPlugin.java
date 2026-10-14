/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.activemq.broker.BrokerPluginSupport;
import org.apache.activemq.broker.Connection;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ConsumerBrokerExchange;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.command.MessagePull;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.usage.Usage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingBrokerPlugin
extends BrokerPluginSupport {
    private static final Logger LOG = LoggerFactory.getLogger(LoggingBrokerPlugin.class);
    private boolean logAll = false;
    private boolean logConnectionEvents = true;
    private boolean logSessionEvents = true;
    private boolean logTransactionEvents = false;
    private boolean logConsumerEvents = false;
    private boolean logProducerEvents = false;
    private boolean logInternalEvents = false;
    private boolean perDestinationLogger = false;

    @PostConstruct
    private void postConstruct() {
        try {
            this.afterPropertiesSet();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void afterPropertiesSet() throws Exception {
        LOG.info("Created LoggingBrokerPlugin: {}", (Object)this.toString());
    }

    public boolean isLogAll() {
        return this.logAll;
    }

    public void setLogAll(boolean logAll) {
        this.logAll = logAll;
    }

    public boolean isLogConnectionEvents() {
        return this.logConnectionEvents;
    }

    public void setLogConnectionEvents(boolean logConnectionEvents) {
        this.logConnectionEvents = logConnectionEvents;
    }

    public boolean isLogSessionEvents() {
        return this.logSessionEvents;
    }

    public void setLogSessionEvents(boolean logSessionEvents) {
        this.logSessionEvents = logSessionEvents;
    }

    public boolean isLogTransactionEvents() {
        return this.logTransactionEvents;
    }

    public void setLogTransactionEvents(boolean logTransactionEvents) {
        this.logTransactionEvents = logTransactionEvents;
    }

    public boolean isLogConsumerEvents() {
        return this.logConsumerEvents;
    }

    public void setLogConsumerEvents(boolean logConsumerEvents) {
        this.logConsumerEvents = logConsumerEvents;
    }

    public boolean isLogProducerEvents() {
        return this.logProducerEvents;
    }

    public void setLogProducerEvents(boolean logProducerEvents) {
        this.logProducerEvents = logProducerEvents;
    }

    public boolean isLogInternalEvents() {
        return this.logInternalEvents;
    }

    public void setLogInternalEvents(boolean logInternalEvents) {
        this.logInternalEvents = logInternalEvents;
    }

    @Override
    public void acknowledge(ConsumerBrokerExchange consumerExchange, MessageAck ack) throws Exception {
        if (this.isLogAll() || this.isLogConsumerEvents()) {
            LOG.info("Acknowledging message for client ID: {}{}", (Object)consumerExchange.getConnectionContext().getClientId(), (Object)(ack.getMessageCount() == 1 ? ", " + ack.getLastMessageId() : ""));
            if (ack.getMessageCount() > 1) {
                LOG.trace("Message count: {}, First Message Id: {}, Last Message Id: {}", ack.getMessageCount(), ack.getFirstMessageId(), ack.getLastMessageId());
            }
        }
        super.acknowledge(consumerExchange, ack);
    }

    @Override
    public Response messagePull(ConnectionContext context, MessagePull pull) throws Exception {
        if (this.isLogAll() || this.isLogConsumerEvents()) {
            LOG.info("Message Pull from: {} on {}", (Object)context.getClientId(), (Object)pull.getDestination().getPhysicalName());
        }
        return super.messagePull(context, pull);
    }

    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
        if (this.isLogAll() || this.isLogConnectionEvents()) {
            LOG.info("Adding Connection: {}", (Object)info);
        }
        super.addConnection(context, info);
    }

    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        if (this.isLogAll() || this.isLogConsumerEvents()) {
            LOG.info("Adding Consumer: {}", (Object)info);
        }
        return super.addConsumer(context, info);
    }

    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        if (this.isLogAll() || this.isLogProducerEvents()) {
            LOG.info("Adding Producer: {}", (Object)info);
        }
        super.addProducer(context, info);
    }

    @Override
    public void commitTransaction(ConnectionContext context, TransactionId xid, boolean onePhase) throws Exception {
        if (this.isLogAll() || this.isLogTransactionEvents()) {
            LOG.info("Committing transaction: {}", (Object)xid.getTransactionKey());
        }
        super.commitTransaction(context, xid, onePhase);
    }

    @Override
    public void removeSubscription(ConnectionContext context, RemoveSubscriptionInfo info) throws Exception {
        if (this.isLogAll() || this.isLogConsumerEvents()) {
            LOG.info("Removing subscription: {}", (Object)info);
        }
        super.removeSubscription(context, info);
    }

    @Override
    public TransactionId[] getPreparedTransactions(ConnectionContext context) throws Exception {
        TransactionId[] result = super.getPreparedTransactions(context);
        if ((this.isLogAll() || this.isLogTransactionEvents()) && result != null) {
            StringBuffer tids = new StringBuffer();
            for (TransactionId tid : result) {
                if (tids.length() > 0) {
                    tids.append(", ");
                }
                tids.append(tid.getTransactionKey());
            }
            LOG.info("Prepared transactions: {}", (Object)tids);
        }
        return result;
    }

    @Override
    public int prepareTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        if (this.isLogAll() || this.isLogTransactionEvents()) {
            LOG.info("Preparing transaction: {}", (Object)xid.getTransactionKey());
        }
        return super.prepareTransaction(context, xid);
    }

    @Override
    public void removeConnection(ConnectionContext context, ConnectionInfo info, Throwable error) throws Exception {
        if (this.isLogAll() || this.isLogConnectionEvents()) {
            LOG.info("Removing Connection: {}", (Object)info);
        }
        super.removeConnection(context, info, error);
    }

    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        if (this.isLogAll() || this.isLogConsumerEvents()) {
            LOG.info("Removing Consumer: {}", (Object)info);
        }
        super.removeConsumer(context, info);
    }

    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        if (this.isLogAll() || this.isLogProducerEvents()) {
            LOG.info("Removing Producer: {}", (Object)info);
        }
        super.removeProducer(context, info);
    }

    @Override
    public void rollbackTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        if (this.isLogAll() || this.isLogTransactionEvents()) {
            LOG.info("Rolling back Transaction: {}", (Object)xid.getTransactionKey());
        }
        super.rollbackTransaction(context, xid);
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        if (this.isLogAll() || this.isLogProducerEvents()) {
            this.logSend(messageSend.copy());
        }
        super.send(producerExchange, messageSend);
    }

    private void logSend(Message copy) {
        copy.getSize();
        Logger perDestinationsLogger = LOG;
        if (this.isPerDestinationLogger()) {
            ActiveMQDestination destination = copy.getDestination();
            perDestinationsLogger = LoggerFactory.getLogger(LOG.getName() + "." + destination.getDestinationTypeAsString() + "." + destination.getPhysicalName());
        }
        perDestinationsLogger.info("Sending message: {}", (Object)copy);
    }

    @Override
    public void beginTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        if (this.isLogAll() || this.isLogTransactionEvents()) {
            LOG.info("Beginning transaction: {}", (Object)xid.getTransactionKey());
        }
        super.beginTransaction(context, xid);
    }

    @Override
    public void forgetTransaction(ConnectionContext context, TransactionId transactionId) throws Exception {
        if (this.isLogAll() || this.isLogTransactionEvents()) {
            LOG.info("Forgetting transaction: {}", (Object)transactionId.getTransactionKey());
        }
        super.forgetTransaction(context, transactionId);
    }

    @Override
    public Connection[] getClients() throws Exception {
        Connection[] result = super.getClients();
        if (this.isLogAll() || this.isLogInternalEvents()) {
            if (result == null) {
                LOG.info("Get Clients returned empty list.");
            } else {
                StringBuffer cids = new StringBuffer();
                for (Connection c : result) {
                    cids.append(cids.length() > 0 ? ", " : "");
                    cids.append(c.getConnectionId());
                }
                LOG.info("Connected clients: {}", (Object)cids);
            }
        }
        return super.getClients();
    }

    @Override
    public Destination addDestination(ConnectionContext context, ActiveMQDestination destination, boolean create) throws Exception {
        if (this.isLogAll() || this.isLogInternalEvents()) {
            LOG.info("Adding destination: {}:{}", (Object)destination.getDestinationTypeAsString(), (Object)destination.getPhysicalName());
        }
        return super.addDestination(context, destination, create);
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        if (this.isLogAll() || this.isLogInternalEvents()) {
            LOG.info("Removing destination: {}:{}", (Object)destination.getDestinationTypeAsString(), (Object)destination.getPhysicalName());
        }
        super.removeDestination(context, destination, timeout2);
    }

    @Override
    public ActiveMQDestination[] getDestinations() throws Exception {
        ActiveMQDestination[] result = super.getDestinations();
        if (this.isLogAll() || this.isLogInternalEvents()) {
            if (result == null) {
                LOG.info("Get Destinations returned empty list.");
            } else {
                StringBuffer destinations = new StringBuffer();
                for (ActiveMQDestination dest : result) {
                    destinations.append(destinations.length() > 0 ? ", " : "");
                    destinations.append(dest.getPhysicalName());
                }
                LOG.info("Get Destinations: {}", (Object)destinations);
            }
        }
        return result;
    }

    @Override
    public void start() throws Exception {
        if (this.isLogAll() || this.isLogInternalEvents()) {
            LOG.info("Starting {}", (Object)this.getBrokerName());
        }
        super.start();
    }

    @Override
    public void stop() throws Exception {
        if (this.isLogAll() || this.isLogInternalEvents()) {
            LOG.info("Stopping {}", (Object)this.getBrokerName());
        }
        super.stop();
    }

    @Override
    public void addSession(ConnectionContext context, SessionInfo info) throws Exception {
        if (this.isLogAll() || this.isLogSessionEvents()) {
            LOG.info("Adding Session: {}", (Object)info);
        }
        super.addSession(context, info);
    }

    @Override
    public void removeSession(ConnectionContext context, SessionInfo info) throws Exception {
        if (this.isLogAll() || this.isLogSessionEvents()) {
            LOG.info("Removing Session: {}", (Object)info);
        }
        super.removeSession(context, info);
    }

    @Override
    public void addBroker(Connection connection, BrokerInfo info) {
        if (this.isLogAll() || this.isLogInternalEvents()) {
            LOG.info("Adding Broker {}", (Object)info.getBrokerName());
        }
        super.addBroker(connection, info);
    }

    @Override
    public void removeBroker(Connection connection, BrokerInfo info) {
        if (this.isLogAll() || this.isLogInternalEvents()) {
            LOG.info("Removing Broker {}", (Object)info.getBrokerName());
        }
        super.removeBroker(connection, info);
    }

    @Override
    public BrokerInfo[] getPeerBrokerInfos() {
        BrokerInfo[] result = super.getPeerBrokerInfos();
        if (this.isLogAll() || this.isLogInternalEvents()) {
            if (result == null) {
                LOG.info("Get Peer Broker Infos returned empty list.");
            } else {
                StringBuffer peers = new StringBuffer();
                for (BrokerInfo bi : result) {
                    peers.append(peers.length() > 0 ? ", " : "");
                    peers.append(bi.getBrokerName());
                }
                LOG.info("Get Peer Broker Infos: {}", (Object)peers);
            }
        }
        return result;
    }

    @Override
    public void preProcessDispatch(MessageDispatch messageDispatch) {
        if (this.isLogAll() || this.isLogInternalEvents() || this.isLogConsumerEvents()) {
            LOG.info("preProcessDispatch: {}", (Object)messageDispatch);
        }
        super.preProcessDispatch(messageDispatch);
    }

    @Override
    public void postProcessDispatch(MessageDispatch messageDispatch) {
        if (this.isLogAll() || this.isLogInternalEvents() || this.isLogConsumerEvents()) {
            LOG.info("postProcessDispatch: {}", (Object)messageDispatch);
        }
        super.postProcessDispatch(messageDispatch);
    }

    @Override
    public void processDispatchNotification(MessageDispatchNotification messageDispatchNotification) throws Exception {
        if (this.isLogAll() || this.isLogInternalEvents() || this.isLogConsumerEvents()) {
            LOG.info("ProcessDispatchNotification: {}", (Object)messageDispatchNotification);
        }
        super.processDispatchNotification(messageDispatchNotification);
    }

    @Override
    public Set<ActiveMQDestination> getDurableDestinations() {
        Set<ActiveMQDestination> result = super.getDurableDestinations();
        if (this.isLogAll() || this.isLogInternalEvents()) {
            if (result == null) {
                LOG.info("Get Durable Destinations returned empty list.");
            } else {
                StringBuffer destinations = new StringBuffer();
                for (ActiveMQDestination dest : result) {
                    destinations.append(destinations.length() > 0 ? ", " : "");
                    destinations.append(dest.getPhysicalName());
                }
                LOG.info("Get Durable Destinations: {}", (Object)destinations);
            }
        }
        return result;
    }

    @Override
    public void addDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        if (this.isLogAll() || this.isLogInternalEvents()) {
            LOG.info("Adding destination info: {}", (Object)info);
        }
        super.addDestinationInfo(context, info);
    }

    @Override
    public void removeDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        if (this.isLogAll() || this.isLogInternalEvents()) {
            LOG.info("Removing destination info: {}", (Object)info);
        }
        super.removeDestinationInfo(context, info);
    }

    @Override
    public void messageExpired(ConnectionContext context, MessageReference message, Subscription subscription) {
        if (this.isLogAll() || this.isLogInternalEvents()) {
            String msg = "Unable to display message.";
            msg = message.getMessage().toString();
            LOG.info("Message has expired: {}", (Object)msg);
        }
        super.messageExpired(context, message, subscription);
    }

    @Override
    public boolean sendToDeadLetterQueue(ConnectionContext context, MessageReference messageReference, Subscription subscription, Throwable poisonCause) {
        if (this.isLogAll() || this.isLogInternalEvents()) {
            String msg = "Unable to display message.";
            msg = messageReference.getMessage().toString();
            LOG.info("Sending to DLQ: {}", (Object)msg);
        }
        return super.sendToDeadLetterQueue(context, messageReference, subscription, poisonCause);
    }

    @Override
    public void fastProducer(ConnectionContext context, ProducerInfo producerInfo, ActiveMQDestination destination) {
        if (this.isLogAll() || this.isLogProducerEvents() || this.isLogInternalEvents()) {
            LOG.info("Fast Producer: {}", (Object)producerInfo);
        }
        super.fastProducer(context, producerInfo, destination);
    }

    @Override
    public void isFull(ConnectionContext context, Destination destination, Usage<?> usage) {
        if (this.isLogAll() || this.isLogProducerEvents() || this.isLogInternalEvents()) {
            LOG.info("Destination is full: {}", (Object)destination.getName());
        }
        super.isFull(context, destination, usage);
    }

    @Override
    public void messageConsumed(ConnectionContext context, MessageReference messageReference) {
        if (this.isLogAll() || this.isLogConsumerEvents() || this.isLogInternalEvents()) {
            String msg = "Unable to display message.";
            msg = messageReference.getMessage().toString();
            LOG.info("Message consumed: {}", (Object)msg);
        }
        super.messageConsumed(context, messageReference);
    }

    @Override
    public void messageDelivered(ConnectionContext context, MessageReference messageReference) {
        if (this.isLogAll() || this.isLogConsumerEvents() || this.isLogInternalEvents()) {
            String msg = "Unable to display message.";
            msg = messageReference.getMessage().toString();
            LOG.info("Message delivered: {}", (Object)msg);
        }
        super.messageDelivered(context, messageReference);
    }

    @Override
    public void messageDiscarded(ConnectionContext context, Subscription sub, MessageReference messageReference) {
        if (this.isLogAll() || this.isLogInternalEvents()) {
            String msg = "Unable to display message.";
            msg = messageReference.getMessage().toString();
            LOG.info("Message discarded: {}", (Object)msg);
        }
        super.messageDiscarded(context, sub, messageReference);
    }

    @Override
    public void slowConsumer(ConnectionContext context, Destination destination, Subscription subs) {
        if (this.isLogAll() || this.isLogConsumerEvents() || this.isLogInternalEvents()) {
            LOG.info("Detected slow consumer on {}", (Object)destination.getName());
            StringBuffer buf = new StringBuffer("Connection(");
            buf.append(subs.getConsumerInfo().getConsumerId().getConnectionId());
            buf.append(") Session(");
            buf.append(subs.getConsumerInfo().getConsumerId().getSessionId());
            buf.append(")");
            LOG.info(buf.toString());
        }
        super.slowConsumer(context, destination, subs);
    }

    @Override
    public void nowMasterBroker() {
        if (this.isLogAll() || this.isLogInternalEvents()) {
            LOG.info("Is now the master broker: {}", (Object)this.getBrokerName());
        }
        super.nowMasterBroker();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("LoggingBrokerPlugin(");
        buf.append("logAll=");
        buf.append(this.isLogAll());
        buf.append(", logConnectionEvents=");
        buf.append(this.isLogConnectionEvents());
        buf.append(", logSessionEvents=");
        buf.append(this.isLogSessionEvents());
        buf.append(", logConsumerEvents=");
        buf.append(this.isLogConsumerEvents());
        buf.append(", logProducerEvents=");
        buf.append(this.isLogProducerEvents());
        buf.append(", logTransactionEvents=");
        buf.append(this.isLogTransactionEvents());
        buf.append(", logInternalEvents=");
        buf.append(this.isLogInternalEvents());
        buf.append(")");
        return buf.toString();
    }

    public void setPerDestinationLogger(boolean perDestinationLogger) {
        this.perDestinationLogger = perDestinationLogger;
    }

    public boolean isPerDestinationLogger() {
        return this.perDestinationLogger;
    }
}

