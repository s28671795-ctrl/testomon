/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.middleware.model.Model;
import com.incoresoft.vezha.backup.BackupType;
import com.incoresoft.vezha.backup.file.BackupFileInfo;
import com.incoresoft.vezha.backup.ws.message.create.client.CreateBackupMessage;
import java.time.Instant;

public final class BackupInfo
implements Model {
    private final int id;
    private final String fileName;
    private final String description;
    private final Version coreVersion;
    private final BackupType type;
    private final Instant createdAt;
    private final long size;
    private final boolean isVmsIntegrationEnabled;
    private final int clientId;

    public BackupInfo(CreateBackupMessage createBackupMessage, BackupFileInfo backupFileInfo) {
        this(0, backupFileInfo.getFileName(), createBackupMessage.getDescription(), backupFileInfo.getCoreVersion(), BackupType.manual, backupFileInfo.getCreatedAt(), backupFileInfo.getSize(), backupFileInfo.isVmsIntegrationEnabled(), 0);
    }

    @Override
    public int getId() {
        return this.id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getDescription() {
        return this.description;
    }

    public Version getCoreVersion() {
        return this.coreVersion;
    }

    public BackupType getType() {
        return this.type;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public long getSize() {
        return this.size;
    }

    public boolean isVmsIntegrationEnabled() {
        return this.isVmsIntegrationEnabled;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BackupInfo)) {
            return false;
        }
        BackupInfo other = (BackupInfo)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getSize() != other.getSize()) {
            return false;
        }
        if (this.isVmsIntegrationEnabled() != other.isVmsIntegrationEnabled()) {
            return false;
        }
        if (this.getClientId() != other.getClientId()) {
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
        Version this$coreVersion = this.getCoreVersion();
        Version other$coreVersion = other.getCoreVersion();
        if (this$coreVersion == null ? other$coreVersion != null : !((Object)this$coreVersion).equals(other$coreVersion)) {
            return false;
        }
        BackupType this$type = this.getType();
        BackupType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        Instant this$createdAt = this.getCreatedAt();
        Instant other$createdAt = other.getCreatedAt();
        return !(this$createdAt == null ? other$createdAt != null : !((Object)this$createdAt).equals(other$createdAt));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        long $size = this.getSize();
        result = result * 59 + (int)($size >>> 32 ^ $size);
        result = result * 59 + (this.isVmsIntegrationEnabled() ? 79 : 97);
        result = result * 59 + this.getClientId();
        String $fileName = this.getFileName();
        result = result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Version $coreVersion = this.getCoreVersion();
        result = result * 59 + ($coreVersion == null ? 43 : ((Object)$coreVersion).hashCode());
        BackupType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        Instant $createdAt = this.getCreatedAt();
        result = result * 59 + ($createdAt == null ? 43 : ((Object)$createdAt).hashCode());
        return result;
    }

    public String toString() {
        return "BackupInfo(id=" + this.getId() + ", fileName=" + this.getFileName() + ", description=" + this.getDescription() + ", coreVersion=" + String.valueOf(this.getCoreVersion()) + ", type=" + String.valueOf((Object)this.getType()) + ", createdAt=" + String.valueOf(this.getCreatedAt()) + ", size=" + this.getSize() + ", isVmsIntegrationEnabled=" + this.isVmsIntegrationEnabled() + ", clientId=" + this.getClientId() + ")";
    }

    public BackupInfo(int id, String fileName, String description, Version coreVersion, BackupType type, Instant createdAt, long size, boolean isVmsIntegrationEnabled, int clientId) {
        this.id = id;
        this.fileName = fileName;
        this.description = description;
        this.coreVersion = coreVersion;
        this.type = type;
        this.createdAt = createdAt;
        this.size = size;
        this.isVmsIntegrationEnabled = isVmsIntegrationEnabled;
        this.clientId = clientId;
    }
}

