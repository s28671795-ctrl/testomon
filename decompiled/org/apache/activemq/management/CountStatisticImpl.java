/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import java.util.concurrent.atomic.AtomicLong;
import javax.management.j2ee.statistics.CountStatistic;
import org.apache.activemq.management.StatisticImpl;

public class CountStatisticImpl
extends StatisticImpl
implements CountStatistic {
    private final AtomicLong counter = new AtomicLong(0L);
    private CountStatisticImpl parent;

    public CountStatisticImpl(CountStatisticImpl parent, String name, String description) {
        this(name, description);
        this.parent = parent;
    }

    public CountStatisticImpl(String name, String description) {
        this(name, "count", description);
    }

    public CountStatisticImpl(String name, String unit, String description) {
        super(name, unit, description);
    }

    @Override
    public void reset() {
        if (this.isDoReset()) {
            super.reset();
            this.counter.set(0L);
        }
    }

    @Override
    public long getCount() {
        return this.counter.get();
    }

    public void setCount(long count) {
        if (this.isEnabled()) {
            this.counter.set(count);
        }
    }

    public void add(long amount) {
        if (this.isEnabled()) {
            this.counter.addAndGet(amount);
            this.updateSampleTime();
            if (this.parent != null) {
                this.parent.add(amount);
            }
        }
    }

    public void increment() {
        if (this.isEnabled()) {
            this.counter.incrementAndGet();
            this.updateSampleTime();
            if (this.parent != null) {
                this.parent.increment();
            }
        }
    }

    public void subtract(long amount) {
        if (this.isEnabled()) {
            this.counter.addAndGet(-amount);
            this.updateSampleTime();
            if (this.parent != null) {
                this.parent.subtract(amount);
            }
        }
    }

    public void decrement() {
        if (this.isEnabled()) {
            this.counter.decrementAndGet();
            this.updateSampleTime();
            if (this.parent != null) {
                this.parent.decrement();
            }
        }
    }

    public CountStatisticImpl getParent() {
        return this.parent;
    }

    public void setParent(CountStatisticImpl parent) {
        if (this.parent != null) {
            this.parent.subtract(this.getCount());
        }
        this.parent = parent;
    }

    @Override
    protected void appendFieldDescription(StringBuffer buffer) {
        buffer.append(" count: ");
        buffer.append(Long.toString(this.counter.get()));
        super.appendFieldDescription(buffer);
    }

    public double getPeriod() {
        double count = this.counter.get();
        if (count == 0.0) {
            return 0.0;
        }
        double time = System.currentTimeMillis() - this.getStartTime();
        return time / (count * 1000.0);
    }

    public double getFrequency() {
        double count = this.counter.get();
        double time = System.currentTimeMillis() - this.getStartTime();
        return count * 1000.0 / time;
    }
}

