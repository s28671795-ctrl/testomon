/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.file;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.vezha.backup.BackupType;
import com.incoresoft.vezha.backup.file.BackupFileConfiguration;
import com.incoresoft.vezha.backup.file.BackupFilePluginInfo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.List;

public final class BackupFileInfo {
    private final String fileName;
    private final long size;
    private final String hash;
    private final Instant createdAt;
    private final Version coreVersion;
    private final String description;
    private final BackupType type;
    private final boolean areAuditLogsIncluded;
    private final List<BackupFilePluginInfo> analyticsPlugins;
    private final List<BackupFilePluginInfo> eventManagerPlugins;
    private final boolean isVmsIntegrationEnabled;

    public BackupFileInfo(BackupFileConfiguration configuration, String hash, File zipFile) throws IOException {
        this.fileName = zipFile.getName();
        this.size = zipFile.length();
        this.hash = hash;
        this.createdAt = Files.readAttributes(zipFile.toPath(), BasicFileAttributes.class, new LinkOption[0]).creationTime().toInstant();
        this.coreVersion = configuration.getCoreVersion();
        this.description = configuration.getDescription();
        this.type = configuration.getType();
        this.areAuditLogsIncluded = configuration.isAreAuditLogsIncluded();
        this.analyticsPlugins = configuration.getAnalyticsPlugins();
        this.eventManagerPlugins = configuration.getEventManagerPlugins();
        this.isVmsIntegrationEnabled = configuration.isVmsIntegrationEnabled();
    }

    public String getFileName() {
        return this.fileName;
    }

    public long getSize() {
        return this.size;
    }

    public String getHash() {
        return this.hash;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
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

    public List<BackupFilePluginInfo> getAnalyticsPlugins() {
        return this.analyticsPlugins;
    }

    public List<BackupFilePluginInfo> getEventManagerPlugins() {
        return this.eventManagerPlugins;
    }

    public boolean isVmsIntegrationEnabled() {
        return this.isVmsIntegrationEnabled;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BackupFileInfo)) {
            return false;
        }
        BackupFileInfo other = (BackupFileInfo)o;
        if (this.getSize() != other.getSize()) {
            return false;
        }
        if (this.isAreAuditLogsIncluded() != other.isAreAuditLogsIncluded()) {
            return false;
        }
        if (this.isVmsIntegrationEnabled() != other.isVmsIntegrationEnabled()) {
            return false;
        }
        String this$fileName = this.getFileName();
        String other$fileName = other.getFileName();
        if (this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName)) {
            return false;
        }
        String this$hash = this.getHash();
        String other$hash = other.getHash();
        if (this$hash == null ? other$hash != null : !this$hash.equals(other$hash)) {
            return false;
        }
        Instant this$createdAt = this.getCreatedAt();
        Instant other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !((Object)this$createdAt).equals(other$createdAt)) {
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
        List<BackupFilePluginInfo> this$analyticsPlugins = this.getAnalyticsPlugins();
        List<BackupFilePluginInfo> other$analyticsPlugins = other.getAnalyticsPlugins();
        if (this$analyticsPlugins == null ? other$analyticsPlugins != null : !((Object)this$analyticsPlugins).equals(other$analyticsPlugins)) {
            return false;
        }
        List<BackupFilePluginInfo> this$eventManagerPlugins = this.getEventManagerPlugins();
        List<BackupFilePluginInfo> other$eventManagerPlugins = other.getEventManagerPlugins();
        return !(this$eventManagerPlugins == null ? other$eventManagerPlugins != null : !((Object)this$eventManagerPlugins).equals(other$eventManagerPlugins));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $size = this.getSize();
        result = result * 59 + (int)($size >>> 32 ^ $size);
        result = result * 59 + (this.isAreAuditLogsIncluded() ? 79 : 97);
        result = result * 59 + (this.isVmsIntegrationEnabled() ? 79 : 97);
        String $fileName = this.getFileName();
        result = result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
        String $hash = this.getHash();
        result = result * 59 + ($hash == null ? 43 : $hash.hashCode());
        Instant $createdAt = this.getCreatedAt();
        result = result * 59 + ($createdAt == null ? 43 : ((Object)$createdAt).hashCode());
        Version $coreVersion = this.getCoreVersion();
        result = result * 59 + ($coreVersion == null ? 43 : ((Object)$coreVersion).hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        BackupType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        List<BackupFilePluginInfo> $analyticsPlugins = this.getAnalyticsPlugins();
        result = result * 59 + ($analyticsPlugins == null ? 43 : ((Object)$analyticsPlugins).hashCode());
        List<BackupFilePluginInfo> $eventManagerPlugins = this.getEventManagerPlugins();
        result = result * 59 + ($eventManagerPlugins == null ? 43 : ((Object)$eventManagerPlugins).hashCode());
        return result;
    }

    public String toString() {
        return "BackupFileInfo(fileName=" + this.getFileName() + ", size=" + this.getSize() + ", hash=" + this.getHash() + ", createdAt=" + String.valueOf(this.getCreatedAt()) + ", coreVersion=" + String.valueOf(this.getCoreVersion()) + ", description=" + this.getDescription() + ", type=" + String.valueOf((Object)this.getType()) + ", areAuditLogsIncluded=" + this.isAreAuditLogsIncluded() + ", analyticsPlugins=" + String.valueOf(this.getAnalyticsPlugins()) + ", eventManagerPlugins=" + String.valueOf(this.getEventManagerPlugins()) + ", isVmsIntegrationEnabled=" + this.isVmsIntegrationEnabled() + ")";
    }
}

