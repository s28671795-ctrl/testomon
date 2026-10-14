/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.Service;
import org.apache.activemq.broker.jmx.MBeanInfo;

public interface ConnectorViewMBean
extends Service {
    @MBeanInfo(value="Connection count")
    public int connectionCount();

    @MBeanInfo(value="Resets the statistics")
    public void resetStatistics();

    @MBeanInfo(value="Enables statistics gathering")
    public void enableStatistics();

    @MBeanInfo(value="Disables statistics gathering")
    public void disableStatistics();

    @MBeanInfo(value="Statistics gathering enabled")
    public boolean isStatisticsEnabled();

    @MBeanInfo(value="Link Stealing enabled")
    public boolean isAllowLinkStealingEnabled();

    @MBeanInfo(value="Update client URL's when brokers leave/join a custer enabled")
    public boolean isUpdateClusterClients();

    @MBeanInfo(value="Rebalance clients across the broker cluster enabled")
    public boolean isRebalanceClusterClients();

    @MBeanInfo(value="Update clients when a broker is removed from a network enabled.")
    public boolean isUpdateClusterClientsOnRemove();

    @MBeanInfo(value="Comma separated list of regex patterns to match broker names for cluster client updates.")
    public String getUpdateClusterFilter();
}

