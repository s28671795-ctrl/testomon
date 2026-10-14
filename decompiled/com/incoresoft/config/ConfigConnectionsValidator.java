/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.config;

import com.google.inject.Inject;
import com.incoresoft.config.CorePluginsProvider;
import com.incoresoft.config.DatabaseConfigUtils;
import com.incoresoft.config.exceptions.StorageConnectionException;
import com.incoresoft.config.models.DatabaseConfig;
import com.incoresoft.config.models.StorageConfig;
import com.incoresoft.config.models.config.Config;
import com.incoresoft.database.settings.DatabaseUrlParsingManager;
import com.incoresoft.dependencyinjection.exceptions.DatabaseInitializationException;
import com.incoresoft.dto.config.SetupDatabaseConfigDTO;
import com.incoresoft.dto.config.storage.SetupStorageConfigDTO;
import com.incoresoft.dto.config.storage.StorageType;
import com.incoresoft.middleware.database.DatabaseConnector;
import com.incoresoft.middleware.database.DatabaseSettings;
import com.incoresoft.middleware.database.SQLConnectionException;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigConnectionsValidator {
    private static final Logger log = LoggerFactory.getLogger(ConfigConnectionsValidator.class);
    private final DatabaseUrlParsingManager databaseUrlParsingManager;
    private final CorePluginsProvider corePluginsProvider;
    public static final String DB_CHANGELOG_FILE = "sql/changelog/db-changelog.xml";

    public void testAllConnections(Config config) {
        this.testDatabaseConnection(config.getDatabase());
        this.testStorageConnection(config.getStorage());
    }

    public void testStorageConnection(StorageConfig storageConfig) {
        try {
            this.corePluginsProvider.createStorageConnector().init(storageConfig.getProperties());
        }
        catch (RuntimeException e) {
            throw new StorageConnectionException();
        }
    }

    public void testStorageConnection(@Nullable SetupStorageConfigDTO setupDTO) {
        this.testStorageConnection(StorageType.buildStorageConfig(setupDTO));
    }

    public void testDatabaseConnection(SetupDatabaseConfigDTO setupDTO) {
        DatabaseConfig databaseConfig = DatabaseConfigUtils.buildDatabaseConfig(setupDTO, this.databaseUrlParsingManager);
        this.testDatabaseConnection(databaseConfig);
    }

    public void testDatabaseConnection(DatabaseConfig databaseConfig) {
        try {
            DatabaseConnector databaseConnector = this.corePluginsProvider.createDatabaseConnector();
            databaseConnector.init(databaseConfig.convertToMap());
        }
        catch (RuntimeException e) {
            if (e instanceof SQLConnectionException) {
                throw e;
            }
            throw new SQLConnectionException(e){

                @Override
                public String getErrorKey() {
                    return "ERROR_UNKNOWN_ERROR_SQL_CONNECTION";
                }
            };
        }
    }

    public void testDatabaseInitialization(SetupDatabaseConfigDTO setupDTO) {
        try {
            DatabaseConfig databaseConfig = DatabaseConfigUtils.buildDatabaseConfig(setupDTO, this.databaseUrlParsingManager);
            this.validateDatabaseInitialization(databaseConfig);
        }
        catch (RuntimeException e) {
            log.error("Cannot initialize db structure.", e);
            throw new DatabaseInitializationException(e);
        }
    }

    private void validateDatabaseInitialization(DatabaseConfig databaseConfig) {
        DatabaseConnector databaseConnector = this.corePluginsProvider.createDatabaseConnector();
        databaseConnector.init(databaseConfig.convertToMap());
        boolean dbStructure = databaseConnector.checkStructure(DB_CHANGELOG_FILE, DatabaseSettings.class.getClassLoader());
        if (!dbStructure) {
            throw new RuntimeException("Database structure is not correct.");
        }
    }

    @Inject
    public ConfigConnectionsValidator(DatabaseUrlParsingManager databaseUrlParsingManager, CorePluginsProvider corePluginsProvider) {
        this.databaseUrlParsingManager = databaseUrlParsingManager;
        this.corePluginsProvider = corePluginsProvider;
    }
}

