/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.buslifecycle;

public interface BusLifeCycleListener {
    public void initComplete();

    public void preShutdown();

    public void postShutdown();
}

