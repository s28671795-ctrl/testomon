/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import org.apache.cxf.endpoint.Client;

public interface ClientLifeCycleListener {
    public void clientCreated(Client var1);

    public void clientDestroyed(Client var1);
}

