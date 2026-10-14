/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.config.models;

import com.incoresoft.config.models.ConnectionConfig;
import com.incoresoft.config.models.DatabaseConfig;
import com.incoresoft.config.models.LoadBalancerConfig;
import com.incoresoft.config.models.NatsConfig;
import com.incoresoft.config.models.QDrantConfig;
import com.incoresoft.config.models.RedisConfig;
import com.incoresoft.config.models.StorageConfig;
import com.incoresoft.config.models.config.Config;
import com.incoresoft.dto.config.ConfigDTO;
import com.incoresoft.vezha.config.models.AnalyticsVramConfig;
import com.incoresoft.vezha.dto.config.VezhaConfigDTO;

public class VezhaConfig
implements Config {
    private final String customStylesFile;
    private final DatabaseConfig database;
    private final StorageConfig storage;
    private final NatsConfig nats;
    private final RedisConfig redis;
    private final QDrantConfig qdrant;
    private final ConnectionConfig connectionConfig;
    private final LoadBalancerConfig loadBalancer;
    private final AnalyticsVramConfig analyticsVram;
    private final int gpuStartDebounceSeconds;
    private final String jettyMultipartFilesCacheDirectory;
    private final boolean isSecondStart;
    private final String marketplaceServer;
    private final boolean checkDiskSpace;
    private final boolean isVmsIntegrationEnabled;
    private final boolean checkServerDeath;
    private final String sessionStoreDirectory;

    public VezhaConfig(VezhaConfigDTO configDTO) {
        this.customStylesFile = configDTO.getCustomStylesFile();
        this.storage = new StorageConfig(configDTO.getStorage());
        this.database = new DatabaseConfig(configDTO.getDatabase());
        this.nats = new NatsConfig(configDTO.getNats());
        this.redis = new RedisConfig(configDTO.getRedis());
        this.qdrant = new QDrantConfig(configDTO.getQdrant());
        this.connectionConfig = new ConnectionConfig(configDTO.getConnections());
        this.loadBalancer = new LoadBalancerConfig(configDTO.getLoadBalancer());
        this.analyticsVram = new AnalyticsVramConfig(configDTO.getAnalyticsVram());
        this.gpuStartDebounceSeconds = configDTO.getGpuStartDebounceSeconds() != null ? configDTO.getGpuStartDebounceSeconds() : 3;
        this.jettyMultipartFilesCacheDirectory = configDTO.getJettyMultipartFilesCacheDirectory();
        this.isSecondStart = configDTO.isSecondStart();
        this.marketplaceServer = configDTO.getMarketplaceServer();
        this.checkDiskSpace = configDTO.isCheckDiskSpace();
        this.isVmsIntegrationEnabled = configDTO.isVmsIntegrationEnabled();
        this.checkServerDeath = configDTO.getCheckServerDeath() != null ? configDTO.getCheckServerDeath() : true;
        this.sessionStoreDirectory = configDTO.getSessionStoreDirectory();
    }

    @Override
    public ConfigDTO toDTO() {
        return new VezhaConfigDTO(this);
    }

    public String getCustomStylesFile() {
        return this.customStylesFile;
    }

    @Override
    public DatabaseConfig getDatabase() {
        return this.database;
    }

    @Override
    public StorageConfig getStorage() {
        return this.storage;
    }

    public NatsConfig getNats() {
        return this.nats;
    }

    public RedisConfig getRedis() {
        return this.redis;
    }

    public QDrantConfig getQdrant() {
        return this.qdrant;
    }

    @Override
    public ConnectionConfig getConnectionConfig() {
        return this.connectionConfig;
    }

    public LoadBalancerConfig getLoadBalancer() {
        return this.loadBalancer;
    }

    public AnalyticsVramConfig getAnalyticsVram() {
        return this.analyticsVram;
    }

    public int getGpuStartDebounceSeconds() {
        return this.gpuStartDebounceSeconds;
    }

    @Override
    public String getJettyMultipartFilesCacheDirectory() {
        return this.jettyMultipartFilesCacheDirectory;
    }

    public boolean isSecondStart() {
        return this.isSecondStart;
    }

    @Override
    public String getMarketplaceServer() {
        return this.marketplaceServer;
    }

    public boolean isCheckDiskSpace() {
        return this.checkDiskSpace;
    }

    public boolean isVmsIntegrationEnabled() {
        return this.isVmsIntegrationEnabled;
    }

    public boolean isCheckServerDeath() {
        return this.checkServerDeath;
    }

    @Override
    public String getSessionStoreDirectory() {
        return this.sessionStoreDirectory;
    }

    public VezhaConfig(String customStylesFile, DatabaseConfig database, StorageConfig storage, NatsConfig nats, RedisConfig redis, QDrantConfig qdrant, ConnectionConfig connectionConfig, LoadBalancerConfig loadBalancer, AnalyticsVramConfig analyticsVram, int gpuStartDebounceSeconds, String jettyMultipartFilesCacheDirectory, boolean isSecondStart, String marketplaceServer, boolean checkDiskSpace, boolean isVmsIntegrationEnabled, boolean checkServerDeath, String sessionStoreDirectory) {
        this.customStylesFile = customStylesFile;
        this.database = database;
        this.storage = storage;
        this.nats = nats;
        this.redis = redis;
        this.qdrant = qdrant;
        this.connectionConfig = connectionConfig;
        this.loadBalancer = loadBalancer;
        this.analyticsVram = analyticsVram;
        this.gpuStartDebounceSeconds = gpuStartDebounceSeconds;
        this.jettyMultipartFilesCacheDirectory = jettyMultipartFilesCacheDirectory;
        this.isSecondStart = isSecondStart;
        this.marketplaceServer = marketplaceServer;
        this.checkDiskSpace = checkDiskSpace;
        this.isVmsIntegrationEnabled = isVmsIntegrationEnabled;
        this.checkServerDeath = checkServerDeath;
        this.sessionStoreDirectory = sessionStoreDirectory;
    }

    public VezhaConfig withDatabase(DatabaseConfig database) {
        return this.database == database ? this : new VezhaConfig(this.customStylesFile, database, this.storage, this.nats, this.redis, this.qdrant, this.connectionConfig, this.loadBalancer, this.analyticsVram, this.gpuStartDebounceSeconds, this.jettyMultipartFilesCacheDirectory, this.isSecondStart, this.marketplaceServer, this.checkDiskSpace, this.isVmsIntegrationEnabled, this.checkServerDeath, this.sessionStoreDirectory);
    }

    public VezhaConfig withSecondStart(boolean isSecondStart) {
        return this.isSecondStart == isSecondStart ? this : new VezhaConfig(this.customStylesFile, this.database, this.storage, this.nats, this.redis, this.qdrant, this.connectionConfig, this.loadBalancer, this.analyticsVram, this.gpuStartDebounceSeconds, this.jettyMultipartFilesCacheDirectory, isSecondStart, this.marketplaceServer, this.checkDiskSpace, this.isVmsIntegrationEnabled, this.checkServerDeath, this.sessionStoreDirectory);
    }

    public VezhaConfig withMarketplaceServer(String marketplaceServer) {
        return this.marketplaceServer == marketplaceServer ? this : new VezhaConfig(this.customStylesFile, this.database, this.storage, this.nats, this.redis, this.qdrant, this.connectionConfig, this.loadBalancer, this.analyticsVram, this.gpuStartDebounceSeconds, this.jettyMultipartFilesCacheDirectory, this.isSecondStart, marketplaceServer, this.checkDiskSpace, this.isVmsIntegrationEnabled, this.checkServerDeath, this.sessionStoreDirectory);
    }

    public VezhaConfig withVmsIntegrationEnabled(boolean isVmsIntegrationEnabled) {
        return this.isVmsIntegrationEnabled == isVmsIntegrationEnabled ? this : new VezhaConfig(this.customStylesFile, this.database, this.storage, this.nats, this.redis, this.qdrant, this.connectionConfig, this.loadBalancer, this.analyticsVram, this.gpuStartDebounceSeconds, this.jettyMultipartFilesCacheDirectory, this.isSecondStart, this.marketplaceServer, this.checkDiskSpace, isVmsIntegrationEnabled, this.checkServerDeath, this.sessionStoreDirectory);
    }
}

