/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.models;

import com.github.zafarkhaja.semver.Version;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PluginFileInfo {
    private static final Logger log = LoggerFactory.getLogger(PluginFileInfo.class);
    private final String id;
    private final Version version;
    private final Path path;
    private final String name;
    private final String description;
    private final PluginState state;
    private final String iconPath;
    private final ClassLoader classLoader;
    private final Map<String, String> allAttributes;

    public PluginFileInfo(PluginWrapper pluginWrapper, Map<String, String> allAttributes) {
        this.id = pluginWrapper.getPluginId();
        this.version = this.getVersion(pluginWrapper.getDescriptor().getVersion());
        this.path = pluginWrapper.getPluginPath();
        this.description = pluginWrapper.getDescriptor().getPluginDescription();
        this.state = pluginWrapper.getPluginState();
        this.classLoader = pluginWrapper.getPluginClassLoader();
        this.name = Optional.ofNullable(allAttributes.get("Plugin-Name")).orElse(pluginWrapper.getPluginId());
        this.iconPath = allAttributes.get("Plugin-Icon-Resource-Path");
        this.allAttributes = allAttributes;
    }

    @Nullable
    private Version getVersion(String version) {
        try {
            return Version.valueOf(version);
        }
        catch (Exception e) {
            log.error("Cannot parse version string \"{}\" of plugin \"{}\"", version, this.id, e);
            return null;
        }
    }

    public String getId() {
        return this.id;
    }

    public Version getVersion() {
        return this.version;
    }

    public Path getPath() {
        return this.path;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public PluginState getState() {
        return this.state;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public Map<String, String> getAllAttributes() {
        return this.allAttributes;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PluginFileInfo)) {
            return false;
        }
        PluginFileInfo other = (PluginFileInfo)o;
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        Version this$version = this.getVersion();
        Version other$version = other.getVersion();
        if (this$version == null ? other$version != null : !((Object)this$version).equals(other$version)) {
            return false;
        }
        Path this$path = this.getPath();
        Path other$path = other.getPath();
        if (this$path == null ? other$path != null : !((Object)this$path).equals(other$path)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$description = this.getDescription();
        String other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
            return false;
        }
        PluginState this$state = this.getState();
        PluginState other$state = other.getState();
        if (this$state == null ? other$state != null : !((Object)((Object)this$state)).equals((Object)other$state)) {
            return false;
        }
        String this$iconPath = this.getIconPath();
        String other$iconPath = other.getIconPath();
        if (this$iconPath == null ? other$iconPath != null : !this$iconPath.equals(other$iconPath)) {
            return false;
        }
        ClassLoader this$classLoader = this.getClassLoader();
        ClassLoader other$classLoader = other.getClassLoader();
        if (this$classLoader == null ? other$classLoader != null : !this$classLoader.equals(other$classLoader)) {
            return false;
        }
        Map<String, String> this$allAttributes = this.getAllAttributes();
        Map<String, String> other$allAttributes = other.getAllAttributes();
        return !(this$allAttributes == null ? other$allAttributes != null : !((Object)this$allAttributes).equals(other$allAttributes));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Version $version = this.getVersion();
        result = result * 59 + ($version == null ? 43 : ((Object)$version).hashCode());
        Path $path = this.getPath();
        result = result * 59 + ($path == null ? 43 : ((Object)$path).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        PluginState $state = this.getState();
        result = result * 59 + ($state == null ? 43 : ((Object)((Object)$state)).hashCode());
        String $iconPath = this.getIconPath();
        result = result * 59 + ($iconPath == null ? 43 : $iconPath.hashCode());
        ClassLoader $classLoader = this.getClassLoader();
        result = result * 59 + ($classLoader == null ? 43 : $classLoader.hashCode());
        Map<String, String> $allAttributes = this.getAllAttributes();
        result = result * 59 + ($allAttributes == null ? 43 : ((Object)$allAttributes).hashCode());
        return result;
    }

    public String toString() {
        return "PluginFileInfo(id=" + this.getId() + ", version=" + String.valueOf(this.getVersion()) + ", path=" + String.valueOf(this.getPath()) + ", name=" + this.getName() + ", description=" + this.getDescription() + ", state=" + String.valueOf((Object)this.getState()) + ", iconPath=" + this.getIconPath() + ", classLoader=" + String.valueOf(this.getClassLoader()) + ", allAttributes=" + String.valueOf(this.getAllAttributes()) + ")";
    }
}

