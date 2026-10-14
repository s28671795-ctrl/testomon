/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputLocationTracker;
import org.apache.maven.model.Reporting;
import org.apache.maven.model.Repository;
import org.codehaus.plexus.util.xml.Xpp3Dom;

public class ModelBase
implements Serializable,
Cloneable,
InputLocationTracker {
    private List<String> modules;
    private DistributionManagement distributionManagement;
    private Properties properties;
    private DependencyManagement dependencyManagement;
    private List<Dependency> dependencies;
    private List<Repository> repositories;
    private List<Repository> pluginRepositories;
    private Object reports;
    private Reporting reporting;
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation modulesLocation;
    private InputLocation distributionManagementLocation;
    private InputLocation propertiesLocation;
    private InputLocation dependencyManagementLocation;
    private InputLocation dependenciesLocation;
    private InputLocation repositoriesLocation;
    private InputLocation pluginRepositoriesLocation;
    private InputLocation reportsLocation;
    private InputLocation reportingLocation;

    public void addDependency(Dependency dependency) {
        this.getDependencies().add(dependency);
    }

    public void addModule(String string) {
        this.getModules().add(string);
    }

    public void addPluginRepository(Repository repository) {
        this.getPluginRepositories().add(repository);
    }

    public void addProperty(String key, String value) {
        this.getProperties().put(key, value);
    }

    public void addRepository(Repository repository) {
        this.getRepositories().add(repository);
    }

    public ModelBase clone() {
        try {
            ModelBase copy = (ModelBase)super.clone();
            if (this.modules != null) {
                copy.modules = new ArrayList<String>();
                copy.modules.addAll(this.modules);
            }
            if (this.distributionManagement != null) {
                copy.distributionManagement = this.distributionManagement.clone();
            }
            if (this.properties != null) {
                copy.properties = (Properties)this.properties.clone();
            }
            if (this.dependencyManagement != null) {
                copy.dependencyManagement = this.dependencyManagement.clone();
            }
            if (this.dependencies != null) {
                copy.dependencies = new ArrayList<Dependency>();
                for (Dependency dependency : this.dependencies) {
                    copy.dependencies.add(dependency.clone());
                }
            }
            if (this.repositories != null) {
                copy.repositories = new ArrayList<Repository>();
                for (Repository repository : this.repositories) {
                    copy.repositories.add(repository.clone());
                }
            }
            if (this.pluginRepositories != null) {
                copy.pluginRepositories = new ArrayList<Repository>();
                for (Repository repository : this.pluginRepositories) {
                    copy.pluginRepositories.add(repository.clone());
                }
            }
            if (this.reports != null) {
                copy.reports = new Xpp3Dom((Xpp3Dom)this.reports);
            }
            if (this.reporting != null) {
                copy.reporting = this.reporting.clone();
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

    public List<Dependency> getDependencies() {
        if (this.dependencies == null) {
            this.dependencies = new ArrayList<Dependency>();
        }
        return this.dependencies;
    }

    public DependencyManagement getDependencyManagement() {
        return this.dependencyManagement;
    }

    public DistributionManagement getDistributionManagement() {
        return this.distributionManagement;
    }

    @Override
    public InputLocation getLocation(Object key) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    return this.location;
                }
                case "modules": {
                    return this.modulesLocation;
                }
                case "distributionManagement": {
                    return this.distributionManagementLocation;
                }
                case "properties": {
                    return this.propertiesLocation;
                }
                case "dependencyManagement": {
                    return this.dependencyManagementLocation;
                }
                case "dependencies": {
                    return this.dependenciesLocation;
                }
                case "repositories": {
                    return this.repositoriesLocation;
                }
                case "pluginRepositories": {
                    return this.pluginRepositoriesLocation;
                }
                case "reports": {
                    return this.reportsLocation;
                }
                case "reporting": {
                    return this.reportingLocation;
                }
            }
            return this.getOtherLocation(key);
        }
        return this.getOtherLocation(key);
    }

    public List<String> getModules() {
        if (this.modules == null) {
            this.modules = new ArrayList<String>();
        }
        return this.modules;
    }

    @Override
    public void setLocation(Object key, InputLocation location) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    this.location = location;
                    return;
                }
                case "modules": {
                    this.modulesLocation = location;
                    return;
                }
                case "distributionManagement": {
                    this.distributionManagementLocation = location;
                    return;
                }
                case "properties": {
                    this.propertiesLocation = location;
                    return;
                }
                case "dependencyManagement": {
                    this.dependencyManagementLocation = location;
                    return;
                }
                case "dependencies": {
                    this.dependenciesLocation = location;
                    return;
                }
                case "repositories": {
                    this.repositoriesLocation = location;
                    return;
                }
                case "pluginRepositories": {
                    this.pluginRepositoriesLocation = location;
                    return;
                }
                case "reports": {
                    this.reportsLocation = location;
                    return;
                }
                case "reporting": {
                    this.reportingLocation = location;
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

    public List<Repository> getPluginRepositories() {
        if (this.pluginRepositories == null) {
            this.pluginRepositories = new ArrayList<Repository>();
        }
        return this.pluginRepositories;
    }

    public Properties getProperties() {
        if (this.properties == null) {
            this.properties = new Properties();
        }
        return this.properties;
    }

    public Reporting getReporting() {
        return this.reporting;
    }

    public Object getReports() {
        return this.reports;
    }

    public List<Repository> getRepositories() {
        if (this.repositories == null) {
            this.repositories = new ArrayList<Repository>();
        }
        return this.repositories;
    }

    public void removeDependency(Dependency dependency) {
        this.getDependencies().remove(dependency);
    }

    public void removeModule(String string) {
        this.getModules().remove(string);
    }

    public void removePluginRepository(Repository repository) {
        this.getPluginRepositories().remove(repository);
    }

    public void removeRepository(Repository repository) {
        this.getRepositories().remove(repository);
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public void setDependencyManagement(DependencyManagement dependencyManagement) {
        this.dependencyManagement = dependencyManagement;
    }

    public void setDistributionManagement(DistributionManagement distributionManagement) {
        this.distributionManagement = distributionManagement;
    }

    public void setModules(List<String> modules) {
        this.modules = modules;
    }

    public void setPluginRepositories(List<Repository> pluginRepositories) {
        this.pluginRepositories = pluginRepositories;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setReporting(Reporting reporting) {
        this.reporting = reporting;
    }

    public void setReports(Object reports) {
        this.reports = reports;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }
}

