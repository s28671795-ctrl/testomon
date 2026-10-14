/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputLocationTracker;

public class DependencyManagement
implements Serializable,
Cloneable,
InputLocationTracker {
    private List<Dependency> dependencies;
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation dependenciesLocation;

    public void addDependency(Dependency dependency) {
        this.getDependencies().add(dependency);
    }

    public DependencyManagement clone() {
        try {
            DependencyManagement copy = (DependencyManagement)super.clone();
            if (this.dependencies != null) {
                copy.dependencies = new ArrayList<Dependency>();
                for (Dependency item : this.dependencies) {
                    copy.dependencies.add(item.clone());
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

    public List<Dependency> getDependencies() {
        if (this.dependencies == null) {
            this.dependencies = new ArrayList<Dependency>();
        }
        return this.dependencies;
    }

    @Override
    public InputLocation getLocation(Object key) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    return this.location;
                }
                case "dependencies": {
                    return this.dependenciesLocation;
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
                case "dependencies": {
                    this.dependenciesLocation = location;
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

    public void removeDependency(Dependency dependency) {
        this.getDependencies().remove(dependency);
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }
}

