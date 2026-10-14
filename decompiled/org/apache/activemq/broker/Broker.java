/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.activemq.Service;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.Connection;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Region;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.virtual.VirtualDestination;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.store.PListStore;
import org.apache.activemq.thread.Scheduler;
import org.apache.activemq.usage.Usage;

public interface Broker
extends Region,
Service {
    public Broker getAdaptor(Class<?> var1);

    public BrokerId getBrokerId();

    public String getBrokerName();

    public void addBroker(Connection var1, BrokerInfo var2);

    public void removeBroker(Connection var1, BrokerInfo var2);

    public void addConnection(ConnectionContext var1, ConnectionInfo var2) throws Exception;

    public void removeConnection(ConnectionContext var1, ConnectionInfo var2, Throwable var3) throws Exception;

    public void addSession(ConnectionContext var1, SessionInfo var2) throws Exception;

    public void removeSession(ConnectionContext var1, SessionInfo var2) throws Exception;

    @Override
    public void addProducer(ConnectionContext var1, ProducerInfo var2) throws Exception;

    @Override
    public void removeProducer(ConnectionContext var1, ProducerInfo var2) throws Exception;

    public Connection[] getClients() throws Exception;

    public ActiveMQDestination[] getDestinations() throws Exception;

    public Map<ActiveMQDestination, Destination> getDestinationMap(ActiveMQDestination var1);

    public TransactionId[] getPreparedTransactions(ConnectionContext var1) throws Exception;

    public void beginTransaction(ConnectionContext var1, TransactionId var2) throws Exception;

    public int prepareTransaction(ConnectionContext var1, TransactionId var2) throws Exception;

    public void rollbackTransaction(ConnectionContext var1, TransactionId var2) throws Exception;

    public void commitTransaction(ConnectionContext var1, TransactionId var2, boolean var3) throws Exception;

    public void forgetTransaction(ConnectionContext var1, TransactionId var2) throws Exception;

    public BrokerInfo[] getPeerBrokerInfos();

    public void preProcessDispatch(MessageDispatch var1);

    public void postProcessDispatch(MessageDispatch var1);

    public boolean isStopped();

    public Set<ActiveMQDestination> getDurableDestinations();

    public void addDestinationInfo(ConnectionContext var1, DestinationInfo var2) throws Exception;

    public void removeDestinationInfo(ConnectionContext var1, DestinationInfo var2) throws Exception;

    public boolean isFaultTolerantConfiguration();

    public ConnectionContext getAdminConnectionContext();

    public void setAdminConnectionContext(ConnectionContext var1);

    public PListStore getTempDataStore();

    public URI getVmConnectorURI();

    public void brokerServiceStarted();

    public BrokerService getBrokerService();

    public Broker getRoot();

    public boolean isExpired(MessageReference var1);

    public void messageExpired(ConnectionContext var1, MessageReference var2, Subscription var3);

    public boolean sendToDeadLetterQueue(ConnectionContext var1, MessageReference var2, Subscription var3, Throwable var4);

    public long getBrokerSequenceId();

    public void messageConsumed(ConnectionContext var1, MessageReference var2);

    public void messageDelivered(ConnectionContext var1, MessageReference var2);

    public void messageDiscarded(ConnectionContext var1, Subscription var2, MessageReference var3);

    public void slowConsumer(ConnectionContext var1, Destination var2, Subscription var3);

    public void fastProducer(ConnectionContext var1, ProducerInfo var2, ActiveMQDestination var3);

    public void isFull(ConnectionContext var1, Destination var2, Usage<?> var3);

    public void virtualDestinationAdded(ConnectionContext var1, VirtualDestination var2);

    public void virtualDestinationRemoved(ConnectionContext var1, VirtualDestination var2);

    public void nowMasterBroker();

    public Scheduler getScheduler();

    public ThreadPoolExecutor getExecutor();

    public void networkBridgeStarted(BrokerInfo var1, boolean var2, String var3);

    public void networkBridgeStopped(BrokerInfo var1);
}

