/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.io.IOException;
import java.util.Map;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import org.apache.activemq.Service;
import org.apache.activemq.broker.jmx.MBeanInfo;

public interface BrokerViewMBean
extends Service {
    @MBeanInfo(value="The unique id of the broker.")
    public String getBrokerId();

    @MBeanInfo(value="The name of the broker.")
    public String getBrokerName();

    @MBeanInfo(value="The version of the broker.")
    public String getBrokerVersion();

    @MBeanInfo(value="Uptime of the broker.")
    public String getUptime();

    @MBeanInfo(value="Uptime of the broker in milliseconds.")
    public long getUptimeMillis();

    public int getCurrentConnectionsCount();

    public long getTotalConnectionsCount();

    @MBeanInfo(value="Runs the Garbage Collector.")
    public void gc() throws Exception;

    @MBeanInfo(value="Reset all broker statistics.")
    public void resetStatistics();

    @MBeanInfo(value="Enable broker statistics.")
    public void enableStatistics();

    @MBeanInfo(value="Disable broker statistics.")
    public void disableStatistics();

    @MBeanInfo(value="Broker statistics enabled.")
    public boolean isStatisticsEnabled();

    @MBeanInfo(value="Number of messages that have been sent to the broker.")
    public long getTotalEnqueueCount();

    @MBeanInfo(value="Number of messages that have been acknowledged on the broker.")
    public long getTotalDequeueCount();

    @MBeanInfo(value="Number of message consumers subscribed to destinations on the broker.")
    public long getTotalConsumerCount();

    @MBeanInfo(value="Number of message producers active on destinations on the broker.")
    public long getTotalProducerCount();

    @MBeanInfo(value="Number of unacknowledged messages on the broker.")
    public long getTotalMessageCount();

    @MBeanInfo(value="Average message size on this broker")
    public long getAverageMessageSize();

    @MBeanInfo(value="Max message size on this broker")
    public long getMaxMessageSize();

    @MBeanInfo(value="Min message size on this broker")
    public long getMinMessageSize();

    @MBeanInfo(value="Percent of memory limit used.")
    public int getMemoryPercentUsage();

    @MBeanInfo(value="Memory limit, in bytes, used for holding undelivered messages before paging to temporary storage.")
    public long getMemoryLimit();

    public void setMemoryLimit(@MBeanInfo(value="bytes") long var1);

    @MBeanInfo(value="Percent of store limit used.")
    public int getStorePercentUsage();

    @MBeanInfo(value="Disk limit, in bytes, used for persistent messages before producers are blocked.")
    public long getStoreLimit();

    public void setStoreLimit(@MBeanInfo(value="bytes") long var1);

    @MBeanInfo(value="Percent of temp limit used.")
    public int getTempPercentUsage();

    @MBeanInfo(value="Disk limit, in bytes, used for non-persistent messages and temporary data before producers are blocked.")
    public long getTempLimit();

    public void setTempLimit(@MBeanInfo(value="bytes") long var1);

    @MBeanInfo(value="Percent of job store limit used.")
    public int getJobSchedulerStorePercentUsage();

    @MBeanInfo(value="Disk limit, in bytes, used for scheduled messages before producers are blocked.")
    public long getJobSchedulerStoreLimit();

    public void setJobSchedulerStoreLimit(@MBeanInfo(value="bytes") long var1);

    @MBeanInfo(value="Messages are synchronized to disk.")
    public boolean isPersistent();

    @MBeanInfo(value="Slave broker.")
    public boolean isSlave();

    @MBeanInfo(value="Shuts down the JVM.")
    public void terminateJVM(@MBeanInfo(value="exitCode") int var1);

    @Override
    @MBeanInfo(value="Stop the broker and all its components.")
    public void stop() throws Exception;

    @MBeanInfo(value="Restart the broker and all its components.")
    public void restart() throws Exception;

    @MBeanInfo(value="Poll for queues matching queueName are empty before stopping")
    public void stopGracefully(String var1, String var2, long var3, long var5) throws Exception;

    @MBeanInfo(value="Topics (broadcasted 'queues'); generally system information.")
    public ObjectName[] getTopics();

    @MBeanInfo(value="Standard Queues containing AIE messages.")
    public ObjectName[] getQueues();

    @MBeanInfo(value="Query queues")
    public String queryQueues(String var1, int var2, int var3) throws IOException;

    @MBeanInfo(value="Query topics")
    public String queryTopics(String var1, int var2, int var3) throws IOException;

    public CompositeData[] browseQueue(String var1) throws OpenDataException, MalformedObjectNameException;

    @MBeanInfo(value="Temporary Topics; generally unused.")
    public ObjectName[] getTemporaryTopics();

    @MBeanInfo(value="Temporary Queues; generally temporary message response holders.")
    public ObjectName[] getTemporaryQueues();

    @MBeanInfo(value="Topic Subscribers")
    public ObjectName[] getTopicSubscribers();

    @MBeanInfo(value="Durable (persistent) topic subscribers")
    public ObjectName[] getDurableTopicSubscribers();

    @MBeanInfo(value="Inactive (disconnected persistent) topic subscribers")
    public ObjectName[] getInactiveDurableTopicSubscribers();

    @MBeanInfo(value="Queue Subscribers.")
    public ObjectName[] getQueueSubscribers();

    @MBeanInfo(value="Temporary Topic Subscribers.")
    public ObjectName[] getTemporaryTopicSubscribers();

    @MBeanInfo(value="Temporary Queue Subscribers.")
    public ObjectName[] getTemporaryQueueSubscribers();

    @MBeanInfo(value="Topic Producers.")
    public ObjectName[] getTopicProducers();

    @MBeanInfo(value="Queue Producers.")
    public ObjectName[] getQueueProducers();

    @MBeanInfo(value="Temporary Topic Producers.")
    public ObjectName[] getTemporaryTopicProducers();

    @MBeanInfo(value="Temporary Queue Producers.")
    public ObjectName[] getTemporaryQueueProducers();

    @MBeanInfo(value="Dynamic Destination Producers.")
    public ObjectName[] getDynamicDestinationProducers();

    @MBeanInfo(value="Adds a Connector to the broker.")
    public String addConnector(@MBeanInfo(value="discoveryAddress") String var1) throws Exception;

    @MBeanInfo(value="Adds a Network Connector to the broker.")
    public String addNetworkConnector(@MBeanInfo(value="discoveryAddress") String var1) throws Exception;

    @MBeanInfo(value="Removes a Connector from the broker.")
    public boolean removeConnector(@MBeanInfo(value="connectorName") String var1) throws Exception;

    @MBeanInfo(value="Removes a Network Connector from the broker.")
    public boolean removeNetworkConnector(@MBeanInfo(value="connectorName") String var1) throws Exception;

    @MBeanInfo(value="Adds a Topic destination to the broker.")
    public void addTopic(@MBeanInfo(value="name") String var1) throws Exception;

    @MBeanInfo(value="Adds a Queue destination to the broker.")
    public void addQueue(@MBeanInfo(value="name") String var1) throws Exception;

    @MBeanInfo(value="Removes a Topic destination from the broker.")
    public void removeTopic(@MBeanInfo(value="name") String var1) throws Exception;

    @MBeanInfo(value="Removes a Queue destination from the broker.")
    public void removeQueue(@MBeanInfo(value="name") String var1) throws Exception;

    @MBeanInfo(value="Creates a new durable topic subscriber.")
    public ObjectName createDurableSubscriber(@MBeanInfo(value="clientId") String var1, @MBeanInfo(value="subscriberName") String var2, @MBeanInfo(value="topicName") String var3, @MBeanInfo(value="selector") String var4) throws Exception;

    @MBeanInfo(value="Destroys a durable subscriber.")
    public void destroyDurableSubscriber(@MBeanInfo(value="clientId") String var1, @MBeanInfo(value="subscriberName") String var2) throws Exception;

    @MBeanInfo(value="Reloads log4j.properties from the classpath.")
    public void reloadLog4jProperties() throws Throwable;

    @MBeanInfo(value="The url of the VM connector")
    public String getVMURL();

    @MBeanInfo(value="The map of all defined transport connectors, with transport name as a key")
    public Map<String, String> getTransportConnectors();

    @MBeanInfo(value="The url of transport connector by it's type; e.g. tcp, stomp, ssl, etc.")
    public String getTransportConnectorByType(String var1);

    @MBeanInfo(value="The location of the data directory")
    public String getDataDirectory();

    @MBeanInfo(value="JMSJobScheduler")
    public ObjectName getJMSJobScheduler();
}

