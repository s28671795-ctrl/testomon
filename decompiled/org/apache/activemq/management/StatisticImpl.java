/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import javax.management.j2ee.statistics.Statistic;
import org.apache.activemq.management.Resettable;

public class StatisticImpl
implements Statistic,
Resettable {
    protected boolean enabled;
    private String name;
    private String unit;
    private String description;
    private long startTime;
    private long lastSampleTime;
    private boolean doReset = true;

    public StatisticImpl(String name, String unit, String description) {
        this.name = name;
        this.unit = unit;
        this.description = description;
        this.lastSampleTime = this.startTime = System.currentTimeMillis();
    }

    @Override
    public synchronized void reset() {
        if (this.isDoReset()) {
            this.lastSampleTime = this.startTime = System.currentTimeMillis();
        }
    }

    protected synchronized void updateSampleTime() {
        this.lastSampleTime = System.currentTimeMillis();
    }

    public synchronized String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.name);
        buffer.append("{");
        this.appendFieldDescription(buffer);
        buffer.append(" }");
        return buffer.toString();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public synchronized long getStartTime() {
        return this.startTime;
    }

    @Override
    public synchronized long getLastSampleTime() {
        return this.lastSampleTime;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDoReset() {
        return this.doReset;
    }

    public void setDoReset(boolean doReset) {
        this.doReset = doReset;
    }

    protected synchronized void appendFieldDescription(StringBuffer buffer) {
        buffer.append(" unit: ");
        buffer.append(this.unit);
        buffer.append(" startTime: ");
        buffer.append(this.startTime);
        buffer.append(" lastSampleTime: ");
        buffer.append(this.lastSampleTime);
        buffer.append(" description: ");
        buffer.append(this.description);
    }
}

