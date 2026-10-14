/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.plugins;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.zafarkhaja.semver.Version;
import com.incoresoft.dto.plugins.PluginDependencyInfoDTO;
import com.incoresoft.plugins.models.MarketplacePluginInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

@Schema(name="MarketplacePluginInfoDTO", description="Marketplace plugin info class structure")
public final class MarketplacePluginInfoDTO {
    @JsonProperty(value="id")
    @JSONField(name="id")
    @Schema(name="id", description="Plugin id", example="com.incoresoft.plugin.crowds", implementation=String.class)
    private final String id;
    @JsonProperty(value="latest_version")
    @JSONField(name="latest_version")
    @Schema(name="latest_version", description="Latest plugin version", example="1.0.0", implementation=Version.class)
    private final Version latestVersion;
    @JsonProperty(value="byte_size")
    @JSONField(name="byte_size")
    @Schema(name="byte_size", description="Plugin size in bytes", example="123456", implementation=Long.class)
    private final long byteSize;
    @JsonProperty(value="latest_update_date")
    @JSONField(name="latest_update_date")
    @Schema(name="latest_update_date", description="Latest plugin update date", example="2021-01-01T00:00:00Z", implementation=Instant.class)
    private final Instant latestUpdateDate;
    @JsonProperty(value="icon")
    @JSONField(name="icon")
    @Schema(name="icon", description="Plugin icon", example="https://example.com/icon.png", implementation=String.class)
    private final String ico;
    @JsonProperty(value="name")
    @JSONField(name="name")
    @Schema(name="name", description="Plugin name", example="Crowds", implementation=String.class)
    private final String name;
    @JsonProperty(value="type")
    @JSONField(name="type")
    @Schema(name="type", description="Plugin type", example="vms", implementation=String.class)
    private final String type;
    @JsonProperty(value="description")
    @JSONField(name="description")
    @Schema(name="description", description="Plugin description", example="Crowds plugin", implementation=String.class)
    private final String description;
    @JsonProperty(value="download_url")
    @JSONField(name="download_url")
    @Schema(name="download_url", description="Plugin download url", example="https://example.com/plugin.jar", implementation=String.class)
    private final String downloadUrl;
    @JsonProperty(value="requirements")
    @JSONField(name="requirements")
    @Schema(name="requirements", description="Plugin requirements", example="[{\"id\":\"com.incoresoft.plugin.crowds\",\"version\":\"1.0.0\"}]", implementation=PluginDependencyInfoDTO.class)
    private final List<PluginDependencyInfoDTO> requirements;
    @JsonProperty(value="platforms")
    @JSONField(name="platforms")
    @Schema(name="platforms", description="Plugin platforms", example="[\"windows\",\"linux\"]", implementation=String.class)
    private final List<String> platforms;
    @JsonProperty(value="hash")
    @JSONField(name="hash")
    @Schema(name="hash", description="Plugin hash", example="1234567890abcdef1234567890abcdef12345678", implementation=String.class)
    private final String hash;
    @JsonProperty(value="installed")
    @JSONField(name="installed")
    @Schema(name="installed", description="Plugin installed", example="true", implementation=Boolean.class)
    private final boolean installed;

    public MarketplacePluginInfoDTO(MarketplacePluginInfo pluginInfo) {
        this.id = pluginInfo.getId();
        this.latestVersion = pluginInfo.getVersion();
        this.byteSize = pluginInfo.getByteSize();
        this.latestUpdateDate = pluginInfo.getUpdateDate();
        this.ico = pluginInfo.getIcon();
        this.name = pluginInfo.getName();
        this.type = pluginInfo.getType();
        this.description = pluginInfo.getDescription();
        this.downloadUrl = pluginInfo.getDownloadUrl();
        this.requirements = List.of();
        this.platforms = pluginInfo.getPlatforms();
        this.hash = pluginInfo.getHash();
        this.installed = pluginInfo.isInstalled();
    }

    public String getId() {
        return this.id;
    }

    public Version getLatestVersion() {
        return this.latestVersion;
    }

    public long getByteSize() {
        return this.byteSize;
    }

    public Instant getLatestUpdateDate() {
        return this.latestUpdateDate;
    }

    public String getIco() {
        return this.ico;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public List<PluginDependencyInfoDTO> getRequirements() {
        return this.requirements;
    }

    public List<String> getPlatforms() {
        return this.platforms;
    }

    public String getHash() {
        return this.hash;
    }

    public boolean isInstalled() {
        return this.installed;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MarketplacePluginInfoDTO)) {
            return false;
        }
        MarketplacePluginInfoDTO other = (MarketplacePluginInfoDTO)o;
        if (this.getByteSize() != other.getByteSize()) {
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
        Version this$latestVersion = this.getLatestVersion();
        Version other$latestVersion = other.getLatestVersion();
        if (this$latestVersion == null ? other$latestVersion != null : !((Object)this$latestVersion).equals(other$latestVersion)) {
            return false;
        }
        Instant this$latestUpdateDate = this.getLatestUpdateDate();
        Instant other$latestUpdateDate = other.getLatestUpdateDate();
        if (this$latestUpdateDate == null ? other$latestUpdateDate != null : !((Object)this$latestUpdateDate).equals(other$latestUpdateDate)) {
            return false;
        }
        String this$ico = this.getIco();
        String other$ico = other.getIco();
        if (this$ico == null ? other$ico != null : !this$ico.equals(other$ico)) {
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
        String this$description = this.getDescription();
        String other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        String this$downloadUrl = this.getDownloadUrl();
        String other$downloadUrl = other.getDownloadUrl();
        if (this$downloadUrl == null ? other$downloadUrl != null : !this$downloadUrl.equals(other$downloadUrl)) {
            return false;
        }
        List<PluginDependencyInfoDTO> this$requirements = this.getRequirements();
        List<PluginDependencyInfoDTO> other$requirements = other.getRequirements();
        if (this$requirements == null ? other$requirements != null : !((Object)this$requirements).equals(other$requirements)) {
            return false;
        }
        List<String> this$platforms = this.getPlatforms();
        List<String> other$platforms = other.getPlatforms();
        if (this$platforms == null ? other$platforms != null : !((Object)this$platforms).equals(other$platforms)) {
            return false;
        }
        String this$hash = this.getHash();
        String other$hash = other.getHash();
        return !(this$hash == null ? other$hash != null : !this$hash.equals(other$hash));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $byteSize = this.getByteSize();
        result = result * 59 + (int)($byteSize >>> 32 ^ $byteSize);
        result = result * 59 + (this.isInstalled() ? 79 : 97);
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Version $latestVersion = this.getLatestVersion();
        result = result * 59 + ($latestVersion == null ? 43 : ((Object)$latestVersion).hashCode());
        Instant $latestUpdateDate = this.getLatestUpdateDate();
        result = result * 59 + ($latestUpdateDate == null ? 43 : ((Object)$latestUpdateDate).hashCode());
        String $ico = this.getIco();
        result = result * 59 + ($ico == null ? 43 : $ico.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        String $downloadUrl = this.getDownloadUrl();
        result = result * 59 + ($downloadUrl == null ? 43 : $downloadUrl.hashCode());
        List<PluginDependencyInfoDTO> $requirements = this.getRequirements();
        result = result * 59 + ($requirements == null ? 43 : ((Object)$requirements).hashCode());
        List<String> $platforms = this.getPlatforms();
        result = result * 59 + ($platforms == null ? 43 : ((Object)$platforms).hashCode());
        String $hash = this.getHash();
        result = result * 59 + ($hash == null ? 43 : $hash.hashCode());
        return result;
    }

    public String toString() {
        return "MarketplacePluginInfoDTO(id=" + this.getId() + ", latestVersion=" + String.valueOf(this.getLatestVersion()) + ", byteSize=" + this.getByteSize() + ", latestUpdateDate=" + String.valueOf(this.getLatestUpdateDate()) + ", ico=" + this.getIco() + ", name=" + this.getName() + ", type=" + this.getType() + ", description=" + this.getDescription() + ", downloadUrl=" + this.getDownloadUrl() + ", requirements=" + String.valueOf(this.getRequirements()) + ", platforms=" + String.valueOf(this.getPlatforms()) + ", hash=" + this.getHash() + ", installed=" + this.isInstalled() + ")";
    }
}

