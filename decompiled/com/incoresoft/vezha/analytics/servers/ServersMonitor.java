/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.inject.Inject;
import com.incoresoft.config.models.LoadBalancerConfig;
import com.incoresoft.middleware.http.exceptions.ServerNotFoundException;
import com.incoresoft.middleware.model.analytics.module.hardware.Hardware;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.servers.GpuMemoryReserveCalculator;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.ServerBusyException;
import com.incoresoft.vezha.analytics.servers.listeners.HeartBeatMessageQueueListener;
import com.incoresoft.vezha.analytics.servers.listeners.ServerActionListener;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.config.models.VezhaConfig;
import com.incoresoft.vezha.database.ServerRepository;
import com.incoresoft.vezha.dto.servers.ServerSettingsDTO;
import com.incoresoft.vezha.dto.servers.mq.LogFileContentMqDTO;
import com.incoresoft.vezha.dto.servers.mq.LogFileContentRequestParamsDTO;
import com.incoresoft.vezha.dto.servers.mq.LogFileMqDTO;
import com.incoresoft.vezha.models.servers.ServerSettings;
import java.lang.invoke.LambdaMetafactory;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServersMonitor {
    private static final Logger log = LoggerFactory.getLogger(ServersMonitor.class);
    public static final int MAX_NVDEC_LOAD = 80;
    public static final long DEATH_CHECK_PERIOD = 10L;
    public static final long SERVER_TIMEOUT = 60000L;
    private final Map<String, Server> serversMap = new ConcurrentHashMap<String, Server>();
    private final Set<ServerActionListener> serverActionListeners = new ConcurrentSkipListSet<ServerActionListener>(Comparator.comparingInt(System::identityHashCode));
    private final MessageQueueConnector mqConnector;
    private final VezhaConfigManager configManager;
    private final StreamsManager streamsManager;
    private final GpuMemoryReserveCalculator gpuMemoryReserveCalculator;

    @Inject
    public ServersMonitor(@NotNull MessageQueueConnector mqConnector, @NotNull VezhaConfigManager configManager, @NotNull StreamsManager streamsManager, @NotNull ServerRepository serverRepository, @NotNull GpuMemoryReserveCalculator gpuMemoryReserveCalculator) {
        this.mqConnector = Objects.requireNonNull(mqConnector, "mqConnector must not be null");
        this.configManager = Objects.requireNonNull(configManager, "configManager must not be null");
        this.streamsManager = Objects.requireNonNull(streamsManager, "streamsManager must not be null");
        this.gpuMemoryReserveCalculator = Objects.requireNonNull(gpuMemoryReserveCalculator, "gpuMemoryReserveCalculator must not be null");
        mqConnector.addListener(new HeartBeatMessageQueueListener(serverRepository, this));
    }

    public void addServerActionListener(@Nullable ServerActionListener listener) {
        if (listener != null) {
            this.serverActionListeners.add(listener);
        }
    }

    public void removeServerActionListener(@Nullable ServerActionListener listener) {
        if (listener != null) {
            this.serverActionListeners.remove(listener);
        }
    }

    public void removeDeadServers() {
        try {
            Instant now = Instant.now();
            Iterator<Map.Entry<String, Server>> it = this.serversMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Server> entry = it.next();
                Server server = entry.getValue();
                if (server == null) {
                    it.remove();
                    continue;
                }
                long offlineMs = Duration.between(server.getLastHeartBeat(), now).toMillis();
                if (offlineMs <= 60000L) continue;
                log.info("Server {} is dead (offlineMs={}).", (Object)server.getId(), (Object)offlineMs);
                for (ServerActionListener listener : this.serverActionListeners) {
                    try {
                        listener.onServerDeath(server);
                    }
                    catch (Throwable e) {
                        log.error("Error processing onServerDeath for server {}", (Object)server.getId(), (Object)e);
                    }
                }
                it.remove();
            }
        }
        catch (Exception e) {
            log.error("Error while removing dead servers", e);
        }
    }

    @Nullable
    public Server getServerById(String id) {
        return this.serversMap.get(id);
    }

    @NotNull
    public Map<String, Server> getServers() {
        return this.serversMap;
    }

    public void restartServer(String id) {
        this.mqConnector.publish("restart_server-" + id, "{}".getBytes());
    }

    public void rebootMachine(String id) {
        this.mqConnector.publish("reboot_machine-" + id, "{}".getBytes());
    }

    public void sendCurrentStorageSettings(String serverId) {
        HashMap<String, String> storageParams = new HashMap<String, String>(((VezhaConfig)this.configManager.getConfig()).getStorage().getProperties());
        storageParams.put("prefix", "analytics/");
        this.mqConnector.publish("update-storage-settings-%s".formatted(serverId), JSON.toJSONString(storageParams).getBytes());
    }

    public void updateServerSettings(@NotNull ServerSettings serverSettings) {
        Server server = this.serversMap.get(serverSettings.getId());
        if (server != null) {
            this.serversMap.put(serverSettings.getId(), new Server(server, serverSettings.getPersistenceInfo()));
        }
        this.mqConnector.publish("update_server_settings-" + serverSettings.getId(), JSON.toJSONString((Object)new ServerSettingsDTO(serverSettings), SerializerFeature.WriteNonStringKeyAsString).getBytes(StandardCharsets.UTF_8));
    }

    public List<LogFileMqDTO> getLogFiles(String serverId) {
        Message response = this.mqConnector.request("analytics_log_files-" + serverId, new JSONObject().toJSONString().getBytes(), Duration.ofSeconds(5L));
        if (response == null) {
            throw new RuntimeException("no response from server for 5 seconds");
        }
        return JSON.parseArray(new String(response.getData()), LogFileMqDTO.class);
    }

    public LogFileContentMqDTO getLogFileContent(String serverId, String path, int linesFromFileEnd) {
        LogFileContentRequestParamsDTO paramsDTO = new LogFileContentRequestParamsDTO(path, linesFromFileEnd);
        Message response = this.mqConnector.request("analytics_log_file_content-" + serverId, JSON.toJSONBytes((Object)paramsDTO, new SerializerFeature[0]), Duration.ofSeconds(5L));
        if (response == null) {
            throw new RuntimeException("no response from server for 5 seconds");
        }
        return JSON.parseObject(new String(response.getData()), LogFileContentMqDTO.class);
    }

    /*
     * Unable to fully structure code
     */
    @NotNull
    public Server getOptimalServer(@NotNull BaseAnalytics analytics) throws IllegalStateException {
        block13: {
            Objects.requireNonNull(analytics, "analytics must not be null");
            embeddedStream = ((Stream)this.streamsManager.get(analytics.getStreamId())).getType() == StreamType.embedded;
            loadBalancerConfig = ((VezhaConfig)this.configManager.getConfig()).getLoadBalancer();
            canHold = (Predicate<Server>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$getOptimalServer$0(boolean com.incoresoft.vezha.analytics.BaseAnalytics com.incoresoft.config.models.LoadBalancerConfig com.incoresoft.vezha.analytics.servers.Server ), (Lcom/incoresoft/vezha/analytics/servers/Server;)Z)((ServersMonitor)this, (boolean)embeddedStream, (BaseAnalytics)analytics, (LoadBalancerConfig)loadBalancerConfig);
            known = this.serversMap.values();
            if (ServersMonitor.log.isDebugEnabled()) {
                allowed = known.stream().filter((Predicate<Server>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, isServerAllowed(com.incoresoft.vezha.analytics.servers.Server ), (Lcom/incoresoft/vezha/analytics/servers/Server;)Z)((BaseAnalytics)analytics)).toList();
                supporting = allowed.stream().filter((Predicate<Server>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$getOptimalServer$1(com.incoresoft.vezha.analytics.BaseAnalytics com.incoresoft.vezha.analytics.servers.Server ), (Lcom/incoresoft/vezha/analytics/servers/Server;)Z)((BaseAnalytics)analytics)).toList();
                supported = supporting.stream().filter(canHold).toList();
                ServersMonitor.log.debug("Placing analytics {} (hardware={}, embeddedStream={}): {} servers known -> {} allowed -> {} support the type -> {} can hold it", new Object[]{analytics.getId(), analytics.getModule().getHardwareSettings().getHardware(), embeddedStream, known.size(), allowed.size(), supporting.size(), supported.size()});
            } else {
                supported = known.stream().filter((Predicate<Server>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, isServerAllowed(com.incoresoft.vezha.analytics.servers.Server ), (Lcom/incoresoft/vezha/analytics/servers/Server;)Z)((BaseAnalytics)analytics)).filter((Predicate<Server>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$getOptimalServer$2(com.incoresoft.vezha.analytics.BaseAnalytics com.incoresoft.vezha.analytics.servers.Server ), (Lcom/incoresoft/vezha/analytics/servers/Server;)Z)((BaseAnalytics)analytics)).filter(canHold).toList();
            }
            if (!supported.isEmpty()) break block13;
            if (embeddedStream) ** GOTO lbl-1000
            if (known.stream().filter((Predicate<Server>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, isServerAllowed(com.incoresoft.vezha.analytics.servers.Server ), (Lcom/incoresoft/vezha/analytics/servers/Server;)Z)((BaseAnalytics)analytics)).filter((Predicate<Server>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$getOptimalServer$3(com.incoresoft.vezha.analytics.BaseAnalytics com.incoresoft.vezha.analytics.servers.Server ), (Lcom/incoresoft/vezha/analytics/servers/Server;)Z)((BaseAnalytics)analytics)).anyMatch((Predicate<Server>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$getOptimalServer$4(com.incoresoft.vezha.analytics.BaseAnalytics com.incoresoft.config.models.LoadBalancerConfig com.incoresoft.vezha.analytics.servers.Server ), (Lcom/incoresoft/vezha/analytics/servers/Server;)Z)((ServersMonitor)this, (BaseAnalytics)analytics, (LoadBalancerConfig)loadBalancerConfig))) {
                v0 = true;
            } else lbl-1000:
            // 2 sources

            {
                v0 = onlyDebounce = false;
            }
            if (onlyDebounce) {
                throw new ServerBusyException(analytics);
            }
            throw new ServerNotFoundException(analytics);
        }
        if (analytics.getAnalyticsStatus().getServerValue() != null) {
            pinnedServerId = analytics.getAnalyticsStatus().getServerValue().getId();
            pinned = supported.stream().filter((Predicate<Server>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$getOptimalServer$5(java.lang.String com.incoresoft.vezha.analytics.servers.Server ), (Lcom/incoresoft/vezha/analytics/servers/Server;)Z)((String)pinnedServerId)).findFirst().orElse(null);
            if (pinned != null) {
                ServersMonitor.log.debug("Analytics {} is pinned to server {} which still has room, keeping it there", (Object)analytics.getId(), (Object)pinnedServerId);
                return pinned;
            }
            ServersMonitor.log.info("Analytics {} was pinned to server {} but it can no longer hold it, re-balancing", (Object)analytics.getId(), (Object)pinnedServerId);
        }
        if (this.configManager.isDebugMode()) {
            first = supported.getFirst();
            ServersMonitor.log.debug("Debug mode: placing analytics {} on first available server {}", (Object)analytics.getId(), (Object)first.getId());
            return first;
        }
        mustBeStartedOn = analytics.getModule().getHardwareSettings().getHardware();
        if (mustBeStartedOn == Hardware.gpu) {
            sorted = supported.stream().filter((Predicate<Server>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$getOptimalServer$6(com.incoresoft.vezha.analytics.servers.Server ), (Lcom/incoresoft/vezha/analytics/servers/Server;)Z)()).sorted((Comparator)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;Ljava/lang/Object;)I, compareServersLoadByGpu(com.incoresoft.vezha.analytics.servers.Server com.incoresoft.vezha.analytics.servers.Server ), (Lcom/incoresoft/vezha/analytics/servers/Server;Lcom/incoresoft/vezha/analytics/servers/Server;)I)()).toList();
        } else if (mustBeStartedOn == Hardware.cpu) {
            sorted = supported.stream().sorted((Comparator)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;Ljava/lang/Object;)I, compareServersLoadByCpu(com.incoresoft.vezha.analytics.servers.Server com.incoresoft.vezha.analytics.servers.Server ), (Lcom/incoresoft/vezha/analytics/servers/Server;Lcom/incoresoft/vezha/analytics/servers/Server;)I)()).toList();
        } else {
            throw new IllegalStateException(String.format("Cannot start analytics with hardware option \"%s\"", new Object[]{mustBeStartedOn}));
        }
        if (sorted.isEmpty()) {
            throw new ServerNotFoundException(analytics);
        }
        chosen = Objects.requireNonNull(sorted.getFirst());
        ServersMonitor.log.debug("Balancer selected server {} for analytics {} out of {} candidate(s), ranked by {} load", new Object[]{chosen.getId(), analytics.getId(), sorted.size(), mustBeStartedOn});
        return chosen;
    }

    public boolean addOrUpdateServer(@NotNull Server server) {
        Objects.requireNonNull(server, "server must not be null");
        boolean[] added = new boolean[]{false};
        this.serversMap.compute(server.getId(), (id, previous) -> {
            if (previous == null) {
                added[0] = true;
            } else {
                Server.carryOverStartDebounce(previous, server);
            }
            return server;
        });
        return added[0];
    }

    public Set<ServerActionListener> getServerActionListeners() {
        return this.serverActionListeners;
    }

    private static /* synthetic */ boolean lambda$getOptimalServer$6(Server server) {
        return !server.getGraphics().isEmpty();
    }

    private static /* synthetic */ boolean lambda$getOptimalServer$5(String pinnedServerId, Server server) {
        return server.getId().equals(pinnedServerId);
    }

    private /* synthetic */ boolean lambda$getOptimalServer$4(BaseAnalytics analytics, LoadBalancerConfig loadBalancerConfig, Server server) {
        return server.canHold(analytics, loadBalancerConfig, this.gpuMemoryReserveCalculator.forServer(server), true);
    }

    private static /* synthetic */ boolean lambda$getOptimalServer$3(BaseAnalytics analytics, Server server) {
        return server.supports(analytics);
    }

    private static /* synthetic */ boolean lambda$getOptimalServer$2(BaseAnalytics analytics, Server server) {
        return server.supports(analytics);
    }

    private static /* synthetic */ boolean lambda$getOptimalServer$1(BaseAnalytics analytics, Server server) {
        return server.supports(analytics);
    }

    private /* synthetic */ boolean lambda$getOptimalServer$0(boolean embeddedStream, BaseAnalytics analytics, LoadBalancerConfig loadBalancerConfig, Server server) {
        return embeddedStream || server.canHold(analytics, loadBalancerConfig, this.gpuMemoryReserveCalculator.forServer(server));
    }
}

