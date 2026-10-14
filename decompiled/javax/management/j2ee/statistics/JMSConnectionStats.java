/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.JMSSessionStats;
import javax.management.j2ee.statistics.Stats;

public interface JMSConnectionStats
extends Stats {
    public JMSSessionStats[] getSessions();

    public boolean isTransactional();
}

