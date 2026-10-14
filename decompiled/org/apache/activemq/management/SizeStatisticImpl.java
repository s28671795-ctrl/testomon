/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import org.apache.activemq.management.StatisticImpl;

public class SizeStatisticImpl
extends StatisticImpl {
    private long count;
    private volatile long maxSize;
    private volatile long minSize;
    private long totalSize;
    private SizeStatisticImpl parent;

    public SizeStatisticImpl(String name, String description) {
        this(name, "bytes", description);
    }

    public SizeStatisticImpl(SizeStatisticImpl parent, String name, String description) {
        this(name, description);
        this.parent = parent;
    }

    public SizeStatisticImpl(String name, String unit, String description) {
        super(name, unit, description);
    }

    @Override
    public synchronized void reset() {
        if (this.isDoReset()) {
            super.reset();
            this.count = 0L;
            this.maxSize = 0L;
            this.minSize = 0L;
            this.totalSize = 0L;
        }
    }

    public synchronized long getCount() {
        return this.count;
    }

    public synchronized void addSize(long size) {
        ++this.count;
        this.totalSize += size;
        if (size > this.maxSize) {
            this.maxSize = size;
        }
        if (size < this.minSize || this.minSize == 0L) {
            this.minSize = size;
        }
        this.updateSampleTime();
        if (this.parent != null) {
            this.parent.addSize(size);
        }
    }

    public synchronized void setTotalSize(long size) {
        ++this.count;
        this.totalSize = size;
        if (size > this.maxSize) {
            this.maxSize = size;
        }
        if (size < this.minSize || this.minSize == 0L) {
            this.minSize = size;
        }
        this.updateSampleTime();
    }

    public long getMaxSize() {
        return this.maxSize;
    }

    public long getMinSize() {
        return this.minSize;
    }

    public synchronized long getTotalSize() {
        return this.totalSize;
    }

    public synchronized double getAverageSize() {
        if (this.count == 0L) {
            return 0.0;
        }
        double d = this.totalSize;
        return d / (double)this.count;
    }

    public synchronized double getAverageSizeExcludingMinMax() {
        if (this.count <= 2L) {
            return 0.0;
        }
        double d = this.totalSize - this.minSize - this.maxSize;
        return d / (double)(this.count - 2L);
    }

    public double getAveragePerSecond() {
        double d = 1000.0;
        double averageSize = this.getAverageSize();
        if (averageSize == 0.0) {
            return 0.0;
        }
        return d / averageSize;
    }

    public double getAveragePerSecondExcludingMinMax() {
        double d = 1000.0;
        double average = this.getAverageSizeExcludingMinMax();
        if (average == 0.0) {
            return 0.0;
        }
        return d / average;
    }

    public SizeStatisticImpl getParent() {
        return this.parent;
    }

    public void setParent(SizeStatisticImpl parent) {
        this.parent = parent;
    }

    @Override
    protected synchronized void appendFieldDescription(StringBuffer buffer) {
        buffer.append(" count: ");
        buffer.append(Long.toString(this.count));
        buffer.append(" maxSize: ");
        buffer.append(Long.toString(this.maxSize));
        buffer.append(" minSize: ");
        buffer.append(Long.toString(this.minSize));
        buffer.append(" totalSize: ");
        buffer.append(Long.toString(this.totalSize));
        buffer.append(" averageSize: ");
        buffer.append(Double.toString(this.getAverageSize()));
        buffer.append(" averageTimeExMinMax: ");
        buffer.append(Double.toString(this.getAveragePerSecondExcludingMinMax()));
        buffer.append(" averagePerSecond: ");
        buffer.append(Double.toString(this.getAveragePerSecond()));
        buffer.append(" averagePerSecondExMinMax: ");
        buffer.append(Double.toString(this.getAveragePerSecondExcludingMinMax()));
        super.appendFieldDescription(buffer);
    }
}

