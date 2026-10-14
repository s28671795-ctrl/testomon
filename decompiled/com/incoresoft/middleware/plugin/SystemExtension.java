/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.plugin.InitializableExtension;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jooq.Table;

public abstract class SystemExtension<T>
implements InitializableExtension<T> {
    public abstract String getPluginName();

    public List<String> getFrontComponents() {
        return this.readJSONArray("public/component.json");
    }

    public JSONObject getDevParams() {
        return Optional.ofNullable(this.getClass().getClassLoader().getResourceAsStream("public/dev.json")).map(is -> {
            try {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).map(JSON::parseObject).orElse(new JSONObject());
    }

    public JSONObject getSettings() {
        return Optional.ofNullable(this.getClass().getClassLoader().getResourceAsStream("public/settings.json")).map(is -> {
            try {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).map(JSON::parseObject).orElse(new JSONObject());
    }

    public List<String> getSidebarMenuGroups() {
        return this.readJSONArray("public/sidebar_menu.json");
    }

    public List<String> getSettingsMenuItems() {
        return this.readJSONArray("public/sidebar_settings_menu.json");
    }

    @NotNull
    private List<String> readJSONArray(String name) {
        return Optional.ofNullable(this.getClass().getClassLoader().getResourceAsStream(name)).map(is -> {
            try {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).map(it -> JSON.parseArray(it, String.class)).orElseGet(Collections::emptyList);
    }

    public Set<AuditEventAction> customAuditActions() {
        return Set.of();
    }

    public abstract Class<? extends AnalyticsModule> getModuleClass();

    public abstract List<Table<?>> getManagedTables();

    public List<Table<?>> getBackupIgnoredTables() {
        return List.of();
    }
}

