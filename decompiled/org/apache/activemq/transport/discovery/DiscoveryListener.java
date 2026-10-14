/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.discovery;

import org.apache.activemq.command.DiscoveryEvent;

public interface DiscoveryListener {
    public static final String DISCOVERED_OPTION_PREFIX = "discovered.";

    public void onServiceAdd(DiscoveryEvent var1);

    public void onServiceRemove(DiscoveryEvent var1);
}

