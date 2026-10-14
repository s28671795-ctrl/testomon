/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.JCAConnectionPoolStats;
import javax.management.j2ee.statistics.JCAConnectionStats;
import javax.management.j2ee.statistics.Stats;

public interface JCAStats
extends Stats {
    public JCAConnectionStats[] getConnections();

    public JCAConnectionPoolStats[] getConnectionPools();
}

