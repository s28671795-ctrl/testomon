/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.backup;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.zafarkhaja.semver.Version;
import com.incoresoft.vezha.backup.BackupType;
import com.incoresoft.vezha.backup.file.BackupFileRestoreInfo;
import com.incoresoft.vezha.dto.backup.BackupRequiredPluginInfoDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

@Schema(name="BackupFileRestoreInfoDTO", description="Backup file restore info")
public class BackupFileRestoreInfoDTO {
    @JSONField(name="file_name")
    @Schema(name="file_name", description="Name of backup file", example="backup1")
    private String fileName;
    @JSONField(name="description")
    @Schema(name="description", description="Description of backup file", example="backup1 desc")
    private String description;
    @JSONField(name="created_at")
    @Schema(name="created_at", description="Backup creation time", example="1627982400000", implementation=Long.class)
    private Instant createdAt;
    @JSONField(name="type")
    @Schema(name="type", description="Backup type", implementation=BackupType.class)
    private BackupType type;
    @JSONField(name="are_audit_logs_included")
    @Schema(name="are_audit_logs_included", description="Are audit logs included", example="false")
    private boolean areAuditLogsIncluded;
    @JSONField(name="core_version")
    @Schema(name="core_version", description="Core version", example="1.0.0")
    private Version coreVersion;
    @JSONField(name="size")
    @Schema(name="size", description="Size of backup file", example="1000", implementation=Long.class)
    private long size;
    @JSONField(name="hash")
    @Schema(name="hash", description="Backup hash", example="134sdvwr33", implementation=String.class)
    private String hash;
    @JSONField(name="driver_plugins")
    @ArraySchema(schema=@Schema(name="driver_plugins", description="Driver plugins required infos", implementation=BackupRequiredPluginInfoDTO.class))
    private List<BackupRequiredPluginInfoDTO> driverPlugins;
    @JSONField(name="vezha_plugins")
    @ArraySchema(schema=@Schema(name="vezha_plugins", description="Vezha plugins required infos", implementation=BackupRequiredPluginInfoDTO.class))
    private List<BackupRequiredPluginInfoDTO> vezhaPlugins;

    public BackupFileRestoreInfoDTO(BackupFileRestoreInfo backupFileRestoreInfo) {
        this.fileName = backupFileRestoreInfo.getFileName();
        this.description = backupFileRestoreInfo.getDescription();
        this.createdAt = backupFileRestoreInfo.getCreatedAt();
        this.type = backupFileRestoreInfo.getType();
        this.areAuditLogsIncluded = backupFileRestoreInfo.isAreAuditLogsIncluded();
        this.coreVersion = backupFileRestoreInfo.getCoreVersion();
        this.size = backupFileRestoreInfo.getSize();
        this.hash = backupFileRestoreInfo.getHash();
        this.driverPlugins = backupFileRestoreInfo.getEventManagerPlugins().stream().map(BackupRequiredPluginInfoDTO::new).toList();
        this.vezhaPlugins = backupFileRestoreInfo.getAnalyticsPlugins().stream().map(BackupRequiredPluginInfoDTO::new).toList();
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

    public List<BackupRequiredPluginInfoDTO> getDriverPlugins() {
        return this.driverPlugins;
    }

    public List<BackupRequiredPluginInfoDTO> getVezhaPlugins() {
        return this.vezhaPlugins;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setType(BackupType type) {
        this.type = type;
    }

    public void setAreAuditLogsIncluded(boolean areAuditLogsIncluded) {
        this.areAuditLogsIncluded = areAuditLogsIncluded;
    }

    public void setCoreVersion(Version coreVersion) {
        this.coreVersion = coreVersion;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setDriverPlugins(List<BackupRequiredPluginInfoDTO> driverPlugins) {
        this.driverPlugins = driverPlugins;
    }

    public void setVezhaPlugins(List<BackupRequiredPluginInfoDTO> vezhaPlugins) {
        this.vezhaPlugins = vezhaPlugins;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BackupFileRestoreInfoDTO)) {
            return false;
        }
        BackupFileRestoreInfoDTO other = (BackupFileRestoreInfoDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
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
        List<BackupRequiredPluginInfoDTO> this$driverPlugins = this.getDriverPlugins();
        List<BackupRequiredPluginInfoDTO> other$driverPlugins = other.getDriverPlugins();
        if (this$driverPlugins == null ? other$driverPlugins != null : !((Object)this$driverPlugins).equals(other$driverPlugins)) {
            return false;
        }
        List<BackupRequiredPluginInfoDTO> this$vezhaPlugins = this.getVezhaPlugins();
        List<BackupRequiredPluginInfoDTO> other$vezhaPlugins = other.getVezhaPlugins();
        return !(this$vezhaPlugins == null ? other$vezhaPlugins != null : !((Object)this$vezhaPlugins).equals(other$vezhaPlugins));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BackupFileRestoreInfoDTO;
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
        List<BackupRequiredPluginInfoDTO> $driverPlugins = this.getDriverPlugins();
        result = result * 59 + ($driverPlugins == null ? 43 : ((Object)$driverPlugins).hashCode());
        List<BackupRequiredPluginInfoDTO> $vezhaPlugins = this.getVezhaPlugins();
        result = result * 59 + ($vezhaPlugins == null ? 43 : ((Object)$vezhaPlugins).hashCode());
        return result;
    }

    public String toString() {
        return "BackupFileRestoreInfoDTO(fileName=" + this.getFileName() + ", description=" + this.getDescription() + ", createdAt=" + String.valueOf(this.getCreatedAt()) + ", type=" + String.valueOf((Object)this.getType()) + ", areAuditLogsIncluded=" + this.isAreAuditLogsIncluded() + ", coreVersion=" + String.valueOf(this.getCoreVersion()) + ", size=" + this.getSize() + ", hash=" + this.getHash() + ", driverPlugins=" + String.valueOf(this.getDriverPlugins()) + ", vezhaPlugins=" + String.valueOf(this.getVezhaPlugins()) + ")";
    }
}

