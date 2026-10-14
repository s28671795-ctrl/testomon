/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config.storage;

import com.incoresoft.config.models.StorageConfig;
import com.incoresoft.dto.config.storage.LocalStorageConfigDTO;
import com.incoresoft.dto.config.storage.S3StorageConfigDTO;
import com.incoresoft.dto.config.storage.SetupLocalStorageConfigDTO;
import com.incoresoft.dto.config.storage.SetupS3StorageConfigDTO;
import com.incoresoft.dto.config.storage.SetupStorageConfigDTO;
import com.incoresoft.dto.config.storage.StorageConfigDTO;
import java.io.File;
import java.nio.file.Path;
import org.jetbrains.annotations.Nullable;

public enum StorageType {
    S3(S3StorageConfigDTO.class, SetupS3StorageConfigDTO.class),
    LOCAL(LocalStorageConfigDTO.class, SetupLocalStorageConfigDTO.class),
    UNKNOWN(null, null);

    private final Class<? extends StorageConfigDTO> configDtoClass;
    private final Class<? extends SetupStorageConfigDTO> setupConfigDtoClass;

    public String toString() {
        return this.name().toLowerCase();
    }

    public static StorageType parse(String value) {
        return StorageType.valueOf(value.toUpperCase());
    }

    public static StorageType getByConfigDTOClass(Class<? extends StorageConfigDTO> messageClass) {
        for (StorageType type : StorageType.values()) {
            if (!type.getConfigDtoClass().equals(messageClass)) continue;
            return type;
        }
        return UNKNOWN;
    }

    public static StorageType getBySetupConfigDTOClass(Class<? extends SetupStorageConfigDTO> messageClass) {
        for (StorageType type : StorageType.values()) {
            if (!type.getSetupConfigDtoClass().equals(messageClass)) continue;
            return type;
        }
        return UNKNOWN;
    }

    public static StorageConfig buildStorageConfig(@Nullable SetupStorageConfigDTO setupDTO) {
        if (setupDTO == null || setupDTO instanceof SetupLocalStorageConfigDTO) {
            return new StorageConfig(new LocalStorageConfigDTO(String.valueOf(Path.of("../storage", new String[0]).toAbsolutePath().normalize()) + File.separator));
        }
        SetupS3StorageConfigDTO s3ConfigDTO = (SetupS3StorageConfigDTO)setupDTO;
        String url2 = String.format("http://%s:%d", s3ConfigDTO.getHost(), s3ConfigDTO.getPort());
        return new StorageConfig(new S3StorageConfigDTO(url2, s3ConfigDTO.getAccessKey(), s3ConfigDTO.getSecretKey(), s3ConfigDTO.getBucketName()));
    }

    public Class<? extends StorageConfigDTO> getConfigDtoClass() {
        return this.configDtoClass;
    }

    public Class<? extends SetupStorageConfigDTO> getSetupConfigDtoClass() {
        return this.setupConfigDtoClass;
    }

    private StorageType(Class<? extends StorageConfigDTO> configDtoClass, Class<? extends SetupStorageConfigDTO> setupConfigDtoClass) {
        this.configDtoClass = configDtoClass;
        this.setupConfigDtoClass = setupConfigDtoClass;
    }
}

