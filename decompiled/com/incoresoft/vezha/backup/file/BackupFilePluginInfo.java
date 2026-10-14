/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.file;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.zafarkhaja.semver.Version;
import com.incoresoft.plugins.models.InstalledPluginInfo;

public class BackupFilePluginInfo {
    @JSONField(name="id")
    private String id;
    @JSONField(name="name")
    private String name;
    @JSONField(name="version")
    private Version version;

    public BackupFilePluginInfo(InstalledPluginInfo pluginInfo) {
        this.id = pluginInfo.getId();
        this.name = pluginInfo.getName();
        this.version = pluginInfo.getVersion();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Version getVersion() {
        return this.version;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BackupFilePluginInfo)) {
            return false;
        }
        BackupFilePluginInfo other = (BackupFilePluginInfo)o;
        if (!other.canEqual(this)) {
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
        Version this$version = this.getVersion();
        Version other$version = other.getVersion();
        return !(this$version == null ? other$version != null : !((Object)this$version).equals(other$version));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BackupFilePluginInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Version $version = this.getVersion();
        result = result * 59 + ($version == null ? 43 : ((Object)$version).hashCode());
        return result;
    }

    public String toString() {
        return "BackupFilePluginInfo(id=" + this.getId() + ", name=" + this.getName() + ", version=" + String.valueOf(this.getVersion()) + ")";
    }

    public BackupFilePluginInfo() {
    }

    public BackupFilePluginInfo(String id, String name, Version version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }
}

