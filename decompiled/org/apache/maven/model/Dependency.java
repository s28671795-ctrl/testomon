/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputLocationTracker;

public class Dependency
implements Serializable,
Cloneable,
InputLocationTracker {
    private String groupId;
    private String artifactId;
    private String version;
    private String type = "jar";
    private String classifier;
    private String scope;
    private String systemPath;
    private List<Exclusion> exclusions;
    private String optional;
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation groupIdLocation;
    private InputLocation artifactIdLocation;
    private InputLocation versionLocation;
    private InputLocation typeLocation;
    private InputLocation classifierLocation;
    private InputLocation scopeLocation;
    private InputLocation systemPathLocation;
    private InputLocation exclusionsLocation;
    private InputLocation optionalLocation;
    private String managementKey;

    public void addExclusion(Exclusion exclusion) {
        this.getExclusions().add(exclusion);
    }

    public Dependency clone() {
        try {
            Dependency copy = (Dependency)super.clone();
            if (this.exclusions != null) {
                copy.exclusions = new ArrayList<Exclusion>();
                for (Exclusion item : this.exclusions) {
                    copy.exclusions.add(item.clone());
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

    public String getArtifactId() {
        return this.artifactId;
    }

    public String getClassifier() {
        return this.classifier;
    }

    public List<Exclusion> getExclusions() {
        if (this.exclusions == null) {
            this.exclusions = new ArrayList<Exclusion>();
        }
        return this.exclusions;
    }

    public String getGroupId() {
        return this.groupId;
    }

    @Override
    public InputLocation getLocation(Object key) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    return this.location;
                }
                case "groupId": {
                    return this.groupIdLocation;
                }
                case "artifactId": {
                    return this.artifactIdLocation;
                }
                case "version": {
                    return this.versionLocation;
                }
                case "type": {
                    return this.typeLocation;
                }
                case "classifier": {
                    return this.classifierLocation;
                }
                case "scope": {
                    return this.scopeLocation;
                }
                case "systemPath": {
                    return this.systemPathLocation;
                }
                case "exclusions": {
                    return this.exclusionsLocation;
                }
                case "optional": {
                    return this.optionalLocation;
                }
            }
            return this.getOtherLocation(key);
        }
        return this.getOtherLocation(key);
    }

    public String getOptional() {
        return this.optional;
    }

    @Override
    public void setLocation(Object key, InputLocation location) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    this.location = location;
                    return;
                }
                case "groupId": {
                    this.groupIdLocation = location;
                    return;
                }
                case "artifactId": {
                    this.artifactIdLocation = location;
                    return;
                }
                case "version": {
                    this.versionLocation = location;
                    return;
                }
                case "type": {
                    this.typeLocation = location;
                    return;
                }
                case "classifier": {
                    this.classifierLocation = location;
                    return;
                }
                case "scope": {
                    this.scopeLocation = location;
                    return;
                }
                case "systemPath": {
                    this.systemPathLocation = location;
                    return;
                }
                case "exclusions": {
                    this.exclusionsLocation = location;
                    return;
                }
                case "optional": {
                    this.optionalLocation = location;
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

    public String getScope() {
        return this.scope;
    }

    public String getSystemPath() {
        return this.systemPath;
    }

    public String getType() {
        return this.type;
    }

    public String getVersion() {
        return this.version;
    }

    public void removeExclusion(Exclusion exclusion) {
        this.getExclusions().remove(exclusion);
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public void setExclusions(List<Exclusion> exclusions) {
        this.exclusions = exclusions;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setSystemPath(String systemPath) {
        this.systemPath = systemPath;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isOptional() {
        return this.optional != null ? Boolean.parseBoolean(this.optional) : false;
    }

    public void setOptional(boolean optional) {
        this.optional = String.valueOf(optional);
    }

    public String toString() {
        return "Dependency {groupId=" + this.groupId + ", artifactId=" + this.artifactId + ", version=" + this.version + ", type=" + this.type + "}";
    }

    public String getManagementKey() {
        if (this.managementKey == null) {
            this.managementKey = this.groupId + ":" + this.artifactId + ":" + this.type + (this.classifier != null ? ":" + this.classifier : "");
        }
        return this.managementKey;
    }

    public void clearManagementKey() {
        this.managementKey = null;
    }
}

