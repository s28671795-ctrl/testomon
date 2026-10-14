/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.util.function.LongConsumer;

public interface DemandChain {
    public void demand(long var1);

    default public void setNextDemand(LongConsumer nextDemand) {
    }
}

