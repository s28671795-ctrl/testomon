/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.config.models.config.Config;
import com.incoresoft.dto.config.ConfigDTO;
import com.incoresoft.dto.config.ConnectionConfigDTO;
import com.incoresoft.dto.config.DatabaseConfigDTO;
import com.incoresoft.dto.config.LoadBalancerDTO;
import com.incoresoft.dto.config.NatsConfigDTO;
import com.incoresoft.dto.config.QDrantConfigDTO;
import com.incoresoft.dto.config.RedisConfigDTO;
import com.incoresoft.dto.config.storage.StorageConfigDTO;
import com.incoresoft.vezha.config.models.VezhaConfig;
import com.incoresoft.vezha.dto.config.AnalyticsVramDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="ConfigDTO", description="Config class structure")
@JsonDeserialize(builder=VezhaConfigDTOBuilder.class)
public final class VezhaConfigDTO
implements ConfigDTO {
    @JsonProperty(value="custom_styles_file")
    @Schema(name="custom_styles_file", description="Custom styles file", example="./custom_styles.css", implementation=String.class)
    private final String customStylesFile;
    @JsonProperty(value="storage")
    @Schema(name="storage", description="Storage path", implementation=StorageConfigDTO.class)
    private final StorageConfigDTO storage;
    @JsonProperty(value="db")
    @Schema(name="db", description="Database settings", implementation=DatabaseConfigDTO.class)
    private final DatabaseConfigDTO database;
    @JsonProperty(value="nats")
    @Schema(name="nats", description="Nats settings", implementation=NatsConfigDTO.class)
    private final NatsConfigDTO nats;
    @JsonProperty(value="redis")
    @Schema(name="redis", description="Redis settings", implementation=RedisConfigDTO.class)
    private final RedisConfigDTO redis;
    @JsonProperty(value="qdrant")
    @Schema(name="qdrant", description="Qdrant settings", implementation=QDrantConfigDTO.class)
    private final QDrantConfigDTO qdrant;
    @JsonProperty(value="connections")
    @Schema(name="connections", description="Host settings", implementation=ConnectionConfigDTO.class)
    private final ConnectionConfigDTO connections;
    @JsonProperty(value="load_balancer")
    @Schema(name="load_balancer", description="Load balancer settings", implementation=LoadBalancerDTO.class)
    private final LoadBalancerDTO loadBalancer;
    @JsonProperty(value="analytics_vram")
    @Schema(name="analytics_vram", description="Expected GPU memory per analytics, used to account for analytics whose stream is offline and therefore invisible in the reported free memory", implementation=AnalyticsVramDTO.class)
    private final AnalyticsVramDTO analyticsVram;
    @JsonProperty(value="gpu_start_debounce_seconds")
    @Schema(name="gpu_start_debounce_seconds", description="How long a GPU is held back after an analytics starts on it, so a burst does not all land on one card before the server reports it. 0 disables the debounce; values are clamped to 0..60.", example="3", nullable=true)
    private final Integer gpuStartDebounceSeconds;
    @JsonProperty(value="jetty_multipart_files_cache_directory")
    @Schema(name="jetty_multipart_files_cache_directory", description="Directory to store multipart files cache for jetty", example="/temp", implementation=String.class)
    private final String jettyMultipartFilesCacheDirectory;
    @JsonProperty(value="second_start")
    @Schema(hidden=true)
    private final boolean isSecondStart;
    @JsonProperty(value="marketplace_server")
    @Schema(name="marketplace_server", description="URL of marketplace API", implementation=String.class)
    private final String marketplaceServer;
    @JsonProperty(value="check_disk_space")
    @Schema(name="check_disk_space", description="Check disk space", example="true")
    private final boolean checkDiskSpace;
    @JsonProperty(value="vms_integration_enabled")
    @Schema(name="vms_integration_enabled", description="Flag to enable VMS integration", example="false")
    private final boolean isVmsIntegrationEnabled;
    @JsonProperty(value="check_server_death")
    @Schema(name="check_server_death", description="Flag to check server death", example="false")
    private final Boolean checkServerDeath;
    @JsonProperty(value="session_store_directory")
    @Schema(name="session_store_directory", description="Session store directory", example="session-store", implementation=String.class)
    private final String sessionStoreDirectory;

    public VezhaConfigDTO(VezhaConfig config) {
        this.customStylesFile = config.getCustomStylesFile();
        this.storage = config.getStorage().toDto();
        this.database = new DatabaseConfigDTO(config.getDatabase());
        this.nats = new NatsConfigDTO(config.getNats());
        this.redis = new RedisConfigDTO(config.getRedis());
        this.qdrant = new QDrantConfigDTO(config.getQdrant());
        this.connections = new ConnectionConfigDTO(config.getConnectionConfig());
        this.loadBalancer = new LoadBalancerDTO(config.getLoadBalancer());
        this.analyticsVram = new AnalyticsVramDTO(config.getAnalyticsVram());
        this.gpuStartDebounceSeconds = config.getGpuStartDebounceSeconds();
        this.jettyMultipartFilesCacheDirectory = null;
        this.isSecondStart = config.isSecondStart();
        this.marketplaceServer = config.getMarketplaceServer();
        this.checkDiskSpace = config.isCheckDiskSpace();
        this.isVmsIntegrationEnabled = config.isVmsIntegrationEnabled();
        this.checkServerDeath = config.isCheckServerDeath();
        this.sessionStoreDirectory = config.getSessionStoreDirectory();
    }

    @Override
    public <T extends Config> T toConfig() {
        return (T)new VezhaConfig(this);
    }

    public static VezhaConfigDTOBuilder builder() {
        return new VezhaConfigDTOBuilder();
    }

    public String getCustomStylesFile() {
        return this.customStylesFile;
    }

    @Override
    public StorageConfigDTO getStorage() {
        return this.storage;
    }

    @Override
    public DatabaseConfigDTO getDatabase() {
        return this.database;
    }

    public NatsConfigDTO getNats() {
        return this.nats;
    }

    public RedisConfigDTO getRedis() {
        return this.redis;
    }

    public QDrantConfigDTO getQdrant() {
        return this.qdrant;
    }

    public ConnectionConfigDTO getConnections() {
        return this.connections;
    }

    public LoadBalancerDTO getLoadBalancer() {
        return this.loadBalancer;
    }

    public AnalyticsVramDTO getAnalyticsVram() {
        return this.analyticsVram;
    }

    public Integer getGpuStartDebounceSeconds() {
        return this.gpuStartDebounceSeconds;
    }

    public String getJettyMultipartFilesCacheDirectory() {
        return this.jettyMultipartFilesCacheDirectory;
    }

    public boolean isSecondStart() {
        return this.isSecondStart;
    }

    public String getMarketplaceServer() {
        return this.marketplaceServer;
    }

    public boolean isCheckDiskSpace() {
        return this.checkDiskSpace;
    }

    public boolean isVmsIntegrationEnabled() {
        return this.isVmsIntegrationEnabled;
    }

    public Boolean getCheckServerDeath() {
        return this.checkServerDeath;
    }

    public String getSessionStoreDirectory() {
        return this.sessionStoreDirectory;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof VezhaConfigDTO)) {
            return false;
        }
        VezhaConfigDTO other = (VezhaConfigDTO)o;
        if (this.isSecondStart() != other.isSecondStart()) {
            return false;
        }
        if (this.isCheckDiskSpace() != other.isCheckDiskSpace()) {
            return false;
        }
        if (this.isVmsIntegrationEnabled() != other.isVmsIntegrationEnabled()) {
            return false;
        }
        Integer this$gpuStartDebounceSeconds = this.getGpuStartDebounceSeconds();
        Integer other$gpuStartDebounceSeconds = other.getGpuStartDebounceSeconds();
        if (this$gpuStartDebounceSeconds == null ? other$gpuStartDebounceSeconds != null : !((Object)this$gpuStartDebounceSeconds).equals(other$gpuStartDebounceSeconds)) {
            return false;
        }
        Boolean this$checkServerDeath = this.getCheckServerDeath();
        Boolean other$checkServerDeath = other.getCheckServerDeath();
        if (this$checkServerDeath == null ? other$checkServerDeath != null : !((Object)this$checkServerDeath).equals(other$checkServerDeath)) {
            return false;
        }
        String this$customStylesFile = this.getCustomStylesFile();
        String other$customStylesFile = other.getCustomStylesFile();
        if (this$customStylesFile == null ? other$customStylesFile != null : !this$customStylesFile.equals(other$customStylesFile)) {
            return false;
        }
        StorageConfigDTO this$storage = this.getStorage();
        StorageConfigDTO other$storage = other.getStorage();
        if (this$storage == null ? other$storage != null : !this$storage.equals(other$storage)) {
            return false;
        }
        DatabaseConfigDTO this$database = this.getDatabase();
        DatabaseConfigDTO other$database = other.getDatabase();
        if (this$database == null ? other$database != null : !((Object)this$database).equals(other$database)) {
            return false;
        }
        NatsConfigDTO this$nats = this.getNats();
        NatsConfigDTO other$nats = other.getNats();
        if (this$nats == null ? other$nats != null : !((Object)this$nats).equals(other$nats)) {
            return false;
        }
        RedisConfigDTO this$redis = this.getRedis();
        RedisConfigDTO other$redis = other.getRedis();
        if (this$redis == null ? other$redis != null : !((Object)this$redis).equals(other$redis)) {
            return false;
        }
        QDrantConfigDTO this$qdrant = this.getQdrant();
        QDrantConfigDTO other$qdrant = other.getQdrant();
        if (this$qdrant == null ? other$qdrant != null : !((Object)this$qdrant).equals(other$qdrant)) {
            return false;
        }
        ConnectionConfigDTO this$connections = this.getConnections();
        ConnectionConfigDTO other$connections = other.getConnections();
        if (this$connections == null ? other$connections != null : !((Object)this$connections).equals(other$connections)) {
            return false;
        }
        LoadBalancerDTO this$loadBalancer = this.getLoadBalancer();
        LoadBalancerDTO other$loadBalancer = other.getLoadBalancer();
        if (this$loadBalancer == null ? other$loadBalancer != null : !((Object)this$loadBalancer).equals(other$loadBalancer)) {
            return false;
        }
        AnalyticsVramDTO this$analyticsVram = this.getAnalyticsVram();
        AnalyticsVramDTO other$analyticsVram = other.getAnalyticsVram();
        if (this$analyticsVram == null ? other$analyticsVram != null : !((Object)this$analyticsVram).equals(other$analyticsVram)) {
            return false;
        }
        String this$jettyMultipartFilesCacheDirectory = this.getJettyMultipartFilesCacheDirectory();
        String other$jettyMultipartFilesCacheDirectory = other.getJettyMultipartFilesCacheDirectory();
        if (this$jettyMultipartFilesCacheDirectory == null ? other$jettyMultipartFilesCacheDirectory != null : !this$jettyMultipartFilesCacheDirectory.equals(other$jettyMultipartFilesCacheDirectory)) {
            return false;
        }
        String this$marketplaceServer = this.getMarketplaceServer();
        String other$marketplaceServer = other.getMarketplaceServer();
        if (this$marketplaceServer == null ? other$marketplaceServer != null : !this$marketplaceServer.equals(other$marketplaceServer)) {
            return false;
        }
        String this$sessionStoreDirectory = this.getSessionStoreDirectory();
        String other$sessionStoreDirectory = other.getSessionStoreDirectory();
        return !(this$sessionStoreDirectory == null ? other$sessionStoreDirectory != null : !this$sessionStoreDirectory.equals(other$sessionStoreDirectory));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isSecondStart() ? 79 : 97);
        result = result * 59 + (this.isCheckDiskSpace() ? 79 : 97);
        result = result * 59 + (this.isVmsIntegrationEnabled() ? 79 : 97);
        Integer $gpuStartDebounceSeconds = this.getGpuStartDebounceSeconds();
        result = result * 59 + ($gpuStartDebounceSeconds == null ? 43 : ((Object)$gpuStartDebounceSeconds).hashCode());
        Boolean $checkServerDeath = this.getCheckServerDeath();
        result = result * 59 + ($checkServerDeath == null ? 43 : ((Object)$checkServerDeath).hashCode());
        String $customStylesFile = this.getCustomStylesFile();
        result = result * 59 + ($customStylesFile == null ? 43 : $customStylesFile.hashCode());
        StorageConfigDTO $storage = this.getStorage();
        result = result * 59 + ($storage == null ? 43 : $storage.hashCode());
        DatabaseConfigDTO $database = this.getDatabase();
        result = result * 59 + ($database == null ? 43 : ((Object)$database).hashCode());
        NatsConfigDTO $nats = this.getNats();
        result = result * 59 + ($nats == null ? 43 : ((Object)$nats).hashCode());
        RedisConfigDTO $redis = this.getRedis();
        result = result * 59 + ($redis == null ? 43 : ((Object)$redis).hashCode());
        QDrantConfigDTO $qdrant = this.getQdrant();
        result = result * 59 + ($qdrant == null ? 43 : ((Object)$qdrant).hashCode());
        ConnectionConfigDTO $connections = this.getConnections();
        result = result * 59 + ($connections == null ? 43 : ((Object)$connections).hashCode());
        LoadBalancerDTO $loadBalancer = this.getLoadBalancer();
        result = result * 59 + ($loadBalancer == null ? 43 : ((Object)$loadBalancer).hashCode());
        AnalyticsVramDTO $analyticsVram = this.getAnalyticsVram();
        result = result * 59 + ($analyticsVram == null ? 43 : ((Object)$analyticsVram).hashCode());
        String $jettyMultipartFilesCacheDirectory = this.getJettyMultipartFilesCacheDirectory();
        result = result * 59 + ($jettyMultipartFilesCacheDirectory == null ? 43 : $jettyMultipartFilesCacheDirectory.hashCode());
        String $marketplaceServer = this.getMarketplaceServer();
        result = result * 59 + ($marketplaceServer == null ? 43 : $marketplaceServer.hashCode());
        String $sessionStoreDirectory = this.getSessionStoreDirectory();
        result = result * 59 + ($sessionStoreDirectory == null ? 43 : $sessionStoreDirectory.hashCode());
        return result;
    }

    public String toString() {
        return "VezhaConfigDTO(customStylesFile=" + this.getCustomStylesFile() + ", storage=" + String.valueOf(this.getStorage()) + ", database=" + String.valueOf(this.getDatabase()) + ", nats=" + String.valueOf(this.getNats()) + ", redis=" + String.valueOf(this.getRedis()) + ", qdrant=" + String.valueOf(this.getQdrant()) + ", connections=" + String.valueOf(this.getConnections()) + ", loadBalancer=" + String.valueOf(this.getLoadBalancer()) + ", analyticsVram=" + String.valueOf(this.getAnalyticsVram()) + ", gpuStartDebounceSeconds=" + this.getGpuStartDebounceSeconds() + ", jettyMultipartFilesCacheDirectory=" + this.getJettyMultipartFilesCacheDirectory() + ", isSecondStart=" + this.isSecondStart() + ", marketplaceServer=" + this.getMarketplaceServer() + ", checkDiskSpace=" + this.isCheckDiskSpace() + ", isVmsIntegrationEnabled=" + this.isVmsIntegrationEnabled() + ", checkServerDeath=" + this.getCheckServerDeath() + ", sessionStoreDirectory=" + this.getSessionStoreDirectory() + ")";
    }

    public VezhaConfigDTO(String customStylesFile, StorageConfigDTO storage, DatabaseConfigDTO database, NatsConfigDTO nats, RedisConfigDTO redis, QDrantConfigDTO qdrant, ConnectionConfigDTO connections, LoadBalancerDTO loadBalancer, AnalyticsVramDTO analyticsVram, Integer gpuStartDebounceSeconds, String jettyMultipartFilesCacheDirectory, boolean isSecondStart, String marketplaceServer, boolean checkDiskSpace, boolean isVmsIntegrationEnabled, Boolean checkServerDeath, String sessionStoreDirectory) {
        this.customStylesFile = customStylesFile;
        this.storage = storage;
        this.database = database;
        this.nats = nats;
        this.redis = redis;
        this.qdrant = qdrant;
        this.connections = connections;
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

    public VezhaConfigDTO withSecondStart(boolean isSecondStart) {
        return this.isSecondStart == isSecondStart ? this : new VezhaConfigDTO(this.customStylesFile, this.storage, this.database, this.nats, this.redis, this.qdrant, this.connections, this.loadBalancer, this.analyticsVram, this.gpuStartDebounceSeconds, this.jettyMultipartFilesCacheDirectory, isSecondStart, this.marketplaceServer, this.checkDiskSpace, this.isVmsIntegrationEnabled, this.checkServerDeath, this.sessionStoreDirectory);
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class VezhaConfigDTOBuilder {
        private String customStylesFile;
        private StorageConfigDTO storage;
        private DatabaseConfigDTO database;
        private NatsConfigDTO nats;
        private RedisConfigDTO redis;
        private QDrantConfigDTO qdrant;
        private ConnectionConfigDTO connections;
        private LoadBalancerDTO loadBalancer;
        private AnalyticsVramDTO analyticsVram;
        private Integer gpuStartDebounceSeconds;
        private String jettyMultipartFilesCacheDirectory;
        private boolean isSecondStart;
        private String marketplaceServer;
        private boolean checkDiskSpace;
        private boolean isVmsIntegrationEnabled;
        private Boolean checkServerDeath;
        private String sessionStoreDirectory;

        VezhaConfigDTOBuilder() {
        }

        @JsonProperty(value="custom_styles_file")
        public VezhaConfigDTOBuilder customStylesFile(String customStylesFile) {
            this.customStylesFile = customStylesFile;
            return this;
        }

        @JsonProperty(value="storage")
        public VezhaConfigDTOBuilder storage(StorageConfigDTO storage) {
            this.storage = storage;
            return this;
        }

        @JsonProperty(value="db")
        public VezhaConfigDTOBuilder database(DatabaseConfigDTO database) {
            this.database = database;
            return this;
        }

        @JsonProperty(value="nats")
        public VezhaConfigDTOBuilder nats(NatsConfigDTO nats) {
            this.nats = nats;
            return this;
        }

        @JsonProperty(value="redis")
        public VezhaConfigDTOBuilder redis(RedisConfigDTO redis) {
            this.redis = redis;
            return this;
        }

        @JsonProperty(value="qdrant")
        public VezhaConfigDTOBuilder qdrant(QDrantConfigDTO qdrant) {
            this.qdrant = qdrant;
            return this;
        }

        @JsonProperty(value="connections")
        public VezhaConfigDTOBuilder connections(ConnectionConfigDTO connections) {
            this.connections = connections;
            return this;
        }

        @JsonProperty(value="load_balancer")
        public VezhaConfigDTOBuilder loadBalancer(LoadBalancerDTO loadBalancer) {
            this.loadBalancer = loadBalancer;
            return this;
        }

        @JsonProperty(value="analytics_vram")
        public VezhaConfigDTOBuilder analyticsVram(AnalyticsVramDTO analyticsVram) {
            this.analyticsVram = analyticsVram;
            return this;
        }

        @JsonProperty(value="gpu_start_debounce_seconds")
        public VezhaConfigDTOBuilder gpuStartDebounceSeconds(Integer gpuStartDebounceSeconds) {
            this.gpuStartDebounceSeconds = gpuStartDebounceSeconds;
            return this;
        }

        @JsonProperty(value="jetty_multipart_files_cache_directory")
        public VezhaConfigDTOBuilder jettyMultipartFilesCacheDirectory(String jettyMultipartFilesCacheDirectory) {
            this.jettyMultipartFilesCacheDirectory = jettyMultipartFilesCacheDirectory;
            return this;
        }

        @JsonProperty(value="second_start")
        public VezhaConfigDTOBuilder isSecondStart(boolean isSecondStart) {
            this.isSecondStart = isSecondStart;
            return this;
        }

        @JsonProperty(value="marketplace_server")
        public VezhaConfigDTOBuilder marketplaceServer(String marketplaceServer) {
            this.marketplaceServer = marketplaceServer;
            return this;
        }

        @JsonProperty(value="check_disk_space")
        public VezhaConfigDTOBuilder checkDiskSpace(boolean checkDiskSpace) {
            this.checkDiskSpace = checkDiskSpace;
            return this;
        }

        @JsonProperty(value="vms_integration_enabled")
        public VezhaConfigDTOBuilder isVmsIntegrationEnabled(boolean isVmsIntegrationEnabled) {
            this.isVmsIntegrationEnabled = isVmsIntegrationEnabled;
            return this;
        }

        @JsonProperty(value="check_server_death")
        public VezhaConfigDTOBuilder checkServerDeath(Boolean checkServerDeath) {
            this.checkServerDeath = checkServerDeath;
            return this;
        }

        @JsonProperty(value="session_store_directory")
        public VezhaConfigDTOBuilder sessionStoreDirectory(String sessionStoreDirectory) {
            this.sessionStoreDirectory = sessionStoreDirectory;
            return this;
        }

        public VezhaConfigDTO build() {
            return new VezhaConfigDTO(this.customStylesFile, this.storage, this.database, this.nats, this.redis, this.qdrant, this.connections, this.loadBalancer, this.analyticsVram, this.gpuStartDebounceSeconds, this.jettyMultipartFilesCacheDirectory, this.isSecondStart, this.marketplaceServer, this.checkDiskSpace, this.isVmsIntegrationEnabled, this.checkServerDeath, this.sessionStoreDirectory);
        }

        public String toString() {
            return "VezhaConfigDTO.VezhaConfigDTOBuilder(customStylesFile=" + this.customStylesFile + ", storage=" + String.valueOf(this.storage) + ", database=" + String.valueOf(this.database) + ", nats=" + String.valueOf(this.nats) + ", redis=" + String.valueOf(this.redis) + ", qdrant=" + String.valueOf(this.qdrant) + ", connections=" + String.valueOf(this.connections) + ", loadBalancer=" + String.valueOf(this.loadBalancer) + ", analyticsVram=" + String.valueOf(this.analyticsVram) + ", gpuStartDebounceSeconds=" + this.gpuStartDebounceSeconds + ", jettyMultipartFilesCacheDirectory=" + this.jettyMultipartFilesCacheDirectory + ", isSecondStart=" + this.isSecondStart + ", marketplaceServer=" + this.marketplaceServer + ", checkDiskSpace=" + this.checkDiskSpace + ", isVmsIntegrationEnabled=" + this.isVmsIntegrationEnabled + ", checkServerDeath=" + this.checkServerDeath + ", sessionStoreDirectory=" + this.sessionStoreDirectory + ")";
        }
    }
}

