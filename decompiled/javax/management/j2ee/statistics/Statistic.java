/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

public interface Statistic {
    public String getName();

    public String getUnit();

    public String getDescription();

    public long getStartTime();

    public long getLastSampleTime();
}

