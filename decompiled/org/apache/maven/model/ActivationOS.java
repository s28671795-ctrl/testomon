/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputLocationTracker;

public class ActivationOS
implements Serializable,
Cloneable,
InputLocationTracker {
    private String name;
    private String family;
    private String arch;
    private String version;
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation nameLocation;
    private InputLocation familyLocation;
    private InputLocation archLocation;
    private InputLocation versionLocation;

    public ActivationOS clone() {
        try {
            ActivationOS copy = (ActivationOS)super.clone();
            if (copy.locations != null) {
                copy.locations = new LinkedHashMap<Object, InputLocation>(copy.locations);
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public String getArch() {
        return this.arch;
    }

    public String getFamily() {
        return this.family;
    }

    @Override
    public InputLocation getLocation(Object key) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    return this.location;
                }
                case "name": {
                    return this.nameLocation;
                }
                case "family": {
                    return this.familyLocation;
                }
                case "arch": {
                    return this.archLocation;
                }
                case "version": {
                    return this.versionLocation;
                }
            }
            return this.getOtherLocation(key);
        }
        return this.getOtherLocation(key);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void setLocation(Object key, InputLocation location) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    this.location = location;
                    return;
                }
                case "name": {
                    this.nameLocation = location;
                    return;
                }
                case "family": {
                    this.familyLocation = location;
                    return;
                }
                case "arch": {
                    this.archLocation = location;
                    return;
                }
                case "version": {
                    this.versionLocation = location;
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

    public void setArch(String arch) {
        this.arch = arch;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

