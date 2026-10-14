/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.discovery;

import java.io.IOException;
import org.apache.activemq.Service;
import org.apache.activemq.command.DiscoveryEvent;
import org.apache.activemq.transport.discovery.DiscoveryListener;

public interface DiscoveryAgent
extends Service {
    public void setDiscoveryListener(DiscoveryListener var1);

    public void registerService(String var1) throws IOException;

    public void serviceFailed(DiscoveryEvent var1) throws IOException;
}

