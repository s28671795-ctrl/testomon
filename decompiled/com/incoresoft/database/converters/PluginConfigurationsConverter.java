/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database.converters;

import com.incoresoft.generated.tables.records.PluginConfigurationsRecord;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.plugin.commons.database.CRUDConverter;
import com.incoresoft.plugins.configurations.PluginConfiguration;
import org.jetbrains.annotations.NotNull;

public class PluginConfigurationsConverter
implements CRUDConverter<PluginConfigurationsRecord, PluginConfiguration> {
    @Override
    public PluginConfiguration from(PluginConfigurationsRecord databaseObject) {
        return new PluginConfiguration(databaseObject.getId(), databaseObject.getPluginName(), databaseObject.getPluginId(), Serialization.readAsList(databaseObject.getManagedTables(), String.class), Serialization.readAsList(databaseObject.getBackupIgnoredTables(), String.class), databaseObject.getClientId());
    }

    @Override
    public PluginConfigurationsRecord to(PluginConfiguration userObject) {
        return new PluginConfigurationsRecord(userObject.getId(), userObject.getPluginName(), Serialization.writeValueAsString(userObject.getBackupIgnoredTables()), Serialization.writeValueAsString(userObject.getManagedTables()), userObject.getClientId(), userObject.getPluginId());
    }

    @Override
    @NotNull
    public Class<PluginConfigurationsRecord> fromType() {
        return PluginConfigurationsRecord.class;
    }

    @Override
    @NotNull
    public Class<PluginConfiguration> toType() {
        return PluginConfiguration.class;
    }
}

