/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.backup;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.version.models.MinorVersion;
import com.incoresoft.vezha.backup.BackupRequiredPluginInfo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="BackupRequiredPluginInfoDTO", description="Backup required plugin info")
public class BackupRequiredPluginInfoDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Plugin id", example="vms-alpr-plugin")
    private String id;
    @JSONField(name="name")
    @Schema(name="name", description="Plugin name", example="plugin1")
    private String name;
    @JSONField(name="version")
    @Schema(name="version", description="Plugin version", example="1.0")
    private MinorVersion version;
    @JSONField(name="is_installed")
    @Schema(name="is_installed", description="Is plugin installed", example="true", implementation=Boolean.class)
    private boolean isInstalled;

    public BackupRequiredPluginInfoDTO(BackupRequiredPluginInfo pluginInfo) {
        this.id = pluginInfo.getId();
        this.name = pluginInfo.getName();
        this.version = pluginInfo.getVersion();
        this.isInstalled = pluginInfo.isInstalled();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public MinorVersion getVersion() {
        return this.version;
    }

    public boolean isInstalled() {
        return this.isInstalled;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(MinorVersion version) {
        this.version = version;
    }

    public void setInstalled(boolean isInstalled) {
        this.isInstalled = isInstalled;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BackupRequiredPluginInfoDTO)) {
            return false;
        }
        BackupRequiredPluginInfoDTO other = (BackupRequiredPluginInfoDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isInstalled() != other.isInstalled()) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        MinorVersion this$version = this.getVersion();
        MinorVersion other$version = other.getVersion();
        return !(this$version == null ? other$version != null : !this$version.equals(other$version));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BackupRequiredPluginInfoDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isInstalled() ? 79 : 97);
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        MinorVersion $version = this.getVersion();
        result = result * 59 + ($version == null ? 43 : $version.hashCode());
        return result;
    }

    public String toString() {
        return "BackupRequiredPluginInfoDTO(id=" + this.getId() + ", name=" + this.getName() + ", version=" + String.valueOf(this.getVersion()) + ", isInstalled=" + this.isInstalled() + ")";
    }
}

