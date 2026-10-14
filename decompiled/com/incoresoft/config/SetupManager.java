/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.config;

import com.google.inject.Inject;
import com.incoresoft.config.ConfigConnectionsValidator;
import com.incoresoft.config.ConfigManager;
import com.incoresoft.config.models.config.Config;
import com.incoresoft.dependencyinjection.exceptions.SettingsSavingException;
import com.incoresoft.dto.config.SetupConfigDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SetupManager<T extends Config> {
    private static final Logger log = LoggerFactory.getLogger(SetupManager.class);
    private final ConfigManager<T> configManager;
    private final ConfigConnectionsValidator configConnectionsValidator;
    protected int httpPort;
    protected int httpsPort;
    protected int natsPort;

    public void saveSetupConfiguration(SetupConfigDTO configDTO) {
        this.configConnectionsValidator.testDatabaseConnection(configDTO.getDatabase());
        this.configConnectionsValidator.testDatabaseInitialization(configDTO.getDatabase());
        this.configConnectionsValidator.testStorageConnection(configDTO.getStorage());
        try {
            T config = this.createConfig(configDTO);
            this.configManager.update(config);
        }
        catch (Exception e) {
            throw new SettingsSavingException(e);
        }
    }

    protected abstract T createConfig(SetupConfigDTO var1);

    @Inject
    public SetupManager(ConfigManager<T> configManager, ConfigConnectionsValidator configConnectionsValidator) {
        this.configManager = configManager;
        this.configConnectionsValidator = configConnectionsValidator;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public void setHttpsPort(int httpsPort) {
        this.httpsPort = httpsPort;
    }

    public void setNatsPort(int natsPort) {
        this.natsPort = natsPort;
    }
}

