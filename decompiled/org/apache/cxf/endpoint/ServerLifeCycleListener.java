/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import org.apache.cxf.endpoint.Server;

public interface ServerLifeCycleListener {
    public void startServer(Server var1);

    public void stopServer(Server var1);
}

