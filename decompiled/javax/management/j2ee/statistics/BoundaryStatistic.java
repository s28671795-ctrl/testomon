/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.Statistic;

public interface BoundaryStatistic
extends Statistic {
    public long getUpperBound();

    public long getLowerBound();
}

