/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.configurations;

import com.incoresoft.middleware.model.Model;
import com.incoresoft.middleware.plugin.SystemExtension;
import java.util.List;
import org.jooq.Named;
import org.jooq.Table;

public final class PluginConfiguration
implements Model {
    private final int id;
    private final String pluginName;
    private final String pluginId;
    private final List<String> managedTables;
    private final List<String> backupIgnoredTables;
    private final int clientId;

    public PluginConfiguration(SystemExtension<?> extension, String pluginId) {
        this.id = 0;
        this.pluginName = extension.getPluginName().toLowerCase();
        this.pluginId = pluginId;
        this.clientId = 0;
        this.managedTables = PluginConfiguration.getTableNames(extension.getManagedTables());
        this.backupIgnoredTables = PluginConfiguration.getTableNames(extension.getBackupIgnoredTables()).stream().filter(this.managedTables::contains).toList();
    }

    private static List<String> getTableNames(List<Table<?>> tables) {
        if (tables == null) {
            return List.of();
        }
        return tables.stream().map(Named::getName).map(String::toLowerCase).toList();
    }

    @Override
    public int getId() {
        return this.id;
    }

    public String getPluginName() {
        return this.pluginName;
    }

    public String getPluginId() {
        return this.pluginId;
    }

    public List<String> getManagedTables() {
        return this.managedTables;
    }

    public List<String> getBackupIgnoredTables() {
        return this.backupIgnoredTables;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PluginConfiguration)) {
            return false;
        }
        PluginConfiguration other = (PluginConfiguration)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getClientId() != other.getClientId()) {
            return false;
        }
        String this$pluginName = this.getPluginName();
        String other$pluginName = other.getPluginName();
        if (this$pluginName == null ? other$pluginName != null : !this$pluginName.equals(other$pluginName)) {
            return false;
        }
        String this$pluginId = this.getPluginId();
        String other$pluginId = other.getPluginId();
        if (this$pluginId == null ? other$pluginId != null : !this$pluginId.equals(other$pluginId)) {
            return false;
        }
        List<String> this$managedTables = this.getManagedTables();
        List<String> other$managedTables = other.getManagedTables();
        if (this$managedTables == null ? other$managedTables != null : !((Object)this$managedTables).equals(other$managedTables)) {
            return false;
        }
        List<String> this$backupIgnoredTables = this.getBackupIgnoredTables();
        List<String> other$backupIgnoredTables = other.getBackupIgnoredTables();
        return !(this$backupIgnoredTables == null ? other$backupIgnoredTables != null : !((Object)this$backupIgnoredTables).equals(other$backupIgnoredTables));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        result = result * 59 + this.getClientId();
        String $pluginName = this.getPluginName();
        result = result * 59 + ($pluginName == null ? 43 : $pluginName.hashCode());
        String $pluginId = this.getPluginId();
        result = result * 59 + ($pluginId == null ? 43 : $pluginId.hashCode());
        List<String> $managedTables = this.getManagedTables();
        result = result * 59 + ($managedTables == null ? 43 : ((Object)$managedTables).hashCode());
        List<String> $backupIgnoredTables = this.getBackupIgnoredTables();
        result = result * 59 + ($backupIgnoredTables == null ? 43 : ((Object)$backupIgnoredTables).hashCode());
        return result;
    }

    public String toString() {
        return "PluginConfiguration(id=" + this.getId() + ", pluginName=" + this.getPluginName() + ", pluginId=" + this.getPluginId() + ", managedTables=" + String.valueOf(this.getManagedTables()) + ", backupIgnoredTables=" + String.valueOf(this.getBackupIgnoredTables()) + ", clientId=" + this.getClientId() + ")";
    }

    public PluginConfiguration withId(int id) {
        return this.id == id ? this : new PluginConfiguration(id, this.pluginName, this.pluginId, this.managedTables, this.backupIgnoredTables, this.clientId);
    }

    public PluginConfiguration withPluginName(String pluginName) {
        return this.pluginName == pluginName ? this : new PluginConfiguration(this.id, pluginName, this.pluginId, this.managedTables, this.backupIgnoredTables, this.clientId);
    }

    public PluginConfiguration withPluginId(String pluginId) {
        return this.pluginId == pluginId ? this : new PluginConfiguration(this.id, this.pluginName, pluginId, this.managedTables, this.backupIgnoredTables, this.clientId);
    }

    public PluginConfiguration withManagedTables(List<String> managedTables) {
        return this.managedTables == managedTables ? this : new PluginConfiguration(this.id, this.pluginName, this.pluginId, managedTables, this.backupIgnoredTables, this.clientId);
    }

    public PluginConfiguration withBackupIgnoredTables(List<String> backupIgnoredTables) {
        return this.backupIgnoredTables == backupIgnoredTables ? this : new PluginConfiguration(this.id, this.pluginName, this.pluginId, this.managedTables, backupIgnoredTables, this.clientId);
    }

    public PluginConfiguration withClientId(int clientId) {
        return this.clientId == clientId ? this : new PluginConfiguration(this.id, this.pluginName, this.pluginId, this.managedTables, this.backupIgnoredTables, clientId);
    }

    public PluginConfiguration(int id, String pluginName, String pluginId, List<String> managedTables, List<String> backupIgnoredTables, int clientId) {
        this.id = id;
        this.pluginName = pluginName;
        this.pluginId = pluginId;
        this.managedTables = managedTables;
        this.backupIgnoredTables = backupIgnoredTables;
        this.clientId = clientId;
    }
}

