/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config;

import com.incoresoft.config.models.config.Config;
import com.incoresoft.dto.config.DatabaseConfigDTO;
import com.incoresoft.dto.config.storage.StorageConfigDTO;

public interface ConfigDTO {
    public <T extends Config> T toConfig();

    public DatabaseConfigDTO getDatabase();

    public StorageConfigDTO getStorage();
}

