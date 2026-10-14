/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.backup;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.zafarkhaja.semver.Version;
import com.incoresoft.vezha.backup.BackupInfo;
import com.incoresoft.vezha.backup.BackupType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

@Schema(name="BackupInfoDTO", description="Backup information class structure")
public record BackupInfoDTO(@Schema(name="id", description="Backup id", example="1", implementation=Integer.class) @JSONField(name="id") int id, @Schema(name="file_name", description="Backup file name", example="backup123", implementation=String.class) @JSONField(name="file_name") String fileName, @Schema(name="description", description="Backup description", example="Backup description", implementation=String.class) @JSONField(name="description") String description, @Schema(name="core_version", description="Version of VMS Management Server", example="1.0.0", implementation=String.class) @JSONField(name="core_version") Version coreVersion, @Schema(name="type", description="Backup type", example="manual", implementation=BackupType.class) @JSONField(name="type") BackupType type, @Schema(name="created_at", description="Backup creation date", example="1627982400000", implementation=Instant.class) @JSONField(name="created_at") Instant createdAt, @Schema(name="size", description="Backup size in bytes", example="1024", implementation=Long.class) @JSONField(name="size") long size, @Schema(name="is_vms_integration_enabled", description="Is VMS integration enabled", example="true", implementation=Boolean.class) @JSONField(name="is_vms_integration_enabled") boolean isVmsIntegrationEnabled) {
    public BackupInfoDTO(BackupInfo backupInfo) {
        this(backupInfo.getId(), backupInfo.getFileName(), backupInfo.getDescription(), backupInfo.getCoreVersion(), backupInfo.getType(), backupInfo.getCreatedAt(), backupInfo.getSize(), backupInfo.isVmsIntegrationEnabled());
    }
}

