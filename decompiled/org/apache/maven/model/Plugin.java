/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.maven.model.ConfigurationContainer;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.PluginExecution;
import org.codehaus.plexus.util.xml.Xpp3Dom;

public class Plugin
extends ConfigurationContainer
implements Serializable,
Cloneable {
    private String groupId = "org.apache.maven.plugins";
    private String artifactId;
    private String version;
    private String extensions;
    private List<PluginExecution> executions;
    private List<Dependency> dependencies;
    private Object goals;
    private Map<String, PluginExecution> executionMap = null;

    public void addDependency(Dependency dependency) {
        this.getDependencies().add(dependency);
    }

    public void addExecution(PluginExecution pluginExecution) {
        this.getExecutions().add(pluginExecution);
    }

    @Override
    public Plugin clone() {
        try {
            Plugin copy = (Plugin)super.clone();
            if (this.executions != null) {
                copy.executions = new ArrayList<PluginExecution>();
                for (PluginExecution pluginExecution : this.executions) {
                    copy.executions.add(pluginExecution.clone());
                }
            }
            if (this.dependencies != null) {
                copy.dependencies = new ArrayList<Dependency>();
                for (Dependency dependency : this.dependencies) {
                    copy.dependencies.add(dependency.clone());
                }
            }
            if (this.goals != null) {
                copy.goals = new Xpp3Dom((Xpp3Dom)this.goals);
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public String getArtifactId() {
        return this.artifactId;
    }

    public List<Dependency> getDependencies() {
        if (this.dependencies == null) {
            this.dependencies = new ArrayList<Dependency>();
        }
        return this.dependencies;
    }

    public List<PluginExecution> getExecutions() {
        if (this.executions == null) {
            this.executions = new ArrayList<PluginExecution>();
        }
        return this.executions;
    }

    public String getExtensions() {
        return this.extensions;
    }

    public Object getGoals() {
        return this.goals;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getVersion() {
        return this.version;
    }

    public void removeDependency(Dependency dependency) {
        this.getDependencies().remove(dependency);
    }

    public void removeExecution(PluginExecution pluginExecution) {
        this.getExecutions().remove(pluginExecution);
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public void setExecutions(List<PluginExecution> executions) {
        this.executions = executions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public void setGoals(Object goals) {
        this.goals = goals;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isExtensions() {
        return this.extensions != null ? Boolean.parseBoolean(this.extensions) : false;
    }

    public void setExtensions(boolean extensions) {
        this.extensions = String.valueOf(extensions);
    }

    public void flushExecutionMap() {
        this.executionMap = null;
    }

    public Map<String, PluginExecution> getExecutionsAsMap() {
        if (this.executionMap == null) {
            this.executionMap = new LinkedHashMap<String, PluginExecution>();
            if (this.getExecutions() != null) {
                for (PluginExecution exec : this.getExecutions()) {
                    if (this.executionMap.containsKey(exec.getId())) {
                        throw new IllegalStateException("You cannot have two plugin executions with the same (or missing) <id/> elements.\nOffending execution\n\nId: '" + exec.getId() + "'\nPlugin:'" + this.getKey() + "'\n\n");
                    }
                    this.executionMap.put(exec.getId(), exec);
                }
            }
        }
        return this.executionMap;
    }

    public String getId() {
        StringBuilder id = new StringBuilder(128);
        id.append(this.getGroupId() == null ? "[unknown-group-id]" : this.getGroupId());
        id.append(":");
        id.append(this.getArtifactId() == null ? "[unknown-artifact-id]" : this.getArtifactId());
        id.append(":");
        id.append(this.getVersion() == null ? "[unknown-version]" : this.getVersion());
        return id.toString();
    }

    public String getKey() {
        return Plugin.constructKey(this.groupId, this.artifactId);
    }

    public static String constructKey(String groupId, String artifactId) {
        return groupId + ":" + artifactId;
    }

    public boolean equals(Object other) {
        if (other instanceof Plugin) {
            Plugin otherPlugin = (Plugin)other;
            return this.getKey().equals(otherPlugin.getKey());
        }
        return false;
    }

    public int hashCode() {
        return this.getKey().hashCode();
    }

    public String toString() {
        return "Plugin [" + this.getKey() + "]";
    }
}

