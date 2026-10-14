/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.Connection;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ConsumerBrokerExchange;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.virtual.VirtualDestination;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerControl;
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
import org.apache.activemq.store.PListStore;
import org.apache.activemq.thread.Scheduler;
import org.apache.activemq.usage.Usage;

public class BrokerFilter
implements Broker {
    protected final Broker next;

    public BrokerFilter(Broker next) {
        this.next = next;
    }

    public Broker getNext() {
        return this.next;
    }

    @Override
    public Broker getAdaptor(Class<?> type) {
        return type.isInstance(this) ? this : this.getNext().getAdaptor(type);
    }

    @Override
    public Map<ActiveMQDestination, Destination> getDestinationMap() {
        return this.getNext().getDestinationMap();
    }

    @Override
    public Map<ActiveMQDestination, Destination> getDestinationMap(ActiveMQDestination destination) {
        return this.getNext().getDestinationMap(destination);
    }

    @Override
    public Set<Destination> getDestinations(ActiveMQDestination destination) {
        return this.getNext().getDestinations(destination);
    }

    @Override
    public void acknowledge(ConsumerBrokerExchange consumerExchange, MessageAck ack) throws Exception {
        this.getNext().acknowledge(consumerExchange, ack);
    }

    @Override
    public Response messagePull(ConnectionContext context, MessagePull pull) throws Exception {
        return this.getNext().messagePull(context, pull);
    }

    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
        this.getNext().addConnection(context, info);
    }

    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        return this.getNext().addConsumer(context, info);
    }

    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        this.getNext().addProducer(context, info);
    }

    @Override
    public void commitTransaction(ConnectionContext context, TransactionId xid, boolean onePhase) throws Exception {
        this.getNext().commitTransaction(context, xid, onePhase);
    }

    @Override
    public void removeSubscription(ConnectionContext context, RemoveSubscriptionInfo info) throws Exception {
        this.getNext().removeSubscription(context, info);
    }

    @Override
    public TransactionId[] getPreparedTransactions(ConnectionContext context) throws Exception {
        return this.getNext().getPreparedTransactions(context);
    }

    @Override
    public int prepareTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        return this.getNext().prepareTransaction(context, xid);
    }

    @Override
    public void removeConnection(ConnectionContext context, ConnectionInfo info, Throwable error) throws Exception {
        this.getNext().removeConnection(context, info, error);
    }

    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        this.getNext().removeConsumer(context, info);
    }

    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        this.getNext().removeProducer(context, info);
    }

    @Override
    public void rollbackTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        this.getNext().rollbackTransaction(context, xid);
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        this.getNext().send(producerExchange, messageSend);
    }

    @Override
    public void beginTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        this.getNext().beginTransaction(context, xid);
    }

    @Override
    public void forgetTransaction(ConnectionContext context, TransactionId transactionId) throws Exception {
        this.getNext().forgetTransaction(context, transactionId);
    }

    @Override
    public Connection[] getClients() throws Exception {
        return this.getNext().getClients();
    }

    @Override
    public Destination addDestination(ConnectionContext context, ActiveMQDestination destination, boolean createIfTemporary) throws Exception {
        return this.getNext().addDestination(context, destination, createIfTemporary);
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        this.getNext().removeDestination(context, destination, timeout2);
    }

    @Override
    public ActiveMQDestination[] getDestinations() throws Exception {
        return this.getNext().getDestinations();
    }

    @Override
    public void start() throws Exception {
        this.getNext().start();
    }

    @Override
    public void stop() throws Exception {
        this.getNext().stop();
    }

    @Override
    public void addSession(ConnectionContext context, SessionInfo info) throws Exception {
        this.getNext().addSession(context, info);
    }

    @Override
    public void removeSession(ConnectionContext context, SessionInfo info) throws Exception {
        this.getNext().removeSession(context, info);
    }

    @Override
    public BrokerId getBrokerId() {
        return this.getNext().getBrokerId();
    }

    @Override
    public String getBrokerName() {
        return this.getNext().getBrokerName();
    }

    @Override
    public void gc() {
        this.getNext().gc();
    }

    @Override
    public void addBroker(Connection connection, BrokerInfo info) {
        this.getNext().addBroker(connection, info);
    }

    @Override
    public void removeBroker(Connection connection, BrokerInfo info) {
        this.getNext().removeBroker(connection, info);
    }

    @Override
    public BrokerInfo[] getPeerBrokerInfos() {
        return this.getNext().getPeerBrokerInfos();
    }

    @Override
    public void preProcessDispatch(MessageDispatch messageDispatch) {
        this.getNext().preProcessDispatch(messageDispatch);
    }

    @Override
    public void postProcessDispatch(MessageDispatch messageDispatch) {
        this.getNext().postProcessDispatch(messageDispatch);
    }

    @Override
    public void processDispatchNotification(MessageDispatchNotification messageDispatchNotification) throws Exception {
        this.getNext().processDispatchNotification(messageDispatchNotification);
    }

    @Override
    public boolean isStopped() {
        return this.getNext().isStopped();
    }

    @Override
    public Set<ActiveMQDestination> getDurableDestinations() {
        return this.getNext().getDurableDestinations();
    }

    @Override
    public void addDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        this.getNext().addDestinationInfo(context, info);
    }

    @Override
    public void removeDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        this.getNext().removeDestinationInfo(context, info);
    }

    @Override
    public boolean isFaultTolerantConfiguration() {
        return this.getNext().isFaultTolerantConfiguration();
    }

    @Override
    public ConnectionContext getAdminConnectionContext() {
        return this.getNext().getAdminConnectionContext();
    }

    @Override
    public void setAdminConnectionContext(ConnectionContext adminConnectionContext) {
        this.getNext().setAdminConnectionContext(adminConnectionContext);
    }

    @Override
    public PListStore getTempDataStore() {
        return this.getNext().getTempDataStore();
    }

    @Override
    public URI getVmConnectorURI() {
        return this.getNext().getVmConnectorURI();
    }

    @Override
    public void brokerServiceStarted() {
        this.getNext().brokerServiceStarted();
    }

    @Override
    public BrokerService getBrokerService() {
        return this.getNext().getBrokerService();
    }

    @Override
    public boolean isExpired(MessageReference messageReference) {
        return this.getNext().isExpired(messageReference);
    }

    @Override
    public void messageExpired(ConnectionContext context, MessageReference message, Subscription subscription) {
        this.getNext().messageExpired(context, message, subscription);
    }

    @Override
    public boolean sendToDeadLetterQueue(ConnectionContext context, MessageReference messageReference, Subscription subscription, Throwable poisonCause) {
        return this.getNext().sendToDeadLetterQueue(context, messageReference, subscription, poisonCause);
    }

    @Override
    public Broker getRoot() {
        return this.getNext().getRoot();
    }

    @Override
    public long getBrokerSequenceId() {
        return this.getNext().getBrokerSequenceId();
    }

    @Override
    public void fastProducer(ConnectionContext context, ProducerInfo producerInfo, ActiveMQDestination destination) {
        this.getNext().fastProducer(context, producerInfo, destination);
    }

    @Override
    public void isFull(ConnectionContext context, Destination destination, Usage<?> usage) {
        this.getNext().isFull(context, destination, usage);
    }

    @Override
    public void messageConsumed(ConnectionContext context, MessageReference messageReference) {
        this.getNext().messageConsumed(context, messageReference);
    }

    @Override
    public void messageDelivered(ConnectionContext context, MessageReference messageReference) {
        this.getNext().messageDelivered(context, messageReference);
    }

    @Override
    public void messageDiscarded(ConnectionContext context, Subscription sub, MessageReference messageReference) {
        this.getNext().messageDiscarded(context, sub, messageReference);
    }

    @Override
    public void slowConsumer(ConnectionContext context, Destination destination, Subscription subs) {
        this.getNext().slowConsumer(context, destination, subs);
    }

    @Override
    public void virtualDestinationAdded(ConnectionContext context, VirtualDestination virtualDestination) {
        this.getNext().virtualDestinationAdded(context, virtualDestination);
    }

    @Override
    public void virtualDestinationRemoved(ConnectionContext context, VirtualDestination virtualDestination) {
        this.getNext().virtualDestinationRemoved(context, virtualDestination);
    }

    @Override
    public void nowMasterBroker() {
        this.getNext().nowMasterBroker();
    }

    @Override
    public void processConsumerControl(ConsumerBrokerExchange consumerExchange, ConsumerControl control) {
        this.getNext().processConsumerControl(consumerExchange, control);
    }

    @Override
    public void reapplyInterceptor() {
        this.getNext().reapplyInterceptor();
    }

    @Override
    public Scheduler getScheduler() {
        return this.getNext().getScheduler();
    }

    @Override
    public ThreadPoolExecutor getExecutor() {
        return this.getNext().getExecutor();
    }

    @Override
    public void networkBridgeStarted(BrokerInfo brokerInfo, boolean createdByDuplex, String remoteIp) {
        this.getNext().networkBridgeStarted(brokerInfo, createdByDuplex, remoteIp);
    }

    @Override
    public void networkBridgeStopped(BrokerInfo brokerInfo) {
        this.getNext().networkBridgeStopped(brokerInfo);
    }
}

