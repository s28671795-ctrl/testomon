/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.jms.IllegalStateException;
import javax.jms.JMSException;
import org.apache.activemq.DestinationDoesNotExistException;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ConsumerBrokerExchange;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.AbstractSubscription;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFactory;
import org.apache.activemq.broker.region.DestinationInterceptor;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.broker.region.QueueBrowserSubscription;
import org.apache.activemq.broker.region.Region;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.RegionStatistics;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.virtual.CompositeDestinationFilter;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerControl;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.command.MessagePull;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.filter.DestinationMap;
import org.apache.activemq.security.SecurityContext;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.usage.SystemUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRegion
implements Region {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractRegion.class);
    protected final Map<ActiveMQDestination, Destination> destinations = new ConcurrentHashMap<ActiveMQDestination, Destination>();
    protected final DestinationMap destinationMap = new DestinationMap();
    protected final Map<ConsumerId, Subscription> subscriptions = new ConcurrentHashMap<ConsumerId, Subscription>();
    protected final SystemUsage usageManager;
    protected final DestinationFactory destinationFactory;
    protected final DestinationStatistics destinationStatistics;
    protected final RegionStatistics regionStatistics = new RegionStatistics();
    protected final RegionBroker broker;
    protected boolean autoCreateDestinations = true;
    protected final TaskRunnerFactory taskRunnerFactory;
    protected final ReentrantReadWriteLock destinationsLock = new ReentrantReadWriteLock();
    protected final Map<ConsumerId, Object> consumerChangeMutexMap = new HashMap<ConsumerId, Object>();
    protected boolean started;

    public AbstractRegion(RegionBroker broker, DestinationStatistics destinationStatistics, SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        if (broker == null) {
            throw new IllegalArgumentException("null broker");
        }
        this.broker = broker;
        this.destinationStatistics = destinationStatistics;
        this.usageManager = memoryManager;
        this.taskRunnerFactory = taskRunnerFactory;
        if (destinationFactory == null) {
            throw new IllegalArgumentException("null destinationFactory");
        }
        this.destinationFactory = destinationFactory;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void start() throws Exception {
        this.started = true;
        Set<ActiveMQDestination> inactiveDests = this.getInactiveDestinations();
        for (ActiveMQDestination activeMQDestination : inactiveDests) {
            ConnectionContext context = new ConnectionContext();
            context.setBroker(this.broker.getBrokerService().getBroker());
            context.setSecurityContext(SecurityContext.BROKER_SECURITY_CONTEXT);
            context.getBroker().addDestination(context, activeMQDestination, false);
        }
        this.destinationsLock.readLock().lock();
        try {
            for (Destination destination : this.destinations.values()) {
                destination.start();
            }
        }
        finally {
            this.destinationsLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void stop() throws Exception {
        this.started = false;
        this.destinationsLock.readLock().lock();
        try {
            for (Destination dest : this.destinations.values()) {
                dest.stop();
            }
        }
        finally {
            this.destinationsLock.readLock().unlock();
        }
        this.destinationsLock.writeLock().lock();
        try {
            this.destinations.clear();
            this.regionStatistics.getAdvisoryDestinations().reset();
            this.regionStatistics.getDestinations().reset();
            this.regionStatistics.getAllDestinations().reset();
        }
        finally {
            this.destinationsLock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Destination addDestination(ConnectionContext context, ActiveMQDestination destination, boolean createIfTemporary) throws Exception {
        this.destinationsLock.writeLock().lock();
        try {
            Destination dest = this.destinations.get(destination);
            if (dest == null) {
                if (!destination.isTemporary() || createIfTemporary) {
                    this.validateMaxDestinations(destination);
                    LOG.debug("{} adding destination: {}", (Object)this.broker.getBrokerName(), (Object)destination);
                    dest = this.createDestination(context, destination);
                    DestinationInterceptor destinationInterceptor = this.broker.getDestinationInterceptor();
                    if (destinationInterceptor != null) {
                        dest = destinationInterceptor.intercept(dest);
                    }
                    dest.start();
                    this.addSubscriptionsForDestination(context, dest);
                    this.destinations.put(destination, dest);
                    this.updateRegionDestCounts(destination, 1);
                    this.destinationMap.unsynchronizedPut(destination, dest);
                }
                if (dest == null) {
                    throw new DestinationDoesNotExistException(destination.getQualifiedName());
                }
            }
            Destination destination2 = dest;
            return destination2;
        }
        finally {
            this.destinationsLock.writeLock().unlock();
        }
    }

    public Map<ConsumerId, Subscription> getSubscriptions() {
        return this.subscriptions;
    }

    protected void updateRegionDestCounts(ActiveMQDestination destination, int count) {
        if (destination != null) {
            if (AdvisorySupport.isAdvisoryTopic(destination)) {
                this.regionStatistics.getAdvisoryDestinations().add(count);
            } else {
                this.regionStatistics.getDestinations().add(count);
            }
            this.regionStatistics.getAllDestinations().add(count);
        }
    }

    protected void validateMaxDestinations(ActiveMQDestination destination) throws Exception {
        int destinationSize;
        PolicyEntry entry;
        if (this.broker.getDestinationPolicy() != null && (entry = this.broker.getDestinationPolicy().getEntryFor(destination)) != null && entry.getMaxDestinations() >= 0 && !AdvisorySupport.isAdvisoryTopic(destination) && (destinationSize = (int)(entry.getDestination() != null ? (long)this.destinationMap.unsynchronizedGet(entry.getDestination()).size() : this.regionStatistics.getDestinations().getCount())) >= entry.getMaxDestinations()) {
            if (entry.getDestination() != null) {
                throw new IllegalStateException("The maxmimum number of destinations allowed (" + entry.getMaxDestinations() + ") for the policy " + entry.getDestination() + " has already been reached.");
            }
            throw new IllegalStateException("The maxmimum number of destinations allowed (" + entry.getMaxDestinations() + ") has already been reached.");
        }
    }

    protected List<Subscription> addSubscriptionsForDestination(ConnectionContext context, Destination dest) throws Exception {
        ArrayList<Subscription> rc = new ArrayList<Subscription>();
        for (Subscription sub : this.subscriptions.values()) {
            if (!sub.matches(dest.getActiveMQDestination())) continue;
            try {
                ConnectionContext originalContext = sub.getContext() != null ? sub.getContext() : context;
                dest.addSubscription(originalContext, sub);
                rc.add(sub);
            }
            catch (SecurityException e) {
                if (sub.isWildcard()) {
                    LOG.debug("Subscription denied for " + sub + " to destination " + dest.getActiveMQDestination() + ": " + e.getMessage());
                    continue;
                }
                throw e;
            }
        }
        return rc;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        if (timeout2 == 0L) {
            for (Subscription sub : this.subscriptions.values()) {
                if (!sub.matches(destination)) continue;
                throw new JMSException("Destination still has an active subscription: " + destination);
            }
        }
        if (timeout2 > 0L) {
            // empty if block
        }
        LOG.debug("{} removing destination: {}", (Object)this.broker.getBrokerName(), (Object)destination);
        this.destinationsLock.writeLock().lock();
        try {
            Destination dest = this.destinations.remove(destination);
            if (dest != null) {
                this.updateRegionDestCounts(destination, -1);
                for (Subscription sub : this.subscriptions.values()) {
                    if (!sub.matches(destination)) continue;
                    dest.removeSubscription(context, sub, 0L);
                }
                this.destinationMap.unsynchronizedRemove(destination, dest);
                if (dest instanceof Queue) {
                    ((Queue)dest).purge();
                }
                this.dispose(context, dest);
                DestinationInterceptor destinationInterceptor = this.broker.getDestinationInterceptor();
                if (destinationInterceptor != null) {
                    destinationInterceptor.remove(dest);
                }
            } else {
                LOG.debug("Cannot remove a destination that doesn't exist: {}", (Object)destination);
            }
        }
        finally {
            this.destinationsLock.writeLock().unlock();
        }
    }

    @Override
    public Set<Destination> getDestinations(ActiveMQDestination destination) {
        this.destinationsLock.readLock().lock();
        try {
            Set set = this.destinationMap.unsynchronizedGet(destination);
            return set;
        }
        finally {
            this.destinationsLock.readLock().unlock();
        }
    }

    @Override
    public Map<ActiveMQDestination, Destination> getDestinationMap() {
        return this.destinations;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        Object addGuard;
        LOG.debug("{} adding consumer: {} for destination: {}", this.broker.getBrokerName(), info.getConsumerId(), info.getDestination());
        ActiveMQDestination destination = info.getDestination();
        if (destination != null && !destination.isPattern() && !destination.isComposite()) {
            this.lookup(context, destination, true);
        }
        Object object = this.consumerChangeMutexMap;
        synchronized (object) {
            addGuard = this.consumerChangeMutexMap.get(info.getConsumerId());
            if (addGuard == null) {
                addGuard = new Object();
                this.consumerChangeMutexMap.put(info.getConsumerId(), addGuard);
            }
        }
        object = addGuard;
        synchronized (object) {
            Subscription o = this.subscriptions.get(info.getConsumerId());
            if (o != null) {
                LOG.warn("A duplicate subscription was detected. Clients may be misbehaving. Later warnings you may see about subscription removal are a consequence of this.");
                return o;
            }
            DestinationFilter.parseFilter(info.getDestination());
            Subscription sub = this.createSubscription(context, info);
            ArrayList<Destination> addList = new ArrayList<Destination>();
            this.destinationsLock.readLock().lock();
            try {
                for (Destination dest : this.destinationMap.unsynchronizedGet(info.getDestination())) {
                    addList.add(dest);
                }
                this.subscriptions.put(info.getConsumerId(), sub);
            }
            finally {
                this.destinationsLock.readLock().unlock();
            }
            ArrayList<Destination> removeList = new ArrayList<Destination>();
            for (Destination dest : addList) {
                try {
                    dest.addSubscription(context, sub);
                    removeList.add(dest);
                }
                catch (SecurityException e) {
                    if (sub.isWildcard()) {
                        LOG.debug("Subscription denied for " + sub + " to destination " + dest.getActiveMQDestination() + ": " + e.getMessage());
                        continue;
                    }
                    for (Destination remove : removeList) {
                        try {
                            remove.removeSubscription(context, sub, info.getLastDeliveredSequenceId());
                        }
                        catch (Exception ex) {
                            LOG.error("Error unsubscribing " + sub + " from " + remove + ": " + ex.getMessage(), ex);
                        }
                    }
                    this.subscriptions.remove(info.getConsumerId());
                    removeList.clear();
                    throw e;
                }
            }
            removeList.clear();
            if (info.isBrowser()) {
                ((QueueBrowserSubscription)sub).destinationsAdded();
            }
            return sub;
        }
    }

    public Set getDurableDestinations() {
        return this.destinationFactory.getDestinations();
    }

    protected Set<ActiveMQDestination> getInactiveDestinations() {
        Set<ActiveMQDestination> inactiveDests = this.destinationFactory.getDestinations();
        this.destinationsLock.readLock().lock();
        try {
            inactiveDests.removeAll(this.destinations.keySet());
        }
        finally {
            this.destinationsLock.readLock().unlock();
        }
        return inactiveDests;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        LOG.debug("{} removing consumer: {} for destination: {}", this.broker.getBrokerName(), info.getConsumerId(), info.getDestination());
        Subscription sub = this.subscriptions.remove(info.getConsumerId());
        if (sub != null) {
            ArrayList<Destination> removeList = new ArrayList<Destination>();
            this.destinationsLock.readLock().lock();
            try {
                for (Destination dest : this.destinationMap.unsynchronizedGet(info.getDestination())) {
                    removeList.add(dest);
                }
            }
            finally {
                this.destinationsLock.readLock().unlock();
            }
            for (Destination dest : removeList) {
                dest.removeSubscription(context, sub, info.getLastDeliveredSequenceId());
            }
            this.destroySubscription(sub);
        }
        Map<ConsumerId, Object> map = this.consumerChangeMutexMap;
        synchronized (map) {
            this.consumerChangeMutexMap.remove(info.getConsumerId());
        }
    }

    protected void destroySubscription(Subscription sub) {
        sub.destroy();
    }

    @Override
    public void removeSubscription(ConnectionContext context, RemoveSubscriptionInfo info) throws Exception {
        throw new JMSException("Invalid operation.");
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        ConnectionContext context = producerExchange.getConnectionContext();
        if (producerExchange.isMutable() || producerExchange.getRegionDestination() == null) {
            Destination regionDestination = this.lookup(context, messageSend.getDestination(), false);
            producerExchange.setRegionDestination(regionDestination);
        }
        producerExchange.getRegionDestination().send(producerExchange, messageSend);
        if (producerExchange.getProducerState() != null && producerExchange.getProducerState().getInfo() != null) {
            producerExchange.getProducerState().getInfo().incrementSentCount();
        }
    }

    @Override
    public void acknowledge(ConsumerBrokerExchange consumerExchange, MessageAck ack) throws Exception {
        Subscription sub = consumerExchange.getSubscription();
        if (sub == null) {
            sub = this.subscriptions.get(ack.getConsumerId());
            if (sub == null) {
                if (!consumerExchange.getConnectionContext().isInRecoveryMode()) {
                    LOG.warn("Ack for non existent subscription, ack: {}", (Object)ack);
                    throw new IllegalArgumentException("The subscription does not exist: " + ack.getConsumerId());
                }
                LOG.debug("Ack for non existent subscription in recovery, ack: {}", (Object)ack);
                return;
            }
            consumerExchange.setSubscription(sub);
        }
        sub.acknowledge(consumerExchange.getConnectionContext(), ack);
    }

    @Override
    public Response messagePull(ConnectionContext context, MessagePull pull) throws Exception {
        Subscription sub = this.subscriptions.get(pull.getConsumerId());
        if (sub == null) {
            throw new IllegalArgumentException("The subscription does not exist: " + pull.getConsumerId());
        }
        return sub.pullMessage(context, pull);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Destination lookup(ConnectionContext context, ActiveMQDestination destination, boolean createTemporary) throws Exception {
        Destination dest = null;
        this.destinationsLock.readLock().lock();
        try {
            dest = this.destinations.get(destination);
        }
        finally {
            this.destinationsLock.readLock().unlock();
        }
        if (dest == null) {
            if (this.isAutoCreateDestinations()) {
                dest = context.getBroker().addDestination(context, destination, createTemporary);
            }
            if (dest == null) {
                throw new JMSException("The destination " + destination + " does not exist.");
            }
        }
        return dest;
    }

    @Override
    public void processDispatchNotification(MessageDispatchNotification messageDispatchNotification) throws Exception {
        Subscription sub = this.subscriptions.get(messageDispatchNotification.getConsumerId());
        if (sub == null) {
            throw new JMSException("Slave broker out of sync with master - Subscription: " + messageDispatchNotification.getConsumerId() + " on " + messageDispatchNotification.getDestination() + " does not exist for dispatch of message: " + messageDispatchNotification.getMessageId());
        }
        sub.processMessageDispatchNotification(messageDispatchNotification);
    }

    protected void processDispatchNotificationViaDestination(MessageDispatchNotification messageDispatchNotification) throws Exception {
        Destination dest = null;
        this.destinationsLock.readLock().lock();
        try {
            dest = this.destinations.get(messageDispatchNotification.getDestination());
        }
        finally {
            this.destinationsLock.readLock().unlock();
        }
        if (dest == null) {
            throw new JMSException("Slave broker out of sync with master - Destination: " + messageDispatchNotification.getDestination() + " does not exist for consumer " + messageDispatchNotification.getConsumerId() + " with message: " + messageDispatchNotification.getMessageId());
        }
        dest.processDispatchNotification(messageDispatchNotification);
    }

    @Override
    public void gc() {
        for (Subscription sub : this.subscriptions.values()) {
            sub.gc();
        }
        this.destinationsLock.readLock().lock();
        try {
            for (Destination dest : this.destinations.values()) {
                dest.gc();
            }
        }
        finally {
            this.destinationsLock.readLock().unlock();
        }
    }

    protected abstract Subscription createSubscription(ConnectionContext var1, ConsumerInfo var2) throws Exception;

    protected Destination createDestination(ConnectionContext context, ActiveMQDestination destination) throws Exception {
        return this.destinationFactory.createDestination(context, destination, this.destinationStatistics);
    }

    public boolean isAutoCreateDestinations() {
        return this.autoCreateDestinations;
    }

    public void setAutoCreateDestinations(boolean autoCreateDestinations) {
        this.autoCreateDestinations = autoCreateDestinations;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        this.destinationsLock.readLock().lock();
        try {
            for (Destination dest : this.destinationMap.unsynchronizedGet(info.getDestination())) {
                dest.addProducer(context, info);
            }
        }
        finally {
            this.destinationsLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeProducer(ConnectionContext context, ProducerInfo info) throws Exception {
        this.destinationsLock.readLock().lock();
        try {
            for (Destination dest : this.destinationMap.unsynchronizedGet(info.getDestination())) {
                dest.removeProducer(context, info);
            }
        }
        finally {
            this.destinationsLock.readLock().unlock();
        }
    }

    protected void dispose(ConnectionContext context, Destination dest) throws Exception {
        dest.dispose(context);
        dest.stop();
        this.destinationFactory.removeDestination(dest);
    }

    @Override
    public void processConsumerControl(ConsumerBrokerExchange consumerExchange, ConsumerControl control) {
        Subscription sub = this.subscriptions.get(control.getConsumerId());
        if (sub != null && sub instanceof AbstractSubscription) {
            PolicyEntry entry;
            ((AbstractSubscription)sub).setPrefetchSize(control.getPrefetch());
            if (this.broker.getDestinationPolicy() != null && (entry = this.broker.getDestinationPolicy().getEntryFor(control.getDestination())) != null) {
                entry.configurePrefetch(sub);
            }
            LOG.debug("setting prefetch: {}, on subscription: {}; resulting value: {}", control.getPrefetch(), control.getConsumerId(), sub.getConsumerInfo().getPrefetchSize());
            try {
                this.lookup(consumerExchange.getConnectionContext(), control.getDestination(), false).wakeup();
            }
            catch (Exception e) {
                LOG.warn("failed to deliver post consumerControl dispatch-wakeup, to destination: {}", (Object)control.getDestination(), (Object)e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void reapplyInterceptor() {
        this.destinationsLock.writeLock().lock();
        try {
            DestinationInterceptor destinationInterceptor = this.broker.getDestinationInterceptor();
            Map<ActiveMQDestination, Destination> map = this.getDestinationMap();
            for (ActiveMQDestination key : map.keySet()) {
                Destination destination = map.get(key);
                if (destination instanceof CompositeDestinationFilter) {
                    destination = ((CompositeDestinationFilter)destination).next;
                }
                if (destinationInterceptor != null) {
                    destination = destinationInterceptor.intercept(destination);
                }
                this.getDestinationMap().put(key, destination);
                Destination prev = this.destinations.put(key, destination);
                if (prev != null) continue;
                this.updateRegionDestCounts(key, 1);
            }
        }
        finally {
            this.destinationsLock.writeLock().unlock();
        }
    }
}

