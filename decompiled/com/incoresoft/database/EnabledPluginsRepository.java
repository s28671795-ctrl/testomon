/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database;

import com.google.inject.Inject;
import com.incoresoft.generated.Tables;
import com.incoresoft.generated.tables.records.EnabledPluginsRecord;
import com.incoresoft.plugins.models.EnabledPluginInfo;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;

public class EnabledPluginsRepository {
    private final DSLContext dslContext;

    @Inject
    public EnabledPluginsRepository(@NotNull DSLContext dslContext) {
        this.dslContext = Objects.requireNonNull(dslContext);
    }

    public List<EnabledPluginInfo> getAll() {
        return this.dslContext.selectFrom(Tables.ENABLED_PLUGINS).fetch().map(it -> new EnabledPluginInfo(it.getPluginId(), it.getEnabled())).stream().toList();
    }

    public void putAllIfEmpty(Map<String, EnabledPluginInfo> enabled) {
        this.dslContext.transaction(configuration -> {
            DSLContext dsl = configuration.dsl();
            if (dsl.fetchCount(Tables.ENABLED_PLUGINS) > 0) {
                return;
            }
            List<EnabledPluginsRecord> values2 = enabled.values().stream().map(it -> new EnabledPluginsRecord(it.getPluginId(), it.isEnabled())).toList();
            dsl.batchInsert(values2).execute();
        });
    }

    private boolean isNotEmpty() {
        return this.dslContext.fetchCount(Tables.ENABLED_PLUGINS) > 0;
    }

    public void setEnabled(String pluginId, boolean enabled) {
        this.dslContext.insertInto(Tables.ENABLED_PLUGINS).set(Tables.ENABLED_PLUGINS.PLUGIN_ID, pluginId).set(Tables.ENABLED_PLUGINS.ENABLED, enabled).onDuplicateKeyUpdate().set(Tables.ENABLED_PLUGINS.ENABLED, enabled).execute();
    }
}

