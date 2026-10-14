/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.jms.InvalidSelectorException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.TabularData;
import org.apache.activemq.broker.jmx.MBeanInfo;

public interface DestinationViewMBean {
    @MBeanInfo(value="Name of this destination.")
    public String getName();

    @MBeanInfo(value="Resets statistics.")
    public void resetStatistics();

    @MBeanInfo(value="Number of messages that have been sent to the destination.")
    public long getEnqueueCount();

    @MBeanInfo(value="Number of messages that has been delivered to consumers, including those not acknowledged")
    public long getDispatchCount();

    @MBeanInfo(value="Number of messages that has been acknowledged (and removed) from the destination.")
    public long getDequeueCount();

    @MBeanInfo(value="Number of messages that have been forwarded (to a networked broker) from the destination.")
    public long getForwardCount();

    @MBeanInfo(value="Number of messages that have been dispatched to, but not acknowledged by, consumers.")
    public long getInFlightCount();

    @MBeanInfo(value="Number of messages that have been expired.")
    public long getExpiredCount();

    @MBeanInfo(value="Number of consumers subscribed to this destination.")
    public long getConsumerCount();

    @MBeanInfo(value="Number of producers attached to this destination")
    public long getProducerCount();

    @MBeanInfo(value="Number of messages on this destination, including any that have been dispatched but not acknowledged")
    public long getQueueSize();

    @MBeanInfo(value="The memory size of all messages in this destination's store.")
    public long getStoreMessageSize();

    @MBeanInfo(value="An array of all messages in the destination. Not HTML friendly.")
    public CompositeData[] browse() throws OpenDataException;

    @MBeanInfo(value="A list of all messages in the destination. Not HTML friendly.")
    public TabularData browseAsTable() throws OpenDataException;

    @MBeanInfo(value="An array of all messages in the destination based on an SQL-92 selection on the message headers or XPATH on the body. Not HTML friendly.")
    public CompositeData[] browse(@MBeanInfo(value="selector") String var1) throws OpenDataException, InvalidSelectorException;

    @MBeanInfo(value="A list of all messages in the destination based on an SQL-92 selection on the message headers or XPATH on the body. Not HTML friendly.")
    public TabularData browseAsTable(@MBeanInfo(value="selector") String var1) throws OpenDataException, InvalidSelectorException;

    @MBeanInfo(value="Sends a TextMessage to the destination.")
    public String sendTextMessage(@MBeanInfo(value="body") String var1) throws Exception;

    @MBeanInfo(value="Sends a TextMessage to the destination.")
    public String sendTextMessageWithProperties(String var1) throws Exception;

    @MBeanInfo(value="Sends a TextMessage to the destination.")
    public String sendTextMessage(@MBeanInfo(value="headers") Map<?, ?> var1, @MBeanInfo(value="body") String var2) throws Exception;

    @MBeanInfo(value="Sends a TextMessage to a password-protected destination.")
    public String sendTextMessage(@MBeanInfo(value="body") String var1, @MBeanInfo(value="user") String var2, @MBeanInfo(value="password") String var3) throws Exception;

    @MBeanInfo(value="Sends a TextMessage to a password-protected destination.")
    public String sendTextMessage(@MBeanInfo(value="headers") Map<String, String> var1, @MBeanInfo(value="body") String var2, @MBeanInfo(value="user") String var3, @MBeanInfo(value="password") String var4) throws Exception;

    @MBeanInfo(value="The percentage of the memory limit used")
    public int getMemoryPercentUsage();

    @MBeanInfo(value="Memory used by undelivered messages in bytes")
    public long getMemoryUsageByteCount();

    @MBeanInfo(value="Memory limit, in bytes, used by undelivered messages before paging to temporary storage.")
    public long getMemoryLimit();

    public void setMemoryLimit(long var1);

    @MBeanInfo(value="The percentage of the temp usage limit used")
    public int getTempUsagePercentUsage();

    @MBeanInfo(value="Temp usage limit, in bytes, assigned to this destination.")
    public long getTempUsageLimit();

    public void setTempUsageLimit(long var1);

    @MBeanInfo(value="Portion of memory from the broker memory limit for this destination")
    public float getMemoryUsagePortion();

    public void setMemoryUsagePortion(@MBeanInfo(value="bytes") float var1);

    @MBeanInfo(value="A list of all messages in the destination. Not HTML friendly.")
    public List<?> browseMessages() throws InvalidSelectorException;

    @MBeanInfo(value="A list of all messages in the destination based on an SQL-92 selection on the message headers or XPATH on the body. Not HTML friendly.")
    public List<?> browseMessages(String var1) throws InvalidSelectorException;

    @MBeanInfo(value="The longest time a message was held on this destination")
    public long getMaxEnqueueTime();

    @MBeanInfo(value="The shortest time a message was held on this destination")
    public long getMinEnqueueTime();

    @MBeanInfo(value="Average time a message was held on this destination.")
    public double getAverageEnqueueTime();

    @MBeanInfo(value="Average message size on this destination")
    public long getAverageMessageSize();

    @MBeanInfo(value="Max message size on this destination")
    public long getMaxMessageSize();

    @MBeanInfo(value="Min message size on this destination")
    public long getMinMessageSize();

    @MBeanInfo(value="Flow control is enabled for producers")
    public boolean isProducerFlowControl();

    public void setProducerFlowControl(@MBeanInfo(value="producerFlowControl") boolean var1);

    @MBeanInfo(value="Always treat consumers as retroactive")
    public boolean isAlwaysRetroactive();

    public void setAlwaysRetroactive(@MBeanInfo(value="alwaysRetroactive") boolean var1);

    public void setBlockedProducerWarningInterval(@MBeanInfo(value="blockedProducerWarningInterval") long var1);

    @MBeanInfo(value="Blocked Producer Warning Interval")
    public long getBlockedProducerWarningInterval();

    @MBeanInfo(value="Maximum number of producers to audit")
    public int getMaxProducersToAudit();

    public void setMaxProducersToAudit(@MBeanInfo(value="maxProducersToAudit") int var1);

    @MBeanInfo(value="Max audit depth")
    public int getMaxAuditDepth();

    public void setMaxAuditDepth(@MBeanInfo(value="maxAuditDepth") int var1);

    @MBeanInfo(value="Maximum number of messages to be paged in")
    public int getMaxPageSize();

    public void setMaxPageSize(@MBeanInfo(value="pageSize") int var1);

    @MBeanInfo(value="Caching is allowed")
    public boolean isUseCache();

    @MBeanInfo(value="Prioritized messages is enabled")
    public boolean isPrioritizedMessages();

    public void setUseCache(@MBeanInfo(value="cache") boolean var1);

    @MBeanInfo(value="Subscription MBeans matching this destination")
    public ObjectName[] getSubscriptions() throws IOException, MalformedObjectNameException;

    @MBeanInfo(value="Optional slowConsumer handler MBean for this destination")
    public ObjectName getSlowConsumerStrategy() throws IOException, MalformedObjectNameException;

    @MBeanInfo(value="Destination options as name value pairs in a URL queryString")
    public String getOptions();

    @MBeanInfo(value="Dead Letter Queue")
    public boolean isDLQ();

    public void setDLQ(boolean var1);

    @MBeanInfo(value="Number of messages blocked for flow control")
    public long getBlockedSends();

    @MBeanInfo(value="Average time (ms) messages have been blocked by flow control")
    public double getAverageBlockedTime();

    @MBeanInfo(value="Total time (ms) messages have been blocked by flow control")
    public long getTotalBlockedTime();
}

