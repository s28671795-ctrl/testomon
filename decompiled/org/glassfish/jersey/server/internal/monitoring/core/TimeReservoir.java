/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring.core;

import java.util.concurrent.TimeUnit;
import org.glassfish.jersey.server.internal.monitoring.core.UniformTimeSnapshot;

public interface TimeReservoir<V> {
    public int size(long var1, TimeUnit var3);

    public void update(V var1, long var2, TimeUnit var4);

    public UniformTimeSnapshot getSnapshot(long var1, TimeUnit var3);

    public long interval(TimeUnit var1);
}

