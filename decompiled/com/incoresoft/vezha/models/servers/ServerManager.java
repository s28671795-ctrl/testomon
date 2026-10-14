/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.servers;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.plugin.commons.utils.AnalyticsUtils;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.ServersMonitor;
import com.incoresoft.vezha.database.ServerRepository;
import com.incoresoft.vezha.dto.servers.FrontServerDTO;
import com.incoresoft.vezha.dto.servers.mq.LogFileContentMqDTO;
import com.incoresoft.vezha.dto.servers.mq.LogFileMqDTO;
import com.incoresoft.vezha.models.servers.ServerPersistenceInfo;
import com.incoresoft.vezha.models.servers.ServerSettings;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerManager {
    private static final Logger log = LoggerFactory.getLogger(ServerManager.class);
    private final ServersMonitor serversMonitor;
    private final ServerRepository repository;
    private final AnalyticsManager analyticsManager;
    private final StreamsManager streamsManager;

    public List<FrontServerDTO> getServersDTO() {
        return this.serversMonitor.getServers().values().stream().map(this::injectAdditionalInfo).toList();
    }

    public Optional<FrontServerDTO> getServerDTO(@NotNull String id) {
        return Optional.ofNullable(this.serversMonitor.getServerById(id)).map(this::injectAdditionalInfo);
    }

    @NotNull
    public FrontServerDTO injectAdditionalInfo(@NotNull Server server) {
        Optional<String> userSetName = Optional.ofNullable(this.repository.getServerInfo(server.getId())).map(ServerPersistenceInfo::name);
        Optional<String> cameraAnalyticsName = this.tryFindCameraAnalyticsName(server);
        Object serverName = userSetName.isPresent() && StringUtils.isNotBlank(userSetName.orElseThrow()) ? userSetName.orElseThrow() : (cameraAnalyticsName.isPresent() ? cameraAnalyticsName.orElseThrow() : "Server " + server.getId());
        return new FrontServerDTO(server, (String)serverName, this.analyticsManager);
    }

    private Optional<String> tryFindCameraAnalyticsName(Server server) {
        return this.analyticsManager.getAll(false).stream().filter(analytics -> analytics.getAllowedServerIds() != null && analytics.getAllowedServerIds().contains(server.getId())).map(analytics -> this.streamsManager.get(analytics.getStreamUUID())).filter(Objects::nonNull).filter(stream -> stream.getType() == StreamType.embedded).map(Stream::getName).findFirst();
    }

    public Optional<Server> tryFindServerByCameraAnalyticsId(int analyticsId) {
        return AnalyticsUtils.getAnalytics(analyticsId, this.analyticsManager).filter(it -> {
            Stream stream = this.streamsManager.get(it.getStreamUUID());
            return stream != null && stream.getType() == StreamType.embedded;
        }).stream().flatMap(it -> it.getAllowedServerIds().stream()).map(this.serversMonitor::getServerById).filter(Objects::nonNull).findFirst();
    }

    public void restartServer(String id) {
        this.serversMonitor.restartServer(id);
    }

    public void rebootMachine(String id) {
        this.serversMonitor.rebootMachine(id);
    }

    public FrontServerDTO updateServer(@NotNull ServerSettings serverSettings) {
        ServerPersistenceInfo persistenceInfo = serverSettings.getPersistenceInfo();
        this.repository.upsert(persistenceInfo);
        this.serversMonitor.updateServerSettings(serverSettings);
        return this.getServerDTO(serverSettings.getId()).orElseThrow();
    }

    public List<LogFileMqDTO> getLogFiles(String serverId) {
        return this.serversMonitor.getLogFiles(serverId);
    }

    public LogFileContentMqDTO getLogFileContent(String serverId, String path, int linesFromFileEnd) {
        return this.serversMonitor.getLogFileContent(serverId, path, linesFromFileEnd);
    }

    @Inject
    public ServerManager(ServersMonitor serversMonitor, ServerRepository repository, AnalyticsManager analyticsManager, StreamsManager streamsManager) {
        this.serversMonitor = serversMonitor;
        this.repository = repository;
        this.analyticsManager = analyticsManager;
        this.streamsManager = streamsManager;
    }
}

