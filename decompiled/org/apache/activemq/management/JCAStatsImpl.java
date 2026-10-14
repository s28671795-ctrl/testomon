/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import org.apache.activemq.management.JCAConnectionPoolStatsImpl;
import org.apache.activemq.management.JCAConnectionStatsImpl;
import org.apache.activemq.management.StatsImpl;

public class JCAStatsImpl
extends StatsImpl {
    private JCAConnectionStatsImpl[] connectionStats;
    private JCAConnectionPoolStatsImpl[] connectionPoolStats;

    public JCAStatsImpl(JCAConnectionStatsImpl[] connectionStats, JCAConnectionPoolStatsImpl[] connectionPoolStats) {
        this.connectionStats = connectionStats;
        this.connectionPoolStats = connectionPoolStats;
    }

    public JCAConnectionStatsImpl[] getConnections() {
        return this.connectionStats;
    }

    public JCAConnectionPoolStatsImpl[] getConnectionPools() {
        return this.connectionPoolStats;
    }
}

