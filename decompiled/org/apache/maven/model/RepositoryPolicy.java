/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputLocationTracker;

public class RepositoryPolicy
implements Serializable,
Cloneable,
InputLocationTracker {
    private String enabled;
    private String updatePolicy;
    private String checksumPolicy;
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation enabledLocation;
    private InputLocation updatePolicyLocation;
    private InputLocation checksumPolicyLocation;

    public RepositoryPolicy clone() {
        try {
            RepositoryPolicy copy = (RepositoryPolicy)super.clone();
            if (copy.locations != null) {
                copy.locations = new LinkedHashMap<Object, InputLocation>(copy.locations);
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public String getChecksumPolicy() {
        return this.checksumPolicy;
    }

    public String getEnabled() {
        return this.enabled;
    }

    @Override
    public InputLocation getLocation(Object key) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    return this.location;
                }
                case "enabled": {
                    return this.enabledLocation;
                }
                case "updatePolicy": {
                    return this.updatePolicyLocation;
                }
                case "checksumPolicy": {
                    return this.checksumPolicyLocation;
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
                case "enabled": {
                    this.enabledLocation = location;
                    return;
                }
                case "updatePolicy": {
                    this.updatePolicyLocation = location;
                    return;
                }
                case "checksumPolicy": {
                    this.checksumPolicyLocation = location;
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

    public String getUpdatePolicy() {
        return this.updatePolicy;
    }

    public void setChecksumPolicy(String checksumPolicy) {
        this.checksumPolicy = checksumPolicy;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public void setUpdatePolicy(String updatePolicy) {
        this.updatePolicy = updatePolicy;
    }

    public boolean isEnabled() {
        return this.enabled != null ? Boolean.parseBoolean(this.enabled) : true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = String.valueOf(enabled);
    }
}

