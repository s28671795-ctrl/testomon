/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputLocationTracker;

public class License
implements Serializable,
Cloneable,
InputLocationTracker {
    private String name;
    private String url;
    private String distribution;
    private String comments;
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation nameLocation;
    private InputLocation urlLocation;
    private InputLocation distributionLocation;
    private InputLocation commentsLocation;

    public License clone() {
        try {
            License copy = (License)super.clone();
            if (copy.locations != null) {
                copy.locations = new LinkedHashMap<Object, InputLocation>(copy.locations);
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public String getComments() {
        return this.comments;
    }

    public String getDistribution() {
        return this.distribution;
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
                case "url": {
                    return this.urlLocation;
                }
                case "distribution": {
                    return this.distributionLocation;
                }
                case "comments": {
                    return this.commentsLocation;
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
                case "url": {
                    this.urlLocation = location;
                    return;
                }
                case "distribution": {
                    this.distributionLocation = location;
                    return;
                }
                case "comments": {
                    this.commentsLocation = location;
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

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public String toString() {
        return "License {name=" + this.name + ", url=" + this.url + "}";
    }
}

