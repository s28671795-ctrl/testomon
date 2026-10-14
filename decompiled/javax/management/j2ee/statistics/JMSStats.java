/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.JMSConnectionStats;
import javax.management.j2ee.statistics.Stats;

public interface JMSStats
extends Stats {
    public JMSConnectionStats[] getConnections();
}

