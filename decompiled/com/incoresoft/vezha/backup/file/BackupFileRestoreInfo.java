/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.file;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.vezha.backup.BackupRequiredPluginInfo;
import com.incoresoft.vezha.backup.BackupType;
import com.incoresoft.vezha.backup.file.BackupFileInfo;
import java.time.Instant;
import java.util.List;

public final class BackupFileRestoreInfo {
    private final String fileName;
    private final String description;
    private final Instant createdAt;
    private final BackupType type;
    private final boolean areAuditLogsIncluded;
    private final Version coreVersion;
    private final long size;
    private final String hash;
    private final List<BackupRequiredPluginInfo> eventManagerPlugins;
    private final List<BackupRequiredPluginInfo> analyticsPlugins;

    public BackupFileRestoreInfo(BackupFileInfo fileInfo, List<BackupRequiredPluginInfo> eventManagerPluginInfos, List<BackupRequiredPluginInfo> analyticsPluginInfos) {
        this.fileName = fileInfo.getFileName();
        this.description = fileInfo.getDescription();
        this.createdAt = fileInfo.getCreatedAt();
        this.type = fileInfo.getType();
        this.areAuditLogsIncluded = fileInfo.isAreAuditLogsIncluded();
        this.coreVersion = fileInfo.getCoreVersion();
        this.size = fileInfo.getSize();
        this.hash = fileInfo.getHash();
        this.eventManagerPlugins = eventManagerPluginInfos;
        this.analyticsPlugins = analyticsPluginInfos;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getDescription() {
        return this.description;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public BackupType getType() {
        return this.type;
    }

    public boolean isAreAuditLogsIncluded() {
        return this.areAuditLogsIncluded;
    }

    public Version getCoreVersion() {
        return this.coreVersion;
    }

    public long getSize() {
        return this.size;
    }

    public String getHash() {
        return this.hash;
    }

    public List<BackupRequiredPluginInfo> getEventManagerPlugins() {
        return this.eventManagerPlugins;
    }

    public List<BackupRequiredPluginInfo> getAnalyticsPlugins() {
        return this.analyticsPlugins;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BackupFileRestoreInfo)) {
            return false;
        }
        BackupFileRestoreInfo other = (BackupFileRestoreInfo)o;
        if (this.isAreAuditLogsIncluded() != other.isAreAuditLogsIncluded()) {
            return false;
        }
        if (this.getSize() != other.getSize()) {
            return false;
        }
        String this$fileName = this.getFileName();
        String other$fileName = other.getFileName();
        if (this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName)) {
            return false;
        }
        String this$description = this.getDescription();
        String other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        Instant this$createdAt = this.getCreatedAt();
        Instant other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !((Object)this$createdAt).equals(other$createdAt)) {
            return false;
        }
        BackupType this$type = this.getType();
        BackupType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        Version this$coreVersion = this.getCoreVersion();
        Version other$coreVersion = other.getCoreVersion();
        if (this$coreVersion == null ? other$coreVersion != null : !((Object)this$coreVersion).equals(other$coreVersion)) {
            return false;
        }
        String this$hash = this.getHash();
        String other$hash = other.getHash();
        if (this$hash == null ? other$hash != null : !this$hash.equals(other$hash)) {
            return false;
        }
        List<BackupRequiredPluginInfo> this$eventManagerPlugins = this.getEventManagerPlugins();
        List<BackupRequiredPluginInfo> other$eventManagerPlugins = other.getEventManagerPlugins();
        if (this$eventManagerPlugins == null ? other$eventManagerPlugins != null : !((Object)this$eventManagerPlugins).equals(other$eventManagerPlugins)) {
            return false;
        }
        List<BackupRequiredPluginInfo> this$analyticsPlugins = this.getAnalyticsPlugins();
        List<BackupRequiredPluginInfo> other$analyticsPlugins = other.getAnalyticsPlugins();
        return !(this$analyticsPlugins == null ? other$analyticsPlugins != null : !((Object)this$analyticsPlugins).equals(other$analyticsPlugins));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isAreAuditLogsIncluded() ? 79 : 97);
        long $size = this.getSize();
        result = result * 59 + (int)($size >>> 32 ^ $size);
        String $fileName = this.getFileName();
        result = result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Instant $createdAt = this.getCreatedAt();
        result = result * 59 + ($createdAt == null ? 43 : ((Object)$createdAt).hashCode());
        BackupType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        Version $coreVersion = this.getCoreVersion();
        result = result * 59 + ($coreVersion == null ? 43 : ((Object)$coreVersion).hashCode());
        String $hash = this.getHash();
        result = result * 59 + ($hash == null ? 43 : $hash.hashCode());
        List<BackupRequiredPluginInfo> $eventManagerPlugins = this.getEventManagerPlugins();
        result = result * 59 + ($eventManagerPlugins == null ? 43 : ((Object)$eventManagerPlugins).hashCode());
        List<BackupRequiredPluginInfo> $analyticsPlugins = this.getAnalyticsPlugins();
        result = result * 59 + ($analyticsPlugins == null ? 43 : ((Object)$analyticsPlugins).hashCode());
        return result;
    }

    public String toString() {
        return "BackupFileRestoreInfo(fileName=" + this.getFileName() + ", description=" + this.getDescription() + ", createdAt=" + String.valueOf(this.getCreatedAt()) + ", type=" + String.valueOf((Object)this.getType()) + ", areAuditLogsIncluded=" + this.isAreAuditLogsIncluded() + ", coreVersion=" + String.valueOf(this.getCoreVersion()) + ", size=" + this.getSize() + ", hash=" + this.getHash() + ", eventManagerPlugins=" + String.valueOf(this.getEventManagerPlugins()) + ", analyticsPlugins=" + String.valueOf(this.getAnalyticsPlugins()) + ")";
    }
}

