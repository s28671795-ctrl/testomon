/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.monitoring;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredAttribute;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredObject;

public class MonitoredObjectImpl
implements MonitoredObject {
    private final String name;
    private final String description;
    private Map<String, MonitoredObject> children = new HashMap<String, MonitoredObject>();
    private Map<String, MonitoredAttribute> monitoredAttributes = new HashMap<String, MonitoredAttribute>();
    private MonitoredObject parent = null;

    MonitoredObjectImpl(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public MonitoredObject getChild(String name) {
        MonitoredObjectImpl monitoredObjectImpl = this;
        synchronized (monitoredObjectImpl) {
            return this.children.get(name);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<MonitoredObject> getChildren() {
        MonitoredObjectImpl monitoredObjectImpl = this;
        synchronized (monitoredObjectImpl) {
            return this.children.values();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addChild(MonitoredObject m4) {
        if (m4 != null) {
            MonitoredObjectImpl monitoredObjectImpl = this;
            synchronized (monitoredObjectImpl) {
                this.children.put(m4.getName(), m4);
                m4.setParent(this);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeChild(String name) {
        if (name != null) {
            MonitoredObjectImpl monitoredObjectImpl = this;
            synchronized (monitoredObjectImpl) {
                this.children.remove(name);
            }
        }
    }

    @Override
    public synchronized MonitoredObject getParent() {
        return this.parent;
    }

    @Override
    public synchronized void setParent(MonitoredObject p) {
        this.parent = p;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public MonitoredAttribute getAttribute(String name) {
        MonitoredObjectImpl monitoredObjectImpl = this;
        synchronized (monitoredObjectImpl) {
            return this.monitoredAttributes.get(name);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<MonitoredAttribute> getAttributes() {
        MonitoredObjectImpl monitoredObjectImpl = this;
        synchronized (monitoredObjectImpl) {
            return this.monitoredAttributes.values();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addAttribute(MonitoredAttribute value) {
        if (value != null) {
            MonitoredObjectImpl monitoredObjectImpl = this;
            synchronized (monitoredObjectImpl) {
                this.monitoredAttributes.put(value.getName(), value);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeAttribute(String name) {
        if (name != null) {
            MonitoredObjectImpl monitoredObjectImpl = this;
            synchronized (monitoredObjectImpl) {
                this.monitoredAttributes.remove(name);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clearState() {
        MonitoredObjectImpl monitoredObjectImpl = this;
        synchronized (monitoredObjectImpl) {
            Iterator<MonitoredAttribute> i = this.monitoredAttributes.values().iterator();
            while (i.hasNext()) {
                i.next().clearState();
            }
            Iterator<MonitoredObject> i2 = this.children.values().iterator();
            while (i2.hasNext()) {
                ((MonitoredObject)((Object)i.next())).clearState();
            }
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}

