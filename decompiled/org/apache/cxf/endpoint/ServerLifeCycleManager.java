/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerLifeCycleListener;

public interface ServerLifeCycleManager {
    public void startServer(Server var1);

    public void stopServer(Server var1);

    public void registerListener(ServerLifeCycleListener var1);

    public void unRegisterListener(ServerLifeCycleListener var1);
}

