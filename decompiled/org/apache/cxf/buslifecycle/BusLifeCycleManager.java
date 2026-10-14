/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.buslifecycle;

import org.apache.cxf.buslifecycle.BusLifeCycleListener;

public interface BusLifeCycleManager
extends BusLifeCycleListener {
    public void registerLifeCycleListener(BusLifeCycleListener var1);

    public void unregisterLifeCycleListener(BusLifeCycleListener var1);
}

