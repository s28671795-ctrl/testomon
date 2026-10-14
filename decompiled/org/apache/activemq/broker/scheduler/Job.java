/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.scheduler;

public interface Job {
    public String getJobId();

    public int getRepeat();

    public long getStart();

    public long getDelay();

    public long getPeriod();

    public String getCronEntry();

    public byte[] getPayload();

    public String getStartTime();

    public String getNextExecutionTime();

    public int getExecutionCount();
}

