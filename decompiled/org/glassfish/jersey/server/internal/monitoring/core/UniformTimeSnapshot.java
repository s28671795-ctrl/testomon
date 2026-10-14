/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring.core;

import java.util.concurrent.TimeUnit;

public interface UniformTimeSnapshot {
    public long size();

    public long getMax();

    public long getMin();

    public double getMean();

    public long getTimeInterval(TimeUnit var1);

    public double getRate(TimeUnit var1);
}

