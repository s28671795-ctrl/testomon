/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.pf4j.Plugin;
import org.pf4j.PluginDependency;
import org.pf4j.PluginDescriptor;

public class DefaultPluginDescriptor
implements PluginDescriptor {
    private String pluginId;
    private String pluginDescription;
    private String pluginClass = Plugin.class.getName();
    private String version;
    private String requires = "*";
    private String provider;
    private List<PluginDependency> dependencies = new ArrayList<PluginDependency>();
    private String license;

    public DefaultPluginDescriptor() {
    }

    public DefaultPluginDescriptor(String pluginId, String pluginDescription, String pluginClass, String version, String requires, String provider, String license) {
        this();
        this.pluginId = pluginId;
        this.pluginDescription = pluginDescription;
        this.pluginClass = pluginClass;
        this.version = version;
        this.requires = requires;
        this.provider = provider;
        this.license = license;
    }

    public void addDependency(PluginDependency dependency) {
        this.dependencies.add(dependency);
    }

    @Override
    public String getPluginId() {
        return this.pluginId;
    }

    @Override
    public String getPluginDescription() {
        return this.pluginDescription;
    }

    @Override
    public String getPluginClass() {
        return this.pluginClass;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public String getRequires() {
        return this.requires;
    }

    @Override
    public String getProvider() {
        return this.provider;
    }

    @Override
    public String getLicense() {
        return this.license;
    }

    @Override
    public List<PluginDependency> getDependencies() {
        return this.dependencies;
    }

    public String toString() {
        return "PluginDescriptor [pluginId=" + this.pluginId + ", pluginClass=" + this.pluginClass + ", version=" + this.version + ", provider=" + this.provider + ", dependencies=" + this.dependencies + ", description=" + this.pluginDescription + ", requires=" + this.requires + ", license=" + this.license + "]";
    }

    protected DefaultPluginDescriptor setPluginId(String pluginId) {
        this.pluginId = pluginId;
        return this;
    }

    protected PluginDescriptor setPluginDescription(String pluginDescription) {
        this.pluginDescription = pluginDescription;
        return this;
    }

    protected PluginDescriptor setPluginClass(String pluginClassName) {
        this.pluginClass = pluginClassName;
        return this;
    }

    protected DefaultPluginDescriptor setPluginVersion(String version) {
        this.version = version;
        return this;
    }

    protected PluginDescriptor setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    protected PluginDescriptor setRequires(String requires) {
        this.requires = requires;
        return this;
    }

    protected PluginDescriptor setDependencies(String dependencies) {
        this.dependencies = new ArrayList<PluginDependency>();
        if (dependencies != null && !(dependencies = dependencies.trim()).isEmpty()) {
            String[] tokens;
            for (String dependency : tokens = dependencies.split(",")) {
                if ((dependency = dependency.trim()).isEmpty()) continue;
                this.dependencies.add(new PluginDependency(dependency));
            }
        }
        return this;
    }

    public PluginDescriptor setLicense(String license) {
        this.license = license;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultPluginDescriptor)) {
            return false;
        }
        DefaultPluginDescriptor that = (DefaultPluginDescriptor)o;
        return Objects.equals(this.pluginId, that.pluginId) && Objects.equals(this.pluginDescription, that.pluginDescription) && Objects.equals(this.pluginClass, that.pluginClass) && Objects.equals(this.version, that.version) && Objects.equals(this.requires, that.requires) && Objects.equals(this.provider, that.provider) && this.dependencies.equals(that.dependencies) && Objects.equals(this.license, that.license);
    }

    public int hashCode() {
        return Objects.hash(this.pluginId, this.pluginDescription, this.pluginClass, this.version, this.requires, this.provider, this.dependencies, this.license);
    }
}

