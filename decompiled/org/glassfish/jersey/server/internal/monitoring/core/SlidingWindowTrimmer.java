/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring.core;

import java.util.concurrent.ConcurrentNavigableMap;
import org.glassfish.jersey.server.internal.monitoring.core.TimeReservoir;

public interface SlidingWindowTrimmer<V> {
    public void trim(ConcurrentNavigableMap<Long, V> var1, long var2);

    public void setTimeReservoir(TimeReservoir<V> var1);
}

