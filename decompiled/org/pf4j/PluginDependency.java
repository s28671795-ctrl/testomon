/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.util.Objects;

public class PluginDependency {
    private String pluginId;
    private String pluginVersionSupport = "*";
    private boolean optional;

    public PluginDependency(String dependency) {
        int index = dependency.indexOf(64);
        if (index == -1) {
            this.pluginId = dependency;
        } else {
            this.pluginId = dependency.substring(0, index);
            if (dependency.length() > index + 1) {
                this.pluginVersionSupport = dependency.substring(index + 1);
            }
        }
        this.optional = this.pluginId.endsWith("?");
        if (this.optional) {
            this.pluginId = this.pluginId.substring(0, this.pluginId.length() - 1);
        }
    }

    public String getPluginId() {
        return this.pluginId;
    }

    public String getPluginVersionSupport() {
        return this.pluginVersionSupport;
    }

    public boolean isOptional() {
        return this.optional;
    }

    public String toString() {
        return "PluginDependency [pluginId=" + this.pluginId + ", pluginVersionSupport=" + this.pluginVersionSupport + ", optional=" + this.optional + "]";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PluginDependency)) {
            return false;
        }
        PluginDependency that = (PluginDependency)o;
        return this.optional == that.optional && this.pluginId.equals(that.pluginId) && this.pluginVersionSupport.equals(that.pluginVersionSupport);
    }

    public int hashCode() {
        return Objects.hash(this.pluginId, this.pluginVersionSupport, this.optional);
    }
}

