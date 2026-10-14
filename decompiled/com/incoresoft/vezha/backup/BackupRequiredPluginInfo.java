/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup;

import com.incoresoft.version.models.MinorVersion;
import com.incoresoft.vezha.backup.file.BackupFilePluginInfo;

public class BackupRequiredPluginInfo {
    private String id;
    private String name;
    private MinorVersion version;
    private boolean isInstalled;

    public BackupRequiredPluginInfo(BackupFilePluginInfo pluginInfo, boolean isInstalled) {
        this.id = pluginInfo.getId();
        this.name = pluginInfo.getName();
        this.version = new MinorVersion(pluginInfo.getVersion());
        this.isInstalled = isInstalled;
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
        if (!(o instanceof BackupRequiredPluginInfo)) {
            return false;
        }
        BackupRequiredPluginInfo other = (BackupRequiredPluginInfo)o;
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
        return other instanceof BackupRequiredPluginInfo;
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
        return "BackupRequiredPluginInfo(id=" + this.getId() + ", name=" + this.getName() + ", version=" + String.valueOf(this.getVersion()) + ", isInstalled=" + this.isInstalled() + ")";
    }

    public BackupRequiredPluginInfo() {
    }

    public BackupRequiredPluginInfo(String id, String name, MinorVersion version, boolean isInstalled) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.isInstalled = isInstalled;
    }
}

