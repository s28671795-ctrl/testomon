/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.listeners;

import com.incoresoft.vezha.analytics.servers.Server;

public interface ServerActionListener {
    public void onNewServer(Server var1);

    public void onServerStart(Server var1);

    public void onServerDeath(Server var1);

    public void onServerHeartBeat(Server var1);
}

