/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.settings;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.Inject;
import com.incoresoft.core.ApplicationReloadManager;
import com.incoresoft.vezha.config.VezhaConfigManager;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomSettingsManager {
    private static final Logger log = LoggerFactory.getLogger(CustomSettingsManager.class);
    public static final String SHOW_HELP_TOOLTIP_KEY = "show_help_tooltip";
    public static final String APP_TITLE = "title";
    public static final String MARKETPLACE_SERVER = "marketplace_server";
    private final Map<String, String> customSettings = new ConcurrentHashMap<String, String>();

    @Inject
    public void init(VezhaConfigManager vezhaConfigManager, ApplicationReloadManager reloadManager) {
        Path settingsFile = Path.of("custom_settings.json", new String[0]);
        if (Files.exists(settingsFile, new LinkOption[0])) {
            try {
                JSONObject customParamsJson = JSON.parseObject(Files.readString(settingsFile));
                customParamsJson.forEach((key, value) -> this.customSettings.put((String)key, value.toString()));
            }
            catch (Exception e) {
                log.error("failed to read custom_settings.json");
            }
        }
        this.customSettings.computeIfAbsent(SHOW_HELP_TOOLTIP_KEY, key -> String.valueOf(true));
        this.customSettings.computeIfAbsent(APP_TITLE, key -> "IncoreSoft");
        this.customSettings.computeIfAbsent(MARKETPLACE_SERVER, key -> null);
        CustomSettingsManager.updateMarketplaceUrl(this.getValue(MARKETPLACE_SERVER), vezhaConfigManager, reloadManager);
    }

    public String getValue(String key) {
        return this.customSettings.get(key);
    }

    private static void updateMarketplaceUrl(String customMarketplaceUrl, VezhaConfigManager vezhaConfigManager, ApplicationReloadManager reloadManager) {
        if (customMarketplaceUrl == null) {
            return;
        }
        if (Objects.equals(vezhaConfigManager.getMarketplace(), customMarketplaceUrl)) {
            return;
        }
        log.info("custom_settings.json provides different marketplace URL to one in config, config will now be overwritten with value from custom_settings.json");
        vezhaConfigManager.setMarketplaceUrl(customMarketplaceUrl);
        reloadManager.reload();
    }
}

