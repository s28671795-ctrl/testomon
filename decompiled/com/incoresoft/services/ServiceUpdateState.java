/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.services;

import com.github.zafarkhaja.semver.Version;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;

public final class ServiceUpdateState {
    private final String id;
    private final String name;
    private final Version currentVersion;
    private final List<Version> availableVersions;
    private final boolean isUpdateAvailable;
    private final boolean isBeingUpdated;

    public ServiceUpdateState(String id, String name, List<Version> availableVersions, Version currentVersion, boolean isBeingUpdated) {
        this.id = id;
        this.name = name;
        this.currentVersion = currentVersion;
        this.availableVersions = availableVersions;
        this.isUpdateAvailable = this.currentVersion != null && CollectionUtils.isNotEmpty(availableVersions) && availableVersions.getFirst() != null && availableVersions.getFirst().greaterThan(this.currentVersion);
        this.isBeingUpdated = isBeingUpdated;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Version getCurrentVersion() {
        return this.currentVersion;
    }

    public List<Version> getAvailableVersions() {
        return this.availableVersions;
    }

    public boolean isUpdateAvailable() {
        return this.isUpdateAvailable;
    }

    public boolean isBeingUpdated() {
        return this.isBeingUpdated;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ServiceUpdateState)) {
            return false;
        }
        ServiceUpdateState other = (ServiceUpdateState)o;
        if (this.isUpdateAvailable() != other.isUpdateAvailable()) {
            return false;
        }
        if (this.isBeingUpdated() != other.isBeingUpdated()) {
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
        Version this$currentVersion = this.getCurrentVersion();
        Version other$currentVersion = other.getCurrentVersion();
        if (this$currentVersion == null ? other$currentVersion != null : !((Object)this$currentVersion).equals(other$currentVersion)) {
            return false;
        }
        List<Version> this$availableVersions = this.getAvailableVersions();
        List<Version> other$availableVersions = other.getAvailableVersions();
        return !(this$availableVersions == null ? other$availableVersions != null : !((Object)this$availableVersions).equals(other$availableVersions));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isUpdateAvailable() ? 79 : 97);
        result = result * 59 + (this.isBeingUpdated() ? 79 : 97);
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Version $currentVersion = this.getCurrentVersion();
        result = result * 59 + ($currentVersion == null ? 43 : ((Object)$currentVersion).hashCode());
        List<Version> $availableVersions = this.getAvailableVersions();
        result = result * 59 + ($availableVersions == null ? 43 : ((Object)$availableVersions).hashCode());
        return result;
    }

    public String toString() {
        return "ServiceUpdateState(id=" + this.getId() + ", name=" + this.getName() + ", currentVersion=" + String.valueOf(this.getCurrentVersion()) + ", availableVersions=" + String.valueOf(this.getAvailableVersions()) + ", isUpdateAvailable=" + this.isUpdateAvailable() + ", isBeingUpdated=" + this.isBeingUpdated() + ")";
    }
}

