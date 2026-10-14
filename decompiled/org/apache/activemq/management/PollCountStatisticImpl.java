/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import java.util.ArrayList;
import java.util.List;
import javax.management.j2ee.statistics.CountStatistic;
import org.apache.activemq.management.StatisticImpl;

public class PollCountStatisticImpl
extends StatisticImpl
implements CountStatistic {
    private PollCountStatisticImpl parent;
    private List<PollCountStatisticImpl> children;

    public PollCountStatisticImpl(PollCountStatisticImpl parent, String name, String description) {
        this(name, description);
        this.setParent(parent);
    }

    public PollCountStatisticImpl(String name, String description) {
        this(name, "count", description);
    }

    public PollCountStatisticImpl(String name, String unit, String description) {
        super(name, unit, description);
    }

    public PollCountStatisticImpl getParent() {
        return this.parent;
    }

    public void setParent(PollCountStatisticImpl parent) {
        if (this.parent != null) {
            this.parent.removeChild(this);
        }
        this.parent = parent;
        if (this.parent != null) {
            this.parent.addChild(this);
        }
    }

    private synchronized void removeChild(PollCountStatisticImpl child) {
        if (this.children != null) {
            this.children.remove(child);
        }
    }

    private synchronized void addChild(PollCountStatisticImpl child) {
        if (this.children == null) {
            this.children = new ArrayList<PollCountStatisticImpl>();
        }
        this.children.add(child);
    }

    @Override
    public synchronized long getCount() {
        if (this.children == null) {
            return 0L;
        }
        long count = 0L;
        for (PollCountStatisticImpl child : this.children) {
            count += child.getCount();
        }
        return count;
    }

    @Override
    protected void appendFieldDescription(StringBuffer buffer) {
        buffer.append(" count: ");
        buffer.append(Long.toString(this.getCount()));
        super.appendFieldDescription(buffer);
    }

    public double getPeriod() {
        double count = this.getCount();
        if (count == 0.0) {
            return 0.0;
        }
        double time = System.currentTimeMillis() - this.getStartTime();
        return time / (count * 1000.0);
    }

    public double getFrequency() {
        double count = this.getCount();
        double time = System.currentTimeMillis() - this.getStartTime();
        return count * 1000.0 / time;
    }
}

