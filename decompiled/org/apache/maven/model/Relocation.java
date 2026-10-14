/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputLocationTracker;

public class Relocation
implements Serializable,
Cloneable,
InputLocationTracker {
    private String groupId;
    private String artifactId;
    private String version;
    private String message;
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation groupIdLocation;
    private InputLocation artifactIdLocation;
    private InputLocation versionLocation;
    private InputLocation messageLocation;

    public Relocation clone() {
        try {
            Relocation copy = (Relocation)super.clone();
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
                case "message": {
                    return this.messageLocation;
                }
            }
            return this.getOtherLocation(key);
        }
        return this.getOtherLocation(key);
    }

    public String getMessage() {
        return this.message;
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
                case "message": {
                    this.messageLocation = location;
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

    public String getVersion() {
        return this.version;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

