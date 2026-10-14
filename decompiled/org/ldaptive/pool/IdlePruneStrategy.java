/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import java.time.Duration;
import java.time.Instant;
import org.ldaptive.pool.PooledConnectionProxy;
import org.ldaptive.pool.PruneStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdlePruneStrategy
implements PruneStrategy {
    private static final int DEFAULT_STATISTICS_SIZE = 1;
    private static final Duration DEFAULT_PRUNE_PERIOD = Duration.ofMinutes(5L);
    private static final Duration DEFAULT_IDLE_TIME = Duration.ofMinutes(10L);
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Duration prunePeriod;
    private Duration idleTime;

    public IdlePruneStrategy() {
        this(DEFAULT_PRUNE_PERIOD, DEFAULT_IDLE_TIME);
    }

    public IdlePruneStrategy(Duration idle) {
        this.setPrunePeriod(idle.dividedBy(2L));
        this.setIdleTime(idle);
    }

    public IdlePruneStrategy(Duration period, Duration idle) {
        this.setPrunePeriod(period);
        this.setIdleTime(idle);
    }

    @Override
    public Boolean apply(PooledConnectionProxy conn) {
        Instant timeAvailable = conn.getPooledConnectionStatistics().getLastAvailableStat();
        this.logger.trace("evaluating timestamp {} for connection {}", (Object)timeAvailable, (Object)conn);
        return timeAvailable.plus(this.idleTime).isBefore(Instant.now());
    }

    @Override
    public int getStatisticsSize() {
        return 1;
    }

    @Override
    public Duration getPrunePeriod() {
        return this.prunePeriod;
    }

    public void setPrunePeriod(Duration period) {
        if (period == null || period.isNegative() || period.isZero()) {
            throw new IllegalArgumentException("Prune period cannot be null, negative or zero");
        }
        this.prunePeriod = period;
    }

    public Duration getIdleTime() {
        return this.idleTime;
    }

    public void setIdleTime(Duration time) {
        if (time == null || time.isNegative()) {
            throw new IllegalArgumentException("Idle time cannot be null or negative");
        }
        this.idleTime = time;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "prunePeriod=" + this.prunePeriod + ", " + "idleTime=" + this.idleTime + "]";
    }
}

