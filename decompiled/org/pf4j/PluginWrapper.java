/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Path;
import org.pf4j.Plugin;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginFactory;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;
import org.pf4j.RuntimeMode;

public class PluginWrapper {
    private PluginManager pluginManager;
    private PluginDescriptor descriptor;
    private Path pluginPath;
    private ClassLoader pluginClassLoader;
    private PluginFactory pluginFactory;
    private PluginState pluginState;
    private RuntimeMode runtimeMode;
    private Throwable failedException;
    Plugin plugin;

    public PluginWrapper(PluginManager pluginManager, PluginDescriptor descriptor, Path pluginPath, ClassLoader pluginClassLoader) {
        this.pluginManager = pluginManager;
        this.descriptor = descriptor;
        this.pluginPath = pluginPath;
        this.pluginClassLoader = pluginClassLoader;
        this.pluginState = PluginState.CREATED;
        this.runtimeMode = pluginManager.getRuntimeMode();
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public PluginDescriptor getDescriptor() {
        return this.descriptor;
    }

    public Path getPluginPath() {
        return this.pluginPath;
    }

    public ClassLoader getPluginClassLoader() {
        return this.pluginClassLoader;
    }

    public Plugin getPlugin() {
        if (this.plugin == null) {
            this.plugin = this.pluginFactory.create(this);
        }
        return this.plugin;
    }

    public PluginState getPluginState() {
        return this.pluginState;
    }

    public RuntimeMode getRuntimeMode() {
        return this.runtimeMode;
    }

    public String getPluginId() {
        return this.getDescriptor().getPluginId();
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.descriptor.getPluginId().hashCode();
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        PluginWrapper other = (PluginWrapper)obj;
        return this.descriptor.getPluginId().equals(other.descriptor.getPluginId());
    }

    public String toString() {
        return "PluginWrapper [descriptor=" + this.descriptor + ", pluginPath=" + this.pluginPath + "]";
    }

    public void setPluginState(PluginState pluginState) {
        this.pluginState = pluginState;
    }

    public void setPluginFactory(PluginFactory pluginFactory) {
        this.pluginFactory = pluginFactory;
    }

    public Throwable getFailedException() {
        return this.failedException;
    }

    public void setFailedException(Throwable failedException) {
        this.failedException = failedException;
    }
}

