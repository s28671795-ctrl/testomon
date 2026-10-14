/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.plugins;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.zafarkhaja.semver.Version;
import com.incoresoft.plugins.models.InstalledPluginInfo;
import com.incoresoft.plugins.models.PluginDependencyInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@Schema(name="InstalledPluginInfo", description="Installed plugin info")
public final class InstalledPluginInfoDTO {
    @JsonProperty(value="id")
    @JSONField(name="id")
    @Schema(name="id", description="Plugin id", type="string")
    private final String id;
    @JsonProperty(value="description")
    @JSONField(name="description")
    @Schema(name="description", description="Plugin description", type="string")
    private final String description;
    @JsonProperty(value="name")
    @JSONField(name="name")
    @Schema(name="name", description="Plugin name", type="string")
    private final String name;
    @JsonProperty(value="type")
    @JSONField(name="type")
    @Schema(name="type", description="Plugin type", example="vms", implementation=String.class)
    private final String type;
    @JsonProperty(value="install_path")
    @JSONField(name="install_path")
    @Schema(name="install_path", description="Plugin install path", type="string")
    private final Path installPath;
    @JsonProperty(value="version")
    @JSONField(name="version")
    @Schema(name="version", description="Plugin version", type="string")
    private final Version version;
    @JsonProperty(value="enabled")
    @JSONField(name="enabled")
    @Schema(name="enabled", description="Plugin enabled", type="boolean")
    private final boolean enabled;
    @JsonProperty(value="requirements")
    @JSONField(name="requirements")
    @Schema(name="requirements", description="Plugin requirements", type="array")
    private final Collection<PluginDependencyInfo> requirements;

    public InstalledPluginInfoDTO(InstalledPluginInfo pluginInfo) {
        this.id = pluginInfo.getId();
        this.name = pluginInfo.getName();
        this.type = pluginInfo.getType();
        this.description = pluginInfo.getDescription();
        this.installPath = pluginInfo.getInstallPath();
        this.version = pluginInfo.getVersion();
        this.enabled = pluginInfo.isEnabled();
        this.requirements = List.of();
    }

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public Path getInstallPath() {
        return this.installPath;
    }

    public Version getVersion() {
        return this.version;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public Collection<PluginDependencyInfo> getRequirements() {
        return this.requirements;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof InstalledPluginInfoDTO)) {
            return false;
        }
        InstalledPluginInfoDTO other = (InstalledPluginInfoDTO)o;
        if (this.isEnabled() != other.isEnabled()) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$description = this.getDescription();
        String other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        Path this$installPath = this.getInstallPath();
        Path other$installPath = other.getInstallPath();
        if (this$installPath == null ? other$installPath != null : !((Object)this$installPath).equals(other$installPath)) {
            return false;
        }
        Version this$version = this.getVersion();
        Version other$version = other.getVersion();
        if (this$version == null ? other$version != null : !((Object)this$version).equals(other$version)) {
            return false;
        }
        Collection<PluginDependencyInfo> this$requirements = this.getRequirements();
        Collection<PluginDependencyInfo> other$requirements = other.getRequirements();
        return !(this$requirements == null ? other$requirements != null : !((Object)this$requirements).equals(other$requirements));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isEnabled() ? 79 : 97);
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        Path $installPath = this.getInstallPath();
        result = result * 59 + ($installPath == null ? 43 : ((Object)$installPath).hashCode());
        Version $version = this.getVersion();
        result = result * 59 + ($version == null ? 43 : ((Object)$version).hashCode());
        Collection<PluginDependencyInfo> $requirements = this.getRequirements();
        result = result * 59 + ($requirements == null ? 43 : ((Object)$requirements).hashCode());
        return result;
    }

    public String toString() {
        return "InstalledPluginInfoDTO(id=" + this.getId() + ", description=" + this.getDescription() + ", name=" + this.getName() + ", type=" + this.getType() + ", installPath=" + String.valueOf(this.getInstallPath()) + ", version=" + String.valueOf(this.getVersion()) + ", enabled=" + this.isEnabled() + ", requirements=" + String.valueOf(this.getRequirements()) + ")";
    }
}

