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

public class MailingList
implements Serializable,
Cloneable,
InputLocationTracker {
    private String name;
    private String subscribe;
    private String unsubscribe;
    private String post;
    private String archive;
    private List<String> otherArchives;
    private Map<Object, InputLocation> locations;
    private InputLocation location;
    private InputLocation nameLocation;
    private InputLocation subscribeLocation;
    private InputLocation unsubscribeLocation;
    private InputLocation postLocation;
    private InputLocation archiveLocation;
    private InputLocation otherArchivesLocation;

    public void addOtherArchive(String string) {
        this.getOtherArchives().add(string);
    }

    public MailingList clone() {
        try {
            MailingList copy = (MailingList)super.clone();
            if (this.otherArchives != null) {
                copy.otherArchives = new ArrayList<String>();
                copy.otherArchives.addAll(this.otherArchives);
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

    public String getArchive() {
        return this.archive;
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
                case "subscribe": {
                    return this.subscribeLocation;
                }
                case "unsubscribe": {
                    return this.unsubscribeLocation;
                }
                case "post": {
                    return this.postLocation;
                }
                case "archive": {
                    return this.archiveLocation;
                }
                case "otherArchives": {
                    return this.otherArchivesLocation;
                }
            }
            return this.getOtherLocation(key);
        }
        return this.getOtherLocation(key);
    }

    public String getName() {
        return this.name;
    }

    public List<String> getOtherArchives() {
        if (this.otherArchives == null) {
            this.otherArchives = new ArrayList<String>();
        }
        return this.otherArchives;
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
                case "subscribe": {
                    this.subscribeLocation = location;
                    return;
                }
                case "unsubscribe": {
                    this.unsubscribeLocation = location;
                    return;
                }
                case "post": {
                    this.postLocation = location;
                    return;
                }
                case "archive": {
                    this.archiveLocation = location;
                    return;
                }
                case "otherArchives": {
                    this.otherArchivesLocation = location;
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

    public String getPost() {
        return this.post;
    }

    public String getSubscribe() {
        return this.subscribe;
    }

    public String getUnsubscribe() {
        return this.unsubscribe;
    }

    public void removeOtherArchive(String string) {
        this.getOtherArchives().remove(string);
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOtherArchives(List<String> otherArchives) {
        this.otherArchives = otherArchives;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public void setUnsubscribe(String unsubscribe) {
        this.unsubscribe = unsubscribe;
    }

    public String toString() {
        return "MailingList {name=" + this.name + ", archive=" + this.archive + "}";
    }
}

