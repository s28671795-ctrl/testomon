/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import org.apache.activemq.Service;
import org.apache.activemq.broker.region.ConnectorStatistics;
import org.apache.activemq.command.BrokerInfo;

public interface Connector
extends Service {
    public BrokerInfo getBrokerInfo();

    public ConnectorStatistics getStatistics();

    public boolean isUpdateClusterClients();

    public boolean isRebalanceClusterClients();

    public void updateClientClusterInfo();

    public boolean isUpdateClusterClientsOnRemove();

    public int connectionCount();

    public boolean isAllowLinkStealing();

    public String getUpdateClusterFilter();
}

