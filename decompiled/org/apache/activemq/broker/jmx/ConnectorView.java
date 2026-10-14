/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.broker.Connector;
import org.apache.activemq.broker.jmx.ConnectorViewMBean;
import org.apache.activemq.command.BrokerInfo;

public class ConnectorView
implements ConnectorViewMBean {
    private final Connector connector;

    public ConnectorView(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void start() throws Exception {
        this.connector.start();
    }

    public String getBrokerName() {
        return this.getBrokerInfo().getBrokerName();
    }

    @Override
    public void stop() throws Exception {
        this.connector.stop();
    }

    public String getBrokerURL() {
        return this.getBrokerInfo().getBrokerURL();
    }

    public BrokerInfo getBrokerInfo() {
        return this.connector.getBrokerInfo();
    }

    @Override
    public void resetStatistics() {
        this.connector.getStatistics().reset();
    }

    @Override
    public void enableStatistics() {
        this.connector.getStatistics().setEnabled(true);
    }

    @Override
    public void disableStatistics() {
        this.connector.getStatistics().setEnabled(false);
    }

    @Override
    public boolean isStatisticsEnabled() {
        return this.connector.getStatistics().isEnabled();
    }

    @Override
    public int connectionCount() {
        return this.connector.connectionCount();
    }

    @Override
    public boolean isUpdateClusterClients() {
        return this.connector.isUpdateClusterClients();
    }

    @Override
    public boolean isRebalanceClusterClients() {
        return this.connector.isRebalanceClusterClients();
    }

    @Override
    public boolean isUpdateClusterClientsOnRemove() {
        return this.connector.isUpdateClusterClientsOnRemove();
    }

    @Override
    public String getUpdateClusterFilter() {
        return this.connector.getUpdateClusterFilter();
    }

    @Override
    public boolean isAllowLinkStealingEnabled() {
        return this.connector.isAllowLinkStealing();
    }
}

