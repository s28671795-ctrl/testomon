/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputLocationTracker;

public class ActivationFile
implements Serializable,
Cloneable,
InputLocationTracker {
    private String missing;
    private String exists;
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation missingLocation;
    private InputLocation existsLocation;

    public ActivationFile clone() {
        try {
            ActivationFile copy = (ActivationFile)super.clone();
            if (copy.locations != null) {
                copy.locations = new LinkedHashMap<Object, InputLocation>(copy.locations);
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public String getExists() {
        return this.exists;
    }

    @Override
    public InputLocation getLocation(Object key) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    return this.location;
                }
                case "missing": {
                    return this.missingLocation;
                }
                case "exists": {
                    return this.existsLocation;
                }
            }
            return this.getOtherLocation(key);
        }
        return this.getOtherLocation(key);
    }

    public String getMissing() {
        return this.missing;
    }

    @Override
    public void setLocation(Object key, InputLocation location) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    this.location = location;
                    return;
                }
                case "missing": {
                    this.missingLocation = location;
                    return;
                }
                case "exists": {
                    this.existsLocation = location;
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

    public void setExists(String exists) {
        this.exists = exists;
    }

    public void setMissing(String missing) {
        this.missing = missing;
    }
}

