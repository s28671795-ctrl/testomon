/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.listeners;

import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.listeners.ServerActionListener;

public interface ServerHeartBeatListener
extends ServerActionListener {
    @Override
    default public void onNewServer(Server server) {
    }

    @Override
    default public void onServerStart(Server server) {
    }

    @Override
    default public void onServerDeath(Server server) {
    }
}

