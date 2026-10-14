/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.listeners;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.plugin.commons.mq.ResponseMessage;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.ServersMonitor;
import com.incoresoft.vezha.analytics.servers.dto.ServerDTO;
import com.incoresoft.vezha.analytics.servers.listeners.ServerActionListener;
import com.incoresoft.vezha.database.ServerRepository;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartBeatMessageQueueListener
implements MessageQueueListener {
    private static final Logger log = LoggerFactory.getLogger(HeartBeatMessageQueueListener.class);
    private final ServerRepository serverRepository;
    private final ServersMonitor serversMonitor;
    private final Map<String, Integer> lastHbByServer = new ConcurrentHashMap<String, Integer>();
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    public HeartBeatMessageQueueListener(@NotNull ServerRepository serverRepository, @NotNull ServersMonitor serversMonitor) {
        this.serverRepository = Objects.requireNonNull(serverRepository);
        this.serversMonitor = Objects.requireNonNull(serversMonitor);
    }

    @Override
    public String subject() {
        return "heartBeat";
    }

    @Override
    public Message messageReceived(Message message) {
        this.executorService.submit(() -> {
            try {
                this.newHeartBeat(message);
            }
            catch (Throwable e) {
                log.error("Error receiving heartBeat message.", e);
            }
        });
        return ResponseMessage.empty();
    }

    private void newHeartBeat(Message message) {
        JSONObject params;
        boolean hasStartFlag;
        boolean isNewServer;
        HeartbeatDecision decision;
        String json = new String(message.getData(), StandardCharsets.UTF_8);
        ServerDTO serverDTO = JSON.parseObject(json, ServerDTO.class);
        if (serverDTO == null || serverDTO.getId() == null) {
            log.warn("Invalid heartbeat payload: {}", (Object)json);
            return;
        }
        Server server = new Server(serverDTO, this.serverRepository.getServerInfo(serverDTO.getId()));
        String serverId = server.getId();
        Integer newHb = serverDTO.getHeartbeatId();
        if (newHb == null) {
            log.warn("Heartbeat from server {} carries no heartbeat_id; skipping restart/loss detection", (Object)serverId);
            decision = new HeartbeatDecision();
            decision.prevHb = this.lastHbByServer.get(serverId);
            decision.newHb = -1;
            decision.lostDetected = false;
            decision.counterRestarted = false;
        } else {
            decision = this.updateHeartbeatAtomically(serverId, newHb);
        }
        if (decision.lostDetected) {
            log.info("Heartbeat lost for server {} (prev={}, new={})", serverId, decision.prevHb, decision.newHb);
        }
        if (isNewServer = this.serversMonitor.addOrUpdateServer(server)) {
            for (ServerActionListener listener : this.serversMonitor.getServerActionListeners()) {
                try {
                    listener.onNewServer(server);
                }
                catch (Throwable e) {
                    log.error("Error processing onNewServer for server {}", (Object)serverId, (Object)e);
                }
            }
        }
        boolean bl = hasStartFlag = (params = serverDTO.getParams()) != null && params.containsKey("start");
        if (hasStartFlag || decision.counterRestarted) {
            for (ServerActionListener listener : this.serversMonitor.getServerActionListeners()) {
                try {
                    listener.onServerStart(server);
                }
                catch (Throwable e) {
                    log.error("Error processing onServerStart for server {}", (Object)serverId, (Object)e);
                }
            }
        }
        for (ServerActionListener listener : this.serversMonitor.getServerActionListeners()) {
            try {
                listener.onServerHeartBeat(server);
            }
            catch (Throwable e) {
                log.error("Error processing onServerHeartBeat for server {}", (Object)serverId, (Object)e);
            }
        }
    }

    private HeartbeatDecision updateHeartbeatAtomically(String serverId, int newHb) {
        HeartbeatDecision decision = new HeartbeatDecision();
        decision.newHb = newHb;
        this.lastHbByServer.compute(serverId, (id, prev) -> {
            decision.prevHb = prev;
            if (prev == null) {
                decision.counterRestarted = false;
                decision.lostDetected = false;
                return newHb;
            }
            if (newHb == prev) {
                decision.counterRestarted = false;
                decision.lostDetected = false;
                return prev;
            }
            if (newHb < prev) {
                decision.counterRestarted = true;
                decision.lostDetected = false;
                return newHb;
            }
            decision.counterRestarted = false;
            decision.lostDetected = newHb - prev > 1;
            return newHb;
        });
        return decision;
    }

    private static final class HeartbeatDecision {
        Integer prevHb;
        int newHb;
        boolean lostDetected;
        boolean counterRestarted;

        private HeartbeatDecision() {
        }
    }
}

