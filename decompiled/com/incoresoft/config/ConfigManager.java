/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.config;

import com.google.inject.Inject;
import com.incoresoft.config.ConfigConnectionsValidator;
import com.incoresoft.config.models.DatabaseConfig;
import com.incoresoft.config.models.StorageConfig;
import com.incoresoft.config.models.config.Config;
import com.incoresoft.crypto.Cipher;
import com.incoresoft.dto.config.ConfigDTO;
import com.incoresoft.middleware.http.serialization.Serialization;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ConfigManager<T extends Config> {
    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private volatile T config;
    private final Cipher cipher;
    private boolean debug = false;
    private static final String KEY = "N+V5W+aP82/D9KP8";
    private static final String SETTINGS_FILE_PATH = "config.json";
    private static final String temp = "temp/";
    private static final String images = "images";
    private static final String sounds = "sounds";
    private final Path customLogoPath = Paths.get("images", "custom_logo.png");
    private final Path customFaviconPath = Paths.get("images", "custom_favicon.ico");
    private final ConfigConnectionsValidator validator;

    @Inject
    public ConfigManager(Cipher cipher, ConfigConnectionsValidator validator) {
        String configStr;
        this.cipher = cipher;
        this.validator = validator;
        File settingsFile = new File(SETTINGS_FILE_PATH);
        if (!settingsFile.exists()) {
            return;
        }
        try {
            configStr = Files.readString(settingsFile.toPath());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        ConfigDTO configDTO = Serialization.readValue(configStr, this.dtoClass());
        Object config = configDTO.toConfig();
        if (config.getDatabase().isEncrypted()) {
            this.decryptPassword((Config)config);
        }
        this.config = config;
        this.createDirectoryIfNotExists(Paths.get(images, new String[0]));
        this.createDirectoryIfNotExists(Paths.get(sounds, new String[0]));
        this.createTemp();
    }

    private void createTemp() {
        File file = new File(temp);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public void updateSecondStart(boolean isSecondStart) {
        this.update(this.config.withSecondStart(isSecondStart));
    }

    private void createDirectoryIfNotExists(Path directoryPath) {
        if (!Files.exists(directoryPath, new LinkOption[0])) {
            try {
                Files.createDirectories(directoryPath, new FileAttribute[0]);
            }
            catch (IOException e) {
                log.error("Exception trying to create directory");
            }
        }
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
        if (debug) {
            log.debug("DEBUG_MODE:ON");
        }
    }

    public boolean isDebugMode() {
        return this.debug;
    }

    public void update(T config) {
        this.validator.testAllConnections((Config)config);
        if (config.getDatabase().isEncrypted()) {
            this.encryptPassword((Config)config);
        }
        try {
            Files.writeString(Paths.get(SETTINGS_FILE_PATH, new String[0]), (CharSequence)Serialization.writeValueAsPrettyString(config.toDTO()), new OpenOption[0]);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (config.getDatabase().isEncrypted()) {
            this.decryptPassword((Config)config);
        }
        this.config = config;
    }

    private void encryptPassword(Config config) {
        DatabaseConfig databaseConfig = config.getDatabase();
        databaseConfig.setPassword(this.cipher.encrypt(KEY, databaseConfig.getPassword()));
    }

    private void decryptPassword(Config config) {
        DatabaseConfig databaseConfig = config.getDatabase();
        databaseConfig.setPassword(this.cipher.decrypt(KEY, databaseConfig.getPassword()));
    }

    public boolean isConfigured() {
        return this.config != null;
    }

    public StorageConfig getStorage() {
        return this.config.getStorage();
    }

    public DatabaseConfig getDatabase() {
        return this.config.getDatabase();
    }

    public String getMarketplace() {
        return this.config.getMarketplaceServer();
    }

    public String getJettyMultipartFilesCacheDirectory() {
        return this.config.getJettyMultipartFilesCacheDirectory();
    }

    public abstract Class<? extends ConfigDTO> dtoClass();

    public T getConfig() {
        return this.config;
    }

    public Path getCustomLogoPath() {
        return this.customLogoPath;
    }

    public Path getCustomFaviconPath() {
        return this.customFaviconPath;
    }
}

