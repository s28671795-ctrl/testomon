/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.config;

import com.google.inject.Inject;
import com.incoresoft.config.ConfigConnectionsValidator;
import com.incoresoft.config.DatabaseConfigUtils;
import com.incoresoft.config.SetupManager;
import com.incoresoft.config.models.ConnectionConfig;
import com.incoresoft.config.models.LoadBalancerConfig;
import com.incoresoft.config.models.NatsConfig;
import com.incoresoft.config.models.QDrantConfig;
import com.incoresoft.config.models.RedisConfig;
import com.incoresoft.database.settings.DatabaseUrlParsingManager;
import com.incoresoft.dto.config.SetupConfigDTO;
import com.incoresoft.dto.config.storage.StorageType;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.config.models.AnalyticsVramConfig;
import com.incoresoft.vezha.config.models.VezhaConfig;

public class VezhaSetupManager
extends SetupManager<VezhaConfig> {
    private final DatabaseUrlParsingManager databaseUrlParsingManager;

    @Inject
    public VezhaSetupManager(VezhaConfigManager configManager, ConfigConnectionsValidator configConnectionsValidator, DatabaseUrlParsingManager databaseUrlParsingManager) {
        super(configManager, configConnectionsValidator);
        this.databaseUrlParsingManager = databaseUrlParsingManager;
    }

    @Override
    protected VezhaConfig createConfig(SetupConfigDTO configDTO) {
        return new VezhaConfig(null, DatabaseConfigUtils.buildDatabaseConfig(configDTO.getDatabase(), this.databaseUrlParsingManager), StorageType.buildStorageConfig(configDTO.getStorage()), new NatsConfig(String.format("localhost:%d", this.natsPort)), new RedisConfig(), new QDrantConfig("http://localhost:6333"), new ConnectionConfig(this.httpPort, this.httpsPort), new LoadBalancerConfig(), new AnalyticsVramConfig(), 3, "temp", false, "https://marketplace.incoresoft.com", true, false, true, "session-store-vezha");
    }
}

