/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.util.List;
import org.apache.cxf.endpoint.Server;

public interface ServerRegistry {
    public void register(Server var1);

    public void unregister(Server var1);

    public List<Server> getServers();
}

