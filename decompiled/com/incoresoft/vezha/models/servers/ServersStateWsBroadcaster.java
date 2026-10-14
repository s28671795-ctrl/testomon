/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.servers;

import com.google.inject.Inject;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.ServersMonitor;
import com.incoresoft.vezha.analytics.servers.listeners.ServerActionListener;
import com.incoresoft.vezha.dto.servers.FrontServerDTO;
import com.incoresoft.vezha.models.servers.ServerManager;
import com.incoresoft.vezha.protocol.answers.ws.servers.ServerStateMessageType;
import com.incoresoft.vezha.protocol.answers.ws.servers.ServerStateWsAnswer;
import com.incoresoft.vezha.protocol.ws.ServersWSBroadcaster;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServersStateWsBroadcaster
implements ServerActionListener {
    private static final Logger log = LoggerFactory.getLogger(ServersStateWsBroadcaster.class);
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private final ServersWSBroadcaster serversWsBroadcaster;
    private final ServerManager serverManager;
    private final ServersMonitor serversMonitor;

    @Inject
    public void init() {
        this.serversMonitor.addServerActionListener(this);
    }

    @Override
    public void onNewServer(Server server) {
    }

    @Override
    public void onServerStart(Server server) {
        this.sendWsMessage(ServerStateMessageType.ONLINE, server);
    }

    @Override
    public void onServerDeath(Server server) {
        this.sendWsMessage(ServerStateMessageType.OFFLINE, server);
    }

    @Override
    public void onServerHeartBeat(Server server) {
        this.sendWsMessage(ServerStateMessageType.HEART_BEAT, server);
    }

    private void sendWsMessage(@NotNull ServerStateMessageType messageType, @NotNull Server server) {
        this.executor.submit(() -> {
            try {
                FrontServerDTO dto = this.serverManager.injectAdditionalInfo(server);
                this.serversWsBroadcaster.broadcast(new ServerStateWsAnswer(messageType, dto));
            }
            catch (Exception e) {
                log.error("Error while broadcasting server state update via WS for server id {}: {}", server.getId(), e.getMessage(), e);
            }
        });
    }

    @Inject
    public ServersStateWsBroadcaster(ServersWSBroadcaster serversWsBroadcaster, ServerManager serverManager, ServersMonitor serversMonitor) {
        this.serversWsBroadcaster = serversWsBroadcaster;
        this.serverManager = serverManager;
        this.serversMonitor = serversMonitor;
    }
}

