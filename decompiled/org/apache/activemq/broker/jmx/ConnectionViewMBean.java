/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import javax.management.ObjectName;
import org.apache.activemq.Service;
import org.apache.activemq.broker.jmx.MBeanInfo;

public interface ConnectionViewMBean
extends Service {
    @MBeanInfo(value="Connection is slow.")
    public boolean isSlow();

    @MBeanInfo(value="Connection is blocked.")
    public boolean isBlocked();

    @MBeanInfo(value="Connection is connected to the broker.")
    public boolean isConnected();

    @MBeanInfo(value="Connection is active (both connected and receiving messages).")
    public boolean isActive();

    @MBeanInfo(value="Resets the statistics")
    public void resetStatistics();

    @MBeanInfo(value="source address for this connection")
    public String getRemoteAddress();

    @MBeanInfo(value="client id for this connection")
    public String getClientId();

    @MBeanInfo(value="The number of messages pending dispatch")
    public int getDispatchQueueSize();

    @MBeanInfo(value="User Name used to authorize creation of this connection")
    public String getUserName();

    @MBeanInfo(value="The ObjectNames of all Consumers created by this Connection")
    public ObjectName[] getConsumers();

    @MBeanInfo(value="The ObjectNames of all Producers created by this Connection")
    public ObjectName[] getProducers();

    @MBeanInfo(value="The number of active transactions established on this Connection.")
    public int getActiveTransactionCount();

    @MBeanInfo(value="The age in ms of the oldest active transaction established on this Connection.")
    public Long getOldestActiveTransactionDuration();
}

