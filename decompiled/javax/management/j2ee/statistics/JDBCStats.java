/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.JDBCConnectionPoolStats;
import javax.management.j2ee.statistics.JDBCConnectionStats;
import javax.management.j2ee.statistics.Stats;

public interface JDBCStats
extends Stats {
    public JDBCConnectionStats[] getConnections();

    public JDBCConnectionPoolStats[] getConnectionPools();
}

