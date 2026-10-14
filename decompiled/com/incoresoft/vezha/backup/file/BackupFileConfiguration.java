/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.file;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.zafarkhaja.semver.Version;
import com.incoresoft.core.models.CoreService;
import com.incoresoft.plugins.models.InstalledPluginInfo;
import com.incoresoft.vezha.backup.BackupType;
import com.incoresoft.vezha.backup.file.BackupFilePluginInfo;
import com.incoresoft.vezha.backup.ws.message.create.client.CreateBackupMessage;
import java.util.List;

public class BackupFileConfiguration {
    @JSONField(name="core_version")
    private Version coreVersion;
    @JSONField(name="description")
    private String description;
    @JSONField(name="type")
    private BackupType type;
    @JSONField(name="are_audit_logs_included")
    private boolean areAuditLogsIncluded;
    @JSONField(name="event_manager_plugins")
    private List<BackupFilePluginInfo> eventManagerPlugins;
    @JSONField(name="analytics_plugins")
    private List<BackupFilePluginInfo> analyticsPlugins;
    @JSONField(name="core_service_id")
    private String coreServiceId;
    @JSONField(name="is_vms_integration_enabled")
    private boolean isVmsIntegrationEnabled;

    public BackupFileConfiguration(CreateBackupMessage message, Version coreVersion, List<InstalledPluginInfo> eventManagerPlugins, List<InstalledPluginInfo> analyticsPlugins, CoreService coreService, boolean isVmsIntegrationEnabled) {
        this.coreVersion = coreVersion;
        this.description = message.getDescription();
        this.type = BackupType.manual;
        this.areAuditLogsIncluded = message.isIncludeAuditLogs();
        this.eventManagerPlugins = eventManagerPlugins.stream().map(BackupFilePluginInfo::new).toList();
        this.analyticsPlugins = analyticsPlugins.stream().map(BackupFilePluginInfo::new).toList();
        this.coreServiceId = coreService.getId();
        this.isVmsIntegrationEnabled = isVmsIntegrationEnabled;
    }

    public Version getCoreVersion() {
        return this.coreVersion;
    }

    public String getDescription() {
        return this.description;
    }

    public BackupType getType() {
        return this.type;
    }

    public boolean isAreAuditLogsIncluded() {
        return this.areAuditLogsIncluded;
    }

    public List<BackupFilePluginInfo> getEventManagerPlugins() {
        return this.eventManagerPlugins;
    }

    public List<BackupFilePluginInfo> getAnalyticsPlugins() {
        return this.analyticsPlugins;
    }

    public String getCoreServiceId() {
        return this.coreServiceId;
    }

    public boolean isVmsIntegrationEnabled() {
        return this.isVmsIntegrationEnabled;
    }

    public void setCoreVersion(Version coreVersion) {
        this.coreVersion = coreVersion;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(BackupType type) {
        this.type = type;
    }

    public void setAreAuditLogsIncluded(boolean areAuditLogsIncluded) {
        this.areAuditLogsIncluded = areAuditLogsIncluded;
    }

    public void setEventManagerPlugins(List<BackupFilePluginInfo> eventManagerPlugins) {
        this.eventManagerPlugins = eventManagerPlugins;
    }

    public void setAnalyticsPlugins(List<BackupFilePluginInfo> analyticsPlugins) {
        this.analyticsPlugins = analyticsPlugins;
    }

    public void setCoreServiceId(String coreServiceId) {
        this.coreServiceId = coreServiceId;
    }

    public void setVmsIntegrationEnabled(boolean isVmsIntegrationEnabled) {
        this.isVmsIntegrationEnabled = isVmsIntegrationEnabled;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BackupFileConfiguration)) {
            return false;
        }
        BackupFileConfiguration other = (BackupFileConfiguration)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isAreAuditLogsIncluded() != other.isAreAuditLogsIncluded()) {
            return false;
        }
        if (this.isVmsIntegrationEnabled() != other.isVmsIntegrationEnabled()) {
            return false;
        }
        Version this$coreVersion = this.getCoreVersion();
        Version other$coreVersion = other.getCoreVersion();
        if (this$coreVersion == null ? other$coreVersion != null : !((Object)this$coreVersion).equals(other$coreVersion)) {
            return false;
        }
        String this$description = this.getDescription();
        String other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        BackupType this$type = this.getType();
        BackupType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        List<BackupFilePluginInfo> this$eventManagerPlugins = this.getEventManagerPlugins();
        List<BackupFilePluginInfo> other$eventManagerPlugins = other.getEventManagerPlugins();
        if (this$eventManagerPlugins == null ? other$eventManagerPlugins != null : !((Object)this$eventManagerPlugins).equals(other$eventManagerPlugins)) {
            return false;
        }
        List<BackupFilePluginInfo> this$analyticsPlugins = this.getAnalyticsPlugins();
        List<BackupFilePluginInfo> other$analyticsPlugins = other.getAnalyticsPlugins();
        if (this$analyticsPlugins == null ? other$analyticsPlugins != null : !((Object)this$analyticsPlugins).equals(other$analyticsPlugins)) {
            return false;
        }
        String this$coreServiceId = this.getCoreServiceId();
        String other$coreServiceId = other.getCoreServiceId();
        return !(this$coreServiceId == null ? other$coreServiceId != null : !this$coreServiceId.equals(other$coreServiceId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BackupFileConfiguration;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isAreAuditLogsIncluded() ? 79 : 97);
        result = result * 59 + (this.isVmsIntegrationEnabled() ? 79 : 97);
        Version $coreVersion = this.getCoreVersion();
        result = result * 59 + ($coreVersion == null ? 43 : ((Object)$coreVersion).hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        BackupType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        List<BackupFilePluginInfo> $eventManagerPlugins = this.getEventManagerPlugins();
        result = result * 59 + ($eventManagerPlugins == null ? 43 : ((Object)$eventManagerPlugins).hashCode());
        List<BackupFilePluginInfo> $analyticsPlugins = this.getAnalyticsPlugins();
        result = result * 59 + ($analyticsPlugins == null ? 43 : ((Object)$analyticsPlugins).hashCode());
        String $coreServiceId = this.getCoreServiceId();
        result = result * 59 + ($coreServiceId == null ? 43 : $coreServiceId.hashCode());
        return result;
    }

    public String toString() {
        return "BackupFileConfiguration(coreVersion=" + String.valueOf(this.getCoreVersion()) + ", description=" + this.getDescription() + ", type=" + String.valueOf((Object)this.getType()) + ", areAuditLogsIncluded=" + this.isAreAuditLogsIncluded() + ", eventManagerPlugins=" + String.valueOf(this.getEventManagerPlugins()) + ", analyticsPlugins=" + String.valueOf(this.getAnalyticsPlugins()) + ", coreServiceId=" + this.getCoreServiceId() + ", isVmsIntegrationEnabled=" + this.isVmsIntegrationEnabled() + ")";
    }

    public BackupFileConfiguration() {
    }
}

