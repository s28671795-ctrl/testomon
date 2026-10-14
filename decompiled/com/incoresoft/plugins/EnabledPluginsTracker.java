/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import com.incoresoft.Utils.StringUtils;
import com.incoresoft.database.EnabledPluginsRepository;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.plugins.models.EnabledPluginInfo;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnabledPluginsTracker {
    private static final Logger log = LoggerFactory.getLogger(EnabledPluginsTracker.class);
    private final Map<String, EnabledPluginInfo> enabledPluginInfoMap = new ConcurrentHashMap<String, EnabledPluginInfo>();
    private final EnabledPluginsRepository enabledPluginsRepository;
    private final Path configFilePath;

    public EnabledPluginsTracker(EnabledPluginsRepository enabledPluginsRepository, String configFileName) {
        this.enabledPluginsRepository = enabledPluginsRepository;
        this.configFilePath = Path.of(configFileName, new String[0]);
        this.migrateFromFileToDbStorage();
        this.init();
    }

    private void migrateFromFileToDbStorage() {
        try {
            if (!Files.exists(this.configFilePath, new LinkOption[0])) {
                log.warn("Config file \"{}\" does not exist", (Object)this.configFilePath);
                return;
            }
            String config = Files.readString(this.configFilePath);
            if (StringUtils.isBlank(config)) {
                log.warn("Config file (\"{}\") contents are blank", (Object)this.configFilePath);
                return;
            }
            Map<String, EnabledPluginInfo> enabled = Arrays.stream(Serialization.objectMapper().readValue(config, EnabledPluginInfo[].class)).collect(Collectors.toMap(EnabledPluginInfo::getPluginId, Function.identity()));
            this.enabledPluginsRepository.putAllIfEmpty(enabled);
        }
        catch (Exception e) {
            log.error("Couldn't load enabled plugin info from JSON file", e);
        }
    }

    private void init() {
        List<EnabledPluginInfo> values2 = this.enabledPluginsRepository.getAll();
        for (EnabledPluginInfo value : values2) {
            this.enabledPluginInfoMap.put(value.getPluginId(), value);
        }
    }

    public boolean enabled(String pluginId) {
        if (this.enabledPluginInfoMap.containsKey(pluginId)) {
            return this.enabledPluginInfoMap.get(pluginId).isEnabled();
        }
        return true;
    }

    public void setEnabled(String pluginId) {
        this.setEnabled(pluginId, true);
    }

    public void setDisabled(String pluginId) {
        this.setEnabled(pluginId, false);
    }

    private void setEnabled(String pluginId, boolean enabled) {
        if (!this.enabledPluginInfoMap.containsKey(pluginId)) {
            this.enabledPluginInfoMap.put(pluginId, new EnabledPluginInfo(pluginId, enabled));
        }
        EnabledPluginInfo plugin = this.enabledPluginInfoMap.get(pluginId);
        plugin.setEnabled(enabled);
        this.enabledPluginsRepository.setEnabled(pluginId, enabled);
    }
}

