/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.config;

import com.google.inject.Inject;
import com.incoresoft.config.ConfigConnectionsValidator;
import com.incoresoft.config.ConfigManager;
import com.incoresoft.crypto.Cipher;
import com.incoresoft.dto.config.ConfigDTO;
import com.incoresoft.vezha.config.models.VezhaConfig;
import com.incoresoft.vezha.dto.config.VezhaConfigDTO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

public class VezhaConfigManager
extends ConfigManager<VezhaConfig> {
    @Inject
    public VezhaConfigManager(Cipher cipher, ConfigConnectionsValidator validator) {
        super(cipher, validator);
    }

    @Inject
    public void createStorageDirectoryIfNotExists() {
        if (this.getConfig() == null) {
            return;
        }
        if (((VezhaConfig)this.getConfig()).getStorage().localDirectory().isEmpty()) {
            return;
        }
        Path storagePath = Path.of(((VezhaConfig)this.getConfig()).getStorage().localDirectory().orElseThrow(), new String[0]);
        if (Files.exists(storagePath, new LinkOption[0])) {
            return;
        }
        try {
            Files.createDirectories(storagePath, new FileAttribute[0]);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<? extends ConfigDTO> dtoClass() {
        return VezhaConfigDTO.class;
    }

    public boolean isVmsIntegrationEnabled() {
        return ((VezhaConfig)this.getConfig()).isVmsIntegrationEnabled();
    }

    public void enableVmsIntegration() {
        VezhaConfig updated = ((VezhaConfig)this.getConfig()).withVmsIntegrationEnabled(true);
        this.update(updated);
    }

    public void disableVmsIntegration() {
        VezhaConfig updated = ((VezhaConfig)this.getConfig()).withVmsIntegrationEnabled(false);
        this.update(updated);
    }

    public void setMarketplaceUrl(String newMarketplaceUrl) {
        VezhaConfig updated = ((VezhaConfig)this.getConfig()).withMarketplaceServer(newMarketplaceUrl);
        this.update(updated);
    }
}

