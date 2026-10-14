/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.config.models.config;

import com.incoresoft.config.models.ConnectionConfig;
import com.incoresoft.config.models.DatabaseConfig;
import com.incoresoft.config.models.StorageConfig;
import com.incoresoft.dto.config.ConfigDTO;

public interface Config {
    public String getMarketplaceServer();

    public String getJettyMultipartFilesCacheDirectory();

    public DatabaseConfig getDatabase();

    public StorageConfig getStorage();

    public ConnectionConfig getConnectionConfig();

    @Deprecated
    public String getSessionStoreDirectory();

    public <T extends Config> T withSecondStart(boolean var1);

    public ConfigDTO toDTO();
}

