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
import org.apache.maven.model.ReportPlugin;

public class Reporting
implements Serializable,
Cloneable,
InputLocationTracker {
    private String excludeDefaults;
    private String outputDirectory;
    private List<ReportPlugin> plugins;
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation excludeDefaultsLocation;
    private InputLocation outputDirectoryLocation;
    private InputLocation pluginsLocation;
    Map<String, ReportPlugin> reportPluginMap;

    public void addPlugin(ReportPlugin reportPlugin) {
        this.getPlugins().add(reportPlugin);
    }

    public Reporting clone() {
        try {
            Reporting copy = (Reporting)super.clone();
            if (this.plugins != null) {
                copy.plugins = new ArrayList<ReportPlugin>();
                for (ReportPlugin item : this.plugins) {
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

    public String getExcludeDefaults() {
        return this.excludeDefaults;
    }

    @Override
    public InputLocation getLocation(Object key) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    return this.location;
                }
                case "excludeDefaults": {
                    return this.excludeDefaultsLocation;
                }
                case "outputDirectory": {
                    return this.outputDirectoryLocation;
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
                case "excludeDefaults": {
                    this.excludeDefaultsLocation = location;
                    return;
                }
                case "outputDirectory": {
                    this.outputDirectoryLocation = location;
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

    public String getOutputDirectory() {
        return this.outputDirectory;
    }

    public List<ReportPlugin> getPlugins() {
        if (this.plugins == null) {
            this.plugins = new ArrayList<ReportPlugin>();
        }
        return this.plugins;
    }

    public void removePlugin(ReportPlugin reportPlugin) {
        this.getPlugins().remove(reportPlugin);
    }

    public void setExcludeDefaults(String excludeDefaults) {
        this.excludeDefaults = excludeDefaults;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void setPlugins(List<ReportPlugin> plugins) {
        this.plugins = plugins;
    }

    public boolean isExcludeDefaults() {
        return this.excludeDefaults != null ? Boolean.parseBoolean(this.excludeDefaults) : false;
    }

    public void setExcludeDefaults(boolean excludeDefaults) {
        this.excludeDefaults = String.valueOf(excludeDefaults);
    }

    public synchronized void flushReportPluginMap() {
        this.reportPluginMap = null;
    }

    public synchronized Map<String, ReportPlugin> getReportPluginsAsMap() {
        if (this.reportPluginMap == null) {
            this.reportPluginMap = new LinkedHashMap<String, ReportPlugin>();
            if (this.getPlugins() != null) {
                for (ReportPlugin reportPlugin : this.getPlugins()) {
                    this.reportPluginMap.put(reportPlugin.getKey(), reportPlugin);
                }
            }
        }
        return this.reportPluginMap;
    }
}

