/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database;

import com.google.inject.Inject;
import com.incoresoft.database.converters.PluginConfigurationsConverter;
import com.incoresoft.generated.Tables;
import com.incoresoft.generated.tables.records.PluginConfigurationsRecord;
import com.incoresoft.plugin.commons.database.CRUDRepository;
import com.incoresoft.plugins.configurations.PluginConfiguration;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;

public class PluginConfigurationsRepository
extends CRUDRepository<PluginConfigurationsRecord, PluginConfiguration> {
    @Inject
    public PluginConfigurationsRepository(@NotNull DSLContext context, @NotNull PluginConfigurationsConverter converter) {
        super(context, converter, Tables.PLUGIN_CONFIGURATIONS, Tables.PLUGIN_CONFIGURATIONS.ID, Tables.PLUGIN_CONFIGURATIONS.PLUGIN_NAME, Tables.PLUGIN_CONFIGURATIONS.CLIENT_ID);
    }

    public Optional<PluginConfiguration> getByPluginName(String pluginName) {
        return this.context.selectFrom(this.table).where(this.nameField.equalIgnoreCase(pluginName)).fetchOptional(this.converter);
    }
}

