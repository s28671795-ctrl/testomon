/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import org.apache.activemq.management.StatisticImpl;

public class RangeStatisticImpl
extends StatisticImpl {
    private long highWaterMark;
    private long lowWaterMark;
    private long current;

    public RangeStatisticImpl(String name, String unit, String description) {
        super(name, unit, description);
    }

    @Override
    public void reset() {
        if (this.isDoReset()) {
            super.reset();
            this.current = 0L;
            this.lowWaterMark = 0L;
            this.highWaterMark = 0L;
        }
    }

    public long getHighWaterMark() {
        return this.highWaterMark;
    }

    public long getLowWaterMark() {
        return this.lowWaterMark;
    }

    public long getCurrent() {
        return this.current;
    }

    public void setCurrent(long current) {
        this.current = current;
        if (current > this.highWaterMark) {
            this.highWaterMark = current;
        }
        if (current < this.lowWaterMark || this.lowWaterMark == 0L) {
            this.lowWaterMark = current;
        }
        this.updateSampleTime();
    }

    @Override
    protected void appendFieldDescription(StringBuffer buffer) {
        buffer.append(" current: ");
        buffer.append(Long.toString(this.current));
        buffer.append(" lowWaterMark: ");
        buffer.append(Long.toString(this.lowWaterMark));
        buffer.append(" highWaterMark: ");
        buffer.append(Long.toString(this.highWaterMark));
        super.appendFieldDescription(buffer);
    }
}

