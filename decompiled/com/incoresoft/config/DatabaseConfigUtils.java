/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.config;

import com.incoresoft.config.models.DatabaseConfig;
import com.incoresoft.database.settings.DatabaseUrlComponents;
import com.incoresoft.database.settings.DatabaseUrlParsingManager;
import com.incoresoft.dto.config.SetupDatabaseConfigDTO;

public class DatabaseConfigUtils {
    public static final String SQL_SERVER_DEFAULT_SCHEMA = "dbo";
    public static final String H2_DEFAULT_SCHEMA = "PUBLIC";

    public static DatabaseConfig buildDatabaseConfig(SetupDatabaseConfigDTO setupDTO, DatabaseUrlParsingManager databaseUrlParsingManager) {
        DatabaseUrlComponents components = new DatabaseUrlComponents(setupDTO);
        String url2 = databaseUrlParsingManager.parseUrlComponents(components);
        return new DatabaseConfig(url2, setupDTO.getSchemaName(), setupDTO.getLogin(), setupDTO.getPassword(), true);
    }
}

