/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.jms.InvalidClientIDException;
import javax.jms.JMSException;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.Connection;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ConsumerBrokerExchange;
import org.apache.activemq.broker.EmptyBroker;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.TransportConnection;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.region.BaseDestination;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFactory;
import org.apache.activemq.broker.region.DestinationInterceptor;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.QueueRegion;
import org.apache.activemq.broker.region.Region;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.TempQueueRegion;
import org.apache.activemq.broker.region.TempTopicRegion;
import org.apache.activemq.broker.region.TopicRegion;
import org.apache.activemq.broker.region.policy.DeadLetterStrategy;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.ConnectionId;
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
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.state.ConnectionState;
import org.apache.activemq.store.PListStore;
import org.apache.activemq.thread.Scheduler;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transport.TransmitCallback;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.util.BrokerSupport;
import org.apache.activemq.util.IdGenerator;
import org.apache.activemq.util.InetAddressUtil;
import org.apache.activemq.util.LongSequenceGenerator;
import org.apache.activemq.util.ServiceStopper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionBroker
extends EmptyBroker {
    public static final String ORIGINAL_EXPIRATION = "originalExpiration";
    private static final Logger LOG = LoggerFactory.getLogger(RegionBroker.class);
    private static final IdGenerator BROKER_ID_GENERATOR = new IdGenerator();
    protected final DestinationStatistics destinationStatistics = new DestinationStatistics();
    protected DestinationFactory destinationFactory;
    protected final Map<ConnectionId, ConnectionState> connectionStates = Collections.synchronizedMap(new HashMap());
    private final Region queueRegion;
    private final Region topicRegion;
    private final Region tempQueueRegion;
    private final Region tempTopicRegion;
    protected final BrokerService brokerService;
    private boolean started;
    private boolean keepDurableSubsActive;
    private final CopyOnWriteArrayList<Connection> connections = new CopyOnWriteArrayList();
    private final Map<ActiveMQDestination, ActiveMQDestination> destinationGate = new HashMap<ActiveMQDestination, ActiveMQDestination>();
    private final Map<ActiveMQDestination, Destination> destinations = new ConcurrentHashMap<ActiveMQDestination, Destination>();
    private final Map<BrokerId, BrokerInfo> brokerInfos = new HashMap<BrokerId, BrokerInfo>();
    private final LongSequenceGenerator sequenceGenerator = new LongSequenceGenerator();
    private BrokerId brokerId;
    private String brokerName;
    private final Map<String, ConnectionContext> clientIdSet = new HashMap<String, ConnectionContext>();
    private final DestinationInterceptor destinationInterceptor;
    private ConnectionContext adminConnectionContext;
    private final Scheduler scheduler;
    private final ThreadPoolExecutor executor;
    private boolean allowTempAutoCreationOnSend;
    private final ReentrantReadWriteLock inactiveDestinationsPurgeLock = new ReentrantReadWriteLock();
    private final Runnable purgeInactiveDestinationsTask = new Runnable(){

        @Override
        public void run() {
            RegionBroker.this.purgeInactiveDestinations();
        }
    };

    public RegionBroker(BrokerService brokerService, TaskRunnerFactory taskRunnerFactory, SystemUsage memoryManager, DestinationFactory destinationFactory, DestinationInterceptor destinationInterceptor, Scheduler scheduler, ThreadPoolExecutor executor2) throws IOException {
        this.brokerService = brokerService;
        this.executor = executor2;
        this.scheduler = scheduler;
        if (destinationFactory == null) {
            throw new IllegalArgumentException("null destinationFactory");
        }
        this.sequenceGenerator.setLastSequenceId(destinationFactory.getLastMessageBrokerSequenceId());
        this.destinationFactory = destinationFactory;
        this.queueRegion = this.createQueueRegion(memoryManager, taskRunnerFactory, destinationFactory);
        this.topicRegion = this.createTopicRegion(memoryManager, taskRunnerFactory, destinationFactory);
        this.destinationInterceptor = destinationInterceptor;
        this.tempQueueRegion = this.createTempQueueRegion(memoryManager, taskRunnerFactory, destinationFactory);
        this.tempTopicRegion = this.createTempTopicRegion(memoryManager, taskRunnerFactory, destinationFactory);
    }

    @Override
    public Map<ActiveMQDestination, Destination> getDestinationMap() {
        HashMap<ActiveMQDestination, Destination> answer = new HashMap<ActiveMQDestination, Destination>(this.getQueueRegion().getDestinationMap());
        answer.putAll(this.getTopicRegion().getDestinationMap());
        return answer;
    }

    @Override
    public Map<ActiveMQDestination, Destination> getDestinationMap(ActiveMQDestination destination) {
        try {
            return this.getRegion(destination).getDestinationMap();
        }
        catch (JMSException jmse) {
            return Collections.emptyMap();
        }
    }

    @Override
    public Set<Destination> getDestinations(ActiveMQDestination destination) {
        try {
            return this.getRegion(destination).getDestinations(destination);
        }
        catch (JMSException jmse) {
            return Collections.emptySet();
        }
    }

    public Region getQueueRegion() {
        return this.queueRegion;
    }

    public Region getTempQueueRegion() {
        return this.tempQueueRegion;
    }

    public Region getTempTopicRegion() {
        return this.tempTopicRegion;
    }

    public Region getTopicRegion() {
        return this.topicRegion;
    }

    protected Region createTempTopicRegion(SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        return new TempTopicRegion(this, this.destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
    }

    protected Region createTempQueueRegion(SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        return new TempQueueRegion(this, this.destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
    }

    protected Region createTopicRegion(SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        return new TopicRegion(this, this.destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
    }

    protected Region createQueueRegion(SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        return new QueueRegion(this, this.destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
    }

    @Override
    public void start() throws Exception {
        this.started = true;
        this.queueRegion.start();
        this.topicRegion.start();
        this.tempQueueRegion.start();
        this.tempTopicRegion.start();
        int period = this.brokerService.getSchedulePeriodForDestinationPurge();
        if (period > 0) {
            this.scheduler.executePeriodically(this.purgeInactiveDestinationsTask, period);
        }
    }

    @Override
    public void stop() throws Exception {
        this.started = false;
        this.scheduler.cancel(this.purgeInactiveDestinationsTask);
        ServiceStopper ss = new ServiceStopper();
        this.doStop(ss);
        ss.throwFirstException();
        this.clientIdSet.clear();
        this.connections.clear();
        this.destinations.clear();
        this.brokerInfos.clear();
    }

    public PolicyMap getDestinationPolicy() {
        return this.brokerService != null ? this.brokerService.getDestinationPolicy() : null;
    }

    public ConnectionContext getConnectionContext(String clientId) {
        return this.clientIdSet.get(clientId);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {
        String clientId = info.getClientId();
        if (clientId == null) {
            throw new InvalidClientIDException("No clientID specified for connection request");
        }
        ConnectionContext oldContext = null;
        Map<String, ConnectionContext> map = this.clientIdSet;
        synchronized (map) {
            oldContext = this.clientIdSet.get(clientId);
            if (oldContext != null) {
                if (!context.isAllowLinkStealing()) throw new InvalidClientIDException("Broker: " + this.getBrokerName() + " - Client: " + clientId + " already connected from " + oldContext.getConnection().getRemoteAddress());
                this.clientIdSet.put(clientId, context);
            } else {
                this.clientIdSet.put(clientId, context);
            }
        }
        if (oldContext != null) {
            if (oldContext.getConnection() != null) {
                Connection connection = oldContext.getConnection();
                LOG.warn("Stealing link for clientId {} From Connection {}", (Object)clientId, (Object)oldContext.getConnection());
                if (connection instanceof TransportConnection) {
                    TransportConnection transportConnection = (TransportConnection)connection;
                    transportConnection.stopAsync(new IOException("Stealing link for clientId " + clientId + " From Connection " + oldContext.getConnection().getConnectionId()));
                } else {
                    connection.stop();
                }
            } else {
                LOG.error("No Connection found for {}", (Object)oldContext);
            }
        }
        this.connections.add(context.getConnection());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeConnection(ConnectionContext context, ConnectionInfo info, Throwable error) throws Exception {
        String clientId = info.getClientId();
        if (clientId == null) {
            throw new InvalidClientIDException("No clientID specified for connection disconnect request");
        }
        Map<String, ConnectionContext> map = this.clientIdSet;
        synchronized (map) {
            ConnectionContext oldValue = this.clientIdSet.get(clientId);
            if (oldValue == context && this.isEqual(oldValue.getConnectionId(), info.getConnectionId())) {
                this.clientIdSet.remove(clientId);
            }
        }
        this.connections.remove(context.getConnection());
    }

    protected boolean isEqual(ConnectionId connectionId, ConnectionId connectionId2) {
        return connectionId == connectionId2 || connectionId != null && connectionId.equals(connectionId2);
    }

    @Override
    public Connection[] getClients() throws Exception {
        ArrayList<Connection> l = new ArrayList<Connection>(this.connections);
        Connection[] rc = new Connection[l.size()];
        l.toArray(rc);
        return rc;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Destination addDestination(ConnectionContext context, ActiveMQDestination destination, boolean createIfTemp) throws Exception {
        Destination answer = this.destinations.get(destination);
        if (answer != null) {
            return answer;
        }
        Map<ActiveMQDestination, ActiveMQDestination> map = this.destinationGate;
        synchronized (map) {
            answer = this.destinations.get(destination);
            if (answer != null) {
                return answer;
            }
            if (this.destinationGate.get(destination) != null) {
                while (this.destinationGate.containsKey(destination)) {
                    this.destinationGate.wait();
                }
                answer = this.destinations.get(destination);
                if (answer != null) {
                    return answer;
                }
                this.destinationGate.put(destination, destination);
            }
        }
        try {
            boolean create = true;
            if (destination.isTemporary()) {
                create = createIfTemp;
            }
            answer = this.getRegion(destination).addDestination(context, destination, create);
            this.destinations.put(destination, answer);
        }
        finally {
            Map<ActiveMQDestination, ActiveMQDestination> map2 = this.destinationGate;
            synchronized (map2) {
                this.destinationGate.remove(destination);
                this.destinationGate.notifyAll();
            }
        }
        return answer;
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        if (this.destinations.containsKey(destination)) {
            this.getRegion(destination).removeDestination(context, destination, timeout2);
            this.destinations.remove(destination);
        }
    }

    @Override
    public void addDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        this.addDestination(context, info.getDestination(), true);
    }

    @Override
    public void removeDestinationInfo(ConnectionContext context, DestinationInfo info) throws Exception {
        this.removeDestination(context, info.getDestination(), info.getTimeout());
    }

    @Override
    public ActiveMQDestination[] getDestinations() throws Exception {
        ArrayList<ActiveMQDestination> l = new ArrayList<ActiveMQDestination>(this.getDestinationMap().keySet());
        ActiveMQDestination[] rc = new ActiveMQDestination[l.size()];
        l.toArray(rc);
        return rc;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        ActiveMQDestination destination = info.getDestination();
        if (destination != null) {
            this.inactiveDestinationsPurgeLock.readLock().lock();
            try {
                context.getBroker().addDestination(context, destination, this.isAllowTempAutoCreationOnSend());
                this.getRegion(destination).addProducer(context, info);
            }
            finally {
                this.inactiveDestinationsPurgeLock.readLock().unlock();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        ActiveMQDestination destination = info.getDestination();
        if (destination != null) {
            this.inactiveDestinationsPurgeLock.readLock().lock();
            try {
                this.getRegion(destination).removeProducer(context, info);
            }
            finally {
                this.inactiveDestinationsPurgeLock.readLock().unlock();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        ActiveMQDestination destination = info.getDestination();
        if (this.destinationInterceptor != null) {
            this.destinationInterceptor.create(this, context, destination);
        }
        this.inactiveDestinationsPurgeLock.readLock().lock();
        try {
            Subscription subscription = this.getRegion(destination).addConsumer(context, info);
            return subscription;
        }
        finally {
            this.inactiveDestinationsPurgeLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        ActiveMQDestination destination = info.getDestination();
        this.inactiveDestinationsPurgeLock.readLock().lock();
        try {
            this.getRegion(destination).removeConsumer(context, info);
        }
        finally {
            this.inactiveDestinationsPurgeLock.readLock().unlock();
        }
    }

    @Override
    public void removeSubscription(ConnectionContext context, RemoveSubscriptionInfo info) throws Exception {
        this.inactiveDestinationsPurgeLock.readLock().lock();
        try {
            this.topicRegion.removeSubscription(context, info);
        }
        finally {
            this.inactiveDestinationsPurgeLock.readLock().unlock();
        }
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, Message message) throws Exception {
        ActiveMQDestination destination = message.getDestination();
        message.setBrokerInTime(System.currentTimeMillis());
        if (producerExchange.isMutable() || producerExchange.getRegion() == null || producerExchange.getRegionDestination() != null && producerExchange.getRegionDestination().isDisposed()) {
            producerExchange.getConnectionContext().getBroker().addDestination(producerExchange.getConnectionContext(), destination, this.isAllowTempAutoCreationOnSend());
            producerExchange.setRegion(this.getRegion(destination));
            producerExchange.setRegionDestination(null);
        }
        producerExchange.getRegion().send(producerExchange, message);
        if (producerExchange.isMutable()) {
            producerExchange.setRegionDestination(null);
            producerExchange.setRegion(null);
        }
    }

    @Override
    public void acknowledge(ConsumerBrokerExchange consumerExchange, MessageAck ack) throws Exception {
        if (consumerExchange.isWildcard() || consumerExchange.getRegion() == null) {
            ActiveMQDestination destination = ack.getDestination();
            consumerExchange.setRegion(this.getRegion(destination));
        }
        consumerExchange.getRegion().acknowledge(consumerExchange, ack);
    }

    public Region getRegion(ActiveMQDestination destination) throws JMSException {
        switch (destination.getDestinationType()) {
            case 1: {
                return this.queueRegion;
            }
            case 2: {
                return this.topicRegion;
            }
            case 5: {
                return this.tempQueueRegion;
            }
            case 6: {
                return this.tempTopicRegion;
            }
        }
        throw this.createUnknownDestinationTypeException(destination);
    }

    @Override
    public Response messagePull(ConnectionContext context, MessagePull pull) throws Exception {
        ActiveMQDestination destination = pull.getDestination();
        return this.getRegion(destination).messagePull(context, pull);
    }

    @Override
    public TransactionId[] getPreparedTransactions(ConnectionContext context) throws Exception {
        throw new IllegalAccessException("Transaction operation not implemented by this broker.");
    }

    @Override
    public void beginTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        throw new IllegalAccessException("Transaction operation not implemented by this broker.");
    }

    @Override
    public int prepareTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        throw new IllegalAccessException("Transaction operation not implemented by this broker.");
    }

    @Override
    public void rollbackTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        throw new IllegalAccessException("Transaction operation not implemented by this broker.");
    }

    @Override
    public void commitTransaction(ConnectionContext context, TransactionId xid, boolean onePhase) throws Exception {
        throw new IllegalAccessException("Transaction operation not implemented by this broker.");
    }

    @Override
    public void forgetTransaction(ConnectionContext context, TransactionId transactionId) throws Exception {
        throw new IllegalAccessException("Transaction operation not implemented by this broker.");
    }

    @Override
    public void gc() {
        this.queueRegion.gc();
        this.topicRegion.gc();
    }

    @Override
    public BrokerId getBrokerId() {
        if (this.brokerId == null) {
            this.brokerId = new BrokerId(BROKER_ID_GENERATOR.generateId());
        }
        return this.brokerId;
    }

    public void setBrokerId(BrokerId brokerId) {
        this.brokerId = brokerId;
    }

    @Override
    public String getBrokerName() {
        if (this.brokerName == null) {
            try {
                this.brokerName = InetAddressUtil.getLocalHostName().toLowerCase(Locale.ENGLISH);
            }
            catch (Exception e) {
                this.brokerName = "localhost";
            }
        }
        return this.brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public DestinationStatistics getDestinationStatistics() {
        return this.destinationStatistics;
    }

    protected JMSException createUnknownDestinationTypeException(ActiveMQDestination destination) {
        return new JMSException("Unknown destination type: " + destination.getDestinationType());
    }

    @Override
    public synchronized void addBroker(Connection connection, BrokerInfo info) {
        BrokerInfo existing = this.brokerInfos.get(info.getBrokerId());
        if (existing == null) {
            existing = info.copy();
            existing.setPeerBrokerInfos(null);
            this.brokerInfos.put(info.getBrokerId(), existing);
        }
        existing.incrementRefCount();
        LOG.debug("{} addBroker: {} brokerInfo size: {}", this.getBrokerName(), info.getBrokerName(), this.brokerInfos.size());
        this.addBrokerInClusterUpdate(info);
    }

    @Override
    public synchronized void removeBroker(Connection connection, BrokerInfo info) {
        if (info != null) {
            BrokerInfo existing = this.brokerInfos.get(info.getBrokerId());
            if (existing != null && existing.decrementRefCount() == 0) {
                this.brokerInfos.remove(info.getBrokerId());
            }
            LOG.debug("{} removeBroker: {} brokerInfo size: {}", this.getBrokerName(), info.getBrokerName(), this.brokerInfos.size());
            if (!this.brokerService.isStopping()) {
                this.removeBrokerInClusterUpdate(info);
            }
        }
    }

    @Override
    public synchronized BrokerInfo[] getPeerBrokerInfos() {
        BrokerInfo[] result = new BrokerInfo[this.brokerInfos.size()];
        result = this.brokerInfos.values().toArray(result);
        return result;
    }

    @Override
    public void preProcessDispatch(final MessageDispatch messageDispatch) {
        final Message message = messageDispatch.getMessage();
        if (message != null) {
            long endTime = System.currentTimeMillis();
            message.setBrokerOutTime(endTime);
            if (this.getBrokerService().isEnableStatistics()) {
                long totalTime = endTime - message.getBrokerInTime();
                ((Destination)message.getRegionDestination()).getDestinationStatistics().getProcessTime().addTime(totalTime);
            }
            if (((BaseDestination)message.getRegionDestination()).isPersistJMSRedelivered() && !message.isRedelivered()) {
                int originalValue = message.getRedeliveryCounter();
                message.incrementRedeliveryCounter();
                try {
                    if (message.isPersistent()) {
                        ((BaseDestination)message.getRegionDestination()).getMessageStore().updateMessage(message);
                    }
                    messageDispatch.setTransmitCallback(new TransmitCallback(){
                        final TransmitCallback delegate;
                        {
                            this.delegate = messageDispatch.getTransmitCallback();
                        }

                        @Override
                        public void onSuccess() {
                            message.incrementRedeliveryCounter();
                            if (this.delegate != null) {
                                this.delegate.onSuccess();
                            }
                        }

                        @Override
                        public void onFailure() {
                            if (this.delegate != null) {
                                this.delegate.onFailure();
                            }
                        }
                    });
                }
                catch (IOException error) {
                    RuntimeException runtimeException = new RuntimeException("Failed to persist JMSRedeliveryFlag on " + message.getMessageId() + " in " + message.getDestination(), error);
                    LOG.warn(runtimeException.getLocalizedMessage(), runtimeException);
                    throw runtimeException;
                }
                finally {
                    message.setRedeliveryCounter(originalValue);
                }
            }
        }
    }

    @Override
    public void postProcessDispatch(MessageDispatch messageDispatch) {
    }

    @Override
    public void processDispatchNotification(MessageDispatchNotification messageDispatchNotification) throws Exception {
        ActiveMQDestination destination = messageDispatchNotification.getDestination();
        this.getRegion(destination).processDispatchNotification(messageDispatchNotification);
    }

    @Override
    public boolean isStopped() {
        return !this.started;
    }

    @Override
    public Set<ActiveMQDestination> getDurableDestinations() {
        return this.destinationFactory.getDestinations();
    }

    protected void doStop(ServiceStopper ss) {
        ss.stop(this.queueRegion);
        ss.stop(this.topicRegion);
        ss.stop(this.tempQueueRegion);
        ss.stop(this.tempTopicRegion);
    }

    public boolean isKeepDurableSubsActive() {
        return this.keepDurableSubsActive;
    }

    public void setKeepDurableSubsActive(boolean keepDurableSubsActive) {
        this.keepDurableSubsActive = keepDurableSubsActive;
        ((TopicRegion)this.topicRegion).setKeepDurableSubsActive(keepDurableSubsActive);
    }

    public DestinationInterceptor getDestinationInterceptor() {
        return this.destinationInterceptor;
    }

    @Override
    public ConnectionContext getAdminConnectionContext() {
        return this.adminConnectionContext;
    }

    @Override
    public void setAdminConnectionContext(ConnectionContext adminConnectionContext) {
        this.adminConnectionContext = adminConnectionContext;
    }

    public Map<ConnectionId, ConnectionState> getConnectionStates() {
        return this.connectionStates;
    }

    @Override
    public PListStore getTempDataStore() {
        return this.brokerService.getTempDataStore();
    }

    @Override
    public URI getVmConnectorURI() {
        return this.brokerService.getVmConnectorURI();
    }

    @Override
    public void brokerServiceStarted() {
    }

    @Override
    public BrokerService getBrokerService() {
        return this.brokerService;
    }

    @Override
    public boolean isExpired(MessageReference messageReference) {
        return messageReference.canProcessAsExpired();
    }

    private boolean stampAsExpired(Message message) throws IOException {
        boolean stamped = false;
        if (message.getProperty(ORIGINAL_EXPIRATION) == null) {
            long expiration = message.getExpiration();
            message.setProperty(ORIGINAL_EXPIRATION, new Long(expiration));
            stamped = true;
        }
        return stamped;
    }

    @Override
    public void messageExpired(ConnectionContext context, MessageReference node, Subscription subscription) {
        LOG.debug("Message expired {}", (Object)node);
        this.getRoot().sendToDeadLetterQueue(context, node, subscription, new Throwable("Message Expired. Expiration:" + node.getExpiration()));
    }

    @Override
    public boolean sendToDeadLetterQueue(ConnectionContext context, MessageReference node, Subscription subscription, Throwable poisonCause) {
        try {
            Message message;
            if (node != null && (message = node.getMessage()) != null && node.getRegionDestination() != null) {
                DeadLetterStrategy deadLetterStrategy = ((Destination)node.getRegionDestination()).getDeadLetterStrategy();
                if (deadLetterStrategy != null) {
                    if (deadLetterStrategy.isSendToDeadLetterQueue(message)) {
                        ActiveMQDestination deadLetterDestination = deadLetterStrategy.getDeadLetterQueueFor(message, subscription);
                        if (deadLetterDestination.equals(message.getDestination())) {
                            LOG.debug("Not re-adding to DLQ: {}, dest: {}", (Object)message.getMessageId(), (Object)message.getDestination());
                            return false;
                        }
                        message = message.copy();
                        long dlqExpiration = deadLetterStrategy.getExpiration();
                        if (dlqExpiration > 0L) {
                            dlqExpiration += System.currentTimeMillis();
                        } else {
                            this.stampAsExpired(message);
                        }
                        message.setExpiration(dlqExpiration);
                        if (!message.isPersistent()) {
                            message.setPersistent(true);
                            message.setProperty("originalDeliveryMode", "NON_PERSISTENT");
                        }
                        if (poisonCause != null) {
                            message.setProperty("dlqDeliveryFailureCause", poisonCause.toString());
                        }
                        ConnectionContext adminContext = context;
                        if (context.getSecurityContext() == null || !context.getSecurityContext().isBrokerContext()) {
                            adminContext = BrokerSupport.getConnectionContext(this);
                        }
                        this.addDestination(adminContext, deadLetterDestination, false).getActiveMQDestination().setDLQ(true);
                        BrokerSupport.resendNoCopy(adminContext, message, deadLetterDestination);
                        return true;
                    }
                } else {
                    LOG.debug("Dead Letter message with no DLQ strategy in place, message id: {}, destination: {}", (Object)message.getMessageId(), (Object)message.getDestination());
                }
            }
        }
        catch (Exception e) {
            LOG.warn("Caught an exception sending to DLQ: {}", (Object)node, (Object)e);
        }
        return false;
    }

    @Override
    public Broker getRoot() {
        try {
            return this.getBrokerService().getBroker();
        }
        catch (Exception e) {
            LOG.error("Trying to get Root Broker", e);
            throw new RuntimeException("The broker from the BrokerService should not throw an exception");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long getBrokerSequenceId() {
        LongSequenceGenerator longSequenceGenerator = this.sequenceGenerator;
        synchronized (longSequenceGenerator) {
            return this.sequenceGenerator.getNextSequenceId();
        }
    }

    @Override
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public ThreadPoolExecutor getExecutor() {
        return this.executor;
    }

    @Override
    public void processConsumerControl(ConsumerBrokerExchange consumerExchange, ConsumerControl control) {
        ActiveMQDestination destination = control.getDestination();
        try {
            this.getRegion(destination).processConsumerControl(consumerExchange, control);
        }
        catch (JMSException jmse) {
            LOG.warn("unmatched destination: {}, in consumerControl: {}", (Object)destination, (Object)control);
        }
    }

    protected void addBrokerInClusterUpdate(BrokerInfo info) {
        List<TransportConnector> connectors = this.brokerService.getTransportConnectors();
        for (TransportConnector connector : connectors) {
            if (!connector.isUpdateClusterClients()) continue;
            connector.addPeerBroker(info);
            connector.updateClientClusterInfo();
        }
    }

    protected void removeBrokerInClusterUpdate(BrokerInfo info) {
        List<TransportConnector> connectors = this.brokerService.getTransportConnectors();
        for (TransportConnector connector : connectors) {
            if (!connector.isUpdateClusterClients() || !connector.isUpdateClusterClientsOnRemove()) continue;
            connector.removePeerBroker(info);
            connector.updateClientClusterInfo();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void purgeInactiveDestinations() {
        this.inactiveDestinationsPurgeLock.writeLock().lock();
        try {
            ArrayList<Destination> list = new ArrayList<Destination>();
            Map<ActiveMQDestination, Destination> map = this.getDestinationMap();
            if (this.isAllowTempAutoCreationOnSend()) {
                map.putAll(this.tempQueueRegion.getDestinationMap());
                map.putAll(this.tempTopicRegion.getDestinationMap());
            }
            long maxPurgedDests = this.brokerService.getMaxPurgedDestinationsPerSweep();
            long timeStamp = System.currentTimeMillis();
            for (Destination d : map.values()) {
                d.markForGC(timeStamp);
                if (!d.canGC()) continue;
                list.add(d);
                if (maxPurgedDests <= 0L || (long)list.size() != maxPurgedDests) continue;
                break;
            }
            if (!list.isEmpty()) {
                ConnectionContext context = BrokerSupport.getConnectionContext(this);
                context.setBroker(this);
                for (Destination dest : list) {
                    Logger log = LOG;
                    if (dest instanceof BaseDestination) {
                        log = ((BaseDestination)dest).getLog();
                    }
                    log.info("{} Inactive for longer than {} ms - removing ...", (Object)dest.getName(), (Object)dest.getInactiveTimeoutBeforeGC());
                    try {
                        this.getRoot().removeDestination(context, dest.getActiveMQDestination(), this.isAllowTempAutoCreationOnSend() ? 1L : 0L);
                    }
                    catch (Exception e) {
                        LOG.error("Failed to remove inactive destination {}", (Object)dest, (Object)e);
                    }
                }
            }
        }
        finally {
            this.inactiveDestinationsPurgeLock.writeLock().unlock();
        }
    }

    public boolean isAllowTempAutoCreationOnSend() {
        return this.allowTempAutoCreationOnSend;
    }

    public void setAllowTempAutoCreationOnSend(boolean allowTempAutoCreationOnSend) {
        this.allowTempAutoCreationOnSend = allowTempAutoCreationOnSend;
    }

    @Override
    public void reapplyInterceptor() {
        this.queueRegion.reapplyInterceptor();
        this.topicRegion.reapplyInterceptor();
        this.tempQueueRegion.reapplyInterceptor();
        this.tempTopicRegion.reapplyInterceptor();
    }
}

