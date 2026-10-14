/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputLocationTracker;
import org.apache.maven.model.Plugin;

public class PluginContainer
implements Serializable,
Cloneable,
InputLocationTracker {
    private List<Plugin> plugins;
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation pluginsLocation;
    Map<String, Plugin> pluginMap;

    public void addPlugin(Plugin plugin) {
        this.getPlugins().add(plugin);
    }

    public PluginContainer clone() {
        try {
            PluginContainer copy = (PluginContainer)super.clone();
            if (this.plugins != null) {
                copy.plugins = new ArrayList<Plugin>();
                for (Plugin item : this.plugins) {
                    copy.plugins.add(item.clone());
                }
            }
            if (copy.locations != null) {
                copy.locations = new LinkedHashMap<Object, InputLocation>(copy.locations);
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    @Override
    public InputLocation getLocation(Object key) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    return this.location;
                }
                case "plugins": {
                    return this.pluginsLocation;
                }
            }
            return this.getOtherLocation(key);
        }
        return this.getOtherLocation(key);
    }

    @Override
    public void setLocation(Object key, InputLocation location) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    this.location = location;
                    return;
                }
                case "plugins": {
                    this.pluginsLocation = location;
                    return;
                }
            }
            this.setOtherLocation(key, location);
            return;
        }
        this.setOtherLocation(key, location);
    }

    public void setOtherLocation(Object key, InputLocation location) {
        if (location != null) {
            if (this.locations == null) {
                this.locations = new LinkedHashMap<Object, InputLocation>();
            }
            this.locations.put(key, location);
        }
    }

    private InputLocation getOtherLocation(Object key) {
        return this.locations != null ? this.locations.get(key) : null;
    }

    public List<Plugin> getPlugins() {
        if (this.plugins == null) {
            this.plugins = new ArrayList<Plugin>();
        }
        return this.plugins;
    }

    public void removePlugin(Plugin plugin) {
        this.getPlugins().remove(plugin);
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public synchronized void flushPluginMap() {
        this.pluginMap = null;
    }

    public synchronized Map<String, Plugin> getPluginsAsMap() {
        if (this.pluginMap == null) {
            this.pluginMap = new LinkedHashMap<String, Plugin>();
            if (this.plugins != null) {
                for (Plugin plugin : this.plugins) {
                    this.pluginMap.put(plugin.getKey(), plugin);
                }
            }
        }
        return this.pluginMap;
    }

    public String toString() {
        return "PluginContainer {}";
    }
}

