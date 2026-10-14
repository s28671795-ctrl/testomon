/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.maven.model.InputLocation;
import org.apache.maven.model.InputLocationTracker;

public class Site
implements Serializable,
Cloneable,
InputLocationTracker {
    private String id;
    private String name;
    private String url;
    private String childSiteUrlInheritAppendPath;
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation idLocation;
    private InputLocation nameLocation;
    private InputLocation urlLocation;
    private InputLocation childSiteUrlInheritAppendPathLocation;

    public Site clone() {
        try {
            Site copy = (Site)super.clone();
            if (copy.locations != null) {
                copy.locations = new LinkedHashMap<Object, InputLocation>(copy.locations);
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public String getChildSiteUrlInheritAppendPath() {
        return this.childSiteUrlInheritAppendPath;
    }

    public String getId() {
        return this.id;
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
                case "childSiteUrlInheritAppendPath": {
                    return this.childSiteUrlInheritAppendPathLocation;
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
                case "childSiteUrlInheritAppendPath": {
                    this.childSiteUrlInheritAppendPathLocation = location;
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

    public void setChildSiteUrlInheritAppendPath(String childSiteUrlInheritAppendPath) {
        this.childSiteUrlInheritAppendPath = childSiteUrlInheritAppendPath;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public boolean isChildSiteUrlInheritAppendPath() {
        return this.childSiteUrlInheritAppendPath != null ? Boolean.parseBoolean(this.childSiteUrlInheritAppendPath) : true;
    }

    public void setChildSiteUrlInheritAppendPath(boolean childSiteUrlInheritAppendPath) {
        this.childSiteUrlInheritAppendPath = String.valueOf(childSiteUrlInheritAppendPath);
    }
}

