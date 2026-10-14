/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.broker.jmx.MBeanInfo;

public interface ProducerViewMBean {
    @MBeanInfo(value="JMS Client id of the Connection the Producer is on.")
    public String getClientId();

    @MBeanInfo(value="ID of the Connection the Producer is on.")
    public String getConnectionId();

    @MBeanInfo(value="ID of the Session the Producer is on.")
    public long getSessionId();

    @MBeanInfo(value="ID of the Producer.")
    public String getProducerId();

    @MBeanInfo(value="The name of the destionation the Producer is on.")
    public String getDestinationName();

    @MBeanInfo(value="Producer is on a Queue")
    public boolean isDestinationQueue();

    @MBeanInfo(value="Producer is on a Topic")
    public boolean isDestinationTopic();

    @MBeanInfo(value="Producer is on a temporary Queue/Topic")
    public boolean isDestinationTemporary();

    @MBeanInfo(value="Configured Window Size for the Producer")
    public int getProducerWindowSize();

    @Deprecated
    @MBeanInfo(value="Is the producer configured for Async Dispatch")
    public boolean isDispatchAsync();

    @MBeanInfo(value="User Name used to authorize creation of this Producer")
    public String getUserName();

    @MBeanInfo(value="is the producer blocked for Flow Control")
    public boolean isProducerBlocked();

    @MBeanInfo(value="total time (ms) Producer Blocked For Flow Control")
    public long getTotalTimeBlocked();

    @MBeanInfo(value="percentage of sends Producer Blocked for Flow Control")
    public int getPercentageBlocked();

    @MBeanInfo(value="reset flow control state")
    public void resetFlowControlStats();

    @MBeanInfo(value="Resets statistics.")
    public void resetStatistics();

    @MBeanInfo(value="Messages dispatched by Producer")
    public long getSentCount();
}

