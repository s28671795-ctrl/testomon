/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputLocationTracker;

public class RepositoryBase
implements Serializable,
Cloneable,
InputLocationTracker {
    private String id;
    private String name;
    private String url;
    private String layout = "default";
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation idLocation;
    private InputLocation nameLocation;
    private InputLocation urlLocation;
    private InputLocation layoutLocation;

    public RepositoryBase clone() {
        try {
            RepositoryBase copy = (RepositoryBase)super.clone();
            if (copy.locations != null) {
                copy.locations = new LinkedHashMap<Object, InputLocation>(copy.locations);
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RepositoryBase)) {
            return false;
        }
        RepositoryBase that = (RepositoryBase)other;
        boolean result = true;
        result = result && (this.getId() == null ? that.getId() == null : this.getId().equals(that.getId()));
        return result;
    }

    public String getId() {
        return this.id;
    }

    public String getLayout() {
        return this.layout;
    }

    @Override
    public InputLocation getLocation(Object key) {
        if (key instanceof String) {
            switch ((String)key) {
                case "": {
                    return this.location;
                }
                case "id": {
                    return this.idLocation;
                }
                case "name": {
                    return this.nameLocation;
                }
                case "url": {
                    return this.urlLocation;
                }
                case "layout": {
                    return this.layoutLocation;
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
                case "id": {
                    this.idLocation = location;
                    return;
                }
                case "name": {
                    this.nameLocation = location;
                    return;
                }
                case "url": {
                    this.urlLocation = location;
                    return;
                }
                case "layout": {
                    this.layoutLocation = location;
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

    public String getUrl() {
        return this.url;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (this.id != null ? this.id.hashCode() : 0);
        return result;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder(128);
        buf.append("id = '");
        buf.append(this.getId());
        buf.append("'");
        return buf.toString();
    }
}

