/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import java.time.Duration;
import java.util.function.Function;
import org.ldaptive.pool.PooledConnectionProxy;

public interface PruneStrategy
extends Function<PooledConnectionProxy, Boolean> {
    public int getStatisticsSize();

    public Duration getPrunePeriod();
}

