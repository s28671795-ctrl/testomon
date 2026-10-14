/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientLifeCycleListener;

public interface ClientLifeCycleManager {
    public void clientCreated(Client var1);

    public void clientDestroyed(Client var1);

    public void registerListener(ClientLifeCycleListener var1);

    public void unRegisterListener(ClientLifeCycleListener var1);
}

