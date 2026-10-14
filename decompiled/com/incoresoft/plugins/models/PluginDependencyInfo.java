/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.models;

import com.alibaba.fastjson.JSONObject;

public final class PluginDependencyInfo {
    private final String id;
    private final int majorVersion;

    public PluginDependencyInfo(JSONObject json) {
        this.id = json.getString("id");
        this.majorVersion = json.getIntValue("version");
    }

    public String getId() {
        return this.id;
    }

    public int getMajorVersion() {
        return this.majorVersion;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PluginDependencyInfo)) {
            return false;
        }
        PluginDependencyInfo other = (PluginDependencyInfo)o;
        if (this.getMajorVersion() != other.getMajorVersion()) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        return !(this$id == null ? other$id != null : !this$id.equals(other$id));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getMajorVersion();
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        return result;
    }

    public String toString() {
        return "PluginDependencyInfo(id=" + this.getId() + ", majorVersion=" + this.getMajorVersion() + ")";
    }

    public PluginDependencyInfo(String id, int majorVersion) {
        this.id = id;
        this.majorVersion = majorVersion;
    }
}

