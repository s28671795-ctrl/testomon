/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import org.apache.activemq.ActiveMQConnectionMetaData;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.DestinationsViewFilter;
import org.apache.activemq.broker.jmx.Log4JConfigView;
import org.apache.activemq.broker.jmx.ManagedRegionBroker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.network.NetworkConnector;
import org.apache.activemq.util.BrokerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrokerView
implements BrokerViewMBean {
    private static final Logger LOG = LoggerFactory.getLogger(BrokerView.class);
    ManagedRegionBroker broker;
    private final BrokerService brokerService;
    private final AtomicInteger sessionIdCounter = new AtomicInteger(0);
    private ObjectName jmsJobScheduler;

    public BrokerView(BrokerService brokerService, ManagedRegionBroker managedBroker) throws Exception {
        this.brokerService = brokerService;
        this.broker = managedBroker;
    }

    public ManagedRegionBroker getBroker() {
        return this.broker;
    }

    public void setBroker(ManagedRegionBroker broker) {
        this.broker = broker;
    }

    @Override
    public String getBrokerId() {
        return this.safeGetBroker().getBrokerId().toString();
    }

    @Override
    public String getBrokerName() {
        return this.safeGetBroker().getBrokerName();
    }

    @Override
    public String getBrokerVersion() {
        return ActiveMQConnectionMetaData.PROVIDER_VERSION;
    }

    @Override
    public String getUptime() {
        return this.brokerService.getUptime();
    }

    @Override
    public long getUptimeMillis() {
        return this.brokerService.getUptimeMillis();
    }

    @Override
    public int getCurrentConnectionsCount() {
        return this.brokerService.getCurrentConnections();
    }

    @Override
    public long getTotalConnectionsCount() {
        return this.brokerService.getTotalConnections();
    }

    @Override
    public void gc() throws Exception {
        this.brokerService.getBroker().gc();
        try {
            this.brokerService.getPersistenceAdapter().checkpoint(true);
        }
        catch (IOException e) {
            LOG.error("Failed to checkpoint persistence adapter on gc request", e);
        }
    }

    @Override
    public void start() throws Exception {
        this.brokerService.start();
    }

    @Override
    public void stop() throws Exception {
        this.brokerService.stop();
    }

    @Override
    public void restart() throws Exception {
        if (!this.brokerService.isRestartAllowed()) {
            throw new Exception("Restart is not allowed");
        }
        this.brokerService.requestRestart();
        this.brokerService.stop();
    }

    @Override
    public void stopGracefully(String connectorName, String queueName, long timeout2, long pollInterval) throws Exception {
        this.brokerService.stopGracefully(connectorName, queueName, timeout2, pollInterval);
    }

    @Override
    public long getTotalEnqueueCount() {
        return this.safeGetBroker().getDestinationStatistics().getEnqueues().getCount();
    }

    @Override
    public long getTotalDequeueCount() {
        return this.safeGetBroker().getDestinationStatistics().getDequeues().getCount();
    }

    @Override
    public long getTotalConsumerCount() {
        return this.safeGetBroker().getDestinationStatistics().getConsumers().getCount();
    }

    @Override
    public long getTotalProducerCount() {
        return this.safeGetBroker().getDestinationStatistics().getProducers().getCount();
    }

    @Override
    public long getTotalMessageCount() {
        return this.safeGetBroker().getDestinationStatistics().getMessages().getCount();
    }

    @Override
    public long getAverageMessageSize() {
        return (long)this.safeGetBroker().getDestinationStatistics().getMessageSize().getAverageSize();
    }

    @Override
    public long getMaxMessageSize() {
        return this.safeGetBroker().getDestinationStatistics().getMessageSize().getMaxSize();
    }

    @Override
    public long getMinMessageSize() {
        return this.safeGetBroker().getDestinationStatistics().getMessageSize().getMinSize();
    }

    public long getTotalMessagesCached() {
        return this.safeGetBroker().getDestinationStatistics().getMessagesCached().getCount();
    }

    @Override
    public int getMemoryPercentUsage() {
        return this.brokerService.getSystemUsage().getMemoryUsage().getPercentUsage();
    }

    @Override
    public long getMemoryLimit() {
        return this.brokerService.getSystemUsage().getMemoryUsage().getLimit();
    }

    @Override
    public void setMemoryLimit(long limit) {
        this.brokerService.getSystemUsage().getMemoryUsage().setLimit(limit);
    }

    @Override
    public long getStoreLimit() {
        return this.brokerService.getSystemUsage().getStoreUsage().getLimit();
    }

    @Override
    public int getStorePercentUsage() {
        return this.brokerService.getSystemUsage().getStoreUsage().getPercentUsage();
    }

    @Override
    public long getTempLimit() {
        return this.brokerService.getSystemUsage().getTempUsage().getLimit();
    }

    @Override
    public int getTempPercentUsage() {
        return this.brokerService.getSystemUsage().getTempUsage().getPercentUsage();
    }

    @Override
    public long getJobSchedulerStoreLimit() {
        return this.brokerService.getSystemUsage().getJobSchedulerUsage().getLimit();
    }

    @Override
    public int getJobSchedulerStorePercentUsage() {
        return this.brokerService.getSystemUsage().getJobSchedulerUsage().getPercentUsage();
    }

    @Override
    public void setStoreLimit(long limit) {
        this.brokerService.getSystemUsage().getStoreUsage().setLimit(limit);
    }

    @Override
    public void setTempLimit(long limit) {
        this.brokerService.getSystemUsage().getTempUsage().setLimit(limit);
    }

    @Override
    public void setJobSchedulerStoreLimit(long limit) {
        this.brokerService.getSystemUsage().getJobSchedulerUsage().setLimit(limit);
    }

    @Override
    public void resetStatistics() {
        this.safeGetBroker().getDestinationStatistics().reset();
    }

    @Override
    public void enableStatistics() {
        this.safeGetBroker().getDestinationStatistics().setEnabled(true);
    }

    @Override
    public void disableStatistics() {
        this.safeGetBroker().getDestinationStatistics().setEnabled(false);
    }

    @Override
    public boolean isStatisticsEnabled() {
        return this.safeGetBroker().getDestinationStatistics().isEnabled();
    }

    @Override
    public boolean isPersistent() {
        return this.brokerService.isPersistent();
    }

    @Override
    public void terminateJVM(int exitCode) {
        System.exit(exitCode);
    }

    @Override
    public ObjectName[] getTopics() {
        return this.safeGetBroker().getTopicsNonSuppressed();
    }

    @Override
    public ObjectName[] getQueues() {
        return this.safeGetBroker().getQueuesNonSuppressed();
    }

    @Override
    public String queryQueues(String filter2, int page, int pageSize) throws IOException {
        return DestinationsViewFilter.create(filter2).setDestinations(this.safeGetBroker().getQueueViews()).filter(page, pageSize);
    }

    @Override
    public String queryTopics(String filter2, int page, int pageSize) throws IOException {
        return DestinationsViewFilter.create(filter2).setDestinations(this.safeGetBroker().getTopicViews()).filter(page, pageSize);
    }

    @Override
    public CompositeData[] browseQueue(String queueName) throws OpenDataException, MalformedObjectNameException {
        return this.safeGetBroker().getQueueView(queueName).browse();
    }

    @Override
    public ObjectName[] getTemporaryTopics() {
        return this.safeGetBroker().getTemporaryTopicsNonSuppressed();
    }

    @Override
    public ObjectName[] getTemporaryQueues() {
        return this.safeGetBroker().getTemporaryQueuesNonSuppressed();
    }

    @Override
    public ObjectName[] getTopicSubscribers() {
        return this.safeGetBroker().getTopicSubscribersNonSuppressed();
    }

    @Override
    public ObjectName[] getDurableTopicSubscribers() {
        return this.safeGetBroker().getDurableTopicSubscribersNonSuppressed();
    }

    @Override
    public ObjectName[] getQueueSubscribers() {
        return this.safeGetBroker().getQueueSubscribersNonSuppressed();
    }

    @Override
    public ObjectName[] getTemporaryTopicSubscribers() {
        return this.safeGetBroker().getTemporaryTopicSubscribersNonSuppressed();
    }

    @Override
    public ObjectName[] getTemporaryQueueSubscribers() {
        return this.safeGetBroker().getTemporaryQueueSubscribersNonSuppressed();
    }

    @Override
    public ObjectName[] getInactiveDurableTopicSubscribers() {
        return this.safeGetBroker().getInactiveDurableTopicSubscribersNonSuppressed();
    }

    @Override
    public ObjectName[] getTopicProducers() {
        return this.safeGetBroker().getTopicProducersNonSuppressed();
    }

    @Override
    public ObjectName[] getQueueProducers() {
        return this.safeGetBroker().getQueueProducersNonSuppressed();
    }

    @Override
    public ObjectName[] getTemporaryTopicProducers() {
        return this.safeGetBroker().getTemporaryTopicProducersNonSuppressed();
    }

    @Override
    public ObjectName[] getTemporaryQueueProducers() {
        return this.safeGetBroker().getTemporaryQueueProducersNonSuppressed();
    }

    @Override
    public ObjectName[] getDynamicDestinationProducers() {
        return this.safeGetBroker().getDynamicDestinationProducersNonSuppressed();
    }

    @Override
    public String addConnector(String discoveryAddress) throws Exception {
        TransportConnector connector = this.brokerService.addConnector(discoveryAddress);
        if (connector == null) {
            throw new NoSuchElementException("Not connector matched the given name: " + discoveryAddress);
        }
        this.brokerService.startTransportConnector(connector);
        return connector.getName();
    }

    @Override
    public String addNetworkConnector(String discoveryAddress) throws Exception {
        NetworkConnector connector = this.brokerService.addNetworkConnector(discoveryAddress);
        if (connector == null) {
            throw new NoSuchElementException("Not connector matched the given name: " + discoveryAddress);
        }
        this.brokerService.registerNetworkConnectorMBean(connector);
        connector.start();
        return connector.getName();
    }

    @Override
    public boolean removeConnector(String connectorName) throws Exception {
        TransportConnector connector = this.brokerService.getConnectorByName(connectorName);
        if (connector == null) {
            throw new NoSuchElementException("Not connector matched the given name: " + connectorName);
        }
        connector.stop();
        return this.brokerService.removeConnector(connector);
    }

    @Override
    public boolean removeNetworkConnector(String connectorName) throws Exception {
        NetworkConnector connector = this.brokerService.getNetworkConnectorByName(connectorName);
        if (connector == null) {
            throw new NoSuchElementException("Not connector matched the given name: " + connectorName);
        }
        connector.stop();
        return this.brokerService.removeNetworkConnector(connector);
    }

    @Override
    public void addTopic(String name) throws Exception {
        this.safeGetBroker().getContextBroker().addDestination(BrokerSupport.getConnectionContext(this.safeGetBroker().getContextBroker()), new ActiveMQTopic(name), true);
    }

    @Override
    public void addQueue(String name) throws Exception {
        this.safeGetBroker().getContextBroker().addDestination(BrokerSupport.getConnectionContext(this.safeGetBroker().getContextBroker()), new ActiveMQQueue(name), true);
    }

    @Override
    public void removeTopic(String name) throws Exception {
        this.safeGetBroker().getContextBroker().removeDestination(BrokerSupport.getConnectionContext(this.safeGetBroker().getContextBroker()), new ActiveMQTopic(name), 1000L);
    }

    @Override
    public void removeQueue(String name) throws Exception {
        this.safeGetBroker().getContextBroker().removeDestination(BrokerSupport.getConnectionContext(this.safeGetBroker().getContextBroker()), new ActiveMQQueue(name), 1000L);
    }

    @Override
    public ObjectName createDurableSubscriber(String clientId, String subscriberName, String topicName, String selector) throws Exception {
        ConnectionContext context = this.getConnectionContext();
        context.setBroker(this.safeGetBroker());
        context.setClientId(clientId);
        ConsumerInfo info = new ConsumerInfo();
        ConsumerId consumerId = new ConsumerId();
        consumerId.setConnectionId(clientId);
        consumerId.setSessionId(this.sessionIdCounter.incrementAndGet());
        consumerId.setValue(0L);
        info.setConsumerId(consumerId);
        info.setDestination(new ActiveMQTopic(topicName));
        info.setSubscriptionName(subscriberName);
        info.setSelector(selector);
        Subscription subscription = this.safeGetBroker().addConsumer(context, info);
        this.safeGetBroker().removeConsumer(context, info);
        if (subscription != null) {
            return subscription.getObjectName();
        }
        return null;
    }

    @Override
    public void destroyDurableSubscriber(String clientId, String subscriberName) throws Exception {
        RemoveSubscriptionInfo info = new RemoveSubscriptionInfo();
        info.setClientId(clientId);
        info.setSubscriptionName(subscriberName);
        ConnectionContext context = this.getConnectionContext();
        context.setBroker(this.safeGetBroker());
        context.setClientId(clientId);
        this.brokerService.getBroker().removeSubscription(context, info);
    }

    @Override
    public void reloadLog4jProperties() throws Throwable {
        Log4JConfigView.doReloadLog4jProperties();
    }

    @Override
    public Map<String, String> getTransportConnectors() {
        HashMap<String, String> answer = new HashMap<String, String>();
        try {
            for (TransportConnector connector : this.brokerService.getTransportConnectors()) {
                answer.put(connector.getName(), connector.getConnectUri().toString());
            }
        }
        catch (Exception e) {
            LOG.debug("Failed to read URI to build transport connectors map", e);
        }
        return answer;
    }

    @Override
    public String getTransportConnectorByType(String type) {
        return this.brokerService.getTransportConnectorURIsAsMap().get(type);
    }

    @Override
    public String getVMURL() {
        URI answer = this.brokerService.getVmConnectorURI();
        return answer != null ? answer.toString() : "";
    }

    @Override
    public String getDataDirectory() {
        File file = this.brokerService.getDataDirectoryFile();
        try {
            return file != null ? file.getCanonicalPath() : "";
        }
        catch (IOException e) {
            return "";
        }
    }

    @Override
    public ObjectName getJMSJobScheduler() {
        return this.jmsJobScheduler;
    }

    public void setJMSJobScheduler(ObjectName name) {
        this.jmsJobScheduler = name;
    }

    @Override
    public boolean isSlave() {
        return this.brokerService.isSlave();
    }

    private ManagedRegionBroker safeGetBroker() {
        if (this.broker == null) {
            throw new IllegalStateException("Broker is not yet started.");
        }
        return this.broker;
    }

    private ConnectionContext getConnectionContext() {
        ConnectionContext context;
        if (this.broker == null) {
            context = new ConnectionContext();
        } else {
            ConnectionContext sharedContext = BrokerSupport.getConnectionContext(this.broker.getContextBroker());
            context = sharedContext.copy();
        }
        return context;
    }
}

