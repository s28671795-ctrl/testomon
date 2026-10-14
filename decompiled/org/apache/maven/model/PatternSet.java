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

public class PatternSet
implements Serializable,
Cloneable,
InputLocationTracker {
    private List<String> includes;
    private List<String> excludes;
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation includesLocation;
    private InputLocation excludesLocation;

    public void addExclude(String string) {
        this.getExcludes().add(string);
    }

    public void addInclude(String string) {
        this.getIncludes().add(string);
    }

    public PatternSet clone() {
        try {
            PatternSet copy = (PatternSet)super.clone();
            if (this.includes != null) {
                copy.includes = new ArrayList<String>();
                copy.includes.addAll(this.includes);
            }
            if (this.excludes != null) {
                copy.excludes = new ArrayList<String>();
                copy.excludes.addAll(this.excludes);
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

    public List<String> getExcludes() {
        if (this.excludes == null) {
            this.excludes = new ArrayList<String>();
        }
        return this.excludes;
    }

    public List<String> getIncludes() {
        if (this.includes == null) {
            this.includes = new ArrayList<String>();
        }
        return this.includes;
    }

    @Override
    public InputLocation getLocation(Object key) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    return this.location;
                }
                case "includes": {
                    return this.includesLocation;
                }
                case "excludes": {
                    return this.excludesLocation;
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
                case "includes": {
                    this.includesLocation = location;
                    return;
                }
                case "excludes": {
                    this.excludesLocation = location;
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

    public void removeExclude(String string) {
        this.getExcludes().remove(string);
    }

    public void removeInclude(String string) {
        this.getIncludes().remove(string);
    }

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("PatternSet [includes: {");
        for (String str : this.getIncludes()) {
            sb.append(str).append(", ");
        }
        if (sb.substring(sb.length() - 2).equals(", ")) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("}, excludes: {");
        for (String str : this.getExcludes()) {
            sb.append(str).append(", ");
        }
        if (sb.substring(sb.length() - 2).equals(", ")) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("}]");
        return sb.toString();
    }
}

