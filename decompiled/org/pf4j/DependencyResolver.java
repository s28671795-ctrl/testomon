/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.pf4j.PluginDependency;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginRuntimeException;
import org.pf4j.VersionManager;
import org.pf4j.util.DirectedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DependencyResolver {
    private static final Logger log = LoggerFactory.getLogger(DependencyResolver.class);
    private VersionManager versionManager;
    private DirectedGraph<String> dependenciesGraph;
    private DirectedGraph<String> dependentsGraph;
    private boolean resolved;

    public DependencyResolver(VersionManager versionManager) {
        this.versionManager = versionManager;
    }

    public Result resolve(List<PluginDescriptor> plugins) {
        this.dependenciesGraph = new DirectedGraph();
        this.dependentsGraph = new DirectedGraph();
        HashMap<String, PluginDescriptor> pluginByIds = new HashMap<String, PluginDescriptor>();
        for (PluginDescriptor plugin : plugins) {
            this.addPlugin(plugin);
            pluginByIds.put(plugin.getPluginId(), plugin);
        }
        log.debug("Graph: {}", (Object)this.dependenciesGraph);
        List<String> sortedPlugins = this.dependenciesGraph.reverseTopologicalSort();
        log.debug("Plugins order: {}", (Object)sortedPlugins);
        Result result = new Result(sortedPlugins);
        this.resolved = true;
        if (sortedPlugins != null) {
            for (String pluginId : sortedPlugins) {
                if (pluginByIds.containsKey(pluginId)) continue;
                result.addNotFoundDependency(pluginId);
            }
        }
        for (PluginDescriptor plugin : plugins) {
            String pluginId = plugin.getPluginId();
            String existingVersion = plugin.getVersion();
            List<String> dependents = this.getDependents(pluginId);
            while (!dependents.isEmpty()) {
                String dependentId = dependents.remove(0);
                PluginDescriptor dependent = (PluginDescriptor)pluginByIds.get(dependentId);
                String requiredVersion = this.getDependencyVersionSupport(dependent, pluginId);
                boolean ok = this.checkDependencyVersion(requiredVersion, existingVersion);
                if (ok) continue;
                result.addWrongDependencyVersion(new WrongDependencyVersion(pluginId, dependentId, existingVersion, requiredVersion));
            }
        }
        return result;
    }

    public List<String> getDependencies(String pluginId) {
        this.checkResolved();
        return new ArrayList<String>(this.dependenciesGraph.getNeighbors(pluginId));
    }

    public List<String> getDependents(String pluginId) {
        this.checkResolved();
        return new ArrayList<String>(this.dependentsGraph.getNeighbors(pluginId));
    }

    protected boolean checkDependencyVersion(String requiredVersion, String existingVersion) {
        return this.versionManager.checkVersionConstraint(existingVersion, requiredVersion);
    }

    private void addPlugin(PluginDescriptor descriptor) {
        String pluginId = descriptor.getPluginId();
        List<PluginDependency> dependencies = descriptor.getDependencies();
        if (dependencies.isEmpty()) {
            this.dependenciesGraph.addVertex(pluginId);
            this.dependentsGraph.addVertex(pluginId);
        } else {
            boolean edgeAdded = false;
            for (PluginDependency dependency : dependencies) {
                if (dependency.isOptional()) continue;
                edgeAdded = true;
                this.dependenciesGraph.addEdge(pluginId, dependency.getPluginId());
                this.dependentsGraph.addEdge(dependency.getPluginId(), pluginId);
            }
            if (!edgeAdded) {
                this.dependenciesGraph.addVertex(pluginId);
                this.dependentsGraph.addVertex(pluginId);
            }
        }
    }

    private void checkResolved() {
        if (!this.resolved) {
            throw new IllegalStateException("Call 'resolve' method first");
        }
    }

    private String getDependencyVersionSupport(PluginDescriptor dependent, String dependencyId) {
        List<PluginDependency> dependencies = dependent.getDependencies();
        for (PluginDependency dependency : dependencies) {
            if (!dependencyId.equals(dependency.getPluginId())) continue;
            return dependency.getPluginVersionSupport();
        }
        throw new IllegalStateException("Cannot find a dependency with id '" + dependencyId + "' for plugin '" + dependent.getPluginId() + "'");
    }

    public static class DependenciesWrongVersionException
    extends PluginRuntimeException {
        private List<WrongDependencyVersion> dependencies;

        public DependenciesWrongVersionException(List<WrongDependencyVersion> dependencies) {
            super("Dependencies '{}' have wrong version", dependencies);
            this.dependencies = dependencies;
        }

        public List<WrongDependencyVersion> getDependencies() {
            return this.dependencies;
        }
    }

    public static class DependenciesNotFoundException
    extends PluginRuntimeException {
        private List<String> dependencies;

        public DependenciesNotFoundException(List<String> dependencies) {
            super("Dependencies '{}' not found", dependencies);
            this.dependencies = dependencies;
        }

        public List<String> getDependencies() {
            return this.dependencies;
        }
    }

    public static class CyclicDependencyException
    extends PluginRuntimeException {
        public CyclicDependencyException() {
            super("Cyclic dependencies");
        }
    }

    public static class WrongDependencyVersion {
        private String dependencyId;
        private String dependentId;
        private String existingVersion;
        private String requiredVersion;

        WrongDependencyVersion(String dependencyId, String dependentId, String existingVersion, String requiredVersion) {
            this.dependencyId = dependencyId;
            this.dependentId = dependentId;
            this.existingVersion = existingVersion;
            this.requiredVersion = requiredVersion;
        }

        public String getDependencyId() {
            return this.dependencyId;
        }

        public String getDependentId() {
            return this.dependentId;
        }

        public String getExistingVersion() {
            return this.existingVersion;
        }

        public String getRequiredVersion() {
            return this.requiredVersion;
        }

        public String toString() {
            return "WrongDependencyVersion{dependencyId='" + this.dependencyId + '\'' + ", dependentId='" + this.dependentId + '\'' + ", existingVersion='" + this.existingVersion + '\'' + ", requiredVersion='" + this.requiredVersion + '\'' + '}';
        }
    }

    public static class Result {
        private boolean cyclicDependency;
        private List<String> notFoundDependencies;
        private List<String> sortedPlugins;
        private List<WrongDependencyVersion> wrongVersionDependencies;

        Result(List<String> sortedPlugins) {
            if (sortedPlugins == null) {
                this.cyclicDependency = true;
                this.sortedPlugins = Collections.emptyList();
            } else {
                this.sortedPlugins = new ArrayList<String>(sortedPlugins);
            }
            this.notFoundDependencies = new ArrayList<String>();
            this.wrongVersionDependencies = new ArrayList<WrongDependencyVersion>();
        }

        public boolean hasCyclicDependency() {
            return this.cyclicDependency;
        }

        public List<String> getNotFoundDependencies() {
            return this.notFoundDependencies;
        }

        public List<WrongDependencyVersion> getWrongVersionDependencies() {
            return this.wrongVersionDependencies;
        }

        public List<String> getSortedPlugins() {
            return this.sortedPlugins;
        }

        void addNotFoundDependency(String pluginId) {
            this.notFoundDependencies.add(pluginId);
        }

        void addWrongDependencyVersion(WrongDependencyVersion wrongDependencyVersion) {
            this.wrongVersionDependencies.add(wrongDependencyVersion);
        }
    }
}

