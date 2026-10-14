/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.statistics.extended;

import java.util.concurrent.ScheduledExecutorService;
import net.sf.ehcache.statistics.extended.SemiExpiringStatistic;
import org.terracotta.statistics.ValueStatistic;

class ExpiringStatistic<T extends Number>
extends SemiExpiringStatistic<T> {
    public ExpiringStatistic(ValueStatistic<T> source2, ScheduledExecutorService executor2, int historySize, long historyNanos) {
        super(source2, executor2, historySize, historyNanos);
    }

    @Override
    public T value() {
        this.touch();
        return (T)super.value();
    }
}

